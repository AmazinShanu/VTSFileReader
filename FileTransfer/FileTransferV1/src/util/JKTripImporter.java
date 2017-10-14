package util;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.log4j.Logger;

import com.sun.org.apache.xerces.internal.impl.xpath.regex.ParseException;;


public class JKTripImporter 
{
	static SimpleDateFormat dateFormat = new SimpleDateFormat("YYYYMMddhhmmss");
	static Date date = new Date(System.currentTimeMillis());
	public static int tripCounter=0;
	final static Logger logger = Logger.getLogger(JKTripImporter.class);
	public static String getTripCode()
	{
		tripCounter++;	
		return "T"+dateFormat.format(date)+String.format("%04d", new Random().nextInt(100));
	}
	
	
	public static String getTripCount()
	{
		String tripCount = null;
		try
		{
			String sql = "SELECT * FROM configuration_details where c_key='TripCount'";
			Statement stmt = DatabaseUtil.getConnection().createStatement();
			ResultSet rs = stmt.executeQuery(sql);	
		 
			while(rs.next())
			{
				tripCount = rs.getString("c_value");
			}
			
			rs.close();
			stmt.close();
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
		}
		
		return tripCount;
	}
	
	
	
	public static void updateTripCount(String count)
	{
		try
		{
			PreparedStatement ps = DatabaseUtil.getConnection().prepareStatement("UPDATE configuration_details SET c_value = ? WHERE c_key = ? ");
			ps.setString(1,count);
			ps.setString(2,"TripCount");
			ps.executeUpdate();
			ps.close();
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
		 
	}
	
	
	
	public static boolean isInvoiceExist(String invoiceNumber)
	{
		boolean isInvoiceExist = false;
		try
		{
			String sql = "SELECT * FROM delivery_order where invoiceNo='"+invoiceNumber+"'";
			Statement stmt = DatabaseUtil.getConnection().createStatement();
			ResultSet rs = stmt.executeQuery(sql);	
		 
			while(rs.next())
			{
				isInvoiceExist = true;
			}
			
			rs.close();
			stmt.close();
		}
		catch(Exception e)
		{
			
		}
		return isInvoiceExist;
	}
	
	//Inserting logs into the system about how the application is progressing	
	public static void insertProcessData(String data)
	{
		try
		{
			java.util.Date dt = new java.util.Date();
			java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String currentTime = sdf.format(dt);
						
			String deviceDataQquery = " insert into process_data (data,date)" + " values (?,?)";
			
			PreparedStatement deviceDataPreparedStmt = DatabaseUtil.getConnection().prepareStatement(deviceDataQquery,Statement.RETURN_GENERATED_KEYS);
			deviceDataPreparedStmt.setString(1, data);
			deviceDataPreparedStmt.setString(2, currentTime);
						
			int id =  deviceDataPreparedStmt.executeUpdate();
			if(id>0) 
			{
				ResultSet rs2 = deviceDataPreparedStmt.getGeneratedKeys();
				rs2.next();
				id = rs2.getInt(1);
				rs2.close();
			}
			
			deviceDataPreparedStmt.close();
		}
		catch(Exception e)
		{
			System.out.println( e);
		}		
	}
	
	
	/*public static void insertDeviceData(String data)
	{
		try
		{
			java.util.Date dt = new java.util.Date();
			java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String currentTime = sdf.format(dt);
			
			
			String deviceDataQquery = " insert into devicedata (data,date)"
			        + " values (?,?)";
			
			PreparedStatement deviceDataPreparedStmt = DatabaseUtil.getConnection().prepareStatement(deviceDataQquery,Statement.RETURN_GENERATED_KEYS);
				
			deviceDataPreparedStmt.setString(1, data);
			deviceDataPreparedStmt.setString(2, currentTime);
			
			
			int id =  deviceDataPreparedStmt.executeUpdate();
			if(id>0) 
			{
				ResultSet rs2 = deviceDataPreparedStmt.getGeneratedKeys();
				rs2.next();
				id = rs2.getInt(1);
				rs2.close();
			}
			
			deviceDataPreparedStmt.close();
		}
		catch(Exception e)
		{
			System.out.println( e);
		}
		
		
	}*/
	
	
	
	public static PlantDetails getPlantType(String plantCode)
	{
		PlantDetails plantDetails = new PlantDetails();
		plantDetails.setPlantCode(plantCode);
		try
		{
			
			String sql = "SELECT * FROM plant_master where plant_code='"+plantCode+"'";
			Statement stmt = DatabaseUtil.getConnection().createStatement();
			ResultSet rs = stmt.executeQuery(sql);	
		 
			while(rs.next())
			{
				plantDetails.setPlantType(rs.getString("type"));
				//plantDetails.setPlantType(rs.getString("plant_code"));
				plantDetails.setPlantName(rs.getString("plant_name"));
			}
			
			rs.close();
			stmt.close();
		}
		catch(Exception e)
		{
			System.out.println( e.getMessage());
		}
			
		
		return plantDetails;
	}
	
	
	public static boolean isDeviceRegistered(String vehicleNumber)
	{
		boolean isRegistered = false;
		try
		{
			String sql = "SELECT * FROM vehiclestatus where vehicleNumber='"+vehicleNumber+"'";
			Statement stmt = DatabaseUtil.getConnection().createStatement();
			ResultSet rs = stmt.executeQuery(sql);	
		 
			while(rs.next())
			{
				isRegistered = true;
			}
			
			rs.close();
			stmt.close();
		}
		catch(Exception e)
		{
			
		}
		
		
		return isRegistered;
	}
	
	
	
	
	public static void updateVehicleStatus(String tripId,String vehicleNumber,String transporter_name)
	{
		try
		{
			PreparedStatement ps = DatabaseUtil.getConnection().prepareStatement("UPDATE vehiclestatus SET tripId = ?, loadingStatus = ?,transporter_name =? WHERE vehicleNumber = ? ");
			ps.setString(1,tripId);
			ps.setString(2,"L");
			ps.setString(3,transporter_name);
			ps.setString(4,vehicleNumber);
			ps.executeUpdate();
			ps.close();
			//DatabaseUtil.getConnection().close();
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
	}
	
	
	
	
	public static int insertTripData(TripData tripData,Map<String,String> vehMap)
	{		
		int tripId=0;
		int deliveryOrder=0;
		String newInvoiceDateString="";
		try
		{
			java.util.Date dt = new java.util.Date();
			java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			//java.text.SimpleDateFormat sdfInvoiceDate = new java.text.SimpleDateFormat("dd.MM.yyyy");
			String currentTime = sdf.format(dt);
			//newInvoiceDateString=tripData.getInvoiceDate().replace('.', '/');
			try{
				newInvoiceDateString=stringTodate(tripData.getInvoiceDate(), "dd.MM.yyyy", "yyyy-MM-dd HH:mm:ss");
				//newInvoiceDateString= sdf.format(startDate);
				System.out.println("Data date:"+newInvoiceDateString);
			}
			catch(Exception pe){
				newInvoiceDateString=tripData.getInvoiceDate();
				System.out.println("Invoice date parse exception:"+pe.getMessage()+newInvoiceDateString);
			}
			String tripQquery = " insert into trip (trip_code, vehicle_number, transporter_code, transporter_name,driver_licenceNo, route_code,trip_type_code,status,isActive,createdOn,updatedOn,tripStartedOn)"
			        + " values (?,?,?,?,?,?,?,?,?,?,?,?)";
			
			
			String grtTripQuery="Select * from trip where vehicle_number=? and (status='A' or status='ST' or status='DE')";
			PreparedStatement pst=DatabaseUtil.getConnection().prepareStatement(grtTripQuery);
			pst.setString(1, tripData.getVehicleNumber());
			ResultSet rst=pst.executeQuery();
			boolean isExist=false;
			
			//Date from Excel
			/*java.util.Date GPSRecordDate=new java.util.Date();			
			java.text.SimpleDateFormat sdfFromExcel = new java.text.SimpleDateFormat("dd.MM.yyyy");
			GPSRecordDate = sdfFromExcel.parse(tripData.getInvoiceDate());
*/
			
			//Date from mySQL
			//java.util.Date currentRecordCreatedDate=new java.util.Date();

			
			while(rst.next())
			{
				isExist=true;	
				tripId=rst.getInt("id");
				//currentRecordCreatedDate=sdf.parse(rst.getString("createdOn"));
				
			}
			
			if(vehMap.get(tripData.getVehicleNumber()).equals("NA")){
				if(isExist){
					PreparedStatement pst1=DatabaseUtil.getConnection().prepareStatement("UPDATE trip set status=?,REMARKS=? where vehicle_number=? and status<>'SP'");
					pst1.setString(1, "SP");
					pst1.setString(2, "Trip Closed Forcibly");
					pst1.setString(3, tripData.getVehicleNumber());
					pst1.executeUpdate();
					pst1.close();
					PreparedStatement tripPreparedStmt = DatabaseUtil.getConnection().prepareStatement(tripQquery,Statement.RETURN_GENERATED_KEYS);
					tripPreparedStmt.setString(1, getTripCode());
					tripPreparedStmt.setString(2, tripData.getVehicleNumber());
					tripPreparedStmt.setString(3, tripData.getTransporterCode());
					tripPreparedStmt.setString(4, tripData.getTransporterName());
					tripPreparedStmt.setString(5, tripData.getDriverLicenseNumber());
					tripPreparedStmt.setString(6, tripData.getRouteCode());
					tripPreparedStmt.setString(7, tripData.getShipmentType());
					tripPreparedStmt.setString(8, "ST");
					tripPreparedStmt.setString(9, "1");
					tripPreparedStmt.setString(10, currentTime);
					tripPreparedStmt.setString(11, currentTime);
					tripPreparedStmt.setString(12, currentTime);
					tripId =  tripPreparedStmt.executeUpdate();	
					
					if(tripId>0) 
					{
						ResultSet tripRS = tripPreparedStmt.getGeneratedKeys();
						tripRS.next();
						tripId = tripRS.getInt(1);
						
						
					}
					tripPreparedStmt.close();
					
					
					
				}
				else{
					PreparedStatement tripPreparedStmt = DatabaseUtil.getConnection().prepareStatement(tripQquery,Statement.RETURN_GENERATED_KEYS);
					tripPreparedStmt.setString(1, getTripCode());
					tripPreparedStmt.setString(2, tripData.getVehicleNumber());
					tripPreparedStmt.setString(3, tripData.getTransporterCode());
					tripPreparedStmt.setString(4, tripData.getTransporterName());
					tripPreparedStmt.setString(5, tripData.getDriverLicenseNumber());
					tripPreparedStmt.setString(6, tripData.getRouteCode());
					tripPreparedStmt.setString(7, tripData.getShipmentType());
					tripPreparedStmt.setString(8, "ST");
					tripPreparedStmt.setString(9, "1");
					tripPreparedStmt.setString(10, currentTime);
					tripPreparedStmt.setString(11, currentTime);
					tripPreparedStmt.setString(12, currentTime);
					tripId =  tripPreparedStmt.executeUpdate();
					
					if(tripId>0) 
					{
						ResultSet tripRS = tripPreparedStmt.getGeneratedKeys();
						tripRS.next();
						tripId = tripRS.getInt(1);
						
						
					}
					tripPreparedStmt.close();
					
				}
			}
			else{
				tripId=Integer.parseInt(vehMap.get(tripData.getVehicleNumber()));	
			}
			System.out.println("Trip Id:"+tripId);
			
			
			
			
			String deliveryOrderQquery = " insert into delivery_order (do_number, total_qty, from_code,to_code,sold_to_party_code, ship_to_party_code, invoiceNo,invoiceDate,destinationDistrictDiscription,deliveryDestinationAsPerSAP,salesDistrictCityCode,district,state,distanceAsPerSAP,mrp,trip_id,isActive,createdOn,updatedOn,status,"
					+ "invoiceItemNo,shippingCondition,SAPOrderDate,SAPOrderNo,deliveryCreationDate,targetMaterialDeliveryDate,targetMaterialDeliveryTime,productName,plant,destination,customerCode,customerName,customerSegment,SAPkms,PGIDate,PGITime,stateDesc,cityCode,cityDesc,InvoiceDateNew)"
			        + " values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
			
			PreparedStatement deliveryOrderPreparedStmt = DatabaseUtil.getConnection().prepareStatement(deliveryOrderQquery,Statement.RETURN_GENERATED_KEYS);
				
			deliveryOrderPreparedStmt.setString(1, tripData.getDiNumber());
			deliveryOrderPreparedStmt.setString(2, tripData.getQty());
			deliveryOrderPreparedStmt.setString(3, tripData.getFromCode());
			deliveryOrderPreparedStmt.setString(4, tripData.getToCode());
			deliveryOrderPreparedStmt.setString(5, "NA");
			deliveryOrderPreparedStmt.setString(6, "NA");
			deliveryOrderPreparedStmt.setString(7, tripData.getInvoiceNo());
			deliveryOrderPreparedStmt.setString(8, tripData.getInvoiceDate());
			deliveryOrderPreparedStmt.setString(9, tripData.getDestinationDistrictDiscription());
			deliveryOrderPreparedStmt.setString(10, tripData.getDeliveryDestinationAsPerSAP());
			deliveryOrderPreparedStmt.setString(11, tripData.getSalesDistrictCityCode());
			deliveryOrderPreparedStmt.setString(12, tripData.getDistrict());
			deliveryOrderPreparedStmt.setString(13, tripData.getState());
			deliveryOrderPreparedStmt.setString(14, tripData.getDistanceAsPerSAP());
			deliveryOrderPreparedStmt.setString(15, tripData.getMrp());
			deliveryOrderPreparedStmt.setLong(16, tripId);
			deliveryOrderPreparedStmt.setString(17, "1");
			deliveryOrderPreparedStmt.setString(18, currentTime);
			deliveryOrderPreparedStmt.setString(19, currentTime);
			deliveryOrderPreparedStmt.setString(20, "A");
			
			
			deliveryOrderPreparedStmt.setString(21, tripData.getInvoiceItemNo());
			deliveryOrderPreparedStmt.setString(22, tripData.getShippingCondition());
			deliveryOrderPreparedStmt.setString(23, tripData.getSAPOrderDate());
			deliveryOrderPreparedStmt.setString(24, tripData.getSAPOrderNo());
			deliveryOrderPreparedStmt.setString(25, tripData.getDeliveryCreationDate());
			deliveryOrderPreparedStmt.setString(26, tripData.getTargetMaterialDeliveryDate());
			deliveryOrderPreparedStmt.setString(27, tripData.getTargetMaterialDeliveryTime());
			deliveryOrderPreparedStmt.setString(28, tripData.getProductName());
			deliveryOrderPreparedStmt.setString(29, tripData.getPlant());
			deliveryOrderPreparedStmt.setString(30, tripData.getDestination());
			deliveryOrderPreparedStmt.setString(31, tripData.getCustomerCode());
			deliveryOrderPreparedStmt.setString(32, tripData.getCustomerName());
			deliveryOrderPreparedStmt.setString(33, tripData.getCustomerSegment());
			deliveryOrderPreparedStmt.setString(34, tripData.getSAPkms());
			deliveryOrderPreparedStmt.setString(35, tripData.getPGIDate());
			deliveryOrderPreparedStmt.setString(36, tripData.getPGITime());
			
			deliveryOrderPreparedStmt.setString(37, tripData.getStateDesc());
			deliveryOrderPreparedStmt.setString(38, tripData.getCityCode());
			deliveryOrderPreparedStmt.setString(39, tripData.getCityDesc());
			deliveryOrderPreparedStmt.setString(40, newInvoiceDateString);
			
			deliveryOrder =  deliveryOrderPreparedStmt.executeUpdate();
			if(deliveryOrder>0) 
			{
				ResultSet deliveryOrderRS = deliveryOrderPreparedStmt.getGeneratedKeys();
				deliveryOrderRS.next();
				deliveryOrder = deliveryOrderRS.getInt(1);
			}
			
			
			updateVehicleStatus(String.valueOf(tripId), tripData.getVehicleNumber(),tripData.getTransporterName());
			
			
			
		}
		catch(Exception e)
		{
			System.out.println("Exception:"+e.getMessage());
		}
		return tripId;
	}
	
	
	public  TotalKm calDisAndTimeForTrip(String tripId)
	{
		TotalKm totalKm = new TotalKm();
		
		try
		{
			
			ArrayList<DeviceNavigationDetails>  deviceNavigationDetailsList = new ArrayList<DeviceNavigationDetails>();
			
			float total_km_run = 0;
			float empty_run = 0;
			float loaded_run = 0;
			float cargo_loaded_run = 0;
			
			String sql = "SELECT * FROM device_navigation_details where trip_id='"+tripId+"'";
			Statement stmt = DatabaseUtil.getConnection().createStatement();
			ResultSet rs = stmt.executeQuery(sql);	
		 
			while(rs.next())
			{
				DeviceNavigationDetails deviceNavigationDetails = new DeviceNavigationDetails();
				
				deviceNavigationDetails.setId(rs.getString("id"));
				deviceNavigationDetails.setLatitude(rs.getString("latitude"));
				deviceNavigationDetails.setLongitude(rs.getString("longitude"));
				deviceNavigationDetails.setUpdatedOn(rs.getString("updatedOn"));
				deviceNavigationDetails.setDeviceId(rs.getString("deviceId"));
				deviceNavigationDetails.setStatus(rs.getString("status"));
				deviceNavigationDetails.setSpeed(rs.getString("speed"));
				deviceNavigationDetails.setTrip_id(rs.getString("trip_id"));
				deviceNavigationDetails.setTripStatus(rs.getString("tripStatus"));
				
				deviceNavigationDetailsList.add(deviceNavigationDetails);
			}
			
			for(int i=1; i< deviceNavigationDetailsList.size(); i++)
			{
				float distance  = AmazinLocation.distFrom(Double.valueOf(deviceNavigationDetailsList.get(i-1).getLatitude()),Double.valueOf(deviceNavigationDetailsList.get(i-1).getLongitude()),Double.valueOf(deviceNavigationDetailsList.get(i).getLatitude()),Double.valueOf(deviceNavigationDetailsList.get(i).getLongitude()));
				
				if(deviceNavigationDetailsList.get(i).getTripStatus() == null)
				{
					deviceNavigationDetailsList.get(i).setTripStatus("ST");
				}
				
				if(deviceNavigationDetailsList.get(i).getTripStatus().trim().equals("ST"))
				{
					total_km_run = total_km_run +distance;
					loaded_run = loaded_run + distance;
				}
				else if(deviceNavigationDetailsList.get(i).getTripStatus().trim().equals("DE"))
				{
					total_km_run = total_km_run +distance;
					empty_run = empty_run + distance;
				}
				else if(deviceNavigationDetailsList.get(i).getTripStatus().trim().equals("SP"))
				{
					total_km_run = total_km_run +distance;
					empty_run = empty_run + distance;
				}
				else 
				{
					total_km_run = total_km_run +distance;
					empty_run = empty_run + distance;
				}
			}
			
			totalKm.setTotal_km_run(total_km_run);
			totalKm.setEmpty_run(empty_run);
			totalKm.setLoaded_run(loaded_run);
			totalKm.setCargo_loaded_run(cargo_loaded_run);
			
			
			
			PreparedStatement ps = DatabaseUtil.getConnection().prepareStatement("UPDATE trip SET total_km_run = ?, empty_run = ?,loaded_run = ?,cargo_loaded_run = ? WHERE id = ? ");
			ps.setString(1,String.valueOf(total_km_run));
			ps.setString(2,String.valueOf(empty_run));
			ps.setString(3,String.valueOf(loaded_run));
			ps.setString(4,String.valueOf(cargo_loaded_run));
			ps.setString(5,tripId);
			ps.executeUpdate();
			ps.close();
			
			
			rs.close();
			stmt.close();
			
		}
		catch(Exception e)
		{
			System.out.println( e.getMessage());
		}
		
		return totalKm;
	}
	
	public static void updateVehicleStatusForTotalTravel(TotalKm totalKm,String vehicleNumber)
	{
		try
		{
			String total_km_run="0",empty_run="0",loaded_run="0",cargo_loaded_run="0";
			
			String sql = "SELECT * FROM vehiclestatus where vehicleNumber='"+vehicleNumber+"'";
			Statement stmt = DatabaseUtil.getConnection().createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			
			while(rs.next())
			{
				total_km_run = rs.getString("total_km_run");
				empty_run = rs.getString("empty_run");
				loaded_run = rs.getString("loaded_run");
				cargo_loaded_run = rs.getString("cargo_loaded_run");
			}
			
			rs.close();
			stmt.close();
			
			if(total_km_run == null || total_km_run.equals(""))
			{
				total_km_run="0";
			}
			if(empty_run == null || empty_run.equals(""))
			{
				empty_run="0";
			}
			if(loaded_run == null || loaded_run.equals(""))
			{
				loaded_run="0";
			}
			if(cargo_loaded_run == null || cargo_loaded_run.equals(""))
			{
				cargo_loaded_run="0";
			}
			
			float total_km_run_float= Float.parseFloat(total_km_run) + totalKm.getTotal_km_run();
			float empty_run_float= Float.parseFloat(empty_run) + totalKm.getEmpty_run();
			float loaded_run_float= Float.parseFloat(loaded_run) + totalKm.getLoaded_run();
			float cargo_loaded_run_float= Float.parseFloat(cargo_loaded_run) + totalKm.getCargo_loaded_run();
			
			
			PreparedStatement ps = DatabaseUtil.getConnection().prepareStatement("UPDATE vehiclestatus SET total_km_run = ?, empty_run = ?, loaded_run = ?, cargo_loaded_run = ? WHERE vehicleNumber = ? ");
			ps.setString(1,String.valueOf(total_km_run_float));
			ps.setString(2,String.valueOf(empty_run_float));
			ps.setString(3,String.valueOf(loaded_run_float));
			ps.setString(4,String.valueOf(cargo_loaded_run_float));
			ps.setString(5,vehicleNumber);
			//ps.executeUpdate();
			ps.close();
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
	}
	
	public static String getTripIdOfVehicleStatus(String vehicleNumber)
	{
		String tripId="NA";
		try
		{
			String sql = "SELECT * FROM vehiclestatus where vehicleNumber='"+vehicleNumber+"'";
			Statement stmt = DatabaseUtil.getConnection().createStatement();
			ResultSet rs = stmt.executeQuery(sql);	
		 
			while(rs.next())
			{
				tripId = rs.getString("tripId");
			}
			
			rs.close();
			stmt.close();
		}
		catch(Exception e)
		{
			
		}
		
		
		
		return tripId;
	}
	
	
	public static void updateTripStatus(String tripId, String status)
	{
		try
		{
			PreparedStatement ps = DatabaseUtil.getConnection().prepareStatement("UPDATE trip SET status = ? WHERE id = ? ");
			ps.setString(1,status);
			ps.setString(2,tripId);
			ps.executeUpdate();
			ps.close();
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
	}
	
	
	
	
	
	
	public static void readJKTrip()
	{
		try
		{
			 insertProcessData("Reader Start");
			 tripCounter =  Integer.parseInt(getTripCount());
			 File file = new File("X:\\GPS_DATA__13-10_2017.csv");
			 List<String> lines = Files.readAllLines(file.toPath(), StandardCharsets.UTF_8);//
			 System.out.println("Call.....");
			 Map<String,String> vehicleMap=new HashMap<String,String>();
		    for (int x=1; x < lines.size() ; x++) 
		    {
		    	//Single Line of File
		    	String line = lines.get(x);
	    		    	
		    	TripData tripData = new TripData();
		    	
		    	int columnIndex=0;
		    	String w="";
		    	//System.out.println(line.length());
		    	char c[] = line.toCharArray();
		    	System.out.println("Row No:"+x+"");
		    	 for(int i=0; i<c.length; i++)
			     {
		    		  char ch = c[i];
		    		 
		    		  if(ch == ';')
		    		 	{		    			  
		    			  columnIndex++;
		    		 		
		    		 		if(columnIndex  == 1)
		    		 		{
		    		 			//Invoice No
		    		 			tripData.setInvoiceNo(w);
		    		 		}
		    		 		else if(columnIndex  == 2)
		    		 		{
		    		 			//Invoice Item No
		    		 			tripData.setInvoiceItemNo(w);
		    		 			
		    		 		}
		    		 		else if(columnIndex  == 3)
		    		 		{
		    		 			//Invoice Date
		    		 			tripData.setInvoiceDate(w);
		    		 			
		    		 		}
		    		 		else if(columnIndex  == 4)
		    		 		{
		    		 			//Vehicle No
		    		 			tripData.setVehicleNumber(w);
		    		 			
		    		 			if(vehicleMap.get(w)==null){
		    		 			System.out.println("veh-no:"+w);
		    		 			vehicleMap.put(w, "NA");
		    		 			}
		    		 			else{
		    		 				System.out.println("Double DI veh-no:"+w);
		    		 			}
		    		 		}
		    		 		else if(columnIndex  == 5)
		    		 		{
		    		 			//Shipping Conditio
		    		 			tripData.setShippingCondition(w);
		    		 		}
		    		 		else if(columnIndex  == 6)
		    		 		{
		    		 			//Qty Loaded (in TO)
		    		 			tripData.setQty(w);
		    		 		}
		    		 		else if(columnIndex  == 7)
		    		 		{
		    		 			//SAP Order Date or Credit Block Rel Date
		    		 			tripData.setSAPOrderDate(w);
		    		 		}
		    		 		else if(columnIndex  == 8)
		    		 		{
		    		 			//SAP order no.
		    		 			tripData.setSAPOrderNo(w);
		    		 		}
		    		 		else if(columnIndex  == 9)
		    		 		{
		    		 			//Delivery Creation Date
		    		 			tripData.setDeliveryCreationDate(w);
		    		 		}
		    		 		else if(columnIndex  == 10)
		    		 		{
		    		 			//Target Material Delivery Date
		    		 			tripData.setTargetMaterialDeliveryDate(w);
		    		 		}
		    		 		else if(columnIndex  == 11)
		    		 		{
		    		 			//Target Material Delivery Time
		    		 			tripData.setTargetMaterialDeliveryTime(w);
		    		 		}
		    		 		else if(columnIndex  == 12)
		    		 		{
		    		 			//Product Name
		    		 			tripData.setProductName(w);
		    		 		}
		    		 		else if(columnIndex  == 13)
		    		 		{
		    		 			//Plant
		    		 			tripData.setPlant(w);
		    		 			
		    		 		}
		    		 		else if(columnIndex  == 14)
		    		 		{
		    		 			//Destination
		    		 			tripData.setDestination(w);
		    		 		}
		    		 		else if(columnIndex  == 15)
		    		 		{
		    		 			//Customer Code
		    		 			tripData.setCustomerCode(w);
		    		 		}
		    		 		else if(columnIndex  == 16)
		    		 		{
		    		 			//Customer Name
		    		 			tripData.setCustomerName(w);
		    		 			
		    		 		}
		    		 		else if(columnIndex  == 17)
		    		 		{
		    		 			//Customer Segment
		    		 			tripData.setCustomerSegment(w);
		    		 		}
		    		 		else if(columnIndex  == 18)
		    		 		{
		    		 			//SAP kms
		    		 			tripData.setSAPkms(w);
		    		 		}
		    		 		else if(columnIndex  == 19)
		    		 		{
		    		 			//PGI Date
		    		 			tripData.setPGIDate(w);
		    		 		}
		    		 		else if(columnIndex  == 20)
		    		 		{
		    		 			//PGI Time
		    		 			tripData.setPGITime(w);
		    		 		}
		    		 		else if(columnIndex  == 21)
		    		 		{
		    		 			//DI Number
		    		 			tripData.setDiNumber(w);
		    		 		}
		    		 		else if(columnIndex  == 22)
		    		 		{
		    		 			//Transporter Code
		    		 			tripData.setTransporterCode(w);
		    		 		}
		    		 		else if(columnIndex  == 23)
		    		 		{
		    		 			//Transporter Name
		    		 			tripData.setTransporterName(w);
		    		 		}
		    		 		else if(columnIndex  == 24)
		    		 		{
		    		 			//District
		    		 			tripData.setDistrict(w);
		    		 		}
		    		 		else if(columnIndex  == 25)
		    		 		{
		    		 			//State
		    		 			tripData.setState(w);
		    		 		}
		    		 		else if(columnIndex  == 26)
		    		 		{
		    		 			//State Desc.
		    		 			tripData.setStateDesc(w);
		    		 		}
		    		 		else if(columnIndex  == 27)
		    		 		{
		    		 			//City Code
		    		 			tripData.setCityCode(w);
		    		 		}
		    		 		else if(columnIndex  == 28)
		    		 		{
		    		 			//City Desc
		    		 			tripData.setCityDesc(w);
		    		 		}
		    		 		
		    		 		w="";
		    		 	}
		    		  else
		    		  {
		    			  w = w + new String(""+ch);
		    		  }
		    	 }
		    	 tripData.setCityDesc(w);
		    	 
		    	 
		    	PlantDetails from_plantDetails = getPlantType(tripData.getPlant());
		    	 
		    	 	if(tripData.getCustomerCode().startsWith("000000"))
 		 			{
		    	 		PlantDetails to_plant_details = getPlantType(tripData.getCustomerCode().substring(6, 10));
		    	 		if(from_plantDetails.getPlantType() != null && to_plant_details.getPlantType() != null )
		    	 		{
		    	 			if( from_plantDetails.getPlantType().equals("P") && to_plant_details.getPlantType().equals("P"))
			    	 		{
			    	 			System.out.println("Trip Type :"+"PTP");
			    	 			tripData.setShipmentType("PTP");
			    	 			
			    	 		}
			    	 		else if(from_plantDetails.getPlantType().equals("P") && to_plant_details.getPlantType().equals("D"))
			    	 		{
			    	 			System.out.println("Trip Type :"+"PTD");
			    	 			tripData.setShipmentType("PTD");
			    	 		}
			    	 		else if(from_plantDetails.getPlantType().equals("D") && to_plant_details.getPlantType().equals("P"))
			    	 		{
			    	 			System.out.println("Trip Type :"+"DTP");
			    	 			tripData.setShipmentType("DTP");
			    	 		}
			    	 		else if (from_plantDetails.getPlantType().equals("D") && to_plant_details.getPlantType().equals("D"))
			    	 		{
			    	 			System.out.println("Trip Type :"+"DTD");
			    	 			tripData.setShipmentType("DTD");
			    	 		}
			    	 		else
			    	 		{
			    	 			System.out.println("CustomerCode --- from Type---"+to_plant_details.getPlantCode());
			    	 			tripData.setShipmentType("FROM"+from_plantDetails.getPlantCode()+"TO"+to_plant_details.getPlantCode());
			    	 		}
		    	 		}
		    	 		else
		    	 		{
		    	 			System.out.println("FROM"+from_plantDetails.getPlantCode()+"TO"+to_plant_details.getPlantCode());
		    	 			tripData.setShipmentType("FROM"+from_plantDetails.getPlantCode()+"TO"+to_plant_details.getPlantCode());
		    	 		}
		    	 		
 		 			}
		    	 	else
		    	 	{
		    	 		if(from_plantDetails.getPlantType() != null && from_plantDetails.getPlantType().equals("P"))
		    	 		{
		    	 			System.out.println("PTC");
		    	 			tripData.setShipmentType("PTC");
		    	 		}
		    	 		else if(from_plantDetails.getPlantType() != null && from_plantDetails.getPlantType().equals("D"))
		    	 		{
		    	 			System.out.println("DTC");
		    	 			tripData.setShipmentType("DTC");
		    	 		}
		    	 		else
		    	 		{
		    	 			System.out.println("Else -- from Type "+from_plantDetails.getPlantCode());
		    	 			tripData.setShipmentType("FROM"+from_plantDetails.getPlantCode()+"TO"+tripData.getCustomerCode());
		    	 		}
		    	 	}
		    	 	tripData.setFromCode(tripData.getPlant());
		    	 	tripData.setToCode(tripData.getCustomerCode());
		    	 	
		    	 		//tripDataList.add(tripData);
		    	 	
		    	 	if(!isInvoiceExist(tripData.getInvoiceNo()))
		    	 	{
		    	 		if(isDeviceRegistered(tripData.getVehicleNumber()))
		    	 		{
		    	 			String tripId = getTripIdOfVehicleStatus(tripData.getVehicleNumber());
		    	 			if(tripId == null || tripId.equals("") || tripId.equals("NA") )
		    	 			{
		    	 				
		    	 				if(tripData.getDiNumber()==null || tripData.getDiNumber().equals(""))
		    	 				{
		    	 					System.out.println("Empty Data:"+tripId);
		    	 				}
		    	 				else{	
		    	 					System.out.println("Try to insert data:"+tripId);
		    	 				 int result=insertTripData(tripData,vehicleMap);
		    	 				 vehicleMap.put(tripData.getVehicleNumber(), Integer.toString(result));
		    	 				
		    	 				}
		    	 				insertProcessData("Not Exist");
				    	 		
		    	 			}
		    	 			else
		    	 			{
		    	 				System.out.println("Else:"+tripId+tripData.getVehicleNumber());
		    	 				TotalKm totalKm = new JKTripImporter().calDisAndTimeForTrip(tripId);
		    	 				updateVehicleStatusForTotalTravel(totalKm,tripData.getVehicleNumber());
		    	 				//updateTripStatus(tripId,"SP");
		    	 				if(tripData.getDiNumber()==null || tripData.getDiNumber().equals(""))
		    	 				{
		    	 					System.out.println("Empty Data:"+tripId);	
		    	 				}else{
		    	 				
		    	 					 int result=insertTripData(tripData,vehicleMap);
			    	 				 vehicleMap.put(tripData.getVehicleNumber(), Integer.toString(result));
				    	 		
		    	 				}
		    	 				insertProcessData("Not Exist but already trip assign");	
		    	 			}
		    	 			
		    	 			
		    	 		}
		    	 		else
		    	 		{
		    	 			
		    	 			insertProcessData("Vehicle Number Not Exist");
		    	 			System.out.println("Vehicle Number Not Exist");
	    	 				
		    	 		}
		    	 		 
		    	 	}
		    	 	else
		    	 	{
		    	 		
		    	 		insertProcessData("Exist");
		    	 		System.out.println("Vehicle Number Not Exist");
    	 				
		    	 	}
		    	 	
			    	 
			    	
			    	// System.out.println(tripData.customerName);
			    	 
			    	
		    }
		    
		    
		    updateTripCount(String.valueOf(tripCounter));
			System.out.println("Done......");
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
			insertProcessData(e.toString());
			
			 updateTripCount(String.valueOf(tripCounter));
		}
	}
	public static String stringTodate(String date, String formatter, String format) throws Exception
	{
		System.out.println(  "String   "+   date+ " formatter  "+ formatter+" format   "+ format);
		SimpleDateFormat desiredFormat = new SimpleDateFormat(format);
		SimpleDateFormat dateFormatter = new SimpleDateFormat(formatter); 

		java.util.Date newdate = null;
		String newDateString = null;
	    try {
	        newdate = dateFormatter.parse(date);
	        newDateString = desiredFormat.format(newdate);
	        System.out.println(newDateString);
	    } catch (ParseException e) {
	        e.printStackTrace();
	        throw e;
	    }
		System.out.println("newDateString : "+newDateString);
		return newDateString;
		
	}
	public static void main(String s[])
	{
		logger.debug("Start thred...");

		System.out.println("Shantnu");

		/*Map<String,String> valmap=new HashMap<String,String>();
		if(valmap.get("Sumit")==null){
			System.out.println("Null val");
		}
		System.out.println("VVVV"+valmap.get("Sumit"));
		valmap.put("Sumit", "");
		valmap.put("Amit", "");
		System.out.println("Val:"+valmap.get("Sumit")+valmap.get("Amit"));
		valmap.put("Sumit", "babab");
		valmap.put("Amit", "ram");
		System.out.println("Val:"+valmap.get("Sumit")+valmap.get("Amit"));*/
		
		///////////readJKTrip();
		//System.out.println(getTripCode());
		
		try
		{
			/*java.text.SimpleDateFormat sdfInvoiceDate = new java.text.SimpleDateFormat("dd.MM.yyyy");
			String currentTime = sdfInvoiceDate.format("12.10.2017");*/
			//System.out.println("Date;"+stringTodate("12.10.2017", "dd.MM.yyyy", "yyyy-MM-dd HH:mm:ss"));
		}
		catch(Exception e)
		{
			System.out.println("Exp:"+e.getMessage());
		}
		logger.debug("End thred...");
		
		
		
	
//		System.out.println(totalKm.getTotal_km_run());
//		System.out.println(totalKm.getLoaded_run());
//		System.out.println(totalKm.getEmpty_run());
//		System.out.println(totalKm.getCargo_loaded_run());
		
		
		/*updateTripCount("60000");
		System.out.println(getTripCount());*/
		
		//readData();
	}
	
	
	
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
	                
	                insertProcessData(firstLine);
	            } 
	            finally 
	            {
	                if (reader != null) try { reader.close(); } catch (IOException logOrIgnore) {}
	                
	                insertProcessData("Close");
	            }
	            
	            
	        }
	        catch(Exception e)
	        {
	        	insertProcessData(e.getMessage());
	        }
			
	}
	
	
}
