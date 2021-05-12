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

    public static boolean CheckAge(int age){
        String age1 = String.valueOf(age);
        String regex ="^[0-9]{2}$";
//                "\d{1,2}"
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(age1);
        if (matcher.find()){
            return true;
        }else {
            return false;
        }
    }
}
