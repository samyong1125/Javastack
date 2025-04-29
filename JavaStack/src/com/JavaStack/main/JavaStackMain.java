package com.JavaStack.main;

import com.JavaStack.DB.DbManager;

public class JavaStackMain {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		DbManager db = DbManager.getInst();
		db.dbClose();
	}

}
