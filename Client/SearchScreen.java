import javax.swing.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Image;
import java.awt.Color;


public class SearchScreen extends JPanel implements ActionListener{

    MainFrame mFrame;
	
	private JList<CellDataEntry> dataList;

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">                          
    public SearchScreen(MainFrame mf){

		mFrame = mf;
		
		CellDataEntry c;
		DefaultListModel<CellDataEntry> listModel = new DefaultListModel<>();
		for (int i = 0; i<19; i++){
			c = new CellDataEntry("420BlazeIt", "This describes the picture " + i, "https://i.ytimg.com/vi/tntOCGkgt98/maxresdefault.jpg");
			listModel.addElement(c);
		}
	
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
        jScrollPane1.setBounds(180, 10, 610, 380);
        add(jTextField1);
        jTextField1.setBounds(10, 30, 150, 20);

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "All", "Tags", "Description"}));
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
        jButton2.setBounds(10, 360, 130, 23);
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
			
		}
		else 
			mFrame.showMain();
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
