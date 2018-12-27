<?php 

function travemang($sql){
	include "../../ketnoicsdl/ketnoiCSDL.php";
	$thucthi = $con ->query($sql);
	$i =0;
	while($row=mysqli_fetch_array($thucthi)){
		$mang[$i]=$row;
		$i++;
	}
	return $mang;
}
function chonphantumang(){
	$tam=travemang("select   * from dap_an");
	
	shuffle($tam);
	$j=0;
	echo "Đề ".ghepmang();
	foreach($tam as $key => $value ){ ?>
		 
	<table border="1" style="border-collapse:collapse;"> 
		<tr>
			<td width="300" colspan="2"> 
				<?php echo "Câu ".$j.":".$value[1]; ?>
				<?php  $id= $value[0];?>
			</td>
		</tr>
		<tr> 
			<td colspan="2">
				<ol type="A" style="width:600px; position:relative;">
					    <li style="float:left; width:40%;"><?php echo $value[2];?></li>	
						<li style="float:left; width:40%;"><?php echo $value[3];?></li>					 
						<li style="float:left; width:40%;"><?php echo $value[4];?></li>		
						<li style="float:left; width:40%;"><?php echo $value[5];?></li>
				</ol>
			</td>
		</tr>
	</table>
	<?php ++$j; }
	
}
?>
<?php 
function ghepmang(){
	$tam=travemang("select * from thongtindethi");
	$tamp=travemang("select * from dap_an");
	shuffle($tam);
	$j=0;
	foreach($tam as $key => $value ){ 
	foreach($tamp as $key1 => $value1 ){ ?>
	<?php 
		if($value[0]==$value1[7]){
			echo $id=$value1[7];
		}continue;
	
	?>
	<?php ++$j;}} 
return  $id; }	

?>