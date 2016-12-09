function eXcell_pop(cell) {
	if (!cell) {
		return;
	}
	this.cell = cell;
	this.grid = cell.parentNode.grid;
	this.edit = function() {
		if (_isIE) {
			//创建textarea元素
			this.textareaObj = document.createElement("textarea");
			this.textareaObj.setAttribute("autocomplete", "off");
			this.textareaObj.style.height = (this.cell.offsetHeight - (_isIE
					? 2
					: 2))
					+ "px";
			this.textareaObj.style.width = this.cell.offsetWidth * 0.8 + "px";
			this.textareaObj.className = "dhx_combo_edit";
			this.textareaObj.value = this.cell.objName || "";
			this.textareaObj.readOnly = true;
			this.textareaObj.wrap = "soft";
			this.textareaObj.style.textAlign = this.cell.style.textAlign;
			//this.textareaObj.style.padding-bottom = "2px";
			this.textareaObj.onclick = function(e) {
				(e || event).cancelBubble = true
			}
			this.textareaObj.onmousedown = function(e) {
				(e || event).cancelBubble = true
			}
			//创建img元素
			this.imgObj = document.createElement("img");
			this.imgObj.style.height = (this.cell.offsetHeight - (_isIE ? 2 : 2))
					+ "px";
			this.imgObj.style.width = (_isIE ? 20 : 20) + "px";
			this.imgObj.src = "component/dhtmlxGrid/imgs/edit_link.jpg";
			var othis = this;
			this.imgObj.onclick = function() {
				othis.tempEdit();
			}
			this.cell.innerHTML = "";
			this.cell.appendChild(this.textareaObj);
			this.cell.appendChild(this.imgObj);
		}

	};
	this.detach = function() {
		//将input的值赋给cell
		this.setCValue(this.cell.objName || "", this.val);
		return this.val != this.getValue();
	}
	this.tempEdit = function() {
		var _relObj = this.grid.relObj[this.cell._cellIndex];//relations
		var rowId = this.grid.row.rowIndex - 1;
		var isShow = true;
		this.cell.operate_type = 0;//0增1删2改
		//		if (_relObj.isPipe) {//判断是否为资源所属资源
		//			this.isPipe(_relObj,rowId);
		//		} else {
		resId = this.grid.cellByIndex(rowId, _relObj.type_index).getValue();
		//		}
		this.copyResId = resId;//被复制使用copyResDataToOther
		var obj = Object.clone(this.grid.resObj[resId]);//resource
		if (obj) {
			var resObj = _relObj.isPipe ? Object.clone(obj.pipeRes) : Object
					.clone(obj.commonRes);
			if (_relObj.onlyNew)
				resObj = Object.clone(obj.lineRes);
			var newObj = Object.clone(resObj.newObj);
			if (newObj.resTypeId == '511') {//标石获得直埋系统
				newObj.buriedId = this.grid.cellByIndex(rowId, 2).cell.objId
						|| "";
			}
			if (_relObj.onlyNew) {//若只新增，一般为线资源

				if (this.cell.objId && this.cell.objId != "") {
					newObj.isNew = false;
					newObj.resObjectId = this.cell.objId;
				}
				newObj = this.newLine(_relObj, rowId, resObj);
				if (!newObj.proVal) {
					isShow = false;
				}
			}
			this.cell.tableName = resObj.tableName;//保存资源的表名
			this.cell.keyWord = resObj.key;//资源表的主键名
			this.key = resObj.key;//js中资源对象ID
			this.keyVal = resObj.keyVal;//显示在页面中的字段名

			if (this.grid.cellByIndex(rowId, _relObj.new_index).getValue() == 1
					|| _relObj.onlyNew) {//新增或修改资源
				if (this.cell.objId && this.cell.objId != ""
						&& this.cell.oldResId == resId) {//如果已存在资源，并且资源类型一致，则只能修改
					newObj.isNew = false;
					newObj.resObjectId = this.cell.objId;
					this.cell.operate_type = 2;
				}
				if (isShow) {//控制是否可以编辑
					//光交接箱，分纤箱，odf使用模板
					if (newObj.isNew
							&& (newObj.resTypeId == '302'
									|| newObj.resTypeId == '703' || newObj.resTypeId == '704')) {//调用模板创建
						var pResId = '205';//父资源类型Id，默认为odf的父资源
						var _queryId = '9435';//父资源查询ID，默认为odf的父资源查询ID
						if (newObj.resTypeId == '703'
								|| newObj.resTypeId == '704') {
							pResId = '200';
							_queryId = '9274';
						}
						this.returnVal = templateCreateResource(newObj, pResId,
								_queryId);//common.js方法中
					} else {
						if (_relObj.isPipe) {
							var returnObj;
							if (newObj.isNew) {
								returnObj = PipePole_AddObject(
										newObj.resTypeId, newObj.proVal, woId);
							} else
								returnObj = PipePole_EditObject(
										newObj.resTypeId, newObj.resObjectId,
										newObj.proVal, woId);
							if (returnObj) {
								this.returnVal = new Object();
                              
								this.returnVal["saveXML"] = returnObj["saveXML"];
								for (var i = 0; i < returnObj.length; i++)
									this.returnVal[returnObj[i].field_name] = returnObj[i].field_value;
                                  /*同步长度*/
                                  var rowId=this.grid.getRowIndex(this.cell.parentNode.idd);
                                   var cellId=this.cell._cellIndex;
                                 this.grid.cellByIndex(rowId,cellId-1).setValue(this.returnVal["LENGTH"],true);
							}
						} else {//新增或编辑点资源
							newObj.woId = woId;
                             newObj.proVal=new Array();
		                    var area = new Object();
		                     newObj.proVal.push(area);
                            var _x=new Object();
                            newObj.proVal.push(_x);
                            var _y=new Object();
                            newObj.proVal.push(_y);
		                     area.field_name = "AREA_ID";
		                     area.field_value = areaId;
		                     //所属电信管理区
		                     var tml = new Object();
		                     newObj.proVal.push(tml);
		                     tml.field_name = "TML_ID";
		                     tml.field_value = tmlId;
		                     
                           _x.field_name = "LONGITUDE";
                               _x.field_value = this.grid.cellByIndex(rowId, _relObj.x).getValue();
                               _y.field_name = "LATITUDE";
                               _y.field_value = this.grid.cellByIndex(rowId, _relObj.y).getValue();
                           var returnObj;
                                   if (newObj.isNew) {
                                        returnObj = PipePole_AddObject(
                                                  newObj.resTypeId, newObj.proVal, woId);
                                   } else
                                        returnObj = PipePole_EditObject(
                                                  newObj.resTypeId, newObj.resObjectId,
                                                  newObj.proVal, woId);
                                   if (returnObj) {
                                        this.returnVal = new Object();
                                        this.returnVal["saveXML"] = returnObj["saveXML"];
                                        for (var i = 0; i < returnObj.length; i++)
                                             this.returnVal[returnObj[i].field_name] = returnObj[i].field_value;
                                   }
//							this.returnVal = showPropertyDialog(
//									"component/dynamicPage/dynamicProperty/projectpro.jsp",
//									newObj, 310, 500);
						}
					}
				}
				if (newObj.isNew && this.returnVal) {//将新增数据加到全局变量中,以备删除垃圾数据用

					this.cell.resTypeId = newObj.resTypeId;
					this.cell.notToLog = false;
					this.afterAdd(this.cell.tableName, this.cell.keyWord,
							this.returnVal[this.key], this.cell.resTypeId);
					this.cell.startProBar = true;//显示进度条
					showProBar();
					if (typeof(_relObj.type_index) == "object") {//生成同所属资源或线资源                    if (rowId < this.grid.getRowsNum() - 1 && _relObj.resCopy
                                   && !_relObj.allCopy
                                   && confirm('是否其他同类资源以此模式同样创建? ') == true) {
                              this.data = this.returnVal.data;
                              var incre = 1;
							var tem1 = this.grid.cellByIndex(rowId,
									_relObj.type_index[0]).getValue();
							var tem2 = this.grid.cellByIndex(rowId,
									_relObj.type_index[1]).getValue();
							for (var i = rowId + 1; i < this.grid.getRowsNum(); i++) {
								var res1 = this.grid.cellByIndex(i,
										_relObj.type_index[0]).getValue();
								var res2 = this.grid.cellByIndex(i,
										_relObj.type_index[1]).getValue();
								var otherCell = this.grid.cellByIndex(i,
										this.cell._cellIndex);
								if (tem1 == res1
										&& tem2 == res2
										&& (!otherCell.cell.objId || otherCell.cell.objId == "")) {//资源类型相同，并且要拷贝的目的地是空值
									if (_relObj.type && _relObj.type == "line") {//线资源
										this.copyLineDataToOther(
												newObj.resTypeId, this.key,
												this.keyVal, incre++, i,
												_relObj, Object
														.clone(this.returnVal),
												this.returnVal.saveXML,
												otherCell)
									}
								}
							}
						}
                         }else {//生成同资源                         if (rowId <= this.grid.getRowsNum() - 1 && _relObj.resCopy
                                   && !_relObj.allCopy
                                   && confirm('是否其他同类资源以此模式同样创建? ') == true) {
                              this.data = this.returnVal.data;
                              var incre = 1;
							var index = _relObj.type_index;
							var tem1 = this.grid.cellByIndex(rowId, index)
									.getValue();
							var i = rowId + 1;
							if (_relObj.copyTo) {
								index = _relObj.copyTo;
								i = rowId;
							}
							for (; i < this.grid.getRowsNum(); i++) {
								var res1 = this.grid.cellByIndex(i, index)
										.getValue();
								var otherCell = this.grid.cellByIndex(i, index
												+ 1);
								if (tem1 == res1
										&& (!otherCell.cell.objId || otherCell.cell.objId == "")&&!otherCell.cell.theSameFieldNum) {
									this.copyResDataToOther(newObj.resTypeId,
											this.key, this.keyVal, incre++,
											Object.clone(this.returnVal), this.returnVal.saveXML,
											otherCell);
								}
							}
						}
					}
				}
			} else {//查询获得资源
                  if (isShow) {
					this.cell.notToLog = true;//不写日志
					this.returnVal = window
							.showModalDialog(
									"component/dynamicPage/dynamicQuery/modalQuery.jsp",
									resObj.queryObj, "dialogWidth:" + 800
											+ "px;dialogHeight:" + 630
											+ "px;help:no");
					if (this.returnVal){
						this.returnVal = this.returnVal[0];
                        this.returnVal.resTypeId=resObj.newObj.resTypeId;
                             if(confirm('是否同步资源的坐标信息? ') == true) {
                                   var propertiesVO = callRemoteFunctionNoTrans("com.goodyou.busi.component.dynamicpage.service.PropertiesService",
                                    "getPropertiesData", resId, this.returnVal[this.key], "1", "1",null,null,"dynamicProperty");
							    props = propertiesVO.propertiesData;
                                putPropertyValueByFieldName("LONGITUDE",this.grid.cellByIndex(rowId, _relObj.x).getValue());
                                putPropertyValueByFieldName("LATITUDE",this.grid.cellByIndex(rowId, _relObj.y).getValue());
                               if(!saveProps(resId)){
                                   alert("自动同步坐标失败，请手工同步！");
                               }
                             }
                      }
				}
			}
		}
		if (this.returnVal) {
			this.returnVal.objId = this.returnVal[this.key]
					|| this.returnVal["A_" + this.key];
			this.returnVal.objName = this.returnVal[this.keyVal]
					|| this.returnVal["A_" + this.keyVal];
			this.returnVal.oldResId = resId;
			this.returnVal.tableName = this.cell.tableName;
			this.returnVal.keyWord = this.cell.keyWord;
			this.setValue(this.returnVal);
		}
		if (_relObj.allCopy) {
			var tem1 = this.grid.cellByIndex(rowId, _relObj.type_index[0])
					.getValue();
			for (var i = rowId + 1; i < this.grid.getRowsNum(); i++) {
				var otherCell = this.grid.cellByIndex(i, this.cell._cellIndex);
				otherCell.isPipe(_relObj, i);
				if (this.cell.isPipe == otherCell.cell.isPipe
						&& (!otherCell.cell.objId || otherCell.cell.objId == "")) {//资源类型相同，并且要拷贝的目的地是空值
					otherCell.notToLog = true;
					otherCell.setValue(this.returnVal);
				}
			}
		}
	}
	this.getValue = function() {
		return this.cell;
	};
	this.setValue = function(val) {
		if (this.cell.startProBar) {//关闭进度条			closeProBar();
		}
		if (typeof(val) == "object") {
            if(val.objId){
			this.cell.objId = val.objId;
            if(!val.objName){
               val.objName=callRemoteFunctionNoTrans(
                              "com.goodyou.busi.module.pipeline.service.enteringpiple.PipeSectService",
                              "getBseEqpNo",val.objId);
            }
			this.cell.objName = val.objName;
			this.cell.oldResId = val.oldResId;//资源类型ID，判断是否更换资源类型，如果更换，可以新增，否则只能修改
			this.cell.tableName = val.tableName;
			this.cell.keyWord = val.keyWord;
               this.setCValue(this.cell.objName, val);
            }
               if(val.sameFieldNum)
            this.cell.theSameFieldNum=val.sameFieldNum;
			if (val.resTypeId) {
				this.cell.resTypeId = val.resTypeId;
			}
			
			var rowId = this.grid.getRowIndex(this.cell.parentNode.idd);
			var _relObj = this.grid.relObj[this.cell._cellIndex];//relations
			if (_relObj.x && val["X"]) {//经度
				this.grid.cellByIndex(rowId, _relObj.x).setValue(val["X"]);
				this.cell.X = val["X"];
			}
			if (_relObj.y && val["Y"]) {//纬度
				this.grid.cellByIndex(rowId, _relObj.y).setValue(val["Y"]);
				this.cell.Y = val["Y"];
			}
			if (!this.cell.notToLog) {//写入日志
			//			writeLog(this.cell.operate_type,this.cell.resTypeId,this.cell.objId);
			}
               if(this.grid.row){
            for(var i=0;i<this.grid.getRowsNum();i++){
              var _otherCell=this.grid.cellByIndex(i,3).getValue();
               if(_otherCell.theSameFieldNum){
                    if(_otherCell.theSameFieldNum.split("-")[0]==(this.grid.row.rowIndex-1)&&_otherCell.theSameFieldNum.split("-")[1]==this.cell._cellIndex){
                       this.grid.cellByIndex(i, 3).setValue(this.cell);  
                    }
               }    
               _otherCell=this.grid.cellByIndex(i,7).getValue();
                if(_otherCell.theSameFieldNum){
                    if(_otherCell.theSameFieldNum.split("-")[0]==(this.grid.row.rowIndex-1)&&_otherCell.theSameFieldNum.split("-")[1]==this.cell._cellIndex){
                       this.grid.cellByIndex(i, 7).setValue(this.cell);  
                    }
               } 
               }
              }
             if(this.cell.theSameFieldNum){
               var _otherCell=this.grid.cellByIndex(this.cell.theSameFieldNum.split("-")[0],this.cell.theSameFieldNum.split("-")[1]).getValue();
               if(_otherCell.objId){
               this.cell.objId = _otherCell.objId;
               this.cell.objName = _otherCell.objName;
               this.cell.oldResId = _otherCell.oldResId;//资源类型ID，判断是否更换资源类型，如果更换，可以新增，否则只能修改
               this.cell.tableName = _otherCell.tableName;
               this.cell.keyWord = _otherCell.keyWord;
               this.setCValue(this.cell.objName, val);
               if (_otherCell.resTypeId) {
                    this.cell.resTypeId = _otherCell.resTypeId;
               }
               }
             }
			if (_relObj.copy) {
				istrue = false;
				for (var i = 0; i < _relObj.src.length; i++) {
					if (this.cell._cellIndex == _relObj.src[i]) {//是拷贝的数据源列
						istrue = true;
						break;
					}
				}
				if (istrue && rowId + 1 < this.grid.getRowsNum()) {//复制到下一个资源
					for (var i = 0; i < _relObj.src.length; i++) {
						this.grid.cellByIndex(rowId + 1, _relObj.dest[i])
								.setValue(this.grid.cellByIndex(rowId,
										_relObj.src[i]).getValue());
					}
				}
			}
		}
	};
	this.afterAdd = function(tabName, keyW, keyV, _resTypeId) {
		var newData = new Object();
		newData.tableName = tabName;//
		newData.resTypeId = _resTypeId;
		newData.keyWord = keyW;//
		newData.keyVal = keyV;//
		newArr.push(newData);
	};
	this.copyResDataToOther = function(_resTypeId, objId, objName, incremental,
			_data, _xml, otherCell) {
		resTypeId = _resTypeId;
		isNew = true;
		var _relObj = this.grid.relObj[otherCell.cell._cellIndex];//relations
          var rowId = otherCell.cell.parentNode.idd;
		var id = callRemoteFunctionNoTrans(
				"com.goodyou.busi.component.dynamicpage.service.PropertiesService",
				"getResKeyId", _resTypeId);
//		props = _data;
		var val = _data[objName];//获取编号
       var numPos= getLastNumPos(val+"");
          var a1 = val.substr(0,numPos);
          var a2 = val.substring(numPos);//数字部分

          if (numPos == val.length) {//如果没有数字
               val=val + incremental;
          } else {
            var incrNum=Number(a2)+incremental;

            if((incrNum+"").length>=(a2+"").length){
               a2=incrNum;
            }else{
                 a2 = a2.substr(0,(a2+"").length-(incrNum+"").length)+"" + incrNum;
            }
              val=a1+""+a2;
          }
		var doc = makeDOM();
		if (doc.loadXML(_xml)) {
			doc.documentElement.childNodes[0].childNodes[1].text = id;
			_xml = doc.xml;
			doc = null;
		}
		debugger;
		var noCount = callRemoteFunctionNoTrans(
                "com.goodyou.busi.module.pipeline.service.enteringpiple.PipeSectService",
                "getBseEqpNoByNo",val,resTypeId,areaId);
		if(parseInt(noCount) > 0){
			alert("名称 "+val+ " 在数据库中已存在,请单独再建！");
			return;
		}
          _data[objId]=id;
          _data[objName]=val;
//		putPropertyValueByFieldName(objId, id);//更新id
//		putPropertyValueByFieldName(objName, val);//更新编号
//		putPropertyValueByFieldName("NAME", val);//更新名称
//        putPropertyValueByFieldName("LONGITUDE", otherCell.grid.cellByIndex(rowId, _relObj.x).getValue());//更新名称
//        putPropertyValueByFieldName("LATITUDE", otherCell.grid.cellByIndex(rowId, _relObj.y).getValue());//更新名称
		_xml = setXMLValue(_xml, objId, id);
		_xml = setXMLValue(_xml, objName, val);
		_xml = setXMLValue(_xml, "NAME", val);
        _xml = setXMLValue(_xml, "LONGITUDE",otherCell.grid.cellById(rowId, _relObj.x).getValue());
        _xml = setXMLValue(_xml, "LATITUDE",otherCell.grid.cellById(rowId, _relObj.y).getValue());
        if (batchSave(_xml)) {//保存
			var dataMap = new Object();
//			var dataList = getObjectData();
//			for (var i = 0; i < dataList.length; i++) {
//				var obj = dataList[i];
//				dataMap[obj.tableFieldName] = obj.propertyValue;
//			}
			dataMap.objId = _data[objId];
			dataMap.objName = _data[objName];
			dataMap.oldResId = this.copyResId;
			dataMap.tableName = this.cell.tableName;
			dataMap.keyWord = this.cell.keyWord;
			otherCell.cell.operate_type = 0;
			otherCell.cell.resTypeId = this.cell.resTypeId;
			otherCell.cell.notToLog = false;
			otherCell.setValue(dataMap);
			otherCell.afterAdd(this.cell.tableName, this.cell.keyWord,
					dataMap.objId, this.cell.resTypeId);
		}
	};
     /*找出最后为数字的位置*/
   function getLastNumPos(src){
          var regNum =/^\d$/;
          var pos=src.length;
            if(src.length==0) return 0;
          if(regNum.test(src.substring(pos-1))){
               pos=getLastNumPos(src.substr(0,pos-1))
            }
               return pos;
    }
	function setXMLValue(_xml, sField, val, objType) {
          if (!objType)
               objType = "s";//字段类型，s:String,i:int,o:object
          var doc = makeDOM();
          if (doc.loadXML(_xml)) {
               var oItems = doc.documentElement.childNodes[0].childNodes[2].childNodes;
               for (var i = 0; i < oItems.length; i++) {
            	   if(oItems[i].getElementsByTagName("stableFieldName")[0] != null){
                       if (oItems[i].getElementsByTagName("stableFieldName")[0].text == sField
                                 .toUpperCase()) {
	   	                    try{//如果此类型报错，默认使用s类型
	   	                         oItems[i].getElementsByTagName(objType + "propertyValue")[0].text = val;
	   	                    }catch(e){
	   	                       oItems[i].getElementsByTagName("spropertyValue")[0].text = val;  
	   	                    }
                           break;
                       }
                   }
               }
          }
          return doc.xml;
     }
	function batchSave(_xml) {
		var URL = getContext() + "/component/busiFacade.spr";
		var xmlHttp = new ActiveXObject("Microsoft.XmlHttp");
		try {
			xmlHttp.open("POST", URL, false);
			xmlHttp.send(_xml);
			if (xmlHttp.status != 200) {
				throw new Error(0, "Network issue or remote server issue");
				return false;
			} else
				return xmlHttp.responseText;

		} finally {
			retVal = xmlHttp = null;
		}
	}
	this.newLine = function(_relObj, rowId, resObj) {
		proVal = [];
		val = [];
		var sectNo = "";//段编码暂时为起点+终点
		var i = 0;
		for (; i < _relObj.relCol.length; i++) {//为道段赋值，顺序为，起始资源类型，起始资源ID，终止资源类型，终止资源ID，其他
			proVal.push(new Object());
			val.push("");
			var _obj = this.grid.cellByIndex(rowId, _relObj.relCol[i]);
			switch (i) {
				case 1 ://起点ID
					if (_obj.cell.objId)
						this.existRes1 = true;
				case 3 ://终点ID
					if (_obj.cell.objId) {
						this.existRes2 = true;
					} else {
						this.existRes2 = false;
					}
					if (_obj
							&& _obj.cell.oldResId == this.grid.cellByIndex(
									rowId, _relObj.relCol[i - 1]).getValue()) {//判断资源类型与设施类型是否相同
						proVal[i].field_name = _relObj.pipeField[i];
						proVal[i].field_value = _obj.cell.objId;
						proVal[i].field_no = _obj.cell.objName;
						val[i] = _obj.cell.objName;
						if (sectNo == "")
							sectNo = _obj.cell.objName;
						else
							sectNo = sectNo + "-" + _obj.cell.objName;
					}
					break;
				case 4 ://长度
					var l = _obj.getValue();
					if (!l || l == "") {
						l = 0;
					}
					proVal[i].field_name = _relObj.pipeField[i];
					proVal[i].field_value = l;
					val[i] = l;
					break;
				default ://起点类型，终点类型

					if (_obj) {
						proVal[i].field_name = _relObj.pipeField[i];
						proVal[i].field_value = _obj.getValue();
						val[i] = _obj.getValue();
					}
					break;
			}
		}
		proVal.push(new Object());
		proVal[i].field_name = "NO";
		proVal[i].field_value = sectNo;
		i++;
		proVal.push(new Object());
		proVal[i].field_name = "NAME";
		proVal[i].field_value = sectNo;
          if(areaId){
		var area = new Object();
		area.field_name = "AREA_ID";
		area.field_value = areaId;
		proVal.push(area);
          }
          if(tmlId){
        	  //所属电信管理区
              var tml = new Object();
              tml.field_name = "TML_ID";
              tml.field_value = tmlId;
              proVal.push(tml);
          }
		var newObj = Object.clone(resObj.newObj);
		if (this.existRes1 && this.existRes2) {//若起始，终止资源ID存在，则可以创建资源
			this.isPipe(_relObj, rowId);
			newObj.resTypeId = this.cell.isPipe;
			newObj.proVal = proVal;
			newObj.val = val;
		}
		return newObj;
	}
	this.copyLineDataToOther = function(_resTypeId, objId, objName,
			incremental, rowId, _relObj, _data, _xml, otherCell) {
		var tempResObj = new Object();
		tempResObj.newObj = new Object();
		tempResObj.newObj = this.newLine(_relObj, rowId, tempResObj);
		if (tempResObj.newObj.proVal) {
			resTypeId = _resTypeId;
			isNew = true;
			var id = callRemoteFunctionNoTrans(
					"com.goodyou.busi.component.dynamicpage.service.PropertiesService",
					"getResKeyId", _resTypeId);
			var val = _data[objName];//获取编号
			var beginNo, endNo, len;
			for (var i = 0; i < proVal.length; i++) {
				if (proVal[i].field_name == "SECT_POINT_1")
					beginNo = proVal[i].field_no;
				if (proVal[i].field_name == "SECT_POINT_2")
					endNo = proVal[i].field_no;
				if (proVal[i].field_name == "LENGTH")
					len = proVal[i].field_value;
			}
			val = beginNo + "-" + endNo;
			_data[objId] = id;
			_data[objName] = val;

			_xml = setXMLValue(_xml, objId, id);
			_xml = setXMLValue(_xml, objName, val);
			_xml = setXMLValue(_xml, "name", val);
			if (IsNumberInt(val))
				_xml = setXMLValue(_xml, "LENGTH", len, "i");
			else
				_xml = setXMLValue(_xml, "LENGTH", len, "f");

			var doc = makeDOM();
			if (doc.loadXML(_xml)) {
				doc.documentElement.childNodes[0].childNodes[1].text = id;
				_xml = doc.xml;
				doc = null;
			}
			if (batchSaveLine(_xml, _resTypeId, id, tempResObj.newObj.proVal)) {//保存
				var dataMap = new Object();
				dataMap.objId = _data[objId];
				dataMap.objName = _data[objName];
				dataMap.oldResId = this.copyResId;
				dataMap.tableName = this.cell.tableName;
				dataMap.keyWord = this.cell.keyWord;
				otherCell.cell.operate_type = 0;
				otherCell.cell.resTypeId = this.cell.resTypeId;
				otherCell.cell.notToLog = false;
				otherCell.setValue(dataMap);
				otherCell.afterAdd(this.cell.tableName, this.cell.keyWord,
						dataMap.objId, this.cell.resTypeId);
			}
		}
	};
	this.isPipe = function(_relObj, rowId) {
		this.cell.isPipe = false;
		if (this.grid.resObj.from) {
			var typeIndex = _relObj.type_index;
			resId0 = inPipeSectArr(this.grid.cellByIndex(rowId, typeIndex[0])
					.getValue());
			resId1 = inPipeSectArr(this.grid.cellByIndex(rowId, typeIndex[1])
					.getValue());

			if (resId0 && resId1) {
				this.cell.isPipe = "501";//管道段

			} else if (!resId0 && !resId1) {
				this.cell.isPipe = "608";//吊线段

			} else {
				this.cell.isPipe = "517";//引上段

			}
		}
	}
	function inPipeSectArr(id) {
		id = id + "";
		var flag = true;
		switch (id) {
			case '205' ://机房
				break;
			case '506' :
				break;//地下进线室

			case '508' :
				break;//人井
			case '516' :
				break;//手井
			default :
				flag = false;
				break;
		}
		return flag;
	}
	this.isDisabled = function() {
		/*	var _relObj = this.grid.relObj[this.cell._cellIndex];//relations
			if (_relObj.disabled) {
				if (this.grid.row.rowIndex - 1 >= _relObj.disabledIndex) {
					return true;
				}
			}*/
		return false;
	};
}
function batchSaveLine(_xml, iResTypeID, strResId, proVal) {
	var bIfSuccess = false;
	var rtFieldValue = new Array();
	var beginTypeId, endTypeId, endId, beginId;
	var g_strContextPath = getContext();
	var _batchSave = function(_xml) {
		var URL = getContext() + "/component/busiFacade.spr";
		var xmlHttp = new ActiveXObject("Microsoft.XmlHttp");
		try {
			xmlHttp.open("POST", URL, false);
			xmlHttp.send(_xml);
			if (xmlHttp.status != 200) {
				throw new Error(0, "Network issue or remote server issue");
				return false;
			} else
				return xmlHttp.responseText;

		} finally {
			retVal = xmlHttp = null;
		}
	}
	for (var i = 0; i < proVal.length; i++) {
		if (proVal[i].field_name == "SECT_POINT_TYPE_1")
			beginTypeId = proVal[i].field_value;
		if (proVal[i].field_name == "SECT_POINT_1")
			beginId = proVal[i].field_value;
		if (proVal[i].field_name == "SECT_POINT_TYPE_2")
			endTypeId = proVal[i].field_value;
		if (proVal[i].field_name == "SECT_POINT_2")
			endId = proVal[i].field_value;
	}
	//江苏电信， 段资源的 端点信息判断
	if (iResTypeID == 501 || iResTypeID == 514 || iResTypeID == 517
			|| iResTypeID == 608) {
		var bResult = 0;
		var callCheckResult = function(loader) {
			var result = JSON.parse(loader.xmlDoc.responseText);
			if (!result) {
				alert("检查段的端点信息错误，数据返回为 null");
				bResult = -1;
				return;
			} else if (result.errorFlag < 0) {
				alert("段端点信息错误：" + result.errorDesc);
				bResult = -1;
				return;
			}
			bResult = 1;
		}
		var postURL = g_strContextPath + "/pipepole.spr?method=sectPointBuiz";
		var param = "operation=checkSectPoints&id=" + strResId + "&resTypeId="
				+ iResTypeID+ "&woId="+woId;
		param = param + "&beginId=" + beginId + "&beginTypeId=" + beginTypeId
				+ "&endId=" + endId + "&endTypeId=" + endTypeId;
		var _loader = dhtmlxAjax.postSync(postURL, param);
		callCheckResult(_loader);
		if (bResult < 0) { //端点信息检查错误

			return;
		} else if (bResult == 0) {
			alert("段端点信息检查失败，无法保存");
			return;
		}
	}
	bIfSuccess = _batchSave(_xml);
	if (bIfSuccess) {
		//段信息保存完成后，需要保存 2端端点信息

		//如果是管道段或者引上段------自动添加一个虚拟孔
		if (iResTypeID == 501 || iResTypeID == 517)
			PipePole_AddVirHoleByPipeOrUpSectAuto(strResId,woId);
		else if (iResTypeID == 608)
			PipePole_AddSubWireByWireSectAuto(strResId,woId);

		//江苏电信， 段资源的 端点信息保存      
		if (iResTypeID == 501 || iResTypeID == 514 || iResTypeID == 517
				|| iResTypeID == 608) {
			var bSectPointsSaveResult = 1;
			var saveSectPoints = function(loader) {
				var result = JSON.parse(loader.xmlDoc.responseText);
				if (!result) {
					alert("保存端点信息发生错误，数据返回为 null");
					bSectPointsSaveResult = -1;
					return;
				} else if (result.errorFlag < 0) {
					alert("保存端点信息发生错误：" + result.errorDesc);
					bSectPointsSaveResult = -1;
					return;
				}

				bSectPointsSaveResult = 1;
			}
			var postURL = g_strContextPath
					+ "/pipepole.spr?method=sectPointBuiz";
			var param = "operation=saveSectPoints&id=" + strResId
					+ "&resTypeId=" + iResTypeID+ "&woId="+woId;
			param = param + "&beginId=" + beginId + "&beginTypeId="
					+ beginTypeId + "&endId=" + endId + "&endTypeId="
					+ endTypeId;
			var _l = dhtmlxAjax.postSync(postURL, param, saveSectPoints);
			saveSectPoints(_l);
			if (bSectPointsSaveResult <= 0) {
				alert("段信息保存成功，但是端点信息保存失败");
			}
		}
	} else { // 失败
		alert("操作失败，请联系管理员");
		rtFieldValue = null;
		return null;
	}
	return rtFieldValue;

}
function saveProps(resTypeId) {
     var actionModel = new Object();
     actionModel.resTypeId = resTypeId;
     var pros = new Array();
     for (var i = 0; i < props.length; i++) {
          var o=new Object();
          if (props[i].keyFieldFlag == "1") {
               resObjectId = props[i].propertyValue;
          }
          if (isNew == true) {
               if (props[i].displayStyle != "d") {
                    props[i].propertyValue = props[i].propertyValue == null
                              ? ""
                              : props[i].propertyValue;
                    pros[pros.length] = props[i];
                    o.field=props[i].tableFieldName;
                    o.fieldValue=props[i].propertyValue;
               }
          } else {
               if (props[i].displayStyle != "d" && props[i].propertyValue != null) {
                    pros[pros.length] = props[i];
                    o.field=props[i].tableFieldName;
                    o.fieldValue=props[i].propertyValue;
               }
          }
     }
    
     actionModel.resObjectId = resObjectId;
     actionModel.params = pros;
    actionModel.newWoId=woId;
     var rtnVO = callRemoteFunctionNoTrans(
               "com.goodyou.busi.component.dynamicpage.service.PropertiesService",
               "insertOrUpdateResObject", actionModel, false);
     if (rtnVO.iReturnFlag == 0) {
          return true;
     } else {
          return false;
     }
}
eXcell_pop.prototype = new eXcell;
