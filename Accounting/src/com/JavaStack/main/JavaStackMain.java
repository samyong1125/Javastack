package com.JavaStack.main;

import java.util.Date;

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
			
			while(true) {
				RecordService rs = new RecordService();
				rs.insertRecord(loginUI.getLoggedInMember().getMemberId(), 0, 0, 0, null, null);
				break;
			}
			

			RecordService recordService = new RecordService();

			while (true) {

				if (loginUI.mainMenu(loginUI.scanner) == 1) {	//지출, 수입
					
					    System.out.println("1. 수입 등록");
					    System.out.println("2. 지출 등록");
					    System.out.println("3. 기록 삭제");
					    System.out.println("4. 기록 수정");
					    System.out.println("5. 기록 전체 보기");

					    int subChoice = loginUI.mainMenu(loginUI.scanner);

					    if (subChoice == 1 || subChoice == 2) {
					        System.out.print("금액 입력: ");
					        int amount = loginUI.scanner.nextInt();
					        loginUI.scanner.nextLine(); // 개행 제거

					        System.out.print("내용 입력: ");
					        String details = loginUI.scanner.nextLine();

					        int paymentId = 1; // 기본값 또는 사용자로부터 받을 수 있음
					        int categoryId = (subChoice == 1) ? 1 : 2; // 예시: 수입:1, 지출:2
					        Date today = new Date(System.currentTimeMillis());

					        recordService.insertRecord(
					            loginUI.getLoggedInMember().getMemberId(),
					            paymentId,
					            categoryId,
					            amount,
					            details,
					            today
					        );

					    } else if (subChoice == 3) {
					        System.out.print("삭제할 record_id 입력: ");
					        int recordIdDel = loginUI.scanner.nextInt();
					        recordService.deleteRecord(recordIdDel);

					    } else if (subChoice == 4) {
					        System.out.print("수정할 record_id 입력: ");
					        int recordIdUpd = loginUI.scanner.nextInt();

					        System.out.print("새 금액 입력: ");
					        int newAmount = loginUI.scanner.nextInt();
					        loginUI.scanner.nextLine(); // 개행 제거

					        System.out.print("새 내용 입력: ");
					        String newDetails = loginUI.scanner.nextLine();

					        recordService.updateRecord(recordIdUpd, newAmount, newDetails);

					    } else if (subChoice == 5) {
					        recordService.showRecords();

					    } else {
					        System.out.println("올바른 숫자를 입력하세요.");
					    }
					}



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

