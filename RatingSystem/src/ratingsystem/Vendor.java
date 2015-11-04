//Name: S. A. C. Fernando
//Index No:  110159E
package ratingsystem;

import java.util.Date;

public class Vendor {
    public Vendor NextVendor;                               //next Vendor object to handle collission
    public String Name;                                     //name of the Vendor
    private String[] Books = new String[500];               //list of book of the Vendor
    private String[] users = new String[500];               //list of users of the book
    private Date[] RecentDate = new Date[5];                //Store the time of the most recent ratings of the Vendor
    private int[] RecentRate = new int[5];                  //Store most recent ratings of the Vendor
    private int[] ratesOfUsers = new int[500];              //list of RatesOfUsers of the Vendor
    private int[] numberOfTimes = new int[500];             //list of NumberOfTimes of the Vendor
    private float aggrigateRate;                            //aggrigateRate of the Vendor
    int Topratingcount=0;
    int ratingcount=0;
    int BooksCount=0;
    int temp;
    
    
    public void InsertData(Record R){                       //insert new Vendor
        Name=R.Vendorname;
        Books[0]=R.Bookname;
        users[0]=R.Username;
        RecentDate[0]=R.Timestamp;
        RecentRate[0]=R.VRating;
        NextVendor=null;
        Topratingcount++;
        users[0]=R.Username;
        ratesOfUsers[0]=R.VRating;
        numberOfTimes[0]++;
        BooksCount++;
        ratingcount++;
    }
    
    public void InsertUser(Record R){                       //inset new Vendor when colission occurs
        temp = 0;
        while(!R.Username.equals(users[temp]) && temp<users.length){
            if(users[temp]==null){
                users[temp] = R.Username;
                break;
            }
            temp++;
        }

        ratesOfUsers[temp]+=R.VRating;
        numberOfTimes[temp]++;
        temp = 0;
        ratingcount++;
        if(Topratingcount<5){
        RecentDate[Topratingcount]=R.Timestamp;
        RecentRate[Topratingcount]=R.VRating;
        Topratingcount++;
        
        }
    }
    
    public void InsertBook(Record R){                       //insert Book
        temp = 0;
        while(!R.Bookname.equals(Books[temp]) && temp<Books.length){
            if(Books[temp]==null){
                Books[temp] = R.Bookname;
                BooksCount++;
                break;
            }
            temp++;
        }
    }
    
    public void SetAggrigateRate(float aggr){               //set aggrigate rates
        aggrigateRate=aggr;
    }
    
    public void PrintAggrigateRate(){                       //Print Aggrigate Rate
        System.out.println("\n\nAggrigate Ratig of the "+Name+" : "+aggrigateRate);
    }
    
    public int GetBooksCount(){                             
        return BooksCount;
    }
    
    public void PrintBooks(){
        System.out.println("\nTop Rated Books,\n");
        System.out.println(Books[0]);
        for(int i=1;i<BooksCount;i++)
            if(!Books[i].equals(Books[i-1]))
                System.out.println(Books[i]);
        System.out.println("\n");
    }
    
    public int[] GetUserRates(){
        return ratesOfUsers;
    }
    
    public float GetAggrigateRate(){                        //get aggrigate rates
        return aggrigateRate;
    }
    
    public int[] GetRatedTimes(){
        return numberOfTimes;
    }
    
    public String[] GetUsers(){
        return users;
    }
    
    public String[] GetBooks(){
        return Books;
    }
    
    public void SetBooks(String[] temp){
        Books=temp;
        Books[0]=temp[0];
        int temp1=1;
        int temp2=BooksCount;
        BooksCount=1;
        for(int i=1;i<temp2;i++)
            if(!temp[i].equals(temp[i-1])){
                Books[temp1]=temp[i];
                temp1++;
                BooksCount++;
            }
    }
    
    public int GetNoOfRates(){
        return ratingcount;
    }
    
    public String GetName(){
        return Name;
    }
    
    public void getVenders(){
        System.out.println("*");
        for(int i=0;i<Books.length;i++){
            System.out.println(Books[i]);
        }
    }
    
    public void getUserRatings(){
        System.out.println("*");
        for(int i=0;i<ratingcount;i++){
                System.out.println("Name : "+users[i]+" Rate : "+ratesOfUsers[i]+"  No. Of times: "+numberOfTimes[i]);
        }
    }
    
    public void getRecentRatings(){
        System.out.println("\n5 most Recent Ratings of the V1endor"+Name);
        System.out.println("Date                            Rate");
        for(int i=0;i<RecentDate.length;i++){
            System.out.println(RecentDate[i]+"      "+RecentRate[i]);
        }
    }
    
}
