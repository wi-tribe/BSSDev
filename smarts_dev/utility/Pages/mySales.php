<?php
	include_once('../util_config.php');
	$conf=new config();
?>

            <form name="mySales" id="mySales" method="post" action="FormProcessor/Reports/salesReport.php">
                <table>
                    <tr>
						<td>Start Date:</td>
						<td>
							<input name="from_date" id="from_date" class="date-pick dp-applied" value= "<?=date('m/01/Y')?>"><!--a href="#" class="dp-choose-date" title="Choose date">Choose date</a-->
						</td>
						<td> End Date:</td>
						<td>
							<input name="to_date" id="to_date" class="date-pick dp-applied" value= "<?=date('m/d/Y')?>"> 
						</td>
						<td align='right'>
							<input name="FormAction" type='button' id="display" value='Display Report' onclick="javascript:processForm( 'mySales','FormProcessor/mySales.php','Report' );">
						</td>

                </table>
		   </form>
		   <div id="Report"></div>
<script>
processForm( 'mySales','FormProcessor/mySales.php','Report' );
</script> 