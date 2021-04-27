package com.github.kbreczko.jmhbenchmarks.example.findlast;

public class FindLastPrefixScanner2 {
    private final String searchedText;

    private int lastValue;
    private boolean searched;

    public FindLastPrefixScanner2(String searchedText) {
        this.searchedText = searchedText;
    }

    public void consume(String name){
        this.searched = this.searchedText.startsWith(name);
    }

    public void process(int value) {
        if (searched) {
            lastValue = value;
        }
    }

    public int getLastValue() {
        return lastValue;
    }
}
