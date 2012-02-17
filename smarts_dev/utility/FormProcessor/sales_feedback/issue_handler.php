<?php

include_once("/var/www/html/smarts/utility/FormProcessor/sales_feedback/JIRAWrapper.php");
   
    $jira = new JIRAWrapper();
    $jira->LoadWSDL();
        
$summary = $_POST['txt_issue_type'];
$customer_id = $_POST['txt_customer_id'];
$notification_type = $_POST['rad_notification_type'];
$shop_name = $_POST['txt_shop_name'];
$details = $_POST['txt_details'];
$time_of_call = $_POST['txt_time_call'];
$email_address = $_POST['txt_email_address'];

$jira->login($shop_name, "shop123shop");


$description =
"

Call Type: $notification_type
Shop Name: $shop_name
Time of Call: $time_of_call
Email Address: $email_address
Details: $details

";

//$proj_array = $jira->getProjects();
//
//print_r($proj_array);
//ID = 10030
//Key= BOSS
//Key=WT
//ID=10010 
//
//$issue_types = $jira->getIssueTypesForProject("10030");
//
//$issue_count = count($issue_types);
//
//for( $i = 0 ; $i < $issue_count; $i ++)
//{
//    echo $issue_types[$i]['name'] . ", " . $issue_types[$i]["id"] . "<br />";
//}
// Feedback - Shop = 54


//$issue_priorities = $jira->getPriorities();
//print_r($issue_priorities);
//Major - 3
  

$response = $jira->createIssue("WT", "54", $summary, $description, "3", $customer_id, $shop_name , $time_of_call);
print_r($response);
exit(0);
if( $response )
{
    header("Location: http://smarts.wi-tribe.net.pk/sales/Pages/sales_feedback.php?msg=success");
}

?>
