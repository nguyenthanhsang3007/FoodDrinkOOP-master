package org.example;

import java.util.Scanner;

public abstract class Employee {
    private String name;
    private String id;
    private int hoursWorked;
    private String position;

    public Employee() {
    }

    public Employee(String name, String id, int hoursWorked) {
        this.name = name;
        this.id = id;
        this.hoursWorked = hoursWorked;
    }
    public String getName() {
        return name;
    }
    public String getId() {
        return id;
    }

    public int getHoursWorked() {
        return hoursWorked;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setHoursWorked(int hoursWorked) {
        this.hoursWorked = hoursWorked;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public abstract double Salary_Cal () ;
    public abstract void inputEmployeeInfo() ;
   /* public String getEmployeeInfo() {
        String formattedString = String.format("%10s, %-1s, %-1s, %-1s", id, name, position, hoursWorked);
        return formattedString;
    }*/

    public String getEmployeeInfo() {
        return  id + ", " + name + ", " + position + ", " + hoursWorked;
    }

    public String displayEmployeeInfo() {
        return String.format("| %-5s | %-20s | %11s | %10d |", id, name, position, hoursWorked);
    }


}


