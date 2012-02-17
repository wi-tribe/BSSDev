<?php
       session_start();
	ob_start();
	include_once("../util_config.php");
	$conf=new config();
	include_once(UTIL_CLASSDIR."/SalesPersonnel.php");

	$sales_obj = new SalesPersonnel();

        $executive = $_POST['executive'];
        $customers = $_POST['customers'];

        if(count($customers)==0)
        {
            echo "<strong>Please Select Customer!!!!</strong><br>";
            exit();
        }

        $sales_obj->allocateCallsToExecutives($executive, $customers);
        $MSG="<strong>Successfuly Allocated </strong><br> ";
?>
<?=$MSG?>



