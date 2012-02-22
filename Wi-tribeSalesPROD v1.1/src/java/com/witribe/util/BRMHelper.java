/*
 * BRMHelper.java
 *
 * Created on March 31, 2011, 11:46 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.witribe.util;
import com.portal.bas.PClientServices;
import com.portal.bas.PPooledConnectionClientServices;
import com.portal.pcm.EBufException;
import java.io.*;
import java.net.*;
import java.rmi.RemoteException;
import java.util.Locale;
import java.util.Properties;
import javax.servlet.*;
import javax.servlet.http.*;
import com.portal.web.*;
//import javax.servlet.jsp.PageContext;
import org.apache.log4j.PropertyConfigurator;
import org.apache.log4j.Logger;

/**
 *
 * @author ES61060
 * @version
 */
public class BRMHelper extends HttpServlet  implements java.io.Serializable {
    public static final Logger logger =Logger.getLogger("LSLPLog");// For log4j implementation
    
    /** Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
      /* TODO output your page here
        out.println("<html>");
        out.println("<head>");
        out.println("<title>Servlet BRMHelper</title>");
        out.println("</head>");
        out.println("<body>");
        out.println("<h1>Servlet BRMHelper at " + request.getContextPath () + "</h1>");
        out.println("</body>");
        out.println("</html>");
       */
        out.close();
    }
    
    public PPooledConnectionClientServices getPPooledConnectionClientServices(HttpSession session,HttpServletRequest request) throws RemoteException,EBufException, IOException{
        System.out.println("session"+session);
        PPooledConnectionClientServices pCS = (PPooledConnectionClientServices) session.getAttribute(ServletUtil.CONNECTION);
     //   PropertyConfigurator.configure("log4j.properties");
        Properties props = new Properties();
          //  props.load(this.getClass().getClassLoader().getResourceAsStream("log4j.properties"));
        try{
        logger.debug("BRMHelper......1");
        ServletContext application= request.getSession().getServletContext();
        logger.debug("BRMHelper......2");
        if (pCS == null) {
            // Create a new client service
            logger.debug("execute......3");
            pCS =  new PPooledConnectionClientServices( (PClientServices) application.getAttribute(ServletUtil.PARENT_SERVICE));
            logger.debug("BRMHelper......1");
            // Setup a connection for this transaction
            Object o = ServletUtil.logIn(pCS);
            application.setAttribute("CREATE_CONNECTION", pCS);
            session.setAttribute("CREATE_CONNECTION", pCS);
            logger.debug("BRMHelper......4");
            // Initialize the listener with the client services object
            // so it can handle return of the connection when session expires
            ConnectionListener listener = new ConnectionListener(session.getCreationTime() , pCS);
            logger.debug("BRMHelper......5");
            application.setAttribute("CREATE_LISTENER", listener);
            session.setAttribute("CREATE_LISTENER", listener);
            // since controller is New we need to grab the http headers
            // to determine the locale etc.
            logger.debug("BRMHelper......6");
            ServletUtil.saveLocaleInfo(request);
            pCS.registerApp("CONTENT_APP_NAME", (Locale) session.getAttribute(ServletUtil.LOCALE), null);
            logger.debug("BRMHelper......7");
        }
        }
        catch(Exception e){
            logger.error("Exception .."+e);
        }
        return pCS;
    }
    
    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /** Handles the HTTP <code>GET</code> method.
     * @param request servlet request
     * @param response servlet response
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        processRequest(request, response);
    }
    
    /** Handles the HTTP <code>POST</code> method.
     * @param request servlet request
     * @param response servlet response
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        processRequest(request, response);
    }
    
    /** Returns a short description of the servlet.
     */
    public String getServletInfo() {
        return "Short description";
    }
    // </editor-fold>
}

