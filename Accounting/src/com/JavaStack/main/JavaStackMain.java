package com.JavaStack.main;

import com.JavaStack.DB.DbManager;
import com.JavaStack.UI.LoginUI;

public class JavaStackMain {

	public static void main(String[] args) {
		try {
			DbManager db = DbManager.getInst();

			LoginUI loginUI = new LoginUI();
			loginUI.showLoginScreen();

			if (loginUI.isLoggedIn()) {
				System.out.println("로그인 상태: 로그인됨");
				System.out.println("사용자 정보: " + loginUI.getLoggedInMember());
			} else {
				System.out.println("로그인 상태: 로그인되지 않음");
			}

			loginUI.close();
			db.dbClose();

		} catch (Exception e) {
			System.out.println("예외 발생: " + e.getMessage());
			e.printStackTrace();
		}
	}
}