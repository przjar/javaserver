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
                    //Creating Connection Object - debug time zone jdbc:mysql://localhost/db?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC
                    Connection connection= DriverManager.getConnection("jdbc:mysql://localhost:3306/myDatabase?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC","root","root");
                    //Preapared Statement
                    PreparedStatement Pstatement=connection.prepareStatement("insert into customer values(?,?,?)");
                    //Specifying the values of it's parameter
                    Pstatement.setString(1,loginField.getText());
                    Pstatement.setString(2,passwordField.getText());
                    Pstatement.setString(3,emailField.getText());
                    //Checking for duplicates
                    Statement stm = connection.createStatement();
                    String query = "SELECT LOGIN, EMAIL FROM customer;";
                    stm.executeQuery(query);
                    ResultSet rs = stm.getResultSet();
                    while(rs.next()) {
                        String dbUsername = rs.getString("LOGIN");
                        String dbEmail = rs.getString("EMAIL");
                        if(dbUsername.equals(loginField.getText()) || dbEmail.equals(emailField.getText())) {
                            JOptionPane.showMessageDialog(panel1,"Login or email is taken.");
                            setRegister(0);
                            break;
                        }
                    }
                    if(register==1) {
                        Pstatement.executeUpdate();
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
