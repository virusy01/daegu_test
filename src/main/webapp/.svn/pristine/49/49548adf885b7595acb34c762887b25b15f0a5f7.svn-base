<?php
/**
 * Created by IntelliJ IDEA.
 * User: jwm_000
 * Date: 2015-10-09
 * Time: 오후 7:41
 */
require( dirname(__FILE__)."/db_connect.php");


$pCorpNm = is_null($_REQUEST["corpNm"])? '' : $_REQUEST["corpNm"];
$pCorpType = is_null($_REQUEST["corpType"])? '' : $_REQUEST["corpType"];
$pCompKind = is_null($_REQUEST["compKind"])? '' : $_REQUEST["compKind"];
$pBizSect = is_null($_REQUEST["bizSect"])? '' : $_REQUEST["bizSect"];

$query = <<<EOD
select COUNT(*) ROW_COUNT
from
(
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
) PAGING
EOD;

//syslog(LOG_DEBUG, $query);

$result = mysqli_query($connection, $query);
$row = mysqli_fetch_array($result, MYSQLI_ASSOC);
print json_encode($row, JSON_UNESCAPED_UNICODE | JSON_PRETTY_PRINT);

require( dirname(__FILE__)."/db_close.php");