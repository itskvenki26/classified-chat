package Models;

import org.json.JSONException;
import org.json.JSONObject;

public class ChatRoom_User {

	private static int id;
	private static long crid;
	private static long usrid;
	private static long Cur_Mak;
	private static long Cur_Ekey;

	public static final String CRU_CRID = "current user chat room id";
	public static final String CRU_CUR_MAK = "current user current MAK";
	public static final String CRU_EKEY = "current user current EKEY";
	public static final String CRU_USER_ID = "User Id";

	public ChatRoom_User() {

	}

	// constructor
	public ChatRoom_User(int id, long crid, long usrid, long Cur_Mak,
			long Cur_Ekey) {
		ChatRoom_User.id = id;
		ChatRoom_User.crid = crid;
		ChatRoom_User.Cur_Mak = Cur_Mak;
		ChatRoom_User.Cur_Ekey = Cur_Ekey;
		ChatRoom_User.usrid = usrid;

		// this.Chlist=Chlist;

	}

	public static int getId() {
		return id;
	}

	public void setID(int id) {
		ChatRoom_User.id = id;
	}

	public long getCur_Mak() {
		return ChatRoom_User.Cur_Mak;
	}

	public void setCur_Mak(long Cur_Mak) {
		ChatRoom_User.Cur_Mak = Cur_Mak;
	}

	public void setCur_Ekey(long Cur_Ekey) {
		ChatRoom_User.Cur_Ekey = Cur_Ekey;
	}

	public long getCur_Ekey() {
		return ChatRoom_User.Cur_Ekey;
	}

	public void setusrid(long usrid) {
		ChatRoom_User.usrid = usrid;
	}

	public long getusrid() {
		return ChatRoom_User.usrid;
	}

	public void setcrid(long crid) {
		ChatRoom_User.crid = crid;
	}

	public long getcrid() {
		return ChatRoom_User.crid;
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
