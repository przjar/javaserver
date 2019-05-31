import org.apache.commons.lang3.StringUtils;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

public class ChatClient {

    private final String serverName;
    private final int serverPort;
    private InputStream serverIn;
    private OutputStream serverOut;
    private BufferedReader bufferedIn;
    private Socket socket;

    public ArrayList<UserStatusListener> getUserStatusListeners() {
        return userStatusListeners;
    }

    private ArrayList<UserStatusListener> userStatusListeners = new ArrayList<>();
    private ArrayList<MessageListener> messageListeners = new ArrayList<>();

    public ChatClient(String serverName, int serverPort) {
        this.serverName = serverName;
        this.serverPort = serverPort;
    }



    public void leave(String groupName) throws  IOException{
        String cmd = "leave "  + groupName + "\n";
        serverOut.write(cmd.getBytes());
    }


    public void join(String groupName) throws  IOException{
        String cmd = "join "   + groupName + "\n";
        serverOut.write(cmd.getBytes());
    }



    public void msg(String groupName,String sendTo, String msgBody) throws  IOException{
        String cmd = "msg "+sendTo + " " + msgBody + "\n";
        if(groupName!=null){
             cmd = "msg "+groupName + " " + msgBody + "\n";
            serverOut.write(cmd.getBytes());
        }else
        serverOut.write(cmd.getBytes());
    }

    public void logoff() throws  IOException{
       String cmd = "quit\n";
       serverOut.write(cmd.getBytes());
    }

    public boolean login(String login, String password) throws IOException{
        String cmd = "login " + login + " "+ password + "\n";
        serverOut.write(cmd.getBytes());
        String response = bufferedIn.readLine();

        if("ok login".equalsIgnoreCase(response)){
            startMessageReader();
            return true;
        }else{
            return false;
        }
    }

    private void startMessageReader(){
        Thread t = new Thread(){
            @Override
            public void run(){
                readMsgLoop();
            }
        };
        t.start();
    }

    private void readMsgLoop() {
        try {
            String line;
            while ((line = bufferedIn.readLine()) != null) {
                String[] tokens = StringUtils.split(line);
                if (tokens != null && tokens.length > 0) {
                    String cmd = tokens[0];
                    if ("online".equalsIgnoreCase(cmd)) {
                        handleOnline(tokens);
                    }
                    else if ("offline".equalsIgnoreCase(cmd)){
                        handleOffline(tokens);}
                      if("msg".equalsIgnoreCase(cmd)){
                        String[] tokensMsg = StringUtils.split(line);
                        handleMsg(tokensMsg);
                    }
                }
            }
            }catch(Exception ex){
                ex.printStackTrace();
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        private void handleMsg(String[] tokenMsg){
        String login;
        String msgBody;
        String groupName;
        if(tokenMsg[1].charAt(0) == '#'){
            groupName = tokenMsg[1];
            login = tokenMsg[2];
            msgBody = tokenMsg[3];

            int x=4;
            try{
            do{
                msgBody += " "+tokenMsg[x];
                System.out.println(msgBody);
                x++;

            }while(x<tokenMsg.length);
            }catch(IndexOutOfBoundsException e){

            }

            for (MessageListener listener : messageListeners) {
                listener.onGroupMessage(groupName,login, msgBody);
            }
        }else {
            login = tokenMsg[1];
            msgBody = tokenMsg[2];

            int x=3;
            try{
            do{
                msgBody += " "+tokenMsg[x];
                System.out.println(msgBody);
                x++;

            }while(x<tokenMsg.length);
            }catch(IndexOutOfBoundsException e){

            }

            for (MessageListener listener : messageListeners) {
                listener.onMessage(login, msgBody);
            }
        }
        }


    private void handleOnline(String[] tokens){
        String login = tokens[1];
        for(UserStatusListener listener:userStatusListeners){
            listener.online(login);
        }
    }


    public void handleOffline(String[] tokens){
        String login = tokens[1];
        for(UserStatusListener listener:userStatusListeners){
            listener.offline(login);
        }
    }

    public boolean connect(){
        try {
            this.socket = new Socket(serverName,serverPort);
            System.out.println("Client port is " +socket.getLocalPort());
            this.serverOut = socket.getOutputStream();
            this.serverIn = socket.getInputStream();
            this.bufferedIn = new BufferedReader((new InputStreamReader(serverIn)));
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
    public void addUserStatusListener(UserStatusListener listener){
        userStatusListeners.add(listener);
    }

    public  void removeUserStatusListener(UserStatusListener listener){
        userStatusListeners.remove(listener);
    }
    public void addMessageListener(MessageListener listener){
        messageListeners.add(listener);
    }

    public void removeMessageListener(MessageListener listener){
        messageListeners.remove(listener);
    }
}

