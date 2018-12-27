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



$id_mon = $_POST["thamso"]["id_mon"];


include "../../ketnoicsdl/ketnoiCSDL.php";

$sqlkhoa="DELETE FROM `thongtin_monhoc` WHERE id_mon='$id_mon'";
	$thucthi4=$con->query($sqlkhoa);
	
	if($thucthi4){
		echo "Xoa thành công";
	}
	else{
		echo "Có lỗi xảy ra vui lòng kiểm tra lại";
	}
?>