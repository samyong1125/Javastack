package com.JavaStack.service;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.JavaStack.DB.DbManager;

public class RecordService {
    DbManager db = DbManager.getInst();

    // 1. 데이터 등록 (Insert)
    public void insertRecord(
            int memberId,
            int paymentId,
            int categoryId,
            int amount,
            String recordDetails,
            java.sql.Date regDate,
            String memoContent
    ) {
        String sql = "INSERT INTO record (" +
                "record_id, member_id, payment_id, category_id, amount, record_details, reg_date, memo_content) " +
                "VALUES (seq_record_id.NEXTVAL, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement pstmt = db.con.prepareStatement(sql)) {
            pstmt.setInt(1, memberId);
            pstmt.setInt(2, paymentId);
            pstmt.setInt(3, categoryId);
            pstmt.setInt(4, amount);
            pstmt.setString(5, recordDetails);
            pstmt.setDate(6, regDate);
            pstmt.setString(7, (memoContent == null || memoContent.trim().isEmpty()) ? null : memoContent);

            pstmt.executeUpdate();
            db.con.commit();
            System.out.println("Record 등록 성공");
        } catch (SQLException e) {
            db.showErr(e);
            try {
                db.con.rollback();
            } catch (SQLException ex) {
                db.showErr(ex);
            }
        }
    }


    // 2. 데이터 삭제 (Delete)
    public void deleteRecord(int recordId) {
        String sql = "DELETE FROM Record WHERE record_id = ?";
        //record_id입력받아야 함.

        try (PreparedStatement pstmt = db.con.prepareStatement(sql)) {
            pstmt.setInt(1, recordId);
            int rows = pstmt.executeUpdate();
            db.con.commit();
            if (rows > 0) {
                System.out.println("Record 삭제 성공");
            } else {
                System.out.println("해당 record_id가 존재하지 않습니다.");
            }
        } catch (SQLException e) {
            db.showErr(e);
            try {
                db.con.rollback();
            } catch (SQLException ex) {
                db.showErr(ex);
            }
        }
    }

    // 3. 데이터 수정 (Update)
    public void updateRecord(int recordId, int amount, String recordDetails) {
        String sql = "UPDATE record SET amount = ?, record_details = ? WHERE record_id = ?";
        try (PreparedStatement pstmt = db.con.prepareStatement(sql)) {
            pstmt.setInt(1, amount);
            pstmt.setString(2, recordDetails);
            pstmt.setInt(3, recordId);
            int rows = pstmt.executeUpdate();
            db.con.commit();
            if (rows > 0) {
                System.out.println("Record 수정 성공");
            } else {
                System.out.println("해당 record_id가 존재하지 않습니다.");
            }
        } catch (SQLException e) {
            db.showErr(e);
            try {
                db.con.rollback();
            } catch (SQLException ex) {
                db.showErr(ex);
            }
        }
    }

    // 4. 데이터 보기 (Select)
    public void showRecords() {
        String sql = "SELECT * FROM record ORDER BY reg_date DESC";
        try (Statement st = db.con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            int i = 1;
            while (rs.next()) {
                System.out.println("번호: " + (i++));
                System.out.println("Record ID: " + rs.getInt("record_id"));
                System.out.println("회원 ID: " + rs.getInt("member_id"));
                System.out.println("결제수단 ID: " + rs.getInt("payment_id"));
                System.out.println("카테고리 ID: " + rs.getInt("category_id"));
                System.out.println("금액: " + rs.getInt("amount"));
                System.out.println("세부사항: " + rs.getString("record_details"));
                System.out.println("등록일: " + rs.getDate("reg_date"));
                System.out.println("메모: " + rs.getString("memo_content"));  // ✅ 추가됨
                System.out.println("---------------------------------");
            }
        } catch (SQLException e) {
            db.showErr(e);
        }
    }


    // 5. 전체 필드 수정 (member_id, payment_id, category_id, amount, record_details, reg_date 모두 수정 가능)
    public void updateRecordAllFields(
            int recordId,
            int memberId,
            int paymentId,
            int categoryId,
            int amount,
            String recordDetails,
            Date regDate,
            String memoContent
    ) {
        String sql = "UPDATE record SET " +
                "member_id = ?, " +
                "payment_id = ?, " +
                "category_id = ?, " +
                "amount = ?, " +
                "record_details = ?, " +
                "reg_date = ?, " +
                "memo_content = ? " +
                "WHERE record_id = ?";
        try (PreparedStatement pstmt = db.con.prepareStatement(sql)) {
            pstmt.setInt(1, memberId);
            pstmt.setInt(2, paymentId);
            pstmt.setInt(3, categoryId);
            pstmt.setInt(4, amount);
            pstmt.setString(5, recordDetails);
            pstmt.setDate(6, regDate);
            pstmt.setString(7, memoContent);
            pstmt.setInt(8, recordId);

            int rows = pstmt.executeUpdate();
            db.con.commit();

            if (rows > 0) {
                System.out.println("Record 전체 필드 수정 성공");
            } else {
                System.out.println("해당 record_id가 존재하지 않습니다.");
            }
        } catch (SQLException e) {
            db.showErr(e);
            try {
                db.con.rollback();
            } catch (SQLException ex) {
                db.showErr(ex);
            }
        }
    }


}


