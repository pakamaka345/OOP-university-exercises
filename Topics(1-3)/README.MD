# Temat 1. Klasa i obiekt

## Zadanie 1
Zdefiniuj klasę Point posiadającą dwa publiczne, ostateczne pola x, y. Napisz konstruktor ustawiający te wartości.
Zdefiniuj klasę Segment reprezentującą odcinek, posiadającą dwa prywatne punkty klasy Point. Wygeneruj akcesory i mutatory klasy Segment. Napisz publiczną metodę, która zwraca długość odcinka. W kolejnym kroku usuń mutatory i utwórz konstruktor ustawiający te pola na wartości swoich dwóch parametrów.

## Zadanie 2
Zdefiniuj w klasie Segment publiczną metodę toSvg(), która zwróci napis zawierający kod języka znacznikowego SVG pozwalający wyświetlić tę linię.

## Zadanie 3
Napisz funkcję (metodę klasy głównej), która przyjmie: obiekt segment klasy Segment oraz obiekt point klasy Point. Funkcja powinna zwrócić odcinek prostopadły do segment, rozpoczynający się w punkcie point o długości równej odcinkowi segment. Następnie zmodyfikuj tę metodę tak, aby zwracała tablicę dwóch możliwych do konstrukcji linii oraz przenieś tę metodę jako statyczną do klasy Segment. Szczególne przypadki należy zignorować.

## Zadanie 4
Zdefiniuj klasę Polygon posiadającą prywatną tablicę punktów. Konstruktor tej klasy powinien przyjmować tablicę punktów. Napisz publiczną metodę toSvg() działającą analogicznie jak w poprzednim zadaniu.

## Zadanie 5
W klasie Polygon napisz konstruktor kopiujący, wykonujący głęboką kopię obiektu.

# Temat 2. Paradygmaty programowania obiektowego

## Laboratorium

### Zadanie 1
Zdefiniuj klasę Style o finalnych, publicznych polach klasy String: fillColor, strokeColor oraz Double: strokeWidth. Napisz trójargumentowy konstruktor ustawiający te wartości. Napisz publiczną metodę toSvg() zwracającą napis, będący opcją style, którą można umieścić np. w tagu <polygon>.

Zmodyfikuj klasę Polygon dodając do jej konstruktora argument Style i modyfikując jej metodę toSvg(), aby uwzględniała styl. Dopuść możliwość pominięcia stylu przy konstrukcji. Wówczas należy narysować przezroczysty (fillColor) wielokąt, z czarnym obrysem (strokeColor) o grubości jednego piksela (strokeWidth).

### Zadanie 2
Napisz klasę SvgScene posiadającą prywatną listę obiektów Polygon. Napisz metodę, która przyjmuje obiekt klasy Polygon oraz dodaje go do listy w obiekcie SvgScene. Napisz funkcję save(String), która utworzy plik HTML w ścieżce danej argumentem i zapisze do niego reprezentacje wszystkich wielokątów znajdujących się na kanwie.

### Zadanie 3
Napisz publiczną, statyczną metodę wytwórczą klasy Polygon o nazwie square. Funkcja powinna przyjąć jako argumenty: obiekt Line, obiekt Style i zwrócić wielokąt będący kwadratem, którego przekątną jest dany odcinek.

Przeciąż metodę klasy Line zwracającą prostopadły odcinek tak, aby przyjmowała jako dodatkowy argument długość zwracanego odcinka.

### Zadanie 4a
Utwórz klasę abstrakcyjną Shape, która otrzyma jako pole, obiekt klasy Style. Uczyń pole tego obiektu chronionym. Utwórz publiczny konstruktor, który ustawia to pole. Napisz abstrakcyjną metodę toSvg(). Zmodyfikuj klasę Polygon, aby dziedziczyła po klasie Shape.

### Zadanie 4b
Napisz klasę Ellipse dziedziczącą po Shape, posiadającą prywatne pola: środek elipsy (Point), długości promieni i styl. W jej implementacji metody toSvg() powinno znaleźć się rysowanie z użyciem tagu <ellipse>.

Zmodyfikuj klasę SvgScene, aby posiadała tablicę obiektów klasy Shape i korzystając z polimorfizmu zapisz w niej obiekty typu Polygon i Ellipse.

# Temat 3. Polimorfizm. Elementy inżynierii oprogramowania

## Laboratorium

### Zadanie 0
Z klasy Shape usuń wszystkie pola i uczyń ją interfejsem. Przemianuj klasę Point na Vec2.

### Zadanie 1
Napisz klasę SolidFilledPolygon dziedziczącą po Polygon. Klasa powinna posiadać prywatne pole String color ustawiane, obok tablicy punktów, w konstruktorze. Przemodeluj funkcję toSvg w interfejsie Shape tak, aby możliwe było przekazanie jej parametru typu String, który zostanie umieszczony w tagu rysowanego obiektu. Wykorzystaj poniższy kod:
"<polygon points=\"%s\" %s />"
W klasie SolidFilledPolygon zdefiniuj metodę toSvg, która nadpisze metodę klasy nadrzędnej. Wewnątrz tej metody wywołaj metodę toSvg klasy nadrzędnej, przekazując jej jako parametr napis powstały ze sformatowania:
`"fill=\"%s\" %s "`
kolejno kolorem i parametrem napisowym.

Zastanów się, jakie konsekwencje dla struktury programu miałoby stworzenie analogicznej klasy dziedziczącej po klasie Ellipse oraz próba dodawania innych parametrów do tagu.

### Zadanie 2
Zdefiniuj klasę ShapeDecorator implementującą interfejs Shape, która posiadać będzie chronione pole Shape decoratedShape. Pole to powinno być ustawiane w konstruktorze. Nadpisz metodę toSvg w taki sposób, by zawierała wywołanie tej samej metody na rzecz obiektu decoratedShape.

Po klasie ShapeDecorator podziedzicz nową klasę SolidFillShapeDecorator. Klasa ta powinna posiadać prywatne pole String color. W konstruktorze ma przyjmować obiekt klasy Shape oraz kolor wypełnienia typu String. W jej metodzie toSvg wywołaj metodę toSvg na rzecz decoratedShape, z parametrami jak w zadaniu 1.

Utwórz dwa obiekty klasy SolidFillShapeDecorator tak, aby parametrem jednego był obiekt wielokąta, a drugiego elipsy.

### Zadanie 3
Utwórz klasę StrokeShapeDecorator posiadającą prywatne pola String color i double width, które powinny być ustawione w konstruktorze. Wywołaj metodę toSvg podobnie jak w zadaniu 2. formatując napis
`"stroke=\"%s\" stroke-width=\"%f\" "`
kolorem i grubością obrysu. Przetestuj udekorowanie tą klasą obiektów będących wynikiem poprzedniego zadania.

### Zadanie 4
Utwórz klasę TransformationDecorator odpowiadającą za wpisanie w wyświetlany tab informacji o przekształceniach afinicznych: translacji, rotacji i skalowaniu. Na potrzeby każdego z tych działań stwórz prywatne pola:

boolean translate, Vec2 translateVector,
boolean rotate, double rotateAngle, Vec2 rotateCenter,
boolean scale, Vec2 scaleVector.
Wewnątrz klasy TransformationDecorator zdefiniuj publiczną klasę Builder. Zdefiniuj w niej prywatne pola, jednakowe z polami w klasie zewnętrznej oraz pole Shape shape. Wartości logiczne powinny być fałszywe. Napisz po jednej metodzie ustawiającej parametry transformacji i zmieniającej wartość logiczną na prawdziwą na znak, że transformacja ma się wykonać. Funkcje powinny zwracać obiekt klasy Builder z wprowadzonymi zmianami. Napisz w klasie Builder metodę build, która utworzy obiekt TransformationDecorator, przekazując mu jako parametr obiekt shape, a następnie ustawi wszystkim polom w tym obiekcie wartości zapisane w obiekcie Buildera i zwróci tak stworzony obiekt.

W klasie TransformationDecorator nadpisz metodę toSvg tak, aby poskładać w niej napis definiujący transformację z elementów:
`"translate(%f %f) ", translateVector.x, translateVector.y
"rotate(%f %f %f) ", rotateAngle, rotateCenter.x, rotateCenter.y
"scale(%f %f) ", scaleVector.x, scaleVector.y`

Umieść je w we własności “transform”:
`"transform=\"%s\" %s"`, result, parameters.

Przetestuj tworzenie klasy TransformationDecorator za pomocą całości lub części dostępnych transformacji.

Uzyskanie możliwości zastosowania filtra oraz wypełnienia obiektu gradientem wymaga zapisania stosownego kodu w tagu <defs>, którego zawartość nie będzie wprost renderowana. Lokalne obiekty w SVG mogą być identyfikowane za pomocą unikalnych nazw (id), a odwoływać można się do nich za pomocą składni “url(#id)”.

### Zadanie 5a
W klasie SvgScene utwórz prywatne, statyczne pole SvgScene instance, początkowo równe null. Napisz akcesor do tego pola. Jeżeli znajduje się tam null, należy je zainicjalizować.

### Zadanie 5b
Dodaj do klasy SvgScene tablicę String defs[] oraz metodę dodającą elementy do tej tablicy, wzorując się na tablicy shapes i metodzie addShape. W metodzie saveHtml uwzględnij dopisanie tagów <defs> do wynikowego pliku.

Zdefiniuj klasę DropShadowDecorator dziedziczącą po ShapeDecorator. Jej zadaniem jest udekorowanie obiektu Shape rzucanym cieniem. Jest to realizowane przez umieszczenie w tagu <defs> sformatowanego kodu:

`\t<filter id=\"f%d\" x=\"-100%%\" y=\"-100%%\" width=\"300%%\" height=\"300%%\">\n" +
"\t\t<feOffset result=\"offOut\" in=\"SourceAlpha\" dx=\"5\" dy=\"5\" />\n" +
"\t\t<feGaussianBlur result=\"blurOut\" in=\"offOut\" stdDeviation=\"5\" />\n" +
"\t\t<feBlend in=\"SourceGraphic\" in2=\"blurOut\" mode=\"normal\" />\n" +
"\t</filter>", index`

oraz w metodzie toSvg:

`"filter=\"url(#f%d)\" ", index`

gdzie w obu przypadkach index jest liczbą całkowitą, unikalną dla tego filtra. Unikalność indeksu zagwarantuj przy użyciu prywatnego, statycznego pola klasy.

### Zadanie 6
Łącząc wiedzę wyniesioną z zadania 4 i 5 zdefiniuj klasę GradientFillShapeDecorator dziedziczącą po ShapeDecorator, której celem jest wypełnienie kształtu poziomym, barwnym gradientem.

Gradient wymaga umieszczenia w tagu <defs> napisu rozpoczynającego się od:
`"\t<linearGradient id=\"g%d\" >\n", index
a następnie dla każdego koloru i jego położenia:
\t\t<stop offset=\"%f\" style=\"stop-color:%s\" />\n", stop.offset, stop.color,
gdzie stop.offset jest liczbą zmiennoprzecinkową z przedziału 0-1, a stop.color napisem. Definicję gradientu zamyka:
"\t</linearGradient>"`

Wewnątrz klasy zdefiniuj klasę Builder. W klasie Builder stwórz metodę, która przyjmuje offset i kolor, a której wielokrotne wywołania pozwalają stworzyć tablicę tych wartości definiującą przebieg gradientu.

W metodzie toSvg klasy zewnętrznej wykorzystaj sformatowany napis:
`"fill=\"url(#g%d)\" ", index`

Rozważ, jakie modyfikacje należałoby poczynić w programie wynikowym, aby możliwa była rezygnacja z singletonowej postaci klasy SvgScene.