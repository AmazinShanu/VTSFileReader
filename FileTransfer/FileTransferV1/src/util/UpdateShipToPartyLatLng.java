package util;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import sun.awt.dnd.SunDragSourceContextPeer;

public class UpdateShipToPartyLatLng 
{
	
	public static void updateLatLng()
	{
		try
		{
			java.util.Date dt = new java.util.Date();
			java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String currentTime = sdf.format(dt);
			
			
			String allLocationSql = "SELECT * FROM location_update where location_update.isUpdated IS NULL";
			Statement allLocationstmt = DatabaseUtil.getConnection().createStatement();
			ResultSet allLocationstmtRS = allLocationstmt.executeQuery(allLocationSql);	
		 
			while(allLocationstmtRS.next())
			{
				UpdateLocation updateLocation = new UpdateLocation();
				
				updateLocation.setId(allLocationstmtRS.getString("id"));
				updateLocation.setCode(allLocationstmtRS.getString("code"));
				updateLocation.setLatitude(allLocationstmtRS.getString("latitude"));
				updateLocation.setLongitude(allLocationstmtRS.getString("longitude"));
				updateLocation.setCreatedOn(allLocationstmtRS.getString("createdOn"));
				updateLocation.setIsUpdated(allLocationstmtRS.getString("isUpdated"));
				
				
				/*if(updateLocation.getIsUpdated() != null && !updateLocation.getIsUpdated().equals("U"))
				{*/
					
				if(updateLocation.getLatitude() != null && updateLocation.getLatitude() != null)
				{
				if(!updateLocation.getLatitude().equals("") && !updateLocation.getLatitude().equals(""))
				{
					if(!updateLocation.getLatitude().equals("0.0") && !updateLocation.getLatitude().equals("0.0"))
					{
						String updateTableSQL = "UPDATE ship_to_party SET latitude = ?,longitude = ?,locationUpdatedOn = ? WHERE ship_to_party_code = ?";
						PreparedStatement preparedStatement = DatabaseUtil.getConnection().prepareStatement(updateTableSQL);
						
						preparedStatement.setString(1,updateLocation.getLatitude() );
						preparedStatement.setString(2, updateLocation.getLongitude());
						preparedStatement.setString(3, currentTime);
						preparedStatement.setString(4, updateLocation.getCode());
						// execute insert SQL stetement
						preparedStatement .executeUpdate();
						
						
						String updateLatLngFlag = "UPDATE location_update SET isUpdated = ? WHERE code = ?";
						PreparedStatement updateLatLngFlagStatement = DatabaseUtil.getConnection().prepareStatement(updateLatLngFlag);
						
						updateLatLngFlagStatement.setString(1, "U");
						updateLatLngFlagStatement.setString(2, updateLocation.getCode());
						// execute insert SQL stetement
						updateLatLngFlagStatement .executeUpdate();
						
						System.out.println("id"+updateLocation.getId()+"Code"+updateLocation.getCode()+"Updated "+updateLocation.getLatitude()+"  "+updateLocation.getLongitude());
					}
				}
				}
			}
				
			//}
			
			 
			System.out.println("Completed");
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
	}
	
	
	
	public static void main(String s[])
	{
		updateLatLng();
	}
}
