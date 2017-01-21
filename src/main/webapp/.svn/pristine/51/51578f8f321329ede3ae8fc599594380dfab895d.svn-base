<?php
/**
 * Created by IntelliJ IDEA.
 * User: jwm_000
 * Date: 2015-10-09
 * Time: 오후 7:41
 */
require( dirname(__FILE__)."/db_connect.php");

$pCorpSeq = $_REQUEST["corpSeq"]? $_REQUEST["corpSeq"] : "1";

$query = <<<EOD
select EMP_SEQ
, CORP_SEQ
, CLS_DT
, EMP_NUM
, WEAK_NUM
, FULL_NUM
, PART_NUM
, CXX_SAL
, NORM_SAL
, MOD_DT
, CRE_DT
from CORP_EMP
where CORP_SEQ = '$pCorpSeq'
and CLS_DT >= (select date_format(date_sub(now(), interval 5 year), '%Y-01-01'))
order by CLS_DT desc
EOD;
$result = mysqli_query($connection, $query);
$corpHelp = toArray($result);

print json_encode($corpHelp, JSON_UNESCAPED_UNICODE | JSON_PRETTY_PRINT);

require( dirname(__FILE__)."/db_close.php");