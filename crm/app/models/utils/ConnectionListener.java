package models.utils;

import java.io.Serializable;
import java.util.NoSuchElementException;

import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionBindingListener;

import com.portal.bas.PPooledConnectionClientServices;

public class ConnectionListener
  implements HttpSessionBindingListener, Serializable
{
  public Object mObject = null;
  private long mTime;

  public ConnectionListener(long cTime, Object o)
  {
    this.mTime = cTime;
    this.mObject = o;
  }
  
  public void valueBound(HttpSessionBindingEvent event) {
  }

  public void valueUnbound(HttpSessionBindingEvent event) {
    try {
      long diffTimeInSecs = (System.currentTimeMillis() - this.mTime) / 1000L;

      PPooledConnectionClientServices pCS = (PPooledConnectionClientServices)this.mObject;
      pCS.getAppController().unregisterControllers();
      pCS.releaseConnection();
    }
    catch (SecurityException e)
    {
    }
    catch (NoSuchElementException e)
    {
    }
  }
}