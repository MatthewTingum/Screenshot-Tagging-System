import javax.swing.*;
import java.awt.Image;
import java.awt.Component;
//import javax.swing.ListCellRenderer;
//import net.codejava.model.Country;
 
/**
 * Custom renderer to display a country's flag alongside its name
 *
 * @author wwww.codejava.net
 */
public class DataRenderer extends JLabel implements ListCellRenderer<CellDataEntry> {
 
    public DataRenderer() {
        setOpaque(true);
    }
 
    @Override
    public Component getListCellRendererComponent(JList<? extends CellDataEntry> list, CellDataEntry cellData, int index,
            boolean isSelected, boolean cellHasFocus) {
 
        //String code = cellData.getCode();
        //ImageIcon imageIcon = new ImageIcon(getClass().getResource("/images/" + code + ".png"));
		//ImageIcon imageIcon = new ImageIcon(new ImageIcon("WowEx.png").getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT));
		ImageIcon imageIcon = new ImageIcon(cellData.getImage().getImage().getScaledInstance(200, 200, Image.SCALE_DEFAULT));
 
        setIcon(imageIcon);
        setText("<html>#" + cellData.getTag() + "<br><br>" + cellData.getDesc() + "</html>");
 
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