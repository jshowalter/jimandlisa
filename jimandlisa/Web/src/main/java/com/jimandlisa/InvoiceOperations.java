package com.jimandlisa;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.RollbackException;
import javax.persistence.TransactionRequiredException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public class InvoiceOperations {
	protected EntityManager manager;
	
	/*public class Singleton {
	 * 
	 * 			EntityManagerFactory entityFactory = Persistence
					.createEntityManagerFactory("persistInvoice");
					
        // Private constructor prevents instantiation from other classes
        private Singleton() { }

        private static class SingletonHolder { 
                public static final Singleton INSTANCE = new Singleton();
        }
 
        public static Singleton getInstance() {
                return SingletonHolder.INSTANCE;
        }
}*/

	/**
	 * Initializes new instance of InvoiceOperations class.
	 * 
	 * @throws ServiceException
	 */
	public InvoiceOperations() throws ServiceException {
		try {
			EntityManagerFactory entityFactory = Persistence
					.createEntityManagerFactory("persistInvoice");
			manager = entityFactory.createEntityManager();
		} catch (IllegalStateException x) {
			throw new ServiceException("Unable to create manager", x);
		}
	}

	/*
	 * Persistence methods ----------------------------------------------------
	 */

	/**
	 * Deletes invoice with specified invoice ID.
	 * 
	 * @param invoiceId
	 *            ID for invoice to delete.
	 * @return true if invoice was found and deleted; false if not.
	 * @throws ServiceException
	 * @throws ValidationException
	 */
	public boolean deleteInvoice(int invoiceId) throws ValidationException,
			ServiceException {
		Invoice invoice = findInvoice(invoiceId);
		boolean wasDeleted = false;

		if (invoice != null) {
			try {
				EntityTransaction transaction = manager.getTransaction();
				transaction.begin();
				manager.remove(invoice);
				transaction.commit();
				wasDeleted = true;
			} catch (IllegalStateException x) {
				throw new ServiceException("Unable to delete invoice", x);
			} catch (IllegalArgumentException x) {
				throw new ServiceException(
						"Illegal argument when deleting invoice", x);
			} catch (TransactionRequiredException x) {
				throw new ServiceException(
						"No transaction when deleting invoice", x);
			} catch (RollbackException x) {
				throw new ServiceException(
						"Unable to rollback invoice delete transaction", x);
			}
		}

		return wasDeleted;
	}

	/**
	 * 
	 * @param lineItemId
	 * @return
	 * @throws ValidationException
	 * @throws ServiceException
	 */
	public boolean deleteInvoiceLineItem(int lineItemId)
			throws ValidationException, ServiceException {
		InvoiceLineItem lineItem = findInvoiceLineItem(lineItemId);
		boolean wasDeleted = false;

		if (lineItem != null) {
			try {
				EntityTransaction transaction = manager.getTransaction();
				transaction.begin();
				manager.remove(lineItem);
				transaction.commit();
				wasDeleted = true;
			} catch (IllegalStateException x) {
				throw new ServiceException(
						"Unable to delete invoice line item", x);
			} catch (IllegalArgumentException x) {
				throw new ServiceException(
						"Illegal argument when deleting invoice line item", x);
			} catch (TransactionRequiredException x) {
				throw new ServiceException(
						"No transaction when deleting invoice line item", x);
			} catch (RollbackException x) {
				throw new ServiceException(
						"Unable to rollback invoice line item delete transaction",
						x);
			}
		}

		return wasDeleted;
	}

	/**
	 * Finds invoice belonging to specified ID.
	 * 
	 * @param invoiceId
	 *            ID of invoice to find.
	 * @return Found invoice or null if invoice not found.
	 * @throws ValidationException
	 */
	public Invoice findInvoice(int invoiceId) throws ValidationException {
		try {
			return manager.find(Invoice.class, invoiceId);
		} catch (IllegalArgumentException x) {
			throw new ValidationException("Unable to find invoice", x);
		}
	}

	/**
	 * 
	 * @param lineItemId
	 * @return
	 * @throws ValidationException
	 */
	public InvoiceLineItem findInvoiceLineItem(int lineItemId)
			throws ValidationException {
		try {
			return manager.find(InvoiceLineItem.class, lineItemId);
		} catch (IllegalArgumentException x) {
			throw new ValidationException("unable to find invoice", x);
		}
	}

	/**
	 * 
	 * @param invoice
	 * @return
	 * @throws ServiceException
	 * @throws ValidationException
	 */
	public Invoice saveInvoice(Invoice invoice) throws ServiceException,
			ValidationException {
		try {
			EntityTransaction transaction = manager.getTransaction();
			transaction.begin();
			// is merge the correct choice?
			// http://docs.jboss.org/hibernate/stable/entitymanager/reference/en/html/objectstate.html#d0e1318
			Invoice mergedInvoice = manager.merge(invoice);
			// manager.persist(invoice);
			transaction.commit();
			return mergedInvoice;
		} catch (IllegalStateException x) {
			throw new ServiceException("Unable to save invoice", x);
		} catch (IllegalArgumentException x) {
			throw new ValidationException(
					"Illegal argument when saving invoice", x);
		} catch (TransactionRequiredException x) {
			throw new ServiceException("No transaction when saving invoice", x);
		} catch (RollbackException x) {
			throw new ServiceException(
					"Unable to rollback invoice save transaction", x);
		}
	}

	/**
	 * 
	 * @param lineItem
	 * @return
	 * @throws ServiceException
	 * @throws ValidationException
	 */
	public InvoiceLineItem saveInvoiceLineItem(InvoiceLineItem lineItem)
			throws ServiceException, ValidationException {
		try {
			EntityTransaction transaction = manager.getTransaction();
			transaction.begin();
			InvoiceLineItem mergedLineItem = manager.merge(lineItem);
			// manager.persist(lineItem);
			transaction.commit();
			return mergedLineItem;
		} catch (IllegalStateException x) {
			throw new ServiceException("Unable to save invoice line item", x);
		} catch (IllegalArgumentException x) {
			throw new ValidationException(
					"Illegal argument when saving invoice line item", x);
		} catch (TransactionRequiredException x) {
			throw new ServiceException(
					"No transaction when saving invoice line item", x);
		} catch (RollbackException x) {
			throw new ServiceException(
					"Unable to rollback invoice line item save transaction", x);
		}
	}

//	// Finds a list of invoices by id (which, of course, will result in a list
//	// that contains 1 or 0 invoices).
//	/**
//	 * 
//	 * @param invoiceId
//	 * @return
//	 * @throws ValidationException
//	 * @throws ServiceException
//	 */
//	public List<Invoice> findInvoices(int invoiceId)
//			throws ValidationException, ServiceException {
//		try {
//			List<Invoice> invoices = manager
//					.createNamedQuery("nativeSQL", Invoice.class)
//					.setParameter(1, invoiceId).getResultList();
//			return invoices;
//		} catch (IllegalArgumentException x) {
//			throw new ValidationException(
//					"Illegal argument when finding invoices", x);
//		} catch (IllegalStateException x) {
//			throw new ServiceException("Unable to find invoices", x);
//		} catch (TransactionRequiredException x) {
//			throw new ServiceException("No transaction when finding invoices",
//					x);
//		} catch (QueryTimeoutException x) {
//			throw new ServiceException("Query timed out when finding invoices",
//					x);
//		} catch (PessimisticLockException x) {
//			throw new ServiceException("Lock exception when finding invoices",
//					x);
//		} catch (LockTimeoutException x) {
//			throw new ServiceException("Lock timed out when finding invoices",
//					x);
//		} catch (PersistenceException x) {
//			throw new ServiceException("Persistence error when finding invoices",
//					x);
//		}
//	}

	// Finds specified invoice by using CriteriaQuery.
	public static List<Invoice> findByCriteriaQuery(int id) {
		EntityManagerFactory entityFactory = Persistence
				.createEntityManagerFactory("persistInvoice");
		EntityManager manager = entityFactory.createEntityManager();

		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<Invoice> criteria = builder.createQuery(Invoice.class);
		Root<Invoice> from = criteria.from(Invoice.class);
		CriteriaQuery<Invoice> select = criteria.select(from);

		Expression<String> field = from.get("invoiceID");
		Predicate predicate = builder.equal(field, id);
		criteria.where(predicate);

		TypedQuery<Invoice> typedQuery = manager.createQuery(select);
		List<Invoice> resultList = typedQuery.getResultList();

		manager.close();
		entityFactory.close();
		return resultList;
	}

	// Finds specified invoice.
	public static Invoice findInvoiceDep(int id) {
		try {
			EntityManagerFactory entityFactory = Persistence
					.createEntityManagerFactory("persistInvoice");
			EntityManager manager = entityFactory.createEntityManager();
			Invoice invoice = manager.find(Invoice.class, id);
			manager.close();
			entityFactory.close();
			return invoice;
		} catch (Throwable t) {
			System.out.println(t.getMessage());
			t.printStackTrace();
			throw t;
		}
	}

	// Finds specified invoice using JPQL.
	public static Invoice findByJpql(int id) {
		EntityManagerFactory entityFactory = Persistence
				.createEntityManagerFactory("persistInvoice");
		EntityManager manager = entityFactory.createEntityManager();
		Invoice invoice = (Invoice) manager
				.createQuery(
						"SELECT i FROM Invoice i where I.invoiceID=:invoice_ID")
				.setParameter("invoice_ID", id).getSingleResult();
		manager.close();
		entityFactory.close();
		return invoice;
	}

	// Finds invoices by name, using "like" with CriteriaQuery.
	public static List<Invoice> findByLikeName(String likeName) {
		EntityManagerFactory entityFactory = Persistence
				.createEntityManagerFactory("persistInvoice");
		EntityManager manager = entityFactory.createEntityManager();

		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<Invoice> criteria = builder.createQuery(Invoice.class);
		Root<Invoice> from = criteria.from(Invoice.class);
		CriteriaQuery<Invoice> select = criteria.select(from);

		Expression<String> literal = builder.literal(likeName);
		Expression<String> field = from.get("name");
		Predicate predicate = builder.like(field, literal);
		criteria.where(predicate);

		TypedQuery<Invoice> typedQuery = manager.createQuery(select);
		List<Invoice> resultList = typedQuery.getResultList();

		manager.close();
		entityFactory.close();
		return resultList;
	}

	// Finds invoices by using a native query.
	@SuppressWarnings("unchecked")
	public static List<Invoice> findByNativeQuery(int id) {
		EntityManagerFactory entityFactory = Persistence
				.createEntityManagerFactory("persistInvoice");
		EntityManager manager = entityFactory.createEntityManager();
		// This is stupid -- EntityManager provides a method to specify the
		// class of the returned values,
		// but doesn't provide a method to return a TypedQuery<T> so there's a
		// type safety issue.
		Query findQuery = manager
				.createNativeQuery(
						"SELECT i.InvoiceID, i.Name, l.LineItemID, l.Name, l.Balance "
								+ "FROM Invoice i INNER JOIN InvoiceLineItem l "
								+ "WHERE (l.InvoiceID = i.InvoiceID) and (i.InvoiceID = '"
								+ id + "')", "NativeJoin");
		List<Invoice> invoices = (List<Invoice>) findQuery.getResultList();
		manager.close();
		entityFactory.close();
		return invoices;
	}

	// Finds invoice by using a named query to call a stored procedure
	public static Invoice findByStoredProcedure(int id) {
		EntityManagerFactory entityFactory = Persistence
				.createEntityManagerFactory("persistInvoice");
		EntityManager manager = entityFactory.createEntityManager();
		Query findQuery = manager.createNamedQuery("nativeSP").setParameter(
				"targetID", id);
		Invoice invoice = (Invoice) findQuery.getSingleResult();
		manager.close();
		entityFactory.close();
		return invoice;
	}
}
