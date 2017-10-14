package util;


import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriBuilder;

import com.google.gson.Gson;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;

import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

public class ReadExcelFile 
{
	
	public static void readExcel() //throws BiffException, IOException 
	{
		try
		{
			String FilePath = "D:\\sapdata.xls";
			FileInputStream fs = new FileInputStream(FilePath);
			Workbook wb = Workbook.getWorkbook(fs);
			
			
			try
			{
				Sheet sh = wb.getSheet("Sheet1");
				int totalNoOfRows = sh.getRows();
				int totalNoOfCols = sh.getColumns();
				Gson gson = new Gson();
				ClientConfig config = new DefaultClientConfig();
				Client client = Client.create(config);
				//WebResource webResource = client.resource(UriBuilder.fromUri("http://localhost:8086/DCBLWEB/amazin/DCBL/RFC/savePlantMovement/").build());
			
				WebResource webResource = client.resource(UriBuilder.fromUri("http://amazinvts.com/DCBLWEBV1/amazin/DCBL/RFC/savePlantMovement/").build());
				List<DataModal> modalList=new ArrayList<DataModal>();
			
				for (int row = 1; row < totalNoOfRows; row++) 
				{
					DataModal modal=new DataModal();
				
					for (int col = 0; col < totalNoOfCols; col++) 
					{
						//System.out.print(sh.getCell(col, row).getContents() + "\t");
						if(col==0)
						{
							modal.setTruckNo(sh.getCell(col, row).getContents());	
						}
						else if(col==1)
						{
							modal.setBillDoc(sh.getCell(col, row).getContents());	
						}
						else if(col==2)
						{
							modal.setRoutDesc(sh.getCell(col, row).getContents());	
						}
						else if(col==3)
						{
							modal.setTransporter(sh.getCell(col, row).getContents());	
						}
						else if(col==4)
						{
							modal.setShipToPartyCode(sh.getCell(col, row).getContents());
						}
						else if(col==5)
						{
							modal.setShipToParty(sh.getCell(col, row).getContents());	
						}
						else if(col==6)
						{
							modal.setSoldToPartyCode(sh.getCell(col, row).getContents());	
						}
						else if(col==7)
						{
							modal.setSoldToParty(sh.getCell(col, row).getContents());	
						}
						else if(col==8)
						{
							modal.setDriver(sh.getCell(col, row).getContents());	
						}
						else if(col==9)
						{
							modal.setMobileNo(sh.getCell(col, row).getContents());	
						}
						else if(col==10)
						{
							modal.setPlantCode(sh.getCell(col, row).getContents());	
						}
						else if(col==11)
						{
							modal.setInvoiceQnty(Float.parseFloat(sh.getCell(col, row).getContents()));	
						}
						else if(col==12)
						{
							modal.setMatarialDesc(sh.getCell(col, row).getContents());	
						}
						else if(col==13)
						{
							modal.setDriverLicence(sh.getCell(col, row).getContents());	
						}
						else if(col==14)
						{
							modal.setShipToPartyAddress(sh.getCell(col, row).getContents());	
						}
					}
					modalList.add(modal);
					
				}
				String jsonInString = gson.toJson(modalList);
			 
				ClientResponse resData = webResource.type(MediaType.APPLICATION_JSON).post(ClientResponse.class, jsonInString);
				System.out.println("Response:"+resData);
				wb.close();
				fs.close();
				System.out.println("Call Data");
			}
			catch(Exception e)
			{
				System.out.println(e.getMessage());
			}
			finally
			{
				wb.close();
				fs.close();
				
			}
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
		}
		finally
		{
			
		}
		  
	}

	public static void main(String args[]) throws BiffException, IOException 
	{
//		readExcel();
//		Timer timer = new Timer();
//		timer.schedule(this, 10 * 1000, 10 * 1000);
		
		new ExcelDataController();
		
	}

	
}
