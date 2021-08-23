import java.util.Scanner;
import java.io.*;
import java.net.*;

static void LRU(String ref[] , int size){
    System.out.println("LRU Page Replacement policy + \n");
    ArrayList<String>buffer = new ArrayList<String>(size);
    int count = 0;
    int i = 0;
    double hit = 0;
    LinkedList<String>recent = new LinkedList<String>();
    for(; i < ref.length ; i++ ){
        if(count == size) break;
        System.out.print( "\n" + "Incoming --> " + ref[i]);
        if(buffer.contains(ref[i])){
            recent.remove(ref[i]);
            recent.add(ref[i]);
            System.out.print(" Hit!!!   " + buffer.toString() + "\n");
            hit++;
        }else{
            buffer.add(ref[i]);
            System.out.print(" Fault!!! " + buffer.toString() + "\n");
            recent.remove(ref[i]);
            recent.add(ref[i]);
            count++;
        }
    }
    for(int j = i ; j < ref.length ; j++){
        System.out.print( "\n" + "Incoming --> " + ref[j]);
        if(buffer.contains(ref[j])){
            recent.remove(ref[j]);
            recent.add(ref[j]);
            System.out.print(" Hit!!!   " + buffer.toString() + "\n");
            hit++;
        }else{
            int k = 0 ;
            while(!buffer.contains(recent.get(k))){
                k++;
            }
            buffer.set(buffer.indexOf(recent.get(k)), ref[j]);
            System.out.print(" Fault!!! " + buffer.toString() +  "\n");
            recent.remove(ref[j]);
            recent.add(ref[j]);
        }
    }

    double hitr = hit/(double)ref.length;
    double faultr = 1 - hit/(double)ref.length;
    System.out.println("\nHit ratio :-  "  + hitr);
    System.out.println("\nFault ratio:- " + faultr);

}