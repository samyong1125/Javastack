package com.JavaStack.service;

import com.JavaStack.DB.DbManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class BudgetService implements BudgetChecker {
    @Override
    public int checkBudgetBalance(int memberId) {
        DbManager db = DbManager.getInst();
        Connection conn = db.getCon();
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        int budgetBalance = 0; // 잔여 예산
        
        try {
            // 1. 지출(EX) 총액 계산
            String sumExpenditureSql = """
                    SELECT NVL(SUM(r.amount), 0) AS total_expenditure
                    FROM record r
                    JOIN category c ON r.category_id = c.category_id
                    WHERE c.category_type = 'EX' AND r.member_id = ?
                    """;

            pstmt = conn.prepareStatement(sumExpenditureSql);
            pstmt.setInt(1, memberId);
            rs = pstmt.executeQuery();

            int totalExpenditure = 0;
            if (rs.next()) {
                totalExpenditure = rs.getInt("total_expenditure");
            }

            rs.close();
            pstmt.close();

            // 2. 갱신된 예산(amount) 조회
            String selectBudgetSql = "SELECT BUDGET_AMOUNT FROM budget WHERE member_id = ?";

            pstmt = conn.prepareStatement(selectBudgetSql);
            pstmt.setInt(1, memberId);
            rs = pstmt.executeQuery();

            int budgetAmount = 0;
            if (rs.next()) {
                budgetAmount = rs.getInt("BUDGET_AMOUNT");
            }
            
            // 3. 예산 잔액 계산 (예산 - 지출)
            budgetBalance = budgetAmount - totalExpenditure;

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 자원 해제
            try {
                if (rs != null) rs.close();
            } catch (Exception e) {
            }
            try {
                if (pstmt != null) pstmt.close();
            } catch (Exception e) {
            }
        }

        return budgetBalance;
    }
    
    @Override
    public boolean isBudgetExceeded(int memberId) {
        return checkBudgetBalance(memberId) < 0;
    }
    
    @Override
    public String formatBudgetStatus(int balance) {
        if (balance < 0) {
            return String.format("예산 초과: %,d원", Math.abs(balance));
        } else {
            return String.format("남은 예산: %,d원", balance);
        }
    }
    
    // 이전 호환성을 위해 기존 메서드 유지 (내부적으로 새 메서드 호출)
    public int updateBudgetAmount(int memberId) {
        return checkBudgetBalance(memberId);
    }
}
