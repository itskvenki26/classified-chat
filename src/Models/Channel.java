package Models;

import org.json.JSONException;
import org.json.JSONObject;
 public  class Channel {
	    public static final String CH_U_ID= "user id of the channel";
		public static final String CH_TYPE ="Channel type";
		public static final String CH_PH_NUM ="phone number";
		public static final String CH_TIME ="Channel establish time";
		public static final String CH_PREF ="Preffered Channel";
        //private variables
        int id;
       // String CR_name;
        //String Password;
        int U_id;
        //String creator;
        String C_Type;
        String ph_Num;
        String time;
        String preffered_Ch;
  
         
        // Empty constructor
        public Channel(){
      
        }
        // constructor
        public Channel(int id,int U_id, String C_Type, String ph_Num, String time, String preffered_Ch){
            this.id      = id;
            this.U_id = U_id;
            
            this.C_Type=C_Type;
            this.ph_Num    = ph_Num;
            this.time    = time;
            this.preffered_Ch = preffered_Ch;            
             
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
        
        public String getC_Type(){
            return this.C_Type;
        }
        public void setC_Type(String C_Type){
        	 this.C_Type =C_Type;
        }
        
        public String getph_Num(){
            return this.ph_Num;
        }
        public void setph_Num(String ph_Num){
        	 this.ph_Num =ph_Num;
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
        public String getpreffered_Ch(){
            return this.preffered_Ch;
        }
      
        // setting Message
        public void setpreffered_Ch(String preffered_Ch){
            this.preffered_Ch = preffered_Ch;
        }
        
        public JSONObject toJSON() throws JSONException {
        	JSONObject json = new JSONObject();
        	json.put(CH_U_ID, U_id);
        	json.put(CH_TYPE, C_Type);
        	json.put(CH_PH_NUM, ph_Num);
        	json.put(CH_TIME,time);
        	json.put(CH_PREF, preffered_Ch);
        	return json;
        }
        /* (non-Javadoc)
         * @see java.lang.Object#toString()
         */
        @Override
        public String toString() {
            return "Channel Info [user=" +U_id+'\t' +time + "]";
        }
       
         
    }