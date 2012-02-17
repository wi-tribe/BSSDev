<%@page contentType="text/html" import="java.sql.*,com.witribe.bo.WitribeBO,com.witribe.util.DBUtil"%>
<%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%> 
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%> 
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%--
The taglib directive below imports the JSTL library. If you uncomment it,
you must also add the JSTL library to the project. The Add Library... action
on Libraries node in Projects view can be used to add the JSTL 1.1 library.
--%>
<%--
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%> 
--%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">
    
<html>

<%
            String Commission_Mode="";
            String pblimit="";
            String srlimit="";
            String pbvlimit="";
            String pbperiod="";
            String logenabled="";
            String pfactor="";            
            String srvlimit="";                       
            String Commission_Mode_Descr="";
            Connection con=null;
            PreparedStatement pstmt = null;
            ResultSet rs = null;
            String exception=null;
            
            try
               {
                    if(request.getParameter("comm_mode")==null)  
                    {
                        Commission_Mode="";  
                    }            
                    else
                    {
                        Commission_Mode=request.getParameter("comm_mode");    
                    }    
                    if(request.getParameter("pblimit")==null)  
                    {
                        pblimit="";  
                    }            
                    else
                    {
                        pblimit=request.getParameter("pblimit");    
                    } 
                    if(request.getParameter("srlimit")==null)  
                    {
                        srlimit="";  
                    }            
                    else
                    {
                        srlimit=request.getParameter("srlimit");    
                    } 
                    if(request.getParameter("srvlimit")==null)  
                    {
                        srvlimit="";  
                    }            
                    else
                    {
                        srvlimit=request.getParameter("srvlimit");    
                    }     
                    if(request.getParameter("pbvlimit")==null)  
                    {
                        pbvlimit="";  
                    }            
                    else
                    {
                        pbvlimit=request.getParameter("pbvlimit");    
                    }                     
                    if(request.getParameter("pbperiod")==null)  
                    {
                        pbperiod="";  
                    }            
                    else
                    {
                        pbperiod=request.getParameter("pbperiod");    
                    }                          
                    if(request.getParameter("pfactor")==null)  
                    {
                        pfactor="";  
                    }            
                    else
                    {
                        pfactor=request.getParameter("pfactor");    
                    }  
                    if(request.getParameter("logenabled")==null)  
                    {
                        logenabled="";  
                    }            
                    else
                    {
                        logenabled=request.getParameter("logenabled");    
                    }    
                    con = DBUtil.getConnection();
                    if(!Commission_Mode.equals(""))
                    {             //-----------Update existing Commission Mode-----------
                           pstmt=con.prepareStatement("Update COMMISSION_CONFIG_MASTER set value=to_number(?) where key='COMMISSION_MODE'");
                        pstmt.setString(1,Commission_Mode);
                           if(pstmt.executeUpdate()>0)
                        {    
                                con.commit();
                           }     
                           DBUtil.closeResultSet(rs);  
                           DBUtil.closeStatement(pstmt);               
                    }
                    if(!pblimit.equals(""))
                    {     //-----------Update existing Pull Back Period-----------
                           pstmt=con.prepareStatement("Update COMMISSION_CONFIG_MASTER set value=to_number(?) where key='PULL_BACK_LIMIT'");
                           pstmt.setString(1,pblimit);
                           if(pstmt.executeUpdate()>0)
                           {     
                                con.commit();
                           }     
                        DBUtil.closeResultSet(rs);
                        DBUtil.closeStatement(pstmt);
                    }    
                    if(!srlimit.equals(""))
                    {     //-----------Update existing Sales Return -----------
                           pstmt=con.prepareStatement("Update COMMISSION_CONFIG_MASTER set value=to_number(?) where key='SALES_RETURN_LIMIT'");
                           pstmt.setString(1,srlimit);
                           if(pstmt.executeUpdate()>0)
                           {     
                                con.commit();
                        }  
                        DBUtil.closeResultSet(rs);
                        DBUtil.closeStatement(pstmt);
                    }
                    if(!pbvlimit.equals(""))
                    {     //-----------Update existing PB Value Limit -----------
                           pstmt=con.prepareStatement("Update COMMISSION_CONFIG_MASTER set value=to_number(?) where key='PB_VALUE_LIMIT'");
                           pstmt.setString(1,pbvlimit);
                           if(pstmt.executeUpdate()>0)
                           {     
                                con.commit();
                           }     
                        DBUtil.closeResultSet(rs);
                        DBUtil.closeStatement(pstmt);
                    }    
                    if(!srvlimit.equals(""))
                    {     //-----------Update existing SR Return Limit  -----------
                           pstmt=con.prepareStatement("Update COMMISSION_CONFIG_MASTER set value=to_number(?) where key='SR_VALUE_LIMIT'");
                           pstmt.setString(1,srvlimit);
                           if(pstmt.executeUpdate()>0)
                           {     
                                con.commit();
                           }     
                        DBUtil.closeResultSet(rs);
                        DBUtil.closeStatement(pstmt);
                    }                                                 
                    if(!pbperiod.equals(""))
                    {     //-----------Update existing Pull Back Period  -----------
                           pstmt=con.prepareStatement("Update COMMISSION_CONFIG_MASTER set value=to_number(?) where key='PULL_BACK_PERIOD'");
                           pstmt.setString(1,pbperiod);
                           if(pstmt.executeUpdate()>0)
                           {     
                                con.commit();
                           }     
                        DBUtil.closeResultSet(rs);
                        DBUtil.closeStatement(pstmt);
                    }           
                    if(!pfactor.equals(""))
                    {     //-----------Update existing Penal Factor -----------
                           pstmt=con.prepareStatement("Update COMMISSION_CONFIG_MASTER set value=to_number(?) where key='PENAL_FACTOR'");
                           pstmt.setString(1,pfactor);
                           if(pstmt.executeUpdate()>0)
                           {     
                                con.commit();
                           }     
                        DBUtil.closeResultSet(rs);
                        DBUtil.closeStatement(pstmt);
                    }     
                    if(!logenabled.equals(""))
                    {     //-----------Update existing Log Enabled Setting -----------
                           pstmt=con.prepareStatement("Update COMMISSION_CONFIG_MASTER set value=? where key='LOG_ENABLED'");
                           pstmt.setString(1,logenabled);
                           if(pstmt.executeUpdate()>0)
                           {     
                                con.commit();
                           }     
                        DBUtil.closeResultSet(rs);
                        DBUtil.closeStatement(pstmt);
                    }                          
                        //-------------Qry for Commission Mode
                        pstmt=con.prepareStatement("select value,decode(value,1,'Market wise',2,'Product wise',3,'Market+Product') from COMMISSION_CONFIG_MASTER where key='COMMISSION_MODE'");
                        rs = pstmt.executeQuery();
                        if(rs.next())
                        {    
                            Commission_Mode=rs.getString(1);
                            Commission_Mode_Descr=rs.getString(2);
                        }
                        DBUtil.closeResultSet(rs);
                        DBUtil.closeStatement(pstmt);
                        //-------------Qry for Pullback Period-------------
                        pstmt=con.prepareStatement("select value from COMMISSION_CONFIG_MASTER where key='PULL_BACK_LIMIT'");
                        rs = pstmt.executeQuery();
                        if(rs.next())
                        {    
                            pblimit=rs.getString(1);

                        }
                        DBUtil.closeResultSet(rs);
                        DBUtil.closeStatement(pstmt);
                        //-------------Qry for SALES_RETURN_LIMIT-------------
                        pstmt=con.prepareStatement("select value from COMMISSION_CONFIG_MASTER where key='SALES_RETURN_LIMIT'");
                        rs = pstmt.executeQuery();
                        if(rs.next())
                        {    
                            srlimit=rs.getString(1);

                        }                        
                        DBUtil.closeResultSet(rs);
                        DBUtil.closeStatement(pstmt);
                        //-------------Qry for SR Value LIMIT-------------
                        pstmt=con.prepareStatement("select value from COMMISSION_CONFIG_MASTER where key='SR_VALUE_LIMIT'");
                        rs = pstmt.executeQuery();
                        if(rs.next())
                        {    
                            srvlimit=rs.getString(1);

                        }                                                
                        DBUtil.closeResultSet(rs);
                        DBUtil.closeStatement(pstmt);
                        //-------------Qry for PB Value LIMIT-------------
                        pstmt=con.prepareStatement("select value from COMMISSION_CONFIG_MASTER where key='PB_VALUE_LIMIT'");
                        rs = pstmt.executeQuery();
                        if(rs.next())
                        {    
                            pbvlimit=rs.getString(1);

                        }                                                                        
                        //-------------Qry for Pull Back Period-------------
                        DBUtil.closeResultSet(rs);
                        DBUtil.closeStatement(pstmt);
                        pstmt=con.prepareStatement("select value from COMMISSION_CONFIG_MASTER where key='PULL_BACK_PERIOD'");
                        rs = pstmt.executeQuery();
                        if(rs.next())
                        {    
                            pbperiod=rs.getString(1);

                        }

                        DBUtil.closeResultSet(rs);
                        DBUtil.closeStatement(pstmt);
                        //-------------Qry for Penal Factor-------------                        
                        pstmt=con.prepareStatement("select value from COMMISSION_CONFIG_MASTER where key='PENAL_FACTOR'");
                        rs = pstmt.executeQuery();
                        if(rs.next())
                        {    
                            pfactor=rs.getString(1);

                        }       

                        DBUtil.closeResultSet(rs);
                        DBUtil.closeStatement(pstmt);
                        //-------------Qry for Log Enabled-------------                        
                        pstmt=con.prepareStatement("select value from COMMISSION_CONFIG_MASTER where key='LOG_ENABLED'");
                        rs = pstmt.executeQuery();
                        if(rs.next())
                        {    
                            logenabled=rs.getString(1);

                        }                           
                        DBUtil.closeConnection(con);
                   } catch(SQLException ex) {
                    exception = "SQL Exception";
                    out.print(exception+":"+ex);
                   } catch(Exception ex) {
                        exception = "Other Than SQL Exception";
                        out.print(exception+":"+ex);
                } 
%>
    <script language="javascript" type="text/javascript" src="js/witribescript.js"></script>
  <script language="javascript" type="text/javascript">
        commission_mode="<%=Commission_Mode%>";
        pblimit="<%=pblimit%>";
        srlimit="<%=srlimit%>";
        pbvlimit="<%=pbvlimit%>";
        srvlimit="<%=srvlimit%>";
        pbperiod="<%=pbperiod%>";        
        pfactor="<%=pfactor%>";        
        logenabled="<%=logenabled%>";        
        function setMode()
        {
            if(IsNumeric(document.getElementById('pblimit'))==false)
            {
                alert("Please enter numbers only");
                document.getElementById('pblimit').focus();
                return false;
            }
            if(IsNumeric(document.getElementById('srlimit'))==false)
            {
                alert("Please enter numbers only");
                document.getElementById('srlimit').focus();
                return false;
            }   
            if(IsNumeric(document.getElementById('pbvlimit'))==false)
            {
                alert("Please enter numbers only");
                document.getElementById('pbvlimit').focus();
                return false;
            }        
            if(IsNumeric(document.getElementById('srvlimit'))==false)
            {
                alert("Please enter numbers only");
                document.getElementById('srvlimit').focus();
                return false;
            }       
            if(IsNumeric(document.getElementById('pbperiod'))==false)
            {
                alert("Please enter numbers only");
                document.getElementById('pbperiod').focus();
                return false;
        }
            if(IsNumeric(document.getElementById('pfactor'))==false)
            {
                alert("Please enter numbers only");
                document.getElementById('pfactor').focus();
                return false;
            }     
            if(document.setcommissionmode.logena[0].checked==true)
                   document.setcommissionmode.logenabled.value="TRUE";
            else
                   document.setcommissionmode.logenabled.value="FALSE";                    
            if(confirm("Are you sure to change these settings(Effects Commission Calclation)")==false)  
                return false;
        }
function IsNumeric(sText)
{
   var ValidChars = "0123456789.";
   var IsNumber=true;
   var Char;

 
   for (i = 0; i < sText.length && IsNumber == true; i++) 
      { 
      Char = sText.charAt(i); 
      if (ValidChars.indexOf(Char) == -1) 
         {
         IsNumber = false;
         }
      }
   return IsNumber;
   
   }

        function Load_Default()
        {

            ctrlname=document.getElementById('comm_mode');
            for(i=0;i<ctrlname.options.length;i++)
            {
                if(ctrlname.options[i].value==commission_mode)
                   ctrlname.options[i].selected=true; 
            }
            document.getElementById('logenabled').value=logenabled;
            if(logenabled=="TRUE")
                document.setcommissionmode.logena[0].checked=true;
            else    
                document.setcommissionmode.logena[1].checked=true;

        }        
    </script>   
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Set Commission Mode</title>
    </head>
    <body onload="Load_Default()">
    <h1>Set Commission Mode</h1>
        <form name="setcommissionmode" method="post" action="/Wi-tribeSales/SetCommissionMode.do" onsubmit="return setMode();">
            <table><tr><td>Existing Commission Mode</td><td><%=Commission_Mode_Descr%></td></tr>
                <tr><td></td><td><select name="comm_mode" id="comm_mode">
                            <option value="1">Market wise</option>
                            <option value="2">Product wise</option>
                            <option value="3">Market+Product</option>
                </select></td></tr>
                    <tr><td>Pull Back Limit:</td> <td><input type="text" name="pblimit" id="pblimit" value="<%=pblimit%>"></td></tr>
                    <tr><td>Sales Return Limit:</td><td><input type="text" name="srlimit" id="srlimit" value="<%=srlimit%>"></td></tr>
                    <tr><td>SR Value Limit:</td><td><input type="text" name="srvlimit" id="srvlimit" value="<%=srvlimit%>"></td></tr>                
                    <tr><td>PB Value Limit:</td><td><input type="text" name="pbvlimit" id="pbvlimit" value="<%=pbvlimit%>"></td></tr>
                    <tr><td>Pull Back Period:</td><td><input type="text" name="pbperiod" id="pbperiod" value="<%=pbperiod%>"></td></tr>
                    <tr><td>Penal Factor:</td><td><input type="text" name="pfactor" id="pfactor" value="<%=pfactor%>"></td></tr>
                    <tr><td>Log Enabled:</td><td><input type="radio" name="logena" id="logena" value="TRUE"><Label>True</Label></td><td><input type="radio" name="logena" id="logena" value="FALSE"><Label>False</Label></td></tr>
                    <tr><td><input type="hidden" name="logenabled" id="logenabled"></td></tr>
                <tr><td></td></tr></table>                
                <table><tr><td><B>NOTE:              </B></td><td>Change of these Settings Can effect Commission Calculations</td></tr>
                <tr><td></td></tr>
                <tr><td></td></tr>

                
                                <TR>
                            <TD>&nbsp;</TD>
                                <TD align=left><INPUT class=submit-btn 
                                              onmouseover="this.src='images/btn-submit-thickbox-on.gif'" 
                                              onmouseout="this.src='images/btn-submit-thickbox.gif'" type=image 
                                              src="images/btn-submit-thickbox.gif" 
                                                            name=input> </TD>
                                                            </TR>

        
        </table> 
            
            
        </form>
    </body>
</html>
