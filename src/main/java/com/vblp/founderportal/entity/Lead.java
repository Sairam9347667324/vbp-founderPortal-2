package com.vblp.founderportal.entity;


import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;


@Entity
@Table(name = "leads")
public class Lead {

	public enum EnquirySource{
			Website,
			Referral,		
			Youtube,
			Facebook,
			Linkedin,
			Instagram,
			GoogleAds,
			MetaAds,
			WalkIn,
	       }
	
	public enum LeadStatus {
            Newlead,
            Contacted,
            Accepted,
            ProposalSent,
            ClosedWon,
            ClosedLost
	}
	
	   public enum ServiceType {
		    WebDevelopment,
		    AppDevelopment,
		    LogoDesign,
		    CloudService,
		    DigitalMarketing
		}
	   
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "full_name", nullable = false, length = 100)
    private String fullName;

    @Column(name = "business_name", nullable = false, length = 100)
    private String businessName;
    
    @Column(name = "location", nullable = false, length = 100)
    private String location;
    
	@Column(name = "phone_number", nullable = false, length = 15)
    private String phoneNumber;

    @Enumerated(EnumType.STRING)
    @Column(name = "enquiry_source", nullable = false, length = 20)
    private EnquirySource enquirySource;

    @Enumerated(EnumType.STRING)
    @Column(name = "service_type", nullable = false, length = 20) // previously was String
    private ServiceType serviceType;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 20) // previously caused truncation
    private LeadStatus status;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @Column(name = "project_date", nullable = false)
    private LocalDate projectDate;
    
    @Column(name = "type", nullable = false, length = 100)
    private String type;
 
//getters and setters
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}

    public String getFullName() {
		return fullName;
	}
    public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getBusinessName() {
		return businessName;
	}
	public void setBusinessName(String businessName) {
		this.businessName = businessName;
	}

    public String getLocation() {
		return location;
	}
     public void setLocation(String location) {
		this.location = location;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public EnquirySource getEnquirySource() {
		return enquirySource;
	}
    public void setEnquirySource(EnquirySource enquirySource) {
		this.enquirySource = enquirySource;
	}

    
    public ServiceType getServiceType() {
		return serviceType;
	}
   public void setServiceType(ServiceType serviceType) {
		this.serviceType = serviceType;
	}

   public LeadStatus getStatus() {
		return status;
	}
   public void setStatus(LeadStatus status) {
		this.status = status;
	}

   public LocalDate getProjectDate() {
		return projectDate;
	}
   public void setProjectDate(LocalDate projectDate) {
		this.projectDate = projectDate;
	}

   public String getType() {
		return type;
	}
   public void setType(String type) {
		this.type = type;
	}


	public Lead(Long id, String fullName, String businessName, String location, String phoneNumber,
			EnquirySource enquirySource, ServiceType serviceType, LeadStatus status, LocalDate projectDate,
			 String type) {
		super();
		this.id = id;
		this.fullName = fullName;
		this.businessName = businessName;
		this.location = location;
		this.phoneNumber = phoneNumber;
		this.enquirySource = enquirySource;
		this.serviceType = serviceType;
		this.status = status;
		this.projectDate = projectDate;
		this.type = type;
	}


     public Lead() {}
    
}