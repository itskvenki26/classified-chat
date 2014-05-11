package Models;

import org.json.JSONException;
import org.json.JSONObject;

public class CurrentUser {

	private int id;
	private long crid;
	private String Cur_Mak;
	private String Cur_Ekey;
	private String Old_Mak;
	
	public static final String CU_CRID="current user crid";
	public static final String CU_CUR_MAK = "current user current MAK";
	public static final String CU_EKEY = "current user current EKEY";
	public static final String CU_OLD_MAK = "current user OLD MAK";
	
	 public CurrentUser(){
	      
     }
     // constructor
     public CurrentUser(int id,long crid, String Cur_Mak, String Cur_Ekey, String Old_Mak/*,ArrayList<Channel> Chlist*/){
         this.id      = id;
         this.crid=crid;
         this.Cur_Mak    = Cur_Mak;
         this.Cur_Ekey    = Cur_Ekey;
         this.Old_Mak = Old_Mak;
        // this.Chlist=Chlist;
          
     }

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCur_Mak() {
		return Cur_Mak;
	}
	public long getcrid() {
		return crid;
	}

	public  void setcrid(long crid) {
		this.crid = crid;
	}

	public void setCur_Mak(String Cur_Mak) {
		this.Cur_Mak = Cur_Mak;
	}

	public String getCur_Ekey() {
		return Cur_Ekey;
	}

	public void setCur_Ekey(String Cur_Ekey) {
		this.Cur_Ekey = Cur_Ekey;
	}

	public String getOld_Mak() {
		return Old_Mak;
	}

	public void setOld_Mak(String Old_Mak) {
		this.Old_Mak = Old_Mak;
	}
	
	public JSONObject toJSON() throws JSONException {
    	JSONObject json = new JSONObject();
    	json.put(CU_CUR_MAK, Cur_Mak);
    	json.put(CU_EKEY, Cur_Ekey);
    	json.put(CU_OLD_MAK, Old_Mak);
    	
    	return json;
    }
}
