package app;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import org.apache.commons.codec.binary.Base64;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

import com.fasterxml.jackson.databind.JsonNode;

public class invokeZementis {

	private String urlconnection = "https://myadapa.zementis.com/adapars/apply/RetailBank_RefiWealthMgmt?record=";
	private String username = "";
	private String pwd = "";

	public invokeZementis(String uid, String pw) {
		username = uid;
		pwd = pw;
	}

	public predectionResponse predict(Object userProfile) {
		try {
			String authString = username + ":" + pwd;
			String authStringEnc = new String(Base64.encodeBase64(authString.getBytes()));
			String data = dataToJson(userProfile);
			String encode = URLEncoder.encode(data, "UTF-8");
			URL url = new URL(urlconnection + encode);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Accept", "application/json");

			conn.setRequestProperty("Authorization", "Basic " + authStringEnc);

			if (conn.getResponseCode() != 200) {
				throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
			}

			BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));

			String response = new String();
			for (String line; (line = br.readLine()) != null; response += line)
				;
			System.out.println(response);
			conn.disconnect();
			return stringTopredictResponse(response);
		} catch (MalformedURLException e) {

			e.printStackTrace();

		} catch (IOException e) {

			e.printStackTrace();

		}
		return null;

	}

	public static String dataToJson(Object data) {
		try {
			ObjectMapper mapper = new ObjectMapper();
			return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(data);
		} catch (IOException e) {
			e.printStackTrace(System.out);
			throw new RuntimeException("IOException from a StringWriter?");

		}
	}

	static predectionResponse stringTopredictResponse(String jsonString) {
		try {
			ObjectMapper mapper = new ObjectMapper();
			adapaResponse resp = mapper.readValue(jsonString, adapaResponse.class);
			predectionResponse usrResp = new predectionResponse();
			System.out.println("Business Decision = "+resp.outputs[0].BusinessDecision);
			if (resp.outputs[0].BusinessDecision.equals("WealthManagement")) {
				System.out.println("Setting Wealth Management Scrore");
				usrResp.WealthMgmtscore = resp.outputs[0].Score;
				usrResp.Mortgagescore = 0;
			} else {
				System.out.println("Setting Mortgage Scrore");
				usrResp.Mortgagescore = resp.outputs[0].Score;
				usrResp.WealthMgmtscore = 0;

			}
			return usrResp;
		} catch (Exception e) {
			e.printStackTrace(System.out);
			throw new RuntimeException("Exception conv JSON response to Obj");

		}
	}

}
