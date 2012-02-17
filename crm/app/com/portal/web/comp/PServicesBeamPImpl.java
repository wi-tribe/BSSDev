/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.portal.web.comp;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import models.utils.ExecuteOpcode;

import play.mvc.Scope.Session;

import com.portal.app.util.CustomerValErrorData;
import com.portal.bas.PBadFieldDescriptionException;
import com.portal.bas.PCachedContext;
import com.portal.bas.PControllerImpl;
import com.portal.bas.PFieldSpecFactory;
import com.portal.bas.PModelHandle;
import com.portal.pcm.DefaultLog;
import com.portal.pcm.EBufException;
import com.portal.pcm.FList;
import com.portal.pcm.Poid;
import com.portal.pcm.SparseArray;
import com.portal.pcm.fields.FldAccountObj;
import com.portal.pcm.fields.FldArgs;
import com.portal.pcm.fields.FldLogin;
import com.portal.pcm.fields.FldPasswd;
import com.portal.pcm.fields.FldPoid;
import com.portal.pcm.fields.FldResults;
import com.portal.pcm.fields.FldServices;
import com.portal.pcm.fields.FldStatus;

public class PServicesBeamPImpl extends PControllerImpl
  implements PServicesBean
{
  private static final String SEARCH_STR = "/search/pin";
  private static final String SERVICES_SPEC = "FldServices[";
  private static final String CLOSE_BRAC = "]";
  private String mLogin = null;
  private transient String mPassword = null;
  private transient String mConfirm = null;
  private transient Integer mStatus = null;
  private transient Integer mAccountStatus = null;

  private List mServices = new ArrayList();
  private List mServiceStatus = new ArrayList();

  private FList mServicesFlist = null;

  public PServicesBeamPImpl()
    throws RemoteException
  {
  }

  public void setLogin(String sLogin)
    throws RemoteException
  {
    this.mLogin = sLogin;
  }

  public void setPassword(String sPassword)
    throws RemoteException
  {
    this.mPassword = sPassword;
  }

  public void setConfirmation(String sPassword)
    throws RemoteException
  {
    this.mConfirm = sPassword;
  }

  public void setStatus(Integer status)
    throws RemoteException
  {
    this.mStatus = status;
  }

  public void setAccountStatus(Integer status)
    throws RemoteException
  {
    this.mAccountStatus = status;
  }

  public List getServices()
    throws RemoteException
  {
    if (this.mServices == null) {
      return null;
    }

    if (DefaultLog.doLog(8)) {
      DefaultLog.log(8, "WebKit:***getServices()***\n");
    }

    return this.mServices;
  }

  public String getServices(int index)
    throws RemoteException
  {
    if (DefaultLog.doLog(8)) {
      DefaultLog.log(8, "WebKit:***getServices(int index)***\n");
    }

    if ((index < 0) || (index >= this.mServices.size()))
    {
      throw new IndexOutOfBoundsException();
    }

    return (String)this.mServices.get(index);
  }

  protected void setServices(String sResult)
  {
    this.mServices.add(sResult);
  }

  public Integer getServiceStatus(int index)
    throws RemoteException
  {
    if (DefaultLog.doLog(8)) {
      DefaultLog.log(8, "WebKit:***getServiceStatus(int index)***\n");
    }

    if ((index < 0) || (index >= this.mServiceStatus.size()))
    {
      throw new IndexOutOfBoundsException();
    }

    return (Integer)this.mServiceStatus.get(index);
  }

  protected void setServiceStatus(Integer sResult)
  {
    this.mServiceStatus.add(sResult);
  }

  public void update(int reason, Object data)
  {
  }

  public Object getSelectionDataFor(String dataItem, int index)
  {
    return null;
  }

  public PModelHandle getModel(int index)
  {
    PModelHandle model = null;

    PCachedContext ctx = null;
    try {
      ctx = getContext();

      if (index != -1) {
        model = (PModelHandle)ctx.getModelField(this.mServicesFlist, PFieldSpecFactory.createSpec("FldServices[" + index + "]"));
      }

    }
    catch (EBufException e)
    {
      if (DefaultLog.doLog(2)) {
        DefaultLog.log(this, 2, e);
      }

    }
    catch (PBadFieldDescriptionException e)
    {
      if (DefaultLog.doLog(2))
        DefaultLog.log(this, 2, e);
    }
    catch (RemoteException e)
    {
    }
    finally
    {
      try {
        releaseContext(ctx);
      }
      catch (RemoteException e)
      {
      }

    }

    return model;
  }

  public void services(Poid acctPoid, Session session)
    throws RemoteException
  {
    try
    {
      SparseArray args = new SparseArray();
      FList f = new FList();
      
      f.set(FldAccountObj.getInst(), acctPoid);
      args.add(1, f);

      FList in = new FList();
      in.set(FldPoid.getInst(), new Poid(acctPoid.getDb(), 235L, "/search/pin"));

      in.set(FldArgs.getInst(), args);

      FList resfl = new FList();
      resfl.set(FldPoid.getInst());
      in.setElement(FldResults.getInst(), -1);

      FList out = ExecuteOpcode.execute(7, in,session);

      this.mServicesFlist = new FList();
      int index = 0;

      if (!out.hasField(FldResults.getInst())) {
        if (DefaultLog.doLog(8)) {
          DefaultLog.log(8, "services() - No services found");
        }
        throw new RemoteException("error.services");
      }

      SparseArray res = out.get(FldResults.getInst());
      Enumeration enumVals = res.getValueEnumerator();
      while (enumVals.hasMoreElements())
      {
        FList services = (FList)enumVals.nextElement();

        if (services == null) {
          if (DefaultLog.doLog(8)) {
            DefaultLog.log(8, "services() - No Services found");
          }
          throw new RemoteException("error.services");
        }

        this.mServicesFlist.setElement(FldServices.getInst(), index++, services);

        Poid service_poid = services.get(FldPoid.getInst());

        String login = services.get(FldLogin.getInst());
        Integer status = services.get(FldStatus.getInst());
        String password = services.get(FldPasswd.getInst());
        if (service_poid == null) {
          if (DefaultLog.doLog(8)) {
            DefaultLog.log(8, "services() - Service Poid missing");
          }
          throw new RemoteException("error.services");
        }

        String service_type = service_poid.getType();

        int start = service_type.indexOf('/', 1);
        service_type = service_type.substring(start + 1);

        StringBuffer service_txt = new StringBuffer(service_type);
        service_txt.append(",");
        service_txt.append(login);
        service_txt.append(",");
        service_txt.append(service_poid);
        service_txt.append(",");
        service_txt.append(password.substring(6, password.length()));

        setServices(service_txt.toString());

        setServiceStatus(status);
      }

    }
    catch (EBufException e)
    {
      if (DefaultLog.doLog(2)) {
        DefaultLog.log(this, 2, e);
      }
      throw createClientException(e);
    }
    catch (Exception e)
    {
      if (DefaultLog.doLog(2)) {
        DefaultLog.log(this, 2, e);
      }
      throw new RemoteException("error.infranet");
    }
    finally {
      
    }
  }

  

  public CustomerValErrorData[] save(PModelHandle mH, int index)
    throws RemoteException
  {

    return null;
  }
}
