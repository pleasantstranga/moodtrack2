package com.erac.pomutil.util;

import com.erac.pomutil.models.Change;
import com.erac.pomutil.models.ChangeType;
import com.erac.pomutil.models.ChangeVersionType;
import com.erac.pomutil.models.Dependency;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ChangeVersionTest {

    @BeforeEach
    void setUp() {
    }

    @Test
    void testChangeMajorSet() throws Exception {
        Dependency d = new Dependency();
        d.setChangeVersionType(ChangeVersionType.MAJOR);
        d.setCurrentVersion("4.3.2-SNAPSHOT");
        d.setChangeType(ChangeType.SET);
        //String change = ChangeVersion.change(d);
        d.setChangeAmount("5.3.2-SNAPSHOT");

    }
}