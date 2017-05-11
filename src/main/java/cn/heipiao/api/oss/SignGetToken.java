/**
 * 
 */
package cn.heipiao.api.oss;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;
import java.util.SimpleTimeZone;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;

/**
 * 
 * oss url签名授权
 * 
 * @author wzw
 * @date 2016年7月25日
 * @version 1.0
 */
@Component
public class SignGetToken  extends OssConfig{

	private static final Logger logger  = LoggerFactory.getLogger(SignGetToken.class);
    
    /* Signature method. */
    private static final String ALGORITHM = "HmacSHA1";
    
	private static final Object LOCK = new Object();

	/* Prototype of the Mac instance. */
	private static Mac macInstance;

	public Map<String, String> getToken(String bucket,String dir) throws Exception {
//		String endpoint = "oss-cn-hangzhou.aliyuncs.com";
		String host = "https://" + bucket + "." + endpoint;
		String ja1 = null;
		long expireTime = 300;
		long expireEndTime = System.currentTimeMillis() + expireTime * 1000;
		Date expiration = new Date(expireEndTime);
		String signStr = String.format(
				"{\"expiration\":\"%s\",\"conditions\":[[\"content-length-range\",0,%s],[\"starts-with\",\"$key\",\"%s\"]]}",
				getIso8601DateFormat().format(expiration), 1048576000, dir);
		byte[] binaryData = signStr.getBytes("utf-8");
		String encodedPolicy = Base64.encodeBase64String(binaryData);
		String postSignature = Base64.encodeBase64String(sign(accessKeySecret.getBytes("UTF-8"), encodedPolicy.getBytes("UTF-8")));
		
		
		Map<String, String> respMap = new LinkedHashMap<String, String>();
		respMap.put("accessid", accessKeyId);
		respMap.put("policy", encodedPolicy);
		respMap.put("signature", postSignature);
		respMap.put("dir", dir);
		respMap.put("host", host);
		respMap.put("expire", String.valueOf(expireEndTime / 1000));
		ja1 = JSONObject.toJSONString(respMap);
		logger.info("sign url:{}",ja1.toString());

		return respMap;
	}

	private static DateFormat getIso8601DateFormat() {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.US);
		df.setTimeZone(new SimpleTimeZone(0, "GMT"));
		return df;
	}

	private static byte[] sign(byte[] key, byte[] data) {
		try {
			// Because Mac.getInstance(String) calls a synchronized method, it
			// could block on
			// invoked concurrently, so use prototype pattern to improve perf.
			if (macInstance == null) {
				synchronized (LOCK) {
//					if (macInstance == null) {
						macInstance = Mac.getInstance(ALGORITHM);
//					}
				}
			}

			Mac mac = null;
			try {
				mac = (Mac) macInstance.clone();
			} catch (CloneNotSupportedException e) {
				// If it is not clonable, create a new one.
				mac = Mac.getInstance(ALGORITHM);
			}
			mac.init(new SecretKeySpec(key, ALGORITHM));
			return mac.doFinal(data);
		} catch (NoSuchAlgorithmException ex) {
			throw new RuntimeException("Unsupported algorithm: " + ALGORITHM, ex);
		} catch (InvalidKeyException ex) {
			throw new RuntimeException("Invalid key: " + Arrays.toString(key) , ex);
		}
	}

}
