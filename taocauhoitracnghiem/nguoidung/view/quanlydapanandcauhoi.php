 <?php include "../../header/header.php" ?>
 <script type="text/javascript" src="../js/nguoidung.js"></script>
<div class="easyui-layout" style="width:1000px;height:350px;">
  <div data-options="region:'east',split:true" title="East" style="width:450px;height:250px">
	 <table id="dg_khoa" class="easyui-datagrid"  style="width:250px;height:250px"
            data-options="border:false,singleSelect:true,fit:true,fitColumns:true,url:'../model/taidulieucauhoi.php',pagination:true,toolbar:'#toolbar_nguoidung2'">
        <thead>
            <tr>              
                <th data-options="field:'id_CauHoi',width:100,align:'right'">id_CauHoi</th>
			  <th data-options="field:'NoiDung',width:380" >NoiDung </th>
			  <th data-options="field:'DoKho',width:100" > DoKho</th>

            </tr>
        </thead>
    </table>
  </div>
  <div data-options="region:'center',title:'quản lý bộ môn',iconCls:'icon-ok'">
      <table id="dg_bomon" class="easyui-datagrid"  style="width:580px;height:250px"
            data-options="border:false,singleSelect:true,fit:true,fitColumns:true,url:'../model/taidulieudapan.php',pagination:true,toolbar:'#toolbar_nguoidung'">
        <thead>
            <tr>
                <th data-options="field:'id_DapAn',width:80" >id_DapAn</th>
                <th data-options="field:'noiDung',width:280" >noiDung</th>
                <th data-options="field:'id_CauHoi',width:100,align:'right'">id_CauHoi</th>
                <th data-options="field:'Dap_An',width:100,align:'right'">Dap_An</th>
            </tr>
        </thead>
    </table>
  </div>
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
  <!--Toolbar datagrid-->
  <div id="toolbar_nguoidung2">
    <div>
      <span class="easyui-linkbutton" iconCls="icon-add" onclick="themkhoa()">Thêm </span>
      <span class="easyui-linkbutton"  iconCls="icon-remove" onclick="xoak(id)">Xóa </span>
      <span class="easyui-linkbutton" iconCls="icon-edit" onclick="editUser()">sửa </span>
      <input  type="text" class="easyui-textbox"  id="chonidu2" name="" style="width:120px;height:30px">
    </div>
  </div>
   <!--dialog them thong tin nguoi dung-->
   <div id="thongtinnguoidung" class="easyui-dialog" style="width:445;height:fit-content;padding:10px 20px"
        closed="true" >
    <div class="ftitle" style="text-align:center">Thông Tin Bộ môn</div>
    <form id="thongtinnguoidung" method="">    
        <div class="fitem">
            
            <input name=""  label="Tên bộ môn:" labelPosition="top" required="true" id="txt_tenbomon" class="easyui-textbox" style="width:260px" >
        </div>
		<div class="fitem">
            <select label="id Khoa" labelPosition="top" class="easyui-combobox"  style="width:260px" id="check_khoa" >    
				
				<?php 
				include "../../ketnoicsdl/ketnoiCSDL.php";
				$thucthi=$con->query("select Id_khoa from khoa");
					while($row=mysqli_fetch_array($thucthi)){
							echo '<option>'.$row[0].'</option>';
					}	
				?>
            
            </select>
        </div>     
<div id="nguoidung-buttons">
    <a href="javascript:void(0)" class="easyui-linkbutton c6" iconCls="icon-ok"id="thembomon" style="width:90px">Save</a>
 
    <a href="javascript:void(0)" class="easyui-linkbutton" onclick="javascript:$('#thongtinnguoidung').dialog('close')" iconCls="icon-cancel">Hủy</a>
	</div>  		
    </form>
</div>
<!--botton-->
<!--dialog them thong tin nguoi dung-->
   <div id="thongtinkhoa" class="easyui-dialog" style="width:445;height:fit-content;padding:10px 20px"
        closed="true"  >
    <div class="ftitle" style="text-align:center">Thông Tin Bộ môn</div>
    <form id="thongtinkhoa" method="">    
        <div class="fitem">           
            <input name=""  label="Tên khoa:" labelPosition="top" required="true" id="txt_khoa" class="easyui-textbox" style="width:260px" >
        </div>	
	<div id="nguoidung-buttons">
    <a href="javascript:void(0)" class="easyui-linkbutton c6" iconCls="icon-ok"id="thembomon" style="width:90px">Save</a>
 
    <a href="javascript:void(0)" class="easyui-linkbutton" onclick="javascript:$('#thongtinnguoidung').dialog('close')" iconCls="icon-cancel">Hủy</a>
	</div>  		
    </form>
</div>
<!--botton-->


<?php include "../../footer/footer.php" ?>