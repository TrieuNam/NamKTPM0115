 <?php include "../../header/header.php" ?>
 <script type="text/javascript" src="../js/nguoidung.js"></script>

  <div>
      <table id="dg_bomon" class="easyui-datagrid" title="Basic DataGrid" style="width:580px;height:250px"
            data-options="singleSelect:true,collapsible:true,url:'../model/taidulieudethi.php',pagination:true,toolbar:'#toolbar_nguoidung'">
        <thead>
            <tr>
                <th data-options="field:'id_DeThi',width:80" >id Đề Thi</th>
                <th data-options="field:'tieuDe',width:280" >Tiêu đề</th>
                <th data-options="field:'GhiChu',width:100,align:'right'">Ghi chú</th>
                <th data-options="field:'id_NguoiDung',width:100,align:'right'">id Người dùng</th>
                <th data-options="field:'id_monHoc',width:100,align:'right'">id Môn học</th>
				
				
            </tr>
        </thead>
    </table>
  </div>
  <!--Toolbar datagrid-->
  <div id="toolbar_nguoidung">
    <div>
      <span class="easyui-linkbutton" iconCls="icon-add" onclick="thembomon()">Thêm </span>
      <span class="easyui-linkbutton"  iconCls="icon-remove" onclick="xoabm(id)">Xóa </span>
      <span class="easyui-linkbutton" iconCls="icon-edit" onclick="editUser()">sửa </span>
      <input  type="text" class="easyui-textbox"  id="chonidu" name="" style="width:250px;height:30px">
    </div>
  </div>
   <!--dialog them thong tin nguoi dung-->
   <div id="thongtinnguoidung" class="easyui-dialog" style="width:445;height:fit-content;padding:10px 20px"
        closed="true" buttons="#nguoidung-buttons" >
    <div class="ftitle" style="text-align:center">Thông Tin Bộ môn</div>
    <form id="thongtinnguoidung" method="">    
        <div class="fitem">           
            <input name=""  label="Tiêu đề:" labelPosition="top" required="true" id="txt_tenbomon" class="easyui-textbox" style="width:260px" >
        </div>
		<div class="fitem">           
            <input name=""  label="Ghi chú:" labelPosition="top" required="true" id="txt_tenbomon" class="easyui-textbox" style="width:260px" >
        </div>
		<div class="fitem">
            <select label="id Khoa" labelPosition="top" class="easyui-combobox"  style="width:260px" id="check_khoa" >    
				<option value="nam">
				<?php 
				include "../../ketnoicsdl/ketnoiCSDL.php";
				$thucthi=$con->query("select Id_khoa from khoa");
					while($row=mysqli_fetch_array($thucthi)){
						echo $row[0];
					}	
				?>
              </option>
            </select>
        </div>      
    </form>
</div>
<!--botton-->
<div id="nguoidung-buttons">
  <a href="javascript:void(0)" class="easyui-linkbutton" id="thembomon" iconCls="icon-ok">Thêm</a>
    <a href="javascript:void(0)" class="easyui-linkbutton" onclick="javascript:$('#thongtinnguoidung').dialog('close')" iconCls="icon-cancel">Hủy</a>
</div>  

<?php include "../../footer/footer.php" ?>