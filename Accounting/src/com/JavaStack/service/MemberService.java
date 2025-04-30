package com.JavaStack.service;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.JavaStack.DB.DbManager;
import com.JavaStack.model.Member;


public class MemberService {
    private final DbManager dbManager;

    public MemberService() {
        dbManager = DbManager.getInst();
    }


    public Member login(String email, String password) {
        Member member = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            String sql = "SELECT member_id, member_name, email FROM test.member WHERE email = ? AND password = ?";
            pstmt = dbManager.con.prepareStatement(sql);
            pstmt.setString(1, email);
            pstmt.setString(2, password);

            rs = pstmt.executeQuery();

            if (rs.next()) {
                member = new Member();
                member.setMemberId(rs.getInt("member_id"));
                member.setMemberName(rs.getString("member_name"));
                member.setEmail(rs.getString("email"));
                
                System.out.println("로그인 성공: DB에서 사용자 정보 확인됨");
            } else {
                System.out.println("로그인 실패: 일치하는 정보가 없음");
            }

        } catch (SQLException e) {
            dbManager.showErr(e);
        } finally {
            try {
                if (rs != null) rs.close();
                if (pstmt != null) pstmt.close();
            } catch (SQLException e) {
                dbManager.showErr(e);
            }
        }

        return member;
    }

    //이메일, 비번 형식 체크 사용안할 예정
    public boolean validateCredentials(String email, String password) {
        if (email == null || email.trim().isEmpty()) {
            System.out.println("이메일을 입력해주세요.");
            return false;
        }

        if (password == null || password.trim().isEmpty()) {
            System.out.println("비밀번호를 입력해주세요.");
            return false;
        }

        if (!email.contains("@") || !email.contains(".")) {
            System.out.println("유효한 이메일 형식이 아닙니다.");
            return false;
        }

        return true;
    }
}