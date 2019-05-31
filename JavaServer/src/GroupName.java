import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;

public class GroupName {
    private JTextField nameField;
    private JButton nameButton;
    private JLabel nameLabel;
    private JPanel panel1;
    private JFrame frame;
    private ChatClient client;

    public JPanel getPanel1() {
        return panel1;
    }

    public GroupName(JFrame frame, ChatClient client) {
        this.client = client;
        this.frame=frame;

        Font font = null;
        try {
            font = Font.createFont(Font.TRUETYPE_FONT, new File("res\\DeLaFuente.ttf"));
            font = font.deriveFont(Font.BOLD, 24);
            nameButton.setFont(font);
            nameField.setFont(font);
            nameLabel.setFont(font);
        } catch (FontFormatException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


        nameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(nameField.getText()!=null) {
                frame.dispose();
                String grpName = "#"+nameField.getText();

                    try {
                        client.join(grpName);
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }

                JFrame msgGrpFrame = new JFrame(grpName);
                MessagePane msgGrpPane = new MessagePane(client,grpName,null);
                msgGrpFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                msgGrpFrame.setSize(600,400);
                msgGrpFrame.getContentPane().add(msgGrpPane, BorderLayout.CENTER);
                msgGrpFrame.setVisible(true);
                msgGrpFrame.setResizable(false);
                    msgGrpFrame.addWindowListener(new WindowAdapter() {
                        public void windowClosing(WindowEvent e) {
                            try {
                                client.leave(grpName);
                            } catch (IOException e1) {

                            }
                        }
                    });
                }
            }
        });


        nameField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode()==KeyEvent.VK_ENTER) {
                    if(nameField.getText()!=null) {
                        frame.dispose();
                        String grpName = "#"+nameField.getText();
                        JFrame msgGrpFrame = new JFrame(grpName);
                        MessagePane msgGrpPane = new MessagePane(client,grpName,null);
                        msgGrpFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                        msgGrpFrame.setSize(600,400);
                        msgGrpFrame.getContentPane().add(msgGrpPane, BorderLayout.CENTER);
                        msgGrpFrame.setVisible(true);
                        msgGrpFrame.setResizable(false);
                        try {
                            client.join(grpName);
                        } catch (IOException e1) {
                            e1.printStackTrace();
                        }
                        msgGrpFrame.addWindowListener(new WindowAdapter() {
                            public void windowClosing(WindowEvent e) {
                                try {
                                    client.leave(grpName);
                                } catch (IOException e1) {

                                }
                            }
                        });
                    }
                }
            }
        });

        nameButton.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode()==KeyEvent.VK_ENTER) {
                    if(nameField.getText()!=null) {
                        frame.dispose();
                        String grpName = "#"+nameField.getText();
                        JFrame msgGrpFrame = new JFrame(grpName);
                        MessagePane msgGrpPane = new MessagePane(client,grpName,null);
                        msgGrpFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                        msgGrpFrame.setSize(600,400);
                        msgGrpFrame.getContentPane().add(msgGrpPane, BorderLayout.CENTER);
                        msgGrpFrame.setVisible(true);
                        msgGrpFrame.setResizable(false);
                        try {
                            client.join(grpName);
                        } catch (IOException e1) {
                            e1.printStackTrace();
                        }
                        msgGrpFrame.addWindowListener(new WindowAdapter() {
                            public void windowClosing(WindowEvent e) {
                                try {
                                    client.leave(grpName);
                                } catch (IOException e1) {

                                }
                            }
                        });
                    }
                }
            }
        });


    }
}
