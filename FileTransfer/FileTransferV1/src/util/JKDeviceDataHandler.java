package util;


import java.io.FileInputStream;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import jxl.Sheet;
import jxl.Workbook;


public class JKDeviceDataHandler 
{
	/*ArrayList<DeviceMaster> getAllDevice()
	{
		ArrayList<DeviceMaster> deviceList = new ArrayList<DeviceMaster>();
		try
		{
			
			String sql = "SELECT * FROM admin_user";
			 Statement stmt = DatabaseUtil.getConnection().createStatement();
			 ResultSet rs = stmt.executeQuery(sql);	
			 
			 
			 while(rs.next())
			 {
				 DeviceMaster deviceMaster = new DeviceMaster();
				 
				 deviceMaster.setId(Integer.parseInt(rs.getString("id")));
				 deviceMaster.setimeiNumber(rs.getString("imeiNumber"));
				 deviceMaster.setMobileNumber(rs.getString("mobileNumber"));
				 deviceMaster.setMobileCom(rs.getString("mobileCom"));
				 deviceMaster.setCreatedOn(rs.getString("createdOn"));
				 deviceMaster.setUpdatedOn(rs.getString("updatedOn"));
				 deviceMaster.setIsActive(rs.getString("isActive"));
				 deviceMaster.setplant_id(Integer.parseInt(rs.getString("plant_id")));
				 
				 deviceList.add(deviceMaster);
			 }
			 
			 rs.close();
			 stmt.close();
			
		}
		catch(Exception e)
		{
			
		}
		return deviceList;
	}*/
	
	
	
	public static int insertDeviceData(DeviceMaster deviceMaster)
	{
		int device_id=0;
		try
		{
			String query = " insert into device_master (imeiNumber, mobileNumber, mobileCom, createdOn, updatedOn,isActive,company_id)"
			        + " values (?, ?, ?, ?, ?,?,?)";
			
					PreparedStatement preparedStmt = DatabaseUtil.getConnection().prepareStatement(query,Statement.RETURN_GENERATED_KEYS);
			      preparedStmt.setString (1, deviceMaster.getimeiNumber());
			      preparedStmt.setString (2, deviceMaster.getMobileNumber());
			      preparedStmt.setString   (3, deviceMaster.getMobileCom());
			      preparedStmt.setString(4, deviceMaster.getCreatedOn());
			      preparedStmt.setString    (5, deviceMaster.getUpdatedOn());
			      preparedStmt.setString    (6, deviceMaster.getIsActive());
			      preparedStmt.setDouble(7, deviceMaster.getCompany_id());
			      
			      device_id = preparedStmt.executeUpdate();
			      
			      if(device_id>0) 
					{
						   ResultSet rs2 = preparedStmt.getGeneratedKeys();
						    rs2.next();
						    device_id = rs2.getInt(1);
					}
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
		
		return device_id;
	}
	
	
	public static int insertVehicle(Vehicle vehicle)
	{
		int vehicle_id=0;
		try
		{
			String query = " insert into vehicle (ownerName, vehical_capacity, vehicleNo, isActive, createdOn,No_of_wheels,status)"
			        + " values (?, ?, ?, ?, ?,?,?)";
			
					PreparedStatement preparedStmt = DatabaseUtil.getConnection().prepareStatement(query,Statement.RETURN_GENERATED_KEYS);
					
				
			      preparedStmt.setString (1, vehicle.getOwnerName());
			      preparedStmt.setString (2, vehicle.getVehical_capacity());
			      preparedStmt.setString   (3, vehicle.getVehicleNo());
			      preparedStmt.setString(4, vehicle.getIsActive());
			      preparedStmt.setString    (5, vehicle.getCreatedOn());
			      preparedStmt.setString    (6, vehicle.getNo_of_wheels());
			      preparedStmt.setString    (7, vehicle.getStatus());
			      vehicle_id =  preparedStmt.executeUpdate();
			      
			      
			      if(vehicle_id>0) 
					{
						   ResultSet rs2 = preparedStmt.getGeneratedKeys();
						    rs2.next();
						    vehicle_id = rs2.getInt(1);
					}
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
		return vehicle_id;
	}
	
	
	
	public static void insertVehicleStatus(VehicleStatus vehicleStatus)
	{
		try
		{
			String query = " insert into vehiclestatus (tripId, currentlatitude, currentlongitude, currentLocation, vehicleNumber,imeiNumber,loadingStatus,datetime,colorStatus,nearest_site,speed,owner_name,transporter_name,vehicle_capacity,number_of_wheels)"
			        + " values (?, ?, ?, ?, ?,?,?,?,?,?,?,?,?,?,?)";
			
					PreparedStatement preparedStmt = DatabaseUtil.getConnection().prepareStatement(query);
					
				
			      preparedStmt.setString (1, vehicleStatus.getTripId());
			      preparedStmt.setString(2, vehicleStatus.getCurrentlatitude());
			      preparedStmt.setString(3, vehicleStatus.getCurrentlongitude());
			      preparedStmt.setString(4, vehicleStatus.getCurrentLocation());
			      preparedStmt.setString(5, vehicleStatus.getVehicleNumber());
			      preparedStmt.setString(6, vehicleStatus.getImeiNumber());
			      preparedStmt.setString(7, vehicleStatus.getLoadingStatus());
			      preparedStmt.setString(8, vehicleStatus.getDatetime());
			      preparedStmt.setString(9, vehicleStatus.getColorStatus());
			      preparedStmt.setString(10, vehicleStatus.getNearest_site());
			      preparedStmt.setString(11, vehicleStatus.getSpeed());
			      preparedStmt.setString(12, vehicleStatus.getOwner_name());
			      preparedStmt.setString(13, vehicleStatus.getTransporter_name());
			      preparedStmt.setString(14, vehicleStatus.getVehicle_capacity());
			      preparedStmt.setString(15, vehicleStatus.getNumber_of_wheels());
			     
			     
			  preparedStmt.execute();
			     // System.out.println(as);
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
		
	}
	
	
	public static void insertPlantVehicleMap(PlantVehicleMap plantVehicleMap)
	{
		try
		{
			String query = " insert into plant_vehicle_map (plant_id, vehicle_id, createdOn, updatedOn, isActive)"
			        + " values (?, ?, ?, ?, ?)";
			
					PreparedStatement preparedStmt = DatabaseUtil.getConnection().prepareStatement(query);
				
				preparedStmt.setInt (1, plantVehicleMap.getPlant_id());
			      preparedStmt.setInt (2, plantVehicleMap.getVehicle_id());
			      preparedStmt.setString   (3, plantVehicleMap.getCreatedOn());
			      preparedStmt.setString(4, plantVehicleMap.getUpdatedOn());
			      preparedStmt.setString    (5, plantVehicleMap.getIsActive());
			     
			      preparedStmt.execute();;
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
		
	}
	
	
	
	public static void insertDeviceVehicleMap(DeviceVehicleMap deviceVehicleMap)
	{
		try
		{
			String query = " insert into vehicle_devicemaster_map (vehicle_id, deviceMaster_id, createdOn, updatedOn, isActive)"
			        + " values (?, ?, ?, ?, ?)";
			
					PreparedStatement preparedStmt = DatabaseUtil.getConnection().prepareStatement(query);
				
				preparedStmt.setInt (1, deviceVehicleMap.getVehicle_id());
			      preparedStmt.setInt (2, deviceVehicleMap.getDeviceMaster_id());
			      preparedStmt.setString   (3, deviceVehicleMap.getCreatedOn());
			      preparedStmt.setString(4, deviceVehicleMap.getUpdatedOn());
			      preparedStmt.setString    (5, deviceVehicleMap.getIsActive());
			     
			      preparedStmt.execute();;
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
		
	}
	
	
	
	public static void main(String s[])
	{
		try
		{
			String sql = "SELECT * FROM plant_vehicle_map";
			Statement stmt = DatabaseUtil.getConnection().createStatement();
			ResultSet rs = stmt.executeQuery(sql);	
			
			 while(rs.next())
			 {
				
				 String query = " insert into vehicle_transporter_map (transporter_id, vehicle_id, isActive, createdOn, updatedOn)"
					        + " values (?, ?, ?, ?, ?)";
					
							PreparedStatement preparedStmt = DatabaseUtil.getConnection().prepareStatement(query);
							
						
					      preparedStmt.setString (1, rs.getString("plant_id"));
					      preparedStmt.setString(2, rs.getString("vehicle_id"));
					      preparedStmt.setString(3, rs.getString("isActive"));
					      preparedStmt.setString(4, rs.getString("createdOn"));
					      preparedStmt.setString(5,rs.getString("updatedOn"));
					    
					      preparedStmt.execute();
				 
				 
			 }
		}
		catch(Exception e)
		{
			
		}
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
/*public static void main(String s[])
	{
		
		
		
		
//		DeviceMaster deviceMaster1 = new DeviceMaster();
//		deviceMaster1.setMobileNumber("AAAA");
//		deviceMaster1.setMobileCom("AAAA");
//		
//		deviceMaster1.setIsActive("AAAA");
//		deviceMaster1.setimeiNumber("AAAA");
//		deviceMaster1.setimeiNumber("AAAA");
//		
//		System.out.println(insertDeviceData(deviceMaster1));  
//		
//		
//		
//		
//		
//		Vehicle vehicle = new Vehicle();
//		vehicle.setVehicleNo("AAAA");
//		vehicle.setOwnerName("AAAA");
//		vehicle.setStatus("AAAA");
//		
//		insertVehicle(vehicle);
//		
//		
//		
//		VehicleStatus vehicleStatus = new VehicleStatus();
//		
//		
//		vehicleStatus.setImeiNumber("AAAAAAAAAAAAAAA");
//		vehicleStatus.setVehicleNumber("BBBBBBBB");
//		
//		insertVehicleStatus(vehicleStatus);
		
		int plantId =3;
		
		
	try
		{
		
				String FilePath = "D:\\Vishal\\ProjectBackup\\JKGPS\\db\\gpsdata.xls";
				FileInputStream fs = new FileInputStream(FilePath);
				Workbook wb = Workbook.getWorkbook(fs);
		
			Sheet sh = wb.getSheet("jharli_plant");
			int totalNoOfRows = sh.getRows();
			int totalNoOfCols = sh.getColumns();
			
			
			for (int row = 1; row < totalNoOfRows; row++) 
			{
				String vehicleNumber = null;
				DeviceMaster deviceMaster = new DeviceMaster();
			
				for (int col = 0; col < totalNoOfCols; col++) 
				{
					
					String ssss = sh.getCell(col, row).getContents();
					
					if(ssss != null && !ssss.equals(""))
					{
						//System.out.println(ssss);
						
						
						
						if(col == 1)
						{
							deviceMaster.setimeiNumber("0"+ssss);
							
							//System.out.println("imeiNumber   "+"0"+ssss);
						}
						else if(col == 2)
						{
							
						}
						else if(col == 3)
						{
							
						}
						else if(col == 4)
						{
							
							deviceMaster.setMobileNumber(ssss);
							//System.out.println("MobileNumber"+ssss);
						}
						else if(col == 5)
						{
							
						}
						else if(col == 6)
						{
							vehicleNumber = ssss;
							//deviceMaster.setimeiNumber(ssss);
							
						//	System.out.println("VehicleNumber"+ssss);
						}
						else if(col == 7)
						{
							
						}
						else if(col == 8)
						{
							
						}
						else if(col == 9)
						{
							
						}
						else if(col == 10)
						{
							
						}
						
					}
					else
					{
						//System.out.println("AAAAA");
					}
				
				}
				
				if(deviceMaster.getimeiNumber() != null && !deviceMaster.getimeiNumber().equals(""))
				{
					if(deviceMaster.getMobileNumber() != null && !deviceMaster.getMobileNumber().equals(""))
					{
						if(vehicleNumber != null && !vehicleNumber.equals(""))
						{
							DatabaseUtil.getConnection().setAutoCommit(false);
							
							
							java.util.Date dt = new java.util.Date();
							java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
							String currentTime = sdf.format(dt);
							
							
							Vehicle vehicle = new Vehicle();
							
							vehicle.setOwnerName("NA");
							vehicle.setVehical_capacity("NA");
							vehicle.setVehicleNo(vehicleNumber);
							vehicle.setIsActive("1");
							vehicle.setCreatedOn(currentTime);
							vehicle.setNo_of_wheels("NA");
							vehicle.setStatus("NA");
							int vehicleId = insertVehicle(vehicle);
							
							PlantVehicleMap  plantVehicleMap = new PlantVehicleMap();
							
							plantVehicleMap.setPlant_id(plantId);
							plantVehicleMap.setVehicle_id(vehicleId);
							plantVehicleMap.setCreatedOn(currentTime);
							plantVehicleMap.setUpdatedOn(currentTime);
							plantVehicleMap.setIsActive("1");
							
							insertPlantVehicleMap(plantVehicleMap);
							
							
							DeviceMaster device = new DeviceMaster();
							
							device.setimeiNumber(deviceMaster.getImeiNumber());
							device.setMobileNumber(deviceMaster.getMobileNumber());
							device.setMobileCom("NA");
							device.setCreatedOn(currentTime);
							device.setUpdatedOn(currentTime);
							device.setIsActive("1");
							device.setCompany_id(1);
							int deviceId =  insertDeviceData(device);
							
							
							DatabaseUtil.getConnection().commit();
							
							DeviceVehicleMap deviceVehicleMap = new DeviceVehicleMap();
							
							deviceVehicleMap.setVehicle_id(vehicleId);
							deviceVehicleMap.setDeviceMaster_id(deviceId);
							deviceVehicleMap.setCreatedOn(currentTime);
							deviceVehicleMap.setUpdatedOn(currentTime);
							deviceVehicleMap.setIsActive("1");
							insertDeviceVehicleMap(deviceVehicleMap);
							
							
							VehicleStatus vehicleStatus = new VehicleStatus();
							vehicleStatus.setTripId("NA");
							vehicleStatus.setCurrentlatitude("NA");
							vehicleStatus.setCurrentlongitude("NA");
							vehicleStatus.setCurrentLocation("NA");
							vehicleStatus.setVehicleNumber(vehicleNumber);
							vehicleStatus.setImeiNumber(deviceMaster.getImeiNumber());
							vehicleStatus.setLoadingStatus("NA");
							vehicleStatus.setDatetime(currentTime);
							vehicleStatus.setColorStatus("NA");
							vehicleStatus.setNearest_site("NA");
							vehicleStatus.setSpeed("NA");
							vehicleStatus.setOwner_name("NA");
							vehicleStatus.setTransporter_name("NA");
							vehicleStatus.setVehicle_capacity("NA");
							vehicleStatus.setNumber_of_wheels("NA");
							
							insertVehicleStatus(vehicleStatus);
							
							DatabaseUtil.getConnection().commit();
							
							System.out.println(deviceMaster.getImeiNumber());
							System.out.println(deviceMaster.getMobileNumber());
							System.out.println(vehicleNumber);
							
						}
					}
				}
				
				
				
				
				
				
				
			}
		}
		catch(Exception e)
		{
			
		}
	}*/
}
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
	
	
