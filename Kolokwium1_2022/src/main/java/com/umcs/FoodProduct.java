package com.umcs;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Scanner;

public class FoodProduct extends Product{
    String[] provinces;
    Double[][] prices;
    public FoodProduct(String name, String[] provinces, Double[][] prices){
        super(name);
        this.provinces = provinces;
        this.prices = prices;
    }
    public double getPrice(int year, int month, String province){
        int provinceIndex = Arrays.asList(provinces).indexOf(province);
        if(year < 2010 || year > 2022 && month > 3 || month < 1 || month > 12 || provinceIndex == -1){
            throw new IndexOutOfBoundsException("Niepoprawny zakres daty lub województwa");
        }

        return prices[provinceIndex][(year - 2010) * 12 + month - 1];
    }
    @Override
    public double getPrice(int year, int month){
        int i = 0, length = 0;
        if(year < 2010 || year > 2022 && month > 3 || month < 1 || month > 12){
            throw new IndexOutOfBoundsException("Niepoprawny zakres daty");
        }
        double sum = 0;
        while(prices[i] != null){
            length++;
            i++;
        }
        for (i = 0; i < length; i++) {
            sum += prices[i][(year - 2010) * 12 + month - 1];
        }
        return sum / length;
    }
    public static FoodProduct fromCsv(Path path){
        String name;
        String[] provinces = new String[32];
        Double[][] prices = new Double[32][];

        try{
            Scanner scanner = new Scanner(path);
            name = scanner.nextLine();
            scanner.nextLine();
            int i = 0;
            while(scanner.hasNextLine()){
                String[] line = scanner.nextLine().split(";");
                provinces[i] = line[0];
                prices[i] = Arrays.stream(Arrays.copyOfRange(line, 1, line.length))
                        .map(value -> value.replace(",", "."))
                        .map(Double::valueOf)
                        .toArray(Double[]::new);
                i++;
            }
            scanner.close();
            return new FoodProduct(name, provinces, prices);
        }catch (IOException e){
            throw new RuntimeException(e);
        }
    }
}
