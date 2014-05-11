package Models;

import org.json.JSONException;
import org.json.JSONObject;

public class ChatRoom_Profile {

	// private variables
	int id;
	long crid;
	String MAC_Cur;
	String MAC_Old;

	// String ph_no;
	// ArrayList<Channel> Chlist = new ArrayList<Channel>();

	public static final String CRP_CRID = "Chat Room Id";
	public static final String CRP_MAC_CUR = "Current MAC key";
	public static final String CRP_MAC_OLD = "Old MAC key";

	// public static final String CRP_CHANNELS="list of user channels";

	// Empty constructor
	public ChatRoom_Profile() {

	}

	// constructor
	public ChatRoom_Profile(int id, long crid, String MAC_Cur, String MAC_Old/*
																			 * ArrayList
																			 * <
																			 * Channel
																			 * >
																			 * Chlist
																			 */) {
		this.id = id;
		this.crid = crid;
		this.MAC_Cur = MAC_Cur;
		this.MAC_Old = MAC_Old;
		// this.Chlist=Chlist;

	}

	/*
	 * public ArrayList<Channel> getChlist() { return Chlist; } public void
	 * setChlist(ArrayList<Channel> Chlist) { this.Chlist = Chlist; }
	 */
	// getting ID
	public int getID() {
		return this.id;
	}

	// setting id
	public void setID(int id) {
		this.id = id;
	}

	// getting imei
	public long getcrid() {
		return this.crid;
	}

	// setting imei
	public void setcrid(long crid) {
		this.crid = crid;
	}

	// getting name
	public String getMAC_Cur() {
		return this.MAC_Cur;
	}

	// setting name
	public void setMAC_Cur(String MAC_Cur) {
		this.MAC_Cur = MAC_Cur;
	}

	// getting Message
	public String getMAC_Old() {
		return this.MAC_Old;
	}

	// setting Message
	public void setMAC_Old(String MAC_Old) {
		this.MAC_Old = MAC_Old;
	}

	public JSONObject toJSON() throws JSONException {
		JSONObject json = new JSONObject();
		json.put(CRP_CRID, crid);
		json.put(CRP_MAC_CUR, MAC_Cur);
		json.put(CRP_MAC_OLD, MAC_Old);
		// json.put(U_CHANNELS,Chlist);

		return json;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Char Room UserInfo [crid=" + crid + "]";
	}

}