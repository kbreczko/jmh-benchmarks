package com.github.kbreczko.jmhbenchmarks;

import java.io.IOException;


/**
 * Uruchomienie testów wydajnościowych w celu porównania Java 8 z Java 11.
 * Założenia: Maksymalny rozmiar sterty to 4GB.
 */
public class Main {

    public static void main(String[] args) throws IOException {
        org.openjdk.jmh.Main.main(args);
    }
}
