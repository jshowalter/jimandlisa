/**
 * 
 */
package com.jimandlisa;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 * @author Lisa
 * 
 */
@Entity(name = "InvoiceLineItem")
public class InvoiceLineItem implements Serializable {

	private static final long serialVersionUID = -3397263129495350023L;

	// Initializes new instance of LineItem class
	public InvoiceLineItem() {
		super();
		name = "";
		invoice = null;
	}

	// LineItem ID
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "LineItemID")
	private int lineItemID;

	// LineItem Name
	@Column(name = "Name")
	private String name;

	// LineItem Balance
	@Column(name = "Balance")
	private BigDecimal balance;

	@ManyToOne(cascade = { javax.persistence.CascadeType.ALL })
	@JoinColumn(name = "InvoiceId")
	private Invoice invoice;

	// Gets LineItem ID
	public int getLineItemID() {
		return lineItemID;
	}

	// Sets LineItem ID
	public void setLineItemID(int value) {
		lineItemID = value;
	}

	// Gets LineItem Name
	public String getName() {
		return name;
	}

	// Sets LineItem Name
	public void setName(String value) {
		name = value != null ? value : "";
	}

	// Gets LineItem Balance
	public BigDecimal getBalance() {
		return balance;
	}

	// Sets LineItem Balance
	public void setBalance(BigDecimal value) {
		balance = value;
	}

	// Gets Invoice
	public Invoice getInvoice() {
		return invoice;
	}

	// Sets Invoice
	public void setInvoice(Invoice value) {
		invoice = value;
	}

	// To string.
	@Override
	public String toString() {
		return "LineItem [id=" + lineItemID + ", name=" + name + ", balance="
				+ balance + "]";
	}
}
