package util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;

public class ReadCSV 
{
	
	public static void readData()
	{
		String server = "ftp.amazinvts.com";
		 int port = 21;
	     String user = "test@amazinvts.com";
	     String pass = "12345";
		
		
		BufferedReader reader = null;
		String firstLine = null;
		
		 	FTPClient ftpClient = new FTPClient();
	        try 
	        {
	 
	            ftpClient.connect(server, port);
	            ftpClient.login(user, pass);
	            ftpClient.enterLocalPassiveMode();
	            ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
	 
	            // APPROACH #1: using retrieveFile(String, OutputStream)
	            String remoteFile1 = "/home/amazinvt/public_html/amazinvts.com/test/download.jpg";
	            
	            
	            try 
	            {
	                InputStream stream = ftpClient.retrieveFileStream(remoteFile1);
	                reader = new BufferedReader(new InputStreamReader(stream, "UTF-8"));
	                firstLine = reader.readLine();
	                
	                
	            } 
	            finally 
	            {
	                if (reader != null) try { reader.close(); } catch (IOException logOrIgnore) {}
	            }
	            
	            
	        }
	        catch(Exception e)
	        {
	        	
	        }
			
	}
}
