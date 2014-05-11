package Models;

import org.json.JSONException;
import org.json.JSONObject;

public class User_Channel {

	// private variables
	int id;
	long usrid;
	String type;
	String phnum;
	String Host;
	String Service;
	long port;
	String username;
	int pref_channel_boolean;

	public static final String UCH_U_ID = "User channel user id";
	public static final String UCH_TYPE="User Channel Type";
	public static final String UCH_HOST = "User channel Host";
	public static final String UCH_SERVICE = "User channel service";
	public static final String UCH_USR_NAME = "User channel user name";
//	public static final String UCH_PWD = "User channel password";
	public static final String UCH_PH_NUM="User phone number";
	public static final String UCH_PORT ="User Port number";
	public static final String UCH_PRF_CHANNEL="User preferred channel(boolean)";

	// Empty constructor
	public User_Channel() {

	}

	// constructor
	public User_Channel(int id, long usrid, String type, String phnum,
			String Host, String Service, long port, String username,
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
	
	public long getusrid() {
		return this.usrid;
	}
	
	public void setusrid(long usrid) {
		this.usrid = usrid;
	}
	
	public int getpref_channel_boolean() {
		return this.pref_channel_boolean;
	}
	
	public void setpref_channel_boolean(int pref_channel_boolean) {
		this.pref_channel_boolean = pref_channel_boolean;
	}
	
	public long getport() {
		return this.port;
	}
	
	public void setport(long port) {
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
		json.put(UCH_U_ID , usrid);
		json.put(UCH_TYPE, type);
		json.put(UCH_HOST, Host);
		json.put(UCH_SERVICE, Service);
		json.put(UCH_USR_NAME , username);
		json.put(UCH_PH_NUM , phnum);
		json.put(UCH_PORT  , port);
		json.put(UCH_PRF_CHANNEL , pref_channel_boolean);
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