# Projekt Amber - doskonalenie i rozwój języka
![project-amber-timeline.png](img%2Fproject-amber-timeline.png)
![java22-arrival.png](img%2Fjava22-arrival.png)
Żródło: https://openjdk.org/projects/amber/ 

## Inferencja typów z użyciem `var` (ang. Local-Variable Type Inference (var))
Inferencja typów – mechanizm w językach statycznie typowanych, w którym kompilator określa typ danych na podstawie informacji dostępnych w czasie 
kompilacji, np. typów zadeklarowanych wcześniej lub określania typów na podstawie wartości już znanych zmiennych.

Od jdk10: 
```java
var list = new ArrayList<String>();  // infers ArrayList<String>
var stream = list.stream();          // infers Stream<String>
```

```java
List<String> names = new ArrayList<>();
// ^ developers complain about degree of boilerplate code
//var names = new ArrayList<>();
names.add("aa");
names.add("bb");
names.add("cc");
//names.add(2);
System.out.println(names);
```
Ograniczenia: 
```java
//it won't work, it causes:  "cannot infer type for local variable x"
var x;
var x = null;
var x = { 1 , 2 };
```

## Fabryka niemodyfikowalnych (ang. immutable) kolekcji
Przed jdk9:
```java
List<String> names = new ArrayList<>();
names.add("aa");
names.add("bb");
names.add("cc");
List<String> unmodifiableList = Collections.unmodifiableList(names);
unmodifiableList.add("zz");
```
Od jdk9 (przydatne dla kolekcji z małą, niemodyfikowalną liczbą elementów), działa dla `List`, `Set` oraz `Map`:
```java
List<String> newUmodificableList = List.of("aa", "bb", "cc");
newUmodificableList.add("zz");
```

## Niemutowalne kolekcje zwracane z użyciem `stream()`
```java
List<String> names = new ArrayList<>();
names.add("aa");
names.add("bb");
names.add("cc");
List<String> listFromStream = names.stream().collect(Collectors.toUnmodifiableList());
listFromStream.add("zzz");
//From jdk16
List<String> listFromStream_v2 = names.stream().toList();
```

## Rozwój `Optional`
Dodanie `orElseThrow`, przed jdk10, należało użyć metody `isPresent`, bo inaczej narażalismy się na wyjątek `NoSuchElementException`
oraz ostrzeżenie (warning). Po dodaniu "zapach kodu" jest lepszy

```java
public static void main(String[] args) {
    Optional<String> result = getResult();
    //before orElseThrow
    if(result.isPresent()) {
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

## Uproszczenie skomplikowanego API operacji na plikach
Wczytywanie i zapisywanie plików (od jdk11):
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

// intent (jdk12)
String indent15 = "indent\nplease".indent(15);

// lines (jdk11)
List<String> list = ("ttt \r www \n qwqq \t bbb").lines().toList();

// transform (jdk12)
List<String> transformed = "Ola\nKasia\nZosia".transform(e -> e.lines().toList());

// splitWithDelimiters (jdk21)
String[] splitWithDelimiters = "Long::brown::curly::hair".splitWithDelimiters("::", 3);
```
## Bloki tekstowe
Reprezentacja jsona/sql/xml itp
```json
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
```
Przed jdk15:
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
Po jdk15:
```java
String mrJsonSinceJdk15 = """
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
List<String> list = mrJsonSinceJdk15.lines().toList();
```

## Dopasowanie wzorca z użyciem `instanceof` (ang. Pattern Matching for `instanceof` )
Przed jdk16:
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
2. declarowanie nowych zmiennych `s` lub `i`
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

## Wyrażenie `switch` oraz ewolucja przez kolejne wersje
Do jdk7 włącznie tylko liczby całkowite (`int`) mogły być używane:
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
Od jdk8 dodano możliwość użycia `String` oraz `enum`
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
Od jdk13 mozemy zwrócić bezpośrednio z uzyciem słowa kluczowego `yield` (w jdk12 było to `break`, ale tylko jako preview): 
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
Lub od jdk12 z wykorzystaniem operatora strzałki `->`
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
Od jdk12, można było grupować opcje:
```java
    private static String switchEvolution(String day) {
        return switch (day) {
            case "Monday", "Tuesday", "Wednesday", "Thursday", "Friday" -> "Week day";
            case "Saturday", "Sunday" -> "Weekend";
            default -> "Unknown";
        };
    }
```
Teraz dopiero zaczyna się ból głowy, bo dokładamy "pattern matching"... (dorzucone w jdk17 jako "preview")
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
Na początku "łącznikiem" (w jdk17 jako preview) w wyrazeniu `case` na połączenie "pattern matching" oraz "quarded patterns" 
był `&&` , potem zamieniono na słowo kluczowe `when`. Czy tak się stanie w przypadku `instanceof`? Zobaczymy...

## Recordy


## Klasy `sealed`

