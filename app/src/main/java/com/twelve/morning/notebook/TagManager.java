package com.twelve.morning.notebook;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TagManager {

    public static String[] parse(String data) {

        Pattern p = Pattern.compile("#[^\\s]+");
        Matcher m = p.matcher(data);

        ArrayList<String> matches = new ArrayList<String>();
        while(m.find()) {
            String s = m.group();
            matches.add(s.substring(1));
        }

        Set<String> s = new LinkedHashSet<>(matches);
        String[] ret = new String[matches.size()];
        return s.toArray(ret);
    }
}

