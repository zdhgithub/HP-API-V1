package cn.heipiao.api.utils;

import org.apache.commons.lang3.StringUtils;
/**
 * 视频图片转html
 * @author zf
 *
 */
public class ExTransferHtmlUtils {
	private static final String MP4 = ".mp4";
	
	// FIXME maven构建不同环境
	
	//测试
//	public static final String OSSURI = "dyna.res.heipiaola.cn/";
	//生产
	public static final String OSSURI = "dyna.res.heipiaola.com/";

	/**
	 * 转化图片或视频
	 * 
	 * @param picture
	 * @return
	 */
	public static String convertPictureToHtml(String picture) {
		if (StringUtils.isEmpty(picture)) {
			return null;
		}
		StringBuilder sb = new StringBuilder();
		if (picture.endsWith(MP4)) {
			// 视频 FIXME 这里很有可能会无法通过苹果审核，下个版本必须改掉，OSS资源先改成阿里公网地址
			sb.append("<video  style=\"width:100%;\" controls><source src=\"");
			sb.append("http://");
			sb.append(OSSURI);
			sb.append(picture);
			sb.append("\" type=\"video/mp4\"Your browser does not support video</video>");
		} else {
			// 图片 FIXME 这里很有可能会无法通过苹果审核，下个版本必须改掉，OSS资源先改成阿里公网地址
			sb.append("<p style=\"text-align:center\">");
			String[] sl = picture.split(",");
			for (String s : sl) {
				sb.append("<img src=\"http://");
				sb.append(OSSURI);
				sb.append(s);
				sb.append("\" style=\"width:97%;\" />");
			}
			sb.append("</p>");
		}
		return sb.toString();
	}
}
