package models.utils;

import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

import pk.net.wi_tribe.helpdesk.rpc.soap.jirasoapservice_v2.JiraSoapService;

import com.atlassian.jira.rpc.exception.RemoteAuthenticationException;
import com.atlassian.jira.rpc.exception.RemotePermissionException;
import com.atlassian.jira.rpc.exception.RemoteValidationException;
import com.atlassian.jira.rpc.soap.beans.RemoteCustomFieldValue;
import com.atlassian.jira.rpc.soap.beans.RemoteIssue;


public class JiraTasks {

	// Login details
	static final String LOGIN_NAME = "asim.nawaz";
	static final String LOGIN_PASSWORD = "jiramaster";

	// Constants for issue updation
	static String ISSUE_KEY = null;
	private static JiraSoapService jiraSoapService = null;
	private static String authToken = null;
	
	static {
		String baseUrl = "http://helpdesk.wi-tribe.net.pk:8080/rpc/soap/jirasoapservice-v2?wsdl";

		try {
			// Create new Session
			SOAPSession soapSession = new SOAPSession(new URL(baseUrl));

			// Now login
			soapSession.connect(LOGIN_NAME, LOGIN_PASSWORD);

			// Get the JIRA SOAP Service and authentication token
			jiraSoapService = soapSession.getJiraSoapService();
			authToken = soapSession.getAuthenticationToken();

			// get CF Details
		
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public JiraTasks() {
		
	}
	
	public static void main(String[] args) {
				JiraTasks.getCFsFromIssue("372110339");
			

	}

	public static List<TaskDetails> getCFsFromIssue(String customerID)  {
		ISSUE_KEY = "'Customer ID' ~ " + customerID;
		RemoteIssue[] issues = null;
		if(authToken == null || jiraSoapService == null)
			getAuthCode();
		
		System.out.println("ISSUE_KEY........." + ISSUE_KEY + " authToken:" + authToken);
		
		try {
			issues = jiraSoapService.getIssuesFromJqlSearch(authToken, ISSUE_KEY, 5);
		} catch (com.atlassian.jira.rpc.exception.RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		List<TaskDetails> tasksList = new ArrayList<TaskDetails>();
		TaskDetails task = null;
		for(RemoteIssue issue:issues) {
			task = new TaskDetails(issue.getKey(), issue.getDescription());
			System.out.println("Value for CF with Id:" + issue.getDescription() + " Status : " + issue.getKey());
			tasksList.add(task);
			
		}
		
		return tasksList;
	}
	
	public static void getAuthCode() {
		String baseUrl = "http://helpdesk.wi-tribe.net.pk:8080/rpc/soap/jirasoapservice-v2?wsdl";
		System.out.println("getAuthCode() Called.............");
		try {
			// Create new Session
			SOAPSession soapSession = new SOAPSession(new URL(baseUrl));

			// Now login
			soapSession.connect(LOGIN_NAME, LOGIN_PASSWORD);

			// Get the JIRA SOAP Service and authentication token
			jiraSoapService = soapSession.getJiraSoapService();
			authToken = soapSession.getAuthenticationToken();

			// get CF Details
		
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

