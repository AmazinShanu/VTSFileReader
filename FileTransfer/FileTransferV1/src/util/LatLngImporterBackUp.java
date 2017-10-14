package util;

import java.io.FileInputStream;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import jxl.Sheet;
import jxl.Workbook;

public class LatLngImporterBackUp 
{
	public static int insertLatLng(UpdateLocation updateLocation)
	{
		int id=0;
		try
		{
			java.util.Date dt = new java.util.Date();
			java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String currentTime = sdf.format(dt);
			
			
			String query = " insert into location_update (code, latitude, longitude, createdOn, isUpdated)"
			        + " values (?, ?, ?, ?, ?)";
			
					PreparedStatement preparedStmt = DatabaseUtil.getConnection().prepareStatement(query,Statement.RETURN_GENERATED_KEYS);
					
				
			      preparedStmt.setString (1, updateLocation.getCode());
			      preparedStmt.setString (2, updateLocation.getLatitude());
			      preparedStmt.setString   (3, updateLocation.getLongitude());
			      preparedStmt.setString(4, currentTime);
			      preparedStmt.setString(5, "");
			     
			      id =  preparedStmt.executeUpdate();
			      
			      
			      if(id>0) 
					{
						   ResultSet rs2 = preparedStmt.getGeneratedKeys();
						    rs2.next();
						    id = rs2.getInt(1);
						    
						    System.out.println("Inserted");
					}
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
		return id;
	}
	
	
	public static void main(String s[])
	{
		try
		{
			
			
			
			String FilePath = "D:\\Vishal\\ProjectBackup\\JKGPS\\LatLngReport\\back\\LatLngB.xls";
				//String FilePath = "D:\\Vishal\\ProjectBackup\\JKGPS\\plant.xls";
				FileInputStream fs = new FileInputStream(FilePath);
				Workbook wb = Workbook.getWorkbook(fs);
		
			Sheet sh = wb.getSheet("Sheet2");
			int totalNoOfRows = sh.getRows();
			int totalNoOfCols = sh.getColumns();
			
			
			for (int row = 1; row < totalNoOfRows; row++) 
			{
				String vehicleNumber = null;
				//PlantDetails plant = new PlantDetails();
				UpdateLocation updateLocation = new UpdateLocation();
				for (int col = 0; col < totalNoOfCols; col++) 
				{
					
					String ssss = sh.getCell(col, row).getContents();
					//String ssss = sh.getCell(col, row).getContents()
					if(ssss != null && !ssss.equals(""))
					{
						
						if(col == 0)
						{
							System.out.println("Code "+ssss);
							updateLocation.setCode(ssss);
						}
						else if(col == 1)
						{
							System.out.println("Lat "+ssss);
							updateLocation.setLatitude(ssss);
						}
						else if(col == 2)
						{
							System.out.println("Lng "+ssss);
							updateLocation.setLongitude(ssss);
						}
						
					}
					else
					{
						
					}
				
				}
				
				insertLatLng(updateLocation);
				
				
			}
			System.out.println("Success");
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
	}
}
