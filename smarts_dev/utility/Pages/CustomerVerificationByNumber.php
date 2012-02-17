<?php
	session_start();
	ob_start();
	include_once("../util_config.php");
	$conf=new config();

?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" dir="ltr" lang="ur">
<head>
<title>wi-tribe Coverage Tool </title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link href="../../vims/CSS/style.css" rel="stylesheet" type="text/css"></link>
<style type="text/css">

.pg-normal {
    color: black;
    font-weight: normal;
    text-decoration: none;
    cursor: pointer;
}
.pg-selected {
    color: black;
    font-weight: bold;
    text-decoration: underline;
    cursor: pointer;
}
</style>
<link href="../css/jquery-ui.css" rel="stylesheet" type="text/css" />
<link href="../../vims/styles/style.css" rel="stylesheet" type="text/css"></link>
<link href="../css/jquery-ui.css" rel="stylesheet" type="text/css"></link>
<script type="text/javascript" src="../js/jquery.min.js"></script>
<script type="text/javascript" src="../js/jquery-ui.min.js"></script>
<script type="text/javascript" src="../js/paging.js"></script>
<style type="text/css">
<!--
.style2 {font-size: 16px}
-->
</style>
</head>
<title>Verify Customer By Phone Number</title>
<script type="text/javascript">
function VerfiyCustomer()
{
        document.getElementById('Customer_info_div').innerHTML="";
	var Customerno = $("#customerno").val();
	var post_func = function()
				{

					var effect = function(data){ $("#verification_info").html(data).show("normal"); 	};
					$.post("FormProcessor/ajax_verifyCustomerBy_number.php",{Customerno: Customerno},effect);
				}
	$("#verification_info").html("<img src='../../../images/loading.gif' /> Loading...");
	$("#verification_info").show("normal",post_func);
}

function viewDetails(cnic)
{
	var post_func = function()
				{

					var effect = function(data){ $("#Customer_info_div").html(data).show("normal"); 	};
					$.post("FormProcessor/ajax_verifyCustomerDetails.php",{Customerno: cnic},effect);
				}
	$("#Customer_info_div").html("<img src='../../../images/loading.gif' /> Loading...");
	$("#Customer_info_div").show("normal",post_func);
}

</script>
<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">

      <table width="100%" border="0" align="center" cellpadding="0" cellspacing="0" bordercolor="#CCCCCC" bgcolor="#f7f7f7" >
    <tr>
    <td align="center">
			<span class="heading"> Customer Verification by Telephone/Mobile Number </span>	</td>
  </tr>
      <tr> 
        <td>
        <!-- ================================= Start Content TD =============================== -->
                         <form name="search_form" id="search_form" action="" method="post"  >
                            <table width="100%" border="0" cellspacing="5" cellpadding="5" align="center">
                         <tr>
                            <td class="textboxBur">Telephone/Mobile Number: </td>
                            <td class="textboxBur"><input type="text" name="customerno" id="customerno"  /></td>
                            <td align="center"><input type="button" value="Verify Customer" class="button" onclick="javascript:VerfiyCustomer();"/></td>
                          </tr>
     <!--                     <tr>
                            <td class="textboxBur">Speed</td>
                            <td class="textboxBur"><select >
                                <option value=""> -- select speed --</option>
                                <option value="post_b_256_64">256 Kbps</option>
                                <option value="post_b_512_128">512 Kbps</option>
                                <option value="post_b_1024_256">1 Mbps</option>
                            </select>
                            </td>
                            <td><input type="button" value="Activate CPE" class="button" onclick="javascript:activateCPE();";/></td>
                          <tr> -->
                          </table>
                        </form>

        <!-- =============================== End Content TD ================================== -->
       </td>
      </tr>
          <tr>
              <td>
             
                                         <div id="verification_info"></div>
                                     
              </td>
          </tr>
          <tr>
              <td>   
                  <div id="Customer_info_div"></div>
              </td>
          </tr>
    </table>
<!-- End ImageReady Slices -->
</body>
</html>