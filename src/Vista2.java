

import java.awt.Color;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.sound.sampled.*;
import java.io.*;
import java.util.Calendar;
import java.util.GregorianCalendar;
import javax.swing.*;
import java.awt.event.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Reed
 */
public class Vista2 extends javax.swing.JFrame {
    private File datos;
    private String pass;
    private File error;
    private String args;
    /**
     * Creates new form Vista2
     */
    public Vista2(String P) {
        pass = P;
        //Asignamos el fondo al Panel
        setContentPane(new Background());
        initComponents();
        jProgressBar1.setVisible(false);
        inicializar();
        this.setLocationRelativeTo(null);
        jButton3.setFocusPainted(false);
        jButton3.setContentAreaFilled(false);
        jButton4.setFocusPainted(false);
        jButton4.setContentAreaFilled(false);
        jButton7.setFocusPainted(false);
        jButton7.setContentAreaFilled(false);
        jButton8.setFocusPainted(false);
        jButton8.setContentAreaFilled(false);
        datos = new File(System.getProperty("user.home") + "\\AppData\\Roaming");
        error = new File(System.getProperty("user.home") + "\\AppData\\Roaming\\Data\\LogEr.txt");
        File rem = new File (datos.getAbsolutePath() + "\\Data\\RMB.txt");
        //Controlamos las pestañas de recordar
        if (rem.exists()){
            try{
                BufferedReader bf = new BufferedReader (new FileReader (rem));
                String temp = bf.readLine();
                String temp2 = bf.readLine();
                StringECP t = new StringECP(P);
                File te = new File (datos.getAbsolutePath() + "\\Data\\data.cfg");
                bf.close();
                if (temp.equals("true")){
                    //Si se seleccionó en un principio la pestaña, se deja seleccionada y se recuerda el usuario
                    jCheckBox1.setSelected(true);
                    if (te.exists()){
                        //Si el fichero de datos existe, se recuerda el usuario
                        bf = new BufferedReader (new FileReader (te));
                        temp = bf.readLine();
                        bf.close();
                        if (temp != null){
                            String A = t.decrypt(temp);
                            jTextField1.setText(A);
                        }
                    }
                }
                if (temp2.equals("true")){
                    //Si se seleccionó en un principio la pestaña, se deja seleccionada y se recuerda la contraseña
                    jCheckBox2.setSelected(true);
                    bf = new BufferedReader (new FileReader (te));
                    temp = bf.readLine();
                    temp2 = bf.readLine();
                    bf.close();
                    if (temp2 != null){
                        String A = t.decrypt(temp2);
                        jPasswordField1.setText(A);
                    }
                }
            } catch (IOException e){
                
            }
        }
        //Creamos el cliente del actualizador
        try{
            URL url = new URL("http://minechinchas.blogspot.com/2012/09/downloads.html");
            Cliente client = new Cliente(jLabel1, jLabel5, jButton5, url, this);
            client.start();
        } catch(Exception e){
            jLabel1.setForeground(Color.red);
            jLabel1.setText("ERROR!");
            jLabel5.setForeground(Color.red);
            jLabel5.setText(e.getMessage());
        }
        jTextArea1.getPreferredScrollableViewportSize().setSize(0, 0);
    }
    public static void per (int size, int downloaded){
        String temp = downloaded + "bytes/" + size + "bytes";
        jProgressBar1.setString(temp);
    }
    private void exit(){
        System.exit(0);
    }
    private void inicializar(){
        CHLG text = new CHLG(jTextArea1);
        text.start();
    }
    private void Play(){
        //Método del botón ¡Jugar!
        boolean open = false; boolean er = false; boolean remember = jCheckBox1.isSelected(); boolean rememberP = jCheckBox2.isSelected();
        //Comprobamos si el Recordar está activo y lo recordamos para un futuro
    File rem = new File(datos.getAbsolutePath() + "\\Data\\RMB.txt");
    if (!rem.exists()) {
            try {
              rem.createNewFile();
            }
            catch (IOException e)
            {
            }
        }
    try {
        //Escribimos los booleanos de recordar
      PrintWriter pw = new PrintWriter(rem);
      pw.print(remember);
      pw.println();
      pw.print(rememberP);
      pw.close();
      //Leemos el fichero de datos
      BufferedReader bf = new BufferedReader(new FileReader(new File(datos.getAbsolutePath() + "\\Data\\data.cfg")));
      String temp1 = null; String temp2 = null;
      //Creamos el desencriptador y comprobamos si el usuario y la contraseña coinciden
      StringECP crypt = new StringECP(this.pass);
      temp1 = crypt.decrypt(bf.readLine());
      temp2 = crypt.decrypt(bf.readLine());
      boolean open1 = temp1.equals(jTextField1.getText());
      boolean open2 = temp2.equals(new String(jPasswordField1.getPassword()));
      if ((open1) && (open2)) {
          //Si coinciden, se procede al Login
            open = true;
            args = temp1;
        }
    }
    catch (IOException ex) {
      JOptionPane.showMessageDialog(null, "Error al recoger los datos. Si el error persiste, contacte con Infernage.");
      if (!error.exists()) {
            try {
              error.createNewFile();
            }
            catch (IOException exe) {
            }
        }
      try {
        PrintWriter pw = new PrintWriter(error);
        pw.print(ex.getMessage());
        pw.println();
        pw.close();
      } catch (IOException exe) {
      }
    } catch (NullPointerException e) {
        //Controla si alguien que no tiene cuenta de usuario, intenta ejecutar el Botón
      JOptionPane.showMessageDialog(null, "No tienes cuenta de usuario creada.");
      open = false;
      er = true;
    }
    if (open)
    {
        //Si todo está correcto, se abre la ventana de elección de RAM
      RAM ram = new RAM(this, true);
      ram.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
      ram.setLocationRelativeTo(null);
      ram.setVisible(true);
      int ind = ram.devolver() + 1;
      ram.dispose();
      //Si no se elige ninguna opción, se ejecuta la existente por defecto
      if (ind == 0){
          ind = 2;
          JOptionPane.showMessageDialog(null, "No ha elegido ninguna opción. Se ejecutará la opción por defecto (1GB)");
      }
      Process minecraft;
      String user = new StringBuilder().append(System.getProperty("user.home")).append("\\AppData\\Roaming\\.minecraft\\bin\\minecraft.jar").toString();
      if (ind > 0) { //Según el caso, elegimos una u otra opción
        switch (ind) {
        case 1:
          try { 
              //Leemos el fichero de comando y creamos el ejecutable
              File bat = new File (System.getProperty("user.home") + "\\AppData\\Roaming\\.minecraft\\bin\\start.bat");
              BufferedReader bf = new BufferedReader (new FileReader (new File(System.getProperty("user.home") + "\\AppData\\Roaming\\.minecraft\\bin\\com.txt")));
              String comand = bf.readLine();
              bf.close();
              if (!bat.exists()){
                  bat.createNewFile();
              }
              //Escribimos el comando en el ejecutable
              PrintWriter pw = new PrintWriter (bat);
              pw.print("java -Xmx512m -Xms512m " + comand + args);
              pw.close();
              if (!bat.canExecute()){
                  bat.setExecutable(true);
              }
              //Lo ejecutamos con cmd
              minecraft = Runtime.getRuntime().exec(bat.getAbsolutePath());
          } catch (IOException e)
          {
            JOptionPane.showMessageDialog(null, "Error, no se ha podido ejecutar Minecraft.");
            if (!error.exists()) {
                  try {
                    error.createNewFile();
                  }
                  catch (IOException exe) {
                  }
              }
            try {
              PrintWriter pw = new PrintWriter(error);
              pw.print(e.getMessage());
              pw.println();
              pw.close();
            }
            catch (IOException exe) {
            }
            System.exit(0);
          }
            break;
        case 2:
          try {
              File bat = new File (System.getProperty("user.home") + "\\AppData\\Roaming\\.minecraft\\bin\\start.bat");
              BufferedReader bf = new BufferedReader (new FileReader (new File(System.getProperty("user.home") + "\\AppData\\Roaming\\.minecraft\\bin\\com.txt")));
              String comand = bf.readLine();
              bf.close();
              if (!bat.exists()){
                  bat.createNewFile();
              }
              PrintWriter pw = new PrintWriter (bat);
              pw.print("java -Xmx1024m -Xms1024m " + comand + args);
              pw.close();
              if (!bat.canExecute()){
                  bat.setExecutable(true);
              }
              minecraft = Runtime.getRuntime().exec(bat.getAbsolutePath());
          } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error, no se ha podido ejecutar Minecraft.");
            if (!error.exists()) {
                  try {
                    error.createNewFile();
                  }
                  catch (IOException exe) {
                  }
              }
            try {
              PrintWriter pw = new PrintWriter(error);
              pw.print(e.getMessage());
              pw.println();
              pw.close();
            }
            catch (IOException exe) {
            }
            System.exit(0);
          }
            break;
        case 3:
          try {
              File bat = new File (System.getProperty("user.home") + "\\AppData\\Roaming\\.minecraft\\bin\\start.bat");
              BufferedReader bf = new BufferedReader (new FileReader (new File(System.getProperty("user.home") + "\\AppData\\Roaming\\.minecraft\\bin\\com.txt")));
              String comand = bf.readLine();
              bf.close();
              if (!bat.exists()){
                  bat.createNewFile();
              }
              PrintWriter pw = new PrintWriter (bat);
              pw.print("java -Xmx2048m -Xms2048m " + comand + args);
              pw.close();
              if (!bat.canExecute()){
                  bat.setExecutable(true);
              }
              minecraft = Runtime.getRuntime().exec(bat.getAbsolutePath());
          } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error, no se ha podido ejecutar Minecraft.");
            if (!error.exists()) {
                  try {
                    error.createNewFile();
                  }
                  catch (IOException exe) {
                  }
              }
            try {
              PrintWriter pw = new PrintWriter(error);
              pw.print(e.getMessage());
              pw.println();
              pw.close();
            }
            catch (IOException exe) {
            }
            System.exit(0);
          }
            break;
        case 4:
          try {
              File bat = new File (System.getProperty("user.home") + "\\AppData\\Roaming\\.minecraft\\bin\\start.bat");
              BufferedReader bf = new BufferedReader (new FileReader (new File(System.getProperty("user.home") + "\\AppData\\Roaming\\.minecraft\\bin\\com.txt")));
              String comand = bf.readLine();
              bf.close();
              if (!bat.exists()){
                  bat.createNewFile();
              }
              PrintWriter pw = new PrintWriter (bat);
              pw.print("java -Xmx4096m -Xms4096m " + comand + args);
              pw.close();
              if (!bat.canExecute()){
                  bat.setExecutable(true);
              }
              minecraft = Runtime.getRuntime().exec(bat.getAbsolutePath());
          } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error, no se ha podido ejecutar Minecraft.");
            if (!error.exists()) {
                  try {
                    error.createNewFile();
                  }
                  catch (IOException exe) {
                  }
              }
            try {
              PrintWriter pw = new PrintWriter(error);
              pw.print(e.getMessage());
              pw.println();
              pw.close();
            }
            catch (IOException exe) {
            }
            System.exit(0);
          }
            break;
        }
        //Creamos el fichero Log si no existía
        File log = new File(new StringBuilder().append(System.getProperty("user.home")).append("\\AppData\\Roaming\\Data\\LogMC.cfg").toString());
        if (!log.exists()) {
              try {
                log.createNewFile();
              }
              catch (IOException e)
              {
              }
          }
        //Obtenemos la fecha, la hora y el usuario
        Calendar C = new GregorianCalendar();
        StringBuilder str = new StringBuilder("Connected at ");
        str.append(C.get(5)).append("/").append(C.get(2) + 1).append("/").append(C.get(1));
        str.append(" ").append(C.get(11)).append(":").append(C.get(12)).append(":").append(C.get(13));
        str.append(" with name of ").append(this.jTextField1.getText()).append("\n");
        try {
            //Escribimos en el Log indicándole que no sobreescriba lo anterior
          PrintWriter pw = new PrintWriter(new FileWriter(log, true));
          pw.println();
          pw.print(str.toString());
          pw.close();
        }
        catch (IOException e) {
        }
        System.exit(0);
      }
    } else if (!er) {
        //Si el nombre y la contraseña son inválidos
      JOptionPane.showMessageDialog(null, "Contraseña y/o nombre de usuario inválidos");
    }
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel3 = new javax.swing.JLabel();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jTextField1 = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jButton7 = new javax.swing.JButton();
        jButton8 = new javax.swing.JButton();
        jPasswordField1 = new javax.swing.JPasswordField();
        jCheckBox1 = new javax.swing.JCheckBox();
        jCheckBox2 = new javax.swing.JCheckBox();
        jLabel1 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jLabel6 = new javax.swing.JLabel();
        jProgressBar1 = new javax.swing.JProgressBar();

        jLabel3.setText("jLabel3");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMaximumSize(new java.awt.Dimension(829, 600));
        setPreferredSize(new java.awt.Dimension(829, 600));
        setResizable(false);

        jButton3.setBackground(new java.awt.Color(255, 255, 255));
        jButton3.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jButton3.setForeground(new java.awt.Color(255, 255, 0));
        jButton3.setText("Tengo cuenta de Minecraft");
        jButton3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(216, 216, 216), 3));
        jButton3.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jButton4.setBackground(new java.awt.Color(255, 255, 255));
        jButton4.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jButton4.setForeground(new java.awt.Color(216, 216, 216));
        jButton4.setText("Tengo Mineshafter");
        jButton4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 0), 3));
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jTextField1.setBackground(new java.awt.Color(174, 108, 17));
        jTextField1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextField1KeyPressed(evt);
            }
        });

        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Nombre de usuario:");

        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("Password:");

        jButton1.setBackground(new java.awt.Color(53, 46, 79));
        jButton1.setForeground(new java.awt.Color(51, 255, 255));
        jButton1.setText("Recordar datos");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setBackground(new java.awt.Color(53, 46, 79));
        jButton2.setForeground(new java.awt.Color(51, 255, 255));
        jButton2.setText("Cambiar contraseña");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton5.setBackground(new java.awt.Color(255, 153, 0));
        jButton5.setText("¡Jugar!");
        jButton5.setBorder(javax.swing.BorderFactory.createCompoundBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(51, 102, 255), 4), javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED)));
        jButton5.setEnabled(false);
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        jButton7.setFont(new java.awt.Font("Gulim", 1, 12)); // NOI18N
        jButton7.setForeground(new java.awt.Color(255, 0, 255));
        jButton7.setText("FAQ");
        jButton7.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 51), 3));
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });

        jButton8.setFont(new java.awt.Font("Gulim", 1, 12)); // NOI18N
        jButton8.setForeground(new java.awt.Color(0, 255, 238));
        jButton8.setText("Acerca de...");
        jButton8.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(102, 255, 102), 3));
        jButton8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton8ActionPerformed(evt);
            }
        });

        jPasswordField1.setBackground(new java.awt.Color(174, 108, 17));
        jPasswordField1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jPasswordField1KeyPressed(evt);
            }
        });

        jCheckBox1.setForeground(new java.awt.Color(255, 255, 255));
        jCheckBox1.setText("Recordar usuario");
        jCheckBox1.setContentAreaFilled(false);

        jCheckBox2.setForeground(new java.awt.Color(255, 255, 255));
        jCheckBox2.setText("Recordar contraseña");
        jCheckBox2.setContentAreaFilled(false);

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setPreferredSize(new java.awt.Dimension(38, 15));

        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setPreferredSize(new java.awt.Dimension(38, 15));

        jTextArea1.setEditable(false);
        jTextArea1.setColumns(20);
        jTextArea1.setLineWrap(true);
        jTextArea1.setRows(5);
        jTextArea1.setWrapStyleWord(true);
        jScrollPane1.setViewportView(jTextArea1);

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(0, 255, 255));
        jLabel6.setText("CHANGELOG");
        jLabel6.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255), 3));

        jProgressBar1.setOpaque(true);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(layout.createSequentialGroup()
                                    .addGap(44, 44, 44)
                                    .addComponent(jLabel4))
                                .addComponent(jLabel2))
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(jButton8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jButton7, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jCheckBox1))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jPasswordField1, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jCheckBox2))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jButton2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jProgressBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 321, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 321, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 321, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(300, 300, 300)
                        .addComponent(jButton3)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addGap(90, 90, 90)
                .addComponent(jButton4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel6)
                .addGap(161, 161, 161))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(109, 109, 109)
                .addComponent(jButton3)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addComponent(jButton4)
                        .addGap(246, 246, 246)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jCheckBox1)
                            .addComponent(jLabel2))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jPasswordField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jCheckBox2)
                            .addComponent(jLabel4))
                        .addGap(13, 13, 13)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addGroup(layout.createSequentialGroup()
                                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jButton7)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton8)))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(23, 23, 23)
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 225, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jProgressBar1, javax.swing.GroupLayout.DEFAULT_SIZE, 20, Short.MAX_VALUE)
                        .addGap(123, 123, 123))))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        try {
            //Preguntamos la palabra secreta
            String word = JOptionPane.showInputDialog("Introduzca la palabra secreta:");
            if (word != null){
            StringECP stringed = new StringECP(pass);
            BufferedReader bf = new BufferedReader(new FileReader(new File(datos.getAbsolutePath() + "\\Data\\data.cfg")));
            String str = bf.readLine();
            String str1 = bf.readLine();
            String str2 = bf.readLine();
            //Desencriptamos el nombre de usuario y la contraseña
            if (str == null){
                JOptionPane.showMessageDialog(null, "No hay datos de registro. Eso significa que tienes cuenta oficial de Minecraft o Mineshafter.");
            } else{
                if (word.equals(stringed.decrypt(str2))){
                    String A = stringed.decrypt(str);
                    String B = stringed.decrypt(str1);
                    JOptionPane.showMessageDialog(null, "Su nombre de cuenta es " + A + " con una contraseña de " + B);
                } else{
                    JOptionPane.showMessageDialog(null, "Error, la palabra secreta no es correcta.");
                }
            }
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Error al recoger los datos. Si el error persiste, contacte con Infernage.");
            if (!error.exists()){
                try {
                    error.createNewFile();
                } catch (IOException exe) {
                }
            }
                try{
                    PrintWriter pw = new PrintWriter (error);
                    pw.print(ex.getMessage());
                    pw.println();
                    pw.close();
                } catch (IOException exe){
                }
        }
        
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
            PassConfirm change = new PassConfirm(this, true, pass);
            change.setTitle("Cambiar contraseña");
            change.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
            change.setLocationRelativeTo(null);
            change.setVisible(true);
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        // TODO add your handling code here:
        //Botón de FAQ que crea la ventana de FAQ
        FAQ faq = new FAQ(this, true);
        faq.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        faq.setLocationRelativeTo(null);
        faq.setVisible(true);
    }//GEN-LAST:event_jButton7ActionPerformed

    private void jButton8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton8ActionPerformed
        // TODO add your handling code here:
        //Botón de Acerca de... que crea la ventana
        Acerca A = new Acerca(this, true);
        A.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        A.setLocationRelativeTo(null);
        A.setVisible(true);
    }//GEN-LAST:event_jButton8ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        // TODO add your handling code here:
        //Botón de jugar para los jugadores que no tienen minecraft o mineshafter
        Play();
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
        //Igual que el Botón de Minecraft de Vista
        RAM ram = new RAM(this, true);
        ram.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
    ram.setLocationRelativeTo(null);
    ram.setVisible(true);
    int ind = ram.devolver() + 1;
    ram.dispose();
    if (ind == 0){
        ind = 2;
        JOptionPane.showMessageDialog(null, "No ha elegido ninguna opción. Se ejecutará la opción por defecto (1GB)");
    }
    Process minecraft;
    String user = new StringBuilder().append(System.getProperty("user.home")).append("\\AppData\\Roaming\\.minecraft\\minecraft.jar").toString();
    if (ind > 0) {
      switch (ind) {
      case 1:
        try { minecraft = Runtime.getRuntime().exec(new StringBuilder().append("java -Xmx512M -Xms512M -jar ").append(user).toString());
        } catch (IOException e) {
          JOptionPane.showMessageDialog(null, "Error, no se ha podido ejecutar Minecraft.");
          if (!this.error.exists()) {
                try {
                  this.error.createNewFile();
                }
                catch (IOException exe) {
                }
            }
          try {
            PrintWriter pw = new PrintWriter(this.error);
            pw.print(e.getMessage());
            pw.println();
            pw.close();
          }
          catch (IOException exe) {
          }
          System.exit(0);
        }
          break;
      case 2:
        try {minecraft = Runtime.getRuntime().exec(new StringBuilder().append("java -Xmx1024M -Xms1024M -jar ").append(user).toString());
        } catch (IOException e) {
          JOptionPane.showMessageDialog(null, "Error, no se ha podido ejecutar Minecraft.");
          if (!this.error.exists()) {
                try {
                  this.error.createNewFile();
                }
                catch (IOException exe) {
                }
            }
          try {
            PrintWriter pw = new PrintWriter(this.error);
            pw.print(e.getMessage());
            pw.println();
            pw.close();
          }
          catch (IOException exe) {
          }
          System.exit(0);
        }
          break;
      case 3:
        try {minecraft = Runtime.getRuntime().exec(new StringBuilder().append("java -Xmx2048M -Xms2048M -jar ").append(user).toString());
        } catch (IOException e) {
          JOptionPane.showMessageDialog(null, "Error, no se ha podido ejecutar Minecraft.");
          if (!this.error.exists()) {
                try {
                  this.error.createNewFile();
                }
                catch (IOException exe) {
                }
            }
          try {
            PrintWriter pw = new PrintWriter(this.error);
            pw.print(e.getMessage());
            pw.println();
            pw.close();
          }
          catch (IOException exe) {
          }
          System.exit(0);
        }
          break;
      case 4:
        try {minecraft = Runtime.getRuntime().exec(new StringBuilder().append("java -Xmx4096M -Xms4096M -jar ").append(user).toString());
        } catch (IOException e) {
          JOptionPane.showMessageDialog(null, "Error, no se ha podido ejecutar Minecraft.");
          if (!this.error.exists()){
            try {
              this.error.createNewFile();
            }
            catch (IOException exe) {
            }
          }
          try {
            PrintWriter pw = new PrintWriter(this.error);
            pw.print(e.getMessage());
            pw.println();
            pw.close();
          }
          catch (IOException exe) {
          }
          System.exit(0);
        }
          break;
      }
      File log = new File(new StringBuilder().append(System.getProperty("user.home")).append("\\AppData\\Roaming\\Data\\LogMC.cfg").toString());
      if (!log.exists()) {
            try {
              log.createNewFile();
            }
            catch (IOException e)
            {
            }
        }
      Calendar C = new GregorianCalendar();
      StringBuilder str = new StringBuilder("Connected at ");
      str.append(C.get(5)).append("/").append(C.get(2) + 1).append("/").append(C.get(1));
      str.append(" ").append(C.get(11)).append(":").append(C.get(12)).append(":").append(C.get(13));
      str.append(" with name of Minecraft Official User").append("\n");
      try {
        PrintWriter pw = new PrintWriter(new FileWriter(log, true));
        pw.println();
        pw.print(str.toString());
        pw.close();
      }
      catch (IOException e) {
      }
      System.exit(0);
    }
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        // TODO add your handling code here:
        //Igual que el Botón de Mineshafter de Vista
        RAM ram = new RAM(this, true);
        ram.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
    ram.setLocationRelativeTo(null);
    ram.setVisible(true);
    int ind = ram.devolver() + 1;
    ram.dispose();
    if (ind == 0){
        ind = 2;
        JOptionPane.showMessageDialog(null, "No ha elegido ninguna opción. Se ejecutará la opción por defecto (1GB)");
    }
    Process minecraftshafter;
    String user = new StringBuilder().append(System.getProperty("user.home")).append("\\AppData\\Roaming\\.minecraft\\Mineshafter-proxy.jar").toString();
    if (ind > 0) {
      switch (ind) {
      case 1:
        try { minecraftshafter = Runtime.getRuntime().exec(new StringBuilder().append("java -Xmx512M -Xms512M -jar ").append(user).toString());
        } catch (IOException e) {
          JOptionPane.showMessageDialog(null, "Error, no se ha podido ejecutar Minecraft.");
          if (!this.error.exists()) {
                try {
                  this.error.createNewFile();
                }
                catch (IOException exe) {
                }
            }
          try {
            PrintWriter pw = new PrintWriter(this.error);
            pw.print(e.getMessage());
            pw.println();
            pw.close();
          }
          catch (IOException exe) {
          }
          System.exit(0);
        }
          break;
      case 2:
        try {minecraftshafter = Runtime.getRuntime().exec(new StringBuilder().append("java -Xmx1024M -Xms1024M -jar ").append(user).toString());
        } catch (IOException e) {
          JOptionPane.showMessageDialog(null, "Error, no se ha podido ejecutar Minecraft.");
          if (!this.error.exists()) {
                try {
                  this.error.createNewFile();
                }
                catch (IOException exe) {
                }
            }
          try {
            PrintWriter pw = new PrintWriter(this.error);
            pw.print(e.getMessage());
            pw.println();
            pw.close();
          }
          catch (IOException exe) {
          }
          System.exit(0);
        }
          break;
      case 3:
        try {minecraftshafter = Runtime.getRuntime().exec(new StringBuilder().append("java -Xmx2048M -Xms2048M -jar ").append(user).toString());
        } catch (IOException e) {
          JOptionPane.showMessageDialog(null, "Error, no se ha podido ejecutar Minecraft.");
          if (!this.error.exists()) {
                try {
                  this.error.createNewFile();
                }
                catch (IOException exe) {
                }
            }
          try {
            PrintWriter pw = new PrintWriter(this.error);
            pw.print(e.getMessage());
            pw.println();
            pw.close();
          }
          catch (IOException exe) {
          }
          System.exit(0);
        }
          break;
      case 4:
        try {minecraftshafter = Runtime.getRuntime().exec(new StringBuilder().append("java -Xmx4096M -Xms4096M -jar ").append(user).toString());
        } catch (IOException e) {
          JOptionPane.showMessageDialog(null, "Error, no se ha podido ejecutar Minecraft.");
          if (!this.error.exists()){
            try {
              this.error.createNewFile();
            }
            catch (IOException exe) {
            }
          }
          try {
            PrintWriter pw = new PrintWriter(this.error);
            pw.print(e.getMessage());
            pw.println();
            pw.close();
          }
          catch (IOException exe) {
          }
          System.exit(0);
        }
          break;
      }
      File log = new File(new StringBuilder().append(System.getProperty("user.home")).append("\\AppData\\Roaming\\Data\\LogMC.cfg").toString());
      if (!log.exists()) {
            try {
              log.createNewFile();
            }
            catch (IOException e)
            {
            }
        }
      Calendar C = new GregorianCalendar();
      StringBuilder str = new StringBuilder("Connected at ");
      str.append(C.get(5)).append("/").append(C.get(2) + 1).append("/").append(C.get(1));
      str.append(" ").append(C.get(11)).append(":").append(C.get(12)).append(":").append(C.get(13));
      str.append(" with name of Mineshafter User").append("\n");
      try {
        PrintWriter pw = new PrintWriter(new FileWriter(log, true));
        pw.println();
        pw.print(str.toString());
        pw.close();
      }
      catch (IOException e) {
      }
      System.exit(0);
    } else if (ind == 0){
      JOptionPane.showMessageDialog(null, "No ha elegido ninguna opción. Se ejecutará la opción por defecto (1GB)");
            try {
                minecraftshafter = Runtime.getRuntime().exec(new StringBuilder().append("java -Xmx1024M -Xms1024M -jar ").append(user).toString());
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(null, "Error, no se ha podido ejecutar Mineshafter.");
             if (!this.error.exists()) {
                try {
                  this.error.createNewFile();
                }
                catch (IOException exe) {
                }
            }
          try {
            PrintWriter pw = new PrintWriter(this.error);
            pw.print(ex.getMessage());
            pw.println();
            pw.close();
          }
          catch (IOException exe) {
          }
          System.exit(0);
            }
    }
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jTextField1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField1KeyPressed
        // TODO add your handling code here:
        //Escáner de Intro en el usuario
        if((evt.getKeyCode() == KeyEvent.VK_ENTER) && jButton5.isEnabled()){
            Play();
        } else if (evt.getKeyCode() == KeyEvent.VK_CONTROL){
            String a = jTextField1.getText();
            if (a.equals("ADMINCLOSE")){
                exit();
            }
        }
    }//GEN-LAST:event_jTextField1KeyPressed

    private void jPasswordField1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jPasswordField1KeyPressed
        // TODO add your handling code here:
        //Escáner de Intro en la contraseña
        if ((evt.getKeyCode() == KeyEvent.VK_ENTER) && jButton5.isEnabled()){
            Play();
        }
    }//GEN-LAST:event_jPasswordField1KeyPressed

    /**
     * @param args the command line arguments
     */
    public static void main(final String args) {
        /*
         * Set the Nimbus look and feel
         */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /*
         * If Nimbus (introduced in Java SE 6) is not available, stay with the
         * default look and feel. For details see
         * http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Vista2.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Vista2.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Vista2.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Vista2.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /*
         * Create and display the form
         */
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                new Vista2(args).setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton8;
    private javax.swing.JCheckBox jCheckBox1;
    private javax.swing.JCheckBox jCheckBox2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPasswordField jPasswordField1;
    public static javax.swing.JProgressBar jProgressBar1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JTextField jTextField1;
    // End of variables declaration//GEN-END:variables
}
