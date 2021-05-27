package dataparser;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class CustomerDataValidator {
	
	/**
	 * For given data.json file, 
	 * program that will read in the data and mark any records:
	 * That are a duplicate of another record.
	 * name field is null, missing, or blank. 
	 * address field is null, missing, or blank. 
	 * zip is null, missing, or an invalid U.S. zipcode.
	 * 
	 * @param filePath location of the json path
	 * @return list of invalid ids
	 */
	public static List<String> listInvalidateIds(String filePath){
		List<String> result = new ArrayList<String>();
		try {
			JSONArray jsonArray = new JSONArray(Files.readString(Paths.get(filePath)));
			
			Map<String, List<String>> uniqValid = new HashMap<String, List<String>>();
			
			for(int i=0; i<jsonArray.length(); i++) {
				JSONObject obj = jsonArray.getJSONObject(i);
				System.out.println(i+"-"+obj.toString());
				
				String name = obj.optString("name");
				String address = obj.optString("address");
				String zip = obj.optString("zip");
				String id = obj.optString("id");
				
				//System.out.println(isValid(name) +"-"+ isValid(address) +"-"+ isValidZip(zip));
				
				if(!isValid(name) || !isValid(address) || !isValidZip(zip)) {
					result.add(id);
					System.out.println("This is an invalid record!");
				} else {
					String key = name+"-"+address+"-"+zip;
					if(uniqValid.containsKey(key)) {
						List<String> existing =  uniqValid.get(key);
						System.out.println("This is duplicate record of ! " + existing);
						existing.add(id);
						uniqValid.put(key, existing);
					} else {
						List<String> list = new ArrayList<String>();
						list.add(id);
						uniqValid.put(key, list);
					}
				}	
			}
			uniqValid.entrySet().stream().filter(list -> list.getValue().size()>1).forEach(entry -> result.addAll(entry.getValue()));
			System.out.println("========================================================");
			System.out.println("Total Records Count: "+jsonArray.length());
			System.out.println("Total Valid Records Count: "+uniqValid.entrySet().stream().filter(list -> list.getValue().size()==1).count());
		} catch (JSONException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} 
		
		System.out.println("Total Invalid Records Count: "+result.size());
		return result;
	}
	
	/**
	 * 
	 * @param prop
	 * @return
	 */
	public static boolean isValid(String prop) {
		if(prop == null || prop.isEmpty()) {
			return false;
		}
		
		return true;
	}
	
	/**
	 * 
	 * @param prop
	 * @return
	 */
	public static boolean isValidZip(String prop) {
		if(prop == null || prop.isBlank()) {
			return false;
		}
		
		if(prop.length() != 5 || prop.contains(" ")) {
			return false;
		}
		
		try{
			Integer.parseInt(prop);
		} catch (Exception e) {
			return false;
		}
		/*
		 * if(Integer.getInteger(prop)==null) { return false; }
		 */
		
		return true;
	}

}
