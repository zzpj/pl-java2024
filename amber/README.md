# Projekt Amber - doskonalenie i rozwój języka
![project-amber-timeline.png](img%2Fproject-amber-timeline.png)
![java22-arrival.png](img%2Fjava22-arrival.png)
Żródło: https://openjdk.org/projects/amber/ 

## Inferencja typów z użyciem `var` (ang.: Local-Variable Type Inference (var))
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

## Fabryka niemodyfikowalnych (immutable) kolekcji
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

## Niemutowalne kolekcje zwracane z użyciem `Stream`
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


## switch
## recordy
## dospasowanie wzorca z użyciem `instanceof`
## klasy `sealed`
