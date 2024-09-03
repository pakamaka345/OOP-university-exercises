package com.umcs;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ICDCodeTabularOptimizedForMemory implements ICDCodeTabular{

    public ICDCodeTabularOptimizedForMemory(String path){
        try(Stream<String> fileLines = Files.lines(Path.of(path))){
            BufferedWriter bufferedWriter = Files.newBufferedWriter(Path.of("src/main/resources/ICDCODES.txt"));
            bufferedWriter.write(fileLines
                    .skip(87)
                    .map(String::trim)
                    .filter(line -> line.matches("[A-Z]\\d\\d.*"))
                    .map(line -> line + "\n")
                    .collect(Collectors.joining()));
            bufferedWriter.close();
        }catch (IOException e){
            System.err.println(e.getMessage());
        }
    }
    @Override
    public String getDescription(String code) throws IndexOutOfBoundsException {
        try(Stream<String> fileLines = Files.lines(Path.of("src/main/resources/ICDCODES.txt"))){
            return fileLines
                    .filter(line -> line.startsWith(code))
                    .map(line -> line.split(" ", 2))
                    .map(part -> part[1].trim())
                    .findFirst()
                    .orElseThrow(IndexOutOfBoundsException::new);
        }catch (IOException e){
            System.err.println(e.getMessage());
            return "";
        }
    }
}
