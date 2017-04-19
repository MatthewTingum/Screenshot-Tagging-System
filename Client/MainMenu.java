import javax.swing.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * The MainMenu class creates the main menu for the GUI 
 * @author Richard Lee
 * @version 2.0
 */ 
 
public class MainMenu extends JPanel implements ActionListener{

    //Database variables declared
	private static final String USER_AGENT = "Mozilla/5.0";
	private static final String POST_URL = "http://localhost:3000/submissions/submission";
	private static final String SUBS_URL = "http://localhost:3000/submissions/submission";
	
	//The MainFrame the MainMenu is connected to
    MainFrame mFrame;
	
    /**
	 * This constructor creates and returns an instance of MainMenu
	 * @param mf the MainFrame of the GUI
	 */
    public MainMenu(MainFrame mf) {
		
		mFrame = mf;

        lblTitle = new javax.swing.JLabel();
        btnUpload = new javax.swing.JButton();
        btnSearch = new javax.swing.JButton();
        btnHelp = new javax.swing.JButton();
        btnExit = new javax.swing.JButton();
        btnLog = new javax.swing.JButton();
        btnConfig = new javax.swing.JButton();

        setLayout(null);

        lblTitle.setFont(new java.awt.Font("SansSerif", 1, 48)); // NOI18N
        lblTitle.setText("SSTagger Client");
        add(lblTitle);
        lblTitle.setBounds(200, 10, 390, 60);

        btnUpload.setFont(new java.awt.Font("SansSerif", 2, 14)); // NOI18N
        btnUpload.setText("Upload Data");
		btnUpload.addActionListener(this);
        add(btnUpload);
        btnUpload.setBounds(330, 110, 120, 40);

        btnSearch.setFont(new java.awt.Font("SansSerif", 2, 14)); // NOI18N
        btnSearch.setText("Search Database");
        btnSearch.addActionListener(this);
        add(btnSearch);
        btnSearch.setBounds(310, 180, 160, 40);

        btnHelp.setFont(new java.awt.Font("SansSerif", 2, 14)); // NOI18N
        btnHelp.setText("Help");
		btnHelp.addActionListener(this);
        add(btnHelp);
        btnHelp.setBounds(330, 250, 120, 40);

        btnExit.setFont(new java.awt.Font("SansSerif", 2, 14)); // NOI18N
        btnExit.setText("Exit");
		btnExit.addActionListener(this);
        add(btnExit);
        btnExit.setBounds(670, 340, 100, 40);

        btnLog.setFont(new java.awt.Font("SansSerif", 2, 14)); // NOI18N
        btnLog.setText("Log out");
		btnLog.addActionListener(this);
        add(btnLog);
        btnLog.setBounds(30, 340, 110, 40);

        btnConfig.setFont(new java.awt.Font("SansSerif", 2, 14)); // NOI18N
        btnConfig.setText("Configure File Information");
		btnConfig.addActionListener(this);
        add(btnConfig);
        btnConfig.setBounds(270, 340, 240, 40);
    }                      
                   
    private javax.swing.JButton btnConfig;
    private javax.swing.JButton btnExit;
    private javax.swing.JButton btnHelp;
    private javax.swing.JButton btnLog;
    private javax.swing.JButton btnSearch;
    private javax.swing.JButton btnUpload;
    private javax.swing.JLabel lblTitle;
    
    /**
	 * This method is called whenever a button is pressed on the Main Menu 
	 * @param e the ActionEvent of a button press
	 */
    public void actionPerformed(ActionEvent e){
        Object src=e.getSource();
		//if the upload button is pressed
        if(src.equals(btnUpload)){
			//method that starts an upload attempt
			mFrame.startSEND();
		}		
        //Search button
        if(src.equals(btnSearch)){
			//Show the search screen
            mFrame.showSearch();
        }
        //Help button
        if(src.equals(btnHelp)){
			//Show help page
			mFrame.showHelp();
        }
        //Exit button
        if(src.equals(btnExit)){
        	System.exit(0);
        }
        //Login Button
        if(src.equals(btnLog)){
			//Call method that logs out user
			mFrame.logOutUser();
        }
        //Config button
        if(src.equals(btnConfig)){
			mFrame.findDirectory();
        }
    }
    	
}