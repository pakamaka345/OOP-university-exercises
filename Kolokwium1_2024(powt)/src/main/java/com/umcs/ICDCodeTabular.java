package com.umcs;

//import java.io.IOException;
//import java.nio.file.Files;
//import java.nio.file.Path;
//import java.util.HashMap;
//import java.util.Map;
//import java.util.stream.Collectors;
//import java.util.stream.Stream;
//
//public class ICDCodeTabular {
//    private Map<String, String> iCDMap = new HashMap<>();
//
//    public ICDCodeTabular(String path){
//        try(Stream<String> fileLines = Files.lines(Path.of(path))){
//            iCDMap = fileLines
//                    .skip(87)
//                    .map(String::trim)
//                    .filter(line -> line.matches("[A-Z]\\d\\d.*"))
//                    .map(line -> line.split(" ", 2))
//                    .collect(Collectors.toMap(
//                            part -> part[0],
//                            part -> part[1].trim(),
//                            (oldV, newV) -> oldV));
//
//        }catch (IOException e){
//            System.err.println(e.getMessage());
//        }
//    }
//    public String getDescription(String code){
//        return iCDMap.get(code);
//    }
//}

public interface ICDCodeTabular{
    public String getDescription(String code) throws IndexOutOfBoundsException;
}
