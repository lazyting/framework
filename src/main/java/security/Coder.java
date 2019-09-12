package security;


import org.apache.log4j.Logger;
import sun.security.provider.MD5;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class Coder {
    private static Logger logger = Logger.getLogger(Coder.class);
    private static final String KEY_MD5 = "MD5";

    public static String decodeBase64(String str) throws UnsupportedEncodingException {
        return new String(Base64.getDecoder().decode(str), "UTF-8");
    }

    public static String encodeBase64(byte[] key) {
        return Base64.getEncoder().encodeToString(key);
    }

    private static final char hexDigits[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
            'A', 'B', 'C', 'D', 'E', 'F'};

    /**
     * md5加密
     *
     * @param data
     * @return
     */
    public static byte[] encryptMD5(byte[] data) {
        MessageDigest md5;
        byte[] resultBytes = null;
        try {
            md5 = MessageDigest.getInstance(KEY_MD5);
            md5.update(data);
            resultBytes = md5.digest();
        } catch (NoSuchAlgorithmException e) {
            logger.error(e.getMessage(), e);
        }
        return resultBytes;

    }

    /**
     * 得到参数加密后的MD5值
     *
     * @param inStr
     * @return 32byte MD5 Value
     */
    public static String encryptMD5(String inStr) {
        byte[] inStrBytes = inStr.getBytes();
        try {
            MessageDigest MD = MessageDigest.getInstance("MD5");
            MD.update(inStrBytes);
            byte[] mdByte = MD.digest();
            char[] str = new char[mdByte.length * 2];
            int k = 0;
            for (int i = 0; i < mdByte.length; i++) {
                byte temp = mdByte[i];
                str[k++] = hexDigits[temp >>> 4 & 0xf];
                str[k++] = hexDigits[temp & 0xf];
            }
            return new String(str);
        } catch (NoSuchAlgorithmException e) {
            // TODO Auto-generated catch block
            logger.error(e.getMessage(), e);
        }
        return null;
    }
}
