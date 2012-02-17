package models.utils;

import java.net.URL;
import java.rmi.RemoteException;

import javax.xml.rpc.ServiceException;

import pk.net.wi_tribe.helpdesk.rpc.soap.jirasoapservice_v2.JiraSoapService;
import pk.net.wi_tribe.helpdesk.rpc.soap.jirasoapservice_v2.JiraSoapServiceService;
import pk.net.wi_tribe.helpdesk.rpc.soap.jirasoapservice_v2.JiraSoapServiceServiceLocator;


/**
 * This represents a SOAP session with JIRA including that state of being logged
 * in or not
 */
public class SOAPSession {
	private JiraSoapServiceService jiraSoapServiceLocator;
	private JiraSoapService jiraSoapService;
	private String token;

	public SOAPSession(URL webServicePort) {
		jiraSoapServiceLocator = new JiraSoapServiceServiceLocator();
		try {
			if (webServicePort == null) {
				jiraSoapService = jiraSoapServiceLocator.getJirasoapserviceV2();
			} else {
				jiraSoapService = jiraSoapServiceLocator.getJirasoapserviceV2(webServicePort);
				System.out.println("SOAP Session service endpoint at " + webServicePort.toExternalForm());
			}
		} catch (ServiceException e) {
			throw new RuntimeException("ServiceException during SOAPClient contruction", e);
		}
	}

	public SOAPSession() {
		this(null);
	}

	public void connect(String userName, String password) throws RemoteException {
		System.out.println("\tConnnecting via SOAP as : " + userName);
		token = getJiraSoapService().login(userName, password);
		System.out.println("\tConnected");
	}

	public String getAuthenticationToken() {
		return token;
	}

	public JiraSoapService getJiraSoapService() {
		return jiraSoapService;
	}

	public JiraSoapServiceService getJiraSoapServiceLocator() {
		return jiraSoapServiceLocator;
	}
}
