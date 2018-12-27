<?php 
include "../../ketnoicsdl/ketnoiCSDL.php";
function kiemtratrungmand($mnd){
	include "../../ketnoicsdl/ketnoiCSDL.php";
	$sql="SELECT * from nguoidung WHERE `ma_NgDung` ='$mnd'";
	$thucthi=$con->query($sql);
	if(mysqli_num_rows($thucthi)>0){

		return true;
	}
	else{ 
		return false;
	}
}

if(isset($_POST["thamso"]["ten_Khoa"])){
$ten_Khoa = $_POST["thamso"]['ten_Khoa'];
$Id_khoa=$_POST["thamso"]['Id_khoa'];
$sql = "update khoa set ten_Khoa='$ten_Khoa' where Id_khoa='$Id_khoa'";

$thucthi=$con->query($sql);

	if($thucthi){
		echo "update thành công";
	}
	else{
		echo "Có lỗi xảy ra vui lòng kiểm tra lại";
	}
}else{
		echo "Lỗi";
	}
?>
