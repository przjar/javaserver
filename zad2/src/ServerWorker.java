

import org.apache.commons.lang3.StringUtils;
import java.io.*;
import java.net.Socket;
import java.sql.*;
import java.util.HashSet;
import java.util.List;
import java.io.IOException;

public class ServerWorker extends  Thread{
    private final Socket clientSocket;
    private String login = null;
    private final Server server;
    private OutputStream output;
    private HashSet<String> topicSet = new HashSet<>();

    public ServerWorker(Server server, Socket clientSocket){
        this.server = server;
        this.clientSocket = clientSocket;
    }

    @Override
    public void run(){
        try {
            handleClientSocket();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public String getLogin() {
        return login;
    }

    private void handleClientSocket() throws IOException, InterruptedException {

        InputStream input = clientSocket.getInputStream();
        this.output = clientSocket.getOutputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(input));
        String line;
        //Reading command line commands from the users
        while ((line = reader.readLine()) != null) {
            System.out.println("Pushed command: " + line);    //debug print for system
            String[] tokens = line.split(" ");
            if (tokens != null && tokens.length > 0) {
                String cmd = tokens[0];
                if ("quit".equals(line)) {
                    handleLogoff();
                    break;
                } else if("login".equalsIgnoreCase(cmd)){
                    handleLogin(output,tokens);
                }else if("msg".equalsIgnoreCase(cmd)){

                    String[] tokensMsg = StringUtils.split(line);
                    handleMsg(tokensMsg);
                }else if("leave".equalsIgnoreCase(cmd)){
                    handleLeave(tokens);
                }
                else if("join".equalsIgnoreCase(cmd)){
                    handleJoin(tokens);
                }
                else {
                    String msg = "Unknown " + cmd + "\n";
                    output.write(msg.getBytes());
                }
            }
        }
        clientSocket.close();
    }


    private void handleLeave(String[] tokens){
        if(tokens.length>1){
            String topic = tokens[1];
            topicSet.remove(topic);
        }
    }


    public boolean isMemberOfTopic(String topic){
        return topicSet.contains(topic);
    }


    private void handleJoin(String[] tokens) throws IOException{

        if(tokens.length>1){
            String topic = tokens[1];
            topicSet.add(topic);

        }
    }


    //msg login rest
    //msg #topic rest

    private void handleMsg(String[] tokens)throws IOException{
        String sendTo = tokens[1];
        String body = tokens[2];
        System.out.println(body);
        int x=3;
        try {
            do {
                body += " " + tokens[x];
                System.out.println(body);
                x++;
            } while (x < tokens.length - 1);
        }catch(IndexOutOfBoundsException e){

        }

        boolean isTopic = sendTo.charAt(0) == '#';


        List<ServerWorker> workerList = server.getWorkerList();
        for(ServerWorker worker:workerList){
            if(isTopic){
                if(worker.isMemberOfTopic(sendTo)) {
                    String outMsg = "msg " + sendTo + " " + login + " " + body + "\n";
                    worker.send(outMsg);
                }
            }
            if(sendTo.equalsIgnoreCase(worker.getLogin())){
                String outMsg = "msg " + login + " " + body + "\n";
                worker.send(outMsg);
            }
        }
    }

    private void handleLogin(OutputStream outputStream, String[] tokens) throws IOException {
        int x = 0;
        if (tokens.length == 3) {
            String login = tokens[1];
            String password = tokens[2];
            if ((login.equalsIgnoreCase("guest") && password.equalsIgnoreCase("guest")) || login.equalsIgnoreCase("student") && password.equalsIgnoreCase("student")) {
                String msg = "ok login\n";
                outputStream.write(msg.getBytes());
                this.login = login;
                System.out.println(login + " has logged in ");
                List<ServerWorker> workerList = server.getWorkerList();


                //Sending to the current user the status of the other users
                for (ServerWorker worker : workerList) {
                    if (!login.equals(worker.getLogin())) {
                        if (worker.getLogin() != null) {
                            String msg2 = "online " + worker.getLogin() + "\n";
                            send(msg2);
                        }
                    }
                }


                // Sending to the other users the status of the current user
                String onlineMsg = "online " + login + "\n";
                for (ServerWorker worker : workerList) {
                    if (!login.equals(worker.getLogin())) {
                        worker.send(onlineMsg);
                    }
                }
            } else {
                String msg = "error login\n";
                outputStream.write(msg.getBytes());
                System.err.println("Login failed for " + login);
            }
        }

    }

    public void handleLogoff() throws IOException {
        server.removeWorker(this, login);
        List<ServerWorker> workerList = server.getWorkerList();
        // Sending to other users the status of the current user
        String onlineMsg = "offline " + login + "\n";
        for(ServerWorker worker : workerList){
            if(!login.equals(worker.getLogin())) {
                worker.send(onlineMsg);
            }
        }
        clientSocket.close();
    }
        //Function that writes the commands results to the OutputStream which is later read by ChatClient
        private void send(String msg) throws IOException{
            output.write(msg.getBytes());
        }




}
