package com.JavaStack.service;

/**
 * 예산 확인 관련 기능을 정의하는 인터페이스
 */
public interface BudgetChecker {
    /**
     * 현재 예산 잔액을 확인합니다.
     * 양수: 예산 내에서 지출 중
     * 음수: 예산 초과
     * 
     * @param memberId 회원 ID
     * @return 현재 예산 잔액 (예산 - 지출)
     */
    int checkBudgetBalance(int memberId);
    
    /**
     * 예산이 초과되었는지 확인합니다.
     * 
     * @param memberId 회원 ID
     * @return 예산 초과 여부
     */
    boolean isBudgetExceeded(int memberId);
    
    /**
     * 예산 상태 메시지를 반환합니다.
     * 
     * @param balance 예산 잔액
     * @return 포맷팅된 예산 상태 메시지
     */
    String formatBudgetStatus(int balance);
    
    /**
     * 사용자의 예산이 존재하는지 확인합니다.
     * 
     * @param memberId 회원 ID
     * @return 예산 존재 여부
     */
    boolean budgetExists(int memberId);
    
    /**
     * 새로운 예산을 설정합니다.
     * 
     * @param memberId 회원 ID
     * @param amount 예산 금액
     * @return 성공 여부
     */
    boolean createBudget(int memberId, int amount);
    
    /**
     * 기존 예산을 업데이트합니다.
     * 
     * @param memberId 회원 ID
     * @param amount 새 예산 금액
     * @return 성공 여부
     */
    boolean updateBudget(int memberId, int amount);
    
    /**
     * 현재 설정된 예산 금액을 조회합니다.
     * 
     * @param memberId 회원 ID
     * @return 예산 금액
     */
    int getCurrentBudgetAmount(int memberId);
} 