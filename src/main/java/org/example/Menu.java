package org.example;

import java.util.Scanner;

public class Menu {
    Scanner sc = new Scanner(System.in);
    AccountList al = new AccountList();
    String reply;

    public void run() {
        
int choice;

do {
    selectRoleMenu();
    System.out.print("Lựa chọn của bạn: ");
    choice = Integer.parseInt(sc.nextLine().trim());

    switch (choice) {

        case 1:
            cashierLoginMenu(); // xử lý đăng nhập
            if (reply.equalsIgnoreCase("exit")) break;
            if (!reply.equalsIgnoreCase("cashier")) {
                System.out.println("Quyền truy cập không hợp lệ!");
                break;
            }

            System.out.println("Đăng nhập hợp lệ!");

            while (true) {
                System.out.println("+ ------------------------------- +");
                System.out.println("|         MENU NHÂN VIÊN          |");
                System.out.println("+ ------------------------------- +");
                System.out.println("| 1. Tạo đơn hàng mới             |");
                System.out.println("| 2. Xem tất cả hóa đơn           |");
                System.out.println("| 3. Tìm hóa đơn theo mã          |");
                System.out.println("| 0. Đăng xuất                    |");
                System.out.println("+ ------------------------------- +");
                System.out.print("Lựa chọn của bạn: ");

                String choiceNV = sc.nextLine().trim();
                ListBill listBill = new ListBill();

                switch (choiceNV) {
                    case "1":
                        CustomerList cl = new CustomerList();
                        cl.readFromFile();

                        System.out.println("Nhấn Enter nếu là khách mới, nhập 'exit' để hủy");
                        System.out.print("Nhập mã khách hàng: ");
                        String idCustomer = sc.nextLine().trim();

                        while (!cl.isExistId(idCustomer) && 
                               !idCustomer.isEmpty() && 
                               !idCustomer.equalsIgnoreCase("exit")) {
                            System.out.print("Mã khách không tồn tại, nhập lại: ");
                            idCustomer = sc.nextLine().trim();
                        }

                        if (idCustomer.equalsIgnoreCase("exit")) break;

                        Customer cs = new Customer();
                        if (idCustomer.isEmpty()) {
                            cs.input();
                            if (cs.getNameCustomer().equalsIgnoreCase("exit") ||
                                cs.getPhoneCustomer().equalsIgnoreCase("exit")) {
                                break;
                            }
                            cl.add(cs);
                            idCustomer = cs.getIdCustomer();
                        }

                        ProductList pl = new ProductList();
                        OrderList ol = new OrderList();
                        Order order = new Order();
                        pl.readFromFile();
                        ol.readFromFile();

                        customerMenu(pl);
                        order.newOrder();
                        order.setCustomer(idCustomer);
                        order.setWaiter(order.getRandomWaiterId().trim());

                        if (order.getOrder().length != 0) {
                            ol.add(order);
                            cl.writeToFile();
                            ol.writeToFile();
                            Bill bill = new Bill();
                            bill.setDataLastOrder();
                            System.out.println(bill.toString());
                            bill.writeToFile();
                            System.out.println("Nhấn Enter để tiếp tục...");
                            sc.nextLine();
                        }
                        break;

                    case "2":
                        listBill.printAllBills();
                        System.out.println("Nhấn Enter để tiếp tục...");
                        sc.nextLine();
                        break;

                    case "3":
                        listBill.searchBillById();
                        System.out.println("Nhấn Enter để tiếp tục...");
                        sc.nextLine();
                        break;

                    case "0":
                        System.out.println("Đăng xuất thành công!");
                        break;

                    default:
                        System.out.println("Lựa chọn không hợp lệ!");
                }

                if (choiceNV.equals("0")) break;
            }
            break;


        case 2:
            adminLoginMenu();
            if (reply.equalsIgnoreCase("exit")) break;
            if (!reply.equalsIgnoreCase("admin")) {
                System.out.println("Quyền truy cập không hợp lệ!");
                break;
            }
            System.out.println("Đăng nhập hợp lệ!");
            adminMenu();
            break;

        case 0:
            System.out.println("Thoát chương trình...");
            break;

        default:
            System.out.println("Lựa chọn không hợp lệ! Vui lòng thử lại.");
    }

} while (choice != 0);

    }

    public void selectRoleMenu() {
        System.out.println("+ ------------------------------- +");
        System.out.println("|       Welcome to F&D Store      |");
        System.out.println("+ ------------------------------- +");
        System.out.println("| 1. Nhân viên                    |");
        System.out.println("| 2. Quản trị viên                |");
        System.out.println("| 0. Thoát                        |");
        System.out.println("+ ------------------------------- +");
        System.out.print("Lựa chọn của bạn: ");
    }

    public void customerMenu(ProductList productList) {
        System.out.println("+ ----------------------------------------------- +");
        System.out.println("|                  MENU: F&D                      |");
        System.out.println("+ ----------------------------------------------- +");
        System.out.println("| Food                    Số lượng         Giá    |");
        productList.outputFoodMenu();
        System.out.println("+ ----------------------------------------------- +");
        System.out.println("| Drink                   Số lượng         Giá    |");
        productList.outputDrinkMenu();
        System.out.println("+ ----------------------------------------------- +");
    }

    public void adminMenu() {
        System.out.println("+ ------------------------------- +");
        System.out.println("|          Quản trị viên          |");
        System.out.println("+ ------------------------------- +");
        System.out.println("| 1. Quản lý kho                  |");
        System.out.println("| 2. Quản lý nhân viên            |");
        System.out.println("| 3. Quản lý khách hàng           |");
        System.out.println("| 4. Thống kê                     |");
        System.out.println("| 0. Quay lại                     |");
        System.out.println("+ ------------------------------- +");

        Inventory in = new Inventory();
        EmployeeList el = new EmployeeList();
        int select;
        String temp;
        System.out.print("Lựa chọn của bạn: ");
        temp = sc.nextLine();
        while (!isNumber(temp)) {
            System.out.print("Lựa chọn không hợp lệ, nhập lại: ");
            temp = sc.nextLine();
        }
        select = Integer.parseInt(temp);
        while (select != 0) {
            switch (select) {
                case 1:
                    System.out.println("+ ------------------------------- +");
                    System.out.println("|         Kho - F&D Store         |");
                    System.out.println("+ ------------------------------- +");
                    System.out.println("| 1. Xem kho                      |");
                    System.out.println("| 2. Thêm sản phẩm                |");
                    System.out.println("| 3. Xóa sản phầm                 |");
                    System.out.println("| 4. Tìm kiếm sản phẩm            |");
                    System.out.println("| 5. Sửa thông tin sản phẩm       |");
                    System.out.println("| 0. Quay lại                     |");
                    System.out.println("+ ------------------------------- +");
                    System.out.print("Lựa chọn của bạn: ");
                    temp = sc.nextLine();
                    while (!isNumber(temp)) {
                        System.out.print("Lựa chọn không hợp lệ, nhập lại: ");
                        temp = sc.nextLine();
                    }
                    select = Integer.parseInt(temp);
                    while (select != 0) {
                        switch (select) {
                            case 1:
                                in.displayItemInventory();
                                break;
                            case 2:
                                in.add(in);
                                break;
                            case 3:
                                in.remove();
                                break;
                            case 4:
                                in.search();
                                break;
                            case 5:
                                in.edit();
                                break;
                            default:
                                System.out.print("Lựa chọn không hợp lệ");
                                break;
                        }
                        System.out.println("+ ------------------------------- +");
                        System.out.println("|          Kho - F&D Store        |");
                        System.out.println("+ ------------------------------- +");
                        System.out.println("| 1. Xem kho                      |");
                        System.out.println("| 2. Thêm sản phẩm                |");
                        System.out.println("| 3. Xóa sản phầm                 |");
                        System.out.println("| 4. Tìm kiếm sản phẩm            |");
                        System.out.println("| 5. Sửa thông tin sản phẩm       |");
                        System.out.println("| 0. Quay lại                     |");
                        System.out.println("+ ------------------------------- +");
                        System.out.print("Lựa chọn của bạn: ");
                        temp = sc.nextLine();
                        while (!isNumber(temp)) {
                            System.out.print("Lựa chọn không hợp lệ, nhập lại: ");
                            temp = sc.nextLine();
                        }
                        select = Integer.parseInt(temp);
                    }
                    break;
                case 2:
                    System.out.println("+ ------------------------------- +");
                    System.out.println("|          Quản lí nhân viên      |");
                    System.out.println("+ ------------------------------- +");
                    System.out.println("| 1. Hiển thị danh sách nhân viên |");
                    System.out.println("| 2. Thêm nhân viên mới           |");
                    System.out.println("| 3. Xóa nhân viên                |");
                    System.out.println("| 4. Hiển thị lương của nhân viên |");
                    System.out.println("| 5. Tìm kiếm nhân viên           |");
                    System.out.println("| 0. Quay lại                     |");
                    System.out.println("+ ------------------------------- +");
                    temp = sc.nextLine();
                    while (!isNumber(temp)) {
                        System.out.print("Lựa chọn không hợp lệ, nhập lại: ");
                        temp = sc.nextLine();
                    }
                    select = Integer.parseInt(temp);
                    while (select != 0) {
                        switch (select) {
                            case 1:
                                el.displayEmployeeList();
                                break;
                            case 2:
                                el.add(new Object());
                                break;
                            case 3:
                                el.remove();
                                break;
                            case 4:
                                el.displaySalary();
                                break;
                            case 5:
                                el.menuSearch();
                            default:
                                System.out.print("Lựa chọn không hợp lệ");
                                break;
                        }
                        System.out.println("+ ------------------------------- +");
                        System.out.println("|          Quản lí nhân viên      |");
                        System.out.println("+ ------------------------------- +");
                        System.out.println("| 1. Hiển thị danh sách nhân viên |");
                        System.out.println("| 2. Thêm nhân viên mới           |");
                        System.out.println("| 3. Xóa nhân viên                |");
                        System.out.println("| 4. Hiển thị lương của nhân viên |");
                        System.out.println("| 5. Tìm kiếm nhân viên           |");
                        System.out.println("| 0. Quay lại                     |");
                        System.out.println("+ ------------------------------- +");
                        temp = sc.nextLine();
                        while (!isNumber(temp)) {
                            System.out.print("Lựa chọn không hợp lệ, nhập lại: ");
                            temp = sc.nextLine();
                        }
                        select = Integer.parseInt(temp);
                    }
                    break;
                case 3:
                    adminCustomerMenu();
                    temp = sc.nextLine();
                    while (!isNumber(temp)) {
                        System.out.print("Lựa chọn không hợp lệ, nhập lại: ");
                        temp = sc.nextLine();
                    }
                    select = Integer.parseInt(temp);
                    while (select != 0) {
                        // Lấy thông tin từ file customer.txt
                        CustomerList cl = new CustomerList();
                        cl.readFromFile();

                        switch (select) {
                            // Hiển thị danh sách khách hàng
                            case 1:
                                System.out.println("+ ------------------------------------------------------- +");
                                System.out.println(
                                        String.format("| %-5s | %-20s | %11s | %10s |", "ID", "Họ tên", "SĐT", "Điểm"));
                                System.out.println("+ ------------------------------------------------------- +");
                                cl.output();
                                System.out.println("+ ------------------------------------------------------- +");
                                System.out.print("Nhấn `enter` để tiếp tục...");
                                sc.nextLine();
                                break;
                            // Thêm khách hàng
                            case 2:
                                Customer cs = new Customer();
                                cl.add(cs);
                                // cl.writeToFile();
                                System.out.print("Nhấn `enter` để tiếp tục...");
                                sc.nextLine();
                                break;
                            // Xóa khách hàng
                            case 3:
                                System.out.println("Nhập `exit` để hủy bỏ, `...` để xem danh sách");
                                cl.remove();
                                // cl.writeToFile("customer.txt");
                                System.out.print("Nhấn `enter` để tiếp tục...");
                                sc.nextLine();
                                break;
                            // Tìm kiếm khách hàng
                            case 4:
                                System.out.println(
                                        "Thông tin tìm kiếm là id\\tên\\sđt khách hàng (Nhập `exit` để hủy bỏ, `...` để xem danh sách)");
                                cl.search();
                                System.out.print("Nhấn `enter` để tiếp tục...");
                                sc.nextLine();
                                break;
                            case 5:
                                cl.edit();
                                System.out.print("Nhấn `enter` để tiếp tục...");
                                sc.nextLine();
                                break;
                            default:
                                System.out.print("Lựa chọn không hợp lệ");
                                break;
                        }
                        adminCustomerMenu();
                        temp = sc.nextLine();
                        while (!isNumber(temp)) {
                            System.out.print("Lựa chọn không hợp lệ, nhập lại: ");
                            temp = sc.nextLine();
                        }
                        select = Integer.parseInt(temp);
                    }
                    break;
                case 4:
                    statisticMenu();
                    break;
                default:
                    System.out.print("Lựa chọn không hợp lệ, nhập lại: ");
                    temp = sc.nextLine();
                    while (!isNumber(temp)) {
                        System.out.print("Lựa chọn không hợp lệ, nhập lại: ");
                        temp = sc.nextLine();
                    }
                    select = Integer.parseInt(temp);
                    break;
            }
            System.out.println("+ ------------------------------- +");
            System.out.println("|          Quản trị viên          |");
            System.out.println("+ ------------------------------- +");
            System.out.println("| 1. Quản lý kho                  |");
            System.out.println("| 2. Quản lý nhân viên            |");
            System.out.println("| 3. Quản lý khách hàng           |");
            System.out.println("| 4. Thống kê                     |");
            System.out.println("| 0. Quay lại                     |");
            System.out.println("+ ------------------------------- +");
            System.out.print("Nhập lựa chọn : ");
            temp = sc.nextLine();
            while (!isNumber(temp)) {
                System.out.print("Lựa chọn không hợp lệ, nhập lại: ");
                temp = sc.nextLine();
            }
            select = Integer.parseInt(temp);
        }
    }

    public void adminCustomerMenu() {
        System.out.println("+ ----------------------------------------- +");
        System.out.println("|         QL Khách hàng - F&D Store         |");
        System.out.println("+ ----------------------------------------- +");
        System.out.println("| 1. Xem danh sách khách hàng               |");
        System.out.println("| 2. Thêm khách hàng                        |");
        System.out.println("| 3. Xóa khách hàng                         |");
        System.out.println("| 4. Tìm kiếm khách hàng                    |");
        System.out.println("| 5. Sửa thông tin khách hàng               |");
        System.out.println("| 0. Quay lại                               |");
        System.out.println("+ ----------------------------------------- +");
        System.out.print("Lựa chọn của bạn: ");
    }

    public void adminLoginMenu() {
        System.out.println("+ -------------------------- +");
        System.out.println("|       QTV - F&D Store      |");
        System.out.println("+ -------------------------- +");
        reply = al.login();
    }

    public void cashierLoginMenu() {
        System.out.println("+ -------------------------- +");
        System.out.println("|    Nhân viên - F&D Store   |");
        System.out.println("+ -------------------------- +");
        reply = al.login();
    }

    public void statisticMenu() {
        ListBill listBill = new ListBill();

        System.out.println("+ ---------------------------------+");
        System.out.println("|            Thống kê              |");
        System.out.println("+ ---------------------------------+");
        System.out.println("| 1. Tất cả hoá đơn                |");
        System.out.println("| 2. Tìm hóa đơn theo mã           |");
        System.out.println("| 3. Doanh thu                     |");
        System.out.println("| 4. Số lượng hàng bán được        |");
        System.out.println("| 5. Nhân viên                     |");
        System.out.println("| 6. Đơn hàng của khách hàng       |");
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
                    listBill.printAllBills();
                    break;
                case 2:
                    listBill.searchBillById();
                    break;
                case 3:
                    listBill.printfDoanhTHu();
                    break;
                case 4:
                    listBill.displayProductSales();
                    break;
                case 5:
                    thongKeNhanVien();
                    break;
                case 6:
                    listBill.displayCustomerPurchaseCount();
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
            System.out.println("| 5. Nhân viên                     |");
            System.out.println("| 6. Đơn hàng của khách hàng       |");
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

    private void thongKeNhanVien() {
        EmployeeList em = new EmployeeList();
        ListBill listBill = new ListBill();

        System.out.println("+ ---------------------------------+");
        System.out.println("|       Thống kê về nhân viên      |");
        System.out.println("+ ---------------------------------+");
        System.out.println("| 1. Số đơn hàng nhân viên bán được|");
        System.out.println("| 2. Đơn hàng nhân viên phục vụ    |");
        System.out.println("| 3. Lương Nhân Viên               |");
        System.out.println("| 4. Giờ làm việc của nhân viên    |");
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
                    listBill.numberOfSalesOrders();
                    break;
                case 2:
                    OrderList ol = new OrderList();
                    ol.statisticsWaiter();
                    break;
                case 3:
                    em.luongMax();
                    System.out.println("");
                    em.luongMin();
                    break;
                case 4:
                    em.timeWorkMax();
                    System.out.println("");
                    em.timeWorkMin();
                    break;
                default:
                    System.out.println("Không có lựa chọn này.");
                    break;
            }
            System.out.println("+ ---------------------------------+");
            System.out.println("|       Thống kê về nhân viên      |");
            System.out.println("+ ---------------------------------+");
            System.out.println("| 1. Số đơn hàng nhân viên bán được|");
            System.out.println("| 2. Đơn hàng nhân viên phục vụ    |");
            System.out.println("| 3. Lương Nhân Viên               |");
            System.out.println("| 4. Giờ làm việc của nhân viên    |");
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

    public boolean isClosed() {
        Inventory i = new Inventory();
        i.readFromFileInventory();

        EmployeeList el = new EmployeeList();
        el.readFromFile();

        if (i.getItemInventory().length == 0 || el.getSize() == 0) {
            System.out.println("Cửa hàng đóng cửa, vui lòng quay lại sau!");
            return true;
        }

        return false;
    }
}
