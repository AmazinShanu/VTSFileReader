package util;

import java.io.FileInputStream;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import jxl.Sheet;
import jxl.Workbook;

public class JKPlantImporter 
{
	
	
	
	public static void updateDeportData(PlantDetails plantDetails)
	{
		try
		{
			int plantCount =0 ;
			String sql = "SELECT * FROM plant_master where plant_code='"+plantDetails.getPlantCode()+"'";
			Statement stmt = DatabaseUtil.getConnection().createStatement();
			ResultSet rs = stmt.executeQuery(sql);	
		 
			
			while(rs.next())
			{
				plantCount++;
			}
			
			if(plantCount == 0)
			{
				insertPlant(plantDetails);
			}
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
		
	}
	
	
	
	
	
	
	
	public static int insertPlant(PlantDetails plantDetails)
	{
		int id=0;
		try
		{
			java.util.Date dt = new java.util.Date();
			java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String currentTime = sdf.format(dt);
			
			
			String query = " insert into plant_master (plant_code, plant_name, type, isActive, comp_id,createdOn,updatedOn,status)"
			        + " values (?, ?, ?, ?, ?,?,?,?)";
			
					PreparedStatement preparedStmt = DatabaseUtil.getConnection().prepareStatement(query,Statement.RETURN_GENERATED_KEYS);
					
				
			      preparedStmt.setString (1, plantDetails.getPlantCode());
			      preparedStmt.setString (2, plantDetails.getPlantName());
			      preparedStmt.setString   (3, plantDetails.getPlantType());
			      preparedStmt.setString(4, "1");
			      preparedStmt.setString    (5, "10");
			      preparedStmt.setString    (6, currentTime);
			      preparedStmt.setString    (7, currentTime);
			      preparedStmt.setString    (8, "A");
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
			String FilePath = "D:\\Vishal\\ProjectBackup\\JKGPS\\masterData\\depot.xls";
				//String FilePath = "D:\\Vishal\\ProjectBackup\\JKGPS\\plant.xls";
				FileInputStream fs = new FileInputStream(FilePath);
				Workbook wb = Workbook.getWorkbook(fs);
		
			Sheet sh = wb.getSheet("Sheet1");
			int totalNoOfRows = sh.getRows();
			int totalNoOfCols = sh.getColumns();
			
			
			for (int row = 1; row < totalNoOfRows; row++) 
			{
				String vehicleNumber = null;
				PlantDetails plant = new PlantDetails();
			
				for (int col = 0; col < totalNoOfCols; col++) 
				{
					
					String ssss = sh.getCell(col, row).getContents();
					
					if(ssss != null && !ssss.equals(""))
					{
						
						if(col == 0)
						{
							plant.setPlantCode(ssss);
						}
						else if(col == 1)
						{
							plant.setPlantName(ssss);
						}
						else if(col == 2)
						{
							plant.setPlantType("D");
						}
						
					}
					else
					{
						
					}
				
				}
				
				insertPlant(plant);
				
				System.out.println();
			}
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
	}
}
