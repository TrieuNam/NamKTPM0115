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

$id = $_POST["thamso"]["id_NgDung"];



include "../../ketnoicsdl/ketnoiCSDL.php";
	$sql="DELETE FROM `nguoidung` WHERE id_NgDung='$id'";
	
	$thucthi=$con->query($sql);
	if($thucthi){
		echo "Xoa thành công";
	}
	else{
		echo "Có lỗi xảy ra vui lòng kiểm tra lại";
	}

?>