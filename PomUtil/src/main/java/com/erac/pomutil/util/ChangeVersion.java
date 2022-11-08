package com.erac.pomutil.util;

import com.erac.pomutil.models.Change;
import com.erac.pomutil.models.ChangeType;
import com.erac.pomutil.models.ChangeVersionType;
import com.erac.pomutil.models.Dependency;

public class ChangeVersion {

    public static org.apache.maven.model.Dependency change(org.apache.maven.model.Dependency pomDependency, Dependency dependency) throws Exception {
        Change change = ChangeRetriever.retrieve(dependency);
        String newVersion = replaceVersionWithNewValue(dependency, change);
        pomDependency.setVersion(newVersion);
        System.out.println("Changed dependency: " + dependency.getGroupId() + " version from " + dependency.getCurrentVersion() + " to " + newVersion);
        return pomDependency;
    }

    private static Integer getNewVersionValue(Dependency dependency, Change change) {
         if(null == dependency.getChangeType()) {
            return -1;
        }
        if (ChangeType.SET.equals(dependency.getChangeType())) {
            return change.getChangeAmount();
        } else if (ChangeType.ADD.equals(dependency.getChangeType())) {
            return change.getCurrentValue() + change.getChangeAmount();
        } else {
            return change.getCurrentValue() - change.getChangeAmount();
        }
    }

    private static String replaceVersionWithNewValue(Dependency dependency, Change change) {
        //TODO: DEAL WITH SNAPSHOT VERSIONS "-SNAPSHOT"
        Integer newVersion = getNewVersionValue(dependency, change);
        StringBuffer buf = new StringBuffer(dependency.getCurrentVersion());
        if(ChangeType.SET.equals(dependency.getChangeType()) && ChangeVersionType.FULL.equals(dependency.getChangeVersionType())) {
            return String.valueOf(change.getChangeAmount());
        } else {
            return buf.replace(change.getStartIndex(), change.getEndIndex(), String.valueOf(newVersion)).toString();
        }
    }

}
