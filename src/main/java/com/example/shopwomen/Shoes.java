// Shoes.java
package com.example.shopwomen;

public class Shoes extends Products {
    private int shoeSize;

    public Shoes(int Id, String name, String size, int qty, String category, int price) {
        super(Id, name, size, qty, category, price);

    }

    public int getShoeSize() {
        return shoeSize;
    }

    public void setShoeSize(int shoeSize) {
        this.shoeSize = shoeSize;
    }

    @Override
    public String toString() {
        return "Shoes{" +
                "name='" + getName() + '\'' +
                ", size=" + getSize() +
                ", shoeSize=" + shoeSize +
                '}';
    }

}
