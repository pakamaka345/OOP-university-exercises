package com.umcs.lessons;

import lombok.Getter;
import lombok.Setter;

import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Getter
@Setter
public class Person implements Serializable {
    private final String name;
    private final LocalDate birthDate;
    private final LocalDate deathDate;
    private final List<Person> parents;

    public Person(String name, LocalDate birthDate, LocalDate deathDate){
        this.name = name;
        this.birthDate = birthDate;
        this.deathDate = deathDate;
        this.parents = new ArrayList<>();
    }
    public void addParent(Person person){
        parents.add(person);
    }
    public static Person fromCsvLine(String line){
        String[] parts = line.split(",", -1);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        LocalDate birthDate = LocalDate.parse(parts[1], formatter);
        LocalDate deathDate = (parts[2].isEmpty()) ? null : LocalDate.parse(parts[2], formatter);
        return new Person(parts[0], birthDate, deathDate);
    }
    public static List<Person> fromCsv(String path){
        List<Person> personList = new ArrayList<>();
        Map<String, PersonWithParentsNames> personWithParentsNamesMap = new HashMap<>();
        Map<String, Person> ambiguousNames = new HashMap<>();
        String line;
        BufferedReader bufferedReader = null;
        FileReader fileReader;
        try{
            fileReader = new FileReader(path);
            bufferedReader = new BufferedReader(fileReader);
            bufferedReader.readLine();
            while((line = bufferedReader.readLine()) != null){
                PersonWithParentsNames personWithParentsNames = PersonWithParentsNames.fromCsvLine(line);
                Person person = personWithParentsNames.getPerson();
                try{
                    person.lifespanValidate();
                    person.ambiguousNameValidate(ambiguousNames);
                    personList.add(person);
                    personWithParentsNamesMap.put(person.name, personWithParentsNames);
                    ambiguousNames.put(person.name, person);
                }catch (NegativeLifespanExceprion | AmbiguousPersonException e){
                    System.err.println(e.getMessage());
                }
            }
            PersonWithParentsNames.fillParents(personWithParentsNamesMap);
        } catch (IOException e){
            System.out.println(e.getMessage());
        } finally {
            if(bufferedReader != null){
                try{
                    bufferedReader.close();
                }catch (IOException e){
                    System.out.println(e.getMessage());
                }
            }
        }
        return personList;
    }
    public static void toBinaryFile(List<Person> people, String path) {
        try (
                FileOutputStream fileOutputStream = new FileOutputStream(path);
                ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream)
                ){
            for(Person person : people){
                objectOutputStream.writeObject(person);
            }
        }catch (IOException e){
            System.out.println(e.getMessage());
        }
    }
    public static List<Person> fromBinaryFile(String path){
        List<Person> people = new ArrayList<>();
        try(
                FileInputStream fileInputStream = new FileInputStream(path);
                ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream)
                ) {
            while (true) {
                people.add((Person) objectInputStream.readObject());
            }
        }catch (EOFException e){
            System.out.println("End of file");
        }catch (IOException | ClassNotFoundException e){
            System.out.println(e.getMessage());
        }
        return people;
    }
    public String generateUML(Function<String, String> postProcess, Predicate<Person> condition){
        StringBuilder uml = new StringBuilder();
        Function<Person, String> deleteSpaces = p -> p.getName().replaceAll(" ", "");
        Function<Person, String> addObject = p -> condition.test(p)
                ? postProcess.apply("object " + deleteSpaces.apply(p))
                : "object " + deleteSpaces.apply(p);

        String nameSurname = deleteSpaces.apply(this);
        uml.append("@startuml\n").append(addObject.apply(this));
        if(!parents.isEmpty()){
            uml.append(parents.stream()
                    .map(p -> addObject.apply(p) + "\n" + deleteSpaces.apply(p) + " <-- " + nameSurname)
                    .collect(Collectors.joining()));
        }
        uml.append("\n@enduml");
        return uml.toString();
    }
    public static String generateUML(List<Person> people){
        StringBuilder sb = new StringBuilder();
        Function<Person, String> deleteSpaces = p -> p.getName().replaceAll(" ", "");
        Function<Person, String> addObject = p -> "object " + deleteSpaces.apply(p);
        sb.append("@startuml\n");
        sb.append(people.stream()
                .map(p -> "\n" + addObject.apply(p))
                .collect(Collectors.joining()));
        sb.append(people.stream()
                .flatMap(person -> person.parents.isEmpty()
                        ? Stream.empty()
                        : person.parents.stream()
                            .map(p -> "\n" + deleteSpaces.apply(p) + " <-- " + deleteSpaces.apply(person)))
                            .collect(Collectors.joining()));
        sb.append("\n@enduml");
        return sb.toString();
    }
    public static List<Person> filterByName(List<Person> people, String substring){
        return people.stream()
                .filter(p -> p.getName().contains(substring))
                .collect(Collectors.toList());
    }
    public static List<Person> sortByName(List<Person> people){
        Function<Person, Long> birthDateToLong = person -> person.birthDate.toEpochDay();
        return people.stream()
                .sorted((Comparator.comparingLong(birthDateToLong::apply)))
                .collect(Collectors.toList());
    }
    public static List<Person> sortDeathByName(List<Person> people){
        Function<Person, Long> lengthOfLife = person -> ChronoUnit.DAYS.between(person.birthDate, person.deathDate);
        return people.stream()
                .filter(person -> !person.getDeathDate().isEqual(null))
                .sorted(((o1, o2) -> Comparator.comparingLong(lengthOfLife::apply).compare(o2, o1)))
                .toList();
    }
    public static Person findOldest(List<Person> people){
        Function<Person, Long> lengthOfLifeFromNow = person -> ChronoUnit.DAYS.between(person.birthDate, LocalDate.now());
        return people.stream()
                .filter(person ->
                        person.deathDate
                                .isEqual(null))
                                .max((o1, o2) ->
                                        Comparator.comparingLong(lengthOfLifeFromNow::apply)
                                                .compare(o1, o2))
                .orElse(null);
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", birthDate=" + birthDate +
                ", deathDate=" + deathDate +
                ", parents=" + parents +
                '}';
    }
    public void lifespanValidate() throws NegativeLifespanExceprion{
        if(this.deathDate != null && this.deathDate.isBefore(this.birthDate)){
            throw new NegativeLifespanExceprion(this);
        }
    }
    public void ambiguousNameValidate(Map<String, Person> map) throws AmbiguousPersonException{
        if(map.containsKey(this.name)){
            throw new AmbiguousPersonException(this);
        }
    }
    public void parentingValidate(Person child) throws ParentingAgeException{
        if(this.deathDate != null && child.deathDate != null && ((child.getBirthDate().getYear() - this.birthDate.getYear() < 15) || (this.getDeathDate().isBefore(child.getBirthDate())))){
            throw new ParentingAgeException(this, child);
        }
    }
}
