package org.example;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;

public class Supplier{
  private String[] supplierData ;
  Scanner sc = new Scanner(System.in);

  public Supplier() {};


  public void setSupplierData(String[] supplierData){
    this.supplierData = supplierData  ;
  }

  public String[] getSupplierData(){
    return supplierData;
  }

  public boolean boolHasProductSupplier(String productId){
    readFileSupplierData();
    for(int i = 0 ; i < supplierData.length ; i++){
      if(productId.equals(supplierData[i].split(",")[0].trim()))
        {
          System.out.println("Id " +  productId + " Tồn tại " );
          return true;
        }
    }
    System.out.println(productId + "không tồn tại");
    return false;
  }

  public int intHasProductSupplier(String productId){
    readFileSupplierData();
    for(int i = 0 ; i < supplierData.length ; i++){
      if(productId.equals(supplierData[i].split(",")[0].trim()))
        {
          System.out.println(  supplierData[i].split(",")[1].trim() + " vào hàng chờ vận chuyển"  );
          System.out.println("+ ----------------------------------------------------------------------------------------------------- +");
          return i;
        }
    }
    System.out.println("Chúng tôi không có sản phẩm mang Id :  " +  productId );
    System.out.println("+ ----------------------------------------------------------------------------------------------------- +");
    return -1;
  }

  public void readFileSupplierData(){
    File supplierFile = new File("data\\supplier.txt");
    String[] supplier = new String[0];
    try{
      Scanner readFile = new Scanner(supplierFile);
      while(readFile.hasNextLine()){
        String line = readFile.nextLine();
        line = line.trim();
        if(line.split(",").length < 6)
          continue;
        supplier = Arrays.copyOf(supplier, supplier.length + 1);
        supplier[supplier.length - 1] = line;
      }
      readFile.close();
    }
    catch(FileNotFoundException e){
      e.printStackTrace();
    }
    setSupplierData(supplier);
  }

  public void displayListSupplier(){
    readFileSupplierData();
    int i ;
    for(i = 0 ; i < supplierData.length ; i++)
    {
      String[] infor = supplierData[i].split(",");
      System.out.printf("| %-4s | %-20s | %10.2f |\n", infor[0].trim(),infor[1].trim(),Float.parseFloat(infor[2].trim()));
    }
  }
}
