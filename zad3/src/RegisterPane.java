import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.sql.*;

public class RegisterPane {
    private JPanel panel1;
    private JButton registerButton;
    private JTextField loginField;
    private JPasswordField passwordField;
    private JTextField emailField;
    private JLabel emailLabel;
    private JLabel passwordLabel;
    private JLabel loginLabel;
    private JButton exitButton;
    private  JFrame frame;
    private int register = 1;

    public void setRegister(int register) {
        this.register = register;
    }

    public void setFrame(JFrame frame) {
        this.frame = frame;
    }

    public RegisterPane(JFrame rframe){

        setFrame(rframe);
        //Font
        Font font = null;
        try {
            font = Font.createFont(Font.TRUETYPE_FONT, new File("res\\DeLaFuente.ttf"));
            font = font.deriveFont(Font.BOLD, 24);
            loginLabel.setFont(font);
            passwordLabel.setFont(font);
            emailLabel.setFont(font);
            registerButton.setFont(font);
            exitButton.setFont(font);
        } catch (FontFormatException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }



        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    //Creating Connection Object
                    // If you encounter a time zone error add ......./myDatabase?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC
                    //One line of code needed
                    //
                    //Preapared Statement
                    PreparedStatement Pstatement=connection.prepareStatement("insert into customer values(?,?,?)");
                    //Specifying the values of it's parameter
                    //Three lines of code needed
                    //
                    //
                    //
                    //Checking for duplicates in the database
                    Statement stm = connection.createStatement();
                    String query = "SELECT LOGIN, EMAIL FROM customer;";
                    //Two lines of code needed(resultSet)
                    //
                    //
                    while(......) {
                        String Username = //add String from database "LOGIN"
                        String Email = //add String from database "EMAIL"
                        if(Username.equals(loginField.getText()) || Email.equals(emailField.getText())) {
                            JOptionPane.showMessageDialog(panel1,"Login or email is taken.");
                            setRegister(0);
                            break;
                        }
                    }
                    if(register==1) {
                        //If succesfull updates the database and disposes of the register frame
                        //One line of code needed
                        //
                        frame.dispose();
                    }
                    setRegister(1);
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }
        });
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
            }
        });
    }





    public JPanel getPanel1() {
        return panel1;
    }
}
