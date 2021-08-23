import java.util.*;
public class bitstuffing
{
  public static void main(String[] args)
  {
    Scanner sc=new Scanner(System.in);
    System.out.print("Enter the message:-");
    String d1 = sc.nextLine();
    String r = new String();
    String output=new String();
    int counter = 0;
    for(int i=0;i<d1.length();i++)
    {
      if (d1.charAt(i)!='1' && d1.charAt(i)!='0')
      {
        System.out.println("Enter valid Binary values");
        return;
      }
      if(d1.charAt(i) == '1')
      {
        counter++;
        r = r + d1.charAt(i);
      }
      else
      {
        r = r + d1.charAt(i);
        counter = 0;
      }
      if(counter == 5)
      {
        r = r + '0';
        counter = 0;
      }
    }
    System.out.println("Flag-> 01111110");
    String new1="/01111110/"+r+"/01111110/";
    System.out.println("Stuffed data at intermediate site is:");
    System.out.println();
    System.out.println(" "+new1);
    
    System.out.println();
    counter=0;
    for(int i=0;i<r.length();i++)
    {
      if(r.charAt(i) == '1')
      {
        counter++;
        output = output + r.charAt(i);
      }
      else
      {
        output = output + r.charAt(i);
        counter = 0;
      }
      if(counter == 5)
      {
        if((i+2)!=r.length())
        {
          output = output + r.charAt(i+2);
        }
        else
        {
          output=output + '1';
        }
        i=i+2;
        counter = 1;
      }
    }
    System.out.println("Destuffed BIT is: "+output);
  }
}