package models.utils;

import java.rmi.RemoteException;

import play.cache.Cache;
import play.mvc.Scope.Session;

import com.portal.bas.PCachedContext;
import com.portal.bas.PPooledConnectionClientServices;
import com.portal.pcm.EBufException;
import com.portal.pcm.FList;
import com.portal.pcm.PortalException;

public class ExecuteOpcode {

	public static FList execute(int opcode, FList input, Session session, int flag) throws RemoteException  {
		PPooledConnectionClientServices pCS = Cache.get(session.getId(),PPooledConnectionClientServices.class);
		PCachedContext ctx = null;
		FList result = new FList();
		
		try {
			ctx = (PCachedContext) pCS.getContext();
        	result = ctx.opcode(opcode, flag, input);

        } catch (EBufException e) {
        	ServletUtil.logIn(pCS,session.get("name"),session.get("password"),session.get("url"));

        	try {
        		ctx = (PCachedContext) pCS.getContext();
				return ctx.opcode(opcode,flag, input);
			} catch (EBufException e2) {
				e2.printStackTrace();
			}
            
        	System.out.println("Getting connection again.................");
            return result;
        } finally {
        	try {
				pCS.getAppController().releaseConnection(ctx);
			} catch (RemoteException e) {
				e.printStackTrace();
			}
        }
		return result;
    }
	
	
	public static FList execute(int opcode, FList input, Session session) throws RemoteException {
		PPooledConnectionClientServices pCS = Cache.get(session.getId(),PPooledConnectionClientServices.class);
		
		PCachedContext ctx = null;
		FList result = new FList();
		
		try {
				ctx = (PCachedContext) pCS.getContext();
				result = ctx.opcode(opcode, input);
			
		} catch (EBufException e) {
        	ServletUtil.logIn(pCS,session.get("user"),session.get("password"),session.get("url"));
        	
        	try {
        		if(e.getError() != 3) {
        			ctx = (PCachedContext) pCS.getContext();
        			result = ctx.opcode(opcode, input);
        		}
			} catch (EBufException e2) {
				e2.printStackTrace();
			}
            System.out.println("Getting connection again................." + e.getError());
            return result;
        } finally {
        	try {
				pCS.getAppController().releaseConnection(ctx);
			} catch (RemoteException e) {
				e.printStackTrace();
			}
        }
		return result;
    }
	
	public static PCachedContext getContext(Session session) throws RemoteException  {
		PPooledConnectionClientServices pCS = Cache.get(session.getId(),PPooledConnectionClientServices.class);
		PCachedContext ctx = null;
		
		try {
			ctx = (PCachedContext) pCS.getContext();
        } finally {
        
        }
		return ctx;
    }
	
	public static PCachedContext releaseContext(PCachedContext ctx, Session session) throws RemoteException  {
		PPooledConnectionClientServices pCS = Cache.get(session.getId(),PPooledConnectionClientServices.class);
		
		try {
			pCS.getAppController().releaseConnection(ctx);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		return ctx;
    }
}
