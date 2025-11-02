    package org.example;

    import java.io.File;
    import java.io.FileReader;
    import java.io.IOException;
    import java.text.ParseException;
    import java.text.SimpleDateFormat;
    import java.util.Arrays;
    import java.util.Scanner;

    public class ListBill {
        private static final int MAX_BILLS = 1000;
        private static final int MAX_CUSTOMERS = 1000;
        private static final int MAX_PRODUCTS = 1000;
        private String[] customerIds = new String[MAX_BILLS];
        private float[] customerTotalSpent = new float[MAX_CUSTOMERS];

        private String[] productIdList = new String[MAX_BILLS];
        private String[] productNames = new String[MAX_PRODUCTS];
        private int[] productQuantities = new int[MAX_PRODUCTS];
        private Bill[] billList = new Bill[MAX_BILLS];
        private int numBills = 0;
        private String[] customerNames = new String[MAX_CUSTOMERS];
        private int[] customerPurchaseCounts = new int[MAX_CUSTOMERS];
        private int numCustomers = 0;
        Scanner sc = new Scanner(System.in);

        public ListBill() {
            Arrays.fill(productIdList, null);
            readFromFile("data\\list_bill.txt");
            updateStatistics();
        }
   
    public void readFromFile(String filename) {
        try (FileReader file = new FileReader(filename);
                Scanner sc = new Scanner(file)) {

            while (sc.hasNextLine() && numBills < MAX_BILLS) {
                String line = sc.nextLine();
                String[] parts = line.split("#"); 
                if (parts.length < 7) { 
                    if (parts.length < 2) continue; 
                    continue; 
                }

                String idBill = parts[0];
                String idCustomer = parts[1].equals("null") ? null : parts[1];
                String idCashier = parts[2].equals("null") ? null : parts[2];
                String billData = parts[3];
                float totalPriceBeforeDiscount = Float.parseFloat(parts[4]); 
                float totalPrice = Float.parseFloat(parts[5]); 
                String dateCreate = parts[6];

                Bill bill = new Bill(idBill, idCustomer, idCashier, billData, totalPrice);

                String productId = extractProductId(parts[3]);
                productIdList[numBills] = productId;
                
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                try {
                    bill.setDateCreate(dateFormat.parse(dateCreate));
                } catch (ParseException e) {
                    System.out.println("Error parsing date: " + e.getMessage());
                }
                billList[numBills] = bill;
                numBills++;
            }

        } catch (IOException e) {
            System.out.println("An error occurred while reading the file.");
            e.printStackTrace();
        }
    }

        public String getProductId(String productName) {
            try {
                File file = new File("data\\inventory.txt");
                Scanner scanner = new Scanner(file);

                while (scanner.hasNextLine()) {
                    String line = scanner.nextLine();
                    String[] parts = line.split(",");
                    if (parts.length > 1 && productName.trim().equalsIgnoreCase(parts[1].trim())) {
                        return parts[0].trim();
                    }
                }

                scanner.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        private String extractProductId(String billData) {
            String[] products = billData.split("@");

            for (String product : products) {
                String[] productInfo = product.split(",");
                if (productInfo.length > 0) {
                    String productId = productInfo[0].trim();
                    if (!"null".equalsIgnoreCase(productId)) {
                        String validProductId = getProductId(productId);
                        if (validProductId != null) {
                            return validProductId;
                        } else {
                            return null;
                        }
                    }
                }
            }

            return null;
        }

    public float calculateDiscountAmount(String customerId, float totalBillPrice) {
        if (customerId == null || customerId.equals("null") || customerId.trim().isEmpty()) {
            return 0.0f;
        }

        try {
            try (Scanner read = new Scanner(new File("data\\customer.txt"))) {
                while (read.hasNextLine()) {
                    String[] line = read.nextLine().split(",");
                        if (line.length >= 4 && customerId.trim().equals(line[0].trim())) {
                        try {
                            float score = Float.parseFloat(line[3].trim()); 
                        
                            if (score > 300000f) {
                                return totalBillPrice * 0.10f; 
                            } else {

                                return 0.0f;
                            }
                        } catch (NumberFormatException e) {
                        }
                    }
                }
            }
            } catch (Exception e) {
                 e.printStackTrace();
            }
            return 0.0f;
        }

    public void printAllBills() {
        System.out.println(
                "+----------+----------------------+----------------------+---------------+-----+----------------+----------------+----------+---------------+---------------------+"
        );
        System.out.printf("| %-8s | %-20s | %-20s | %-13s | %-3s | %-14s | %14s | %-8s | %13s | %-19s |\n",
                "Id HĐ", "Khách hàng", "Thu ngân", "Tên sản phẩm", "SL", "Tổng SP", "Giá Trước Giảm", "Giảm(VIP)", "Giá Sau Giảm", "Ngày tạo");
        System.out.println(
                "+----------+----------------------+----------------------+---------------+-----+----------------+----------------+----------+---------------+---------------------+"
        );

        for (int i = 0; i < numBills; i++) {
            Bill bill = billList[i];
            
            float totalPriceAfterDiscount = bill.getTotalPrice(); 
            float totalPriceBeforeDiscount = totalPriceAfterDiscount;
            String vipStatus = "";
            
            float discountAmount = calculateDiscountAmount(bill.getIdCustomer(), totalPriceAfterDiscount);
            if (discountAmount > 0) {
                totalPriceBeforeDiscount = totalPriceAfterDiscount / 0.9f; 
                vipStatus = "10%";
            } else {
                totalPriceBeforeDiscount = totalPriceAfterDiscount;
            }

            if (bill.getBill().contains("@")) {
                String[] infors = bill.getBill().split("@");
                String productDetail = inforProduct(infors[0]);
                System.out.printf("| %-8s | %-20s | %-20s | %-13s | %-3s | %14s | %14.2f | %-8s | %13.2f | %-19s |\n",
                        bill.getIdBill(), nameCustomer(bill.getIdCustomer()), nameCashier(bill.getIdCashier()),
                        ToString1(productDetail), getQuantity(productDetail), getTotalPricePerItem(productDetail), 
                        totalPriceBeforeDiscount, vipStatus, totalPriceAfterDiscount, bill.getDateCreate());
                
                for (int j = 1; j < infors.length; j++) {
                    String nextProductDetail = inforProduct(infors[j]);
                    System.out.printf("| %-8s | %-20s | %-20s | %-13s | %-3s | %14s | %14s | %-8s | %13s | %-19s |\n",
                            "", "", "",
                            ToString1(nextProductDetail), getQuantity(nextProductDetail), getTotalPricePerItem(nextProductDetail),
                            "", "", "", "");
                }
                System.out.println(
                        "+----------+----------------------+----------------------+---------------+-----+----------------+----------------+----------+---------------+---------------------+"
                );
            } else {
                String productDetail = inforProduct(bill.getBill());
                System.out.printf("| %-8s | %-20s | %-20s | %-13s | %-3s | %14s | %14.2f | %-8s | %13.2f | %-19s |\n",
                        bill.getIdBill(), nameCustomer(bill.getIdCustomer()), nameCashier(bill.getIdCashier()),
                        ToString1(productDetail), getQuantity(productDetail), getTotalPricePerItem(productDetail), 
                        totalPriceBeforeDiscount, vipStatus, totalPriceAfterDiscount, bill.getDateCreate());
                System.out.println(
                        "+----------+----------------------+----------------------+---------------+-----+----------------+----------------+----------+---------------+---------------------+"
                );
            }
        }
    }
    public String getDiscountStatus(String customerId) {
        if (customerId == null || customerId.equals("null") || customerId.trim().isEmpty()) {
            return "";
        }
        try {
            File file = new File("data\\customer.txt");

            Scanner read = new Scanner(file);
            while (read.hasNextLine()) {
                String[] line = read.nextLine().split(",");
                if (line.length >= 3 && customerId.trim().equals(line[0].trim())) {
                    try {
                        float score = Float.parseFloat(line[2].trim());
                        read.close();
                        if (score > 300000f) {
                            return "10%";
                        } else {
                            return "";
                        }
                    } catch (NumberFormatException e) {
                    }
                }
            }
                read.close();
            } catch (Exception e) {
            e.printStackTrace();
            }
        return "";
        }

        public float calculateTotalIncome() {
            float revenue = 0;
            for (int i = 0; i < numBills; i++) {
                revenue += billList[i].getTotalPrice();
            }
            return revenue;
        }

        public String nameCashier(String id) {
            try {
                File flie = new File("data\\employee.txt");

                Scanner read = new Scanner(flie);
                while (read.hasNextLine()) {
                    String[] line = read.nextLine().split(",");
                    if (id.trim().equals(line[0].trim()))
                        return line[1];
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return "";
        }

        public String nameCustomer(String id) {
            try {
                File flie = new File("data\\customer.txt");

                Scanner read = new Scanner(flie);
                while (read.hasNextLine()) {
                    String[] line = read.nextLine().split(",");
                    if (id.trim().equals(line[0].trim()))
                        return line[1];
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return "";
        }

        public String inforProduct(String infors) {

            String[] infor = infors.split(",");
            String price = "";
            try {
                File flie = new File("data\\inventory.txt");

                Scanner read = new Scanner(flie);
                while (read.hasNextLine()) {
                    String lines = read.nextLine();
                    String[] line = lines.split(",");
                    if (infor[0].trim().equals(line[0].trim())) {
                        infors = "";
                        infors = line[1];
                        price = line[2];
                        break;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            float total = Float.parseFloat(price) * Float.parseFloat(infor[1]);
            infors = infors + "," + infor[1].trim() + "," + Float.toString(total);
            return infors;
        }
    private String ToString1(String infors) {
        String[] infor = infors.split(",");
        return infor[0].trim(); 
    }

    private String getQuantity(String infors) {
        String[] infor = infors.split(",");
        return String.format("%3s", infor[1].trim()); 
    }

    private String getTotalPricePerItem(String infors) {
        String[] infor = infors.split(",");
        try {
            float total = Float.parseFloat(infor[2].trim());
            return String.format("%14.2f", total);
        } catch (NumberFormatException e) {
            return "";
        }
    }
        public void displayMenu() {
            System.out.println("+ ---------------------------------+");
            System.out.println("|            Thống kê              |");
            System.out.println("+ ---------------------------------+");
            System.out.println("| 1. Tất cả hoá đơn                |");
            System.out.println("| 2. Tìm hóa đơn theo mã           |");
            System.out.println("| 3. Doanh thu                     |");
            System.out.println("| 4. Số lượng hàng bán được        |");
            System.out.println("| 5. Số đơn hàng nhân viên bán được|");
            System.out.println("| 6. Đơn hàng nhân viên phục vụ    |");
            System.out.println("| 7. Đơn hàng của khách hàng       |");
            System.out.println("| 0. Quay lại                      |");
            System.out.println("+ -------------------------------- +");
            System.out.print("Nhập lựa chọn của bạn : ");
            int select;
            String temp;
            temp = sc.nextLine();
            while (!isNumber(temp)) {
                System.out.print("Lựa chọn không hợp lệ, nhập lại: ");
                temp = sc.nextLine();
            }
            select = Integer.parseInt(temp);
            while (select != 0) {
                switch (select) {
                    case 1:
                        printAllBills();
                        break;
                    case 2:
                        searchBillById();
                        break;
                    case 3:
                        float revenue = calculateTotalIncome();
                        System.out.println(
                                "+ ---------------------------------------------------------------------------------------------------------------------------- +");
                        System.out.printf(
                                "| Doanh thu : %7.2f                                                                                                      |\n",
                                revenue);
                        System.out.println(
                                "+ ---------------------------------------------------------------------------------------------------------------------------- +");
                        break;
                    case 4:
                        displayProductSales();
                        break;
                    case 5:

                        break;
                    case 6:
                        OrderList ol = new OrderList();
                        ol.statisticsWaiter();
                        break;
                    case 7:
                        displayCustomerPurchaseCount();
                        break;
                    default:
                        System.out.println("Không có lựa chọn này.");
                        break;
                }
                System.out.println("+ ---------------------------------+");
                System.out.println("|            Thống kê              |");
                System.out.println("+ ---------------------------------+");
                System.out.println("| 1. Tất cả hoá đơn                |");
                System.out.println("| 2. Tìm hóa đơn theo mã           |");
                System.out.println("| 3. Doanh thu                     |");
                System.out.println("| 4. Số lượng hàng bán được        |");
                System.out.println("| 5. Số đơn hàng nhân viên bán được|");
                System.out.println("| 6. Đơn hàng nhân viên phục vụ    |");
                System.out.println("| 7. Đơn hàng của khách hàng       |");
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

        public void numberOfSalesOrders() {
            try (FileReader file = new FileReader("data\\list_bill.txt");
                    Scanner sc = new Scanner(file)) {

                System.out.println("+ --------------------------------------------------- +");
                System.out.printf("| %-10s | %-20s | %-15s |\n", "idCashier", "Cashier name", "Count bill");
                System.out.println("+ --------------------------------------------------- +");

                final int MAX_CASHIERS = 100;
                String[] cashierIds = new String[MAX_CASHIERS];
                String[] cashierNames = new String[MAX_CASHIERS];
                int[] cashierCounts = new int[MAX_CASHIERS];

                int cashierIndex = 0;

                while (sc.hasNextLine() && numBills < MAX_BILLS) {
                    String line = sc.nextLine();
                    String[] parts = line.split("#");

                    String idCashier = parts[2].equals("null") ? null : parts[2];
                    boolean isNewCashier = true;
                    for (int i = 0; i < cashierIndex; i++) {
                        if (idCashier != null && idCashier.equals(cashierIds[i])) {
                            cashierCounts[i]++;
                            isNewCashier = false;
                            break;
                        }
                    }

                    if (isNewCashier && cashierIndex < MAX_CASHIERS) {
                        cashierIds[cashierIndex] = idCashier;
                        cashierNames[cashierIndex] = nameCashier(idCashier);
                        cashierCounts[cashierIndex] = 1;
                        cashierIndex++;
                    }
                }

                for (int i = 0; i < cashierIndex; i++) {
                    System.out.printf("| %-10s | %-20s | %-15d |\n", cashierIds[i], cashierNames[i], cashierCounts[i]);
                }
                System.out.println("+ --------------------------------------------------- +");

            } catch (IOException e) {
                System.out.println("An error occurred while reading the file.");
                e.printStackTrace();
            }
        }

        public void printfDoanhTHu() {
            float revenue = calculateTotalIncome();
            System.out.println("+ ------------------------- +");
            System.out.printf("| Doanh thu : %7.2f |\n", revenue);
            System.out.println("+ -------------------------- +");
        }

        private boolean isNumber(String s) {
            if (s == null)
                return false;

            try {
                int x = Integer.parseInt(s);
            } catch (NumberFormatException e) {
                return false;
            }
            return true;
        }

        private void updateStatistics() {
            for (int i = 0; i < numBills; i++) {
                Bill bill = billList[i];

                String customerId = bill.getIdCustomer();
                String customerName = nameCustomer(customerId);

                int customerIndex = findCustomerIndex(customerId);

                if (customerIndex == -1) {
                    customerIds[numCustomers] = customerId;
                    customerNames[numCustomers] = customerName;
                    customerPurchaseCounts[numCustomers] = 1;
                    customerTotalSpent[numCustomers] = bill.getTotalPrice();
                    numCustomers++;
                } else {
                    customerPurchaseCounts[customerIndex]++;
                    customerTotalSpent[customerIndex] += bill.getTotalPrice();
                }
            }
            for (int i = 0; i < numBills; i++) {
                Bill bill = billList[i];

                String[] billInfo = bill.getBill().split("@");
                for (String info : billInfo) {
                    String[] productInfo = inforProduct(info).split(",");
                    String productName = productInfo[0].toLowerCase();
                    int quantity = Integer.parseInt(productInfo[1]);

                    updateProductSales(productName, quantity);
                }
            }
        }

        private int findCustomerIndex(String customerId) {
            for (int i = 0; i < numCustomers; i++) {
                if (customerIds[i].equals(customerId)) {
                    return i;
                }
            }
            return -1;
        }

        private void updateProductSales(String productName, int quantity) {
            for (int i = 0; i < MAX_PRODUCTS; i++) {
                if (productNames[i] == null) {
                    productNames[i] = productName;
                    productQuantities[i] += quantity;
                    break;
                } else if (productNames[i].equals(productName)) {
                    productQuantities[i] += quantity;
                    break;
                }
            }
        }

        public void displayProductSales() {
            System.out.println("+ -------------------------------------------------------- +");
            System.out.printf("|%-10s | %-30s | %-10s |\n", "ID sản phẩm", "Sản phẩm", "Số lượng");
            System.out.println("+ -------------------------------------------------------- +");

            for (int i = 0; i < MAX_PRODUCTS && productNames[i] != null; i++) {
                String productId = getProductId(productNames[i]);
                String productName = productNames[i].toLowerCase();
                int quantity = productQuantities[i];

                System.out.printf("| %-10s | %-30s | %10d |\n", productId, productName, quantity);
            }

            System.out.println("+ -------------------------------------------------------- +");
        }

        public void displayCustomerPurchaseCount() {
            System.out.println("+ ---------------------------------------------------------------- +");
            System.out.printf("|%-10s  | %-25s | %-9s | %-12s|\n", " ID ", "Tên khách hàng", "SL", "Tổng tiền");
            System.out.println("+ ---------------------------------------------------------------- +");

            for (int i = 0; i < numCustomers; i++) {
                System.out.printf("| %-10s | %-25s | %9d |%12.2f |\n",
                        customerIds[i], customerNames[i], customerPurchaseCounts[i], customerTotalSpent[i]);
            }

            System.out.println("+ ---------------------------------------------------------------- +");
        }

        public static void main(String[] args) {
            ListBill listBill = new ListBill();

            listBill.displayMenu();

        }
        // public static void main(String[] args) {
        // ListBill listBill = new ListBill();
        //
        // listBill.displayMenu();
        // System.out.println("All Bills:");
        // listBill.displayMenu();
        //
        // }
    public void searchBillById() {
        System.out.print("Nhập mã hóa đơn cần tìm: ");
        Scanner sc = new Scanner(System.in);
        String id = sc.nextLine().trim().toLowerCase();
        boolean found = false;

        System.out.println(
                "+----------+----------------------+----------------------+---------------+-----+----------------+----------------+----------+---------------+---------------------+"
        );
        System.out.printf("| %-8s | %-20s | %-20s | %-13s | %-3s | %-14s | %14s | %-8s | %13s | %-19s|\n",
                "Id HĐ", "Khách hàng", "Thu ngân", "Tên sản phẩm", "SL", "Tổng SP", "Giá Trước Giảm", "Giảm(VIP)", "Giá Sau Giảm", "Ngày tạo");
        System.out.println(
                "+----------+----------------------+----------------------+---------------+-----+----------------+----------------+----------+---------------+---------------------+"
        );

        for (int i = 0; i < numBills; i++) {
            Bill bill = billList[i];
            if (bill.getIdBill().trim().toLowerCase().equals(id)) {
                found = true;
                
                float totalPriceAfterDiscount = bill.getTotalPrice();
                float totalPriceBeforeDiscount = totalPriceAfterDiscount;
                String vipStatus = "";

                float discountAmount = calculateDiscountAmount(bill.getIdCustomer(), totalPriceAfterDiscount);
                if (discountAmount > 0) {
                    totalPriceBeforeDiscount = totalPriceAfterDiscount / 0.9f; 
                    vipStatus = "10%";
                } else {
                    totalPriceBeforeDiscount = totalPriceAfterDiscount;
                }

                if (bill.getBill().contains("@")) {
                    String[] infors = bill.getBill().split("@");
                    String productDetail = inforProduct(infors[0]);
                    System.out.printf("| %-8s | %-20s | %-20s | %-13s | %-3s | %14s | %14.2f | %-8s | %13.2f | %-19s|\n",
                            bill.getIdBill(), nameCustomer(bill.getIdCustomer()), nameCashier(bill.getIdCashier()),
                            ToString1(productDetail), getQuantity(productDetail), getTotalPricePerItem(productDetail), 
                            totalPriceBeforeDiscount, vipStatus, totalPriceAfterDiscount, bill.getDateCreate());
                    
                    for (int j = 1; j < infors.length; j++) {
                        String nextProductDetail = inforProduct(infors[j]);
                        System.out.printf("| %-8s | %-20s | %-20s | %-13s | %-3s | %14s | %14s | %-8s | %13s | %-19s |\n",
                                "", "", "",
                                ToString1(nextProductDetail), getQuantity(nextProductDetail), getTotalPricePerItem(nextProductDetail),
                                "", "", "", "");
                    }
                    System.out.println(
                        "+----------+----------------------+----------------------+---------------+-----+----------------+----------------+----------+---------------+---------------------+"
                    );

                } else {
                    String productDetail = inforProduct(bill.getBill());
                    System.out.printf("| %-8s | %-20s | %-20s | %-13s | %-3s | %14s | %14.2f | %-8s | %13.2f | %-19s |\n",
                            bill.getIdBill(), nameCustomer(bill.getIdCustomer()), nameCashier(bill.getIdCashier()),
                            ToString1(productDetail), getQuantity(productDetail), getTotalPricePerItem(productDetail), 
                            totalPriceBeforeDiscount, vipStatus, totalPriceAfterDiscount, bill.getDateCreate());
                    System.out.println(
                        "+----------+----------------------+----------------------+---------------+-----+----------------+----------------+----------+---------------+---------------------+"
                    );
                }
            }
        }

        if (!found) {
            System.out.println("Không tìm thấy hóa đơn.");
            System.out.println(
                "+----------+----------------------+----------------------+---------------+-----+--------------+----------------+----------+---------------+---------------------+"
            );
        }
    }
    }
