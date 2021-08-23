import java.util.*;
public class CharacterCount
{
 public static void main(String args[])
 {
  int i,n;
  Scanner scan=new Scanner(System.in);
  System.out.println("From Sender's Side:");
  System.out.print("Enter the no.of frames:");
  n=scan.nextInt();
  String frame[]=new String[n];
  String answer="";
  String rec_data;
  for(i=0;i<n;i++)
  {
   System.out.print("Enter frame:");
   frame[i]=scan.next();
   answer=answer+String.valueOf(frame[i].length()+1);
   answer=answer+frame[i];
  }
  System.out.print("Frames Created:"+answer);
  System.out.println();
  System.out.println("From Receiver's Side:");
  System.out.print("Enter the received data:");
  rec_data=scan.next();
 if(rec_data.equals(answer))
 {
  int c=0,k=0;
  while(n!=0)
  {
   int l=Integer.parseInt(String.valueOf(rec_data.charAt(c)));
   c++;
   k++;
   System.out.print("Frame "+ k+":");
   while(l>1)
   {
    System.out.print(rec_data.charAt(c));
    l--;
    c++;
   }
   System.out.println();
   n--;
  }
 }
 else
 {
  System.out.println("Error in received code.");
 }
 }
}