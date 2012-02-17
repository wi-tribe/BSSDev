package models.utils;

import com.portal.pcm.Poid;

public class Accounts {
	private String accountNo;
	private String firstName;
	private String lastName;
	private String companyName;
	private Poid accountPoid;
	
	Accounts(String accountNo, String firstName, String lastName, String companyName,Poid accountPoid) {
		this.accountNo = accountNo;
		this.firstName = firstName;
		this.lastName = lastName;
		this.companyName = companyName;
		this.accountPoid = accountPoid;
	}
	
	public String getAccountNo() {
		return accountNo;
	}

	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	
	public Poid getAccountPoid() {
		return accountPoid;
	}

	public void setAccountPoid(Poid accountPoid) {
		this.accountPoid = accountPoid;
	}

}
