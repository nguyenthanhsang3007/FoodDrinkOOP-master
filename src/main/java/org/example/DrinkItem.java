package org.example;

public class DrinkItem extends Product {
    private int volume;
    private int sugarLevel;
    private int iceLevel;
    public DrinkItem(){
    }
    public DrinkItem(String id, String name, float price, String description, int quantity, String category, int volume, int sugarLevel, int iceLevel){
        super(id, name, price, description, quantity, category);
        this.volume = volume;
        this.sugarLevel = sugarLevel;
        this.iceLevel = iceLevel;
    }

    public int getVolume(){
        return volume;
    }
    public void setVolume(int volume) {
        while (volume != 300 && volume != 500 && volume != 700) {
            System.out.print("Nhập sai định dạng size, nhập lại (300/500/700): ");
            volume = sc.nextInt();
        }
        this.volume = volume;
    }

    public int getSugarLevel(){
        return sugarLevel;
    }
    public void setSugarLevel(int sugarLevel){
        while (sugarLevel != 0 && sugarLevel != 1) {
            System.out.print("Nhập sai mức độ ngọt, nhập lại (0/1): ");
            sugarLevel = sc.nextInt();
        }
        this.sugarLevel = sugarLevel;
    }
    public int getIceLevel(){
        return iceLevel;
    }
    public void setIceLevel(int iceLevel){
        while (iceLevel != 0 && iceLevel != 1) {
            System.out.print("Nhập sai mức độ lạnh, nhập lại (0/1): ");
            iceLevel = sc.nextInt();
        }
        this.iceLevel = iceLevel;
    }
    public void inputInfo() {
        super.inputInfo();
        sc.nextLine();
        System.out.print("Bạn chọn size (300 || 500 || 700): ");
        setVolume(sc.nextInt());
        System.out.print("Bạn có uống ngọt (0/1): ");
        setSugarLevel(sc.nextInt());
        System.out.print("Bạn có uống lạnh (0/1): ");
        setIceLevel(sc.nextInt());
        sc.nextLine();
    }

    @Override
    public String toString() {
        return super.toString() + String.format(" %5d | %3d | %3d |", volume,sugarLevel,iceLevel);
    }

    public String toOrderString() {
        return super.toOrderString() + String.format("%d, %d, %d", volume, sugarLevel, iceLevel);
    }
}
