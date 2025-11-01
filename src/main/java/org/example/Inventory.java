package org.example;

import java.io.File;
import java.io.FileWriter;
import java.util.Arrays;
import java.util.Scanner;

public class Inventory implements funtion {

  Scanner sc = new Scanner(System.in);
  private String[] itemInventory ;
  private PurchaseOrder purchaseOrder;


  public Inventory(){
    purchaseOrder = new PurchaseOrder();
    itemInventory =new String[0];
    readFromFileInventory();
  }

  public PurchaseOrder getPurchaseOrder(){return purchaseOrder;}

  public String[] getItemInventory(){
    readFromFileInventory();
    return itemInventory;
  }

  public void add(Object obj){
    readFromFileInventory();
    int select ;
    System.out.println("+ ------------------------------------- +");
    System.out.println("|         Thêm sản phẩm vào kho         |");
    System.out.println("+ ------------------------------------- +");
    System.out.println("| 1. Thêm sản phẩm từ nhà cung cấp      |");
    System.out.println("| 2. Thêm sản phẩm mới                  |");
    System.out.println("| 0. Quay lại                           |");
    System.out.println("+ ------------------------------------- +");
    System.out.print("Lựa chọn của bạn: ");
    String tempp ;
    tempp = sc.nextLine();
      System.out.println("+ ----------------------------------------------------------------------------------------------------- +");
      while (!isNumber(tempp)) {
          System.out.println("Lựa chọn không hợp lệ !");
          System.out.print("Nhập lại lựa chọn : ");
          tempp = sc.nextLine();
          System.out.println("+ ----------------------------------------------------------------------------------------------------- +");
      }
    select = Integer.parseInt(tempp);
    while (select != 0) {
      switch (select) {
        case 1:
          addProductFromSupplier();
          break;
        case 2 :
          addNewProduct();
          break;
        default:
          System.out.println("Không có lựa chọn này");
          System.out.println("+ ----------------------------------------------------------------------------------------------------- +");
          break;
      }
        System.out.println("+ ------------------------------------- +");
        System.out.println("|         Thêm sản phẩm vào kho         |");
        System.out.println("+ ------------------------------------- +");
        System.out.println("| 1. Thêm sản phẩm từ nhà cung cấp      |");
        System.out.println("| 2. Thêm sản phẩm mới                  |");
        System.out.println("| 0. Quay lại                           |");
        System.out.println("+ ------------------------------------- +");
        System.out.print("Lựa chọn của bạn: ");
        tempp = sc.nextLine();
      System.out.println("+ ----------------------------------------------------------------------------------------------------- +");
      while (!isNumber(tempp)) {
          System.out.println("Lựa chọn không hợp lệ !");
          System.out.print("Nhập lại lựa chọn : ");
          tempp = sc.nextLine();
          System.out.println("+ ----------------------------------------------------------------------------------------------------- +");
      }
        select = Integer.parseInt(tempp);
    }
    updateFileInventory();
  }

  private void addProductFromSupplier(){
          System.out.println("+ ---------------------------------------- +");
          System.out.println(String.format("| %-4s | %-20s | %10s |", "ID", "Tên sản phẩm", "Giá"));
          System.out.println("+ ---------------------------------------- +");
          purchaseOrder.getSupplier().displayListSupplier();
          System.out.println("+ ---------------------------------------- +");

          purchaseOrder.selectSupplierProduct();
          String[] temp= purchaseOrder.getListPurchase();
          if(itemInventory.length == 0)
            return;
          System.out.println("Đang phân loại...");
          for(int i=0; i<temp.length; i++){
            int index = hadItem( temp[i].split(",")[1].trim());
            // == -1 is new product// != -1 is old product , increase quantity
            if(index == -1)
            {
              System.out.println(temp[i].split(",")[1] + " là một sản phẩm mới ");
              itemInventory = Arrays.copyOf(itemInventory , itemInventory.length + 1);
              itemInventory[itemInventory.length - 1] = temp[i];
              updateFileInventory();
              readFromFileInventory();
            }
            else{
              System.out.println(temp[i].split(",")[1] + " là một sản phẩm đã có sẵn.Tăng số lượng sản phẩm ");
              int a = Integer.parseInt(temp[i].split(",")[4]);
              int b = Integer.parseInt(itemInventory[index].split(",")[4].trim());
              int c = a + b;
              String[] temp1 = itemInventory[index].split(",");
              temp1[4] = Integer.toString(c);
              itemInventory[index] = "";
              for(int j = 0; j < temp1.length; j++){
                itemInventory[index] = itemInventory[index] +  temp1[j] + ",";
              }
              updateFileInventory();
              readFromFileInventory();
            }
          }
        System.out.println("Đã phân loại xong.");
    System.out.println("+ ----------------------------------------------------------------------------------------------------- +");
  }

  private void addNewProduct(){
    int count1 = 0;
    String continues = "1";
    do{
      if(count1 > 0)
      {
        System.out.print("Bạn có muốn nhập thêm sản phẩm mới nữa không ? (0/1):");
        continues = sc.nextLine();
        if(continues.equals("0"))
          break;
      }
                ++count1;
      System.out.println("Nhập thông tin sản phẩm mới(Nếu không muôn thêm sản phẩm này  nữa hãy nhập 'exit')");
          Product newProduct = new Product();
          newProduct.inputInfo();
          if(newProduct.getHasExit().equals("exit"))
            continue;
          String tempItem = newProduct.toCVString();
          String[] infor = tempItem.split(",");
          if(infor[0].trim().equals("FD"))
            {
              int count = 0;
              for(int i = 0; i < itemInventory.length; i++)
                if(itemInventory[i].trim().startsWith("FD"))
                  {
                    String id_stt = itemInventory[i].trim().split(",")[0];
                    int countTemp = Integer.parseInt(id_stt.substring(2));
                    if(countTemp  >count)
                      count = countTemp;
                  }
              if(count <= 5)
                infor[0] = "FD"  + Integer.toString(6);
              else infor[0] = "FD"  + Integer.toString(count +1);
            }
          if(infor[0].trim().equals("DK"))
          {
            int count = 0;
              for(int i = 0; i < itemInventory.length; i++)
                if(itemInventory[i].trim().startsWith("DK"))
                  {
                    String id_stt = itemInventory[i].trim().split(",")[0];
                    int countTemp = Integer.parseInt(id_stt.substring(2));
                    if(countTemp  > count)
                      count = countTemp;
                  }
              if(count <= 5)
                infor[0] = "DK"  + Integer.toString(6);
              else infor[0] = "DK"  + Integer.toString(count +1);
          }
          tempItem = infor[0];
          int indexItem = hadItem(infor[1].trim());
          for(int j = 1; j < infor.length ; j++)
            tempItem = tempItem + "," + infor[j] ;
          if(indexItem == -1){
              System.out.println( "Thông tin sản phẩm vừa nhập :  " + tempItem);
              System.out.println("Đây là một sản phẩm mới");
              itemInventory = Arrays.copyOf(itemInventory , itemInventory.length + 1);
              itemInventory[itemInventory.length - 1] = tempItem;
              updateFileInventory();
              readFromFileInventory();
          }
          else{
            System.out.println( "Thông tin sản phẩm vừa nhập :  " + tempItem);
            System.out.println("Sản phẩm đã có sẵn");
            System.out.println("+ ----------------------------------------------------------------------------------------------------- +");
          }

    }while(true);
  }

  private int hadItem(String name){
    if(itemInventory.length == 0){
      return -1;
    }
    for(int i = 0; i < itemInventory.length; i++){
      if(itemInventory[i].split(",")[1].trim().equals(name)){
          return i;
      }
    }
    return -1;
  }

  public void remove(){
    displayItemInventory();
    int select;
    while(true)
    {
      boolean deleted = false;
      String id;
      System.out.print("Nhập id sản phẩm cần xoá : ");
      id = sc.nextLine();
      System.out.println("+ ----------------------------------------------------------------------------------------------------- +");

        for(int i = 0  ; i < itemInventory.length ; i++){
        if(itemInventory[i].split(",")[0].trim().equals(id)){
          {
            deleted =true;
            for(int j = i ; j < itemInventory.length - 1 ; j++)
              itemInventory[j] = itemInventory[j + 1];
            itemInventory = Arrays.copyOf(itemInventory , itemInventory.length - 1);
            updateFileInventory();
            break;
          }
        }
      }
      if(deleted)
      {
        System.out.println("Sản phẩm đã được xoá");
        System.out.println("+ ----------------------------------------------------------------------------------------------------- +");
      }
      else
      {
        System.out.println("Sản phẩm không tồn tại");
        System.out.println("+ ----------------------------------------------------------------------------------------------------- +");
      }
      System.out.print("Bạn có muốn xoá tiếp không ? (0/1) : ");
      String tempp ;
      tempp = sc.nextLine();
      System.out.println("+ ----------------------------------------------------------------------------------------------------- +");
      while (!isNumber(tempp)) {
          System.out.println("Lựa chọn không hợp lệ !");
          System.out.print("Nhập lại lựa chọn : ");
          tempp = sc.nextLine();
          System.out.println("+ ----------------------------------------------------------------------------------------------------- +");
      }
      select = Integer.parseInt(tempp);
      if(select == 0) break;
    }
    
  }

  public void search(){
    readFromFileInventory();
    if(itemInventory.length == 0){
      System.out.println("Kho trống");
      return ;
    }
    String name;
    System.out.println("Nhập tên sản phẩm cần tìm kiếm(nhập \"exit\" để dừng, nhập \"...\" hiển thị tất cả) ");
    System.out.print("Nhập: ");
    name = sc.nextLine();
    System.out.println("+ ----------------------------------------------------------------------------------------------------- +");

    name = name.toLowerCase();
    while(true){
      if(name.equals("exit")) return;
      if(name.equals("...")){
        System.out.println("Kết quả tìm kiếm : ");
      System.out.println("+ ----------------------------------------------------------------------------------------------------- +");
      System.out.println(String.format("| %-4s | %-20s | %-10s | %-5s | %-50s |", "Id", "Tên sản phẩm", "Giá", "SL", "Mô tả"));
      System.out.println("+ ----------------------------------------------------------------------------------------------------- +");
        for(int i = 0 ; i < itemInventory.length ; i++)
        {
          String[] infor = itemInventory[i].split(",");
          System.out.println(toString(infor[0].trim() , infor[1].trim() , infor[2].trim(),infor[4].trim(),infor[3].trim()));
        }

      }
      else
      {
        int count = 0;
        System.out.println("Kết quả tìm kiếm : ");
        System.out.println("+ ----------------------------------------------------------------------------------------------------- +");
        System.out.println(String.format("| %-4s | %-20s | %-10s | %-5s | %-50s |", "Id", "Tên sản phẩm", "Giá", "SL", "Mô tả"));
        System.out.println("+ ----------------------------------------------------------------------------------------------------- +");
        for(int i = 0 ; i < itemInventory.length ; i++){
          if(itemInventory[i].toLowerCase().split(",")[1].trim().contains(name.toLowerCase()))
            {
              String[] infor = itemInventory[i].split(",");
              System.out.println(toString(infor[0].trim() , infor[1].trim() , infor[2].trim(),infor[4].trim(),infor[3].trim()));
              ++count;
            }
          
        }
        if(count == 0)
        {
          System.out.println("Không có kết quả");
          System.out.println("+ ----------------------------------------------------------------------------------------------------- +");
        }
      }
    System.out.println("Nhập tên sản phẩm cần tìm kiếm(nhập \"exit\" để dừng,nhập \"...\" hiển thị tất cả) ");
    System.out.print("Nhập : ");
    name = sc.nextLine();
    System.out.println("+ ----------------------------------------------------------------------------------------------------- +");

    name = name.toLowerCase();
    }
  }

  public void edit(){
    if(itemInventory.length == 0){
      System.out.println("Kho trống");
      return;
    }
      System.out.println("+ ------------------------------- +");
      System.out.println("|              Sửa                |");
      System.out.println("+ ------------------------------- +");
      System.out.println("| 1. Thay đổi giá sản phẩm        |");
      System.out.println("| 2. Thay đổi số lượng sản phẩm   |");
      System.out.println("| 3. Thay đổi mô tả sản phẩm      |");
      System.out.println("| 0.Thoát                         |");
      System.out.println("+ ------------------------------- +");
      int select ;
      System.out.print("Nhập lựa chọn : ");
      String tempp ;
      tempp = sc.nextLine();
      System.out.println("+ ----------------------------------------------------------------------------------------------------- +");
      while (!isNumber(tempp)) {
          System.out.println("Lựa chọn không hợp lệ !");
          System.out.print("Nhập lại lựa chọn : ");
          tempp = sc.nextLine();
          System.out.println("+ ----------------------------------------------------------------------------------------------------- +");
      }
      select = Integer.parseInt(tempp);
      while(select != 0){
        switch(select){
          case 1:
            editPrice();
            break;
          case 2:
            editQuantity();
            break;
          case 3:
            editDescription();
            break;
          default :
          System.out.println("Không có lựa chọn này");
            break;
        }
      System.out.println("+ ------------------------------- +");
      System.out.println("|              Sửa                |");
      System.out.println("+ ------------------------------- +");
      System.out.println("| 1. Thay đổi giá sản phẩm        |");
      System.out.println("| 2. Thay đổi số lượng sản phẩm   |");
      System.out.println("| 3. Thay đổi mô tả sản phẩm      |");
      System.out.println("| 0.Thoát                         |");
      System.out.println("+ ------------------------------- +");
      System.out.print("Nhập lựa chọn : ");
      tempp = sc.nextLine();
      System.out.println("+ ----------------------------------------------------------------------------------------------------- +");
      while (!isNumber(tempp)) {
          System.out.println("Lựa chọn không hợp lệ !");
          System.out.print("Nhập lại lựa chọn : ");
          tempp = sc.nextLine();
          System.out.println("+ ----------------------------------------------------------------------------------------------------- +");
      }
      select = Integer.parseInt(tempp);
    }
}

  private void editPrice(){
    displayItemInventory();
    System.out.println("Nhập id sản phẩm mà bạn muốn thay đổi giá(Nhập \'exit\' để dừng)");
    System.out.print("Nhập : ");
    String id = sc.nextLine();
    System.out.println("+ ----------------------------------------------------------------------------------------------------- +");
    while(!id.equals("exit")){
      int indexProduct = hadId(id);
      if(indexProduct != -1)
      {
        System.out.print("Nhập giá tiền mới của sản phẩm  :");
        String price = sc.nextLine();
        System.out.println("+ ----------------------------------------------------------------------------------------------------- +");
        int i = 0;
        for(  ; i < price.length(); i++)
          if(price.charAt(i) < '0' || price.charAt(i) > '9')
          {
            System.out.println("Giá tiền chỉ được nhập các số 0 -> 9");
            System.out.print("Nhập lại giá  :");
            price = sc.nextLine();
            System.out.println("+ ----------------------------------------------------------------------------------------------------- +");
            i = -1;
          }
        String[] infor = itemInventory[indexProduct].split(",");
        infor[2] = Float.toString(Float.parseFloat(price.trim()));
        itemInventory[indexProduct] = infor[0];
        for( i = 1; i < infor.length; i++){
          itemInventory[indexProduct] = itemInventory[indexProduct] + "," + infor[i];
        }
        updateFileInventory();
        displayItemInventory();
        System.out.println("Nhập id sản phẩm mà bạn muốn thay đổi giá(Nhập \'exit\' để dừng)");
        System.out.print("Nhập : ");
        id = sc.nextLine();
        System.out.println("+ ----------------------------------------------------------------------------------------------------- +");
      }
      else {
        System.out.println("Sản phẩm không tồn tại");
        System.out.println("Nhập id sản phẩm mà bạn muốn thay đổi giá(Nhập \'exit\' để dừng)");
        System.out.print("Nhập : ");
        id = sc.nextLine();
        System.out.println("+ ----------------------------------------------------------------------------------------------------- +");
      }
    
    }
  }

  private void editQuantity(){
    displayItemInventory();
    System.out.println("Nhập id sản phẩm mà bạn muốn thay đổi số lượng(Nhập \'exit\' để dừng)");
    System.out.print("Nhập : ");
    String id = sc.nextLine();
    System.out.println("+ ----------------------------------------------------------------------------------------------------- +");

    while(!id.equals("exit")){
      int indexProduct = hadId(id);
      if(indexProduct != -1)
      {
        System.out.print("Nhập số lưọng mới của sản phẩm  :");
        String quantity = sc.nextLine();
        System.out.println("+ ----------------------------------------------------------------------------------------------------- +");

        int i = 0;
        for(  ; i < quantity.length(); i++)
          if(quantity.charAt(i) < '0' || quantity.charAt(i) > '9')
          {
            System.out.println("Số lượng chỉ được nhập các số 0 -> 9");
            System.out.print("Nhập lại giá  :");
            quantity = sc.nextLine();
            i = -1;
          }
        String[] infor = itemInventory[indexProduct].split(",");
        infor[4] = quantity.trim();
        itemInventory[indexProduct] = infor[0];
        for( i = 1; i < infor.length; i++){
          itemInventory[indexProduct] = itemInventory[indexProduct] + "," + infor[i];
        }
        updateFileInventory();
        displayItemInventory();
        System.out.println("Nhập id sản phẩm mà bạn muốn thay đổi số lượng(Nhập \'exit\' để dừng)");
        System.out.print("Nhập : ");
        id = sc.nextLine();
        System.out.println("+ ----------------------------------------------------------------------------------------------------- +");

      }
      else {
        System.out.println("Sản phẩm không tồn tại");
        System.out.println("Nhập id sản phẩm mà bạn muốn thay đổi số lượng(Nhập \'exit\' để dừng)");
        System.out.print("Nhập : ");
        id = sc.nextLine();
        System.out.println("+ ----------------------------------------------------------------------------------------------------- +");

      }
    
    }
  }
  
  private void editDescription(){
    displayItemInventory();
    System.out.println("Chọn Id sản phẩm bạn muốn thay đổi mô tả(nhập \"exit\" để dừng)");
    System.out.print("Nhập id  : ");
    String id = sc.nextLine();
    System.out.println("+ ----------------------------------------------------------------------------------------------------- +");

    while(!id.equals("exit")){
      int indexProduct = hadId(id);
      if(indexProduct != -1)
      {
        System.out.print("Nhập mô tả mới của sản phẩm  :");
        String des = sc.nextLine();
        System.out.println("+ ----------------------------------------------------------------------------------------------------- +");

        int i = 0;
        String[] infor = itemInventory[indexProduct].split(",");
        infor[3] = des.trim();
        itemInventory[indexProduct] = infor[0];
        for( i = 1; i < infor.length; i++){
          itemInventory[indexProduct] = itemInventory[indexProduct] + "," + infor[i];
        }
        updateFileInventory();
        displayItemInventory();
        System.out.println("Chọn Id sản phẩm bạn muốn thay đổi mô tả(nhập \"exit\" để dừng)");
        System.out.print("Nhập id  : ");
        id = sc.nextLine();
        System.out.println("+ ----------------------------------------------------------------------------------------------------- +");

      }
      else {
        System.out.println("Sản phẩm không tồn tại");
        System.out.println("Select id product that you want to edit Des(Enter \"exit\" to stop)");
        System.out.print("Select : ");
        id = sc.nextLine();
        System.out.println("+ ----------------------------------------------------------------------------------------------------- +");

      }
    
    }
  }

  private int hadId(String id){
    if(itemInventory.length ==0)
      return -1;
    for(int i = 0; i < itemInventory.length; i++)
      if(itemInventory[i].split(",")[0].equals(id))
        return i;
    return -1;
  }

  public void readFromFileInventory(){
    try{
      File readFile = new File("data\\inventory.txt");
      String[] tempItem = new String[0];
      Scanner read = new Scanner(readFile);
      while(read.hasNextLine())
      {
        String line = read.nextLine();

        if(line.split(",").length < 5)
          continue;
        tempItem = Arrays.copyOf(tempItem , tempItem.length + 1);
        tempItem[tempItem.length - 1] = line;
      }
      itemInventory = tempItem;
      read.close();
    }catch(Exception e){
      e.printStackTrace();
    }
  }

  private void updateFileInventory(){
    try{

      File file = new File("data\\inventory.txt");
      file.delete();

      FileWriter writer = new FileWriter("data\\inventory.txt");
      for(int i = 0 ; i < itemInventory.length ; i++){
        writer.write(itemInventory[i].toString() + "\n".toString());
      }
      writer.close();
    }catch(Exception e){
      e.printStackTrace();
    }
  }

  private String toString(String id , String name , String price ,String quantity ,String des){
    return String.format("| %-4s | %-20s | %10.2f | %5s | %-50s |",id , name ,Float.parseFloat(price) ,quantity,des);
  }
  public void displayItemInventory(){
    readFromFileInventory();
    if(itemInventory.length ==0) {
      System.out.println("Kho trống");
      return;
    }
    System.out.println("+ ----------------------------------------------------------------------------------------------------- +");
    System.out.println(String.format("| %-4s | %-20s | %-10s | %-5s | %-50s |", "Id", "Tên sản phẩm", "Giá", "SL", "Mô tả"));
    System.out.println("+ ----------------------------------------------------------------------------------------------------- +");
    for(int i=0; i<itemInventory.length ; i++)
      {
        String[] infor = itemInventory[i].split(",");
        System.out.println(toString(infor[0].trim() , infor[1].trim() , infor[2].trim(),infor[4].trim(),infor[3].trim()));
      }
    System.out.println("+ ----------------------------------------------------------------------------------------------------- +");

  }

  private boolean isNumber(String s){
    if(s == null) return false;

    try{
        int x = Integer.parseInt(s);
    }catch(NumberFormatException e){
        return false;
    }
    return true;
}
}
