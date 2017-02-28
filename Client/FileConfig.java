import java.util.Properties;
import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.io.IOException;

public class FileConfig
{
	public static Properties fileConfig = new Properties();
	
	public void saveFileInfo(String value)
	{
		try
		{
			fileConfig.setProperty("File", value);
			fileConfig.store(new FileOutputStream("fileConfig.prop"), null);
		}
		catch(IOException e)
		{
			System.out.println("");
		}
	}
	
	public String getFileInfo()
	{
		String value = "";
		try
		{
			fileConfig.load(new FileInputStream("fileConfig.prop"));
			value = fileConfig.getProperty("File");
		}
		catch(IOException e)
		{
			System.out.println("");
		}
		return value;
	}
}