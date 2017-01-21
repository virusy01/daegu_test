<?php
/**
 * Created by IntelliJ IDEA.
 * User: jwm_000
 * Date: 2015-10-09
 * Time: 오후 7:41
 */
require( dirname(__FILE__)."/db_connect.php");

$queryCurrYear = <<<EOD
select YEAR(now()) as EVAL_YEAR
EOD;

$currYearResult = mysqli_query($connection, $queryCurrYear);
$currYear = mysqli_fetch_array($currYearResult, MYSQLI_NUM);

$pEvalYear = $_REQUEST["evalYear"] ? $_REQUEST["evalYear"] : $currYear[0];


$query = <<<EOD
select EVAL.EVAL_YEAR
, EVAL.DEPTH_1
, CODE.CD_NM as DEPTH_1_NM
, sum(SCORE) as SCORE
from EVAL
inner join CODE on CODE.GRP_CD = 'KPI_DEPTH_1' and EVAL.DEPTH_1 = CODE.CD
inner join CORP_INFO as CORP on EVAL.CORP_SEQ = CORP.CORP_SEQ and CORP.DEL_YN = 'N'
where '1' = '1'
and EVAL_YEAR = '$pEvalYear'
group by EVAL_YEAR, DEPTH_1, DEPTH_1_NM
order by SCORE desc
EOD;

$result = mysqli_query($connection, $query);
$rows = toArray($result);

print json_encode($rows, JSON_UNESCAPED_UNICODE | JSON_PRETTY_PRINT);

require( dirname(__FILE__)."/db_close.php");