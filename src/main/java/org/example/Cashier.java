package org.example;

import java.util.Scanner;

public class Cashier extends Employee {
    public Cashier() {
    }
    public Cashier(String name, String id, int hoursWorked) {
        super(name, id, hoursWorked);
    }

    @Override
    public double Salary_Cal() {
        return 2.5 * getHoursWorked() * 10000;
    }

    public void inputEmployeeInfo() {
        Scanner scanner = new Scanner(System.in);
//        System.out.print("Nhập ID của nhân viên: ");
//        setId(scanner.nextLine());
        System.out.print("Nhập tên của nhân viên: ");
        setName(scanner.nextLine());
    }

    @Override
    public String getEmployeeInfo() {
        return super.getEmployeeInfo();
    }

}

