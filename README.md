# jmh-benchmarks

## Przeprowadzenie testów

### 1. Instalacja wybranej wersji Java i ustawienie ściezki do katalogu w zmiennych środowiskowych

### 2. Zbudowanie artefaktu
    
    ./mvnw clean install assembly:single
    
### 3. Uruchomienie testów w tle z ustawieniem rozmiarów sterty na 2GB

    nohup java -jar -Xms2g -Xmx2g -XX:+AlwaysPreTouch jmh-benchmarks-1.0-SNAPSHOT-jar-with-dependencies.jar > ./result.txt &