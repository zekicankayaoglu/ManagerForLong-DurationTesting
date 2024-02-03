
package swing;

/**
 *
 * @author zekican
 */
public class PanelStatus_Item extends javax.swing.JPanel {

   
    public PanelStatus_Item() {
        initComponents();
    }

    public void showStatus(int values){
        pro.setValue(values);
        System.out.println("value: " + pro.getValue());
        if(pro.getValue() == 100 && !lbFileName1.toString().contains("Error")){
            jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/done.png")));
        }else if(pro.getValue() == 100 && lbFileName1.toString().contains("Error")){
            jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/delete.png")));
        }else if(lbSize.toString().equals("0 bytes")){
            jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/delete.png")));
            pro.setValue(100);
        }
    }
    public void setFile(String fileName, String fileSize){
        lbFileName1.setText(fileName);
        lbSize.setText(fileSize);
        System.out.println("sizeeeeeee:  " + fileSize);
        if(fileSize.equals("0 bytes")){
            jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/delete.png")));
            pro.setValue(100);
        }
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pro = new javax.swing.JProgressBar();
        lbSize = new javax.swing.JLabel();
        lbFileName1 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();

        pro.setStringPainted(true);

        lbSize.setText("Size");

        lbFileName1.setText("File Name");

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/save.png"))); // NOI18N
        jButton1.setContentAreaFilled(false);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lbFileName1, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lbSize, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(pro, javax.swing.GroupLayout.PREFERRED_SIZE, 194, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(pro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbSize)
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbFileName1))
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents
    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel lbFileName1;
    private javax.swing.JLabel lbSize;
    private javax.swing.JProgressBar pro;
    // End of variables declaration//GEN-END:variables
}
