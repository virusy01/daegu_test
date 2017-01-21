<?php
/**
 * Created by IntelliJ IDEA.
 * User: jwm_000
 * Date: 2015-10-09
 * Time: 오후 7:41
 */
require( dirname(__FILE__)."/db_connect.php");

$pCorpSeq = $_REQUEST["corpSeq"];
$pFinnSeqs = $_REQUEST["finnSeq"];
$pClsDts = $_REQUEST["clsDt"];
$pAsstAmts = $_REQUEST["asstAmt"];
$pDeptAmts = $_REQUEST["deptAmt"];
$pCapitAmts = $_REQUEST["capitAmt"];
$pSoldPrivAmts = $_REQUEST["soldPrivAmt"];
$pSoldPublAmts = $_REQUEST["soldPublAmt"];
$pSoldOrgCosts = $_REQUEST["soldOrgCost"];
$pSoldIncoms = $_REQUEST["soldIncom"];
$pSalesAmts = $_REQUEST["salesAmt"];
$pSalesIncoms = $_REQUEST["salesIncom"];
$pExtProfs = $_REQUEST["extProf"];
$pNetIncoms = $_REQUEST["netIncom"];

$query = "";

$saveResults = array();

for($i=0; $i<count($pFinnSeqs); $i++) {

  $finnSeq = $pFinnSeqs[$i];

  if ($finnSeq < 0) {
    $query = <<<EOD
    insert into CORP_FINN
    (
      CORP_SEQ
      , CLS_DT
      , ASST_AMT
      , DEPT_AMT
      , CAPIT_AMT
      , SOLD_PRIV_AMT
      , SOLD_PUBL_AMT
      , SOLD_ORG_COST
      , SOLD_INCOM
      , SALES_AMT
      , SALES_INCOM
      , EXT_PROF
      , NET_INCOM
      , CRE_DT
    )
    values
    (
      ?
      , ?
      , replace(nullif(?, ''), ',', '')
      , replace(nullif(?, ''), ',', '')
      , replace(nullif(?, ''), ',', '')
      , replace(nullif(?, ''), ',', '')
      , replace(nullif(?, ''), ',', '')
      , replace(nullif(?, ''), ',', '')
      , replace(nullif(?, ''), ',', '')
      , replace(nullif(?, ''), ',', '')
      , replace(nullif(?, ''), ',', '')
      , replace(nullif(?, ''), ',', '')
      , replace(nullif(?, ''), ',', '')
      , now()
    )
EOD;
    $stmt = mysqli_prepare($connection, $query);
    mysqli_stmt_bind_param($stmt, 'dssssssssssss', $pCorpSeq, $pClsDts[$i], $pAsstAmts[$i], $pDeptAmts[$i], $pCapitAmts[$i], $pSoldPrivAmts[$i],
        $pSoldPublAmts[$i], $pSoldOrgCosts[$i], $pSoldIncoms[$i], $pSalesAmts[$i], $pSalesIncoms[$i], $pExtProfs[$i], $pNetIncoms[$i]);

  } else {
    $query = <<<EOD
    update CORP_FINN SET
      CLS_DT            = ?
      , ASST_AMT        = replace(nullif(?, ''), ',', '')
      , DEPT_AMT        = replace(nullif(?, ''), ',', '')
      , CAPIT_AMT       = replace(nullif(?, ''), ',', '')
      , SOLD_PRIV_AMT   = replace(nullif(?, ''), ',', '')
      , SOLD_PUBL_AMT   = replace(nullif(?, ''), ',', '')
      , SOLD_ORG_COST   = replace(nullif(?, ''), ',', '')
      , SOLD_INCOM      = replace(nullif(?, ''), ',', '')
      , SALES_AMT       = replace(nullif(?, ''), ',', '')
      , SALES_INCOM     = replace(nullif(?, ''), ',', '')
      , EXT_PROF        = replace(nullif(?, ''), ',', '')
      , NET_INCOM       = replace(nullif(?, ''), ',', '')
      , MOD_DT          = now()
    where FINN_SEQ = ?
EOD;
    $stmt = mysqli_prepare($connection, $query);
    mysqli_stmt_bind_param($stmt, "ssssssssssssd", $pClsDts[$i], $pAsstAmts[$i], $pDeptAmts[$i], $pCapitAmts[$i], $pSoldPrivAmts[$i],
        $pSoldPublAmts[$i], $pSoldOrgCosts[$i], $pSoldIncoms[$i], $pSalesAmts[$i], $pSalesIncoms[$i], $pExtProfs[$i], $pNetIncoms[$i], $finnSeq);
  }

  $saveResult = array();

  $result = mysqli_stmt_execute($stmt);

  if ($result){
    $saveResult["RESULT"] = "SUCCESS";
  }
  else {
    $saveResult["RESULT"] = "FAIL";
    $saveResult["MESSAGE"] = mysqli_error($connection);
    $saveResult["QUERY"] = $query;
  }
  $saveResults[] = $saveResult;
}

print json_encode($saveResults, JSON_UNESCAPED_UNICODE | JSON_PRETTY_PRINT);

require( dirname(__FILE__)."/db_close.php");