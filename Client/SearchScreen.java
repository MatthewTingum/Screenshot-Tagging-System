import javax.swing.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Image;
import java.awt.Color;
import java.util.*;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.io.*;


import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import javax.json.JsonObject;
import javax.json.JsonArray;
import javax.json.JsonReader;
import javax.json.Json;

import java.io.StringReader;

/**
 * The SearchScreen class creates the search screen of the GUI 
 * @author Richard Lee
 * @version 2.0
 */ 

public class SearchScreen extends JPanel implements ActionListener{
	
	private static final String GET_URL = "http://localhost:3000/api/submissions/submission";
	private static final String USER_AGENT = "Mozilla/5.0";

    MainFrame mFrame;
	
	private JList<CellDataEntry> dataList;
	DefaultListModel<CellDataEntry> listModel;

    /**
     * The constructor creates and returns an instance of SearchScreen 
	 * @param mf the MainFrame of the GUI 
	 */
    public SearchScreen(MainFrame mf){

		mFrame = mf;
		
		listModel = new DefaultListModel<>();
		
		dataList = new JList<>(listModel);
		
        jScrollPane1 = new javax.swing.JScrollPane(dataList);
		dataList.setCellRenderer(new DataRenderer());
		
        jTextField1 = new javax.swing.JTextField();
        jComboBox1 = new javax.swing.JComboBox<>();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
		dataList = new javax.swing.JList<>();

        setLayout(null);
		
        add(jScrollPane1);
        jScrollPane1.setBounds(180, 10, 900, 540);
        add(jTextField1);
        jTextField1.setBounds(10, 30, 150, 20);

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "All", "Tags", "Description", "Character", "Location"}));
        add(jComboBox1);
        jComboBox1.setBounds(10, 100, 150, 20);

        jLabel1.setText("Enter Keyword");
        add(jLabel1);
        jLabel1.setBounds(10, 10, 110, 14);

        jLabel2.setText("Category");
        add(jLabel2);
        jLabel2.setBounds(10, 80, 110, 14);

        jButton1.setText("Search");
        add(jButton1);
        jButton1.setBounds(10, 170, 120, 23);
		jButton1.addActionListener(this);

        jButton2.setText("Back");
        add(jButton2);
        jButton2.setBounds(10, 540, 130, 23);
		jButton2.addActionListener(this);
    }                                                           

                    
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField jTextField1;

	/**
	 * This method is called if a button is pressed on the Search screen
	 * @param e the ActionEvent of a button press
	 */
	public void actionPerformed(ActionEvent e){
		Object src=e.getSource();
		//if the search button is pressed 
        if(src.equals(jButton1)) {
			searchData();
		}
		//if the back button is pressed 
		else 
			mFrame.showMain();
    }
	
	/**
	 * This method is called when the search button is pressed
	 */
	public void searchData(){
		listModel.removeAllElements();
		CellDataEntry c;
		String category = jComboBox1.getSelectedItem().toString();
		String keyword = jTextField1.getText();
		try{
				CloseableHttpClient httpClient = HttpClients.createDefault();
				HttpGet request = new HttpGet(GET_URL);
				request.addHeader("User-Agent", USER_AGENT);
				
				request.addHeader("Authorization", mFrame.getToken());
				
				HttpResponse response = httpClient.execute(request);
				
                response.getStatusLine().getStatusCode();
				
				
				BufferedReader reader = new BufferedReader(new InputStreamReader(
					response.getEntity().getContent()));
				

				String inputLine;
				StringBuffer responseSB = new StringBuffer();

				while ((inputLine = reader.readLine()) != null) {
					responseSB.append(inputLine);
				}
				reader.close();
				
				String x = responseSB.toString();
				x = "{ \"data\":" + x.substring(0, x.length()) + "}";
				
				
				JsonReader rdr = Json.createReader(new StringReader(x));
				
				JsonObject obj = rdr.readObject();
				JsonArray results = obj.getJsonArray("data");
				for (JsonObject result : results.getValuesAs(JsonObject.class)) {
					
					System.out.println("-----------");
					System.out.println(result.getString("Description", ""));
					
					File imageFileStart = new File("../Server/app");
					
					//Creates a CellDataEntry object for an entry
					c = new CellDataEntry(result.getString("Tags", ""), result.getString("Description", ""), 
											result.getString("Character", ""), result.getString("Location", ""), 
											imageFileStart.getAbsolutePath()+ "/" + result.getString("ImagePath", ""));
											
					//If all is the selected category 
					if (category.equals("All")){
						//If the CellDataEntry object's info math the search criteria, display in the list 
						if (searchAll(keyword, result.getString("Description", ""), result.getString("Tags", ""), result.getString("Character", ""), result.getString("Location", "")))
							listModel.addElement(c);
					}
					//If a different category is selected 
					else{
						//If the CellDataEntry object's info math the search criteria, display in the list 
						if (matchSearch(keyword, result.getString(category, "")))
							listModel.addElement(c);
					}
				}
					   
				httpClient.close();
			}catch (Exception err){
				System.out.println("Error");
			}
	}
	
	/**
	 * This method takes a keyword and the data info and checks for a match 
	 * @param keyword, the string being searched for 
	 * @param sample, the data being searched 
	 * @return boolean, if there is a match or keyword was left empty, return true.  Otherwise, return false 
	 */
	public boolean matchSearch(String keyword, String sample){
		if (keyword.isEmpty())
			return true;
		Pattern pattern = Pattern.compile(keyword.toLowerCase());
		Matcher match = pattern.matcher(sample.toLowerCase());
		if (match.find())
			return true;
		return false;
	}
	
	/**
	 * This method takes a keyword and the data info and checks for a match 
	 * @param keyword, the string being searched for 
	 * @param descript, the description part of the data being searched 
	 * @param tagg, the tag part of the data being searched 
	 * @param charact, the character part of the data being searched
	 * @param locate, the location part of the data being searched 
	 * @return boolean, if there is a match or keyword was left empty, return true.  Otherwise, return false 
	 */
	public boolean searchAll(String keyword, String descript, String tagg, String charact, String locate){
		if (keyword.isEmpty())
			return true;
		Pattern pattern = Pattern.compile(keyword.toLowerCase());
		Matcher m1 = pattern.matcher(descript.toLowerCase());
		Matcher m2 = pattern.matcher(tagg.toLowerCase());
		Matcher m3 = pattern.matcher(charact.toLowerCase());
		Matcher m4 = pattern.matcher(locate.toLowerCase());
		if (m1.find())
			return true;
		if (m2.find())
			return true;
		if (m3.find())
			return true;
		if (m4.find())
			return true;
		return false;
	}
}