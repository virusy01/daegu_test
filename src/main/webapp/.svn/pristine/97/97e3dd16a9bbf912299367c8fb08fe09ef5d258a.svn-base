<?php
/**
 * Created by IntelliJ IDEA.
 * User: jwm_000
 * Date: 2015-10-09
 * Time: 오후 7:41
 */
require( dirname(__FILE__)."/db_connect.php");

$pCorpSeq = $_REQUEST["corpSeq"];
$pEmpSeqs = $_REQUEST["empSeq"];
$pClsDts = $_REQUEST["clsDt"];
$pEmpNums = $_REQUEST["empNum"];
$pWeakNums = $_REQUEST["weakNum"];
$pFullNums = $_REQUEST["fullNum"];
$pPartNums = $_REQUEST["partNum"];
$pCxxSals = $_REQUEST["cxxSal"];
$pNormSals = $_REQUEST["normSal"];

$query = "";

$saveResults = array();

for($i=0; $i<count($pEmpSeqs); $i++) {

  $empSeq = $pEmpSeqs[$i];

  if ($empSeq < 0) {
    $query = <<<EOD
    insert into CORP_EMP
    (
      CORP_SEQ
      , CLS_DT
      , EMP_NUM
      , WEAK_NUM
      , FULL_NUM
      , PART_NUM
      , CXX_SAL
      , NORM_SAL
      , CRE_DT
    )
    values
    (
      $pCorpSeq
      , '$pClsDts[$i]'
      , '$pEmpNums[$i]'
      , '$pWeakNums[$i]'
      , '$pFullNums[$i]'
      , '$pPartNums[$i]'
      , replace('$pCxxSals[$i]', ',', '')
      , replace('$pNormSals[$i]', ',', '')
      , now()
    )
EOD;

  } else {
    $query = <<<EOD
    update CORP_EMP SET
      CLS_DT         = '$pClsDts[$i]'
      , EMP_NUM      = '$pEmpNums[$i]'
      , WEAK_NUM     = '$pWeakNums[$i]'
      , FULL_NUM     = '$pFullNums[$i]'
      , PART_NUM     = '$pPartNums[$i]'
      , CXX_SAL      = replace('$pCxxSals[$i]', ',', '')
      , NORM_SAL     = replace('$pNormSals[$i]', ',', '')
      , MOD_DT       = now()
    where EMP_SEQ = $empSeq
EOD;
  }

  $saveResult = array();
  $result = mysqli_query($connection, $query);
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