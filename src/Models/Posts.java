package Models;

import org.json.JSONException;
import org.json.JSONObject;
 public  class Posts {
     
	   // public static final String P_CREATOR = "creator";
		public static final String P_CR_ID ="chat room it belongs to";
		public static final String P_TITLE= "title";
		//public static final String P_TIME = "time";
		public static final String P_MSG = "message";
        //private variables
        int id;
        int CR_id;
        //String creator;
        //String time;
        String message;
        String title;
         
        // Empty constructor
        public Posts(){
      
        }
        // constructor
        public Posts(int id, int CR_id, String title, String message){
            this.id      = id;
            this.CR_id   = CR_id;  
            this.title   = title;            
            this.message = message;            
             
        }
      
        // getting ID
        public int getID(){
            return this.id;
        }
      
        // setting id
        public void setID(int id){
            this.id = id;
        }
        
        public int getCR_id(){
            return this.CR_id;
        }
        public void setCR_id(int CR_id){
        	 this.CR_id =CR_id;
        }
        
        public String getTitle(){
            return this.title;
        }
        public void setTitle(String title){
        	 this.title =title;
        }
      
      
//        // getting imei
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
        	//json.put(P_CREATOR, creator);
        	json.put(P_CR_ID, CR_id);
        	json.put(P_TITLE, title);
        	//json.put(P_TIME,time);
        	json.put(P_MSG, message);
        	return json;
        }
     
        /* (non-Javadoc)
         * @see java.lang.Object#toString()
         */
        @Override
        public String toString() {
            return "PostInfo [time=" +title + "]";
        }
         
    }