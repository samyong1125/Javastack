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
} 