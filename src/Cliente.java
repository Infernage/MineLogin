/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
import java.awt.Color;
import java.io.*;
import java.net.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.*;
import javax.swing.*;
/**
 *
 * @author Reed
 */
public class Cliente extends Thread{
    private BufferedReader input;//Entrada de datos
    private boolean exit = false;//Control del bucle del run
    private boolean actualize = false; //Control del actualizador
    private boolean error = false;//Control de errores
    private List<String> lista;//Lista de lineas de comando del servidor
    private List<String> versiones;//Lista de versiones del servidor
    private List<String> links;// Lista de links del servidor
    private JLabel info, state;//Etiquetas para indicar el estado de la actualizacion
    private JButton play, MC, MS;//Botón jugar
    private JFrame fr;//Ventana
    //Creamos el cliente
    public Cliente(JLabel A, JLabel B, JButton C, URL url, JFrame fra, JButton D, JButton E){
        info = B;
        state = A;
        play = C;
        MS = D;
        MC = E;
        fr = fra;
        state.setText("Comprobando actualizaciones...");
        lista = new ArrayList<String>();
        versiones = new ArrayList<String>();
        links = new ArrayList<String>();
        try {
            input = new BufferedReader(new InputStreamReader(url.openStream()));
        } catch (IOException ex) {
            state.setForeground(Color.red);
            state.setText("ERROR!");
            info.setForeground(Color.red);
            info.setText(ex.getMessage());
            salir();
            error = true;
        }
    }
    //Método para salir del bucle
    public void salir(){
        exit = true;
    }
    //Método actualizador
    private void actualizar(int i, String version){
        Vista2.jProgressBar1.setVisible(true);
        String link = links.get(i);//Link de la nueva versión
        boolean exit = false;
        try {
            URL url = new URL(link);
            BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
            String lin;
            while (((lin = in.readLine()) != null) && !exit){
                if (lin.contains("http://i.minus.com/") && lin.contains(".zip")){
                    StringTokenizer token = new StringTokenizer(lin, "\"");
                    while (token.hasMoreTokens()){
                        String te = token.nextToken();
                        if (te.contains("http://i.minus.com/")){
                            link = te;
                            exit = true;
                        }
                    }
                }
            }
        } catch (Exception ex) {
            state.setForeground(Color.red);
            state.setText("ERROR!");
            info.setForeground(Color.red);
            info.setText(ex.getMessage());
            salir();
            error = true;
        }
        state.setText("Actualizando a la versión " + version);
        Updater update = new Updater(link);//Creamos el actualizador
        update.start();//Lo ejecutamos
        play.setEnabled(false);
        MC.setEnabled(false);
        MS.setEnabled(false);
    }
    //Método para procesar los links
    private void procesar(){
        for (int i = 0; i < lista.size(); i++){
            StringTokenizer toke = new StringTokenizer(lista.get(i), "<>\"");
            while (toke.hasMoreTokens()){
                String temp = toke.nextToken();
                if (temp.contains("minus.com")){
                    links.add(temp);
                }
                if (temp.contains(".") && !temp.contains("minus.com")){
                    versiones.add(temp);
                }
            }
        }
        for (int i = 0; i < versiones.size(); i++){
            StringTokenizer token = new StringTokenizer(versiones.get(i), ".");
            String main = Mainclass.version;
            main = main.substring(1);
            StringTokenizer actual = new StringTokenizer(main, ".");
            while (token.hasMoreTokens() && !exit && !actualize){
                int V = Integer.parseInt(token.nextToken());
                int V2 = Integer.parseInt(actual.nextToken());
                if (V > V2){
                    actualize = true;
                    fr.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
                    actualizar(i, "V" + versiones.get(i));
                } else if (V < V2){
                    salir();
                }
            }
        }
        if (!actualize){
            state.setForeground(Color.GREEN);
            state.setText("No hay nuevas versiones disponibles");
        }
        salir();
    }
    //Método de ejecución
    @Override
    @SuppressWarnings("empty-statement")
    public void run(){
        try {
            Thread.sleep(3000);
        } catch (InterruptedException ex) {
            
        }
        while (!exit){
            try {//Leemos los datos que nos envía el servidor
                String msg;
                while((msg = input.readLine()) != null){ //Si es distinto de null, comprobamos el mensaje
                    if (msg.contains("minus.com")){
                        lista.add(msg);
                    }
                }
                procesar();
            } catch (IOException ex) {
                if (!error){
                    state.setForeground(Color.red);
                    state.setText("ERROR! No se ha podido recibir la información del servidor.");
                    info.setForeground(Color.red);
                    info.setText(ex.getMessage());
                    error = true;
                }
            }
        }
        try {
            input.close();
        } catch (Exception ex) {
            if (!error){
                state.setForeground(Color.red);
                state.setText("ERROR!");
                info.setForeground(Color.red);
                info.setText(ex.getMessage());
                error = true;
            }
        }
        if (!actualize){
            play.setEnabled(true);
            MC.setEnabled(true);
            MS.setEnabled(true);
        }
    }
}
