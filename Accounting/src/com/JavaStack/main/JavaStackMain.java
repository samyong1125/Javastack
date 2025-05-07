package com.JavaStack.main;

import com.JavaStack.DB.DbManager;
import com.JavaStack.UI.RecordUI;
import com.JavaStack.UI.LoginUI;
import com.JavaStack.service.BudgetChecker;
import com.JavaStack.service.BudgetService;

public class JavaStackMain {
    public static void main(String[] args) {
        try {
            DbManager db = DbManager.getInst();
            LoginUI loginUI = new LoginUI();
            BudgetChecker budgetChecker = new BudgetService();
            boolean loginSuccess = loginUI.showLoginScreen();

            if (!loginSuccess) {
                System.out.println("\n로그인 실패. 프로그램 종료.");
                return;
            }

            System.out.println("로그인됨: " + loginUI.getLoggedInMember());

            // 로그인 시 예산 상태 확인
            int budgetBalance = budgetChecker.checkBudgetBalance(loginUI.getLoggedInMember().getMemberId());
            if (budgetChecker.isBudgetExceeded(loginUI.getLoggedInMember().getMemberId())) {
                System.out.println(" 경고: " + budgetChecker.formatBudgetStatus(budgetBalance));
            } else {
                System.out.println(" " + budgetChecker.formatBudgetStatus(budgetBalance));
            }

            boolean runWhile = true;
            while (runWhile) {
                System.out.println("---가계부---");
                System.out.println("1. 수입/지출");
                System.out.println("2. 카테고리");
                System.out.println("3. 통계");
                System.out.println("4. 예산 관리");
                System.out.println("5. 종료");
                System.out.print("숫자를 입력하세요: ");

                int mainChoice = loginUI.scanner.nextInt();

                switch (mainChoice) {
                    case 1:
                        RecordUI.recordUI(loginUI.scanner, loginUI.getLoggedInMember());
                        // 레코드 작업 후 예산 상태 확인
                        checkAndDisplayBudgetStatus(budgetChecker, loginUI.getLoggedInMember().getMemberId());
                        break;
                    case 2:
                        RecordUI.CateUI(loginUI.scanner, loginUI.getLoggedInMember());
                        break;
                    case 3:
                        RecordUI.StaUI(loginUI.scanner, loginUI.getLoggedInMember());
                        break;
                    case 4:
                        // 예산 관리 메뉴
                        showBudgetManagement(budgetChecker, loginUI);
                        break;
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
    
    // 예산 상태 확인 및 표시 메서드
    private static void checkAndDisplayBudgetStatus(BudgetChecker budgetChecker, int memberId) {
        int budgetBalance = budgetChecker.checkBudgetBalance(memberId);
        if (budgetChecker.isBudgetExceeded(memberId)) {
            System.out.println("⚠️ 경고: " + budgetChecker.formatBudgetStatus(budgetBalance));
        } else {
            System.out.println("✅ " + budgetChecker.formatBudgetStatus(budgetBalance));
        }
    }
    
    // 예산 관리 메뉴 표시
    private static void showBudgetManagement(BudgetChecker budgetChecker, LoginUI loginUI) {
        System.out.println("\n--- 예산 관리 ---");
        int memberId = loginUI.getLoggedInMember().getMemberId();
        
        // 현재 예산 상태 확인
        int budgetBalance = budgetChecker.checkBudgetBalance(memberId);
        System.out.println("현재 " + budgetChecker.formatBudgetStatus(budgetBalance));
        
        // 여기에 예산 수정 등의 추가 기능 구현 가능
        // 예: 예산 금액 수정, 카테고리별 예산 설정 등
    }
}
