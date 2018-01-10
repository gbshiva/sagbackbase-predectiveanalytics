package app;

import app.utils.SparkUtils;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;


import lombok.Data;
import spark.QueryParamsMap;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.Spark;

import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import org.apache.log4j.Logger;

import static spark.Spark.get;

public class ApplicationMain {

    public static void main(String[] args) {
    	
        Logger logger = Logger.getLogger(ApplicationMain.class);
        SparkUtils.createServerWithRequestLog(logger);
        spark.Spark.port(8080);
        get("/predict", (request, response) -> {
        	response.status(200);
            response.type("application/json");
        	customerModel model = new customerModel(request);
        	//model.predict();
            return dataToJson(model.predict());
        });
        		
        		
        		
        		
        		
    }

    public static String dataToJson(Object data) {
        try {
        	
        	//List dt = (List) data;
        	//System.out.println(((cust)dt.get(1)).getName());
        	
        	
           ObjectMapper mapper = new ObjectMapper();
        	
        	//ObjectMapper mapper = new XmlMapper();
        	
        	
            //mapper.enable(SerializationFeature.INDENT_OUTPUT);
            //StringWriter sw = new StringWriter();
            //mapper.writeValue(sw, data);
            
            return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(data);
            
            //return sw.toString();
        } catch (IOException e){
        	e.printStackTrace(System.out);
        	throw new RuntimeException("IOException from a StringWriter?");
            
        }
    }
}
