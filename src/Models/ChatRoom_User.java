package Models;

import org.json.JSONException;
import org.json.JSONObject;

public class ChatRoom_User {

	private static int id;
	private static int crid;
	private static int usrid;
	private static int Cur_Mak;
	private static int Cur_Ekey;
	
	public static final String CRU_CRID="current user chat room id";
	public static final String CRU_CUR_MAK = "current user current MAK";
	public static final String CRU_EKEY = "current user current EKEY";
	public static final String CRU_USER_ID = "User Id";
	
	 public ChatRoom_User(){
	      
     }
     // constructor
     public ChatRoom_User(int id,int crid, int usrid, int Cur_Mak, int Cur_Ekey){
         this.id      = id;
         this.crid=crid;
         this.Cur_Mak    = Cur_Mak;
         this.Cur_Ekey    = Cur_Ekey;
         this.usrid = usrid;
         
        // this.Chlist=Chlist;
          
     }

	public static int getId() {
		return id;
	}
	public void setID(int id){
        this.id = id;
    }
	public int getCur_Mak(){
        return this.Cur_Mak;
    }

    public void setCur_Mak(int Cur_Mak){
        this.Cur_Mak = Cur_Mak;
    }
  
   
    public void setCur_Ekey(int Cur_Ekey){
        this.Cur_Ekey = Cur_Ekey;
    }
    
    public int getCur_Ekey(){
        return this.Cur_Ekey;
    }
    
    public void setusrid(int usrid){
        this.usrid = usrid;
    }
    
    public int getusrid(){
        return this.usrid;
    }  
	
    public void setcrid(int crid){
        this.crid = crid;
    }
    
    public int getcrid(){
        return this.crid;
    }  
	
	public JSONObject toJSON() throws JSONException {
    	JSONObject json = new JSONObject();
    	json.put(CRU_CUR_MAK, Cur_Mak);
    	json.put(CRU_EKEY, Cur_Ekey);
    	json.put(CRU_USER_ID, usrid);
    	json.put(CRU_CRID, crid);
    	
    	return json;
    }
}

