<?php
/**
 * Created by IntelliJ IDEA.
 * User: jwm_000
 * Date: 2015-10-09
 * Time: 오후 7:41
 */
require( dirname(__FILE__)."/db_connect.php");

$pEmpSeq = $_REQUEST["empSeq"];

$query = <<<EOD
    delete from CORP_EMP
    where EMP_SEQ = $pEmpSeq
EOD;

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