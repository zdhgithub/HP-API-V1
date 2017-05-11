package cn.heipiao.api.utils;

import java.security.AlgorithmParameters;
import java.security.Key;
import java.security.Security;
import java.util.Arrays;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

/**
 * @author wzw
 * @date 2017年2月16日
 */
public class ExAES128Utils {
	
	static{
		Security.addProvider(new BouncyCastleProvider());
	}
	
	 //算法名  
    public static final String KEY_ALGORITHM = "AES";  
    //加解密算法/模式/填充方式  
    public static final String CIPHER_ALGORITHM = "AES/CBC/PKCS7Padding";  
  
    //生成密钥  
    public static byte[] generateKey() throws Exception{  
        KeyGenerator keyGenerator = KeyGenerator.getInstance(KEY_ALGORITHM);  
        keyGenerator.init(128);  
        SecretKey key = keyGenerator.generateKey();  
        return key.getEncoded();  
    }  
      
    //生成iv  
    public static AlgorithmParameters generateIV(String iv) throws Exception{  
        AlgorithmParameters params = AlgorithmParameters.getInstance(KEY_ALGORITHM);  
        params.init(new IvParameterSpec(Base64.decodeBase64(iv)));  
        return params;  
    }  
      
    //转化成JAVA的密钥格式  
    public static Key convertToKey(byte[] keyBytes) throws Exception{  
  	  int base = 16;
  	  if (keyBytes.length % base != 0) {
  	   int groups = keyBytes.length / base + (keyBytes.length % base != 0 ? 1 : 0);
  	   byte[] temp = new byte[groups * base];
  	   Arrays.fill(temp, (byte) 0);
  	   System.arraycopy(keyBytes, 0, temp, 0, keyBytes.length);
  	   keyBytes = temp;
  	  }
        Key secretKey = new SecretKeySpec(keyBytes,KEY_ALGORITHM);  
        return secretKey;  
    }  
      
    //加密  
    public static byte[] encrypt(byte[] data,byte[] keyBytes,AlgorithmParameters iv) throws Exception {  
        //转化为密钥  
        Key key = convertToKey(keyBytes);  
        Security.addProvider(new BouncyCastleProvider());  
        Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);  
        //设置为加密模式  
        cipher.init(Cipher.ENCRYPT_MODE, key,iv);  
        return cipher.doFinal(data);  
    }  
      
    //解密  
    public static String decrypt(String encryptedData,String keyBytes,AlgorithmParameters iv) throws Exception{  
        Key key = convertToKey(Base64.decodeBase64(keyBytes));  
        Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);  
        //设置为解密模式  
        cipher.init(Cipher.DECRYPT_MODE, key,iv);  
        return new String(cipher.doFinal(Base64.decodeBase64(encryptedData)),"UTF-8");  
    }
    
    public static void main(String[] args) throws Exception {
//    	String encryptedData = "2my+Jxd6MWFrkRAj9jdv1IY0DINgUzV+GzTPPjh9DLzGf2XKTnrSFqNvypnjk+MWhYj5G8TrbRHoBjNdy4/fWniPbNbCf3zELfEoBwrbVwJL2AH3S2HTX4yGnjYDt3IaGnWAKyQqlPMH8r5zBvnV2lO9edEUy0uI28E3MLvcVNBeUsxWVaY5qkBnTIVG3CcX8R95f0ptm+MzDwd00gM8qS9mlOBxBXfQGBcl0gDQMMXVGCerCQ7bpbVioXMuzWo2dyS3a5SbkUVIgqcTID6SplQeemNBchQM/bLmnSPyiGrwwhvBrBusIVMrvFug99Bha4xgtAXPX+eaf/R+nTbklIE9tP2r08IWnkk2AZrOGraroq3c0aeMpvH3at9N4hitMwqHK22jtiTKKpwOdfDb/pD/eVUHtwhj4a0ZVf1E+1Q1teGEaDHzT2gB6NR0bRcZwqOKVgZF2R7xliR6oL+iFkvlJn9pJf/RqYVMlf+T5P5b5Gd/fR17L5OHKFSRlXQkIftMv0i0nD1Cd9KGJ1AB/Q==";
//   	 String keyBytes = "rptFThKxA28qxJQA/VR9GQ==";
//   	 String iv = "Oo5zbMxl/mLYiWdYHd/jKA==";
   	 String encryptedData = "gg27IGhQo4VulmmkHz/tt76z0lbFt31OYwiDNRNjTei8rWOf2LhFapC/4hZuWpWp9dyEiz5/LLChtK1lM5/lc2WrwwwAUIbpwNxvx+BiwczJo1wBT1yO7x3AHXNsjNi4LZGCTeL39fCksCyetmL5eYA/45wUWhYf9W7lKivyPQkINWTuaU4z/poN9/xfznYbLwi241/NAL7vjkHXuqFLyrMF5GrpnJApn1OUGd46cD9iK5yumS5xEQc/+3vk4JMdm2tUfFWb12udrWHuBatSXUL8pn7RDpu3TqwpynvtAmzrFGiBXOhX7iyKBTXlFMQxLRLtqoYRVeFIUAvhmO36Uew9CQyAzsj3SZ7vmwCd4EEqMKnNa9roxBmBGkcTFFynjthxyd8s9N0QUI2MN0FPKKhI6r4yhHKG2ZxAsMYIco1sYRCRH0qwFvNk6pD7YEwRwjhbqqMppKOf/IyNVJBrYiKGMnwFJAtcJUoiq2sCkwrt5t5SMMmgzt63dHmXPDjCB8d5pQaBAmk64E14L7PXcw==";
   	 String keyBytes = "xGTcD3QCG0/7EvRCLtVSCg==";
   	 String iv = "OoyIgB9wpl7se406GOuqJQ==";
   	String data = decrypt(encryptedData, keyBytes, generateIV(iv));
	
	System.out.println(data);
	}
}
