package Models;

import org.json.JSONException;
import org.json.JSONObject;

public class CurrentUser {

	private static int id;
	private static long crid;
	private static String Cur_Mak;
	private static String Cur_Ekey;
	private static String Old_Mak;
	
	public static final String CU_CRID="current user crid";
	public static final String CU_CUR_MAK = "current user current MAK";
	public static final String CU_EKEY = "current user current EKEY";
	public static final String CU_OLD_MAK = "current user OLD MAK";
	
	 public CurrentUser(){
	      
     }
     // constructor
     public CurrentUser(int id,long crid, String Cur_Mak, String Cur_Ekey, String Old_Mak/*,ArrayList<Channel> Chlist*/){
         CurrentUser.id      = id;
         CurrentUser.crid=crid;
         CurrentUser.Cur_Mak    = Cur_Mak;
         CurrentUser.Cur_Ekey    = Cur_Ekey;
         CurrentUser.Old_Mak = Old_Mak;
        // this.Chlist=Chlist;
          
     }

	public static int getId() {
		return id;
	}

	public static void setId(int id) {
		CurrentUser.id = id;
	}

	public String getCur_Mak() {
		return Cur_Mak;
	}
	public long getcrid() {
		return crid;
	}

	public static void setcrid(long crid) {
		CurrentUser.crid = crid;
	}

	public static void setCur_Mak(String Cur_Mak) {
		CurrentUser.Cur_Mak = Cur_Mak;
	}

	public String getCur_Ekey() {
		return Cur_Ekey;
	}

	public static void setCur_Ekey(String Cur_Ekey) {
		CurrentUser.Cur_Ekey = Cur_Ekey;
	}

	public String getOld_Mak() {
		return Old_Mak;
	}

	public static void setOld_Mak(String Old_Mak) {
		CurrentUser.Old_Mak = Old_Mak;
	}
	
	public JSONObject toJSON() throws JSONException {
    	JSONObject json = new JSONObject();
    	json.put(CU_CUR_MAK, Cur_Mak);
    	json.put(CU_EKEY, Cur_Ekey);
    	json.put(CU_OLD_MAK, Old_Mak);
    	
    	return json;
    }
}
