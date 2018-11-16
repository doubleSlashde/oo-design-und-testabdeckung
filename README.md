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
* Grundlegende Kenntnisse in 
  * objektorientierter Programmierung mit Java (Klasse, Interface, Objekt, Konstruktor)
  * JUnit
  * der `java.time` API
  * Mockito
 
## Verwendete Frameworks:
 
* [JUnit 4](https://junit.org/junit4/) - zum Implementieren und Ausführen von Unit-Tests.
* [Mockito](https://site.mockito.org/) - zum Ersetzen von Abhängigkeiten durch Testdoubles.
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

## Szenario 2: "Geldautomat"

Die Klasse `de.doubleslash.workshops.oodesign.atm.ATM` (Automatic Teller Machine) repräsentiert 
einen **Geldautomaten**, mit dem Kunden einer Bank sich Geld auszahlen lassen können. 

Die Klasse `ATM` hat die folgenden **Abhängigkeiten**:

* **`CardReader`**: repräsentiert eine Hardwarekomponente, die die vom Benutzer eingegebene PIN verifiziert 
    und die Kontonummer ausliest
* **`AccountingRESTServiceClient`**: ruft einen REST-Service auf, über den die Abhebung auf dem Konto des Kunden verbucht wird
* **`MoneyDispenser`**: repräsentiert die Hardwarekomponente, die das Bargeld enthält und ausgibt.

Folgender Prozess ist in `ATM` implementiert:
![ATM](./src/UML/atm-sequence.png)

### Ausgangssituation

Offensichtlich wurde die Klasse `ATM` nicht testgetrieben entwickelt. Denn sie ist so gestaltet, 
dass sie ihre Abhängigkeiten selbst in ihrem Konstruktor erzeugt. Das macht die Klasse untestbar, denn
es ist nicht möglich, ihre Abhängigkeiten durch Testdoubles auszutauschen (diesen Vorgang nennt man
"mocken"; hierfür wird üblicherweise ein Mocking-Framework wie [Mockito](https://site.mockito.org/) verwendet).

Darüber hinaus ist die Klasse `de.doubleslash.workshops.oodesign.atm.AccountingRESTServiceClient` als 
Singleton implementiert. Dadurch kann in der laufenden Anwendung nur eine einzige Instanz existieren,
was das Testen zusätzlich erschwert.  

### Aufgabe 2.1: Audit-Log des Konto-Service testen

Die Klasse `AccountingRESTServiceClient` loggt alle Verbuchungen der Geldabhebungen. 
Da die Bank Audit-Verpflichtungen hat, soll ein Test sicherstellen, dass der Logaufruf tatsächlich passiert. 
Dafür wurde das Testgerüst `de.doubleslash.workshops.oodesign.atm.AccountingRESTServiceClientTest` angelegt.
Das Problem hierbei ist, dass die Logmethoden der Klasse `AuditLog` `static` sind 
und nicht ohne weiteres gemockt werden können.

1. Zunächst sorgen Sie dafür, dass die Klasse `de.doubleslash.workshops.oodesign.atm.AccountingRESTServiceClient` 
   kein Singleton mehr ist. Entfernen Sie dazu die Methode `getInstance()` sowie die statische Klassenvariable 
   `instance`, und machen Sie den Konstruktor `public`.
1. Ändern Sie die Log-Methoden `info`, `warn` und `error` der Klasse `de.doubleslash.workshops.oodesign.atm.log.AuditLog` 
   so dass diese nicht mehr `static` sind.
1. Fügen Sie dem Konstruktor von `AccountingRESTServiceClient` einen Parameter vom Typ `AuditLog` hinzu, und speichern Sie das 
   übergebene Argument als Instanzvariable mit Namen `log`.
1. Ersetzen Sie alle statischen Log-Aufrufe in der Klasse durch Aufrufe auf die neue `log`-Variable.
1. Die Klasse `ATM` kompiliert nun nicht mehr. Ändern Sie die Initailisierung der Variable `accountingService`, indem Sie 
   die Klasse `AccountingRESTServiceClient` über ihren Konstruktor initialisieren, dem Sie eine neue Instanz von `AuditLog` 
   mitgeben.
1. Tun Sie dasselbe in `AccountingRESTServiceClientTest` bei der Initialisierung von `testee`, damit auch die Testklasse 
   wieder kompiliert.
1. Nun soll die Klasse `AccountingRESTServiceClient` von ihrer Abhängigkeit zu `AuditLog` entkoppelt werden. Erstellen Sie 
   dazu ein neues Interface namens `Log` mit den Methoden `info`, `warn` und `error` wie sie in `AuditLog` definiert sind, 
   und lassen Sie `AuditLog` das Interface implementieren.
   Hierfür können Sie das "Extract Interface"-Refactoring Ihrer IDE nutzen. Die Enum `LogLevel` wandert von `AuditLog` 
   in das `Log`-Interface.
1. Ersetzen Sie im `AccountingRESTServiceClient` alle Stellen wo `AuditLog` verwendet wird durch das Interface `Log`. 
   Jetzt ist der `AccountingRESTServiceClient` nicht mehr abhängig von der konkreten `AuditLog`-Implementierung.
1. Um das Logging des `AccountingRESTServiceClient` testen zu können, erstellen Sie nun eine weitere Implementierung des
   `Log`-Interfaces namens `TestLog`, die ausschließlich fürs Testen gedacht ist. Daher landet die Klasse unterhalb von 
   `src\test\java\...`, d.h. im selben Verzeichnis wie die `AccountingRESTServiceClientTest`-Klasse.
1. Implementieren Sie die `info`-Methode der `TestLog`-Klasse so, dass sie alle geloggten Nachrichten in einer `List` 
   speichert.
   
   _Tipp:_ nutzen Sie hierfür die Methode `String#format(...)`, wie sie auch in `AuditLog` verwendet wird. 
1. Fügen Sie `TestLog` eine Methode `getLogMessages()` hinzu, die die Liste zurückgibt.
1. Im `AccountingRESTServiceClientTest` verwenden Sie nun statt `AuditLog` eine `TestLog`-Instanz. Diese muss als 
   Klassenattribut gespeichert werden, damit die Testmethode darauf zugreifen kann.
1. Jetzt können Sie die Testmethode `accountingServiceShouldLogTransaction()` fertig implementieren, indem Sie 
   die Liste der geloggten Nachrichten von `TestLog` abfragen und in der lokalen Variablen `loggedMessages` speichern.
   
   => Der `AccountingRESTServiceClientTest` sollte nun erfolgreich durchlaufen.
