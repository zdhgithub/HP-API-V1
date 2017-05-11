/**
 * 
 */
package cn.heipiao.api.utils;

import java.util.Arrays;

/**
 * @author wzw
 * @date 2016年7月6日
 * @version 1.0
 */
public class ExMapUtil {

	
	private static final double EARTHRADIUS = 6370996.81;
	
	public static double[] getAround(double lat, double lon, int radius) {

		Double latitude = lat;
		Double longitude = lon;

		Double degree = (24901 * 1609) / 360.0;
		double radiusMile = radius;

		Double dpmLat = 1 / degree;
		Double radiusLat = dpmLat * radiusMile;
		Double minLat = latitude - radiusLat;
		Double maxLat = latitude + radiusLat;

		Double mpdLng = degree * Math.cos(latitude * (Math.PI / 180));
		Double dpmLng = 1 / mpdLng;
		Double radiusLng = dpmLng * radiusMile;
		Double minLng = longitude - radiusLng;
		Double maxLng = longitude + radiusLng;
		return new double[] { minLng, minLat, maxLng, maxLat };
	}
	
	
	public static double getDistanceByLL(double lng1,double lat1,double lng2,double lat2){
		lng1 = getLoop(lng1, -180.0, 180.0);
		lat1 = getRange(lat1, -74.0, 74.0);
		lng2 = getLoop(lng2, -180.0, 180.0);
		lat2 = getRange(lat2, -74.0, 74.0);
		Double  cM, T, cP, cN;
		cM = toRadians(lng1);
		cP = toRadians(lat1);
		T = toRadians(lng2);
		cN = toRadians(lat2);
		return Math.round(EARTHRADIUS
				* Math.acos((Math.sin(cP) * Math.sin(cN) + Math
						.cos(cP)
						* Math.cos(cN) * Math.cos(T - cM))) * 100 ) /100.0;
	}
	
	
	private static double getLoop(Double cN,Double cM,Double T){
		while (cN > T) {
			cN -= T - cM;
		}
		while (cN < cM) {
			cN += T - cM;
		}
		return cN;
	}
	
	private static double getRange(Double cN,Double cM,Double T){
		if (cM != null) {
			cN = Math.max(cN, cM);
		}
		if (T != null) {
			cN = Math.min(cN, T);
		}
		return cN;
	}
	
	private static double toRadians(Double T){
		return Math.PI * T / 180;
	}
	
	
	public static void main(String[] args) {
		System.out.println(Arrays.toString(getAround(22.691342, 114.235761, 50000)));
		
		System.out.println(getDistanceByLL(114.235761, 22.691342, 114.03821, 22.556802));
	}
}
