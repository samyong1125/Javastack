package com.JavaStack.DB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DbManager {
	private String CLASS = "oracle.jdbc.driver.OracleDriver";
	private String URL = "jdbc:oracle:thin:@localhost:1521:XE";
	private String USER = "test";
	private String PASSWD = "1234";
	public Connection con;
	private static DbManager inst = null;
	public Statement st;
	public ResultSet rs;
	public PreparedStatement pstmt;

	public DbManager() {
		dbConnect();
	}

	public static DbManager getInst() {
		if (inst == null) {
			inst = new DbManager();
		}
		return inst;
	}

	public void dbConnect() {
		try {
			Class.forName(CLASS);
			con = DriverManager.getConnection(URL, USER, PASSWD);
			con.setAutoCommit(false);
			System.out.println("오라클 접속 성공");
		} catch (ClassNotFoundException e) {
			System.out.println("오라클 드라이버를 못찾음!!");
			e.printStackTrace();
		} catch (SQLException e) {
			showErr(e);
		}
	}

	public void dbClose() {
		try {
			if (rs != null) rs.close();
			if (st != null) st.close();
			if (pstmt != null) pstmt.close();
		} catch (SQLException e) {
			showErr(e);
		}

		if (con != null) {
			System.out.println("오라클 접속 해제");
			try {
				con.close();
			} catch (SQLException e) {
				showErr(e);
			}
		}
	}

	public void commit() {
		try {
			if (con != null && !con.isClosed()) {
				con.commit();
				System.out.println("트랜잭션 커밋 완료");
			}
		} catch (SQLException e) {
			showErr(e);
		}
	}

	public void rollback() {
		try {
			if (con != null && !con.isClosed()) {
				con.rollback();
				System.out.println("트랜잭션 롤백 완료");
			}
		} catch (SQLException e) {
			showErr(e);
		}
	}

	public void showErr(SQLException e) {
		System.out.println("######################");
		System.out.println("오류 코드:" + e.getErrorCode());
		System.out.println("오류 내용:" + e.getMessage());
		System.out.println("######################");
		e.printStackTrace();
	}
}