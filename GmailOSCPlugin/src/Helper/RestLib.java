package Helper;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.testng.annotations.Test;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import TestEngine.Base;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class RestLib extends Base{
  
  public Map<String,String> restResponse(String restMethod,String userName,String password,String uri,String payLoadPath) {
	  RequestSpecification spec = RestAssured.given();
	  spec.auth().preemptive().basic(userName, password);
	  spec.header("Content-Type", "application/json");
	  Map<String,String> restMap = new HashMap<String,String>();
	  
	  if(!restMethod.toUpperCase().equalsIgnoreCase("GET"))
	  {
	  File file = new File(payLoadPath);
	  spec.body(file);
	  }
	  
	  
	  Response res = null;
	  if(restMethod.toUpperCase().equalsIgnoreCase("POST"))
	  {
		  res = spec.post(uri);
	  }
	  else if(restMethod.toUpperCase().equalsIgnoreCase("GET"))
	  {
		  res = spec.get(uri);
	  }
	  else if(restMethod.toUpperCase().equalsIgnoreCase("PATCH"))
	  {
		  res = spec.patch(uri);
	  }
	  else
	  {
		  report.log("FAIL","invalid rest method used");
	  }
	  String mapvalue[] =null;
	  String restBody[] = res.body().asString().replace("\"","").replace(",","").split("\\r?\\n");
	  for(String value : restBody)
	  {
		  if(value.contains(":"))
				  {
			  		mapvalue = value.split(":",2);
			  		restMap.put(mapvalue[0].trim(), mapvalue[1].trim());
				  }
	  }
	  
	  String statusCode = res.getStatusCode()+"";
	  restMap.put("statusCode", statusCode);
	return restMap;
	  }
 
  public void addParameterToJsonFile(String filePath,String key,String value)
  {
	  File jsonFile = new File(filePath);
	  
	// Commons-IO
	String jsonString;
	try {
		jsonString = FileUtils.readFileToString(jsonFile);
	JsonElement jelement = new JsonParser().parse(jsonString);
	JsonObject jobject = jelement.getAsJsonObject();
	jobject.addProperty(key,value);
	 
	Gson gson = new Gson();
	 
	String resultingJson = gson.toJson(jelement);
	
		FileUtils.writeStringToFile(jsonFile, resultingJson);
	} catch (IOException e) 
	{
		report.log("FAIL", "Unable to add new parameter to Json file "+e.toString());
		
	}
  }
//  @Test
//  public void test()
//  {
//	  Response res = restResponse("ebenes","Welcome1","https://fuscdrmsmc422-fa-ext.us.oracle.com:443/salesApi/resources/latest/goiApps?onlyData=true&q=IsActive=Y");
//	  System.out.println(res.body().asString());
//	  JsonPath p = res.jsonPath();
//	 System.out.println(p.get("ApplicationId").toString());
//	  
//  }
//  
  @Test
  public void test()
  {
	 Map<String, String> restBody= restResponse("GET","ebenes","Welcome1","https://fuscdrmsmc422-fa-ext.us.oracle.com:443/salesApi/resources/latest/goiApps?onlyData=true&q=IsActive=Y","");
	 System.out.println(restBody.get("count"));
	  
  }
  
}
