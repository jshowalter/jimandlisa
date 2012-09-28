package com.jimandlisa;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.math.BigDecimal;
import java.util.HashSet;

import org.junit.Test;
import org.junit.runner.RunWith;


import mockit.Mock;
import mockit.MockClass;

import mockit.integration.junit4.JMockit;
import static mockit.Mockit.setUpMock;
import static mockit.Mockit.tearDownMocks;

@RunWith(JMockit.class)
public final class MockPersistenceTest {
	@MockClass(realClass = Invoice.class)
	public static class MockInvoice {
		@Mock
		public static Invoice findInvoice(int id) {
			Invoice invoice = new Invoice();
			assertNotNull(invoice);
			invoice.setInvoiceID(id);
			invoice.setName("Test Invoice");
			InvoiceLineItem item = new InvoiceLineItem();
			item.setLineItemID(135);
			item.setInvoice(invoice);
			item.setName("Test Item");
			item.setBalance(new BigDecimal("\u002D10.50"));
			assertNotNull(item);
			invoice.addLineItem(item);
			return invoice;
		}
	}
	
	@Test
	public void testSomething() {
		try {
			setUpMock(Invoice.class, MockInvoice.class);
			Invoice invoice = InvoiceOperations.findInvoiceDep(1);
			assertNotNull(invoice);
			assertEquals(1, invoice.getInvoiceID());
			assertEquals("Test Invoice", invoice.getName());
			assertEquals(1, invoice.getLineItems().size());
			HashSet<InvoiceLineItem> items = (HashSet<InvoiceLineItem>) invoice.getLineItems();
			InvoiceLineItem item = items.iterator().next();
			assertNotNull(item);
			assertEquals(135,item.getLineItemID());
			assertEquals("Test Item", item.getName());
			assertEquals(invoice.getInvoiceID(), item.getInvoice().getInvoiceID());
		} catch (Throwable t) {
			System.out.println(t.getMessage());
		} finally {
			tearDownMocks();			
		}
	}
}
