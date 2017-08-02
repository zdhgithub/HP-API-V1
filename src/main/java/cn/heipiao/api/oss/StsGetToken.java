/**
 * 
 */
package cn.heipiao.api.oss;

import java.text.SimpleDateFormat;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.http.ProtocolType;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.aliyuncs.sts.model.v20150401.AssumeRoleRequest;
import com.aliyuncs.sts.model.v20150401.AssumeRoleResponse;
import com.aliyuncs.sts.model.v20150401.AssumeRoleResponse.Credentials;

import cn.heipiao.api.pojo.Token;

/**
 * oss sts临时授权
 * 
 * @author wzw
 * @date 2016年7月11日
 * @version 1.0
 */
@Component
public class StsGetToken extends OssConfig{
	
	private static final Logger logger  = LoggerFactory.getLogger(StsGetToken.class);
	
//	public static final String fs_dynamic = "fs-dynamic";
//	public static final String fs_profile = "fs-profile";
//	public static final String user_portrait = "user-portrait";
	
	public static final String oss_sts = "oss-sts";
	

	// 目前只有"cn-hangzhou"这个region可用, 不要使用填写其他region的值
    public static final String REGION_CN_HANGZHOU = "cn-hangzhou";
    // 当前 STS API 版本
    public static final String STS_API_VERSION = "2015-04-01";
    
    static ProtocolType protocolType = ProtocolType.HTTPS;
    
    public Token  getStsToken(String roleSessionName) throws Exception{
    	logger.debug("accessKeyId:{},accessKeySecret:{},roleArn:{},roleSessionName:{}"
    			,accessKeyId,accessKeySecret,roleArn,roleSessionName);
    	AssumeRoleResponse response = assumeRole(accessKeyId, accessKeySecret,roleArn, roleSessionName, null, protocolType,null);
    	Credentials c = response.getCredentials();
    	logger.info("expiration : " + c.getExpiration());
    	logger.info("accessKeyId : " + c.getAccessKeyId());
    	logger.info("accessKeySecret : " + c.getAccessKeySecret());
    	logger.info("securityToken : " + c.getSecurityToken());
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
    	Token t = new Token();
    	t.setAccessKeyId(c.getAccessKeyId());
    	t.setAccessKeySecret(c.getAccessKeySecret());
    	t.setExpiration(sdf.parse(c.getExpiration()).getTime() / 1000 + 8 * 60 * 60);
    	t.setSecurityToken(c.getSecurityToken());
    	return t;
    }

	/**
	 * @param accessKeyId
	 * @param accessKeySecret
	 * @param roleArn
	 * @param roleSessionName
	 * @param object
	 * @param protocolType
	 * @param durationSeconds 
	 * @return
	 * @throws Exception 
	 */
	private static AssumeRoleResponse assumeRole(String accessKeyId, String accessKeySecret, String roleArn,
			String roleSessionName, String policy, ProtocolType protocolType,Long durationSeconds) throws Exception {
		try {
            // 创建一个 Aliyun Acs Client, 用于发起 OpenAPI 请求
            IClientProfile profile = DefaultProfile.getProfile(REGION_CN_HANGZHOU, accessKeyId, accessKeySecret);
            DefaultAcsClient client = new DefaultAcsClient(profile);

            // 创建一个 AssumeRoleRequest 并设置请求参数
            final AssumeRoleRequest request = new AssumeRoleRequest();
            request.setVersion(STS_API_VERSION);
            request.setMethod(MethodType.POST);
            request.setProtocol(protocolType);
            request.setRoleArn(roleArn);
            request.setRoleSessionName(roleSessionName);
            request.setPolicy(policy);
            //设置过期时间900~3600秒之间,
            //目前没有设置，默认是3600
            if(durationSeconds != null)
            	request.setDurationSeconds(durationSeconds);

            // 发起请求，并得到response
            final AssumeRoleResponse response = client.getAcsResponse(request);

            return response;
        } catch (ClientException e) {
            throw e;
        }
	}
	
	
	public static void main(String[] args) throws Exception {
//		StsGetToken s = new StsGetToken();
//		s.getStsToken("pc");
//		 SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
//		 Date d = sdf.parse("2016-07-11T11:18:46Z");
//		 long l1 = sdf.parse("2016-07-11T11:18:46Z").getTime() / 1000  + 8 * 60 * 60;
//
//		 System.out.println(new Date(l1 * 1000 ));
	}
	
}
