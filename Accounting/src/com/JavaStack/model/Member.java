package com.JavaStack.model;


public class Member {
    private int memberId;
    private String memberName;
    private String email;
    private String password;

    public Member() {}

    public Member(int memberId, String memberName, String email, String password) {
        this.memberId = memberId;
        this.memberName = memberName;
        this.email = email;
        this.password = password;
    }

    public Member(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public int getMemberId() {
        return memberId;
    }

    public void setMemberId(int memberId) {
        this.memberId = memberId;
    }

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "Member [memberId=" + memberId + ", memberName=" + memberName + ", email=" + email + "]";
    }
}