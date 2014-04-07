package Models;
 public  class Posts {
     
        //private variables
        int id;
        String creator;
        String time;
        String message;
         
        // Empty constructor
        public Posts(){
      
        }
        // constructor
        public Posts(int id, String creator, String time, String message){
            this.id      = id;
            this.creator    = creator;
            this.time    = time;
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
        public String getMessage(){
            return this.message;
        }
      
        // setting Message
        public void setMessage(String message){
            this.message = message;
        }
     
        /* (non-Javadoc)
         * @see java.lang.Object#toString()
         */
        @Override
        public String toString() {
            return "PostInfo [time=" +creator+'\t' +time + "]";
        }
         
    }