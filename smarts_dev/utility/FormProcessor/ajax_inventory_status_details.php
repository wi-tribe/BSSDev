<?php

session_start();
ob_start();
include_once("../util_config.php");
$conf=new config();

include_once(UTIL_CLASSDIR."/SalesPersonnel.php");
include_once(CLASSDIR."/Logging.php");

$log_obj = new Logging();

$invenory_id = trim($_POST['inventoryID']);

$filters = $_POST;
$customer=new SalesPersonnel();
$cdata=$customer->getDeviceAssignmentHistory($invenory_id);

if($cdata==null) {
    echo "No History Found for Selected Device!";
    $log_obj->LogMsg(__CLASS__,__METHOD__, __FILE__,__LINE__,"INFO: ".$_SESSION['username']." No record found against customer mobile/telephone number ".$Customerno);
    exit();
}

$log_obj->LogMsg(__CLASS__,__METHOD__, __FILE__,__LINE__,"INFO: ".$_SESSION['username']." Successfuly verified against customer mobile/telephone number ".$Customerno);

?>

<table width="100%">
    <tr>
        <td>
            <strong>Device History </strong>
        </td>
    </tr>
</table>
<table id="deviceDetails" width="100%" border="1">
    <tr>
        <td class="orangeText"><font size="2">Serial # </font></td>
        <td class="orangeText"><font size="2">INVENTORY ID </font></td>
         <td class="orangeText"><font size="2">ACTION DATE </font></td>
        <td class="orangeText"><font size="2">FROM SHOP </font></td>
        <td class="orangeText"><font size="2">TO SHOP </font></td>
        <td class="orangeText"><font size="2">ASSIGNED FROM </font></td>
        <td class="orangeText"><font size="2">ASSIGNED TO </font></td>
        <td class="orangeText"><font size="2">ASSIGNED BY </font></td>
        <td class="orangeText"><font size="2">OLD DEVICE STATUS </font></td>
        <td class="orangeText"><font size="2">CURRENT DEVICE STATUS </font></td>        
    </tr>
<? $index=1; foreach($cdata as $arr) {?>
    <tr>
        <td><font size="2"><?=$index++?></font></td>
        <td><font size="2"><?=$arr['INVENTORY_ID']?></font></td>
        <td ><font size="2"><?=$arr['ACTIONDATE']?></font></td>
        <td><font size="2"><?=$arr['FROM_SHOP_ID']?></font></td>
        <td><font size="2"><?=$arr['TO_SHOP_ID']?></font></td>
        <td ><font size="2"><?=$arr['ASSIGNEDFROM']?></font></td>
         <td ><font size="2"><?=$arr['ASSIGNEDTO']?></font></td>
        <td><font size="2"><?=$arr['ASSIGNEDBY']?></font></td>
        <td ><font size="2"><?=$arr['DEVICE_OLD_STATUS']?></font></td>
        <td ><font size="2"><?=$arr['DEVICE_NEW_STATUS']?></font></td>
    </tr>
    <?}?>
</table>
<script type='text/javascript'>
var pager2 = new Pager('deviceDetails', 10);
pager2.init();
pager2.showPageNav('pager2', 'pageNavPosition2');
pager2.showPage(1);
</script> 