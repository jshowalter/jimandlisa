package com.jimandlisa;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.Provider;
import javax.ws.rs.ext.ExceptionMapper;

@Provider
public class ServiceExceptionMapper implements ExceptionMapper<ServiceException> {
	@Context
	private static HttpServletRequest request;
	@Context
	private static HttpHeaders headers;

	@Override
	public Response toResponse(ServiceException exception) {
		MediaType mediaType = null;
		String acceptHeader = request.getHeader("accept");
		
		if (MediaType.APPLICATION_XML.equals(acceptHeader)) {
			mediaType = MediaType.APPLICATION_XML_TYPE;
		} else if (MediaType.APPLICATION_JSON.equals(acceptHeader)) {
			mediaType = MediaType.APPLICATION_JSON_TYPE;
		} else {
			mediaType = headers.getMediaType();

			if (mediaType == null) {
				mediaType = MediaType.APPLICATION_XML_TYPE;
			}
		}
		
		ResponseBuilder builder = Response.status(Status.BAD_REQUEST);
		builder.type(mediaType);
		return builder.build();
	}
}
