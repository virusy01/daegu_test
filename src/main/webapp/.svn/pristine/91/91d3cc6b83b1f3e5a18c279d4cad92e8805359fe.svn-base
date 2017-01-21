<?php
/**
 * Created by IntelliJ IDEA.
 * User: jwm_000
 * Date: 2015-10-09
 * Time: 오후 7:41
 */
require( dirname(__FILE__)."/db_connect.php");

$pCorpSeq = is_null($_REQUEST["corpSeq"])? "1" : $_REQUEST["corpSeq"];

$queryCurrYear = <<<EOD
select max(EVAL_YEAR) as EVAL_YEAR from EVAL
EOD;

$currYearResult = mysqli_query($connection, $queryCurrYear);
$currYear = mysqli_fetch_array($currYearResult, MYSQLI_NUM);

$pEvalYear = $_REQUEST["evalYear"] ? $_REQUEST["evalYear"] : $currYear[0];


$queryCorpDepth1 = <<<EOD
select D1.CORP_SEQ
, D1.DEPTH_1
, D1.DEPTH_1_NM
, D1.MAX_SCORE
, EV.EVAL_DATE
, ifnull(EV.SCORE, '') as SCORE
from
(
	select $pCorpSeq as CORP_SEQ
	, SCO.DEPTH_1
   , CODE.CD_NM as DEPTH_1_NM
	, sum(MAX_SCORE) as MAX_SCORE
	from SCORING as SCO
    inner join CODE on CODE.GRP_CD = 'KPI_DEPTH_1' and SCO.DEPTH_1 = CODE.CD
	group by DEPTH_1
) as D1
left join
(
	select CORP_SEQ
	, EVAL_DATE
	, DEPTH_1
	, sum(SCORE) as SCORE
	from EVAL
	where CORP_SEQ = $pCorpSeq
	and EVAL_YEAR = '$pEvalYear'
	group by CORP_SEQ, EVAL_DATE, DEPTH_1
) as EV on D1.CORP_SEQ = EV.CORP_SEQ and D1.DEPTH_1 = EV.DEPTH_1
EOD;

$resultCorpDepth1 = mysqli_query($connection, $queryCorpDepth1);
$rowsCorpDepth1 = toArray($resultCorpDepth1);

// 잔체기업의 depth1 result에서 해당 기업의 depth1 result 의 순위를 구한다.
foreach ($rowsCorpDepth1 as & $rowCorpDepth1){

  $depth1 = $rowCorpDepth1["DEPTH_1"];
  $myScore = $rowCorpDepth1["SCORE"];

  $queryAllCorpDepth1 = <<<EOD
  select CORP.CORP_SEQ
  , D1.EVAL_DATE
  , D1.DEPTH_1
  , ifnull(D1.SCORE, '') as SCORE
  from CORP_INFO as CORP
  left join
  (
      select CORP_SEQ
      , EVAL_DATE
      , DEPTH_1
      , sum(SCORE) as SCORE
      from EVAL
      where DEPTH_1 = '$depth1'
      and EVAL_YEAR = '$pEvalYear'
      group by CORP_SEQ, EVAL_DATE, DEPTH_1
  ) as D1 on CORP.CORP_SEQ = D1.CORP_SEQ
EOD;

  $depth1Result = mysqli_query($connection, $queryAllCorpDepth1);
  $depth1Rows = toArray($depth1Result);

  $ranking = 1;

  foreach($depth1Rows as $depth1Row){
    $depth1Score = $depth1Row["SCORE"];

    if ($myScore < $depth1Score ){
      $ranking += 1;
    }
  }

  $queryDepth2 = <<<EOD
    select D2.CORP_SEQ
    , D2.DEPTH_2
    , D2.DEPTH_2_NM
    , D2.MAX_SCORE
    , EV.EVAL_DATE
    , ifnull(EV.SCORE, '') as SCORE
    from
    (
        select $pCorpSeq as CORP_SEQ
        , SCO.DEPTH_2
        , CODE.CD_NM as DEPTH_2_NM
        , sum(MAX_SCORE) as MAX_SCORE
        from SCORING as SCO
        inner join CODE on CODE.GRP_CD = 'KPI_DEPTH_2' and SCO.DEPTH_2 = CODE.CD
        and DEPTH_1 = '$depth1'
        group by DEPTH_2
    ) as D2
    left join
    (
        select CORP_SEQ
        , EVAL_DATE
        , DEPTH_1
        , DEPTH_2
        , SCORE
        from EVAL
        where CORP_SEQ = $pCorpSeq
        and DEPTH_1 = '$depth1'
        and EVAL_YEAR = '$pEvalYear'
    ) as EV on D2.CORP_SEQ = EV.CORP_SEQ and D2.DEPTH_2 = EV.DEPTH_2
EOD;

  $depth2Result = mysqli_query($connection, $queryDepth2);
  $depth2Rows = toArray($depth2Result);

  $rowCorpDepth1["RANKING"] = $ranking;
  $rowCorpDepth1["CORP_COUNT"] = count($depth1Rows);
  $rowCorpDepth1["DEPTH_2"] = $depth2Rows;
}

$queryAvg = <<<EOD
select
EVAL_DATE
, DEPTH_1
, avg(SUM_1) as AVG
from
(
  select CORP_SEQ
  , EVAL_DATE
  , DEPTH_1
  , sum(SCORE) as SUM_1
  from EVAL
  where EVAL_YEAR = '$pEvalYear'
  group by CORP_SEQ, EVAL_DATE, DEPTH_1
) S
group by EVAL_DATE, DEPTH_1
EOD;

$resultAvg = mysqli_query($connection, $queryAvg);
$avgs = toArray($resultAvg);

//print json_encode($avgs, JSON_UNESCAPED_UNICODE);

foreach($rowsCorpDepth1 as & $rowCorpDepth1){
  $rowCorpDepth1["AVG"] = null;
  foreach($avgs as & $avg){
    if ($rowCorpDepth1["DEPTH_1"] == $avg["DEPTH_1"]){
        $rowCorpDepth1["AVG"] = $avg["AVG"];
    }
  }
}


print json_encode($rowsCorpDepth1, JSON_UNESCAPED_UNICODE | JSON_PRETTY_PRINT);

require( dirname(__FILE__)."/db_close.php");