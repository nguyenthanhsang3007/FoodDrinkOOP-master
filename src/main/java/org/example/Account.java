package org.example;

import java.util.Scanner;

public class Account {
    private String username;
    private String password;
    private String access;

    Scanner sc = new Scanner(System.in);

    public Account() {}

    public Account(String username, String password, String access) {
        this.username = username;
        this.password = password;
        this.access = access;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getAccesss() {
        return access;
    }

    public void setUsername(String username) {
        while (username.trim().isEmpty()) {
            System.out.println("Tài khoản không được để trống, nhâập lại: ");
            username = sc.nextLine();
        }
        this.username = username;
    }

    public void setPassword(String password) {
        while (password.trim().isEmpty()) {
            System.out.println("Mật khẩu không được để trống, nhập lại: ");
            username = sc.nextLine();
        }
        this.password = password;
    }

    public void setAccesss(String access) {
        while (!access.trim().equalsIgnoreCase("admin") && !access.trim().equalsIgnoreCase("cashier")) {
            System.out.println("Nhập quyền truy cập sai, nhập lại: ");
        }
        this.access = access;
    }

    public String toCVString() {
        return String.format("%s, %s, %s", username, password, access);
    }
}
