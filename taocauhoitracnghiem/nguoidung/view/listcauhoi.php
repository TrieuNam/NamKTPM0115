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
		
			$counts=0;
			$sql="SELECT * FROM dap_an";
			$thucthi=$con->query($sql);
			
			while($rowscounts = mysqli_fetch_array($thucthi))
			{
				 ++$counts;
			}
			
			
				
?>
<style>
body{
    color: #151515;
    background: #ede6e2;
}
</style>
</head>
<body>  
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
<a href="CauHoi.php" id="idthemcauhoi"><img id="idthemcauhoi" src="../../css/images/images/add_new.gif" align="absmiddle" border="0" /></a> <a id="idthemcauhoi" href="CauHoi.php">Câu hỏi mới</a>
</div>
<div class='wrapper' >
  <input id="filter" type="text" value="Tìm kiếm..." onClick="if(this.value=='Tìm kiếm...')this.value='';" onBlur="if(this.value=='')this.value='Tìm kiếm...';" name="filter" /> 
 	<div style="float: right;
    margin-right: 930px;
    margin-top: -34px;">
  <form  action="form.php" name="aaaaa" method="post">
  <select  name='made' style="margin-left: 10px;" >
	<?php	
		$sql="SELECT DISTINCT `thongtin_monhoc`.`ten_MonHoc`as `tenmon`,`thongtin_monhoc`.`id_mon`as`idmonhoc` FROM `thongtindethi`,`thongtin_monhoc` WHERE `thongtindethi`.`id_monHoc`=`thongtin_monhoc`.`id_mon`";	
			$thucthi=$con->query($sql);		
			while($rowss = mysqli_fetch_array($thucthi))
			{
					  
?>				
	<option value="<?php echo $rowss["idmonhoc"];?>"><?php echo $rowss["tenmon"];?></option>
		
		<?php } ?>	
	</select>				
	<input type="submit" name="choose" id="choose" value="IN Đề Thi" style="margin-right: -58px;" /> 
	  </form>
</div>
  <form action="xoacauhoi.php" method="post">
    <table cellpadding="1" cellspacing="1" id="resultTable">
			<thead>
				<tr>
					
					<th><input type="checkbox" name="checkid[]" id="checkid" value="<?=$counts?>" onClick="CheckALL(<?=$counts?>);" /></th>
					<th>Nội dung</th>
                    <th>LC1</th>
					<th>LC2</th>
					<th>LC3</th>
					<th>LC4</th>
					<th>Đáp án</th>
					<th>Mã Đề</th>					
					<th>Độ khó</th>					
					<th> Tên môn </th>
					<th>Edit</th>
				</tr>
			</thead>
        <tbody>
		<?php	
		
		
		 $sql="SELECT *,`thongtin_monhoc`.`ten_MonHoc` as `tenmon` FROM `dap_an`,`thongtin_monhoc` WHERE `dap_an`.`idmon` =`thongtin_monhoc`.`id_mon` ";
			$thucthi=$con->query($sql);
			
			while($rows = mysqli_fetch_array($thucthi))
			{		
			echo 	
			'<tr class="teacher">
			<td>' . '<input type="checkbox" name="selector[]" value="'.$rows['ma_ch'].'" />' . '</td>
			<td>'.$rows['noidung'].'</td>
			<td>'.$rows['lc1'].'</td>
			<td>'.$rows['lc2'].'</td>
			<td>'.$rows['lc3'].'</td>
			<td>'.$rows['lc4'].'</td>
			<td>'.$rows['Dap_An'].'</td>
			<td>'.$rows['made'].'</td>
			<td>'.$rows['DoKho'].'</td>
			<td>'.$rows['tenmon'].'</td>
			<td><a href="editcauhoi.php?ma_ch='.$rows['ma_ch'].'"><img src= "../../css/images/images/go_right.gif" /></a></td>
			</tr>';
			}
		
		?>
        </tbody>
    </table>
	<input type="submit" name="bnm" value="Delete all" style="margin:3px 50px auto;">
	</form>
	</form>
</div>
</div>
  </body>
</html>