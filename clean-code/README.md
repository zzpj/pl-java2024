# Clean Code

**Zadanie 1:** Clean Code

Klasa `pl.zzpj.cleancode.doIt` nie przestrzega zasad Clean Code. Przerefaktoruj kod zgodnie z zasadami poznanymi podczas prezentacji.

Pytania pomocnicze:
- Jaka jest rola tej klasy? 
- Jaką funkcjonalność realizuje?
- Jaki jest najlepszy sposób na sprawdzenie poprawności implementacji kolejnych metod tej klasy? 
- Czy wszystkie metody są zaimplementowane poprawnie?
---

**Zadanie 2:** Single Responsibility Principle

Reprezentacja modelu ksiązki oraz możliwość jej drukowania (czytania) powinna być odseparowana. Dostarcz oddzielne implementacje modelu książki oraz 
jej drukowania zgodnie z Single Responsibility Principle. 

Pakiet, w którym należy umieścić rozwiązanie: `pl.zzpj.solid.srp.book.solution`

---

**Zadanie 3:** Open-closed principle

Limity prędkości i kary za jej przekroczenie są różne dla każdego z 50 stanów USA. Należy dostarczyć łatwo rozszerzalną implementację, w której każdy 
stan będzie mógł zdefiniować swoje kary. Na chwilę obecną załóżmy, że będą to 3 stany USA, które skorzystają z naszego API, ale niewykluczone, że w 
przyszłości inne stany również decydują się je wykorzystać.

Pakiet, w którym należy umieścić rozwiązanie: `pl.zzpj.solid.ocp.usa.solution`

---

**Zadanie 4:** Liscov Substitution Principle

Podstawowym przykładem obrazującym zasadę Barbary Liskov jest zbudowanie aplikacji, która będzie korzystała z własności podstawowych figur geometrycznych: 
kwadrat, prostokąt, koło... wraz z obliczaniem ich obwodu oraz pola powierzchni. Czy z punktu widzenia programisty i programowania obiektowego 
kwadrat jest prostokątem?

Pakiet w którym należy dostarczyć rozwiązanie: `pl.zzpj.solid.lsp.shape`

---

**Zadanie 5:** Interface Separation Principle

Klasa `Contact` ma dwie własności, jedna z nich przedstawia możliwość kontaktowania się przez wiadomość email, a druga to kontakt telefoniczny. 
Zaleca się wyróżnienie tych własności za pomocą interfejsów. Interfejsy powinny deklarować metody charakterystyczne dla danej własności.

Pakiet, w którym należy dostarczyć rozwiązanie: `pl.zzpj.solid.isp.contactbook.solution`

---

**Zadanie 6:** Dependency Inversion Principle

Mamy serwis informujący o zmianach pogodowych. Informacje o zmianach wysyłane są na różne urządzenia: telefony lub skrzynki poczty elektronicznej. Oba te urządzenia łączy jedna wspólna cecha jaką jest odbiór wiadomości i jej wyświetlenie. Telefon i skrzynka są modułami niskopoziomowymi, natomiast "Tracker" jest modułem wysokopoziomowym, który nie powinien zależeć od modułów niskopoziomowych.

Pakiet, w którym należy dostarczyć rozwiązanie: `pl.zzpj.solid.dip.weathertracker.solution`