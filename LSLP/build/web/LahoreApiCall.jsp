<%@page contentType="text/html" import="java.util.*,java.net.*,java.io.*,java.rmi.RemoteException,sun.misc.*" pageEncoding="UTF-8" %>
<% Properties prop = new Properties();
try {
    prop.load(this.getClass().getClassLoader().getResourceAsStream("Lslp.properties"));
    String sApiURL = prop.getProperty("lahore.http.url");
    String sUserName = prop.getProperty("lahore.url.username");
    String sPassword = prop.getProperty("lahore.url.password");
    //out.print("<BR>" + sApiURL + ":" + sUserName + ":" + sPassword);
    String strData = "";
    HttpURLConnection urlConn = null;
    DataInputStream di = null;
    FileOutputStream fo = null;
    byte b[] = new byte[1];
    String sQueryParams = "";
    if(request.getParameterNames() != null ) {
        Enumeration enu = request.getParameterNames();
        while(enu.hasMoreElements()) {
            String sParam = (String)enu.nextElement();
            sQueryParams = sQueryParams + "&" + sParam + "="+  request.getParameter(sParam);
        }
    }
    sQueryParams=sQueryParams.replaceAll(" ","+");
    sQueryParams = sApiURL + "?" + sQueryParams.substring(1);
    //out.print("<br>" + sQueryParams);
    URL url = new URL(sQueryParams);
    urlConn = (HttpURLConnection)url.openConnection();
    String userPassword = (new StringBuilder()).append(sUserName).append(":").append(sPassword).toString();
    String encoding = (new BASE64Encoder()).encode(userPassword.getBytes());
    urlConn = (HttpURLConnection)url.openConnection();
    urlConn.setDoOutput(true);
    urlConn.setDoInput(true);
    urlConn.setUseCaches(false);
    urlConn.setDefaultUseCaches(false);
    urlConn.setRequestProperty("Authorization", (new StringBuilder()).append("Basic ").append(encoding).toString());
    url.openConnection();
    for(di = new DataInputStream(urlConn.getInputStream()); -1 != di.read(b, 0, 1);)
        strData = (new StringBuilder()).append(strData).append(new String(b)).toString();
    out.println((new StringBuilder()).append("").append(strData.trim()).toString());

} catch(Exception e) {
    out.print("Error: try again");
} %>