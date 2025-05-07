package com.JavaStack.service;

import com.JavaStack.DB.DbManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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
    
    @Override
    public boolean budgetExists(int memberId) {
        DbManager db = DbManager.getInst();
        Connection conn = db.getCon();
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        boolean exists = false;
        
        try {
            String sql = "SELECT COUNT(*) FROM budget WHERE member_id = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, memberId);
            rs = pstmt.executeQuery();
            
            if (rs.next()) {
                exists = rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
            } catch (Exception e) {
            }
            try {
                if (pstmt != null) pstmt.close();
            } catch (Exception e) {
            }
        }
        
        return exists;
    }
    
    @Override
    public boolean createBudget(int memberId, int amount) {
        if (budgetExists(memberId)) {
            return false; // 이미 예산이 존재
        }
        
        DbManager db = DbManager.getInst();
        Connection conn = db.getCon();
        PreparedStatement pstmt = null;
        boolean success = false;
        
        try {
            String sql = "INSERT INTO budget (budget_id, member_id, budget_amount) VALUES (seq_budget_id.NEXTVAL, ?, ?)";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, memberId);
            pstmt.setInt(2, amount);
            
            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                success = true;
                conn.commit();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                conn.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        } finally {
            try {
                if (pstmt != null) pstmt.close();
            } catch (Exception e) {
            }
        }
        
        return success;
    }
    
    @Override
    public boolean updateBudget(int memberId, int amount) {
        if (!budgetExists(memberId)) {
            return false; // 예산이 존재하지 않음
        }
        
        DbManager db = DbManager.getInst();
        Connection conn = db.getCon();
        PreparedStatement pstmt = null;
        boolean success = false;
        
        try {
            String sql = "UPDATE budget SET budget_amount = ? WHERE member_id = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, amount);
            pstmt.setInt(2, memberId);
            
            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                success = true;
                conn.commit();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                conn.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        } finally {
            try {
                if (pstmt != null) pstmt.close();
            } catch (Exception e) {
            }
        }
        
        return success;
    }
    
    @Override
    public int getCurrentBudgetAmount(int memberId) {
        DbManager db = DbManager.getInst();
        Connection conn = db.getCon();
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        int amount = 0;
        
        try {
            String sql = "SELECT budget_amount FROM budget WHERE member_id = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, memberId);
            rs = pstmt.executeQuery();
            
            if (rs.next()) {
                amount = rs.getInt("budget_amount");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
            } catch (Exception e) {
            }
            try {
                if (pstmt != null) pstmt.close();
            } catch (Exception e) {
            }
        }
        
        return amount;
    }
    
    // 이전 호환성을 위해 기존 메서드 유지 (내부적으로 새 메서드 호출)
    public int updateBudgetAmount(int memberId) {
        return checkBudgetBalance(memberId);
    }
}
