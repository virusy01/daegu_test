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

$pCorpType = is_null($_REQUEST["corpType"])? '' : $_REQUEST["corpType"];
$pCompKind = is_null($_REQUEST["compKind"])? '' : $_REQUEST["compKind"];
$pBizSect = is_null($_REQUEST["bizSect"])? '' : $_REQUEST["bizSect"];

$query = <<<EOD
select CORP.CORP_SEQ
, CORP.CORP_NM
, SUM(DEPTH_1_01) as DEPTH_1_01
, SUM(DEPTH_1_02) as DEPTH_1_02
, SUM(DEPTH_1_03) as DEPTH_1_03
, SUM(DEPTH_1_04) as DEPTH_1_04
, SUM(DEPTH_1_05) as DEPTH_1_05
, SUM(DEPTH_1_06) as DEPTH_1_06
, SUM(DEPTH_1_07) as DEPTH_1_07
, SUM(DEPTH_1_08) as DEPTH_1_08
, SUM(DEPTH_1_09) as DEPTH_1_09
, SUM(DEPTH_1_10) as DEPTH_1_10
, SUM(DEPTH_1_01) + SUM(DEPTH_1_02) + SUM(DEPTH_1_03) + SUM(DEPTH_1_04) + SUM(DEPTH_1_05) + SUM(DEPTH_1_06) + SUM(DEPTH_1_07) + SUM(DEPTH_1_08) + SUM(DEPTH_1_09) + SUM(DEPTH_1_10) as DEPTH_1_SUM
from CORP_INFO as CORP
left join
(
	select CORP_SEQ
	, EVAL_DATE
	, DEPTH_1
	, case when DEPTH_1 = '01' then sum(SCORE) else 0 end as DEPTH_1_01
	, case when DEPTH_1 = '02' then sum(SCORE) else 0 end as DEPTH_1_02
	, case when DEPTH_1 = '03' then sum(SCORE) else 0 end as DEPTH_1_03
	, case when DEPTH_1 = '04' then sum(SCORE) else 0 end as DEPTH_1_04
	, case when DEPTH_1 = '05' then sum(SCORE) else 0 end as DEPTH_1_05
	, case when DEPTH_1 = '06' then sum(SCORE) else 0 end as DEPTH_1_06
	, case when DEPTH_1 = '07' then sum(SCORE) else 0 end as DEPTH_1_07
	, case when DEPTH_1 = '08' then sum(SCORE) else 0 end as DEPTH_1_08
	, case when DEPTH_1 = '09' then sum(SCORE) else 0 end as DEPTH_1_09
	, case when DEPTH_1 = '10' then sum(SCORE) else 0 end as DEPTH_1_10
	from EVAL
	inner join CODE on CODE.GRP_CD = 'KPI_DEPTH_1' and EVAL.DEPTH_1 = CODE.CD
	where '1' = '1'
	and EVAL_YEAR = '$pEvalYear'
	group by CORP_SEQ, EVAL_DATE, DEPTH_1, CODE.CD_NM
	order by CORP_SEQ, DEPTH_1
) EVAL
on EVAL.CORP_SEQ = CORP.CORP_SEQ
where 1 = 1
and ('$pCorpType' = '' OR CORP.CORP_TYPE = '$pCorpType') and ('$pCompKind' = '' OR CORP.COMP_KIND = '$pCompKind') and ('$pBizSect' = '' OR CORP.BIZ_SECT = '$pBizSect')
and CORP.DEL_YN = 'N'
group by CORP_SEQ
order by DEPTH_1_SUM desc
EOD;

$depth1Result = mysqli_query($connection, $query);
$depth1Rows = toArray($depth1Result);

foreach($depth1Rows as & $depth1Row){
	$corpSeq = $depth1Row["CORP_SEQ"];
	$depth1 = $depth1Row["DEPTH_1"];

	$queryDepth2 = <<<EOD
    select D2.CORP_SEQ
    , D2.DEPTH_1
    , D2.DEPTH_1_NM
    , D2.DEPTH_2
    , D2.DEPTH_2_NM
    , D2.MAX_SCORE
    , EV.EVAL_DATE
    , ifnull(EV.SCORE, '') as SCORE
    from
    (
        select $corpSeq as CORP_SEQ
        , SCO.DEPTH_1
        , SCO.DEPTH_2
        , D1.CD_NM as DEPTH_1_NM
        , D2.CD_NM as DEPTH_2_NM
        , sum(MAX_SCORE) as MAX_SCORE
        from SCORING as SCO
        inner join CODE as D1 on D1.GRP_CD = 'KPI_DEPTH_1' and SCO.DEPTH_1 = D1.CD
        inner join CODE as D2 on D2.GRP_CD = 'KPI_DEPTH_2' and SCO.DEPTH_2 = D2.CD
        group by DEPTH_1, DEPTH_2
    ) as D2
    left join
    (
        select CORP_SEQ
        , EVAL_DATE
        , DEPTH_1
        , DEPTH_2
        , SCORE
        from EVAL
        where CORP_SEQ = $corpSeq
        and EVAL_YEAR = '$pEvalYear'
    ) as EV on D2.CORP_SEQ = EV.CORP_SEQ and D2.DEPTH_2 = EV.DEPTH_2
EOD;

	$depth2Result = mysqli_query($connection, $queryDepth2);
	$depth2Rows = toArray($depth2Result);

	$depth1Row["DEPTH_2"] = $depth2Rows;
}

print json_encode($depth1Rows, JSON_UNESCAPED_UNICODE | JSON_PRETTY_PRINT);

require( dirname(__FILE__)."/db_close.php");