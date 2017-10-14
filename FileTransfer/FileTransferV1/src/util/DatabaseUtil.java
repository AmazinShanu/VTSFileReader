package util;

import java.sql.Connection;
import java.sql.DriverManager;


public class DatabaseUtil 
{
	/*static
	{
		try
		{
			Class.forName("com.mysql.jdbc.Driver"); 
		}
		catch(Exception e)
		{
			
		}
	}*/
	private static Connection connection=null;
	//private static Connection testConnection=null;
	public static Connection getConnection()
	{
		if(connection == null)
		{
			try
			{  
				Class.forName("com.mysql.jdbc.Driver"); 
				connection=DriverManager.getConnection("jdbc:mysql://35.189.165.109:3306/jk_vts_live_database","connect","connectamazin4321");
				//connection=DriverManager.getConnection("jdbc:mysql://35.189.165.109:3306/test_vts_database","connect","connectamazin4321");
			}
			catch(Exception e)
			{
				System.out.println(e.getMessage());
			}
			
			return connection;
		}
		else
		{
			return connection;
		}
	}
	
	/*public static Connection getTestConnection()
	{
		if(testConnection == null)
		{
			try
			{  
				//Class.forName("com.mysql.jdbc.Driver");  
				//connection=DriverManager.getConnection("jdbc:mysql://35.189.165.109:3306/jk_vts_live_database","connect","connectamazin4321");
				testConnection=DriverManager.getConnection("jdbc:mysql://35.189.165.109:3306/test_vts_database","connect","connectamazin4321");
			}
			catch(Exception e)
			{
				System.out.println(e.getMessage());
			}
			
			return testConnection;
		}
		else
		{
			return testConnection;
		}
	}*/
	
	
	
	
	/*public static void main(String s[])
	{
		try
		{
			System.out.println(getAllAdminUserList());
			for(AdminUser adminUser: getAllAdminUserList())
			{
				System.out.println(adminUser.getUsername());
			}
			
			System.out.println(getAdminUser("Admin","Admin").getUsername());
			
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
		}
		
	}*/
	
	
	
	
	
	
}
