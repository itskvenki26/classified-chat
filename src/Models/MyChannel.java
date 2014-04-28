package Models;

import org.json.JSONException;
import org.json.JSONObject;
 public  class MyChannel {
     
        //private variables
        int id;
       // String CR_name;
        //String Password;
        String Host;
        //String creator;
        String Service;
        String username;
        String pwd;
       // String preffered_Ch;
  
        public static final String MCH_U_ID="My channel user id";
    	public static final String MCH_HOST="My channel Host";
    	public static final String MCH_SERVICE="My channel service";
    	public static final String MCH_USR_NAME="My channel user name";
    	public static final String MCH_PWD="My channel password";
         
        // Empty constructor
        public MyChannel(){
      
        }
        // constructor
        public MyChannel(int id,String Host, String Service, String username, String pwd){
            this.id      = id;
            this.Host = Host;
            
            this.Service=Service;
            this.username    = username;
            this.pwd    = pwd;
                      
             
        }
      
        // getting ID
        public int getID(){
            return this.id;
        }
      
        // setting id
        public void setID(int id){
            this.id = id;
        }
        
        public String getHost(){
            return this.Host;
        }
        public void setHost(String Host){
        	 this.Host =Host;
        }
        
        public String getService(){
            return this.Service;
        }
        public void setService(String Service){
        	 this.Service =Service;
        }
        
        public String getusername(){
            return this.username;
        }
        public void setusername(String username){
        	 this.username =username;
        }     
         
        // getting name
        public String getpwd(){
            return this.pwd;
        }
      
        // setting name
        public void setpwd(String pwd){
            this.pwd = pwd;
        }
         
        public JSONObject toJSON() throws JSONException {
        	JSONObject json = new JSONObject();
        	//json.put(MCH_U_ID, U_id);
        	json.put(MCH_HOST, Host);
        	json.put(MCH_SERVICE, Service);
        	json.put(MCH_USR_NAME,username);
        	json.put(MCH_PWD, pwd);
        	return json;
        }
     
        /* (non-Javadoc)
         * @see java.lang.Object#toString()
         */
        @Override
        public String toString() {
            return "Channel Info [user=" +username+'\t' +Service + "]";
        }
         
    }