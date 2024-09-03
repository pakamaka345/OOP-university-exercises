### Temat 4. Pliki, wyjątki
***Skorzystaj z udostępnionego pliku zawierającego opisy przykładowych osób tworzących ze sobą związki.***
W kolejnych kolumnach pliku opisane są:
1. imię i nazwisko,
2. data narodzin,
3. data śmierci (pusta jeśli żyje),
4. imię rodzica (puste jeśli nieznany),
5. imię rodzica (puste jeśli nieznany).
* Zadanie 1.
Napisz klasę Person, w której znajdować będą się dane odpowiadające wierszowi pliku. Na tym etapie pomiń wczytywanie rodziców. Napisz metodę wytwórczą fromCsvLine() klasy Person przyjmującą jako argument linię opisanego pliku.
* Zadanie 2.
Napisz metodę fromCsv(), która przyjmie ścieżkę do pliku i zwróci listę obiektów typu Person.
* Zadanie 3.
Napisz klasę NegativeLifespanException. Rzuć jej obiekt jako wyjątek, jeżeli data śmierci osoby jest wcześniejsza niż data urodzin. Wywołanie metody getMessage() powinno wyświetlić stosowną informację zawierającą przyczyny rzucenia wyjątku.
* Zadanie 4.
Napisz klasę AmbiguousPersonException. Rzuć jej obiekt jako wyjątek, jeżeli w pliku pojawi się więcej niż jedna osoba o tym samym imieniu i nazwisku. Wywołanie metody getMessage() powinno wyświetlić stosowną informację zawierającą przyczyny rzucenia wyjątku.
* Zadanie 5.
Niech rodzice będą przechowywani w klasie Person w postaci listy obiektów Person. Zmodyfikuj metodę fromCsv, by w obiektach dzieci ustawiała referencje do obiektów rodziców.
* Zadanie 6.
Napisz klasę ParentingAgeException. Rzuć jej obiekt jako wyjątek, jeżeli rodzic jest młodszy niż 15 lat lub nie żyje w chwili narodzin dziecka. Przechwyć ten wyjątek tak, aby nie zablokował dodania takiej osoby, a jedynie poprosił użytkownika o potwierdzenie lub odrzucenie takiego przypadku za pomocą wpisania znaku “Y” ze standardowego wejścia.
* Zadanie 7.
W klasie Person napisz statyczne metody toBinaryFile i fromBinaryFile, które zapiszą i odczytają listę osób do i z pliku binarnego.***

### Temat 5. Programowanie funkcyjne
* Zadanie 1.

Pobierz aplikację PlantUML. Przetestuj jej działanie z linii komend.

Napisz klasę PlantUMLRunner, posiadającą publiczne statyczne metody:
- ustawienie ścieżki do uruchamialnego pliku jar.
- wygenerowanie schematu po przekazaniu: napisu z danymi, ścieżki do katalogu wynikowego i nazwy pliku wynikowego.


* Zadanie 2. W klasie Person napisz bezargumentową metodę, która zwróci napis sformatowany według składni PlantUML. Napis, korzystając z diagramu obiektów, powinien przedstawiać obiekt osoby na rzecz której została wywołana metoda oraz jej rodziców (o ile są zdefiniowani). Obiekty powinny zawierać nazwę osoby. Od dziecka do rodziców należy poprowadzić strzałki.
* Zadanie 3. W klasie Person napisz statyczną metodę, która przyjmie listę osób. Lista powinna zwrócić podobny jak w poprzedni zadaniu napis. Tym razem powinien on zawierać wszystkie osoby w liście i ich powiązania.
* Zadanie 4.W klasie Person napisz statyczną metodę, która przyjmie listę osób oraz napis substring. Metoda powinna zwrócić listę osób z listy wejściowej, ograniczoną do osób, których nazwa zawiera substring.
* Zadanie 5. W klasie Person napisz statyczną metodę, która przyjmie listę osób. Metoda powinna zwrócić listę osób z listy wejściowej, posortowanych według roku urodzenia.
* Zadanie 6. W klasie Person napisz statyczną metodę, która przyjmie listę osób. Metoda powinna zwrócić listę zmarłych osób z listy wejściowej, posortowanych malejąco według długości życia.
* Zadanie 7. W klasie Person napisz statyczną metodę, która przyjmie listę osób. Metoda powinna zwrócić najstarszą żyjącą osobę.
* Zadanie 8. Zmodyfikuj metodę zadania 2. poprzez dodanie do jej argumentów obiektu Function<String, String> postProcess. Funkcja powinna przekształcić wszystkie linie odpowiadające obiektom za pomocą tej funkcji. Przetestuj metodę z dwiema funkcjami: funkcją zmieniającą kolor obiektu na żółty oraz funkcją nie wprowadzającą żadnych zmian.
* Zadanie 9. Zmodyfikuj metodę z poprzedniego zadania poprzez dodanie do jej argumentów obiektu Predicate<Person> condition. Metoda postProcess powinna wywołać się wyłącznie dla osób spełniających warunek condition. Przetestuj metodę z danymi wygenerowanymi w zadaniach 6. i 7.


