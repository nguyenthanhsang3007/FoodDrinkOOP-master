package org.example;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.Scanner;

public class ListPurchasedOrder {
  private String headContent = "data\\listPurchasesOrder\\purchased";
  private String tailContent = ".txt";

  public ListPurchasedOrder() {}

  public String getHeadcontent() { return headContent; }
  public String getTailContent() { return tailContent; }

  public void writeToFilePurchasedOrder(String[] listPurchase ){

    try {
      FileWriter writer = new FileWriter("data\\historyOrdered.txt",true);
      LocalDate myDate = LocalDate.now();
      LocalTime myTime = LocalTime.now();
      writer.append("\n" + myDate.toString() + "," + myTime.toString());
      for(String line : listPurchase){
        writer.append(  "\n" + line.toString()  );
      }
      writer.close();
    }catch(IOException e){
      e.printStackTrace();
    }
  }
  public void readFilePurchasedOrder(){
    try{
      File file = new File("data\\historyOrdered.txt");

      String[] datas = new String[0];
      Scanner read = new Scanner(file);
      while(read.hasNextLine()){
        String line = read.nextLine();
        if(line.trim().isEmpty())
          continue;
        datas = Arrays.copyOf( datas, datas.length + 1 );
        datas[datas.length - 1] = line;
      }

      for(int i = 0 ; i < datas.length; i++){
        System.out.println(datas[i]);
        if(i < datas.length - 1 && datas[i+1].trim().split(",").length == 2)
          System.out.println("");
        // String[] data = datas[i].split(",");
        //time start
        // if(data.length == 2 && data[0].trim().compareTo("2023-11-22") == 0)
        // {
        //   System.out.println(datas[i]);
        //   for(int j = i + 1 ; j < datas.length ; j++)
        //   {
        //     String[] temp = datas[j].split(",");
        //time end
        //     if(temp.length == 2 && temp[0].trim().compareTo("2023-11-23") >  0)
        //       break;
        //     System.out.println(datas[j]);
        //   }
        // }
      }
    }catch(Exception e)
    {e.printStackTrace();}
  }
}