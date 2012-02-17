package models.utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.StringBufferInputStream;
import java.rmi.RemoteException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.NoSuchElementException;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.portal.app.util.CustomerValErrorData;
import com.portal.bas.PClientServices;
import com.portal.bas.PFieldBean;
import com.portal.bas.PLightComponentHelper;
import com.portal.bas.PModelHandle;
import com.portal.bas.PPooledConnectionClientServices;
import com.portal.bas.comp.PIAComponentCollection;
import com.portal.pcm.DefaultLog;
import com.portal.web.comp.MapLanguageToBundle;

public class ServletUtil {
	public static final String BUNDLE = "bundle";
	public static final String POST_METHOD = "POST";
	public static final String GET_METHOD = "GET";
	public static final String LOCALE = "locale";
	public static final String CHARSET = "charset";
	public static final String BRAND = "brand";
	public static final String CONTENT_LANGUAGE = "Content-Language";
	public static final String VARY = "Vary";
	public static final String ACCEPT_LANGUAGE = "Accept-Language";
	public static final String ACCEPT_CHARSET = "Accept-Charset";
	public static final String EXCEPTION = "Exception";
	public static final String CONNECTION = "connection";
	public static final String PARENT_SERVICE = "parentService";
	private static final String HOST = "host";
	private static final String PORT = "port";
	private static final String USER = "user";
	private static final String PASSWORD = "password";
	private static final String SERVICE = "service";
	private static final String BUNDLE_NAME = "com.portal.web.comp.WebKitResources";
	private static final String PCP_PROTOCOL = "pcp://";
	private static final String SERVICE_DEF = "/service/pcm_client 1";
	private static final String COLON = ":";
	private static final String AT = "@";
	private static final String ACCOUNT_MODEL = "AccountModel";
	private static final String SERVICE_MODEL = "ServiceModel";
	private static final String FIELD_BEAN_CLASS = "com.portal.bas.comp.PIAFieldBean";
	private static final String OBRACK = "[";
	private static final String CBRACK = "]";
	private static final String SEP = ".";
	private static final String EXTENDED = "extended";
	private static final String MAPPING = "mapping";

	public static Object logIn(PPooledConnectionClientServices pCS) {
		Properties props = pCS.getDefaultProperties();
		String host = props.getProperty("host");
		String port = props.getProperty("port");
		String user = props.getProperty("user");
		String password = props.getProperty("password");
		String service = props.getProperty("service");

		if (service == null) {
			service = "/service/pcm_client 1";
		}

		StringBuffer url = new StringBuffer("pcp://");
		url.append(user);
		url.append(":");
		url.append(password);
		url.append("@");
		url.append(host);
		url.append(":");
		url.append(port);
		url.append(service);

		return pCS.doLoginForConnection(user, password, url.toString());
	}

	@SuppressWarnings("deprecation")
	public static Object logIn(PPooledConnectionClientServices pCS,
			String user, String password, String url) {
		
		return pCS.doLoginForConnection(user, password, url.toString());
	}

	public static void saveLocaleInfo(HttpServletRequest req) {
		HttpSession session = req.getSession(false);

		String acceptLanguage = req.getHeader("Accept-Language");
		String acceptCharset = req.getHeader("Accept-Charset");
		if (acceptCharset == null) {
			acceptCharset = req.getParameter("charset");
		}

		String bundleName = "com.portal.web.comp.WebKitResources";

		MapLanguageToBundle map = new MapLanguageToBundle(bundleName,
				acceptLanguage, acceptCharset);
		String charset;
		if ((charset = map.getCharset()) != null)
			session.setAttribute("charset", charset);
		Locale locale;
		if ((locale = map.getLocale()) != null)
			session.setAttribute("locale", locale);
		ResourceBundle bundle;
		if ((bundle = map.getBundle()) != null)
			session.setAttribute("bundle", bundle);
	}

	public static HashMap gatherFormInput(HttpServletRequest request) {
		HashMap input = new HashMap();
		Enumeration fieldNames = request.getParameterNames();

		String paramName = null;
		String paramValue = null;
		String charset = null;

		HttpSession session = request.getSession(false);

		if (session != null) {
			charset = (String) session.getAttribute("charset");
		}

		if ((charset == null) || (charset.length() == 0)) {
			charset = request.getParameter("charset");
		}

		if ((charset == null) || (charset.length() == 0)) {
			charset = "UTF-8";
		}

		StringBuffer buf = new StringBuffer();

		while (fieldNames.hasMoreElements()) {
			paramName = fieldNames.nextElement().toString();
			paramValue = request.getParameterValues(paramName)[0].toString();

			if (paramValue.length() > 0) {
				try {
					StringBuffer sb = new StringBuffer();
					BufferedReader br = new BufferedReader(
							new InputStreamReader(new StringBufferInputStream(
									paramValue), charset));

					while ((paramValue = br.readLine()) != null) {
						sb.append(paramValue);

						if (br.ready()) {
							sb.append(System.getProperty("line.separator"));
						}
					}
					paramValue = sb.toString();
				} catch (Exception e) {
				}
			}

			buf.setLength(0);
			buf.append("WebKit:*** param ");
			buf.append(paramName);
			buf.append('=');
			buf.append(paramValue);
			buf.append(" ***");

			if (DefaultLog.doLog(8)) {
				DefaultLog.log(8, buf.toString());
			}

			input.put(paramName, paramValue);
		}

		return input;
	}

	public static void setLightDataForAll(Map formInput,
			PIAComponentCollection col, String caller) {
		PLightComponentHelper lc = null;
		PClientServices cs = null;
		try {
			cs = col.getServices();
		} catch (RemoteException e) {
		}
		for (Iterator i = formInput.entrySet().iterator(); i.hasNext();) {
			Map.Entry e = (Map.Entry) i.next();
			lc = col.getChild(e.getKey());

			if (lc != null) {
				lc.setLightData(e.getValue());
			} else if (cs != null)
				cs.logRemote(caller, "No field bean exists for " + e.getKey());
		}
	}

	public static HashMap parseErrorData(PIAComponentCollection col,
			CustomerValErrorData[] errorData) {
		return parseErrorDataEnhanced(col, errorData);
	}

	public static HashMap parseErrorDataEnhanced(PIAComponentCollection col,
			CustomerValErrorData[] errorData) {
		PLightComponentHelper lc = null;

		HashMap errorMap = new HashMap();
		Iterator j;
		for (int i = 0; i < errorData.length; i++) {
			for (j = col.keyIterator(); j.hasNext();) {
				String key = (String) j.next();
				lc = col.getChild(key);
				PFieldBean fb = (PFieldBean) lc.getRemoteComponent();
				try {
					if (matchClose(fb.getModelFieldDescription(),
							errorData[i].getFieldSpecification())) {
						errorMap.put(lc, errorData[i].getErrorMessage());
					}
				} catch (RemoteException e) {
				}

			}

		}

		return errorMap;
	}

	private static boolean matchClose(String desc, String error) {
		if ((error == null) || (desc == null)) {
			return false;
		}

		if (desc.equals(error)) {
			return true;
		}

		if (desc.indexOf("[") != error.indexOf("[")) {
			int tok1 = error.indexOf("[");
			if (tok1 == -1) {
				tok1 = error.indexOf(".");
				String tok = error.substring(0, tok1);
				if (desc.indexOf(tok) != -1) {
					char[] tmp = new char[desc.length()];

					int cnt = 0;
					int i = 0;
					for (int len = desc.length(); i < len; i++) {
						char c = desc.charAt(i);
						if (c == '[')
							while (true) {
								i++;
								if (desc.charAt(i) == ']')
									break;
							}
						tmp[(cnt++)] = c;
					}
					String tmpS = new String(tmp).trim();
					return tmpS.indexOf(error) != -1;
				}

				return false;
			}
			return false;
		}

		StringTokenizer descST = new StringTokenizer(desc, ".");
		StringTokenizer errorST = new StringTokenizer(error, ".");
		try {
			while (descST.hasMoreTokens()) {
				String descToken = descST.nextToken();
				String errorToken = errorST.nextToken();

				int locB = descToken.indexOf("[");
				int locA = errorToken.indexOf("[");

				String idx2 = null;
				if ((locB != -1) && (locA != -1) && (locB == locA)) {
					String idx = descToken.substring(locB + 1,
							descToken.indexOf("]"));

					if ((!idx.equalsIgnoreCase("any"))
							&& (!idx.equalsIgnoreCase("all"))) {
						try {
							int bID = Integer.parseInt(idx);
							idx2 = errorToken.substring(locA + 1,
									errorToken.indexOf("]"));

							int aID = Integer.parseInt(idx2);
							if (aID != bID)
								return false;
						} catch (NumberFormatException e) {
						}
					} else {
						String descT = descToken.substring(0, locB);
						String errT = errorToken.substring(0, locA);
						if ((descT != null) && (!descT.equals(errT)))
							return false;
					}
				} else if ((locB == -1) && (locA == -1)) {
					if (!descToken.equals(errorToken)) {
						return false;
					}
				} else if (locB != locA) {
					return false;
				}
			}
		} catch (NoSuchElementException ne) {
			return false;
		}
		return true;
	}

	public static boolean checkError(Map errorMap, PLightComponentHelper lc) {
		boolean exists = false;
		if ((errorMap != null) && (errorMap.size() > 0))
			if ((lc != null & errorMap.containsKey(lc))) {
				exists = true;
			}

		return exists;
	}

	public static PModelHandle getModelFromSession(HttpSession session)
			throws InvalidSessionException {
		if (session.isNew()) {
			throw new InvalidSessionException("Session is New "
					+ session.getId());
		}

		PModelHandle model = (PModelHandle) session
				.getAttribute("AccountModel");

		if (model == null) {
			throw new InvalidSessionException(
					"Session does not have the required values.");
		}

		return model;
	}

	public static PLightComponentHelper addComponent(
			PIAComponentCollection col, String name, String fieldDesc) {
		return addComponent(col, name, fieldDesc, fieldDesc);
	}

	public static PLightComponentHelper addComponent(
			PIAComponentCollection col, String name, String inputFieldDesc,
			String outputFieldDesc) {
		try {
			PLightComponentHelper lch = (PLightComponentHelper) col
					.getServices().createController(
							"com.portal.bas.comp.PIAFieldBean");

			PFieldBean fb = (PFieldBean) lch.getRemoteComponent();
			fb.setModelFieldDescription(inputFieldDesc);
			fb.setDisplayFieldDescription(outputFieldDesc);
			col.addChild(name, lch);

			return lch;
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static Object[] convertStrToArgs(String str) {
		int index = str.indexOf(',');

		Object[] arguments = new Object[2];

		if (index > -1) {
			arguments[0] = str.substring(0, index);
			arguments[1] = str.substring(index + 1);
		} else {
			arguments[0] = str;
			arguments[1] = "";
		}

		return arguments;
	}

	public static String validateDate(String str) {
		String month = "";
		String year = "";

		int index = str.indexOf('/');
		if (index > -1) {
			month = str.substring(0, index);
			year = str.substring(index + 1);
		}
		StringBuffer sb = new StringBuffer(month);
		if (sb.length() == 1) {
			sb.insert(0, '0');
		}

		if (year != null)
			sb.append(year);
		else {
			sb.append("00");
		}
		if (sb.length() == 3) {
			sb.insert(2, '0');
		}
		return sb.toString();
	}

	// public static void saveBrandModel(PPooledConnectionClientServices pCS,
	// String brand, HttpSession session)
	// throws RemoteException
	// {
	// Properties props = pCS.getDefaultProperties();
	// String brandProp = props.getProperty("brand." + brand);
	//
	// StringTokenizer token = new StringTokenizer(brandProp, ",");
	// String service = null;
	// String user = null;
	// String pass = null;
	//
	// if (token.hasMoreTokens()) {
	// service = token.nextToken();
	// }
	// if (token.hasMoreTokens()) {
	// user = token.nextToken();
	// }
	// if (token.hasMoreTokens()) {
	// pass = token.nextToken();
	// }
	//
	// PLoginBeanImpl loginBean =
	// (PLoginBeanImpl)pCS.createController("com.portal.web.comp.PLoginBeanImpl");
	//
	// loginBean.setService(service);
	// loginBean.setLogin(user);
	// loginBean.setPassword(pass);
	//
	// loginBean.login_verify(session, brand,
	// (ResourceBundle)session.getAttribute("bundle"));
	// }

	public static String getServiceName(PModelHandle service,
			HttpSession session) throws RemoteException {
		String serviceName = null;

		ResourceBundle bundle = (ResourceBundle) session.getAttribute("bundle");
		String type = service.getType();

		StringBuffer serviceType = new StringBuffer("extended");
		serviceType.append(type.replace('/', '.'));
		serviceType.append(".");
		serviceType.append("mapping");
		try {
			serviceName = bundle.getString(serviceType.toString());
		} catch (MissingResourceException e) {
			serviceName = type;
		}
		return serviceName;
	}
}