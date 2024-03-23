# Projekt Amber - doskonale rozwoju języka
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

## switch 
## bloki tekstowe
## recordy
## dospasowanie wzorca z użyciem `instanceof`
## klasy `sealed`
