package com.bitmonlab.batch.imports.map.managers.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Named;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.openstreetmap.osmosis.core.Osmosis;
import org.springframework.beans.factory.annotation.Value;

import com.bitmonlab.commons.api.map.model.geojson.Feature;
import com.bitmonlab.commons.api.map.model.geojson.LineString;
import com.bitmonlab.commons.api.map.model.geojson.MetaData;
import com.bitmonlab.commons.api.map.model.geojson.Point;
import com.bitmonlab.commons.api.map.model.geojson.Polygon;
import com.bitmonlab.batch.imports.map.dao.api.ImportFilesRepository;
import com.bitmonlab.batch.imports.map.dao.api.ImportRepository;
import com.bitmonlab.batch.imports.map.dao.api.MetaDataImportRepository;
import com.bitmonlab.batch.imports.map.exceptions.BackgroundMapBuilderException;
import com.bitmonlab.batch.imports.map.exceptions.ExecutionNotAllowed;
import com.bitmonlab.batch.imports.map.exceptions.ImportFilesException;
import com.bitmonlab.batch.imports.map.exceptions.InternalErrorException;
import com.bitmonlab.batch.imports.map.exceptions.ParseMapException;
import com.bitmonlab.batch.imports.map.exceptions.QueryException;
import com.bitmonlab.batch.imports.map.managers.api.ImportOSMFileManager;
import com.bitmonlab.batch.imports.map.model.osm.Bounds;
import com.bitmonlab.batch.imports.map.model.osm.Member;
import com.bitmonlab.batch.imports.map.model.osm.ND;
import com.bitmonlab.batch.imports.map.model.osm.Node;
import com.bitmonlab.batch.imports.map.model.osm.OSM;
import com.bitmonlab.batch.imports.map.model.osm.Relation;
import com.bitmonlab.batch.imports.map.model.osm.Tag;
import com.bitmonlab.batch.imports.map.model.osm.Way;
import com.bitmonlab.batch.imports.map.utils.Cryptography;


@Named
public class ImportOSMFileManagerImpl implements ImportOSMFileManager {
	
	@Inject	
	private ImportRepository importRepository;
	
	@Inject
	private MetaDataImportRepository metaDataRepository;
	
	@Inject
	private ImportFilesRepository importFilesRepository;
	
	@Value("${pathRootUser}")
	private String pathRootUser;
	
	private final String file_osm = "map.osm";
	
	private final String file_map = "background.map";

	public void importOSMFile(final String appIdentifier,
							  final InputStream data) 
									  throws ExecutionNotAllowed, InternalErrorException, ParseMapException, QueryException, IOException, NoSuchAlgorithmException, BackgroundMapBuilderException, ImportFilesException{

		OSM osm = null;
		OutputStream os = null;
		String jsonStr = null;
		
		final String pathUser = pathRootUser.concat(File.separator)
				.concat(appIdentifier).concat(File.separator);
		final String strOSMFile = pathUser.concat(file_osm);
		
		File dPathUser = new File(pathUser);
		if (!dPathUser.exists()) {
			boolean createdPathUser=dPathUser.mkdirs();
			if(!createdPathUser){
				throw new InternalErrorException();
			}
		}
		
		if (importRepository.isImportProcessLocked(appIdentifier)) {
			throw new ExecutionNotAllowed();
		} else {
			importRepository.lockImportProcess(appIdentifier);
		}

		try {				
			
			//Parse map .osm file (xml format)
			JAXBContext context = JAXBContext.newInstance(OSM.class);
			Unmarshaller unmarshaller = context.createUnmarshaller();
			UnMarshallerListener umlistener = new UnMarshallerListener();
			unmarshaller.setListener(umlistener);
			osm = (OSM) unmarshaller.unmarshal(data);

			Marshaller marshaller = context.createMarshaller();	
			MarshallerListener mlistener = null;
			if (osm.getBounds() == null) {
				mlistener = new MarshallerListener();
				marshaller.setListener(mlistener);
			}
			
			os = new FileOutputStream(strOSMFile);
			marshaller.marshal(osm, os);
			os.close();

			//Convert OSM to GeoJson and save in MongoDB
			Collection<Feature> featureCollection = transformOSMFileToGeoJson(
					osm, appIdentifier);

			jsonStr=importRepository.saveGeoJson(appIdentifier,featureCollection);
			
			//Calculate Bounds of Map
			Bounds boundsMap = new Bounds();
			if (mlistener != null) {
				boundsMap = mlistener.getBoundsMap();
			} else {
				boundsMap = osm.getBounds();
			}
			
			MetaData metaData = generateMetaData(jsonStr, boundsMap, appIdentifier);
			
			metaDataRepository.save(metaData);
			
			backgroundMapBuilder(appIdentifier, pathUser, strOSMFile, boundsMap);
			
			saveImportFiles(appIdentifier, pathUser);

			deleteImportFilesDisk(pathUser);
									

		} catch (JAXBException parseException) {
			throw new ParseMapException();
		} catch (IOException ioe) {
			throw new InternalErrorException();
		} 
			finally {

			importRepository.deleteLockImportProcess(appIdentifier);

			if (os != null) {
				os.close();
			}
		}		
	}
	
	public Collection<Feature> transformOSMFileToGeoJson(final OSM osm,
			final String appIdentifier){

		List<Feature> featureCollection = new ArrayList<Feature>();

		osm.sortNodes();
		osm.sortWays();
		osm.sortRelations();

		if (osm.getRelations() != null) {
			for (Relation relation : osm.getRelations()) {
				relationToPolygon(relation, osm, null, featureCollection,
						appIdentifier);
			}
		}

		if (osm.getWays() != null) {
			for (Way way : osm.getWays()) {
				wayToLineString(way, osm, null, featureCollection,
						appIdentifier);
			}
		}

		if (osm.getNodes() != null) {
			for (Node node : osm.getNodes()) {
				nodeToPoint(appIdentifier, node, null, featureCollection);
			}
		}

		return featureCollection;
	}
	
	private Feature relationToPolygon(final Relation relation, final OSM osm,
			final List<Map<String, String>> inheritedProperties,
			final List<Feature> featureCollection, final String appIdentifier){

		Feature feature = new Feature();
		feature.setId(relation.getId());

		int pos = searchFeature(featureCollection, feature);

		if (pos < 0) {

			if (inheritedProperties != null) {
				feature.setPropertiesRelations(inheritedProperties);
			}			

			Collection<List<List<Double>>> coordinatesPolygon = new ArrayList<List<List<Double>>>();
			
			Map<String, String> properties = tagsToProperties(
					relation.getTags(), relation.getId(), "relation");

			feature.setProperties(properties);

			if(relation.getMembers()!=null){
				for (Member member : relation.getMembers()) {
	
					if (!member.getRef().equals(relation.getId())) {
	
						Feature featureChild = createRelationsChilds(osm, feature,
								member, featureCollection, appIdentifier);
	
						if (!member.getType().equals("relation")
								&& featureChild != null
								&& featureChild.getGeometry() != null
								&& (featureChild.getGeometry() instanceof LineString)
								&& (member.getRole().equals("shell") || (properties
										.get("type").equals("multipolygon") && (member
										.getRole().equals("inner") || member
										.getRole().equals("outer"))))) {
	
							LineString lineString = (LineString) featureChild
									.getGeometry();
							List<List<Double>> coordinatesList = new ArrayList<List<Double>>();
							coordinatesList.addAll(lineString.getCoordinates());
							Point firstPoint = new Point();
							firstPoint
									.setCoordinates((List<Double>) coordinatesList
											.get(0));
							Point lastPoint = new Point();
							lastPoint.setCoordinates((List<Double>) coordinatesList
									.get(coordinatesList.size() - 1));
							if (!firstPoint.equals(lastPoint)) {
								coordinatesList.add(coordinatesList.get(0));
							}
							coordinatesPolygon
									.add((ArrayList<List<Double>>) coordinatesList);
	
						} // end if 2
					} // end if 1
				}// end for
			}

			Polygon poly = null;
			if (!coordinatesPolygon.isEmpty()) {
				poly = new Polygon();
				poly.setCoordinates(coordinatesPolygon);
			}
			feature.setGeometry(poly);
			featureCollection.add(feature);
			sortFeatures(featureCollection);

		} else {

			if (inheritedProperties != null) {
				feature = featureCollection.get(pos);
				feature.updateProperties(inheritedProperties);
				for (Member m : relation.getMembers()) {
					createRelationsChilds(osm, feature, m, featureCollection,
							appIdentifier);
				}
			}
		}

		return feature;
	}
	
	private Feature wayToLineString(final Way way, final OSM osm,
			final List<Map<String, String>> inheritedProperties,
			final List<Feature> featureCollection, final String appIdentifier){

		Feature feature = new Feature();
		feature.setId(way.getId());

		int pos = searchFeature(featureCollection, feature);

		if (pos < 0) {

			if (inheritedProperties != null) {
				feature.setPropertiesRelations(inheritedProperties);
			}
		
			Map<String, String> properties = tagsToProperties(way.getTags(),
					way.getId(), "way");

			feature.setProperties(properties);

			LineString ls = new LineString();
			List<List<Double>> coordinatesList = new ArrayList<List<Double>>();
			List<Map<String, String>> parentProperties = feature
					.getAllProperties();

			if (way.getNds() != null && osm.getNodes() != null) {

				for (ND n : way.getNds()) {

					int posNode = osm.searchNode(new Node(n.getRef()));

					if (posNode >= 0) {
						Node node = osm.getNodes().get(posNode);
						Point nodePoint = new Point();
						nodePoint.setCoordinates(node.getNodeCoordinates());
						boolean bInsert = true;
						ListIterator<List<Double>> it = coordinatesList
								.listIterator();
						while (bInsert && it.hasNext()) {
							List<Double> coordinate = it.next();
							Point otherPoint = new Point();
							otherPoint.setCoordinates(coordinate);
							if (nodePoint.equals(otherPoint)
									&& !(it.nextIndex() - 1 == 0 && way
											.getNds().indexOf(n) == way
											.getNds().size() - 1)) {

								bInsert = false;
							}
						}

						if (bInsert) {
							coordinatesList.add((ArrayList<Double>) node
									.getNodeCoordinates());
						}
						
						if(way.existTag("@graphIndoor")){    
							if(n.getRef().equals(way.getNds().get(0).getRef()) || 
									n.getRef().equals(way.getNds().get(way.getNds().size()-1).getRef())){
									Tag extremTag = new Tag();
		                            extremTag.setK("@end");
		                            extremTag.setV("true");
		                            if(node.getTags()!=null){
		                            	node.getTags().add(extremTag);
		                            }
		                            else{
		                            	List<Tag> tags=new ArrayList<Tag>();
		                            	tags.add(extremTag);
		                            	node.setTags(tags);
		                            }
							}
						} 

						nodeToPoint(appIdentifier, node, parentProperties, featureCollection);
					}
				}

			}

			if (coordinatesList.isEmpty()) {
				ls = null;
			} else {
				ls.setCoordinates(coordinatesList);
				ls.calculateCentroid();
			}

			feature.setGeometry(ls);
			featureCollection.add(feature);
			sortFeatures(featureCollection);

		} else {

			if (inheritedProperties != null) {
				feature = featureCollection.get(pos);
				feature.updateProperties(inheritedProperties);
				for (ND n : way.getNds()) {
					int positionNode = osm.searchNode(new Node(n.getRef()));
					if (positionNode >= 0) {
						Node node = osm.getNodes().get(positionNode);
						nodeToPoint(appIdentifier, node, feature.getAllProperties(),
								featureCollection);
					}
				}
			}

		}

		return feature;
	}
	
	private Feature nodeToPoint(final String appIdentifier,
			final Node node,
			final List<Map<String, String>> inheritedProperties,
			final List<Feature> featureCollection){

		Feature feature = null;
		
		feature = new Feature();
		feature.setId(node.getId());
		
		int pos = searchFeature(featureCollection, feature);
		
		if (pos < 0) {
		
			if (inheritedProperties != null) {
				feature.setPropertiesRelations(inheritedProperties);
			}
					
			Map<String, String> properties = tagsToProperties(node.getTags(),
			node.getId(), "node");
			
			feature.setProperties(properties);
			
			Point point = new Point();
			point.setCoordinates(node.getNodeCoordinates());
			
			feature.setGeometry(point);
			featureCollection.add(feature);
			sortFeatures(featureCollection);
		
		} else {
		
			if (inheritedProperties != null) {
				feature = featureCollection.get(pos);
				feature.updateProperties(inheritedProperties);
			}
		}
		
		return feature;
		
	}
	
	private int searchFeature(final List<Feature> featureCollection,
			final Feature feature) {

		int pos = -1;

		if (featureCollection != null && feature != null) {
			pos = Collections.binarySearch(featureCollection, feature);
		}

		return pos;
	}
	
	private Map<String, String> tagsToProperties(final List<Tag> tagslist,
			final String id, final String type) {

		Map<String, String> properties = new HashMap<String, String>();
		properties.put("@id", id);
		properties.put("@type", type);
		if (tagslist != null) {
			for (Tag t : tagslist) {
				properties.put(t.getK(), t.getV());
			}
		}

		return properties;

	}
	
	private Feature createRelationsChilds( 
			final OSM osm,
			final Feature featureParent, final Member member,
			final List<Feature> featureCollection, final String appIdentifier){

		Feature featureChild = null;
		List<Map<String, String>> parentProperties = featureParent
		.getAllProperties();
		
		if (member.getType().equals("node") && osm.getNodes() != null) {
			int pos = osm.searchNode(new Node(member.getRef()));
			if (pos >= 0) {
				Node node = osm.getNodes().get(pos);
				featureChild = nodeToPoint(appIdentifier, node, parentProperties,
						featureCollection);
			}
		} else if (member.getType().equals("way") && osm.getWays() != null) {
			int pos = osm.searchWay(new Way(member.getRef()));
			if (pos >= 0) {
				Way way = osm.getWays().get(pos);
				featureChild = wayToLineString(way, osm, parentProperties,
						featureCollection, appIdentifier);
			}
		} else if (member.getType().equals("relation")
				&& osm.getRelations() != null) {
			int pos = osm.searchRelation(new Relation(member.getRef()));
			if (pos >= 0) {
				Relation relationChild = osm.getRelations().get(pos);
				relationToPolygon(relationChild, osm, parentProperties,
					featureCollection, appIdentifier);
			}
		}
		
		if (featureChild != null && featureChild.getProperties() != null
				&& member.getRole() != null) {
			featureChild.getProperties().put("@role", member.getRole());
		}
		
		return featureChild;
		
	}
	
	private void sortFeatures(final List<Feature> featuresList) {

		if (featuresList != null) {
			Collections.sort((List<Feature>) featuresList);
		}

	}
	
	private MetaData generateMetaData(String strjson, Bounds boundsMap,
			String appId) throws NoSuchAlgorithmException{

		String strChkSum = Cryptography.calculateCheckSum(strjson);
		MetaData metaData = new MetaData();
		metaData.setAppId(appId);
		metaData.setOSMChecksum(strChkSum);

		metaData.setMaxlat(Double.valueOf(boundsMap.getMaxlat()));
		metaData.setMaxlon(Double.valueOf(boundsMap.getMaxlon()));
		metaData.setMinlat(Double.valueOf(boundsMap.getMinlat()));
		metaData.setMinlon(Double.valueOf(boundsMap.getMinlon()));

		return metaData;

	}
	
	private void backgroundMapBuilder(final String appIdentifier,
			final String pathUser, final String osmFile, Bounds boundsMap) throws BackgroundMapBuilderException{

		try {

			String[] args = {
					"-plugin",
					"org.mapsforge.map.writer.osmosis.MapFileWriterPluginLoader",					
					"--read-xml",
					osmFile,
					"--mapfile-writer",
					"file=".concat(pathUser.concat(file_map)),
					"bbox=".concat(boundsMap.getMinlat()).concat(",")
							.concat(boundsMap.getMinlon()).concat(",")
							.concat(boundsMap.getMaxlat()).concat(",")
							.concat(boundsMap.getMaxlon())};

			
			Osmosis.run(args);			

		} catch (Exception e) {
			e.printStackTrace();
			throw new BackgroundMapBuilderException();
		}

	}
	
	private void saveImportFiles(String appIdentifier, String pathUser) throws IOException, ImportFilesException{

		String pathOSM = pathUser.concat(file_osm);
		String pathMap = pathUser.concat(file_map);

		File fileOSM = new File(pathOSM);
		File fileMap = new File(pathMap);

		if (fileOSM.exists() && fileMap.exists()) {
			importFilesRepository.saveFileOSM(appIdentifier, fileOSM);
			importFilesRepository.saveFileMap(appIdentifier, fileMap);
		} else {
			throw new ImportFilesException();
		}

	}
	
	private void  deleteImportFilesDisk(String pathUser) throws ImportFilesException{

		File folder=new File(pathUser);
		
		if(folder.exists()&&folder.isDirectory()){
			 File[] files = folder.listFiles();
			 for(File file: files){
				 file.delete();
			 }
		}
		else{
			throw new ImportFilesException();
		}
	}
	

}
