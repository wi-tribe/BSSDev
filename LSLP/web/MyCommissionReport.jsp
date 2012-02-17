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
        <title>Commission Report</title>
                <div><h3 class="heading">My Commission Report</h3></div> 
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
            pstmt=con.prepareStatement("select cdt.commission_id,sd.full_name,ct.comm_start_date,ct.comm_end_date,ct.total_sale_amount,cdt.order_id,"+
    "cdt.comm_amount  from COMMISSION_TBL ct,COMMISSION_DETAIL_TBL cdt,SALESPERSONNEL_DETAILS sd where "+
    "ct.ID=cdt.commission_id and ct.sales_id=?");
            pstmt.setString(1,Sales_id);
            rs = pstmt.executeQuery();
            out.print("<table border=1 cellspacing=0 cellpadding=0 class=bill-listing><tr><B><td><B>Commission Id</td><td><B>Fullname</td><td><B>Commission Start Date</td><td><B>Commission End Date</td>"+
                    "<td><B>Total Sale Amount</td><td><B>Order Id</td><td><B>Commission Amount</td></B></tr>"
                    );
            while(rs.next())
            {
                out.print("<tr><td>"+rs.getString(1)+"</td><td>"+rs.getString(2)+"</td><td>"+rs.getString(3)+"</td><td>"+rs.getString(4)+"</td><td>"+rs.getString(5)+"</td><td>"+rs.getString(6)+"</td><td>"+rs.getString(7)+"</td></tr>");
            }
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
