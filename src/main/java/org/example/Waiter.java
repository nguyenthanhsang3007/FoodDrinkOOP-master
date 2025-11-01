package org.example;

import java.util.Scanner;

public class Waiter extends Employee {

    public Waiter() {
    }

    public Waiter(String name, String id, int hoursWorked) {
        super(name, id, hoursWorked);
    }

    @Override
    public double Salary_Cal() {
        return 2 * getHoursWorked() *10000;
    }

    @Override
    public void inputEmployeeInfo() {
        Scanner scanner = new Scanner(System.in);
//        System.out.print("Nhập ID của nhân viên: ");
//        setId(scanner.nextLine());
        System.out.print("Nhập tên của nhân viên: ");
        setName(scanner.nextLine());
    }

    public void generateId() {
    }

    @Override
    public String getEmployeeInfo() {
        return super.getEmployeeInfo() ;
    }


}
