package com.example.shopwomen;

public class Products  {

//    ProductID int,
//    Name varchar(255),
//    size varchar(50),
//    qty int,
//    category varchar(100),
//    Price float

    private int Id;
    private String Name;
    private String size;
    private int qty;
    private  String category;
    private  double price;

    public Products(int Id, String name, String size, int qty, String category, double price) {
        this.Id = Id;
        Name = name;
        this.size = size;
        this.qty = qty;
        this.category = category;
        this.price = price;
    }

    public int getId() {
        return Id;
    }

    public String getName() {
        return Name;
    }

    public String getSize() {
        return size;
    }

    public int getQty() {
        return qty;
    }

    public String getCategory() {
        return category;
    }

    public double getPrice() {
        return price;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setPrice(double price) throws IllegalArgumentException {
        if (price >= 0) {
            this.price = price;
        } else throw new IllegalArgumentException("Price is negative");
    }

    @Override
    public String toString() {
        return "Products{" +
                "Id=" + Id +
                ", Name='" + Name + '\'' +
                ", size='" + size + '\'' +
                ", qty=" + qty +
                ", category='" + category + '\'' +
                ", price=" + price +
                '}';
    }


}
