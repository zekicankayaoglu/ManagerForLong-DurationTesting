/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package swing;

import Data.DataWriter;
import java.util.HashMap;

/**
 *
 * @author zekican
 */
public class PanelTask extends javax.swing.JPanel {

    /**
     * Creates new form PanelTask
     */
    public PanelTask() {
        initComponents();
    }
    private HashMap<Integer, PanelStatus> list = new HashMap<>();
    public void addStatus(int taskNumber,int fileID, DataWriter data){
        PanelStatus status = new PanelStatus();
      
        list.put(taskNumber, status);
        this.add(status);
        this.repaint();
        this.revalidate();
        System.out.println("gelio");
    }
    public void addItem(int taskNumber,int fileID, DataWriter data){
        list.get(taskNumber).addItem(fileID, data.getFile().getName(), data.getMaxFileSize());
    }
    public void updateStatus(int taskNumber,int fileID, int values){
        list.get(taskNumber).updateStatus(fileID,values);
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}