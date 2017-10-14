package util;


public class Vehicle 
{
	long id;
	String vehical_capacity;
	
	String No_of_wheels;
	String ownerName;
	String vehicleNo;
	String isActive;
	String createdOn;
	String status;
	
	
	
	
	
	public String getVehical_capacity() {
		return vehical_capacity;
	}
	public void setVehical_capacity(String vehical_capacity) {
		this.vehical_capacity = vehical_capacity;
	}
	public void setNo_of_wheels(String no_of_wheels) {
		No_of_wheels = no_of_wheels;
	}
	
	
	
	
	
	public String getNo_of_wheels(){
		return No_of_wheels;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getOwnerName() {
		return ownerName;
	}
	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
	}
	public String getVehicleNo() {
		return vehicleNo;
	}
	public void setVehicleNo(String vehicleNo) {
		this.vehicleNo = vehicleNo;
	}
	public String getIsActive() {
		return isActive;
	}
	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}
	public String getCreatedOn() {
		return createdOn;
	}
	public void setCreatedOn(String createdOn) {
		this.createdOn = createdOn;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	
}
