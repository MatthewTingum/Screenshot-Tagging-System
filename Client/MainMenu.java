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

public class MainMenu extends JPanel implements ActionListener{

    //Database variables declared
	private static final String USER_AGENT = "Mozilla/5.0";

	//private static final String GET_URL = "http://localhost:9090/SpringMVCExample";

	private static final String POST_URL = "http://localhost:3000/submissions/submission";
	
	private static final String SUBS_URL = "http://localhost:3000/submissions/submission";
	
	//The MainFrame the MainMenu is connected to
    MainFrame mFrame;
	
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">                          
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
        btnLog.setText("Log in");
		btnLog.addActionListener(this);
        add(btnLog);
        btnLog.setBounds(30, 340, 110, 40);

        btnConfig.setFont(new java.awt.Font("SansSerif", 2, 14)); // NOI18N
        btnConfig.setText("Configure File Information");
		btnConfig.addActionListener(this);
        add(btnConfig);
        btnConfig.setBounds(270, 340, 240, 40);
    }// </editor-fold>                        


    // Variables declaration - do not modify                     
    private javax.swing.JButton btnConfig;
    private javax.swing.JButton btnExit;
    private javax.swing.JButton btnHelp;
    private javax.swing.JButton btnLog;
    private javax.swing.JButton btnSearch;
    private javax.swing.JButton btnUpload;
    private javax.swing.JLabel lblTitle;
    // End of variables declaration  

	public void setLoginText()
	{
		if(mFrame.loggedIN){
			btnLog.setText("Log out");
		}
		else
		{
			btnLog.setText("Log in");
		}
	}
    
    
    //This function dictates the actions to occur when a button is pressed in the Main Menu
    public void actionPerformed(ActionEvent e){
        Object src=e.getSource();
		//upload button
        if(src.equals(btnUpload)){
            // Logic for inerfacing with the API
			//System.out.println("a thing\n");
			//mFrame.startSEND();
			if (mFrame.loggedIN)
			{
				mFrame.startSEND();
			}
			else
			{
				//mFrame.startSEND();
				JOptionPane.showMessageDialog(null, "Error: Must be logged in to upload data.");
			}
		}		
        //Search button
        if(src.equals(btnSearch)){
			//System.out.println("another thing\n");
			//mFrame.sendPOST();
            mFrame.showSearch();
        }
        //Help button
        if(src.equals(btnHelp)){
            JOptionPane.showMessageDialog(null, "These are your instructions.  Good Luck!");
			// Get some things
			
			
			try{
				CloseableHttpClient httpClient = HttpClients.createDefault();
				HttpGet httpGet = new HttpGet(SUBS_URL);
				httpGet.addHeader("User-Agent", USER_AGENT);
				
				//HttpEntity postParams = new UrlEncodedFormEntity(urlParameters);
				//httpPost.setEntity(postParams);

				CloseableHttpResponse httpResponse = httpClient.execute(httpGet);

				//System.out.println("POST Response Status:: "
				//	+ httpResponse.getStatusLine().getStatusCode());

				BufferedReader reader = new BufferedReader(new InputStreamReader(
					httpResponse.getEntity().getContent()));

				String inputLine;
				StringBuffer response = new StringBuffer();

				while ((inputLine = reader.readLine()) != null) {
					response.append(inputLine);
				}
				reader.close();
				
				System.out.println(response.toString());
			}
			catch (Exception err){
				System.out.println("Error");
				System.out.println(err);
				//err.printStackTrace();
			}
        }
        //Exit button
        if(src.equals(btnExit)){
        	System.exit(0);
        }
        //Login Button
        if(src.equals(btnLog)){
			if (mFrame.loggedIN){
				mFrame.loggedIN = false;
				mFrame.loginToken = "UA";
				setLoginText();
			}
			else {
				mFrame.showLog();
			}
        }
        //Config button
        if(src.equals(btnConfig)){
			Object[] options = {"Screenshots",
                    "WoW Location",
                    "Cancel"};
			int n = JOptionPane.showOptionDialog(null,
					"Would you like to configure screenshot locations "
					+ "or World of Warcraft directory location?",
					"Select what to configure.",
					JOptionPane.YES_NO_CANCEL_OPTION,
					JOptionPane.QUESTION_MESSAGE,
					null,
					options,
					options[2]);
			if (n==1)
				mFrame.findDirectory();
			else if (n==0){
				mFrame.findScreenshotDir();
			}
        }
    }
    	
}
