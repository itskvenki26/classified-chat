package Models;

import org.json.JSONException;
import org.json.JSONObject;

public class ChatRoom_User {

	private  int id;
	private  long crid;
	private  long usrid;
	private  long Cur_Mak;
	private  long Cur_Ekey;

	public static final String CRU_CRID = "current user chat room id";
	public static final String CRU_CUR_MAK = "current user current MAK";
	public static final String CRU_EKEY = "current user current EKEY";
	public static final String CRU_USER_ID = "User Id";

	public ChatRoom_User() {

	}

	// constructor
	public ChatRoom_User(int id, long crid, long usrid, long Cur_Mak,
			long Cur_Ekey) {
		this.id = id;
		this.crid = crid;
		this.Cur_Mak = Cur_Mak;
		this.Cur_Ekey = Cur_Ekey;
		this.usrid = usrid;

		// this.Chlist=Chlist;

	}

	public  int getId() {
		return this.id;
	}

	public void setID(int id) {
		this.id = id;
	}

	public long getCur_Mak() {
		return this.Cur_Mak;
	}

	public void setCur_Mak(long Cur_Mak) {
		this.Cur_Mak = Cur_Mak;
	}

	public void setCur_Ekey(long Cur_Ekey) {
		this.Cur_Ekey = Cur_Ekey;
	}

	public long getCur_Ekey() {
		return this.Cur_Ekey;
	}

	public void setusrid(long usrid) {
		this.usrid = usrid;
	}

	public long getusrid() {
		return this.usrid;
	}

	public void setcrid(long crid) {
		this.crid = crid;
	}

	public long getcrid() {
		return this.crid;
	}

	public JSONObject toJSON() throws JSONException {
		JSONObject json = new JSONObject();
		json.put(CRU_CUR_MAK, Cur_Mak);
		json.put(CRU_EKEY, Cur_Ekey);
		json.put(CRU_USER_ID, usrid);
		json.put(CRU_CRID, crid);

		return json;
	}
}
