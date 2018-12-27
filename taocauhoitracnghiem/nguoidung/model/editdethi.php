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

if(isset($_POST["thamso"]["tieude"])){
			$tieude=$_POST["thamso"]["tieude"];		
			$ghichu=$_POST["thamso"]["ghichu"];
			$id_NgDung=$_POST["thamso"]["id_NgDung"];	
			$id_monhoc=$_POST["thamso"]["id_monhoc"];		
			$id_DeThi=$_POST["thamso"]["id_DeThi"];		
			$sql = "UPDATE `thongtindethi` SET tieuDe='$tieude',GhiChu='$ghichu',id_NguoiDung='$id_NgDung',id_monHoc='$id_monhoc' WHERE id_DeThi='$id_DeThi'";
			//echo $sql;
			$thucthi=$con->query($sql);
			
				if($thucthi){
					echo "update thành công";
				}
				else{
					echo "Có lỗi xảy ra vui lòng kiểm tra lại";
				}
	}

?>
