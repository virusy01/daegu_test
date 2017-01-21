<?php
/**
 * Created by IntelliJ IDEA.
 * User: jwm_000
 * Date: 2015-10-09
 * Time: 오후 7:41
 */
require( dirname(__FILE__)."/db_connect.php");

$pCorpSeq = $_REQUEST["corpSeq"];
$pHelpSeqs = $_REQUEST["helpSeq"];
$pHelpers = $_REQUEST["helper"];
$pPrjNms = $_REQUEST["prjNm"];
$pHelpStDts = $_REQUEST["helpStDt"];
$pHelpEndDts = $_REQUEST["helpEndDt"];
$pHelpAmts = $_REQUEST["helpAmt"];

$query = "";

$saveResults = array();

for($i=0; $i<count($pHelpSeqs); $i++) {

  $helpSeq = $pHelpSeqs[$i];

  if ($helpSeq < 0) {
    $query = <<<EOD
    insert into CORP_HELP
    (
      CORP_SEQ
      , HELPER
      , PRJ_NM
      , HELP_ST_DT
      , HELP_END_DT
      , HELP_AMT
      , CRE_DT
    )
    values
    (
      $pCorpSeq
      , '$pHelpers[$i]'
      , '$pPrjNms[$i]'
      , case when '$pHelpStDts[$i]' = '' then null else '$pHelpStDts[$i]' end
      , case when '$pHelpEndDts[$i]' = '' then null else '$pHelpEndDts[$i]' end
      , replace('$pHelpAmts[$i]', ',', '')
      , now()
    )
EOD;

  } else {
    $query = <<<EOD
    update CORP_HELP SET
      HELPER         = '$pHelpers[$i]'
      , PRJ_NM       = '$pPrjNms[$i]'
      , HELP_ST_DT   = case when '$pHelpStDts[$i]' = '' then null else '$pHelpStDts[$i]' end
      , HELP_END_DT  = case when '$pHelpEndDts[$i]' = '' then null else '$pHelpEndDts[$i]' end
      , HELP_AMT     = replace('$pHelpAmts[$i]', ',', '')
      , MOD_DT       = now()
    where HELP_SEQ = $helpSeq
EOD;
  }

  $saveResult = array();
  $result = mysqli_query($connection, $query);
  if ($result){
    $saveResult["RESULT"] = "SUCCESS";
  }
  else {
    $saveResult["RESULT"] = "FAIL";
    $saveResult["MESSAGE"] = mysqli_error();
    $saveResult["QUERY"] = $query;
  }
  $saveResults[] = $saveResult;
}

print json_encode($saveResults, JSON_UNESCAPED_UNICODE | JSON_PRETTY_PRINT);

require( dirname(__FILE__)."/db_close.php");