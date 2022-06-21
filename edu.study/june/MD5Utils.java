package june;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Utils {
    private static int SHORT_URL_CHAR_SIZE=7;
//    public static String convert(String longURL) {
//        try {
//            // Create MD5 Hash
//            MessageDigest digest = MessageDigest.getInstance("MD5");
//            digest.update(longURL.getBytes());
//            byte messageDigest[] = digest.digest();
//            // Create Hex String
//            StringBuilder hexString = new StringBuilder();
//            for (byte b : messageDigest) {
//                hexString.append(Integer.toHexString(0xFF & b));
//            }
//            return hexString.toString();
//        } catch (NoSuchAlgorithmException e) {
//            throw new RuntimeException(e);
//        }
//    }
//    public static String generateRandomShortUrl(String longURL) {
//        String hash=MD5Utils.convert(longURL);
//        int numberOfCharsInHash=hash.length();
//        int counter=0;
//        while(counter < numberOfCharsInHash-SHORT_URL_CHAR_SIZE){
//            //从前往后移动截取
//            if(!DB.exists(hash.substring(counter, counter+SHORT_URL_CHAR_SIZE))){
//                return hash.substring(counter, counter+SHORT_URL_CHAR_SIZE);
//            }
//            counter++;
//        }
//    }
}
