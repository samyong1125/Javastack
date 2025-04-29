package com.JavaStack.model;

public class Payment {

	
	int payment_id;
	String payment_name;
	String payment_type;
	
	
	
	public Payment(int payment_id, String payment_name, String payment_type) {
		super();
		this.payment_id = payment_id;
		this.payment_name = payment_name;
		this.payment_type = payment_type;
	}
	
	public int getPayment_id() {
		return payment_id;
	}
	public void setPayment_id(int payment_id) {
		this.payment_id = payment_id;
	}
	public String getPayment_name() {
		return payment_name;
	}
	public void setPayment_name(String payment_name) {
		this.payment_name = payment_name;
	}
	public String getPayment_type() {
		return payment_type;
	}
	public void setPayment_type(String payment_type) {
		this.payment_type = payment_type;
	}
	
	
	
}
