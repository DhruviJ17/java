import java.io.*;
import  java.net.*;

public class TCPClient {
    public static void main(String z[])throws IOException{
        String msg;
        Socket s1 = new Socket("localhost",80);
        BufferedReader d2 = new BufferedReader(new InputStreamReader((s1.getInputStream())));
        msg = d2.readLine();
        System.out.println("Msg is : "+msg);
        s1.close();
    }
}