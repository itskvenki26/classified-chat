package Models;

import org.json.JSONException;
import org.json.JSONObject;

public class MyProfile_Channel {

	// private variables
	int id;
	int usrid;
	String type;
	String phnum;
	String Host;
	String Service;
	int port;
	String username;
	int pref_channel_boolean;

	public static final String MPC_U_ID = "User channel user id";
	public static final String MPC_TYPE="User Channel Type";
	public static final String MPC_HOST = "User channel Host";
	public static final String MPC_SERVICE = "User channel service";
	public static final String MPC_USR_NAME = "User channel user name";
    //public static final String UCH_PWD = "User channel password";
	public static final String MPC_PH_NUM="User phone number";
	public static final String MPC_PORT ="User Port number";
	public static final String MPC_PRF_CHANNEL="User preferred channel(boolean)";

	// Empty constructor
	public MyProfile_Channel() {

	}

	// constructor
	public MyProfile_Channel(int id, int usrid, String type, String phnum,
			String Host, String Service, int port, String username,
			int pref_channel_boolean) {
		this.id = id;
        this.usrid=usrid;
        this.type=type;
        this.phnum=phnum;
		this.Host = Host;
		this.Service = Service;
		this.port=port;
		this.username = username;
		this.pref_channel_boolean=pref_channel_boolean;

	}

	
	public int getID() {
		return this.id;
	}
	
	public void setID(int id) {
		this.id = id;
	}
	
	public int getusrid() {
		return this.usrid;
	}
	
	public void setusrid(int usrid) {
		this.usrid = usrid;
	}
	
	public int getpref_channel_boolean() {
		return this.pref_channel_boolean;
	}
	
	public void setpref_channel_boolean(int pref_channel_boolean) {
		this.pref_channel_boolean = pref_channel_boolean;
	}
	
	public int getport() {
		return this.port;
	}
	
	public void setport(int port) {
		this.port = port;
	}
	
	public String gettype() {
		return this.type;
	}

	public void settype(String type) {
		this.type = type;
	}

	public String getHost() {
		return this.Host;
	}

	public void setHost(String Host) {
		this.Host = Host;
	}

	public String getService() {
		return this.Service;
	}

	public void setService(String Service) {
		this.Service = Service;
	}

	public String getusername() {
		return this.username;
	}

	public void setusername(String username) {
		this.username = username;
	}

	// getting name
	public String getphnum() {
		return this.phnum;
	}

	// setting name
	public void setphnum(String phnum) {
		this.phnum = phnum;
	}

	public JSONObject toJSON() throws JSONException {
		JSONObject json = new JSONObject();
		// json.put(MCH_U_ID, U_id);
		json.put(MPC_U_ID , usrid);
		json.put(MPC_TYPE, type);
		json.put(MPC_HOST, Host);
		json.put(MPC_SERVICE, Service);
		json.put(MPC_USR_NAME , username);
		json.put(MPC_PH_NUM , phnum);
		json.put(MPC_PORT  , port);
		json.put(MPC_PRF_CHANNEL , pref_channel_boolean);
		return json;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Channel Info [user=" + username + '\t' + Service + "]";
	}

}
