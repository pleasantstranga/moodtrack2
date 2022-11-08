package com.erac.pomutil.util;

import org.springframework.util.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class VersionValidator {
    private static String regexp = "[0-9]*\\.?[0-9\\.]*[\\-SNAPSHOT]*";

    public static boolean validate(String version) {
        int occurrenceOfDecimals = StringUtils.countOccurrencesOf(version, ".");
        Pattern p = Pattern.compile(regexp);//. represents single character
        Matcher m = p.matcher(version);
        return m.matches() && occurrenceOfDecimals <=2;
    }
}
