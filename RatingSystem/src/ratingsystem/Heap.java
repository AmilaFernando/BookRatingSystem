//Name: S. A. C. Fernando
//Index No:  110159E
package ratingsystem;

public class Heap {
    Record[] heap;          //Array of records
    int heaplength;         //length of the heap
    int size;

    Heap(int SIZE){
        heap=new Record[SIZE];      //create the heap of length SIZE
        heaplength=0;               //set length to 0
    }
    
    void add(Record temp){          //add new record to the heap
        heap[heaplength]=temp;
        heaplength++;
    }

    void printrecord(){
        for(int i=0;i<size;i++)
            System.out.println(heap[i].Timestamp+"  "+heap[i].Bookname+"    "+heap[i].BRating+" "+heap[i].Vendorname
                    +"  "+heap[i].VRating+" "+heap[i].Username);
    }
    
    private int Parent(int index) {     //get the parent element 
        return index/2;
    }

    private int Left(int index) {                //get the Left element
        return index*2 + 1;
    }

    private int Right(int index) {              //get the Right element
        return index*2 + 2;
    }

    private void Heapify(int i) {               //heapify the heap
        int l = Left(i);
        int r = Right(i);
        int largest = i;

        if(l < heaplength && compare(heap[l], heap[i]) > 0)
            largest = l;
        if(r < heaplength && compare(heap[r], heap[largest]) > 0)
            largest = r;

        if(largest != i) {
            Record temp = heap[i];
            heap[i] = heap[largest];
            heap[largest] = temp;
            Heapify(largest);
        }
    }

    public void BuildHeap() {               //Build the heap
        size=heaplength;
        heaplength--;
        for(int i = (heaplength - 1)/2; i >= 0; i--)
           Heapify(i);
        
    }

    public void HeapSort() {                //heap Sort algorithm
        BuildHeap();
        size=heaplength;
        Record t;
        for(int x = heaplength - 1; x > 0; x--) {
            t = heap[0];
            heap[0] = heap[heaplength - 1];
            heap[heaplength - 1] = t;
            heaplength--;
            Heapify(0);
        }
    }
    
    public int compare(Record r1,Record r2){            //compare two TimeStamps
        if(r1.Timestamp.after(r2.Timestamp)){
            return -1;
        }
        return 1;
    }

}
