import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
/*

public class PathExample
{
   public static void write(Path p)
   {
      try ( PrintWriter pw = new PrintWriter(p.toString()) )
      {
         pw.println("1 2 3");
      }
      catch (Exception e)
      {
         System.out.println("Write: " + p);
         System.out.println(e);
      }
   }

   public static void read(Path p)
   {
      try ( Scanner sc = new Scanner(p); )
      {
	 while (sc.hasNextInt())
	 {
	    int v = sc.nextInt();
	    System.out.println(v);
         }
      }
      catch (Exception e)
      {
         System.out.println("Read: " + p);
         System.out.println(e);
      }
   }


   public static void main(String args[])
   {
      Path p = Paths.get("tmp/t1");
      PathExample.write(p);
      PathExample.read(p);
   }
}




Objective:

     Implement an external sort routine.


Description:

     Create a class called ExternalSort.

     Your class should have the following methods.  You may add additional methods as needed.

     You may use code from the textbook, but all other code must be your own.
 */

public class ExternalSort {

    public static Path extsort(Path t1, int runsize) throws IOException {
        List<Integer> numbers = new ArrayList<Integer>();
        File t2 =  new File("T2.txt");
        File t3 =  new File("T3.txt");
        File t4 = new File("T4.txt");

        Scanner sc = new Scanner(t1);

        String line = null;
        int counter = 0;
        while ((sc.hasNext() && counter < runsize)) {

            String []strNumbers = line.split(" ");
            for(String strNumber : strNumbers){
                counter++;
                numbers.add(Integer.parseInt(strNumber));
            }

            //System.out.println(line);
        } if(line != null) {
            line = mergeSort(line);
        }
        sc.close();
        System.out.println(numbers);

        return t1;

    }
//************ BOOK *******************//
 //    void mergeSort( AnyType [ ] a, AnyType [ ] tmpArray, int left, int right )
//{
//   if( left < right )
//      {
//        int center = ( left + right ) / 2;
//        mergeSort( a, tmpArray, left, center );
// mergeSort( a, tmpArray, center + 1, right );
// merge( a, tmpArray, left, center + 1, right );
//}
//}
//void merge( AnyType [ ] a, AnyType [ ] tmpArray,
//11 int leftPos, int rightPos, int rightEnd )
//12 {
//        13 int leftEnd = rightPos - 1;
//        14 int tmpPos = leftPos;
//        15 int numElements = rightEnd - leftPos + 1;
//        16
//        17 // Main loop
//        18 while( leftPos <= leftEnd && rightPos <= rightEnd )
//            19 if( a[ leftPos ].compareTo( a[ rightPos ] ) <= 0 )
//            20 tmpArray[ tmpPos++ ] = a[ leftPos++ ];
//        21 else
//        22 tmpArray[ tmpPos++ ] = a[ rightPos++ ];
//        23
//        24 while( leftPos <= leftEnd ) // Copy rest of first half
//            25 tmpArray[ tmpPos++ ] = a[ leftPos++ ];
//        26
//        27 while( rightPos <= rightEnd ) // Copy rest of right half
//            28 tmpArray[ tmpPos++ ] = a[ rightPos++ ];
//        29
//        30 // Copy tmpArray back
//        31 for( int i = 0; i < numElements; i++, rightEnd-- )
//            32 a[ rightEnd ] = tmpArray[ rightEnd ];
//        33 }

    public static void merge(Integer[] a, Integer[] l, Integer[] r,int left, int right) {
        int i = 0, j = 0, k=0;

        while(i < left && j < right) {
            if(l[i] <= r[j]) {
                a[k++] = l[i++];
            } else {
                a[k++] = r[j++];
            }
        }

        while(i < left) {
            a[k++] = l[i++];
        }

        while(j < right) {
            a[k++] = r[j++];
        }
    }
    public static void mergeSort(Integer [] arr, int runsize)
    {
        if(runsize < 2){
            return;
        }

        int mid = runsize / 2;

        Integer[] l = new Integer[mid];
        Integer[] r = new Integer[runsize - mid];

        for(int i = 0; i < mid; i++){
            l[i] = arr[i];
        }

        for(int i = mid; i < runsize; i++){
            r[i - mid] = arr[i];
        }

        mergeSort(l, mid);
        mergeSort(r, (runsize - mid));

        merge(arr, l, r, mid, (runsize - mid));
    }
    public static void main(String[] args) throws IOException {
        Scanner scan = new Scanner(System.in);
        System.out.print("Enter the run size: ");
        int runsize = scan.nextInt();

        Path t1 = Paths.get("t1.txt");

        extsort(t1, runsize);
    }

}
