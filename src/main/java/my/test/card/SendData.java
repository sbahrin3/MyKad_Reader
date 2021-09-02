/**
 * 
 */
package my.test.card;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.json.simple.JSONObject;

/**
 * @author Shamsul Bahrin
 *
 */
public class SendData {
	
	
	
	public static void post(final String requestUrl, final String jsonInputString) throws Exception {
		
		System.out.println();
		System.out.println("initiate requestUrl... " + requestUrl);
		System.out.println("sending... " + jsonInputString);
		
		
        ExecutorService executorService = Executors.newFixedThreadPool(10);  
        executorService.execute(new Runnable() {  
              
            @Override  
            public void run() {  
            	try {
					doPost(requestUrl, jsonInputString);
				} catch (Exception e) {
					e.printStackTrace();
				}
                 
            }  
        });  
        executorService.shutdown(); 
        
	}

	private static void doPost(String requestUrl, String jsonInputString) throws Exception {

		HttpURLConnection con = (HttpURLConnection) new URL(requestUrl).openConnection();
		con.setRequestMethod("POST");
		con.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95 Safari/537.11");
		con.setRequestProperty("Content-Type", "application/json" );//; utf-8");
		con.setRequestProperty("Accept", "application/json");
		con.setDoOutput(true);

		OutputStream os = con.getOutputStream();
		byte[] input = jsonInputString.getBytes("utf-8");
		os.write(input);
		os.flush();
		String responseMessage = con.getResponseMessage();
		int responseCode = con.getResponseCode();
		
		System.out.println("responseCode = " + responseCode);
		System.out.println("responseMessage = " + responseMessage);
		
		con.disconnect();
		
		
	}
	
	
	
	
	@SuppressWarnings("unchecked")
	public static void main(String[] args) throws Exception {
		
		
		String url = "http://localhost:8080/my.web/mykad/readmykad";
		System.out.println("api url: " + url);
		
		JSONObject obj = new JSONObject();       
		obj.put("name", "MOHD FAIZAL BIN HASSAN");  
		obj.put("icno", "990822-09-9900");
		
		post(url, obj.toJSONString());

		System.out.println("done");
		
	}

}
