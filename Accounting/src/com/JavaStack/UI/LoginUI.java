package com.JavaStack.UI;

import java.util.Scanner;

import com.JavaStack.model.Member;
import com.JavaStack.service.MemberService;


public class LoginUI {
    public Scanner scanner;
    private MemberService memberService;
    private Member loggedInMember;

    public LoginUI() {
        scanner = new Scanner(System.in);
        memberService = new MemberService();
        loggedInMember = null;
    }


    public boolean showLoginScreen() {
        int maxAttempts = 3;
        int attempts = 0;

        while (attempts < maxAttempts) {
            System.out.println("\n===== 가계부 프로그램 로그인 =====");
            System.out.println("이메일과 비밀번호를 입력하세요. (남은 시도: " + (maxAttempts - attempts) + ")");

            System.out.print("이메일: ");
            String email = scanner.nextLine();

            System.out.print("비밀번호: ");
            String password = scanner.nextLine();



            loggedInMember = memberService.login(email, password);

            if (loggedInMember != null) {
                System.out.println("로그인 성공: " + loggedInMember.getMemberName() + "님 환영합니다!");
                //asdf
                return true;
            } else {
                System.out.println("로그인 실패: 이메일 또는 비밀번호가 일치하지 않습니다.");
                attempts++;
            }
        }

        System.out.println("\n최대 로그인 시도 횟수를 초과했습니다. 프로그램을 종료합니다.");
        return false;
    }

    public boolean isLoggedIn() {
        return loggedInMember != null;
    }


    public Member getLoggedInMember() {
        return loggedInMember;
    }

    public int mainMenu(Scanner s) {
        System.out.println("=======================");
        System.out.println("가계부");
        System.out.println("=======================");
        System.out.println("1. 지출/수입");
        System.out.println("2. 카테고리");
        System.out.println("3. 통계");
        System.out.println("4. 종료");
        System.out.println("=======================");
        System.out.print("메뉴 선택:");
        int m = s.nextInt();
        return m;
    }


    public void close() {
        if (scanner != null) {
            scanner.close();
        }
    }
}