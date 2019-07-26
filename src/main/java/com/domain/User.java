package com.domain;


import org.springframework.stereotype.Repository;

@Repository
public class User {
    private Integer uid;
    private String username;
    private String password;
    private String loginCode;

//    @Override
//    public String toString() {
//        return "User{" +
//                "uid=" + uid +
//                ", username='" + username + '\'' +
//                ", password='" + password + '\'' +
//                ", loginCode='" + loginCode + '\'' +
//                '}';
//    }

    public String getLoginCode() {
        return loginCode;
    }

    public void setLoginCode(String loginCode) {
        this.loginCode = loginCode;
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
