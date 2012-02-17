/*     */ package com.witribe.util;
/*     */ 
/*     */ import com.portal.bas.PClientServices;
/*     */ import com.portal.bas.PPooledConnectionClientServices;
/*     */ import com.portal.pcm.EBufException;
/*     */ import com.portal.web.ConnectionListener;
/*     */ import com.portal.web.ServletUtil;
/*     */ import java.io.IOException;
/*     */ import java.io.PrintStream;
/*     */ import java.io.PrintWriter;
/*     */ import java.io.Serializable;
/*     */ import java.rmi.RemoteException;
/*     */ import java.util.Locale;
/*     */ import java.util.Properties;
/*     */ import javax.servlet.ServletContext;
/*     */ import javax.servlet.ServletException;
/*     */ import javax.servlet.http.HttpServlet;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import javax.servlet.http.HttpSession;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ public class BRMHelper extends HttpServlet
/*     */   implements Serializable
/*     */ {
/*  63 */   public static final Logger logger = Logger.getLogger("LSLPLog");
/*     */ 
/*     */   protected void processRequest(HttpServletRequest request, HttpServletResponse response)
/*     */     throws ServletException, IOException
/*     */   {
/*  79 */     response.setContentType("text/html;charset=UTF-8");
/*     */ 
/*  81 */     PrintWriter out = response.getWriter();
/*     */ 
/* 103 */     out.close();
/*     */   }
/*     */ 
/*     */   public PPooledConnectionClientServices getPPooledConnectionClientServices(HttpSession session, HttpServletRequest request)
/*     */     throws RemoteException, EBufException, IOException
/*     */   {
/* 111 */     System.out.println("session" + session);
/*     */ 
/* 113 */     PPooledConnectionClientServices pCS = (PPooledConnectionClientServices)session.getAttribute("connection");
/*     */ 
/* 117 */     Properties props = new Properties();
/*     */     try
/*     */     {
/* 123 */       logger.debug("BRMHelper......1");
/*     */ 
/* 125 */       ServletContext application = request.getSession().getServletContext();
/*     */ 
/* 127 */       logger.debug("BRMHelper......2");
/*     */ 
/* 129 */       if (pCS == null)
/*     */       {
/* 133 */         logger.debug("execute......3");
/*     */ 
/* 135 */         pCS = new PPooledConnectionClientServices((PClientServices)application.getAttribute("parentService"));
/*     */ 
/* 137 */         logger.debug("BRMHelper......1");
/*     */ 
/* 141 */         Object o = ServletUtil.logIn(pCS);
/*     */ 
/* 143 */         application.setAttribute("CREATE_CONNECTION", pCS);
/*     */ 
/* 145 */         session.setAttribute("CREATE_CONNECTION", pCS);
/*     */ 
/* 147 */         logger.debug("BRMHelper......4");
/*     */ 
/* 153 */         ConnectionListener listener = new ConnectionListener(session.getCreationTime(), pCS);
/*     */ 
/* 155 */         logger.debug("BRMHelper......5");
/*     */ 
/* 157 */         application.setAttribute("CREATE_LISTENER", listener);
/*     */ 
/* 159 */         session.setAttribute("CREATE_LISTENER", listener);
/*     */ 
/* 165 */         logger.debug("BRMHelper......6");
/*     */ 
/* 167 */         ServletUtil.saveLocaleInfo(request);
/*     */ 
/* 169 */         pCS.registerApp("CONTENT_APP_NAME", (Locale)session.getAttribute("locale"), null);
/*     */ 
/* 171 */         logger.debug("BRMHelper......7");
/*     */       }
/*     */ 
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/* 179 */       logger.error("Exception .." + e);
/*     */     }
/*     */ 
/* 183 */     return pCS;
/*     */   }
/*     */ 
/*     */   protected void doGet(HttpServletRequest request, HttpServletResponse response)
/*     */     throws ServletException, IOException
/*     */   {
/* 203 */     processRequest(request, response);
/*     */   }
/*     */ 
/*     */   protected void doPost(HttpServletRequest request, HttpServletResponse response)
/*     */     throws ServletException, IOException
/*     */   {
/* 221 */     processRequest(request, response);
/*     */   }
/*     */ 
/*     */   public String getServletInfo()
/*     */   {
/* 233 */     return "Short description";
/*     */   }
/*     */ }

/* Location:           C:\Users\PKAasimN\Desktop\
 * Qualified Name:     com.witribe.util.BRMHelper
 * JD-Core Version:    0.6.0
 */