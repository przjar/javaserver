import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Server extends Thread{

    private final int serverPort;
    private ArrayList<ServerWorker> workerList = new ArrayList<>();



    public Server(int serverPort){
        this.serverPort=serverPort;
    }

    public List<ServerWorker> getWorkerList(){
        return workerList;
    }




    @Override
    public void run(){
        //Creating socket server and listing connected sockets
        try {
            // Create socket server - one line of code needed
            //
            ServerSocket serverSocket = new ServerSocket(serverPort);
            while(true) {
                System.out.println("Waiting for a new connection...");
                //Listen to the socket for a client to make a connection request and create Client Socket - one line of code needed
                //
                System.out.println("Client connected:" + clientSocket);
                ServerWorker worker = new ServerWorker(this,clientSocket);
                //Two lines of code needed (add ServerWorker to the ArrayList, start thread in ServerWorker worker class)
                //
                //
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void removeWorker(ServerWorker serverWorker, String login){
        //Two lines of code needed (remove ServerWorker from the ArrayList, write in system output that client has logged off
        //
        //

    }
    }

