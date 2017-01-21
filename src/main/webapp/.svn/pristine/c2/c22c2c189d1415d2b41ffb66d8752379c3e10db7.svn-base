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
select FINN_SEQ
, CORP_SEQ
, CLS_DT
, format(ASST_AMT, 0) as ASST_AMT
, format(DEPT_AMT, 0) as DEPT_AMT
, format(CAPIT_AMT, 0) as CAPIT_AMT
, format(SOLD_PUBL_AMT + SOLD_PRIV_AMT, 0) as SOLD_AMT
, format(SOLD_PUBL_AMT, 0) as SOLD_PUBL_AMT
, format(SOLD_PRIV_AMT, 0) as SOLD_PRIV_AMT
, format(SOLD_ORG_COST, 0) as SOLD_ORG_COST
, format(SOLD_INCOM, 0) as SOLD_INCOM
, format(SALES_AMT, 0) as SALES_AMT
, format(SALES_INCOM, 0) as SALES_INCOM
, format(EXT_PROF, 0) as EXT_PROF
, format(NET_INCOM, 0) as NET_INCOM
from CORP_FINN
where CORP_SEQ = $pCorpSeq
and CLS_DT > date(date_sub(now(), interval 10 year))
order by CLS_DT desc
EOD;
$result = mysqli_query($connection, $query);
$corpFinn = toArray($result);

print json_encode($corpFinn, JSON_UNESCAPED_UNICODE | JSON_PRETTY_PRINT);

require( dirname(__FILE__)."/db_close.php");