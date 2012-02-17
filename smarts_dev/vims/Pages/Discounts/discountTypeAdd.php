<?
session_start("VIMS");
set_time_limit(700);

include('../../vims_config.php');
include_once(CLASSDIR."/Channel.php");

//check permission
if(!$GLOBALS['_GACL']->checkPermission($_SESSION['username'],'discountadd'))
{
	echo "Permission denied";
	exit;
}
$channel_obj = new Channel();
$geotypes = $channel_obj->getAllChannelTypes();
$regions = $channel_obj->getRegions();

 
?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>wi-tribe Coverage Tool </title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />

<?=$_HTML_LIBS?>
</head>
        
<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">
    <table width="600" align="center" cellpadding="0" cellspacing="0">
  <tr>
    <td align="right" valign="top" bgcolor="#f7f7f7">

		<table width="600" cellpadding="0" cellspacing="0" bgcolor="#F7F7F7">
			<tr>
				<td height="30" colspan="2" background="../images/nav-rep.png" valign="middle" style=" background-repeat:repeat-x; height:50px; vertical-align:middle; text-align:center;">
				<span class="textboxBur"  align="center" style="font-size:16px" > <strong>Discount Type Addition</strong> </span>

				</td>
			</tr>
		</table>

    <table align="center" width="90%" border=0>
		<tr valign="top" class="textbox" >
			<td align="center">
              <!-----------------------------THIS PAGE------------------------------------->
                          <form name="discountTypeAdd" id="discountTypeAdd"  method="post" action="FormProcessor/Discounts/ajax_discountTypeAdd.php">
                            <table width="447" border="0" cellspacing=0" cellpadding="0">
                            <tr>
                                 <td class='orangeText' >Discount Type Name: </td>
				 <td ><input type='text' name='dis_type_name' id='dis_type_name' /></td>
                            </tr>
                             <tr >
                            <td class='orangeText' >Type Description</td>
                             <td><textarea name="dis_type_desc" id="dis_type_desc"></textarea></td>
                             <td ><input type='hidden' name='myaction' id='myaction' value="add_dis_type"/></td>
                            </tr>
                          </table>
                            <table width="80%" align="center" border="0" cellspacing="0" cellpadding="0">
				<tr>
				  <td align='right'>
                                  <input name="action" id="discount_type" type='submit' value='Add' /></td>
				</tr>
			  </table>
                        </form>
              </td>
		</tr>
	</table>
    </td>
</tr>
        <tr>
		<td>
		 <table width="600" cellpadding="0" cellspacing="0" bgcolor="#F7F7F7">
		  <tr>
			<td align="center" valign="middle" background="../images/nav-rep.png" style=" background-repeat:repeat-x; height:50px;"><a href="#">&copy; wi-tribe Pakistan Limited. All Rights Reserved &reg;</a></td>
		  </tr>
		  </table>
		</td>
	</tr>
</table>

</body>
</html>





