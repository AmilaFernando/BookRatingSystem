//Name: S. A. C. Fernando
//Index No:  110159E

package ratingsystem;


import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Date;
import java.util.Scanner;

public class RatingSystem {


    public static final int SIZE = 2000;        //Get the number of records as 2000.
    public Heap InputHeap;                      //records are edited and then store 
    public int size;
    private Book[] BookHashTable = new Book[1000];      //book hash table
    private Vendor[] VendorHashTable = new Vendor[1000];    //vendor hash table
    

    public static void main(String[] args) throws IOException {
        RatingSystem project=new RatingSystem();
        project.start();                                      
        project.Interface();
       
    }
    
    public void Interface(){        //Interact with user
        
        int check=0;
        String temp;
        Scanner input=new Scanner(System.in);       //to get input
        
        while(check!=3){                        //run till user wants to exit
            
            System.out.println("Enter 1 for Book Search.");
            System.out.println("Enter 2 for Vendor Search.");
            System.out.println("Enter 3 for Exit.");
            System.out.print("Choise : ");
            check=input.nextInt();
            
            switch(check){
                case 1: {                                           //search books
                    System.out.print("Enter the Book name : ");
                    temp=input.next();
                            
                    if(BookHashTable[calHash(temp)]!=null){
                        BookSearch(temp);
                    }
                    else{
                        System.out.println("Incorrect Book Name!");
                    }
                    break;
                }
                case 2: {                                           //search vendor
                    System.out.print("Enter the Vendor name : ");
                    temp=input.next();
                    if(VendorHashTable[calHash(temp)]!=null){
                        VendorSearch(temp);
                    }
                    else{
                        System.out.println("\nIncorrect Vendor Name!");
                    }
                    break;
                }
                case 3:                                            //esit
                    return;
                default : break;
            }
        }
    } 
    
    
    
    public void BookSearch(String name){                        //get the book name and search that book
        int val=calHash(name);                                  //calculate hash value of the boo name
        
        BookHashTable[val].getRecentRatings();
        BookHashTable[val].PrintAggrigateRate();                //print aggrigate rating of the book
        
        String temp[]=BookHashTable[val].GetVendors();      //get vendor list of the book
        int temp1=BookHashTable[val].GetVendorCount();      //get the number of vendors, that book has
        
        System.out.println("Top Rated Vendors....\n");
        for (int i = 0; i < temp1; i++) {                   //printing top rated vendors
            if(VendorHashTable[calHash(temp[i])]!=null)
            System.out.println(temp[i]+"     Rating : "+VendorHashTable[calHash(temp[i])].GetAggrigateRate());
        }
        System.out.println("\n\n");
        
    }
    
    public void VendorSearch(String name){                      //get the vendor name and search that vendor
        
        int val=calHash(name);                                  //calculate hash value of the vendor name
        
        VendorHashTable[val].getRecentRatings();
        VendorHashTable[val].PrintAggrigateRate();              //print aggrigate rating of the vendor
        
        String temp[]=VendorHashTable[val].GetBooks();      //get book list of the book vendor
        int temp1=VendorHashTable[val].GetBooksCount();     //get the number of books, that vendor has
        
        System.out.println("\nTop Rated Books....\n");
        for (int i = 0; i < temp1; i++) {                   //printing top rated books
            if(BookHashTable[calHash(temp[i])]!=null)
            System.out.println(temp[i]+"     Rating : "+BookHashTable[calHash(temp[i])].GetAggrigateRate());
        }
        System.out.println("\n\n");
    }
    
    public String[] VerdorsSort(String[] names,int size){       //sort the vendor list according to decrease the aggrigate rate
        String t;
        for(int i = 0; i < size; i++){
            for(int j = 1; j < (size-i); j++){
                if(VendorHashTable[calHash(names[j-1])].GetAggrigateRate() <= VendorHashTable[calHash(names[j])].GetAggrigateRate()){
                    t = names[j-1];
                    names[j-1]=names[j];
                    names[j]=t;
                }
            }
        }
        return names;
    }
    
    public String[] BooksSort(String[] names,int size){         //sort the vendor list according to decrease the aggrigate rate
        String t;
        for(int i = 0; i < size; i++){
            for(int j = 1; j < (size-i); j++){
                if(BookHashTable[calHash(names[j-1])].GetAggrigateRate() <= BookHashTable[calHash(names[j])].GetAggrigateRate()){
                    t = names[j-1];
                    names[j-1]=names[j];
                    names[j]=t;
                }
            }
        }
        return names;
    }
    
    
    public void start() throws IOException {                //execution of the program is starting here
        InputHeap=new Heap(SIZE);                           //Create new Heap
        ReadFile();                                         //Read the file
        InputHeap.BuildHeap();                              //build heap
        
        InputHeap.HeapSort();                               //Sort the heap by Time Stamp
        EnterToHashTables();
        
        for (int i = 0; i < BookHashTable.length; i++) {    //calculate aggrigate rates of the books
            if(BookHashTable[i]!=null){
                CalAggrigrateBookRate(BookHashTable[i]);
            }
        }

        for (int i = 0; i < VendorHashTable.length; i++) {   //calculate aggrigate rates of the vendors
            if(VendorHashTable[i]!=null)
            CalAggrigratevendorRate(VendorHashTable[i]);
        }
        
        for (int i = 0; i < BookHashTable.length; i++) {    //Sort the vendor arrays in the books by aggrigate rates
            if(BookHashTable[i]!=null){
                String temp[]=VerdorsSort(BookHashTable[i].GetVendors(),BookHashTable[i].GetVendorCount());
                BookHashTable[i].SetVendors(temp);
            }
        }
        
        for (int i = 0; i < VendorHashTable.length; i++) {  //Sort the books arrays in the vendor by aggrigate rates
            if(VendorHashTable[i]!=null){
                String temp[]=BooksSort(VendorHashTable[i].GetBooks(),VendorHashTable[i].GetBooksCount());
                VendorHashTable[i].SetBooks(temp);
            }
        }
        
        
    }
    
    void ReadFile() throws IOException{     //get inputs from file
    
        String s;
        int year, month, date, hour, min;
        Record R;
        FileInputStream input = new FileInputStream("data.txt");
        InputStreamReader I=new InputStreamReader(input);
        BufferedReader Read = new BufferedReader(I);
        
        while((s = Read.readLine()) != null) {
            String[] Tokens = s.split(" ");
            String[] dateStr = Tokens[0].split("[T:-]");

            year = Integer.parseInt(dateStr[0]);        //get the year from record
            month = Integer.parseInt(dateStr[1]);       //get the month from record
            date = Integer.parseInt(dateStr[2]);        //get the date from record
            hour = Integer.parseInt(dateStr[3]);        //get the hour from record
            min = Integer.parseInt(dateStr[4]);         //get the min from record

            R = new Record();
            R.Timestamp = new Date(year - 1900, month - 1, date, hour, min);

            R.Username = Tokens[1];             //Set the values of the book object
            R.Bookname = Tokens[2];
            R.Vendorname = Tokens[3];
            R.VRating = Integer.parseInt(Tokens[4]);
            R.BRating = Integer.parseInt(Tokens[5]);

            InputHeap.add(R);
        }
    
    }
    
    public int calHash(String name){                    // calculating the hash value of a given name
		
        int temp = 0,val = 0;               
	int hashval;
        while(temp<name.length()){
                val+= ((int)name.charAt(temp)*temp);    // the hash function is to multiply the ASCII value of the each letter with the position of the letter
                temp++;
            }
            hashval = val%1000;
            return hashval;
    }
    
    public void CalAggrigrateBookRate(Book book){
        float x=0,y=0;
        int[] SIGMAr = book.GetUserRates();			// get the list of the sum of the rates of the users
        int[] K = book.GetRatedTimes();				// get the list of the number of times that one user has rate the particulat book 
        String[] t = book.GetUsers();
        int count=book.Ratingcount;
        
        double weight[]=new double[count];
        
        for(int i=0;i<count;i++){
            if(K[i]!=0)
            weight[i]=(2-1/K[i]);
        }
        
        for(int i=0;i<count;i++){
            x+=weight[i]*SIGMAr[i];
        }
        
        for(int i=0;i<count;i++){
            y+=weight[i]*K[i];
        }
        book.SetAggrigateRate(x/y);
    }
    
    public void CalAggrigratevendorRate(Vendor vendor){
        float x=0,y=0;
        int[] SIGMAr = vendor.GetUserRates();			// get the list of the sum of the rates of the users
        int[] K = vendor.GetRatedTimes();				// get the list of the number of times that one user has rate the particulat book 
        String[] t = vendor.GetUsers();
        int count=vendor.ratingcount;
        
        double weight[]=new double[count];
        
        for(int i=0;i<count;i++){
            if(K[i]!=0)
            weight[i]= (2-1/K[i]);
        }
        
        for(int i=0;i<count;i++){
            x+=weight[i]*SIGMAr[i];
        }
        
        for(int i=0;i<count;i++){
            y+=weight[i]*K[i];
        }
        vendor.SetAggrigateRate(x/y);
        	
    }
    
    public void EnterToHashTables(){               // this method split the line send by the readFile method and enter them to the hash tables

        for(int i=0;i<InputHeap.size;i++){
            InsertBook(InputHeap.heap[i]);  // enter the book in to the hash table
            InsertVendor(InputHeap.heap[i]);	// enter the vendor in to the hash table
        }
        System.out.println("Data entered to the Hash Table");
                                                                            // enter the user in to the hash table
    }
    
    public void InsertBook(Record R){
        int hashval=calHash(R.Bookname);
        Book temp,temp1;
        if(BookHashTable[hashval]==null){					// if the corresponding position given by the hash finction is null
            BookHashTable[hashval] = new Book();			// make a new book object and pass the name in to it
            BookHashTable[hashval].InsertData(R);
            BookHashTable[hashval].NextBook = null;
	}
        
        else{
            temp = BookHashTable[hashval];				// if there is a book object in the location given by the hash value, 
            temp1 = temp.NextBook;
            while(temp!=null){
                if(temp.GetName().equals(R.Bookname)){    // check the book object has present before, if so, update the new information.
                        temp.InsertUser(R);
                        temp.InsertVendor(R);
                        break;
                }
                temp1 = temp;
                temp = temp.NextBook;
            }
            
            if (temp == null){             // if the book hasn't precented before, we make a new book object and link it to the previous one
                temp1.NextBook = new Book();		// then we insert the relevant details in to the book object
                temp1 = temp1.NextBook;
                BookHashTable[hashval] = new Book();			// make a new book object and pass the name in to it
                BookHashTable[hashval].InsertData(R);
                BookHashTable[hashval].NextBook = null;
            }
        }        
    }
    
    public void InsertVendor(Record R){
        int hashval=calHash(R.Vendorname);
        Vendor temp,temp1;
        if(VendorHashTable[hashval]==null){					// if the corresponding position given by the hash finction is null
            VendorHashTable[hashval] = new Vendor();			// make a new book object and pass the name in to it
            VendorHashTable[hashval].InsertData(R);
            VendorHashTable[hashval].NextVendor = null;
	}
        
        else{
            temp = VendorHashTable[hashval];				// if there is a book object in the location given by the hash value, 
            temp1 = temp.NextVendor;
            while(temp!=null){
                if(temp.GetName().equals(R.Vendorname)){    // check the book object has present before, if so, update the new information.
                        temp.InsertUser(R);
                        temp.InsertBook(R);
                        break;
                }
                temp1 = temp;
                temp = temp.NextVendor;
            }
            
            if (temp == null){             // if the book hasn't precented before, we make a new book object and link it to the previous one
                temp1.NextVendor = new Vendor();		// then we insert the relevant details in to the book object
                temp1 = temp1.NextVendor;
                VendorHashTable[hashval] = new Vendor();			// make a new book object and pass the name in to it
                VendorHashTable[hashval].InsertData(R);
                VendorHashTable[hashval].NextVendor = null;
            }
        }
    }
    
}
