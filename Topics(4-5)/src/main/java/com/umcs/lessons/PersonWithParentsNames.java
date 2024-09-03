package com.umcs.lessons;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

@Getter
public class PersonWithParentsNames {
    private Person person;
    private List<String> names;
    PersonWithParentsNames(Person person, List<String> names){
        this.person = person;
        this.names = names;
    }
    public static PersonWithParentsNames fromCsvLine(String line){
        String[] parts = line.split(",", -1);
        Person person = Person.fromCsvLine(line);
        List<String> names = new ArrayList<>();
        for(int i = 3; i <= 4; i++){
            if(!parts[i].isEmpty()){
                names.add(parts[i]);
            }
        }
        return new PersonWithParentsNames(person, names);
    }
    public static void fillParents(Map<String, PersonWithParentsNames> map){
        for(PersonWithParentsNames helperChild : map.values()){
            for (String name : helperChild.names){
                PersonWithParentsNames helperParent = map.get(name);
                Person parent = helperParent.getPerson();
                try{
                    parent.parentingValidate(helperChild.getPerson());
                    helperChild.getPerson().addParent(parent);
                }catch (ParentingAgeException e){
                    System.err.println(e.getMessage());
                    System.out.println("Do you want to continue? [y/n]");
                    Scanner scanner = new Scanner(System.in);
                    String answer = scanner.nextLine();
                    if(answer.toUpperCase().equals("Y")){
                        helperChild.getPerson().addParent(parent);
                    }
                }
            }
        }
    }
}
