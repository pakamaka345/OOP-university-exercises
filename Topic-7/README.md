### Temat 7. Pakiety
* Zadanie 1.

Utwórz mavenowy projekt o nazwie site zawierający dwa pakiety: database oraz auth. W pakiecie database stwórz klasę DatabaseConnection, posiadającą prywatne pole java.sql.Connection oraz publiczny akcesor do niego. Klasa powinna posiadać także metody publiczne:
- connect, która przyjmie ścieżkę do pliku z bazą w formacie SQLite i połączy się z nią,

- disconnect, która rozłączy się z bazą.

Poza pakietami napisz klasę Main, w której przetestowane zostanie utworzenie, zapis i odczyt danych z tabeli.


* Zadanie 2.

W pakiecie auth utwórz rekord Account zawierający id i nazwę użytkownika. Stwórz także klasę AccountManager posiadającą metody publiczne:

- register, dodającą nowego użytkownika do bazy danych,

- authenticate, zwracająca wartość logiczną mówiącą, czy nazwa użytkownika i hasło zgadzają się,

- getAccount, zwracającą obiekt Account dla zadanej nazwy użytkownika lub id.

Hasło w bazie należy zaszyfrować z użyciem biblioteki bcrypt. W klasie Main przetestuj napisane metody.


* Zadanie 3.

Z projektu site usuń klasę Main, ale zachowaj jej zawartość w bezpiecznym miejscu. Skonfiguruj w swoim koncie na Githubie rejestr pakietów Mavena. Umieść w nim napisaną bibliotekę site. Utwórz mavenowy projekt o nazwie shop zawierający pakiet music. Zaimportuj w nim bibliotekę site. Umieść w nim plik z usuniętą wcześniej klasą Main i dostosuj jej kod, aby możliwe było uruchomienie metody main.


* Zadanie 4.

W pakiecie shop zdefiniuj rekord Product (id, nazwa i cena) oraz klasę Cart (id, konto kupującego, lista par: id produktu, liczebność). W klasie Cart zdefiniuj publiczne metody:

- konstruktor, generujący informację o koszyku (id i id klienta),

- add - dodającą produkt w określonej liczbie (mnożnik) do koszyka,

- price - zwracającą wartość koszyka.


* Zadanie 5.

W pakiecie shop napisz klasę ShopClient dziedziczącą po auth.Account. Napisz w niej publiczną metodę getCarts, która zwraca listę koszyków wskazanej osoby.

