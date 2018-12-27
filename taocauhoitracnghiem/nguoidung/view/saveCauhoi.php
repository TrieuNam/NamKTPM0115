<link rel="stylesheet" href="../../css/febe/style.css" type="text/css" media="screen" charset="utf-8">
<?php
include "../../ketnoicsdl/ketnoiCSDL.php";
if(isset($_POST["edit"]))
{
	$idmach=$_POST['idmach'];
	$noidung=$_POST['noidung'];
	$lc1=$_POST['lc1'];
	$lc2=$_POST['lc2'];
	$lc3=$_POST['lc3'];
	$lc4=$_POST['lc4'];
	$dapan=$_POST['dapan'];
	echo $dapan;
	//// la bi sao h han ko update dc do T, van giu nguyen kq do h H thu lai di/ rang han luu a, b rua T he. a b dau H
	 $sql="UPDATE  Dap_An SET `ma_ch` =  '".$idmach."',
	`noidung` ='".$noidung."',
	`lc1` ='".$lc1."',
	`lc2` ='".$lc2."',
	`lc3` ='".$lc3."',
	`lc4` ='".$lc4."',
	`Dap_An` ='".$dapan."'
	where ma_ch='".$idmach."'";
	echo $sql;
	$thucthi=$con->query($sql);
				if($thucthi){
					echo "Thêm thành công";
				}
			else{
					echo "Có lỗi xảy ra vui lòng kiểm tra lại";
			}
	//echo $sql;
	echo"<script>document.location.href='http://localhost:8080/taocauhoitracnghiem/nguoidung/view/listcauhoi.php' </script>";
}
?>