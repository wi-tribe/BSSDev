package com.portal.web.comp;

import java.io.Serializable;
import java.rmi.RemoteException;

import com.portal.bas.PControllerImpl;
import com.portal.bas.PInfranetAppController;

public class PLoginBeanImpl extends PControllerImpl
        implements PLoginBean, Serializable {

    private String mLogin = null;
    private transient String mPassword = null;
    private String mService = null;
    private String error = "";
    private String errorCodes = "";

    public PLoginBeanImpl()
            throws RemoteException {
    }

    public void setLogin(String sLogin)
            throws RemoteException {
        this.mLogin = sLogin;
    }

    public void setPassword(String sPassword)
            throws RemoteException {
        this.mPassword = sPassword;
    }

    public void setService(String sService)
            throws RemoteException {
        this.mService = sService;
    }

    public void update(int reason, Object data) {
    }

    public Object getSelectionDataFor(String dataItem, int index) {
        return null;
    }

    public String getError() {
        return error;
    }

    public String getErrorCodes() {
        return errorCodes;
    }

    public static Object[] convertStrToArgs(String str) {
        Object[] arguments = new Object[3];
        arguments = str.split(",");
        return arguments;
    }

    public void login_verify(play.mvc.Scope.Session session, String Identifier) {
    	System.out.println("Started......................");
        
    	PInfranetAppController appCtrl = getAppController();
        try {
        	appCtrl.verifyAndSwitchToUser(this.mLogin, this.mPassword, "/service/" + this.mService);

        } catch (RemoteException e) {
        	System.out.println("Exception......................");
			e.printStackTrace();
		}
        
    }
}
