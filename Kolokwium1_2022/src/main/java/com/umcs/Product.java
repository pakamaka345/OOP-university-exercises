package com.umcs;

import com.umcs.exceptions.AmbigiousProductException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Stream;

public abstract class Product {
    private String name;
    private static List<Product> products;
    public Product(String name){
        this.name = name;
    }
    public static void clearProducts(){
        products.clear();
    }
    public static void addProducts(Function<Path, Product> productCreator, Path path){
        if(products == null){
            products = new ArrayList<>();
        }
        try(Stream<Path> paths = Files.walk(path)){
            paths.filter(Files::isRegularFile).map(productCreator).forEach(products::add);
        }catch (IOException e){
            throw new RuntimeException(e);
        }
    }
    public static Product getProducts(String prefix) throws AmbigiousProductException, IndexOutOfBoundsException {
        List<Product> result = products.stream()
                .filter(product -> product.getName().startsWith(prefix))
                .toList();
        if(result.isEmpty())
        {
            throw new IndexOutOfBoundsException(prefix + " nie wkazuje na żaden obiekt");
        } else if(result.size() > 1){
            throw new AmbigiousProductException(result.stream().map(Product::getName).toList());
        }else{
            return result.get(0);
        }
    }
    public String getName(){
        return name;
    }
    public abstract double getPrice(int year, int month);
}
