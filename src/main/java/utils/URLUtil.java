package utils;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;

/**
 * URL工具
 *
 */
public class URLUtil {

	/**
	 * 对url进行编码
	 */
	public static String encodeURL(String url) {
		try {
			return URLEncoder.encode(url, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * 对url进行解码
	 * @param url
	 * @return
	 */
	public static String decodeURL(String url){
		try {
			return URLDecoder.decode(url, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 判断URL地址是否存在
	 * @param url
	 * @return
	 */
	public static boolean isURLExist(String url) {
		try {
			URL u = new URL(url);
			HttpURLConnection urlconn = (HttpURLConnection) u.openConnection();
			int state = urlconn.getResponseCode();
			if (state == 200) {
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			return false;
		}
	}
	
	/**
	 * 将请求参数还原为key=value的形式
	 * @param params
	 * @return
	 */
	public static String getParamString(Map<?, ?> params) {
		StringBuilder queryString = new StringBuilder(256);
		Iterator<?> it = params.keySet().iterator();
		int count = 0;
		String key;
		String[] param;
		while (it.hasNext()) {
			key = (String) it.next();
			param = (String[]) params.get(key);
			for (int i = 0; i < param.length; i++) {
				if (count == 0) {
					count++;
				} else {
					queryString.append("&");
				}
				queryString.append(key);
				queryString.append("=");
				try {
					queryString.append(URLEncoder.encode((String) param[i], "UTF-8"));
				} catch (UnsupportedEncodingException e) {
				}
			}
		}
		return queryString.toString();
	}
	
	/**
	 * 将参数转换成字符串，并拼接到url后
	 * @param url,params
	 * @return
	 */
	public static String generalURLParam(String url,String params) {
		StringBuilder resultURL = new StringBuilder(url);
		if(EmptyUtil.isNotEmpty(params)){
			if(url.indexOf("?")>-1){
				resultURL.append("&").append(params);
			}else{
				resultURL.append("?").append(params);
			}
		}
		return resultURL.toString();
	}

	/**
	 * 获得请求的路径及参数
	 * @param request
	 * @return
	 */
	public static String getRequestURL(HttpServletRequest request) {
		StringBuilder originalURL = new StringBuilder(request.getServletPath());
		Map<?,?> parameters = request.getParameterMap();
		if (parameters != null && parameters.size() > 0) {
			originalURL.append("?");
			originalURL.append(getParamString(parameters));
		}
		return originalURL.toString();
	}

	/**
	 * 抓取网页内容,自动识别编码
	 * @param urlString
	 * @return
	 */
	public static String url2Str(String urlString) {
		try {
			StringBuilder html = new StringBuilder();
			URL url = new URL(urlString);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			URLConnection c = url.openConnection();
			c.connect();
			String contentType = c.getContentType();
			String characterEncoding = null;
			int index = contentType.indexOf("charset=");
			if(index == -1){
				characterEncoding = "UTF-8";
			}else{
				characterEncoding = contentType.substring(index + 8, contentType.length());
			}
	        InputStreamReader isr = new InputStreamReader(conn.getInputStream(), characterEncoding);
	        BufferedReader br = new BufferedReader(isr);
	        String temp;
	        while ((temp = br.readLine()) != null) {
	            html.append(temp).append("\n");
	        }
	        br.close();
	        isr.close();
	        return html.toString();
	     } catch (Exception e) {
	        e.printStackTrace();
	        return null;
	     }
	 }
	
	/**
	 * 保存图片到本地 
	 * @param picUrl
	 * 		图片URL地址
	 * @param newFileName
	 * 		保存文件名
	 * @param dir
	 * 		保存目录
	 * @return
	 */
	public static void savePic(String picUrl, String newFileName, String dir){
		try{
			URL url = new URL(picUrl);
			InputStream in = url.openStream();   
			BufferedImage srcImage =  ImageIO.read(url.openStream());
	 
			File img = new File(dir + newFileName);
			
			ImageIO.write(srcImage, "jpg", img);
	        in.close();
    	} catch (IOException e) {   
    		e.printStackTrace();   
    	}
	}
}
