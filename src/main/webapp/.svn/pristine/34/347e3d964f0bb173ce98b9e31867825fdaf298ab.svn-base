<?php
/**
 * Created by IntelliJ IDEA.
 * User: jwm_000
 * Date: 2015-10-09
 * Time: 오후 7:41
 */
require( dirname(__FILE__)."/db_connect.php");

$pUserSeq = $_REQUEST["userSeq"];
$pUserId = $_REQUEST["userId"];
$pUserNm = $_REQUEST["userNm"];
$pEmail = $_REQUEST["email"];
$pPassword = $_REQUEST["password"];
$pAdminYn = $_REQUEST["adminYn"];

$query = "";

if ($pUserSeq < 1)
{
  $query = <<<EOD
  insert into USERS(
  USER_ID
  , USER_NM
  , EMAIL
  , PASSWORD
  , ADMIN_YN
  , CRE_DT
  )
  values(
  '$pUserId'
  , '$pUserNm'
  , '$pEmail'
  , password('$pPassword')
  , '$pAdminYn'
  , now()
  )
EOD;

}else{
  $query = <<<EOD
  update USERS SET
  USER_ID = '$pUserId'
  , USER_NM = '$pUserNm'
  , EMAIL = '$pEmail'
  , PASSWORD = password('$pPassword')
  , ADMIN_YN = '$pAdminYn'
  , MOD_DT = now()
  where USER_SEQ = $pUserSeq
EOD;
}

$result = mysqli_query($connection, $query);
$output = array();

if ($result){
  $output["RESULT"] = "SUCESS";
} else {
  $output["RESULT"] = "FAIL";
  $output["MESSAGE"] = mysqli_error();
  $output["QUERY"] = $query;
}


print json_encode($output, JSON_UNESCAPED_UNICODE | JSON_PRETTY_PRINT);

require( dirname(__FILE__)."/db_close.php");