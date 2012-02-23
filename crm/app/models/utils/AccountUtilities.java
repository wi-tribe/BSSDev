package models.utils;

import java.math.BigDecimal;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;
import java.util.Properties;

import play.mvc.Scope.Session;
import play.cache.Cache;

import com.portal.bas.PCachedContext;
import com.portal.bas.PControllerImpl;
import com.portal.bas.PModelHandle;
import com.portal.pcm.EBufException;
import com.portal.pcm.FList;
import com.portal.pcm.Poid;
import com.portal.pcm.PortalOp;
import com.portal.pcm.SparseArray;
import com.portal.pcm.fields.FldAacAccess;
import com.portal.pcm.fields.FldAccessCode1;
import com.portal.pcm.fields.FldAccountNo;
import com.portal.pcm.fields.FldAccountObj;
import com.portal.pcm.fields.FldAction;
import com.portal.pcm.fields.FldAdjusted;
import com.portal.pcm.fields.FldAmount;
import com.portal.pcm.fields.FldAmountIndicator;
import com.portal.pcm.fields.FldArBillinfoObj;
import com.portal.pcm.fields.FldArgs;
import com.portal.pcm.fields.FldBillNo;
import com.portal.pcm.fields.FldBillinfo;
import com.portal.pcm.fields.FldBrandObj;
import com.portal.pcm.fields.FldCompany;
import com.portal.pcm.fields.FldCreatedT;
import com.portal.pcm.fields.FldCurrentTotal;
import com.portal.pcm.fields.FldCycleEndDetails;
import com.portal.pcm.fields.FldDealInfo;
import com.portal.pcm.fields.FldDealObj;
import com.portal.pcm.fields.FldDeals;
import com.portal.pcm.fields.FldDescr;
import com.portal.pcm.fields.FldDeviceId;
import com.portal.pcm.fields.FldDeviceNum;
import com.portal.pcm.fields.FldDeviceObj;
import com.portal.pcm.fields.FldDiscounts;
import com.portal.pcm.fields.FldDueT;
import com.portal.pcm.fields.FldEndT;
import com.portal.pcm.fields.FldFirstCanon;
import com.portal.pcm.fields.FldFirstName;
import com.portal.pcm.fields.FldFlags;
import com.portal.pcm.fields.FldGroupObj;
import com.portal.pcm.fields.FldIncludeChildren;
import com.portal.pcm.fields.FldInvoiceObj;
import com.portal.pcm.fields.FldItemNo;
import com.portal.pcm.fields.FldLastCanon;
import com.portal.pcm.fields.FldLastName;
import com.portal.pcm.fields.FldLogin;
import com.portal.pcm.fields.FldManufacturer;
import com.portal.pcm.fields.FldMembers;
import com.portal.pcm.fields.FldModT;
import com.portal.pcm.fields.FldModel;
import com.portal.pcm.fields.FldName;
import com.portal.pcm.fields.FldNameinfo;
import com.portal.pcm.fields.FldObject;
import com.portal.pcm.fields.FldOpCorrelationId;
import com.portal.pcm.fields.FldOpenbillDue;
import com.portal.pcm.fields.FldPackageId;
import com.portal.pcm.fields.FldParent;
import com.portal.pcm.fields.FldPasswd;
import com.portal.pcm.fields.FldPayinfoObj;
import com.portal.pcm.fields.FldPendingbillDue;
import com.portal.pcm.fields.FldPermitted;
import com.portal.pcm.fields.FldPhone;
import com.portal.pcm.fields.FldPhones;
import com.portal.pcm.fields.FldPoid;
import com.portal.pcm.fields.FldPoidType;
import com.portal.pcm.fields.FldProducts;
import com.portal.pcm.fields.FldProgramName;
import com.portal.pcm.fields.FldPurchaseEndDetails;
import com.portal.pcm.fields.FldReadAccess;
import com.portal.pcm.fields.FldReasonCode;
import com.portal.pcm.fields.FldRecvd;
import com.portal.pcm.fields.FldResults;
import com.portal.pcm.fields.FldServiceId;
import com.portal.pcm.fields.FldServiceObj;
import com.portal.pcm.fields.FldServices;
import com.portal.pcm.fields.FldSource;
import com.portal.pcm.fields.FldStartT;
import com.portal.pcm.fields.FldStateId;
import com.portal.pcm.fields.FldStatus;
import com.portal.pcm.fields.FldStatusMsg;
import com.portal.pcm.fields.FldSubtype;
import com.portal.pcm.fields.FldTemplate;
import com.portal.pcm.fields.FldTitle;
import com.portal.pcm.fields.FldTotalDue;
import com.portal.pcm.fields.FldTransfered;
import com.portal.pcm.fields.FldUnappliedAmount;
import com.portal.pcm.fields.FldUsageEndDetails;
import com.portal.pcm.fields.FldWriteAccess;
import com.wtb.flds.WtbFldCpeDetails;
import com.wtb.flds.WtbFldMacAddressWan;
import com.wtb.flds.WtbFldProfileId;
import com.wtb.flds.WtbFldSalesId;
import com.wtb.flds.WtbFldSubType;
import com.wtb.flds.WtbFldSvcInfo;
import com.portal.app.ccare.comp.LoggedInUsrInfoBean;
import com.portal.pcm.fields.FldAacSource;

public class AccountUtilities extends PControllerImpl {

    /** Creates a new instance of MyAcctUtility */
    public AccountUtilities() throws RemoteException {
        mInvoices = new ArrayList();
        mStartTime = null;
        mEndTime = null;
        macctPOID = null;
        mbillInfoPOID = null;
        mIncludechildren = 0;
        mCNIC = "";
        isSingle = false;
        mProducts = new ArrayList();
        mDiscounts = new ArrayList();
        mAccountCreationDate = null;
        salespersonID=null;
        salespersonnelName=null;
    }

    public void setCNIC(String sCNIC) {
        mCNIC = sCNIC;
    }

    public String getCNIC() {
        return mCNIC;
    }

    public void setBillInfoPoid(Poid obillpoid) {
        mbillInfoPOID = obillpoid;
    }

    public Poid getBillInfoPoid() {
        return mbillInfoPOID;
    }

    public Poid getAcctPoid() {
        return macctPOID;
    }

    public void setAcctPoid(Poid oPOID) {
        macctPOID = oPOID;
    }

    public void setStartTime(Date startDate) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(startDate);
        mStartTime = cal.getTime();
    }

    public void setProperties(Properties props) {
        mProperties = props;
    }

    public void setEndTime(Date endDate) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(endDate);
        mEndTime = cal.getTime();
    }

    public FList getDuplicateAccountSearch() throws RemoteException {
        PCachedContext ctx = null;
        FList FListIn = new FList();
        FList FListOut = new FList();
        Poid searchPoid = new Poid(1, -1, "/search");
        SparseArray resArray = new SparseArray();
        SparseArray argsArray = new SparseArray();
        FList resFlist = new FList();
        FList argsList = new FList();
        Poid rsPoid = new Poid(1, -1, "/search");
        resFlist.set(FldPoid.getInst(), rsPoid);
        resFlist.set(FldGroupObj.getInst(), rsPoid);
        resArray.add(resFlist);
        argsList.set(FldAccessCode1.getInst(), mCNIC);
        argsArray.add(1, argsList);
        String searchTmpt = "select X from /account where upper( F1 ) = upper( V1 ) ";
        FListIn.set(FldPoid.getInst(), searchPoid);
        FListIn.set(FldFlags.getInst(), 256);
        FListIn.set(FldTemplate.getInst(), searchTmpt);
        FListIn.set(FldResults.getInst(), resArray);
        FListIn.set(FldArgs.getInst(), argsArray);

        try {
            ctx = getContext();
            FListOut = ctx.opcode(PortalOp.SEARCH, FListIn);
        } catch (EBufException e) {
            throw createClientException(e);
        } finally {
            releaseContext(ctx);
        }
        return FListOut;
    }

    /**

     *  following method called by checkDuplicate() method.

     *  this function verifies with the existing /account storable class for the CNIC mentioned.

     *  this function verifies either this account is the parent account or not.

     */
    public boolean performParentCheck() {
        boolean isParent = false;
        try {
            FList flOut = getDuplicateAccountSearch();
            if (flOut.hasField(FldResults.getInst())) {
                SparseArray parentArray = flOut.get(FldResults.getInst());
                for (int i = 0; i < parentArray.size(); i++) {
                    FList res = parentArray.elementAt(i);
                    res.dump();
                    
                    Poid parentPoid = res.get(FldGroupObj.getInst());
                    if (parentPoid.getId() > 0) {
                        isParent = true;
                    }
                }
            }
        } catch (Exception e) {
            e.getMessage();
        }
        return isParent;
    }

    /**

     *  This method checks if the is there any Child account available with the same CNIC

     *

     */
    public boolean performChildCheck() throws RemoteException {
        isSingle = false;
        boolean isChild = false;
        PCachedContext ctx = null;
        try {
            ctx = getContext();
            FList fcOut = getDuplicateAccountSearch();
            if (fcOut.hasField(FldResults.getInst())) {
                isSingle = true;
                SparseArray childArray = fcOut.get(FldResults.getInst());
                for (int i = 0; i < childArray.size(); i++) {
                    isSingle = true;
                    FList FListIn = new FList();
                    FList FListOut = new FList();
                    Poid searchPoid = new Poid(1, -1, "/search");
                    SparseArray resArray = new SparseArray();
                    SparseArray argsArray = new SparseArray();
                    SparseArray membArray = new SparseArray();
                    SparseArray outArray = new SparseArray();
                    FList resFlist = new FList();
                    FList argsList = new FList();
                    FList membList = new FList();
                    resFlist.set(FldPoid.getInst(), searchPoid);
                    resFlist.set(FldParent.getInst(), searchPoid);
                    resArray.add(resFlist);
                    String searchTmpt = "select X from /group/billing where upper( F1 ) = upper( V1 ) ";
                    FListIn.set(FldPoid.getInst(), searchPoid);
                    FListIn.set(FldFlags.getInst(), 256);
                    FListIn.set(FldTemplate.getInst(), searchTmpt);
                    FListIn.set(FldResults.getInst(), resArray);
                    FList res = childArray.elementAt(i);
                    Poid actPoid = res.get(FldPoid.getInst());
                    membList.set(FldObject.getInst(), actPoid);
                    membArray.add(membList);
                    argsList.set(FldMembers.getInst(), membArray);
                    argsArray.add(i + 1, argsList);
                    FListIn.set(FldArgs.getInst(), argsArray);
                    FListOut = ctx.opcode(PortalOp.SEARCH, FListIn);
                    if (FListOut.hasField(FldResults.getInst())) {
                        isChild = true;
                    }
                }
            }
        } catch (Exception che) {
            che.getMessage();
        } finally {
            releaseContext(ctx);
        }
        return isChild;
    }

    
    /**

     *  By default PCM library will not return the service exprity date for the each product/service

     *  So, this funtion was written so that customer can see the service expiry date in My Services dashboard.

     *

     */
    public void getRegdServices() throws RemoteException {
        List resList = new ArrayList();
        FList inList = new FList();
        PCachedContext ctx = null;
        try {
            ctx = getContext();
            inList.set(FldPoid.getInst(), macctPOID);
            inList.set(FldFlags.getInst(), 1);
            FList out = ctx.opcode(81, inList);

            if (!out.hasField(FldServices.getInst()) && !out.hasField(FldDeals.getInst()) && !out.hasField(FldProducts.getInst())) {
                throw new RemoteException("error.products");
            }

            if (out.hasField(FldServices.getInst())) {
                SparseArray sp = out.get(FldServices.getInst());

                if (sp == null) {
                    throw new RemoteException("error.products");
                }

                Enumeration results = sp.getValueEnumerator();
                do {
                    if (!results.hasMoreElements()) {
                        break;
                    }
                    FList services = (FList) results.nextElement();
                    Poid poid = services.get(FldPoid.getInst());
                    if (poid == null) {
                        throw new RemoteException("error.products");
                    }

                    String service = poid.toString();
                    int start = service.indexOf('/');
                    int end = service.indexOf(' ', start);
                    if (services.hasField(FldDeals.getInst())) {
                        processDealsFlist(services, service.substring(start, end));
                    }
                } while (true);
            }

            if (out.hasField(FldDeals.getInst())) {
                processDealsFlist(out, null);
            }

            if (out.hasField(FldProducts.getInst())) {
                processProductsFlist(out, null, null);
            }

            if (out.hasField(FldDiscounts.getInst())) {
                processDiscountsFlist(out, null, null);
            }
        } catch (Exception e) {
            
        } finally {
            releaseContext(ctx);
        }
    }

    /**

     *  This method is called from getRegdServices function to process the list of deals associated to the account

     *  This method returns the list deals associatedt o the account in the form of ArrayList.

     */
    private void processDealsFlist(FList out, String serviceStr)
            throws RemoteException {

        try {
            SparseArray dealsArray = out.get(FldDeals.getInst());

            if (dealsArray == null) {
                throw new RemoteException("error.products");
            }

            Enumeration enum1 = dealsArray.getValueEnumerator();
            do {
                if (!enum1.hasMoreElements()) {
                    break;
                }
                FList deals = (FList) enum1.nextElement();
                String deal = deals.get(FldName.getInst());
                if (deals.hasField(FldProducts.getInst())) {
                    processProductsFlist(deals, serviceStr, deal);
                }

                if (deals.hasField(FldDiscounts.getInst())) {
                    processDiscountsFlist(deals, serviceStr, deal);
                }

            } while (true);
        } catch (EBufException e) {
            
        }
    }

    /**

     *  This method is called from getRegdServices function to process the list of discounted products associated to the account

     *  This method returns the list discounted products associatedt o the account in the form of ArrayList.

     */
    private void processDiscountsFlist(FList out, String serviceStr, String deal)
            throws RemoteException {

        try {

            SparseArray discountsArray = out.get(FldDiscounts.getInst());

            if (discountsArray == null) {
                throw new RemoteException("error.NoDiscounts");
            }

            for (Enumeration enum2 = discountsArray.getValueEnumerator(); enum2.hasMoreElements();) {

                FList discounts = (FList) enum2.nextElement();

                if (discounts == null) {
                    throw new RemoteException("error.NoDiscounts");
                }

                mDiscounts.add(discounts);

            }
        } catch (EBufException e) {
            
        }
    }

    /**

     *  This method is called from getRegdServices function to process the list of products associated to the account

     *  This method returns the list products associatedt o the account in the form of ArrayList.

     */
    private void processProductsFlist(FList out, String serviceStr, String deal)
            throws RemoteException {
        try {
            SparseArray productsArray = out.get(FldProducts.getInst());
            if (productsArray == null) {
                throw new RemoteException("error.products");
            }
            for (Enumeration enum2 = productsArray.getValueEnumerator(); enum2.hasMoreElements();) {
                FList products = (FList) enum2.nextElement();
                products.set(FldServiceId.getInst(), serviceStr);
                products.set(FldStatusMsg.getInst(), getProductStatusAsString((products.get(FldStatus.getInst())).intValue()));
                mProducts.add(products);
            }
        } catch (EBufException e) {
            
        }
    }

    /**

     *  Folloing funciton is called from getRegdServices to get the product status based ont he code passed to it.

     */
    public String getProductStatusAsString(int sValue) {
        String pStatus = "";
        switch (sValue) {
            case 0: // '\0'
                pStatus = "product.status.notset";
                break;
            case 1: // '\001'
                pStatus = "product.status.active";
                break;
            case 2: // '\002'
                pStatus = "product.status.inactive";
                break;
            case 3: // '\003'
                pStatus = "product.status.cancelled";
                break;
            default:
                pStatus = "product.status.unknown";
                break;
        }
        return pStatus;
    }

    /**

     *  Following function is called from my profile and account summary page to display the customer registed products details.

     */
    public List getRegdProducts() throws RemoteException {
        List resList = new ArrayList();
        FList inList = new FList();
        PCachedContext ctx = null;
        try {
            ctx = getContext();
            inList.set(FldPoid.getInst(), macctPOID);
            inList.set(FldFlags.getInst(), 1);
            FList outputFlist = ctx.opcode(81, inList);
            if (outputFlist.hasField(FldServices.getInst())) {
                SparseArray sp = outputFlist.get(FldServices.getInst());
                Enumeration results = sp.getValueEnumerator();
                do {
                    if (!results.hasMoreElements()) {
                        break;
                    }
                    FList services = (FList) results.nextElement();
                    Poid poid = services.get(FldPoid.getInst());
                    if (poid == null) {
                        
                        throw new RemoteException("error.pay");
                    }
                    resList.add(services);
                } while (true);
            }
        } catch (Exception e) {
            
        } finally {
            releaseContext(ctx);
        }
        return resList;
    }

    public FList getDefaultBillingAddress() throws RemoteException {
        PCachedContext ctx = null;
        try {
            ctx = getContext();
            FList flInput = new FList();
            flInput.set(FldPoid.getInst(), new Poid(ctx.getCurrentDB(), -1L, "/search"));
            flInput.set(FldFlags.getInst(), 256);
            flInput.set(FldTemplate.getInst(), "select X from /billinfo where F1 = V1 ");
            flInput.set(FldResults.getInst());
            FList args = new FList();
            args.set(FldAccountObj.getInst(), macctPOID);
            flInput.setElement(FldArgs.getInst(), 1, args);
            FList flResult = ctx.opcode(7, flInput);
            if (flResult.hasField(FldResults.getInst()) == false) {
                return null;
            }
            SparseArray results = flResult.get(FldResults.getInst());
            FList flBillInfo = results.elementAt(0);
            Poid poidPayinfoObj = flBillInfo.get(FldPayinfoObj.getInst());
            flInput = null;
            flResult = new FList();
            flInput = new FList();
            flInput.set(FldPoid.getInst(), poidPayinfoObj);
            flResult = ctx.opcode(3, flInput);
            return flResult;
        } catch (Exception e) {
            return null;
        } finally {
            releaseContext(ctx);
        }
    }

    /**

     *  This function is to get  all the billing address available in /payinfo table for the selected account.

     */
    public List getActBillingAddress() throws RemoteException {



        List resList = new ArrayList();

        FList outlist = new FList();

        Poid searchPoid = new Poid(1, -1, "/search");

        SparseArray argsArray = new SparseArray();

        PCachedContext ctx = null;

        try {

            ctx = getContext();

            String searchTmpt = "select X from /payinfo where F1 = V1";

            FList FListIn = new FList();

            FListIn.set(FldPoid.getInst(), searchPoid);

            FListIn.set(FldFlags.getInst(), 256);

            FList argsList = new FList();

            argsList.set(FldAccountObj.getInst(), macctPOID);

            argsArray.add(1, argsList);

            FListIn.set(FldArgs.getInst(), argsArray);

            FListIn.set(FldTemplate.getInst(), searchTmpt);

            FListIn.setElement(FldResults.getInst(), -1);

            outlist = ctx.opcode(PortalOp.SEARCH, FListIn);

            if (!outlist.hasField(FldResults.getInst())) {

            	return resList;

            }

            SparseArray resultsArray = outlist.get(FldResults.getInst());

            resList = new ArrayList();

            if (resultsArray == null) {
                return resList;
            }

            Enumeration enumVals = resultsArray.getValueEnumerator();

            do {

                if (!enumVals.hasMoreElements()) {
                    break;
                }

                FList r1 = (FList) enumVals.nextElement();

                Poid poid = r1.get(FldPoid.getInst());

                if (poid == null) {

                    throw new RemoteException("error.pay");

                }

                resList.add(r1);



            } while (true);

        } catch (Exception e) {
        } finally {

            releaseContext(ctx);
        }

        return resList;

    }

    

    /**

     *  This method is called to show the payments made by the customer for the selected duration.

     */
    public List getPayHistory() throws RemoteException {

        List resList = null;

        PCachedContext ctx = null;

        try {

            ctx = getContext();

            FList in = new FList();

            FList outl = new FList();

            in.set(FldPoid.getInst(), macctPOID);

            in.set(FldStatus.getInst(), 6);

            in.set(FldAmountIndicator.getInst(), 0);

            in.set(FldIncludeChildren.getInst(), 1);

            in.set(FldStartT.getInst(), mStartTime);

            in.set(FldEndT.getInst(), mEndTime);

            in.set(FldPoidType.getInst(), "/item/payment,/item/payment/reversal");

            in.set(FldArBillinfoObj.getInst(), mbillInfoPOID);

            outl = ctx.opcode(4506, in);

            if (!outl.hasField(FldResults.getInst())) {

                

                return resList;

            }

            SparseArray resultsArray = outl.get(FldResults.getInst());

            resList = new ArrayList();

            if (resultsArray == null) {
                return resList;
            }

            Enumeration enumVals = resultsArray.getValueEnumerator();

            do {

                if (!enumVals.hasMoreElements()) {
                    break;
                }

                FList r1 = (FList) enumVals.nextElement();

                Poid poid = r1.get(FldPoid.getInst());

                if (poid == null) {

                    

                    throw new RemoteException("error.pay");

                }

                resList.add(r1);



            } while (true);

        } catch (Exception e) {

            

        } finally {
            releaseContext(ctx);
        }

        return resList;

    }

    public List getVoIPNumbers(String sCity, int ServiceType) throws RemoteException {

        List resList = null; //new ArrayList();

        FList inList = new FList();

        String CityCode = "";

        PCachedContext ctx = null;

        try {

            ctx = getContext();

            if (sCity.equalsIgnoreCase("Karachi")) {

                CityCode = "KAR";

            } else if (sCity.equalsIgnoreCase("Lahore")) {

                CityCode = "LA";

            } else if (sCity.equalsIgnoreCase("Islamabad")) {

                CityCode = "LA";

                return null;

            } else if (sCity.equalsIgnoreCase("Rawalpindi")) {

                CityCode = "RAW";

            }

            if (ServiceType == 1) {

                CityCode = CityCode + "_" + "Prepaid";

            } else {

                CityCode = CityCode + "_" + "Postpaid";

            }

            if (CityCode.equals("")) {

                FList flInput = new FList();

                FList flResults = new FList();

                flResults.set(FldPoid.getInst());

                flResults.set(FldDeviceId.getInst());

                flInput.set(FldPoid.getInst(), new Poid(ctx.getCurrentDB(), -1L, "/search"));

                flInput.set(FldFlags.getInst(), 256);

                flInput.set(FldTemplate.getInst(), "select X from /device/num where F1 = V1 and F2 = V2 ");

                flInput.setElement(FldResults.getInst(), 0, flResults);

                FList args = new FList();

                args.set(FldStateId.getInst(), 1);

                flInput.setElement(FldArgs.getInst(), 1, args);

                args = new FList();

                FList flDeviceNum = new FList();

                flDeviceNum.set(FldPermitted.getInst(), CityCode);

                args.set(FldDeviceNum.getInst(), flDeviceNum);

                flInput.setElement(FldArgs.getInst(), 2, args);

                ///flInput.dump();



                FList flResult = ctx.opcode(7, flInput);

                //flResult.dump();

                if (flResult.hasField(FldResults.getInst()) == false) {

                    
                    return null;

                } else {

                    SparseArray results = flResult.get(FldResults.getInst());

                    if (results == null) {

                        return null;

                    }

                    Enumeration enumVals = results.getValueEnumerator();

                    resList = new ArrayList();

                    do {

                        if (!enumVals.hasMoreElements()) {
                            break;
                        }

                        FList r1 = (FList) enumVals.nextElement();

                        resList.add(r1);

                    } while (true);

                }

            }



        } catch (Exception e) {
        } finally {
            releaseContext(ctx);
        }
        return resList;

    }

    /**

     *  This method returns the additions information to be displayed in Account summary page. in My Account applicaiton.

     */
    public FList getSummaryData() throws RemoteException {
        PCachedContext ctx = null;
        FList out = null;
        try {
            ctx = getContext();
            out = new FList();
            FList in = new FList();
            in.set(FldPoid.getInst(), macctPOID);
            in.dump();
            out = ctx.opcode(10001, in);
        } catch (Exception e) {
            
        } finally {
            releaseContext(ctx);
        }
        return out;
    }

    /**

     * returns the Purchased products assocated to the account

     */
    public List getProducts() throws RemoteException {
        return mProducts;
    }

    /**
     * returns the discounted products associated to the account.
     */
    public List getDiscounts() throws RemoteException {
        return mDiscounts;
    }

    public void update(int i, Object obj) {
    }

    public String getAcctPwd() throws RemoteException {
        PCachedContext ctx = null;
        String pwd = null;
        try {
            ctx = getContext();
            FList input = new FList();
            FList outflst = new FList();
            input.set(FldPoid.getInst(), new Poid(ctx.getCurrentDB(), -1L, "/search"));
            input.set(FldFlags.getInst(), 256);
            input.set(FldTemplate.getInst(), "select X from /service/ip where F1 = V1 ");
            SparseArray resArray = new SparseArray();

            FList resFlist = new FList();
            SparseArray argsArray = new SparseArray();
            FList args = new FList();
            args.set(FldAccountObj.getInst(), macctPOID);
            argsArray.add(1, args);
            input.set(FldArgs.getInst(), argsArray);
            input.setElement(FldResults.getInst(), -1);

            
            outflst = ctx.opcode(PortalOp.SEARCH, input);

            if (outflst.hasField(FldResults.getInst())) {
                SparseArray arResults = outflst.get(FldResults.getInst());
                pwd = arResults.getAnyElement().get(FldPasswd.getInst());
                return (pwd);
            }
        } catch (Exception e) {

            
        } finally {
            releaseContext(ctx);
        }
        return pwd;
    }

    public void manageChangeBillAddress(PModelHandle mh, Poid billInfoPoid) throws RemoteException {
        PModelHandle mH = mh;
        PCachedContext ctx = null;

        try {
            ctx = getContext();
            FList flFinal = (FList) ctx.lookupModel(mH);
            flFinal.set(FldPoid.getInst(), macctPOID);
            flFinal.remove(FldBillinfo.getInst());
            flFinal.set(FldProgramName.getInst(), "Webkit");
            FList result = ctx.opcode(77, flFinal);

        } catch (EBufException e) {
           
        } finally {
            releaseContext(ctx);
        }
    }

    /////////////
    public void getProfileNameNMac(Session session) {
        String[] temp = null;
        try {
            FList in = new FList();
            in.set(FldPoid.getInst(), new Poid(1, -1L, "/search"));
            in.set(FldFlags.getInst(), 256);
            in.set(FldTemplate.getInst(), "select X from /profile/ipservice_info where F1 = V1 and poid_type = '/profile/ipservice_info' ");
            FList serviceFlist = new FList();
            serviceFlist.set(FldServiceObj.getInst(), this.mServicePoid);
            SparseArray argArray = new SparseArray();
            argArray.add(1, serviceFlist);
            in.set(FldArgs.getInst(), argArray);

            FList resultFlist = new FList();
            SparseArray resultArray = new SparseArray();
            resultArray.add(1, resultFlist);
            in.set(FldResults.getInst(), resultArray);

            FList output = ExecuteOpcode.execute(7, in, session);

            FList profileResult = output.get(FldResults.getInst()).elementAt(0);
            FList serviceFld = profileResult.get(WtbFldSvcInfo.getInst()).elementAt(0);
            this.mAccountCreationDate = profileResult.get(FldCreatedT.getInst());

            Poid deviceApnCpePoid = serviceFld.get(FldDeviceObj.getInst());
            String deviceProfileStr =  serviceFld.get(WtbFldProfileId.getInst());

            FList macFlist = new FList();
            macFlist.set(FldPoid.getInst(), deviceApnCpePoid);

            FList CpeFld = ExecuteOpcode.execute(3, macFlist, session);

            System.out.println("getProfileNameMAC inFLIST "+macFlist.toString());
            System.out.println("getProfileNameMAC outFLIST "+CpeFld.toString());
            
            FList cpeDetailsFld = CpeFld.get(WtbFldCpeDetails.getInst()).elementAt(0);

            temp = deviceProfileStr.split("_");
            this.mProfileName = (temp[2] + " Kbps");
            
            this.mMacAddress = cpeDetailsFld.get(WtbFldMacAddressWan.getInst());
            this.cpeType = cpeDetailsFld.get(WtbFldSubType.getInst());
            this.serialNo = CpeFld.get(FldDeviceId.getInst());
            this.salespersonID=cpeDetailsFld.get(WtbFldSalesId.getInst());

            // addding details
            this.DevManufacturer=CpeFld.get(FldManufacturer.getInst());
            this.DevModel = CpeFld.get(FldModel.getInst());
            this.Source = CpeFld.get(FldSource.getInst());

        } catch (EBufException e) {
           
        } catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
            
        }
    }

    public void getBalanceSummaryForBillInfo(Poid billInfoPoid, int nIncludeChildren, Session session)
            throws RemoteException {
        try {
            FList flInput = new FList();
            flInput.set(FldPoid.getInst(), billInfoPoid);
            flInput.set(FldIncludeChildren.getInst(), nIncludeChildren);
            
            FList flOutBal = ExecuteOpcode.execute(4507, flInput, session);
            flOutBal.dump();
            BigDecimal bdAllBillDue = flOutBal.get(FldOpenbillDue.getInst());
            BigDecimal bdPending = flOutBal.get(FldPendingbillDue.getInst());
            BigDecimal bdUnapplied = flOutBal.get(FldUnappliedAmount.getInst());
            
            BigDecimal bdDueNow = bdAllBillDue.add(bdUnapplied);
            BigDecimal bdTotal = bdDueNow.add(bdPending);

            dueNow = bdDueNow;
            unbilledAmount = bdPending;
            totalBalance = bdTotal;
            unappliedAmount = bdUnapplied;
            allBillDueAmount = bdAllBillDue;
        } catch (EBufException e) {
        } finally {
            
        }
    }

    public static String purchaseDeal(Poid accountPoid, Poid servicePoid, String dealPoid, Session session) throws RemoteException, EBufException {
        try {
       
            FList ReadDealObj = new FList();
            SparseArray ReadDealArray = new SparseArray();
            FList ReadDealFl = new FList();
            FList PurcahseDeal = new FList();
            ReadDealObj.set(FldPoid.getInst(), Poid.valueOf("0.0.0.1 /deal " + dealPoid));
            FList ReadDealObjOpt = ExecuteOpcode.execute(3, ReadDealObj,session);
            
            ReadDealObjOpt.remove(FldCreatedT.getInst());
            ReadDealObjOpt.remove(FldModT.getInst());
            ReadDealObjOpt.remove(FldCreatedT.getInst());
            ReadDealObjOpt.remove(FldPermitted.getInst());
            ReadDealObjOpt.remove(FldWriteAccess.getInst());
            ReadDealObjOpt.remove(FldReadAccess.getInst());
            ReadDealObjOpt.remove(FldOpCorrelationId.getInst());
            ReadDealObjOpt.remove(FldAccountObj.getInst());
            ReadDealArray = ReadDealObjOpt.get(FldProducts.getInst());
            ReadDealFl = ReadDealArray.elementAt(0);
            ReadDealFl.remove(FldUsageEndDetails.getInst());
            ReadDealFl.set(FldUsageEndDetails.getInst(), 0);
            ReadDealFl.remove(FldCycleEndDetails.getInst());
            ReadDealFl.set(FldCycleEndDetails.getInst(), 0);
            ReadDealFl.remove(FldPurchaseEndDetails.getInst());
            ReadDealFl.set(FldPurchaseEndDetails.getInst(), 0);
            PurcahseDeal.set(FldDealInfo.getInst(), ReadDealObjOpt);
            PurcahseDeal.set(FldPoid.getInst(), accountPoid);
            PurcahseDeal.set(FldProgramName.getInst(), ReadDealObjOpt.get(FldDescr.getInst()));
            PurcahseDeal.set(FldServiceObj.getInst(), servicePoid);

            ExecuteOpcode.execute(108, PurcahseDeal,session);
        } catch (EBufException ex) {
            
            throw ex;
        } catch (RemoteException e) {
			// TODO: handle exception
		}
        return "";
    }
    
    public static boolean addOrRemoveDevice(Poid accountPoid, Poid devicePoid, String action, String salesID,String Source, String resonCode, Session session, String subType) throws RemoteException, EBufException {
        try {
            FList inputFlist = new FList();
            inputFlist.set(FldPoid.getInst(), accountPoid);
            inputFlist.set(FldDeviceObj.getInst(), devicePoid);
            inputFlist.set(FldProgramName.getInst(), "REPLACE");
            inputFlist.set(WtbFldSubType.getInst(), subType);
            inputFlist.set(WtbFldSalesId.getInst(), salesID);
            inputFlist.set(FldSource.getInst(), Source);
            inputFlist.set(FldReasonCode.getInst(), resonCode);
            inputFlist.set(FldAction.getInst(), action);
            inputFlist.set(FldFlags.getInst(), 0);
            inputFlist.dump();
            System.out.println("addOrRemove inFLIST \n"+inputFlist);
            ExecuteOpcode.execute(100058,inputFlist,session);
         }
        catch (RemoteException e) {
			return false;
		}
        catch(Exception Ex)
        {
            return false;
        }
        return true;
    }
    
    public static List<Accounts> searchCustomer(String accountNo, String fName, String lName, String serviceID, String mac, String cnic, String mobileNo, Session session) throws RemoteException, EBufException {
    	List<Accounts> accounts = new ArrayList<Accounts>();
    	
    	try {
        	int i = 1;
        	int j = 2;
        	
        	StringBuffer queryString = new StringBuffer("select X from /account 1 ");
        	
        	StringBuffer whereClause = new StringBuffer("where ");
        	
        	FList input = new FList();
            input.set(FldPoid.getInst(), new Poid(1L, 0L, "/search"));
            input.set(FldFlags.getInst(), 256);
            
            FList flResults = new FList();
            flResults.set(FldPoid.getInst());
            flResults.set(FldAccountNo.getInst());
            flResults.set(FldBrandObj.getInst());
            
            FList resultFlist = new FList();
            resultFlist.set(FldLastName.getInst());
            resultFlist.set(FldFirstName.getInst());
            resultFlist.set(FldCompany.getInst());
            
            SparseArray resultArray = new SparseArray();
            resultArray.add(1, resultFlist);
            flResults.set(FldNameinfo.getInst(), resultArray);

            input.setElement(FldResults.getInst(),0 ,flResults);
            
            SparseArray args = new SparseArray();
            FList arguments = null;
            
            if(accountNo != null && !accountNo.equals("")) {
        		whereClause.append(" lower( 1.F" + i + " )  like lower( V" + i + " )");
            		
            	arguments = new FList();
            	arguments.set(FldAccountNo.getInst(), "%"+accountNo);
            	args.add(i,arguments);
            	i++;
            }
            
			if(fName != null && !fName.equals("")) {
				if(i==1)
            		whereClause.append(" lower( 1.F" + i + " )  like lower( V" + i + " ) ");
            	else
            		whereClause.append(" and lower( 1.F" + i + " )  like lower( V" + i + " ) ");
            	
				arguments = new FList();
            	FList child = new FList();
            	child.set(FldFirstCanon.getInst(),fName);
            	arguments.setElement(FldNameinfo.getInst(),0,child);
            	
            	args.add(i,arguments);
            	i++;
            }
			
			if(lName != null && !lName.equals("")) {
				if(i==1)
            		whereClause.append(" lower( 1.F" + i + " )  like lower( V" + i + " ) ");
            	else
            		whereClause.append(" and lower( 1.F" + i + " )  like lower( V" + i + " ) ");
            	arguments = new FList();
            	FList child = new FList();
            	child.set(FldLastCanon.getInst(),lName);
            	arguments.setElement(FldNameinfo.getInst(),0,child);
            	
            	args.add(i,arguments);
            	i++;
			}
			
			
			if(cnic != null && !cnic.equals("")) {
				if(i==1)
            		whereClause.append(" lower( 1.F" + i + " )  like lower( V" + i + " ) ");
            	else
            		whereClause.append(" and lower( 1.F" + i + " )  like lower( V" + i + " ) ");
            	arguments = new FList();
            	arguments.set(FldAccessCode1.getInst(), cnic);
            	args.add(i,arguments);
            	i++;
			}
			
			if(mobileNo != null && !mobileNo.equals("")) {
				if(i==1)
            		whereClause.append(" lower( 1.F" + i + " )  like lower( V" + i + " ) ");
            	else
            		whereClause.append(" and lower( 1.F" + i + " )  like lower( V" + i + " ) ");
            	arguments = new FList();
            	FList child1 = new FList();
            	
            	FList child = new FList();
            	child.set(FldPhone.getInst(),mobileNo);
            	
            	child1.setElement(FldPhones.getInst(),0,child);
            	
            	arguments.setElement(FldNameinfo.getInst(),0,child1);
            	
            	args.add(i,arguments);
            	i++;
			}
            
			if(serviceID != null && !serviceID.equals("")) {
				queryString.append(", /service 2 ");
				j = 3;
				if(i==1)
					whereClause.append(" lower( 2.F" + i + " )  like lower( V" + i + " ) and 1.F" + (i + 1) + " = 2.F" + (i + 2) + " ");
				else
					whereClause.append(" and lower( 2.F" + i + " )  like lower( V" + i + " ) and 1.F" + (i + 1) + " = 2.F" + (i + 2) + " ");
            	
				arguments = new FList();
            	arguments.set(FldLogin.getInst(), serviceID);
            	args.add(i,arguments);
            	i ++;
            	arguments = new FList();
            	arguments.set(FldPoid.getInst());
            	args.add(i,arguments);
            	i ++;
            	arguments = new FList();
            	arguments.set(FldAccountObj.getInst());
            	args.add(i,arguments);
            	i ++;
			}
			
			if(mac != null && !mac.equals("")) {
				queryString.append(", /device/apn/cpe " + j + " ");
				if(i==1)
					whereClause.append(" lower( " + j + ".F" + i + " )  like lower( V" + i + " ) and 1.F" + (i + 1) + " = " + j + ".F" + (i + 2) + " ");
				else
					whereClause.append(" and lower( " + j + ".F" + i + " )  like lower( V" + i + " ) and 1.F" + (i + 1) + " = " + j + ".F" + (i + 2) + " ");
				FList child = new FList();
				child.set(WtbFldMacAddressWan.getInst(),mac);
				
				arguments = new FList();
            	arguments.setElement(WtbFldCpeDetails.getInst(), 0, child);
            	args.add(i,arguments);
            	i ++;
            	arguments = new FList();
            	arguments.set(FldPoid.getInst());
            	args.add(i,arguments);
            	i ++;
            	child = new FList();
            	child.set(FldAccountObj.getInst());
            	arguments = new FList();
            	arguments.setElement(FldServices.getInst(),0,child);
            	args.add(i,arguments);
            	
            	
			}
			
			input.set(FldArgs.getInst(), args);
            input.set(FldTemplate.getInst(), queryString.toString() + " " + whereClause.toString());

            FList output = ExecuteOpcode.execute(7, input,session);
            
            if (output != null && output.hasField(FldResults.getInst()) == true) {
                
                SparseArray results = output.get(FldResults.getInst());
                if (results == null) {
                    return null;
                }
                Enumeration enumVals = results.getValueEnumerator();

                do {
                    if (!enumVals.hasMoreElements()) {
                        break;
                    }

                    FList r1 = (FList) enumVals.nextElement();
                    FList result = r1.get(FldNameinfo.getInst()).getAnyElement();
                    accounts.add(new Accounts(r1.get(FldAccountNo.getInst()), result.get(FldFirstName.getInst()), result.get(FldLastName.getInst()), result.get(FldCompany.getInst()), r1.get(FldPoid.getInst())));
                } while (true);
            }
        } catch (EBufException ex) {
            
            throw ex;
        } 
        catch (RemoteException e) {
			// TODO: handle exception
		}
        return accounts;
    }

    public String cancelDeal(Poid accountPoid, Poid servicePoid, String dealPoid) throws RemoteException, EBufException {
        PCachedContext ctx = null;
        try {
            ctx = getContext();

            FList input = new FList();
            input.set(FldPoid.getInst(), new Poid(1L, 0L, "/search"));
            input.set(FldFlags.getInst(), 256);
            input.set(FldTemplate.getInst(), "select x from /purchased_product where F1 = V1 and F2 = V2 and status = 1 ");
            input.set(FldResults.getInst());
            SparseArray args = new SparseArray();

            FList device_sub = new FList();
            device_sub.set(FldAccountObj.getInst(), accountPoid);

            FList device_elem = new FList();
            device_elem.set(FldDealObj.getInst(), Poid.valueOf("0.0.0.1 /deal " + dealPoid));
            args.add(1, device_sub);
            args.add(2, device_elem);
            input.set(FldArgs.getInst(), args);
            
            FList output = ctx.opcode(7, input);
            SparseArray results = output.get(FldResults.getInst());

            FList deviceFlist = results.getAnyElement();
            int packageid = deviceFlist.get(FldPackageId.getInst()).intValue();

            FList CancelDeal = new FList();
            CancelDeal.set(FldPoid.getInst(), accountPoid);
            CancelDeal.set(FldServiceObj.getInst(), servicePoid);
            CancelDeal.set(FldProgramName.getInst(), "WebKit");

            FList CancelDeal_SubFlist = new FList();
            CancelDeal_SubFlist.set(FldPackageId.getInst(), packageid);
            CancelDeal_SubFlist.set(FldDealObj.getInst(), Poid.valueOf("0.0.0.1 /deal " + dealPoid));
            CancelDeal.set(FldDealInfo.getInst(), CancelDeal_SubFlist);
            
            output = ctx.opcode(926, CancelDeal);
            
        } catch (EBufException ex) {
            throw new EBufException(1, ex);
        } finally {
            try {
                releaseContext(ctx);
            } catch (Exception ex) {
                
            }
        }
        return "";
    }

    /**

     *  By default PCM Library for the selfcare will not provide the additional details like invoice date, invoice no and invoice amount in  selfcare.

     *  Following function will return the getInvoices with the details of invoice date, no and invoice amount.

     */
    public static List<Invoices> getInvoices(Poid macctPOID, Session session) throws RemoteException {
    	List<Invoices> invoiceList = new ArrayList<Invoices>();
        FList FListOut = null;
        Invoices invoice = null;
        
        try {
            FList input = new FList();
            input.set(FldPoid.getInst(), new Poid(1, -1L, "/search"));
            input.set(FldFlags.getInst(), 256);
            input.set(FldTemplate.getInst(), "select X from /bill where F1 = V1 and F2.type like V2 order by F3 desc ");
            input.setElement(FldResults.getInst(), 0);
            FList tempFL = new FList();
            tempFL.set(FldAccountObj.getInst(), macctPOID);
            input.setElement(FldArgs.getInst(), 1, tempFL);
            
            tempFL = new FList();
            tempFL.set(FldInvoiceObj.getInst(), new Poid(1, -1L, "/invoice"));
            input.setElement(FldArgs.getInst(), 2, tempFL);
            tempFL = new FList();
            tempFL.set(FldDueT.getInst());
            input.setElement(FldArgs.getInst(), 3, tempFL);
            
            FListOut = ExecuteOpcode.execute(7, input,session);

            if (!FListOut.hasField(FldResults.getInst())) {
                
                throw new RemoteException("error.invoice");
            }

            SparseArray resultsArray = FListOut.get(FldResults.getInst());
            if (resultsArray == null) {
                throw new RemoteException("error.invoice");
            }
            
            int i = 0;
            Enumeration enumVals = resultsArray.getValueEnumerator();
            do {
            	i += 1;
            	if(i == 6)
            		break;
                if (!enumVals.hasMoreElements()) {
                    break;
                }
                FList r1 = (FList) enumVals.nextElement();
                Poid poid = r1.get(FldPoid.getInst());
                invoice = new Invoices(r1.get(FldDueT.getInst()), r1.get(FldStartT.getInst()), r1.get(FldEndT.getInst()), r1.get(FldBillNo.getInst()), r1.get(FldCurrentTotal.getInst()), r1.get(FldRecvd.getInst()), r1.get(FldAdjusted.getInst()),r1.get(FldTransfered.getInst()));
                
                if (poid == null) {
                    continue;
                }
                invoiceList.add(invoice);
            } while (true);
        } catch (Exception e) {
            
        } finally {
            
        }
        return invoiceList;
    }
    
    public String getLastPayment(Poid accountPoid, Session session) throws RemoteException {
        try {
            
            FList input = new FList();
            input.set(FldPoid.getInst(), new Poid(1L, 0L, "/search"));
            input.set(FldFlags.getInst(), 256);
            input.set(FldTemplate.getInst(), "select X from /receipt_info where F1 = V1 order by created_t desc");
            input.set(FldResults.getInst());
            SparseArray args = new SparseArray();

            FList device_sub = new FList();
            device_sub.set(FldAccountObj.getInst(), accountPoid);

            args.add(1, device_sub);
            input.set(FldArgs.getInst(), args);
            

            FList output = ExecuteOpcode.execute(7, input, session);
            output.dump();
            SparseArray results = output.get(FldResults.getInst());
            FList resultFields = results.getAnyElement();
            this.lastPaidAmount = resultFields.get(FldAmount.getInst());
            this.lastPaidDate = resultFields.get(FldCreatedT.getInst());
            
        } catch (EBufException ex) {
            
        } finally {
            
        }
        return "";
    }

    /* getDevice Details base on MAC
     * PKAasimN
     */

    public FList getMacDetails(String macAddress, Session session) throws RemoteException {
        FList output = new FList();
        try {

            FList input = new FList();
            input.set(FldPoid.getInst(), new Poid(1, -1, "/search"));
            input.set(FldFlags.getInst(), 256);
            SparseArray FLD_RESULTS_Array = new SparseArray();
            SparseArray FLD_SERVICES_Array = new SparseArray();
            SparseArray PIN_FLD_ARGS_Array = new SparseArray();
            SparseArray WTB_FLD_CPE_DETAILS_Array = new SparseArray();

            FList FLD_RESULTS_LIST = new FList();
            FList PIN_FLD_SERVICES_LIST = new FList();
            FList WTB_FLD_MAC_ADDRESS_WAN_LIST = new FList();
            FList WTB_FLD_CPE_DETAILS_LIST = new FList();

            FLD_RESULTS_Array.add(FLD_RESULTS_LIST);

            input.set(FldResults.getInst(), FLD_RESULTS_Array);

            PIN_FLD_SERVICES_LIST.set(FldServices.getInst(), FLD_SERVICES_Array);

            WTB_FLD_MAC_ADDRESS_WAN_LIST.set(WtbFldMacAddressWan.getInst(), macAddress);

            WTB_FLD_CPE_DETAILS_Array.add(1, WTB_FLD_MAC_ADDRESS_WAN_LIST);
            WTB_FLD_CPE_DETAILS_LIST.set(WtbFldCpeDetails.getInst(), WTB_FLD_CPE_DETAILS_Array);

            PIN_FLD_ARGS_Array.add(1, WTB_FLD_CPE_DETAILS_LIST);
            input.set(FldArgs.getInst(), PIN_FLD_ARGS_Array);
            input.set(FldTemplate.getInst(), "select X from /device/apn/cpe where lower( F1 ) like lower( V1 ) ");
            
            output = ExecuteOpcode.execute(7, input, session);

            System.out.println("MAC details InFLIST :" + input.toString());
            System.out.println("MAC details outFLIST:" + output.toString());

        } //catch (EBufException ex) {

    //    }
    finally {

        }

    return output;

    }

     /* getLoggedInUserID
     * PKAasimN
     */

    public String getLoggedInCSRID(Session session) throws RemoteException
    {
        String CSRID = "";
        String Source="";
        FList out = null;
        
        //LoggedInUsrInfoBean CSRInfo = new LoggedInUsrInfoBean();

        //Getting SaleID of loggedin User
        FList InFlist = new FList();
        Poid UsrAcc = Poid.valueOf(session.get("LogInUsrAccID"));
        InFlist.set(FldPoid.getInst(), UsrAcc);
        System.out.println("/n In Flist of LoggedInUser"+InFlist.toString()+"/n");
        try{
        out = ExecuteOpcode.execute(3, InFlist, session);
        //out.elements();
        System.out.println("<br> Out Flist of LoggedInUser"+out.toString()+"<br>");
        CSRID = out.get(FldAccessCode1.getInst()).toString();
        Source = out.get(FldAacSource.getInst()).toString();
        System.out.println("Source is"+Source);
        //CSRInfo.setCSRID(out.get(FldAccessCode1.getInst()).toString());
       // CSRInfo.setSource(out.get(FldSource.getInst()).toString());

       // Cache.set(session.getId()+"CSRInfo", CSRInfo);
        
        } catch(EBufException ebuf)
        {
            
        }
        catch(RemoteException e)
        {

        }
        session.put("salesId", CSRID);
        session.put("source",Source);

        return CSRID;
    }
    
    public List<Payments> getLastPayment(Poid accountPoid, Session session, int size) throws RemoteException {
    	List<Payments> paymentList = new ArrayList<Payments>();
    	
    	try {
             Payments payment = null;
        	
            FList input = new FList();
            input.set(FldPoid.getInst(), new Poid(1L, 0L, "/search"));
            input.set(FldFlags.getInst(), 256);
            input.set(FldTemplate.getInst(), "select X from /receipt_info where F1 = V1 order by created_t desc");
            input.set(FldResults.getInst());
            SparseArray args = new SparseArray();

            FList device_sub = new FList();
            device_sub.set(FldAccountObj.getInst(), accountPoid);

            args.add(1, device_sub);
            input.set(FldArgs.getInst(), args);
            

            FList FListOut = ExecuteOpcode.execute(7, input, session);
            FListOut.dump();
            if (!FListOut.hasField(FldResults.getInst())) {
                
                throw new RemoteException("error.invoice");
            }

            SparseArray resultsArray = FListOut.get(FldResults.getInst());
            if (resultsArray == null) {
                throw new RemoteException("error.invoice");
            }

            Enumeration enumVals = resultsArray.getValueEnumerator();
            do {
                if (!enumVals.hasMoreElements()) {
                    break;
                }
                FList r1 = (FList) enumVals.nextElement();
                Poid poid = r1.get(FldPoid.getInst());
                payment = new Payments(r1.get(FldAacAccess.getInst()), r1.get(FldTitle.getInst()), r1.get(FldAmount.getInst()), r1.get(FldAmount.getInst()), new BigDecimal("0.00"), r1.get(FldItemNo.getInst()));
                
                if (poid == null) {
                    continue;
                }
                paymentList.add(payment);
            } while (true);
            
        } catch (EBufException ex) {
            
        } finally {
            
        }
        return paymentList;
    }
    
    public BigDecimal getLastPaidAmount() {
        return lastPaidAmount;
    }

    public Date getLastPaidDate() {
        return lastPaidDate;
    }

    public Poid getmServicePoid() {
        return mServicePoid;
    }

    public void setmServicePoid(Poid mServicePoid) {
        this.mServicePoid = mServicePoid;
    }

    //Override method
    public Object getSelectionDataFor(String dataItem, int index) {
        return null;
    }

    public String getmMacAddress() {
        return mMacAddress;
    }

    public void setmMacAddress(String mMacAddress) {
        this.mMacAddress = mMacAddress;
    }

    public String getmProfileName() {
        return mProfileName;
    }

    public void setmProfileName(String mProfileName) {
        this.mProfileName = mProfileName;
    }

    public BigDecimal getDueNow() {
        return dueNow;
    }

    public BigDecimal getTotalBalance() {
        return totalBalance;
    }

    public BigDecimal getUnbilledAmount() {
        return unbilledAmount;
    }

    public Date getmAccountCreationDate() {
        return mAccountCreationDate;
    }

    public void setmAccountCreationDate(Date mAccountCreationDate) {
        this.mAccountCreationDate = mAccountCreationDate;
    }
    public String getSerialNo() {
		return serialNo;
	}

	public void setSerialNo(String serialNo) {
		this.serialNo = serialNo;
	}

	public String getCpeType() {
		return cpeType;
	}

	public static BigDecimal getUnappliedAmount() {
		return unappliedAmount;
	}

	public void setCpeType(String cpeType) {
		this.cpeType = cpeType;
	}
	
    public static BigDecimal getAllBillDueAmount() {
		return allBillDueAmount;
	}
     public String getSalespersonID() {
        return salespersonID;
    }

    public void setSalespersonID(String salespersonID) {
        this.salespersonID = salespersonID;
    }

    public String getSalespersonnelName() {
        return salespersonnelName;
    }

    public void setSalespersonnelName(String salespersonnelName) {
        this.salespersonnelName = salespersonnelName;
    }

     public String getDevManufacturer() {
        return DevManufacturer;
    }

    public void setDevManufacturer(String DevManufacturer) {
        this.DevManufacturer = DevManufacturer;
    }

    public String getDevModel() {
        return DevModel;
    }

    public void setDevModel(String DevModel) {
        this.DevModel = DevModel;
    }

    public String getSource() {
        return Source;
    }

    public void setSource(String Source) {
        this.Source = Source;
    }

    private Properties mProperties;
    private Poid macctPOID;
    private int mIncludechildren;
    private Poid mbillInfoPOID;
    private Date mStartTime;
    private Date mEndTime;
    private List mInvoices;
    private List mProducts;
    private List mDiscounts;
    private String mCNIC;
    private boolean isSingle;
    private Poid mServicePoid;
    private String mMacAddress;
    private String mProfileName;
    private static BigDecimal dueNow;
    private static BigDecimal unbilledAmount;
    private static BigDecimal totalBalance;
    private static BigDecimal unappliedAmount;
    private static BigDecimal allBillDueAmount;
    private BigDecimal lastPaidAmount;
    private Date lastPaidDate;
    private Date mAccountCreationDate;
    private String serialNo = null;
    private String cpeType = null;
    private String salespersonID = null;
    private String salespersonnelName = null;
    private String DevModel = null;
    private String DevManufacturer = null;
    private String Source = null;
    
}
