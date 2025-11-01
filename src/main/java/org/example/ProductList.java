package org.example;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;

public class ProductList implements funtion, ConnectDatabase {
    private Product[] productList;

    Scanner sc = new Scanner(System.in);

    public ProductList() {
        productList = new Product[0];
    }

    public void setProductList(Product[] productList) {
        this.productList = productList;
    }

    public Product[] getProductList() {
        return productList;
    }

    public void readFromFile() {
        try {
            productList = new Product[0];

            File myObj = new File("data\\inventory.txt");
            Scanner myReader = new Scanner(myObj);

            while (myReader.hasNextLine()) {
                Product newProductList[] = Arrays.copyOf(productList, productList.length + 1);

                String data = myReader.nextLine();

                // Nếu dòng trong file trống thì bỏ qua
                if (data.equalsIgnoreCase("")) {
                    continue;
                }

                String[] parts = data.split(",");

                Product product;

                if (parts[0].trim().contains("FD")) {
                    product = new FoodItem(parts[0].trim(), parts[1].trim(), Float.parseFloat(parts[2].trim()), parts[3].trim(),
                            Integer.parseInt(parts[4].trim()), parts[5].trim(), 0);
                } else if (parts[0].trim().contains("DK")) {
                    product = new DrinkItem(parts[0].trim(), parts[1].trim(), Float.parseFloat(parts[2].trim()), parts[3].trim(),
                            Integer.parseInt(parts[4].trim()), parts[5].trim(), 0, 0, 0);
                } else {
                    continue;
                }

                newProductList[newProductList.length - 1] = product;
                productList = newProductList;
            }

            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    //  Cập nhập lại file
    public void writeToFile() {
        try {
            FileWriter myWriter = new FileWriter("data\\inventory.txt");

            for (int i = 0; i < productList.length; i++) {
                if (productList[i] instanceof FoodItem) {
                    FoodItem foodItem = (FoodItem) productList[i];
                    myWriter.write(foodItem.toCVString());
                    myWriter.write("\n");
                } else if (productList[i] instanceof DrinkItem) {
                    DrinkItem drinkItem = (DrinkItem) productList[i];
                    myWriter.write(drinkItem.toCVString());
                    myWriter.write("\n");
                }
            }

            myWriter.close();
            // System.out.println("Successfully wrote to the file.");
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public void output() {
        for (int i= 0 ; i < productList.length; i++) {
            System.out.println(productList[i]);
        }
    }

    public void outputFoodMenu() {
        for (int i = 0; i < productList.length; i++) {
            if (productList[i] instanceof FoodItem) {
                System.out.println(productList[i].toMenuString());
            }
        }
    }

    public void outputDrinkMenu() {
        for (int i = 0; i < productList.length; i++) {
            if (productList[i] instanceof DrinkItem) {
                System.out.println(productList[i].toMenuString());
            }
        }
    }

    public void add(Object object) {
        if (object instanceof Product) {
            Product newProductList[] = Arrays.copyOf(productList, productList.length + 1);

            Product product = (Product) object;

            newProductList[newProductList.length - 1] = product;
            productList = newProductList;
        } else {
            System.out.println("Invalid object type.");
        }
    }

    public void remove() {
        System.out.print("Enter id: ");
        String id = sc.nextLine();
        Product[] newProductList = new Product[productList.length - 1];
        int index = 0;
        for (int i = 0; i < productList.length; i++) {
            if (!productList[i].getId().equals(id)) {
                newProductList[index] = productList[i];
                index++;
            }
        }
        productList = newProductList;
    }

    public void search() {

    }

    public void edit() {

    }
}