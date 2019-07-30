package security;


import java.io.UnsupportedEncodingException;
import java.util.Base64;

public class Base64Coder {

    public static String decodeBase64(String str) throws UnsupportedEncodingException {
        return new String(Base64.getDecoder().decode(str),"UTF-8");
    }

    public static String encodeBase64(byte[] key){
        return Base64.getEncoder().encodeToString(key);
    }
}
