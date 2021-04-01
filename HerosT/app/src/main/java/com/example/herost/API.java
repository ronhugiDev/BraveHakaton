package com.example.herost;

import java.io.IOException;
import java.util.*;
import org.json.simple.parser.ParseException;


public class API {

    // convert Object to List
    public static List<?> convertObjectToList(Object obj) {
        List<?> list = new ArrayList<>();
        if (obj.getClass().isArray()) {
            list = Arrays.asList((Object[])obj);
        }
        else
            {
            if (obj instanceof Collection)
            {
                list = new ArrayList<>((Collection<?>)obj);
            }
            else
                {
                    System.out.println("Didn't found any match");
                }
        }
        return list;
    }
}
