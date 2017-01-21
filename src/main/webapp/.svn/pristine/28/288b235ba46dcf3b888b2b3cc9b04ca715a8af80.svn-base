<?php
/**
 * Created by IntelliJ IDEA.
 * User: jwm_000
 * Date: 2015-10-09
 * Time: 오후 11:44
 */
require( dirname(__FILE__)."/db_connect.php");
$grpCd = $_REQUEST["grpCd"];
$query = <<<EOD
select GRP_CD
, CD
, CD_NM
from CODE
where GRP_CD = '$grpCd'
EOD;

$result = mysqli_query($connection, $query);
$rows = toArray($result);
print json_encode($rows, JSON_UNESCAPED_UNICODE);
require( dirname(__FILE__)."/db_close.php");