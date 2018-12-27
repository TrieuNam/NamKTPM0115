function themnd() {
	$("#suand").hide();
	$("#themnd").show();
	$("#suathongtinnd").hide();
	$("#thongtinnguoidung").dialog("open");
	$("#themnd").click(function(){
		alert("a");
		$mand=$("#txt_mand").textbox("getValue");
		$tennd=$("#txt_tennd").textbox("getValue");
		$gioitinh=$("#check_gioiTinh").combobox("getValue");
		$quequan=$("#txt_quequan").textbox("getValue");
		$txt_mk=$("#txt_mk").textbox("getValue");
		$bomon=$("#txt_boMon").textbox("getValue");
		var bien={mand:$mand,tennd:$tennd,gioitinh:$gioitinh,quequan:$quequan,bomon:$bomon,txt_mk:$txt_mk};
		sendajax("../model/them.php",bien,"dg_nguoidung");
		$("#thongtinnguoidung").dialog("close");
	})
}

function xoand(){
	 var row = $('#dg_nguoidung').datagrid('getSelected');
    var url = '../model/xoa.php';
    if(row){
            $.messager.confirm('Confirm','Are you sure you want to destroy this user?',function(r){
            if (r){          
				$id_NgDung=row.id_NgDung;
				var bien={id_NgDung:$id_NgDung};
                sendajax("../model/xoa.php",bien,"dg_nguoidung");
            }
        });
    }
}
function editngdung(){
var row = $('#dg_nguoidung').datagrid('getSelected');
$("#suand").show();
$("#themnd").hide();
			if (row){
				
			    $('#thongtinnguoidung').dialog('open').dialog('setTitle','quan Ly bo mon');
			    $('#frm_thongtinnguoidung').form('load',row);
				$("#suand").click(function(){
					$mand=$("#txt_mand").textbox("getValue");
					$tennd=$("#txt_tennd").textbox("getValue");
					$gioitinh=$("#check_gioiTinh").combobox("getValue");
					$quequan=$("#txt_quequan").textbox("getValue");
					$bomon=$("#txt_boMon").textbox("getValue");
					$id_NgDung=row.id_NgDung;
				var bien={mand:$mand,tennd:$tennd,gioitinh:$gioitinh,quequan:$quequan,bomon:$bomon,id_NgDung:$id_NgDung};
					sendajax("../model/editngdung.php",bien,"dg_bomon");
					$("#thongtinnguoidung").dialog("close");
				})
				
				
			}
else{
	 alert("Bạn chưa chọn bộ môn")
}
}
function xoabm(){
	 var row = $('#dg_bomon').datagrid('getSelected');
    var url = '../model/xoa.php';
    if(row){
            $.messager.confirm('Confirm','Are you sure you want to destroy this user?',function(r){
            if (r){
				$id_bomon=row.id_BMon;
				var bien={id_bm:$id_bomon};
                sendajax("../model/xoabomon.php",bien,"dg_bomon");
            }
        });
    }
}

function thembomon(){
	$("#thembomon").show();
	$("#thongtinnguoidung").dialog("open");
	$("#thembomon").click(function(){
		$tenbm=$("#txt_tenbomon").textbox("getValue");
		$id_khoa=$("#check_khoa").combobox("getValue");
		var bien={tenbm:$tenbm,id_khoa:$id_khoa};
		sendajax("../model/them.php",bien,"dg_bomon");
		$("#thongtinnguoidung").dialog("close");
	})
}

function themkhoa(){
	$("#dg_khoa").show();
	$("#suakhoa").hide();
	$("#themkhoa").show();
	$("#thongtinkhoa").dialog("open");
	$("#themkhoa").click(function(){
		$tenkh=$("#txt_khoa").textbox("getValue");
		var bien={tenkh:$tenkh};
		sendajax("../model/them.php",bien,"dg_khoa");
		$("#thongtinkhoa").dialog("close");
	})
}
function editkhoa(){
var row = $('#dg_khoa').datagrid('getSelected');
$("#suakhoa").show();
$("#themkhoa").hide();
			if (row){
			    $('#thongtinkhoa').dialog('open').dialog('setTitle','quan Ly bo mon');
			    $('#frm_thongtinkhoa').form('load',row);
				$("#suakhoa").click(function(){
					$ten_Khoa=$("#txt_khoa").textbox("getValue");
					$Id_khoa=row.Id_khoa;
					var bien={ten_Khoa:$ten_Khoa,Id_khoa:$Id_khoa};
					sendajax("../model/editthongtinkhoa.php",bien,"dg_khoa");
					$("#thongtinkhoa").dialog("close");
				})					
			}
else{
	 alert("Bạn chưa chọn bộ môn")
}
}
function xoakhoa(){
	 var row = $('#dg_khoa').datagrid('getSelected');
    var url = '../model/xoa.php';
    if(row){
            $.messager.confirm('Confirm','Are you sure you want to destroy this user?',function(r){
            if (r){
				$Id_khoa=row.Id_khoa;
				var bien={Id_khoa:$Id_khoa};
                sendajax("../model/xoakhoa.php",bien,"dg_khoa");
            }
        });
    }
}

function editbomon(){
var row = $('#dg_bomon').datagrid('getSelected');
$("#suabomon").show();
$("#thembomon").hide();
			if (row){
			    $('#thongtinnguoidung').dialog('open').dialog('setTitle','quan Ly bo mon');
			    $('#frm_thongtinnguoidung').form('load',row);
				$("#suabomon").click(function(){
					$tenbm=$("#txt_tenbomon").textbox("getValue");
					$id_khoa=$("#check_khoa").combobox("getValue");
					$id_bomon=row.id_BMon;
					var bien={tenbm:$tenbm,id_khoa:$id_khoa,id_bm:$id_bomon};
					sendajax("../model/edit.php",bien,"dg_bomon");
					$("#thongtinnguoidung").dialog("close");
				})					
			}
else{
	 alert("Bạn chưa chọn bộ môn")
}
}


function themdethi(){
	$("#suadethi").hide();
	
	$("#themdethi").show();
	$("#thongtinnguoidung").dialog("open");
	$("#themdethi").click(function(){
		
		$tieude=$("#txt_tieude").textbox("getValue");
		$ghichu=$("#txt_ghichu").textbox("getValue");
		$id_NgDung=$("#check_nguoidung").combobox("getValue");
		$id_monhoc=$("#check_monhoc").combobox("getValue");
		var bien={tieude:$tieude,ghichu:$ghichu,id_NgDung:$id_NgDung,id_monhoc:$id_monhoc};
		sendajax("../model/them.php",bien,"dg_taodethi");
		$("#thongtinnguoidung").dialog("close");
	})
}
function xoadethi(){
	 var row = $('#dg_taodethi').datagrid('getSelected');
    var url = '../model/xoa.php';
    if(row){
            $.messager.confirm('Confirm','Are you sure you want to destroy this user?',function(r){
            if (r){
               $id_DeThi=row.id_DeThi;
				var bien={id_DeThi:$id_DeThi};
                sendajax("../model/xoadethi.php",bien,"dg_taodethi");
            }
        });
    }
}
function editdethi(){
var row = $('#dg_taodethi').datagrid('getSelected');
$("#suadethi").show();
$("#themdethi").hide();
			if (row){
			    $('#thongtinnguoidung').dialog('open').dialog('setTitle','quan Ly bo mon');
			    $('#frm_thongtinnguoidung').form('load',row);
				$("#suadethi").click(function(){
				$tieude=$("#txt_tieude").textbox("getValue");
					$ghichu=$("#txt_ghichu").textbox("getValue");
					$id_NgDung=$("#check_nguoidung").combobox("getValue");
					$id_monhoc=$("#check_monhoc").combobox("getValue");
					 $id_DeThi=row.id_DeThi;
					var bien={tieude:$tieude,ghichu:$ghichu,id_NgDung:$id_NgDung,id_monhoc:$id_monhoc,id_DeThi:$id_DeThi};
					sendajax("../model/editdethi.php",bien,"dg_taodethi");
					$("#thongtinnguoidung").dialog("close");
							})					
			}
else{
	 alert("Bạn chưa chọn bộ môn")
}
}


function themmonhoc() {
	$("#suand").hide();
	$("#themmonhoc").show();
	$("#suamonhoc").hide();
	$("#thongtinmonhoc").dialog("open");
	$("#themmonhoc").click(function(){
		$monhoc=$("#txt_mon").textbox("getValue");	
		var bien={monhoc:$monhoc};
		sendajax("../model/them.php",bien,"dg_monhoc");
		$("#thongtinmonhoc").dialog("close");
	})
}

function xoamonhoc(){
	 var row = $('#dg_monhoc').datagrid('getSelected');
    var url = '../model/xoamonhoc.php';
    if(row){
            $.messager.confirm('Confirm','Are you sure you want to destroy this user?',function(r){
            if (r){          
				$id_mon=row.id_mon;
				var bien={id_mon:$id_mon};
                sendajax("../model/xoamonhoc.php",bien,"dg_monhoc");
            }
        });
    }
}
function editmonhoc(){
var row = $('#dg_monhoc').datagrid('getSelected');
$("#suamonhoc").show();
$("#themmonhoc").hide();
			if (row){
				
			    $('#thongtinmonhoc').dialog('open').dialog('setTitle','quản lý môn học');
			    $('#frm_thongtinmonhoc').form('load',row);
				$("#suamonhoc").click(function(){
					$monhoc=$("#txt_mon").textbox("getValue");	
					$id_mon=row.id_mon;
				var bien={monhoc:$monhoc,id_mon:$id_mon};
					sendajax("../model/editmonhoc.php",bien,"dg_monhoc");
					$("#thongtinmonhoc").dialog("close");
				})
				
				
			}
else{
	 alert("Bạn chưa chọn bộ môn")
}
}
/*
function xoadethi(){
	var row = $('#dg_taodethi').datagrid('getSelected');
	
	 xoadulieuajax("../model/xoa.php",{id_DeThi:row.id_DeThi},"dg_taodethi");
}*/
