# Projekt Amber - doskonalenie i rozwój języka

![project-amber-timeline.png](img%2Fproject-amber-timeline.png)
![java22-arrival.png](img%2Fjava22-arrival.png)

- Źródło: https://openjdk.org/projects/amber/
-

Przykłady: https://www.happycoders.eu/java/ ([cheatsheet w pdf](tmp%2Fjava-versions-cheat-sheet-happycoders.eu-v22.0.3.pdf))

## Inferencja typów z użyciem `var` (ang. Local-Variable Type Inference `var`)

Inferencja typów – mechanizm w językach statycznie typowanych, w którym kompilator określa typ danych na podstawie
informacji dostępnych w czasie
kompilacji, np. typów zadeklarowanych wcześniej lub określania typów na podstawie wartości już znanych zmiennych.

Od java10:

```java
var list = new ArrayList<String>();  // infers ArrayList<String>
var stream = list.stream();          // infers Stream<String>
```

```java
List<String> names = new ArrayList<>();
// ^ developers complain about degree of boilerplate code
//var names = new ArrayList<>();
names.

add("aa");
names.

add("bb");
names.

add("cc");
//names.add(2);
System.out.

println(names);
```

Ograniczenia:

```java
//it won't work, it causes:  "cannot infer type for local variable x"
var x;
var x = null;
var x = {1, 2};
```

## Fabryka niemodyfikowalnych (ang. immutable) kolekcji

Przed java9:

```java
List<String> names = new ArrayList<>();
names.

add("aa");
names.

add("bb");
names.

add("cc");

List<String> unmodifiableList = Collections.unmodifiableList(names);
unmodifiableList.

add("zz");
```

Od java9 (przydatne dla kolekcji z małą, niemodyfikowalną liczbą elementów), działa dla `List`, `Set` oraz `Map`:

```java
List<String> newUmodificableList = List.of("aa", "bb", "cc");
newUmodificableList.

add("zz");
```

## Niemutowalne kolekcje zwracane z użyciem `stream()`

```java
List<String> names = new ArrayList<>();
names.

add("aa");
names.

add("bb");
names.

add("cc");

List<String> listFromStream = names.stream().collect(Collectors.toUnmodifiableList());
listFromStream.

add("zzz");

//From java16
List<String> listFromStream_v2 = names.stream().toList();
```

## Rozwój `Optional`

Przed java10, należało użyć metody `isPresent`, bo inaczej narażalismy się na wyjątek `NoSuchElementException`
oraz ostrzeżenie (warning). Po dodaniu `orElseThrow`, "zapachy kodu" jest lepsze:

```java
public static void main(String[] args) {
    Optional<String> result = getResult();
    //before orElseThrow
    if (result.isPresent()) {
        String s = result.get();
        System.out.println(s);
    }
    //after
    String s = result.orElseThrow();
    System.out.println(s);
}

private static Optional<String> getResult() {
    //return Optional.of("any value");
    return Optional.empty();
}
```

## Uproszczenie API do operacji na plikach

Wczytywanie i zapisywanie plików (od java11):

```java
public static void main(String[] args) throws IOException {

    Path path = Files.createTempFile("myfile", ".txt");

    Files.writeString(path, "Some text to be saved");

    String string = Files.readString(path);

    System.out.println(string);
}
```

## Nowe metody dla klasy `String` (łancuch znaków)

```java
// repeat
String repeat = "ha".repeat(20);

// strip() vs trim()
String trimmed = " to be trimmed \t".trim();
String strip = " to be stripped \t \n".strip();
String stripLeading = " from leading".stripLeading();
String stripTrailing = "from tailing  ".stripTrailing();

// isBlank vs isEmpty
// The isBlank() method, introduced in Java 11, is identical to isEmpty() with the nuance 
// that it also returns true for Strings that contain only whitespace characters.
boolean empty = "".isEmpty();
boolean blank = "".isBlank();
boolean blank1 = "  ".isBlank();
boolean blank2 = "\t\n\r\f ".isBlank();

// intent (java12)
String indent15 = "indent\nplease".indent(15);

// lines (java11)
List<String> list = ("ttt \r www \n qwqq \t bbb").lines().toList();

// transform (java12)
List<String> transformed = "Ola\nKasia\nZosia".transform(e -> e.lines().toList());

// splitWithDelimiters (java21)
String[] splitWithDelimiters = "Long::brown::curly::hair".splitWithDelimiters("::", 3);
```

### Emoji

```java
String welcomeMsg = "Hey Java Developers! 🙋🏻‍♂️";
```

## Bloki tekstowe

Reprezentacja jsona/sql/xml itp

```json
{
  "menu": {
    "id": "file",
    "value": "File",
    "popup": {
      "menuitem": [
        {
          "value": "New",
          "onclick": "CreateNewDoc()"
        },
        {
          "value": "Open",
          "onclick": "OpenDoc()"
        },
        {
          "value": "Close",
          "onclick": "CloseDoc()"
        }
      ]
    }
  }
}
```

Przed java15:

```java
String mrJson = "{\"menu\": {\n" +
        "  \"id\": \"file\",\n" +
        "  \"value\": \"File\",\n" +
        "  \"popup\": {\n" +
        "    \"menuitem\": [\n" +
        "      {\"value\": \"New\", \"onclick\": \"CreateNewDoc()\"},\n" +
        "      {\"value\": \"Open\", \"onclick\": \"OpenDoc()\"},\n" +
        "      {\"value\": \"Close\", \"onclick\": \"CloseDoc()\"}\n" +
        "    ]\n" +
        "  }\n" +
        "}}";
```

Po java15:

```java
String mrJsonSincejava15 = """
        {"menu": {
          "id": "file",
          "value": "File",
          "popup": {
            "menuitem": [
              {"value": "New", "onclick": "CreateNewDoc()"},
              {"value": "Open", "onclick": "OpenDoc()"},
              {"value": "Close", "onclick": "CloseDoc()"}
            ]
          }
        }}
        """;
List<String> list = mrJsonSincejava15.lines().toList();
```

## Dopasowanie wzorca z użyciem `instanceof` (ang. Pattern Matching for `instanceof` )

Przed java16:

```java
public static void main(String[] args) {

    Object obj = getObject();
    if (obj instanceof String) {
        String s = (String) obj;
        if (s.length() > 5) {
            System.out.println(s.toUpperCase());
        }
    } else if (obj instanceof Integer) {
        Integer i = (Integer) obj;
        System.out.println(i * i);
    }
}

private static Object getObject() {
    return "I am string now";
}
```

W powyższym snippecie dzieją się aż 3 rzeczy

1. testowanie czy `obj` jest typu  `String` lub `Integer`
2. deklarowanie nowych zmiennych `s` lub `i`
3. kastowanie obiektu na typ `String` lub `Integer`

```java
public static void main(String[] args) {

    Object obj = getObject();
    if (obj instanceof String s && s.length() > 5) {
        System.out.println(s.toUpperCase());
    } else if (obj instanceof Integer i) {
        System.out.println(i * i);
        // note that here `String s` is out of scope
    }
}

private static Object getObject() {
    return "I am string now";
}
```

## Wyrażenie `switch` oraz ewolucja przez kolejne wydania

Do java7 włącznie, tylko liczby całkowite (`int`) mogły być używane:

```java
public static void main(String[] args) {
    switchEvolution(5);
    switchEvolution(-1);
}

private static void switchEvolution(int value) {
    switch (value) {
        case 1:
            System.out.println("One");
            break;
        case 5:
            System.out.println("five");
            break;
        default:
            System.out.println("Unknown");
    }
}
```

Od java8 dodano możliwość użycia `String` oraz `enum`

```java
public static void main(String[] args) {
    switchEvolution("Monday");
    switchEvolution("Sunday");
}

private static void switchEvolution(String day) {
    switch (day) {
        case "Monday":
            System.out.println("Week day");
            break;
        case "Tuesday":
            System.out.println("Week day");
            break;
        case "Wednesday":
            System.out.println("Week day");
            break;
        case "Thursday":
            System.out.println("Week day");
            break;
        case "Friday":
            System.out.println("Week day");
            break;
        case "Saturday":
            System.out.println("Weekend");
            break;
        case "Sunday":
            System.out.println("Weekend");
            break;
        default:
            System.out.println("Unknown");
    }
}
```

Od java13 mozemy zwrócić bezpośrednio z uzyciem słowa kluczowego `yield` (w java12 było to `break`, ale tylko jako
preview):

```java
public static void main(String[] args) {
    System.out.println(switchEvolution("Monday"));
    System.out.println(switchEvolution("Sunday"));
}

private static String switchEvolution(String day) {
    return switch (day) {
        case "Monday":
            yield "Weekday";
        case "Tuesday":
            yield "Weekday";
        case "Wednesday":
            yield "Weekday";
        case "Thursday":
            yield "Weekday";
        case "Friday":
            yield "Weekday";
        case "Saturday":
            yield "Weekend";
        case "Sunday":
            yield "Weekend";
        default:
            yield "Unknown";
    };
}
```

Lub od java12 z wykorzystaniem operatora strzałki `->`

```java
private static String switchEvolution(String day) {
    return switch (day) {
        case "Monday" -> "Week day";
        case "Tuesday" -> "Week day";
        case "Wednesday" -> "Week day";
        case "Thursday" -> "Week day";
        case "Friday" -> "Week day";
        case "Saturday" -> "Weekend";
        case "Sunday" -> "Weekend";
        default -> "Unknown";
    };
}
```

Od java12, można było grupować opcje:

```java
    private static String switchEvolution(String day) {
    return switch (day) {
        case "Monday", "Tuesday", "Wednesday", "Thursday", "Friday" -> "Week day";
        case "Saturday", "Sunday" -> "Weekend";
        default -> "Unknown";
    };
}
```

Teraz dopiero zaczyna się ból głowy, bo dokładamy "pattern matching"... (dorzucone w java17 jako "preview")

```java
public static void main(String[] args) {
    switchEvolution("Hello from new switch expression and pattern matching");
    switchEvolution(1000);
    switchEvolution(new File(""));
}

private static void switchEvolution(Object object) {
    switch (object) {
        case Integer i -> System.out.printf("I am integer: %d \n", i);
        case Double d -> System.out.printf("I am double: %f \n", d);
        case String s -> System.out.printf("I am string: %s \n", s);
        default -> throw new IllegalStateException("Unexpected value: " + object);
    }
}
```

Skomplikujmy i dorzućmy "null cases" oraz "gaurded patterns":

```java
public static void main(String[] args) {
    switchEvolution("Hello from new switch expression and pattern matching");
    switchEvolution(1000);
    //switchEvolution(new File(""));
    switchEvolution(null);
    switchEvolution("Hello");

}

private static void switchEvolution(Object object) {
    switch (object) {
        case Integer i when i > 10 && i < 2000 -> System.out.printf("I am integer: %d \n", i);
        case Double d -> System.out.printf("I am double: %f \n", d);
        case String s when s.length() > 10 -> System.out.printf("I am string: %s \n", s);
        case null -> System.out.println("I am null :(");
        default -> throw new IllegalStateException("Unexpected value: " + object);
    }
}
```

Na początku "łącznikiem" (w java17 jako preview) w wyrazeniu `case` na połączenie "pattern matching" oraz "quarded
patterns"
był `&&`, potem zamieniono na słowo kluczowe `when`. Czy tak się stanie w przypadku `instanceof`? Zobaczymy...

## Rekordy

Jako "preview" w java14, standard od jdk16, przed:

```java
class Person {
    private Long id;
    private String username;
    private int value;
    //generate constructor + setters and getters + hashCode, equals and toString methods
}
```

Pośrednie rozwiązanie: `Lombok`

```java

@Data
@AllArgsConstructor
class Person {
    private Long id;
    private String name;
}
```

Jednak wprowadzenie rekordów pozwala na stworzenie niemutowalnych obiektów tej "klasy"

```java
public class Sandbox {
    public static void main(String[] args) {
        Person person = new Person(1L, "Zbyszko");
        System.out.println(person);
        System.out.println(person.hashCode());
    }
}

record Person(Long id, String username) {
}
```

Również otrzymujemy dostęp do pól, konstruktor z polami klas, metody `equals`, `hashCode` oraz `toString`, a oprócz
tego możemy wprowadzić pole statyczne lub dodatkowe metody:

```java
record Person(Long id, String username) implements Personable {
    private static final UUID UUID_IDENTIFIER = UUID.randomUUID();

    public boolean isIdPositiveNumber() {
        return id > 0;
    }

    @Override
    public boolean isHuman() {
        return true;
    }
}

interface Personable {
    boolean isHuman();
}
```

Rekordy są niejawnie (ang. implicitly) finalne (ang. final), czyli nie możemy z nich dziedziczyć oraz one same nie mogą
dziedziczyć z klas.

Do stworzonego rekordu automatycznie przypisywany jest "kanoniczny konstruktor" (ang. canonical constructor) - tak jak w
klasie, w której nie ma zadeklarowanego konstruktora, dostajemy "domyślny". Jednak może zostać stworzony kompaktowy (
ang.
compact cannonical constructor), sprawdzający niejawnie przypisane parametry:

```java
Person {
    if (id < 0) {
        throw new IllegalArgumentException(String.format("%d is less than 0", id));
    }
}
```

Możemy też przeciążyć konstruktor i podać np. mniejszą liczbę parametrów, ale zostanie pod tym wywołany
konstruktor kanoniczny wraz z tym kompaktowym, bo pola są domyślnie finalne:

```java
public static void main(String[] args) {
    //Person person = new Person(-1L, "Zbyszko");
    //Person person = new Person(-1L);
    Person person = new Person(1L);
    System.out.println(person);
}

record Person(Long id, String username) {

    // custom constructor
    public Person(Long id) {
        this(id, null);
    }

    // compact canonical
    Person {
        if (id < 0) {
            throw new IllegalArgumentException(String.format("%d is less than 0", id));
        }
    }
}
```

### Rekordy vs Lombok

- Oba rozwiązania eliminują nadmiarowość kodu (ang. boilerplate code)
- Rekordy są do małych, niemutowalnych klas
- Dla klas z dużą ilością pól, Lombok może wygenerować wzorzec buildera
- Klasy z adnotacjami Lomboka są mutowalne
- Rekordy nie wspierają dziedziczenia

## Klasy `sealed` w kontekście dziedziczenia
- Oznaczając zajęcia jako `final`, zapobiegamy **całkowicie** dziedziczeniu
- Jednak od java 17, możemy zaimplementować tak zwaną „hierarchię klas zapieczętowanych”:
  - Klasę, której podklasy chcemy ograniczyć, zaznaczamy słowem kluczowym `sealed`.
  - Używając słowa kluczowego `permits` podajemy listę dozwolonych podklas.

Implementacja wyjściowa:
```java
interface Shapeable {
}

abstract class Shape implements Shapeable {
  abstract int getNumberOfSides();
}

class Circle extends Shape {

  @Override
  int getNumberOfSides() {
    return 0;
  }
}

class Triangle extends Shape {

  @Override
  int getNumberOfSides() {
    return 3;
  }
}

class GeometricalOperations {
  // do nothing
}
```
1. Dodajemy słowo kluczowe `permits` to klasy `Shapeable` i ograniczamy do klasy `Shape`, która powinna się oznaczyć jako `non-sealed`
2. Zmieniamy przy klasie `Shape` na `sealed` i dodajemy `permits Circle, Triangle`
3. Oznaczamy klasy `Triangle` i `Cirle` jako `non-sealed` albo `final`
4. Próbujemy `class GeometricalOperations extends Shape` jednak nie jest na liście dozwolonych klas

### Kontekstowe słowa kluczowe (ang. Contextual Keywords)

Wprowadzenie nowych słów kluczowych, takich jak `sealed`, `non-sealed`, `permits` (lub `switch`) zrodziło wśród
programistów JDK następujące pytanie: Co powinno się stać z istniejącym kodem, który używa tych słów kluczowych jako
nazw metod lub zmiennych?

Ponieważ Java przywiązuje dużą wagę do kompatybilności wstecznej, zdecydowano nie wpływać w jak największym stopniu na
istniejący kod. Jest to możliwe dzięki tak zwanym „kontekstowym słowom kluczowym”, które mają znaczenie tylko w
określonym kontekście. Zatem `sealed`, `non-sealed`, `permits` mają znaczenie tylko w kontekście definicji klasy.

## Sequence collections

Interfejs `SequencedCollection` dostarcza jednolite metody dostępu do elementów kolekcji ze stabilną kolejnością
iteracji, są dwie metody `getFirst` i `getLast`.
Te metody są dostępne dla:

- `List` (np. `ArrayList`, `LinkedList`)
- `SortedSet` i jego rozszerzenie NavigableSet (np. `TreeSet`)
- `LinkedHashSet`

Oprócz powyższych metod, `SequencedCollection` definiuje również następujące metody:

- `void addFirst(E)` – wstawia element na początku kolekcji
- `void addLast(E)` – dołącza element na końcu kolekcji
- `E removeFirst()` – usuwa pierwszy element i zwraca go
- `E removeLast()` – usuwa ostatni element i zwraca go
  W przypadku kolekcji niemutowalnych wszystkie cztery metody zgłaszają wyjątek `UnsupportedOperationException`.

Natomiast metoda `reversed` zwraca widok kolekcji w odwrotnej kolejności. Możemy go użyć do iteracji wstecz po kolekcji.

```java
public static void main(String[] args) {
    List<Integer> arrayList = new ArrayList<>();
    arrayList.add(1);   // List contains: [1]

    arrayList.addFirst(0);  // List contains: [0, 1]
    arrayList.addLast(2);   // List contains: [0, 1, 2]

    Integer firstElement = arrayList.getFirst();  // 0
    Integer lastElement = arrayList.getLast();  // 2
    System.out.printf(" first is %s and the last is %s", firstElement, lastElement);

    List<Integer> reversed = arrayList.reversed();
    System.out.println(reversed); // Prints [2, 1, 0]

    System.out.println("------------------------------");
    LinkedHashMap<Integer, String> map = new LinkedHashMap<>();

    map.put(1, "One");
    map.put(2, "Two");
    map.put(3, "Three");

    map.firstEntry();   //1=One
    map.lastEntry();    //3=Three

    System.out.println(map);  //{1=One, 2=Two, 3=Three}

    Map.Entry<Integer, String> first = map.pollFirstEntry();   //1=One
    Map.Entry<Integer, String> last = map.pollLastEntry();    //3=Three

    System.out.println(map);  //{2=Two}

    map.putFirst(1, "One");     //{1=One, 2=Two}
    map.putLast(3, "Three");    //{1=One, 2=Two, 3=Three}

    System.out.println(map);  //{1=One, 2=Two, 3=Three}
    System.out.println(map.reversed());   //{3=Three, 2=Two, 1=One}
}
```

## Przykłady dodania adnotacji `@Deprecated` w SDK

- URI (ang. Uniform Resource Identifier) String identyfikujący konkretny zasób w internecie (obrazek, dokument, etc)
- URL (Uniform Resource Locator) określa kolacje zasobu, podzespół URI

```java
URL url_deprecated = new URL("http://github.com");
URL url_new = URI.create("http://github.com").toURL();
```

## JDK22 - nienazwane zmienne i wzorca (ang. Unnamed Variables & Patterns)

```java
public static void main(String[] args) {
    String iAmInteger = "10e";
    try {
        int i = Integer.parseInt(iAmInteger);
        //...
    } catch (NumberFormatException _) {        // Unnamed variable
        System.out.println("Bad number: " + iAmInteger);
    }
}
```

Uruchom z terminala: `java --enable-preview --source 22 Sandbox.java`

## Java 21-22 - eksperymentalnie - odchudzenie protokołu uruchomieniowego (ang. launch protocol)

```java
package com.example;

public class Sandbox {
    public static void main(String[] args) {
        String welcomeMsg = "Hey Java Developers! 🙋🏻‍♂️";
        System.out.println(welcomeMsg);
    }
}
```

Ekspermentalnie, włączając opcje eksperymentalne w IntelliJ:

```java
void main() {
    String welcomeMsg = "Hey Java Developers! 🙋🏻‍♂️";
    System.out.println(welcomeMsg);
}
```

Lub z konsoli `java --enable-preview --source 22 Sandbox.java`
