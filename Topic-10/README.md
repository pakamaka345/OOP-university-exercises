### Temat 10. Serwer webowy
Zadanie 1.

Napisz aplikację, która uruchomi serwer webowy, który po połączeniu wyświetli napis “Hello World”.


Zadanie 2.

Utwórz klasę Rectangle składającą się z całkowitych parametrów: położenia x, położenia y, szerokości, wysokości oraz koloru wyrażonego napisem. Wygeneruj konstruktor, akcesory oraz mutatory do wszystkich pól.
Napisz kontroler REST RectangleController posiadający metodę, której wywołanie spowoduje wyświetlenie obiektu Rectangle zmapowanego na JSON.


Zadanie 3.

W kontrolerze:

Stwórz prywatną listę prostokątów.

Napisz metodę, która dodaje prostokąt o określonych w kodzie parametrach.

Napisz metodę, która zwróci listę prostokątów zmapowaną na JSON.

Napisz metodę, która wygeneruje napis zawierający kod SVG z prostokątami znajdującymi się na liście.


Zadanie 4.

Zmodyfikuj metodę dodającą prostokąt, by odpowiadała na żądanie HTTP POST. Niech metoda przyjmuje prostokąt, który zostanie zdefiniowany w ciele żądania HTTP.


Zadanie 5.

Napisz metody:

GET z argumentem typu int,  zwracającą prostokąt w liście o podanym indeksie.

PUT z argumentem typu int i argumentem typu Rectangle, modyfikującą istniejący na liście pod tym indeksem prostokąt na prostokąt przekazany argumentem.

DELETE  z argumentem typu int, usuwającą prostokąt z listy z miejsca o podanym indeksie.


Zadanie 6.

Napisz kontroler REST ImageController, w którym znajdzie się metoda zawracająca obraz ze zmodyfikowaną jasnością. Metoda typu GET powinna przyjąć obraz w formacie base64 oraz liczbę całkowitą określającą jasność. Metoda powinna rozjaśnić obraz o podaną wartość i zwrócić go w formacie base64.


Zadanie 7.

Napisz kolejną, zbliżoną metodę, w której wyniku znajdzie się niezakodowany obraz.


Zadanie 8.

Napisz kontroler ImageFormController. Skorzystaj z udostępnionych szablonów w plikach index.html i image.html. W kontrolerze napisz:

Metodę, która wyświetli plik index.html.

Metodę upload, która zostanie wyzwolona przez naciśnięcie przycisku Upload. Metoda powinna wyświetlić plik image.html, wyświetlając w nim przesłany obraz.


Zadanie 9.

Zmodyfikuj formatkę w pliku index.html i dodaj do niej pole, w którym można ustawić zmianę jasności. Niech naciśnięcie przycisku Upload spowoduje wyświetlenie załadowanego obrazu rozjaśnionego o daną wartość.

