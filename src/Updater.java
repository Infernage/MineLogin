/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.zip.*;
import javax.swing.JOptionPane;
/**
 *
 * @author Reed
 */
public class Updater extends Thread{
    private String link;
    private String path;
    private String name;
    //Creamos el actualizador con el link de la nueva versión como parámetro
    public Updater (String host){
        link = host;
    }
    private String getFileName(URL url) {
        String fileName = url.getFile();
        return fileName.substring(fileName.lastIndexOf('/') + 1);
    }
    //Método de descarga
    private void descargar(){
        RandomAccessFile file = null;
        InputStream stream = null;
        try {
            URL url = new URL(link);
            // Abrimos la conexión a la URL
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            // Especificamos la porción que queremos descargar
            connection.setRequestProperty("Range", "bytes=" + 0 + "-");
            // Conectamos al servidor
            connection.connect();
            String path = System.getProperty("user.home") + "\\Desktop";
            // Abrimos el archivo
            name = getFileName(url);
            file = new RandomAccessFile(path + "\\" + name, "rw");
            file.seek(0);
            //Obtenemos el stream de la URL
            stream = connection.getInputStream();
            //Creamos un array de bytes
            byte buffer[] = new byte[1024];
            //Indicamos cuantos se van a leer cada vez
            int read = stream.read(buffer);
            while (read > 0) {
                // Escribimos los bytes en el fichero
                file.write(buffer, 0, read);
                read = stream.read(buffer);
            }
            //Cerramos los sockets
            stream.close();
            file.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }
    //Método de descompresión
    private void descomprimir(){
        //Creamos la carpeta donde van a ir los archivos
        path = System.getProperty("user.home") + "\\Desktop\\Minecraft Update";
        File mine = new File(path);
        mine.mkdirs();
        try {
            //Abrimos el comprimido
            ZipInputStream zip = new ZipInputStream(new FileInputStream(new File(System.getProperty("user.home") + "\\Desktop\\" + name)));
            ZipEntry entrada;
            //Vamos cogiendo cada vez la siguiente entrada
            while ((entrada = zip.getNextEntry()) != null){
                boolean direc = false;//Comprobamos si es un directorio o no
                //Separamos los nombres por el separador "/"
                StringTokenizer token = new StringTokenizer(entrada.getName(), "/");
                //Creamos una lista con todo el path
                List<String> lista = new ArrayList<String>();
                //Separamos el path y lo añadimos a la lista
                while (token.hasMoreTokens()){
                    String A = token.nextToken();
                    if (A != null){
                        lista.add(A);
                    }
                }
                //Comprobamos si es un fichero o un directorio
                if (entrada.getName().endsWith("/")){
                    direc = true;
                }
                //Cambiamos el tipo de separación de carpetas
                StringBuilder build = new StringBuilder(path);
                for (int i = 0; i < lista.size(); i++){
                    build.append("\\").append(lista.get(i));
                }
                String filero = build.toString();
                File fich = new File(filero);
                //Si es un directorio, creamos la carpeta
                if (direc){
                    fich.mkdirs();
                } else{//Sino, traspasamos el archivo a su destino
                    FileOutputStream salida = new FileOutputStream(fich);
                    int leido;
                    byte [] buffer = new byte[4096];
                    while ((leido = zip.read(buffer)) > 0){
                        salida.write(buffer, 0, leido);
                    }
                    //Cerramos todos los escuchadores
                    salida.close();
                }
                zip.closeEntry();
            }
            zip.close();
        } catch (IOException ex) {
            
        }
    }
    //Método de ejecución de Main Instalador
    private void exec(){
        try {
            //Por último ejecutamos el nuevo instalador
            Process inst = Runtime.getRuntime().exec("java -jar " + name + "Install.jar");
        } catch (IOException ex) {
            
        } finally{
            //Salimos de la JVM
            System.exit(0);
        }
    }
    //Método de ejecución
    @Override
    public void run(){
        descargar();//Descargamos los archivos necesarios
        descomprimir();//Los descomprimimos
        exec();//Ejecutamos el main
    }
}
