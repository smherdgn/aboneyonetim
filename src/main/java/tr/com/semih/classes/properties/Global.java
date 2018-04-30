package tr.com.semih.classes.properties;

import tr.com.semih.classes.beans.Abone;

import java.util.*;

public class Global {

    private static List<Abone> aboneList;
    private static Map<String,String> erroredAbone;

    public static List<Abone> getAboneList() {
        if (aboneList == null) {
            aboneList = Collections.synchronizedList(new ArrayList<Abone>());
        }
        return aboneList;
    }

    public static Map<String,String> getErrorList() {
        if (erroredAbone == null) {
            erroredAbone = Collections.synchronizedMap(new HashMap<String,String>());
        }
        return erroredAbone;
    }
}
