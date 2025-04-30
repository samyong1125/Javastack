package com.JavaStack.main;

import com.JavaStack.DB.DbManager;
import com.JavaStack.UI.LoginUI;
import com.JavaStack.service.RecordService;



public class JavaStackMain {

	public static void main(String[] args) {
		try {
			DbManager db = DbManager.getInst();

			LoginUI loginUI = new LoginUI();
			boolean loginSuccess = loginUI.showLoginScreen();

			if (loginSuccess) {
				System.out.println("\n로그인 상태: 로그인됨");
				System.out.println("사용자 정보: " + loginUI.getLoggedInMember());
				System.out.println("\n=== 메인 메뉴가 여기에 표시됩니다 ===");
			} else {
				System.out.println("\n로그인 상태: 로그인되지 않음");
				System.out.println("프로그램을 종료합니다.");
			}

			RecordService recordService = new RecordService();

			while (true) {

				if (loginUI.mainMenu(loginUI.scanner) == 1) {	//지출, 수입
					if (loginUI.mainMenu(loginUI.scanner) == 1) {
						//여기다 함수를 넣어주세요
					} else if (loginUI.mainMenu(loginUI.scanner) == 2) {
						//여기다 함수를 넣어주세요
					} else if (loginUI.mainMenu(loginUI.scanner) == 3) {
						//여기다 함수를 넣어주세요
					} else if (loginUI.mainMenu(loginUI.scanner) == 4) {

					} else {System.out.println("올바른 숫자를 입력하세요");}


				} else if (loginUI.mainMenu(loginUI.scanner) == 2) {	//카테고리
					if (loginUI.mainMenu(loginUI.scanner) == 1) {
						//여기다 함수를 넣어주세요
					} else if (loginUI.mainMenu(loginUI.scanner) == 2) {
						//여기다 함수를 넣어주세요
					} else if (loginUI.mainMenu(loginUI.scanner) == 3) {
						//여기다 함수를 넣어주세요
						break;
					} else if (loginUI.mainMenu(loginUI.scanner) == 4) {

					} else {System.out.println("올바른 숫자를 입력하세요");}

					
				} else if (loginUI.mainMenu(loginUI.scanner) == 3) {	//통계보기
					if (loginUI.mainMenu(loginUI.scanner) == 1) {
						//여기다 함수를 넣어주세요
					} else if (loginUI.mainMenu(loginUI.scanner) == 2) {
						//여기다 함수를 넣어주세요
					} else if (loginUI.mainMenu(loginUI.scanner) == 3) {
						//여기다 함수를 넣어주세요
					} else if (loginUI.mainMenu(loginUI.scanner) == 4) {

					} else {System.out.println("올바른 숫자를 입력하세요");}
				} else if (loginUI.mainMenu(loginUI.scanner) == 4) {	//종료
					break;
				} else {System.out.println("올바른 숫자를 입력하세요");}

			}

			loginUI.close();
			db.dbClose();

		} catch (Exception e) {
			System.out.println("예외 발생: " + e.getMessage());
			e.printStackTrace();
		}
	}

}

