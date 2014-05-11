package Models;

import org.json.JSONException;
import org.json.JSONObject;

public class MyProfile {

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
	String name;

	public static final String MP_U_ID = "User channel user id";
	public static final String MP_TYPE="User Channel Type";
	public static final String MP_HOST = "User channel Host";
	public static final String MP_SERVICE = "User channel service";
	public static final String MP_USR_NAME = "User channel user name";
//	public static final String UCH_PWD = "User channel password";
	public static final String MP_PH_NUM="User phone number";
	public static final String MP_PORT ="User Port number";
	public static final String MP_PRF_CHANNEL="User preferred channel(boolean)";
	public static final String MP_NAME="User full name";

	// Empty constructor
	public MyProfile() {

	}

	// constructor
	public MyProfile(int id, long usrid, String type, String phnum,
			String Host, String Service, long port, String username,
			int pref_channel_boolean,String name) {
		this.id = id;
        this.usrid=usrid;
        this.type=type;
        this.phnum=phnum;
		this.Host = Host;
		this.Service = Service;
		this.port=port;
		this.username = username;
		this.pref_channel_boolean=pref_channel_boolean;
		this.name=name;

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
	
	public String getname() {
		return this.name;
	}

	public void setname(String name) {
		this.name = name;
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
		json.put(MP_U_ID , usrid);
		json.put(MP_TYPE, type);
		json.put(MP_HOST, Host);
		json.put(MP_SERVICE, Service);
		json.put(MP_USR_NAME , username);
		json.put(MP_PH_NUM , phnum);
		json.put(MP_PORT  , port);
		json.put(MP_PRF_CHANNEL , pref_channel_boolean);
		json.put(MP_NAME, name);
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