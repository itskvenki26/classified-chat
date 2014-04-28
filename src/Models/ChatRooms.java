package Models;

import org.json.JSONException;
import org.json.JSONObject;
 public  class ChatRooms {
     
        //private variables
        int id;
        int userid;
        int authkey_old;
        int authkey_new;
        int enc_key;
        int signing_key;
        String CR_name;
        String Password;
        String creator;
        String time;
        String text;
        
  
        public static final String CR_NAME="Chat_Room_Name";
    	public static final String CR_PASSWORD="Chat_Room_Password";
    	public static final String CR_CREATOR="Chat_Room_Creator";
    	public static final String CR_TIME="Chat_Room_time";
    	public static final String CR_TEXT="Chat_Room_text";
    	public static final String CR_USER_ID="Chat Room User id";
    	public static final String CR_AUTHKEY_OLD="Authentication key old";
    	public static final String CR_AUTHKEY_NEW="Authentication key new"; 
    	public static final String CR_ENCRYPTION_KEY="Encryption key";
    	public static final String CR_SIGNING_KEY="Signing Key";
        // Empty constructor
        public ChatRooms(){
      
        }
        // constructor
        public ChatRooms(int id, int userid, int authkey_old,int authkey_new,int enc_key,int signing_key,String CR_name,String Password, String creator, String time, String text){
            this.id      = id;
            this.CR_name=CR_name;
            this.Password=Password;
            this.creator    = creator;
            this.time    = time;
            this.text = text; 
            this.userid=userid;
            this.authkey_old=authkey_old;
            this.authkey_new=authkey_new;
            this.enc_key=enc_key;
            this.signing_key=signing_key;
             
        }
      
        // getting ID
        public int getID(){
            return this.id;
        }
      
        // setting id
        public void setID(int id){
            this.id = id;
        }
        public int getUserID(){
            return this.userid;
        }
   
        public void setUserID(int userid){
            this.userid = userid;
        }
      
       
        public void setauthkey_old(int authkey_old){
            this.authkey_old = authkey_old;
        }
        
        public int getauthkey_old(){
            return this.authkey_old;
        }
      
        
        
        public void setauthkey_new(int authkey_new){
            this.authkey_new = authkey_new;
        }
        
        public int getauthkey_new(){
            return this.authkey_new;
        }
        
        public void setenc_key(int authkey_new){
            this.authkey_new = authkey_new;
        }
        
        public int getenc_key(){
            return this.authkey_new;
        }
        
        public String getCR_name(){
            return this.CR_name;
        }
        public void setCR_name(String CR_name){
        	 this.CR_name =CR_name;
        }
        
        public String getPassword(){
            return this.Password;
        }
        public void setPassword(String Password){
        	 this.Password =Password;
        }
      
      
        // getting imei
        public String getCREATOR(){
            return this.creator;
        }
      
        // setting imei
        public void setCREATOR(String creator){
            this.creator = creator;
        }
         
        // getting name
        public String getTIME(){
            return this.time;
        }
      
        // setting name
        public void setTime(String time){
            this.time = time;
        }
         
        // getting Message
        public String getText(){
            return this.text;
        }
      
        // setting Message
        public void setText(String text){
            this.text = text;
        }
        
        public int getSigningKey(){
            return this.signing_key;
        }
      
        // setting Message
        public void setText(int signing_key){
            this.signing_key = signing_key;
        }
        
        public JSONObject toJSON() throws JSONException {
        	JSONObject json = new JSONObject();
        	json.put(CR_NAME, CR_name);
        	json.put(CR_PASSWORD, Password);
        	json.put(CR_CREATOR, creator);
        	json.put(CR_TIME,time);
        	json.put(CR_TEXT, text);
        	json.put(CR_USER_ID, userid);
        	json.put(CR_AUTHKEY_OLD,authkey_old);
        	json.put(CR_AUTHKEY_NEW,authkey_new);
        	json.put(CR_ENCRYPTION_KEY,enc_key);
        	json.put(CR_SIGNING_KEY, signing_key);
        	return json;
        }
     
        /* (non-Javadoc)
         * @see java.lang.Object#toString()
         */
        @Override
        public String toString() {
            return "ChatRoom Info [time=" +creator+'\t' +time + "]";
        }
         
    }