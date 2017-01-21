<?php
/**
 * Created by IntelliJ IDEA.
 * User: jwm_000
 * Date: 2015-10-09
 * Time: 오후 7:41
 */
require( dirname(__FILE__)."/db_connect.php");

$pUserSeq = is_null($_REQUEST["userSeq"])? "1" : $_REQUEST["userSeq"];

$query = <<<EOD
   select
  U.USER_SEQ
  , U.USER_ID
  , U.USER_NM
  , U.EMAIL
  , U.ADMIN_YN
  , U.MOD_DT
  , date(U.CRE_DT) as CRE_DT
  from USERS U
  where U.USER_SEQ = $pUserSeq
EOD;

$result = mysqli_query($connection, $query);
$row = toObject($result);

print json_encode($row, JSON_UNESCAPED_UNICODE | JSON_PRETTY_PRINT);

require( dirname(__FILE__)."/db_close.php");