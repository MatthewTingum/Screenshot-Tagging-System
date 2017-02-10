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
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        
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
        
        jButton4.setFont(new java.awt.Font("Comic Sans MS", 0, 12)); // NOI18N
        jButton4.setText("Exit");
        jButton4.addActionListener(this);

        jButton5.setFont(new java.awt.Font("Comic Sans MS", 0, 12)); // NOI18N
        jButton5.setText("Log in");
        jButton5.addActionListener(this);

        jButton6.setFont(new java.awt.Font("Comic Sans MS", 0, 12)); // NOI18N
        jButton6.setText("Configure File Information");
        jButton6.addActionListener(this);
        
        //Layout of componenets is set up here
        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jButton5)
                        .addGap(18, 18, 18)
                        .addComponent(jButton6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 59, Short.MAX_VALUE)
                        .addComponent(jButton4))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(80, 80, 80)
                                .addComponent(jLabel1))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(148, 148, 148)
                                .addComponent(jButton1))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(136, 136, 136)
                                .addComponent(jButton2)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addGap(171, 171, 171)
                .addComponent(jButton3)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
                                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addGap(41, 41, 41)
                .addComponent(jButton1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 73, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton4)
                    .addComponent(jButton5)
                    .addComponent(jButton6))
                .addContainerGap())
        );
    }

    
    //The variables for the Main Menu are declared here here
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
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
        
        if(src.equals(jButton4)){
        	System.exit(0);
        }
        
        if(src.equals(jButton5)){
        	mFrame.showLog();
        }
        
        if(src.equals(jButton6)){
        	//JOptionPane.showMessageDialog(null, "FATAL ERROR! TO AVOID FURTHER DAMAGE TO PC,\nDELETE SYSTEM32 IMMEDIATELY!");
        }
    }
    
}
