package com.erac.pomutil.util;

import com.erac.pomutil.models.Change;
import com.erac.pomutil.models.ChangeType;
import com.erac.pomutil.models.ChangeVersionType;
import com.erac.pomutil.models.Dependency;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class VersionValidatorTest {

    @BeforeEach
    void setUp() {
    }

    @Test
    void testValidAll3() {
        VersionValidator test = new VersionValidator();
        assertTrue(test.validate("4.1.1"));
    }
    @Test
    void testValid2() {
        VersionValidator test = new VersionValidator();
        assertTrue(test.validate("4.1"));
    }
    @Test
    void testValid1() {
        VersionValidator test = new VersionValidator();
        assertTrue(test.validate("4"));
    }
    @Test
    void testValidAll3SNAPSHOT() {
        VersionValidator test = new VersionValidator();
        assertTrue(test.validate("4.1.1-SNAPSHOT"));
    }
    @Test
    void testValid2SNAPSHOT() {
        VersionValidator test = new VersionValidator();
        assertTrue(test.validate("4.1-SNAPSHOT"));
    }
    @Test
    void testValid1SNAPSHOT() {
        VersionValidator test = new VersionValidator();
        assertTrue(test.validate("4-SNAPSHOT"));
    }
}