<?php
include('login.php'); // Includes Login Script
if(isset($_SESSION['login_user'])){
header("location: profile.php"); // Redirecting To Profile Page
}
?> 

<!DOCTYPE html>
<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta name="keywords" content="Existing Login Form Widget Responsive, Login Form Web Template, Flat Pricing Tables, Flat Drop-Downs, Sign-Up Web Templates, Flat Web Templates, Login Sign-up Responsive Web Template, Smartphone Compatible Web Template, Free Web Designs for Nokia, Samsung, LG, Sony Ericsson, Motorola Web Design">
<script type="application/x-javascript"> 
addEventListener("load", function() { setTimeout(hideURLbar, 0); }, false); function hideURLbar(){ window.scrollTo(0,1); } 
</script>

<link href="../../css/logincss/popuo-box.css" rel="stylesheet" type="text/css" media="all" />

<link rel="stylesheet" href="../../css/logincss/style.css" type="text/css" media="all">

<link href="//fonts.googleapis.com/css?family=Quicksand:300,400,500,700" rel="stylesheet">
</head>
<body>
 
 <div class="w3layoutscontaineragileits">
	<h2>Đăng Nhập</h2>
		<form action="#" method="post">
			<input id="name" type="text" Name="username" placeholder="EMAIL" required="">
			<input type="password"  id="password" name="password" placeholder="PASSWORD" required="">
			<div class="aitssendbuttonw3ls">
				<input name="submit" type="submit" value=" Login ">
			</div>
			 <span><?php echo $error; ?></span>
		</form>
	</div>
  
  

</body>
</html>