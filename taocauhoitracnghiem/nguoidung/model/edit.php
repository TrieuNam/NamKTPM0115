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

if(isset($_POST["thamso"]["tenbm"])){
			$ten_BMon = $_POST["thamso"]['tenbm'];
			$id_Khoa = $_POST["thamso"]['id_khoa'];
			$id_BMon=$_POST["thamso"]['id_bm'];
			$sql = "update bomon set ten_BMon='$ten_BMon',id_Khoa='$id_Khoa' where id_BMon='$id_BMon'";

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
