package com.jimandlisa.api.v1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.util.HashMap;

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
import com.jimandlisa.api.v1.Invoice;
import com.jimandlisa.api.v1.InvoiceLineItem;

public class TestUtils {

	public static enum Mode {
		NONE, JSON, XML;
	}

	/***
	 * Creates API invoice with line items for specified name/value pairs.
	 * 
	 * @param invoiceName
	 * @param lineItemNameValuePairs
	 * @return
	 */
	public static Invoice createTestInvoice(String invoiceName, HashMap<String, BigDecimal> lineItemNameValuePairs) {
		Invoice invoice = new Invoice();
		invoice.setName(invoiceName);

		for (String name : lineItemNameValuePairs.keySet()) {
			InvoiceLineItem lineItem = new InvoiceLineItem();
			lineItem.setName(name);
			lineItem.setBalance(lineItemNameValuePairs.get(name));
			invoice.getLineItems().add(lineItem);
		}

		return invoice;
	}

	/***
	 * Generates specified number of line item name/value pairs
	 * 
	 * @param numberOfLineItems
	 * @return
	 */
	public static HashMap<String, BigDecimal> generateLineItemNameValuePairs(int numberOfLineItems) {
		HashMap<String, BigDecimal> lineItemNameValuePairs = new HashMap<String, BigDecimal>(numberOfLineItems);

		for (int index = 0; index < numberOfLineItems; index++) {
			String itemName = String.format("Line Item %d", index);
			try {
				String value = String.format("\u002D%d.%d", index+1, index+2);
				BigDecimal itemValue = new BigDecimal(value);
				lineItemNameValuePairs.put(itemName, itemValue);
			} catch (Exception x) {
				System.out.println(x.getMessage());
			}
		}

		return lineItemNameValuePairs;
	}

	/***
	 * Generates API invoice with specified number of line items.
	 * 
	 * @param numberOfLineItems
	 * @return
	 */
	public static Invoice generateTestInvoice(int numberOfLineItems) {
		String invoiceName = "General Invoice";
		HashMap<String, BigDecimal> lineItemNameValuePairs = generateLineItemNameValuePairs(numberOfLineItems);
		return createTestInvoice(invoiceName, lineItemNameValuePairs);
	}

	/***
	 * Gets text body from stream.
	 * 
	 * @param inputStream
	 * @return
	 */
	public static String getBody(InputStream inputStream) {
		String result = "";

		try {
			BufferedReader in = new BufferedReader(new InputStreamReader(inputStream));
			String inputLine;

			while ((inputLine = in.readLine()) != null) {
				result += inputLine;
				result += "\n";
			}

			in.close();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}

		return result;
	}

	private static void saveInvoice(Mode accept) {
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
			postRequest.setHeader("accept", "application/" + accept.toString().toLowerCase());
			HttpResponse response = httpClient.execute(postRequest);
			String result = getBody(response.getEntity().getContent());
			System.out.println(result);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (httpClient != null) {
				httpClient.getConnectionManager().shutdown();
			}
		}
	}

	@Test
	public void saveInvoice() {
		saveInvoice(Mode.XML);
		saveInvoice(Mode.JSON);
	}

	private static StringEntity getGsonInvoice() throws Exception {
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
		String gson = new Gson().toJson(invoice);
		StringEntity entity = new StringEntity(gson);
		entity.setContentType("application/json");
		System.out.println("Java Invoice to GSON to Entity: " + gson);
		return entity;
	}

	private static StringEntity getJSONObjectInvoice() throws Exception {
		JSONObject invoice = new JSONObject();
		invoice.put("name", "Web Invoice");
		JSONObject lineItem1 = new JSONObject();
		lineItem1.put("name", "Web item 1");
		lineItem1.put("balance", "224.18");
		JSONObject lineItem2 = new JSONObject();
		lineItem2.put("name", "Web item 2");
		lineItem2.put("balance", "-76.01");
		JSONObject[] invoiceLineItems = new JSONObject[] { lineItem1, lineItem2 };
		invoice.put("lineItems", invoiceLineItems);
		StringEntity entity = new StringEntity(invoice.toString());
		entity.setContentType("application/json");
		System.out.println("JSONObject Invoice to Entity: " + invoice.toString());
		return entity;
	}

	public void saveInvoice(StringEntity entity, Mode accept) throws Exception {

		HttpClient httpClient = null;

		try {
			httpClient = new DefaultHttpClient();
			HttpPost postRequest = new HttpPost("http://localhost:8080/WebTest/rest/v1/invoice/save/");
			postRequest.setHeader("content-type", "application/json");
			postRequest.setEntity(entity);
			postRequest.setHeader("accept", "application/" + accept.toString().toLowerCase());
			HttpResponse response = httpClient.execute(postRequest);
			String result = getBody(response.getEntity().getContent());
			System.out.println(result);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (httpClient != null) {
				httpClient.getConnectionManager().shutdown();
			}
		}
	}

	@Test
	public void saveInvoiceJSON() throws Exception {
		saveInvoice(getGsonInvoice(), Mode.XML);
		saveInvoice(getGsonInvoice(), Mode.JSON);
		saveInvoice(getJSONObjectInvoice(), Mode.XML);
		saveInvoice(getJSONObjectInvoice(), Mode.JSON);
	}

	@Test
	public void readInvoice() {

		HttpClient httpClient = new DefaultHttpClient();
		HttpGet getRequest = new HttpGet("http://localhost:8080/WebTest/rest/v1/invoice/382");

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
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (httpClient != null) {
				httpClient.getConnectionManager().shutdown();
			}
		}
	}

}
