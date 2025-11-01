package org.example;

public class FoodItem extends Product {
    private int spicyLevel;

    public FoodItem() {
    }

    public FoodItem(String id, String name, float price, String description, int quantity, String category, int spicyLevel) {
        super(id, name, price, description, quantity, category);
        this.spicyLevel = spicyLevel;
    }

    public void setSpicyLevel(int spicyLevel) {
        while (spicyLevel != 0 && spicyLevel != 1) {
            System.out.print("Nhập sai mức độ cay, nhập lại (0/1): ");
            spicyLevel = sc.nextInt();
        }
        this.spicyLevel = spicyLevel;
    }

    public int getSpicyLevel() {
        return this.spicyLevel;
    }

    @Override
    public void inputInfo() {
        super.inputInfo();
        sc.nextLine();
        System.out.print("Bạn có ăn cay (0/1): ");
        setSpicyLevel(sc.nextInt());
        sc.nextLine();
    }

    @Override
    public String toString() {
        return super.toString() + String.format(" %7d |", spicyLevel);
    }

    public String toOrderString() {
        return super.toOrderString() + String.format("%d", spicyLevel);
    }
}
