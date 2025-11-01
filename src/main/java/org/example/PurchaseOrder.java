package org.example;

import java.util.Arrays;
import java.util.Scanner;
  
public class PurchaseOrder {
  private Supplier supplier;
  private ListPurchasedOrder purchasedOrder;
  private String[] listPurchase;
  Scanner sc = new Scanner(System.in);

  public PurchaseOrder() {
    supplier = new Supplier();
    listPurchase = new String[0];
    purchasedOrder = new ListPurchasedOrder();
  }

  public void setSupplier(Supplier supplier) {
    this.supplier = supplier;
  }

  public void setListPurchase(String[] listPurchase){
    this.listPurchase = listPurchase;
  }

  public void setPurchasedOrder(ListPurchasedOrder purchasedOrder){
    this.purchasedOrder = purchasedOrder;
  }

  public String[] getListPurchase() {
    return listPurchase;
  }

  public Supplier getSupplier() {
    return supplier;
  }

  public ListPurchasedOrder getPurchasedOrder() {
    return purchasedOrder;
  }

  public void selectSupplierProduct() {
    String s;
    listPurchase = null;
    listPurchase = new String[0];

    System.out.println("Chọn những sản phẩm bạn cần từ nhà cung cấp (VD: FD1,100)");
    System.out.println("Nếu bạn muốn dừng, nhập `exit`");
    System.out.print("Vui lòng nhập `id, số lượng`: ");
    s = sc.nextLine();
    System.out.println("+ ----------------------------------------------------------------------------------------------------- +");


    while (true) {
      if (s.trim().toLowerCase().compareTo("EXIT".toLowerCase()) == 0)
        break;

      while(   !s.contains(",") )
      {
        System.out.println("Bạn nhập sai rồi.(VD: FD1,100)");
        System.out.print("Vui lòng nhập lại : ");
        s = sc.nextLine();
        System.out.println("+ ----------------------------------------------------------------------------------------------------- +");
        if (s.trim().toLowerCase().compareTo("EXIT".toLowerCase()) == 0)
              break;
      }

      if (s.trim().toLowerCase().compareTo("EXIT".toLowerCase()) == 0)
        break;

      while(s.split(",").length != 2  ||  !isNumber(s.split(",")[1])) {
        System.out.println("Bạn nhập sai rồi.(VD: FD1,100)");
        System.out.print("Vui lòng nhập lại : ");
        s = sc.nextLine();
        System.out.println("+ ----------------------------------------------------------------------------------------------------- +");
        if (s.trim().toLowerCase().compareTo("EXIT".toLowerCase()) == 0)
              break;
      }

      if (s.trim().toLowerCase().compareTo("EXIT".toLowerCase()) == 0)
        break;

      int index = supplier.intHasProductSupplier(s.split(",")[0].trim());
      if (index != -1) {
        String quantity = s.split(",")[1].trim();
        String[] infor = supplier.getSupplierData()[index].split(",");
        /////// UPDATE QUANTITY OF PRODUCT///
        infor[4] = quantity;
        boolean infornew = true;
        ///////// is infor new product ?
        for (int i = 0; i < listPurchase.length; i++) {
          if (listPurchase[i].split(",")[0].equals(infor[0])) {
            infornew = false;
            String[] infor1 = listPurchase[i].split(",");
            int a = Integer.parseInt(infor1[4]);
            int b = Integer.parseInt(infor[4]);
            int c = a + b;
            infor1[4] = Integer.toString(c);
            listPurchase[i] = "";
            for (int j = 0; j < infor1.length; j++) {
              listPurchase[i] = listPurchase[i] + infor1[j] + ",";
            }
            break;
          }
        }
        if (infornew) {
          String inforNew = "";
          for (int i = 0; i < infor.length; i++)
            inforNew = inforNew + infor[i] + ",";
          listPurchase = Arrays.copyOf(listPurchase, listPurchase.length + 1);
          listPurchase[listPurchase.length - 1] = inforNew;
        }
      }
      System.out.print("Vui lòng nhập `id, số lượng`: ");
      s = sc.nextLine();
      System.out.println("+ ----------------------------------------------------------------------------------------------------- +");

    }

    /// Save history purchased order//////
    if(listPurchase.length > 0)
      purchasedOrder.writeToFilePurchasedOrder(listPurchase);
    System.out.println("Hàng đã được vận chuyển đến kho của bạn");
    System.out.println("+ ----------------------------------------------------------------------------------------------------- +");

  }

  public void displayPurchaseOrder() {
    for (int i = 0; i < listPurchase.length; i++) {
      System.out.println(listPurchase[i]);
    }
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
