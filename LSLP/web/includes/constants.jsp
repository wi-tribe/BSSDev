<%!
    //Add by Ramesh - Start
    private static final String EMAIL_FROM = "noreply@wi-tribe.pk";
    private static final String EMAIL_WELCOME_SUBJECT = "Welcome to Wi-tribe";
    private static final String EMAIL_AUTH_MAIL_SUBJECT = "Authentication Information for Wi-tribe My Account";
    private static final String EMAIL_AUTHENTICATION_REQUIRED = "true";
    private static final String TPIN = "tpin";
    private static final String PREPAID_ACCOUNT = "1";
    private static final String POSTPAID_ACCOUNT = "2";
    private static final String WTB_ACCT_SERVICE_TYPE = "accountservicetype";
    private static final String WTB_PLAN_SEL = "planselected";
    private static final String WTB_DOB = "txtDateofbirth";
    private static final String WTB_BUS_TYPE = "business_type";
    private static final String IDENTITY = "identity";
    private static final String COVERAGE_TYPE="coverage_type";
    private static final String LEAD_IDENTITY="leadid";
    private static final String SLA="sla";
    private static final String LONGITUDE="longitude";
    private static final String LATITUDE="latitude";
    private static final String CONTRACT_PERIOD="contract_period";
    // add by muralidhar - start
    private static final String PAYMENT_ALERT="slsubscribe";
    //ended by muralidhar
    private static final String CONTRACT_START="contract_start";
    private static final String CONTRACT_END="contract_end";
    private static final String PROMO_PKG="promo_pkg";
    private static final String ACCT_USAGE_LIMIT="aac_source";
    private static final String SVS_LOGIN_TYPE="svslogintype";
    private static final String TAX_EXPT_TYPE="execpttype";
    private static final String TAX_EXPT_PERCENT="execptpercent";
    private static final String CONTRACT_USAGE_LIMIT="usagelimit";
    private static final String CPE_MAC_ADDRESS="macaddress";
    private static final String CPE_SERIAL_NUMBER="serialnumber";
    private static final String CPE_MAC_STATUS="MacStatus";
    private static final String CPE_DEVICE_ID="macdeviceid";
    private static final String SALES_ID="salesid";
    private static final String NEW_ACCT_BY="newacctffrom";
    private static final String ACCOUNT_SERVICE_TYPE_PREPAID="1";
    private static final String ACCOUNT_SERVICE_TYPE_POSTPAID="2";
    private static final String PAYMETHODNAME="paymentMethod_Name";
    private static final String BILLINFOPOID="billinfo_poid";
    private static final String BILLINFOID="billinfo_id";
    // Add by Ramesh - End
    // add by muralidhar - start
    private static final String WTB_CLIENT_APP_NAME="LSLP";
    //Add by muralidhar - End
    private static final String CONNECTION = "connection";
    private static final String CREATE_CONNECTION = "createConnection";
    private static final String CREATE_LISTENER = "createListener";
    private static final String BRANDBEAN = "brandBean";
    private static final String BILL_BILL_ID = "billinfoidentity";
    private static final String BILL_POID = "billinfopoid";
    // prepaid fields
    private static final String PP_NAME="pp_name";
    private static final String PP_EMAIL="pp_email";
    private static final String PP_ADDR="pp_addr";
    private static final String PP_CITY="pp_city";
    private static final String PP_STATE="pp_state";
    private static final String PP_COUNTRY="pp_country";
    private static final String PP_ZIP="pp_zip";

    private static final String SALUTE = "salute";
    private static final String FIRSTNAME = "firstname";
    private static final String MIDDLENAME = "middlename";
    private static final String LASTNAME = "lastname";
    private static final String TITLE = "title";
    private static final String COMPANY = "company";
    private static final String EMAIL = "email";
    private static final String ADDRESS1 = "address1";
    private static final String CITY = "city";
    private static final String STATE = "state";
    private static final String ZIP = "zip";
    private static final String COUNTRY = "country";
    private static final String LOCALE = "locale";
    private static final String BILL = "bill";
    private static final String CURRENCY = "currency";
    private static final String INVCITY = "invoice_city";
    private static final String INVSTATE = "invoice_state";
    private static final String INVZIP = "invoice_zip";
    private static final String INVCOUNTRY = "invoice_country";
    private static final String INVNAME = "invoice_name";
    private static final String DELIVERMETHOD = "Deliver_method";
    private static final String INVADDRESS1 = "invoice_address1";
    private static final String INVEMAIL = "invoice_email";
    private static final String BANK = "bank";
    private static final String ACCTNUM = "account_num";
    private static final String ACCTTYPE = "account_type";
    private static final String DDNAME = "dd_name";
    private static final String DDSTATE = "dd_state";
    private static final String DDZIP = "dd_zip";
    private static final String DDCOUNTRY = "dd_country";
    private static final String DDCITY = "dd_city";
    private static final String DDADDR1 = "dd_address1";
    private static final String CCNAME = "cc_name";
    private static final String CCCITY = "cc_city";
    private static final String CCSTATE = "cc_state";
    private static final String CCZIP = "cc_zip";
    private static final String CCCOUNTRY = "cc_country";
    private static final String CCADDR1 = "cc_address1";
    private static final String DEBITNUM = "debitnum";
    private static final String CV = "cv";
    private static final String DEBITEXP = "debitexp";
    private static final String BILLNAME = "bill_name";
    private static final String BILLSAME = "bill_same";
    private static final String BILLCITY = "bill_city";
    private static final String BILLSTATE = "bill_state";
    private static final String BILLCOUNTRY = "bill_country";
    private static final String BILLZIP = "bill_zip";
    private static final String BILLADDR1 = "bill_address1";
    private static final String BILLEMAIL = "bill_email";

    private static final String DELIVERSAME = "deliversame";
    private static final String PRICEPLANSPECIFIER = "pricingPlan";
    private static final String DEFAULTPRICEPLAN = "default-new";

    // by Ramesh
    private static final String CURRENCYCODE = "localCurrency";
    private static final String NEWACCOUNTAPPLICATION="applicationName";
    private static final String NEWACCOUNTLEAD="DealersWebApplication";

    private static final String DEFAULTCOUNTRY = "Pakistan";
    private static final String WINDOW_TITLE = "Wi-tribe-Pakistan";

    private static final String INVOICE = "10001";
    private static final String CREDIT = "10003";
    private static final String DEBIT = "10005";
    private static final String PREPAID = "10000";

    private static final String ON = "on";
    private static final String OFF = "off";

    private static final String HOMEPHONE = "1";
    private static final String WORKPHONE = "2";
    private static final String FAX = "3";
    private static final String PAGER = "4";
    private static final String MOBILE = "5";

    // from pcm.h for online payment result
    private static final int PIN_RESULT_FAIL = 0;
    private static final int PIN_RESULT_PASS = 1;

    private static final String AACACCESS = "aacaccess";
    // regkit constants
    private static final String VALIDPLANS = "validplans";
    private static final String VALIDBILLTYPES = "validbilltypes";
    private static final String INVOICEMONTHLY = "INVOICE MONTHLY";
    private static final String CREDITCARD = "CREDIT CARD";
    private static final String PREPAIDPAYMENT = "PREPAID";
    private static final String DIRECTDEBIT = "DIRECT DEBIT";
    private static final String VALIDPLANSINDEX = "ValidPlanIndex";
    
    private static final String CURRENCY_CODE = "PKR";
    
    //Account Hierarchy Constants
    private static final String PARENT = "parent";



%>

