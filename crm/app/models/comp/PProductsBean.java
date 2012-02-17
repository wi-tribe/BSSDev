package models.comp;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;
import java.util.ResourceBundle;

import com.portal.bas.PModelHandle;
import com.portal.web.comp.Discount;
import com.portal.web.comp.Product;

public abstract interface PProductsBean extends Remote
{
  public abstract List getProducts()
    throws RemoteException;

  public abstract Product getProducts(int paramInt)
    throws RemoteException;

  public abstract void products(PModelHandle paramPModelHandle, ResourceBundle paramResourceBundle)
    throws RemoteException;

  public abstract List getDiscounts();

  public abstract Discount getDiscounts(int paramInt);
}
