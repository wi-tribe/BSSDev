<?PHP
	session_start("VIMS");
	set_time_limit(200);

	//ob_start();
	include_once("../../vims_config.php");
	$conf=new config();
	include_once(CLASSDIR."/POS.php");

	//check permission
	if(!$GLOBALS['_GACL']->checkPermission($_SESSION['username'],'rentalpayables'))
        {
            echo "Permission denied";
            exit;
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

            <form name="MonthlyRental" id="MonthlyRental" method="post" action="FormProcessor/Rental/Rental_Payables.php">
                 <input type="hidden" name ="permission" id="permission" value="<?=$return?>">

                    <table width="100%">
					<tr>
						<td colspan="2" align="center" style="font-size:16px"><strong>Monthly Rental Payables </strong></td>
					</tr>
										<tr>
						<td> Start Date <input name="start_date" id="start_date" class="date-pick dp-applied"></td>
						<td> End Date <input name="end_date" id="end_date" class="date-pick dp-applied">
						<input name="FormAction" type='button' id="display" value='Display Report' onclick="javascript:processForm( 'MonthlyRental','FormProcessor/Rental/Rental_Payables.php','Report' );">
						</td>
                    </tr>

				 </table>
             </form>
          <div id="Report"></div>
          <!-- end listing vouchers -->

<script>
//processForm( 'collection_date','FormProcessor/Reports/salesReport.php','Report' );

</script>