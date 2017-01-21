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

$pCorpNm = is_null($_REQUEST["corpNm"])? '' : $_REQUEST["corpNm"];
$pCorpType = is_null($_REQUEST["corpType"])? '' : $_REQUEST["corpType"];
$pCompKind = is_null($_REQUEST["compKind"])? '' : $_REQUEST["compKind"];
$pBizSect = is_null($_REQUEST["bizSect"])? '' : $_REQUEST["bizSect"];
$pPageNo = is_null($_REQUEST["pageNo"])? "1" : $_REQUEST["pageNo"];
$pPageRowCount = is_null($_REQUEST["pageRowCount"])? "10" : $_REQUEST["pageRowCount"];

$limitBegin = ($pPageNo-1) * $pPageRowCount;
$limitEnd = $pPageRowCount;

$query = <<<EOD
  select
  INFO.CORP_SEQ
  , INFO.CORP_NM
  , ifnull(INFO.CEO_NM, '') as CEO_NM
  , ifnull(INFO.BIZ_NO, '') as BIZ_NO
  , INFO.BIZ_DT
  , INFO.CORP_TYPE
  , ifnull(case when INFO.CORP_TYPE = '06' then INFO.CORP_TYPE2 else CORP_TYPE.CD_NM end, '') as CORP_TYPE_NM
  , INFO.COMP_KIND
  , ifnull(COMP_KIND.CD_NM, '') as COMP_KIND_NM
  , INFO.BIZ_SECT
  , ifnull(case when INFO.BIZ_SECT = '16' then INFO.BIZ_SECT2 else BIZ_SECT.CD_NM end, '') as BIZ_SECT_NM
  , INFO.SOC_YN
  , INFO.SOC_TYPE
  , SOC_TYPE.CD_NM as SOC_TYPE_NM
  , ifnull(EVAL.SCORE, '') as SCORE
  from CORP_INFO as INFO
  inner join CODE as CORP_TYPE on INFO.CORP_TYPE = CORP_TYPE.CD and CORP_TYPE.GRP_CD = 'CORP_TYPE' and ('$pCorpType' = '' OR CORP_TYPE.CD = '$pCorpType')
  inner join CODE as COMP_KIND on INFO.COMP_KIND = COMP_KIND.CD and COMP_KIND.GRP_CD = 'COMP_KIND' and ('$pCompKind' = '' OR COMP_KIND.CD = '$pCompKind')
  inner join CODE as BIZ_SECT on INFO.BIZ_SECT = BIZ_SECT.CD and BIZ_SECT.GRP_CD = 'BIZ_SECT' and ('$pBizSect' = '' OR BIZ_SECT.CD = '$pBizSect')
  inner join CODE as SOC_TYPE on INFO.SOC_TYPE = SOC_TYPE.CD and SOC_TYPE.GRP_CD = 'SOC_TYPE'
  left join
  (
    select CORP_SEQ
    , sum(SCORE) as SCORE
    from EVAL
    where EVAL_YEAR = '$pEvalYear'
    group by CORP_SEQ
  ) as EVAL on INFO.CORP_SEQ = EVAL.CORP_SEQ
  where (INFO.CORP_NM LIKE '%$pCorpNm%' or INFO.CEO_NM LIKE '%$pCorpNm%' or INFO.BIZ_NO LIKE '%$pCorpNm%')
  and INFO.DEL_YN = 'N'
  order by SCORE
  limit $limitBegin, $limitEnd
EOD;

//syslog(LOG_DEBUG, $query);

$result = mysqli_query($connection, $query);

if (!$result){
    print mysqli_error($connection);
}
$rows = toArray($result);
print json_encode($rows, JSON_UNESCAPED_UNICODE | JSON_PRETTY_PRINT);

require( dirname(__FILE__)."/db_close.php");