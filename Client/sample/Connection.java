package sample;
import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;

public class Connection {
    public Object Connect(Object massage, String string ) throws InterruptedException {
        try(Socket socket = new Socket("localhost", 3345);
            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream ois = new ObjectInputStream(socket.getInputStream()); )
        {
            System.out.println("Client connected to socket.");
            System.out.println();
            System.out.println("Client writing channel = oos & reading channel = ois initialized.");
                System.out.println("Client start writing in channel...");
                oos.writeObject(massage);
                oos.writeUTF(string);
                oos.flush();
                System.out.println("Client sent message " + massage + " to server.");
                System.out.println("Client sent message & start waiting for data from server...");
                System.out.println("reading...");
                return ois.readObject();

        } catch (UnknownHostException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return  null;
    }
}