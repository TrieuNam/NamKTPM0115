 <?php include "../../header/header.php" ;
 include('session.php');
if(!isset($_SESSION['login_user'])){
header("location: index.php"); // Redirecting To Home Page
}
 ?>
 <script type="text/javascript" src="../js/nguoidung.js"></script>
<script src="../../js/js/application.js" type="text/javascript" charset="utf-8"></script>
  <div>
      <table id="dg_taodethi" class="easyui-datagrid" title="Quản lý đề thi" style="width:1120px;height:500px"
            data-options="singleSelect:true,collapsible:true,url:'../model/taidulieudethi.php',pagination:true,toolbar:'#toolbar_nguoidung'">
        <thead>
            <tr>
                <th data-options="field:'id_DeThi',width:100" >id Đề Thi</th>
                <th data-options="field:'tieuDe',width:300" >Tiêu đề</th>
                <th data-options="field:'GhiChu',width:300,align:'left'">Ghi chú</th>
                <th data-options="field:'id_NguoiDung',width:150,align:'left'">id Người dùng</th>
                <th data-options="field:'id_monHoc',width:260,align:'left'">id Môn học</th>
				
				
            </tr>
        </thead>
    </table>
  </div>
  <!--Toolbar datagrid-->
  <div id="toolbar_nguoidung">
    <div>
      <span class="easyui-linkbutton" iconCls="icon-add" onclick="themdethi()">Thêm </span>
      <span class="easyui-linkbutton"  iconCls="icon-remove" onclick="xoadethi()">Xóa </span>
      <span class="easyui-linkbutton" iconCls="icon-edit" onclick="editdethi()">sửa </span>
             <input id="filter" type="text" value="Tìm kiếm..." onClick="if(this.value=='Tìm kiếm...')this.value='';" onBlur="if(this.value=='')this.value='Tìm kiếm...';" name="filter" /> 

    </div>
  </div>
   <!--dialog them thong tin nguoi dung-->
   <div id="thongtinnguoidung" class="easyui-dialog" style="width:445px;height:fit-content;padding:10px 20px"
        closed="true" buttons="#nguoidung-buttons" >
    <div class="ftitle" style="text-align:center">Thông Tin Bộ môn</div>
    <form id="frm_thongtinnguoidung" method="">    
        <div class="fitem">        
            <input name=""  label="Tiêu đề" labelPosition="top" required="true" id="txt_tieude" class="easyui-textbox" style="width:260px" >
        </div>
		<div class="fitem">
		   <div class="fitem">
            
            <input name=""  label="Ghi chú" labelPosition="top" required="true" id="txt_ghichu" class="easyui-textbox" style="width:260px" >
        </div>
		   <select label="id_NguoiDung" labelPosition="top" class="easyui-combobox"  style="width:260px" id="check_nguoidung" >    	
				<?php 
				include "../../ketnoicsdl/ketnoiCSDL.php";
				
				$thucthi=$con->query("select id_NgDung from nguoidung where ten_NgDung='$login_session'");
				
					while($row=mysqli_fetch_array($thucthi)){
							
								echo '<option>'.$row['id_NgDung'].'</option>';
							
					}	
				?>    
            </select>      
	   </div>
            <select label="ID Môn Học" labelPosition="top" class="easyui-combobox"  style="width:260px" id="check_monhoc" >    
				
				<?php 
				include "../../ketnoicsdl/ketnoiCSDL.php";
				$thucthi=$con->query("select id_mon from thongtin_monhoc");
					while($row=mysqli_fetch_array($thucthi)){
							echo '<option>'.$row[0].'</option>';
					}	
				?>
             
            </select>
        </div>      
    </form>
</div>
<!--botton-->
<div id="nguoidung-buttons">
  <a href="javascript:void(0)" class="easyui-linkbutton" id="themdethi" iconCls="icon-ok">Thêm</a>
  <a href="javascript:void(0)" class="easyui-linkbutton" id="suadethi" iconCls="icon-ok">edit</a>
    <a href="javascript:void(0)" class="easyui-linkbutton" onclick="javascript:$('#thongtinnguoidung').dialog('close')" iconCls="icon-cancel">Hủy</a>
</div>  

<?php include "../../footer/footer.php" ?>