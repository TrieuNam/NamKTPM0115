<?php
include('session.php');
if(!isset($_SESSION['login_user'])){
header("location: index.php"); // Redirecting To Home Page
}
?>
<html xmlns="http://www.w3.org/1999/xhtml" dir="ltr" lang="en-US">
<head profile="http://gmpg.org/xfn/11">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>WEB tạo đề thi</title>
</title>
<!--sa poip up-->
	<link href="../../css/src/facebox.css" media="screen" rel="stylesheet" type="text/css" />
	<script src="../../js/lib/jquery.js" type="text/javascript"></script>
	  <script src="../../css/src/facebox.js" type="text/javascript"></script>
	   <script type="text/javascript" src="../js/nguoidung.js"></script>
	  <script type="text/javascript">
		jQuery(document).ready(function($) {
		  $('a[rel*=facebox]').facebox({
			loadingImage : 'src/loading.gif',
			closeImage   : 'src/closelabel.png'
		  })
		})
	  </script>
	  <link rel="stylesheet" href="../../css/febe/style.css" type="text/css" media="screen" charset="utf-8">
	
	<script src="../../js/js/application.js" type="text/javascript" charset="utf-8"></script>
	
	<script type="text/javascript">
        $(document).ready(function() {
        $("#resultTable thead tr th:first input:checkbox").click(function() {
            var checkedStatus = this.checked;
            $("#resultTable tbody tr td:first-child input:checkbox").each(function() {
                this.checked = checkedStatus;
            });
        });

            $("#resultTable").selectAllRows();

            $("#resultTable").selectAllRows({ column: 'last' });

            $("#resultTable").selectAllRows({
                column: '2',
                selectTip: 'Select All Students',
                unselectTip: 'Un-Select All Students'
                })
                .css("border-width", "10px");
        });
		function CheckALL(counts)
{
	for(i=0;i<counts;i++)
	{
		
	if(document.getElementById("checkid").checked!="")
	{
	document.getElementById("checkid").checked	='checked';
	}else{
	document.getElementById("checkid").checked	='';	
	}
	}
}
$(document).ready(function(){
        $("#idthemcauhoi").click(function(){
            $("#aaa").show();
        })
        $("#idthemcauhoi").click(function(){
            $("#aaa").hide();
        })
    })
    </script>
<?php
			
			include "../../ketnoicsdl/ketnoiCSDL.php";
	if(isset($_POST["choose"])){
		$da=$_POST["made"];
			$counts=0;
			$sql="SELECT * FROM dap_an where idmon='$da'";
			$thucthi=$con->query($sql);
			if($thucthi){
			while($rowscounts = mysqli_fetch_array($thucthi))
			{
				$counts++;
			}
		}else{
			echo '<script> alert("Bạn chưa chọn bộ môn");</script>';
		}
	}
			
				
?>
</head>
<body style=" background: #ede6e2;">  
 <div id="profile" style="float: right;" >
  <b id="welcome">Welcome : <i><?php echo $login_session; ?></i></b>
  <b id="logout"><a href="logout.php">Log Out</a></b>
 </div>
<div style="margin:20px 70px 10px; font-size:14px;">
<font size="2" face="Tahoma">
<a href="profile.php">CÂU HỎI<img src="../../css/images/images/bl3.gif" border="0" />
 QUẢN LÝ CÂU HỎI</a></font>
</div>
<hr size="1" color="#cadadd" style="margin:15px auto;" />
<div  id="aaa"> 
<script type="text/javascript" src="../js/nguoidung.js"></script>
<div style="margin:20px 70px auto;" id="idthemcauhoi">
</div>
<div class='wrapper' >
 	<div style="float: right;
    margin-right: 500px;
    margin-top:-17px;; color: #151515; background: #cac2c25c;">
	  <form action="inpdf.php"  name="" method="post" target="_blank"> 
	   <div class="fitem">  
			<p> Số câu hỏi </p>
			
			 <input label=""  value="<?=$counts?>" labelPosition="top" name="socau" required="true" id="txt_mand" class="easyui-textbox" readonly="readonly" style="background-color:#CCC" style="width:260px" >
			<p> tên môn </p>
			<?php
			$sql="SELECT ten_MonHoc FROM thongtin_monhoc where id_mon='$da'";
			$thucthi=$con->query($sql);
			while($rowscoun = mysqli_fetch_array($thucthi))
			{?>
			<input label="id Môn"  value="<?=$rowscoun['ten_MonHoc']?>" labelPosition="top" name="idmon" required="true" class="easyui-textbox" readonly="readonly" style="background-color:#CCC" style="width:260px" >
			<?php } ?>
			<p> Số câu Dễ</p>
			<?php 
			$counts1=0;
			$sql="SELECT * FROM dap_an where DoKho='Dễ' and idmon='$da'";
			$thucthi=$con->query($sql);
			while($rowscounts = mysqli_fetch_array($thucthi))
			{
				$counts1++;
			}
			?>
			<input size=30 type="text" name="tongsocaude" readonly="readonly" style="background-color:#CCC" value="<?=$counts1;?>"></td>
           <p> Nhập số câu dễ</p>
		   <input label="Nhap so cau de" labelPosition="top" name="socauDe" type="text"  required="true"  id="mes" onkeyup="xemKetQua()" class="easyui-textbox" style="width:260px" >
			<div style="margin-left: 301px;margin-top: -137px;">    	
			<p> Số câu khó</p>
			<?php 
			$counts2=0;
			$sql="SELECT * FROM dap_an where DoKho='Khó' and idmon='$da'";
			$thucthi=$con->query($sql);
			while($rowscounts = mysqli_fetch_array($thucthi))
			{
				$counts2++;
			}
			?>
			<input size=30 type="text" name="tongsocaude" readonly="readonly" style="background-color:#CCC" value="<?=$counts2;?>"></td>
         <div>
		 <p> Nhập số câu Khó</p>
		   <input label="Nhap so cau kho" labelPosition="top" name="socaukho" type="text" required="true"id="mes2" onkeyup="xemKetQua()"  class="easyui-textbox" style="width:260px" >
			</div>
			</div>   
	   </div>
	  <div style="    margin-top: 24px; margin-left: 119px;">
	   <label>Số câu hiện tại </label>
	    <input type="text" id="result" value=""  readonly="readonly" style="background-color:#CCC; width:50px; size=10"  ><?php echo '    /'.$counts?></input>
	   	<input type="submit" name="demo" id="demo" value="Xem đề thi"  ></input>
		
		</div>
	  </form>

</div>
  </body>
</html>
<script type="text/javascript">
function xemKetQua() {
   // Xác định 2 thẻ theo id
   var input_text = document.getElementById("mes");
   var input_text1 = document.getElementById("mes2");
   
   var result = document.getElementById("result");
   // lấy giá trị của input_text
  var tong = parseInt(input_text.value) + parseInt(input_text1.value);
   if (!isNaN(tong)){
          //result.value = tong;
		  result.value = tong
   }
   
   // gán giá trị cho result
   
}
</script>