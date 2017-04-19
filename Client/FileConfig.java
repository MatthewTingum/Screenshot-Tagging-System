import java.util.Properties;
import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * The FileConfig class sets up a way to save information 
 * @author Richard Lee
 * @version 2.0
 */ 

public class FileConfig
{
	public static Properties fileConfig = new Properties();
	
	/**
	 * This method saves a string values using a given name
	 * @param value The string value to be saved
	 * @param name The name of the value to be saved
	 */
	public void saveFileInfo(String value, String name)
	{
		try
		{
			fileConfig.setProperty(name, value);
			fileConfig.store(new FileOutputStream("fileConfig.prop"), null);
		}
		catch(IOException e)
		{
			System.out.println("");
		}
	}
	
	/** 
	 * This method gets the value of a string saved under a given name
	 * @param name The name of the value being accessed
	 * @return value The value of the string being accessed
	 */
	public String getFileInfo(String name)
	{
		String value = "";
		try
		{
			fileConfig.load(new FileInputStream("fileConfig.prop"));
			value = fileConfig.getProperty(name);
		}
		catch(IOException e)
		{
			System.out.println("");
		}
		return value;
	}
}