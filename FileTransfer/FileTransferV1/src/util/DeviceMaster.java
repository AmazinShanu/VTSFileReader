package util;

public class DeviceMaster 
{
	
	long id;
	
	String imeiNumber;
	
	String mobileNumber;
	
	String mobileCom;
	
	String createdOn;
	
	String updatedOn;
	
	String isActive;
	
	long company_id;
	
	
	
	
	public String getImeiNumber() {
		return imeiNumber;
	}
	public void setImeiNumber(String imeiNumber) {
		this.imeiNumber = imeiNumber;
	}
	public long getCompany_id() {
		return company_id;
	}
	public void setCompany_id(long company_id) {
		this.company_id = company_id;
	}
	DeviceMaster()
	{
		super();
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getimeiNumber() {
		return imeiNumber;
	}
	public void setimeiNumber(String imeiNumber) {
		this.imeiNumber = imeiNumber;
	}
	public String getMobileNumber() {
		return mobileNumber;
	}
	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}
	public String getMobileCom() {
		return mobileCom;
	}
	public void setMobileCom(String mobileCom) {
		this.mobileCom = mobileCom;
	}
	
	public String getCreatedOn() {
		return createdOn;
	}
	public void setCreatedOn(String createdOn) {
		this.createdOn = createdOn;
	}
	public String getUpdatedOn() {
		return updatedOn;
	}
	public void setUpdatedOn(String updatedOn) {
		this.updatedOn = updatedOn;
	}
	public String getIsActive() {
		return isActive;
	}
	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}
	
}
