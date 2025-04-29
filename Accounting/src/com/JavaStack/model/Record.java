package com.JavaStack.model;

public class Record {

	int record_id;
	int member_id;
	int payment_id;
	int category_id;
	int amount;
	String record_details;
	String reg_date;
	
	
	
	public Record(int record_id, int member_id, int payment_id, int category_id, int amount, String record_details,
			String reg_date) {
		super();
		this.record_id = record_id;
		this.member_id = member_id;
		this.payment_id = payment_id;
		this.category_id = category_id;
		this.amount = amount;
		this.record_details = record_details;
		this.reg_date = reg_date;
	}
	
	
	public int getRecord_id() {
		return record_id;
	}
	public void setRecord_id(int record_id) {
		this.record_id = record_id;
	}
	public int getMember_id() {
		return member_id;
	}
	public void setMember_id(int member_id) {
		this.member_id = member_id;
	}
	public int getPayment_id() {
		return payment_id;
	}
	public void setPayment_id(int payment_id) {
		this.payment_id = payment_id;
	}
	public int getCategory_id() {
		return category_id;
	}
	public void setCategory_id(int category_id) {
		this.category_id = category_id;
	}
	public int getAmount() {
		return amount;
	}
	public void setAmount(int amount) {
		this.amount = amount;
	}
	public String getRecord_details() {
		return record_details;
	}
	public void setRecord_details(String record_details) {
		this.record_details = record_details;
	}
	public String getReg_date() {
		return reg_date;
	}
	public void setReg_date(String reg_date) {
		this.reg_date = reg_date;
	}
	
	
	
	
}
