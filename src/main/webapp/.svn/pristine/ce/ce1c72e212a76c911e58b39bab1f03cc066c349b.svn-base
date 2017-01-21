<?php
/**
 * Created by IntelliJ IDEA.
 * User: jwm_000
 * Date: 2015-10-09
 * Time: 오후 7:41
 */
require( dirname(__FILE__)."/db_connect.php");

$pId = $_REQUEST["id"];
$pPassword = $_REQUEST["password"];

$query = <<<EOD
  select *
  from USERS
  where USER_ID = '$pId'
  and PASSWORD = PASSWORD('$pPassword')
EOD;

//syslog(LOG_DEBUG, $query);

$result = mysqli_query($connection, $query);
$rows = toArray($result);
if (count($rows) > 0){

  $_SESSION["USER_ID"] = $pId;
  $_SESSION["USER_NM"] = $rows[0]["USER_NM"];

  //var_dump($_SESSION);

  print ('{"result":"OK"}');
}else{
  print ('{"result":"FAIL"}');
}

require( dirname(__FILE__)."/db_close.php");