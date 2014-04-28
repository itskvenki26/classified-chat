package Models;

import org.json.JSONException;
import org.json.JSONObject;

public class CurrentUser {

	private static int id;
	private static String email_id;
	private static String name;
	private static String ph_no;
	
	public static final String U_EMAIL_ID = "user_email";
	public static final String U_NAME = "user_name";
	public static final String U_PH_NO = "user_phone_num";

	public static int getId() {
		return id;
	}

	public static void setId(int id) {
		CurrentUser.id = id;
	}

	public static String getEmail_id() {
		return email_id;
	}

	public static void setEmail_id(String email_id) {
		CurrentUser.email_id = email_id;
	}

	public static String getName() {
		return name;
	}

	public static void setName(String name) {
		CurrentUser.name = name;
	}

	public static String getPh_no() {
		return ph_no;
	}

	public static void setPh_no(String ph_no) {
		CurrentUser.ph_no = ph_no;
	}
	
	public JSONObject toJSON() throws JSONException {
    	JSONObject json = new JSONObject();
    	json.put(U_EMAIL_ID, email_id);
    	json.put(U_NAME, name);
    	json.put(U_PH_NO, ph_no);
    	
    	return json;
    }
}
