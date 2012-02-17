package com.portal.web.comp;

import java.rmi.Remote;
import java.rmi.RemoteException;

public abstract interface PLoginBean extends Remote {

    public abstract void setLogin(String paramString)
            throws RemoteException;

    public abstract void setPassword(String paramString)
            throws RemoteException;

    public abstract void setService(String paramString)
            throws RemoteException;

    public abstract void login_verify(play.mvc.Scope.Session paramHttpSession, String paramString)
            throws RemoteException;

    
    public abstract String getError();

    public abstract String getErrorCodes();
}
