<link rel="stylesheet" href="febe/style.css" type="text/css" media="screen" charset="utf-8">
<?php
include "../../ketnoicsdl/ketnoiCSDL.php";
if(isset($_POST["bnm"]))
		{
			$ma_ch = $_POST["selector"];
			$delete = "delete from dap_an where ma_ch in(";
			foreach($ma_ch as $key=>$value)
			{
					$delete.="'".$value."',"; 
					//echo $value;
			}
			 $delete .= "'')";
			 echo $delete;
			
			$thucthi=$con->query($delete);
				if($thucthi){
				header("location:listcauhoi.php");
			}
			else {
				echo "<br>Error inserting database: " . mysqli_error();
			}
			mysqli_close ($con);  
			 
		}
		else
		{
			echo "<script>alert('Không thể xóa dữ liệu !');window.location='listcauhoi.php'</script>";
		}
?>