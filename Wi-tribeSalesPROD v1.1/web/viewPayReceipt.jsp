<%@page contentType="text/html"   import="java.util.*,java.io.*,
 javax.xml.transform.*, javax.xml.transform.stream.StreamResult,javax.xml.transform.stream.StreamSource,java.io.FileOutputStream,java.io.IOException,java.io.InputStream,java.io.StringReader"%>
<%@page pageEncoding="UTF-8"%>
<%
TransformerFactory tFactory = TransformerFactory.newInstance();
BufferedInputStream bufferedInput = null;
byte[] buffer = new byte[1024];
 

System.out.println("get paymentdetails in jsp"+request.getAttribute("paymentReceiptList"));
String sXML = (String)request.getAttribute("paymentReceiptList");
try{
            
           bufferedInput = new BufferedInputStream(new FileInputStream("D:/Wi-tribeSalesPROD/web/Reciept.templates"));
            
            

Transformer transformer = tFactory.newTransformer(new StreamSource(bufferedInput));
 File file = new File("C:/sam.html");
FileOutputStream fos = new FileOutputStream(file);
String s = "<!DOCTYPE html PUBLIC " +"-//W3C//DTD XHTML 1.0 Transitional//EN" +"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd" + ">";
byte[] ch = s.getBytes();
        
fos.write(ch);
transformer.transform(new StreamSource(new StringReader(sXML)),new StreamResult(fos));
}catch(Exception e){
    System.out.println(e);
}
String url =
                "C:/sam.html";
        System.out.println("url:" + url);
        String os = System.getProperty("os.name").toLowerCase();
        Runtime rt = Runtime.getRuntime();
        try {
            if (os.indexOf("win") >= 0) {
                // this doesn't support showing urls in the form of "page.html#nameLink"
                rt.exec("rundll32 url.dll,FileProtocolHandler " + url);
            } else if (os.indexOf("mac") >= 0) {
                rt.exec("open " + url);
            } else if (os.indexOf("nix") >= 0 || os.indexOf("nux") >= 0) {
                // Do a best guess on unix until we get a platform independent way
                // Build a list of browsers to try, in this order.
                String[] browsers = {
                    "epiphany", "firefox", "mozilla", "konqueror",
                    "netscape", "opera", "links", "lynx"};
                
                // Build a command string which looks like "browser1 "url" || browser2 "url" ||..."
                StringBuffer cmd = new StringBuffer();
                for (int i = 0; i < browsers.length; i++) {
                    cmd.append( (i == 0 ? "" : " || ") + browsers[i] + " \"" + url +
                            "\" ");
                    
                }
                rt.exec(new String[] {"sh", "-c", cmd.toString()});
            } else {
                return;
            }
        } catch (Exception e) {
            return;
        } 



%>

 