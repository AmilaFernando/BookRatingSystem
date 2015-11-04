//Name: S. A. C. Fernando
//Index No:  110159E



import java.util.Date;

public class Book {
    public Book NextBook;                       //next book object to handle collission
    public String Name;                         //name of the book
    private String[] Vendors = new String[500]; //list of vendors of the book
    private String[] users = new String[500];   //list of users of the book
    private Date[] RecentDate = new Date[5];    //Store the time of the most recent ratings of the book
    private int[] RecentRate = new int[5];      //Store most recent ratings of the book
    private int[] RatesOfUsers = new int[500];  //list of RatesOfUsers of the book
    private int[] NumberOfTimes = new int[500]; //list of NumberOfTimes of the book
    private float aggrigateRate;                //aggrigateRate of the book
    int Topratingcount=0;                       
    int Ratingcount=0;
    int vendorcount=0;
    int temp;
    
    
    public void InsertData(Record R){           //insert new book
        Name=R.Bookname;
        Vendors[0]=R.Vendorname;
        users[0]=R.Username;
        RecentDate[0]=R.Timestamp;
        RecentRate[0]=R.BRating;
        NextBook=null;
        Topratingcount++;
        users[0]=R.Username;
        RatesOfUsers[0]=R.BRating;
        NumberOfTimes[0]++;
        Ratingcount++;
        vendorcount++;
    }
    
    public void InsertUser(Record R){           //inset new book when colission occurs
        temp = 0;
        while(temp<users.length &&!R.Username.equals(users[temp]) ){
            if(users[temp]==null){
                users[temp] = R.Username;
                break;
            }
            temp++;
        }

        RatesOfUsers[temp]+=R.BRating;
        NumberOfTimes[temp]++;
        temp = 0;
        Ratingcount++;
        if(Topratingcount<5){
        RecentDate[Topratingcount]=R.Timestamp;
        RecentRate[Topratingcount]=R.BRating;
        Topratingcount++;
        
        }
    }
    
    public void InsertVendor(Record R){             //insert vendors
        temp = 0;
        while(!R.Vendorname.equals(Vendors[temp]) && temp<Vendors.length){
            if(Vendors[temp]==null){
                Vendors[temp] = R.Vendorname;
                vendorcount++;
                break;
            }
            temp++;
        }
    }
    public void SetAggrigateRate(float aggr){       //set aggrigate rates
        aggrigateRate=aggr;
    }
    
    public void PrintAggrigateRate(){               //Print Aggrigate Rate
        System.out.println("\nOverall Aggrigate Rating of the Book "+Name+" : "+aggrigateRate);
    }
    
    public float GetAggrigateRate(){                //get aggrigate rates
        return aggrigateRate;
    }
    
    public int[] GetUserRates(){                        //Get User Rates
        return RatesOfUsers;
    }
    
    public String[] GetVendors(){                       //Get Vendors
        return Vendors;
    }
    
    public void SetVendors(String[] temp){              //Set Vendors
        Vendors[0]=temp[0];
        int temp1=1;
        int temp2=vendorcount;
        vendorcount=1;
        for(int i=1;i<temp2;i++)
            if(!temp[i].equals(temp[i-1])){
                Vendors[temp1]=temp[i];
                temp1++;
                vendorcount++;
            }
    }
    
    public void PrintVendors(){                             //Print Vendors
        System.out.println("\nTop Rated Vendors,\n");
        System.out.println(Vendors[0]);
        for(int i=1;i<vendorcount;i++)
            if(!Vendors[i].equals(Vendors[i-1]))
                System.out.println(Vendors[i]);
        System.out.println("\n");
    }
    
    public int GetVendorCount(){
        return vendorcount;
    }
    
    public int[] GetRatedTimes(){
        return NumberOfTimes;
    }
    
    public String[] GetUsers(){
        return users;
    }    
    
    public int GetNoOfRates(){
        return Ratingcount;
    }
    
    public String GetName(){
        return Name;
    }
    
    public void getVenders(){
        for(int i=0;i<Vendors.length;i++){
            System.out.println(Vendors[i]);
        }
    }
    
    public void getUserRatings(){                       //print User Ratings
        for(int i=0;i<Ratingcount;i++){
                System.out.println("Name : "+users[i]+" Rate : "+RatesOfUsers[i]+"  No. Of times: "+NumberOfTimes[i]);
        }
    }
    
    public void getRecentRatings(){                     //print Recent Ratings
        System.out.println("\n5 most Recent Ratings of the Book "+Name);
        System.out.println("Date                            Rate");
        for(int i=0;i<RecentDate.length;i++){
            System.out.println(RecentDate[i]+"      "+RecentRate[i]);
        }
    }
}
