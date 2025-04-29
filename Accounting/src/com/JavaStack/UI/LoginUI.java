package com.JavaStack.UI;

import java.util.Scanner;

import com.JavaStack.model.Member;
import com.JavaStack.service.MemberService;


public class LoginUI {
    private Scanner scanner;
    private MemberService memberService;
    private Member loggedInMember;

    public LoginUI() {
        scanner = new Scanner(System.in);
        memberService = new MemberService();
        loggedInMember = null;
    }


    public void showLoginScreen() {
        System.out.println("===== 가계부 프로그램 로그인 =====");
        System.out.println("이메일과 비밀번호를 입력하세요.");

        System.out.print("이메일: ");
        String email = scanner.nextLine();

        System.out.print("비밀번호: ");
        String password = scanner.nextLine();


        loggedInMember = memberService.login(email, password);

        if (loggedInMember != null) {
            System.out.println("로그인 성공: " + loggedInMember.getMemberName() + "님 환영합니다!");
            // 여기에 로그인 성공 후 메인 메뉴로 이동하는 코드 추가
        } else {
            System.out.println("로그인 실패: 이메일 또는 비밀번호가 일치하지 않습니다.");
        }
    }


    public boolean isLoggedIn() {
        return loggedInMember != null;
    }


    public Member getLoggedInMember() {
        return loggedInMember;
    }

    public void close() {
        if (scanner != null) {
            scanner.close();
        }
    }
}