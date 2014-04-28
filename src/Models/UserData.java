package Models;

import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;
 public  class UserData {
     
        //private variables
        int id;
        String email_id;
        String name;
        String ph_no;
      //  ArrayList<Channel> Chlist = new ArrayList<Channel>();
        
        public static final String U_EMAIL_ID = "user_email";
    	public static final String U_NAME = "user_name";
    	public static final String U_PH_NO = "user_phone_num";
    	//public static final String U_CHANNELS="list of user channels";
         
        // Empty constructor
        public UserData(){
      
        }
        // constructor
        public UserData(int id, String email_id, String name, String ph_no/*,ArrayList<Channel> Chlist*/){
            this.id      = id;
            this.email_id    = email_id;
            this.name    = name;
            this.ph_no = ph_no;
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
        
        public JSONObject toJSON() throws JSONException {
        	JSONObject json = new JSONObject();
        	json.put(U_EMAIL_ID, email_id);
        	json.put(U_NAME , name);
        	json.put(U_PH_NO , ph_no);
        	//json.put(U_CHANNELS,Chlist);
        	
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