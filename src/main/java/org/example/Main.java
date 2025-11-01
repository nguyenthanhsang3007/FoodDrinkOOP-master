package org.example;

import java.io.File;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        initialApp();
        Menu menu = new Menu();
        menu.run();
    }

    // Khởi tạo file text ở lần chạy đâu tiên
    public static void initialApp() {
        String[] filenames = new String[] { "inventory.txt", "history.txt", "account.txt", "customer.txt",
                "employee.txt", "list_bill.txt", "order.txt", "supplier.txt" };
        String path = "data";
        for (String filename : filenames) {
            String filePath = path + "\\" + filename;
            File file = new File(filePath);

            if (!file.exists()) {
                try {
                    if (file.createNewFile()) {
                        System.out.println("File " + filename + " created successfully.");
                    }
                } catch (IOException e) {
                    System.out.println("An error occurred while creating file " + filename + ".");
                    e.printStackTrace();
                    return;
                }
            }
        }
    }
}
