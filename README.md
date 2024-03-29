# "Hohe Testabdeckung durch gutes OO-Design"

Dieses Projekt enthält Quellcode, mit dem gezeigt wird, wie objektorientierter Java-Code so designt werden kann, dass der Code gut mit Unit-Tests getestet werden kann. Als "Nebeneffekt" entsteht ein besseres Code-Design, in dem die Klassen von ihren Abhängigkeit entkoppelt sind. Das macht den Code besser verständlich und leichter wartbar.

Der Schlüssel hierfür ist "Dependency Injection" (DI) - das heißt, ein Objekt erzeugt bzw. erschafft sich seine Abhängigkeiten nicht mehr selbst; stattdessen werden diese von außen in das Objekt hinein gegeben. Dafür ist nicht unbedingt ein DI-Framework wie Spring oder JEE/CDI notwendig. Hier verwenden wir "manuelle DI" mittels _Constructor Injection_: die abhängigen Module werden dem Objekt im Konstruktor übergeben.

Dependency Injection fördert gleichzeitig das "Single Responsibility Principle": Objekte werden von der zusätzlichen Verantwortlichkeit befreit, ihre Abhängigkeiten selbst zu beschaffen, und können sich ausschließlich ihrer eigentlichen Aufgabe widmen.

Für das Testen bedeutet das, dass die Abhängigkeiten in Tests einfach durch Mock-Objekte ersetzt werden können, was das unabhängige Testen der einzelnen Klassen ermöglicht.

## Voraussetzungen

* Git
* Java 11
* Eine IDE mit Funktion zur Messung der Testabdeckung, z.B.
  * __IntelliJ IDEA__ (Community oder Ultimate Edition) - oder
  * __Eclipse__ mit EclEmma- und M2Eclipse-Plugins
* Grundlegende Kenntnisse in den folgenden Themengebieten:
  * objektorientierte Programmierung mit Java (Klasse, Instanz bzw. Objekt, Interface, Konstruktor)
  * JUnit
  * `java.time` API
  * Mockito
 
## Verwendete Frameworks:
 
* [JUnit 5](https://junit.org/junit5/) - zum Implementieren und Ausführen von Unit-Tests.
* [Mockito](https://site.mockito.org/) - zum Ersetzen von Abhängigkeiten durch Testdoubles.
* [AssertJ](https://assertj.github.io/doc/) - für sprechendere Asserts
* [Apache Maven](https://maven.apache.org/) - Zur Verwaltung der Dependencies (Bibliotheken).

## Aufsetzen des Projekts

1. Klonen Sie dieses Projekt von GitHub.
1. Führen Sie im obersten Verzeichnis des Projekts auf einer Kommandozeile folgenden Befehl aus:
   
   Linux:
   ```$ mvnw clean install```

   Windows:
   ```> mvnw.cmd clean install```

Der Build wird fehlschlagen, da die Unit-Tests noch nicht funktionsfähig sind. Ihre Aufgabe wird nun sein, die Testfälle 
"grün" zu machen.

## Szenario 1: "Happy Hour"

### Ausgangssituation

Die Klasse `de.doubleslash.workshops.oodesign.happyhour.PriceService` liefert __Preise für Cocktails__ -
und zwar unterschiedlich, je nachdem ob gerade Happy Hour ist oder nicht. Das wiederum hängt von der aktuellen Uhrzeit ab. 

Zum Testen dieser Funktionalität wurde die Testklasse `de.doubleslash.workshops.oodesign.happyhour.PriceServiceTest`
angelegt. Sie enthält Methoden zum Testen der Preise innerhalb sowie außerhalb der Happy Hour. Da zur gleichen Zeit immer nur eins
von beiden der Fall sein kann, werden immer zwei der vier Testmethoden fehlschlagen. 

#### Aufgabe 1: Testen des "PriceService"

Der `PriceService` muss von der Abhängigkeit "Zeit" entkoppelt werden, damit das Testen des Service zu "unterschiedlichen Uhrzeiten" möglich wird.
Die Abhängigkeit entsteht durch die Nutzung von `LocalTime.now()` im Programmcode. 

1. Starten Sie den UnitTest `CocktailPriceServiceTest`. Sehen Sie wie zwei der vier Testmethoden fehlschlagen.

1. Fügen Sie dem Package `de.doubleslash.workshops.oodesign.happyhour` ein neues Interface namens `TimeProvider` hinzu, 
   mit der Methode `LocalTime getCurrentTime()`.

1. Schreiben Sie eine Klasse `CurrentTimeProvider`, die das Interface implementiert. Die Methode `getCurrentTime()` gibt
   `LocalTime.now()` zurück.

1. Fügen Sie dem Konstruktor der Klasse `CocktailPriceService` einen Parameter vom Interface-Typ `TimeProvider`
   hinzu. Speichern Sie das Argument in einer Instanzvariable namens `timeProvider`.

1. Ersetzen Sie den Ausdruck `LocalTime.now()` in der Klasse `CocktailPriceService` durch `timeProvider.getCurrentTime()`.

1. Die `Main`-Klasse im Package `happyhour` kompiliert nicht mehr, da der Konstruktor von `CocktailPriceService` nun 
   einen Parameter vom Typ `TimeProvider` erwartet. Übergeben Sie im Konstruktor eine neue Instanz der Klasse `CurrentTimeProvider`. 

1. Auch der `CocktailPriceServiceTest` kompiliert nicht mehr, ebenfalls aufgrund des geänderten Konstruktors.
   Hier soll allerdings nicht der `CurrentTimeProvider` verwendet werden, da wir in den Tests kontrollieren möchten, welche
   Zeit vom `TimeProvider` zurückgegeben wird. 
   
   Schreiben Sie eine weitere Implementierung von `TimeProvider` eigens für den Unit-Test, und nennen Sie 
   sie `TestTimeProvider`. Die Klasse bekommt einen Konstruktor, der einen `LocalTime`-Parameter entgegen nimmt. 
   Die dort übergebene Instanz wird von der `getCurrentTime()`-Methode zurückgegeben.

1. In der Methode `priceServiceAtTime` von `CocktailPriceServiceTest` übergeben Sie dem Konstruktor von `CocktailPriceService`
   nun ein `TestTimeProvider`-Objekt, das mit einer `LocalTime`-Instanz mit der Uhrzeit aus den Methodenargumenten `hour` und `minute`
   initialisiert wird. __Tipp:__ Verwenden Sie `LocalTime.of(...)`.
   
   => Der `CocktailPriceServiceTest` sollte jetzt erfolgreich durchlaufen. 

## Szenario 2: "Geldautomat"

Die Klasse `de.doubleslash.workshops.oodesign.atm.ATM` (Automatic Teller Machine) repräsentiert 
einen **Geldautomaten**, mit dem Kunden einer Bank sich Geld auszahlen lassen können. 

Die Klasse `ATM` hat folgende **Abhängigkeiten**:

* **`CardReader`**: repräsentiert eine Hardwarekomponente, die die vom Benutzer eingegebene PIN verifiziert 
    und die Kontonummer ausliest.
* **`AccountingRESTServiceClient`**: ruft einen REST-Service auf, über den die Abhebung auf dem Konto des Kunden verbucht wird.
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
   und lassen Sie `AuditLog` das Interface implementieren. Das Enum `LogLevel` wandert von `AuditLog` in das `Log`-Interface.
   
   __Tipp:__ Hierfür können Sie das "Extract Interface"-Refactoring Ihrer IDE nutzen. 

1. Ersetzen Sie im `AccountingRESTServiceClient` alle Referenzen zu `AuditLog` durch das Interface `Log`. 
   Jetzt ist der `AccountingRESTServiceClient` nicht mehr abhängig von der konkreten `AuditLog`-Implementierung.

1. Um das Logging des `AccountingRESTServiceClient` testen zu können, erstellen Sie nun eine weitere Implementierung des
   `Log`-Interfaces namens `TestLog`, die ausschließlich fürs Testen gedacht ist. Daher landet die Klasse unterhalb von 
   `src\test\java\...`, d.h. im selben Verzeichnis wie die `AccountingRESTServiceClientTest`-Klasse.

1. Implementieren Sie die `info(...)`-Methode der `TestLog`-Klasse so, dass sie alle dort geloggten Nachrichten in einer `List` 
   namens `infoMessages` speichert.
   
   __Tipp:__ nutzen Sie hierfür die Methode `String#format(...)`, wie sie auch in `AuditLog` verwendet wird. 

1. Fügen Sie `TestLog` eine Methode `getInfoMessages()` hinzu, die die Liste zurückgibt.

1. Im `AccountingRESTServiceClientTest` verwenden Sie nun statt `AuditLog` eine `TestLog`-Instanz. Diese muss als 
   Klassenattribut gespeichert werden, damit die Testmethode darauf zugreifen kann.

1. Jetzt können Sie die Testmethode `accountingServiceShouldLogTransaction()` fertig implementieren, indem Sie 
   die Liste der mit `info(...)` geloggten Nachrichten von `TestLog` abfragen und in der lokalen Variablen `loggedMessages` speichern.
   
   => Der `AccountingRESTServiceClientTest` sollte nun erfolgreich durchlaufen.

### Aufgabe 2.2: Funktionalität der Klasse ATM testen 

Für die Geldautomat-Funktionalität in `ATM` sollen nachträglich Unit-Tests geschrieben werden.

Verschiedene Szenarien sollen getestet werden, z.B. dass keine Auszahlung erfolgt, wenn die PIN falsch 
eingegeben wurde bzw. die Abhebung nicht verbucht werden konnte.

Dafür existiert bereits die UnitTest-Klasse `de.doubleslash.workshops.oodesign.atm.ATMTest` mit
entsprechenden Testmethoden, die noch ausimplementiert werden müssen. Damit die Klasse `ATM`
getestet werden kann, muss sie zunächst einem Refactoring unterzogen werden. Danach können die Testmethoden fertiggestellt werden.

1. Schauen Sie sich die Klasse `CardReader` an. Diese simuliert ein Hardware-Modul; daher liefern die Methoden `verifyPin()`und `readAccountNumber()`
   nicht immer dieselben Ergebnisse. Jede Kundenkarte liefert eine andere Kontonummer, und ab und zu kommt es vor dass
   ein Kunde seine PIN falsch eingibt. Dieses "zufällige" Verhalten (hier simuliert anhand von zufällig generierten Werten) 
   erschwert das Testen der Klasse `ATM`, würde diese im Test die "echte" `CardReader`-Implementierung nutzen.

1. Schauen Sie sich die Klasse `ATMTest` und die darin definierten Testfälle an. 

1. Der Konstruktor von `ATM` erzeugt seine Abhängigkeiten selbst (`CardReader`, `AccountingRESTServiceClient` und `MoneyDispenser`).
   Ändern Sie dies, indem Sie den Konstruktor von `ATM` so erweitern, dass er Objekte dieser drei Klassen als Parameter entgegen nimmt.
   Speichern Sie die Argumente aus dem Konstruktor anstelle der mit `new` erzeugten Instanzen in den vorhandenen Klassenvariablen.

1. Die Methode `Main` kompiliert nicht mehr. Korrigieren Sie dies, indem Sie die erwarteten Konstruktor-Parameter bei der Instanzierung von `ATM` hinzufügen (`new CardReader()` etc.).

1. Die Testklasse `ATMTest` kompiliert ebenfalls nicht mehr. Bei der Instanzierung von `testee` (d.h. "Testkandidat") verwenden Sie 
   jedoch keine echten Instanzen der abhängigken Klassen, sondern sogenannte Mock-Objekte. Hierzu verwenden Sie das Mocking-Framework
   [Mockito](https://site.mockito.org/).

   Ein Mock-Objekt für die Klasse `CardReader` wird beispielsweise folgendermaßen erzeugt:  
   ```@Mock private CardReader cardReaderMock;```

   Die Mock-Objekte müssen als Instanzvariablen der Testklasse definiert werden, damit die Testmethoden darauf zugreifen können.

   __Hinweis:__ Die Annotation `@ExtendWith(MockitoExtension.class)` über dem Klassenheader sorgt dafür, dass alle mit `@Mock` annotierten Felder der Klasse automatisch mit Mockobjekten initialisiert werden.

1. Jetzt können die Testmethoden in `ATMTest` fertig implementiert werden. Beginnen Sie mit der ersten Methode
   `accountingServiceShouldBeCalledWithCorrect...`.

   Standardmäßig geben Mock-Objekte `0`, `false` oder `null` zurück wenn eine ihrer Methoden aufgerufen wird. Mit 
   `Mockito.when(...).thenReturn(...);` kann man dafür sorgen, dass ein Mock für einen bestimmten Methodenaufruf einen
   definierten Wert zurückgibt. Der `CardReader`-Mock soll z.B. die Kontonummer _4711_ zurückliefern, wenn seine 
   `radAccountNumber()`-Methode aufgerufen wird. 
   Dies erreichen Sie mit `Mockito.when(cardReaderMock.readAccountNumber()).thenReturn(4711);`.
   Weiterhin müssen Sie dafür sorgen, dass die `verifyPin()`-Methode von CardReader `true` zurückgibt. Tun Sie das analog
   mit `Mockito.when(...).thenReturn(...)`.
   
   Nach dem Aufruf der zu testenden Methode `testee.withdrawMoney(1234, 100.0)` soll geprüft werden, ob `ATM`
   die Methode `withdrawAmount(...)` von `AccountingRestServiceClient` aufgerufen hat, und zwar mit den korrekten Werten -
   also dem Betrag aus `testee.withdrawMoney(...)` sowie der Kontonummer, die von `cardReaderMock` geliefert wurde.
   
   Hierfür gibt es `Mockito.verify(...)`. 
   Ersetzen Sie das `fail(...)`-Statement am Ende der Testmethode durch: `Mockito.verify(accountingServiceMock).withdrawAmount(100.0, 4711);`.
   
   => Wenn Sie alles richtig gemacht haben, sollte diese Testmethode jetzt erfolgreich durchlaufen.

1. Implementieren Sie die restlichen Testmethoden nach dem gleichen Schema, bis die ganze Testklasse "grün" ist.
   
__Mockito-Tipps:__
* Es ist auch möglich zu verifizieren, dass eine bestimmte Methode (z.B. `xyz(...)`) auf einem Mock (z.B. `myMock`) _nicht_ aufgerufen wurde:
`Mockito.verify(myMock, Mockito.never()).xyz(...);`
* Wenn bei Methodenaufrufen auf Mock-Objekte der Wert der Argumente egal ist, können Sie statt konkreten Werten
 auch `Mockito.anyInt()`, `Mockito.anyDouble()` etc. übergeben.
* Es ist auch möglich, einen Mock beim Aufruf einer bestimmten Methode eine Exception werfen zu lassen, z.B.:
 `Mockito.when(myMock.methodCall()).thenThrow(new SomeException());`
* Sollte etwas mit den Mockito-Methoden nicht wie erwartet funktionieren, versichern Sie sich bitte ob die Klammern korrekt gesetzt sind.
* Das JavaDoc von Mockito gibt Auskunft über die Verwendung der Methoden des Frameworks. 
  
 _(Unabhängig von Mockito: ein Blick ins JavaDoc lohnt sich immer! ;-)_

### Aufgabe 2.3: Entkopplung der Klasse `ATM` von der konkreten `AccountingRESRServiceClient`-Implementierung

Ein Makel im Code-Design existiert immer noch. Die Klasse `ATM` ist abhängig von einer konkreten Account-Service-Implementierung,
nämlich vom `AccountingRESRServiceClient`.

Der Klasse `ATM` sollte es allerdings egal sein, ob der verwendete Service technisch als REST-Client oder anderweitig realisiert ist.
Sollte der Service tatsächlich einmal durch eine andere Implementierung ersetzt werden, müsste `ATM` in ihrem jetzigen Zustand ebenfalls angepasst werden.

Zudem wäre es möglich, dass `ATM` Methoden von `AccountingRESTServiceClient` aufruft, die eigentlich Implementierungsdetails sind
(z.B. REST-spezifisch). In dem Fall wäre der Änderungsaufwand beim Austausch der Accounting-Service-Implementierung noch größer,
da diese implementierungsspezifischen Aufrufe entfernt werden müssen.


Hier können Sie Abhilfe schaffen, indem Sie `ATM` von der konkreten Accounting-Service-Implementierung entkoppeln:

1. Erstellen Sie ein neues Interface mit Namen `AccountingService` und der Methode `boolean withdrawAmount(double amount, int bankAccountNumber);`.
1. Lassen Sie den `AccountingRESRServiceClient` das Interface implementieren.
1. Ersetzen Sie in `ATM` alle Referenzen auf `AccountingRESRServiceClient` durch das Interface `AccountingService`. 
1. Gehen Sie in `ATMTest` ebenso vor. Der `ATMTest` sollte nach wie vor erfolgreich durchlaufen.

=> `ATM` ist nun unabhängig von konkreten `AccountService`-Implementierungen. Der `AccountingRESRServiceClient` kann nun 
durch eine andere Klasse ausgetauscht werden, ohne dass `ATM` oder deren Test angefasst werden müssen. Aufgrund des Interfaces
 ist es auch nicht mehr möglich, dass `ATM` implementationsspezifische Methoden des verwendeten AccountService verwendet.
  
`ATM` ist nun lose an den `AccountingService` gekoppelt. 
Die Wartbarkeit des Codes wurde dadurch beträchtlich gesteigert.

## Testbarkeit & Testabdeckung

Wenn Sie alle Aufgaben gelöst haben und die Testklassen erfolgreich durchlaufen, starten Sie die Tests mit der Funktion zur
Coverage-Messung in Ihrer IDE. Sie werden feststellen, dass die Testabdeckung der Klassen, für die es Unit-Tests gibt
(also `PriceService`, `AccountingRESTServiceClient` und `ATM`), jeweils 100% ist! 

Aus den zuvor nicht testbaren Klassen haben Sie Klassen mit __maximaler Testabdeckung__ gemacht. Herzlichen Glückwunsch! `:-)`

_P.S.: die Lösung ist im Branch `solution` eingecheckt._
