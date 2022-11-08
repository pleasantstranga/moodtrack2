package com.erac.pomutil.models;


public class Dependency extends org.apache.maven.model.Dependency {

    private ChangeType changeType;
    private ChangeVersionType changeVersionType;
    private String changeAmount;
    private String currentVersion;
    private boolean isSnapShot;

    public ChangeType getChangeType() {
        return changeType;
    }

    public void setChangeType(ChangeType changeType) {
        this.changeType = changeType;
    }

    public ChangeVersionType getChangeVersionType() {
        return changeVersionType;
    }

    public void setChangeVersionType(ChangeVersionType changeVersionType) {
        this.changeVersionType = changeVersionType;
    }

    public String getChangeAmount() {
        return changeAmount;
    }

    public void setChangeAmount(String changeAmount) {
        this.changeAmount = changeAmount;
    }

    public String getCurrentVersion() {
        return currentVersion;
    }

    public void setCurrentVersion(String currentVersion) {
        this.currentVersion = currentVersion;
    }

    public boolean isSnapShot() {
        return isSnapShot;
    }

    public void setIsSnapShot(boolean snapShot) {
        isSnapShot = snapShot;
    }
}

