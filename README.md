# "Hohe Testabdeckung durch gutes OO-Design"

Dieses Projekt enthält den Quellcode für einen Workshop, in dem gezeigt wird wie objektorientierter Java-Code
so designt werden kann, dass der Code gut (automatisiert) getestet werden kann. Als "Nebeneffekt" erhält man
ein gutes Code-Design, in dem die Klassen von ihren Abhängigkeit entkoppelt sind. Dies macht den Code besser 
verständlich und leichter wartbar.

## Vorausetzungen

* Git
* Java 8
* Eine IDE mit Funktion zur Messung der Testabdeckung, z.B.
  * __IntelliJ IDEA__ (Community oder Ultimate Edition) - oder
  * __Eclipse__ mit EclEmma und M2Eclipse Plugins

## Verwendete Frameworks:
 
* [JUnit 4](https://junit.org/junit4/) - zum Implementieren und Ausführen von Unit-Tests.
* [Mockito](https://site.mockito.org/) - zum Ersetzen von Abhängigkeiten durch Testdoubles .
* [Apache Maven](https://maven.apache.org/) - Zur Verwaltung der Dependencies (Bibliotheken).

## Aufsetzen des Projekts

1. Klonen Sie dieses Projekt von GitHub.
1. Führen Sie im obersten Verzeichnis des Projekts auf einer Kommandozeile folgenden Befehl aus:

   ```$ mvnw clean install``` (Linux)

   ```$ mvnw.cmd clean install``` (Windows)

Der Build wird fehlschlagen, da die Unit-Tests noch nicht funktionsfähig sind. Ihre Aufgabe wird nun sein, die Testfälle 
"grün" zu machen ;-)

## Szenario 1: "Happy Hour"

### Ausgangssituation

Die Klasse `de.doubleslash.workshops.oodesign.happyhour.PriceService` liefert Preise für Cocktails -
und zwar unterschiedliche Preise, je nachdem ob gerade Happy Hour ist oder nicht, was wiederum von der aktuellen Uhrzeit abhängt. 

Zum Testen dieser Funktionalität wurde die Klasse `de.doubleslash.workshops.oodesign.happyhour.PriceServiceTest`
angelegt. Sie enthält Testmethoden zum Testen der Preise innerhalb sowie außerhalb der Happy Hour. Da immer nur eins
von beiden der Fall sein kann, werden immer zwei der vier Testmethoden fehlschlagen. 

#### Aufgabe 1: Testen des "PriceService"

Der `PriceService` muss von der Abhängigkeit "Zeit", also von der internen Nutzung von `LocalTime.now()` entkoppelt werden, 
damit das Testen des Service zu "unterschiedlichen Uhrzeiten" möglich wird.

1. Starten Sie den UnitTest `CocktailPriceServiceTest`. Sehen Sie wie zwei der vier Testmethoden fehlschlagen.
1. Fügen Sie dem Package `de.doubleslash.workshops.oodesign.happyhour` ein neues Interface namens `TimeProvider` hinzu, 
   mit der Methode `LocalTime getCurrentTime()`.
1. Schreiben Sie eine Klasse `CurrentTimeProvider`, die das Interface implementiert. Die Methode `getCurrentTime()` gibt
   `LocalDate.now()` zurück.
1. Fügen Sie dem Konstruktor der Klasse `CocktailPriceService` einen Parameter vom Interface-Typ `TimeProvider`
   hinzu. Speichern Sie das Argument als Klassenattribut namens `timeProvider`.
1. Ersetzen Sie den Ausdruck `LocalTime.now()` in der Klasse `CocktailPriceService` durch `timeProvider.getCurrentTime()`.
1. Die `Main`-Klasse im Package `happyhour` kompiliert jetzt nicht mehr, da der Konstruktor von `CocktailPriceService` nun 
   einen Parameter vom Typ `TimeProvider` erwartet. Übergeben Sie im Konstruktor eine Instanz der Klasse `CurrentTimeProvider`. 
1. Die Testklasse `CocktailPriceServiceTest` kompiliert auch nicht mehr, ebenfalls wegen des geänderten Konstruktors.
   Hier soll allerdings nicht der `CurrentTimeProvider` verwendet werden, da wir in den Tests kontrollieren möchten, welche
   Zeit vom `TimeProvider` zurückgegeben wird. 
   
   Schreiben Sie eine weitere Implementierung von `TimeProvider` eigens für den Unit-Test, und nennen Sie 
   sie `TestTimeProvider`. Die Klasse bekommt einen Konstruktor mit einem `LocalTime`-Parameter. Die dort übergebene Instanz
   wird von der `getCurrentTime()`-Methode zurückgegeben.
1. In der Methode `priceServiceAtTime` von `CocktailPriceServiceTest` übergeben Sie dem Konstruktor von `CocktailPriceService`
   nun ein `LocalTime`-Objekt mit der Uhrzeit aus den Methodenargumenten (`hour` und `minute`).
   
   => Der `CocktailPriceServiceTest` sollte jetzt erfolgreich durchlaufen. 


