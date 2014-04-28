package Models;

import org.json.JSONException;
import org.json.JSONObject;
 public  class MessageQ {
	 
	    public static final String M_U_ID = "user id message belongs to";
		public static final String M_CR_ID = "Chat Room message belongs to"; 
		public static final String M_CH_ID ="Channel in which message need to send";
		public static final String M_ENC_MSG= "Encrypted Message";
		public static final String M_TIME = "Message Time";
     
        //private variables
        int id;
        int U_id;
        int CR_id;
        int CH_id;
        String Encypt_Message;
        String Time;
       // String ph_Num;
       // String time;
       // String preffered_Ch;
  
         
        // Empty constructor
        public MessageQ(){
      
        }
        // constructor
        public MessageQ(int id, int U_id,int CR_id, int CH_id,String Encypt_Message, String Time){
            this.id      = id;
            this.Encypt_Message= Encypt_Message;
            this.Time=Time;
            this.U_id= U_id;
            this.CR_id = CR_id;
            this.CH_id=CH_id;           
        }
      
        // getting ID
        public int getID(){
            return this.id;
        }
      
        // setting id
        public void setID(int id){
            this.id = id;
        }
        
        public int getU_id(){
            return this.U_id;
        }
        public void setU_id(int U_id){
        	 this.U_id =U_id;
        }
        
        public int getCH_id(){
            return this.CH_id;
        }
        public void setCH_id(int CH_id){
        	 this.CH_id =CH_id;
        }
        
        public int getCR_id(){
            return this.CR_id;
        }
        public void setCR_name(int CR_id){
        	 this.CR_id =CR_id;
        }
        
        public String getEncypt_Message(){
            return this.Encypt_Message;
        }
        public void setEncypt_Message(String Encypt_Message){
        	 this.Encypt_Message =Encypt_Message;
        }
        
        public String getTime(){
            return this.Time;
        }
        public void setTime(String Time){
        	 this.Time =Time;
        }
      
      
        public JSONObject toJSON() throws JSONException {
        	JSONObject json = new JSONObject();
        	json.put(M_U_ID, U_id);
        	json.put(M_CR_ID, CR_id);
        	json.put(M_CH_ID, CH_id);
        	json.put(M_ENC_MSG,Encypt_Message);
        	json.put(M_TIME, Time);
        	return json;
        }
       
        @Override
        public String toString() {
            return "MessageQ Info [time=" +Time+'\t' +Encypt_Message + "]";
        }
         
    }