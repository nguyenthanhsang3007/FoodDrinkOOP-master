package org.example;

import java.util.Scanner;

public class Product {
    private String id;
    private String name;
    private float price;
    private String description;
    private int quantity;
    private String category;
    private String hasExit;

    Scanner sc = new Scanner(System.in);
    public Product() {
        this.hasExit ="";
    }

    public Product(String id, String name, float price, String description, int quantity, String category ) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.description = description;
        this.quantity = quantity;
        this.category = category;
    }

    public String getHasExit(){return this.hasExit;}
    public void setHasExit(String hasExit) {this.hasExit = hasExit;}

    public void setId(String id) {
        while (id.trim().isEmpty()) {
            System.out.print("Invalid input, please re-enter the product ID: ");
            id = sc.nextLine();
        }
        this.id = id;
    }

    public void setName() {
        String nameTemp ;
        nameTemp = sc.nextLine();
        System.out.println("+ -------------------- +");
        nameTemp = nameTemp.trim();
        while(nameTemp.trim().isEmpty())
            {
                System.out.print("Chưa nhập tên sản phẩm, nhập lại: ");
                nameTemp = sc.nextLine();
                System.out.println("+ -------------------- +");
            }
        if(nameTemp.toLowerCase().trim().startsWith("exit")){
            setHasExit(nameTemp);
            return;
        }
        int i = 0;
        for(; i < nameTemp.length(); i++) {
            if(nameTemp.charAt(i) < ' ' || nameTemp.charAt(i) > ' ' &&nameTemp.charAt(i)  < '0' || nameTemp.charAt(i) > '9' && nameTemp.charAt(i) < 'A' || nameTemp.charAt(i) > 'Z' && nameTemp.charAt(i) < 'a' || nameTemp.charAt(i) > 'z'){
                System.out.println("Tên sản phẩm không được viết dấu");
                System.out.print("Nhập lại tên sản pẩm: ");
                nameTemp = sc.nextLine();
                System.out.println("+ -------------------- +");
                i = -1;
            }
        }
        this.name = nameTemp;
    }

//    public void setName(String name) {
//        while (name.trim().isEmpty()) {
//            System.out.print("Invalid input, please re-enter the product name: ");
//            name = sc.nextLine();
//        }
//    }

    public void setPrice(float price) {
        while (price < 0) {
            System.out.print("Nhập giá tiền phải nguyên dương, nhập lại: ");
            price = sc.nextFloat();
        }
        this.price = price;
    }

    public void setPrice() {
        String price_temp  ;
        price_temp = sc.nextLine();
        System.out.println("+ -------------------- +");
        int i = 0;
        while(price_temp.trim().isEmpty())
            {
                System.out.print("Chưa nhập giá sản phẩm, nhập lại: ");
                price_temp = sc.nextLine();
                System.out.println("+ -------------------- +");
            }
        if(price_temp.toLowerCase().trim().startsWith("exit")){
            setHasExit(price_temp);
            return;
        }
        for(; i < price_temp.length(); i++) {
            if(price_temp.charAt(i) < '0' || price_temp.charAt(i) > '9'){
                System.out.println("Chỉ được nhập các số từ 0 -> 9");
                System.out.print("Nhập lại giá : ");
                price_temp = sc.nextLine();
                System.out.println("+ -------------------- +");
                i = -1;
            }
        }
        this.price =Integer.parseInt(price_temp);
    }

    public void setDescription(String description) {
        System.out.println("+ -------------------- +");
        while (description.trim().isEmpty()) {
            System.out.print("Nhập sai mô tả, nhập lại: ");
            description = sc.nextLine();
            System.out.println("+ -------------------- +");
        }
        if(description.toLowerCase().trim().startsWith("exit")){
            setHasExit(description);
            return;
        }
        this.description = description;
    }

    public void setQuantity(int quantity) {
        while (quantity < 0) {
            System.out.print("Nhập sai số lượng, nhập lại: ");
            quantity = sc.nextInt();
        }
        this.quantity = quantity;
    }

    public void setQuantity() {
        String quantity_temp  ;
        quantity_temp = sc.nextLine();
        System.out.println("+ -------------------- +");
        while(quantity_temp.trim().isEmpty())
            {
                System.out.print("Chưa nhập số lượng sản phẩm, nhập lại: ");
                quantity_temp = sc.nextLine();
                System.out.println("+ -------------------- +");
            }
        if(quantity_temp.toLowerCase().trim().startsWith("exit")){
            setHasExit(quantity_temp);
            return;
        }
        int i = 0;
        for(; i < quantity_temp.length(); i++) {
            if(quantity_temp.charAt(i) < '0' || quantity_temp.charAt(i) > '9'){
                System.out.println("Chỉ được nhập các số từ 0 -> 9");
                System.out.print("Nhập lại số lượng : ");
                quantity_temp = sc.nextLine();
                System.out.println("+ -------------------- +");
                i = -1;
            }
        }
        this.quantity = Integer.parseInt(quantity_temp);
    }

    public void setCategory(String category) {
        while (category.trim().isEmpty()) {
            System.out.print("Nhập sai phân loại sản phẩm, nhập lại: ");
            category = sc.nextLine();
        }
        this.category = category;
    }

    public String getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public float getPrice() {
        return this.price;
    }

    public String getDescription() {
        return this.description;
    }

    public int getQuantity() {
        return this.quantity;
    }

    public String getCategory() {
        return category;
    }

    public void inputInfo() {
        System.out.println("+ -------------------- +");
        System.out.print("Nhập tên sản phẩm: ");
        setName();
        if(getHasExit().equals("exit"))
            return;
        System.out.print("Nhập giá sản phẩm: ");
        setPrice();
                if(getHasExit().equals("exit"))
            return;
        System.out.print("Nhập mô tả sản phẩm: ");
        setDescription(sc.nextLine());
                if(getHasExit().equals("exit"))
            return;
        System.out.print("Nhập số lượng: ");
        setQuantity();
                if(getHasExit().equals("exit"))
            return;
        System.out.println("F: Thức ăn/ D: Đồ uống");
        System.out.print("Nhập phân loại sản phẩm (F/D) : ");
        String temp = sc.nextLine();
        if(temp.toLowerCase().trim().startsWith("exit")){
            setHasExit(temp);
            return;
        }
        while(true){
            if(temp.equals("F"))
            {
                setId("FD");
                setCategory("Thức Ăn");
                break;
            }
            else
            if(temp.equals("D"))
            {
                setId("DK");
                setCategory("Đồ Uống");
                break;
            }
            else{
                System.out.println("F: Thức ăn/ D: Đồ uống");
                System.out.print("Nhập phân loại sản phẩm (F/D) : ");
                temp = sc.nextLine();
                System.out.println("+ -------------------- +");
            }

        }
    }

    public String toString() {
        return String.format("| %10s | %15s | %10.2f | %10s | %5d | %10s |", id, name, price, description, quantity,category);
    }

    public String toCVString() {
        return String.format("%s, %s, %f, %s, %d, %s", id, name, price, description, quantity, category);
    }

    public void outputInfo() {
        System.out.println(toString());
    }

    public void toViewDescription() {
        System.out.println(getDescription());
    }

    public String toMenuString() {
        return String.format("| %-4s %-20s %-10d %10.2f |", id, name,quantity, price);
    }

    public String toOrderString() {
        return String.format("%s, %d, ", id, quantity);
    }

    public void addItem() {

    }
}


