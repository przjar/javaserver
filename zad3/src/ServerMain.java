import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;

public class ServerMain {
    public static void main(String[] args){
        //Starting a server on chosen port
        int port = 9999;
        Server server = new Server(port);
        server.start();
    }


}
