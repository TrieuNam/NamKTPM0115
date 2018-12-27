<?php


include('session.php');


if(!isset($_SESSION['login_user'])){
header("location: index.php"); // Redirecting To Home Page
}
?>

<!DOCTYPE html>
<html>
<head>
  <title>Your Home Page</title>
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.4/jquery.min.js"></script>
	
	
		<script src="../../js/modernizr-custom.js"></script>	
	<link rel="stylesheet" type="text/css" href="../../css/demoo.css" />
	<link rel="stylesheet" type="text/css" href="../../css/component.css" />
	<link rel="stylesheet" type="text/css" href="../../css/organicfoodicons.css" />
	<script src="../../js/classie.js"></script>
	<script src="../../js/dummydata.js"></script>
	<script src="../../js/main.js"></script>
		<script src="../../js/modernizr-custom.js"></script>
</head>
<body>
 <div id="profile" style="float: right;" >
  <b id="welcome">Welcome : <i><?php echo $login_session; ?></i></b>
  <b id="logout"><a href="logout.php">Log Out</a></b>
 </div>
	<div class="container">
			<nav id="ml-menu" class="menu">
			<button class="action action--close" aria-label="Close Menu"><span class="icon icon--cross"></span></button>
			<div class="menu__wrap">
					<ul data-menu="main" class="menu__level" tabindex="-1" role="menu" aria-label="All">
					<li class="menu__item" role="menuitem"><a class="menu__link" id="page1" data-submenu="submenu-1" aria-owns="submenu-1" href="#">Quản lý người dùng</a></li>
					<li class="menu__item" role="menuitem"><a class="menu__link" id="page2" data-submenu="submenu-2" aria-owns="submenu-2" href="#">Quản lý đề thi</a></li>
					<li class="menu__item" role="menuitem"><a class="menu__link" id="page3" data-submenu="submenu-3" aria-owns="submenu-3" href="#">Quản lý bộ môn,khoa và môn học </a></li>
					<li class="menu__item" role="menuitem"><a class="menu__link" id="page4" data-submenu="submenu-4" aria-owns="submenu-4" href="listcauhoi.php">Quản lý câu hỏi</a></li>
						<ul data-menu="submenu-4" id="submenu-4" class="menu__level" tabindex="-1" role="menu" aria-label="Mylk &amp; Drinks">
							<li ><a class="menu__link" href="listcauhoi.php">Danh Sách Câu Hỏi</a></li>
							<li ><a class="menu__link" href="CauHoi.php">Thêm Câu Hỏi</a></li>
							
						</ul>
				</ul>
			
			</div>
		</nav>
	
		<div class="content" id="result"style="margin-left: 216px; margin-top: 68px;" >
			<p> 
				Website tạo đề thi.
			
			</p>
			<!-- Ajax loaded content here -->
		</div>
		
	</div>
 
</body>

<script>
function myFunction1() {
    var x = document.getElementById("myTopnav1");
    if (x.className === "Menu") {
        x.className += " responsive";
    } else {
        x.className = "Menu";
    }
}
$(document).ready(function(){
		   $("#page1").click(function(){
		   	$('#result').load('quanlynguoidung.php');
		     //alert("Thanks for visiting!");
		   }); 
		    $("#page2").click(function(){
		   	$('#result').load('quanlydethi.php');
		     //alert("Thanks for visiting!");
		   }); 
		   $("#page3").click(function(){
		   	$('#result').load('quanlybomon.php');
		     //alert("Thanks for visiting!");
		   });		    
		 });
    (function() {
		var menuEl = document.getElementById('ml-menu'),
			mlmenu = new MLMenu(menuEl, {
				// breadcrumbsCtrl : true, // show breadcrumbs
				// initialBreadcrumb : 'all', // initial breadcrumb text
				backCtrl : false, // show back button
				// itemsDelayInterval : 60, // delay between each menu item sliding animation
				onItemClick: loadDummyData // callback: item that doesn´t have a submenu gets clicked - onItemClick([event], [inner HTML of the clicked item])
			});
		// simulate grid content loading
		var gridWrapper = document.querySelector('.content');

		function loadDummyData(ev, itemName) {
			ev.preventDefault();

			
		}
	})();
</script>
</html>