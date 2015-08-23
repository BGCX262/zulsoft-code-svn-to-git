package my.zulsoft.common.game;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;


import org.lwjgl.util.Rectangle;

public class Config {
	
	private static Config c = new Config();
	@SuppressWarnings("rawtypes")
	private HashMap listMap;
	
	protected void parseCmdLineOptions(String[] args) {
		
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	protected HashMap loadConfigFile()
	{
		HashMap tempMap = null;
		if(listMap !=null) return listMap;
		try {
			BufferedReader in = new BufferedReader(new FileReader("System.config"));
			
			tempMap = new HashMap();
			String line= in.readLine();
			while (line != null) {
				String[] keyVal = line.split("=");
				//check if the key is supported
				boolean keysupported = false;
				for(Object key : StaticValueMap.getValueMap().keySet())
				{
					if(key.equals(keyVal[0])) {
						keysupported = true;
						break;
					}
				}
				
				if(keysupported) {
					tempMap.put(keyVal[0], keyVal[1]);
				}
				
				line= in.readLine();
			}
			
			in.close();
		} catch (Exception e) {
		
			e.printStackTrace();
		}
		
		return tempMap;
			
	}
	
	@SuppressWarnings("rawtypes")
	protected void populateConfigFile(HashMap map) 
	{
		try {
			BufferedWriter bw = new BufferedWriter(new FileWriter("System.config"));
			StringBuffer sb =new StringBuffer();
			for(Object key : map.keySet())
			{
				sb.append(key);
				sb.append("=");
				sb.append(map.get(key));
				bw.write(sb.toString());
				bw.newLine();
				sb = new StringBuffer();
			}
			
			bw.flush();
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked", "unused" })
	protected void populateConfigMap() 
	{
		//load config from config file
		HashMap mapFrmFile = loadConfigFile();
		if(listMap == null && mapFrmFile != null) 
			listMap = mapFrmFile;
		else {
			if(listMap.size() >= mapFrmFile.size()) return;
			if(mapFrmFile == null) {
				//set default from static value and save config file 
				// and then call populateConfigMap() again
				populateConfigFile(StaticValueMap.getValueMap());
				populateConfigMap(); //call it self
			} else {
			
				for(Object key : mapFrmFile.keySet() ) {
					if(!listMap.containsKey(key)) {
					listMap.put(key, mapFrmFile.get(key));
					}
				}
			}
		}
	}
	
	public static Config loadConfig(String[] args) {
		if(args != null) {
			c.parseCmdLineOptions(args);
			c.populateConfigMap();
		}
		return c;
	}
	
	public static Config getConfig() {
		c.populateConfigMap();
		return c;
	}
	
	public Rectangle getConfigScreenSize()
	{
		Rectangle r;
		String s = (String) listMap.get(StaticValueMap.ScreenSize) ;
		String[] ss = s.split(",");
		
		r = new Rectangle(0,0,Integer.parseInt(ss[0]),Integer.parseInt(ss[1]));
		return r; 
	}
	
	public String getConfigContentPath()
	{
		return (String) listMap.get(StaticValueMap.ContentPath);
	}
	
	public float getConfigScreenGamma() {
		return (Float) listMap.get(StaticValueMap.ScreenGamma);
	}
	
	public float getConfigScreenBrightness() {
		return (Float) listMap.get(StaticValueMap.ScreenBrightness);
	}
	
	public float getConfigScreenContrast() {
		return (Float) listMap.get(StaticValueMap.ScreenContrast);
	}
	
	public boolean getConfigScreenVSync() {
		return (Boolean) listMap.get(StaticValueMap.ScreenVSync);
	}
	
	public boolean getConfigScreenFullsceen() {
		return (Boolean) listMap.get(StaticValueMap.ScreenFullsceen);
	}
	
	public int getConfigScreenWidth() {
		return (Integer) listMap.get(StaticValueMap.ScreenWidth);
	}
	
	public int getConfigScreenHeight() {
		return (Integer) listMap.get(StaticValueMap.ScreenHeight);
	}
	
	public int getConfigScreenBitPerPixel() {
		return (Integer) listMap.get(StaticValueMap.ScreenBitPerPixel);
	}
	
}
