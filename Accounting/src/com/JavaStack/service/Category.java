package com.JavaStack.service;

import java.sql.PreparedStatement;
import java.sql.ResultSet; 
import java.sql.SQLException;

import com.JavaStack.DB.DbManager;

public class Category {
	DbManager db = DbManager.getInst();
    public void insertCategory(
    		int CategoryID, 
    		String CategoryName, 
    		String CategoryType) {
		String sql = "INSERT INTO category (category_id, category_name, category_type)"
				+ " VALUES (seq_category_id.NEXTVAL, ?, ?)";
	    try (PreparedStatement pstmt = db.con.prepareStatement(sql)) {
	        pstmt.setInt(1, CategoryID);
	        pstmt.setString(2, CategoryName);	        
	        pstmt.setString(3, CategoryType);
	        
	        pstmt.executeUpdate();
	        db.commit();
	        System.out.println("카테고리 등록 성공");
        } catch (SQLException e) {
	        db.showErr(e);
	        try { db.con.rollback(); } catch (SQLException ex) { db.showErr(ex); }
        }
    }
    
    public void deleteCategory(int CategoryID) {
    	String sql = "DELETE FROM Category WHERE Category_id = ?";
        try (PreparedStatement pstmt = db.con.prepareStatement(sql)) {
            pstmt.setInt(1, CategoryID);
            int rows = pstmt.executeUpdate();
            db.commit();

            if (rows > 0) {
                System.out.println("카테고리 삭제 성공");
            } else {
                System.out.println("해당 카테고리가 존재하지 않습니다.");
            }
        } catch (SQLException e) {
            db.showErr(e);
            try { db.con.rollback(); } catch (SQLException ex) { db.showErr(ex); }
        }
    }

    public void updateCategory(
    		int CategoryID, 
    		String CategoryName, 
    		String CategoryType) {
        String sql = "UPDATE Category SET category_name = ?, "
        		+ "category_type = ? WHERE category_id = ?";
        try (PreparedStatement pstmt = db.con.prepareStatement(sql)) {
            pstmt.setString(1, CategoryName);
            pstmt.setString(2, CategoryType);
            pstmt.setInt(3, CategoryID);

            int rows = pstmt.executeUpdate();
            db.commit();
            if (rows > 0) {
                System.out.println("카테고리 수정 성공");
            } else {
                System.out.println("해당 카테고리 ID가 존재하지 않습니다.");
            }
        } catch (SQLException e) {
            db.showErr(e);
            try { db.con.rollback(); } catch (SQLException ex) { db.showErr(ex); }
        }
    }
        
    public void showCategory() {
        String sql = "SELECT * FROM Category ORDER BY category_id";
        try (PreparedStatement pstmt = db.con.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                System.out.println("카테고리 ID: " + rs.getInt("category_id"));
                System.out.println("카테고리 이름: " + rs.getString("category_name"));
                System.out.println("카테고리 유형: " + rs.getString("category_type"));
                System.out.println("--------------------------");
            }
        } catch (SQLException e) {
            db.showErr(e);
            try { db.con.rollback(); } catch (SQLException ex) { db.showErr(ex); }
        }
    }
}
    