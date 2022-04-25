package ChatApplication;
import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;

import java.util.Calendar;
import java.text.SimpleDateFormat;

public class Server implements ActionListener {
    JPanel p1;/*just like div in HTML*/
    JTextField t1;/*written*/
    JButton b1;/*button*/
    static JPanel a1;

    static Box vertical = Box.createVerticalBox();
/*server socket*/
    static JFrame f1= new JFrame();

    static ServerSocket skt;
    static Socket s;
    static DataInputStream din;
    static DataOutputStream dout;
    Boolean typing;
   Server(){

        p1 = new JPanel();
        p1.setLayout(null);
        p1.setBackground(new Color(128, 191, 255));
        p1.setBounds(0,0,450,70);
        f1.add(p1);

        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("ChatApplication/icon/left.png"));
        Image i2 = i1.getImage().getScaledInstance(25,25,Image.SCALE_DEFAULT);
        ImageIcon i3 = new ImageIcon(i2);
        JLabel l1 = new JLabel(i3);
        l1.setBounds(2,17,30,30);
        p1.add(l1);

       l1.addMouseListener(new MouseAdapter(){
           @Override
           public void mouseClicked(MouseEvent me){
               System.exit(0);
           }
       });

        ImageIcon i4 = new ImageIcon(ClassLoader.getSystemResource("ChatApplication/icon/Ironman.png"));
        Image i5 = i4.getImage().getScaledInstance(60,60,Image.SCALE_DEFAULT);
        ImageIcon i6 = new ImageIcon(i5);
        JLabel l2 = new JLabel(i6);
        l2.setBounds(40,7,60,60);
        p1.add(l2);

        JLabel l3 = new JLabel("Tony");
        l3.setFont(new Font("BOLD",Font.BOLD,18));
        l3.setBounds(110,15,100,20);
        p1.add(l3);

        JLabel l4 = new JLabel("online..");
        l4.setFont(new Font("BOLD",Font.PLAIN,14));
        l4.setBounds(110,35,100,20);
        p1.add(l4);

/*typing online writen*/
       javax.swing.Timer t = new javax.swing.Timer(1, new ActionListener(){
           public void actionPerformed(ActionEvent ae){
               if(!typing){
                   l4.setText("online..");
               }
           }
       });

       t.setInitialDelay(1000);


        ImageIcon i7 = new ImageIcon(ClassLoader.getSystemResource("ChatApplication/icon/video.png"));/*image get into folder*/
        Image i8 = i7.getImage().getScaledInstance(25,25,Image.SCALE_DEFAULT);
        ImageIcon i9 = new ImageIcon(i8);
        JLabel l5 = new JLabel(i9);
        l5.setBounds(310,19,30,30);
        p1.add(l5);

        ImageIcon i10 = new ImageIcon(ClassLoader.getSystemResource("ChatApplication/icon/phone-call.png"));
        Image i11 = i10.getImage().getScaledInstance(25,25,Image.SCALE_DEFAULT);
        ImageIcon i12 = new ImageIcon(i11);
        JLabel l6 = new JLabel(i12);
        l6.setBounds(360,18,30,30);
        p1.add(l6);

        ImageIcon i13 = new ImageIcon(ClassLoader.getSystemResource("ChatApplication/icon/three-dots.png"));
        Image i14 = i13.getImage().getScaledInstance(25,25,Image.SCALE_DEFAULT);
        ImageIcon i15 = new ImageIcon(i14);
        JLabel l7 = new JLabel(i15);
        l7.setBounds(410,20,13,25);
        p1.add(l7);



       a1 = new JPanel();
       a1.setBounds(5,75,440,570);
       a1.setBackground(Color.WHITE);
       a1.setFont(new Font("SAN_SARIF",Font.BOLD,16));

       f1.add(a1);


       t1 = new JTextField();
       t1.setBounds(5,655,310,40);
       t1.setBackground(Color.white);
       t1.setFont(new Font("SAN_SERIF",Font.PLAIN,16));
       f1.add(t1);
/*typing written*/
       t1.addKeyListener(new KeyAdapter(){
           public void keyPressed(KeyEvent ke){
               l4.setText("typing...");

               t.stop();

               typing = true;
           }

           public void keyReleased(KeyEvent ke){
               typing = false;

               if(!t.isRunning()){
                   t.start();
               }
           }
       });

       b1 = new JButton("Send");
       b1.setBounds(320,655,123,40);
       b1.setBackground(new Color(128, 255, 128));
       b1.setForeground(Color.BLACK);
       b1.setFont(new Font("SAN_SERIF",Font.BOLD,16));
       b1.addActionListener(this);/*past text*/
       f1.add(b1);
        /*frame size*/
       f1.getContentPane().setBackground(Color.WHITE);
       f1.setLayout(null);
       f1.setSize(450,700);/*screen size of gui*/
       f1.setLocation(100,100);/*set popup screen on window*/
       f1.setUndecorated(true);/*frame nav bar hide*/
       f1.setVisible(true);/*written at last*/
    }

    @Override
    public void actionPerformed(ActionEvent ae){
        /*text upend to textarea code*/
        try {
            String out = t1.getText();
            JPanel p2 = formatLabel(out);
            a1.setLayout(new BorderLayout());
            JPanel right = new JPanel(new BorderLayout());
            right.add(p2,BorderLayout.LINE_END);
            vertical.add(right);
            a1.add(vertical,BorderLayout.PAGE_START);
//            a1.add(p2);
            dout.writeUTF(out);
            t1.setText("");


        }catch (Exception e){
            System.out.println(e);
        }
    }
/*chat bubble*/
    public static JPanel formatLabel(String out){
        JPanel p3 = new JPanel();

        p3.setLayout(new BoxLayout(p3,BoxLayout.Y_AXIS));

        JLabel l1 =new JLabel("<html><p style = \"width: 150px\">"+out+"</p></html>");
        l1.setFont(new Font("Tahoma",Font.BOLD,12));
        l1.setBackground(new Color(128, 191, 255));
        l1.setOpaque(true);
        l1.setBorder(new EmptyBorder(15,15,15,50));


        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");

        JLabel l2 =new JLabel();
        l2.setText(sdf.format(cal.getTime()));

        p3.add(l1);
        p3.add(l2);
        return p3;

    }

    public static void main(String[] args) {
        new Server().f1.setVisible(true);
        String msginput = "";
        try{
            skt = new ServerSocket(6000);
            while (true){
            s = skt.accept();
            din =new DataInputStream(s.getInputStream());
            dout = new DataOutputStream(s.getOutputStream());

            while (true) {
                msginput = din.readUTF();
                JPanel p2 = formatLabel(msginput);

                JPanel left = new JPanel(new BorderLayout());
                left.add(p2, BorderLayout.LINE_START);
                vertical.add(left);
                f1.validate();
            }

            }



        }catch(Exception e){}
    }


}
