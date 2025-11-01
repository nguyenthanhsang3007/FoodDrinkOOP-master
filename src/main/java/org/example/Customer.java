package org.example;

import java.util.Scanner;

public class Customer {
    private String idCustomer;
    private String nameCustomer;
    private String phoneCustomer;
    private float score = 0;
    private static int count = 0;
    Scanner sc = new Scanner(System.in);

    public Customer() {
    }

    public Customer(String idCustomer, String nameCustomer, String phoneCustomer, float score) {
        this.idCustomer = idCustomer;
        this.nameCustomer = nameCustomer;
        this.phoneCustomer = phoneCustomer;
        this.score = score;
    }

    public static int getCount() {
        return count;
    }

    public static void setCount(int count) {
        Customer.count = count;
    }

    public String getNameCustomer() {
        return nameCustomer;
    }

    public String getIdCustomer() {
        return idCustomer;
    }

    public String getPhoneCustomer() {
        return phoneCustomer;
    }

    public float getScore() {
        return score;
    }

    public void setIdCustomer(String idCustomer) {
        this.idCustomer = idCustomer;
    }

    public void setNameCustomer(String nameCustomer) {
        while (nameCustomer.isEmpty()) {
            System.out.print("Tên của khách hàng không được để trống, mời nhập lại: ");
            nameCustomer = sc.nextLine();
        }
        this.nameCustomer = nameCustomer;
    }

    public void setPhoneCustomer(String phoneCustomer) {
    while (phoneCustomer.isEmpty() || !phoneCustomer.matches("\\d{10}")) {
        System.out.print("Số điện thoại không hợp lệ (phải gồm 10 chữ số). Nhập lại: ");
        phoneCustomer = sc.nextLine();
    }
    this.phoneCustomer = phoneCustomer;
}


    public void setScore(float d) {
        this.score = d;
    }

    public String toCVCString() {
        return String.format("%s, %s, %s, %f", idCustomer, nameCustomer, phoneCustomer, score);
    }

    public String toString() {
        return String.format("| %-5s | %-20s | %11s | %10.1f |", idCustomer, nameCustomer, phoneCustomer, score);
    }

    public void input() {
        System.out.print("Nhập tên khách hàng: ");
        String name = sc.nextLine();

        while (name.trim().isEmpty()) {
            System.out.print("Tên của khách hàng không được để trống, mời nhập lại: ");
            name = sc.nextLine();
        }

        if (name.trim().equalsIgnoreCase("exit")) {
            setNameCustomer("exit");
            setPhoneCustomer("exit");
            return;
        }

        setNameCustomer(name);

        System.out.print("Nhập số điện thoại khách hàng: ");
        String phoneNumber = sc.nextLine();

        while (phoneNumber.trim().isEmpty()
                || !(phoneNumber.trim().startsWith("0") && phoneNumber.trim().length() == 10)) {
            if (!(phoneNumber.trim().startsWith("0") && phoneNumber.trim().length() == 10)) {
                System.out.println("Sđt của khác hàng phải bắt đầu bằng số 0 và tổng là 10 số");
                System.out.print("Mời nhập lại (vd: 0966966966): ");
            } else {
                System.out.print("Sđt của khách hàng không được để trống, mời nhập lại: ");
            }
            phoneNumber = sc.nextLine();
        }

        if (phoneNumber.trim().equalsIgnoreCase("exit")) {
            return;
        }

        setPhoneCustomer(phoneNumber);
    }
}