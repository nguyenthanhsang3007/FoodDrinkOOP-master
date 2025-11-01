package org.example;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;

public class AccountList implements ConnectDatabase {
    private Account[] accountList;
    Scanner sc = new Scanner(System.in);

    public AccountList() {
        accountList = new Account[0];
    }

    public void setAccountList(Account[] accountList) {
        this.accountList = accountList;
    }

    public Account[] getAccountList() {
        return accountList;
    }

    public void readFromFile() {
        try {
            accountList = new Account[0];

            File myObj = new File("data\\account.txt");
            Scanner myReader = new Scanner(myObj);

            while (myReader.hasNextLine()) {
                Account newA[] = Arrays.copyOf(accountList, accountList.length + 1);
                String data = myReader.nextLine();

                // Nếu dòng trong file trống thì bỏ qua
                if (data.equalsIgnoreCase("")) {
                    continue;
                }

                String[] parts = data.split(",");

                String username = parts[0].trim();
                String password = parts[1].trim();
                String access = parts[2].trim();

                newA[newA.length - 1] = new Account(username, password, access);
                accountList = newA;
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public void writeToFile() {
        try {
            FileWriter myWriter = new FileWriter("data\\account.txt");

            for (int i = 0; i < accountList.length; i++) {
                myWriter.write(accountList[i].toCVString());
                myWriter.write("\n");
            }
            myWriter.close();
            System.out.println("Hệ thống đã ghi nhận dữ liệu của bạn!");
        } catch (IOException e) {
            System.out.println("Xin lỗi, hiện tại dịch vụ của chúng tôi bị lỗi, không thể order sản phẩm!");
            e.printStackTrace();
        }
    }

    public boolean isExistUsername(String username) {
        readFromFile();
        for (int i = 0; i < accountList.length; i++) {
            if (accountList[i].getUsername().trim().equalsIgnoreCase(username)) {
                return true;
            }
        }
        return false;
    }

    public boolean isCorrectAccountLogin(String username, String password) {
        readFromFile();
        for (int i = 0; i < accountList.length; i++) {
            if (accountList[i].getUsername().trim().equalsIgnoreCase(username)
                    && accountList[i].getPassword().trim().equalsIgnoreCase(password)) {
                return true;
            }
        }
        return false;
    }

    // Hàm này chỉ dùng khi đã đăng nhập rồi
    public String getAccessCurrentAccountLoggedIn(String username) {
        readFromFile();
        for (int i = 0; i < accountList.length; i++) {
            if (accountList[i].getUsername().trim().equals(username)) {
                return accountList[i].getAccesss();
            }
        }
        return "";
    }

    public String login() {
        String username = "", password = "";
        System.out.print("Nhập tài khoản (nhập `exit` để huỷ bỏ đăng nhập): ");
        username = sc.nextLine();
        if (username.equalsIgnoreCase("exit"))
            return "exit";
        while (!isExistUsername(username)) {
            System.out.print("Hệ thống không tìm thấy tài khoản, nhập lại: ");
            username = sc.nextLine();
            if (username.equalsIgnoreCase("exit"))
                return "exit";
        }
        int count = 0;

        System.out.print("Nhập mật khẩu: ");
        password = sc.nextLine();
        if (isCorrectAccountLogin(username, password)) {
            String access = getAccessCurrentAccountLoggedIn(username);
            if (access.trim().equalsIgnoreCase("cashier"))
                return "cashier";
            else if (access.trim().equalsIgnoreCase("admin"))
                return "admin";
        }

        while (true && count != 3) {
            System.out.print("Nhập lại mật khẩu : ");
            password = sc.nextLine();
            ++count;
            if (isCorrectAccountLogin(username, password)) {
                String access = getAccessCurrentAccountLoggedIn(username);
                if (access.trim().equalsIgnoreCase("cashier"))
                    return "cashier";
                else if (access.trim().equalsIgnoreCase("admin"))
                    return "admin";
            }
        }

        System.out.println("Bạn nhập sai mật khẩu quá " + (count) + " lần, vui lòng thử lại sau...");
        return "";
    }
}
