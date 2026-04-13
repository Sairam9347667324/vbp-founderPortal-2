


package com.vblp.founderportal.DTO;

public class PendingLeadDTO {
	
	
	

    private String fullName;
    private String phoneNumber;
	
    
    public String getfullName() {
		return fullName;
	}
	public void setfullName(String fullName) {
		this.fullName = fullName;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	
	public PendingLeadDTO(String fullName, String phoneNumber) {
		super();
		this.fullName = fullName;
		this.phoneNumber = phoneNumber;
	}

	public PendingLeadDTO() {
		
	}
    
}
