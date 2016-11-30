import javax.swing.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class SearchScreen extends JPanel implements ActionListener{
    
    //The MainFrame that the SearchScreen is connected to
    MainFrame mFrame;
    
    //This is the constructor for the SearchScreen panel
    public SearchScreen(MainFrame mf) {
        mFrame = mf;
        
        //The components for the Search Screen are created here
        jTextField1 = new javax.swing.JTextField();
        jCheckBox1 = new javax.swing.JCheckBox();
        jCheckBox2 = new javax.swing.JCheckBox();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        
        //This is where the components are initialized
        jTextField1.setFont(new java.awt.Font("Comic Sans MS", 0, 13)); // NOI18N
        jTextField1.setText("Enter keywords here");
        
        jCheckBox1.setFont(new java.awt.Font("Comic Sans MS", 0, 13)); // NOI18N
        jCheckBox1.setText("Tags");
        
        jCheckBox2.setFont(new java.awt.Font("Comic Sans MS", 0, 13)); // NOI18N
        jCheckBox2.setText("Description");
        
        jButton1.setFont(new java.awt.Font("Comic Sans MS", 0, 13)); // NOI18N
        jButton1.setText("Search");
        
        jButton2.setFont(new java.awt.Font("Comic Sans MS", 0, 13)); // NOI18N
        jButton2.setText("Back");
        jButton2.addActionListener(this);
        
        //The Layout of components for the Search Screen is set up here
        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
                                  layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                  .addGroup(layout.createSequentialGroup()
                                            .addGap(29, 29, 29)
                                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                      .addGroup(layout.createSequentialGroup()
                                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                                          .addComponent(jCheckBox2)
                                                                          .addComponent(jCheckBox1)
                                                                          .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                                      .addGroup(layout.createSequentialGroup()
                                                                .addComponent(jButton1)
                                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 167, Short.MAX_VALUE)
                                                                .addComponent(jButton2)
                                                                .addGap(41, 41, 41))))
                                  );
        layout.setVerticalGroup(
                                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(layout.createSequentialGroup()
                                          .addGap(24, 24, 24)
                                          .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                          .addGap(41, 41, 41)
                                          .addComponent(jCheckBox1)
                                          .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                          .addComponent(jCheckBox2)
                                          .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 67, Short.MAX_VALUE)
                                          .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                    .addComponent(jButton1)
                                                    .addComponent(jButton2))
                                          .addGap(56, 56, 56))
                                );
    }
    
    
    // Components for the Search screen are declared here
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JCheckBox jCheckBox1;
    private javax.swing.JCheckBox jCheckBox2;
    private javax.swing.JTextField jTextField1;
    
    
    //When a button is pressed, this function determines what to do about it
    public void actionPerformed(ActionEvent e){
        mFrame.showMain();
    }
}
