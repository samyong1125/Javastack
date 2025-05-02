//package com.JavaStack.UI;
//
//import java.util.Scanner;
//
//public class RecordUI {
//
//    public void recordUI(Scanner sc) {
//        boolean inSubMenu = true;
//        while (inSubMenu) {
//            System.out.println("---서브메뉴---");
//            System.out.println("1.");
//            System.out.println("2.");
//            System.out.println("3.");
//            System.out.println("4.");
//            System.out.println("5. 메인 메뉴로 돌아가기");
//            System.out.print("숫자를 입력하세요: ");
//
//            int subChoice = sc.nextInt();
//
//            if (subChoice == 5) {
//                inSubMenu = false; // 메인 메뉴로 돌아가기
//            } else {
//                System.out.println("서브 메뉴 " + subChoice + " 선택됨.");
//            }
//        }
//    }
//}
package com.JavaStack.UI;

import java.util.Scanner;

import com.JavaStack.service.Category;
import com.JavaStack.service.RecordService;
import com.JavaStack.model.Member;
import com.JavaStack.service.StatisticsService;

public class RecordUI {

    private static RecordService recordService;


    public static void recordUI(Scanner sc, Member member) {
        boolean inSubMenu = true;

        if (recordService == null) {
            recordService = new RecordService();
        }

        while (inSubMenu) {
            System.out.println("\n--- [지출/수입 서브메뉴] ---");
            System.out.println("1. 수입/지출 기록 추가");
            System.out.println("2. 기록 삭제");
            System.out.println("3. 기록 수정");
            System.out.println("4. 전체 기록 보기");
            System.out.println("5. 메인 메뉴로 돌아가기");
            System.out.print("숫자를 입력하세요: ");

            int subChoice = sc.nextInt();
            sc.nextLine(); // 개행 제거

            switch (subChoice) {
                case 1:
                    recordService.insertRecordWithNames(member.getMemberId(), sc);
                    break;

                case 2:
                    recordService.deleteRecordWithValidation(sc);
                    break;

                case 3:
                    System.out.print("수정할 record_id 입력: ");
                    int recordId = sc.nextInt();
                    sc.nextLine();
                    recordService.updateRecordAllFieldsWithNames(recordId, member.getMemberId(), sc);
                    break;

                case 4:
                    recordService.showRecords();
                    break;

                case 5:
                    inSubMenu = false; // 메인 메뉴로 돌아가기
                    break;

                default:
                    System.out.println("❌ 올바른 숫자를 입력해주세요.");
            }
        }
    }

    static StatisticsService ss;

    public static void StaUI(Scanner sc, Member member) {
        if(ss == null) {
            ss = new StatisticsService();
        }


        boolean isCheck = false;

        while(!isCheck) {
            System.out.println("\n--- [통계 서브메뉴] ---");
            System.out.println("1. 월 지출 통계 보기");
            System.out.println("2. 년도 별 지출 통계 보기");
            System.out.println("3. 카테고리별 모든 지출 통계 보기");
            System.out.println("4. 뒤로가기");
            System.out.print("숫자를 입력하세요: ");
            int num = sc.nextInt();
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

    static Category ca;

    public static void CateUI(Scanner sc, Member member) {
        if(ca == null) {
            ca = new Category();
        }


        boolean isCheck = false;

        while(!isCheck) {
            System.out.println("\n--- [통계 서브메뉴] ---");
            System.out.println("1. 카테고리 추가");
            System.out.println("2. 카테고리 삭제");
            System.out.println("3. 카테고리 수정");
            System.out.println("4. 카테고리 표시");
            System.out.print("숫자를 입력하세요: ");
            int num = sc.nextInt();
            switch (num) {
                case 1:
                    //월 지출 통계
//                    ca.insertCategory();
                    break;

                case 2:
                    //년도별 지출 통계

                    break;

                case 3:
                    //카테고리별 지출 통계

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

