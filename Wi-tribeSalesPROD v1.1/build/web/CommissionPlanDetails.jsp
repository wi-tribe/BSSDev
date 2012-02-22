<%@ page contentType="text/html" import="com.witribe.dao.ChannelList,com.witribe.vo.planList,com.witribe.vo.paymentRule,com.witribe.vo.commissionRule,java.util.*,java.sql.SQLException"%>
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
        <title>Plan Summary</title>
    </head>
    <body>
        <table border=1 cellspacing="0" cellpadding="0" class="bill-listing"><tr><td colspan="8"><B>Plan Summary</td></tr>  
            <tr><B><td>Plan Id</td><td>Description</td><td>Plan Type</td><td>Applies To(CSE/TL/RSM/SD)</td><td>Status</td><td>Valid From</td><td>Valid To</td><td></td></B></tr>
        <%
        String exception = "";
        String PlanId="";
        String Commission_Type="";
        boolean valid_plan=false;
       if(request.getQueryString() != null)
       {    
        PlanId=request.getParameter("PlanId");
        //out.println("PlanId="+PlanId);
        }
        try {
            ChannelList cho = new ChannelList();
            valid_plan=cho.validate_Plan(PlanId);
             List Plan = cho.getPlan(PlanId); 
             
           if(Plan.size() > 0) {
                     planList plist = (planList)Plan.get(0);
                     out.print("<tr><td>"+plist.getPlanId()+"</td><td>"+plist.getCityZone()+"</td><td>"+plist.getPlanType()+"</td><td>"+cho.Applies_To(plist.getAppliesTo())+"</td><td>"+plist.getStatus()+"</td><td>"+plist.getValidFrom()+"</td><td>"+plist.getValidTo()+"</td><td><a href=./CommissionPlan.do?PlanId="+PlanId+">Edit Plan</a></td></tr>");   
           }  
           if(valid_plan==false)
           {
                 out.print("<tr><td colspan=8><B>NOTE:</B>This is an Incomplete Plan so you need to create rules for all commission Types use Edit Plan link shown above</td></tr>");
           }    
        } catch(SQLException ex) {
            exception = "SQL Exception";
            out.print(exception+":"+ex);
        } catch(Exception ex) {
            exception = "Other Than SQL Exception";
            out.print(exception+":"+ex);
        }
      
    %>
    <tr><td colspan="8"><B>Payment Rule</td></tr>  
    <% 
            try {
             ChannelList cho = new ChannelList();   
             List payrule = cho.getPayRule(PlanId); 
           if(payrule.size() > 0) {
                for(int i=0; i< payrule.size();i++) {
                     paymentRule paymentrule = (paymentRule)payrule.get(i);
                     if(paymentrule.getDuration_Type()==null)
                     {    
                        out.print("<tr><td colspan=8>"+"Payment Method can't be defined for Commission Type Deduction");
                        Commission_Type="4";//--i.e Deduction
                     }
                     else
                     {
                         out.print("<tr><td colspan=8>"+paymentrule.getAmout()+" percent of commission is paid in "+paymentrule.getDuration()+" "+paymentrule.getDuration_Type()+"</td></tr>");   
                     }    
            }
                   //  out.print("<tr><td colspan=7></td><td><a href=./PaymentRule.do?plan_id="+PlanId+">Edit Payment Rule</a></td></tr>");   
           }   
           else
               out.print("<tr><td colspan=5>Payment Rule is Not defined</td><td></td></tr>");
        } catch(SQLException ex) {
            exception = "SQL Exception";
            out.print(exception+":"+ex);
        } catch(Exception ex) {
            exception = "Other Than SQL Exception";
            out.print(exception+":"+ex);
        }
      
    %>
        <tr><td colspan="8"><B>Commission Rule</td></tr>  
        <tr><B><td>Commission Type</td><td>Eligibility From</td><td>Eligibility To</td><td>Amount</td><td>Eligibility Period</td><td colspan=3>Eligibilty Measure</td></B></tr>
    <%    
                try {
            ChannelList cho = new ChannelList();

             List rulelist = cho.getCommissionRule(PlanId); 
             String measuretype="";
             
           if(rulelist.size() > 0) {
                for(int i=0; i< rulelist.size();i++) {
                     commissionRule Crulelist = (commissionRule)rulelist.get(i);
                  if(Crulelist.getRuleType().equals("Deduction"))
                  {    
                     if(Crulelist.getEligibilty_Measure().equals("1"))
                     {
                        measuretype="Days";
                      }  
                     else if (Crulelist.getEligibilty_Measure().equals("2"))   
                      {  
                             measuretype="Months";
                      }       
                      else
                      {
                             measuretype="Years";
                      }    
                        
                  }
                     out.print("<tr><td>"+Crulelist.getRuleType()+"</td><td>"+Crulelist.getEligibility_From()+"</td><td>"+Crulelist.getEligibility_To()+"</td><td >"+Crulelist.getAmount()+"</td><td>"+Crulelist.getEligibility_Period()+"</td><td colspan=3>"+measuretype+"</td></tr>");   
                     
            }
           }    
          else
               out.print("<tr><td colspan=5>Commission Rule is Not defined</td><td></td></tr>");  
        } catch(SQLException ex) {
            exception = "SQL Exception";
            out.print(exception+":"+ex);
        } catch(Exception ex) {
            exception = "Other Than SQL Exception";
            out.print(exception+":"+ex);
        }
      
    %>
    </table>
    
    <h1></h1>
   

    <%--
    <c:if test="${param.sayHello}">
        <!-- Let's welcome the user ${param.name} -->
        Hello ${param.name}!
    </c:if>
    --%>
    
    </body>
</html>
