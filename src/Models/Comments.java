package Models;

import org.json.JSONException;
import org.json.JSONObject;
 public  class Comments {
     
        //private variables
        int id;
        long P_id;
        long CR_id;
       // String creator;
        //String time;
        String message;
       // int post_id;
        
        public static final String C_P_ID= "post id comment belongs to"; 
    	public static final String C_CR_ID= "chat room comment belongs to";
    	//public static final String C_CREATOR = "comment_creator";
    	//public static final String C_TIME = "comment_time";
    	public static final String C_MSG = "comment_message";
         
        // Empty constructor
        public Comments(){
      
        }
        // constructor
        public Comments(int id, long P_id,long CR_id, String message){
            this.id      = id;
            this.P_id = P_id;
            this.CR_id = CR_id;
           // this.creator    = creator;
           // this.time    = time;
            this.message = message;
           // this.post_id= post_id;
             
        }
      
        // getting ID
        public int getID(){
            return this.id;
        }
      
        // setting id
        public void setID(int id){
            this.id = id;
        }
        
     // getting imei
        public long getP_id(){
            return this.getP_id();
        }
      
        // setting imei
        public void setP_id(long P_id){
            this.P_id = P_id;
        }
        
        public long getCR_id(){
            return this.getCR_id();
        }
      
        // setting imei
        public void setCR_id(long crID){
            this.CR_id = crID;
        }
      
        // getting imei
//        public String getCREATOR(){
//            return this.creator;
//        }
//      
//        // setting imei
//        public void setCREATOR(String creator){
//            this.creator = creator;
//        }
//         
//        // getting name
//        public String getTIME(){
//            return this.time;
//        }
//      
//        // setting name
//        public void setTime(String time){
//            this.time = time;
//        }
         
        // getting Message
        public String getMessage(){
            return this.message;
        }
      
        // setting Message
        public void setMessage(String message){
            this.message = message;
        }
        
        public JSONObject toJSON() throws JSONException {
        	JSONObject json = new JSONObject();
        	json.put(C_P_ID, P_id);
        	json.put(C_CR_ID, CR_id);
        	//json.put(C_CREATOR, creator);
        	//json.put(C_TIME,time);
        	json.put(C_MSG, message);
        	return json;
        }
     
        /* (non-Javadoc)
         * @see java.lang.Object#toString()
         */
        @Override
        public String toString() {
            return "CommentInfo [time=" +message+ "]";
        }
         
    }