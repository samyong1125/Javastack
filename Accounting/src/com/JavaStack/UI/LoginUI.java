package com.JavaStack.UI;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

import com.JavaStack.DB.DbManager;
import com.JavaStack.model.Member;
import com.JavaStack.service.MemberService;

public class LoginUI {
    public Scanner scanner;
    private final MemberService memberService;
    private Member loggedInMember;

    public LoginUI() {
        scanner = new Scanner(System.in);
        memberService = new MemberService();
        loggedInMember = null;
    }


    public boolean showLoginScreen() {
        int maxAttempts = 3;
        int attempts = 0;

        while (attempts < maxAttempts) {
            System.out.println("\n===== 가계부 프로그램 로그인 =====");
            System.out.println("이메일과 비밀번호를 입력하세요. (남은 시도: " + (maxAttempts - attempts) + ")");

            System.out.print("이메일: ");
            //String email = scanner.nextLine();
            String email = "hong@test.com";
            System.out.print("비밀번호: ");
            //String password = scanner.nextLine();
            String password ="pass123";

            loggedInMember = memberService.login(email, password);

            if (loggedInMember != null) {
                System.out.println("로그인 성공: " + loggedInMember.getMemberName() + "님 환영합니다!");
                //asdf
                return true;
            } else {
                System.out.println("로그인 실패: 이메일 또는 비밀번호가 일치하지 않습니다.");
                attempts++;
            }
        }

        System.out.println("\n최대 로그인 시도 횟수를 초과했습니다. 프로그램을 종료합니다.");
        return false;
    }

    public boolean isLoggedIn() {
        return loggedInMember != null;
    }


    public Member getLoggedInMember() {
        return loggedInMember;
    }

    public int updateBudgetAmount(int memberId) {
        DbManager db = DbManager.getInst();
        Connection conn = db.getCon();
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        int updatedBudgetAmount = -1;

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

            System.out.println(totalExpenditure);
            System.out.println(memberId);
            // 2. 예산에서 지출금액 차감
            String updateBudgetSql = " UPDATE budget " +
                    " SET BUDGET_AMOUNT = BUDGET_AMOUNT - ? " +
                    " WHERE member_id = ? ";

            pstmt = conn.prepareStatement(updateBudgetSql);
            pstmt.setInt(1, totalExpenditure);
            pstmt.setInt(2, memberId);
            pstmt.executeUpdate();
            conn.commit();
            pstmt.close();

            // 3. 갱신된 예산(amount) 조회
            String selectUpdatedAmountSql = "SELECT BUDGET_AMOUNT FROM budget " +
                    " WHERE member_id = ? ";

            pstmt = conn.prepareStatement(selectUpdatedAmountSql);
            pstmt.setInt(1, memberId);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                updatedBudgetAmount = rs.getInt("BUDGET_AMOUNT");
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 자원 해제
            try { if (rs != null) rs.close(); } catch (Exception e) {}
            try { if (pstmt != null) pstmt.close(); } catch (Exception e) {}
            try { if (conn != null) conn.close(); } catch (Exception e) {}
        }

        System.out.println(updatedBudgetAmount + "입니다.");
        return updatedBudgetAmount;
    }


    public void close() {
        if (scanner != null) {
            scanner.close();
        }
    }
}