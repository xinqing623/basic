function addTabs(title, url,icon){
	if($("#main_tabs").tabs("exists", title)){
		$('#main_tabs').tabs('select', title);
	}else{
		var content = '<iframe scrolling="auto" frameborder="0"  src="'+url+'" style="width:100%;height:98%;"></iframe>';
		$('#main_tabs').tabs('add',{
            title:title,
            //content:content,
            iconCls:icon,
            closable:true,
            href:url
        });
	}
}


Date.prototype.Format = function(fmt)     
{  
    var o = {     
        "M+" : this.getMonth()+1,                 //月份     
        "d+" : this.getDate(),                    //日     
        "h+" : this.getHours(),                   //小时     
        "m+" : this.getMinutes(),                 //分     
        "s+" : this.getSeconds(),                 //秒     
        "q+" : Math.floor((this.getMonth()+3)/3), //季度     
        "S"  : this.getMilliseconds()             //毫秒     
    };     
    if(/(y+)/.test(fmt)){  
        fmt = fmt.replace(RegExp.$1, (this.getFullYear()+"").substr(4 - RegExp.$1.length));  
    }  
    for(var k in o){  
        if(new RegExp("("+ k +")").test(fmt)){  
            fmt = fmt.replace(RegExp.$1, (RegExp.$1.length==1) ? (o[k]) : (("00"+ o[k]).substr((""+ o[k]).length)));  
        }  
    }  
    return fmt;     
}

function dateFormatter(value, row, index){
	if(!!value ){
		var date = new Date(value);
		return date.Format("yyyy-MM-dd hh:mm:ss");
	}
	return "";
}

function openDialog(mTitle, url, mWidth, mHeight){
	openDialog(mTitle, url, mWidth, mHeight, "icon-add");
}


function openDialog(mTitle, url, mWidth, mHeight, icon){
	$('#dlg').dialog({
        title: mTitle,
        href: url,
        iconCls: icon,
        collapsible: true,
        minimizable: true,
        maximizable: true,
        resizable: true,
        width: mWidth,
        height: mHeight,
        modal: true,
        buttons: [{
        	id:"dialog_save_btn",
            text: '确定',
            iconCls: 'icon-ok',
        }, 
        {
        	id:'dialog_cancel_btn',
            text: '取消',
            iconCls: 'icon-cancel',
            handler: function () {
                $('#dlg').dialog('close');
            }
        }]
    });
}


function ajaxDelete(confirmMsg, url, callBack){
	$.messager.confirm("提示", confirmMsg ,function(isTrue){
		if(isTrue){
			$.ajax({
				url: url,
				type:"get",
				dataType:"json",
				success:function(data){
					if(data.success){
						$.messager.show({
		                    title: '提示',
		                    msg: data.message
		                });
						callBack();
					}else{
						$.messager.show({
		                    title: '提示',
		                    msg: data.message
		                });
					}
				}
			});
		}
	})
}

var cachedDicJson = [];

function renderFromDic(code,dicName){
	var txt = "";
	
	return txt;
}




function ajaxWithoutRemind(url, callBack){
	$.ajax({
		url: url,
		type:"get",
		dataType:"json",
		success:function(data){
			if(data.success){
				$.messager.show({
                    title: '提示',
                    msg: data.message
                });
				if(callBack){
					callBack();
				}
			}else{
				$.messager.show({
                    title: '提示',
                    msg: data.message
                });
			}
		}
	});
}

function showAlert(content){
	$.messager.alert("提示", content ,"info");
}



