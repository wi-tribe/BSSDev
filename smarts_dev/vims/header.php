<?
	session_start("VIMS");
	ob_start();
	include_once("vims_config.php");
?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>wi-tribe VIMS</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<?=$_HTML_LIBS?>

</head>
<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">
<table width="1200" align="center" cellpadding="0" cellspacing="0">
  <tr>
    <td align="right" valign="top" bgcolor="#f7f7f7">
		<table width="100%" cellpadding="0" cellspacing="0" bgcolor="#F7F7F7">
			<tr>
				<td width="468" height="150"><img src="images/smarts_logo.jpg" alt="" hspace="45" vspace="8"></td>
				<td width="529" align="right"><img src="images/map.gif" width="529" height="150"></td>
			</tr>
			<tr>
				<td height="50" colspan="2" background="images/nav-rep.png" valign="middle" style=" background-repeat:repeat-x; height:50px; vertical-align:middle; text-align:center;">
					<a href="#" onclick="javascript:animatedAjaxCall( 'nav.php','LeftNavigationDiv' );" ><img src="images/main/home.gif" border="0"/></a> &nbsp;&nbsp;|&nbsp; &nbsp; 
					<a href="logout.php" border="0"/><img src="images/main/logout.gif" border="0"/></a> &nbsp;&nbsp;|&nbsp; &nbsp; 
				</td>
			</tr>
		</table>
	</td>
  </tr>
  <tr>
    <td><br>
    <table width="100%" border="0" align="center" cellpadding="0" cellspacing="0" bordercolor="#CCCCCC" bgcolor="#f7f7f7" class="td">
	<tr>
    	<td>&nbsp;</td>
    </tr>
    <tr>
        <td><p>&nbsp;</p>

