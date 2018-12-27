<?php
	# ket noi CSDL 
	include "../../ketnoicsdl/ketnoiCSDL.php";
	# phan trang hien thi mat dinh
	$page = isset($_POST['page']) ? intval($_POST['page']) : 1;
	$rows = isset($_POST['rows']) ? intval($_POST['rows']) : 10;
	if (isset($_POST['timNhom']))
	{
	$timNhom=$_POST['timNhom'];
	$offset = ($page-1)*$rows;
	$result = array();
	#dem so dong cua ket qua cau truy van
	$rs = $con->query("SELECT count(*) FROM `nhoms` WHERE maNhom Like '%$timNhom%' or tenNhom Like '%$timNhom%'");
	$row = mysqli_fetch_row($rs);
	$result["total"] = $row[0];
	# lay thong tin tu bang nguoi dung
	$rs = $con->query("SELECT * from nhoms WHERE maNhom Like '%$timNhom%' or tenNhom Like '%$timNhom%' limit $offset,$rows");
	$items = array();
	while($row = mysqli_fetch_object($rs)){
		array_push($items, $row);
	}
	$result["rows"] = $items;
	echo json_encode($result);
   }
   else
   {
    $offset = ($page-1)*$rows;
	$result = array();
	$rs = $con->query ("select count(1) from thongtin_cauhoi");
	$row = mysqli_fetch_row($rs);
	$result["total"] = $row[0];
	$rs = $con->query("select * from thongtin_cauhoi limit $offset,$rows");
	$items = array();
	while($row = mysqli_fetch_object($rs)){
		array_push($items, $row);
	}
	$result["rows"] = $items;
	echo json_encode($result);

   }
?>
