

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author Reed
 */
public class Mainclass {
    //Versión
    public final static String title = "Login Minecraft";
    public final static String version = "V3.1.0";
    public static String OS = System.getProperty("os.name");
    public static Map<String, Thread> hilos;
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        hilos = new HashMap<String, Thread>();
        StringTokenizer token = new StringTokenizer(OS, " ");
        OS = token.nextToken().toLowerCase();
        token = null;
        if (OS.equals("windows")){
            //Creamos las variables
            StringBuilder path = new StringBuilder(System.getProperty("user.home"));//Path del sistema
            path.append("\\AppData\\Roaming\\Data");//Agregamos los datos
            String name = "data.cfg", booleano = "boolean.txt";//Nombre de ficheros de datos
            String pss = "My Pass Phrase";//Nombre de frase pass
            String boo = path.toString() + "\\" + booleano;//Path del fichero booleano
            File fich = new File(path.toString());
            fich.mkdirs();
            path.append("\\").append(name);
            File fichero = new File (boo);
            //Controlamos si es la primera vez que se ejecuta y si hay registro o no
            if (fichero.exists()){
                try{
                    //Leemos el fichero si existe
                    BufferedReader bf = new BufferedReader (new FileReader (fichero));
                    boo = bf.readLine();
                } catch (IOException e){
                    
                }
                if (boo.equals("true")){
                    //Si en el fichero hay un true, significa que ya se ha registrado y abrimos la Vista2
                Vista2 vista = new Vista2(pss);
                vista.setIconImage(new ImageIcon(System.getProperty("user.home") + "\\AppData\\Roaming\\.minecraft\\5547.png").getImage());
                vista.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                vista.setTitle(title + " " + version);
                vista.setLocationRelativeTo(null);
                vista.setVisible(true);
                vista.pack();
                } else{
                    //Sino, no se ha registrado, y abrimos la Vista
                Vista.main(path.toString(), pss, fichero.getAbsolutePath());
                }
            } else {
                //Si no existe el fichero, lo creamos y escribimos en él false
                try{
                    fichero.createNewFile();
                    PrintWriter pw = new PrintWriter(fichero);
                    pw.print("false");
                    pw.close();
                } catch (IOException e){
                    
                }
                //Abrimos Vista
                Vista.main(path.toString(), pss, fichero.getAbsolutePath());
            }
        } else if (OS.equals("linux")){
            //Creamos las variables
            StringBuilder path = new StringBuilder(System.getProperty("user.home"));//Path del sistema
            path.append("/Data");//Agregamos los datos
            String name = "data.cfg", booleano = "boolean.txt";//Nombre de ficheros de datos
            String pss = "My Pass Phrase";//Nombre de frase pass
            String boo = path.toString() + "/" + booleano;//Path del fichero booleano
            File fich = new File(path.toString());
            fich.mkdirs();
            path.append("/").append(name);
            File fichero = new File (boo);
            //Controlamos si es la primera vez que se ejecuta y si hay registro o no
            if (fichero.exists()){
                try{
                    //Leemos el fichero si existe
                    BufferedReader bf = new BufferedReader (new FileReader (fichero));
                    boo = bf.readLine();
                } catch (IOException e){
                    
                }
                if (boo.equals("true")){
                    //Si en el fichero hay un true, significa que ya se ha registrado y abrimos la Vista2
                Vista2 vista = new Vista2(pss);
                vista.setIconImage(new ImageIcon(System.getProperty("user.home") + "/.minecraft/5547.png").getImage());
                vista.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                vista.setTitle(title + " " + version);
                vista.setLocationRelativeTo(null);
                vista.setVisible(true);
                vista.pack();
                } else{
                    //Sino, no se ha registrado, y abrimos la Vista
                Vista.main(path.toString(), pss, fichero.getAbsolutePath());
                }
            } else {
                //Si no existe el fichero, lo creamos y escribimos en él false
                try{
                    fichero.createNewFile();
                    PrintWriter pw = new PrintWriter(fichero);
                    pw.print("false");
                    pw.close();
                } catch (IOException e){
                    
                }
                //Abrimos Vista
                Vista.main(path.toString(), pss, fichero.getAbsolutePath());
            }
        }
    }
}
