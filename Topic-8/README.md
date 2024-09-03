### Temat 8. Testy
Zadanie 0.

Zapoznaj się ze zmianami wprowadzonymi do poprzednio napisanego projektu:

DatabaseConnection przechowuje połączenia w mapie,

AccountManager został przeniesiony do klasy Account.Persistence

Metoda Account.Persistence.register() zwraca id dodanego konta.

Wszystkie zadania należy wykonywać w pakiecie music.


Zadanie 1a.

Utwórz rekord Song składający się z napisów: artysty i tytułu oraz czasu trwania wyrażonego w sekundach. Utwórz klasę Playlist dziedziczącą po ArrayList<Song>. 


Zadanie 1b.

Napisz test sprawdzający, czy nowo utworzona playlista jest pusta.


Zadanie 1c.

Napisz test sprawdzający, czy po dodaniu jednego utworu playlista ma rozmiar 1.


Zadanie 1d.

Napisz jest sprawdzający, czy po dodaniu jednego utworu, jest w nim ten sam utwór.


Zadanie 1e.

Napisz jest sprawdzający, czy po dodaniu jednego utworu, jest w nim taki  sam utwór.


Zadanie 1f.

W klasie Playlist napisz metodę atSecond, która przyjmie całkowitą liczbę sekund i zwróci obiekt Song, który jest odtwarzany po tylu sekundach odtwarzania playlisty. Napisz test sprawdzający działanie tej metody.


Zadanie 1g.

Zmodyfikuj metodę atSecond tak, aby rzucała wyjątek IndexOutOfBoundsException, jeżeli zadany czas jest późniejszy niż czas odtwarzania playlisty. Napisz test sprawdzający rzucanie tego wyjątku.


Zadanie 1h.

Zmodyfikuj metodę atSecond, aby rozróżniała podanie ujemnego czasu i czasu wykraczającego poza czas trwania listy i zapisywała w argumencie message konstruktora IndexOutOfBoundsException odpowiedni napis. Napisz dwa testy sprawdzające każdy z wymienionych przypadków.


Zadanie 2a.

Zapoznaj się z bazą danych songs.db oraz plikiem songs.csv zawierającymi te same dane. W rekordzie Song stwórz publiczną, statyczną klasę Persistence. W tej klasie powinna znaleźć się metoda read, która przyjmuje indeks, a zwraca obiekt Optional<Song>, zapełniony lub nie, w zależności od poprawności indeksu w bazie.


Zadanie 2b.

Napisz test sprawdzający odczyt z bazy danych piosenki o poprawnym indeksie.


Zadanie 2c.

Napisz test sprawdzający próbę odczytu piosenki i niepoprawnym indeksie. Wydziel łączenie i rozłączanie się z bazą do oddzielnych metod i nadaj im odpowiednie adnotacje.


Zadanie 2d.

Napisz test sparametryzowany metodą zwracającą strumień indeksów i oczekiwanych piosenek.


Zadanie 2e.

Napisz test sparametryzowany plikiem songs.csv.


Zadanie 3a.

Zapoznaj się z klasą ListenerAccount. Klasa rozszerza klasę Account o liczbę kredytów oraz listę piosenek posiadaną na koncie. Obie dane znajdują się wyłącznie w bazie danych. Napisz test sprawdzający poprawność rejestracji nowego konta.


Zadanie 3b.

Napisz test sprawdzający poprawne logowanie się do konta.


Zadanie 3c.

Zaimplementuj metody dodawania i odczytu kredytów. Napisz test sprawdzające początkowy, pusty stan konta, oraz test dodawania kredytów.


Zadanie 3d.

Utwórz wyjątek NotEnoughCreditsException. Napisz metodę buySong przyjmującą id piosenki i następnie:
- jeżeli na koncie jest piosenka, nie robi nic,
- jeżeli na koncie nie ma dodanej piosenki dodaje ją i zabiera jeden kredyt,
- jeżeli na koncie nie ma piosenki, ale nie ma także kredytów, rzuca NotEnoughtCreditsException.
Napisz testy sprawdzające wszystkie wymienione przypadki.


Zadanie 3e.
Napisz metodę createPlaylist, która przyjmie listę id piosenek i zwróci obiekt Playlist zawierający te piosenki. Jeżeli piosenki nie znajdują się na koncie, na rzecz którego jest tworzona playlista, należy postąpić jak w poprzednim kroku. Napisz test porównujący tak utworzoną playlistę z ręcznie predefiniowaną.

