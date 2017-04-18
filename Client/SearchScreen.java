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


public class SearchScreen extends JPanel implements ActionListener{
	
	// The sauce
	private static final String GET_URL = "http://localhost:3000/api/submissions/submission";
	private static final String USER_AGENT = "Mozilla/5.0";

    MainFrame mFrame;
	
	private JList<CellDataEntry> dataList;
	DefaultListModel<CellDataEntry> listModel;

    //@SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">                          
    public SearchScreen(MainFrame mf){

		mFrame = mf;
		
		listModel = new DefaultListModel<>();
		
		/*
		for (int i = 0; i<3; i++){
			c = new CellDataEntry("WowTag", "This describes the picture " + i, "http://www.icxm.net/team/uploads/0-Media/WoWScrnShot_010213_205208.jpg");
			listModel.addElement(c);
		}
		*/
		
		/*////// BEGIN SAUCE /////////////////////////////////////////////////////////////
		
		try{
				CloseableHttpClient httpClient = HttpClients.createDefault();
				HttpGet request = new HttpGet(GET_URL);
				request.addHeader("User-Agent", USER_AGENT);
				
				// This is not the sauce. Make it sauce.
				request.addHeader("Authorization", "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiI1ODMxZjVjMzNkYjBhODE5MzAwNGVmODAiLCJpYXQiOjE0Nzk2Nzg4OTB9.Zc03s4RXZmydhAUb-rb4AbQwAXbZZ56ICMwG_0SI5iM");
				
				HttpResponse response = httpClient.execute(request);
				
				System.out.println("\nSending 'GET' request to URL : " + GET_URL);
				System.out.println("Response Code : " +
                response.getStatusLine().getStatusCode());
				
				BufferedReader reader = new BufferedReader(new InputStreamReader(
					response.getEntity().getContent()));

				String inputLine;
				StringBuffer responseSB = new StringBuffer();

				while ((inputLine = reader.readLine()) != null) {
					responseSB.append(inputLine);
				}
				reader.close();
				
				// print result
				//System.out.println(responseSB.toString());
				
				// This is because I'm lazy
				String x = responseSB.toString();
				x = "{ \"data\":" + x.substring(0, x.length()) + "}";
				
				System.out.println(x);
				
				JsonReader rdr = Json.createReader(new StringReader(x));
				//JsonReader rdr = Json.createReader(new StringReader("{\"data\" : [{ \"from\" : { \"name\" : \"xxx\"}, \"message\" : \"yyy\"},{ \"from\" : { \"name\" : \"ppp\"}, \"message\" : \"qqq\"}]}"));
				
				JsonObject obj = rdr.readObject();
				JsonArray results = obj.getJsonArray("data");
				
				for (JsonObject result : results.getValuesAs(JsonObject.class)) {
					System.out.println("-----------");
					System.out.println(result.getString("Description", ""));
					
					//c = new CellDataEntry("WowTag", result.getString("Description", ""), "http://www.icxm.net/team/uploads/0-Media/WoWScrnShot_010213_205208.jpg");
					c = new CellDataEntry("WowTag", result.getString("Description", ""), "C:/Users/kirby/Desktop/CSCI493/github/Screenshot-Tagging-System/Server/app/" + result.getString("ImagePath", ""));
					System.out.println(result.getString("ImagePath", "") + " LOOOOOOOK @ MEEEEEEEEEEEEE");
					listModel.addElement(c);
				}
					   
				httpClient.close();
			}catch (Exception err){
				System.out.println("Error");
				//err.printStackTrace();
			}
		
		
		// THING: result.getString("ImagePath", "")
		///*/// END SAUCE ////////////////////////////////////////////////////////////////
	
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

		/*
        jList1.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });*/
        //jScrollPane1.setViewportView(dataList);
		

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
		
		//test();
    }// </editor-fold>                                                            


    // Variables declaration - do not modify                     
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    //private javax.swing.JList<String> jList1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField jTextField1;
    // End of variables declaration  

	public void actionPerformed(ActionEvent e){
		Object src=e.getSource();
		//upload button
        if(src.equals(jButton1)) {
			searchData();
		}
		else 
			mFrame.showMain();
    }
	
	public void searchData(){
		listModel.removeAllElements();
		CellDataEntry c;
		String category = jComboBox1.getSelectedItem().toString();
		System.out.println("HERE\n\n\n"+ category + "\n\n\n");
		String keyword = jTextField1.getText();
		try{
				CloseableHttpClient httpClient = HttpClients.createDefault();
				HttpGet request = new HttpGet(GET_URL);
				request.addHeader("User-Agent", USER_AGENT);
				
				// This is not the sauce. Make it sauce.
				//request.addHeader("Authorization", "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiI1ODMxZjVjMzNkYjBhODE5MzAwNGVmODAiLCJpYXQiOjE0Nzk2Nzg4OTB9.Zc03s4RXZmydhAUb-rb4AbQwAXbZZ56ICMwG_0SI5iM");
				
				//Use new methods //////////////////////////////////////////////////////////
				request.addHeader("Authorization", mFrame.getToken());
				//System.out.println("a");
				
				HttpResponse response = httpClient.execute(request);
				
				System.out.println("\nSending 'GET' request to URL : " + GET_URL);
				System.out.println("Response Code : " +
                response.getStatusLine().getStatusCode());
				
				
				BufferedReader reader = new BufferedReader(new InputStreamReader(
					response.getEntity().getContent()));
				

				String inputLine;
				StringBuffer responseSB = new StringBuffer();

				while ((inputLine = reader.readLine()) != null) {
					responseSB.append(inputLine);
				}
				reader.close();
				//System.out.println("c");
				
				// print result
				//System.out.println(responseSB.toString());
				
				// This is because I'm lazy
				String x = responseSB.toString();
				x = "{ \"data\":" + x.substring(0, x.length()) + "}";
				
				//System.out.println(x);
				//System.out.println("d");
				
				JsonReader rdr = Json.createReader(new StringReader(x));
				//JsonReader rdr = Json.createReader(new StringReader("{\"data\" : [{ \"from\" : { \"name\" : \"xxx\"}, \"message\" : \"yyy\"},{ \"from\" : { \"name\" : \"ppp\"}, \"message\" : \"qqq\"}]}"));
				
				//System.out.println("b");
				JsonObject obj = rdr.readObject();
				//System.out.println("b");
				JsonArray results = obj.getJsonArray("data");
				for (JsonObject result : results.getValuesAs(JsonObject.class)) {
					
					System.out.println("-----------");
					System.out.println(result.getString("Description", ""));
					
					File imageFileStart = new File("../Server/app");
					//c = new CellDataEntry("WowTag", result.getString("Description", ""), "http://www.icxm.net/team/uploads/0-Media/WoWScrnShot_010213_205208.jpg");
					c = new CellDataEntry(result.getString("Tags", ""), result.getString("Description", ""), 
											result.getString("Character", ""), result.getString("Location", ""), 
											imageFileStart.getAbsolutePath()+ "/" + result.getString("ImagePath", ""));
					//System.out.println(result.getString("ImagePath", "") + " LOOOOOOOK @ MEEEEEEEEEEEEE");
					if (category.equals("All")){
						if (searchAll(keyword, result.getString("Description", ""), result.getString("Tags", ""), result.getString("Character", ""), result.getString("Location", "")))
							listModel.addElement(c);
					}
					else{
						if (matchSearch(keyword, result.getString(category, "")))
							listModel.addElement(c);
					}
				}
					   
					   //System.out.println("e");
				httpClient.close();
			}catch (Exception err){
				System.out.println("Error");
				//err.printStackTrace();
			}
	}
	
	public boolean matchSearch(String keyword, String sample){
		if (keyword.isEmpty())
			return true;
		Pattern pattern = Pattern.compile(keyword.toLowerCase());
		Matcher match = pattern.matcher(sample.toLowerCase());
		if (match.find())
			return true;
		return false;
	}
	
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

	/*
	public void test(){
        DefaultListModel<DataEntry> defaultListModel = new DefaultListModel();
        for(int i  = 0; i< 20;i++){
            Icon icon = null;
            try {
                icon = new ImageIcon(new ImageIcon("WowEx.png").getImage().getScaledInstance(150, 150, Image.SCALE_DEFAULT));
            } catch (NullPointerException e) {
                e.printStackTrace();
            }
            defaultListModel.addElement(new DataEntry("name "+ i, "addr "+i, icon));
			//System.out.println(i);
        }
        //jList2.setCellRenderer(new mypanel());
        
        dataList.setModel(defaultListModel);
        dataList.setCellRenderer(new dataPanel());
    }	*/
}