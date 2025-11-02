package org.example;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.Scanner;

public class Bill {
    private String idBill;
    private String idCustomer;
    private String idCashier;
    private float totalPrice = 0;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private Date dateCreate;
    private ProductList productList = new ProductList();
    private CustomerList customerList = new CustomerList();
    private String bill;
    private String dataLastOrder;
    private String nameCustomer;
    private float totalPriceBeforeDiscount;
    public Bill(String idBill, String idCustomer, String idCashier, String bill, float totalPrice) {
        this.idBill = idBill;
        this.idCustomer = idCustomer;
        this.idCashier = idCashier;
        this.bill = bill;
        this.totalPrice = totalPrice;
        this.dateCreate = new Date();
    }

    public Bill() {
        totalPrice = 0;
    }

    public void setIdBill(String idBill) {
        this.idBill = idBill;
    }

    public void setIdCustomer(String idCustomer) {
        this.idCustomer = idCustomer;
    }

    public void setIdCashier(String idCashier) {
        this.idCashier = idCashier;
    }

    public void setTotalPrice(float totalPrice) {
        this.totalPrice = totalPrice;
    }

    public void setBill(String bill) {
        this.bill = bill;
    }

    public String getIdBill() {
        return idBill;
    }

    public String getIdCustomer() {
        return idCustomer;
    }
    public void setTotalPriceBeforeDiscount(float totalPriceBeforeDiscount) {
        this.totalPriceBeforeDiscount = totalPriceBeforeDiscount;
    }
    public String getIdCashier() {
        this.idCashier = getRandomCashierId();
        return idCashier;
    }

    public float getTotalPrice() {
        return totalPrice;
    }

    public String getBill() {
        return bill;
    }

    public String getDateCreate() {
        return dateFormat.format(dateCreate);
    }

    public void setDateCreate(Date dateCreate) {
        this.dateCreate = dateCreate;
    }

    public String getDataLastOrder() {
        return dataLastOrder;
    }
    public float getTotalPriceBeforeDiscount() {
        return totalPriceBeforeDiscount;
    }
    public void setDataLastOrder() {
        try (FileReader file = new FileReader("data\\order.txt");
                Scanner sc = new Scanner(file)) {

            String line = null;
            while (sc.hasNextLine()) {
                line = sc.nextLine();
            }

            if (line != null) {
                this.dataLastOrder = line;
            } else {
                throw new IllegalStateException("File is empty.");
            }
        } catch (IOException e) {
            System.out.println("An error occurred while reading the file.");
            e.printStackTrace();
        }
    }

    private float newTotalPrice;
    private boolean isVIP;

    public String convertIdBill(String originalId) {
        return originalId.replaceFirst("ORD", "BIL");
    }

    // Trong class Bill.java
    public String getNameCustomer() {
        String[] parts = this.dataLastOrder.split("#");
        customerList.readFromFile();
    
    // Gán giá TRƯỚC GIẢM (totalPrice hiện tại là giá gốc từ convertDataBill())
        this.totalPriceBeforeDiscount = this.totalPrice; // SỬ DỤNG THUỘC TÍNH MỚI
    
        for (int i = 0; i < customerList.getCustomerList().length; i++) {
            if (customerList.getCustomerList()[i].getIdCustomer().equals(parts[1])) {
                nameCustomer = customerList.getCustomerList()[i].getNameCustomer();
                customerList.setCheck(false);
                Customer updateScore = customerList.getCustomerList()[i];
            
                if (updateScore.getScore() > 300000f) {
                // Khach vip: tính giá sau giảm 10%
                    isVIP = true;
                // newTotalPrice = Giá gốc - 10%
                    newTotalPrice = totalPriceBeforeDiscount - totalPriceBeforeDiscount * 0.1f; 
                } else { // Khach thường
                    isVIP = false;
                    newTotalPrice = totalPriceBeforeDiscount;
                }
            
                updateScore.setScore(updateScore.getScore() + newTotalPrice);
                customerList.getCustomerList()[i] = updateScore;
                customerList.writeToFile();
                customerList.setCheck(true);
            
            // Cập nhật totalPrice của Bill là giá SAU giảm giá
                setTotalPrice(newTotalPrice); 
            }
        }
        return nameCustomer;
    }

    private String getProductName(Product product) {
        return product.getName();
    }

    private float getPrice(Product product) {
        return product.getPrice();
    }

    private int getQuantity(String quantity) {
        return Integer.parseInt(quantity);
    }

    private String convertAdditionalInfo(String productId, String[] dataId) {
        StringBuilder additionalInfo = new StringBuilder();
        if (productId.startsWith("FD")) {
            String levelSpice = dataId[2].trim();

            additionalInfo.append(levelSpice.equals("1") ? "Có cay" : "Không cay");
        } else {
            String sizeDrink = dataId[2].trim();
            String levelSugar = dataId[3].trim();
            String levelIce = dataId[4].trim();

            additionalInfo.append("Dung tích: ").append(sizeDrink).append("ml");
            additionalInfo.append(levelSugar.equals("1") ? ", có đường" : ", không đường");
            additionalInfo.append(levelIce.equals("1") ? ", có đá" : ", không đá");
        }
        return additionalInfo.toString();
    }

    public String convertDataBill() {
        productList.readFromFile();
        this.dateCreate = new Date();

        StringBuilder result = new StringBuilder();

        String[] parts = this.dataLastOrder.split("#");
        this.idBill = parts[0];
        this.idCustomer = parts[1];
        this.idCashier = convertIdBill(parts[2]);
        this.bill = parts[3];
        String[] billInfo = parts[3].trim().split("@");

        // Format headers
        result.append(String.format(" | %-20s | %-15s | %-10s | %-40s | %-20s\n", "Tên sản phẩm", "Giá tiền",
                "Số lượng", "Thông tin bổ sung", "Tổng"));
        result.append(String.format("  %-20s   %-15s   %-10s   %-40s   %-20s\n", "--------------", "------",
                "----------", "-----------------", "----------"));

        for (String s : billInfo) {
            String[] dataId = s.split(",");
            String productId = dataId[0].trim();
            String quantity = dataId[1].trim();
            for (int i = 0; i < productList.getProductList().length; i++) {
                if (productId.equals(productList.getProductList()[i].getId())) {
                    result.append(String.format(" | %-20s | %-15.2f | %-10d | %-40s | %-20.2f\n",
                            getProductName(productList.getProductList()[i]),
                            getPrice(productList.getProductList()[i]),
                            getQuantity(quantity),
                            convertAdditionalInfo(productId, dataId),
                            productList.getProductList()[i].getPrice() * Integer.parseInt(quantity)));
                    totalPrice += productList.getProductList()[i].getPrice() * Integer.parseInt(quantity);
                }
            }

        }

        return result.toString();
    }
    public String toString() {
        String inforBill = convertDataBill();
        String x = getNameCustomer();
        setTotalPrice(this.newTotalPrice); 

        if (isVIP) {
            float discountAmount = this.totalPriceBeforeDiscount - this.newTotalPrice; 
            //Khách VIP
            return String.format(" \n%s\nTên khách hàng: %s(VIP) \nĐược giảm giá: 10%%\nSố tiền được giảm: %.2f \nTổng tiền (Sau giảm giá): %.2f \nNgày: %s",
                    inforBill,x,discountAmount,this.newTotalPrice,dateFormat.format(dateCreate)        
            );
        }       
        // Khách hàng thường 
        return String.format(" \n%s\nTên khách hàng: %s \nTổng tiền: %.2f \nNgày: %s",
                inforBill,x,this.newTotalPrice,dateFormat.format(dateCreate)            
        );
    }

    public void writeToFile() {
        convertDataBill();
        getNameCustomer(); 
    
        this.idCashier = getRandomCashierId();
        try {
            FileWriter f = new FileWriter("data\\list_bill.txt", true);
            String code = String.format("%s#%s#%s#%s#%f#%f#%s", this.idBill, this.idCustomer, this.idCashier,
                this.getBill(), this.getTotalPriceBeforeDiscount(), this.getTotalPrice(), this.getDateCreate());
            f.write(code);
            f.write("\n");
            f.close();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }   
    }

    public String getRandomCashierId() {
        String[] cashierIds = new String[0];

        try (BufferedReader reader = new BufferedReader(new FileReader("data\\employee.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(", ");
                if (parts.length >= 3 && "Cashier".equalsIgnoreCase(parts[2])) {
                    String[] newCashierId = new String[cashierIds.length + 1];
                    System.arraycopy(cashierIds, 0, newCashierId, 0, cashierIds.length);
                    newCashierId[cashierIds.length] = parts[0];
                    cashierIds = newCashierId;
                }
            }
        } catch (IOException e) {
            System.out.println("Không tìm thấy file: data\\employee_data.txt");
        }

        if (cashierIds.length > 0) {
            Random random = new Random();
            String randomCashierId = cashierIds[random.nextInt(cashierIds.length)];
            setIdCashier(randomCashierId);
            return randomCashierId;
        } else {
            System.out.println("Không có thông tin Cashier trong tệp employee_data.txt");
            return null;
        }
    }

}
