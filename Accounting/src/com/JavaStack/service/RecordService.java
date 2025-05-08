package com.JavaStack.service;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

import com.JavaStack.DB.DbManager;

public class RecordService {
	DbManager db = DbManager.getInst();

	// 1. 데이터 등록 (Insert)
	public void insertRecord(int memberId, int paymentId, int categoryId, int amount, String recordDetails,
			java.sql.Date regDate, String memoContent) {
		String sql = "INSERT INTO record ("
				+ "record_id, member_id, payment_id, category_id, amount, record_details, reg_date, memo_content) "
				+ "VALUES (seq_record_id.NEXTVAL, ?, ?, ?, ?, ?, ?, ?)";

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
		// record_id 입력받아야 함.

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
		String sql = """
				SELECT 
				r.record_id,
				r.member_id,
				p.payment_name,
				c.category_name,
				r.amount,
				r.record_details,
				r.reg_date,
				r.memo_content
			FROM record r
			JOIN payment p ON r.payment_id = p.payment_id
			JOIN category c ON r.category_id = c.category_id
			ORDER BY r.reg_date DESC
			""";
		try (Statement st = db.con.createStatement(); ResultSet rs = st.executeQuery(sql)) {
			int i = 1;
			while (rs.next()) {
				System.out.println("번호: " + (i++));
				System.out.println("Record ID: " + rs.getInt("record_id"));
				System.out.println("회원 ID: " + rs.getInt("member_id"));
				System.out.println("결제수단 이름: " + rs.getString("payment_name"));
				System.out.println("카테고리 이름: " + rs.getString("category_name"));
				System.out.println("금액: " + rs.getInt("amount"));
				System.out.println("세부사항: " + rs.getString("record_details"));
				System.out.println("등록일: " + rs.getDate("reg_date"));
				System.out.println("메모: " + rs.getString("memo_content")); // ✅ 추가됨
				System.out.println("---------------------------------");
			}
		} catch (SQLException e) {
			db.showErr(e);
		}
	}

	// 5. 전체 필드 수정 (member_id, payment_id, category_id, amount, record_details,
	// reg_date 모두 수정 가능)
	public void updateRecordAllFields(int recordId, int memberId, int paymentId, int categoryId, int amount,
			String recordDetails, Date regDate, String memoContent) {
		String sql = "UPDATE record SET " + "member_id = ?, " + "payment_id = ?, " + "category_id = ?, "
				+ "amount = ?, " + "record_details = ?, " + "reg_date = ?, " + "memo_content = ? "
				+ "WHERE record_id = ?";
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

//	public void insertRecordWithNames(int memberId, Scanner scanner // 사용자 입력을 위한 Scanner 전달
//	) {
//		int paymentId = -1;
//		int categoryId = -1;
//
//		// 1. 유효한 payment_name 입력 받을 때까지 반복
//		while (paymentId == -1) {
//			System.out.print("결제수단 이름 입력 (예: 카드, 이체, 현금, 자동이체): ");
//			String paymentName = scanner.nextLine().trim();
//
//			paymentId = getPaymentIdByName(paymentName);
//			if (paymentId == -1) {
//				System.out.println("❌ 존재하지 않는 결제수단입니다. 다시 입력해주세요.");
//			}
//			
//		}
//		
//		// 2. 유효한 category_name 입력 받을 때까지 반복
//		while (categoryId == -1) {
////			scanner.close(); // 스캐너 종료
//			System.out.print("카테고리 이름 입력 (예: 월급, 식료품비 등): ");
//			String categoryName = scanner.nextLine().trim();
//
//			categoryId = getCategoryIdByName(categoryName);
//			if (categoryId == -1) {
//				System.out.println("❌ 존재하지 않는 카테고리입니다. 다시 입력해주세요.");
//			}
//		}
//
//		// 3. 나머지 값 입력
//		System.out.print("금액 입력: ");
//		int amount = scanner.nextInt();
//		scanner.nextLine();
//
//		System.out.print("상세 내용 입력: ");
//		String recordDetails = scanner.nextLine();
//
//		System.out.print("메모 입력 (선택): ");
//		String memo = scanner.nextLine();
//
//		java.sql.Date today = new java.sql.Date(System.currentTimeMillis());
//
//		// INSERT 실행
//		insertRecord(memberId, paymentId, categoryId, amount, recordDetails, today, memo);
//	}

	public void insertRecordWithNames(int memberId, Scanner scanner) {
		int paymentId = -1;
		int categoryId = -1;

		while (paymentId == -1) {
			System.out.print("결제수단 이름 입력 (예: 카드, 이체, 현금, 자동이체): ");
			String paymentName = scanner.nextLine().trim();
			paymentId = getPaymentIdByName(paymentName);
			if (paymentId == -1) {
				System.err.println("❌ 존재하지 않는 결제수단입니다. 다시 입력해주세요.");
			}
		}

		while (categoryId == -1) {
			Category category = new Category();
			category.showCategory();//카테고리 자바에서 불러옴.
			System.out.print("카테고리 이름 입력 (위의 카테고리 중 선택해 주세요.): ");
			String categoryName = scanner.nextLine().trim();
			categoryId = getCategoryIdByName(categoryName);
			if (categoryId == -1) {
				System.err.println("❌ 존재하지 않는 카테고리입니다. 다시 입력해주세요.");
			}
		}

		int amount = 0;
		while (true) {
			System.out.print("금액 입력: ");
			try {
				amount = Integer.parseInt(scanner.nextLine());
				if (amount < 0) {
					System.err.println("❌ 금액은 음수일 수 없습니다.");
				} else {
					break;
				}
			} catch (NumberFormatException e) {
				System.err.println("❌ 숫자를 입력해야 합니다.");
			}
		}

		System.out.print("상세 내용 입력: ");
		String recordDetails = scanner.nextLine();

		System.out.print("메모 입력 (선택): ");
		String memo = scanner.nextLine();

		java.sql.Date today = new java.sql.Date(System.currentTimeMillis());

		insertRecord(memberId, paymentId, categoryId, amount, recordDetails, today, memo);
	}

	// 보조 메서드: 이름으로 ID 조회
	private int getPaymentIdByName(String name) {
		String sql = "SELECT payment_id FROM payment WHERE payment_name = ?";
		try (PreparedStatement pstmt = db.con.prepareStatement(sql)) {
			pstmt.setString(1, name);
			ResultSet rs = pstmt.executeQuery();
			if (rs.next())
				return rs.getInt("payment_id");
		} catch (SQLException e) {
			db.showErr(e);
		}
		return -1;
	}

//	private int getCategoryIdByName(String name) {
//		String sql = "SELECT category_id FROM category WHERE category_name = ?";
//		try (PreparedStatement pstmt = db.con.prepareStatement(sql)) {
//			pstmt.setString(1, name);
//			ResultSet rs = pstmt.executeQuery();
//			if (rs.next())
//				return rs.getInt("category_id");
//		} catch (SQLException e) {
//			db.showErr(e);
//		}
//		return -1;
//	}
	private int getCategoryIdByName(String name) {
		int categoryId = 0;
	    String sql = "SELECT category_id FROM category WHERE category_name = ?";
	    System.out.println("검색 중인 카테고리 이름: " + name); // 디버깅
	    try (PreparedStatement pstmt = db.con.prepareStatement(sql)) {
	        pstmt.setString(1, name);
	        ResultSet rs = pstmt.executeQuery();
	        boolean hasResult = rs.next();
	        System.out.println("결과 있음: " + hasResult); // 디버깅
	        if (hasResult) {
	            categoryId = rs.getInt("category_id");
	            System.out.println("찾은 카테고리 ID: " + categoryId); // 디버깅
	            return categoryId;
	        }
	    } catch (SQLException e) {
	        System.out.println("SQL 에러 발생: " + e.getMessage()); // 자세한 에러 메시지
	        db.showErr(e);
	    }
	    System.out.println("카테고리를 찾을 수 없습니다."); // 디버깅
	    return -1;
	}

	public void updateRecordAllFields1(int recordId, int memberId, int paymentId, int categoryId, int amount,
			String recordDetails, java.sql.Date regDate, String memoContent) {
		String sql = """
				UPDATE record SET
				member_id = ?, payment_id = ?, category_id = ?, amount = ?,
				record_details = ?, reg_date = ?, memo_content = ?
				WHERE record_id = ?
				""";

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
				System.out.println("✅ Record 수정 성공");
			} else {
				System.out.println("❌ 해당 record_id가 존재하지 않습니다.");
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

//	public void deleteRecordWithValidation(Scanner scanner) {
//	        System.out.print("삭제할 record_id 입력: ");
//	        int recordId = scanner.nextInt();
//	        scanner.nextLine();
//
//	        if (!recordExists(recordId)) {
//	            System.out.println("❌ 해당 ID의 레코드가 존재하지 않습니다.");
//	            return;
//	        }
//
//	        deleteRecord(recordId);
//	       
//	     
//	    }
	public void deleteRecordWithValidation(Scanner scanner) {
		System.out.print("삭제할 record_id 입력: ");
		int recordId;
		try {
			recordId = Integer.parseInt(scanner.nextLine());
		} catch (NumberFormatException e) {
			System.err.println("❌ 숫자로 된 record_id를 입력해야 합니다.");
			return;
		}

		if (!recordExists(recordId)) {
			System.err.println("❌ 해당 ID의 레코드가 존재하지 않습니다.");
			return;
		}

		deleteRecord(recordId);
	}


	private boolean recordExists(int recordId) {
		String sql = "SELECT COUNT(*) FROM record WHERE record_id = ?";
		try (PreparedStatement pstmt = db.con.prepareStatement(sql)) {
			pstmt.setInt(1, recordId);
			ResultSet rs = pstmt.executeQuery();
			return rs.next() && rs.getInt(1) > 0;
		} catch (SQLException e) {
			db.showErr(e);
			return false;
		}
	}

	public void updateRecordAllFieldsWithNames(int recordId, int memberId, Scanner scanner) {
	    int paymentId = -1;
	    int categoryId = -1;

	    while (paymentId == -1) {
	        System.out.print("수정할 결제수단 이름 입력: ");
	        String paymentName = scanner.nextLine().trim();
	        paymentId = getPaymentIdByName(paymentName);
	        if (paymentId == -1) {
	            System.out.println("❌ 존재하지 않는 결제수단입니다. 다시 입력해주세요.");
	        }
	    }

	    while (categoryId == -1) {
	        System.out.print("수정할 카테고리 이름 입력: ");
	        String categoryName = scanner.nextLine().trim();
	        categoryId = getCategoryIdByName(categoryName);
	        if (categoryId == -1) {
	            System.out.println("❌ 존재하지 않는 카테고리입니다. 다시 입력해주세요.");
	        }
	    }

	    System.out.print("새 금액 입력: ");
	    int amount = scanner.nextInt();
	    scanner.nextLine();

	    System.out.print("새 상세내용 입력: ");
	    String details = scanner.nextLine();

	    System.out.print("새 메모 입력: ");
	    String memo = scanner.nextLine();

	    java.sql.Date today = new java.sql.Date(System.currentTimeMillis());

	    updateRecordAllFields(recordId, memberId, paymentId, categoryId, amount, details, today, memo);
	}

}
