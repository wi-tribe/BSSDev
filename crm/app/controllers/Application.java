package controllers;

import java.io.UnsupportedEncodingException;
import java.rmi.RemoteException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import jobs.ConnectionListener;
import models.User;
import models.utils.AccountUtilities;
import models.utils.ServletUtil;
import play.cache.Cache;
import play.data.validation.Required;
import play.mvc.Controller;

import com.portal.bas.PCachedContext;
import com.portal.bas.PClientContext;
import com.portal.bas.PModelHandle;
import com.portal.bas.PPooledConnectionClientServices;
import com.portal.pcm.EBufException;
import com.portal.pcm.FList;
import com.portal.pcm.Poid;
import com.portal.pcm.SparseArray;
import com.portal.pcm.fields.FldAccountObj;
import com.portal.pcm.fields.FldAction;
import com.portal.pcm.fields.FldCreatedT;
import com.portal.pcm.fields.FldCycleEndDetails;
import com.portal.pcm.fields.FldDealInfo;
import com.portal.pcm.fields.FldDescr;
import com.portal.pcm.fields.FldFirstName;
import com.portal.pcm.fields.FldLastName;
import com.portal.pcm.fields.FldLogin;
import com.portal.pcm.fields.FldModT;
import com.portal.pcm.fields.FldOpCorrelationId;
import com.portal.pcm.fields.FldPasswd;
import com.portal.pcm.fields.FldPasswdClear;
import com.portal.pcm.fields.FldPermitted;
import com.portal.pcm.fields.FldPoid;
import com.portal.pcm.fields.FldProducts;
import com.portal.pcm.fields.FldProgramName;
import com.portal.pcm.fields.FldPurchaseEndDetails;
import com.portal.pcm.fields.FldReadAccess;
import com.portal.pcm.fields.FldResult;
import com.portal.pcm.fields.FldServiceObj;
import com.portal.pcm.fields.FldType;
import com.portal.pcm.fields.FldUsageEndDetails;
import com.portal.pcm.fields.FldWriteAccess;
import com.portal.web.comp.PLoginBean;

public class Application extends Controller {

	public static void index() {
		currentUserCan(0);
		render();
	}

	public static void login() {
		render();
	}

	public static void logout() {
		
		PPooledConnectionClientServices pCS = Cache.get(session.getId(),PPooledConnectionClientServices.class);
		
        if (pCS != null) {
            PCachedContext connection = (PCachedContext) pCS.getContext();
            pCS.getAppController().unregisterControllers();
            if (connection != null) {
                PModelHandle model = (PModelHandle) Cache.get("AccountModel");
                if (model != null) {
                    connection.discardModel(model);
                }
                model = null;
                model = (PModelHandle) Cache.get("ServiceModel");
                if (model != null) {
                    connection.discardModel(model);
                }
                pCS.releaseContext(connection);
            }
        }
        flash.success("Deauthentication was successful!");
		session.clear();
		Cache.delete(session.getId());
		Cache.delete(session.getId()+"AccountModel");
		Cache.delete(session.getId()+"ServiceModel");
		login();
	}

	public static void authenticate(@Required String email,
			@Required String password) {

		try {
			verifyAndSwitchToUser(email, password, "/service/admin_client");
		} catch (RemoteException e) {
			flash.error("Invalid user name/ Password.");
			login();
		}
		String url = "pcp://" + email + ":" + password + "@10.1.67.142:11960/service/admin_client 1";
		PPooledConnectionClientServices pCS = new PPooledConnectionClientServices();
		ServletUtil.logIn(pCS,email,password,url);
		
		Cache.set(session.getId(), pCS);
		session.put("user", email);
		session.put("password", password);
		session.put("url", url);
		
		User user = User.findByEmail(email);
		
		connect(user);
		flash.success("Welcome %s!", user.name);
		index();
	}
	 
	static void connect(User user) {
		session.put("id", user.id);
		session.put("userEmail", user.email);
		session.put("userRoleId", user.role);
	}

	public static Boolean currentUserCan(Integer roledId) {
		Boolean okey = false;
		String email = session.get("userEmail");
		Integer userRoleId = null;

		if (session.get("userRoleId") != null) {
			userRoleId = Integer.decode(session.get("userRoleId"));
		}

		if (email != null && userRoleId != null) {
			okey = true;
		}

		if (okey && userRoleId >= roledId) {
			okey = true;
		} else {
			okey = false;
		}

		if (okey == false) {
			flash.error("This is a restricted area, you need to login or ask for an acces level.");
			login();
		}

		return okey;
	}

	static void verifyAndSwitchToUser(String user, String password, String service) throws RemoteException {
		FList input = new FList();
		String custPassword = null;
		input.set(FldLogin.getInst(), user);
		input.set(FldPasswdClear.getInst(), password);
		input.set(FldAction.getInst(), "PCM_OP_ACT_LOGIN");
		PCachedContext ctx = null;
		try {
			ctx = ConnectionListener.mPCS.getAppController().getConnection();
			if (ctx == null) {
				flash.error("Currently application is down, please try later!");
				flash.put("email", user);
				login();
			}

			long db = ctx.getCurrentDB();

			Poid servicePoid = new Poid(db, -1L, service);
			input.set(FldPoid.getInst(), servicePoid);
                        System.out.println("\n input FList of Authentication \n"+input+"\n");
			FList output = ctx.opcode(158, input);

                        System.out.println("\n output FList of Authentication \n"+output+"\n");
			checkVerifyError(output);
			servicePoid = output.get(FldPoid.getInst());
			Poid accountPoid = output.get(FldAccountObj.getInst());

			FList custService = new FList();
			custService.set(FldPoid.getInst(), servicePoid);
			custService.set(FldPermitted.getInst(), "");
			
			output = ctx.opcode(3, custService);
			
			custPassword = output.get(FldPasswd.getInst());
			custPassword = custPassword.substring(4);
			
			
			if (!custPassword.equals(MD5(password))) {
				flash.error("Authentication failed, check your credentials!");
				flash.put("email", user);
				login();
			}
			if ((servicePoid == null) || (accountPoid == null)) {
				flash.error("Authentication failed, check your credentials!");
				flash.put("email", user);
				login();
			}
                        System.out.println("\n AccountPoid is "+accountPoid.toString()+"\n");

			PModelHandle mService = ctx.createModelFrom(servicePoid);
			PModelHandle mAccount = ctx.createModelFrom(accountPoid);
			Cache.set(session.getId() + "AccountModel", mAccount);                        
			Cache.set(session.getId() + "ServiceModel", mService);

                       // AccountUtilities AccUtill = new AccountUtilities();
                        //AccUtill.getLoggedInCSRID(session);
                        
                        System.out.println("\n mAccount Model is "+mAccount.toString()+"\n");
                        session.put("LogInUsrAccID", accountPoid);
		} catch (EBufException e) {
			e.printStackTrace();
		} catch (RemoteException ex) {
			ex.printStackTrace();
			throw ex;
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			try {
				ConnectionListener.mPCS.getAppController().releaseConnection(ctx);
			} catch (RemoteException e) {
				e.printStackTrace();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	}
	
	private static void checkVerifyError(FList output) throws RemoteException, EBufException {
		if (output.get(FldResult.getInst()).intValue() == 0) {
			int error = output.get(FldType.getInst()).intValue();
			
			if ((error == 0) || (error == 3) || (error == 7))
				throw new RemoteException("error.loggingIn");
			if ((error == 1) || (error == 4))
				throw new RemoteException("error.type");
			if (error == 2)
				throw new RemoteException("error.status");
			if (error == 6)
				throw new RemoteException("error.passwd");
			if (error == 8)
				throw new RemoteException("error.dupsession");
		}
	}

	private static String convertedToHex(byte[] data) {
		StringBuffer buf = new StringBuffer();

		for (int i = 0; i < data.length; i++) {
			int halfOfByte = (data[i] >>> 4) & 0x0F;
			int twoHalfBytes = 0;

			do {
				if ((0 <= halfOfByte) && (halfOfByte <= 9)) {
					buf.append((char) ('0' + halfOfByte));
				}

				else {
					buf.append((char) ('a' + (halfOfByte - 10)));
				}

				halfOfByte = data[i] & 0x0F;

			} while (twoHalfBytes++ < 1);
		}
		return buf.toString();
	}

	public static String MD5(String text) throws NoSuchAlgorithmException,
			UnsupportedEncodingException {
		MessageDigest md;
		md = MessageDigest.getInstance("MD5");
		byte[] md5 = new byte[64];
		md.update(text.getBytes("iso-8859-1"), 0, text.length());
		md5 = md.digest();
		return convertedToHex(md5);
	}
}