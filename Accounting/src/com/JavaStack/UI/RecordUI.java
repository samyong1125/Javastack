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
import com.JavaStack.service.RecordService;
import com.JavaStack.model.Member;

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

	
}

