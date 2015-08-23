/**
 * 
 */
package my.zulsoft.common.game;

import java.util.HashMap;

/**
 * @author Faizul
 *
 */
@SuppressWarnings("unchecked")
public class StaticValueMap {

	
	@SuppressWarnings("rawtypes")
	private static HashMap map = new HashMap();
	
	public static String ScreenSize ="ScreenSize";
	public static String ContentPath ="ContentPath";
	public static String ScreenGamma ="ScreenGamma";
	public static String ScreenBrightness ="ScreenBrightness";
	public static String ScreenContrast ="ScreenContrast";
	public static String ScreenVSync ="ScreenVSync";
	public static String ScreenFullsceen ="ScreenFullsceen";
	public static String ScreenWidth ="ScreenWidth";
	public static String ScreenHeight ="ScreenHeight";
	public static String ScreenBitPerPixel ="ScreenBitPerPixel";
	
	
	static {
		map.put(ScreenSize, "800,600");
		map.put(ContentPath, "res");
		map.put(ScreenGamma, "1.0");
		map.put(ScreenBrightness, "0.5");
		map.put(ScreenContrast, "1.0");
		map.put(ScreenVSync, "false");
		map.put(ScreenFullsceen, "true");
		map.put(ScreenWidth, "800");
		map.put(ScreenHeight, "600");
		map.put(ScreenBitPerPixel, "32");
	}
	/**
	 * 
	 */
	public StaticValueMap() {
		// TODO Auto-generated constructor stub
	}

	@SuppressWarnings("rawtypes")
	public static HashMap getValueMap() {
		return map;
	}
}
