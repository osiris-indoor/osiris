package com.bitmonlab.osiris.restsender;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import javax.ws.rs.core.MediaType;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPatch;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.AbstractHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.codehaus.jackson.map.ObjectMapper;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientHandlerException;
import com.sun.jersey.api.client.GenericType;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.WebResource.Builder;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.core.header.InBoundHeaders;
import com.sun.jersey.multipart.FormDataMultiPart;
import com.sun.jersey.multipart.file.FileDataBodyPart;
import com.sun.jersey.multipart.impl.MultiPartWriter;

public class RestRequestSender {

	private static String MEDIA = "application/json";

	private static String MULTIPART_MEDIA = "multipart/form-data";

	private String host;

	private String contextRoot;

	private String acceptMediaType = MediaType.APPLICATION_JSON;

	private String applicationType = MediaType.APPLICATION_JSON;

	private ClientConfig config = new DefaultClientConfig();

	public RestRequestSender(String host, String contextRoot) {
		this.host = host;
		this.contextRoot = contextRoot;
	}

	public <T> ClientResponse<T> invoke(RestMethod method, String url, Class<T> expectedResponse, Headers... headers) {
		return invoke(method, url, expectedResponse, (BasicAuth) null, headers);
	}

	public <T> ClientResponse<T> invoke(RestMethod method, String url, Class<T> expectedResponse, BasicAuth basicAuth,
			Headers... headers) {
		Builder builder = createBuilder(url, headers, basicAuth);

		com.sun.jersey.api.client.ClientResponse clienteResponse = null;
		switch (method) {
		case GET:
			clienteResponse = builder.get(com.sun.jersey.api.client.ClientResponse.class);
			break;
		case DELETE:
			clienteResponse = builder.delete(com.sun.jersey.api.client.ClientResponse.class);
			break;
		case HEAD:
			clienteResponse = builder.head();
			break;
		default:
			throw new UnsupportedOperationException();
		}

		return new ClientResponse<T>(clienteResponse, expectedResponse);
	}

	public <T> ClientResponse<T> invoke(RestMethod method, String url, GenericType<T> genericType, Headers... headers) {
		return invoke(method, url, genericType, (BasicAuth) null, headers);
	}

	public <T> ClientResponse<T> invoke(RestMethod method, String url, GenericType<T> genericType, BasicAuth basicAuth,
			Headers... headers) {
		Builder builder = createBuilder(url, headers, basicAuth);

		com.sun.jersey.api.client.ClientResponse clienteResponse = null;
		switch (method) {
		case GET:
			clienteResponse = builder.get(com.sun.jersey.api.client.ClientResponse.class);
			break;
		case DELETE:
			clienteResponse = builder.delete(com.sun.jersey.api.client.ClientResponse.class);
			break;
		default:
			throw new UnsupportedOperationException();
		}

		return new ClientResponse<T>(clienteResponse, genericType);
	}

	public <T> ClientResponse<T> invoke(RestMethod method, String url, Object requestEntity, Class<T> expectedResponse,
			Headers... headers) {
		return invoke(method, url, requestEntity, expectedResponse, null, headers);
	}

	public <T> ClientResponse<T> invoke(RestMethod method, String url, Object requestEntity, Class<T> expectedResponse,
			BasicAuth basicAuth, Headers... headers) {
		Builder builder = createBuilder(url, headers, basicAuth);

		com.sun.jersey.api.client.ClientResponse clienteResponse = null;
		switch (method) {
		case POST:
			clienteResponse = builder.post(com.sun.jersey.api.client.ClientResponse.class, requestEntity);
			break;
		case PUT:
			clienteResponse = builder.put(com.sun.jersey.api.client.ClientResponse.class, requestEntity);
			break;
		case DELETE:
			clienteResponse = builder.delete(com.sun.jersey.api.client.ClientResponse.class, requestEntity);
			break;
		default:
			throw new UnsupportedOperationException();
		}

		return new ClientResponse<T>(clienteResponse, expectedResponse);
	}

	public <T> ClientResponse<T> invoke(RestMethod method, String url, Object requestEntity,
			GenericType<T> genericType, Headers... headers) {
		return invoke(method, url, requestEntity, genericType, (BasicAuth) null, headers);
	}

	public <T> ClientResponse<T> invoke(RestMethod method, String url, Object requestEntity,
			GenericType<T> genericType, BasicAuth basicAuth, Headers... headers) {

		Builder builder = createBuilder(url, headers, basicAuth);

		com.sun.jersey.api.client.ClientResponse clienteResponse = null;
		switch (method) {
		case POST:
			clienteResponse = builder.post(com.sun.jersey.api.client.ClientResponse.class, requestEntity);
			break;
		case PUT:
			clienteResponse = builder.put(com.sun.jersey.api.client.ClientResponse.class, requestEntity);
			break;
		case DELETE:
			clienteResponse = builder.delete(com.sun.jersey.api.client.ClientResponse.class, requestEntity);
			break;
		default:
			throw new UnsupportedOperationException();
		}

		return new ClientResponse<T>(clienteResponse, genericType);
	}

	public SimpleClientResponse invoke(RestMethod method, String url, Object requestEntity, Headers... headers) {
		return invoke(method, url, requestEntity, (BasicAuth) null, headers);
	}

	public SimpleClientResponse invoke(RestMethod method, String url, Object requestEntity, BasicAuth basicAuth,
			Headers... headers) {
		Builder builder = createBuilder(url, headers, basicAuth);

		com.sun.jersey.api.client.ClientResponse clienteResponse = null;
		switch (method) {
		case POST:
			clienteResponse = builder.post(com.sun.jersey.api.client.ClientResponse.class, requestEntity);
			break;
		case PUT:
			clienteResponse = builder.put(com.sun.jersey.api.client.ClientResponse.class, requestEntity);
			break;
		case PATCH:
			try {
				AbstractHttpClient httpclient = new DefaultHttpClient();
				HttpPatch patch = new HttpPatch(createURI(url));
				for (Headers header : headers) {
					patch.addHeader(header.getKey(), header.getValue());
				}

				ObjectMapper mapper = new ObjectMapper();
				StringEntity entity = new StringEntity(mapper.writeValueAsString(requestEntity));

				entity.setContentType("application/json");
				patch.setEntity(entity);

				HttpResponse response = httpclient.execute(patch);

				Header[] headerList = response.getAllHeaders();
				InBoundHeaders i = new InBoundHeaders();
				for (Header header : headerList) {
					i.add(header.getName(), header.getValue());
				}

				clienteResponse = new com.sun.jersey.api.client.ClientResponse(
						response.getStatusLine().getStatusCode(), i, response.getEntity().getContent(), null);
			} catch (Exception e) {
				throw new ClientHandlerException(e.getMessage());
			}
			break;
		case DELETE:
			clienteResponse = builder.delete(com.sun.jersey.api.client.ClientResponse.class, requestEntity);
			break;
		default:
			throw new UnsupportedOperationException();
		}

		return new SimpleClientResponse(clienteResponse);
	}

	public SimpleClientResponse invoke(RestMethod method, String url, Headers... headers) {
		return invoke(method, url, (BasicAuth) null, headers);
	}

	public SimpleClientResponse invoke(RestMethod method, String url, BasicAuth basicAuth, Headers... headers) {

		Builder builder = createBuilder(url, headers, basicAuth);

		com.sun.jersey.api.client.ClientResponse clienteResponse = null;
		switch (method) {
		case GET:
			clienteResponse = builder.get(com.sun.jersey.api.client.ClientResponse.class);
			break;
		case DELETE:
			clienteResponse = builder.delete(com.sun.jersey.api.client.ClientResponse.class);
			break;
		case POST:
			clienteResponse = builder.post(com.sun.jersey.api.client.ClientResponse.class);
			break;
		case PUT:
			clienteResponse = builder.put(com.sun.jersey.api.client.ClientResponse.class);
			break;			
		case HEAD:
			clienteResponse = builder.head();
			break;

		default:
			throw new UnsupportedOperationException();
		}

		return new SimpleClientResponse(clienteResponse);
	}

	private Builder createBuilder(String url, Headers[] headers, BasicAuth auth) {
		WebResource resource;
		if (null != auth) {
			resource = Client.create(config).resource(createURI(url));
			resource.addFilter(new com.sun.jersey.api.client.filter.HTTPBasicAuthFilter(auth.getName(), auth
					.getPassword()));
		} else {
			resource = Client.create(config).resource(createURI(url));
		}

		Builder builder = resource.accept(acceptMediaType).type(applicationType);
		for (Headers h : headers) {
			builder.header(h.getKey(), h.getValue());
		}
		return builder;
	}

	protected String createURI(String resourceUri) {
		String result = null;

		if (resourceUri.startsWith("http"))
			return resourceUri;

		if (resourceUri.startsWith("/"))
			resourceUri = resourceUri.substring(1);

		if (StringUtils.isBlank(contextRoot)) {
			result = host + "/" + resourceUri;
		} else {
			result = host + "/" + contextRoot + "/" + resourceUri;
		}

		System.out.println("TEST URI:" + result);

		return result;
	}

	public void uploadVoid(String url, File f, String formName) {

		FormDataMultiPart form = new FormDataMultiPart().field(formName, f, MediaType.MULTIPART_FORM_DATA_TYPE);
		String urlCreated = createURI(url);
		ClientConfig cc = new DefaultClientConfig();
		cc.getClasses().add(MultiPartWriter.class);
		WebResource webResource = Client.create(cc).resource(urlCreated);
		webResource.type(MULTIPART_MEDIA).accept(MEDIA).post(form);
	}

	public void uploadVoid(String url, File f, String formName, Headers... headers) {

		FormDataMultiPart form = new FormDataMultiPart().field(formName, f, MediaType.MULTIPART_FORM_DATA_TYPE);
		String urlCreated = createURI(url);
		ClientConfig cc = new DefaultClientConfig();
		cc.getClasses().add(MultiPartWriter.class);
		WebResource webResource = Client.create(cc).resource(urlCreated);

		Builder builder = webResource.type(MULTIPART_MEDIA).accept(MEDIA);

		for (Headers h : headers) {
			builder.header(h.getKey(), h.getValue());
		}

		builder.post(form);
	}

	public SimpleClientResponse upload(String url, File f, String formName) {
		@SuppressWarnings("resource")
		FormDataMultiPart form = new FormDataMultiPart().field(formName, f, MediaType.MULTIPART_FORM_DATA_TYPE);
		String urlCreated = createURI(url);
		ClientConfig cc = new DefaultClientConfig();
		cc.getClasses().add(MultiPartWriter.class);
		WebResource webResource = Client.create(cc).resource(urlCreated);
		webResource.type(MULTIPART_MEDIA).accept(MEDIA).post(form);
		return new SimpleClientResponse(com.sun.jersey.api.client.ClientResponse.Status.NO_CONTENT);
	}

	public ClientResponse<File> upload(String url, File f, Headers... headers) {
		@SuppressWarnings("resource")
		FormDataMultiPart form = new FormDataMultiPart();
		form.bodyPart(new FileDataBodyPart("file", f, MediaType.APPLICATION_OCTET_STREAM_TYPE));

		String urlCreated = createURI(url);
		ClientConfig cc = new DefaultClientConfig();
		cc.getClasses().add(MultiPartWriter.class);
		WebResource webResource = Client.create(cc).resource(urlCreated);
		Builder builder = webResource.type(MULTIPART_MEDIA).accept(MEDIA).accept("text/plain");
		for (Headers h : headers) {
			builder.header(h.getKey(), h.getValue());
		}

		com.sun.jersey.api.client.ClientResponse clienteResponse = null;

		clienteResponse = builder.post(com.sun.jersey.api.client.ClientResponse.class, form);

		return new ClientResponse<File>(clienteResponse, File.class);
	}
	
	public <T> ClientResponse<T> upload(String url, File f, Class<T> expectedResponse, Headers... headers) {
		
		@SuppressWarnings("resource")
		FormDataMultiPart form = new FormDataMultiPart();
		form.bodyPart(new FileDataBodyPart("file", f, MediaType.APPLICATION_OCTET_STREAM_TYPE));

		String urlCreated = createURI(url);
		ClientConfig cc = new DefaultClientConfig();
		cc.getClasses().add(MultiPartWriter.class);
		WebResource webResource = Client.create(cc).resource(urlCreated);
		Builder builder = webResource.type(MULTIPART_MEDIA).accept(MEDIA).accept("text/plain");
		for (Headers h : headers) {
			builder.header(h.getKey(), h.getValue());
		}

		com.sun.jersey.api.client.ClientResponse clienteResponse = null;

		clienteResponse = builder.post(com.sun.jersey.api.client.ClientResponse.class, form);

		return new ClientResponse<T>(clienteResponse, expectedResponse);
	}

	public ClientResponse<File> uploadNoMultipart(String url, File f, Headers... headers) throws FileNotFoundException {

		InputStream is = new FileInputStream(f);

		String urlCreated = createURI(url);
		ClientConfig cc = new DefaultClientConfig();
		cc.getClasses().add(MultiPartWriter.class);
		WebResource webResource = Client.create(cc).resource(urlCreated);
		Builder builder = webResource.type(MediaType.APPLICATION_OCTET_STREAM).accept(MEDIA).accept("text/plain");

		String sContentDisposition = "attachment; filename=\"" + f.getName() + "\"";
		builder.header("Content-Disposition", sContentDisposition);

		for (Headers h : headers) {
			builder.header(h.getKey(), h.getValue());
		}

		com.sun.jersey.api.client.ClientResponse clienteResponse = null;

		clienteResponse = builder.post(com.sun.jersey.api.client.ClientResponse.class, is);

		return new ClientResponse<File>(clienteResponse, File.class);
	}

	public ClientResponse<InputStream> download(String url,String type, String accept,  Headers... headers) {
	
		WebResource resource = Client.create(config).resource(createURI(url));
		
		Builder builder = resource.accept(accept,MEDIA).type(type);
		for (Headers h : headers) {
			builder.header(h.getKey(), h.getValue());
		}

		com.sun.jersey.api.client.ClientResponse clienteResponse = null;
		clienteResponse = builder.get(com.sun.jersey.api.client.ClientResponse.class);
		
		return new ClientResponse<InputStream>(clienteResponse, InputStream.class);
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public void setContextRoot(String contextRoot) {
		this.contextRoot = contextRoot;
	}

	public String getContextRoot() {
		return contextRoot;
	}

}
