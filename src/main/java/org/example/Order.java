package org.example;


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Random;
import java.util.Scanner;


public class Order {
    private String idOrder;
    private String idCustomer;
    private String idWaiter;
    private ProductList productList = new ProductList();
    private String[] order;
    private int idxOrder = 0;

    Scanner sc = new Scanner(System.in);

    public Order() {}

    public Order(String idOrder, String idCustomer , String idWaiter, String[] order) {
        this.idOrder = idOrder;
        this.idCustomer = idCustomer;
        this.idWaiter = idWaiter;
        this.order = order;
    }

    public void setId(String idOrder) {
        this.idOrder = idOrder;
    }

    public void setProductList(ProductList productList) {
        this.productList = productList;
    }

    public void setOrder(String[] order) {
        this.order = order;
    }

    public void setWaiter(String idWaiter) {
        this.idWaiter = idWaiter;
    }

    public void setCustomer(String idCustomer) {
        this.idCustomer = idCustomer;
    }

    public String getIdOrder() {
        return idOrder;
    }

    public ProductList getProductList() {
        return productList;
    }

    public String[] getOrder() {
        return order;
    }

    public String getIdWaiter() {
        return idWaiter;
    }

    public String getIdCustomer() {
        return idCustomer;
    }

    public boolean isInteger(String orderChoice) {
        try {
            Integer.parseInt(orderChoice);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public String convertToNumberId(String originalId) {
        String converted = "";

        if (originalId.startsWith("FD")) {
            converted = originalId.replaceFirst("FD", "");
        } else if (originalId.startsWith("DK")) {
            converted = originalId.replaceFirst("DK", "");
        }

        return converted;
    }

    public boolean checkingEmptyQuantityProduct(Product product) {
        if (product.getQuantity() == 0) {
            System.out.println("Xin lỗi, hiện tại sản phẩm này đã hết hàng.");
            return true;
        }
        return false;
    }

    public boolean checkingIsNotEnoughQuantity(Product product, Product orderItem) {
        if (product.getQuantity() < orderItem.getQuantity()) {
            System.out.println("Hiện tại, chúng tôi chỉ có " + product.getQuantity() + " sản phẩm, không đáp ứng đủ nhu cầu của bạn, vui lòng thử lại sau.");
            return true;
        }
        return false;
    }

    public void customerFoodItemValue(FoodItem foodItem, Product product) {
        foodItem.setId(product.getId());

        // Kiểm tra đầu vào nhập có hợp lệ hay không ?
        System.out.print("Bạn có ăn cay (0/1): ");
        String spicyLevelString = sc.nextLine();
        while (!spicyLevelString.equals("0") && !spicyLevelString.equals("1")) {
            System.out.print("Nhập sai định dạng độ cay, nhập lại (0/1): ");
            spicyLevelString = sc.nextLine();
        }
        foodItem.setSpicyLevel(Integer.parseInt(spicyLevelString));

        System.out.print("Nhập số lượng: ");
        String quantityString = sc.nextLine();
        while (!isInteger(quantityString) || (isInteger(quantityString) && Integer.parseInt(quantityString) <= 0)) {
            if (isInteger(quantityString) && Integer.parseInt(quantityString) <= 0) {
                System.out.print("Nhập số lượng phải lớn hơn bằng 0, nhập lại: ");
                quantityString = sc.nextLine();
            } else {
                System.out.print("Nhập sai định dạng số lượng, nhập lại: ");
                quantityString = sc.nextLine();
            }
        }
        foodItem.setQuantity(Integer.parseInt(quantityString));
    }

    public void customerDrinkItemValue(DrinkItem drinkItem, Product product) {
        drinkItem.setId(product.getId());

        // Kiểm tra đầu vào nhập có hợp lệ hay không ?
        System.out.print("Bạn chọn size (300/500/700): ");
        String volumeString = sc.nextLine();
        while (!volumeString.equals("300") && !volumeString.equals("500") && !volumeString.equals("700")) {
            System.out.print("Nhập sai định dạng size, nhập lại (300/500/700): ");
            volumeString = sc.nextLine();
        }
        drinkItem.setVolume(Integer.parseInt(volumeString));

        System.out.print("Bạn có uống ngọt (0/1): ");
        String sugerLevelString = sc.nextLine();
        while (!sugerLevelString.equals("0") && !sugerLevelString.equals("1")) {
            System.out.print("Nhập sai định dạng mức độ đường, nhập lại (0/1): ");
            sugerLevelString = sc.nextLine();
        }
        drinkItem.setSugarLevel(Integer.parseInt(sugerLevelString));

        System.out.print("Bạn có uống cùng với đá (0/1): ");
        String iceLevelString = sc.nextLine();
        while (!iceLevelString.equals("0") && !iceLevelString.equals("1")) {
            System.out.print("Nhập sai định dạng lựa chọn uống cùng với đá, nhập lại (0/1): ");
            iceLevelString = sc.nextLine();
        }
        drinkItem.setIceLevel(Integer.parseInt(iceLevelString));

        System.out.print("Nhập số lượng: ");
        String quantityString = sc.nextLine();
        while (!isInteger(quantityString) || (isInteger(quantityString) && Integer.parseInt(quantityString) <= 0)) {
            if (isInteger(quantityString) && Integer.parseInt(quantityString) <= 0) {
                System.out.print("Nhập số lượng phải lớn hơn bằng 0, nhập lại: ");
                quantityString = sc.nextLine();
            } else {
                System.out.print("Nhập sai định dạng số lượng, nhập lại: ");
                quantityString = sc.nextLine();
            }
        }
        drinkItem.setQuantity(Integer.parseInt(quantityString));
    }

    public void viewDescription(String orderChoice) {
        boolean isFind = false;
        String[] parts = orderChoice.split(" ");
        //Kiểm tra xem tại vị trí 1 và 2 có trùng với + và des không?
        //Vị trí tổng quan: parts[0] = number, parts[1] = "+", parts[2] = "des"
        if (parts[1].equals("+") && parts[2].equalsIgnoreCase("des")) {
            for (int idx = 0; idx < productList.getProductList().length; idx++) {
                if (productList.getProductList()[idx].getId().equals(parts[0])) {
                    productList.getProductList()[idx].toViewDescription();
                    isFind = true;
                    break;
                }

            }
            if (!isFind) {
                System.out.println("Xin lỗi, không tìm thấy mô tả sản phẩm :(");
            }
        } else {
            System.out.println("Xin lỗi, có lẽ bạn đã nhập sai cú pháp :(");
        }
    }

    public String toCVString() {
        String orderString = String.format("%s#%s#%s#", idOrder, idCustomer, idWaiter);

        for (String orderItem : order) {
            orderString += orderItem;
            orderString += "@";
        }

        if (orderString.endsWith("@")) {
            orderString = orderString.substring(0, orderString.length() - 1);
        }
        return orderString;
    }

    public void newOrder() {
        productList.readFromFile();

        System.out.println("Bạn có thể nhập `id + des` để xem mô tả sản phẩm");
        System.out.println("Ví dụ: FD1 hoặc FD1 + des");

        order = new String[100];

        String orderChoice = "initial";
        while (!orderChoice.trim().isEmpty()) {
            System.out.print("Nhập lựa chọn của bạn (Nhấn `enter` để hoàn tất): ");
            orderChoice = sc.nextLine();

            if (!orderChoice.trim().isEmpty() && !orderChoice.contains("+ des")) {
                //Đặt biến cờ hiệu
                boolean isOrderSuccess = false;

                for (int idx = 0; idx < productList.getProductList().length; idx++) {
                    if (productList.getProductList()[idx].getId().equals(orderChoice) && !isOrderSuccess) {
                        if (productList.getProductList()[idx] instanceof FoodItem) {
                            if (!checkingEmptyQuantityProduct(productList.getProductList()[idx])) {
                                FoodItem orderItem = new FoodItem();

                                customerFoodItemValue(orderItem, productList.getProductList()[idx]);

                                if (!checkingIsNotEnoughQuantity(productList.getProductList()[idx], orderItem)) {
                                    productList.getProductList()[idx].setQuantity(productList.getProductList()[idx].getQuantity() - orderItem.getQuantity());
                                    order[idxOrder++] = String.format("%s, %d, %d", orderItem.getId(), orderItem.getQuantity(), orderItem.getSpicyLevel());
                                }
                            }

                            isOrderSuccess = true;
                        }

                        else if (productList.getProductList()[idx] instanceof DrinkItem) {
                            if (!checkingEmptyQuantityProduct(productList.getProductList()[idx])) {
                                DrinkItem orderItem = new DrinkItem();

                                customerDrinkItemValue(orderItem, productList.getProductList()[idx]);

                                if (!checkingIsNotEnoughQuantity(productList.getProductList()[idx],orderItem)) {
                                    productList.getProductList()[idx].setQuantity(productList.getProductList()[idx].getQuantity() - orderItem.getQuantity());
                                    order[idxOrder++] = String.format("%s, %d, %d, %d, %d", orderItem.getId(), orderItem.getQuantity(), orderItem.getVolume(), orderItem.getSugarLevel(), orderItem.getIceLevel());
                                }
                            }
                            isOrderSuccess = true;
                        }
                    }
                }

                //Nếu người dùng nhập mà chẳng đúng id sản phẩm
                if (!isOrderSuccess) {
                    System.out.println("Xin lỗi, có lẽ bạn đã nhập sai cú pháp :(");
                }
            } else if (!orderChoice.trim().isEmpty()) {
                viewDescription(orderChoice);
            }
        }

        // Ban đầu khởi tạo mảng order với size là 100
        // nhưng nếu đẻ vậy khi ghi file sẽ là null
        // nên cần thu hồi kích thước mảng lại
        String[] newOrder = new String[idxOrder];
        for (int idx = 0; idx < idxOrder; idx++) {
            newOrder[idx] = order[idx];
        }

        order = newOrder;

        productList.writeToFile();
    }


    public String getRandomWaiterId() {
        String[] waiterIds = new String[0];

        try (BufferedReader reader = new BufferedReader(new FileReader("data\\employee.txt"))) {
            int waiterIndex = 0;
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(", ");
                if (parts.length >= 3 && "Waiter".equalsIgnoreCase(parts[2])) {
                    String[] newWaiterIds = new String[waiterIds.length + 1];
                    System.arraycopy(waiterIds, 0, newWaiterIds, 0, waiterIds.length);
                    newWaiterIds[waiterIds.length] = parts[0];
                    waiterIds = newWaiterIds;
                }
            }
        } catch (IOException e) {
            System.out.println("Không tìm thấy file: data\\employee.txt");
        }

        if (waiterIds.length > 0) {
            // Chọn ngẫu nhiên một ID của Waiter từ danh sách
            Random random = new Random();
            String randomWaiterId = waiterIds[random.nextInt(waiterIds.length)];
            setWaiter(randomWaiterId);
            return randomWaiterId;
        } else {
            System.out.println("Không có thông tin Waiter trong tệp employee.txt");
            return null;
        }
    }

}
