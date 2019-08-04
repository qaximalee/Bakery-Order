package com.example.yummypoint.Model;

public class Item {

    private String Name;
    private String Description;
    private String Price;
    private String Discount;
    private String MenuId;
    private String Image;


    public Item(){}

    public Item(String name, String image, String description, String price, String discount, String menuId) {
        Name = name;
        Description = description;
        Price = price;
        Discount = discount;
        MenuId = menuId;
        Image = image;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    public void setName(String name) {
        Name = name;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public void setPrice(String price) {
        Price = price;
    }

    public void setDiscount(String discount) {
        Discount = discount;
    }

    public void setMenuId(String menuId) {
        MenuId = menuId;
    }

    public String getName() {
        return Name;
    }

    public String getDescription() {
        return Description;
    }

    public String getPrice() {
        return Price;
    }

    public String getDiscount() {
        return Discount;
    }

    public String getMenuId() {
        return MenuId;
    }
}
