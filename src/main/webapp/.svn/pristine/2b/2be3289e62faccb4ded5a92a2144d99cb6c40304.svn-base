<?php
/**
 * Created by IntelliJ IDEA.
 * User: jwm_000
 * Date: 2015-10-09
 * Time: 오후 7:41
 */
require( dirname(__FILE__)."/db_connect.php");

$pUserNm = is_null($_REQUEST["userNm"])? '' : $_REQUEST["userNm"];
$pPageNo = is_null($_REQUEST["pageNo"])? "1" : $_REQUEST["pageNo"];
$pPageRowCount = is_null($_REQUEST["pageRowCount"])? "10" : $_REQUEST["pageRowCount"];

$limitBegin = ($pPageNo-1) * $pPageRowCount;
$limitEnd = $pPageRowCount;

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
  where (U.USER_ID LIKE '%$pUserNm%' or U.USER_NM LIKE '%$pUserNm%' or EMAIL LIKE '%$pUserNm%')
  order by U.USER_NM
  limit $limitBegin, $limitEnd
EOD;

//syslog(LOG_DEBUG, $query);

$result = mysqli_query($connection, $query);
$rows = toArray($result);
print json_encode($rows, JSON_UNESCAPED_UNICODE | JSON_PRETTY_PRINT);

require( dirname(__FILE__)."/db_close.php");