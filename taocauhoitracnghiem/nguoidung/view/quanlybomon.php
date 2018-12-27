 <?php include "../../header/header.php" ?>
 <script type="text/javascript" src="../js/nguoidung.js"></script>
 <script src="../../js/js/application.js" type="text/javascript" charset="utf-8"></script>

<div class="easyui-layout" style="width:1120px;height:500px;">
  <div data-options="region:'east',split:true" title="Khoa" style="width:366px;height:250px">
	 <table id="dg_khoa" class="easyui-datagrid"  style="width:250px;height:250px"
            data-options="border:false,singleSelect:true,fit:true,fitColumns:true,url:'../model/taidulieukhoa.php',pagination:true,toolbar:'#toolbar_nguoidung2'">
        <thead>
            <tr>              
                <th data-options="field:'Id_khoa',width:100,align:'right'">id_Khoa</th>
			  <th data-options="field:'ten_Khoa',width:280" >Tên Khoa</th>

            </tr>
        </thead>
    </table>
  </div>
   <div data-options="region:'north',split:true" title="Môn học" style="width:366px;height:250px">
	 <table id="dg_monhoc" class="easyui-datagrid"  style="width:250px;height:250px"
            data-options="border:false,singleSelect:true,fit:true,fitColumns:true,url:'../model/taidulieumonhoc.php',pagination:true,toolbar:'#toolbar_nguoidung3'">
        <thead>
            <tr>              
                <th data-options="field:'id_mon',width:100,align:'right'">id Môn học</th>
			  <th data-options="field:'ten_MonHoc',width:280" >Tên môn</th>

            </tr>
        </thead>
    </table>
  </div>
  <div data-options="region:'center',title:'quản lý bộ môn',iconCls:'icon-ok'">
      <table id="dg_bomon" class="easyui-datagrid"  style="width:752px;height:250px"
            data-options="border:false,singleSelect:true,fit:true,fitColumns:true,url:'../model/taidulieubomo.php',pagination:true,toolbar:'#toolbar_nguoidung'">
        <thead>
            <tr>
                <th data-options="field:'id_BMon',width:100" >id_BMon</th>
                <th data-options="field:'ten_BMon',width:280" >Tên bộ môn</th>
                <th data-options="field:'id_Khoa',width:100,align:'right'">id_Khoa</th>
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
      <span class="easyui-linkbutton" iconCls="icon-edit" onclick="editbomon()">sửa </span>
             <input id="filter" type="text" value="Tìm kiếm..." onClick="if(this.value=='Tìm kiếm...')this.value='';" onBlur="if(this.value=='')this.value='Tìm kiếm...';" name="filter" /> 
    </div>
  </div>
  <!--Toolbar datagrid-->
  <div id="toolbar_nguoidung2">
    <div>
      <span class="easyui-linkbutton" iconCls="icon-add" onclick="themkhoa()">Thêm </span>
      <span class="easyui-linkbutton"  iconCls="icon-remove" onclick="xoakhoa()">Xóa </span>
      <span class="easyui-linkbutton" iconCls="icon-edit" onclick="editkhoa()">sửa </span>
             <input id="filter1" type="text" value="Tìm kiếm..." onClick="if(this.value=='Tìm kiếm...')this.value='';" onBlur="if(this.value=='')this.value='Tìm kiếm...';" name="filter1" /> 
    </div>
  </div>
   <!--Toolbar datagrid-->
  <div id="toolbar_nguoidung3">
    <div>
      <span class="easyui-linkbutton" iconCls="icon-add" onclick="themmonhoc()">Thêm </span>
      <span class="easyui-linkbutton"  iconCls="icon-remove" onclick="xoamonhoc()">Xóa </span>
      <span class="easyui-linkbutton" iconCls="icon-edit" onclick="editmonhoc()">sửa </span>
             <input id="filter" type="text" value="Tìm kiếm..." onClick="if(this.value=='Tìm kiếm...')this.value='';" onBlur="if(this.value=='')this.value='Tìm kiếm...';" name="filter" /> 
    </div>
  </div>
   <!--dialog them thong tin nguoi dung-->
   <div id="thongtinnguoidung" class="easyui-dialog" style="width:445px;height:fit-content;padding:10px 20px"
        closed="true" >
		<div class="ftitle" style="text-align:center">Thông Tin Bộ môn</div>
		<form id="frm_thongtinnguoidung" method="" action="">    
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
		</form>
		<div id="nguoidung-buttons">
			<a href="javascript:void(0)" class="easyui-linkbutton c6"  iconCls="icon-ok"id="thembomon" style="width:90px">Save</a> 
			<a href="javascript:void(0)" class="easyui-linkbutton c6"  iconCls="icon-ok"id="suabomon" style="width:90px">edit</a> 
			<a href="javascript:void(0)" class="easyui-linkbutton" onclick="javascript:$('#thongtinnguoidung').dialog('close')" iconCls="icon-cancel">Hủy</a>
		</div>
	</div>
<!--botton-->
<!--dialog them thong tin nguoi dung-->
   <div id="thongtinkhoa" class="easyui-dialog" style="width:445px;height:fit-content;padding:10px 20px"
        closed="true"  >
    <div class="ftitle" style="text-align:center">Thông Tin khoa</div>
    <form id="frm_thongtinkhoa" method="" >    
        <div class="fitem">           
            <input name=""  label="Tên khoa:" labelPosition="top" required="true" id="txt_khoa" class="easyui-textbox" style="width:260px" >
        </div>	
	<div id="nguoidung-buttons">
    <a href="javascript:void(0)" class="easyui-linkbutton c6"  iconCls="icon-ok"id="themkhoa" style="width:90px">Save</a> 
	<a href="javascript:void(0)" class="easyui-linkbutton c6"  iconCls="icon-ok"id="suakhoa" style="width:90px">edit</a> 
	<a href="javascript:void(0)" class="easyui-linkbutton" onclick="javascript:$('#thongtinnguoidung').dialog('close')" iconCls="icon-cancel">Hủy</a>
	</div>  		
    </form>
</div>
<!--botton-->
<!--dialog them thong tin nguoi dung-->
   <div id="thongtinmonhoc" class="easyui-dialog" style="width:445px;height:fit-content;padding:10px 20px"
        closed="true"  >
    <div class="ftitle" style="text-align:center">Thông Tin môn học</div>
    <form id="frm_thongtinmonhoc" method="" >    
        <div class="fitem">           
            <input name=""  label="Tên môn:" labelPosition="top" required="true" id="txt_mon" class="easyui-textbox" style="width:260px" >
        </div>	
	<div id="nguoidung-buttons">
    <a href="javascript:void(0)" class="easyui-linkbutton c6"  iconCls="icon-ok"id="themmonhoc" style="width:90px">Save</a> 
	<a href="javascript:void(0)" class="easyui-linkbutton c6"  iconCls="icon-ok"id="suamonhoc" style="width:90px">edit</a> 
	<a href="javascript:void(0)" class="easyui-linkbutton" onclick="javascript:$('#thongtinnguoidung').dialog('close')" iconCls="icon-cancel">Hủy</a>
	</div>  		
    </form>
</div>

<?php include "../../footer/footer.php" ?>