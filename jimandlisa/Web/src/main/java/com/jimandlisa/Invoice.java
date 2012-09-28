/**
 * 
 */
package com.jimandlisa;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SqlResultSetMapping;
import javax.persistence.EntityResult;
import javax.persistence.SqlResultSetMappings;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.NamedNativeQueries;
import org.hibernate.annotations.NamedNativeQuery;

/**
 * @author Lisa
 * 
 */
@Entity(name = "Invoice")
@SqlResultSetMappings({
		@SqlResultSetMapping(name = "implicit", entities = @EntityResult(entityClass = Invoice.class)),
		@SqlResultSetMapping(name = "NativeJoin", entities = {
				@EntityResult(entityClass = Invoice.class),
				@EntityResult(entityClass = InvoiceLineItem.class) }) })
@NamedNativeQueries({
		@NamedNativeQuery(name = "nativeSQL", query = "SELECT i.InvoiceID, i.Name, l.LineItemID, l.Name, l.Balance "
				+ "FROM Invoice i INNER JOIN InvoiceLineItem l "
				+ "WHERE ((l.InvoiceID = ?) AND (l.InvoiceID = i.InvoiceID))", resultClass = Invoice.class),
		@NamedNativeQuery(name = "nativeSP", query = "CALL getInvoice(:targetID)", resultClass = Invoice.class) })
public class Invoice implements Serializable {

	// UID.
	private static final long serialVersionUID = -3397263129495350023L;

	// Initializes new instance of Invoice class.
	public Invoice() {
		super();
		name = "";
		lineItems = new HashSet<InvoiceLineItem>();
	}

	/*
	 * Member variables -------------------------------------------------------
	 */

	// Invoice ID.
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "InvoiceID")
	private int invoiceID;

	// Invoice Name.
	@Column(name = "Name")
	private String name;

	// LineItems.
	@OneToMany(mappedBy = "invoice", cascade = javax.persistence.CascadeType.ALL)
	@Cascade(org.hibernate.annotations.CascadeType.ALL)
	private Set<InvoiceLineItem> lineItems;

	/*
	 * Getters and setters ----------------------------------------------------
	 */

	// Gets Invoice ID.
	public int getInvoiceID() {
		return invoiceID;
	}

	// Sets Invoice ID.
	public void setInvoiceID(int value) {
		invoiceID = value;
	}

	// Gets Invoice Name.
	public String getName() {
		return name;
	}

	// Sets Invoice Name.
	public void setName(String value) {
		name = value != null ? value : "";
	}

	// Gets line items.
	public Set<InvoiceLineItem> getLineItems() {
		return lineItems;
	}

	// Sets line items.
	public void setLineItems(Set<InvoiceLineItem> value) {
		lineItems = value != null ? value : new HashSet<InvoiceLineItem>();
	}

	/*
	 * Public methods ---------------------------------------------------------
	 */

	// Adds line item.
	public void addLineItem(InvoiceLineItem lineItem) {
		lineItem.setInvoice(this);
		lineItems.add(lineItem);
	}

	/*
	 * Overrides --------------------------------------------------------------
	 */

	// To string.
	@Override
	public String toString() {
		return "Invoice [id=" + invoiceID + ", name=" + name + ", lineItems="
				+ lineItems + "]";
	}
}
