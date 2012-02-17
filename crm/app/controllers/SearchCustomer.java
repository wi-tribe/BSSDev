package controllers;

import java.math.BigDecimal;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.List;
import java.util.Vector;

import models.Department;
import models.utils.AccountUtilities;
import models.utils.Accounts;
import models.utils.BalanceDetails;
import models.utils.JiraTasks;
import play.mvc.Controller;
import play.mvc.results.NotFound;
import com.portal.pcm.FList;

import com.portal.app.ccare.comp.PIABalanceInfoBeanP;
import com.portal.app.ccare.comp.PIABalanceInfoBeanPImpl;
import com.portal.app.ccare.comp.PNameinfoBeanP;
import com.portal.bas.PInfranetMessageFormat;
import com.portal.bas.PModelHandle;
import com.portal.pcm.EBufException;
import com.portal.pcm.Poid;
import com.portal.pcm.SparseArray;
import com.portal.pcm.fields.FldPoid;
import com.portal.pcm.fields.FldResults;
import com.portal.web.comp.PProductsBeanP;
import com.portal.web.comp.PProductsBeanPImpl;
import com.portal.web.comp.PServicesBeamPImpl;
import com.portal.web.comp.ProductP;
import com.wtb.flds.WtbFldCpeDetails;
import com.wtb.flds.WtbFldSubType;
import java.util.logging.Logger;

/**
 * Courses controller
 * 
 * @author stas
 */
public class SearchCustomer extends Controller {

	/**
	 * Render all departments
	 */
	public static void index() {
		String accountNo = params.get("find[accno]", String.class);
		String fName = params.get("find[fname]", String.class);
		String lName = params.get("find[lname]", String.class);
		String serviceid = params.get("find[serviceid]", String.class);
		String macaddress = params.get("find[macaddress]", String.class);
		String cnic = params.get("find[cnic]", String.class);
		String mobile = params.get("find[mobile]", String.class);

		List<Accounts> accounts = new ArrayList<Accounts>();

		try {
			accounts = AccountUtilities.searchCustomer(accountNo, fName, lName,
					serviceid, macaddress, cnic, mobile, session);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (EBufException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		render(accounts);
	}

	public static Object[] convertStrToArgs(String str) {
		Object[] arguments = new Object[3];
		arguments = str.split(",");
		return arguments;
	}

	/**
	 * Render a department
	 * 
	 * @param id
	 *            , department ID
	 */
	public static void department(Poid id) {
		Application.currentUserCan(1);
		System.out.println("Poid ......." + id);
		render();
	}

	/**
	 * Render department editor for department ID
	 * 
	 * @param id, the department ID
	 */
	public static void edit(Long id) {
		Application.currentUserCan(2);

		Department dep = Department.findById(id);
		if (dep == null) {
			throw new NotFound(null);
		}

		render(dep);
	}

	public static void details(String accountNo) {
		Application.currentUserCan(1);
		String[] val = null;
		
		if(accountNo != null) {
			val = accountNo.split("-");
			accountNo = val[1];
		} else
			accountNo = session.get("accountnumber");
		
		Poid accountPoid = Poid.valueOf("0.0.0.1 /account " + accountNo);
		PNameinfoBeanP customerDetails = null;
		PIABalanceInfoBeanP cb;
		String volume = "0";
        String volumeAvailable = "0";
        BigDecimal totalConsumedInPercent = new BigDecimal(0.0D);
        BigDecimal creditLimit = new BigDecimal(0.0D); 
        
		try {
			AccountUtilities myUtill = new AccountUtilities();
			
			cb = new PIABalanceInfoBeanPImpl();
			cb.getAccountDetails(accountPoid, session);
			
			customerDetails = cb.getAllNameinfo();
			customerDetails.setCustomerNo(accountNo);
			
			cb.getAcctLvlBalanceGroupAndBillinfoID(accountPoid, session);
	        PModelHandle mHBillinfos = cb.getAllBillInfoAndDetailsForAcct(accountPoid, session);
	        cb.processOutputFlistOfBillinfos(mHBillinfos,session);
	        
			Poid mAcctPoid = cb.getAccountPoid();
	        Poid mBalGrpPoid = cb.getBalanceGroupPoid();
	        
	        Hashtable hashBalances = cb.getBalancesForAcctAndBalGrpID(mAcctPoid, mBalGrpPoid, session);
	        if (!hashBalances.isEmpty()) {
	            Enumeration enumKeys = hashBalances.keys();
	            Enumeration enumBalances = hashBalances.elements();
	            while (enumBalances.hasMoreElements()) {
	                Integer key = (Integer) enumKeys.nextElement();
	                String desc = (String) cb.getBEIDDescription(key.intValue(), session);
	                Vector vecBal = (Vector) enumBalances.nextElement();

	                if ((vecBal != null) && (desc.equals("MB Free"))) {
	                    volume = vecBal.elementAt(3).toString().replace("-", "");
	                } else if ((vecBal != null) && (desc.equals("MB Free AVAILABLE"))) {
	                    volumeAvailable = vecBal.elementAt(3).toString().replace("-", "");
	                }
	                if(key == 586)
	                	creditLimit = (BigDecimal)vecBal.elementAt(1);
	            }

	            if ((!volume.toString().equals("0")) && (!volumeAvailable.toString().equals("0"))) {
	                double tVol = Double.parseDouble(volume.toString());
	                double cVol = Double.parseDouble(volumeAvailable.toString());
	                double consumption = (tVol - cVol) / tVol * 100.0D;
	                totalConsumedInPercent = BigDecimal.valueOf(consumption);
	                totalConsumedInPercent = totalConsumedInPercent.setScale(2, 5);
	            }

	            customerDetails.setTotalVolume(volume + " MB");
		        customerDetails.setVolumeConsumed(volumeAvailable + " MB");
		        customerDetails.setVolumeConsumedPersentage(totalConsumedInPercent);
	        }
	        
	        
			com.portal.web.comp.PServicesBeamPImpl serviceBean = new PServicesBeamPImpl();
			serviceBean.services(accountPoid, session);
			List output = serviceBean.getServices();
			int size = output.size();
			if (output != null) {
				for (int i = 0; i < size; i++) {
					String buf = (String) output.get(i);

					if (buf.contains("/service/ip")) {
						customerDetails.setLoginName((PInfranetMessageFormat.format("{1}",
								convertStrToArgs(buf))));
						customerDetails.setServiceObj(Poid.valueOf(PInfranetMessageFormat
								.format("{2}", convertStrToArgs(buf))));
						break;
					}
				}
			}
			
			myUtill.setAcctPoid(accountPoid);
			myUtill.setmServicePoid(customerDetails.getServiceObj());
			myUtill.getProfileNameNMac(session);
			customerDetails.setDeviceType(myUtill.getCpeType());
			customerDetails.setDeviceSerialNumber(myUtill.getSerialNo());
			customerDetails.setMacAddress(myUtill.getmMacAddress());
			customerDetails.setSpeed(myUtill.getmProfileName());
                        customerDetails.setSalesPersonId(myUtill.getSalespersonID());
                        customerDetails.setDeviceManufacturer(myUtill.getDevManufacturer());
                        customerDetails.setDeviceModel(myUtill.getDevModel());
                        customerDetails.setSource(myUtill.getSource());
			
			PProductsBeanP pb = new PProductsBeanPImpl();
			
			pb.products(accountPoid, session);
	        List output1 = pb.getProducts();
	        if (output != null) {
	            int size1 = output1.size();
	            for (int i = 0; i < size1; i++) {
	                ProductP pdt = (ProductP) output1.get(i);
	                if (!pdt.getPlanID().equals("0.0.0.0  0 0")) {
	                	customerDetails.setPlan(pdt.getDealName());
	                }
	            }
	        }
	        
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		customerDetails.setTaskDetails(JiraTasks.getCFsFromIssue(accountNo));
		if(creditLimit == null)
			creditLimit = new BigDecimal(0.00);
		System.out.println("creditLimit............." + creditLimit);
		session.put("accountpoid", accountPoid.toString());
		session.put("accountnumber", accountNo);
		session.put("servicenumber", customerDetails.getServiceObj().toString());
		session.put("creditlimit", creditLimit);
		
		render(customerDetails);
	}

	/**
	 * Render department editor for adding new department
	 */
	public static void add() {
		Application.currentUserCan(2);
		render();
	}

	public static void balances() {
		Application.currentUserCan(2);
		String accountNo = session.get("accountnumber");
		Poid accountPoid = Poid.valueOf("0.0.0.1 /account " + accountNo);
		BalanceDetails balances = new BalanceDetails();
		
		AccountUtilities myUtill;
		try {
			myUtill = new AccountUtilities();
		
			balances.setInvoices(myUtill.getInvoices(accountPoid, session));
			balances.setPayments(myUtill.getLastPayment(accountPoid, session,10));
			myUtill.getBalanceSummaryForBillInfo(accountPoid,1, session);
			balances.setDueNow(myUtill.getDueNow());
			balances.setCurrentBill(myUtill.getUnbilledAmount());
			balances.setPayOrAdjust(myUtill.getUnappliedAmount());
			balances.setTotalAmount(myUtill.getTotalBalance());
			balances.setDueAmount(myUtill.getAllBillDueAmount());
			balances.setCreditLimit(new BigDecimal(session.get("creditlimit").toString()));
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		render(balances);
	}
	
	public static void search() {
		render();
	}

	public static void searchCustomer() {
		String title = params.get("dep[title]", String.class);
		String kw = params.get("dep[kw]", String.class);

		render();
	}

	/**
	 * Render department editor for adding new department
	 */
	public static void delete(Long id) {
		Application.currentUserCan(2);

		Department dep = Department.findById(id);
		if (dep != null)
			dep.delete();

		index();
	}

	/**
	 * Handle POST form to update a department
	 * 
	 * @param id
	 *            , department to be updated
	 */
	public static void update(Long id) {
		Application.currentUserCan(2);

		String title = params.get("dep[title]", String.class);
		String kw = params.get("dep[kw]", String.class);
		Long old_id = params.get("dep[id]", Long.class);
		Department dep = Department.findById(id);
		if (id == old_id && dep != null) {
			dep.title = title;
			dep.keyword = kw;
			dep.save();
		}
		edit(id);
	}

	/**
	 * Handle POST form to create a department
	 */
	public static void create() {
		Application.currentUserCan(2);

		String title = params.get("dep[title]", String.class);
		String kw = params.get("dep[kw]", String.class);

		if (title.length() > 0 && kw.length() > 0) {
			Department dep = new Department(title, kw);
			dep.save();
		}

		index();
	}
	
	/*
	 * Device Change
	 * Satrt Date:10 feb, 2012
         * Added by: Aasim.Naveed@pk.wi-tribe.com
	 */
	public static void deviceChange(String AccountID,String MAC)
	{
            System.out.println("MAC Is"+MAC);
            MAC = MAC.trim();
            String DeviceSerial=null;
            String Model = null;
            String Make=null;
            String Source=null;
            try{
                AccountUtilities myUtil = new AccountUtilities();
                //myUtil.getMacDetails(MAC,session);
            }
            catch(Exception e)
            {
            }
            render(AccountID, DeviceSerial,MAC,Model,Make,Source);
        }

        public static void deviceAddorRemove()
        {
            String accno = params.get("accountNo", String.class);
            String macId = params.get("mac", String.class);
            String reason= params.get("Removalreason",String.class);
            String action= null;
            String AddOp = params.get("Add",String.class);
            String RemoveOp = params.get("Remove",String.class);
            if(AddOp!=null)
            {
                 action = "1";
            }
            else if(RemoveOp!=null)
            {
                 action = "2";
            }
            System.out.println("Param Is acc: "+accno);
            System.out.println("Param Is mac: "+macId);
            System.out.println("Param Is reason: "+reason);
            System.out.println("Action is "+action);
            FList Result = new FList();
            try{
                macId = macId.trim();
                AccountUtilities myUtil = new AccountUtilities();
                Result = myUtil.getMacDetails(macId, session);

                System.out.println("Returned FList Is"+Result.toString());
                Poid accountPoid = Poid.valueOf("0.0.0.1 /account " + accno);

                SparseArray ResArray = Result.get(FldResults.getInst());
                FList fldRes = ResArray.getAnyElement();

                FList fldResult = Result.get(FldResults.getInst()).elementAt(0);
                Poid devicePoid = fldRes.get(FldPoid.getInst());

                SparseArray CpeDetail = fldResult.get(WtbFldCpeDetails.getInst());
                FList CpeDetails = CpeDetail.getAnyElement();
                String Subtype = CpeDetails.get(WtbFldSubType.getInst()).toString();
                System.out.println("Subtype is:"+ CpeDetails.get(WtbFldSubType.getInst()));
                System.out.println("device Poid is:"+fldRes.get(FldPoid.getInst()));
                myUtil.addOrRemoveDevice(accountPoid,devicePoid,action,"702",reason,session,Subtype);
            }
            catch(Exception e)
            {
            }

            render();
        }
}
