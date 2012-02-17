<%@ page contentType="text/html;charset=UTF-8" language="java"  import="com.witribe.bo.WitribeBO" %>

<% WitribeBO bo = new WitribeBO();%>
                    <%String coverage = bo.CheckCoverage(request.getParameter("plot"),request.getParameter("street"),request.getParameter("zone"),request.getParameter("city"));
                    if(coverage == null)
                        out.write("No Coverage");
                        else
                    out.write(coverage);%>
