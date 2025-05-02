package com.JavaStack.service;

import java.sql.PreparedStatement;
import java.sql.ResultSet; 
import java.sql.SQLException;
import java.util.Scanner;

import com.JavaStack.DB.DbManager;

public class Category {
	DbManager db = DbManager.getInst();
    public void insertCategory(
    		String CategoryName, 
    		String CategoryType) {
		String sql = "INSERT INTO category (category_id, category_name, category_type)"
				+ " VALUES (seq_category_id.NEXTVAL, ?, ?)";
	    try (PreparedStatement pstmt = db.con.prepareStatement(sql)) {
	        pstmt.setString(1, CategoryName);	        
	        pstmt.setString(2, CategoryType);
	        
	        pstmt.executeUpdate();
	        db.con.commit();
	        System.out.println("카테고리 등록 성공");
        } catch (SQLException e) {
			db.showErr(e);
			try {
				db.con.rollback();
			} catch (SQLException ex) {
				db.showErr(ex); }
        }
    }
    
    public void deleteCategory(String CategoryName) {
    	String sql = "DELETE FROM Category WHERE category_name = ?";
        try (PreparedStatement pstmt = db.con.prepareStatement(sql)) {
            pstmt.setString(1, CategoryName);
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
    		String NewName, 
    		String CategoryType,
    		String OldName) {
        String sql = "UPDATE Category SET category_name = ?, "
        		+ "category_type = ? WHERE category_name = ?";
        try (PreparedStatement pstmt = db.con.prepareStatement(sql)) {
            pstmt.setString(1, NewName);
            pstmt.setString(2, CategoryType);
            pstmt.setString(3, OldName);
            
            int rows = pstmt.executeUpdate();
            db.commit();
            if (rows > 0) {
                System.out.println("카테고리 수정 성공");
            } else {
                System.out.println("해당 카테고리 이름이 존재하지 않습니다.");
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
            	System.out.println("--------------------------");
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
    
    public void insertCategoryName(Scanner sc) {
    	sc.nextLine();
        System.out.print("추가할 카테고리 이름: ");
        String CategoryName = sc.nextLine();
        
        System.out.print("카테고리 타입(IN or EX): ");
        String CategoryType = sc.nextLine();
        
        if (!CategoryType.equals("IN") && !CategoryType.equals("EX")) {
            System.out.println("존재하지 않는 카테고리 타입입니다");
            return;
        }
        
        insertCategory(CategoryName, CategoryType);
        
    }
    
	public void deleteCategoryName(Scanner sc) {
		sc.nextLine();
		System.out.print("삭제할 카테고리 이름: ");
		String CategoryName = sc.nextLine();
		deleteCategory(CategoryName);
	}
	
	public void updateCategoryName(Scanner sc) {
		sc.nextLine();
		System.out.print("수정할 카테고리 이름: ");
		String OldName = sc.nextLine();
        System.out.print("새로운 카테고리 이름: ");
        String NewName = sc.nextLine();
        System.out.print("새로운 카테고리 타입(IN or EX): ");
        String CategoryType = sc.nextLine();
        
        if (!CategoryType.equals("IN") && !CategoryType.equals("EX")) {
            System.out.println("존재하지 않는 카테고리 타입입니다");
            return;
        }
        
        updateCategory(NewName, CategoryType, OldName);
	}
}
    