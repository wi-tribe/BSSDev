<?php
        session_start();
	ob_start();
	include_once("../util_config.php");
	$conf=new config();
	include_once(UTIL_CLASSDIR."/SalesPersonnel.php");
        
	$sales_obj = new SalesPersonnel();
	$sales_rep = $sales_obj->getCurrentCSR();

        $channels = $sales_obj->getRegionCCShops($_POST['region']);

?>
<select name='channel' id='channel' class="selectbox" onchange="popualteUsers();">
<option value="ALL"> -- ALL -- </option>
<?	foreach($channels as $arr)
{ ?>
        <option value="<?=$arr['SHOP_ID']?>"><?=$arr['SHOP_ID']?> ::: <?=$arr['SHOP_NAME']?></option>
<?	} ?>
</select>