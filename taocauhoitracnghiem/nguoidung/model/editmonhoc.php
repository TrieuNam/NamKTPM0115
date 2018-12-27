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

if(isset($_POST["thamso"]["monhoc"])){
$monhoc = $_POST["thamso"]['monhoc'];
$id_mon=$_POST["thamso"]['id_mon'];
$sql = "update thongtin_monhoc set ten_MonHoc='$monhoc' where id_mon='$id_mon'";

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
