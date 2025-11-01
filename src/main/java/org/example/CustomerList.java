package org.example;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;

public class CustomerList implements funtion, ConnectDatabase {
    private Customer[] customerList;
    private boolean check = true;

    Scanner sc = new Scanner(System.in);

    public CustomerList() {
        customerList = new Customer[0];
    }

    public Customer[] getCustomerList() {
        return customerList;
    }

    public boolean getCheck() {
        return check;
    }

    public void setCheck(boolean check) {
        this.check = check;
    }

    public void output() {
        for (int i = 0; i < customerList.length; i++) {
            System.out.println(customerList[i].toString());
        }
    }

    public void readFromFile() {

        try {
            customerList = new Customer[0];

            FileReader readfile = new FileReader("data\\customer.txt");
            BufferedReader bufferedReader = new BufferedReader(readfile);
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                Customer newCl[] = Arrays.copyOf(customerList, customerList.length + 1);

                // Nếu dòng trong file trống thì bỏ qua
                if (line.equalsIgnoreCase("")) {
                    continue;
                }

                String[] parts = line.split(",");
                String id = parts[0].trim();
                String name = parts[1].trim();
                String phone = parts[2].trim();
                float score = Float.parseFloat(parts[3].trim());

                newCl[newCl.length - 1] = new Customer(id, name, phone, score);
                customerList = newCl;
            }
            bufferedReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void writeToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("data\\customer.txt"))) {
            for (int i = 0; i < customerList.length; i++) {
                writer.write(customerList[i].toCVCString());
                writer.newLine();
            }
            if (check)
                System.out.println("Thông tin của bạn đã được hệ thống ghi nhận!");

        } catch (IOException e) {
            System.err.println("Xuất hiện lời khi ghi file: customer.txt");
        }
    }

    public boolean isExistId(String id) {
        for (int i = 0; i < customerList.length; i++) {
            if (customerList[i].getIdCustomer().equals(id)) {
                return true;
            }
        }
        return false;
    }

    public void add(Object object) {
        if (object instanceof Customer) {
            Customer customer = (Customer) object;

            if (customerList.length == 0) {
                customer.setIdCustomer("CUS0");
            } else {
                String lastIdNumber = customerList[customerList.length - 1].getIdCustomer().replace("CUS", "");
                customer.setIdCustomer("CUS" + (Integer.parseInt(lastIdNumber) + 1));
            }

            // Cho điều kiện kiểm tra null vì
            // Ở chức năng quản lý khách hàng và chức năng khách hàng nhập khác nhau
            // Thêm vào cho thỏa điều kiện nhập nếu nhập exit thì ở cả 2 chức năng đều exit
            // được
            // Khắc phục lõi khi nhập exit ở bên cn khách hàng hiển thị menu
            if ((customer.getNameCustomer() == null && customer.getPhoneCustomer() == null)) {
                System.out.print("Nhập tên khách hàng: ");
                String name = sc.nextLine();

                while (name.trim().isEmpty()) {
                    System.out.print("Tên của khách hàng không được để trống, mời nhập lại: ");
                    name = sc.nextLine();
                }

                if (name.trim().equalsIgnoreCase("exit")) {
                    return;
                }

                customer.setNameCustomer(name);

                System.out.print("Nhập số điện thoại khách hàng: ");
                String phoneNumber = sc.nextLine();

                while (phoneNumber.trim().isEmpty() || !(phoneNumber.trim().startsWith("0") && phoneNumber.trim().length() == 10)) {
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

                customer.setPhoneCustomer(phoneNumber);
            }

            Customer newCl[] = Arrays.copyOf(customerList, customerList.length + 1);
            newCl[newCl.length - 1] = customer;
            customerList = newCl;
            writeToFile();
        } else {
            System.out.println("Invalid object type.");
        }
    }

    @Override
    public void remove() {
        System.out.print("Nhập id khách hàng cần xóa: ");
        String id = sc.nextLine();

        while (id.trim().equalsIgnoreCase("...")) {
            System.out.println("+ ------------------------------------------------------- +");
            System.out.println(String.format("| %-5s | %-20s | %11s | %10s |", "ID", "Họ tên", "SĐT", "Điểm"));
            System.out.println("+ ------------------------------------------------------- +");
            output();
            System.out.println("+ ------------------------------------------------------- +");
            System.out.print("Nhập id khách hàng cần xóa: ");
            id = sc.nextLine();
        }

        // Kiểm tra id nhập là hợp lệ
        while (!isExistId(id) && !id.equalsIgnoreCase("exit")) {
            System.out.print("Hệ thống không tìm thấy id, nhập lại: ");
            if (id.trim().isEmpty()) {
                System.out.print("Nhập id không được để trống, nhập lại: ");
            }
            id = sc.nextLine();
        }

        if (id.equalsIgnoreCase("exit")) {
            return;
        }

        Customer[] newCustomerList = new Customer[customerList.length - 1];
        int index = 0;
        for (int i = 0; i < customerList.length; i++) {
            if (!customerList[i].getIdCustomer().equals(id)) {
                newCustomerList[index] = customerList[i];
                index++;
            }
        }

        customerList = newCustomerList;
        writeToFile();
    }

    public void search() {
        System.out.print("Nhập thông tin cần tìm kiếm: ");
        String searchValue = sc.nextLine();

        while (!searchValue.trim().equalsIgnoreCase("exit")) {
            // Khởi tạo mảng chứa kết quả tìm kếm
            Customer[] result = new Customer[customerList.length];
            String[] idUnique = new String[customerList.length];
            int idx = 0, idxIdUnique = 0;

            while (searchValue.trim().equalsIgnoreCase("...")) {
                System.out.println("+ ------------------------------------------------------- +");
                System.out.println(String.format("| %-5s | %-20s | %11s | %10s |", "ID", "Họ tên", "SĐT", "Điểm"));
                System.out.println("+ ------------------------------------------------------- +");
                output();
                System.out.println("+ ------------------------------------------------------- +");
                System.out.print("Nhập thông tin cần tìm kiếm: ");
                searchValue = sc.nextLine();
            }

            while (searchValue.trim().isEmpty() && !searchValue.equalsIgnoreCase("exit")) {
                System.out.print("Thông tin cần tìm không được để trống, nhập lại: ");
                searchValue = sc.nextLine();
            }

            if (searchValue.equalsIgnoreCase("exit")) {
                return;
            }

            // Tìm kiếm theo id
            for (int i = 0; i < customerList.length; i++) {
                if (customerList[i].getIdCustomer().equals(searchValue)) {
                    result[idx++] = customerList[i];
                    idUnique[idxIdUnique++] = customerList[i].getIdCustomer();
                }
            }

            // Tìm kiếm theo tên
            for (int i = 0; i < customerList.length; i++) {
                if (customerList[i].getNameCustomer().toLowerCase().contains(searchValue.toLowerCase())) {
                    result[idx++] = customerList[i];
                    idUnique[idxIdUnique++] = customerList[i].getIdCustomer();
                }
            }

            // Tìm kiếm theo số điện thoại
            for (int i = 0; i < customerList.length; i++) {
                if (customerList[i].getPhoneCustomer().startsWith(searchValue)
                        && !isContainId(idUnique, customerList[i].getIdCustomer())) {
                    result[idx++] = customerList[i];
                }
            }

            if (idx == 0) {
                System.out.println("Hệ thống không tìm thấy thông tin!");
            } else {
                System.out.println("+ ------------------------------------------------------- +");
                System.out.println(String.format("| %-5s | %-20s | %11s | %10s |", "ID", "Họ tên", "SĐT", "Điểm"));
                System.out.println("+ ------------------------------------------------------- +");
                for (int i = 0; i < idx; i++) {
                    System.out.println(result[i].toString());
                }
                System.out.println("+ ------------------------------------------------------- +");
            }

            System.out.print("Nhập thông tin cần tìm kiếm: ");
            searchValue = sc.nextLine();
        }
    }

    public void edit() {
        System.out.println("+ ------------------------------- +");
        System.out.println("|    Sửa thông tin khách hàng     |");
        System.out.println("+ ------------------------------- +");
        System.out.println("| 1. Sửa tên                      |");
        System.out.println("| 2. Sửa số điện thoại            |");
        System.out.println("| 3. Sửa điểm tích lũy            |");
        System.out.println("| 0. Quay lại                     |");
        System.out.println("+ ------------------------------- +");
        System.out.print("Lựa chọn của bạn: ");
        String choose = sc.nextLine();
        choose = choose.trim();

        while (!choose.trim().equalsIgnoreCase("0")) {
            while (!isNumber(choose) || !(Integer.parseInt(choose) >= 0 && Integer.parseInt(choose) <= 3)) {
                if (choose.trim().isEmpty()) {
                    System.out.print("Lựa chọn không được để trống, nhập lại: ");
                } else {
                    System.out.print("Lựa chọn không hợp lệ, nhập lại: ");
                }
                choose = sc.nextLine();
            }

            System.out.println("Nhập `exit` để hủy bỏ, `...` để xem danh sách");

            if (choose.equalsIgnoreCase("1") || choose.equalsIgnoreCase("2") || choose.equalsIgnoreCase("3")
                    || choose.equalsIgnoreCase("4")) {
                System.out.print("Nhập id khách hàng bạn cần thay đổi: ");
                String id = sc.nextLine();

                if (id.trim().equalsIgnoreCase("exit")) {
                    break;
                }

                while (id.trim().equalsIgnoreCase("...")) {
                    System.out.println("+ ------------------------------------------------------- +");
                    System.out.println(String.format("| %-5s | %-20s | %11s | %10s |", "ID", "Họ tên", "SĐT", "Điểm"));
                    System.out.println("+ ------------------------------------------------------- +");
                    output();
                    System.out.println("+ ------------------------------------------------------- +");
                    System.out.print("Nhập id khách hàng bạn cần thay đổi: ");
                    id = sc.nextLine();
                }

                if (id.trim().equalsIgnoreCase("exit")) {
                    break;
                }

                while (id.trim().isEmpty()) {
                    System.out.print("Id nhập vào không được để trống, nhập lại: ");
                    id = sc.nextLine();
                }

                boolean isUpdate = false;
                for (int i = 0; i < customerList.length; i++) {
                    if (customerList[i].getIdCustomer().equals(id)) {
                        switch (Integer.parseInt(choose)) {
                            case 1:
                                System.out.print("Nhập tên mới: ");
                                String name = sc.nextLine();
                                while (name.trim().isEmpty()) {
                                    System.out.print("Tên mới không được để trống, nhập lại: ");
                                    name = sc.nextLine();
                                }
                                customerList[i].setNameCustomer(name);
                                isUpdate = true;
                                break;
                            case 2:
                                System.out.println("Nhập số điện thoại mới: ");
                                String phoneNumber = sc.nextLine();
                                while (phoneNumber.trim().isEmpty()) {
                                    System.out.print("Số điện thoại mới không được để trống, nhập lại: ");
                                    phoneNumber = sc.nextLine();
                                }
                                customerList[i].setPhoneCustomer(phoneNumber);
                                isUpdate = true;
                                break;
                            case 3:
                                System.out.print("Nhập điểm tích lũy mới: ");
                                String score = sc.nextLine();
                                while (!isNumber(score) && score.trim().isEmpty()) {
                                    if (score.trim().isEmpty()) {
                                        System.out.print("Điểm tích lũy mới không được để trống, nhập lại: ");
                                    } else {
                                        System.out.print("Điểm tích lũy mới không hợp lệ, nhập lại: ");
                                    }
                                    score = sc.nextLine();
                                }
                                customerList[i].setScore(Integer.parseInt(score));
                                isUpdate = true;
                                break;
                            default:
                                break;
                        }
                    }
                }

                if (!isUpdate) {
                    System.out.println("Hệ thông không tìm thấy thông tin!");
                } else {
                    writeToFile();
                }
            }

        }
    }

    public boolean isNumber(String s) {
        if (s == null)
            return false;

        try {
            int x = Integer.parseInt(s);
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }

    public boolean isContainId(String[] idUnique, String id) {
        for (String existingId : idUnique) {
            if (id.equals(existingId)) {
                return true;
            }
        }
        return false;
    }
}
