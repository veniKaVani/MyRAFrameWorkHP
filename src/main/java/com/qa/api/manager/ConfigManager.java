package com.qa.api.manager;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigManager {
	//1.create the objRef of Properties class
	private static Properties prop = new Properties();
	
	//2.create the fileinputStream Ref Obj inside the static block:so as to get exec
	//even before the exec of constructor/objRef creation of the class==>
	//ConfigManager cmgr = new ConfigManager();
	static {
		
		try(InputStream instr = ConfigManager.class.getClassLoader().getResourceAsStream("config\\sampleconfig.properties")){
			//3.Load the prop file if instr is not null otherwise go to the catch block&throw Exception
			if(instr !=null) {
				prop.load(instr);
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	//Creating a generic util fn for getting the Config.Properties value on the basis of key:
	public static String getProp(String key) {
		return prop.getProperty(key);
	}
    //creating a generic util fn for setting the config.Properties value based on the key:
	public static void setProp(String key, String value) {
		prop.setProperty(key, value);
	}
	

}
