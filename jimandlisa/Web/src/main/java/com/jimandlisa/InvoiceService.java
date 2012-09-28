package com.jimandlisa;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

@Path("/rest/v1")
public class InvoiceService {

	// @Path("/invoice/delete/{id}")
	// Delete (both invoice & line item, implement/test delete methods in ops
	// first)

	@POST
	@Path("/invoice/delete/{invoiceId}")
	@Consumes({"application/xml", "application/json"})
	@Produces({"application/xml", "application/json"})
	public boolean deleteInvoice(@PathParam("invoiceId") String invoiceId)
			throws ValidationException, ServiceException {
		InvoiceMapper mapper = new InvoiceMapper();
		int id = mapper.integerFrom(invoiceId);
		InvoiceOperations operations = new InvoiceOperations();
		return operations.deleteInvoice(id);
	}

	@POST
	@Path("/invoicelineitem/delete/{invoiceLineItemId}")
	@Consumes({"application/xml", "application/json"})
	@Produces({"application/xml", "application/json"})
	public boolean deleteInvoiceLineItem(
			@PathParam("invoiceLineItemId") String invoiceLineItemId)
			throws ValidationException, ServiceException {
		InvoiceMapper mapper = new InvoiceMapper();
		int id = mapper.integerFrom(invoiceLineItemId);
		InvoiceOperations operations = new InvoiceOperations();
		return operations.deleteInvoiceLineItem(id);
	}

	@GET
	@Path("/invoice/{invoiceId}")
	@Produces({"application/xml", "application/json"})
	public com.jimandlisa.api.v1.Invoice findInvoice(
			@PathParam("invoiceId") String invoiceId)
			throws ValidationException, ServiceException {
		InvoiceMapper mapper = new InvoiceMapper();
		int id = mapper.integerFrom(invoiceId);
		InvoiceOperations operations = new InvoiceOperations();
		Invoice invoice = operations.findInvoice(id);
		com.jimandlisa.api.v1.Invoice generatedInvoice = mapper.generatedInvoiceFrom(invoice);
		
		return generatedInvoice;
		//return mapper.generatedInvoiceFrom(invoice);
	}

	@GET
	@Path("/invoicelineitem/{invoiceLineItemId}")
	@Produces({"application/xml", "application/json"})
	public com.jimandlisa.api.v1.InvoiceLineItem findInvoiceLineItem(
			@PathParam("invoiceLineItemId") String invoiceLineItemId)
			throws ValidationException, ServiceException {
		InvoiceMapper mapper = new InvoiceMapper();
		int id = mapper.integerFrom(invoiceLineItemId);
		InvoiceOperations operations = new InvoiceOperations();
		InvoiceLineItem lineItem = operations.findInvoiceLineItem(id);
		return mapper.generatedInvoiceLineItemFrom(lineItem);
	}

	// com.jimandlisa.api.Invoice find

	// @Path("/invoices/find")
	// List<com.jimandlisa.api.v1.Invoice> find(com.jimandlisa.api.InvoiceFilter
	// filter) returns List
	
	@POST
	@Path("/invoice/save")
	@Consumes({"application/xml", "application/json"})
	@Produces({"application/xml", "application/json"})
	public com.jimandlisa.api.v1.Invoice saveInvoice(com.jimandlisa.api.v1.Invoice invoice)
			throws ValidationException, ServiceException {
		InvoiceMapper mapper = new InvoiceMapper();
		Invoice invoiceToSave = mapper.invoiceFrom(invoice);
		InvoiceOperations operations = new InvoiceOperations();
		Invoice savedInvoice = operations.saveInvoice(invoiceToSave);
		return mapper.generatedInvoiceFrom(savedInvoice);
	}

	@POST
	@Path("/invoicelineitem/save")
	@Consumes({"application/xml", "application/json"})
	@Produces({"application/xml", "application/json"})
	public com.jimandlisa.api.v1.InvoiceLineItem saveInvoiceLineItem(
			com.jimandlisa.api.v1.InvoiceLineItem lineItem)
			throws ValidationException, ServiceException {
		InvoiceMapper mapper = new InvoiceMapper();
		InvoiceLineItem lineItemToSave = mapper.invoiceLineItemFrom(lineItem);
		InvoiceOperations operations = new InvoiceOperations();
		InvoiceLineItem savedLineItem = operations.saveInvoiceLineItem(lineItemToSave);
		return mapper.generatedInvoiceLineItemFrom(savedLineItem)
;	}
}
