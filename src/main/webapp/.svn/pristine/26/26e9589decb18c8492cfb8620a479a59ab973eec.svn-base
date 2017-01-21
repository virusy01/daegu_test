<?php
/**
 * Created by IntelliJ IDEA.
 * User: jwm_000
 * Date: 2015-10-09
 * Time: 오후 7:41
 */
require( dirname(__FILE__)."/db_connect.php");


$queryCurrYear = <<<EOD
select max(EVAL_YEAR) as EVAL_YEAR from EVAL
EOD;

$currYearResult = mysqli_query($connection, $queryCurrYear);
$currYear = mysqli_fetch_array($currYearResult, MYSQLI_NUM);

$pEvalYear = $_REQUEST["evalYear"] ? $_REQUEST["evalYear"] : $currYear[0];

$pDepth1 = $_REQUEST["depth1"]? $_REQUEST["depth1"]: '01';

$query = <<<EOD
select CORP.CORP_SEQ
, CORP.CORP_NM
, EVAL.EVAL_YEAR
, EVAL.DEPTH_1
, EVAL.SCORE
from CORP_INFO as CORP
left join
(
	select CORP_SEQ
    , EVAL_YEAR
    , DEPTH_1
    , sum(SCORE) as SCORE
    from EVAL
    where  EVAL_YEAR = '$pEvalYear'and DEPTH_1 = '$pDepth1'
    group by CORP_SEQ, EVAL_YEAR, DEPTH_1
) as EVAL on CORP.CORP_SEQ = EVAL.CORP_SEQ
where CORP.DEL_YN = 'N'
order by SCORE desc
EOD;

$result = mysqli_query($connection, $query);
$rows = toArray($result);

print json_encode($rows, JSON_UNESCAPED_UNICODE | JSON_PRETTY_PRINT);

require( dirname(__FILE__)."/db_close.php");