import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JWindow;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

public class GuiDemo5 {
    private JFrame frame;
    private JTextArea textArea;
    private JLabel label;
    private JScrollPane scrollPane;
    private JPanel panel;
    private JButton button1;
    private JButton button2;
    private JButton button3;
    private JButton button4;
    private JButton button5;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(()->new GuiDemo5().showWelcomeScreen());
    }

    public void go(){
        frame=new JFrame();
        frame.setTitle("Daily Journaling Application");
        frame.setSize(800,750);
        frame.setResizable(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);

        label=new JLabel();
        label.setText("Write Your Notes Daily");
        label.setPreferredSize(new Dimension(350,60));
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        label.setFont(new Font("Times New Roman", Font.PLAIN, 40));
        label.setForeground(Color.BLUE);
        label.setHorizontalAlignment(SwingConstants.CENTER);
        frame.getContentPane().add(BorderLayout.NORTH,label);

        textArea=new JTextArea();
        textArea.setFocusable(true);
        textArea.setEditable(true);
        textArea.setEnabled(true);
        textArea.setCursor(Cursor.getPredefinedCursor(Cursor.TEXT_CURSOR));
        textArea.setCaretColor(Color.YELLOW);
        textArea.setBackground(Color.LIGHT_GRAY);
        textArea.setForeground(Color.BLUE);
        textArea.setAlignmentX(Component.CENTER_ALIGNMENT);
        textArea.setAlignmentY(Component.CENTER_ALIGNMENT);
        textArea.setFont(new Font("Times New Roman",Font.PLAIN,24));
        textArea.setBorder(new EmptyBorder(10,10,10,10));
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setText("Enter your text here...");
        textArea.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(FocusEvent event){
                if(textArea.getText().equals("Enter your text here...")){
                    new javax.swing.Timer(1000, new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            if(textArea.getText().equals("Enter your text here...")) 
                                textArea.setText("");
                            ((javax.swing.Timer) e.getSource()).stop();
                        }        
                    }).start();
                }
                textArea.setBorder(BorderFactory.createLineBorder(Color.GREEN,2));
            }
            public void focusLost(FocusEvent event){
                textArea.setBorder(BorderFactory.createCompoundBorder
                    (BorderFactory.createLineBorder(new Color(49,140,38),24),
                    new EmptyBorder(10,10,10,10)));
            }
        });
              
        scrollPane=new JScrollPane(textArea);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        frame.getContentPane().add(BorderLayout.CENTER,scrollPane);

        panel=new JPanel();
        panel.setLayout(new FlowLayout(FlowLayout.CENTER,10,10));
        Random random=new Random();
        int red=random.nextInt(256);
        int green=random.nextInt(256);
        int blue=random.nextInt(256);
        panel.setBackground(new Color(red,green,blue));

        button1=new JButton("Save");
        button2=new JButton("Open Today's");
        button4=new JButton("Open");
        button5=new JButton("Clear");
        button3=new JButton("Exit");
        Font buttonFont=new Font("Times New Roman", Font.BOLD, 16);
        JButton[] buttons={button1,button2,button4,button5,button3};
        for(JButton btn:buttons){
            btn.setFont(buttonFont);
            btn.setPreferredSize(new Dimension(130,60));
            panel.add(btn);
        }

        button1.addActionListener(new ButtonSave());
        button2.addActionListener(new ButtonOpen());
        button4.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser=new JFileChooser();
                int result=fileChooser.showOpenDialog(frame);
                fileChooser.setFileFilter(new FileNameExtensionFilter("Text Files","txt"));
                if(result==JFileChooser.APPROVE_OPTION){
                    File selected=fileChooser.getSelectedFile();
                    button1.setVisible(false);
                    button2.setVisible(false);
                    openFile(selected.toString());
                }    
            }
        });
        button5.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                textArea.setText("");
                button1.setVisible(true);
                button2.setVisible(true);
            }
            
        });
        button3.addActionListener((event)->{
            int result=JOptionPane.showConfirmDialog(frame,
                "Do you really want to exit?",
                "Exit Confirmation",JOptionPane.YES_NO_OPTION);
            if(result==JOptionPane.YES_OPTION){
                frame.dispose();
                System.exit(0);
            }
        });

        frame.getContentPane().add(BorderLayout.SOUTH,panel);
        frame.setVisible(true);
    }
    private void showWelcomeScreen(){
        JWindow welcomeWindow=new JWindow();
        JPanel panel=new JPanel();
        panel.setBackground(new Color(255,248,220));

        JLabel label=new JLabel("<html><div style='text-align:center;'>"+
        "<br>"+"<h1>Welcome to your Daily Notes App </h1>"+"<br>"+
        "<p style='font-size: 18px;'>Created by Partha Sarathi Basu</p>"+
        "</div></html>",SwingConstants.CENTER);
        label.setFont(new Font("Times New Roman",Font.PLAIN,20));
        label.setForeground(new Color(80,40,40));

        panel.add(label);

        welcomeWindow.getContentPane().add(panel);
        welcomeWindow.setSize(450,200);
        welcomeWindow.setLocationRelativeTo(null);
        welcomeWindow.setVisible(true);

        new javax.swing.SwingWorker<Void,Void>() {
            @Override
            protected Void doInBackground() throws Exception {
                Thread.sleep(3000);
                return null;
            }
            protected void done(){
                welcomeWindow.setVisible(false);
                welcomeWindow.dispose();
                go();
            }
        }.execute();
    }
    private String dailyFile(){
        LocalDateTime date=LocalDateTime.now();
        DateTimeFormatter formatter=DateTimeFormatter.ofPattern("dd-MMM-yyyy");
        String now=date.format(formatter);
        String filename="./data/Notes_"+now+".txt";
        return filename;
    }
    class ButtonSave implements ActionListener {
        public void actionPerformed(ActionEvent event){
            String str=textArea.getText();
            String filename=dailyFile();
            try {
                File file=new File(filename);
                if(!file.getParentFile().exists()){
                    file.getParentFile().mkdirs();
                }
                FileWriter write=new FileWriter(filename);
                BufferedWriter buffer=new BufferedWriter(write);
                buffer.write(str);
                buffer.flush();
                buffer.close();
                System.out.println("File saved as "+filename);
            } catch (IOException e) {
                e.printStackTrace();
            }
            JOptionPane.showMessageDialog(frame,"File saved successfully.");
        }
    }
    public void openFile(String filename){
        textArea.setText("");
        try{
            FileReader reader=new FileReader(filename);
            BufferedReader buffer=new BufferedReader(reader);
            String str;
            StringBuilder content=new StringBuilder();
            while((str=buffer.readLine())!=null){
                content.append(str).append("\n");
            }
            textArea.setText(content.toString());
            buffer.close();
        }catch(IOException e){
            e.printStackTrace();
        }       
    }
    class ButtonOpen implements ActionListener{
        public void actionPerformed(ActionEvent event){
            String filename=dailyFile();
            try{
                File file=new File(filename);
                if(!file.exists()){
                    JOptionPane.showMessageDialog(frame,"File does not exist!");
                    return;
                }
                openFile(filename);
            }catch(Exception e){
                e.printStackTrace();
            }
        }
    }
}