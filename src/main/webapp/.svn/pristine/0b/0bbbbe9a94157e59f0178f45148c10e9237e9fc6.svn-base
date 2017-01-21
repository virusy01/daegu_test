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

print json_encode($corpHelp, JSON_UNESCAPED_UNICODE | JSON_PRETTY_PRINT);

require( dirname(__FILE__)."/db_close.php");