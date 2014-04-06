package com.cs9033.classified;
 public  class UserData {
     
        //private variables
        int id;
        String email_id;
        String name;
        String ph_no;
         
        // Empty constructor
        public UserData(){
      
        }
        // constructor
        public UserData(int id, String email_id, String name, String ph_no){
            this.id      = id;
            this.email_id    = email_id;
            this.name    = name;
            this.ph_no = ph_no;
             
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
     
        /* (non-Javadoc)
         * @see java.lang.Object#toString()
         */
        @Override
        public String toString() {
            return "UserInfo [name=" + name + "]";
        }
         
    }