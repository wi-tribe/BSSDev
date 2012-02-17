<?php
        session_start();
	ob_start();
	include_once("../util_config.php");
	$conf=new config();
	
        include_once(UTIL_CLASSDIR."/SmartsUtil.php");	
   
	$smarts_obj = new SmartsUtil();
	$searchInfo['search_type'] = $_POST['txt_search_type'];
	$searchInfo['search_term'] = $_POST['txt_search_term'];
	$result = $smarts_obj->searchAccount($searchInfo);

$count =count($result);

$output_buffer = "<table id='result_table' border='0' cellpadding='0' cellspacing='0' width='100%'>";

if($count > 0)
{
    $output_buffer .= "<tr align='left' class='bigBoldFont'><td>Account ID </td><td>Name</td><td>contact Number Number</td><td>MAC Address</td><td>Action</td></tr>";
    for($i = 0; $i < $count; $i ++)
    {
        $output_buffer .= "<tr align='left'>
                        <td>" .$result[$i]['CUSTOMER_ID'] . "</td>
                        <td>" . $result[$i]['FIRST_NAME']." ".$result[$i]['LAST_NAME'] . "</td>
                        <td>" . $result[$i]['TELEPHONE_NUMBER']."/".$result[$i]['MOBILE_NUMBER'] ."</td>
                        <td>" . $result[$i]['CPE_MAC_ADDRESS'] ."</td>
                        <td>
                        <a href='javascript:get_values(". $result[$i]['CUSTOMER_ID'] .",". $result[$i]['CUSTOMER_ID'] .",". '"View"' .")' class='orangeText'>View</a>";
	}
}else
{
    $output_buffer .= "<tr><td align='center'>Search resulted with no records</td></tr>";
}

$output_buffer .= "</table>";
echo $output_buffer;
?>
