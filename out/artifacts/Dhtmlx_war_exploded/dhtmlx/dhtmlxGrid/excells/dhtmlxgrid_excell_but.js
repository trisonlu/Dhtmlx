//v.2.5 build 91111

/*
Copyright DHTMLX LTD. http://www.dhtmlx.com
To use this component please contact sales@dhtmlx.com to obtain license
*/

function eXcell_but(cell){
	this.cell = cell;
    this.grid = this.cell.parentNode.grid;
	this.edit = function(){}
	this.isDisabled = function(){ return true; }
	this.detach = function(){}
	this.setValue=function(val){
		this.cell.val=val.split("^")[0];
		this.cell.innerHTML="<input type='button' value='"+this.cell.val+"'/>";
		this.cell.childNodes[0].onclick=function(){eval(val.split("^")[1]); };
	}
	this.getValue=function(){
		return (this.cell.val||"");
	}
}
eXcell_but.prototype = new eXcell;