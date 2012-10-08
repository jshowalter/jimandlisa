package com.jimandlisa;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;
import java.util.UUID;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import org.apache.log4j.Logger;

@Provider
public class ThrowableMapper implements ExceptionMapper<Throwable> {
	@Context
	private static HttpServletRequest request;
	@Context
	private static HttpHeaders headers;
	
	public ThrowableMapper() {
		super();
	}
	
	/***
	 * Converts date to GMT string.	
	 * @param date
	 * @return
	 */
	private String toGmtString(Date date) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		dateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
		return dateFormat.format(date);
	}

	@Override
	public Response toResponse(Throwable exception) {
		Status status = null;
		MediaType mediaType = null;
		String acceptHeader = request != null ? request.getHeader("accept") : MediaType.APPLICATION_XML;
		Date currentDate = new Date();
		String errorMessage = toGmtString(currentDate) + ":" + UUID.randomUUID().toString() + " -- " + exception.getMessage();
		Logger logger = Logger.getLogger(ThrowableMapper.class);
		logger.error(errorMessage);

		if (exception instanceof ValidationException) {
			status = Status.BAD_REQUEST;
			errorMessage = exception.getLocalizedMessage();
		} else {
			status = Status.INTERNAL_SERVER_ERROR;
			errorMessage = Messages.getString("InvoiceOperations.1"); //$NON-NLS-1$;
		}

		if (MediaType.APPLICATION_XML.equals(acceptHeader)) {
			mediaType = MediaType.APPLICATION_XML_TYPE;
		} else if (MediaType.APPLICATION_JSON.equals(acceptHeader)) {
			mediaType = MediaType.APPLICATION_JSON_TYPE;
		} else {
			mediaType = headers != null ? headers.getMediaType() : MediaType.APPLICATION_XML_TYPE;

			if (mediaType == null) {
				mediaType = MediaType.APPLICATION_XML_TYPE;
			}
		}

		ResponseBuilder builder = Response.status(status);
		builder.type(mediaType);
		builder.entity(errorMessage);
		return builder.build();
	}
}
