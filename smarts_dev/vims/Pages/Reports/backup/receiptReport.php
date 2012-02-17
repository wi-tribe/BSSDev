<?PHP
session_start("VIMS");
set_time_limit(200);

//ob_start();
include_once("../../vims_config.php");
$conf=new config();
include_once(CLASSDIR."/POS.php");

$return = $GLOBALS['_GACL']->getaccessLevel($_SESSION['username'],'receiptreport');
if(!$return){ echo "Permission denied";	exit;}

if($return=='NWD')
  {
    $ch_obj = new Channel();
    $Regions = $ch_obj->getRegions(); 
  }
 if($return=='Region')
 {
     $ch_obj = new Channel();
     $channels = $ch_obj->getallshopids();
 }

?>
<script>
$(function()
{
   
    var myDate = new Date();
    var month = myDate.getMonth() + 1;
    var date1 = month + '/' + '01' + '/' + myDate.getFullYear();
    $("#start_date").val(date1);
    
    var myDate = new Date();
    var month = myDate.getMonth() + 1;
    var date2 = month + '/' + myDate.getDate() + '/' + myDate.getFullYear();
    $("#end_date").val(date2);

	$('#start_date').datepicker(
		{
			changeMonth: true,
			changeYear: true
		}
	);
	$('#end_date').datepicker(
		{
			changeMonth: true,
			changeYear: true
		}
	);
});

</script>
<script type="text/JavaScript">
  function showhidefield(id1)
  {
      document.getElementById(id1).style.visibility = "visible";
     // document.getElementById(id2).style.visibility = "visible";
  }
  function disableDisplayReport()
  {
      document.receipt_report.display.disabled = true;
  }
</script>

            <form name="receipt_report" id="receipt_report" method="post" action="FormProcessor/Reports/receiptReport.php">
                <table>
                 <input type="hidden" name ="permission" id="permission" value="<?=$return?>">
                  <?php if($return=='NWD') { ?>
                    <tr>
                     <td> Region: </td>
                         <td>
                             <select name='region' id='region' class="selectbox" onchange="javascript:processForm( 'receipt_report','FormProcessor/Reports/getregionShops.php','channeldiv' ); showhidefield('channel_text');">
				<option value=""> -- Region -- </option>
                                    <?
                                    foreach($Regions as $arr) { ?>
                                    <option value="<?=$arr['location_id']?>"><?=$arr['location_name']?> </option>
                                    <? } ?>
                            </select>
                        </td>
                         <td id="channel_text" style="visibility: hidden"> Channel: </td>
                        <td><div name="channeldiv" id="channeldiv">
                        <select name='channel' id='channel' class="selectbox" style="visibility: hidden">
				<option value=""> -- Channel -- </option>
                        </select>
                            </div>
                        </td>
                    </tr>
                </table>
                    <? }?>
                     <?  if($return=='Region') { ?>
                    <table>
                    <tr>
                        <td id="channel_text"> Channel: </td>
                        <td>
                        <select name='channel' id='channel' class="selectbox" >
				<option value=""> -- Channel -- </option>
                                <?
                                foreach($channels as $arr) { ?>
                                <option value="<?=$arr['shop_id']?>"><?=$arr['shop_id']?>--<?=$arr['shop_name']?></option>
                                <? } ?>
                        </select>
                        </td>
                    </tr>
                    </table>
                        <? } ?>
                    <table>
                    <tr>
                    <td> Start Date:</td>
                    <td>
				<input name="start_date" id="start_date" class="date-pick dp-applied"><!--a href="#" class="dp-choose-date" title="Choose date">Choose date</a-->
                    </td>

                    <td> End Date:</td>
                    <td>
				<input name="end_date" id="end_date" class="date-pick dp-applied">
                    </td>
                    
                   
                    <td align='right'>
                                <input name="FormAction" type='button' id="display" value='Display Report' onclick="javascript:processForm( 'receipt_report','FormProcessor/Reports/receiptReport.php','Report' );"></td>
                    </tr>
                    
                </table>
                 
          <div id="Report"></div>
          <!-- end listing vouchers -->
		   </form>

<script>
//processForm( 'collection_date','FormProcessor/Reports/salesReport.php','Report' );
</script>