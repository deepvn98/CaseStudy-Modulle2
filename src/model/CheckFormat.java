package model;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CheckFormat {
    public static boolean checkName(String name){
        String regex ="^([ \\u00c0-\\u01ffa-zA-Z'\\-])+$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(name);
        if (matcher.find()){
            return true;
        }else {
            return false;
        }
    }
//    public static boolean CheckAge(int age){
//        int regex = ^[0-9]{2}$;
//    }
}
