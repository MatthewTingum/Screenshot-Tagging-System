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


public class LoginPage extends JPanel implements ActionListener{

    MainFrame mFrame;
	
	private static final String LOGIN_URL = "http://localhost:3000/api/users/authenticate";
	private static final String USER_AGENT = "Mozilla/5.0";
	
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">                          
    public LoginPage(MainFrame mf) {
		
		mFrame = mf;

        lblUser = new javax.swing.JLabel();
        lblPass = new javax.swing.JLabel();
        txtUser = new javax.swing.JTextField();
        txtPass = new javax.swing.JTextField();
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

        btnCancel.setText("Cancel");
		btnCancel.addActionListener(this);
        add(btnCancel);
        btnCancel.setBounds(280, 250, 110, 30);

        btnOK.setText("OK");
		btnOK.addActionListener(this);
        add(btnOK);
        btnOK.setBounds(460, 250, 90, 30);
    }// </editor-fold>                        
                                      


    // Variables declaration - do not modify                     
    private javax.swing.JButton btnCancel;
    private javax.swing.JButton btnOK;
    private javax.swing.JLabel lblPass;
    private javax.swing.JLabel lblUser;
    private javax.swing.JTextField txtPass;
    private javax.swing.JTextField txtUser;
    // End of variables declaration  

	public void actionPerformed(ActionEvent e){
		
		Object src=e.getSource();
		//upload button
        if(src.equals(btnOK)){
			
			String s1 = txtUser.getText(); //get from textfield1 (username)
			String s2 = txtPass.getText(); //get from textfield2 (password)
		
			// This is where the user auth token will be (Using a static one for now -- linked to a test account)
			//httpPost.addHeader("Authorization", "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiI1ODMxZjVjMzNkYjBhODE5MzAwNGVmODAiLCJpYXQiOjE0Nzk2Nzg4OTB9.Zc03s4RXZmydhAUb-rb4AbQwAXbZZ56ICMwG_0SI5iM");

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

				// print result
				if (response.toString().equals("Username or password is incorrect")){
					JOptionPane.showMessageDialog(null, response.toString());
				}
				else
				{
					String[] splitData = response.toString().split("\"");
					System.out.println(splitData[3]);	// Token
					String myToken = splitData[3];
					mFrame.loginToken = "Bearer " + myToken;
					mFrame.loggedIN = true;
					System.out.println(mFrame.loginToken);
					mFrame.showMain();
				}
				httpClient.close();
			}catch (Exception err){
				System.out.println("Error");
				//err.printStackTrace();
			}
			
		}
		else {
			mFrame.showMain();
		}
	}
}
