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
                      
    public LoginPage(MainFrame mf) {
		
		mFrame = mf;
		

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jTextField2 = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();

        jLabel1.setText("Username:");

        jLabel2.setText("Password:");

        jButton1.setText("Cancel");
		jButton1.addActionListener(this);

        jButton2.setText("OK");
		jButton2.addActionListener(this);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(jLabel2))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jTextField1)
                            .addComponent(jTextField2, javax.swing.GroupLayout.DEFAULT_SIZE, 134, Short.MAX_VALUE)))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jButton1)
                        .addGap(18, 18, 18)
                        .addComponent(jButton2)))
                .addContainerGap(199, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(30, 30, 30)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(jButton2))
                .addContainerGap(205, Short.MAX_VALUE))
        );
    }// </editor-fold>                                                                 


    // Variables declaration - do not modify                     
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    // End of variables declaration   

	public void actionPerformed(ActionEvent e){
		
		Object src=e.getSource();
		//upload button
        if(src.equals(jButton2)){
			
			String s1 = jTextField1.getText(); //get from textfield1 (username)
			String s2 = jTextField2.getText(); //get from textfield2 (password)
		
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