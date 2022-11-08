package com.erac.pomutil;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class FileList {

    private static String POM = "pom.xml";

    public List<String> getPomFilePath() {
        return pomFilePath;
    }

    public void setPomFilePath(List<String> pomFilePath) {
        this.pomFilePath = pomFilePath;
    }

    private List<String> pomFilePath = new ArrayList<>();

    public File[] find(String directoryName) {

        // .............list file
        File directory = new File(directoryName);

        // get all the files from a directory
        File[] fList = directory.listFiles();

        for (File file : fList) {
            if (file.isFile()) {
                if(POM.equals(file.getName())) {
                    pomFilePath.add(file.getAbsolutePath());
                    System.out.println("Found Pom: " + file.getAbsolutePath());
                }
            } else if (file.isDirectory()) {
                find(file.getAbsolutePath());
            }
        }
        return fList;
    }


}
