package com.jimandlisa;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.rmi.AccessException;
import java.util.ResourceBundle;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import mockit.Deencapsulation;
import mockit.Mock;
import mockit.MockUp;

import org.junit.Test;

public final class RestEasyTest {
	@Test
	public void ValidationExceptionXMLMapper() {
		HttpServletRequest request = new MockUp<HttpServletRequest>() {
			@Mock
			String getHeader(String name) {
				return MediaType.APPLICATION_XML;
			}
		}.getMockInstance();

		HttpHeaders headers = new MockUp<HttpHeaders>() {
			@Mock
			MediaType getMediaType() {
				return MediaType.APPLICATION_JSON_TYPE;
			}
		}.getMockInstance();

		try {
			ValidationException exception = new ValidationException("test exception", new IllegalArgumentException("blah"));
			ValidationExceptionMapper mapper = new ValidationExceptionMapper();
			Deencapsulation.setField(ValidationExceptionMapper.class, "request", request);
			Deencapsulation.setField(ValidationExceptionMapper.class, "headers", headers);
			Response response = mapper.toResponse(exception);
			assertNotNull(response);
			assertEquals(400, response.getStatus());
		} catch (Exception x) {
			x.printStackTrace();
		}
	}

	@Test
	public void ValidationExceptionJSONMapper() {
		HttpServletRequest request = new MockUp<HttpServletRequest>() {
			@Mock
			String getHeader(String name) {
				return MediaType.APPLICATION_JSON;
			}
		}.getMockInstance();

		HttpHeaders headers = new MockUp<HttpHeaders>() {
			@Mock
			MediaType getMediaType() {
				return MediaType.APPLICATION_XML_TYPE;
			}
		}.getMockInstance();

		try {
			ValidationException exception = new ValidationException("test exception", new IllegalArgumentException("blah"));
			ValidationExceptionMapper mapper = new ValidationExceptionMapper();
			Deencapsulation.setField(ValidationExceptionMapper.class, "request", request);
			Deencapsulation.setField(ValidationExceptionMapper.class, "headers", headers);
			Response response = mapper.toResponse(exception);
			assertNotNull(response);
			assertEquals(400, response.getStatus());
		} catch (Exception x) {
			x.printStackTrace();
		}
	}

	@Test
	public void ValidationExceptionOtherMapper() {
		HttpServletRequest request = new MockUp<HttpServletRequest>() {
			@Mock
			String getHeader(String name) {
				return MediaType.APPLICATION_SVG_XML;
			}
		}.getMockInstance();

		HttpHeaders headers = new MockUp<HttpHeaders>() {
			@Mock
			MediaType getMediaType() {
				return MediaType.APPLICATION_JSON_TYPE;
			}
		}.getMockInstance();

		try {
			ValidationException exception = new ValidationException("test exception", new IllegalArgumentException("blah"));
			ValidationExceptionMapper mapper = new ValidationExceptionMapper();
			Deencapsulation.setField(ValidationExceptionMapper.class, "request", request);
			Deencapsulation.setField(ValidationExceptionMapper.class, "headers", headers);
			Response response = mapper.toResponse(exception);
			assertNotNull(response);
			assertEquals(400, response.getStatus());
		} catch (Exception x) {
			x.printStackTrace();
		}
	}

	@Test
	public void ValidationExceptionDefaultMapper() {
		HttpServletRequest request = new MockUp<HttpServletRequest>() {
			@Mock
			String getHeader(String name) {
				return MediaType.APPLICATION_SVG_XML;
			}
		}.getMockInstance();

		HttpHeaders headers = new MockUp<HttpHeaders>() {
			@Mock
			MediaType getMediaType() {
				return null;
			}
		}.getMockInstance();

		try {
			ValidationException exception = new ValidationException("test exception", new IllegalArgumentException("blah"));
			ValidationExceptionMapper mapper = new ValidationExceptionMapper();
			Deencapsulation.setField(ValidationExceptionMapper.class, "request", request);
			Deencapsulation.setField(ValidationExceptionMapper.class, "headers", headers);
			Response response = mapper.toResponse(exception);
			assertNotNull(response);
			assertEquals(400, response.getStatus());
		} catch (Exception x) {
			x.printStackTrace();
		}
	}

	@Test
	public void ThrowableExceptionXMLMapper() {
		HttpServletRequest request = new MockUp<HttpServletRequest>() {
			@Mock
			String getHeader(String name) {
				return MediaType.APPLICATION_XML;
			}
		}.getMockInstance();

		HttpHeaders headers = new MockUp<HttpHeaders>() {
			@Mock
			MediaType getMediaType() {
				return MediaType.APPLICATION_JSON_TYPE;
			}
		}.getMockInstance();

		ResourceBundle.clearCache();

		try {
			AccessException exception = new AccessException("test exception", new IllegalArgumentException("blah"));
			ThrowableMapper mapper = new ThrowableMapper();
			Deencapsulation.setField(ValidationExceptionMapper.class, "request", request);
			Deencapsulation.setField(ValidationExceptionMapper.class, "headers", headers);
			Response response = mapper.toResponse(exception);
			assertNotNull(response);
			int status = response.getStatus();
			assertEquals(500, status);
		} catch (Exception x) {
			x.printStackTrace();
		}
	}

	// @Test
	// public void ValidationExceptionMapper() {
	// try {
	// POJOResourceFactory factory = new POJOResourceFactory(InvoiceService.class);
	// Dispatcher dispatcher = MockDispatcherFactory.createDispatcher();
	// dispatcher.getRegistry().addResourceFactory(factory);
	// dispatcher.getProviderFactory().addExceptionMapper(ValidationExceptionMapper.class);
	//
	// try {
	// MockHttpRequest request = MockHttpRequest.get("/rest/v1/invoice/abc");
	// MockHttpResponse response = new MockHttpResponse();
	// dispatcher.invoke(request, response);
	// System.out.println(response.getStatus());
	// System.out.println(response.getContentAsString());
	// assertEquals(400, response.getStatus());
	// } catch (URISyntaxException e) {
	// e.printStackTrace();
	// }
	// } catch (Exception x) {
	// x.printStackTrace();
	// }
	// }
}
