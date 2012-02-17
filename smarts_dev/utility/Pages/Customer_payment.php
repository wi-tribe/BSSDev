<?php

	include_once("../util_config.php");
	$conf=new config();
	include_once(UTIL_CLASSDIR."/SalesPersonnel.php");
	$sales_obj = new SalesPersonnel();

	$sales_rep = $sales_obj->getCurrentCSR();
        $permission = new permissions();

        
        if(!($permission->checkPermission($sales_rep['USERID'], 'CustomerPayment') && $_SESSION['user_role_id']!='61' && $_SESSION['user_role_id']!='61'))
        {
            echo "Permission Denied";
            exit();
        }
?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" dir="ltr" lang="ur">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<script type="text/javascript">
    var GB_ROOT_DIR = "../js/greybox/";
</script>
</head>
<title>Customer Payments Page</title>
     <script type="text/javascript" src="../js/greybox/AJS.js"></script>
    <script type="text/javascript" src="../js/greybox/AJS_fx.js"></script>
    <script type="text/javascript" src="../js/greybox/gb_scripts.js"></script>
    <link href="../js/greybox/gb_styles.css" rel="stylesheet" type="text/css" media="all" />
    <link href="../../vims/CSS/style.css" rel="stylesheet" type="text/css"></link>
    <link href="../styles/style.css" rel="stylesheet" type="text/css"></link>
    <link href="../../vims/CSS/customerPaymentTable.css" rel="stylesheet" type="text/css"></link>
    <script type="text/javascript" src="../js/static_files/help.js"></script>
    <script src="../js/jquery/jquery.js" type="text/javascript" language="javascript"></script>

<script type="text/javascript">
    GB_myShow = function(caption, url, /* optional */ height, width, callback_fn) {
      var options = {
          caption: caption,
          height: height || 100,
          width: width || 100,
          fullscreen: false,
          show_loading: false,
          callback_fn: callback_fn
      }
      var win = new GB_Window(options);
      return win.show(url);
  }

function DisplayNote()
{
    if(document.getElementById('CachedView').checked==true)
    {
        document.getElementById('note').innerHTML = "Note: Cache Customer Data last updated at 2:00 AM Today.Please use this option when you feel slow performance of BRM in retrieving customer data ";
    }

    if(document.getElementById('CachedView').checked==false)
    {
        document.getElementById('note').innerHTML = '';
    }
}

function getcustomerInformation()
{
        var searchOption = document.getElementById('SearchOption').value;
        var SearchTxt = document.getElementById('SearchTxt').value;
        
        if(document.getElementById('CachedView').checked==true)
        {
             var CacheView = 'cacheView';
             document.getElementById('customer_information').innerHTML='';
        }
        
        if(SearchTxt=='')
         {
             alert('Please search term');
             exit();
         }

        if(searchOption=='CustomerID')
        {
            if(isNaN(SearchTxt))
             {
                 alert('Customer ID subject to be digits value');
                 exit();
             }
        }
        if(searchOption=='CustomerName')
        {
           if(!isNaN(SearchTxt))
             {
                 alert('Customer Name subject to be character value');
                 exit();
             }
        }
        if(searchOption=='CustomerCNIC')
        {
            if(isNaN(SearchTxt))
             {
                 alert('Customer CNIC subject to be digits value');
                 exit();
             }
        }
        if(searchOption=='CustomerNo')
        {
            if(isNaN(SearchTxt))
             {
                 alert('Customer Number subject to be digits value');
                 exit();
             }
        }
        if(searchOption=='CustomerEmail')
        {
            var reg = /^([A-Za-z0-9_\-\.])+\@([A-Za-z0-9_\-\.])+\.([A-Za-z]{2,4})$/;
            var address = SearchTxt;
            if(reg.test(address) == false)
            {
                alert('Email address format seems Invalid,please check!!!');
                exit();
            }
        }

	var post_func = function()
				{

					var effect = function(data){ $("#customer_information").html(data).show("normal"); };
					$.post("../FormProcessor/ajax_customer_payment.php",{searchOption:searchOption,SearchTxt:SearchTxt,CacheView:CacheView},effect);
				}
	$("#customer_information").html("<img src='../../images/loading.gif' /> Loading...");
	$("#customer_information").show("normal",post_func);

}
</script>
<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">
<table width="1000" align="center" cellpadding="0" cellspacing="0">
  <tr>
    <td align="right" valign="top" bgcolor="#f7f7f7"><? include_once("../header.php")?></td>
  </tr>
  <tr>
            <td colspan="2" align="center" style="font-size:16px"><strong>Customer Payment </strong></td>
    </tr>

  <tr>
    <td>
        <br>
      <table width="100%" border="0" align="center" cellpadding="0" cellspacing="0" bordercolor="#CCCCCC" bgcolor="#F4F4F4">
      <tr>
        <td>
        <!-- ================================= Start Content TD =============================== -->
                         <form name="customer_payment" id="customer_payment" action="" method="post"  >
                            <table width="60%" border="0" cellspacing="5" cellpadding="5" align="center"> 
                                <tr>
                                    <td>Search Term: </td>
                                    <td><input type="text" name="SearchTxt" id="SearchTxt" class="textboxBur" /></td>
                                    <td>Search Type </td>
                                    <td > <select name="SearchOption" id="SearchOption" class="textboxBur" >
                                            <option value="CustomerNo" selected="selected">TELE/MOBILE NUMBER</option>
                                            <option value="MAC">MAC ADDRESS</option>
                                            <option value="SerialID">CPE SERIEAL ID</option>
                                            <option value="Username">CUSTOMER USERNAME</option>
                                            <option value="CustomerCNIC">CUSTOMER CNIC</option>
                                            <option value="CustomerEmail">CUSTOMER EMAIL</option>
                                            <option value="CustomerName">CUSTOMER NAME</option>
                                            <option value="CustomerID">CUSTOMER ID</option>
                                        </select>    </td>
                                    <td>&nbsp;</td>
                                    <td><input type="button" name="SearchCustomer" value="Search" class="button" onclick="javascript:getcustomerInformation();"/></td>
                                    <td>Use Customer Cache Data</td>
                                    <td>
                                        <input type="Checkbox" id="CachedView" name="CachedView" value="" onchange="DisplayNote();"></input>
                                    </td>
                        </tr>
                                <tr>
                                    <td colspan="7">
                                        <font color="red"><strong><div id="note"></div></strong></font>
                                    </td>
                                </tr>

                        </table>
                        </form>

        <!-- =============================== End Content TD ================================== -->
       </td>


      </tr>
          <tr>
              <td>
                  <table width="60%" border="0" cellspacing="5" cellpadding="5" align="center">
              <tr>
                  <td align="left">
                      <div id="customer_information"></div>
                  </td>
              </tr>
              <tr>
                  <div id="customer_details"></div>
              </tr>
          </table>
              </td>
          </tr>


    </table>
    </br></td>
  </tr>
  	<tr>
		<td><? include_once("../footer.php")?>
		</td>
	</tr>
</table>
<!-- End ImageReady Slices -->
</body>
</html>