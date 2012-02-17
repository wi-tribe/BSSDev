<?
if($_SESSION['username']=='admin')
{
	header('Location: admin/acl_admin.php');
}
else
{
	header('Location: login.php');
}
?>