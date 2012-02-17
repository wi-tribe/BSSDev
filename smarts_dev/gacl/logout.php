<?php
include_once('permission.php');
$permission = new permissions();
session_start();
unset($_SESSION);
$permission->CloseConnection();
session_destroy();
header('Location: login.php');
?>
