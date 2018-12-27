<?php
include('session.php');
if(!isset($_SESSION['login_user'])){
header("location: index.php"); // Redirecting To Home Page
}
?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link rel="stylesheet" href="../../css/febe/style.css" type="text/css" media="screen" charset="utf-8">
<script type="text/javascript" src="../../css/tiny_mce/tiny_mce.js"></script>
<script type="text/javascript" src="../../css/tiny_mce/int.load.js"></script>
	<script type="text/javascript" src="../../js/js/jquery-1.7.2.min.js"></script>
	<script type="text/javascript" src="../../js/js/jquery.validate.min.js"></script>
	<script type="text/javascript" src="../../js/js/messages_vi.js"></script>
	<link rel="stylesheet" type="text/css" href="../../css/demoo.css" />
	<link rel="stylesheet" type="text/css" href="../../css/component.css" />
	<link rel="stylesheet" type="text/css" href="../../css/organicfoodicons.css" />
	<script type="text/javascript">
		$(document).ready(function(){
			$("#contact").validate({
				errorElement: "span", // Định dạng cho thẻ HTML hiện thông báo lỗi
				rules: {
					cpassword: {
						equalTo: "#password" // So sánh trường cpassword với trường có id là password
					},
					min_field: { min: 5 }, //Giá trị tối thiểu
					max_field: { max : 10 }, //Giá trị tối đa
					range_field: { range: [4,10] }, //Giá trị trong khoảng từ 4 - 10
					rangelength_field: { rangelength: [4,10] } //Chiều dài chuỗi trong khoảng từ 4 - 10 ký tự
				}
			});
		});
	</script>
    
    
<title></title>
<style type="text/css">

</style>
</head>
<body>
<div id="profile" style="float: right;" >
  <b id="welcome">Welcome : <i><?php echo $login_session; ?></i></b>
  <b id="logout"><a href="logout.php">Log Out</a></b>
 </div>
<div class="form1">
<div style="margin:20px 70px 10px; font-size:14px;">
<font size="2" face="Tahoma"><b><a href="profile.php?listcauhoi">CÂU HỎI</a><img src="../../css/images/images/bl3.gif" border="0" /> THÊM CÂU HỎI MỚI</b></font></div>
<hr size="1" color="#cadadd" style="margin:15px auto;" />
<form action="" name="frmthemdethi" id="contact" class="contact-form" onsubmit="return checkform();" method="post" enctype="multipart/form-data">
<table width="850" border="0"  cellpadding="0" cellspacing="0" background="images/body.png" id="cauhoi_news">
    <tr>
     <td bgcolor="white" height="38px"colspan="2" align="center" class="style1">THÊM CÂU HỎI MỚI</td>
    </tr>
    <tr>
      <td width="116" align="right">Nội dung:<span style="color:red;font-size:22px">*</span>  </td>
	    <td width="634"><textarea name="noidung" id="textarea_full" class="required" cols="20" rows="2"> </textarea></td>
    </tr>
    
     <tr>
		<td align="right" class="tbltitle">Lựa chọn A: <span style="color:red;font-size:22px">*</span> </td>
		<td><input class="required" size=30 type="text" name="lc1" value=""autocomplete = "off"/></td>
  	</tr>
    
    <tr>
    	<td align="right" class="tbltitle">Lựa chọn B:<span style="color:red;font-size:22px">*</span> </td>
		<td><input class="required" size=30 type="text" name="lc2" value=""autocomplete = "off"/></td>
    </tr>
    
    <tr>
     	<td align="right" class="tbltitle">Lựa chọn C: <span style="color:red;font-size:22px">*</span> </td>
		<td><input size=30 class="required" type="text" name="lc3" value=""autocomplete = "off"/></td>
    </tr>
    
    <tr>
     	<td align="right" class="tbltitle">Lựa chọn D: <span style="color:red;font-size:22px">*</span> </td>
		<td><input size=30 class="required" type="text" name="lc4" value=""autocomplete = "off"/></td>
    </tr>
    
    <tr>
       	<td align="right" class="tbltitle2">Đáp án: <span style="color:red;font-size:22px">*</span></td>
		<td>
        	<select name='Dap_An'>
			<option value="1">Đáp án A</option>
			<option value="2">Đáp án B</option>
			<option value="3">Đáp án C</option>
			<option value="4">Đáp án D</option>
		  </select>
        </td>
     </tr> 
   <tr>
		<td align="right" class="tbltitle2">Tên đề: </td>
		<td>
		<?php
		include "../../ketnoicsdl/ketnoiCSDL.php";
		$sql="select * from thongtindethi";
		$thucthi=$con->query($sql);
		?>
		<select name="made">
		<?php
		while($row=mysqli_fetch_array($thucthi))
		{
		?>
		<option value="<?php echo $row["id_DeThi"];?>"><?php echo $row["tieuDe"];?></option>
		<?php
		}
		?>
        </select>
		</td>
		
	<tr/>
	<tr>
		<td align="right" class="tbltitle2">Tên Môn: </td>
		<td>
		<?php
		include "../../ketnoicsdl/ketnoiCSDL.php";
		$sql="select * from thongtin_monhoc";
		$thucthi=$con->query($sql);
		?>
		<select name="monhoc">
		<?php
		while($row=mysqli_fetch_array($thucthi))
		{
		?>
		<option value="<?php echo $row["id_mon"];?>"><?php echo $row["ten_MonHoc"];?></option>
		<?php
		}
		?>
        </select>
		</td>
	</tr>
	<tr>
		<td align="right" class="tbltitle2">Tên Môn: </td>
		<td>
		<?php
		include "../../ketnoicsdl/ketnoiCSDL.php";
		$sql="select DISTINCT DoKho from dap_an";
		$thucthi=$con->query($sql);
		?>
		<select name="DoKho1">
		<?php
		while($row=mysqli_fetch_array($thucthi))
		{
		?>
		<option value="<?php echo $row["DoKho"];?>"><?php echo $row["DoKho"];?></option>
		<?php
		}
		?>
        </select>
		</td>
	</tr>
   <tr>
       	<td align="right" class="tbltitle2">Trang thái: <span style="color:red;font-size:22px">*</span></td>
		<td>
        	<select name='hieuluc'>
			<option value='1'>1-Duyet va dang bai</option>
			<option value='2'>2-Khong duyet</option>
		  </select><br />
        </td>
     </tr> 
    <tr>
      <td colspan="2"align="center"  style="padding:10px 0px 10px 0px">
      <label>
       	 <input type="submit" name="ok" id="ok" value="Thêm câu hỏi" />
      </label>
      </td>
    </tr>
  </table>
</form>
</div>
</body>
</html>

<?php
if(isset($_POST['ok']))
{	
	$da=$_POST["Dap_An"];
	 $sqla="INSERT INTO dap_an(noidung, lc1, lc2, lc3, lc4, Dap_An, made,trangthai,idmon,DoKho)  
		VALUES ('$_POST[noidung]','$_POST[lc1]','$_POST[lc2]','$_POST[lc3]','$_POST[lc4]','$_POST[lc1]','$_POST[made]','$_POST[hieuluc]','$_POST[monhoc]','$_POST[DoKho1]')";
		
	 $sqlb="INSERT INTO dap_an(noidung, lc1,lc2,lc3,lc4, Dap_An, made, trangthai,idmon,DoKho)  
		VALUES('$_POST[noidung]','$_POST[lc1]','$_POST[lc2]','$_POST[lc3]','$_POST[lc4]','$_POST[lc2]','$_POST[made]','$_POST[hieuluc]','$_POST[monhoc]','$_POST[DoKho1]')";
		
	 $sqlc="INSERT INTO dap_an(noidung, lc1,lc2,lc3,lc4, Dap_An, made, trangthai,idmon,DoKho)  
		VALUES('$_POST[noidung]','$_POST[lc1]','$_POST[lc2]','$_POST[lc3]','$_POST[lc4]','$_POST[lc3]','$_POST[made]','$_POST[hieuluc]','$_POST[monhoc]','$_POST[DoKho1]')";
		
	 $sqld="INSERT INTO  dap_an(noidung, lc1,lc2,lc3,lc4, Dap_An, made, trangthai,idmon,DoKho)  
		VALUES('$_POST[noidung]','$_POST[lc1]','$_POST[lc2]','$_POST[lc3]','$_POST[lc4]','$_POST[lc4]','$_POST[made]','$_POST[hieuluc]','$_POST[monhoc]','$_POST[DoKho1]')";

		if($da==1)
		{
			$thucthi=$con->query($sqla);
				if($thucthi){
					echo "Thêm thành công";
				}
			else{
					echo "Có lỗi xảy ra vui lòng kiểm tra lại";
			}
			
			
		}
		elseif($da==2)
		{
			$thucthi=$con->query($sqlb);
				if($thucthi){
					echo "Thêm thành công";
				}
			else{
					echo "Có lỗi xảy ra vui lòng kiểm tra lại";
			}
		
		}
		elseif($da==3)
		{
			$thucthi=$con->query($sqlc);
				if($thucthi){
					echo "Thêm thành công";
				}
			else{
					echo "Có lỗi xảy ra vui lòng kiểm tra lại";
			}
			
		}
		else
		{
			$thucthi=$con->query($sqld);
				if($thucthi){
					echo "Thêm thành công";
				}
			else{
					echo "Có lỗi xảy ra vui lòng kiểm tra lại";
			}
			
			
		}
		echo"<script>document.location.href='http://localhost:8080/taocauhoitracnghiem/nguoidung/view/listcauhoi.php </script>";
}
?>