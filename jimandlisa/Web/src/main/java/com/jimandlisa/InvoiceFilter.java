/**
 * 
 */
package com.jimandlisa;

public class InvoiceFilter {

	private String filterClause;

	// Initializes new instance of InvoiceFilter class.
	public InvoiceFilter() {
		super();
		filterClause = "";
	}

	public String getFilterClause() {
		return filterClause;
	}

	public void setFilterClause(String value) {
		filterClause = value != null ? value : "";
	}
}
