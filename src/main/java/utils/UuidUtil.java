package utils;

import java.util.Random;
import java.util.UUID;

public class UuidUtil
{
    public static String getUUID()
    {
        String s = UUID.randomUUID().toString();

        String r = String.valueOf(new Random().nextInt(1000));
        //补零
        r = r.length() != 3 ? r.length() != 2 ? "00" + r : "0" + r : r;

        return s.substring(0, 8) + s.substring(9, 13) + s.substring(14, 18) + s.substring(19, 23) + s.substring(24) + r;
    }

    public static String getUUID32len() {
        return UUID.randomUUID().toString().trim().replaceAll("-", "");
    }

    public static void main(String[] args){
        System.out.println(getUUID32len());
    }
}
