package utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class SHAHasher {

    /**
     * Method which convert string in hash
     * @param text
     * @return hashed string
     */
    public static String hashText(String text) {
        MessageDigest md;
        try {
            md = MessageDigest.getInstance("SHA-256");
            md.update(text.getBytes());
            return hexToString(md.digest());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Converting hex to string
     * @param bytes
     * @return string
     */
    private static String hexToString(byte[] bytes) {
        String result = "";
        for (byte aByte : bytes) {
            result +=
                    Integer.toString((aByte & 0xff) + 0x100, 16).substring(1);
        }
        return result;
    }
}
