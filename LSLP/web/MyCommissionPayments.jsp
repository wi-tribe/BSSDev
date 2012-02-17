<%@page contentType="text/html" import="java.sql.*,com.witribe.bo.WitribeBO,com.witribe.util.DBUtil"%>
<%@page pageEncoding="UTF-8"%>
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
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Commission Payments Report</title>
                <div><h3 class="heading">My Commission Payments Report</h3></div> 
    </head>
    <body>


        <div class="scrollable">
    <%

      String exception="";
      if(request.getQueryString() !=null) 
      {    
       try {    

            String Sales_id="";
           
          Sales_id=request.getParameter("salesid");
            Connection con=null;
            boolean status = false;
            PreparedStatement pstmt = null;
            ResultSet rs = null;
            con = DBUtil.getConnection();
            pstmt=con.prepareStatement("select   COMMISSION_DETAIL_ID,PAYMENT_AMOUNT, PAYMENT_SCHEDULE_DATE, PAID_DATE, IS_ABANDONDED"+
" from COMMISSION_PAYMENT_DETAILS pd where pd.sales_person_id=?");
            pstmt.setString(1,Sales_id);
            rs = pstmt.executeQuery();
            out.print("<table border=1 cellspacing=0 cellpadding=0 class=bill-listing><tr><B><td><B>Commission Id</td><td><B>Payment Amount</td><td><B>Payment Schedule Date</td><td><B>Paid Date</td><td><B>Is Abandoned</td></B></tr>");
            while(rs.next())
            {
                out.print("<tr><td>"+rs.getString(1)+"</td><td>"+rs.getString(2)+"</td><td>"+rs.getString(3)+"</td><td>"+rs.getString(4)+"</td><td>"+rs.getString(5)+"</td></tr>");
            }
            out.print("</table>");
            DBUtil.closeResultSet(rs);
            DBUtil.closeStatement(pstmt);
            DBUtil.closeConnection(con);
        }catch(SQLException ex) {
                exception = "SQL Exception";
                out.print(exception+":"+ex);
            } catch(Exception ex) {
                exception = "Other Than SQL Exception";
                out.print(exception+":"+ex);
            }

       } 
    %>
    </table>

            
            
            <%--
    This example uses JSTL, uncomment the taglib directive above.
    To test, display the page like this: index.jsp?sayHello=true&name=Murphy
    --%>
            <%--
    <c:if test="${param.sayHello}">
        <!-- Let's welcome the user ${param.name} -->
        Hello ${param.name}!
    </c:if>
    --%>
        </div>
    </body>
</html>
