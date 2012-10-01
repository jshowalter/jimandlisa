package com.jimandlisa.api.v1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.math.BigDecimal;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;
import org.junit.Test;

import com.google.gson.Gson;

public class HttpClientTest {

	@Test
	public void readInvoice() {
		HttpClient httpClient = new DefaultHttpClient();
		HttpGet getRequest = new HttpGet("http://localhost:8080/WebTest/rest/v1/invoice/27");

		try {
			HttpResponse response = httpClient.execute(getRequest);
			JAXBContext context = JAXBContext.newInstance("com.jimandlisa.api.v1");
			Unmarshaller unmarshaller = context.createUnmarshaller();
			InputStream stream = response.getEntity().getContent();

			try {
				Invoice invoice = (Invoice) unmarshaller.unmarshal(stream);
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
	}

	@Test
	public void saveInvoice() {
		Invoice invoice = new Invoice();
		invoice.setName("Web Invoice");
		InvoiceLineItem lineItem = new InvoiceLineItem();
		lineItem.setBalance(new BigDecimal("\u002D20.99"));
		lineItem.setName("Web item 1");
		invoice.getLineItems().add(lineItem);
		InvoiceLineItem lineItem2 = new InvoiceLineItem();
		lineItem2.setBalance(new BigDecimal("\u002D12.27"));
		lineItem2.setName("Web item 2");
		invoice.getLineItems().add(lineItem2);
		HttpClient httpClient = null;

		try {
			httpClient = new DefaultHttpClient();
			HttpPost postRequest = new HttpPost("http://localhost:8080/WebTest/rest/v1/invoice/save");
			JAXBContext context = JAXBContext.newInstance("com.jimandlisa.api.v1");
			Marshaller marshaller = context.createMarshaller();
			StringWriter writer = new StringWriter();
			marshaller.marshal(invoice, writer);
			StringEntity xml = new StringEntity(writer.toString());
			System.out.println(writer.toString());
			xml.setContentType("application/xml");
			postRequest.setEntity(xml);

			HttpResponse response = httpClient.execute(postRequest);
			String result = getBody(response.getEntity().getContent());
			System.out.println(result);

//			if (response.getStatusLine().getStatusCode() != 201) {
//				System.out.println("HTTP error code: " + response.getStatusLine().getStatusCode());
//			} else {
//				BufferedReader br = new BufferedReader(new InputStreamReader((response.getEntity().getContent())));
//				String output;
//				System.out.println("Output from Server .... \n");
//
//				while ((output = br.readLine()) != null) {
//					System.out.println(output);
//				}
//			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (httpClient != null) {
				httpClient.getConnectionManager().shutdown();
			}
		}
	}
	
	@Test
	public void saveInvoiceJSON() throws Exception {
		
		// Java to GSon to JSON:
		Invoice invoice = new Invoice();
		invoice.setName("Web Invoice");
		InvoiceLineItem lineItem1 = new InvoiceLineItem();
		lineItem1.setBalance(new BigDecimal("\u002D20.99"));
		lineItem1.setName("Web item 1");
		invoice.getLineItems().add(lineItem1);
		InvoiceLineItem lineItem2 = new InvoiceLineItem();
		lineItem2.setBalance(new BigDecimal("\u002D12.27"));
		lineItem2.setName("Web item 2");
		invoice.getLineItems().add(lineItem2);
		Gson gson = new Gson();
		String jsonGsonInvoice = gson.toJson(invoice);
		System.out.println("GSON: " + jsonGsonInvoice);
		StringEntity gsonEntity = new StringEntity(jsonGsonInvoice);
		gsonEntity.setContentType("application/json");
		
		// JSONObject to JSON:
		JSONObject jsonObjInvoice = new JSONObject();
		jsonObjInvoice.put("invoiceID", "0");
		jsonObjInvoice.put("name", "Web Invoice");
		JSONObject jsonObjLineItem1 = new JSONObject();
		jsonObjLineItem1.put("lineItemID", "0");
		jsonObjLineItem1.put("name", "Web items");
		jsonObjLineItem1.put("balance", "-20.99");
		JSONObject[] jsonObjLineItems = new JSONObject[] { jsonObjLineItem1 };
		jsonObjInvoice.put("lineItems", jsonObjLineItems);
		StringEntity jsonObjEntity = new StringEntity(jsonObjInvoice.toString());
		jsonObjEntity.setContentType("application/json");
		System.out.println("JSONObject: " + jsonObjInvoice.toString());
		
		HttpClient httpClient = null;

		try {

			httpClient = new DefaultHttpClient();
			HttpPost postRequest = new HttpPost("http://localhost:8080/WebTest/rest/v1/invoice/save/");
			//postRequest.setHeader("content-type", "application/json");
			postRequest.setEntity(gsonEntity);
			///postRequest.setEntity(jsonObjEntity);

			HttpResponse response = httpClient.execute(postRequest);
			String result = getBody(response.getEntity().getContent());
			System.out.println(result);

//			if (response.getStatusLine().getStatusCode() != 201) {
//				System.out.println("HTTP error code: " + response.getStatusLine().getStatusCode());
//			} else {
//				BufferedReader br = new BufferedReader(new InputStreamReader((response.getEntity().getContent())));
//				String output;
//				System.out.println("Output from Server .... \n");
//
//				while ((output = br.readLine()) != null) {
//					System.out.println(output);
//				}
//			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (httpClient != null) {
				httpClient.getConnectionManager().shutdown();
			}
		}
	}
	
	private String getBody(InputStream inputStream) {
	    String result = "";
	    try {
	        BufferedReader in = new BufferedReader(
	            new InputStreamReader(inputStream)
	        );
	        String inputLine;
	        while ( (inputLine = in.readLine() ) != null ) {
	          result += inputLine;
	          result += "\n";
	        }
	        in.close();
	    } catch (IOException ioe) {
	      ioe.printStackTrace();
	    }
	    return result;
	  }
}
