import javafx.scene.layout.Background;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class UserListPane extends JPanel implements  UserStatusListener{
   private JFrame frame;

    public void setFrame(JFrame frame) {
        this.frame = frame;
    }

   private JList<String> userListUI;
    private final ChatClient client;
    private DefaultListModel<String> userlistModel;
    private JButton addGroup;


    public UserListPane(ChatClient client){
        this.client = client;
        this.client.addUserStatusListener(this);
        userlistModel = new DefaultListModel<>();
        userListUI = new JList<>(userlistModel);
        setLayout(new BorderLayout());

        userListUI = new BackgroundImageList();
        userListUI.setModel(userlistModel);

        addGroup = new JButton();
        JPanel buttonCenter = new JPanel( new FlowLayout(FlowLayout.CENTER) );


        Font font = null;
        try {
            font = Font.createFont(Font.TRUETYPE_FONT, new File("res\\DeLaFuente.ttf"));
            font = font.deriveFont(Font.BOLD, 24);
            addGroup.setFont(font);
            userListUI.setFont(font);
        } catch (FontFormatException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        addGroup.setForeground(Color.black);


        JScrollPane pane = new JScrollPane(userListUI);
        pane.setBounds(0,0,300,450);
        add(pane,BorderLayout.CENTER);
        buttonCenter.add(addGroup);
        addGroup.setText("Add a group");
        add(buttonCenter, BorderLayout.SOUTH);





        addGroup.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                    JFrame grpFrame = new JFrame("Write the group name");
                    GroupName groupName = new GroupName(grpFrame,client);
                    grpFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                    grpFrame.setSize(300,200);
                    grpFrame.setContentPane(groupName.getPanel1());
                    grpFrame.setVisible(true);
                    grpFrame.setResizable(false);
                }
        });



        userListUI.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e){
                if(e.getClickCount()>1 && userListUI.getSelectedValue()!= null){
                    String login = userListUI.getSelectedValue();
                    MessagePane messagePane = new MessagePane(client, null,login);
                    JFrame msgFrame = new JFrame("Message: " + login);
                    msgFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

                    msgFrame.getContentPane().add(messagePane, BorderLayout.CENTER);
                    msgFrame.setSize(600,400);
                    msgFrame.setVisible(true);
                    msgFrame.setResizable(false);
                }
            }
        });
    }

    //List Background class
    public class BackgroundImageList extends JList {
        private BufferedImage background;

        public BackgroundImageList() {
            try {
                background = ImageIO.read(new File("res\\backgroundUserList.jpg"));
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





    @Override
    public void online(String login){
        userlistModel.addElement(login);
    }

    @Override
    public void offline(String login){
        userlistModel.removeElement(login);
    }


}
