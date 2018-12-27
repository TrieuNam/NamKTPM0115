<?php 
include "../../ketnoicsdl/ketnoiCSDL.php";
function kiemtratrungmand($mnd){
	include "../../ketnoicsdl/ketnoiCSDL.php";
	$sql="SELECT * from nguoidung WHERE `mand` ='$mnd'";
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
	$txt_mk=$_POST["thamso"]["txt_mk"];
	
	$sql="INSERT INTO `nguoidung`(`id_NgDung`, `ma_NgDung`, `ten_NgDung`, `queQuan`, `gioi_Tinh`, `id_BMon`,`matKhau`) VALUES ('','$mand','$tennd','$quequan','$gioitinh','$bomon','$txt_mk')";
	$thucthi=$con->query($sql);
	if($thucthi){
		echo "Thêm thành công";
	}
	else{
		echo "Có lỗi xảy ra vui lòng kiểm tra lại";
	}
}
elseif(isset($_POST["thamso"]["tenbm"])){
	$ten_BMon=$_POST["thamso"]["tenbm"];
	$id_khoa=$_POST["thamso"]["id_khoa"];
	$sql="INSERT INTO `bomon`(`id_BMon`, `ten_BMon`, `id_Khoa`) VALUES ('','$ten_BMon','$id_khoa')";
	$thucthi=$con->query($sql);
	if($thucthi){
		echo "Thêm thành công";
	}
	else{
		echo "Có lỗi xảy ra vui lòng kiểm tra lại";
	}
	
}
elseif(isset($_POST["thamso"]["tieude"])){
	$tieude=$_POST["thamso"]["tieude"];

	$ghichu=$_POST["thamso"]["ghichu"];

	$id_NgDung=$_POST["thamso"]["id_NgDung"];

	$id_monhoc=$_POST["thamso"]["id_monhoc"];

	$sql="INSERT INTO `thongtindethi`(`id_DeThi`, `tieuDe`, `GhiChu`, `id_NguoiDung`, `id_monHoc`) VALUES ('','$tieude','$ghichu','$id_NgDung','$id_monhoc')";

	$thucthi1=$con->query($sql);
	if($thucthi1){
		echo "Thêm thành công";
	}
	else{
		echo "Có lỗi xảy ra vui lòng kiểm tra lại";
	}
}
elseif($_POST["thamso"]["tenkh"]){
		$tenkh=$_POST["thamso"]["tenkh"];
		$sql="INSERT INTO `khoa`(`Id_khoa`, `ten_Khoa`) VALUES('','$tenkh')";
		$thucthi=$con->query($sql);
	if($thucthi){
		echo "Thêm thành công";
	}
	else{
		echo "Có lỗi xảy ra vui lòng kiểm tra lại";
	}
}
elseif($_POST["thamso"]["monhoc"]){
		$monhoc=$_POST["thamso"]["monhoc"];
		$sql="INSERT INTO `thongtin_monhoc`(`id_mon`, `ten_MonHoc`) VALUES('','$monhoc')";
		$thucthi=$con->query($sql);
	if($thucthi){
		echo "Thêm thành công";
	}
	else{
		echo "Có lỗi xảy ra vui lòng kiểm tra lại";
	}
}
else{
	echo "thêm không thành công";
}




?>