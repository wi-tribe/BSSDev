package jobs;

import java.net.URL;
import java.rmi.RemoteException;

import models.utils.ServletUtil;
import play.jobs.Every;
import play.jobs.Job;
import play.jobs.OnApplicationStart;

import com.portal.bas.PCachedContext;
import com.portal.bas.PClientContext;
import com.portal.bas.PPooledConnectionClientServices;
import com.portal.pcm.EBufException;
import com.portal.pcm.FList;
import com.portal.pcm.Poid;
import com.portal.pcm.PortalOp;
import com.portal.pcm.fields.FldFirstName;
import com.portal.pcm.fields.FldLastName;
import com.portal.pcm.fields.FldPoid;

@OnApplicationStart
@Every("2mn")
public class ConnectionListener extends Job {
	public static String val = "Asim";
	public static PPooledConnectionClientServices mPCS = null;

	@Override
	public void doJob() {
		if (mPCS == null) {
			URL propsURL = getClass().getResource("/WebKit.properties");
			System.out.println("propsURL: " + propsURL);
			if (propsURL != null) {
				mPCS = new PPooledConnectionClientServices();
				mPCS.setPropertyURL(propsURL);

				PClientContext.setCustomServices(mPCS, false);

				Object connectionKey = ServletUtil.logIn(mPCS);
			}
		}
		if (mPCS != null) {
			PCachedContext ctx = null;
			FList inflist = null;
			try {
				inflist = createFList();
				ctx = mPCS.getAppController().getConnection();
				ctx.opcode(PortalOp.TEST_LOOPBACK, inflist);
				System.out.println("My loop back 0 Called");
			} catch (RemoteException re) {
				ServletUtil.logIn(mPCS);
				try {
					ctx = mPCS.getAppController().getConnection();
					ctx.opcode(PortalOp.TEST_LOOPBACK, inflist);
					System.out.println("My loop back 1 Called");
				} catch (RemoteException e) {
					e.printStackTrace();
				} catch (EBufException ex) {
					ex.printStackTrace();
				}
				
			} catch (EBufException ex) {
				ServletUtil.logIn(mPCS);
				try {
					ctx = mPCS.getAppController().getConnection();
					ctx.opcode(PortalOp.TEST_LOOPBACK, inflist);
					System.out.println("My loop back 2 Called");
				} catch (RemoteException e) {
					e.printStackTrace();
				} catch (EBufException ex1) {
					ex1.printStackTrace();
				}
			} finally {
				try {
					mPCS.getAppController().releaseConnection(ctx);
				} catch (RemoteException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	public static FList createFList() throws EBufException {
		FList flist = new FList();
		flist.set(FldPoid.getInst(), new Poid(1));
		flist.set(FldFirstName.getInst(), "Mickey");
		flist.set(FldLastName.getInst(), "Mouse");
		return flist;
	}

}
