package app;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import spark.Request;

public class customerModel {
	
	private Map<String, String> input = new HashMap<String,String>(); 
	private Map<String, String> output = new HashMap<String,String>(); 
	
	public customerModel(Request request){
		Set<String> params = request.queryParams();  
		for(Object object : params) {
		    input.put((String)object, request.queryParams((String)object));
		}
	}
	public Object predict() throws Exception{
		//output.put("WealthMgmtscore", "95");
		//output.put("Mortgagescore", "45");
		userProfile usr = new userProfile();
		invokeZementis predicitionEngine = new invokeZementis("test","test");
		return predicitionEngine.predict(usr);
		
	}

}
