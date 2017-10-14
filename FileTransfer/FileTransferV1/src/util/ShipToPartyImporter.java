package util;

import java.io.FileInputStream;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import com.mysql.jdbc.StandardSocketFactory;

import jxl.Sheet;
import jxl.Workbook;

public class ShipToPartyImporter 
{
	public static int insertShipToParty(ShipToPartyInfo shipToPartyInfo)
	{
		int id=0;
		try
		{
			
			java.util.Date dt = new java.util.Date();
			java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String currentTime = sdf.format(dt);
			
			int aa=0;
			
			String sql = "SELECT * from ship_to_party WHERE ship_to_party_code='"+ shipToPartyInfo.getShip_to_party_code()+"'";
			 Statement stmt = DatabaseUtil.getConnection().createStatement();
			 ResultSet rs = stmt.executeQuery(sql);	
			
			 while(rs.next())
			 {
				 aa++;
			 }
			
			if(aa == 0)
			{
				String query = " insert into ship_to_party (ship_to_party_code, name, distChannel,region,regionDescription, divison, mobile_number,email_id,isActive,createdOn,updatedOn,status)"
				        + " values (?,?,?,?,?,?,?,?,?,?,?,?)";
				
						PreparedStatement preparedStmt = DatabaseUtil.getConnection().prepareStatement(query,Statement.RETURN_GENERATED_KEYS);
						
					
				      preparedStmt.setString (1, shipToPartyInfo.getShip_to_party_code());
				      preparedStmt.setString (2, shipToPartyInfo.getName());
				      preparedStmt.setString   (3, shipToPartyInfo.getDistChannel());
				      preparedStmt.setString   (4, shipToPartyInfo.getRegion());
				      preparedStmt.setString   (5, shipToPartyInfo.getRegionDescription());
				      preparedStmt.setString(6, shipToPartyInfo.getDivison());
				      preparedStmt.setString    (7, "NA");
				      preparedStmt.setString    (8, "NA");
				      preparedStmt.setString    (9, "1");
				      preparedStmt.setString    (10, currentTime);
				      preparedStmt.setString    (11, currentTime);
				      preparedStmt.setString    (12, "A");
				      id =  preparedStmt.executeUpdate();
				      
				      
				      if(id>0) 
						{
							   ResultSet rs2 = preparedStmt.getGeneratedKeys();
							    rs2.next();
							    id = rs2.getInt(1);
						}
				      
				      System.out.println("Inserted");
			}
			else
			{
				System.out.println("Exist");
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
				//String FilePath = "D:\\Vishal\\ProjectBackup\\JKGPS\\masterData\\custmaster.xls";
			
			String FilePath = "C:\\Users\\Amazin\\Documents\\Customer_List(JK)\\PendingCustomerslatest1.xls";
			
				FileInputStream fs = new FileInputStream(FilePath);
				Workbook wb = Workbook.getWorkbook(fs);
		
			Sheet sh = wb.getSheet("Sheet1");
			int totalNoOfRows = sh.getRows();
			int totalNoOfCols = sh.getColumns();
			
			for (int row = 1; row < totalNoOfRows; row++) 
			{
				ShipToPartyInfo shipToPartyInfo = new ShipToPartyInfo();
			
				for (int col = 0; col < totalNoOfCols; col++) 
				{
					String ssss = sh.getCell(col, row).getContents();
					if(ssss != null && !ssss.equals(""))
					{
						
						if(col == 0)
						{
							shipToPartyInfo.setShip_to_party_code(ssss);
							
						}
						else if(col == 1)
						{
							
							shipToPartyInfo.setName(ssss);
							
						}
						else if(col == 2)
						{
							
							//shipToPartyInfo.setRegion(ssss);
						}
						else if(col == 3)
						{
							
							shipToPartyInfo.setRegionDescription(ssss);
						}
						else if(col == 4)
						{
							//shipToPartyInfo.setDistChannel(ssss);
						}
						else if(col == 5)
						{
							//shipToPartyInfo.setDivison(ssss);
						}
						else if(col == 6)
						{
							
							//shipToPartyInfo.setRegion(ssss);
						}
						else if(col == 7)
						{
							
							//shipToPartyInfo.setRegion(ssss);
						}
						else if(col == 8)
						{
							
							shipToPartyInfo.setLatitude(ssss);
						}
						else if(col == 9)
						{
							
							shipToPartyInfo.setLongitude(ssss);
						}
						
					}
					else
					{
						
					}
				
				}
				insertShipToParty(shipToPartyInfo);
				System.out.println("code" +shipToPartyInfo.getShip_to_party_code());
				System.out.println("name" +shipToPartyInfo.getName());
				System.out.println("region" +shipToPartyInfo.getRegion());
				System.out.println("discription" +shipToPartyInfo.getRegionDescription());
				System.out.println("distChannal" +shipToPartyInfo.getDistChannel());
				System.out.println("division" + shipToPartyInfo.getDivison());
				System.out.println("Latitude:"+ shipToPartyInfo.getLatitude());
				System.out.println("Longtude:"+shipToPartyInfo.getLongitude());
			}
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
	}
}
