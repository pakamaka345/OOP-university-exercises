### Powtorzenie
Dany jest plik CSV zawierający statystykę zgonów w Polsce w 2019 roku z podziałem na grupy wiekowe i przyczyny. Przyczyny są zapisane w pierwszej kolumnie z użyciem kodu ICD-10. Druga kolumna zawiera sumaryczną liczbę zgonów z podanej przyczyny, a kolejne kolumny - liczby zgonów w przedziałach wiekowych opisanych w nagłówku.


* Zadanie 1.

Napisz klasę DeathCauseStatistic, posiadającą jako prywatne pola kod ICD-10 choroby oraz tablicę liczb zgonów w kolejnych grupach wiekowych.


Napisz publiczną, statyczną metodę fromCsvLine zwracającą obiekt DeathCauseStatistic na podstawie pojedynczej linii pliku CSV. Uwzględnij, że w pliku po nazwie kodu znajduje się tabulator.


Napisz akcesor do kodu ICD-10 przyczyny zgonu.


* Zadanie 2. 

Wewnątrz klasy DeathCauseStatistic zdefiniuj publiczną klasę AgeBracketDeaths posiadającą publiczne, ostateczne pola typu int: young, old, deathCount zawierające granice grupy wiekowej i liczbę zgonów.


W klasie DeathCauseStatistic napisz metodę przyjmującą wiek i zwracającą obiekt AgeBracketDeaths odpowiadający temu wiekowi.



* Zadanie 3.

Napisz klasę DeathCauseStatisticList, wewnątrz której, w wybranej strukturze danych przetrzymywana będzie informacja o wszystkich obiektach DeathCauseStatistic. Napisz metodę repopulate, przyjmującą ścieżkę do pliku, która usuwa istniejące dane z tej struktury i zapełnia ją danymi z pliku CSV.



W klasie DeathCauseStatisticsList napisz metodę mostDeadlyDiseases, która przyjmie wiek oraz liczbę n, mniejszą od liczby wymienionych chorób. Metoda powinna zwrócić n-elementową listę referencji na obiekty DeathCauseStatistic odpowiadające chorobom powodującym największą liczbę zgonów w grupie wiekowej do której przynależy podany wiek. Lista powinna być posortowana malejąco.



Kody ICD-10 zawsze rozpoczynają się od pojedynczej litery oraz dwóch cyfr. Podczas sprawdzania poprawności kodu należy ograniczyć się do tego warunku.



Dany jest plik tekstowy zawierający opis kodów listy chorób. Właściwe dane znajdują się w nim od linii 88. Większość linii ma postać:

KOD OPIS

np.

J12.81 Pneumonia due to SARS-associated coronavirus


jednak niektóre zawierają dodatkowe informacje lub są kontynuacją opisów nie mieszczących się w pojedynczej linii i powinny zostać pominięte.


* Zadanie 4.

Napisz interfejs ICDCodeTabular posiadający jedną, publiczną metodę getDescription, która dla podanego kodu choroby zwróci jej opis, lub gdy takiego kodu nie odnajdzie rzuci wyjątek IndexOutOfBoundsException. Zaimplementuj ten interfejs w dwóch klasach ICDCodeTabularOptimizedForTime oraz ICDCodeTabularOptimizedForMemory.


Klasa ICDCodeTabularOptimizedForTime powinna jednorazowo załadować wszystkie kody i opisy z pliku, a jej metoda getDescription powinna zwracać wartości z wybranej struktury danych w pamięci tymczasowej.


Klasa ICDCodeTabularOptimizedForMemory nie powinna przetrzymywać danych w pamięci operacyjnej, ale za każdym wywołaniem metody getDescription powinna otwierać plik i wyszukiwać w nim opisu.