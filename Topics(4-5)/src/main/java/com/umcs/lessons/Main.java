package com.umcs.lessons;

import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;

public class Main {
    public static void main(String[] args) {
        PlantUMLRunner.setPath("plantuml-1.2024.4.jar");
        List<Person> people = Person.fromCsv("family.csv");
        Person person1 = people.get(1);


        Function<String, String> changeToYellow = s -> s + " #Yellow";
        Function<String, String> nothingChange = s -> s;

        //person.generateUML(changeToYellow);


        //PlantUMLRunner.generateDiagram(uml, "./", "NothingChange");

        //PlantUMLRunner.generateDiagram(Person.generateUML(people), "./", "Nowy");
        //System.out.println(people.get(3).generateUML());
//        for(Person person : people){
//            PlantUMLRunner.generateDiagram(person.generateUML(), "./", "Nowy");
//        }
        //Person.toBinaryFile(people1, "people.bin");
        //List<Person> people = Person.fromBinaryFile("people.bin");
//        for (Person person : people){
//            person.generateUML();
//        }

        /*
        String uml = "@startuml\n" + "object MarekKowalski #Yellow \n" + "object JanKowalski #Yellow \n"
                + "object AnnaKowalska #Yellow \n" + "object KatarzynaKowalska #Yellow \n" + "object AndrzejKowalski #Yellow \n"
                + "MarekKowalski -> JanKowalski : syn\n" + "MarekKowalski -> AnnaKowalska : żona\n"
                + "JanKowalski -> KatarzynaKowalska : córka\n" + "AnnaKowalska -> KatarzynaKowalska : córka\n"
                + "AnnaKowalska -> AndrzejKowalski : syn\n" + "@enduml";
        PlantUMLRunner.generateDiagram(uml, "./", "NOwy");*/




    }
}