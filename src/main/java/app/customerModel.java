package app;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import spark.Request;

public class customerModel {
	
	private Map<String, String> input = new HashMap<String,String>(); 
	private Map<String, String> output = new HashMap<String,String>(); 
	private userProfileFactory usrprofiles;
	
	public customerModel(Request request, userProfileFactory factory){
		Set<String> params = request.queryParams();  
		for(Object object : params) {
			String param = (String)object;
			String val = request.queryParamOrDefault(param, "NA");
			System.out.println(param + " "+val);
		    input.put(param,val);
		}
		usrprofiles = factory;
	}
	public Object predict() throws Exception{
		//output.put("WealthMgmtscore", "95");
		//output.put("Mortgagescore", "45");
		invokeZementis predicitionEngine = new invokeZementis("Shivakumar.Gokaram@softwareag.com","@3s3FLc9");
		System.out.println("Invoking Predeiction with ID="+input.get("id"));
		return predicitionEngine.predict(usrprofiles.getUserProfile(input.get("id")));
	}

}
