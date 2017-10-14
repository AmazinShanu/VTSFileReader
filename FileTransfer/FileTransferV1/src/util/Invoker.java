package util;

import java.io.IOException;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class Invoker extends HttpServlet 
{
	Timer timer;
	private static final long serialVersionUID = 1L;
	public Invoker() 
    {
        super();
    }
    
    @Override
    public void init(ServletConfig config) throws ServletException 
    {
    	System.out.println("TimerTask started for ExcelDataController.........");
    	 //ExcelDataController.getInstance();
    	//new ExcelDataController();
    	JKTripImporter.insertProcessData("InIT");
    	
    	
    	Timer timer = new Timer();
    	timer.schedule(new ExcelDataController(), 10*1000,  10*1000);
    }
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
    {
		
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		
	}
	
	@Override
	public void destroy() 
	{
		super.destroy();
		timer.cancel();
		
	}
}
