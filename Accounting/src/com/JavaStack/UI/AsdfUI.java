package com.JavaStack.UI;

import java.util.Scanner;

public class AsdfUI {

    public void recordUI(Scanner sc) {
        boolean inSubMenu = true;
        while (inSubMenu) {
            System.out.println("---서브메뉴---");
            System.out.println("1.");
            System.out.println("2.");
            System.out.println("3.");
            System.out.println("4.");
            System.out.println("5. 메인 메뉴로 돌아가기");
            System.out.print("번호를 입력하세요: ");

            int subChoice = sc.nextInt();

            if (subChoice == 5) {
                inSubMenu = false; // 메인 메뉴로 돌아가기
            } else {
                System.out.println("서브 메뉴 " + subChoice + " 선택됨.");
            }
        }
    }
}
