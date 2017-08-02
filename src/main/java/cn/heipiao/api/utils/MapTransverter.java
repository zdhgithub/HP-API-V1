package cn.heipiao.api.utils;

/**
 * @说明 地图经纬度转换工具
 * 		 主要:高德经纬度转换百度经纬度 
 * @author chenwenye
 * @version heipiao1.0 2016年8月19日
 */
@Deprecated
public class MapTransverter {
	
	/**
	 * 换算PI
	 */
	private static final double x_pi = 3.14159265358979324 * 3000.0 / 180.0;  
	
	/**
	 * @说明
	 * 		高德转百度
	 * @param gg_lat 维度
	 * @param gg_lon 经度
	 */
	public LatAndLon bd_encrypt(double gg_lat, double gg_lon)  
	{  
	    double z = Math.sqrt( gg_lon * gg_lon + gg_lat * gg_lat ) + 0.00002 * Math.sin( gg_lat * x_pi);  
	    double theta = Math.atan2( gg_lat , gg_lon ) + 0.000003 * Math.cos( gg_lon * x_pi);
	    return new LatAndLon(z * Math.sin(theta) + 0.006, z * Math.cos(theta) + 0.0065);
	}
	
	/**
	 * @说明 经纬度 
	 * @author chenwenye
	 * @version heipiao1.0 2016年8月19日
	 */
	public class LatAndLon{
		public double lat;
		public double lon;
		public LatAndLon() {
		}
		public LatAndLon(double lat , double lon) {
			this.lat = lat;
			this.lon = lon;	
		}
	}

}
