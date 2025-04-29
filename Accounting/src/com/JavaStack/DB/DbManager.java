package com.JavaStack.DB;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


public class DbManager {
	private String CLASS = "oracle.jdbc.driver.OracleDriver";
	private String URL = "jdbc:oracle:thin:@localhost:1521:XE";
	private String USER = "test";
	private String PASSWD = "1234";
	protected Connection con;
	private static DbManager inst = null;
	protected Statement st;
	protected ResultSet rs;

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
			//showErr(e);
		}
	}

	public void dbClose() {
		// 오라클 접속 해제
		if (con != null) {
			System.out.println("오라클 접속 해제");
			try {
				con.close();
			} catch (SQLException e) {
				//showErr(e);
			}
		}
	}

//	public void createTable() {
//		String sql = "create table customer_t (\r\n"
//				+ "    cno varchar2(10) primary key,\r\n"
//				+ "    name varchar2(10) not null,\r\n"
//				+ "    tel varchar2(20) not null,\r\n"
//				+ "    addr varchar2(30) not null)";
//		
//		try {
//			st = con.createStatement();
//			st.executeQuery(sql);
//			System.out.println("테이블 생성 성공!");
//			con.commit();
//		} catch (SQLException e) {
//			showErr(e);
//			try {
//				con.rollback();
//			} catch (SQLException e1) {
//				showErr(e1);
//			}
//		}
//	}
//	
//	public void showErr(SQLException e) {
//		System.out.println("######################");
//		System.out.println("오류 코드:" + e.getErrorCode());
//		System.out.println("오류 내용:" + e.getMessage());
//		System.out.println("######################");
//		e.printStackTrace();
//	}
//	
//	public void dropTable() {
//		String sql = "drop table customer_t";
//
//		try {
//			st = con.createStatement();
//			st.executeQuery(sql);
//			System.out.println("테이블 삭제 성공!");
//			con.commit();
//		} catch (SQLException e) {
//			showErr(e);
//			try {
//				con.rollback();
//			} catch (SQLException e1) {
//				showErr(e1);
//			}
//		}
//	}
//	
//	public void insertDb() {
//		String sql = "insert into customer_t values ('C0002', '홍길동'," +
//				" '010-1111-1234', '조선 한양 홍대감댁')";
//
//		try {
//			st = con.createStatement();
//			st.executeQuery(sql);
//			System.out.println("데이터 추가 성공!");
//			con.commit();
//		} catch (SQLException e) {
//			showErr(e);
//			try {
//				con.rollback();
//			} catch (SQLException e1) {
//				showErr(e1);
//			}
//		}
//	}
//	
//	// 메소드 오버로딩 - 메인에서 고객번호, 이름, 전화, 주소를 입력받아서 DB저장
//	public void insertDb(String cno, String name, String tel, String addr) {
//		String sql = "insert into customer_t values ('C0002', '홍길동'," +
//				" '010-1111-1234', '조선 한양 홍대감댁')";
//		
//		String sql2 = 
//			String.format("insert into customer_t values ('%s', '%s', '%s', '%s')", 
//				cno, name, tel, addr);
//
//		try {
//			st = con.createStatement();
//			st.executeQuery(sql2);
//			System.out.println("데이터 추가 성공!");
//			con.commit();
//		} catch (SQLException e) {
//			showErr(e);
//			try {
//				con.rollback();
//			} catch (SQLException e1) {
//				showErr(e1);
//			}
//		}
//	}
//	
//	public String getCno() {
//		String cno = null;
//		String sql = "select cno from customer_t "
//				+ "order by to_number(substr(cno, 6)) desc";
//		try {
//			st = con.createStatement();
//			rs = st.executeQuery(sql);
//			while (rs.next()) {
//				cno = rs.getString("cno");
//				break;
//			}
//			rs.close();
//		} catch (SQLException e) {
//			showErr(e);
//		}
//		
//		if (cno == null) {
//			cno = "C000-1";
//		}
//		else {
//			String[] arr = cno.split("-");
//			int n = Integer.parseInt(arr[1]);
//			cno = "C000-" + (n+1);
//		}
//		return cno;
//	}
//	
//	// 메소드 오버로딩 - cno 자동 증가
//	public void insertDb(String name, String tel, String addr) {
//		PreparedStatement pstmt = null;
//		String sql = "insert into customer_t values (?,?,?,?)";
//
//		try {
//			pstmt = con.prepareStatement(sql);
//			pstmt.setString(1, getCno());
//			pstmt.setString(2, name);
//			pstmt.setString(3, tel);
//			pstmt.setString(4, addr);
//			pstmt.executeUpdate();
//			pstmt.close();
//			con.commit();
//		} catch (SQLException e) {
//			showErr(e);
//			try {
//				con.rollback();
//			} catch (SQLException e1) {
//				showErr(e1);
//			}
//		}
//	}
//	
//	public void insertDb(Customer cu) {
//		PreparedStatement pstmt = null;
//		String sql = "insert into customer_t values (?,?,?,?)";
//
//		try {
//			pstmt = con.prepareStatement(sql);
////			pstmt.setString(1, cu.getCno());
////			pstmt.setString(2, cu.getName());
////			pstmt.setString(3, cu.getTel());
////			pstmt.setString(4, cu.getAddr());
//			pstmt.executeUpdate();
//			pstmt.close();
//			con.commit();
//		} catch (SQLException e) {
//			showErr(e);
//			try {
//				con.rollback();
//			} catch (SQLException e1) {
//				showErr(e1);
//			}
//		}
//	}
//	
//	public void showDb() {
//		String sql = 
//			"select * from customer_t order by to_number(substr(cno, 6)) desc";
//		try {
//			st = con.createStatement();
//			rs = st.executeQuery(sql);
//			int i = 1;
//			while (rs.next() ) {
//				System.out.println("번호:"+ (i++));
//				System.out.println(
//						"고객번호:" + rs.getString("cno") + "\n" +
//						"이름:" + rs.getString("name") + "\n" +
//						"전화:" + rs.getString("tel") + "\n" +
//						"주소:" + rs.getString("addr"));
//				System.out.println("----------------------------");				
//			}
//			rs.close();
//		} catch (SQLException e) {
//			showErr(e);
//		}		
//	}
//	
//	public List<Customer> showDb2() {
//		List<Customer> list = new ArrayList<>();
//		PreparedStatement pstmt = null;
//		String sql = 
//			"select * from customer_t order by to_number(substr(cno, 6)) desc";
//		try {
//			pstmt = con.prepareStatement(sql);
//			rs = pstmt.executeQuery();
//			while (rs.next() ) {
////				list.add(new Customer(
////						rs.getString("cno"), 
////						rs.getString("name"), 
////						rs.getString("tel"), 
////						rs.getString("addr")));
//			}
//			rs.close();
//			pstmt.close();
//		} catch (SQLException e) {
//			showErr(e);
//		}
//		return list;
//	}
//	
//	public void updateDb(String srcName, String dstName) {
//		PreparedStatement pstmt = null;
//		String sql = "update customer_t set name=? where name=?";
//
//		try {
//			pstmt = con.prepareStatement(sql);
//			pstmt.setString(1, dstName);
//			pstmt.setString(2, srcName);
//			pstmt.executeUpdate();
//			pstmt.close();
//			con.commit();
//		} catch (SQLException e) {
//			showErr(e);
//			try {
//				con.rollback();
//			} catch (SQLException e1) {
//				showErr(e1);
//			}
//		}
//	}
}
