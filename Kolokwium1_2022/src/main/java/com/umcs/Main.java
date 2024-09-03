package com.umcs;

import com.umcs.exceptions.AmbigiousProductException;

import java.nio.file.Path;

public class Main {
    public static void main(String[] args) {
        Path path1 = Path.of("src/main/resources/nonfood/");
        Path path2 = Path.of("src/main/resources/food/");

//        NonFoodProduct benzyna = NonFoodProduct.fromCsv(path1);
//        FoodProduct buraki = FoodProduct.fromCsv(path2);
//        System.out.println(buraki.getName() + ":DOLNOŚLĄSKIE/" + buraki.getPrice(2010, 4, "DOLNOŚLĄSKIE"));
//        System.out.println(buraki.getName() + "/" + buraki.getPrice(2022, 1));
//        System.out.println(benzyna.getName() + "/" + benzyna.getPrice(2022,1));

//        Product.addProducts(FoodProduct::fromCsv, path2);
//        try{
//            Product product1 = Product.getProducts("Bu");
//            //Product product2 = Product.getProducts("Abc");
//            Product product3 = Product.getProducts("Ja");
//            System.out.println(product1.getName() + product1.getPrice(2010, 4) );
//        } catch (AmbigiousProductException e ) {
//            System.err.println(e.getMessage());
//        }

        Cart cart = new Cart();
        Product.addProducts(FoodProduct::fromCsv, path2);
        Product.addProducts(NonFoodProduct::fromCsv, path1);
        try{
            cart.addProduct(Product.getProducts("Bu"), 2);
            cart.addProduct(Product.getProducts("Ce"), 3);
            cart.addProduct(Product.getProducts("Jab"), 1);
            cart.addProduct(Product.getProducts("Benzyn"), 60);
            double getInflation = cart.getInflation(2010,4, 2022, 1);
            System.out.println("Inflation: " + getInflation + "%");
            double getPrice = cart.getPrice(2022, 1);
            System.out.println("Price: " + getPrice + "PLN for 2022, 1 for ");
        }catch (AmbigiousProductException e){
            System.err.println(e.getMessage());
        }

    }
}