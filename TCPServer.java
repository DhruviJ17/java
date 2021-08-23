import java.io.*;
import  java.net.*;

public class TCPServer {
    public static void main(String z[])throws IOException{
        ServerSocket ss1 = new ServerSocket(80);
        String outmsg;
        while(true){
            Socket s1 = ss1.accept();
            String m[] = {"SUNDAY","MONDAY","TUESDAY","WEDNESDAY","FRIDAY","SATURDAY"};
            int j = (int)(Math.random()*m.length);
            outmsg = m[j];
            PrintStream d1 = new PrintStream(s1.getOutputStream());
            d1.println(outmsg);
        }
    }
}