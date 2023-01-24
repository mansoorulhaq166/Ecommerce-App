<?php
$conn = mysqli_connect("localhost", "root", "");
mysqli_select_db($conn,"ecomdb");

$email = trim($_POST['email']);
$password = trim($_POST['password']);

// $qry = "select * from tbl_user where email='$email' and password='$password'";
// $raw = mysqli_query($conn, $qry);

$stmt = mysqli_prepare($conn, "SELECT * FROM tbl_user WHERE email=? AND password=?");
mysqli_stmt_bind_param($stmt, "ss", $email, $password);
mysqli_stmt_execute($stmt);
$result = mysqli_stmt_get_result($stmt);

$count = mysqli_num_rows($result);
if($count>0) {
    $response['message'] = 'exist';
} else {
    $response['message'] = 'Doesnot_exist';
}
echo json_encode($response);
?>