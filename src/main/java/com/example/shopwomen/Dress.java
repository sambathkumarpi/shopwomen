// Dress.java
package com.example.shopwomen;

public class Dress extends Products {
    private String dressSize;

    public Dress(int Id, String name, String size, int qty, String category, int price) {
        super(Id, name, size, qty, category, price);
        this.dressSize = size;
    }

    public String getDressType() {
        return dressSize;
    }

    public void setDressType(String dressType) {
        this.dressSize = dressSize;
    }

    @Override
    public String toString() {
        return "Dress{" +
                "name='" + getName() + '\'' +
                ", size=" + getSize() +
                '}';
    }
}
