/**
 * 
 */
package cn.heipiao.api.pay;

import java.io.File;
import java.io.FileInputStream;
import java.security.KeyStore;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.net.ssl.SSLContext;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.ssl.SSLContexts;
import org.apache.http.ssl.TrustStrategy;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author wzw
 * @date 2016年7月15日
 * @version 1.0
 */
@Component
public class HttpUtils {

	private static final Logger logger = LoggerFactory.getLogger(HttpUtils.class);

	// private static HttpClient httpclient =
	// HttpClientBuilder.create().build();

	// private static HttpClient httpsclient = null;

	/*
	 * public static HttpResponse post(String URL,String xmlStr) throws
	 * Exception {
	 * 
	 * 
	 * return ; }
	 */
	/**
	 * 执行http请求支持https
	 * 
	 * @param URL
	 * @param method
	 * @param body
	 * @param password
	 * @param certPath
	 * @return
	 * @throws Exception
	 */
	public String execute(String URL, String method, HttpEntity body, String password, String certPath) throws Exception {
		HttpResponse response = null;
		if (method.equalsIgnoreCase(RequestMethod.POST.toString())) {
			HttpPost post = new HttpPost(URL);
			post.setEntity(body);
			response = StringUtils.isNoneBlank(password, certPath) ? https(password, certPath).execute(post)
					: http().execute(post);
		} else if (method.equalsIgnoreCase(RequestMethod.GET.toString())) {
			HttpGet get = new HttpGet(URL);
			response = StringUtils.isNoneBlank(password, certPath) ? https(password, certPath).execute(get)
					: http().execute(get);
		}
		if (response != null && response.getStatusLine().getStatusCode() == 200) {
			Header[] headers = response.getHeaders("Content-Type");
			if( headers != null){
				for(Header h : headers){
					if(h.getValue() != null && h.getValue().contains("image/jpeg")){
						return Base64.encodeBase64String(EntityUtils.toByteArray(response.getEntity()));
					}
				}
			}
			return EntityUtils.toString(response.getEntity(), PayConfig.utf_8);
		}
		throw new Exception(response == null ? "response is null "
				: "status code:" + response.getStatusLine().getStatusCode() + " : " + (response.getEntity() == null
						? null : EntityUtils.toString(response.getEntity(), PayConfig.utf_8)));
	}

	/**
	 * 执行http请求，不支持https
	 * 
	 * @param URL
	 *            url
	 * @param method
	 *            请求方法
	 * @param body
	 *            body体
	 * @return
	 * @throws Exception
	 */
	public String execute(String URL, String method, HttpEntity body) throws Exception {
		return execute(URL, method, body, null, null);
	}

	private HttpClient http() throws Exception {
		 SSLContext sslcontext = SSLContexts.custom()
	                .loadTrustMaterial(new TrustStrategy(){
						@Override
						public boolean isTrusted(X509Certificate[] chain, String authType) throws CertificateException {
							return true;
						}
	                })
//				 	.loadTrustMaterial(new TrustSelfSignedStrategy())
	                .build();
	        // Allow TLSv1 protocol only
	        SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(
	                sslcontext,
	                new String[] { "TLSv1" },
	                null,
	                SSLConnectionSocketFactory.getDefaultHostnameVerifier());
		return HttpClients.custom()
				.setSSLSocketFactory(sslsf)
				.build();
	}

	private HttpClient https(String password, String certPath) throws Exception {
		logger.debug("password:{},certPath:{}", password, certPath);
		KeyStore keyStore = KeyStore.getInstance("PKCS12");
		FileInputStream instream = new FileInputStream(new File(certPath));
		try {
			keyStore.load(instream, password.toCharArray());
		} finally {
			instream.close();
		}
		// Trust own CA and all self-signed certs
        SSLContext sslcontext = SSLContexts.custom()
                .loadKeyMaterial(keyStore, password.toCharArray())
                .build();
        // Allow TLSv1 protocol only
        SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(
                sslcontext,
                new String[] { "TLSv1" },
                null,
                SSLConnectionSocketFactory.getDefaultHostnameVerifier());
		CloseableHttpClient httpsclient = HttpClients.custom().setSSLSocketFactory(sslsf).build();

		return httpsclient;
	}

	public static List<NameValuePair> getNameValuePair(Map<String, Object> map) {
		List<NameValuePair> m = new ArrayList<NameValuePair>();
		for (Entry<String, Object> ent : map.entrySet()) {
			m.add(new BasicNameValuePair(ent.getKey(), (String) ent.getValue()));
		}
		return m;
	}

}
