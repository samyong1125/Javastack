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
        boolean inSubMenu = true;
        int memberId = loginUI.getLoggedInMember().getMemberId();
        
        while (inSubMenu) {
            System.out.println("\n--- 예산 관리 메뉴 ---");
            
            // 현재 예산 확인 및 표시
            boolean budgetExists = budgetChecker.budgetExists(memberId);
            int currentBudget = budgetChecker.getCurrentBudgetAmount(memberId);
            
            if (budgetExists) {
                System.out.println("현재 설정된 예산: " + String.format("%,d원", currentBudget));
                
                // 지출 정보 및 잔여 예산 표시
                int budgetBalance = budgetChecker.checkBudgetBalance(memberId);
                System.out.println("현재 상태: " + budgetChecker.formatBudgetStatus(budgetBalance));
            } else {
                System.out.println("현재 설정된 예산이 없습니다.");
            }
            
            System.out.println("\n1. 예산 설정/수정");
            System.out.println("2. 메인 메뉴로 돌아가기");
            System.out.print("선택: ");
            
            int choice = loginUI.scanner.nextInt();
            loginUI.scanner.nextLine(); // 개행 처리
            
            switch (choice) {
                case 1:
                    // 예산 설정/수정
                    System.out.print("설정할 예산 금액을 입력하세요: ");
                    int newBudgetAmount = loginUI.scanner.nextInt();
                    loginUI.scanner.nextLine(); // 개행 처리
                    
                    if (newBudgetAmount <= 0) {
                        System.out.println("예산은 0보다 커야 합니다.");
                        continue;
                    }
                    
                    boolean success;
                    if (budgetExists) {
                        // 기존 예산 업데이트
                        success = budgetChecker.updateBudget(memberId, newBudgetAmount);
                        if (success) {
                            System.out.println("예산이 " + String.format("%,d원", newBudgetAmount) + "으로 업데이트되었습니다.");
                        } else {
                            System.out.println("예산 업데이트 중 오류가 발생했습니다.");
                        }
                    } else {
                        // 새 예산 생성
                        success = budgetChecker.createBudget(memberId, newBudgetAmount);
                        if (success) {
                            System.out.println("새 예산이 " + String.format("%,d원", newBudgetAmount) + "으로 설정되었습니다.");
                        } else {
                            System.out.println("예산 설정 중 오류가 발생했습니다.");
                        }
                    }
                    break;
                    
                case 2:
                    // 메인 메뉴로 돌아가기
                    inSubMenu = false;
                    break;
                    
                default:
                    System.out.println("잘못된 선택입니다. 다시 선택해주세요.");
            }
        }
    }
}
