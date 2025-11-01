package org.example;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class EmployeeList implements funtion {
    private static final String FILE_PATH = "data\\"+"employee.txt";
    public static final int MAX_EMPLOYEES = 100;
    private static Employee[] employees = new Employee[MAX_EMPLOYEES];
    private static int size = 0;

    public int getSize() {
        return size;
    }

    public Employee[] getEmployees() {
        return employees;
    }

    public int getMaxEmployees() {
        return MAX_EMPLOYEES;
    }

    @Override
    public void edit() {

    }

    @Override
    public void search() {

    }


    public void displayEmployeeList() {
        if (size == 0) {
            readFromFile();
        }
        System.out.println("Danh sách nhân viên :");
        System.out.println("|----------------------------------------------------------|");
        for (int i = 0; i < size; i++) {
            System.out.println("|---------------------------------------------------------|");
            System.out.println(employees[i].displayEmployeeInfo());
            System.out.println("|---------------------------------------------------------|");

        }
    }


    @Override
    public void add(Object obj) {
        if (size == 0) {
            readFromFile();
        }
        if (size < MAX_EMPLOYEES) {
            Scanner scanner = new Scanner(System.in);
            System.out.print("Chọn chức vụ (Waiter/Cashier): ");
            String position = scanner.nextLine();
            if ("Waiter".equalsIgnoreCase(position)) {
                String newIdWaiter = generateWaiterId();
                Waiter newWaiter = new Waiter();
                employees[size++] = newWaiter;
                newWaiter.setPosition("Waiter");
                newWaiter.setId(newIdWaiter);

                newWaiter.inputEmployeeInfo();
            } else if ("Cashier".equalsIgnoreCase(position)) {
                Cashier newCashier = new Cashier();
                String newIdCashier = generateCashierId();
                newCashier.setPosition("Cashier");
                employees[size++] = newCashier;
                newCashier.setId(newIdCashier);
                newCashier.inputEmployeeInfo();
            } else {
                System.out.println("Chức vụ không hợp lệ.");
                return;
            }
            saveToFile();
            System.out.println("Nhân viên mới đã được thêm.");

        } else {
            System.out.println("Danh sách nhân viên đã đầy. Không thể thêm.");

        }
    }


    public String  generateWaiterId() {

        int maxWaiterId = 0;
        // Tìm Waiter có ID lớn nhất
        for (int i = 0; i < size; i++) {
            if (employees[i] instanceof Waiter) {
                String waiterId = employees[i].getId();
                int currentId = Integer.parseInt(waiterId.substring(3));
                if (currentId > maxWaiterId) {
                    maxWaiterId = currentId;
                }
            }
        }
        return "WAI" + (maxWaiterId + 1);

    }

    public String  generateCashierId() {
        int maxCashierId = 0;

        // Tìm Cashier có ID lớn nhất
        for (int i = 0; i < size; i++) {
            if (employees[i] instanceof Cashier) {
                String cashierId = employees[i].getId();
                int currentId = Integer.parseInt(cashierId.substring(3));
                if (currentId > maxCashierId) {
                    maxCashierId = currentId;
                }
            }
        }

        return   "CAS" + (maxCashierId + 1);

    }

    @Override
    public void remove() {
        if (size == 0) {
            readFromFile();
        }
        Scanner scanner = new Scanner(System.in);
        System.out.println("-----------------------------------------------");
        System.out.print("Nhập ID của nhân viên cần xóa: ");
        String deleteId = scanner.nextLine();

        int indexToDelete = -1;
        for (int i = 0; i < size; i++) {
            if (employees[i].getId().equals(deleteId)) {
                indexToDelete = i;
                break;
            }
        }

        if (indexToDelete != -1) {

            for (int i = indexToDelete; i < size - 1; i++) {
                employees[i] = employees[i + 1];
            }
            size--;

            System.out.println("Nhân viên có ID " + deleteId + " đã được xóa.");

            saveToFile();
        } else {
            System.out.println("Không tìm thấy nhân viên có ID " + deleteId);

        }
    }



    public void displaySalary() {
        if (size == 0) {
            readFromFile();
        }
        Scanner scanner = new Scanner(System.in);
        System.out.print("Nhập ID của nhân viên cần xem lương: ");
        String employeeId = scanner.nextLine();

        for (int i = 0; i < size; i++) {
            if (employees[i].getId().equals(employeeId)) {
                System.out.println("|---------------------------------------------------------|");
                System.out.println("   Lương của nhân viên có ID: " + employeeId  + " Là : " + employees[i].Salary_Cal() +" VNĐ");
                System.out.println("|---------------------------------------------------------|");

                return;
            }
        }

        System.out.println("Không tìm thấy nhân viên có ID " + employeeId);

    }

    public void searchEmployeeID() {

        if(size == 0) {
            readFromFile();
        }
        Scanner scanner = new Scanner(System.in);
        System.out.println(("Nhập ID cần tìm kiếm: "));
        String  employeeID = scanner.nextLine();
        boolean check = false;
        for (int i = 0; i < size; i++) {
            if(employees[i].getId().equals(employeeID)) {
                System.out.println("|---------------------------------------------------------|");
                System.out.println(employees[i].displayEmployeeInfo());
                System.out.println("|---------------------------------------------------------|");
                check = true;
            }
        }
        if (!check) {
            System.out.println("Không tìm thấy ID");
        }

    }

    public void searchEmployeeName() {
    if (size == 0) {
        readFromFile();
    }

    Scanner sc = new Scanner(System.in);
    System.out.print("Nhập tên cần tìm kiếm: ");
    String employeeName = sc.nextLine().trim().toLowerCase();

    Employee[] result = new Employee[size];
    int count = 0;

    for (int i = 0; i < size; i++) {
        String name = employees[i].getName().trim().toLowerCase();
        if (name.contains(employeeName)) {
            result[count++] = employees[i];
        }
    }

    if (count == 0) {
        System.out.println("Không tìm thấy nhân viên nào có tên chứa \"" + employeeName + "\"");
    } else {
        System.out.println("\nKết quả tìm thấy (" + count + "):");
        System.out.println("|---------------------------------------------------------|");
        for (int i = 0; i < count; i++) {
            System.out.println(result[i].displayEmployeeInfo());
            System.out.println("|---------------------------------------------------------|");
        }
    }
}


    public void menuSearch(){
        if(size == 0) {
            readFromFile();
        }
        System.out.println("+ ---------------------------------+");
        System.out.println("|         Tìm kiếm nhân viên       |");
        System.out.println("+ ---------------------------------+");
        System.out.println("| 1. Tìm kiếm theo ID              |");
        System.out.println("| 2. Tìm kiếm tên                  |");
        System.out.println("| 0. Quay lại                      |");
        System.out.println("+ -------------------------------- +");
        System.out.print("Nhập lựa chọn của bạn :   ");
        Scanner sc = new Scanner(System.in);
        int select ;
        String temp;
        temp = sc.nextLine();
        while (!isNumber(temp)) {
            System.out.print("Lựa chọn không hợp lệ, nhập lại: ");
            temp = sc.nextLine();
        }
        select = Integer.parseInt(temp);
        while(select != 0){
            switch (select) {
                case 1:
                    searchEmployeeID();
                    break;
                case 2:
                    searchEmployeeName();
                    break;
                default:
                    System.out.println("Không có lựa chọn này.");
                    break;
            }
            System.out.println("+ ---------------------------------+");
            System.out.println("|         Tìm kiếm nhân viên       |");
            System.out.println("+ ---------------------------------+");
            System.out.println("| 1. Tìm kiếm theo ID              |");
            System.out.println("| 2. Tìm kiếm tên                  |");
            System.out.println("| 0. Quay lại                      |");
            System.out.println("+ -------------------------------- +");
            System.out.print("Nhập lựa chọn : ");
            temp = sc.nextLine();
            while (!isNumber(temp)) {
                System.out.print("Lựa chọn không hợp lệ, nhập lại: ");
                temp = sc.nextLine();
            }
            select = Integer.parseInt(temp);
        }

    }

    private boolean isNumber(String s){
        if(s == null) return false;

        try{
            int x = Integer.parseInt(s);
        }catch(NumberFormatException e){
            return false;
        }
        return true;
    }

    public void dsPhucvu(){
        if (size == 0) {
            readFromFile();
        }
        System.out.println(" +--------------------------------+");
        System.out.println(" |   Danh sách nhân viên phục vụ  |");
        System.out.println(" +--------------------------------+");
        System.out.println(" +--------------------------------------------------------------------------------------------------------+");
        System.out.printf(" | %-12s | %-25s | %-10s | %15s | %15s %14s \n", "Mã nhân viên", "Họ và tên", "Chức vụ", "Giờ làm việc", "Tổng lương", "|");
        System.out.println(" +--------------------------------------------------------------------------------------------------------+");
        for (int i = 0; i < size; i++) {
            if (employees[i].getPosition().equalsIgnoreCase("Waiter")) {
                System.out.printf(" | %-12s | %-25s | %-10s | %15s | %15.2f %3s %10s \n", employees[i].getId(), employees[i].getName(), employees[i].getPosition(), employees[i].getHoursWorked(), employees[i].Salary_Cal(), "VNĐ", "|");
                System.out.println(" +--------------------------------------------------------------------------------------------------------+");
            }
        }
    }

    public void dsThuNgan(){
        if (size == 0) {
            readFromFile();
        }
         System.out.println(" +--------------------------------+");
        System.out.println(" |  Danh sách nhân viên thu ngân  |");
        System.out.println(" +--------------------------------+");
        System.out.println(" +--------------------------------------------------------------------------------------------------------+");
        System.out.printf(" | %-12s | %-25s | %-10s | %15s | %15s %14s \n", "Mã nhân viên", "Họ và tên", "Chức vụ", "Giờ làm việc", "Tổng lương", "|");
        System.out.println(" +--------------------------------------------------------------------------------------------------------+");
            for (int i = 0; i < size; i++) {
                if(employees[i].getPosition().equalsIgnoreCase("Cashier")) {
                    System.out.printf(" | %-12s | %-25s | %-10s | %15s | %15.2f %3s %10s \n", employees[i].getId(), employees[i].getName(), employees[i].getPosition(), employees[i].getHoursWorked(), employees[i].Salary_Cal(), "VNĐ", "|");
                    System.out.println(" +--------------------------------------------------------------------------------------------------------+");
                }
            }
    }

    public void luongMax(){
        if (size == 0) {
            readFromFile();
        }
                    // Tìm nhân viên có lương cao nhất
            double max = 0;
            for (int i = 0; i < size; i++) {
                if (employees[i].Salary_Cal() > max) {
                    max = employees[i].Salary_Cal();
                }
            }
            for (int i = 0; i < size; i++) {
                if (employees[i].Salary_Cal() == max) {
                    System.out.println(" +--------------------------------+");
                    System.out.println(" |   Nhân viên có lương cao nhất  |");
                    System.out.println(" +--------------------------------+");
                    System.out.println(" +--------------------------------------------------------------------------------------------------------+");
                    System.out.printf(" | %-12s | %-25s | %-10s | %15s | %15s %14s \n", "Mã nhân viên", "Họ và tên", "Chức vụ", "Giờ làm việc", "Tổng lương", "|");
                    System.out.println(" +--------------------------------------------------------------------------------------------------------+");
                    System.out.printf(" | %-12s | %-25s | %-10s | %15s | %15.2f %3s %10s \n", employees[i].getId(), employees[i].getName(), employees[i].getPosition(), employees[i].getHoursWorked(), employees[i].Salary_Cal(), "VNĐ", "|");
                    System.out.println(" +--------------------------------------------------------------------------------------------------------+");

                }
            }
    }

    public void luongMin(){
        if (size == 0) {
            readFromFile();
        }
    // Tìm nhân viên có lương thấp nhất
        double min = Double.MAX_VALUE;
        for (int i = 0; i < size; i++) {
            if (employees[i].Salary_Cal() <= min) {
                min = employees[i].Salary_Cal();
            }
        }
        for (int i = 0; i < size; i++) {
            if (employees[i].Salary_Cal() == min) {
                System.out.println(" +--------------------------------+");
                System.out.println(" |   Nhân viên có lương thấp nhất |");
                System.out.println(" +--------------------------------+");
                System.out.println(" +--------------------------------------------------------------------------------------------------------+");
                System.out.printf(" | %-12s | %-25s | %-10s | %15s | %15s %14s \n", "Mã nhân viên", "Họ và tên", "Chức vụ", "Giờ làm việc", "Tổng lương", "|");
                System.out.println(" +--------------------------------------------------------------------------------------------------------+");
                System.out.printf(" | %-12s | %-25s | %-10s | %15s | %15.2f %3s %10s \n", employees[i].getId(), employees[i].getName(), employees[i].getPosition(), employees[i].getHoursWorked(), employees[i].Salary_Cal(), "VNĐ", "|");
                System.out.println(" +--------------------------------------------------------------------------------------------------------+");
            }
        }
    }
    
    public void timeWorkMax(){
        if (size == 0) {
            readFromFile();
        }
        // Nhân viên có giờ làm việc cao nhất
        double  maxHoursWorked = 0;
        for (int i = 0; i < size; i++) {
            if (employees[i].getHoursWorked() > maxHoursWorked) {
                maxHoursWorked = employees[i].getHoursWorked();
            }
        }
        for (int i = 0; i < size; i++) {
            if (employees[i].getHoursWorked() == maxHoursWorked) {
                System.out.println(" +-------------------------------------------+");
                System.out.println(" |   Nhân viên có giờ làm việc cao nhất      |");
                System.out.println(" +-------------------------------------------+");
                System.out.println(" +--------------------------------------------------------------------------------------------------------+");
                System.out.printf(" | %-12s | %-25s | %-10s | %15s | %15s %14s \n", "Mã nhân viên", "Họ và tên", "Chức vụ", "Giờ làm việc", "Tổng lương", "|");
                System.out.println(" +--------------------------------------------------------------------------------------------------------+");
                System.out.printf(" | %-12s | %-25s | %-10s | %15s | %15.2f %3s %10s \n", employees[i].getId(), employees[i].getName(), employees[i].getPosition(), employees[i].getHoursWorked(), employees[i].Salary_Cal(), "VNĐ", "|");
                System.out.println(" +--------------------------------------------------------------------------------------------------------+");
            }
        }
    }
    
    public void timeWorkMin(){
        if (size == 0) {
            readFromFile();
        }
        // Nhân viên có giờ làm việc ít nhất
        System.out.println("\n\n\n");
        double  minHoursWorked = Double.MAX_VALUE;
        for (int i = 0; i < size; i++) {
            if (employees[i].getHoursWorked() <= minHoursWorked) {
                minHoursWorked = employees[i].getHoursWorked();
            }
        }
        for (int i = 0; i < size; i++) {
            if (employees[i].getHoursWorked() == minHoursWorked) {
                System.out.println(" +-------------------------------------------+");
                System.out.println(" |   Nhân viên có giờ làm việc thấp nhất |");
                System.out.println(" +-------------------------------------------+");
                System.out.println(" +--------------------------------------------------------------------------------------------------------+");
                System.out.printf(" | %-12s | %-25s | %-10s | %15s | %15s %14s \n", "Mã nhân viên", "Họ và tên", "Chức vụ", "Giờ làm việc", "Tổng lương", "|");
                System.out.println(" +--------------------------------------------------------------------------------------------------------+");
                System.out.printf(" | %-12s | %-25s | %-10s | %15s | %15.2f %3s %10s \n", employees[i].getId(), employees[i].getName(), employees[i].getPosition(), employees[i].getHoursWorked(), employees[i].Salary_Cal(), "VNĐ", "|");
                System.out.println(" +--------------------------------------------------------------------------------------------------------+");
            }
        }

    }
    public void aggregate() {
        dsPhucvu();
        dsThuNgan();
        luongMax();
        luongMin();
        timeWorkMax();
        timeWorkMin();
    }





//    public void saveToFile() {
//        try (PrintWriter writer = new PrintWriter(new FileWriter(FILE_PATH, false))) {
//            for (int i = 0; i < size; i++) {
//                writer.println(employees[i].getEmployeeInfo());
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }


    public void saveToFile() {
        try (FileWriter writer = new FileWriter(FILE_PATH, false)) {
            for (int i = 0; i < size; i++) {
                writer.write(employees[i].getEmployeeInfo() +  System.lineSeparator());
//                writer.write(employees[i].getEmployeeInfo());
//                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



//    public void readFromFile() {
//        try (Scanner fileScanner = new Scanner(new File(FILE_PATH))) {
//
//            while (fileScanner.hasNextLine()) {
//                String line = fileScanner.nextLine();
//                String[] parts = line.split(", ");
//                if (parts.length >= 4) {
//                    String id = parts[0];
//                    String name = parts[1];
//                    String position = parts[2];
//                    int hoursWorked = Integer.parseInt(parts[3]);
//                    if ("Waiter".equalsIgnoreCase(position)) {
//
//                        Waiter newWaiter = new Waiter(name, id, hoursWorked);
//                        newWaiter.setPosition(position);
//                        employees[size++] = newWaiter;
//                    } else {
//
//                        Cashier newCashier = new Cashier(name, id, hoursWorked);
//                        newCashier.setPosition(position);
//                        employees[size++] = newCashier;
//                    }
//                }
//            }
//        } catch (FileNotFoundException e) {
//            System.out.println("Không tìm thấy file: " + FILE_PATH);
//        }
//    }

    public void readFromFile() {
        
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            employees = new Employee[0];
            employees = new Employee[MAX_EMPLOYEES];
            size = 0;
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(", ");
                if (parts.length >= 4) {
                    String id = parts[0];
                    String name = parts[1];
                    String position = parts[2];
                    int hoursWorked = Integer.parseInt(parts[3]);

                    if ("Waiter".equalsIgnoreCase(position)) {
                        Waiter newWaiter = new Waiter(name, id, hoursWorked);
                        newWaiter.setPosition(position);
                        employees[size++] = newWaiter;
                    } else {
                        Cashier newCashier = new Cashier(name, id, hoursWorked);
                        newCashier.setPosition(position);
                        employees[size++] = newCashier;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace(); // Xử lý ngoại lệ nếu có lỗi đọc file
        }
    }
}
