 <?php include "../../header/header.php" ?>
 <script type="text/javascript" src="../js/nguoidung.js"></script>
<script src="../../js/js/application.js" type="text/javascript" charset="utf-8"></script>
  <div>
      <table id="dg_nguoidung" class="easyui-datagrid" title="Quản Lý Người Dùng" style="width:1120px;height:500px"
            data-options="singleSelect:true,collapsible:true,url:'../model/taidulieunguoidung.php',pagination:true,toolbar:'#toolbar_nguoidung'">
        <thead>
            <tr>
                <th data-options="field:'id_NgDung',width:80" >idND</th>
                <th data-options="field:'ma_NgDung',width:200" >Tài Khoản</th>
                <th data-options="field:'ten_NgDung',width:100,align:'right'">ten NgDung</th>
                <th data-options="field:'gioi_Tinh',width:300,align:'right'">GioiTinh</th>
				<th data-options="field:'queQuan',width:300,align:'right'">Que Quan</th>
				<th data-options="field:'id_BMon',width:300,align:'right'">id_BMon</th>	 
				<th data-options="field:'matKhau',width:300,align:'right'">Mật Khẩu</th>	 
            </tr>
        </thead>
    </table>
  </div>
  <!--Toolbar datagrid-->
  <div id="toolbar_nguoidung">
    <div>
      <span class="easyui-linkbutton" iconCls="icon-add" onclick="themnd()">Thêm </span>
      <span class="easyui-linkbutton"  iconCls="icon-remove" onclick="xoand(id)">Xóa </span>
      <span class="easyui-linkbutton" iconCls="icon-edit" onclick="editngdung()">sửa </span>
       <input id="filter" type="text" value="Tìm kiếm..." onClick="if(this.value=='Tìm kiếm...')this.value='';" onBlur="if(this.value=='')this.value='Tìm kiếm...';" name="filter" /> 
    </div>
  </div>
   <!--dialog them thong tin nguoi dung-->
   <div id="thongtinnguoidung" class="easyui-dialog" style="width:470px;height:fit-content;padding:10px 20px"
        closed="true" buttons="#nguoidung-buttons"" >
    <div class="ftitle" style="text-align:center">Thông Tin nguoi dung</div>
    <form id="frm_thongtinnguoidung" method="">
       <div class="fitem">
           
            <input label="Tài Khoản" labelPosition="top" name="" required="true" id="txt_mand" class="easyui-textbox" style="width:260px" >
        </div>

        <div class="fitem">
            
            <input name=""  label="Tên người dùng:" labelPosition="top" required="true" id="txt_tennd" class="easyui-textbox" style="width:260px" >
        </div>
		<div class="fitem">
            <select label="Giới tính" labelPosition="top" class="easyui-combobox"  style="width:260px" id="check_gioiTinh" >
              <option value="nam">
                Nam
              </option>
              <option  value="Nu">
                Nữ
              </option>
            </select>
        </div>
        
        <div class="fitem">
           
            <input label="Quê quán:" labelPosition="top" name="" required="true" id="txt_quequan" class="easyui-textbox" style="width:260px" >
        </div>
		<div class="fitem">
           <select label="id Khoa" labelPosition="top" class="easyui-combobox"  style="width:260px" id="check_khoa" >    
					
					<?php 
					include "../../ketnoicsdl/ketnoiCSDL.php";
					$thucthi=$con->query("select id_BMon from bomon");
						while($row=mysqli_fetch_array($thucthi)){
								echo '<option>'.$row[0].'</option>';
						}	
					?>
				
				</select>
        </div>
        <div class="fitem">
           
            <input label="Mật Khẩu:" labelPosition="top" name="" required="true" id="txt_mk" class="easyui-textbox" style="width:260px" >
        </div>
    </form>
	
</div>
<!--botton-->
 
<div id="nguoidung-buttons">

  			<a href="javascript:void(0)" class="easyui-linkbutton c6"  iconCls="icon-ok"id="themnd" style="width:90px">Save</a> 
			<a href="javascript:void(0)" class="easyui-linkbutton c6"  iconCls="icon-ok"id="suand" style="width:90px">edit</a> 
			<a href="javascript:void(0)" class="easyui-linkbutton" onclick="javascript:$('#thongtinnguoidung').dialog('close')" iconCls="icon-cancel">Hủy</a>
</div> 
<?php include "../../footer/footer.php" ?>