<?php
	include_once('../util_config.php');
        
?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />

<script type="text/javascript" src="../js/jquery.js"></script>

<link href="../css/style.css" rel="stylesheet" type="text/css">
<link href="../css/style_order.css" rel="stylesheet" type="text/css">
<title>Customer Summary Page</title></head>
<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">
<script type="text/javascript">

    $(document).ready(function(){

        $("#user_div").hide();
        $("#customer_detail").hide();
        $("#customer_contact").hide();
        $("#customer_address").hide();
        $("#customer_account").hide();
        $("#customer_package").text("").hide();
        $("#customer_invoice").text("").hide();


        $("#search_form").submit(function(){
            $("#user_div").hide();
            $("#customer_detail").text("").hide();
            $("#customer_contact").text("").hide();
            $("#customer_address").text("").hide();
            $("#customer_account").text("").hide();
            $("#customer_package").text("").hide();
            $("#customer_invoice").text("").hide();

            var txt_search_term = $("#txt_search_term").val();
            var txt_search_type = $("#txt_search_type").val();

            if(txt_search_term == "")
                {
                    alert("Please enter any search term!");
                    return false;
                }
                //start of post function
				//alert("test");
                $.post("../FormProcessor/customer_address_post.php",{txt_search_term: txt_search_term, txt_search_type: txt_search_type},
                function(data){
                             $("#user_div").html(data).show("normal");
                             
                });
            
            return false;
        });
        
    });

    function get_values(cust_id, sal_id, page_type )
    {
        $("#customer_detail").hide();
        $("#customer_contact").hide();
        $("#customer_address").hide();
        $("#customer_account").hide();
        $("#customer_invoice").hide();
        var customer_id = cust_id;
        var sales_id = sal_id;

        if( page_type == "View" )
            {
                $.get("../FormProcessor/get_user_details.php",{cust_id: customer_id, sal_id: sales_id, call: "view"}, function(data){

                    $("#customer_data").html(data).show("normal");
                });
               /* $.get("get_user_package_type.php",{cust_id: customer_id, sal_id: sales_id, call: "view"}, function(data){

                    $("#customer_package").html(data).show("normal");
                });
                $.get("get_user_contacts.php",{cust_id: customer_id, sal_id: sales_id, call: "view"}, function(data){

                    $("#customer_contact").html(data).show("normal");
                });
                $.get("get_user_address.php",{cust_id: customer_id, sal_id: sales_id, call: "view"}, function(data){
                    $("#customer_address").html(data).show("normal");

                });
                $.get("get_user_account.php",{cust_id: customer_id, sal_id: sales_id, call: "view"}, function(data){
                    $("#customer_account").html(data).show("normal");

                });
                $.get("get_user_invoice.php",{cust_id: customer_id, sal_id: sales_id, call: "view"}, function(data){
                    $("#customer_invoice").html(data).show("normal");

                }); */


                
            }
            else
                {
                     $.get("get_user_details.php",{cust_id: customer_id, sal_id: sales_id, call: "edit"}, function(data){
                        $("#customer_data").html(data).show("normal");
                        });
                     $.get("get_user_package_type.php",{cust_id: customer_id, sal_id: sales_id, call: "edit"}, function(data){

                             $("#customer_package").html(data).show("normal");
                         });
                     $.get("get_user_contacts.php",{cust_id: customer_id, sal_id: sales_id, call: "edit"}, function(data){
                          $("#customer_contact").html(data).show("normal");

                     });
                     $.get("get_user_address.php",{cust_id: customer_id, sal_id: sales_id, call: "edit"}, function(data){
                        $("#customer_address").html(data).show("normal");

                     });
                     $.get("get_user_account.php",{cust_id: customer_id, sal_id: sales_id, call: "edit"}, function(data){
                        $("#customer_account").html(data).show("normal");

                     });
                    
                }


    }









</script>
<table width="1000" align="center" cellpadding="0" cellspacing="0">
  <tr>
    <td align="right" valign="top" bgcolor="#f7f7f7">
    <?php include_once("../header.php"); ?>
    </td>
  </tr>
  <tr>
    <td><br>
      <table width="100%" border="0" align="center" cellpadding="0" cellspacing="0" bordercolor="#CCCCCC" bgcolor="#f7f7f7" class="td">
      <tr>
        <td>
        <!-- ================================= Start Content TD =============================== -->



<table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td align="center" class="td">
			<span class="heading"> Customer Summary Page </span>	</td>
  </tr>
  <tr>
    <td>&nbsp;</td>
  </tr>
  <tr>
    
    <td>
	<form name="search_form" id="search_form" action="<?php $_SERVER['PHP_SELF']; ?>" method="post"  >
	<table border="0" cellspacing="5" cellpadding="5" align="center">
  <tr>
    <td>Search Term: </td>
    <td><input type="text" name="txt_search_term" id="txt_search_term" class="textbox" /></td>
    <td>Search Type </td>
    <td><select name="txt_search_type" id="txt_search_type" class="textbox">
		<option value="customer_id">Account ID</option>
		<option value="mac_address">MAC Address</option>
		<option value="name">Name</option>
		<option value="phone_number">Phone Number</option>
		
    </select>    </td>
    <td>&nbsp;</td>
	<td><input type="submit" name="Submit" value="Search" class="button" /></td>
  </tr>
</table>
</form>	</td>
  </tr>
   <tr>
    
    <td>
    <p>
    <div id="user_div" class="normalFont">
        
   </div></p>
    </td>
  </tr>
   <tr>
    
    <td>
	<div id="customer_data" class="normalFont"></div>
	</div>
	</td>
  </tr>
</table>


			

        <!-- =============================== End Content TD ================================== -->
        </td>
      </tr>
    </table>
    <br></td>
  </tr>
  	<tr>
		<td>
        <?php include_once("../footer.php"); ?>
		</td>
	</tr>
</table>
<!-- End ImageReady Slices -->
</body>
</html>