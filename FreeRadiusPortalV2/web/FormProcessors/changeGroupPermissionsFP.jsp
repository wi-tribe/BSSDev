<%-- 
    Document   : changeGroupPermissionsFP
    Created on : Apr 18, 2011, 11:56:43 AM
    Author     : PKMDUsman
--%>

<%
            org.apache.log4j.Logger log = org.apache.log4j.Logger.getLogger(this.getClass());
%>

<%@page import="java.util.Iterator"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page import="DataBase.DBConnectivity"%>
<%@ page errorPage="../errorPage.jsp" %>

<%
            String username = (String) session.getAttribute("username");
            DBConnectivity dbUtil = new DBConnectivity();
            Iterator pIterator = null;
            String[] currentPermissions = null;

            if (username == null) {
                response.sendRedirect(response.encodeRedirectURL("index.jsp"));
                return;
            }

            String groupname = request.getParameter("groupName");
            String[] newpermissions = request.getParameterValues("permission[]");
            if (newpermissions == null) {
                System.out.println("Atleast one permission should be selected");
                response.sendRedirect(response.encodeRedirectURL("../selectGroup.jsp?changeStatus=pempty"));
            }
            

            List<String> pList = new ArrayList<String>();
            pList = dbUtil.getPermissions(groupname);
            pIterator = pList.iterator();

            currentPermissions = new String[pList.size()];
            int i = 0;
            System.out.println("Portal user " + username + " asked for updation of Group " + groupname + " whose current permissions are : ");
            log.info("Portal user " + username + " asked for updation of Group " + groupname + " whose current permissions are : ");
            while (pIterator.hasNext()) {
                currentPermissions[i] = (String) pIterator.next();
                System.out.println("Group -- " + groupname + " -- " + currentPermissions[i]);
                log.info("Group -- " + groupname + " -- " + currentPermissions[i]);
                i++;
            }

            String[][] permissionStatus = new String[(newpermissions.length + currentPermissions.length)][2];
            int index = 0;
            boolean permissionFound = false;

            for (int j = 0; j < newpermissions.length; j++) {
                permissionFound = false;
                for (int k = 0; k < currentPermissions.length; k++) {
                    if (newpermissions[j].equals(currentPermissions[k])) {
                        permissionStatus[index][0] = currentPermissions[k];
                        permissionStatus[index][1] = "updated";
                        index++;
                        permissionFound = true;
                        break;
                    }
                }
                if (permissionFound == false) {
                    permissionStatus[index][0] = newpermissions[j];
                    permissionStatus[index][1] = "add";
                    index++;
                }
            }

            int currentAdded = index;
            for (int m = 0; m < currentPermissions.length; m++) {
                permissionFound = false;
                for (int n = 0; n < currentAdded; n++) {
                    if (currentPermissions[m].equals(permissionStatus[n][0])) {
                        permissionFound = true;
                        break;
                    }
                }
                if (permissionFound == false) {
                    permissionStatus[index][0] = currentPermissions[m];
                    permissionStatus[index][1] = "delete";
                    index++;
                }
            }


            /* for(int a = 0; a<index; a++){
            System.out.println(permissionStatus[a][0] + "  ---  " + permissionStatus[a][1]);
            }*/


            boolean problemStatus = false;


            for (int x = 0; x < index; x++) {
                if (permissionStatus[x][1].equals("updated")) {
                    System.out.println("Permission " + permissionStatus[x][0] + " for group " + groupname + " already exist");
                    log.info("Permission " + permissionStatus[x][0] + " for group " + groupname + " already exist");
                } else if (permissionStatus[x][1].equals("add")) {
                    String addStatus = dbUtil.addGroupPermission(groupname, permissionStatus[x][0]);
                    if (addStatus.equals("inserted")) {
                        System.out.println("Permission " + permissionStatus[x][0] + " for group " + " has been successfully added");
                        log.info("Permission " + permissionStatus[x][0] + " for group " + " has been successfully added");
                    } else {
                        System.out.println("Problem in adding permission " + permissionStatus[x][0] + " for group " + groupname);
                        log.info("Problem in adding permission " + permissionStatus[x][0] + " for group " + groupname);
                        problemStatus = true;
                    }
                } else if (permissionStatus[x][1].equals("delete")) {
                    String deleteStatus = dbUtil.deleteGroupPermissions(groupname, permissionStatus[x][0]);
                    if (deleteStatus.equals("deleted")) {
                        System.out.println("Permission " + permissionStatus[x][0] + " for group " + " has been successfully deleted");
                        log.info("Permission " + permissionStatus[x][0] + " for group " + " has been successfully deleted");
                    } else {
                        System.out.println("Problem in deleting permission " + permissionStatus[x][0] + " for group " + groupname);
                        log.info("Problem in deleting permission " + permissionStatus[x][0] + " for group " + groupname);
                        problemStatus = true;
                    }
                } else {
                    System.out.println("Please check as option doesnot lie in any category");
                    log.info("Please check as option doesnot lie in any category");
                    problemStatus = true;
                }
            }
            if (problemStatus == true) {
                System.out.println("There was some problem in updating group permissions, so user " + username + " is send an error message");
                log.info("There was some problem in updating group permissions, so user " + username + " is send an error message");
                response.sendRedirect(response.encodeRedirectURL("../changeGroupPermission.jsp?changeStatus=problem"));
            } else {
                response.sendRedirect(response.encodeRedirectURL("../home.jsp?changeStatus=success"));
            }
















            /* String deleteStatus = dbUtil.deleteGroupPermissions(groupname);
            if (deleteStatus.equals("deleted")) {
            System.out.println("All previous permissions for " + groupname + " has been successfully deleted by " + username);
            log.info("All previous permissions for " + groupname + " has been successfully deleted by " + username);
            for (int i = 0; i < permissions.length; i++) {
            String addPermissionStatus = dbUtil.addGroupPermission(groupname, permissions[i]);
            if (!addPermissionStatus.equals("inserted")) {
            System.out.println("Problem in adding permission " + permissions[i] + " to " + groupname+" by user "+ username);
            log.info("Problem in adding permission " + permissions[i] + " to " + groupname+" by user "+ username);
            response.sendRedirect(response.encodeRedirectURL("../changeGroupPermission.jsp?changeStatus=problem"));
            }

            }
            System.out.println("Permissions are successfully added to the group "+ groupname + " by user " + username);
            log.info("Permissions are successfully added to the group "+ groupname + " by user " + username);
            System.out.println("permissions added for group " +groupname +" are metioned below.");
            log.info("permissions added for group " +groupname +" are metioned below.");
            for (int i = 0; i < permissions.length; i++) {
            System.out.println(groupname + " - " + permissions[i]);
            log.info(groupname + " - " + permissions[i]);
            }
            response.sendRedirect(response.encodeRedirectURL("../home.jsp?changeStatus=success"));

            } else {
            System.out.println("Problem in deleting group " + groupname+" permissions..please try again" + username);
            log.info("Problem in deleting group " + groupname+" permissions..please try again" + username);
            response.sendRedirect(response.encodeRedirectURL("../changeGroupPermission.jsp?result=problem"));
            }*/
%>



