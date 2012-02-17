package com.portal.app.ccare.comp;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import models.utils.TaskDetails;

import com.portal.pcm.Poid;

public class PNameinfoBeanP {
	private String mAddress = null;
	private String mCanonCompany = null;
	private String mCanonCountry = null;
	private String mCity = null;
	private String mCompany = null;
	private String mContactType = null;
	private String mEmailAddr = null;
	private String mFirstCanon = null;
	private String mFirstName = null;
	private String mLastCanon = null;
	private String mLastName = null;
	private String mMiddleCanon = null;
	private String mMiddleName = null;
	private String mSalutation = null;
	private String mState = null;
	private String mTitle = null;
	private String mZip = null;
	private String mMobile = null;
	private String mHomePhone = null;
	private String businessType = null;
	private String salesPersonId = null;
	private String salesPersonName = null;
	private String rating = null;
	private String accountType = null;
	private Poid parentAccount = null;
	private boolean isParentAccount = false;
	private String macAddress = null;
	private Date DOB = null;
	private String CNIC = null;
	private String userName = null;
	private String serviceType = null;
	private String status = null;
	private String redirectStatus = null;
	private String statusNotes = null;
	private Date statusChangeDate = null;
	private Date createdDate = null;
	private String deviceType = null;
	private String deviceSerialNumber = null;
	private String plan = null;
	private String speed = null;
	private String totalVolume = null;
	private String volumeConsumed = null;
	private String customerNo = null;
	private BigDecimal volumeConsumedPersentage = null;
	private List<TaskDetails> taskDetails = null;
	private String loginName = null;
	private Poid mServiceObj = null;
	SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        private String Source = null;

    public String getDeviceManufacturer() {
        return DeviceManufacturer;
    }

    public void setDeviceManufacturer(String DeviceManufacturer) {
        this.DeviceManufacturer = DeviceManufacturer;
    }

    public String getDeviceModel() {
        return DeviceModel;
    }

    public void setDeviceModel(String DeviceModel) {
        this.DeviceModel = DeviceModel;
    }

    public String getSource() {
        return Source;
    }

    public void setSource(String Source) {
        this.Source = Source;
    }
        private String DeviceModel = null;
        private String DeviceManufacturer = null;


	public void setAddress(String strAddress) {
		if (strAddress != null) {
			this.mAddress = strAddress;
		} else
			this.mAddress = "";
	}

	public String getAddress() {
		return this.mAddress;
	}

	public void setMobile(String mMobile) {
		if (mMobile != null) {
			this.mMobile = mMobile;
		} else
			this.mMobile = "";
	}

	public String getMobile() {
		return this.mMobile;
	}

	public void setHomePhone(String mHomePhone) {
		if (mHomePhone != null) {
			this.mHomePhone = mHomePhone;
		} else
			this.mHomePhone = "";
	}

	public String getHomePhone() {
		return this.mHomePhone;
	}

	public void setCanonCompany(String strCanonCompany) {
		if (strCanonCompany != null) {
			this.mCanonCompany = strCanonCompany;
		} else
			this.mCanonCompany = "";
	}

	public String getCanonCompany() {
		return this.mCanonCompany;
	}

	public void setCanonCountry(String strCanonCountry) {
		if (strCanonCountry != null) {
			this.mCanonCountry = strCanonCountry;
		} else
			this.mCanonCountry = "";
	}

	public String getCanonCountry() {
		return this.mCanonCountry;
	}

	public void setCity(String strCity) {
		if (strCity != null) {
			this.mCity = strCity;
		} else
			this.mCity = "";
	}

	public String getCity() {
		return this.mCity;
	}

	public void setCompany(String strCompany) {
		if (strCompany != null) {
			this.mCompany = strCompany;
		} else
			this.mCompany = "";
	}

	public String getCompany() {
		return this.mCompany;
	}

	public void setContactType(String strContactType) {
		if (strContactType != null) {
			this.mContactType = strContactType;
		} else
			this.mContactType = "";
	}

	public String getContactType() {
		return this.mContactType;
	}

	public void setEmailAddr(String strEmailAddr) {
		if (strEmailAddr != null) {
			this.mEmailAddr = strEmailAddr;
		} else
			this.mEmailAddr = "";
	}

	public String getEmailAddr() {
		return this.mEmailAddr;
	}

	public void setFirstCanon(String strFirstCanon) {
		if (strFirstCanon != null) {
			this.mFirstCanon = strFirstCanon;
		} else
			this.mFirstCanon = "";
	}

	public String getFirstCanon() {
		return this.mFirstCanon;
	}

	public void setFirstName(String strFirstName) {
		if (strFirstName != null) {
			this.mFirstName = strFirstName;
		} else
			this.mFirstName = "";
	}

	public String getFirstName() {
		return this.mFirstName;
	}

	public void setLastCanon(String strLastCanon) {
		if (strLastCanon != null) {
			this.mLastCanon = strLastCanon;
		} else
			this.mLastCanon = "";
	}

	public String getLastCanon() {
		return this.mLastCanon;
	}

	public void setLastName(String strLastName) {
		if (strLastName != null) {
			this.mLastName = strLastName;
		} else
			this.mLastName = "";
	}

	public String getLastName() {
		return this.mLastName;
	}

	public void setMiddleCanon(String strMiddleCanon) {
		if (strMiddleCanon != null) {
			this.mMiddleCanon = strMiddleCanon;
		} else
			this.mMiddleCanon = "";
	}

	public String getMiddleCanon() {
		return this.mMiddleCanon;
	}

	public void setMiddleName(String strMiddleName) {
		if (this.mMiddleName != null) {
			this.mMiddleName = strMiddleName;
		} else
			this.mMiddleName = "";
	}

	public String getMiddleName() {
		return this.mMiddleName;
	}

	public void setSalutation(String strSalutation) {
		if (strSalutation != null) {
			this.mSalutation = strSalutation;
		} else
			this.mSalutation = "";
	}

	public String getSalutation() {
		return this.mSalutation;
	}

	public void setState(String strState) {
		if (strState != null) {
			this.mState = strState;
		} else
			this.mState = "";
	}

	public String getState() {
		return this.mState;
	}

	public void setTitle(String strTitle) {
		if (strTitle != null) {
			this.mTitle = strTitle;
		} else
			this.mTitle = "";
	}

	public String getTitle() {
		return this.mTitle;
	}

	public void setZip(String strZip) {
		if (strZip != null) {
			this.mZip = strZip;
		} else
			this.mZip = "";
	}

	public String getZip() {
		return this.mZip;
	}

	public void setServiceObj(Poid pServiceObj) {
		this.mServiceObj = pServiceObj;
	}

	public Poid getServiceObj() {
		return this.mServiceObj;
	}

	public String getBusinessType() {
		return businessType;
	}

	public void setBusinessType(String businessType) {
		if (businessType != null) {
			this.businessType = businessType;
		} else
			this.businessType = "";
	}

	public String getSalesPersonId() {
		return salesPersonId;
	}

	public void setSalesPersonId(String salesPersonId) {
		if (salesPersonId != null) {
			this.salesPersonId = salesPersonId;
		} else
			this.salesPersonId = "";
	}

	public String getSalesPersonName() {
		return salesPersonName;
	}

	public void setSalesPersonName(String salesPersonName) {
		if (salesPersonName != null) {
			this.salesPersonName = salesPersonName;
		} else
			this.salesPersonName = "";
	}

	public String getRating() {
		return rating;
	}

	public void setRating(String rating) {
		if (rating != null) {
			this.rating = rating;
		} else
			this.rating = "";
	}

	public String getAccountType() {
		return accountType;
	}

	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}

	public Poid getParentAccount() {
		return parentAccount;
	}

	public void setParentAccount(Poid parentAccount) {
		this.parentAccount = parentAccount;
	}

	public String getMacAddress() {
		return macAddress;
	}

	public void setMacAddress(String macAddress) {
		this.macAddress = macAddress;
	}

	public Date getDOB() {
		return DOB;
	}

	public void setDOB(Date dOB) {
		DOB = dOB;
	}

	public String getCNIC() {
		return CNIC;
	}

	public void setCNIC(String cNIC) {
		CNIC = cNIC;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getServiceType() {
		return serviceType;
	}

	public void setServiceType(String serviceType) {
		this.serviceType = serviceType;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getRedirectStatus() {
		return redirectStatus;
	}

	public void setRedirectStatus(String redirectStatus) {
		this.redirectStatus = redirectStatus;
	}

	public String getStatusNotes() {
		return statusNotes;
	}

	public void setStatusNotes(String statusNotes) {
		this.statusNotes = statusNotes;
	}

	public String getStatusChangeDate() {
		return formatter.format(statusChangeDate);
	}

	public void setStatusChangeDate(Date statusChangeDate) {
		this.statusChangeDate = statusChangeDate;
	}

	public String getCreatedDate() {
		return formatter.format(createdDate);
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public String getDeviceType() {
		return deviceType;
	}

	public void setDeviceType(String deviceType) {
		this.deviceType = deviceType;
	}

	public String getDeviceSerialNumber() {
		return deviceSerialNumber;
	}

	public void setDeviceSerialNumber(String deviceSerialNumber) {
		this.deviceSerialNumber = deviceSerialNumber;
	}

	public String getPlan() {
		return plan;
	}

	public void setPlan(String plan) {
		this.plan = plan;
	}

	public String getSpeed() {
		return speed;
	}

	public void setSpeed(String speed) {
		this.speed = speed;
	}

	public String getVolumeConsumed() {
		return volumeConsumed;
	}

	public void setVolumeConsumed(String volumeConsumed) {
		this.volumeConsumed = volumeConsumed;
	}

	public BigDecimal getVolumeConsumedPersentage() {
		return volumeConsumedPersentage;
	}

	public void setVolumeConsumedPersentage(BigDecimal volumeConsumedPersentage) {
		this.volumeConsumedPersentage = volumeConsumedPersentage;
	}

	public boolean isParentAccount() {
		return isParentAccount;
	}

	public void setParentAccount(boolean isParentAccount) {
		this.isParentAccount = isParentAccount;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public List<TaskDetails> getTaskDetails() {
		return taskDetails;
	}

	public void setTaskDetails(List<TaskDetails> taskDetails) {
		this.taskDetails = taskDetails;
	}

	public String getTotalVolume() {
		return totalVolume;
	}

	public void setTotalVolume(String totalVolume) {
		this.totalVolume = totalVolume;
	}

	public String getCustomerNo() {
		return customerNo;
	}

	public void setCustomerNo(String customerNo) {
		this.customerNo = customerNo;
	}
}
