<?php
session_start("VIMS");
set_time_limit(700);

include('../../vims_config.php');
include_once(CLASSDIR."/Channel.php");
include_once(CLASSDIR."/Rental.php");
include_once(CLASSDIR."/Export.php");
include_once(CLASSDIR."/User.php");

$start_date = $_POST['start_date'];
$end_date = $_POST['end_date'];

$rental_obj = new Rental();
$data = $rental_obj->getMonthRentalPayables($start_date,$end_date);
//print_r($data);
if($_POST['export'] == 'Export Report')
	{
               $user_obj = new User();

		$fname = "RentalPayablesReport_".date("Y").date("m").date("d").".csv";
		$userdetail = $user_obj->getUserInfo($_SESSION['user_id']);
		$user = $userdetail[0]['first_name'].' '.$userdetail[0]['last_name'];
		
                $docInfoC = array(array("",'Title','Rental Payables Report',)
                                ,array("",'Start Date',$start_date)
                                ,array("",'End Date',$end_date)
                                ,array("",'Created By',$user)
                                ,array("",'','','')
                        );
                $dataHeading = array('Rental ID','Retailer User ID','First Name','Last Name','Retailer Shop ID','Shop Type ID','Payable Amount','Retailer Shop Name','Month','Retailer Creation Date','Status','Payment Date');
		$export_obj = new Export();
                $export_obj->CSVExport($dataHeading,$data,$docInfoC,$fname);
                exit();

	}
?>
<div align="right">
<form name='exportReport' id='exportReport' method='post' action="FormProcessor/Rental/Rental_Payables.php">
	 <input type="hidden" name="start_date" id="start_date" value="<?=$_POST['start_date']?>" />
	 <input type="hidden" name="end_date" id="end_date" value="<?=$_POST['end_date']?>" />
	 <input name="export" type="submit" id="export" value="Export Report">
</form>
</div>
<table width="100%">
	<tr>
                <td  class="textboxBur">S/N</td>
		<td class="textboxBur" align="center" ><strong>First Name</strong></td>
		<td class="textboxBur" align="center" ><strong>Last Name</strong></td>
		<td class="textboxBur" align="center" ><strong>Shop ID </strong></td>
                <td class="textboxBur" align="center" ><strong>Shop Name</strong></td>
                 <td class="textboxBur" align="center" ><strong>Creation Date</strong></td>
		<td class="textboxBur" align="center" ><strong>Payable Amount (PKR)</strong></td>
                <td class="textboxBur" align="center" ><strong>Month</strong></td>
                <td class="textboxBur" align="center" ><strong>Status</strong></td>
                <td class="textboxBur" align="center" ><strong>Edit</strong></td>
    </tr>
    <? $index=1;
	foreach ($data as $arr)
	{ ?>
            <? //if($arr['status']=='UnPaid')
                   //{ ?>
		<tr>
                   
                        <td><?=$index++?></td>
			<td class="textboxGray"><?=$arr['first_name']?></td>
			<td class="textboxGray"><?=$arr['last_name']?></td>
			<td class="textboxGray"><?=$arr['channel_id']?></td>
                        <td class="textboxGray"><?=$arr['channel_name']?></td>
                        <td class="textboxGray"><?=$arr['creation_date']?></td>
			<td class="textboxGray"><?=$arr['payable_amount']?></td>
			<td class="textboxGray"><?=$arr['for_month']?></td>
                        <td class="textboxGray"><?=$arr['status']?></td>
                        <td><a href="#" class= "options" onclick="javascript:AjaxPopUpWindow( 'Pages/Rental/EditRental_payables.php','rental_id=<?=$arr['rental_id']?>','top=100, left=100,height=300, width=600, status=no, menubar=no, resizable=no, scrollbars=no, toolbar=no,location=no, directories=no' );" >Details</a></td>
                       
		</tr>
                 <? //} ?>
<?	} ?>
</table>