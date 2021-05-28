package com.example.velaann;

public class ProductsModel {
    private  String Product_Price;
    private  String Product_Quantity;
    private  String Product_Valididty;
    private  String Seller_Name;
    private  String Seller_Location;
    private  String Seller_contactNo;
    private  String Valid_till;

    private ProductsModel(){}

    private ProductsModel(String Product_Price,String Product_Quantity,String Product_Valididty,
                          String Seller_Name, String Seller_Location,String Seller_contactNo,String Valid_till)
    {
        this.Product_Price=Product_Price;
        this.Product_Quantity=Product_Quantity;
        this.Product_Valididty=Product_Valididty;
        this.Seller_Name=Seller_Name;
        this.Seller_Location=Seller_Location;
        this.Seller_contactNo=Seller_contactNo;
        this.Valid_till=Valid_till;
    }

    public String getProduct_Quantity() {
        return Product_Quantity;
    }

    public void setProduct_Quantity(String product_Quantity) {
        Product_Quantity = product_Quantity;
    }

    public String getProduct_Price() {
        return Product_Price;
    }

    public void setProduct_Price(String product_Price) {
        Product_Price = product_Price;
    }

    public String getProduct_Valididty() {
        return Product_Valididty;
    }

    public void setProduct_Valididty(String product_Valididty) {
        Product_Valididty = product_Valididty;
    }

    public String getSeller_Name() {
        return Seller_Name;
    }

    public void setSeller_Name(String seller_Name) {
        Seller_Name = seller_Name;
    }

    public String getSeller_Location() {
        return Seller_Location;
    }

    public void setSeller_Location(String seller_Location) {
        Seller_Location = seller_Location;
    }

    public String getSeller_contactNo() {
        return Seller_contactNo;
    }

    public void setSeller_contactNo(String seller_contactNo) {
        Seller_contactNo = seller_contactNo;
    }
    public void setValid_till(String valid_till) { Valid_till = valid_till; }
    public String getvalid_till() {
        return Valid_till;
    }
}
