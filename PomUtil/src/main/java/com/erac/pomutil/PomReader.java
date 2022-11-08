package com.erac.pomutil;

import com.erac.pomutil.util.ChangeVersion;
import org.apache.maven.model.Dependency;
import org.apache.maven.model.Model;
import org.apache.maven.model.io.xpp3.MavenXpp3Reader;
import org.apache.maven.model.io.xpp3.MavenXpp3Writer;
import org.codehaus.plexus.util.xml.pull.XmlPullParserException;


import java.io.*;
import java.util.List;

public class PomReader {

    public static void main(String[] args) throws IOException {

        JsonReader reader = new JsonReader();
        com.erac.pomutil.models.Dependency[] dependencyChanges = reader.getJSONObjects();

        FileList fl = new FileList();
        fl.find("/Users/aaronbernstein/git/repository2/PomUtil");
        fl.getPomFilePath().forEach(pom-> {
            try {
                readPom(pom, dependencyChanges);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

    }

    public static void readPom(String path, com.erac.pomutil.models.Dependency[] dependencyChanges) throws Exception {
        MavenXpp3Reader mavenreader = new MavenXpp3Reader();
        File pomfile = new File(path);
        Model model = mavenreader.read(new FileReader(pomfile));

        List<Dependency> deps = model.getDependencies();
        for(com.erac.pomutil.models.Dependency change : dependencyChanges) {
            for (Dependency d: deps) {
                if(change.getArtifactId().equals(d.getArtifactId())) {
                    if(change.getGroupId().equals(d.getGroupId())) {
                        change.setCurrentVersion(d.getVersion());
                        d = ChangeVersion.change(d,change);

                    }
                }
            }
        }
        writePom(path,model);
    }
    public static void writePom(String path, Model model) throws IOException {
        String writePath = path.concat("2");
        System.out.println("Writing POM: " + writePath);

        MavenXpp3Writer mavenWriter = new MavenXpp3Writer();
        try (FileOutputStream fos = new FileOutputStream(writePath)) {
            mavenWriter.write(fos, model);

        } catch (IOException e) {
            e.printStackTrace();
            throw e;
        }

    }

}
