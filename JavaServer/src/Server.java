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
        try {
            ServerSocket serverSocket = new ServerSocket(9999);
            while(true) {
                System.out.println("Waiting for a new connection...");
                Socket clientSocket = serverSocket.accept();
                System.out.println("Client connected:" + clientSocket);
                ServerWorker worker = new ServerWorker(this,clientSocket);
                workerList.add(worker);
                worker.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void removeWorker(ServerWorker serverWorker, String login){
        workerList.remove(serverWorker);
        System.out.println(login +" has logged off");
    }
    }

