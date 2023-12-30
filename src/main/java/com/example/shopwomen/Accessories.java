// Accessories.java
package com.example.shopwomen;

public class Accessories extends Products {
    private String accessorySize;

    public Accessories(int Id, String name, String size, int qty, String category, int price) {
        super(Id, name, size, qty, category, price);
        this.accessorySize = size;
    }

    public String getAccessoryType() {
        return accessorySize;
    }

    public void setAccessoryType(String accessoryType) {
        this.accessorySize = accessorySize;
    }

    @Override
    public String toString() {
        return "Accessories{" +
                "name='" + getName() + '\'' +
                ", size=" + getSize() +
                '}';
    }
}
