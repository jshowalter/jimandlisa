package com.jimandlisa;
import java.util.ResourceBundle;

public class AAB {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
            ResourceBundle bundle = ResourceBundle.getBundle("messages");
            String message = bundle.getString("InvoiceOperations.1");
            System.out.println(message);
		} catch (Exception x) {
			x.printStackTrace();
		}
	}
}
