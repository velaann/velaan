package com.example.velaann;

public class order {
    private  String Product_Namee;
    private String Product_Pricee;
    private String Product_Quantityy;
    private String Product_Valididtyy;
    private String valid_tilll;

public order(){

}

    public order(String product_Namee, String product_Pricee, String product_Quantityy, String product_Valididtyy, String Valid_tilll) {
        this.Product_Namee = product_Namee;
        this.Product_Pricee = product_Pricee;
        this.Product_Quantityy = product_Quantityy;
        this.Product_Valididtyy = product_Valididtyy;
        this.valid_tilll = Valid_tilll;
    }

    public String getProduct_Namee() {
        return Product_Namee;
    }

    public String getProduct_Pricee() {
        return Product_Pricee;
    }

    public String getProduct_Quantityy() {
        return Product_Quantityy;
    }

    public String getProduct_Valididtyy() {
        return Product_Valididtyy;
    }

    public String getValid_tilll() {
        return valid_tilll;
    }
}