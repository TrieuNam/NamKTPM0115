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
$idbm = $_POST["thamso"]["id_bm"];

include "../../ketnoicsdl/ketnoiCSDL.php";
	
	$sqlbm="DELETE FROM `bomon` WHERE id_BMon='$idbm'";
	$thucthi2=$con->query($sqlbm);
	if($thucthi2){
		echo "Xoa thành công";
	}
	else{
		echo "Có lỗi xảy ra vui lòng kiểm tra lại";
	}
?>