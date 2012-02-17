<?php
        session_start();
	ob_start();
	include_once("../util_config.php");
	$conf=new config();
	include_once(UTIL_CLASSDIR."/SalesPersonnel.php");

	$sales_obj = new SalesPersonnel();
	$sales_rep = $sales_obj->getCurrentCSR(); 
        $users = $sales_obj->getShopExecutives($_POST['channel']);

?>
<select name='user' id='user' class="selectbox">
<option value="ALL"> -- ALL -- </option>
<?	foreach($users as $arr)
{ ?>
        <option value="<?=$arr['SALESPERSONNEL_ID']?>"><?=$arr['SALESPERSONNEL_ID']?> :: <?=$arr['FULL_NAME']?></option>
<?	} ?>
</select>