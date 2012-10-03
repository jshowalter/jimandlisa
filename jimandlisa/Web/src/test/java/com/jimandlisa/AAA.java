package com.jimandlisa;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.log4j.Logger;

import com.google.gson.Gson;

//import javax.servlet.http.HttpServletRequest;
//import javax.ws.rs.core.Response;
//
//import mockit.Deencapsulation;

public class AAA {

	// private static class MockHttpServletRequest implements HttpServletRequest {
	//
	// }
	/**
	 * @param args
	 */
	public static void main(String[] args) {

		Logger logger = Logger.getLogger(AAA.class.getName());
		logger.info("hi Jim");
		HttpClient httpClient = new DefaultHttpClient();
		HttpGet getRequest = new HttpGet("http://localhost:8080/WebTest/rest/v1/invoice/27");

		try {
			HttpResponse response = httpClient.execute(getRequest);
			JAXBContext context = JAXBContext.newInstance("com.jimandlisa.api.v1");
			Unmarshaller unmarshaller = context.createUnmarshaller();
			InputStream stream = response.getEntity().getContent();

			try {
				com.jimandlisa.api.v1.Invoice invoice = (com.jimandlisa.api.v1.Invoice) unmarshaller.unmarshal(stream);
				System.out.println(invoice.getName());
				System.out.println(invoice.getInvoiceID());
				System.out.println(invoice.getLineItems().size());
			} catch (JAXBException e) {
				e.printStackTrace();
			}
			stream.close();
		} catch (IOException | JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (httpClient != null) {
				httpClient.getConnectionManager().shutdown();
			}
		}

		com.jimandlisa.api.v1.Invoice invoice = new com.jimandlisa.api.v1.Invoice();
		invoice.setName("Web Invoice");
		com.jimandlisa.api.v1.InvoiceLineItem lineItem = new com.jimandlisa.api.v1.InvoiceLineItem();
		lineItem.setBalance(new BigDecimal("\u002D20.99"));
		lineItem.setName("Web items");
		invoice.getLineItems().add(lineItem);
		Gson gson = new Gson();
		String jsonInvoice = gson.toJson(invoice);
		StringEntity input;

		try {
			input = new StringEntity(jsonInvoice);
			input.setContentType("application/json");

			httpClient = new DefaultHttpClient();
			HttpPost postRequest = new HttpPost("//localhost:8080/WebTest/rest/v1/invoice/save");
			postRequest.setEntity(input);

			HttpResponse response = httpClient.execute(postRequest);

			if (response.getStatusLine().getStatusCode() != 201) {
				System.out.println("HTTP error code: " + response.getStatusLine().getStatusCode());
			} else {
				BufferedReader br = new BufferedReader(new InputStreamReader((response.getEntity().getContent())));
				String output;
				System.out.println("Output from Server .... \n");
				
				while ((output = br.readLine()) != null) {
					System.out.println(output);
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (httpClient != null) {
				httpClient.getConnectionManager().shutdown();
			}
		}

		// HttpEntity entity = response.getEntity();
		// if (entity != null) {
		// InputStream stream = entity.getContent();
		//
		// try {
		// String content = new java.util.Scanner(stream).nextLine();
		// System.out.println(content);
		// } finally {
		// stream.close();
		// }
		// }

		// MediaType mediaType = MediaType.APPLICATION_XML_TYPE;
		// ResponseBuilder builder = Response.status(Status.BAD_REQUEST);
		// builder.type(mediaType);
		// Response blah = builder.build();
		// System.out.println(blah.getStatus());
		// ValidationExceptionMapper v = new ValidationExceptionMapper();
		// //Deencapsulation.setField(v, "request", HttpServletRequest)
		// Response r = v.toResponse(new InvoiceValidationException("cover blah"));
		// System.out.println(r.getStatus());
		// System.out.println(r.getEntity());
	}

}
