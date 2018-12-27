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


$iddethi =  $_POST["thamso"]["id_DeThi"];

include "../../ketnoicsdl/ketnoiCSDL.php";
	
$sqldethi="DELETE FROM `thongtindethi` WHERE id_DeThi='$iddethi'";
	$thucthi3=$con->query($sqldethi);
	
	if($thucthi3){
		echo "Xoa thành công";
	}
	else{
		echo "Có lỗi xảy ra vui lòng kiểm tra lại";
	}

?>