package com.JavaStack.service;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.JavaStack.DB.DbManager;
import com.JavaStack.model.Member;

//일, 월, 년, 카테고리 단위/record테이블에 있는 category id를 보고 category tpye의 값을 확인해서 ex, in 구분하여 합산하고 출력 
public class StatisticsService {
	
	DbManager db = DbManager.getInst();
	
	public void getMonthStatistics(Member mb) {
		
		String sql = "SELECT \r\n"
				+ "    r.year,\r\n"
				+ "    r.month,\r\n"
				+ "    SUM(CASE WHEN c.category_type = 'IN' THEN r.amount ELSE 0 END) AS income,\r\n"
				+ "    SUM(CASE WHEN c.category_type = 'EX' THEN r.amount ELSE 0 END) AS expense\r\n"
				+ "FROM \r\n"
				+ "    Record r\r\n"
				+ "JOIN \r\n"
				+ "    Category c ON r.category_id = c.category_id\r\n"
				+ "    \r\n"
				+ "where member_id = ?\r\n"
				+ "\r\n"
				+ "GROUP BY \r\n"
				+ "    r.year, r.month\r\n"
				+ "ORDER BY \r\n"
				+ "    r.year, r.month";
		
		
		try{
			PreparedStatement pstmt = db.con.prepareStatement(sql);
			pstmt.setInt(1, mb.getMemberId());
			ResultSet rs = pstmt.executeQuery();
		
	            while (rs.next()) {	                
	                System.out.print(rs.getInt("year") +"년");
	                System.out.print(rs.getInt("month")+ "월 ");
	                System.out.printf(" | 수입 : %,d ",rs.getInt("income"));
	                System.out.printf(" | 지출 : %,d ",rs.getInt("expense"));
	                System.out.println();
	                System.out.println("---------------------------------");
	            }
	        } catch (SQLException e) {
	            db.showErr(e);
	        }
			
	}
	
	public void getYearStatistics(Member mb) {
		
		String sql = " SELECT \r\n"
				+ "    r.year, \r\n"
				+ "    SUM(CASE WHEN c.category_type = 'IN' THEN r.amount ELSE 0 END) AS income, \r\n"
				+ "    SUM(CASE WHEN c.category_type = 'EX' THEN r.amount ELSE 0 END) AS expense \r\n"
				+ " FROM \r\n"
				+ "    Record r\r\n"
				+ " JOIN \r\n"
				+ "    Category c ON r.category_id = c.category_id\r\n"
				+ "    \r\n"
				+ " where member_id = ?\r\n"
				+ "\r\n"
				+ " GROUP BY \r\n"
				+ "    r.year\r\n"
				+ " ORDER BY \r\n"
				+ "    r.year";
		
		
		try{
			PreparedStatement pstmt = db.con.prepareStatement(sql);
			pstmt.setInt(1, mb.getMemberId());
			ResultSet rs = pstmt.executeQuery();
		
	            while (rs.next()) {	                
	                System.out.print(rs.getInt("year") +"년");
	                //System.out.print(" | 수입 : " + rs.getInt("income"));
	                System.out.printf(" | 수입 : %,d ",rs.getInt("income"));
	                System.out.printf(" | 지출 : %,d ",rs.getInt("expense"));
	                System.out.println();
	                System.out.println("---------------------------------");
	            }
	        } catch (SQLException e) {
	            db.showErr(e);
	        }
			
	}
	
	
	public void getCartegoryStatistics(Member mb) {
		
		String sql = "SELECT\r\n"
				+ "c.category_name,\r\n"
				+ "SUM(CASE WHEN c.category_type = 'IN' THEN r.amount ELSE 0 END) AS income,\r\n"
				+ "SUM(CASE WHEN c.category_type = 'EX' THEN r.amount ELSE 0 END) AS expense\r\n"
				+ "FROM \r\n"
				+ "    Record r\r\n"
				+ "JOIN \r\n"
				+ "    Category c ON r.category_id = c.category_id\r\n"
				+ "\r\n"
				+ "where member_id = ?\r\n"
				+ "\r\n"
				+ "GROUP BY \r\n"
				+ "    c.category_id, c.category_name\r\n"
				+ "ORDER BY \r\n"
				+ "    c.category_id ";
		
		
		try{
			PreparedStatement pstmt = db.con.prepareStatement(sql);
			pstmt.setInt(1, mb.getMemberId());
			ResultSet rs = pstmt.executeQuery();
		
	            while (rs.next()) {	                
	                System.out.print(rs.getString("category_name"));	                
	                System.out.printf(" | 수입 : %,d ",rs.getInt("income"));
	                System.out.printf(" | 지출 : %,d ",rs.getInt("expense"));
	                System.out.println();
	                System.out.println("---------------------------------");
	            }
	        } catch (SQLException e) {
	            db.showErr(e);
	        }
			
	}
	
	
}
