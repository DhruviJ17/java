import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    static int available = 10;
    static String string = null;
    static Socket socket = null;

    public static void main(String[] args) {
        System.out.println("Server started");
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(65521);
        } catch (IOException e) {
            e.printStackTrace();
        }
        while (true) {
            try {
                socket = serverSocket.accept();

                final BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            string = br.readLine();
                            int request = Integer.parseInt(string.substring(0, string.indexOf(' ')));
                            if (request <= 0) {

                                string = "Invalid input";
                            }

                            else if (isAvailable(request))
                                string = "Booked " + request + " tickets successfully for "
                                        + string.substring(string.indexOf(' ')) + ", " + available + " tickets left";
                            else
                                string = "Not enough tickets available";
                        } catch (NumberFormatException e) {
                            string = "Invalid input";
                        } catch (IOException e) {
                        }
                        try {
                            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(socket.getOutputStream());
                            PrintWriter printWriter = new PrintWriter(outputStreamWriter);
                            printWriter.println(string);
                            printWriter.flush();

                            outputStreamWriter.flush();
                        } catch (IOException e) {
                            e.printStackTrace();

                        }
                    }

                }).start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public synchronized static boolean isAvailable(int request) {
        if (available >= request && request > 0) {
            available = available - request;
            return true;
        }
        return false;
    }
}
