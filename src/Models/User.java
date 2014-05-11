package Models;

import org.json.JSONException;
import org.json.JSONObject;
 public  class User {
     
        //private variables
        int id;
        String email_id;
        String name;
        String ph_no;
        int pref_channel_id;
      //  ArrayList<Channel> Chlist = new ArrayList<Channel>();
        
        public static final String U_EMAIL_ID = "user_email";
    	public static final String U_NAME = "user_name";
    	public static final String U_PH_NO = "user_phone_num";
    	public static final String U_PREF_CHANNEL_ID ="ID of preferred channel";
    	//public static final String U_CHANNELS="list of user channels";
         
        // Empty constructor
        public User(){
      
        }
        // constructor
        public User(int id, String email_id, String name, String ph_no, int pref_channel_id){
            this.id      = id;
            this.email_id    = email_id;
            this.name    = name;
            this.ph_no = ph_no;
            this.pref_channel_id=pref_channel_id;
           // this.Chlist=Chlist;
             
        }
       /* public ArrayList<Channel> getChlist() {
    		return Chlist;
    	}
    	public void setChlist(ArrayList<Channel> Chlist) {
    		this.Chlist = Chlist;
    	}*/
        // getting ID
        public int getID(){
            return this.id;
        }
      
        // setting id
        public void setID(int id){
            this.id = id;
        }
      
        // getting imei
        public String getEMAIL(){
            return this.email_id;
        }
      
        // setting imei
        public void setEMAIL(String email_id){
            this.email_id = email_id;
        }
         
        // getting name
        public String getName(){
            return this.name;
        }
      
        // setting name
        public void setName(String name){
            this.name = name;
        }
         
        // getting Message
        public String getPH_NO(){
            return this.ph_no;
        }
      
        // setting Message
        public void setPH_NO(String ph_no){
            this.ph_no = ph_no;
        }
        
        public int getpref_channel_id(){
            return this.pref_channel_id;
        }
      
        // setting imei
        public void setpref_channel_id(int pref_channel_id){
            this.pref_channel_id = pref_channel_id;
        }
        
        public JSONObject toJSON() throws JSONException {
        	JSONObject json = new JSONObject();
        	json.put(U_EMAIL_ID, email_id);
        	json.put(U_NAME , name);
        	json.put(U_PH_NO , ph_no);
        	json.put(U_PREF_CHANNEL_ID,pref_channel_id);
        	
        	return json;
        }
     
        /* (non-Javadoc)
         * @see java.lang.Object#toString()
         */
        @Override
        public String toString() {
            return "UserInfo [name=" + name + "]";
        }
         
    }