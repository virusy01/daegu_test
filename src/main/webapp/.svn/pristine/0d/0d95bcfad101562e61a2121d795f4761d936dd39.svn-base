<?php
/**
 * Created by IntelliJ IDEA.
 * User: jwm_000
 * Date: 2015-10-09
 * Time: 오후 7:41
 */
require( dirname(__FILE__)."/db_connect.php");

$pCorpSeq = $_REQUEST["corpSeq"];
$pCorpNm = $_REQUEST["corpNm"];
$pBizDt = $_REQUEST["bizDt"];
$pCeoNm = $_REQUEST["ceoNm"];
$pBizNo = $_REQUEST["bizNo"];
$pCorpType = $_REQUEST["corpType"];
$pCorpType2 = $_REQUEST["corpType2"];
$pCompKind = $_REQUEST["compKind"];
$pBizSect = $_REQUEST["bizSect"];
$pBizSect2 = $_REQUEST["bizSect2"];
$pSocType = $_REQUEST["socType"];
$pSocCertEndDt = $_REQUEST["socCertEndDt"];
$pEmpNum = $_REQUEST["empNum"];
$pEmpWeakNum = $_REQUEST["empWeakNum"];
$pEmpFullNum = $_REQUEST["empFullNum"];
$pEmpPartNum = $_REQUEST["empParkNum"];
$pEmpCxxSal = $_REQUEST["empCxxSal"];
$pEmpNormSal = $_REQUEST["empNormSal"];

$pSocYn = ($pCompKind == "01" || $pCompKind == "02")? "Y" : "N";

$query = "";

if ($pCorpSeq < 1)
{
  $query = <<<EOD
  insert into CORP_INFO
  (
    CORP_NM
    , CEO_NM
    , BIZ_NO
    , BIZ_DT
    , CORP_TYPE
    , CORP_TYPE2
    , COMP_KIND
    , BIZ_SECT
    , BIZ_SECT2
    , SOC_YN
    , SOC_TYPE
    , SOC_CERT_END_DT
    , CRE_DT
  )
  values
  (
    '$pCorpNm'
    , '$pCeoNm'
    , '$pBizNo'
    , '$pBizDt'
    , '$pCorpType'
    , '$pCorpType2'
    , '$pCompKind'
    , '$pBizSect'
    , '$pBizSect2'
    , '$pSocYn'
    , '$pSocType'
    , case when '$pSocCertEndDt' = '' then null else '$pSocCertEndDt' end
    , now()
  )
EOD;

}else{
  $query = <<<EOD
  update CORP_INFO SET
    CORP_NM           = '$pCorpNm'
    , CEO_NM          = '$pCeoNm'
    , BIZ_NO          = '$pBizNo'
    , BIZ_DT          = '$pBizDt'
    , CORP_TYPE       = '$pCorpType'
    , CORP_TYPE2      = '$pCorpType2'
    , COMP_KIND       = '$pCompKind'
    , BIZ_SECT        = '$pBizSect'
    , BIZ_SECT2       = '$pBizSect2'
    , SOC_YN          = '$pSocYn'
    , SOC_TYPE        = '$pSocType'
    , SOC_CERT_END_DT = case when '$pSocCertEndDt' = '' then null else '$pSocCertEndDt' end
    , MOD_DT          = now()
  where CORP_SEQ = $pCorpSeq
EOD;
}

$result = mysqli_query($connection, $query);
$output = array();

if ($result){
  $output["RESULT"] = "SUCCESS";
} else {
  $output["RESULT"] = "FAIL";
  $output["MESSAGE"] = mysqli_error($connection);
  $output["QUERY"] = $query;
}

print json_encode($output, JSON_UNESCAPED_UNICODE | JSON_PRETTY_PRINT);

require( dirname(__FILE__)."/db_close.php");