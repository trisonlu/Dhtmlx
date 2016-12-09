/**
 * 重载dhtmlXTreeObject的动态加载函数
 * 
 * @param {}
 *            id
 * @param {}
 *            src
 */
dhtmlXTreeObject.prototype._loadDynXML = function(id, src) {
	src = src || this.XMLsource;
	var sn = (new Date()).valueOf();
	this._ld_id = id;
	if (this.xmlalb == "function") {
		if (src) {
			src(this._escape(id));
		}
	} else if (this.xmlalb == "name") {
		this.loadXML(src + this._escape(id));
	} else if (this.xmlalb == "xmlname") {
		this.loadXML(src + this._escape(id) + ".xml?uid=" + sn);
	} else {
		// 添加模型树需要的自定义参数
		var params = appendParams(id);
		try {
			//alert(src + getUrlSymbol(src) + "uid=" + sn + "&id=" + this._escape(id) + params);
			this.loadXML(src + getUrlSymbol(src) + "uid=" + sn + "&id="
					+ this._escape(id) + params);
			
		} catch (e) {
			alert("加载资源树错误，请检查您的资源树配置!");
		}
	}
};

dhtmlXTreeObject.prototype.refreshItem_zte=function(itemId,fun){
      if (!itemId) itemId=this.rootId;
      var temp=this._globalIdStorageFind(itemId);
      this.deleteChildItems(itemId);
        this._loadDynXML_zte(itemId,null,fun);
   };
 dhtmlXTreeObject.prototype._loadDynXML_zte=function(id,src,fun) {
	src = src || this.XMLsource;
	var sn = (new Date()).valueOf();
	this._ld_id = id;
	if (this.xmlalb == "function") {
		if (src) {
			src(this._escape(id));
		}
	} else if (this.xmlalb == "name") {
		this.loadXML(src + this._escape(id));
	} else if (this.xmlalb == "xmlname") {
		this.loadXML(src + this._escape(id) + ".xml?uid=" + sn);
	} else {
		// 添加模型树需要的自定义参数
		var params = appendParams(id);
		try {
			//alert(src + getUrlSymbol(src) + "uid=" + sn + "&id=" + this._escape(id) + params);
			this.loadXML(src + getUrlSymbol(src) + "uid=" + sn + "&id="
					+ this._escape(id) + params,fun);

		} catch (e) {
			alert("加载树错误，请检查您的资源树配置!");
		}
	}
};
/**
 * 设置图标为灰色
 * 
 * @param {}
 *            itemId
 * @return {Number}
 */
dhtmlXTreeObject.prototype.setIconGray = function(itemId) {
	if (itemId) {
		if ((itemId) && (itemId.span))
			var sNode = itemId;
		else
			var sNode = this._globalIdStorageFind(itemId);

		if (!sNode)
			return (0);
		var img = sNode.span.parentNode.previousSibling.childNodes[0];
		img.parentNode.style.filter = "gray";
	}
};

/**
 * 重载树解析函数， 主要是设置没有权限的节点为灰色
 * @param {} c
 * @param {} temp
 * @param {} preNode
 * @param {} befNode
 */
dhtmlXTreeObject.prototype._parseItem = function(c, temp, preNode, befNode) {
	var id;
	if (this._srnd
			&& (!this._idpull[id = c.get("id")] || !this._idpull[id].span)) {
		this._addItemSRND(temp.id, id, c);
		return;
	}
	dhtmlXTreeObject.prototype.getNodeClickEvent = function(id) {
		if (this.nodeClick) {
			return this.nodeClick[id];
		}
	}
	dhtmlXTreeObject.prototype.setNodeClickEvent = function(id, value) {
		if (this.nodeClick) {
			this.nodeClick[id] = value;
		}
	}
	var a = c.get_all();
	if ((typeof(this.waitUpdateXML) == "object") && (!this.waitUpdateXML[a.id])) {
		this._parse(c, a.id, 1);
		return;
	}
	if (c.get('clickEvent')) {
		this.nodeClick[c.get('id')] = c.get('clickEvent');
	}
	// #__pro_feature:01112006{
	if ((a.text === null) || (typeof(a.text) == "undefined")) {
		a.text = c.sub("itemtext");
		if (a.text)
			a.text = a.text.content();
	}
	// #}

	var zST = [];
	if (a.select)
		zST.push("SELECT");
	if (a.top)
		zST.push("TOP");
	if (a.call)
		this.nodeAskingCall = a.id;
	if (a.checked == -1)
		zST.push("HCHECKED");
	else if (a.checked)
		zST.push("CHECKED");
	if (a.open)
		zST.push("OPEN");

	if (this.waitUpdateXML) {
		if (this._globalIdStorageFind(a.id))
			var newNode = this.updateItem(a.id, a.text, a.im0, a.im1, a.im2,
					a.checked);
		else {
			if (this.npl == 0)
				zST.push("TOP");
			else
				preNode = temp.childNodes[this.npl];

			var newNode = this._attachChildNode(temp, a.id, a.text, 0, a.im0,
					a.im1, a.im2, zST.join(","), a.child, 0, preNode);
			preNode = null;
		}
	} else
		var newNode = this._attachChildNode(temp, a.id, a.text, 0, a.im0,
				a.im1, a.im2, zST.join(","), a.child, (befNode || 0), preNode);
	if (a.tooltip)
		newNode.span.parentNode.parentNode.title = a.tooltip;

	if (a.style)
		if (newNode.span.style.cssText)
			newNode.span.style.cssText += (";" + a.style);
		else
			newNode.span.setAttribute("style", newNode.span
							.getAttribute("style")
							+ "; " + a.style);

	if (a.radio)
		newNode._r_logic = true;

	if (a.nocheckbox) {
		var check_node = newNode.span.parentNode.previousSibling.previousSibling;
		check_node.childNodes[0].style.display = 'none';
		if (window._KHTMLrv)
			check_node.style.display = "none";
		newNode.nocheckbox = true;
	}
	if (a.disabled) {
		if (a.checked != null)
			this._setCheck(newNode, a.checked);
		this.disableCheckbox(newNode, 1);
	}

	newNode._acc = a.child || 0;

	if (this.parserExtension)
		this.parserExtension._parseExtension.call(this, c, a, (temp
						? temp.id
						: 0));

	this.setItemColor(newNode, a.aCol, a.sCol);
	if (a.locked == "1")
		this.lockItem(newNode.id, true, true);

	if ((a.imwidth) || (a.imheight))
		this.setIconSize(a.imwidth, a.imheight, newNode);
	if ((a.closeable == "0") || (a.closeable == "1"))
		this.setItemCloseable(newNode, a.closeable);
	var zcall = "";
	if (a.topoffset)
		this.setItemTopOffset(newNode, a.topoffset);
	if ((!this.slowParse) || (typeof(this.waitUpdateXML) == "object")) {
		if (c.sub_exists("item"))
			zcall = this._parse(c, a.id, 1);
	}
	// #__pro_feature:01112006{
	// #smart_parsing:01112006{
	else {
		if ((!newNode.childsCount) && c.sub_exists("item"))
			newNode.unParsed = c.clone();

		c.each("userdata", function(u) {
					this.setUserData(a.id, u.get("name"), u.content());
				}, this);
	}
	// #}
	// #}
	if (zcall != "")
		this.nodeAskingCall = zcall;

	c.each("userdata", function(u) {
		this.setUserData(c.get("id"), u.get("name"), u.content());
		if(u.get("name") == "hasRole" && u.content() == "false"){
			//不设置为灰色，xuwh
			//this.setItemStyle(newNode.id, "color: gray");
			//this.setIconGray(newNode.id);
		}
	}, this)

}

var appendParams = function(itemId) {
	var resTypeIdPath = getResTypeIdPath(itemId);
	var resObjectIdPath = getResObjectIdPath(itemId);

	var nodeType = tree.getUserData(itemId, "nodeType");
	nodeType = nodeType || "1";
	var parentObjId = tree.getUserData(itemId, "parentObjId");
	parentObjId = parentObjId || "0";

	var params = "&viewPathId=" + viewPathId + "&resTypeIdPath="
			+ resTypeIdPath + "&resObjectIdPath=" + resObjectIdPath
			+ "&nodeType=" + nodeType + "&pageNum=" + pageNum + "&resultNum="
			+ resultNum + "&isShowSearch=" + isShowSearch
			+ "&isShowNoneLabelNode=" + isShowNoneLabelNode + "&treeRoleId=" + treeRoleId;
	return params;
};

// 找出资源类型的树状结构
var getResTypeIdPath = function(itemId) {
	var stack = new Array();
	var itemIdTemp = itemId;
	while (itemIdTemp != '0') {
		var resTypeId = tree.getUserData(itemIdTemp, "resTypeId");
		stack.push(resTypeId);
		var parentItemId = tree.getParentId(itemIdTemp);
		itemIdTemp = parentItemId;
	}
	stack.reverse();
	return stack.join("->");
};

// 找出资源对象的树状结构
var getResObjectIdPath = function(itemId) {
	var stack = new Array();
	var itemIdTemp = itemId;
	while (itemIdTemp != '0') {
		stack.push(itemIdTemp);
		var parentItemId = tree.getParentId(itemIdTemp);
		itemIdTemp = parentItemId;
	}
	stack.push("0");
	stack.reverse();
	return stack.join("->");
};
