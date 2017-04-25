import javax.swing.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

//Libraries for connecting to database

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.io.File;
import java.io.FileReader;
import java.io.FileNotFoundException;


import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;

import org.apache.commons.io.FileUtils;

/**
 * The LoginPage class creates a JPanel that allows the user to 
 * enter login information and log in
 * @author Richard Lee
 * @version 2.0
 */ 

public class LoginPage extends JPanel implements ActionListener{

    MainFrame mFrame;
	
	private static final String LOGIN_URL = "http://localhost:3000/api/users/authenticate";
	private static final String USER_AGENT = "Mozilla/5.0";
	
    /**
	 * The constructor creates and returns an instance of LoginPage
	 * @param mf the MainFrame of the GUI
	 */
    public LoginPage(MainFrame mf) {
		
		mFrame = mf;

        lblUser = new javax.swing.JLabel();
        lblPass = new javax.swing.JLabel();
        txtUser = new javax.swing.JTextField();
		txtPass = new JPasswordField();
        btnCancel = new javax.swing.JButton();
        btnOK = new javax.swing.JButton();

        setLayout(null);

        lblUser.setText("Username:");
        add(lblUser);
        lblUser.setBounds(270, 150, 100, 30);

        lblPass.setText("Password:");
        add(lblPass);
        lblPass.setBounds(270, 190, 80, 30);

        add(txtUser);
        txtUser.setBounds(370, 150, 210, 20);
        add(txtPass);
        txtPass.setBounds(370, 190, 210, 20);

        btnCancel.setText("Exit");
		btnCancel.addActionListener(this);
        add(btnCancel);
        btnCancel.setBounds(460, 250, 90, 30);

        btnOK.setText("Ok");
		btnOK.addActionListener(this);
        add(btnOK);
        btnOK.setBounds(280, 250, 110, 30);
    }                      
                                      

                   
    private javax.swing.JButton btnCancel;
    private javax.swing.JButton btnOK;
    private javax.swing.JLabel lblPass;
    private javax.swing.JLabel lblUser;
	private JPasswordField txtPass;
    private javax.swing.JTextField txtUser;

	/**
	 * This method is called whenever a button is pressed on this page
	 * @param e the ActionEvent of a button being pressed 
	 */
	public void actionPerformed(ActionEvent e){
		
		Object src=e.getSource();
		//If the submit button is pressed 
        if(src.equals(btnOK)){
			
			String s1 = txtUser.getText();
			String s2 = new String(txtPass.getPassword()); 
		
			List<NameValuePair> urlParameters = new ArrayList<NameValuePair>();
		
			// Here is where we can add in any data to send to the database
			urlParameters.add(new BasicNameValuePair("username", s1));
			urlParameters.add(new BasicNameValuePair("password", s2));
			
			try{
				CloseableHttpClient httpClient = HttpClients.createDefault();
				HttpPost httpPost = new HttpPost(LOGIN_URL);
				httpPost.addHeader("User-Agent", USER_AGENT);
				
				HttpEntity postParams = new UrlEncodedFormEntity(urlParameters);
				httpPost.setEntity(postParams);

				CloseableHttpResponse httpResponse = httpClient.execute(httpPost);

				BufferedReader reader = new BufferedReader(new InputStreamReader(
					httpResponse.getEntity().getContent()));

				String inputLine;
				StringBuffer response = new StringBuffer();

				while ((inputLine = reader.readLine()) != null) {
					response.append(inputLine);
				}
				reader.close();
				
				//Determining whether or not the login info was valid
				if (response.toString().equals("Username or password is incorrect")){
					JOptionPane.showMessageDialog(null, response.toString());
				}
				else
				{
					String[] splitData = response.toString().split("\"");
					String myToken = splitData[3];
					//System.out.println("Got here");
					mFrame.logInUser("Bearer " + myToken);
				}
				httpClient.close();
			}catch (Exception err){
				JOptionPane.showMessageDialog(null, "Error: Cannot connect to database");
				System.out.println("Error");
			}
			txtUser.setText("");
			txtPass.setText("");
			
		}
		//If the exit button is pressed 
		else {
			System.exit(0);
		}
	}
}