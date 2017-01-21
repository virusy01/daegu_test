<?php
/**
 * Created by IntelliJ IDEA.
 * User: jwm_000
 * Date: 2015-10-09
 * Time: 오후 7:41
 */
require( dirname(__FILE__)."/db_connect.php");

$pCorpSeq = is_null($_REQUEST["corpSeq"])? "1" : $_REQUEST["corpSeq"];

$query = <<<EOD
  select
  INFO.CORP_SEQ
  , INFO.CORP_NM
  , INFO.CEO_NM
  , INFO.BIZ_NO
  , INFO.BIZ_DT
  , INFO.CORP_TYPE
  , case when INFO.CORP_TYPE = '06' then INFO.CORP_TYPE2 else CORP_TYPE.CD_NM end as CORP_TYPE_NM
  , INFO.COMP_KIND
  , COMP_KIND.CD_NM as COMP_KIND_NM
  , INFO.BIZ_SECT
  , case when INFO.BIZ_SECT = '16' then INFO.BIZ_SECT2 else BIZ_SECT.CD_NM end as BIZ_SECT_NM
  , INFO.SOC_YN
  , INFO.SOC_TYPE
  , SOC_TYPE.CD_NM as SOC_TYPE_NM
  , INFO.SOC_CERT_END_DT
  from CORP_INFO as INFO
  inner join CODE as CORP_TYPE on INFO.CORP_TYPE = CORP_TYPE.CD and CORP_TYPE.GRP_CD = 'CORP_TYPE'
  inner join CODE as COMP_KIND on INFO.COMP_KIND = COMP_KIND.CD and COMP_KIND.GRP_CD = 'COMP_KIND'
  inner join CODE as BIZ_SECT on INFO.BIZ_SECT = BIZ_SECT.CD and BIZ_SECT.GRP_CD = 'BIZ_SECT'
  left join CODE as SOC_TYPE on INFO.SOC_TYPE = SOC_TYPE.CD and SOC_TYPE.GRP_CD = 'SOC_TYPE'
  where INFO.CORP_SEQ = $pCorpSeq
EOD;

$result = mysqli_query($connection, $query);
$corpInfo = toObject($result);

$query = <<<EOD
select EMP_SEQ
, CORP_SEQ
, CLS_DT
, EMP_NUM
, WEAK_NUM
, FULL_NUM
, PART_NUM
, CXX_SAL
, NORM_SAL
, MOD_DT
, CRE_DT
from CORP_EMP
where CLS_DT = (
                  select max(CLS_DT)
                  from CORP_EMP
                  where CORP_SEQ = $pCorpSeq
               )
EOD;
$result = mysqli_query($connection, $query);
$corpEmp = toObject($result);

$query = <<<EOD
select FINN_SEQ
, CORP_SEQ
, CLS_DT
, ASST_AMT
, DEPT_AMT
, CAPIT_AMT
, SOLD_PUBL_AMT + SOLD_PRIV_AMT as SOLD_AMT
, SOLD_PUBL_AMT
, SOLD_PRIV_AMT
, SOLD_PUBL_AMT
, SOLD_ORG_COST
, SOLD_INCOM
, SALES_AMT
, SALES_INCOM
, EXT_PROF
, NET_INCOM
from CORP_FINN
where CLS_DT = (
                  select max(CLS_DT)
                  from CORP_FINN
                  where CORP_SEQ = $pCorpSeq
               )
EOD;
$result = mysqli_query($connection, $query);
$corpFinn = toObject($result);

$query = <<<EOD
select HELP_SEQ
, CORP_SEQ
, HELPER
, PRJ_NM
, HELP_ST_DT
, HELP_END_DT
, HELP_AMT
from CORP_HELP
where CORP_SEQ = '$pCorpSeq'
and HELP_ST_DT >= (select date_format(date_sub(now(), interval 3 year), '%Y-01-01'))
order by HELP_ST_DT desc
EOD;
$result = mysqli_query($connection, $query);
$corpHelp = toArray($result);


$output = array();

$output["CORP_INFO"] = $corpInfo;
$output["CORP_EMP"] = $corpEmp;
$output["CORP_FINN"] = $corpFinn;
$output["CORP_HELP"] = $corpHelp;

print json_encode($output, JSON_UNESCAPED_UNICODE | JSON_PRETTY_PRINT);

require( dirname(__FILE__)."/db_close.php");