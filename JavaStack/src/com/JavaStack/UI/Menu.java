package com.JavaStack.UI;

import java.util.Scanner;

public class Menu {
	public int mainMenu(Scanner s) {
		System.out.println("=======================");
		System.out.println("오라클 인터넷뱅킹 App v1.5");
		System.out.println("=======================");
		System.out.println("1. 지출/수입 내역 등록");
		System.out.println("2. 지출/수입 내역 삭제");
		System.out.println("3. 지출/수입 내역 수정");
		System.out.println("4. 지출/수입 내역 보기");
		System.out.println("5. 잔액 보기");
		System.out.println("6. 카테고리 추가");
		System.out.println("7. 카테고리 삭제");
		System.out.println("8. 카테고리 수정");
		System.out.println("9. 종료");
		System.out.println("10. 카테고리 보기");
		System.out.println("=======================");
		System.out.print("메뉴 선택:");
		int m = s.nextInt();
		return m;
				

		
		
		
		
	}
	
	public int updateMenu(Scanner s) {
		System.out.println("===============");
		System.out.println("수정 항목 선택");
		System.out.println("===============");
		System.out.println("1.이름");
		System.out.println("2.전화");
		System.out.println("3.메인메뉴");
		System.out.println("===============");
		System.out.print("항목 선택:");
		return s.nextInt();
	}
}
