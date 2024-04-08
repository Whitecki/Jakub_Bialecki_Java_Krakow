<h2>Opis</h2>
<p>Projekt Basket Splitter rozdziela listę produktów między dostawców, bazując na problemie pokrycia zbioru (Set Cover Problem), będącym wyzwaniem NP-trudnym. W klasie <code>SplittingAlgorithm</code>, algorytm generuje kombinacje dostawców, starając się maksymalizować ilość produktów dostarczanych przez każdego z nich. <code>DeliveryCapabilityChecker</code> ocenia, które kombinacje mogą dostarczyć wszystkie produkty, a <code>SupplierProductAssigner</code> finalizuje przydział produktów.</p>

<h2>Struktura Projektu: Basket Splitter</h2>
<p><strong>Klasy Główne:</strong><br />
<code>BasketSplitter</code>: Klasa główna odpowiedzialna za dzielenie listy przedmiotów na grupy według dostawców zgodnie z załadowaną konfiguracją.</p>
<p><strong>Klasy Pomocnicze:</strong><br />
<code>ProductSupplierLoader</code>: Wczytuje konfigurację z pliku JSON.</p>
<p><strong>Klasy Algorytmiczne:</strong><br />
<code>DeliveryCapabilityChecker</code>, <code>SplittingAlgorithm</code>, <code>SupplierProductAssigner</code>.</p>
<p><strong>Klasa Rekordu:</strong><br />
<code>Product</code>, <code>Supplier</code>, <code>SupplierDeliveryInfo</code> (wewnątrz <code>DeliveryCapabilityChecker</code>).</p>
<p><strong>Wyjątek:</strong><br />
<code>ItemNotFoundException</code>.</p>

<h2>Jak Korzystać</h2>
<p><strong>Budowanie z Gradle</strong><br />
W terminalu projektu uruchom:</p>
<pre><code>./gradlew build</code></pre>
<p>To skompiluje kod i utworzy plik JAR.</p>

<p><strong>Tworzenie Fat-JAR</strong><br />
Dodaj plugin ShadowJar w <code>build.gradle</code> i wykonaj:</p>
<pre><code>./gradlew shadowJar</code></pre>
<p>Znajdziesz fat-JAR w <code>build/libs</code>.</p>

<p><strong>Dodanie jako Zależność</strong><br />
Umieść fat-JAR w folderze <code>libs</code> Twojego projektu i dodaj w <code>build.gradle</code>:</p>
<pre><code>dependencies {
    implementation files('libs/BasketSplitter-1.0-SNAPSHOT-all.jar')
}</code></pre>


<h2>Testy</h2>
<ul>
  <li><strong>testOptimalCourierSelection:</strong> Testuje, czy klasa <code>BasketSplitter</code> prawidłowo wybiera najbardziej optymalnego dostawcę dla podanej listy produktów, weryfikując, czy rozmiar największego koszyka wynosi 2.</li>
  <li><strong>testIllegalArgumentExceptionForEmptyItemList:</strong> Sprawdza, czy metoda <code>split</code> klasy <code>BasketSplitter</code> rzuca wyjątek <code>IllegalArgumentException</code>, gdy przekazana jest pusta lista przedmiotów.</li>
  <li><strong>testCourierCapacityEvaluation:</strong> Weryfikuje, czy algorytm klasy <code>BasketSplitter</code> prawidłowo ocenia pojemność kurierów.</li>
  <li><strong>testItemNotFoundExceptionForMissingItems:</strong> Sprawdza, czy klasa <code>BasketSplitter</code> zgłasza wyjątek <code>ItemNotFoundException</code> dla przedmiotów, które nie są obecne w mapie produktów do dostawców.</li>
  <li><strong>testReadJsonFile</strong> (w <code>ProductSupplierLoaderTest</code>): Testuje czy metoda <code>readJsonFile</code> klasy <code>ProductSupplierLoader</code> poprawnie odczytuje dane JSON.</li>
</ul>

<h2>Obsługa Wyjątków</h2>
<ul>
  <li><code>ProductSupplierLoader</code>: Rzuca <code>RuntimeException</code>.</li>
  <li>Konstruktor <code>BasketSplitter</code>: Rzuca <code>IllegalStateException</code>.</li>
  <li>Metoda <code>Split</code> w <code>BasketSplitter</code>: Rzuca <code>IllegalArgumentException</code>, <code>RuntimeException</code> i <code>ItemNotFoundException</code>.</li>
</ul>
