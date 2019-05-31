import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import static com.sun.javafx.fxml.expression.Expression.add;

public class MessagePane extends JPanel implements MessageListener {

    private final ChatClient client;
    private String login = "";

   private JList<String> userListUI;
    private DefaultListModel<String> userlistModel;
    private JTextField textField1;

    public MessagePane(ChatClient client, String groupName, String login) {
        this.client = client;
        this.login = login;


        userlistModel = new DefaultListModel<>();
        userListUI = new JList<>(userlistModel);
        setLayout(new BorderLayout());

        userListUI = new BackgroundImageList();
        userListUI.setModel(userlistModel);

        textField1 = new JTextField();
        JPanel buttonCenter = new JPanel( new FlowLayout(FlowLayout.CENTER) );


        Font font = null;
        try {
            font = Font.createFont(Font.TRUETYPE_FONT, new File("res\\DeLaFuente.ttf"));
            font = font.deriveFont(Font.BOLD, 24);
            textField1.setFont(font);
            userListUI.setFont(font);
        } catch (FontFormatException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        textField1.setForeground(Color.black);


        JScrollPane pane = new JScrollPane(userListUI);
        pane.setBounds(0,0,600,400);
        add(pane,BorderLayout.CENTER);
        buttonCenter.add(textField1);
        textField1.setPreferredSize(new Dimension(600,40));

        add(buttonCenter, BorderLayout.SOUTH);




        client.addMessageListener(this);
        textField1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String text = textField1.getText();
                    if (groupName == null) {
                        userlistModel.addElement("You: " + text);
                        if(userlistModel.size()>36){
                            userlistModel.removeElementAt(0);
                        }
                    }
                    client.msg(groupName, login, text);
                    textField1.setText("");
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        });
    }

    @Override
    public void onMessage(String fromLogin, String msgBody) {
        String line;
        if (login.equalsIgnoreCase(fromLogin)) {
            line = fromLogin + ": " + msgBody;
           userlistModel.addElement(line);
            if(userlistModel.size()>36){
                userlistModel.removeElementAt(0);
            }

        }
    }

    @Override
    public void onGroupMessage(String groupName, String fromLogin, String msgBody) {
        String line;
        line = fromLogin + ": " + msgBody;
        userlistModel.addElement(line);
        if(userlistModel.size()>36){
            userlistModel.removeElementAt(0);
        }


    }

    //List Background class
    public class BackgroundImageList extends JList {
        private BufferedImage background;

        public BackgroundImageList() {
            try {
                background = ImageIO.read(new File("res\\backgroundMsg.jpg"));
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            setOpaque(false);
            setBackground(new Color(0, 0, 0, 0));
            setForeground(Color.WHITE);
        }

        @Override
        protected void paintComponent(Graphics g) {
            if (background != null) {
                Graphics2D g2d = (Graphics2D) g.create();
                int x = getWidth() - background.getWidth();
                int y = getHeight() - background.getHeight();
                g2d.drawImage(background, x, y, this);
                g2d.dispose();
            }
            super.paintComponent(g);
        }


    }
}

