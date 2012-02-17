package com.portal.bas;

import java.math.BigDecimal;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;
import java.util.Properties;

import com.portal.bas.PCachedContext;
import com.portal.bas.PControllerImpl;
import com.portal.bas.PModelHandle;
import com.portal.pcm.EBufException;
import com.portal.pcm.FList;
import com.portal.pcm.Poid;
import com.portal.pcm.PortalOp;
import com.portal.pcm.SparseArray;
import com.portal.pcm.fields.FldAccessCode1;
import com.portal.pcm.fields.FldAccountObj;
import com.portal.pcm.fields.FldAmount;
import com.portal.pcm.fields.FldAmountIndicator;
import com.portal.pcm.fields.FldArBillinfoObj;
import com.portal.pcm.fields.FldArgs;
import com.portal.pcm.fields.FldBillinfo;
import com.portal.pcm.fields.FldCreatedT;
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
import com.portal.pcm.fields.FldFlags;
import com.portal.pcm.fields.FldGroupObj;
import com.portal.pcm.fields.FldIncludeChildren;
import com.portal.pcm.fields.FldInvoiceObj;
import com.portal.pcm.fields.FldMembers;
import com.portal.pcm.fields.FldModT;
import com.portal.pcm.fields.FldName;
import com.portal.pcm.fields.FldObject;
import com.portal.pcm.fields.FldOpCorrelationId;
import com.portal.pcm.fields.FldOpenbillDue;
import com.portal.pcm.fields.FldPackageId;
import com.portal.pcm.fields.FldParent;
import com.portal.pcm.fields.FldPasswd;
import com.portal.pcm.fields.FldPayinfoObj;
import com.portal.pcm.fields.FldPendingbillDue;
import com.portal.pcm.fields.FldPermitted;
import com.portal.pcm.fields.FldPoid;
import com.portal.pcm.fields.FldPoidType;
import com.portal.pcm.fields.FldProducts;
import com.portal.pcm.fields.FldProgramName;
import com.portal.pcm.fields.FldPurchaseEndDetails;
import com.portal.pcm.fields.FldReadAccess;
import com.portal.pcm.fields.FldResults;
import com.portal.pcm.fields.FldServiceId;
import com.portal.pcm.fields.FldServiceObj;
import com.portal.pcm.fields.FldServices;
import com.portal.pcm.fields.FldStartT;
import com.portal.pcm.fields.FldStateId;
import com.portal.pcm.fields.FldStatus;
import com.portal.pcm.fields.FldStatusMsg;
import com.portal.pcm.fields.FldTemplate;
import com.portal.pcm.fields.FldUnappliedAmount;
import com.portal.pcm.fields.FldUsageEndDetails;
import com.portal.pcm.fields.FldWriteAccess;
import com.wtb.flds.WtbFldCpeDetails;
import com.wtb.flds.WtbFldMacAddressWan;
import com.wtb.flds.WtbFldProfileId;
import com.wtb.flds.WtbFldSvcInfo;

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

     *  By default PCM Library for the selfcare will not provide the additional details like invoice date, invoice no and invoice amount in  selfcare.

     *  Following function will return the getInvoices with the details of invoice date, no and invoice amount.

     */
    public List getInvoices() throws RemoteException {
        PCachedContext ctx = null;
        List invList = new ArrayList();
        FList FListOut = null;
        try {
            ctx = getContext();
            FList input = new FList();
            input.set(FldPoid.getInst(), new Poid(ctx.getCurrentDB(), -1L, "/search"));
            input.set(FldFlags.getInst(), 256);
            input.set(FldTemplate.getInst(), "select X from /bill where F1 = V1 and F2 >= V2 and F3 < V3 and F4.type like V4 order by F5 desc ");
            input.setElement(FldResults.getInst(), 0);
            FList tempFL = new FList();
            tempFL.set(FldAccountObj.getInst(), macctPOID);
            input.setElement(FldArgs.getInst(), 1, tempFL);
            tempFL = new FList();
            tempFL.set(FldEndT.getInst(), mStartTime);
            input.setElement(FldArgs.getInst(), 2, tempFL);
            tempFL = new FList();
            tempFL.set(FldEndT.getInst(), mEndTime);
            input.setElement(FldArgs.getInst(), 3, tempFL);
            tempFL = new FList();
            tempFL.set(FldInvoiceObj.getInst(), new Poid(ctx.getCurrentDB(), -1L, "/invoice"));
            input.setElement(FldArgs.getInst(), 4, tempFL);
            tempFL = new FList();
            tempFL.set(FldDueT.getInst());
            input.setElement(FldArgs.getInst(), 5, tempFL);
            
            FListOut = ctx.opcode(7, input);

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

                if (poid == null) {
                    
                    throw new RemoteException("error.pay");
                }
                invList.add(r1);
            } while (true);
        } catch (Exception e) {
            
        } finally {
            releaseContext(ctx);
        }
        return invList;
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
    public void getProfileNameNMac() throws RemoteException {
        PCachedContext ctx = null;
        String[] temp = null;
        try {
            ctx = getContext();
            FList in = new FList();
            in.set(FldPoid.getInst(), new Poid(ctx.getCurrentDB(), -1L, "/search"));
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

            
            FList output = ctx.opcode(7, in);

            FList profileResult = output.get(FldResults.getInst()).elementAt(0);
            FList serviceFld = profileResult.get(WtbFldSvcInfo.getInst()).elementAt(0);
            this.mAccountCreationDate = profileResult.get(FldCreatedT.getInst());

            Poid deviceApnCpePoid = serviceFld.get(FldDeviceObj.getInst());
            String deviceProfileStr = serviceFld.get(WtbFldProfileId.getInst());

            FList macFlist = new FList();
            macFlist.set(FldPoid.getInst(), deviceApnCpePoid);

            FList CpeFld = ctx.opcode(3, macFlist);

            FList cpeDetailsFld = CpeFld.get(WtbFldCpeDetails.getInst()).elementAt(0);

            temp = deviceProfileStr.split("_");
            this.mProfileName = (temp[2] + " Kbps");
            this.mMacAddress = cpeDetailsFld.get(WtbFldMacAddressWan.getInst());
            
        } catch (EBufException e) {
           
        } finally {
            releaseContext(ctx);
        }
    }

    public void getBalanceSummaryForBillInfo(Poid billInfoPoid, int nIncludeChildren)
            throws RemoteException {
        PCachedContext conn = null;
        try {
            FList flInput = new FList();
            conn = getContext();
            flInput.set(FldPoid.getInst(), billInfoPoid);
            flInput.set(FldIncludeChildren.getInst(), nIncludeChildren);
            
            FList flOutBal = conn.opcode(4507, flInput);

            BigDecimal bdAllBillDue = flOutBal.get(FldOpenbillDue.getInst());
            BigDecimal bdPending = flOutBal.get(FldPendingbillDue.getInst());
            BigDecimal bdUnapplied = flOutBal.get(FldUnappliedAmount.getInst());

            BigDecimal bdDueNow = bdAllBillDue.add(bdUnapplied);
            BigDecimal bdTotal = bdDueNow.add(bdPending);

            this.dueNow = bdDueNow;
            this.unbilledAmount = bdPending;
            this.totalBalance = bdTotal;

            
        } catch (EBufException e) {
            
            throw new RemoteException(e.getMessage());
        } finally {
            releaseContext(conn);
        }
    }

    public String purchaseDeal(Poid accountPoid, Poid servicePoid, String dealPoid) throws RemoteException, EBufException {
        PCachedContext ctx = null;
        try {
            ctx = getContext();

            FList ReadDealObj = new FList();
            SparseArray ReadDealArray = new SparseArray();
            FList ReadDealFl = new FList();
            FList PurcahseDeal = new FList();
            ReadDealObj.set(FldPoid.getInst(), Poid.valueOf("0.0.0.1 /deal " + dealPoid));
            ReadDealObj.dump();
            FList ReadDealObjOpt = ctx.opcode(3, ReadDealObj);
            ReadDealObjOpt.dump();

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
            
            FList output = ctx.opcode(108, PurcahseDeal);

            
        } catch (EBufException ex) {
            
            throw ex;
        } finally {
            try {
                releaseContext(ctx);
            } catch (Exception ex) {
                
            }
        }
        return "";
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

    public String getLastPayment(Poid accountPoid) throws RemoteException {
        PCachedContext ctx = null;
        try {
            ctx = getContext();

            FList input = new FList();
            input.set(FldPoid.getInst(), new Poid(1L, 0L, "/search"));
            input.set(FldFlags.getInst(), 256);
            input.set(FldTemplate.getInst(), "select X from /receipt_info where F1 = V1 order by created_t desc");
            input.setElement(FldResults.getInst(), 1);
            SparseArray args = new SparseArray();

            FList device_sub = new FList();
            device_sub.set(FldAccountObj.getInst(), accountPoid);

            args.add(1, device_sub);
            input.set(FldArgs.getInst(), args);

            

            FList output = ctx.opcode(7, input);

            SparseArray results = output.get(FldResults.getInst());
            FList resultFields = results.getAnyElement();
            this.lastPaidAmount = resultFields.get(FldAmount.getInst());
            this.lastPaidDate = resultFields.get(FldCreatedT.getInst());
            
        } catch (EBufException ex) {
            
        } finally {
            try {
                releaseContext(ctx);
            } catch (Exception ex) {
                
            }
        }
        return "";
    }

    ////////////////
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
    private BigDecimal dueNow;
    private BigDecimal unbilledAmount;
    private BigDecimal totalBalance;
    private BigDecimal lastPaidAmount;
    private Date lastPaidDate;
    private Date mAccountCreationDate;
}

