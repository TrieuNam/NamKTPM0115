<?php


session_start(); // Starting Session
$error = ''; // Variable To Store Error Message include "../../ketnoicsdl/ketnoiCSDL.php";

if (isset($_POST['submit'])) {
if (empty($_POST['username']) || empty($_POST['password'])) {
$error = "Username or Password is invalid";
}
else
{
// Define $username and $password
$username = $_POST['username'];
$password = $_POST['password'];

// mysqli_connect() function opens a new connection to the MySQL server.
$conn = mysqli_connect("localhost", "root", "", "taocauhoitracnghiem");

// SQL query to fetch information of registerd users and finds user match. 
$query = "SELECT ma_NgDung, matKhau from nguoidung where ma_NgDung=? AND matKhau=? LIMIT 1";

// To protect MySQL injection for Security purpose
$stmt = $conn->prepare($query);
$stmt->bind_param("ss", $username, $password);
$stmt->execute();
$stmt->bind_result($username, $password);
$stmt->store_result();

if($stmt->fetch()) //fetching the contents of the row 	$thucthi=$con->query($sql);
        {
          $_SESSION['login_user'] = $username; // Initializing Session
          header("location: profile.php"); // Redirecting To Profile Page 
        }
else {
       $error = "Username or Password is invalid";
     }
mysqli_close($conn); // Closing Connection   ' or 'a'='a 
}
/*
// code loi sql injection
if ($username == "" || $password ==""){
			echo "username hoặc password bạn không được để trống!";
	}else{
			$sql = "SELECT ma_NgDung, matKhau from nguoidung where ma_NgDung='$username' AND matKhau='$password' ";
			$query = mysqli_query($conn,$sql);
			$num_rows = mysqli_num_rows($query);
		if ($num_rows==0) {
			echo "tên đăng nhập hoặc mật khẩu không đúng !";
		}else{
			$_SESSION['login_user'] = $username; // Initializing Session
          header("location: profile.php"); // Redirecting To Profile Page 
		}
	}
}
*/
}
?>

