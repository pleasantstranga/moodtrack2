package com.erac.pomutil.util;

import com.erac.pomutil.models.Change;
import com.erac.pomutil.models.ChangeType;
import com.erac.pomutil.models.ChangeVersionType;
import com.erac.pomutil.models.Dependency;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ChangeRetrieverTest {

    @BeforeEach
    void setUp() {
    }

    @Test
    void testNullDependency() {
        Exception exception = assertThrows(
                Exception.class,
                () -> { ChangeRetriever.retrieve(null); }
        );

        assertEquals("Depency is null", exception.getMessage());
    }
    @Test
    void testNullChangeType() {
        Dependency d = new Dependency();
        Exception exception = assertThrows(
                Exception.class,
                () -> { ChangeRetriever.retrieve(d); }
        );

        assertEquals("Change type cannot be null", exception.getMessage());
    }

    @Test
    void testNullChangeVersionType() {
        Dependency d = new Dependency();
        d.setChangeType(ChangeType.SET);

        Exception exception = assertThrows(
                Exception.class,
                () -> { ChangeRetriever.retrieve(d); }
        );

        assertEquals("Change version type cannot be null", exception.getMessage());
    }

    @Test
    void testInvalidOperation() {
        Dependency d = new Dependency();
        d.setChangeType(ChangeType.ADD);
        d.setChangeVersionType(ChangeVersionType.MAJOR);
        Exception exception = assertThrows(
                Exception.class,
                () -> { ChangeRetriever.retrieve(d); }
        );

        assertEquals("You are trying to add or subtract from a currently empty version. Try using set operation", exception.getMessage());
    }

    @Test
    void testInvalidOperation2() {
        Dependency d = new Dependency();
        d.setCurrentVersion("");
        d.setChangeType(ChangeType.ADD);
        d.setChangeVersionType(ChangeVersionType.MAJOR);
        Exception exception = assertThrows(
                Exception.class,
                () -> { ChangeRetriever.retrieve(d); }
        );

        assertEquals("You are trying to add or subtract from a currently empty version. Try using set operation", exception.getMessage());
    }

    @Test
    void testGetMajorSnapshot() throws Exception {
        Dependency d = new Dependency();
        d.setChangeVersionType(ChangeVersionType.MAJOR);
        d.setCurrentVersion("4.3.2-SNAPSHOT");
        d.setChangeType(ChangeType.SET);
        Change change = ChangeRetriever.retrieve(d);
        assertEquals(0,change.getStartIndex());
        assertEquals(1,change.getEndIndex());
        assertEquals(4, change.getCurrentValue());

    }

    //@Test
    void testGetMajorSnapshot2() throws Exception {
        Dependency d = new Dependency();
        d.setChangeVersionType(ChangeVersionType.MAJOR);
        d.setCurrentVersion("4SNAPSHOT");
        d.setChangeType(ChangeType.SET);
        Change change = ChangeRetriever.retrieve(d);
        assertEquals(0,change.getStartIndex());
        assertEquals(1,change.getEndIndex());
        assertEquals(4, change.getCurrentValue());

    }

    @Test
    void testGetMinor() throws Exception {
        Dependency d = new Dependency();
        d.setChangeVersionType(ChangeVersionType.MINOR);
        d.setCurrentVersion("4.3.2-SNAPSHOT");
        d.setChangeType(ChangeType.SET);
        Change change = ChangeRetriever.retrieve(d);
        assertEquals(2,change.getStartIndex());
        assertEquals(3,change.getEndIndex());
        assertEquals(3, change.getCurrentValue());

    }
    @Test
    void testGetMinor2() throws Exception {
        Dependency d = new Dependency();
        d.setChangeVersionType(ChangeVersionType.MINOR);
        d.setCurrentVersion("422.323.223-SNAPSHOT");
        d.setChangeType(ChangeType.SET);
        Change change = ChangeRetriever.retrieve(d);
        assertEquals(4,change.getStartIndex());
        assertEquals(7,change.getEndIndex());
        assertEquals(323, change.getCurrentValue());

    }
    @Test
    void testGetPatch() throws Exception {
        Dependency d = new Dependency();
        d.setChangeVersionType(ChangeVersionType.PATCH);
        d.setCurrentVersion("422.323.2-SNAPSHOT");
        d.setChangeType(ChangeType.SET);
        Change change = ChangeRetriever.retrieve(d);
        assertEquals(8,change.getStartIndex());
        assertEquals(9,change.getEndIndex());
        assertEquals(2, change.getCurrentValue());

    }
    @Test
    void testGetPatch2() throws Exception {
        Dependency d = new Dependency();
        d.setChangeVersionType(ChangeVersionType.PATCH);
        d.setCurrentVersion("422.323.223-SNAPSHOT");
        d.setChangeType(ChangeType.SET);
        Change change = ChangeRetriever.retrieve(d);
        assertEquals(8,change.getStartIndex());
        assertEquals(11,change.getEndIndex());
        assertEquals(223, change.getCurrentValue());

    }

}