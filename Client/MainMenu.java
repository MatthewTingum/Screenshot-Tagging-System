import javax.swing.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;



public class MainMenu extends JPanel implements ActionListener{
	
    //Database variables declared
	private static final String USER_AGENT = "Mozilla/5.0";

	//private static final String GET_URL = "http://localhost:9090/SpringMVCExample";

	private static final String POST_URL = "http://localhost:3000/submissions/submission";
	
	//The MainFrame the MainMenu is connected to
    MainFrame mFrame;
    
    // Constructor for the MainMenu panel
    public MainMenu(MainFrame mf)  {
        mFrame = mf;
        
        //The variables for the Main Menu are created here
        jLabel1 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        
        //Title label is initialized here
        jLabel1.setFont(new java.awt.Font("Comic Sans MS", 0, 24)); // NOI18N
        jLabel1.setText("Awesome App Name");
        
        //Buttons are initialized and ActionListeners are added
        jButton1.setFont(new java.awt.Font("Comic Sans MS", 0, 13)); // NOI18N
        jButton1.setText("Upload Data");
		jButton1.addActionListener(this);
        
        jButton2.setFont(new java.awt.Font("Comic Sans MS", 0, 13)); // NOI18N
        jButton2.setText("Search Database");
        jButton2.addActionListener(this);
        
        jButton3.setFont(new java.awt.Font("Comic Sans MS", 0, 13)); // NOI18N
        jButton3.setText("Help");
        jButton3.addActionListener(this);
        
        //Layout of componenets is set up here
        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
                                  layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                  .addGroup(layout.createSequentialGroup()
                                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                      .addGroup(layout.createSequentialGroup()
                                                                .addGap(80, 80, 80)
                                                                .addComponent(jLabel1))
                                                      .addGroup(layout.createSequentialGroup()
                                                                .addGap(131, 131, 131)
                                                                .addComponent(jButton1))
                                                      .addGroup(layout.createSequentialGroup()
                                                                .addGap(119, 119, 119)
                                                                .addComponent(jButton2))
                                                      .addGroup(layout.createSequentialGroup()
                                                                .addGap(153, 153, 153)
                                                                .addComponent(jButton3)))
                                            .addContainerGap(95, Short.MAX_VALUE))
                                  );
        layout.setVerticalGroup(
                                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(layout.createSequentialGroup()
                                          .addContainerGap()
                                          .addComponent(jLabel1)
                                          .addGap(43, 43, 43)
                                          .addComponent(jButton1)
                                          .addGap(18, 18, 18)
                                          .addComponent(jButton2)
                                          .addGap(18, 18, 18)
                                          .addComponent(jButton3)
                                          .addContainerGap(94, Short.MAX_VALUE))
                                );
    }

    
    //The variables for the Main Menu are declared here here
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel1;
    
    
    //This function dictates the actions to occur when a button is pressed in the Main Menu
    public void actionPerformed(ActionEvent e){
        Object src=e.getSource();
		//upload button
        if(src.equals(jButton1)){
            // Logic for inerfacing with the API
			//System.out.println("a thing\n");
			mFrame.sendPOST();
		}		
        //Search button
        if(src.equals(jButton2)){
			//System.out.println("another thing\n");
			//mFrame.sendPOST();
            mFrame.showSearch();
        }
        //Help button
        if(src.equals(jButton3)){
            JOptionPane.showMessageDialog(null, "These are your instructions.  Good Luck!");
        }
    }
    
}
