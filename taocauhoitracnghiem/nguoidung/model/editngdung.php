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
if(isset($_POST["thamso"]["mand"])){	
	$mand=$_POST["thamso"]["mand"];
	$tennd=$_POST["thamso"]["tennd"];
	$gioitinh=$_POST["thamso"]["gioitinh"];
	$quequan=$_POST["thamso"]["quequan"];
	$bomon=$_POST["thamso"]["bomon"];
	$id_NgDung=$_POST["thamso"]["id_NgDung"];
	$sql = "update nguoidung set ma_NgDung='$mand',ten_NgDung='$tennd',queQuan='$quequan',gioi_Tinh='$gioitinh',id_BMon='$bomon' where id_NgDung='$id_NgDung'";
	$thucthi=$con->query($sql);
	if($thucthi){
		echo "update thành công";
	}
	else{
		echo "Có lỗi xảy ra vui lòng kiểm tra lại";
	}
}else{
	echo "lỗi";
}
	
?>