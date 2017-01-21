<?php
/**
 * Created by IntelliJ IDEA.
 * User: jwm_000
 * Date: 2015-10-09
 * Time: 오후 7:41
 */
require( dirname(__FILE__)."/db_connect.php");


$pCorpNm = $_REQUEST["corpNm"]? $_REQUEST["corpNm"] : '';
$pCorpType = $_REQUEST["corpType"]? $_REQUEST["corpType"] : '';
$pCompKind = $_REQUEST["compKind"]? $_REQUEST["compKind"] : '';
$pBizSect = $_REQUEST["bizSect"]? $_REQUEST["bizSect"] : '';

$query = <<<EOD
select COUNT(*) ROW_COUNT
from
(
  select
  INFO.CORP_SEQ
  , INFO.CORP_NM
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
  inner join CODE as CORP_TYPE on INFO.CORP_TYPE = CORP_TYPE.CD and CORP_TYPE.GRP_CD = 'CORP_TYPE' and ('$pCorpType' = '' OR CORP_TYPE.CD = '$pCorpType')
  left join CODE as COMP_KIND on INFO.COMP_KIND = COMP_KIND.CD and COMP_KIND.GRP_CD = 'COMP_KIND' and ('$pCompKind' = '' OR COMP_KIND.CD = '$pCompKind')
  left join CODE as BIZ_SECT on INFO.BIZ_SECT = BIZ_SECT.CD and BIZ_SECT.GRP_CD = 'BIZ_SECT' and ('$pBizSect' = '' OR BIZ_SECT.CD = '$pBizSect')
  left join CODE as SOC_TYPE on INFO.SOC_TYPE = SOC_TYPE.CD and SOC_TYPE.GRP_CD = 'SOC_TYPE'
  where INFO.CORP_NM LIKE '%$pCorpNm%'
  and INFO.DEL_YN = 'N'
) PAGING
EOD;

//syslog(LOG_DEBUG, $query);

$result = mysqli_query($connection, $query);
$row = mysqli_fetch_array($result, MYSQLI_ASSOC);
print json_encode($row, JSON_UNESCAPED_UNICODE);

require( dirname(__FILE__)."/db_close.php");