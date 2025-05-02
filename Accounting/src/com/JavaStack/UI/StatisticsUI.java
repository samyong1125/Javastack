package com.JavaStack.UI;

import java.util.Scanner;

import com.JavaStack.model.Member;
import com.JavaStack.service.StatisticsService;

public class StatisticsUI {
	
	StatisticsService ss;
	
	public void ShowUI(Scanner sc, Member member) {
		if(ss == null) {
			ss = new StatisticsService();
		}
		
		int num = sc.nextInt();
		boolean isCheck = false;
		
		while(!isCheck) {
		 System.out.println("\n--- [통계 서브메뉴] ---");
            System.out.println("1. 월 지출 통계 보기");
            System.out.println("2. 년도 별 지출 통계 보기");
            System.out.println("3. 카테고리별 모든 지출 통계 보기");
            System.out.println("4. 뒤로가기");
            System.out.print("숫자를 입력하세요: ");
			switch (num) {
			case 1:
				//월 지출 통계
				ss.getMonthStatistics(member);
				break;
	
			case 2:
				//년도별 지출 통계
				ss.getYearStatistics(member);
				break;
			
			case 3:
				//카테고리별 지출 통계
				ss.getCartegoryStatistics(member);
				break;
				
			case 4:
				//뒤로가기
				isCheck = true;
				break;
				
			default:
				//잘못 입력하였습니다.
				break;
			}
		}
		
		
	}
}
