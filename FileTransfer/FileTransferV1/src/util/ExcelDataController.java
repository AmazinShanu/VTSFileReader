package util;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
public class ExcelDataController extends TimerTask 
{
	
//	public ExcelDataController() 
//	{
//		init();
//		System.out.println("Timer task Object Created");
//	}
//	
//	public void init() 
//	{
//		JKTripImporter.insertProcessData("InIT");
//		//JKTripImporter.readJKTrip();
//		
//		JKTripImporter.readData();
//		
//		Timer timer = new Timer();
//		timer.schedule(this, 30*60 * 1000, 30*60 * 1000);
//		
//		//timer.schedule(this, 10*1000,  10*1000);
//	}
	@Override
	public void run() 
	{
		System.out.println("Timer task started at:"+new Date());
		//JKTripImporter.readJKTrip();
		JKTripImporter.insertProcessData("call");
		//JKTripImporter.readData();
		System.out.println("Timer task finished at:"+new Date());
	}
}