import java.util.Scanner;
import java.io.*;
import java.net.*;

public class Client {

    public static void main(String[] args) throws Exception {
        int port = 65521;
        Socket socket = null;
        Scanner sc = new Scanner(System.in);
        String string = null;
        while (true) {
            try {
                socket = new Socket("localhost", port);
                System.out.println("Enter the number of tickets to be booked (press 0 to exit):-");
                string = sc.nextLine().trim();
                OutputStreamWriter outputStreamWriter = new OutputStreamWriter(socket.getOutputStream());
                PrintWriter printWriter = new PrintWriter(outputStreamWriter);
                try {
                    if (Integer.parseInt(string) == 0)
                        break;
                } catch (Exception e) {
                }
                System.out.println("Enter passenger name:-");
                String name = sc.nextLine();
                printWriter.println(string + " " + name);
                printWriter.flush();
                outputStreamWriter.flush();
                BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));

                System.out.println(br.readLine());
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
