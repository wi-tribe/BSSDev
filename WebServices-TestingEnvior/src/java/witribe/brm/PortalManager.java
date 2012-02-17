package witribe.brm;

import com.portal.pcm.EBufException;
import com.portal.pcm.FList;
import com.portal.pcm.PortalContext;
import java.util.Properties;
//import utils.AppLogger;
import utils.MyLog4j;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author PKFKhan
 */
public class PortalManager {

    private PortalContext ctx;
    private Properties props = new Properties();
    /**
     * 
     */
    public Exception ebuf = null;
    
    MyLog4j logg = new MyLog4j();

    /**
     * 
     * @return
     */
    public Exception getEbuf() {
        return ebuf;
    }
    
    /**
     * 
     */
    public PortalManager() {
        try {
            ctx = new PortalContext();
//            AppLogger.log(1, "PCM connection established successfully.");

        } catch (EBufException e) {
  //          AppLogger.log(3, "Unable to initialize PortalContext.\n" + e.toString());
            logg.smsLogger("","FUNCTION:PortalManager(), Exception", e.toString());
            //System.err.print("Create context:" + e.getMessage());
            ebuf=e;
        } catch (Exception e) {
            //AppLogger.log(3, e.toString());
            logg.smsLogger("","Exception:PortalManager()", e.toString());
        }
    }

    /**
     * 
     * @param username
     * @param password
     * @param server
     * @param port
     */
    public PortalManager(String username, String password, String server, String port) {

        //props.put("infranet.connection", "pcp://pkFkhan:fahad123@10.1.67.28:11960/service/admin_client 1");
        props.put("infranet.connection", "pcp://" + username + ":" + password + "@" + server + ":" + port + "/service/pcm_client 1");
        props.put("infranet.login.type", "1");
        props.put("infranet.log.file", "javaPCM.log");
        props.put("infranet.log.level", "3");
        props.put("infranet.custom.field.package", "com.wtb.flds");
        props.put("infranet.custom.field.10001", "WTB_FLD_SALES_ID");
        props.put("infranet.custom.field.10003", "WTB_FLD_SUB_TYPE");
        props.put("infranet.custom.field.10004", "WTB_FLD_THROUPUT");
        props.put("infranet.custom.field.10005", "WTB_FLD_ROUTER_DETAILS");
        props.put("infranet.custom.field.10006", "WTB_FLD_CPE_DETAILS");
        props.put("infranet.custom.field.10007", "WTB_FLD_MAC_ADDRESS_LAN");
        props.put("infranet.custom.field.10008", "WTB_FLD_MAC_ADDRESS_WAN");
        props.put("infranet.custom.field.10009", "WTB_FLD_BRMPROCESS_STATUS");
        props.put("infranet.custom.field.10011", "WTB_FLD_DIVERSION_REASON");
        props.put("infranet.custom.field.10012", "WTB_FLD_REQUESTID");
        props.put("infranet.custom.field.10013", "WTB_FLD_REQUESTDATE");
        props.put("infranet.custom.field.10014", "WTB_FLD_INVENTORY_TYPE");
        props.put("infranet.custom.field.10015", "WTB_FLD_QUANTITY_ORDERED");
        props.put("infranet.custom.field.10016", "WTB_FLD_REQUIREDBYDATE");
        props.put("infranet.custom.field.10017", "WTB_FLD_PROCESS_STATUS");
        props.put("infranet.custom.field.10018", "WTB_FLD_PROCESSEDDATE");
        props.put("infranet.custom.field.10019", "WTB_FLD_QUANTITY_FROM_ERP");
        props.put("infranet.custom.field.10020", "WTB_FLD_QUANTITY_PROCESSED");
        props.put("infranet.custom.field.10021", "WTB_FLD_FROMSHOPID");
        props.put("infranet.custom.field.10022", "WTB_FLD_TOSHOPID");
        props.put("infranet.custom.field.10023", "WTB_FLD_USR_DEFINED1");
        props.put("infranet.custom.field.10024", "WTB_FLD_USR_DEFINED2");
        props.put("infranet.custom.field.10025", "WTB_FLD_USR_DEFINED3");
        props.put("infranet.custom.field.10026", "WTB_FLD_USR_DEFINED4");
        props.put("infranet.custom.field.10027", "WTB_FLD_USR_DEFINED5");
        props.put("infranet.custom.field.10028", "WTB_FLD_OTHER_DETAILS");
        props.put("infranet.custom.field.10029", "WTB_FLD_REL_CALL_REF");
        props.put("infranet.custom.field.10030", "WTB_FLD_CALL_PROP");
        props.put("infranet.custom.field.10031", "WTB_FLD_ACCT_REC_TYPE");
        props.put("infranet.custom.field.10032", "WTB_FLD_ROLE_NODE");
        props.put("infranet.custom.field.10033", "WTB_FLD_SESSION_ID");
        props.put("infranet.custom.field.10034", "WTB_FLD_SALES_ID_DOC_SUBMIT");
        props.put("infranet.custom.field.10035", "WTB_FLD_SALES_ID_DOC_RECEIVE");
        props.put("infranet.custom.field.10036", "WTB_FLD_SERV_START_T");
        props.put("infranet.custom.field.10037", "WTB_FLD_SERV_END_T");
        props.put("infranet.custom.field.10038", "WTB_FLD_SALES_ID_DOC_VERIFY");
        props.put("infranet.custom.field.10039", "WTB_FLD_IMS_CHARGING_ID");
        props.put("infranet.custom.field.10040", "WTB_FLD_LIST_MSG_BODIES");
        props.put("infranet.custom.field.10041", "WTB_FLD_SERV_CONTEXT_ID");
        props.put("infranet.custom.field.10042", "WTB_FLD_SALES_ID_DOC_QUALITY");
        props.put("infranet.custom.field.10043", "WTB_FLD_LIST_CALL_ASS_ID");
        props.put("infranet.custom.field.10044", "WTB_FLD_ABNORMAL_FINISH");
        props.put("infranet.custom.field.10045", "WTB_FLD_CHARG_CAT");
        props.put("infranet.custom.field.10046", "WTB_FLD_ONLINE_CHARG_FLAG");
        props.put("infranet.custom.field.10047", "WTB_FLD_VISITED_NW_ID");
        props.put("infranet.custom.field.10048", "WTB_FLD_CAUSE_CLOSE");
        props.put("infranet.custom.field.10049", "WTB_FLD_REC_SEQ_NO");
        props.put("infranet.custom.field.10050", "WTB_FLD_LOCAL_REC_NO");
        props.put("infranet.custom.field.10051", "WTB_FLD_SDP_MEDIA_ID");
        props.put("infranet.custom.field.10052", "WTB_FLD_DT_DOC_SUBMIT");
        props.put("infranet.custom.field.10053", "WTB_FLD_DT_DOC_RECEIVE");
        props.put("infranet.custom.field.10054", "WTB_FLD_DT_DOC_VERIFY");
        props.put("infranet.custom.field.10055", "WTB_FLD_DT_DOC_QUALITY");
        props.put("infranet.custom.field.10101", "WTB_FLD_MSGS");
        props.put("infranet.custom.field.10102", "WTB_FLD_CITY");
        props.put("infranet.custom.field.10103", "WTB_FLD_MSG");
        props.put("infranet.custom.field.10104", "WTB_FLD_CITY_CODES");
        props.put("infranet.custom.field.10105", "WTB_FLD_CITY_CODE");
        props.put("infranet.custom.field.10106", "WTB_FLD_PROVINCE_CODE");
        props.put("infranet.custom.field.10107", "WTB_FLD_PROVINCE");
        props.put("infranet.custom.field.10108", "WTB_FLD_COMMUNICATION_PARAMS");
        props.put("infranet.custom.field.10109", "WTB_FLD_COMMUNICATION_TYPE");
        props.put("infranet.custom.field.10110", "WTB_FLD_HOST");
        props.put("infranet.custom.field.10111", "WTB_FLD_PORT");
        props.put("infranet.custom.field.10112", "WTB_FLD_USERNAME");
        props.put("infranet.custom.field.10113", "WTB_FLD_PASSWORD");
        props.put("infranet.custom.field.10114", "WTB_FLD_REASON_CODES");
        props.put("infranet.custom.field.10115", "WTB_FLD_REASON_CODE");
        props.put("infranet.custom.field.10116", "WTB_FLD_AMOUNT");
        props.put("infranet.custom.field.10121", "WTB_FLD_TAX_EXEMPTIONS");
        props.put("infranet.custom.field.10122", "WTB_FLD_EXEMPTION_TYPE");
        props.put("infranet.custom.field.10123", "WTB_FLD_PERCENT");
        props.put("infranet.custom.field.10124", "WTB_FLD_BRACKET_INFO");
        props.put("infranet.custom.field.10201", "WTB_FLD_VOIP_FORMAT");
        props.put("infranet.custom.field.10202", "WTB_FLD_SIP_FORMAT");
        props.put("infranet.custom.field.10203", "WTB_FLD_TEL_FORMAT");
        props.put("infranet.custom.field.10204", "WTB_FLD_PREFIX_FORMAT");
        props.put("infranet.custom.field.10205", "WTB_FLD_DOMAIN_FORMAT");
        props.put("infranet.custom.field.10206", "WTB_FLD_COUNTRY_CODE");
        props.put("infranet.custom.field.10207", "WTB_FLD_VOIP_LINE_INFO");
        props.put("infranet.custom.field.10208", "WTB_FLD_SUPPL_SERV_INFO");
        props.put("infranet.custom.field.10209", "WTB_FLD_VAS_SERV_INFO");
        props.put("infranet.custom.field.10210", "WTB_FLD_CORRELATION_TAG");
        props.put("infranet.custom.field.10211", "WTB_FLD_VOIP_SUB_STATUS");
        props.put("infranet.custom.field.10212", "WTB_FLD_SERVICES");
        props.put("infranet.custom.field.10216", "WTB_FLD_CNIC_PASSPORT");
        props.put("infranet.custom.field.10217", "WTB_FLD_INSTALL_FLAG");
        props.put("infranet.custom.field.10219", "WTB_FLD_IS_TRANSPARENT");
        props.put("infranet.custom.field.10224", "WTB_FLD_LOCAL");
        props.put("infranet.custom.field.10225", "WTB_FLD_LOCATION_LOCK");
        props.put("infranet.custom.field.10226", "WTB_FLD_SUBSCRIPTION_ID");
        props.put("infranet.custom.field.10228", "WTB_FLD_GATEWAY_IP");
        props.put("infranet.custom.field.10235", "WTB_FLD_MEID");
        props.put("infranet.custom.field.10236", "WTB_FLD_VOIP_UNAME");
        props.put("infranet.custom.field.10237", "WTB_FLD_VOIP_PASSWD");
        props.put("infranet.custom.field.10238", "WTB_FLD_IMPU");
        props.put("infranet.custom.field.10239", "WTB_FLD_IMPI");
        props.put("infranet.custom.field.10240", "WTB_FLD_IMPULIST");
        props.put("infranet.custom.field.10241", "WTB_FLD_CSC");
        props.put("infranet.custom.field.10250", "WTB_FLD_NAS_PORT");
        props.put("infranet.custom.field.10251", "WTB_FLD_L2_UID");
        props.put("infranet.custom.field.10252", "WTB_FLD_L1_UID");
        props.put("infranet.custom.field.10253", "WTB_FLD_L1_PASSWD");
        props.put("infranet.custom.field.10254", "WTB_FLD_L2_PASSWD");
        props.put("infranet.custom.field.10257", "WTB_FLD_SERVICE_ID");
        props.put("infranet.custom.field.10258", "WTB_FLD_VIP_STATUS");
        props.put("infranet.custom.field.10259", "WTB_FLD_QOS_REF");
        props.put("infranet.custom.field.10260", "WTB_FLD_NAS_ID");
        props.put("infranet.custom.field.10261", "WTB_FLD_SERVICE_CODE");
        props.put("infranet.custom.field.10262", "WTB_FLD_ORD_RESULT");
        props.put("infranet.custom.field.10263", "WTB_FLD_OSM_ORDER_ID");
        props.put("infranet.custom.field.10264", "WTB_FLD_BRM_ORDER_ID");
        props.put("infranet.custom.field.10265", "WTB_FLD_SERV_PROV_STATUS");
        props.put("infranet.custom.field.10266", "WTB_FLD_LP_NUMBER");
        props.put("infranet.custom.field.10267", "WTB_FLD_POP3HOST");
        props.put("infranet.custom.field.10268", "WTB_FLD_LOGIN_NAME");
        props.put("infranet.custom.field.10269", "WTB_FLD_LOGIN_PASSWD");
        props.put("infranet.custom.field.10270", "WTB_FLD_CORRELATION_ID");
        props.put("infranet.custom.field.10271", "WTB_FLD_MSG_TYPE");
        props.put("infranet.custom.field.10272", "WTB_FLD_IS_WS_CALL");
        props.put("infranet.custom.field.10273", "WTB_FLD_PROD_UPD_FLAG");
        props.put("infranet.custom.field.10274", "WTB_FLD_SUBORDER_TYPE");
        props.put("infranet.custom.field.10301", "WTB_FLD_PPD_INFO");
        props.put("infranet.custom.field.10302", "WTB_FLD_PYMT_DUEDAYS");
        props.put("infranet.custom.field.10303", "WTB_FLD_PYMT_DAYS");
        props.put("infranet.custom.field.10304", "WTB_FLD_PYMT_TYPE");
        props.put("infranet.custom.field.10310", "WTB_FLD_INSTALLMENT_MONTHS");
        props.put("infranet.custom.field.10311", "WTB_FLD_INSTALLMENT_AMOUNT");
        props.put("infranet.custom.field.10401", "WTB_FLD_SVC_INFO");
        props.put("infranet.custom.field.10403", "WTB_FLD_SESSION_ACTIVE");
        props.put("infranet.custom.field.10404", "WTB_FLD_PROFILE_ID");
        props.put("infranet.custom.field.10458", "WTB_FLD_SMS");
        props.put("infranet.custom.field.10459", "WTB_FLD_EMAIL");
        props.put("infranet.custom.field.10460", "WTB_FLD_BARRING_ACTION");
        props.put("infranet.custom.field.10601", "WTB_FLD_SERVICE_ANTIVIRUS");
        props.put("infranet.custom.field.10702", "WTB_FLD_CUSTINFO");
        props.put("infranet.custom.field.10703", "WTB_FLD_DOB_T");
        props.put("infranet.custom.field.10704", "WTB_FLD_ACCOUNT_SERVICE_TYPE");
        props.put("infranet.custom.field.10705", "WTB_FLD_CT_LATITUDE");
        props.put("infranet.custom.field.10706", "WTB_FLD_CT_LONGITUDE");
        props.put("infranet.custom.field.10707", "WTB_FLD_CT_COVERAGE_TYPE");
        props.put("infranet.custom.field.10708", "WTB_FLD_CT_SLA");
        props.put("infranet.custom.field.10709", "WTB_FLD_CUSTOMERID");
        props.put("infranet.custom.field.10710", "WTB_FLD_REASON");
        props.put("infranet.custom.field.10711", "WTB_FLD_BLKLIST_CUSTINFO");
        props.put("infranet.custom.field.10712", "WTB_FLD_BLOCKEDIP_LIST");
        props.put("infranet.custom.field.10713", "WTB_FLD_HOST_IP");
        props.put("infranet.custom.field.10714", "WTB_FLD_ATTEMPT");
        props.put("infranet.custom.field.10715", "WTB_FLD_LAST_ATTEMPT_TIME");
        props.put("infranet.custom.field.10716", "WTB_FLD_CONTRACT_DETAILS");
        props.put("infranet.custom.field.10717", "WTB_FLD_CONTRACT_END_T");
        props.put("infranet.custom.field.10718", "WTB_FLD_CONTRACT_START_T");
        props.put("infranet.custom.field.10719", "WTB_FLD_CONTRACT_PERIOD");
        props.put("infranet.custom.field.10720", "WTB_FLD_RENEWAL_MONTHS");
        props.put("infranet.custom.field.10721", "WTB_FLD_LAST_PAID_AMOUNT");
        props.put("infranet.custom.field.10722", "WTB_FLD_OVERDUE_AMOUNT");
        props.put("infranet.custom.field.10723", "WTB_FLD_BILLED_AMOUNT");
        props.put("infranet.custom.field.10724", "WTB_FLD_UNBILLED_AMOUNT");
        props.put("infranet.custom.field.10725", "WTB_FLD_TOTAL_BALANCE");
        props.put("infranet.custom.field.10726", "WTB_FLD_LAST_PAID_T");
        props.put("infranet.custom.field.10727", "WTB_FLD_SERVICE_LOGIN_TYPE");
        props.put("infranet.custom.field.10728", "WTB_FLD_LEAD_IDENTITY");
        props.put("infranet.custom.field.10730", "WTB_FLD_SALES_INFO");
        props.put("infranet.custom.field.10731", "WTB_FLD_EMAIL_TYPES");
        props.put("infranet.custom.field.10732", "WTB_FLD_CLIENT_APP_NAME");
        props.put("infranet.custom.field.10733", "WTB_FLD_LAST_LOGIN_DATE");
        props.put("infranet.custom.field.10734", "WTB_FLD_INFO_TICKER");
        props.put("infranet.custom.field.10735", "WTB_FLD_INFO_TICKER_TEXT");
        props.put("infranet.custom.field.10736", "WTB_FLD_DOC_QUALITY_CHECK");
        props.put("infranet.custom.field.10737", "WTB_FLD_DOC_RECEIVED");
        props.put("infranet.custom.field.10738", "WTB_FLD_DOC_SUBMITTED");
        props.put("infranet.custom.field.10739", "WTB_FLD_DOC_VERIFICATION_STATUS");
        props.put("infranet.custom.field.10740", "WTB_FLD_DEFAULT_CONTRACT_PERIOD");
        props.put("infranet.custom.field.10741", "WTB_FLD_REFERRED_BY");
        props.put("infranet.custom.field.10742", "WTB_FLD_MY_REFERRALS");
        props.put("infranet.custom.field.10743", "WTB_FLD_REFERRED_ACCOUNT");

        try {
            ctx = new PortalContext();
//            AppLogger.log(1, "PCM connection established successfully.");
            

        } catch (EBufException e) {
  //          AppLogger.log(3, "Unable to initialize PortalContext.\n" + e.toString());
            logg.smsLogger("","Unable to initialize PortalContext.\n", e.toString());
            ebuf=e;
        }


    }

    /* Open context to BRM */
    /**
     * 
     */
    public void openContext() {
        try {
            //ctx.connect();
           // AppLogger.log(3, "Porta; properties.\n" + props.toString());
            ctx.connect(props);//props); 
            //System.out.println("\nContext: " + ctx.toString());

        } catch (EBufException e) {
//            AppLogger.log(3, "Unable to open PortalContext.\n" + e.toString());
            logg.smsLogger("","Unable to open PortalContext.\n", e.toString());
            ebuf=e;
            System.exit(1);
        } catch (Exception e) {
            //AppLogger.log(3, "Unable to open PortalContext.\n" + e.toString());
            logg.smsLogger("","Unable to open PortalContext.\n", e.toString());
            ebuf=e;
            System.exit(1);
        }

    }
    /* Execute Opcode */

    /**
     * 
     * @param opcode
     * @param input
     * @return
     */
    public FList execute(int opcode, FList input) {
        try {
            return getCtx().opcode(opcode, input);

        } catch (EBufException e) {
//            AppLogger.log(3, "Unable to execute PCM opcode.\n" + e.toString());
             logg.smsLogger("","Unable to execute PCM opcode.\n", e.toString());
            ebuf = e;
            return null;
        } catch (Exception e) {
          //  AppLogger.log(3, "Unable to execute PCM opcode.\n" + e.toString());
             logg.smsLogger("","Unable to execute PCM opcode.\n", e.toString());
            ebuf = e;
            return null;
        }
    }
    
    /**
     * 
     * @param opcode
     * @param flags
     * @param input
     * @return
     */
    public FList execute(int opcode,int flags, FList input) {
        try {
            return getCtx().opcode(opcode,flags, input);

        } catch (EBufException e) {
//            AppLogger.log(3, "Unable to execute PCM opcode.\n" + e.toString());
            logg.smsLogger("","Unable to execute PCM opcode.\n", e.toString());
            
            ebuf = e;
            return null;
        } catch (Exception e) {
          //  AppLogger.log(3, "Unable to execute PCM opcode.\n" + e.toString());
            logg.smsLogger("","Unable to execute PCM opcode.\n", e.toString());
            ebuf = e;
            return null;
        }
    }

    /* Close context to BRM */
    /**
     * 
     */
    public void closeContext() {
        try {
            ctx.close(true);
        } catch (EBufException e) {
//            AppLogger.log(3, "Unable to close PortalContext.\n" + e.toString());
            logg.smsLogger("","Unable to close PortalContext.\n", e.toString());
            ebuf=e;
        } catch (Exception e) {
          //  AppLogger.log(3, "Unable to close PortalContext.\n" + e.toString());
            logg.smsLogger("","Unable to close PortalContext.\n", e.toString());
        }
    }

    /**
     * @return the ctx
     */
    public PortalContext getCtx() {
        return ctx;
    }

}