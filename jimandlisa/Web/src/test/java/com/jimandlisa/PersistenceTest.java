/**
 * 
 */
package com.jimandlisa;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.junit.Test;

/**
 * @author Lisa
 * 
 */
public final class PersistenceTest {

	@Test
	public void saveInvoice() {
		String invoiceName = "Merlot Invoice";
		String alternateName = "Invoice for Merlot";
		String lineItemName = "Merlot, 750ml";
		BigDecimal lineItemBalance = new BigDecimal("\u002D20.99");

		Invoice invoice = new Invoice();
		invoice.setName(invoiceName);
		InvoiceLineItem lineItem = new InvoiceLineItem();
		lineItem.setName(lineItemName);
		lineItem.setBalance(lineItemBalance);
		invoice.addLineItem(lineItem);

		InvoiceOperations operations = null;
		try {
			operations = new InvoiceOperations();
		} catch (ServiceException e) {
			e.printStackTrace();
			assertNotNull(operations);
		}

		int invoiceId = -1;

		try {
			invoice = operations.saveInvoice(invoice);
			assertEquals(invoiceName, invoice.getName());
			assertEquals(1, invoice.getLineItems().size());

			for (InvoiceLineItem savedItem : invoice.getLineItems()) {
				assertEquals(lineItemName, savedItem.getName());
				lineItem = savedItem;
			}

			invoiceId = invoice.getInvoiceID();
			assertEquals(invoiceId, lineItem.getInvoice().getInvoiceID());
		} catch (ServiceException e) {
			e.printStackTrace();
		} catch (ValidationException e) {
			e.printStackTrace();
		}

		assertTrue(invoiceId != -1);
		int oldId = invoiceId;
		invoice.setName(alternateName);
		Invoice foundInvoice = null;
		try {
			invoice = operations.saveInvoice(invoice);
			invoiceId = invoice.getInvoiceID();
			assertEquals(oldId, invoiceId);
			foundInvoice = operations.findInvoice(invoiceId);
		} catch (ServiceException e) {
			e.printStackTrace();
		} catch (ValidationException e) {
			e.printStackTrace();
			assertNotNull(foundInvoice);
		}
		assertNotNull(foundInvoice);
		assertEquals(invoiceId, foundInvoice.getInvoiceID());
	}

	@Test
	public void saveInvoiceLineItem() {
		// This is a bad test because it depends on a certain state in the
		// database. But this isn't a production project so I'll worry about
		// this later.
		int lineItemId = 27;
		String lineItemName = "";
		InvoiceOperations operations = null;
		InvoiceLineItem item = null;
		Set<InvoiceLineItem> items = null;
		try {
			operations = new InvoiceOperations();
			item = operations.findInvoiceLineItem(lineItemId);
			String itemName = item.getName();
			lineItemName = itemName.equalsIgnoreCase("Cab") ? "Zin" : "Cab";
			item.setName(lineItemName);
			item = operations.saveInvoiceLineItem(item);
			Invoice invoice = item.getInvoice();
			items = invoice.getLineItems();
			assertFalse(items.isEmpty());
		} catch (ServiceException e) {

			e.printStackTrace();
			assertNotNull(operations);
		} catch (ValidationException e) {
			e.printStackTrace();
		}

		InvoiceLineItem[] lineItems = new InvoiceLineItem[items.size()];
		items.toArray(lineItems);
		assertEquals(1, lineItems.length);
		assertEquals(item.getName(), lineItems[0].getName());
	}

	@Test
	public void findInvoice() {
		// This is a bad test because it depends on a certain state in the
		// database. But this isn't a production project so I'll worry about
		// this later.
		int expectedValue = 27;
		InvoiceOperations operations = null;
		Invoice invoice = null;
		try {
			operations = new InvoiceOperations();
			invoice = operations.findInvoice(expectedValue);
		} catch (ServiceException e) {
			e.printStackTrace();
			assertNotNull(operations);
		} catch (ValidationException e) {
			e.printStackTrace();
			assertNotNull(invoice);
		}
		assertNotNull(invoice);
		assertEquals(expectedValue, invoice.getInvoiceID());
	}

	@Test
	public void findInvoiceLineItem() {
		// This is a bad test because it depends on a certain state in the
		// database. But this isn't a production project so I'll worry about
		// this later.
		int expectedValue = 27;
		InvoiceOperations operations = null;
		try {
			operations = new InvoiceOperations();
			InvoiceLineItem lineItem = operations.findInvoiceLineItem(expectedValue);
			assertNotNull(lineItem);
			assertEquals(expectedValue, lineItem.getLineItemID());
		} catch (ServiceException e) {
			e.printStackTrace();
			assertNotNull(operations);
		} catch (ValidationException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void deleteInvoice() {
		String invoiceName = "Zin Invoice";
		String lineItemName = "Zinfandel, 750ml";
		BigDecimal lineItemBalance = new BigDecimal("\u002D20.99");

		Invoice invoice = new Invoice();
		invoice.setName(invoiceName);
		InvoiceLineItem lineItem = new InvoiceLineItem();
		lineItem.setName(lineItemName);
		lineItem.setBalance(lineItemBalance);
		invoice.addLineItem(lineItem);

		InvoiceOperations operations = null;
		Invoice foundInvoice = null;
		int invoiceId = -1;
		try {
			operations = new InvoiceOperations();
			invoice = operations.saveInvoice(invoice);
			invoiceId = invoice.getInvoiceID();
			foundInvoice = operations.findInvoice(invoiceId);
		} catch (ServiceException e) {
			e.printStackTrace();
			assertNotNull(operations);
		} catch (ValidationException e) {
			e.printStackTrace();
			assertNotNull(foundInvoice);
		}
		assertNotNull(foundInvoice);

		try {
			boolean isDeleted = operations.deleteInvoice(invoiceId);
			assertTrue(isDeleted);
			foundInvoice = operations.findInvoice(invoiceId);
		} catch (ServiceException e) {
			e.printStackTrace();
		} catch (ValidationException e) {
			e.printStackTrace();
			assertNotNull(foundInvoice);
		}
		assertNull(foundInvoice);
	}

	@Test
	public void deleteInvoiceLineItem() {
		String invoiceName = "Shiraz Invoice";
		String lineItemName = "Shiraz, 750ml";
		String secondName = "Pinot, 750ml";
		BigDecimal lineItemBalance = new BigDecimal("\u002D20.99");

		Invoice invoice = new Invoice();
		invoice.setName(invoiceName);
		InvoiceLineItem lineItem = new InvoiceLineItem();
		lineItem.setName(lineItemName);
		lineItem.setBalance(lineItemBalance);
		invoice.addLineItem(lineItem);
		InvoiceLineItem secondItem = new InvoiceLineItem();
		secondItem.setName(secondName);
		secondItem.setBalance(new BigDecimal("\u002D21.50"));
		invoice.addLineItem(secondItem);

		InvoiceOperations operations = null;
		Invoice foundInvoice = null;
		int invoiceId = -1;
		try {
			operations = new InvoiceOperations();
			invoice = operations.saveInvoice(invoice);
			invoiceId = invoice.getInvoiceID();
			foundInvoice = operations.findInvoice(invoiceId);
		} catch (ServiceException e) {
			e.printStackTrace();
			assertNotNull(operations);
		} catch (ValidationException e) {
			e.printStackTrace();
			assertNotNull(foundInvoice);
		}
		assertNotNull(foundInvoice);
		assertEquals(2, foundInvoice.getLineItems().size());

		InvoiceLineItem itemToDelete = null;
		Iterator<InvoiceLineItem> foop = foundInvoice.getLineItems().iterator();

		while (foop.hasNext()) {
			InvoiceLineItem nextItem = foop.next();

			if (nextItem.getName().equals(secondName)) {
				itemToDelete = nextItem;
			}
		}

		assertNotNull(itemToDelete);
		int itemId = itemToDelete.getLineItemID();
		InvoiceLineItem foundItem = null;
		try {
			boolean isDeleted = operations.deleteInvoiceLineItem(itemId);
			assertTrue(isDeleted);
			foundItem = operations.findInvoiceLineItem(itemId);
		} catch (ServiceException e) {
			e.printStackTrace();
			assertNotNull(operations);
		} catch (ValidationException e) {
			e.printStackTrace();
		}
		assertNull(foundItem);
		// clean up
		try {
			operations.deleteInvoice(invoiceId);
		} catch (ServiceException e) {
			e.printStackTrace();
			assertNotNull(operations);
		} catch (ValidationException e) {
			e.printStackTrace();
		}
	}

	// -------------------------------------

	@Test
	public void findInvoiceDep() {
		int expectedValue = 2;
		Invoice invoice = InvoiceOperations.findInvoiceDep(expectedValue);
		assertNotNull(invoice);
		assertEquals(expectedValue, invoice.getInvoiceID());
	}

	@Test
	public void findByCriteriaQuery() {
		List<Invoice> invoiceList = InvoiceOperations.findByCriteriaQuery(2);
		assertNotNull(invoiceList);
		assertEquals(1, invoiceList.size());
	}

	@Test
	public void findByJpql() {
		int expectedValue = 2;
		Invoice invoice = InvoiceOperations.findByJpql(expectedValue);
		assertNotNull(invoice);
		assertEquals(expectedValue, invoice.getInvoiceID());
	}

	@Test
	public void findByLikeName() {
		String likeValue = "%Bill%";
		List<Invoice> invoiceList = InvoiceOperations.findByLikeName(likeValue);
		assertNotNull(invoiceList);
		assertEquals(2, invoiceList.size());
	}

	@Test
	public void findByNativeQuery() {
		List<Invoice> invoiceList = InvoiceOperations.findByNativeQuery(2);
		assertNotNull(invoiceList);
		assertEquals(1, invoiceList.size());
	}

	@Test
	public void findByStoredProcedure() {
		Invoice invoice = InvoiceOperations.findByStoredProcedure(2);
		assertNotNull(invoice);
		assertEquals(2, invoice.getInvoiceID());
	}

	//
	// @Test
	// public void findInvoices() {
	// EntityManagerFactory entityFactory = Persistence
	// .createEntityManagerFactory("persistInvoice");
	// EntityManager manager = entityFactory.createEntityManager();
	// int expectedValue = 2;
	// InvoiceOperations service = null;
	// try {
	// service = new InvoiceOperations();
	// List<Invoice> invoiceList = service.findInvoices(expectedValue);
	// assertNotNull(invoiceList);
	// assertEquals(1, invoiceList.size());
	// } catch (ServiceException e) {
	// e.printStackTrace();
	// assertNotNull(service);
	// } catch (ValidationException e) {
	// e.printStackTrace();
	// }
	//
	// manager.close();
	// entityFactory.close();
	// }

	// @Test
	// public void testCreate() {
	// System.out.println(OneToMany.class.getProtectionDomain().getCodeSource().getLocation());
	// EntityManagerFactory entityFactory =
	// Persistence.createEntityManagerFactory("persistInvoice");
	// EntityManager manager = entityFactory.createEntityManager();
	//
	// Invoice invoice = new Invoice();
	// assertNotNull(invoice);
	// invoice.setName("Invoice For Bob");
	// InvoiceLineItem lineItem = new InvoiceLineItem();
	// assertNotNull(lineItem);
	// lineItem.setName("Cheesehead Bolts");
	// lineItem.setBalance(new BigDecimal("\u002D10.50"));
	// invoice.addLineItem(lineItem);
	// Set<InvoiceLineItem> items = invoice.getLineItems();
	// assertNotNull(items);
	// assertEquals(1, items.size());
	//
	// EntityTransaction transaction = manager.getTransaction();
	// transaction.begin();
	// manager.persist(invoice);
	// transaction.commit();
	//
	// manager.close();
	// entityFactory.close();
	// }
	//
	@Test
	public void testRetrieve() {
		EntityManagerFactory entityFactory = Persistence.createEntityManagerFactory("persistInvoice");
		EntityManager manager = entityFactory.createEntityManager();
		Invoice invoice = manager.find(Invoice.class, 2);
		assertNotNull(invoice);
		Set<InvoiceLineItem> items = invoice.getLineItems();
		assertNotNull(items);
		assertEquals(1, items.size());

		manager.close();
		entityFactory.close();
	}

	@Test
	public void testCriteriaQuery() {
		String arg1 = "%Bill%";
		String arg2 = "name";
		EntityManagerFactory entityFactory = Persistence.createEntityManagerFactory("persistInvoice");
		EntityManager manager = entityFactory.createEntityManager();

		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<Invoice> criteria = builder.createQuery(Invoice.class);
		Root<Invoice> from = criteria.from(Invoice.class);
		CriteriaQuery<Invoice> select = criteria.select(from);

		Expression<String> literal = builder.literal(arg1);
		Expression<String> field = from.get(arg2);
		Predicate predicate = builder.like(field, literal);
		criteria.where(predicate);

		TypedQuery<Invoice> typedQuery = manager.createQuery(select);
		List<Invoice> resultList = typedQuery.getResultList();
		assertNotNull(resultList);
		assertEquals(2, resultList.size());
	}

	// @Test
	// public void findInvoice() {
	// int expectedValue = 2;
	// Invoice invoice = Invoice.findInvoice(expectedValue);
	// assertNotNull(invoice);
	// assertEquals(expectedValue, invoice.getInvoiceID());
	// }
	//
	// @Test
	// public void findByCriteriaQuery() {
	// List<Invoice> invoiceList = Invoice.findByCriteriaQuery(2);
	// assertNotNull(invoiceList);
	// assertEquals(1, invoiceList.size());
	// }
	//
	// @Test
	// public void findByJpql() {
	// int expectedValue = 2;
	// Invoice invoice = Invoice.findByJpql(expectedValue);
	// assertNotNull(invoice);
	// assertEquals(expectedValue, invoice.getInvoiceID());
	// }
	//
	// @Test
	// public void findByLikeName() {
	// String likeValue = "%Bill%";
	// List<Invoice> invoiceList = Invoice.findByLikeName(likeValue);
	// assertNotNull(invoiceList);
	// assertEquals(2, invoiceList.size());
	// }
	//
	// @Test
	// public void findByNativeQuery() {
	// List<Invoice> invoiceList = Invoice.findByNativeQuery(2);
	// assertNotNull(invoiceList);
	// assertEquals(1, invoiceList.size());
	// }
	//
	// @Test
	// public void findByStoredProcedure() {
	// Invoice invoice = Invoice.findByStoredProcedure(2);
	// assertNotNull(invoice);
	// assertEquals(2, invoice.getInvoiceID());
	// }
	//
	// @Test
	// public void findInvoices() {
	// EntityManagerFactory entityFactory =
	// Persistence.createEntityManagerFactory("persistInvoice");
	// EntityManager manager = entityFactory.createEntityManager();
	// int expectedValue = 2;
	// InvoiceService service = new InvoiceService(manager);
	// List<Invoice> invoiceList = service.findInvoices(expectedValue);
	// assertNotNull(invoiceList);
	// assertEquals(1, invoiceList.size());
	// manager.close();
	// entityFactory.close();
	// }
	//
	// @Test
	// public void testCreate() {
	// System.out.println(OneToMany.class.getProtectionDomain().getCodeSource().getLocation());
	// EntityManagerFactory entityFactory =
	// Persistence.createEntityManagerFactory("persistInvoice");
	// EntityManager manager = entityFactory.createEntityManager();
	//
	// Invoice invoice = new Invoice();
	// assertNotNull(invoice);
	// invoice.setName("Invoice For Bob");
	// LineItem lineItem = new LineItem();
	// assertNotNull(lineItem);
	// lineItem.setName("Cheesehead Bolts");
	// lineItem.setBalance(new BigDecimal("\u002D10.50"));
	// invoice.addLineItem(lineItem);
	// Set<LineItem> items = invoice.getLineItems();
	// assertNotNull(items);
	// assertEquals(1, items.size());
	//
	// EntityTransaction transaction = manager.getTransaction();
	// transaction.begin();
	// manager.persist(invoice);
	// transaction.commit();
	//
	// manager.close();
	// entityFactory.close();
	// }
	//
	// @Test
	// public void testRetrieve() {
	// EntityManagerFactory entityFactory =
	// Persistence.createEntityManagerFactory("persistInvoice");
	// EntityManager manager = entityFactory.createEntityManager();
	// Invoice invoice = manager.find(Invoice.class, 2);
	// assertNotNull(invoice);
	// Set<LineItem> items = invoice.getLineItems();
	// assertNotNull(items);
	// assertEquals(1, items.size());
	//
	// manager.close();
	// entityFactory.close();
	// }
	//
	// @Test
	// public void testCriteriaQuery()
	// {
	// String arg1 = "%Bill%";
	// String arg2 = "name";
	// EntityManagerFactory entityFactory =
	// Persistence.createEntityManagerFactory("persistInvoice");
	// EntityManager manager = entityFactory.createEntityManager();
	//
	// CriteriaBuilder builder = manager.getCriteriaBuilder();
	// CriteriaQuery<Invoice> criteria = builder.createQuery(Invoice.class);
	// Root<Invoice> from = criteria.from(Invoice.class);
	// CriteriaQuery<Invoice> select = criteria.select(from);
	//
	// Expression<String> literal = builder.literal(arg1);
	// Expression<String> field = from.get(arg2);
	// Predicate predicate = builder.like(field, literal);
	// criteria.where(predicate);
	//
	// TypedQuery<Invoice> typedQuery = manager.createQuery(select);
	// List<Invoice> resultList = typedQuery.getResultList();
	// assertNotNull(resultList);
	// assertEquals(2, resultList.size());
	// }
}
