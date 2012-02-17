/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package utils;

import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

/**
 *
 * @author PKANawaz
 */
public class RequestNetNanny {

    public String sendRequest(String requestXml, String accountNo) {
        String license = null;
        
        try {
            URL url = new URL("http://www.contentwatch.com/action.php");
            URLConnection con = url.openConnection();

            // specify that we will send output and accept input
            con.setDoInput(true);
            con.setDoOutput(true);

            con.setConnectTimeout(20000);  // long timeout, but not infinite
            con.setReadTimeout(20000);

            con.setUseCaches(false);
            con.setDefaultUseCaches(false);

            // tell the web server what we are sending
            con.setRequestProperty("Content-Type", "text/xml");

            OutputStreamWriter writer = new OutputStreamWriter(con.getOutputStream());
            writer.write(requestXml);
            writer.flush();
            writer.close();

            // reading the response
            InputStreamReader reader = new InputStreamReader(con.getInputStream());

            StringBuilder buf = new StringBuilder();
            char[] cbuf = new char[2048];
            int num;

            while (-1 != (num = reader.read(cbuf))) {
                buf.append(cbuf, 0, num);
            }

            String result = buf.toString();

            license = getLicenseKey(result, accountNo);
//            if (MyDefaultLog.doLog(8)) {
//                    MyDefaultLog.log(this,8, "Netnanny Account No: " +accountNo + " " + result + " " + license);
//            }
        } catch (Throwable t) {
//            if (MyDefaultLog.doLog(2)) {
//                    MyDefaultLog.log(this,2, t.getMessage());
//            }
//            MyDef aultLog.sendMailNSms("Netnanny Server problem", t.toString());
        }
        return license;
    }

    public String createActivationXML(String email, String accountNo, String firstName, String lastName) {

        String requestXml = "<?xml version='1.0' encoding='UTF-8'?> "
                    + "<methodCall>"
                    + "<methodName>authenticate</methodName>"
                    + "<params>"
                    + "<param>"
                    + "<value>"
                    + "<string></string>"
                    + "</value>"
                    + "</param>"
                    + "<param>"
                    + "<value>"
                    + "<int>1674</int>"
                    + "</value>"
                    + "</param>"
                    + "<param>"
                    + "<value>"
                    + "<string>ULWRR.7155TaveR2</string>"
                    + "</value>"
                    + "</param>"
                    + "<param>"
                    + "<value>"
                    + "<string>activate</string>"
                    + "</value>"
                    + "</param>"
                    + "<param>"
                    + "<value>"
                    + "<struct>"
                    + "<member>"
                    + "<name>user_email</name>"
                    + "<value>"
                    + "<string>" + email + "</string>"
                    + "</value>"
                    + "</member>"
                    + "<member>"
                    + "<name>sku</name>"
                    + "<value>"
                    + "<string>161</string>"
                    + "</value>"
                    + "</member>"
                    + "<member>"
                    + "<name>user_first</name>"
                    + "<value>"
                    + "<string> " + firstName + " </string>"
                    + "</value>"
                    + "</member>"
                    + "<member>"
                    + "<name>user_last</name>"
                    + "<value>"
                    + "<string>" + lastName + "</string>"
                    + "</value>"
                    + "</member>"
                    + "<member>"
                    + "<name>user_handle</name>"
                    + "<value>"
                    + "<string>" + accountNo + "</string>"
                    + "</value>"
                    + "</member>"
                    + "</struct>"
                    + "</value>"
                    + "</param>"
                    + "</params>"
                    + "</methodCall>";
        return requestXml;
    }

    public String createDeactivationXML(String email, String accountNo, String license) {

        String requestXml = "<?xml version='1.0' encoding='UTF-8'?> "
                    + "<methodCall>"
                    + "<methodName>authenticate</methodName>"
                    + "<params>"
                    + "<param>"
                    + "<value>"
                    + "<string></string>"
                    + "</value>"
                    + "</param>"
                    + "<param>"
                    + "<value>"
                    + "<int>1674</int>"
                    + "</value>"
                    + "</param>"
                    + "<param>"
                    + "<value>"
                    + "<string>ULWRR.7155TaveR2</string>"
                    + "</value>"
                    + "</param>"
                    + "<param>"
                    + "<value>"
                    + "<string>deactivate</string>"
                    + "</value>"
                    + "</param>"
                    + "<param>"
                    + "<value>"
                    + "<struct>"
                    + "<member>"
                    + "<name>user_email</name>"
                    + "<value>"
                    + "<string>" + email + "</string>"
                    + "</value>"
                    + "</member>"
                    + "<member>"
                    + "<name>sku</name>"
                    + "<value>"
                    + "<string>161</string>"
                    + "</value>"
                    + "</member>"
                    + "<member>"
                    + "<name>user_license</name>"
                    + "<value>"
                    + "<string> " + license + " </string>"
                    + "</value>"
                    + "</member>"
                    + "<member>"
                    + "<name>user_handle</name>"
                    + "<value>"
                    + "<string>" + accountNo + "</string>"
                    + "</value>"
                    + "</member>"
                    + "</struct>"
                    + "</value>"
                    + "</param>"
                    + "</params>"
                    + "</methodCall>";
        return requestXml;
    }

    public String getLicenseKey(String argv, String accountNo) {
        String license = null;

        try {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            InputSource inStream = new org.xml.sax.InputSource();

            inStream.setCharacterStream(new java.io.StringReader(argv));
            Document doc = db.parse(inStream);


            doc.getDocumentElement().normalize();
            System.out.println("Root element " + doc.getDocumentElement().getNodeName());
            NodeList nodeLst = doc.getElementsByTagName("member");
           // System.out.println("Information of member license");

            for (int s = 0; s < nodeLst.getLength(); s++) {

                Node fstNode = nodeLst.item(s);

                if (fstNode.getNodeType() == Node.ELEMENT_NODE) {

                    Element fstElmnt = (Element) fstNode;
                    NodeList fstNmElmntLst = fstElmnt.getElementsByTagName("string");
                    Element fstNmElmnt = (Element) fstNmElmntLst.item(0);
                    NodeList fstNm = fstNmElmnt.getChildNodes();
                    if (((Node) fstNm.item(0)).getNodeValue() != null) {
                        license = ((Node) fstNm.item(0)).getNodeValue();
                        System.out.println("Licence : " + ((Node) fstNm.item(0)).getNodeValue());
                        break;
                    }
                }
            }
            return license;
        } catch (Exception e) {
//            if (MyDefaultLog.doLog(2)) {
//                    MyDefaultLog.log(this,2, "Error in NetNannay: " + accountNo + " Error XML: " + argv);
//            }
        }
        return license;
    }
}
