<?php
session_start("VIMS");
set_time_limit(700);

//ob_start();
include_once("../../vims_config.php");
include_once(CLASSDIR."/Channel.php");
include_once(CLASSDIR."/Discount.php");

//check permission
if(!$GLOBALS['_GACL']->checkPermission($_SESSION['username'],'discountadd'))
{
	echo "Permission denied";
	exit;
}

if($_POST['discount_type_id']==null)
    exit();

$dis_obj = new Discount();
$dis_types = $dis_obj->getdisctypeName($_POST['discount_type_id']);

?>
<select name='discount' id='discount' class="selectbox">
			   <option value=""> --Discount-- </option>
                           <? foreach ($dis_types as $arr) { ?>
                           <? if($arr['discount_type_name']=='percentage'){
                                $pgranule = 0.5; $perc=1;
                                 while($perc <=50.00){  ?>
                                <option value='<?=($perc)?>'><?=$perc?>%</option>
                                <?  $perc=$perc+$pgranule; }?>
                           <? }?>
                           <? if($arr['discount_type_name']=='quantity'){
                               $qgranule = 1; $qty=1;
                               while($qty <= 100){?>
                                <option value='<?=($qty)?>'><?=$qty?> voucher</option>
                                 <?  $qty=$qty+$qgranule; }?>
                                <? } ?>
                            <? if($arr['discount_type_name']=='denomination'){
                           $dgranule = 50; $denomination=100;
                           while($denomination <= 5000){?>
                            <option value='<?=($denomination)?>'><?=$denomination?> Rupees</option>
                             <?  $denomination=$denomination+$dgranule; }?>
                            <? } ?>
                           <? }?>
                              </select>
