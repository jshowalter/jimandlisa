package com.jimandlisa;

import java.util.ResourceBundle;

public class InvoiceMapper {

	public int integerFrom(String idString) throws ValidationException,
			ServiceException {
		if (idString == null) {
			ResourceBundle bundle = ResourceBundle.getBundle("ExceptionMessage");
			throw new ValidationException(bundle.getString("NullInvoiceID"));
		}

		try {
			int id = Integer.parseInt(idString);
			return id;
		} catch (NumberFormatException nfx) {
			throw new ValidationException(nfx);
		} catch (Throwable t) {
			throw new ServiceException(t);
		}
	}

	public InvoiceFilter invoiceFilterFrom(
			com.jimandlisa.api.v1.InvoiceFilter generatedFilter)
			throws ValidationException {
		if (generatedFilter == null) {
			throw new ValidationException("generated filter is null");
		}

		InvoiceFilter filter = new InvoiceFilter();
		filter.setFilterClause(generatedFilter.getFilterClause());
		return filter;
	}

	public Invoice invoiceFrom(com.jimandlisa.api.v1.Invoice generatedInvoice)
			throws ValidationException {
		if (generatedInvoice == null) {
			throw new ValidationException("generated invoice is null");
		}

		Invoice invoice = new Invoice();
		invoice.setInvoiceID(generatedInvoice.getInvoiceID());
		invoice.setName(generatedInvoice.getName());

		// Copy line items.
		for (com.jimandlisa.api.v1.InvoiceLineItem generatedItem : generatedInvoice
				.getLineItems()) {
			InvoiceLineItem lineItem = invoiceLineItemFrom(generatedItem);

			if (lineItem != null) {
				invoice.addLineItem(lineItem);
			}
		}

		return invoice;
	}

	public com.jimandlisa.api.v1.Invoice generatedInvoiceFrom(Invoice invoice) {
		if (invoice == null) {
			// ok to return null? CHANGE TO SERVICE EXCEPTION
			return null;
		}

		com.jimandlisa.api.v1.Invoice generatedInvoice = new com.jimandlisa.api.v1.Invoice();
		generatedInvoice.setInvoiceID(invoice.getInvoiceID());
		generatedInvoice.setName(invoice.getName());

		for (InvoiceLineItem item : invoice.getLineItems()) {
			com.jimandlisa.api.v1.InvoiceLineItem generatedItem = generatedInvoiceLineItemFrom(item);

			if (generatedItem != null) {
				generatedItem.setInvoiceID(invoice.getInvoiceID());
				generatedInvoice.getLineItems().add(generatedItem);
			}
		}

		return generatedInvoice;
	}

	public InvoiceLineItem invoiceLineItemFrom(
			com.jimandlisa.api.v1.InvoiceLineItem generatedLineItem)
			throws ValidationException {
		if (generatedLineItem == null) {
			throw new ValidationException("generated line item is null");
		}

		InvoiceLineItem lineItem = new InvoiceLineItem();
		lineItem.setLineItemID(generatedLineItem.getLineItemID());
		lineItem.setName(generatedLineItem.getName());
		lineItem.setBalance(generatedLineItem.getBalance());
		return lineItem;
	}

	public com.jimandlisa.api.v1.InvoiceLineItem generatedInvoiceLineItemFrom(
			InvoiceLineItem lineItem) {
		if (lineItem == null) {
			// ok to return null?
			return null;
		}

		com.jimandlisa.api.v1.InvoiceLineItem generatedLineItem = new com.jimandlisa.api.v1.InvoiceLineItem();
		generatedLineItem.setLineItemID(lineItem.getLineItemID());
		generatedLineItem.setName(lineItem.getName());
		generatedLineItem.setBalance(lineItem.getBalance());
		return generatedLineItem;
	}
}
