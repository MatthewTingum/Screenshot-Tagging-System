import javax.swing.*;
import java.awt.Image;
import java.awt.Component;
 
/**
 * The DataRenderer class sets up the layout of data for each entry in the table on the search screen
 * @author Richard Lee
 * @version 2.0
 */ 
 
public class DataRenderer extends JLabel implements ListCellRenderer<CellDataEntry> {
 
	/**
	 * This constructor method creates and returns a DataRenderer object
	 */
    public DataRenderer() {
        setOpaque(true);
    }
 
	/**
	 * This method sets up the layout of information for when the data is displayed in the table on the GUI
	 */
    @Override
    public Component getListCellRendererComponent(JList<? extends CellDataEntry> list, CellDataEntry cellData, int index,
            boolean isSelected, boolean cellHasFocus) {
 
		ImageIcon imageIcon = new ImageIcon(cellData.getImage().getImage().getScaledInstance(500, 300, Image.SCALE_DEFAULT));
 
        setIcon(imageIcon);
        setText("<html>Tag: #" + cellData.getTag() + "<br>Description: " + cellData.getDesc()+ "<br>Character: " + cellData.getChar()
								+ "<br>Location: " + cellData.getLoc() + "</html>");
 
        if (isSelected) {
            setBackground(list.getSelectionBackground());
            setForeground(list.getSelectionForeground());
        } else {
            setBackground(list.getBackground());
            setForeground(list.getForeground());
        }
 
        return this;
    }
} 