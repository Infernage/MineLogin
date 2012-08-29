

/*     */ import java.awt.Color;
/*     */ import java.awt.Container;
/*     */ import java.awt.EventQueue;
/*     */ import java.awt.Font;
/*     */ import java.awt.Frame;
/*     */ import java.awt.event.ActionEvent;
/*     */ import java.awt.event.ActionListener;
/*     */ import java.awt.event.WindowAdapter;
/*     */ import java.awt.event.WindowEvent;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
/*     */ import javax.swing.*;
/*     */ 
/*     */ public class RAM extends JDialog
/*     */ {
/*     */   private JButton jButton1;
/*     */   private JLabel jLabel1;
/*     */   private JList jList1;
/*     */   private JScrollPane jScrollPane1;
            private boolean exit = false;
/*     */ 
/*     */   public RAM(Frame parent, boolean modal)
/*     */   {
/*  19 */     super(parent, modal);
/*  20 */     initComponents();
                this.addWindowListener(new WindowAdapter() {
                    public void windowClosing(WindowEvent e){
                        exit = true;
                    }
                });
/*     */   }
/*     */   public int devolver() {
                int i = jList1.getSelectedIndex();
                if (exit){
                    i = -2;
                }
/*  23 */     return i;
/*     */   }
/*     */ 
/*     */   private void initComponents()
/*     */   {
/*  34 */     this.jLabel1 = new JLabel();
/*  35 */     this.jScrollPane1 = new JScrollPane();
/*  36 */     this.jList1 = new JList();
/*  37 */     this.jButton1 = new JButton();
/*     */ 
/*  39 */     setDefaultCloseOperation(2);
/*     */ 
/*  41 */     this.jLabel1.setFont(new Font("Tahoma", 1, 14));
/*  42 */     this.jLabel1.setText("Memoria RAM a asignar a Minecraft.");
/*     */ 
/*  44 */     this.jList1.setModel(new AbstractListModel() {
/*  45 */       String[] strings = { "512Mb RAM", "1Gb RAM", "2Gb RAM (Recomendado)", "4Gb RAM" };
/*     */ 
/*  46 */       public int getSize() { return this.strings.length; } 
/*  47 */       public Object getElementAt(int i) { return this.strings[i];
/*     */       }
/*     */     });
/*  49 */     this.jList1.setSelectionMode(0);
/*  50 */     this.jScrollPane1.setViewportView(this.jList1);
/*     */ 
/*  52 */     this.jButton1.setBackground(new Color(0, 204, 204));
/*  53 */     this.jButton1.setText("Ejecutar");
/*  54 */     this.jButton1.addActionListener(new ActionListener() {
/*     */       public void actionPerformed(ActionEvent evt) {
/*  56 */         RAM.this.jButton1ActionPerformed(evt);
/*     */       }
/*     */     });
/*  60 */     GroupLayout layout = new GroupLayout(getContentPane());
/*  61 */     getContentPane().setLayout(layout);
/*  62 */     layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup().addContainerGap().addComponent(this.jScrollPane1)).addGroup(layout.createSequentialGroup().addGap(96, 96, 96).addComponent(this.jLabel1).addGap(0, 93, 32767))).addContainerGap()).addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup().addGap(0, 0, 32767).addComponent(this.jButton1).addGap(182, 182, 182)));
/*     */ 
/*  79 */     layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addContainerGap().addComponent(this.jLabel1).addGap(18, 18, 18).addComponent(this.jScrollPane1, -2, 89, -2).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addComponent(this.jButton1).addContainerGap(-1, 32767)));
/*     */ 
/*  91 */     pack();
/*     */   }
/*     */ 
/*     */   private void jButton1ActionPerformed(ActionEvent evt)
/*     */   {
/*  96 */     devolver();
/*  97 */     dispose();
/*     */   }
/*     */ 
/*     */   public static void main(String[] args)
/*     */   {
/*     */     try
/*     */     {
/* 114 */       for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels())
/* 115 */         if ("Nimbus".equals(info.getName())) {
/* 116 */           UIManager.setLookAndFeel(info.getClassName());
/* 117 */           break;
/*     */         }
/*     */     }
/*     */     catch (ClassNotFoundException ex) {
/* 121 */       Logger.getLogger(RAM.class.getName()).log(Level.SEVERE, null, ex);
/*     */     } catch (InstantiationException ex) {
/* 123 */       Logger.getLogger(RAM.class.getName()).log(Level.SEVERE, null, ex);
/*     */     } catch (IllegalAccessException ex) {
/* 125 */       Logger.getLogger(RAM.class.getName()).log(Level.SEVERE, null, ex);
/*     */     } catch (UnsupportedLookAndFeelException ex) {
/* 127 */       Logger.getLogger(RAM.class.getName()).log(Level.SEVERE, null, ex);
/*     */     }
/*     */ 
/* 134 */     EventQueue.invokeLater(new Runnable()
/*     */     {
/*     */       public void run() {
/* 137 */         RAM dialog = new RAM(new JFrame(), true);
/* 138 */         dialog.addWindowListener(new WindowAdapter()
/*     */         {
/*     */           public void windowClosing(WindowEvent e)
/*     */           {
/* 142 */             System.exit(0);
/*     */           }
/*     */         });
/* 145 */         dialog.setVisible(true);
/*     */       }
/*     */     });
/*     */   }
/*     */ }

/* Location:           C:\Archivos\Minecraft\Login\inst - copia(Funciona)\.minecraft\RUN.jar
 * Qualified Name:     RAM
 * JD-Core Version:    0.6.0
 */