import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.sql.*;
import javax.swing.UIManager;

public class LoginWindow {
    private ChatClient client;
    private JPanel panel1;
    private JButton sendButton;
    private JButton registerButton;
    private JTextField loginField;
    private JPasswordField passwordField;
    private JLabel loginLabel;
    private JLabel passwordLabel;
    private  JFrame frame;



    public void setFrame(JFrame frame) {
        this.frame = frame;

        //Fonts
        Font font = null;
        try {
            font = Font.createFont(Font.TRUETYPE_FONT, new File("res\\DeLaFuente.ttf"));
            font = font.deriveFont(Font.BOLD, 24);
            loginLabel.setFont(font);
            passwordLabel.setFont(font);
            sendButton.setFont(font);
        } catch (FontFormatException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        loginLabel.setForeground(Color.white);
        passwordLabel.setForeground(Color.white);

    }

    public LoginWindow(){

        //LoginWindow Listeners
        sendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int x =0;
                try {
                    //Creating Connection Object
                    // If you encounter a time zone error add ......./myDatabase?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC
                    //One line of code needed
                    //
                    //Checking for duplicates in the database
                    Statement stm = connection.createStatement();
                    String query = "SELECT LOGIN, PASS FROM customer;";
                    //Two lines of code needed(resultSet)
                    //
                    //
                    //Make a similar loop to the one in register pane but in this one check LOGIN and PASS and see if both of them can be found
                    // in one row, if so then use connect() and change value of x to 1
                    while(......) {
                        //
                        //
                        //
                        //
                        //
                    }
                    if(x==0) {
                        JOptionPane.showMessageDialog(panel1, "Wrong login or password.");

                    }
                    x=0;
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }


            }
        });


        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Creating register frame
                JFrame rframe = new JFrame("User List");
                RegisterPane registerPane = new RegisterPane(rframe);
                rframe.setSize(400,350);
                rframe.setResizable(false);
                rframe.setContentPane(registerPane.getPanel1());
                rframe.setVisible(true);


            }
        });

        passwordField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode()==KeyEvent.VK_ENTER) {
                    int x =0;
                    try {
                        //Creating Connection Object
                        // If you encounter a time zone error add ......./myDatabase?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC
                        //One line of code needed
                        //
                        //Checking for duplicates in the database
                        Statement stm = connection.createStatement();
                        String query = "SELECT LOGIN, PASS FROM customer;";
                        //Two lines of code needed(resultSet)
                        //
                        //
                        //Make a similar loop to the one in register pane but in this one check LOGIN and PASS and see if both of them can be found
                        // in one row, if so then use connect() and change value of x to 1
                        while(......) {
                            //
                            //
                            //
                            //
                            //
                        }
                        if(x==0) {
                            JOptionPane.showMessageDialog(panel1, "Wrong login or password.");

                        }
                        x=0;
                    } catch (SQLException e1) {
                        e1.printStackTrace();
                    }
                }
            }
        });
        loginField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode()==KeyEvent.VK_ENTER) {
                    int x =0;
                    try {
                        //Creating Connection Object
                        // If you encounter a time zone error add ......./myDatabase?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC
                        //One line of code needed
                        //
                        //Checking for duplicates in the database
                        Statement stm = connection.createStatement();
                        String query = "SELECT LOGIN, PASS FROM customer;";
                        //Two lines of code needed(resultSet)
                        //
                        //
                        //Make a similar loop to the one in register pane but in this one check LOGIN and PASS and see if both of them can be found
                        // in one row, if so then use connect() and change value of x to 1
                        while(......) {
                            //
                            //
                            //
                            //
                            //
                        }
                        if(x==0) {
                            JOptionPane.showMessageDialog(panel1, "Wrong login or password.");

                        }
                        x=0;
                    } catch (SQLException e1) {
                        e1.printStackTrace();
                    }

                }

            }
        });
        sendButton.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode()==KeyEvent.VK_ENTER) {
                    int x =0;
                    try {
                        //Creating Connection Object
                        // If you encounter a time zone error add ......./myDatabase?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC
                        //One line of code needed
                        //
                        //Checking for duplicates in the database
                        Statement stm = connection.createStatement();
                        String query = "SELECT LOGIN, PASS FROM customer;";
                        //Two lines of code needed(resultSet)
                        //
                        //
                        //Make a similar loop to the one in register pane but in this one check LOGIN and PASS and see if both of them can be found
                        // in one row, if so then use connect() and change value of x to 1
                        while(......) {
                            //
                            //
                            //
                            //
                            //
                        }
                        if(x==0) {
                            JOptionPane.showMessageDialog(panel1, "Wrong login or password.");

                        }
                        x=0;
                    } catch (SQLException e1) {
                        e1.printStackTrace();
                    }

                }
            }
        });
    }

    private void connect(){
        //Address of the server for the client socket
        String ip = "......";
        //Creating client
        client = new ChatClient(ip,9999);
            if(client.connect()==false){
                JOptionPane.showMessageDialog(panel1,"Unable to connect to the server.");
            }
            else
            doLogin();
    }

    private void doLogin(){
        String login = loginField.getText();
        String password = passwordField.getText();
        //Opening a new window with an user list
        try {
            if(client.login(login,password)){
                panel1.setVisible(false);
                frame.dispose();
                UserListPane userListPane = new UserListPane(client);
                JFrame frame = new JFrame("User List");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setSize(300,450);
                frame.setResizable(false);

                frame.getContentPane().add(userListPane, BorderLayout.CENTER);
                frame.setVisible(true);

                frame.addWindowListener(new WindowAdapter() {
                    public void windowClosing(WindowEvent e) {
                        try {
                            client.logoff();
                        } catch (IOException e1) {

                        }
                    }
                });

            }else{
                JOptionPane.showMessageDialog(panel1,"Invalid login,password.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args){
        //Background
        ImageIcon background=new ImageIcon("res\\background.png");
        Image img=background.getImage();
        Image temp=img.getScaledInstance(400,300,Image.SCALE_SMOOTH);
        background=new ImageIcon(temp);
        JLabel back=new JLabel(background);
        back.setLayout(null);
        back.setBounds(0,0,400,300);



        //Look and Feel
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        }catch(Exception e){
            System.out.println("Look and Feel not set");
        }

        //Creating login frame
        LoginWindow loginWindow = new LoginWindow();
        JFrame frame = new JFrame("Login");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setContentPane(loginWindow.panel1);
        frame.pack();
        frame.setVisible(true);
        loginWindow.setFrame(frame);
        frame.setSize(400,300);
        frame.setResizable(false);
        //Background
        try {
            frame.add(back);
        }catch (NullPointerException e){

        }

    }


}
