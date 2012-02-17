package com.portal.app.ccare.comp;

import java.math.BigDecimal;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.List;
import java.util.Locale;
import java.util.Vector;

import models.utils.ExecuteOpcode;
import play.mvc.Scope.Session;

import com.portal.bas.PBadFieldDescriptionException;
import com.portal.bas.PCachedContext;
import com.portal.bas.PCurrency;
import com.portal.bas.PDataFieldSpecification;
import com.portal.bas.PFieldSpecFactory;
import com.portal.bas.PModelHandle;
import com.portal.bas.comp.PIAComponentCollectionBean;
import com.portal.common.BEIDData;
import com.portal.common.BEIDManager;
import com.portal.common.ReasonCodeData;
import com.portal.common.ReasonCodeManager;
import com.portal.pcm.DefaultLog;
import com.portal.pcm.EBufException;
import com.portal.pcm.FList;
import com.portal.pcm.Poid;
import com.portal.pcm.PortalOp;
import com.portal.pcm.SparseArray;
import com.portal.pcm.fields.FldAccessCode1;
import com.portal.pcm.fields.FldAccountObj;
import com.portal.pcm.fields.FldAddress;
import com.portal.pcm.fields.FldArgs;
import com.portal.pcm.fields.FldBalGrpObj;
import com.portal.pcm.fields.FldBalances;
import com.portal.pcm.fields.FldBillObj;
import com.portal.pcm.fields.FldBillinfo;
import com.portal.pcm.fields.FldBillinfoObj;
import com.portal.pcm.fields.FldBusinessType;
import com.portal.pcm.fields.FldCanonCompany;
import com.portal.pcm.fields.FldCanonCountry;
import com.portal.pcm.fields.FldCity;
import com.portal.pcm.fields.FldCompany;
import com.portal.pcm.fields.FldContactType;
import com.portal.pcm.fields.FldCreatedT;
import com.portal.pcm.fields.FldCreditFloor;
import com.portal.pcm.fields.FldCreditLimit;
import com.portal.pcm.fields.FldCreditThresholds;
import com.portal.pcm.fields.FldCurrency;
import com.portal.pcm.fields.FldCurrencySecondary;
import com.portal.pcm.fields.FldCurrentBal;
import com.portal.pcm.fields.FldEmailAddr;
import com.portal.pcm.fields.FldFirstCanon;
import com.portal.pcm.fields.FldFirstName;
import com.portal.pcm.fields.FldFlags;
import com.portal.pcm.fields.FldGroupObj;
import com.portal.pcm.fields.FldIncludeChildren;
import com.portal.pcm.fields.FldLastBillT;
import com.portal.pcm.fields.FldLastCanon;
import com.portal.pcm.fields.FldLastName;
import com.portal.pcm.fields.FldLastStatusT;
import com.portal.pcm.fields.FldLaststatCmnt;
import com.portal.pcm.fields.FldMiddleCanon;
import com.portal.pcm.fields.FldMiddleName;
import com.portal.pcm.fields.FldNameinfo;
import com.portal.pcm.fields.FldNextBal;
import com.portal.pcm.fields.FldNextBillT;
import com.portal.pcm.fields.FldOpenBal;
import com.portal.pcm.fields.FldOpenbillDue;
import com.portal.pcm.fields.FldParent;
import com.portal.pcm.fields.FldPayType;
import com.portal.pcm.fields.FldPendingbillDue;
import com.portal.pcm.fields.FldPhone;
import com.portal.pcm.fields.FldPhones;
import com.portal.pcm.fields.FldPoid;
import com.portal.pcm.fields.FldReservedAmount;
import com.portal.pcm.fields.FldResults;
import com.portal.pcm.fields.FldSalutation;
import com.portal.pcm.fields.FldServiceObj;
import com.portal.pcm.fields.FldState;
import com.portal.pcm.fields.FldStatus;
import com.portal.pcm.fields.FldTemplate;
import com.portal.pcm.fields.FldTitle;
import com.portal.pcm.fields.FldTotalRecords;
import com.portal.pcm.fields.FldUnappliedAmount;
import com.portal.pcm.fields.FldZip;

public class PIABalanceInfoBeanPImpl extends PIAComponentCollectionBean
		implements PIABalanceInfoBeanP {

	private Poid mAcctPoid = null;
	private Poid mBalGrpPoid = null;
	private Poid mBillinfoPoid = null;
	private PNameinfoBeanP nameInfoBean = null;
	private Hashtable mSvcLvlBillinfos = null;
	private Hashtable mAcctLvlBillinfos = null;
	private Hashtable mBalances = null;
	private Hashtable mSvcsForBalGrp = null;
	private Hashtable mBalsOfAllBalGrpsForBillinfo = null;
	private BigDecimal mCurrentBalance = null;
	private String mPrimaryCurrencySymbol = null;
	private static final String DECIMAL = "0.000000";
	private int priID;
	private int secID;
	private PDataFieldSpecification priCur;
	private PDataFieldSpecification secCur;
	private PDataFieldSpecification dataSpec;
	private Integer[] currencyIDS;
	private Integer[] nonCurrencyIDS;
	private boolean displayIn2ndaryCurrency = false;
	private String noLimit = null;
	private List mCurrencyL = new ArrayList();
	private List mNonCurrencyL = new ArrayList();

	public enum BusinessType {
		Unknown, Individual, Corporate, AffinityChild, Affinity, FUT, CSR, SOHO, Employee, Test;
		public static BusinessType get(int i) {
			return values()[i];
		}
	}

	public PIABalanceInfoBeanPImpl() throws RemoteException {
		try {
			this.dataSpec = ((PDataFieldSpecification) PFieldSpecFactory
					.createSpec("FldBalances[all]"));

			this.priCur = ((PDataFieldSpecification) PFieldSpecFactory
					.createSpec(FldCurrency.getInst()));

			this.secCur = ((PDataFieldSpecification) PFieldSpecFactory
					.createSpec(FldCurrencySecondary.getInst()));
		} catch (PBadFieldDescriptionException e) {
			if (DefaultLog.doLog(2))
				DefaultLog.log(this, 2, e);
			throw new RemoteException("error.baddesc", e);
		}
	}

	public List getCurrencyData() {
		if (DefaultLog.doLog(8)) {
			DefaultLog.log(8, "WebKit:***getCurrencyData()***\n");
		}

		return this.mCurrencyL;
	}

	public Object getCurrencyData(int index) {
		if (DefaultLog.doLog(8)) {
			DefaultLog.log(8, "WebKit:***getCurrencyData(int index)***\n");
		}

		if ((index < 0) || (index >= this.mCurrencyL.size())) {
			throw new IndexOutOfBoundsException();
		}

		return this.mCurrencyL.get(index);
	}

	protected void setCurrencyData(Object Result) {
		this.mCurrencyL.add(Result);
	}

	public List getNonCurrencyData() {
		if (DefaultLog.doLog(8)) {
			DefaultLog.log(8, "WebKit:***getNonCurrencyData()***\n");
		}

		return this.mNonCurrencyL;
	}

	public Object getNonCurrencyData(int index) {
		if (DefaultLog.doLog(8)) {
			DefaultLog.log(8, "WebKit:***getNonCurrencyData(int index)***\n");
		}

		if ((index < 0) || (index >= this.mNonCurrencyL.size())) {
			throw new IndexOutOfBoundsException();
		}

		return this.mNonCurrencyL.get(index);
	}

	protected void setNonCurrencyData(Object Result) {
		this.mNonCurrencyL.add(Result);
	}

	public void update(int reason, Object data) throws RemoteException {
		if (DefaultLog.doLog(8)) {
			DefaultLog.log(this, 8, "BalancePanel.update: data=" + getModel());
		}

		PCachedContext ctx = null;
		try {
			ctx = getContext();
			if (ctx == null) {
				if (DefaultLog.doLog(2))
					DefaultLog.log(2, "Could not get a valid context");
				throw new RemoteException("error.infranet");
			}

			PModelHandle mH = getModel();
			if (mH == null) {
				return;
			}
			ctx.lookupModel(mH, false, 1);
			super.update(reason, data);

			this.priID = ((Number) getField(this.priCur)).intValue();
			this.secID = ((Number) getField(this.secCur)).intValue();
			this.mPrimaryCurrencySymbol = getSymbol(this.priID);

			BEIDManager beidMgr = BEIDManager.getInstance(ctx);

			SparseArray sa = (SparseArray) getField(this.dataSpec);
			if (sa == null)
				return;
			Enumeration keys = sa.getKeyEnumerator();
			Enumeration vals = sa.getValueEnumerator();

			this.currencyIDS = new Integer[sa.getSize()];
			this.nonCurrencyIDS = new Integer[sa.getSize()];

			this.mCurrencyL.clear();

			this.mNonCurrencyL.clear();

			int curCnt = 0;
			int noncurCnt = 0;
			while (vals.hasMoreElements()) {
				Vector line = new Vector();

				Integer id = (Integer) keys.nextElement();
				int id_intVal = id.intValue();
				FList elem = (FList) vals.nextElement();

				String descr = beidMgr.getDescription(id_intVal);
				BigDecimal openBal = elem.get(FldOpenBal.getInst());
				BigDecimal limit = elem.get(FldCreditLimit.getInst());
				BigDecimal bal = elem.get(FldCurrentBal.getInst());
				BigDecimal total = new BigDecimal("0.000000");

				if (BEIDManager.isCurrency(id_intVal)) {
					String sym = getSymbol(id_intVal);

					BigDecimal avail = null;
					if (limit != null) {
						avail = limit.subtract(bal);
						if (avail.compareTo(limit) == 1) {
							avail = limit;
						}
					}
					if ((id_intVal == this.priID)
							&& (this.displayIn2ndaryCurrency)
							&& (this.secID != 0)) {
						if (limit != null) {
							limit = beidMgr.convertCurrency(this.priID,
									this.secID, new Date(), limit);
						}

						bal = beidMgr.convertCurrency(this.priID, this.secID,
								new Date(), bal);

						if (avail != null) {
							avail = beidMgr.convertCurrency(this.priID,
									this.secID, new Date(), avail);
						}

						if (openBal != null) {
							openBal = beidMgr.convertCurrency(this.priID,
									this.secID, new Date(), openBal);
						}

						descr = beidMgr.getDescription(this.secID);
						sym = getSymbol(this.secID);
					}
					Object climit;
					if (limit != null) {
						climit = new PCurrency(limit);
						((PCurrency) climit).setCurrencySymbol(sym);
					} else {
						climit = this.noLimit;
					}
					Object cavail;
					if (avail != null) {
						cavail = new PCurrency(avail);
						((PCurrency) cavail).setCurrencySymbol(sym);
					} else {
						cavail = this.noLimit;
					}
					Object copenbal;
					if (openBal != null) {
						copenbal = new PCurrency(openBal);
						((PCurrency) copenbal).setCurrencySymbol(sym);
					} else {
						copenbal = this.noLimit;
					}
					PCurrency cbal = new PCurrency(bal);
					cbal.setCurrencySymbol(sym);

					line.addElement(descr);
					line.addElement(climit);
					line.addElement(cavail);
					line.addElement(cbal);
					line.addElement(copenbal);

					setCurrencyData(line);
					this.currencyIDS[(curCnt++)] = id;
				} else {
					line.addElement(descr);
					if (limit != null)
						line.addElement(limit);
					else
						line.addElement(this.noLimit);
					line.addElement(bal);

					setNonCurrencyData(line);
					this.nonCurrencyIDS[(noncurCnt++)] = id;
				}
			}

		} catch (PBadFieldDescriptionException e) {
			if (DefaultLog.doLog(2))
				DefaultLog.log(this, 2, e);
			throw new RemoteException("error.baddesc", e);
		} catch (EBufException e) {
			if (DefaultLog.doLog(2)) {
				DefaultLog.log(this, 2, e);
			}
			if (e.getError() != 3) {
				throw createClientException(e);
			}
		} catch (Exception e) {
			if (DefaultLog.doLog(2)) {
				DefaultLog.log(this, 2, e);
			}
			if ((e instanceof RemoteException))
				throw ((RemoteException) e);
			throw new RemoteException("", e);
		} finally {
			releaseContext(ctx);
		}
	}

	public Object getCurrencySymbol(int index) throws RemoteException {
		PCachedContext ctx = getContext();
		if (ctx == null) {
			if (DefaultLog.doLog(2))
				DefaultLog.log(2, "Could not get a valid context");
			throw new RemoteException("error.infranet");
		}

		try {
			BEIDManager beidMgr = BEIDManager.getInstance(ctx);
			int id = this.currencyIDS[index].intValue();
			String sym = getSymbol(id);
			if ("E".equals(sym))
				sym = "€";
			String str1 = sym;
			return str1;
		} finally {
			releaseContext(ctx);
		}
	}

	public Object getBEIDData(int index, boolean currency)
			throws RemoteException {
		PCachedContext ctx = getContext();
		if (ctx == null) {
			if (DefaultLog.doLog(2))
				DefaultLog.log(2, "Could not get a valid context");
			throw new RemoteException("error.infranet");
		}
		try {
			BEIDManager beidMgr = BEIDManager.getInstance(ctx);
			if (currency) {
				return beidMgr.getData(this.currencyIDS[index].intValue());
			}
			BEIDData localBEIDData = beidMgr.getData(this.nonCurrencyIDS[index]
					.intValue());
			return localBEIDData;
		} finally {
			releaseContext(ctx);
		}
	}

	public Object getBEIDDescription(int index) throws RemoteException {
		PCachedContext ctx = getContext();
		if (ctx == null) {
			if (DefaultLog.doLog(2))
				DefaultLog.log(2, "Could not get a valid context");
			throw new RemoteException("error.infranet");
		}

		try {
			BEIDManager beidMgr = BEIDManager.getInstance(ctx);
			String str = beidMgr.getDescription(index);
			return str;
		} finally {
			releaseContext(ctx);
		}
	}

	public Object getBEIDDescription(int index, Session session ) throws RemoteException {
		PCachedContext ctx = ExecuteOpcode.getContext(session);
		if (ctx == null) {
			if (DefaultLog.doLog(2))
				DefaultLog.log(2, "Could not get a valid context");
			throw new RemoteException("error.infranet");
		}

		try {
			BEIDManager beidMgr = BEIDManager.getInstance(ctx);
			String str = beidMgr.getDescription(index);
			return str;
		} finally {
			ExecuteOpcode.releaseContext(ctx, session);
		}
	}
	
	public Boolean isSecIDNonZero() throws RemoteException {
		return new Boolean(this.secID != 0);
	}

	public Object getReasonData(Object eventData) throws RemoteException {
		PCachedContext ctx = getContext();
		if (ctx == null) {
			if (DefaultLog.doLog(2))
				DefaultLog.log(2, "Could not get a valid context");
			throw new RemoteException("error.infranet");
		}
		try {
			ReasonCodeManager rcm = ReasonCodeManager.getInstance(ctx);
			ArrayList wrapper = (ArrayList) eventData;

			Integer reasonType = (Integer) wrapper.get(0);
			Locale locale = (Locale) wrapper.get(1);

			ReasonCodeData[] arrayOfReasonCodeData = rcm.getReasonDataAsArray(
					reasonType.intValue(), locale, 2);
			return arrayOfReasonCodeData;
		} finally {
			releaseContext(ctx);
		}
	}

	private String getSymbol(int id) throws RemoteException {
		PCachedContext ctx = getContext();
		if (ctx == null) {
			if (DefaultLog.doLog(2))
				DefaultLog.log(2, "Could not get a valid context");
			throw new RemoteException("error.infranet");
		}
		try {
			BEIDManager beidMgr = BEIDManager.getInstance(ctx);

			String str = beidMgr.getSymbol(getCurrencyDisplayID(id));
			return str;
		} finally {
			releaseContext(ctx);
		}
	}

	@SuppressWarnings("unused")
	private String getSymbol(int id, PCachedContext ctx) throws RemoteException {
		if (ctx == null) {
			if (DefaultLog.doLog(2))
				DefaultLog.log(2, "Could not get a valid context");
			throw new RemoteException("error.infranet");
		}
		try {
			BEIDManager beidMgr = BEIDManager.getInstance(ctx);

			String str = beidMgr.getSymbol(getCurrencyDisplayID(id));
			return str;
		} finally {
			
		}
	}
	
	private int getCurrencyDisplayID(int id) {
		if ((id == this.priID) && (this.displayIn2ndaryCurrency)
				&& (this.secID != 0)) {
			return this.secID;
		}
		return id;
	}

	public void setAccountPoid(Poid pAcctPoid) {
		this.mAcctPoid = pAcctPoid;
	}

	public Poid getAccountPoid() {
		return this.mAcctPoid;
	}

	public void setBalanceGroupPoid(Poid pBalGrpPoid) {
		this.mBalGrpPoid = pBalGrpPoid;
	}

	public Poid getBalanceGroupPoid() {
		return this.mBalGrpPoid;
	}

	public void setBillinfoPoid(Poid pBillinfoPoid) {
		this.mBillinfoPoid = pBillinfoPoid;
	}

	public Poid getBillinfoPoid() {
		return this.mBillinfoPoid;
	}

	public Hashtable getAllSvcLvlBillinfos() {
		if (DefaultLog.doLog(8)) {
			DefaultLog.log(8, "WebKit:***getAllSvcLvlBillinfos()***");
		}

		return this.mSvcLvlBillinfos;
	}

	public Hashtable getAcctLvlBillinfoDetails() {
		if (DefaultLog.doLog(8)) {
			DefaultLog.log(8, "WebKit:***getAcctLvlBillinfoDetails()***");
		}

		return this.mAcctLvlBillinfos;
	}

	public PNameinfoBeanP getAllNameinfo() {
		if (DefaultLog.doLog(8)) {
			DefaultLog.log(8, "WebKit:***getAllNameinfo()***");
		}

		return this.nameInfoBean;
	}

	public BigDecimal getCurrencyCurrentBal() {
		return this.mCurrentBalance;
	}

	public Hashtable getBalancesForAcctAndBillinfo() {
		if (DefaultLog.doLog(8)) {
			DefaultLog.log(8, "WebKit:***getBalancesForAcctAndBillinfo()***");
		}

		return this.mBalances;
	}

	public Hashtable getSvcsForBalGrpOfAcctAndBillinfo() {
		if (DefaultLog.doLog(8)) {
			DefaultLog.log(8,
					"WebKit:***getSvcsForBalGrpOfAcctAndBillinfo()***");
		}
		return this.mSvcsForBalGrp;
	}

	public Hashtable getBalsOfAllBalGrpsOfAcctAndBillinfo() {
		if (DefaultLog.doLog(8)) {
			DefaultLog.log(8,
					"WebKit:***getBalsOfAllBalGrpsOfAcctAndBillinfo()***");
		}
		return this.mBalsOfAllBalGrpsForBillinfo;
	}

	public BigDecimal getCurrencyCreditLimitForBillunit() {
		Enumeration enumBalGroups = this.mBalsOfAllBalGrpsForBillinfo.keys();
		Enumeration enumBalances = this.mBalsOfAllBalGrpsForBillinfo.elements();
		BigDecimal TotalCreditLimt = null;

		while (enumBalances.hasMoreElements()) {
			Hashtable hashBals = (Hashtable) enumBalances.nextElement();
			Enumeration enumKey = hashBals.keys();
			Enumeration enumBals = hashBals.elements();
			while (enumBals.hasMoreElements()) {
				Integer key = (Integer) enumKey.nextElement();

				Vector vecBalances = (Vector) enumBals.nextElement();
				if (vecBalances.elementAt(1) != null) {
					BigDecimal CreditLimt = (BigDecimal) vecBalances
							.elementAt(1);
					if (TotalCreditLimt == null) {
						TotalCreditLimt = CreditLimt;
					} else {
						TotalCreditLimt = TotalCreditLimt.add(CreditLimt);
					}

				} else {
					return null;
				}
			}
		}
		return TotalCreditLimt;
	}

	public void getAcctLvlBalanceGroupAndBillinfoID(Poid pAcctPoid,
			Session session) throws RemoteException {
		try {
			FList flInput = new FList();

			if (pAcctPoid != null) {
				flInput.set(FldPoid.getInst(), pAcctPoid);
			}

			if (DefaultLog.doLog(8)) {
				DefaultLog.log(this, 8, "PCM_OP_BAL_GET_BALANCES input FList:");

				DefaultLog.log(this, 8, flInput.toString());
			}

			FList flOutBal = ExecuteOpcode.execute(3701, flInput, session);
			flOutBal.dump();
			setAccountPoid(flOutBal.get(FldAccountObj.getInst()));
			setBalanceGroupPoid(flOutBal.get(FldPoid.getInst()));
			setBillinfoPoid(flOutBal.get(FldBillinfoObj.getInst()));

			if (DefaultLog.doLog(8)) {
				DefaultLog
						.log(this, 8, "PCM_OP_BAL_GET_BALANCES output FList:");

				DefaultLog.log(this, 8, flOutBal.toString());
			}
		} catch (EBufException e) {
			if (DefaultLog.doLog(2)) {
				DefaultLog.log(this, 2, e);
			}
			throw new RemoteException(e.getMessage());
		} finally {

		}
	}

	public Hashtable getBalancesForAcctAndBalGrpID(Poid pAcctPoid,
			Poid pBalGrpPoid, Session session) throws RemoteException {
		try {
			FList flInput = new FList();
			FList flOutput = null;

			
			if ((pAcctPoid != null) && (pBalGrpPoid != null)) {
				flInput.set(FldPoid.getInst(), pAcctPoid);
				flInput.set(FldBalGrpObj.getInst(), pBalGrpPoid);
				flInput.setElement(FldBalances.getInst(), -1);
			}

			if (DefaultLog.doLog(8)) {
				DefaultLog.log(this, 8, "PCM_OP_BAL_GET_BALANCES input FList:");

				DefaultLog.log(this, 8, flInput.toString());
			}

			flOutput = ExecuteOpcode.execute(3701, flInput,session);
			flOutput.dump();
			Hashtable hashBalances = processGetBalancesOutputFlist(flOutput,session);

			if (DefaultLog.doLog(8)) {
				DefaultLog
						.log(this, 8, "PCM_OP_BAL_GET_BALANCES output FList:");

				DefaultLog.log(this, 8, flOutput.toString());
			}

			return hashBalances;
		} catch (RemoteException e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			
		}
		return null;
	}
	
	public Hashtable getBalancesForAcctAndBalGrpID(Poid pAcctPoid,
			Poid pBalGrpPoid) throws RemoteException {
		PCachedContext ctx = null;
		try {
			FList flInput = new FList();
			FList flOutput = null;

			ctx = getContext();

			if ((pAcctPoid != null) && (pBalGrpPoid != null)) {
				flInput.set(FldPoid.getInst(), pAcctPoid);
				flInput.set(FldBalGrpObj.getInst(), pBalGrpPoid);
				flInput.setElement(FldBalances.getInst(), -1);
			}

			if (DefaultLog.doLog(8)) {
				DefaultLog.log(this, 8, "PCM_OP_BAL_GET_BALANCES input FList:");

				DefaultLog.log(this, 8, flInput.toString());
			}

			flOutput = ctx.opcode(3701, flInput);

			Hashtable hashBalances = processGetBalancesOutputFlist(flOutput);

			if (DefaultLog.doLog(8)) {
				DefaultLog
						.log(this, 8, "PCM_OP_BAL_GET_BALANCES output FList:");

				DefaultLog.log(this, 8, flOutput.toString());
			}

			Hashtable localHashtable1 = hashBalances;
			return localHashtable1;
		} catch (EBufException e) {
			if (DefaultLog.doLog(2)) {
				DefaultLog.log(this, 2, e);
			}
			throw new RemoteException(e.getMessage());
		} finally {
			releaseContext(ctx);
		}
	}

	protected Hashtable processGetBalancesOutputFlist(FList flOut, Session session)
			throws RemoteException {
		PCachedContext conn = ExecuteOpcode.getContext(session);
		try {
			if ((flOut != null) && (!flOut.hasField(FldBalances.getInst()))) {
				if (DefaultLog.doLog(8)) {
					DefaultLog.log(8, "No Balances ");
				}
				throw new RemoteException("error.NoBalances");
			}

			Poid pBalGrpPoid = flOut.get(FldPoid.getInst());

			if (pBalGrpPoid == null) {
				if (DefaultLog.doLog(8)) {
					DefaultLog.log(8, "No Balance Group Poid");
				}
				throw new RemoteException("error.NoBalanceGroupPoid");
			}

			Poid pAccountPoid = flOut.get(FldAccountObj.getInst());
			if (pAccountPoid == null) {
				if (DefaultLog.doLog(8)) {
					DefaultLog.log(8, "No Account Poid");
				}
				throw new RemoteException("error.NoAccountPoid");
			}

			Poid pBillinfoPoid = flOut.get(FldBillinfoObj.getInst());
			if (pBillinfoPoid == null) {
				if (DefaultLog.doLog(8)) {
					DefaultLog.log(8, "No Billinfo Poid");
				}
				throw new RemoteException("error.NoBillinfoPoid");
			}

			SparseArray balancesArray = flOut.get(FldBalances.getInst());
			if (balancesArray == null) {
				throw new RemoteException("error.NoBalances");
			}

			BEIDManager beidManager = BEIDManager.getInstance(conn);

			Enumeration enumKeys = balancesArray.getKeyEnumerator();
			Enumeration enumBalances = balancesArray.getValueEnumerator();
			this.mBalances = new Hashtable();
			this.mCurrentBalance = null;

			while (enumBalances.hasMoreElements()) {
				Integer key = (Integer) enumKeys.nextElement();
				int iKey = key.intValue();
				String strSymbol = getSymbol(iKey, conn);

				FList flBalances = (FList) enumBalances.nextElement();

				Vector vecBalElements = new Vector();
				vecBalElements.addElement(flBalances.get(FldCreditFloor
						.getInst()));
				vecBalElements.addElement(flBalances.get(FldCreditLimit
						.getInst()));
				vecBalElements.addElement(flBalances.get(FldCreditThresholds
						.getInst()));
				vecBalElements.addElement(flBalances.get(FldCurrentBal
						.getInst()));
				vecBalElements.addElement(flBalances.get(FldNextBal.getInst()));
				vecBalElements.addElement(flBalances.get(FldReservedAmount
						.getInst()));
				vecBalElements.addElement(strSymbol);
				if (BEIDManager.isCurrency(iKey)) {
					this.mCurrentBalance = flBalances.get(FldCurrentBal
							.getInst());
				}

				this.mBalances.put(key, vecBalElements);
			}

			return this.mBalances;
		} catch (EBufException e) {
			if (DefaultLog.doLog(2)) {
				DefaultLog.log(this, 2, e);
			}
			throw new RemoteException(e.getMessage());
		} finally {
			ExecuteOpcode.releaseContext(conn, session);
		}
	}
	
	protected Hashtable processGetBalancesOutputFlist(FList flOut)
			throws RemoteException {
		PCachedContext conn = getContext();
		try {
			if ((flOut != null) && (!flOut.hasField(FldBalances.getInst()))) {
				if (DefaultLog.doLog(8)) {
					DefaultLog.log(8, "No Balances ");
				}
				throw new RemoteException("error.NoBalances");
			}

			Poid pBalGrpPoid = flOut.get(FldPoid.getInst());

			if (pBalGrpPoid == null) {
				if (DefaultLog.doLog(8)) {
					DefaultLog.log(8, "No Balance Group Poid");
				}
				throw new RemoteException("error.NoBalanceGroupPoid");
			}

			Poid pAccountPoid = flOut.get(FldAccountObj.getInst());
			if (pAccountPoid == null) {
				if (DefaultLog.doLog(8)) {
					DefaultLog.log(8, "No Account Poid");
				}
				throw new RemoteException("error.NoAccountPoid");
			}

			Poid pBillinfoPoid = flOut.get(FldBillinfoObj.getInst());
			if (pBillinfoPoid == null) {
				if (DefaultLog.doLog(8)) {
					DefaultLog.log(8, "No Billinfo Poid");
				}
				throw new RemoteException("error.NoBillinfoPoid");
			}

			SparseArray balancesArray = flOut.get(FldBalances.getInst());
			if (balancesArray == null) {
				throw new RemoteException("error.NoBalances");
			}

			BEIDManager beidManager = BEIDManager.getInstance(conn);

			Enumeration enumKeys = balancesArray.getKeyEnumerator();
			Enumeration enumBalances = balancesArray.getValueEnumerator();
			this.mBalances = new Hashtable();
			this.mCurrentBalance = null;

			while (enumBalances.hasMoreElements()) {
				Integer key = (Integer) enumKeys.nextElement();
				int iKey = key.intValue();
				String strSymbol = getSymbol(iKey);

				FList flBalances = (FList) enumBalances.nextElement();

				Vector vecBalElements = new Vector();
				vecBalElements.addElement(flBalances.get(FldCreditFloor
						.getInst()));
				vecBalElements.addElement(flBalances.get(FldCreditLimit
						.getInst()));
				vecBalElements.addElement(flBalances.get(FldCreditThresholds
						.getInst()));
				vecBalElements.addElement(flBalances.get(FldCurrentBal
						.getInst()));
				vecBalElements.addElement(flBalances.get(FldNextBal.getInst()));
				vecBalElements.addElement(flBalances.get(FldReservedAmount
						.getInst()));
				vecBalElements.addElement(strSymbol);
				if (BEIDManager.isCurrency(iKey)) {
					this.mCurrentBalance = flBalances.get(FldCurrentBal
							.getInst());
				}

				this.mBalances.put(key, vecBalElements);
			}

			return this.mBalances;
		} catch (EBufException e) {
			if (DefaultLog.doLog(2)) {
				DefaultLog.log(this, 2, e);
			}
			throw new RemoteException(e.getMessage());
		} finally {
			releaseContext(conn);
		}
	}

	protected void processAcctLvlBalGrpAndBillinfoMH(PModelHandle mHBalGrp)
			throws RemoteException {
		PCachedContext ctx = null;
		try {
			FList flBalGrp = new FList();
			ctx = getContext();

			if (mHBalGrp != null) {
				flBalGrp = (FList) ctx.lookupModel(mHBalGrp);

				if (flBalGrp != null) {
					if (!flBalGrp.hasField(FldPoid.getInst())) {
						if (DefaultLog.doLog(8)) {
							DefaultLog.log(8, "No Balance Group Poid");
						}
						throw new RemoteException("error.NoBalanceGroupPoid");
					}
					setBalanceGroupPoid(flBalGrp.get(FldPoid.getInst()));

					if (!flBalGrp.hasField(FldAccountObj.getInst())) {
						if (DefaultLog.doLog(8)) {
							DefaultLog.log(8, "No Account Poid");
						}
						throw new RemoteException("error.NoAccountPoid");
					}
					setAccountPoid(flBalGrp.get(FldAccountObj.getInst()));

					if (!flBalGrp.hasField(FldBillinfoObj.getInst())) {
						if (DefaultLog.doLog(8)) {
							DefaultLog.log(8, "No Billinfo Poid");
						}
						throw new RemoteException("error.NoBillinfoPoid");
					}
					setBillinfoPoid(flBalGrp.get(FldBillinfoObj.getInst()));
				}
			}
		} catch (EBufException e) {
			if (DefaultLog.doLog(2))
				DefaultLog.log(this, 2, e);
			throw new RemoteException(e.getMessage());
		} finally {
			releaseContext(ctx);
		}
	}

	public void getBalGrpsAndSvcsForAcctAndBillInfo(Poid pAcctPoid,
			Poid pBillinfoPoid) throws RemoteException {
		PCachedContext ctx = null;
		try {
			FList flInput = new FList();
			FList flOutput = null;
			ctx = getContext();

			if ((pAcctPoid != null) && (pBillinfoPoid != null)) {
				flInput.set(FldPoid.getInst(), pBillinfoPoid);
				flInput.set(FldAccountObj.getInst(), pAcctPoid);
			}

			if (DefaultLog.doLog(8)) {
				DefaultLog.log(this, 8,
						"PCM_OP_BAL_GET_BAL_GRP_AND_SVC input FList:");

				DefaultLog.log(this, 8, flInput.toString());
			}
			flOutput = ctx.opcode(3702, flInput);
			processBalGrpAndSvcOutputFlist(flOutput);
			if (DefaultLog.doLog(8)) {
				DefaultLog.log(this, 8,
						"PCM_OP_BAL_GET_BAL_GRP_AND_SVC output FList:");

				DefaultLog.log(this, 8, flOutput.toString());
			}
		} catch (EBufException e) {
			if (DefaultLog.doLog(2))
				DefaultLog.log(this, 2, e);
			throw new RemoteException(e.getMessage());
		} finally {
			releaseContext(ctx);
		}
	}

	protected void processBalGrpAndSvcOutputFlist(FList flOut)
			throws RemoteException {
		try {
			if ((flOut != null) && (!flOut.hasField(FldResults.getInst()))) {
				if (DefaultLog.doLog(8)) {
					DefaultLog.log(8, "No Balance Group and Services");
				}
				throw new RemoteException("error.NoBalanceGroupAndServices");
			}

			Poid pBillinfoPoid = flOut.get(FldPoid.getInst());
			SparseArray resultsArray = flOut.get(FldResults.getInst());

			if (resultsArray == null) {
				throw new RemoteException("error.NoBalanceGroupAndServices");
			}

			Enumeration enumVals = resultsArray.getValueEnumerator();
			this.mBalsOfAllBalGrpsForBillinfo = new Hashtable();
			this.mSvcsForBalGrp = new Hashtable();

			while (enumVals.hasMoreElements()) {
				FList results = (FList) enumVals.nextElement();
				Poid pBalGrpPoid = results.get(FldBalGrpObj.getInst());
				Poid pSvcPoid = results.get(FldServiceObj.getInst());

				if (!this.mSvcsForBalGrp.containsKey(pBalGrpPoid)) {
					Vector vecSvcs = new Vector();
					if (!vecSvcs.contains(pSvcPoid)) {
						vecSvcs.addElement(pSvcPoid);
					}
					this.mSvcsForBalGrp.put(pBalGrpPoid, vecSvcs);
				} else {
					Vector vecSvcs = (Vector) this.mSvcsForBalGrp
							.get(pBalGrpPoid);
					if (!vecSvcs.contains(pSvcPoid)) {
						vecSvcs.addElement(pSvcPoid);
					}

					this.mSvcsForBalGrp.put(pBalGrpPoid, vecSvcs);
				}

				Hashtable hashBals = getBalancesForAcctAndBalGrpID(
						this.mAcctPoid, pBalGrpPoid);
				this.mBalsOfAllBalGrpsForBillinfo.put(pBalGrpPoid, hashBals);
			}
		} catch (EBufException e) {
			if (DefaultLog.doLog(2)) {
				DefaultLog.log(this, 2, e);
			}
			throw new RemoteException(e.getMessage());
		}
	}

	public PModelHandle getAllBillInfoAndDetailsForAcct(Poid pAcctPoid,
			Session session) throws RemoteException {
		PCachedContext ctx = null;
		try {
			FList flInput = new FList();

			if (pAcctPoid != null) {
				flInput.set(FldPoid.getInst(), pAcctPoid);
			}

			if (DefaultLog.doLog(8)) {
				DefaultLog.log(this, 8,
						"PCM_OP_BAL_GET_ACCT_BILLINFO input FList:");

				DefaultLog.log(this, 8, flInput.toString());
			}

			FList flOutBal = ExecuteOpcode.execute(3704, flInput, session);
			ctx = ExecuteOpcode.getContext(session);
			PModelHandle mhBillInfos = ctx.createModelFrom(flOutBal, "/");
			flOutBal.dump();
			if (DefaultLog.doLog(8)) {
				DefaultLog.log(this, 8,
						"PCM_OP_BAL_GET_ACCT_BILLINFO output FList:");

				DefaultLog.log(this, 8, flOutBal.toString());
			}

			return mhBillInfos;
		} catch (EBufException e) {
			if (DefaultLog.doLog(2)) {
				DefaultLog.log(this, 2, e);
			}
			throw new RemoteException(e.getMessage());
		} finally {
			ExecuteOpcode.releaseContext(ctx, session);
		}
	}

	public void getAccountDetails(Poid pAcctPoid, Session session) {

		try {
			FList input = new FList();
			input.set(FldPoid.getInst(), pAcctPoid);
			
			input.dump();
			FList flOutBal = ExecuteOpcode.execute(3, input, session);
			String status = "";
			nameInfoBean = new PNameinfoBeanP();
			nameInfoBean.setBusinessType(BusinessType.get(flOutBal.get(FldBusinessType.getInst())).toString());
			nameInfoBean.setCNIC(flOutBal.get(FldAccessCode1.getInst()));
			nameInfoBean.setCreatedDate(flOutBal.get(FldCreatedT.getInst()));
			if(flOutBal.hasField(FldStatus.getInst()) == true && flOutBal.get(FldStatus.getInst()) ==10100)
				status = "Active";
			else if(flOutBal.hasField(FldStatus.getInst()) == true && flOutBal.get(FldStatus.getInst()) ==10102)
				status = "Inactive";
			else if(flOutBal.hasField(FldStatus.getInst()) == true && flOutBal.get(FldStatus.getInst()) ==10103)
				status = "Closed";
			else 
				status = "";
			nameInfoBean.setStatus(status);
			nameInfoBean.setStatusChangeDate(flOutBal.get(FldLastStatusT.getInst()));
			nameInfoBean.setStatusNotes(flOutBal.get(FldLaststatCmnt.getInst()));
			
			SparseArray nameinfoArray = flOutBal.get(FldNameinfo.getInst());
			processAccountInfoArray(nameinfoArray);
			
			checkParent(pAcctPoid, session);
		} catch (RemoteException e) {
			if (DefaultLog.doLog(2)) {
				DefaultLog.log(this, 2, e);
			}

		} catch (EBufException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {

		}
	}
	
	public FList checkParent(Poid accountNo, Session session) throws RemoteException {
        FList FListIn = new FList();
        FList FListOut = new FList();
        FListIn.set(FldPoid.getInst(), accountNo);
        
        try {
            FListOut = ExecuteOpcode.execute(PortalOp.BILL_GROUP_GET_PARENT, FListIn,session);
            if(FListOut.hasField(FldParent.getInst()))
            	nameInfoBean.setParentAccount(FListOut.get(FldParent.getInst()));
            FListOut.dump();
        } catch (EBufException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  finally {
            
        }
        return FListOut;
    }
	

	public boolean performParentCheck(Session session) {
        boolean isParent = false;
        try {
            FList flOut = getDuplicateAccountSearch(session);
            if (flOut.hasField(FldResults.getInst())) {
                SparseArray parentArray = flOut.get(FldResults.getInst());
                for (int i = 0; i < parentArray.size(); i++) {
                    FList res = parentArray.elementAt(i);
                    
                    Poid parentPoid = res.get(FldGroupObj.getInst());
                    if (parentPoid.getId() > 0) {
                    	nameInfoBean.setParentAccount(true);
                    }
                }
            }
        } catch (Exception e) {
            e.getMessage();
        }
        return isParent;
    }
	
	public FList getDuplicateAccountSearch(Session session) throws RemoteException {
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
        argsList.set(FldAccessCode1.getInst(), nameInfoBean.getCNIC());
        argsArray.add(1, argsList);
        String searchTmpt = "select X from /account where upper( F1 ) = upper( V1 ) ";
        FListIn.set(FldPoid.getInst(), searchPoid);
        FListIn.set(FldFlags.getInst(), 256);
        FListIn.set(FldTemplate.getInst(), searchTmpt);
        FListIn.set(FldResults.getInst(), resArray);
        FListIn.set(FldArgs.getInst(), argsArray);

        try {
            FListOut = ExecuteOpcode.execute(PortalOp.SEARCH, FListIn,session);
            FListOut.dump();
        }  finally {
            
        }
        return FListOut;
    }
	public void processOutputFlistOfBillinfos(PModelHandle mhBillInfos,
			Session session) throws RemoteException {
		PCachedContext ctx = null;
		try {
			ctx = ExecuteOpcode.getContext(session);

			FList flBillinfos = null;

			if (mhBillInfos != null) {
				flBillinfos = (FList) ctx.lookupModel(mhBillInfos);

				if ((flBillinfos != null)
						&& (!flBillinfos.hasField(FldBillinfo.getInst()))) {
					if (DefaultLog.doLog(8)) {
						DefaultLog.log(8, "No Billinfos");
					}
					throw new RemoteException("error.NoBillinfos");
				}

				Poid pAcctPoid = flBillinfos.get(FldPoid.getInst());
				SparseArray billinfoArray = flBillinfos.get(FldBillinfo
						.getInst());
				SparseArray nameinfoArray = flBillinfos.get(FldNameinfo
						.getInst());

				if (billinfoArray == null) {
					throw new RemoteException("error.NoBillinfos");
				}

				processNameinfoArray(nameinfoArray);
				processBillinfoArray(billinfoArray);
			}
		} catch (EBufException e) {
			if (DefaultLog.doLog(2))
				DefaultLog.log(this, 2, e);
			throw new RemoteException(e.getMessage());
		} finally {
			ExecuteOpcode.releaseContext(ctx, session);
		}
	}
	
	protected void processAccountInfoArray(SparseArray nameinfoArray)
			throws RemoteException {
		try {
			Enumeration enumNameKeys = nameinfoArray.getKeyEnumerator();
			Enumeration enumName = nameinfoArray.getValueEnumerator();
			
			while (enumName.hasMoreElements()) {
				Integer nameKey = (Integer) enumNameKeys.nextElement();
				FList flNameinfo = (FList) enumName.nextElement();

				if (flNameinfo.get(FldAddress.getInst()) != null) {
					nameInfoBean
							.setAddress(flNameinfo.get(FldAddress.getInst()));
				}

				if (flNameinfo.get(FldCanonCompany.getInst()) != null) {
					nameInfoBean.setCanonCompany(flNameinfo.get(FldCanonCompany
							.getInst()));
				}

				if (flNameinfo.get(FldCanonCountry.getInst()) != null) {
					nameInfoBean.setCanonCountry(flNameinfo.get(FldCanonCountry
							.getInst()));
				}

				if (flNameinfo.get(FldCity.getInst()) != null) {
					nameInfoBean.setCity(flNameinfo.get(FldCity.getInst()));
				}

				if (flNameinfo.get(FldCompany.getInst()) != null) {
					nameInfoBean
							.setCompany(flNameinfo.get(FldCompany.getInst()));
				}

				if (flNameinfo.get(FldContactType.getInst()) != null) {
					nameInfoBean.setContactType(flNameinfo.get(FldContactType
							.getInst()));
				}

				if (flNameinfo.get(FldEmailAddr.getInst()) != null) {
					nameInfoBean.setEmailAddr(flNameinfo.get(FldEmailAddr
							.getInst()));
				}

				if (flNameinfo.get(FldFirstCanon.getInst()) != null) {
					nameInfoBean.setFirstCanon(flNameinfo.get(FldFirstCanon
							.getInst()));
				}

				if (flNameinfo.get(FldFirstName.getInst()) != null) {
					nameInfoBean.setFirstName(flNameinfo.get(FldFirstName
							.getInst()));
				}

				if (flNameinfo.get(FldLastCanon.getInst()) != null) {
					nameInfoBean.setLastCanon(flNameinfo.get(FldLastCanon
							.getInst()));
				}

				if (flNameinfo.get(FldLastName.getInst()) != null) {
					nameInfoBean.setLastName(flNameinfo.get(FldLastName
							.getInst()));
				}

				if (flNameinfo.get(FldMiddleCanon.getInst()) != null) {
					nameInfoBean.setMiddleCanon(flNameinfo.get(FldMiddleCanon
							.getInst()));
				}

				if (flNameinfo.get(FldMiddleName.getInst()) != null) {
					nameInfoBean.setMiddleName(flNameinfo.get(FldMiddleName
							.getInst()));
				}

				if (flNameinfo.get(FldSalutation.getInst()) != null) {
					nameInfoBean.setSalutation(flNameinfo.get(FldSalutation
							.getInst()));
				}

				if (flNameinfo.get(FldState.getInst()) != null) {
					nameInfoBean.setState(flNameinfo.get(FldState.getInst()));
				}

				if (flNameinfo.get(FldTitle.getInst()) != null) {
					nameInfoBean.setTitle(flNameinfo.get(FldTitle.getInst()));
				}

				if (flNameinfo.get(FldZip.getInst()) != null) {
					nameInfoBean.setZip(flNameinfo.get(FldZip.getInst()));
				}

				FList hPhone = (FList) flNameinfo.get(FldPhones.getInst()).get(
						1);
				if (hPhone != null)
					nameInfoBean.setHomePhone(hPhone.get(FldPhone.getInst()));
				hPhone.dump();
				FList mPhone = (FList) flNameinfo.get(FldPhones.getInst()).get(
						5);
				if (mPhone != null)
					nameInfoBean.setMobile(mPhone.get(FldPhone.getInst()));
				mPhone.dump();

				nameInfoBean.setServiceObj(flNameinfo.get(FldServiceObj
						.getInst()));

				break;
			}
		} catch (EBufException e) {
			if (DefaultLog.doLog(2))
				DefaultLog.log(this, 2, e);
			throw new RemoteException(e.getMessage());
		}
	}
	

	protected void processNameinfoArray(SparseArray nameinfoArray)
			throws RemoteException {
//		try {
//			Enumeration enumNameKeys = nameinfoArray.getKeyEnumerator();
//			Enumeration enumName = nameinfoArray.getValueEnumerator();
//			nameInfoBean = new PNameinfoBeanP();
//
//			while (enumName.hasMoreElements()) {
//				Integer nameKey = (Integer) enumNameKeys.nextElement();
//				FList flNameinfo = (FList) enumName.nextElement();
//
//				if (flNameinfo.get(FldAddress.getInst()) != null) {
//					nameInfoBean
//							.setAddress(flNameinfo.get(FldAddress.getInst()));
//				}
//
//				if (flNameinfo.get(FldCanonCompany.getInst()) != null) {
//					nameInfoBean.setCanonCompany(flNameinfo.get(FldCanonCompany
//							.getInst()));
//				}
//
//				if (flNameinfo.get(FldCanonCountry.getInst()) != null) {
//					nameInfoBean.setCanonCountry(flNameinfo.get(FldCanonCountry
//							.getInst()));
//				}
//
//				if (flNameinfo.get(FldCity.getInst()) != null) {
//					nameInfoBean.setCity(flNameinfo.get(FldCity.getInst()));
//				}
//
//				if (flNameinfo.get(FldCompany.getInst()) != null) {
//					nameInfoBean
//							.setCompany(flNameinfo.get(FldCompany.getInst()));
//				}
//
//				if (flNameinfo.get(FldContactType.getInst()) != null) {
//					nameInfoBean.setContactType(flNameinfo.get(FldContactType
//							.getInst()));
//				}
//
//				if (flNameinfo.get(FldEmailAddr.getInst()) != null) {
//					nameInfoBean.setEmailAddr(flNameinfo.get(FldEmailAddr
//							.getInst()));
//				}
//
//				if (flNameinfo.get(FldFirstCanon.getInst()) != null) {
//					nameInfoBean.setFirstCanon(flNameinfo.get(FldFirstCanon
//							.getInst()));
//				}
//
//				if (flNameinfo.get(FldFirstName.getInst()) != null) {
//					nameInfoBean.setFirstName(flNameinfo.get(FldFirstName
//							.getInst()));
//				}
//
//				if (flNameinfo.get(FldLastCanon.getInst()) != null) {
//					nameInfoBean.setLastCanon(flNameinfo.get(FldLastCanon
//							.getInst()));
//				}
//
//				if (flNameinfo.get(FldLastName.getInst()) != null) {
//					nameInfoBean.setLastName(flNameinfo.get(FldLastName
//							.getInst()));
//				}
//
//				if (flNameinfo.get(FldMiddleCanon.getInst()) != null) {
//					nameInfoBean.setMiddleCanon(flNameinfo.get(FldMiddleCanon
//							.getInst()));
//				}
//
//				if (flNameinfo.get(FldMiddleName.getInst()) != null) {
//					nameInfoBean.setMiddleName(flNameinfo.get(FldMiddleName
//							.getInst()));
//				}
//
//				if (flNameinfo.get(FldSalutation.getInst()) != null) {
//					nameInfoBean.setSalutation(flNameinfo.get(FldSalutation
//							.getInst()));
//				}
//
//				if (flNameinfo.get(FldState.getInst()) != null) {
//					nameInfoBean.setState(flNameinfo.get(FldState.getInst()));
//				}
//
//				if (flNameinfo.get(FldTitle.getInst()) != null) {
//					nameInfoBean.setTitle(flNameinfo.get(FldTitle.getInst()));
//				}
//
//				if (flNameinfo.get(FldZip.getInst()) != null) {
//					nameInfoBean.setZip(flNameinfo.get(FldZip.getInst()));
//				}
//
//				FList hPhone = (FList) flNameinfo.get(FldPhones.getInst()).get(
//						1);
//				if (hPhone != null)
//					nameInfoBean.setHomePhone(hPhone.get(FldPhone.getInst()));
//				hPhone.dump();
//				FList mPhone = (FList) flNameinfo.get(FldPhones.getInst()).get(
//						5);
//				if (mPhone != null)
//					nameInfoBean.setMobile(mPhone.get(FldPhone.getInst()));
//				mPhone.dump();
//
//				nameInfoBean.setServiceObj(flNameinfo.get(FldServiceObj
//						.getInst()));
//
//				break;
//			}
//		} catch (EBufException e) {
//			if (DefaultLog.doLog(2))
//				DefaultLog.log(this, 2, e);
//			throw new RemoteException(e.getMessage());
//		}
	}

	protected void processBillinfoArray(SparseArray billInfoArray)
			throws RemoteException {
		try {
			Enumeration enumBillKeys = billInfoArray.getKeyEnumerator();
			Enumeration enumBills = billInfoArray.getValueEnumerator();
			this.mAcctLvlBillinfos = new Hashtable();
			this.mSvcLvlBillinfos = new Hashtable();

			while (enumBills.hasMoreElements()) {
				Integer billKey = (Integer) enumBillKeys.nextElement();
				FList flBillinfo = (FList) enumBills.nextElement();

				PBillInfoBean billInfoBean = new PBillInfoBean();

				billInfoBean.setBillinfoPoid(flBillinfo.get(FldPoid.getInst()));
				billInfoBean.setBillPoid(flBillinfo.get(FldBillObj.getInst()));
				billInfoBean.setPayType(flBillinfo.get(FldPayType.getInst())
						.intValue());
				billInfoBean.setFlags(flBillinfo.get(FldFlags.getInst())
						.intValue());
				billInfoBean
						.setNextBillT(flBillinfo.get(FldNextBillT.getInst()));
				billInfoBean
						.setLastBillT(flBillinfo.get(FldLastBillT.getInst()));

				if (flBillinfo.get(FldFlags.getInst()).intValue() == 0)
					this.mSvcLvlBillinfos.put(
							flBillinfo.get(FldPoid.getInst()), billInfoBean);
				else
					this.mAcctLvlBillinfos.put(
							flBillinfo.get(FldPoid.getInst()), billInfoBean);
			}
		} catch (EBufException e) {
			if (DefaultLog.doLog(2))
				DefaultLog.log(this, 2, e);
			throw new RemoteException(e.getMessage());
		}
	}

	public String getPrimaryCurrencySymbol() {
		if (DefaultLog.doLog(8)) {
			DefaultLog.log(8, "WebKit:***getPrimaryCurrencySymbol()***\n");
		}

		return this.mPrimaryCurrencySymbol;
	}

	public BigDecimal getBalanceSummaryForBillInfo(Poid billInfoPoid,
			int nIncludeChildren) throws RemoteException {
		PCachedContext conn = null;
		try {
			FList flInput = new FList();
			conn = getContext();

			flInput.set(FldPoid.getInst(), billInfoPoid);
			flInput.set(FldIncludeChildren.getInst(), nIncludeChildren);
			if (DefaultLog.doLog(8)) {
				DefaultLog.log(this, 8, "AR_GET_BAL_SUMMARY input FList:");
				DefaultLog.log(this, 8, flInput.toString());
			}
			FList flOutBal = conn.opcode(1321, flInput);

			BigDecimal bdAllBillDue = flOutBal.get(FldOpenbillDue.getInst());

			BigDecimal bdUnapplied = flOutBal.get(FldUnappliedAmount.getInst());

			Integer pendingRecords = flOutBal.hasField(FldTotalRecords
					.getInst()) ? flOutBal.get(FldTotalRecords.getInst())
					: null;

			BigDecimal bdDueNow = bdAllBillDue.add(bdUnapplied);
			BigDecimal bdPending = flOutBal.get(FldPendingbillDue.getInst());
			BigDecimal bdTotal = bdDueNow.add(bdPending);
			if (DefaultLog.doLog(8)) {
				DefaultLog.log(this, 8, "AR_GET_BAL_SUMMARY output FList:");
				DefaultLog.log(this, 8, flOutBal.toString());
			}
			BigDecimal localBigDecimal1 = bdTotal;
			return localBigDecimal1;
		} catch (EBufException e) {
			if (DefaultLog.doLog(2))
				DefaultLog.log(this, 2, e);
			throw new RemoteException(e.getMessage());
		} finally {
			releaseContext(conn);
		}
	}
}