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

			int HAHAHA = loginUI.mainMenu(loginUI.scanner);

			while (true) {
				switch (HAHAHA) {
					case 1: {	//지출, 수입
						switch (HAHAHA) {
							case 1: {

								//여기다 함수를 넣어주세요
								break;
							}
							case 2: {
								//여기다 함수를 넣어주세요
								break;
							}
							case 3: {
								//여기다 함수를 넣어주세요
								break;
							}
							case 4: continue;	//이전
						}
						break;
					}
					case 2: {	//카테고리
						switch (HAHAHA) {
							case 1: {
								//여기다 함수를 넣어주세요
								break;
							}
							case 2: {
								//여기다 함수를 넣어주세요
								break;
							}
							case 3: {
								//여기다 함수를 넣어주세요
								break;
							}
							case 4: {continue;}	//이전
						}
						break;
					}
					case 3: {	//통계
						switch (HAHAHA) {
							case 1: {
								//여기다 함수를 넣어주세요
								break;
							}
							case 2: {
								//여기다 함수를 넣어주세요
								break;
							}
							case 3: {
								//여기다 함수를 넣어주세요
								break;
							}
							case 4: {continue;}	//이전
						}
						break;
					}
					case 4: {break;}	//종료
				}
				break;
			}

			loginUI.close();
			db.dbClose();

		} catch (Exception e) {
			System.out.println("예외 발생: " + e.getMessage());
			e.printStackTrace();
		}
	}

}

