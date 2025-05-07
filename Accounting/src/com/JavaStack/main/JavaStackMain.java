package com.JavaStack.main;

import com.JavaStack.DB.DbManager;
import com.JavaStack.UI.RecordUI;
import com.JavaStack.UI.LoginUI;

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

            boolean runWhile = true;
            while (runWhile) {
                System.out.println("---가계부---");
                System.out.println("1. 수입/지출");
                System.out.println("2. 카테고리");
                System.out.println("3. 통계");
                System.out.println("4. 예산");
                System.out.println("5. 종료");
                System.out.print("숫자를 입력하세요: ");

                int mainChoice = loginUI.scanner.nextInt();

                switch (mainChoice) {
                    case 1:
                        RecordUI.recordUI(loginUI.scanner, loginUI.getLoggedInMember());
                        break;
                    case 2:
                        RecordUI.CateUI(loginUI.scanner, loginUI.getLoggedInMember());
                        break;
                    case 3:
                        RecordUI.StaUI(loginUI.scanner, loginUI.getLoggedInMember());
                        break;
                    case 4:
                        loginUI.updateBudgetAmount(loginUI.getLoggedInMember().getMemberId());
                    case 5:
                        System.out.println("프로그램을 종료합니다.");
                        runWhile = false;
                        break;

                    default:
                        System.out.println("잘못된 입력입니다. 1~5 사이의 숫자를 입력하세요.");
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
