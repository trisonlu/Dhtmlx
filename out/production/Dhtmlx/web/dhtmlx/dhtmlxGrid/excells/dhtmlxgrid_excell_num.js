/**
 * For Example :mygrid.setColTypes("num"),mygrid.IntegerCol=true;只能输入整数mygrid.DeceimalCol=true;只能输入浮点数
 * */
function eXcell_num(cell) {
	if (!cell) {
		return;
	}
	this.cell = cell;
     this.oldVal;
	this.grid = cell.parentNode.grid;
	this.edit=function(){
         this.oldVal=this.getValue();
	//创建textarea元素
			this.textareaObj=document.createElement("textarea");
			this.textareaObj.style.height=(this.cell.offsetHeight-2)+"px";
			this.textareaObj.style.width= this.cell.offsetWidth +"px";
			this.textareaObj.className="dhx_combo_edit";
			this.textareaObj.value = this.getValue()||"";
			this.textareaObj.wrap="soft";
			this.textareaObj.style.textAlign=this.cell.style.textAlign;
			this.textareaObj.onclick=function(e){
				(e||event).cancelBubble=true
			}
			this.textareaObj.onmousedown=function(e){
				(e||event).cancelBubble=true
			}
			this.cell.innerHTML="";
			this.cell.appendChild(this.textareaObj);
	}
	this.detach=function(){
		this.cell.val=this.textareaObj.value;
		this.setValue(this.cell.val);
		return this.val != this.getValue();
	}
	this.getValue=function(){
		return this.cell.val;
	}
	this.setValue = function (val,aSynchronizeLen) {
          if(aSynchronizeLen){
                    this.setCValue(val,val);return;
          }
		var reg = /^\d+$/;//正整数		var reg2=/(^(0\.)(\d*)$)|(^([1-9])+\d*(\.\d*)?$)/;//浮点数		var isInteger=reg.test(val);
		var isDece=reg2.test(val);
		if(isInteger || isDece){
			if(this.grid.IntegerCol){//取整数				val=parseInt(val);
			}else if(this.grid.DeceimalCol){//取小数				if(!isInteger){
					val=parseInt(val*100)/100;
				}
			}
           var rowId=this.grid.getRowIndex(this.cell.parentNode.idd);
          var cellId=this.cell._cellIndex;
          var resTypeId=this.grid.cellByIndex(rowId,cellId+1).getValue().resTypeId;
          var resObjectId=this.grid.cellByIndex(rowId,cellId+1).getValue().objId;
          if(resTypeId&&resObjectId){
               var propertiesVO = callRemoteFunctionNoTrans("com.goodyou.busi.component.dynamicpage.service.PropertiesService",
                                    "getPropertiesData", resTypeId, resObjectId, "1", "1",null,null,"dynamicProperty");
            props = propertiesVO.propertiesData;
           putPropertyValueByFieldName("LENGTH",val);
         saveProps(resTypeId);
          }

		}else{
           alert(mygrid.msg||"输入值非法！");
			val=this.oldVal;
		}
        val=Number(val);
		this.cell.val=val;
		this.setCValue(val,val);
	};
	this.setFormat=function(type){
		this.cell.format=type;
	}
}
eXcell_num.prototype = new eXcell;

