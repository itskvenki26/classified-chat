package Models;
 public  class Comments {
     
        //private variables
        int id;
        String creator;
        String time;
        String message;
        String post_id;
         
        // Empty constructor
        public Comments(){
      
        }
        // constructor
        public Comments(int id, String creator, String time, String message, String post_id){
            this.id      = id;
            this.creator    = creator;
            this.time    = time;
            this.message = message;
            this.post_id= post_id;
             
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
        
        public String getPOST_ID(){
            return this.post_id;
        }
      
        // setting Message
        public void setPOST_ID(String post_id){
            this.post_id = post_id;
        }
     
        /* (non-Javadoc)
         * @see java.lang.Object#toString()
         */
        @Override
        public String toString() {
            return "CommentInfo [time=" +creator+'\t' +time +'\t'+ post_id+ "]";
        }
         
    }