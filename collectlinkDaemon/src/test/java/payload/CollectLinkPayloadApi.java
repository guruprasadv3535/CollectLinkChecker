package payload;

import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import endpoints.PrivateKeyAccess;
import endpoints.SignatureGenerator;
import endpoints.TimeStampGenerator;
import genricLibraries.PropertiesUtility;
import genricLibraries.UtilitiesPath;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class CollectLinkPayloadApi {

	public static Response CreateUser(PropertiesUtility property) {

//	    Create a JSON object for payer
		JSONObject payer = new JSONObject();
		payer.put("user_ref", "guru1234");
		payer.put("company_name", "guru's softwares");
		payer.put("user_name", "guruprasad");
		payer.put("user_mobile_number", "8970486528");

		/*
		 * Create collect modes of array for the following modes "UPI",
		 * "VIRTUAL_ACCOUNT", "NET_BANKING", "CREDIT_CARD", "DEBIT_CARD"
		 */
		List<String> collectModes = new ArrayList<String>();
		collectModes.add("UPI");
		collectModes.add("NET_BANKING");
		collectModes.add("CREDIT_CARD");
		collectModes.add("DEBIT_CARD");
//		collectModes.add("VIRTUAL_ACCOUNT");

//	    Create Json object for collect and load the payer object into collect	
		JSONObject collect = new JSONObject();
		
		int i=Integer.parseInt(property.readData("coll_ref"));
		while(true) {
			i++;
			break;
		}
		String newValue=""+i;
		property.writeToProperties("coll_ref",newValue, UtilitiesPath.PROPERTIES_PATH);
		String collect_ref="RemoteTest2-"+i;
		
		int amt=500;
//		if(i%2==0) {
//			amt=500;
//		}
//		else {
//			amt=500;
//		}
		collect.put("collect_ref", collect_ref);
		collect.put("collect_modes", collectModes);
		collect.put("amount", amt);
		collect.put("transaction_note", "server test");
		collect.put("payer", payer);
//		collect.put("virtual_account_no", "ESCR11ALFIN00ACMENBFC");
//		collect.put("ifsc", "ICIC0000104");

//		Convert the Jsonobject of collect in array
		JSONArray collectsArray = new JSONArray();
		collectsArray.add(collect);

//		Create the jsonObject for outerblock and load the collect array into it
        /*
         * escrow name: guru
         */
		JSONObject requestPayload = new JSONObject();
		requestPayload.put("escrow_id", "AliceFin-dlb");
		requestPayload.put("redirect_url", "https://www.iamvouched.com/");
		requestPayload.put("collects", collectsArray);
		String timestamp = TimeStampGenerator.generateTimestamp();
		requestPayload.put("timestamp", timestamp);

//		It is used to convert the jsonobject as a exact string for signature generation
		ObjectMapper mapper = new ObjectMapper();
		String jsonString = null;
		try {
			jsonString = mapper.writeValueAsString(requestPayload);
		} catch (JsonProcessingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

//		It is used to generate the signature by using payload and private key
		String signature = null;
		try {
			signature = SignatureGenerator.generateRSASignature(jsonString, PrivateKeyAccess.getPrivateKey());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

//		Loading the generated signature into json object for request payload
		requestPayload.put("signature", signature);

//		It is the apikey for header and to access the api
		String apiKey = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1aWQiOiI1YzljOThkNS1mMDEyLTQxOTctYWZhOC04ZGM4NmQ0NTNjM2QiLCJuYW1lIjoiQWxpY2UgRmluc2VydiIsInJlZyI6Ik5mUmxBbFR4UWlkNFZ2UmJsbnNVIiwiY29uZmlnIjoiQWxpY2VGaW4iLCJpYXQiOjE2NjU0NTMxNTF9.Mda2uuX9o8zTFrcEMHzT5sm5s-TsoGeWyfffVfML5Ms";

//		It is for invoking the api 
		Response response = RestAssured.given().contentType(ContentType.JSON).accept(ContentType.JSON)
				.header("apikey", apiKey).body(requestPayload.toJSONString())
				.baseUri("https://escrow.iamvouched.com/v1/escrow/generate_collect_link").when().post().then()
				.assertThat().statusCode(200).extract().response();
		
//       .log().body() to print the request body which we sent this should hard coded after baseUri
		
		return response;
	}

}
