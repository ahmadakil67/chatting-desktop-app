package com.javaProject;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Client extends JFrame implements ActionListener {
    JPanel p1;
    JTextField t1;
    JButton b1;
    static JPanel a1;
    static Box vertical = Box.createVerticalBox();

    static Socket s;
    static DataInputStream din;
    static DataOutputStream dout;
    boolean typing;

    Client(){
        p1=new JPanel();
        p1.setLayout(null);
        p1.setBackground(new Color(7, 94, 84));
        p1.setBounds(0, 0, 450, 70);
        add(p1);

        //ImageIcon i1=new ImageIcon(ClassLoader.getSystemResource("Java Project/src/com/javaProject/3.png"));
        //Image i2 = i1.getImage().getScaledInstance(30, 30, Image.SCALE_DEFAULT);
        //ImageIcon i3 = new ImageIcon(i2);
        JLabel l1 = new JLabel();
        l1.setBounds(5, 17, 30, 30);
        p1.add(l1);




        JLabel l3 = new JLabel("Srabony");
        l3.setFont(new Font("SAN_SERIF", Font.BOLD, 15));
        l3.setForeground(Color.WHITE);
        l3.setBounds(20, 15, 100, 18);
        p1.add(l3);

        JLabel l4 = new JLabel("Active Now");
        l4.setFont(new Font("SAN_SERIF", Font.PLAIN, 10));
        l4.setForeground(Color.WHITE);
        l4.setBounds(20, 35, 100, 20);
        p1.add(l4);


        Timer t = new Timer(1, new ActionListener(){
            public void actionPerformed(ActionEvent ae){
                if(!typing){
                    l4.setText("Active Now");
                }
            }
        });

        t1 = new JTextField();
        t1.setBounds(3, 430, 220, 30);
        t1.setFont(new Font("SAN_SERIF", Font.PLAIN, 16));
        add(t1);

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
        b1.setBounds(225, 430, 100, 30);
        b1.setBackground(new Color(7, 94, 84));
        b1.setForeground(Color.WHITE);
        b1.setFont(new Font("SAN_SERIF", Font.PLAIN, 16));
        b1.addActionListener(this);
        add(b1);

        a1=new JPanel();
        a1.setBounds(5, 75, 440, 570);
        a1.setBackground(Color.WHITE);
        //a1.setFont(new Font("SAN_SERIF",Font.PLAIN,16));
        add(a1);
 
        //getContentPane().setBackground(Color.YELLOW);
        setLayout(null);
        setSize(350,500);
        setLocation(700,200);
       // setResizable(false);
        setTitle("Magnet Chat");
        setVisible(true);

    }
    public void actionPerformed(ActionEvent ae) {
        try {
            String out = t1.getText();
            JPanel p2 = formatLabel(out);


            a1.setLayout(new BorderLayout());

            JPanel right = new JPanel(new BorderLayout());
            right.add(p2, BorderLayout.LINE_END);
            vertical.add(right);
            vertical.add(Box.createVerticalStrut(15));

            a1.add(vertical, BorderLayout.PAGE_START);

            //a1.add(p2);
            dout.writeUTF(out);
            t1.setText("");
        }catch (Exception e){}
    }
    public static JPanel formatLabel(String out) {
        JPanel p3 = new JPanel();
        JLabel l1 = new JLabel("<html><p style = \"width : 150px\">"+out+"</p></html>");
        p3.setLayout(new BoxLayout(p3, BoxLayout.Y_AXIS));

        l1.setFont(new Font("Tahoma", Font.PLAIN, 16));
        l1.setBackground(new Color(37, 211, 54));
        l1.setOpaque(true);
        l1.setBorder(new EmptyBorder(15,15,15,50));

        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");

        JLabel l2 = new JLabel();
        l2.setText(sdf.format(cal.getTime()));




        p3.add(l1);
        p3.add(l2);
        return p3;
    }


    public static void main(String[] args) {
        new Client().setVisible(true);

        try{

            s = new Socket("127.0.0.1", 6001);
            din  = new DataInputStream(s.getInputStream());
            dout = new DataOutputStream(s.getOutputStream());

            String msginput = "";

            while(true){
                a1.setLayout(new BorderLayout());
                msginput = din.readUTF();
                JPanel p2 = formatLabel(msginput);
                JPanel left = new JPanel(new BorderLayout());
                left.add(p2, BorderLayout.LINE_START);

                vertical.add(left);
                vertical.add(Box.createVerticalStrut(15));
                a1.add(vertical, BorderLayout.PAGE_START);
                //f1.validate();
            }

        }catch(Exception e){}
    }
}



