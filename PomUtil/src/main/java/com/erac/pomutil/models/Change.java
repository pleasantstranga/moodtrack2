package com.erac.pomutil.models;

public class Change extends org.apache.maven.model.Dependency {

    private int startIndex;
    private int endIndex;
    private int changeAmount;
    private int snapshotIndex;
    private Integer currentValue;

    public Change(int startIndex, int endIndex, int changeAmount, int currentValue) {
        this.startIndex = startIndex;
        this.endIndex = endIndex;
        this.currentValue = currentValue;
        this.snapshotIndex = -1;
        this.changeAmount = changeAmount;
    }

    public int getStartIndex() {
        return startIndex;
    }

    public void setStartIndex(int startIndex) {
        this.startIndex = startIndex;
    }

    public int getEndIndex() {
        return endIndex;
    }

    public void setEndIndex(int endIndex) {
        this.endIndex = endIndex;
    }

    public int getChangeAmount() {
        return changeAmount;
    }

    public void setChangeAmount(int changeAmount) {
        this.changeAmount = changeAmount;
    }

    public int getCurrentValue() {
        return currentValue;
    }

    public void setCurrentValue(int currentValue) {
        this.currentValue = currentValue;
    }
}

