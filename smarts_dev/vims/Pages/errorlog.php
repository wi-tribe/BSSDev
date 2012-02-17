<?php
header('Content-Type: application/csv'); //Outputting the file as a csv file
header('Content-Disposition: attachment; filename=errorLog.csv'); //Defining the name of the file and suggesting the browser to offer a 'Save to disk ' option
header('Pragma: no-cache');
readfile('/tmp/errorLog.csv');
?>
