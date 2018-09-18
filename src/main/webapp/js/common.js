$(function(){
//为全选绑定点击事件
	
	$("#checkAll").click(function(){
		var boxes = $("input[id^='box_']");
	
		var flag = this.checked;
	
		boxes.prop("checked",flag);
	//
    //$("tr[id]").trigger(flag?"mouseover":"mouseout");
	   $("tr[id]").trigger(flag?"mouseover":"mouseout");
	  
	});
	 //为数据行绑定  mouseover以及mouseout事件    或者用 hover
	 $("tr[id^='dataTr_']").hover(function(){
		 //颜色还有pointer
         $(this).css("backgroundColor","#eeccff").css("cursor","pointer");
	 },function(){
		//mouseout事件     如果数据行对应的checkbox是选中的那么背景色不去掉，否则可以去掉
	      
		 //获取数据行id
		 var trId = this.id;
		 
		var boxId= trId.replace("dataTr","box");
		var flag= $("#"+boxId).prop("checked");
		//flag!=true
		if(!flag){
			 $(this).css("backgroundColor","");
		}
		
	 }).click(function(){
		   //为数据行绑定点击事件 ,点击tr的时候，让当前行的checkbox状态取反
		     var trId = this.id;
		  //获取数据行id
		 	var boxId= trId.replace("dataTr","box");
		 	$("#"+boxId).prop("checked",!$("#"+boxId).prop("checked"));
		 	
		 	//获取 所有boxes的打钩的数量状态
		 	var boxeschecked = $("input[id^='box_']").filter(":checked");
		 	var boxes=$("input[id^='box_']");
		 	//判断选中的与全部checkbox的个数
		 	$("#checkAll").prop("checked",boxeschecked.length==boxes.length);
		 
	 });
	 //为所有的子checkbox绑定事件
	 $("input[id^='box_']").click(function(event){
		 var checkedBoxes = $("input[id^='box_']").filter(":checked");
		 //获取所有的checkbox的
         var boxes = $("input[id^='box_']");

		 //判断选中的与全部checkbox的个数
         $("#checkAll").prop("checked",checkedBoxes.length == boxes.length);

		 //取消掉 checkbox的默认事件行为
	      event.stopPropagation();
	 });
	 //获取状态信息
	 var msg1 =sessionStorage.getItem('msg');
	 if(msg1!= null && msg1.length > 3){
			$.messager.show({
				title:'提示信息',
				msg:"<font color='red'>"+ msg1+"</font>",
				showType:'show'
			});
			sessionStorage.clear();
	 }
 	
	
})

