package com.portal.web.comp;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

import com.portal.pcm.Poid;

import play.mvc.Scope.Session;

public abstract interface PProductsBeanP extends Remote
{
  public abstract List getProducts()
    throws RemoteException;

  public abstract Product getProducts(int paramInt)
    throws RemoteException;

  public abstract void products(Poid accountpoid, Session session)
    throws RemoteException;

  public abstract List getDiscounts();

  public abstract Discount getDiscounts(int paramInt);
}