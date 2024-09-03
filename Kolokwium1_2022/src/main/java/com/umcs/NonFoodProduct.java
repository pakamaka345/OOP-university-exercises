package com.umcs;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Scanner;

public class NonFoodProduct extends Product{
    Double[] prices;

    private NonFoodProduct(String name, Double[] prices) {
        super(name);
        this.prices = prices;
    }
    @Override
    public double getPrice(int year, int month){
        if(year < 2010 || year > 2022 && month > 3 || month < 1 || month > 12){
            throw new IndexOutOfBoundsException("Niepoprawny zakres daty");
        }
        return prices[(year - 2010) * 12 + month - 1];
    }
    public static NonFoodProduct fromCsv(Path path) {
        String name;
        Double[] prices;

        try {
            Scanner scanner = new Scanner(path);
            name = scanner.nextLine(); // odczytuję pierwszą linię i zapisuję ją jako nazwa
            scanner.nextLine();  // pomijam drugą linię z nagłówkiem tabeli
            prices = Arrays.stream(scanner.nextLine().split(";")) // odczytuję kolejną linię i dzielę ją na tablicę
                    .map(value -> value.replace(",",".")) // zamieniam polski znak ułamka dziesiętnego - przecinek na kropkę
                    .map(Double::valueOf) // konwertuję string na double
                    .toArray(Double[]::new); // dodaję je do nowo utworzonej tablicy

            scanner.close();

            return new NonFoodProduct(name, prices);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
