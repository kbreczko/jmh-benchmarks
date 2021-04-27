package com.github.kbreczko.jmhbenchmarks.example.findlast;

public class FindLastPrefixScanner1 {
    private final String searchedText;

    private String currentText;
    private int lastValue;

    public FindLastPrefixScanner1(String searchedText) {
        this.searchedText = searchedText;
    }

    public void consume(String name){
        this.currentText = name;
    }

    public void process(int value) {
        if (this.searchedText.startsWith(currentText)) {
            lastValue = value;
        }
    }

    public int getLastValue() {
        return lastValue;
    }
}
