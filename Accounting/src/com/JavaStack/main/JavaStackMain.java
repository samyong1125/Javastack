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

            if (!loginSuccess) {
                System.out.println("\n로그인 실패. 프로그램 종료.");
                return;
            }

            System.out.println("로그인됨: " + loginUI.getLoggedInMember());

            RecordService recordService = new RecordService();

            while (true) {
                int mainChoice = loginUI.mainMenu(loginUI.scanner);

                if (mainChoice == 1) { // 수입/지출
                    System.out.println("1. 수입 등록");
                    System.out.println("2. 지출 등록");
                    System.out.println("3. 기록 삭제");
                    System.out.println("4. 기록 수정");
                    System.out.println("5. 기록 전체 보기");

                    int subChoice = loginUI.scanner.nextInt();

                    if (subChoice == 1 || subChoice == 2) {
                        System.out.print("금액 입력: ");
                        int amount = loginUI.scanner.nextInt();
                        loginUI.scanner.nextLine();

                        System.out.print("내용 입력: ");
                        String details = loginUI.scanner.nextLine();

                        System.out.print("메모 입력(선택): ");
                        String memo = loginUI.scanner.nextLine();

                        int paymentId = 1;
                        int categoryId = (subChoice == 1) ? 1 : 2;

                        java.sql.Date today = new java.sql.Date(System.currentTimeMillis());

                        recordService.insertRecord(
                                loginUI.getLoggedInMember().getMemberId(),
                                paymentId,
                                categoryId,
                                amount,
                                details,
                                today,
                                memo
                        );

                    } else if (subChoice == 3) {
                        System.out.print("삭제할 record_id 입력: ");
                        int recordId = loginUI.scanner.nextInt();
                        recordService.deleteRecord(recordId);

                    } else if (subChoice == 4) {
                        System.out.print("수정할 record_id 입력: ");
                        int recordId = loginUI.scanner.nextInt();
                        loginUI.scanner.nextLine();

                        System.out.print("새 금액 입력: ");
                        int newAmount = loginUI.scanner.nextInt();
                        loginUI.scanner.nextLine();

                        System.out.print("새 내용 입력: ");
                        String newDetails = loginUI.scanner.nextLine();

                        System.out.print("새 메모 입력: ");
                        String newMemo = loginUI.scanner.nextLine();

                        recordService.updateRecordAllFields(
                                recordId,
                                loginUI.getLoggedInMember().getMemberId(),
                                1, 1,
                                newAmount,
                                newDetails,
                                new java.sql.Date(System.currentTimeMillis()),
                                newMemo
                        );

                    } else if (subChoice == 5) {
                        recordService.showRecords();

                    } else {
                        System.out.println("올바른 숫자를 입력하세요.");
                    }

                } else if (mainChoice == 2) {
                    // 카테고리 관리
                } else if (mainChoice == 3) {
                    // 통계 보기
                } else if (mainChoice == 4) {
                    break;
                } else {
                    System.out.println("올바른 숫자를 입력하세요.");
                }
            }

            loginUI.close();
            db.dbClose();
        } catch (Exception e) {
            System.out.println("예외 발생: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
