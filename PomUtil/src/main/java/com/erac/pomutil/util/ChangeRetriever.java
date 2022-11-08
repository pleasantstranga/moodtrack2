package com.erac.pomutil.util;

import com.erac.pomutil.models.Change;
import com.erac.pomutil.models.ChangeType;
import com.erac.pomutil.models.ChangeVersionType;
import com.erac.pomutil.models.Dependency;
import org.apache.commons.lang3.StringUtils;

public class ChangeRetriever {

    private static void validate(Dependency d) throws Exception {
        if(null == d) {
            throw new Exception("Depency is null");
        }
        if(null == d.getChangeType()) {
            throw new Exception("Change type cannot be null");
        }
        if(null == d.getChangeVersionType()) {
            throw new Exception("Change version type cannot be null");
        }
        if(StringUtils.isEmpty(d.getCurrentVersion()) && d.getChangeType() != ChangeType.SET) {
            throw new Exception("You are trying to add or subtract from a currently empty version. Try using set operation");
        }

        //TODO: validate all numbers + dots or if contains SNAPSHOT
        //TODO:validate numbers exists between decimals
    }
    protected static Change retrieve(Dependency dependency) throws Exception {
        validate(dependency);
        int changeAmount = Integer.parseInt(dependency.getChangeAmount());
        if(ChangeVersionType.MAJOR.equals(dependency.getChangeVersionType())) {
            return configureChange(dependency.getCurrentVersion(), changeAmount, ChangeVersionType.MAJOR);
        }
        else if(ChangeVersionType.MINOR.equals(dependency.getChangeVersionType())) {
            return configureChange(dependency.getCurrentVersion(), changeAmount,ChangeVersionType.MINOR);
        }
        else if(ChangeVersionType.PATCH.equals(dependency.getChangeVersionType())) {
            return configureChange(dependency.getCurrentVersion(),changeAmount, ChangeVersionType.PATCH);
        }
        else if(ChangeVersionType.FULL.equals(dependency.getChangeVersionType())) {
            return configureChange(dependency.getCurrentVersion(),changeAmount, ChangeVersionType.FULL);
        }
        throw new Exception("You are using an unsupported version type");
    }
    private static Change configureChange(String version, int changeAmount,  ChangeVersionType type) throws Exception {
        int startIndex = 0;
        int endIndex = 0;
        Integer currentValue = null;
        int minorIndex = version.contains(".") ? version.indexOf(".") : -1;
        //int patchIndex = minorIndex == -1 ? -1 :  version.indexOf(minorIndex,version.indexOf('.'));
        int patchIndex = StringUtils.ordinalIndexOf(version, ".", 2);
        int snapshotIndex = version.contains("-SNAPSHOT") ? version.indexOf("-SNAPSHOT") : -1;
        //TODO: HANDLE SNAPSHOT
        if(snapshotIndex != -1) {
            version = version.substring(0,snapshotIndex);
        }
        if(ChangeVersionType.MAJOR.equals(type)) {
            if(minorIndex != -1) {
                startIndex = 0;
                endIndex = minorIndex;
                currentValue = Integer.valueOf(version.substring(0, minorIndex));
            }
            else {
                startIndex = 0;
                endIndex = version.length();
                currentValue = Integer.valueOf(version);
            }
        }
        else if(ChangeVersionType.MINOR.equals(type)) {
           if(minorIndex == -1) {
               throw new Exception("Can't change minor version");
           }
           else {
               if(patchIndex != -1) {
                   startIndex = minorIndex+1;
                   endIndex = patchIndex;
                   currentValue = Integer.valueOf(version.substring(startIndex, patchIndex));
               }
               else {
                   startIndex = minorIndex+1;
                   endIndex = version.length();
                   currentValue = Integer.valueOf(version.substring(startIndex));
               }
           }
        }
        else if(ChangeVersionType.PATCH.equals(type)) {
            if(patchIndex == -1) {
                throw new Exception("Can't change patch version");
            }
            else {
                startIndex = patchIndex+1;
                endIndex = version.length();
                currentValue = Integer.valueOf(version.substring(startIndex));
            }
        }
        else if(ChangeVersionType.FULL.equals(type)) {
            String changeAmountS = String.valueOf(changeAmount);
            startIndex = 0;
            endIndex = changeAmountS.length();
            currentValue = changeAmount;
        }
        return  new Change(startIndex,endIndex,changeAmount,currentValue);
    }
}
