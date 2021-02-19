/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelamiento;

import gnu.io.SerialPortEvent;
import gnu.io.SerialPortEventListener;
import javax.swing.JOptionPane;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import panamahitek.Arduino.PanamaHitek_Arduino;

/**
 *
 * @author Johan Leon
 */
public class TransferFunction extends javax.swing.JFrame {

    PanamaHitek_Arduino arduino = new PanamaHitek_Arduino();
    XYSeries Serie = new XYSeries("RPM");
    final XYSeriesCollection Coleccion = new XYSeriesCollection();
    JFreeChart Grafica;
    double aux = 0;
    int posicion = 0;
    int datos = 0;
    double matrizX[][];
    double matrizY[][];
    double matriz2X[][];
    double matriz2Y[][];
    int u=0;

    SerialPortEventListener listener = new SerialPortEventListener() {
        @Override
        public void serialEvent(SerialPortEvent spe) {

            try {
                if (arduino.isMessageAvailable() == true) {
                    comenzar.setEnabled(false);
                    reset.setEnabled(true);

                    int absoluto = (Integer.parseInt(arduino.printMessage()))/120;

                    ///////////////GRAFICAR Y LLENAR LA MATRIZ ORDEN 1//////////////
                    aux = aux + 0.1;
                    
                    try {

                        if (posicion < (datos - 2)) {
                           Serie.add(aux, absoluto);
                            matrizX[posicion][0] = -1 * absoluto;
                            matrizX[posicion][1] = u;
                        }

                        if (aux > 0.1 && posicion <= (datos - 2)) {
                            matrizY[posicion - 1][0] = absoluto;
                        }

                    } catch (Exception e) {
                        System.out.println("Error en orden 1");
                    }
                    try {
                        //////////////LLENAR LA MATRIZ ORDEN 2////////////////
                        if (aux > 0.1 && posicion < (datos - 1)) {
                            ///COLUMNA 1 Y 3///
                            matriz2X[posicion - 1][0] = -1 * absoluto;
                            matriz2X[posicion - 1][2] = u;
                        }
                        if (posicion < (datos - 2)) {
                            //COLUMNA 2 Y 4///
                            matriz2X[posicion][1] = -1 * absoluto;
                            matriz2X[posicion][3] = u;
                        }
                        if (aux > 0.2 && posicion < datos) {
                            matriz2Y[posicion - 2][0] = absoluto;
                        }
                    } catch (Exception e) {
                        System.out.println("Error en orden 2");
                    }

                    posicion++;
                }

            } catch (Exception e) {
                System.out.println(e);
            }

        }
    };

    public TransferFunction() {
        initComponents();
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        try {

            arduino.arduinoRXTX("COM7", 57600, listener);
        } catch (Exception ex) {
            System.out.println(ex);
        }
        reset.setEnabled(false);
        Serie.add(0, 0);
        Coleccion.addSeries(Serie);
        Grafica = ChartFactory.createXYLineChart("REVOLUCIONES POR MINUTO", "Segundos", "Revoluciones", Coleccion, PlotOrientation.VERTICAL, true, true, false);
        pintar();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")


    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        graf = new javax.swing.JPanel();
        lbltiempo = new javax.swing.JLabel();
        lblentrada = new javax.swing.JLabel();
        tiempo = new javax.swing.JTextField();
        entrada = new javax.swing.JTextField();
        comenzar = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tfo1 = new javax.swing.JTextArea();
        cfuncion = new javax.swing.JButton();
        reset = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        tfo2 = new javax.swing.JTextArea();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMaximumSize(null);
        setMinimumSize(new java.awt.Dimension(1000, 670));
        setPreferredSize(null);
        getContentPane().setLayout(null);

        jPanel1.setBackground(new java.awt.Color(0, 0, 0));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        javax.swing.GroupLayout grafLayout = new javax.swing.GroupLayout(graf);
        graf.setLayout(grafLayout);
        grafLayout.setHorizontalGroup(
            grafLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 750, Short.MAX_VALUE)
        );
        grafLayout.setVerticalGroup(
            grafLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 410, Short.MAX_VALUE)
        );

        jPanel1.add(graf, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 30, 750, 410));

        lbltiempo.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        lbltiempo.setForeground(new java.awt.Color(255, 255, 255));
        lbltiempo.setText("Tiempo:");
        jPanel1.add(lbltiempo, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 110, -1, -1));

        lblentrada.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        lblentrada.setForeground(new java.awt.Color(255, 255, 255));
        lblentrada.setText("r:");
        jPanel1.add(lblentrada, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 170, -1, -1));

        tiempo.setText("1");
        jPanel1.add(tiempo, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 110, 90, -1));

        entrada.setText("100");
        entrada.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                entradaActionPerformed(evt);
            }
        });
        jPanel1.add(entrada, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 170, 90, -1));

        comenzar.setText("START");
        comenzar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comenzarActionPerformed(evt);
            }
        });
        jPanel1.add(comenzar, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 240, -1, -1));

        tfo1.setColumns(20);
        tfo1.setRows(5);
        jScrollPane1.setViewportView(tfo1);

        jPanel1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 460, 490, 169));

        cfuncion.setText("Fun. Transferencia");
        cfuncion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cfuncionActionPerformed(evt);
            }
        });
        jPanel1.add(cfuncion, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 310, -1, -1));

        reset.setText("RESET");
        reset.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                resetActionPerformed(evt);
            }
        });
        jPanel1.add(reset, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 240, -1, -1));

        tfo2.setColumns(20);
        tfo2.setRows(5);
        tfo2.setMaximumSize(new java.awt.Dimension(164, 94));
        tfo2.setMinimumSize(new java.awt.Dimension(164, 94));
        jScrollPane2.setViewportView(tfo2);

        jPanel1.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 460, 480, 170));

        getContentPane().add(jPanel1);
        jPanel1.setBounds(0, 0, 1020, 670);

        pack();
    }// </editor-fold>//GEN-END:initComponents


    private void entradaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_entradaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_entradaActionPerformed


    private void comenzarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comenzarActionPerformed
        if ((!entrada.getText().equals("") || !tiempo.getText().equals("")) && Integer.parseInt(entrada.getText()) <= 100) {
            try {
                int t = Integer.parseInt(tiempo.getText());
                u=Integer.parseInt(entrada.getText());
                datos = (t * 10) + 2;
                matrizX = new double[datos - 2][2];
                matrizY = new double[datos - 2][1];
                matriz2X = new double[datos - 2][4];
                matriz2Y = new double[datos - 2][1];
                arduino.sendByte(t);
                arduino.sendByte(Integer.parseInt(entrada.getText()));
                tiempo.setEnabled(false);
                entrada.setEnabled(false);

                pintar();

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, ex);
            }
        } else {
            System.out.println("Error");
        }
    }//GEN-LAST:event_comenzarActionPerformed

    private void resetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_resetActionPerformed
        reset();
    }//GEN-LAST:event_resetActionPerformed

    private void cfuncionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cfuncionActionPerformed
        this.orden1();
        this.orden2();


    }//GEN-LAST:event_cfuncionActionPerformed

    public void orden1() {
        OperacionesMatrices o = new OperacionesMatrices();
        ////////ORDEN 1//////////
        System.out.println("MATRIZ X: ");
        o.mostrar(matrizX);
        System.out.println("MATRIZ Y: ");
        o.mostrar(matrizY);
        System.out.println("X^T");
        double op1[][] = o.matrizTranspuesta(matrizX);
        o.mostrar(op1);
        System.out.println("X^T * X");
        double op2[][] = o.multiplicarDosMatrices(op1, matrizX);
        o.mostrar(op2);
        System.out.println("(X^T * X)^-1");
        double op3[][] = o.inversa(op2);
        o.mostrar(op3);
        System.out.println("(X^T * X)^-1 * X^T");
        double op34[][] = o.matrizTranspuesta(matrizX);
        o.mostrar(op34);
        double op4[][] = o.multiplicarDosMatrices(op3, op34);
        o.mostrar(op4);
        System.out.println("CONSTANTES");
        double op5[][] = o.multiplicarDosMatrices(op4, matrizY);
        System.out.print("a1 \nb1");
        o.mostrar(op5);

        String mostrar1 = "";
        double oper1 = 2 * -1 * (1 + op5[0][0]);
        double oper2 = -1 * op5[1][0] * 0.1;
        double oper3 = 2 * op5[1][0];
        double oper4 = (0.1 + (op5[0][0] * 0.1));

        if (oper1 < 0) {
            mostrar1 = String.valueOf(oper2) + " S +" + String.valueOf(oper3)
                    + "\n --------------------------------\n"
                    + String.valueOf(oper4) + " S " + String.valueOf(oper1);
            tfo1.setText(mostrar1);
            /*  System.out.println(oper2+ " S + " + oper3);
            System.out.println("-------------------------------------");
            System.out.println(oper4 + " S  " + oper1);*/
        } else {
            mostrar1 = String.valueOf(oper2) + " S +" + String.valueOf(oper3)
                    + "\n --------------------------------\n"
                    + String.valueOf(oper4) + " S + " + String.valueOf(oper1);
            tfo1.setText(mostrar1);
            /* System.out.println(oper2 + " S + " + oper3);
            System.out.println("-------------------------------------");
            System.out.println( oper4+ " S + " + oper1);*/
        }
    }

    public void orden2() {
        OperacionesMatrices o = new OperacionesMatrices();
        ////////ORDEN 2//////////
        System.out.println("MATRIZ X: ");
        o.mostrar(matriz2X);
        System.out.println("MATRIZ Y: ");
        o.mostrar(matriz2Y);
        System.out.println("X^T");
        double op1[][] = o.matrizTranspuesta(matriz2X);
        o.mostrar(op1);
        System.out.println("X^T * X");
        double op2[][] = o.multiplicarDosMatrices(op1, matriz2X);
        o.mostrar(op2);
        System.out.println("(X^T * X)^-1");
        double op3[][] = o.matrizInversa(op2);
        o.mostrar(op3);
        System.out.println("(X^T * X)^-1 * X^T");
        double op34[][] = o.matrizTranspuesta(matriz2X);
        o.mostrar(op34);
        double op4[][] = o.multiplicarDosMatrices(op3, op34);
        o.mostrar(op4);
        System.out.println("CONSTANTES");
        double op5[][] = o.multiplicarDosMatrices(op4, matriz2Y);
        System.out.print("a1 \na2 \nb1 \nb2");
        o.mostrar(op5);

        String mostrar1 = "";
        double oper1 = op5[2][0] * 0.1;
        double oper2 = op5[2][0] + op5[3][0];
        double oper3 = (0.1) * (0.1);
        double oper4 = (2 * 0.1) + (op5[0][0] * 0.1);
        double oper5 = op5[0][0] + op5[1][0] + 1;

        mostrar1 = String.valueOf(oper1) + " S + " + String.valueOf(oper2)
                + "\n -----------------------------------------\n"
                + String.valueOf(oper3) + " S^2 + " + String.valueOf(oper4) + " S + " + String.valueOf(oper5);
        tfo2.setText(mostrar1);

    }

    public void pintar() {

        ChartPanel grafica = new ChartPanel(Grafica);

        graf.removeAll();
        graf.setLayout(new java.awt.BorderLayout());
        graf.add(grafica);
        this.pack();
        Serie.clear();
        Serie.add(0, 0);
        aux = 0;

    }

    public void reset() {
        comenzar.setEnabled(true);
        entrada.setEnabled(true);
        tiempo.setEnabled(true);
        // tiempo.setText("");
        // entrada.setText("");
        tfo1.setText("");
        tfo2.setText("");

        posicion = 0;
        datos = 0;
        pintar();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(TransferFunction.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(TransferFunction.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(TransferFunction.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(TransferFunction.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new TransferFunction().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton cfuncion;
    private javax.swing.JButton comenzar;
    public javax.swing.JTextField entrada;
    public javax.swing.JPanel graf;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lblentrada;
    private javax.swing.JLabel lbltiempo;
    private javax.swing.JButton reset;
    public javax.swing.JTextArea tfo1;
    public javax.swing.JTextArea tfo2;
    public javax.swing.JTextField tiempo;
    // End of variables declaration//GEN-END:variables
}