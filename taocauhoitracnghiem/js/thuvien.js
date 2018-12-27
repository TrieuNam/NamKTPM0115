function sendajax(url,bien,datagrid) {
	$.post(url,{
		thamso:bien
	},
	function (data) {
		if (data) {
			$.messager.show({title: 'Thong bao',
							msg: data});
			$("#"+datagrid).datagrid("reload");
		}

	})
}

function thongbao(bien){
	$.messager.show({
		title: 'Thong bao',
		msg: bien,
		showType:'fade',
		style:{
			right:'',
			bottom:''
		}
	});
}
function xoadulieuajax(url,bien,datagrid){
	$.messager.confirm('Confim','alo nghe khong',function(r){
		if(r){
			$.post(url,{
		thamso:bien
	},
		function (data) {
						if (data) {
						$.messager.show({title: 'Thong bao',
							msg: data});
						$("#"+datagrid).datagrid("reload");
				}

			})
		}
	})
}





