<link rel="stylesheet" href="febe/style.css" type="text/css" media="screen" charset="utf-8">
<style>
body{
 background: #ede6e2;
   margin-left:300px;
}
</style>
	<?php
include "../../ketnoicsdl/ketnoiCSDL.php";
?>
<form action="../model/examples/inpdf.php" method="post">
<?php
if(isset($_POST["demo"])){
		$da=$_POST["socauDe"];
		$kho=$_POST["socaukho"];			
		$d=$_POST["idmon"];
		$j=1;
		$sql="select  id_mon from thongtin_monhoc where ten_MonHoc='$d'";
		
		$thucthi0=$con->query($sql);?>
		<label style="float: left;margin-left: 200px;">Số Câu Dễ Đã Nhập</label>
		<input style="float: right;margin-right: 426px;" size=30 type="text" name="SoCauDeDaNhap" readonly="readonly" style="background-color:#CCC" value="<?=$da;?>"></td>
		<label style="float: left;margin-left: 200px;">Số Câu Khó Đã Nhập</label>
		<input style="float: right;margin-right: 426px;" size=30 type="text" name="SoCauKhoDaNhap" readonly="readonly" style="background-color:#CCC" value="<?=$kho;?>"></td>
		<label style="float: left;margin-left: 200px;">Tên Môn</label>
		<input style="float: right;margin-right: 426px;margin-bottom:50px" size=30 type="text" name="idmon" readonly="readonly" style="background-color:#CCC" value="<?=$d;?>"></td>
		<input style=" margin-left: 700px;margin-top: -80px;" type="submit" name="create" id="create" value="In đề thi"  ></input>
		<?php
		while($row3=mysqli_fetch_array($thucthi0)){
				$id_mon=$row3['id_mon'];
			
				$thucthi=$con->query("select  * from dap_an where DoKho='Dễ' and idmon='$id_mon' ORDER BY RAND() LIMIT $da");
				$thucthi2=$con->query("select  * from dap_an where DoKho='Khó' and idmon='$id_mon' ORDER BY RAND() LIMIT $kho");
				while($row=mysqli_fetch_array($thucthi)){
					?>		
							<table border="1" style="border-collapse:collapse;"  > 
									<tr>
										<td width="300" colspan="2"> 
											<?php echo "Câu ".$j.":".$row['noidung']; ?>
										</td>	
									</tr>
									<tr> 
										<td colspan="2">
											<ol type="A" style="width:600px; position:relative;">
													<li style="float:left; width:40%;"><?php echo $row['lc1'];?></li>	
													<li style="float:left; width:40%;"><?php echo $row['lc2'];?></li>					 
													<li style="float:left; width:40%;"><?php echo $row['lc3'];?></li>		
													<li style="float:left; width:40%;"><?php echo $row['lc4'];?></li>
											</ol>
										</td>
									</tr>
								
							
					<?php	++$j;}
					while($row2=mysqli_fetch_array($thucthi2)){ ?>
					<table border="1" style="border-collapse:collapse;"> 
									<tr>
										<td width="300" colspan="2"> 
											<?php echo "Câu ".$j.":".$row2['noidung']; ?>
										</td>
									</tr>
									<tr> 
										<td colspan="2">
											<ol type="A" style="width:600px; position:relative;">
													<li style="float:left; width:40%;"><?php echo $row2['lc1'];?></li>	
													<li style="float:left; width:40%;"><?php echo $row2['lc2'];?></li>					 
													<li style="float:left; width:40%;"><?php echo $row2['lc3'];?></li>		
													<li style="float:left; width:40%;"><?php echo $row2['lc4'];?></li>
											</ol>
										</td>
									</tr>
								</table>
								
				<?php	++$j;}
				
				}
}
?>
</form>
