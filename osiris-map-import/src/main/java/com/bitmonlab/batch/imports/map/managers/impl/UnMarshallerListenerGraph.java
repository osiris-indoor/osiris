package com.bitmonlab.batch.imports.map.managers.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.xml.bind.Unmarshaller;

import com.bitmonlab.batch.imports.map.model.osm.Member;
import com.bitmonlab.batch.imports.map.model.osm.Node;
import com.bitmonlab.batch.imports.map.model.osm.Relation;
import com.bitmonlab.batch.imports.map.model.osm.Tag;
import com.bitmonlab.batch.imports.map.model.osm.Tags;
import com.bitmonlab.batch.imports.map.model.osm.Way;

public class UnMarshallerListenerGraph extends Unmarshaller.Listener {
	
	//This method only allows two parameters!!!
		public void afterUnmarshal(Object target, Object parent) {
					
				
				Tags obj=null;			   
				if(target instanceof Node){
					obj = (Node) target;
					checkGraphIndoor(obj);
					//System.out.println(((Node)obj).getId());
				}else if (target instanceof Way){
					obj = (Way) target;		
					checkGraphIndoor(obj);
					//System.out.println(((Way)obj).getId());
				}else if (target instanceof Relation){		
					//System.out.println(((Relation)target).getId());
					outerBeforeInner((Relation) target); //Workaround for MongoDB 2.6.x
				}
									 					
				
		}
		
		
		private void outerBeforeInner(Relation relation){
			
			List<Member> inners = new ArrayList<Member>();
			List<Member> outers = new ArrayList<Member>();
			
			
			if(relation.getMembers()!=null){
				
				for(Member member : relation.getMembers()){
					
					if(member.getRole().equals("inner")){
						inners.add(member);				
					}else if(member.getRole().equals("outer")){			
						outers.add(member);								
					}
					
				}
				
				if(!outers.isEmpty() && !inners.isEmpty()){
					relation.getMembers().removeAll(outers);
					relation.getMembers().removeAll(inners);
					relation.getMembers().addAll(relation.getMembers().size(), outers);
					relation.getMembers().addAll(relation.getMembers().size(), inners);
				}
			}
					
			
			
			
		}
			
		private void checkGraphIndoor(Tags obj){
				
				if( obj!=null ){
		  				 
					if(obj.getTags()!=null){																
						Map<String, String> tagsMap = new HashMap<String, String>();								
						for (Tag t: obj.getTags()) {
							tagsMap.put(t.getK(), t.getV());
						}				
						String tagLevel = tagsMap.get("level");
						String tagHighway = tagsMap.get("highway");
						if (tagLevel != null && tagHighway != null){ 
							String tagName = tagsMap.get("name");							
							if (tagName != null) {
								//tagsMap.put("name", tagName + "_level" + tagLevel);		
								tagsMap.put("name", tagName);
							}							
							obj.setTags(rebuildNodeTagsOSMAndAddTagGraphIndoor(tagsMap));
						}else if(tagHighway != null && (tagHighway.equals("elevator") || tagHighway.equals("steps")) ){							
							obj.setTags(rebuildNodeTagsOSMAndAddTagGraphIndoor(tagsMap));
						}
					}
					
				}	
							
			}
			
		private List<Tag> rebuildNodeTagsOSMAndAddTagGraphIndoor(Map<String, String> tagsMap){
				
				Iterator<Entry<String,String>> iter = tagsMap.entrySet().iterator();						
				List<Tag> newListTag = new ArrayList<Tag>();
				while (iter.hasNext()) {
					Entry<String, String> entry = iter.next();
					Tag tag = new Tag();
					tag.setK(entry.getKey());
					tag.setV(entry.getValue());
					newListTag.add(tag);						
				}
				
				Tag tagGraphIndoor = new Tag();
				tagGraphIndoor.setK("@graphIndoor");
				tagGraphIndoor.setV("true");		
				newListTag.add(tagGraphIndoor);
				
				return newListTag;
				
			}
						


}
