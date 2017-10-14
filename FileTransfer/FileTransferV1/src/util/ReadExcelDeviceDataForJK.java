package util;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

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

public class ReadExcelDeviceDataForJK 
{
		public static void main(String s[])
		{
			
			try
			{
				String FilePath = "D:\\Vishal\\ProjectBackup\\JKGPS\\db\\gpsdata.xls";
				FileInputStream fs = new FileInputStream(FilePath);
				Workbook wb = Workbook.getWorkbook(fs);
				
				
				try
				{
					Sheet sh = wb.getSheet("muddapur_plant");
					int totalNoOfRows = sh.getRows();
					int totalNoOfCols = sh.getColumns();
					
					
					for (int row = 1; row < totalNoOfRows; row++) 
					{
						
					
						for (int col = 0; col < totalNoOfCols; col++) 
						{
							
							String ssss = sh.getCell(col, row).getContents();
							
							if(ssss != null && !ssss.equals(""))
							{
								System.out.println(ssss);
							}
							else
							{
								//System.out.println("AAAAA");
							}
						
							
							
						}
					}
					
		
					
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
}
