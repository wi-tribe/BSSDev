/*
 * BRRMUtility.java
 *
 * Created on March 30, 2011, 1:44 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.witribe.util;

import com.portal.bas.comp.PIAComponentCollectionBean;
import com.portal.pcm.DefaultLog;
import com.portal.pcm.EBufException;
import com.portal.pcm.PortalContext;
import java.rmi.RemoteException;
import com.portal.bas.*;
import java.util.Properties;

/**
 *
 * @author ES61060
 */
public class BRRMUtility extends PIAComponentCollectionBean implements java.io.Serializable{
   
    /** Creates a new instance of BRRMUtility */
    public BRRMUtility() throws RemoteException{
    }
    public PCachedContext  getLocalContext() throws RemoteException, EBufException {
	     
        PCachedContext pc = getContext();
        DefaultLog.log(8, " getLocalContext start...");
      
       // Properties props = new Properties();

		// The connection string is of the form:
		// pcp://<login>:<password>@<hostname>:<port>/service/adminclient 1
		//props.put("infranet.connection",  "pcp://root.0.0.0.1:password@192.168.52.71:40401 /service/admin_client 1");
	   // props.put("infranet.login.type", "1");
       // DefaultLog.log(8,"getLocalContext......2");
       // pc.connect(props);
        DefaultLog.log(8,"getLocalContext......3");
        return pc;
	}
     public void  getreleaseContext(PCachedContext ctx) throws RemoteException {
	     DefaultLog.log(8,"getreleaseContext......");
           if(ctx != null) releaseContext(ctx);
	}
     
    
}

