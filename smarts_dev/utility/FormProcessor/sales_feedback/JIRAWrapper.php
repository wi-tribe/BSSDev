<?php
/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * Description of JIRAWrapper
 *
 * @author Waqas Nasir Toor
 */

include_once('/var/www/html/smarts/utility/FormProcessor/sales_feedback/lib/nusoap.php');

class JIRAWrapper {
    //put your code here

   private $token;
   private $user_info_arr;
   private $soap_client;
   private $username;
   

   function __construct()
   {
       
   }
   
   function LoadWSDL()
   {
        $wsdl_url = 'http://helpdesk.wi-tribe.net.pk:8080/rpc/soap/jirasoapservice-v2?wsdl';
        $this->soap_client = new nusoap_client($wsdl_url, 'wsdl');
        $this->soap_client->loadWSDL();
   }

   function login( $username , $password )
   {
       $this->username = $username;
        $params = array( $username , $password );
       $this->token = $this->soap_client->call("login" , $params);
       return $this->token;       
   }
   
   function logout()
   {
            if( $this->token )
            {
                $this->soap_client->call("logout" , array( $this->token));
                return "Success";
            }
            else
                return "false";
   }
   function getIssue( $issue_key )
   {

        if( $this->token )
        {
            $remote_issue = $this->soap_client->call("getIssue" , array( $this->token, $issue_key));
            return $remote_issue;
        }
        else
            return "false";

   }
   function getIssesFromFilter( $filter_id )
   {
       if( $this->token )
       {
           $remote_issues = $this->soap_client->call("getIssuesFromFilter" , array( $this->token, $filter_id));
           return $remote_issues;
       }
       else
            return "false";

   }
   function getIssueTypesForProject( $project_id )
   {
       if( $this->token )
       {
           $remote_types = $this->soap_client->call("getIssueTypesForProject", array( $this->token , $project_id));
           return $remote_types;
       }
       else
            return "false";
       
   }
   function getProjects( )
   {
       if( $this->token )
       {
           $remote_projects = $this->soap_client->call("getProjectsNoSchemes" , array($this->token));
           return $remote_projects;
       }
       else
            return "false";
       
   }
   function getFavouriteFilters()
   {
       if( $this->token )
       {
           $remote_filters = $this->soap_client->call("getFavouriteFilters", array( $this->token));
           return $remote_filters;
       }
       else
            return "false";
       
   }
   
   function getStatuses()
   {
       if( $this->token )
       {
           $remote_statuses = $this->soap_client->call("getStatuses", array( $this->token));
           return $remote_statuses;
       }
       else
            return "false";

   }
   
   function getResolutions()
   {
       if( $this->token )
       {
           $remote_resolutions = $this->soap_client->call("getResolutions", array( $this->token));
           return $remote_resolutions;
       }
       else
            return "false";
   }
   
   function getUser()
   {
       if( $this->token )
       {
           $this->user_info_arr = $this->soap_client->call("getUser" , array( $this->token , $this->username));
           return $this->user_info_arr;
       }
       else
            return "false";
   }
   function getPriorities()
   {
       if( $this->token)
       {
           $remote_priorities = $this->soap_client->call("getPriorities", array($this->token));
           return $remote_priorities;
       }
       else
            return "false";
   }

   function createIssue( $project_key , $issue_type, $summary , $description, $priority, $customer_id , $sales_agent_name , $time_of_Call )
   {
       if($this->token)
       {
           $remote_issue = "";
           $remote_issue['project'] = $project_key;
           $remote_issue['type'] = $issue_type;
           $remote_issue['summary'] = $summary;
           $remote_issue['description'] = $description;
           $remote_issue['priority'] = $priority;
           $custom_fields[] = array("customfieldId" => "customfield_10004", "key" => null, "values" => array($customer_id));
           $custom_fields[] = array("customfieldId" => "customfield_10005", "key" => null, "values" => array($sales_agent_name));
           $custom_fields[] = array("customfieldId" => "customfield_10006", "key" => null, "values" => array($time_of_Call));
           $remote_issue['customFieldValues'] = $custom_fields;

           $response = $this->soap_client->call("createIssue" , array($this->token , $remote_issue) );
           return  $response;
       }
       else
           return "failed";
   }
   
   //util functions/Properties
   function getUserToken()
   {
       if( $this->token )
       {
        return $this->token;
       }
       else
       {
           return "false";
       }
   }
    
}
?>
