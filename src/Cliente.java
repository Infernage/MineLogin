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
    private Socket socket;//Socket de escucha al servidor
    private DataInputStream input;//Entrada de datos
    private DataOutputStream output;//Salida de datos
    private boolean exit = false;//Control del bucle del run
    public boolean connected = false;//Control del estado de conexion
    private String host;//Nombre del host del servidor
    private List<String> lista;//Lista de informacion de la entrada de datos del servidor
    private JLabel info, state;//Etiquetas para indicar el estado de la actualizacion
    private JButton temp;
    //Creamos el cliente
    public Cliente(JLabel A, JLabel B, JButton C){
        info = B;
        state = A;
        temp = C;
        state.setText("Comprobando actualizaciones...");
        host = "minechinchas.no-ip.org";
        lista = new ArrayList<String>();
    }
    //Método para salir del bucle
    public void salir(){
        exit = true;
    }
    //Método de envío de versión
    private void enviar(){
        try {
            output.writeUTF(Mainclass.version);//Versión
        } catch (IOException ex) {
            state.setText("ERROR!");
            info.setText(ex.getMessage());
            exit = true;
        }
    }
    //Método actualizador
    private void actualizar(){
        String link = lista.get(1);//Link de la nueva versión
        String version = lista.get(2);//Número de la nueva versión
        state.setText("Actualizando a la versión " + version);
        Updater update = new Updater(link);//Creamos el actualizador
        update.start();//Lo ejecutamos
    }
    //Método de comprobación de mensajes
    private void comprobar(String msg){
        if (msg.equals("true")){//Si el mensaje es true, indica que ya está actualizado
            state.setForeground(Color.GREEN);
            state.setText("No existen actualizaciones disponibles");
            salir();//Salimos del bucle
            temp.setEnabled(true);
        } else if (msg.equals("false")){//Si el mensaje es false, indica que hay nuevas versiones
            lista.add(msg);//Añadimos los datos a la lista de datos
            /*Los datos proporcionados por el servidor son:
             * 1º-"false"/"true" (Estado de la versión: Actualizada/No actualizada)
             * 2º-link de la version a descargar (Solo si la versión es antigua)
             * 3º-nombre de la versión (Solo si la versión es antigua)
             * 4º-"exit" (Indica que es el último mensaje)
             */
            temp.setEnabled(false);
        } else if (msg.equals("exit")){//Si es el último mensaje, salimos del bucle y actualizamos
            exit = true;
            actualizar();
        }
    }
    //Método de creación de escucha
    private boolean crear(){
        boolean res = true;
        try {
            socket = new Socket(host, 30001); //Creamos el Socket al host en el puerto 30001
            if (!socket.isConnected()){ //Si no está conectado, devolvemos false
                res = false;
                exit = true;
                state.setForeground(Color.red);
                state.setText("El servidor no está conectado.");
                socket.close();
            } else{ //Si está conectado cambiamos el estado
                connected = true;
            }
            if (connected){ //Creamos la lectura y escritura de datos del servidor solo si está conectado
                input = new DataInputStream(socket.getInputStream());
                output = new DataOutputStream(socket.getOutputStream());
            }
        } catch (IOException ex) { //Si se produce algún error, devolvemos false
            res = false;
            state.setForeground(Color.red);
            state.setText("ERROR! No se ha podido contactar con el servidor.");
            info.setForeground(Color.red);
            info.setText(ex.getMessage());
            exit = true;
        }
        return res;
    }
    //Método de ejecución
    @Override
    public void run(){
        boolean send = crear(); //Llamamos al método crear para inicializar la escucha al servidor
        if (send){//Solo si se ha creado la escucha sin problemas, se envían los datos de la versión actual
            enviar();
        }
        try {
            Thread.sleep(3000);
        } catch (InterruptedException ex) {
            
        }
        while (!exit){
            try {//Leemos los datos que nos envía el servidor
                String msg = input.readUTF();
                if (msg != null){ //Si es distinto de null, comprobamos el mensaje
                    comprobar(msg);
                }
            } catch (IOException ex) {
                state.setForeground(Color.red);
                state.setText("ERROR! No se ha podido recibir la información del servidor.");
                info.setForeground(Color.red);
                info.setText(ex.getMessage());
                exit = true;
            }
        }
        if (connected){ //Cerramos las conexiones una vez acabado el bucle solo si ha estado conectado
            try {
                socket.close();
                input.close();
                output.close();
            } catch (IOException ex) {
                info.setForeground(Color.red);
                info.setText(ex.getMessage());
            }
        }
        temp.setEnabled(true);
    }
}
