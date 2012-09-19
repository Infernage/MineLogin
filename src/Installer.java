
import java.io.IOException;
import javax.swing.JOptionPane;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Reed
 */
public class Installer extends Thread{
    private String com;
    public Installer (String command){
        com = command;
    }
    @Override
    public void run(){
        try{
            Process inst = Runtime.getRuntime().exec(com);
        } catch (IOException e){
            System.out.println(e.getMessage());
        } finally{
            System.out.println("Exiting. Installation starts now...");
            System.exit(0);
        }
    }
}
