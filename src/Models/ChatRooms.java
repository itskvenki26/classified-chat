package Models;

import org.json.JSONException;
import org.json.JSONObject;
 public  class ChatRooms {
     
        //private variables
        int id;        
        long current_mac;
        long current_e;
        long old_mac;       
        String CR_name;  
        String time;
        String description;
        
  
        public static final String CR_NAME="Chat_Room_Name";    	
    	public static final String CR_TIME="Chat_Room_time";
    	public static final String CR_DESCRIPTION="Chat_Room_text";    	
    	public static final String CR_CURRENT_MAC="Chat Room Current MAC";
    	public static final String CR_OLD_MAC="Chat Room Old MAC"; 
    	public static final String CR_CURRENT_E="Chat Room Current ENC Key";
    	
        // Empty constructor
        public ChatRooms(){
      
        }
        // constructor
        public ChatRooms(int id, long current_mac, long current_e,long old_mac,String CR_name, String time, String description){
            this.id      = id;
            this.current_mac=current_mac;
            this.current_e=current_e;
            this.old_mac=old_mac;
            this.CR_name=CR_name;
            this.time=time;
            this.description=description;
             
        }
      
        // getting ID
        public int getID(){
            return this.id;
        }
      
        // setting id
        public void setID(int id){
            this.id = id;
        }
        public long getcurrent_mac(){
            return this.current_mac;
        }
   
        public void setcurrent_mac(long current_mac){
            this.current_mac = current_mac;
        }
      
       
        public void setold_mac(long old_mac){
            this.old_mac = old_mac;
        }
        
        public long getold_mac(){
            return this.old_mac;
        }
      
        
        
        public void setcurrent_e(long current_e){
            this.current_e = current_e;
        }
        
        public long getcurrent_e(){
            return this.current_e;
        }   
       
        
        public String getCR_name(){
            return this.CR_name;
        }
        public void setCR_name(String CR_name){
        	 this.CR_name =CR_name;
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
        public String getdescription(){
            return this.description;
        }
      
        // setting Message
        public void setdescription(String description){
            this.description = description;
        }
        
     
        
        public JSONObject toJSON() throws JSONException {
        	JSONObject json = new JSONObject();
        	json.put(CR_NAME, CR_name);        	
        	json.put(CR_TIME,time);
        	json.put(CR_DESCRIPTION, description);        	
        	json.put(CR_CURRENT_E,current_e);
        	json.put(CR_CURRENT_MAC,current_mac);
        	json.put(CR_OLD_MAC,old_mac);
        	
        	return json;
        }
     
        /* (non-Javadoc)
         * @see java.lang.Object#toString()
         */
        @Override
        public String toString() {
            return "ChatRoom Info [time=" +CR_name+'\t' +time + "]";
        }
         
    }