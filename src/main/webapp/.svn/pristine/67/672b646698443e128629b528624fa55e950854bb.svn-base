<?php
/**
 * Created by IntelliJ IDEA.
 * User: jwm_000
 * Date: 2015-10-09
 * Time: 오후 7:41
 */
require(dirname(__FILE__) . "/db_connect.php");

$pCorpSeq = $_REQUEST["corpSeq"] ?: $_REQUEST["corpSeq"] ?: "1";

$queryCurrYear = <<<EOD
select EVAL_YEAR
from EVAL
where CORP_SEQ = $pCorpSeq
and EVAL_YEAR = YEAR(now())
EOD;

$currYearResult = mysqli_query($connection, $queryCurrYear);
$currYear = mysqli_fetch_array($currYearResult, MYSQLI_NUM);

$pEvalYear = $_REQUEST["evalYear"] ? $_REQUEST["evalYear"] : $currYear[0];

$pDepth1s = $_REQUEST["depth1"]? $_REQUEST["depth1"] : array("01", "01");
$pDepth2s = $_REQUEST["depth2"]? $_REQUEST["depth2"] : array("0101", "0102");
$pScores = $_REQUEST["score"]? $_REQUEST["score"]: array("2", "2");

$query = null;
$result = null;
$saveResults = array();

if (!$currYear) {
    for ($i = 0; $i < count($pDepth1s); $i++) {
        $depth1 = $pDepth1s[$i];
        $depth2 = $pDepth2s[$i];
        $score = $pScores[$i];
        $query = <<<EOD

    insert into EVAL
    (
      CORP_SEQ
      , EVAL_YEAR
      , EVAL_DATE
      , DEPTH_1
      , DEPTH_2
      , SCORE
      , CRE_DT
    )
    values
    (
      '$pCorpSeq'
      , year(now())
      , now()
      , '$depth1'
      , '$depth2'
      , '$score'
      , now()
    )
EOD;

        $result = mysqli_query($connection, $query);
        $saveResult = array();

        if ($result) {
            $saveResult["RESULT"] = "SUCESS";
        } else {
            $saveResult["RESULT"] = "FAIL";
            $saveResult["MESSAGE"] = mysqli_error();
            $saveResult["QUERY"] = $query;
        }
        $saveResults[] = $saveResult;
    }
} else {
    for ($i = 0; $i < count($pDepth1s); $i++) {
        $depth1 = $pDepth1s[$i];
        $depth2 = $pDepth2s[$i];
        $score = $pScores[$i];

        $query = <<<EOD
    update EVAL set
    SCORE = '$score'
    , MOD_DT = now()
    where CORP_SEQ = $pCorpSeq
    and EVAL_YEAR = year(now())
    and DEPTH_1 = '$depth1'
    and DEPTH_2 = '$depth2'
EOD;

        $result = mysqli_query($connection, $query);
        $saveResult = array();

        if ($result) {
            $saveResult["RESULT"] = "SUCESS";
        } else {
            $saveResult["RESULT"] = "FAIL";
            $saveResult["MESSAGE"] = mysqli_error();
            $saveResult["QUERY"] = $query;
        }
        $saveResults[] = $saveResult;
    }
}

print json_encode($saveResults, JSON_UNESCAPED_UNICODE | JSON_PRETTY_PRINT);

require(dirname(__FILE__) . "/db_close.php");