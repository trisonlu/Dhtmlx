//v.2.5 build 91111

/*
Copyright DHTMLX LTD. http://www.dhtmlx.com
To use this component please contact sales@dhtmlx.com to obtain license
*/
/**
*   @desc: enable smart paging mode
*   @type: public
*   @param: fl - true|false - enable|disable mode
*   @param: pageSize - count of rows per page
*   @param: pagesInGrp - count of visible page selectors
*   @param: parentObj - ID or container which will be used for showing paging controls
*   @param: showRecInfo - true|false - enable|disable showing of additional info about paging state
*   @param: recInfoParentObj - ID or container which will be used for showing paging state
*   @edition: Professional
*   @topic: 0
*/
dhtmlXGridObject.prototype.enablePaging = function(fl,pageSize,pagesInGrp,parentObj,showRecInfo,recInfoParentObj){
 		this._pgn_parentObj = typeof(parentObj)=="string" ? document.getElementById(parentObj) : parentObj;
         this._pgn_parentId=this._pgn_parentObj.id;
		this._pgn_recInfoParentObj = typeof(recInfoParentObj)=="string" ? document.getElementById(recInfoParentObj) : recInfoParentObj;
		
		this.pagingOn = fl;
		this.showRecInfo = showRecInfo;
		this.rowsBufferOutSize = parseInt(pageSize);
		this.currentPage = 1;
		this.pagesInGroup = parseInt(pagesInGrp);
		this._init_pgn_events();
		this.setPagingSkin("default");
		this.setPagingDisplay("default");
}
/**
*   @desc: allow to configure settings of dynamical paging
*   @type: public
*   @param: filePath - path which will be used for requesting data ( parth from load command used by default )
*   @param: buffer -  count of rows requrested from server by single operation, optional
*   @edition: Professional
*   @topic: 0
*/
dhtmlXGridObject.prototype.setXMLAutoLoading = function(filePath,bufferSize){
      this.xmlFileUrl = filePath;
      this.reloadXMLurl=filePath;
      this._dpref = bufferSize;
}
/**
*   @desc: change current page in grid
*   @type: public
*   @param: ind - correction ( -1,1,2  etc) to current active page
*   @edition: Professional
*   @topic: 0
*/
dhtmlXGridObject.prototype.changePageRelative = function(ind){ 
	this.changePage(this.currentPage+ind);
}
/**
*   @desc: change current page in grid
*   @type: public
*   @param: pageNum -  new active page
*   @edition: Professional
*   @topic: 0
*/
dhtmlXGridObject.prototype.changePage = function(pageNum){ 
	if (arguments.length==0) pageNum=this.currentPage||0;
	pageNum=parseInt(pageNum);
	pageNum=Math.max(1,Math.min(pageNum,Math.ceil(this.rowCount/this.rowsBufferOutSize)));
	
	if(!this.callEvent("onBeforePageChanged",[this.currentPage,pageNum]))
		return;
		
	this.currentPage = parseInt(pageNum);
	this._reset_view();
	this._fixAlterCss();			
	this.callEvent("onPageChanged",this.getStateOfView());
}
dhtmlXGridObject.prototype.dynaChange=function(pageNum){
	if (arguments.length==0) pageNum=this.currentPage||0;
	pageNum=parseInt(pageNum);
	pageNum=Math.max(1,Math.min(pageNum,Math.ceil(this.rowCount/this.rowsBufferOutSize)));
	this.currentPage = parseInt(pageNum);
	if(this.xmlFileUrl){
		this.clearAll();
		this.rowsBuffer=dhtmlxArray();
		this.currentPage = parseInt(pageNum);
		this.loadXML(this.reloadXMLurl+"curr="+pageNum+"&len="+this.rowsBufferOutSize+"&count="+this.rowCount + "&pagingDisplay=" + this.pagingDisplay);
	}			
	//this.changePage(1);
	this.callEvent("onPageChanged",this.getStateOfView());
}
/**
	*   @desc: allows to set custom paging skin
	*	@param: name - skin name (default,toolbar,bricks)
	*   @type:  public
*/
dhtmlXGridObject.prototype.setPagingSkin = function(name){
	this._pgn_skin=this["_pgn_"+name];
	if (name=="toolbar") this._pgn_skin_tlb=arguments[1];
}
/**
	*   @desc: allows to set paging templates for default skin
	*	@param: a - template for zone A
	*	@param: b - template for zone B
	*   @type:  public
*/
dhtmlXGridObject.prototype.setPagingTemplates = function(a,b){
	this._pgn_templateA=this._pgn_template_compile(a);
	this._pgn_templateB=this._pgn_template_compile(b);
	this._page_skin_update();
}
dhtmlXGridObject.prototype._page_skin_update = function(name){
	if (!this.pagesInGroup) this.pagesInGroup=Math.ceil(Math.min(5,this.rowCount/this.rowsBufferOutSize));
	var totalPages=Math.ceil(this.rowCount/this.rowsBufferOutSize);
	if (totalPages && totalPages<this.currentPage)
		return this.changePage(totalPages);
	if (this.pagingOn && this._pgn_skin)this._pgn_skin.apply(this,this.getStateOfView());
}
dhtmlXGridObject.prototype._init_pgn_events = function(name){
     myAttachEvent(this,"onXLE",this._page_skin_update);
     myAttachEvent(this,"onClearAll",this._page_skin_update);
     myAttachEvent(this,"onPageChanged",this._page_skin_update);
     myAttachEvent(this,"onGridReconstructed",this._page_skin_update);
	//this.attachEvent("onXLE",this._page_skin_update)
	//this.attachEvent("onClearAll",this._page_skin_update)
	//this.attachEvent("onPageChanged",this._page_skin_update)
	//this.attachEvent("onGridReconstructed",this._page_skin_update)
	
	this._init_pgn_events=function(){};
}

// default paging
dhtmlXGridObject.prototype._pgn_default=function(page,start,end){
	if (!this.pagingBlock){
		this.pagingBlock = document.createElement("DIV");
		this.pagingBlock.className = "pagingBlock";
		this.recordInfoBlock = document.createElement("SPAN");
		this.recordInfoBlock.className = "recordsInfoBlock";
		if (!this._pgn_parentObj) return;
			this._pgn_parentObj.appendChild(this.pagingBlock)
		if(this._pgn_recInfoParentObj && this.showRecInfo)
			this._pgn_recInfoParentObj.appendChild(this.recordInfoBlock)
		
		//this._pgn_template="{prev:} {current:-1},{current},{current:+1} {next:>}"
		if (!this._pgn_templateA){
			this._pgn_templateA=this._pgn_template_compile("[prevpages:&lt;:&nbsp;] [currentpages:,&nbsp;] [nextpages:&gt;:&nbsp;]");
			this._pgn_templateB=this._pgn_template_compile("Results <b>[from]-[to]</b> of <b>[total]</b>");
		}
	}
	
	var details=this.getStateOfView();
	this.pagingBlock.innerHTML = this._pgn_templateA.apply(this,details);
	this.recordInfoBlock.innerHTML = this._pgn_templateB.apply(this,details);
	this._pgn_template_active(this.pagingBlock);
	this._pgn_template_active(this.recordInfoBlock);
	
	this.callEvent("onPaging",[]);
}

dhtmlXGridObject.prototype._pgn_block=function(sep){ 
	var start=Math.floor((this.currentPage-1)/this.pagesInGroup)*this.pagesInGroup;
	var max=Math.min(Math.ceil(this.rowsBuffer.length/this.rowsBufferOutSize),start+this.pagesInGroup);
	var str=[];
	for (var i=start+1; i<=max; i++)
		if (i==this.currentPage)
			str.push("<a class='dhx_not_active'><b>"+i+"</b></a>");
		else
			str.push("<a onclick='this.grid.changePage("+i+"); return false;'>"+i+"</a>");
	return str.join(sep);
}
dhtmlXGridObject.prototype._pgn_link=function(mode,ac,ds){
	if (mode=="prevpages" || mode=="prev"){
		if (this.currentPage==1) return ds;
		return '<a onclick=\'this.grid.changePageRelative(-1*'+(mode=="prev"?'1':'this.grid.pagesInGroup')+'); return false;\'>'+ac+'</a>'
	}
	
	if (mode=="nextpages" || mode=="next"){
		if (this.rowsBuffer.length/this.rowsBufferOutSize <= this.currentPage ) return ds;
		if (this.rowsBuffer.length/(this.rowsBufferOutSize*(mode=="next"?'1':this.pagesInGroup)) <= 1 ) return ds;
		return '<a onclick=\'this.grid.changePageRelative('+(mode=="next"?'1':'this.grid.pagesInGroup')+'); return false;\'>'+ac+'</a>'
	}
	
	if (mode=="current"){
		var i=this.currentPage+(ac?parseInt(ac):0);
		if (i<1 || Math.ceil(this.rowsBuffer.length/this.rowsBufferOutSize) < i ) return ds;
		return '<a '+(i==this.currentPage?"class='dhx_active_page_link' ":"")+'onclick=\'this.grid.changePage('+i+'); return false;\'>'+i+'</a>'
	}
	return ac;
}

dhtmlXGridObject.prototype._pgn_template_active=function(block){
	var tags=block.getElementsByTagName("A");
	if (tags)
		for (var i=0; i < tags.length; i++) {
			tags[i].grid=this;
		};
}
dhtmlXGridObject.prototype._pgn_template_compile=function(template){
	/*
		[prev],[next]
		[currentpages]
		[from],[to],[total]
	*/
	template=template.replace(/\[([^\]]*)\]/g,function(a,b){
		b=b.split(":");
		switch (b[0]){
			case "from": 
				return '"+(arguments[1]*1+(arguments[2]*1?1:0))+"';
			case "total":
				return '"+arguments[3]+"';
			case "to":
				return '"+arguments[2]+"';
			case "current":
			case "prev":
			case "next":
			case "prevpages":
			case "nextpages":
				return '"+this._pgn_link(\''+b[0]+'\',\''+b[1]+'\',\''+b[2]+'\')+"'
			case "currentpages":
				return '"+this._pgn_block(\''+b[1]+'\')+"'
		}
		//do it here
	})
	return new Function('return "'+template+'";')
}

dhtmlXGridObject.prototype.i18n.paging={
	results:"Results",
	records:"Records from ",
	to:" to ",
	page:"Page ",
	perpage:"rows per page",
	first:"To first Page",
	previous:"Previous Page",
	found:"Found records",
	next:"Next Page",
	last:"To last Page",
	of:" of ",
	notfound:"No Records Found"
}
/**
*	@desc: configure paging with toolbar mode ( must be called BEFORE enablePaging)
*	@param: navButtons - enable/disable navigation buttons
*	@param: navLabel - enable/disable navigation label
*	@param: pageSelect - enable/disable page selector
*	@param: perPageSelect - enable/disable rows per  page selector
*	@type: public
*   @edition: Professional
*/
dhtmlXGridObject.prototype.setPagingWTMode = function(navButtons,navLabel,pageSelect,perPageSelect){
	this._WTDef=[navButtons,navLabel,pageSelect,perPageSelect];
}
/**
*	@desc: Bricks skin for paging
*/
dhtmlXGridObject.prototype._pgn_bricks = function(page, start, end){
		//set class names depending on grid skin
		var tmp = (this.skin_name||"").split("_")[1];
		var sfx="";
		if(tmp=="light" || tmp=="modern" || tmp=="skyblue")
			sfx = "_"+tmp;
		
		this.pagerElAr = new Array();
		this.pagerElAr["pagerCont"] = document.createElement("DIV");
		this.pagerElAr["pagerBord"] = document.createElement("DIV");
		this.pagerElAr["pagerLine"] = document.createElement("DIV");
		this.pagerElAr["pagerBox"] = document.createElement("DIV");
		this.pagerElAr["pagerInfo"] = document.createElement("DIV");
		this.pagerElAr["pagerInfoBox"] = document.createElement("DIV");
		var se = (this.globalBox||this.objBox);
		this.pagerElAr["pagerCont"].style.width = se.clientWidth+"px";
		this.pagerElAr["pagerCont"].style.overflow = "hidden";
		this.pagerElAr["pagerCont"].style.clear = "both";
		this.pagerElAr["pagerBord"].className = "dhx_pbox"+sfx;
		this.pagerElAr["pagerLine"].className = "dhx_pline"+sfx;
		this.pagerElAr["pagerBox"].style.clear = "both";
		this.pagerElAr["pagerInfo"].className = "dhx_pager_info"+sfx;
		
		//create structure
		this.pagerElAr["pagerCont"].appendChild(this.pagerElAr["pagerBord"]);
		this.pagerElAr["pagerCont"].appendChild(this.pagerElAr["pagerLine"]);
		this.pagerElAr["pagerCont"].appendChild(this.pagerElAr["pagerInfo"]);
		this.pagerElAr["pagerLine"].appendChild(this.pagerElAr["pagerBox"]);
		this.pagerElAr["pagerInfo"].appendChild(this.pagerElAr["pagerInfoBox"]);
		this._pgn_parentObj.innerHTML = "";
		this._pgn_parentObj.appendChild(this.pagerElAr["pagerCont"]);
			
				
			
		
		if(this.rowsBuffer.length>0){
			var lineWidth = 20;
			var lineWidthInc = 22;
			
			//create left arrow if needed
			if(page>this.pagesInGroup){
				var pageCont = document.createElement("DIV");
				var pageBox = document.createElement("DIV");
				pageCont.className = "dhx_page"+sfx;
				pageBox.innerHTML = "&larr;";
				pageCont.appendChild(pageBox);
				this.pagerElAr["pagerBox"].appendChild(pageCont);
				var self = this;
				pageCont.pgnum = (Math.ceil(page/this.pagesInGroup)-1)*this.pagesInGroup;
				pageCont.onclick = function(){
					self.changePage(this.pgnum);
				}
				lineWidth +=lineWidthInc;
			}
			//create pages
			for(var i=1;i<=this.pagesInGroup;i++){
				var pageCont = document.createElement("DIV");
				var pageBox = document.createElement("DIV");
				pageCont.className = "dhx_page"+sfx;
				pageNumber = ((Math.ceil(page/this.pagesInGroup)-1)*this.pagesInGroup)+i;
				if(pageNumber>Math.ceil(this.rowsBuffer.length/this.rowsBufferOutSize))
					break;
				pageBox.innerHTML = pageNumber;
				pageCont.appendChild(pageBox);
				if(page==pageNumber){
					pageCont.className += " dhx_page_active"+sfx;
					pageBox.className = "dhx_page_active"+sfx;
				}else{
					var self = this;
					pageCont.pgnum = pageNumber;
					pageCont.onclick = function(){
						self.changePage(this.pgnum);
					}
				}
				lineWidth +=(parseInt(lineWidthInc/3)*pageNumber.toString().length)+15;
				pageBox.style.width = (parseInt(lineWidthInc/3)*pageNumber.toString().length)+8+"px";
				this.pagerElAr["pagerBox"].appendChild(pageCont);
			}
			//create right arrow if needed
			if(Math.ceil(page/this.pagesInGroup)*this.pagesInGroup<Math.ceil(this.rowsBuffer.length/this.rowsBufferOutSize)){
				var pageCont = document.createElement("DIV");
				var pageBox = document.createElement("DIV");
				pageCont.className = "dhx_page"+sfx;
				pageBox.innerHTML = "&rarr;";
				pageCont.appendChild(pageBox);
				this.pagerElAr["pagerBox"].appendChild(pageCont);
				var self = this;
				pageCont.pgnum = (Math.ceil(page/this.pagesInGroup)*this.pagesInGroup)+1;
				pageCont.onclick = function(){
					self.changePage(this.pgnum);
				}
				lineWidth +=lineWidthInc;
			}
			
			this.pagerElAr["pagerLine"].style.width = lineWidth+"px";
		}
		
		//create page info
		if(this.rowsBuffer.length>0 && this.showRecInfo)
			this.pagerElAr["pagerInfoBox"].innerHTML = this.i18n.paging.records+(start+1)+this.i18n.paging.to+end+this.i18n.paging.of+this.rowsBuffer.length;
		else if(this.rowsBuffer.length==0){
			this.pagerElAr["pagerLine"].parentNode.removeChild(this.pagerElAr["pagerLine"]);
			this.pagerElAr["pagerInfoBox"].innerHTML = this.i18n.paging.notfound;
		}
		//add whitespaces where necessary
		this.pagerElAr["pagerBox"].appendChild(document.createElement("SPAN")).innerHTML = "&nbsp;";
		this.pagerElAr["pagerBord"].appendChild(document.createElement("SPAN")).innerHTML = "&nbsp;";
		this.pagerElAr["pagerCont"].appendChild(document.createElement("SPAN")).innerHTML = "&nbsp;";
		this.callEvent("onPaging",[]);			
	}


/**
*	@desc: web toolbar skin for paging
*/
dhtmlXGridObject.prototype._pgn_toolbar = function(page, start, end){
	if (!this.aToolBar) this.aToolBar=this._pgn_createToolBar();
		var totalPages=Math.ceil(this.rowCount/this.rowsBufferOutSize);
		
		this.enableItem(this._pgn_parentId+"right","ar_right.gif");
		this.enableItem(this._pgn_parentId+"rightabs","ar_right_abs.gif");
		this.enableItem(this._pgn_parentId+"left","ar_left.gif");
		this.enableItem(this._pgn_parentId+"leftabs","ar_left_abs.gif");
			if(this._zte_currentPage==totalPages || totalPages==0){
				this.disableItem(this._pgn_parentId+"right","ar_right_dis.gif");
				this.disableItem(this._pgn_parentId+"rightabs","ar_right_abs_dis.gif");
			}
			if(this._zte_currentPage==1){
				this.disableItem(this._pgn_parentId+"left","ar_left_dis.gif");
				this.disableItem(this._pgn_parentId+"leftabs","ar_left_abs_dis.gif");
			}
			if(this.pagingDisplay == "simple"){
				this.disableItem(this._pgn_parentId+"rightabs","ar_right_abs_dis.gif");
				if(this.rowsBufferOutSize < this.rowsBuffer.length){
					this.disableItem(this._pgn_parentId+"right","ar_right_dis.gif");
				}
			}
			
			setItemValue(this._pgn_parentId+'page',this._zte_currentPage);
			setItemText(this._pgn_parentId+"totalPages","/"+totalPages);
			setItemText(this._pgn_parentId+'results',(start+1)+"-"+end);
			setItemText(this._pgn_parentId+'rowCount',this.rowCount);
	this.callEvent("onPaging",[]);		
}
dhtmlXGridObject.prototype._pgn_createToolBar=function(){
  var toolbar="<table  class='pageBar'><tr>";
  toolbar=toolbar+"<td class='img_td'><img id='"+this._pgn_parentId+"leftabs'><img id='"+this._pgn_parentId+"left'></td>";
  toolbar=toolbar+"<td class='label_td'><span class='span'><input type='text' id='"+this._pgn_parentId+"page'/>";
  if(this.pagingDisplay == "default"){
	  toolbar=toolbar+"<span class='label' id='"+this._pgn_parentId+"totalPages'>/0</span></span></td>";
  }else if(this.pagingDisplay == "simple"){
	  toolbar=toolbar+"<span class='label' id='"+this._pgn_parentId+"totalPages' style='display: none'>/0</span></span></td>";
  }
  toolbar=toolbar+"<td class='img_td'><img id='"+this._pgn_parentId+"right'><img id='"+this._pgn_parentId+"rightabs'></td>";
  toolbar=toolbar+"<td class='right_td'>本页记录：<span id='"+this._pgn_parentId+"results'>0-0</span>";
  if(this.pagingDisplay == "default"){
	  toolbar=toolbar+"<span id='"+this._pgn_parentId+"rowCountTrip'>&nbsp;&nbsp;共：</span><span id='"+this._pgn_parentId+"rowCount'>0</span></td>";
  }else if(this.pagingDisplay == "simple"){
	  toolbar=toolbar+"<span id='"+this._pgn_parentId+"rowCountTrip' style='display: none'>&nbsp;&nbsp;共：</span><span id='"+this._pgn_parentId+"rowCount' style='display: none'>0</span></td>";
  }
  toolbar=toolbar+"</tr></table>";
  this._pgn_parentObj.innerHTML=toolbar;
  var self=this;
  myAttachEvent(document.getElementById(this._pgn_parentId+"page"),"onkeydown",function(){
 // document.getElementById("page").attachEvent(
  	var obj=event.srcElement;
  	if(event.keyCode==13){
  		self.dynaChange(obj.value);
  		return true;
  	}
  	if(event.keyCode!=8){
	  	if(event.keyCode<48 || event.keyCode>57){
	  		return false;
	  	}
  	}
  });
}
dhtmlXGridObject.prototype.enableItem=function(id,src){
	document.getElementById(id).src=this.imgURL+src;
	var self=this;
   myAttachEvent(document.getElementById(id),"onclick",function(){
		switch (id){
			case self._pgn_parentId+"leftabs":
				self.dynaChange(1);
				break;
			case self._pgn_parentId+"left":
				self.dynaChange(self._zte_currentPage-1);
				break;
			case self._pgn_parentId+"rightabs":
			    self.dynaChange(Math.ceil(self.rowCount/self.rowsBufferOutSize));
				break;
			case self._pgn_parentId+"right":
				self.dynaChange(self._zte_currentPage+1);
				break;
			}
	});
	onImgMouse(id);
}
dhtmlXGridObject.prototype.disableItem=function(id,src){
	document.getElementById(id).src=this.imgURL+src;
	document.getElementById(id).disabled=true;
	delImgMouse(id);
}
function setItemText(id,value){
	document.getElementById(id).innerHTML=value;
}
function setItemValue(id,value){
	document.getElementById(id).value=value;
}
function onImgMouse(id){
     myAttachEvent(document.getElementById(id),"onmouseover",_over);
     myAttachEvent(document.getElementById(id),"onmouseout",_out);
	//document.getElementById(id).attachEvent("onmouseover",_over);
	//document.getElementById(id).attachEvent("onmouseout",_out);
}
function delImgMouse(id){
     myAttachEvent(document.getElementById(id),"onmouseover",_over);
     myAttachEvent(document.getElementById(id),"onmouseout",_out);
	//document.getElementById(id).detachEvent("onmouseover",_over);
	//document.getElementById(id).detachEvent("onmouseout",_out);
}
function _over(){
	event.srcElement.className='img_hover';
}
function _out(){
	event.srcElement.className='';
}
dhtmlXGridObject.prototype._itemClick=function(){
	var id=event.srcElement.id;
	var self=this;
	switch (id){
			case this._pgn_parentId+"leftabs":
				self.dynaChange(1);
				break;
			case this._pgn_parentId+"left":
				self.dynaChange(self._zte_currentPage-1);
				break;
			case this._pgn_parentId+"rightabs":
			    self.dynaChange(Math.ceil(self.rowCount/self.rowsBufferOutSize));
				break;
			case this._pgn_parentId+"right":
				self.dynaChange(self._zte_currentPage+1);
				break;
			}
}
dhtmlXGridObject.prototype._pgn_createToolBar2 = function(){
	this.aToolBar = new dhtmlXToolbarObject(this._pgn_parentObj,(this._pgn_skin_tlb||"dhx_blue"));
	if (!this._WTDef) this.setPagingWTMode(true,true,true,true);
	var self=this;
     myAttachEvent(this.aToolBar,"onClick",function(val){ 
		val=val.split("_")
		switch (val[0]){
			case "leftabs":
				self.dynaChange(1);
				break;
			case "left":
				self.dynaChange(self._zte_currentPage-1);
				break;
			case "rightabs":
			    self.dynaChange(Math.ceil(self.rowCount/self.rowsBufferOutSize));
				break;
			case "right":
				self.dynaChange(self._zte_currentPage+1);
				break;
			case "perpagenum":
				if (val[1]===this.undefined) return;
			    self.rowsBufferOutSize = parseInt(val[1]);
				self.dynaChange(1);
				self.aToolBar.setItemText("perpagenum","<div style='width:100%; text-align:right'>"+val[1]+" "+self.i18n.paging.perpage+"</div>");
				break;
			case "pages":
				if (val[1]===this.undefined) return;
				self.dynaChange(val[1]);
				self.aToolBar.setItemText("pages","<div style='width:100%; text-align:right'>"+self.i18n.paging.page+val[1]+"</div>");
				break;
			}
	})
	//add buttons
	if (this._WTDef[0]){
		this.aToolBar.addButton("leftabs", NaN,  "", this.imgURL+'ar_left_abs.gif', this.imgURL+'ar_left_abs_dis.gif');
		this.aToolBar.setWidth("leftabs","5")
		this.aToolBar.addButton("left", NaN,  "", this.imgURL+'ar_left.gif', this.imgURL+'ar_left_dis.gif');
		this.aToolBar.setWidth("left","5")
	}
	if (this._WTDef[1]){
		this.aToolBar.addText("results",NaN,this.i18n.paging.results)
		this.aToolBar.setWidth("results","20")
	}
	if (this._WTDef[0]){
		this.aToolBar.addButton("right", NaN,  "", this.imgURL+'ar_right.gif', this.imgURL+'ar_right_dis.gif');
		this.aToolBar.setWidth("right","5")
		this.aToolBar.addButton("rightabs", NaN,  "", this.imgURL+'ar_right_abs.gif', this.imgURL+'ar_right_abs_dis.gif');
		this.aToolBar.setWidth("rightabs","5")
	}
	if (this._WTDef[2]){
		this.aToolBar.addButtonSelect("pages", NaN, "select page",[]);
		this.aToolBar.setWidth("pages","75")
	}
	if (this._WTDef[3]){
		this.aToolBar.addButtonSelect("perpagenum", NaN, "select size",[]);
		for (var k=5; k<35; k+=5)
			this.aToolBar.addListOption('perpagenum', 'perpagenum_'+k, NaN, "button", k+" "+this.i18n.paging.perpage);
		this.aToolBar.setWidth("perpagenum","130")
	}
	
	//var td = document.createElement("TD"); td.width = "5"; this.aToolBar.tr.appendChild(td);
	//var td = document.createElement("TD"); td.width = "100%"; this.aToolBar.tr.appendChild(td);
	
	this.aToolBar.disableItem("results");
	return this.aToolBar;
}
var myAttachEvent = function(obj, evt, fn){  
     _obj = typeof(obj)=="string" ? document.getElementById(obj) : obj;
	if (_obj.addEventListener)  
		_obj.addEventListener(evt.substring(2), fn, false);  
	else if (_obj.attachEvent)  
		_obj.attachEvent(evt, fn);  
}  

dhtmlXGridObject.prototype.setPagingDisplay = function(pagingisplay){
	this.pagingDisplay = pagingisplay;
};

dhtmlXGridObject.prototype.getPagingDisplay = function(){
	return this.pagingDisplay;
};
