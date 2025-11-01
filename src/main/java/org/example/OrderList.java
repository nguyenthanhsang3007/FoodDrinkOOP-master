package org.example;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;

public class OrderList implements funtion, ConnectDatabase {
    private Order[] orderList;

    Scanner sc = new Scanner(System.in);

    public OrderList() {
        orderList = new Order[0];
    }

    public Order[] getOrderList() {
        return orderList;
    }

    public void setOrderList(Order[] orderList) {
        this.orderList = orderList;
    }

    public void output() {
        for (int i = 0; i < orderList.length; i++) {
            System.out.println(orderList[i].toCVString());
        }
    }

    public void readFromFile() {
        try {
            orderList = new Order[0];

            File myObj = new File("data\\order.txt");
            Scanner myReader = new Scanner(myObj);

            while (myReader.hasNextLine()) {
                Order newOl[] = Arrays.copyOf(orderList, orderList.length + 1);

                String data = myReader.nextLine();
                // Nếu dòng trong file trống thì bỏ qua
                if (data.equalsIgnoreCase("")) {
                    continue;
                }

                String[] parts = data.split("#");
                String[] orderInfo;

                if (parts[3].trim().contains("@")) {
                    orderInfo = parts[3].trim().split("@");
                } else {
                    orderInfo = new String[] { parts[3].trim() };
                }

                newOl[newOl.length - 1] = new Order(parts[0].trim(), parts[1].trim(), parts[2].trim(), orderInfo);
                orderList = newOl;
            }

            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public void writeToFile() {
        try {
            FileWriter myWriter = new FileWriter("data\\order.txt");

            for (int i = 0; i < orderList.length; i++) {
                myWriter.write(orderList[i].toCVString());
                myWriter.write("\n");
            }

            myWriter.close();
            System.out.println("Bạn đã order thành công, dữ liệu của bạn đang xử lý!");
        } catch (IOException e) {
            System.out.println("Xin lỗi, hiện tại dịch vụ của chúng tôi bị lỗi, không thể order sản phẩm!");
            e.printStackTrace();
        }
    }

    public boolean isExistIdWaiter(String idWaiter, String[] uniqueIdWaiter) {
        for (int i = 0; i < orderList.length; i++) {
            if (idWaiter.equals(uniqueIdWaiter[i])) {
                return true;
            }
        }
        return false;
    }

    public void statisticsWaiter() {
        readFromFile();

        if (orderList.length <= 0) {
            System.out.println("Hệ thống không có dữ liệu về đơn hàng!");
            return;
        }

        System.out.println("Số đơn phục vụ: " + orderList.length);

        String[] uniqueIdWaiter = new String[orderList.length];
        int idx = 0;

        EmployeeList el = new EmployeeList();
        if (el.getSize() == 0)
            el.readFromFile();

        if (el.getSize() <= 0) {
            System.out.println("Hệ thống không có dữ liệu về nhân viên phục vụ!");
            return;
        }

        System.out.println("+ ---------------------------------- +");
        System.out.println(String.format("| %-5s | %-20s | %3s |", "Id", "Tên nhân viên", "SL"));
        System.out.println("+ ---------------------------------- +");

        for (int i = 0; i < orderList.length; i++) {
            // Nếu chưa tồn tại id Waiter trong mảng uniqueIdWaiter thì mới lọc
            String id = orderList[i].getIdWaiter().trim();
            if (!isExistIdWaiter(id, uniqueIdWaiter)) {
                uniqueIdWaiter[idx++] = id; // Thêm id vào mảng, để khi i++ nếu trùng sẽ không đếm tiếp id đó
                int countJob = 0; // Đếm xem nhân viên waiter đã phục vụ được bao nhiêu đơn
                for (int j = 0; j < orderList.length; j++) {
                    // Đếm số lần idWaiter xuất hiện (tức là số lần phục vụ đơn)
                    if (orderList[j].getIdWaiter().equals(id)) {
                        countJob++;
                    }
                }

                for (int j = 0; j < el.getSize(); j++) {
                    if (el.getEmployees()[j].getId().trim().equals(id)) {
                        System.out.println(
                                String.format("| %-5s | %-20s | %3d |", id, el.getEmployees()[j].getName(), countJob));
                    }
                }

            }
        }
        System.out.println("+ ---------------------------------- +");
        System.out.print("Nhấn `enter` để tiếp tục...");
        sc.nextLine();
    }

    public void add(Object object) {
        if (object instanceof Order) {
            Order order = (Order) object;
            String lastId;
            if (orderList.length == 0) {
                lastId = "BIL0";
                order.setId(lastId);
            } else {
                lastId = orderList[orderList.length - 1].getIdOrder().replace("BIL", "");
                order.setId("BIL" + ((Integer.parseInt(lastId)) + 1));
            }

            Order newOl[] = Arrays.copyOf(orderList, orderList.length + 1);
            newOl[newOl.length - 1] = order;
            orderList = newOl;
        } else {
            System.out.println("Invalid object type.");
        }
    }

    public void remove() {
    }

    public void search() {

    }

    public void edit() {

    }
}
