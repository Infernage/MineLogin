
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Reed
 */
public class CHLG extends Thread{
    private JTextArea area;
    public CHLG (JTextArea ar){
        area = ar;
    }
    private void inicializar(){
        try{
            //Leemos el txt en formato Unicode
            FileInputStream in = null;
            if (Mainclass.OS.equals("windows")){
                in = new FileInputStream(System.getProperty("user.home") + "\\AppData\\Roaming\\.minecraft\\mods\\CHLog.txt");
            } else if (Mainclass.OS.equals("linux")){
                in = new FileInputStream(System.getProperty("user.home") + "/.minecraft/mods/CHLog.txt");
            }
            InputStreamReader isr = new InputStreamReader (in, "UTF8");
            BufferedReader bf = new BufferedReader (isr);
            String temp;
            area.append(bf.readLine());
            while ((temp = bf.readLine()) != null){
                area.append("\n");
                area.append(temp);
            }
            in.close();
            isr.close();
            bf.close();
        }catch(IOException e){
            JOptionPane.showMessageDialog(null, "Error al abrir el ChangeLog.");
            File error = null;
            if (Mainclass.OS.equals("windows")){
                error = new File (System.getProperty("user.home") + "\\AppData\\Roaming\\Data\\LogEr.txt");
            } else if (Mainclass.OS.equals("linux")){
                error = new File (System.getProperty("user.home") + "/Data/LogEr.txt");
            }
            if (!error.exists()){
                try {
                    error.createNewFile();
                } catch (IOException exe) {
                }
            }
                try{
                    PrintWriter pw = new PrintWriter (error);
                    pw.print(e.getMessage());
                    pw.println();
                    pw.close();
                } catch (IOException exe){
                }
        }
    }
    @Override
    public void run(){
        inicializar();
    }
}
