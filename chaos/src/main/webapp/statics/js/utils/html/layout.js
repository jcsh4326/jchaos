/**
 * @project jchaos
 * @package
 * @file layout
 * Created by <a href="mailto:jincheng.jcs@gmail.com">瑾诚</a> on 2016/2/4.
 **/
define(['js/utils/jsbetter'],function(Jsbetter){
    'use strict';
    var Col = function (col) {
        this.type = col.type||'text';
        this.value = col.value||'';
        this.defaultValue = col.defaultValue||'';
        this.title = col.title||col.alias||'';
        this.name = col.name||'';
        this.width = col.width||'';
        this.class = col.class||'';
    };
    Col.prototype.toJSON = function(){
        return {
            type:this.type,value:this.value,title:this.title,
            name:this.name,defaultValue:this.defaultValue,
            width:this.width,class:this.class
        };
    };
    var Row = function (row) {
        this.colNum = 0;
        this.cols = [];
        if(row instanceof Array) {
            this.colNum = row.length;
            var calculable = true, left = 100, free = [];
            for(var i=0;i<this.colNum;i++){
                var tmpCol = new Col(row[i]);
                this.cols.push(tmpCol);
                if(tmpCol.width.endWith('px'))
                    calculable = false;
                left = calculable ? (
                    // String.parseNumber会把百分数转成小数
                    tmpCol.width.endWith('%') ?
                        ( isNaN(tmpCol.parseNumber()*100) ? (function(){calculable=false;return -1;})() : left - (tmpCol.parseNumber()*100) ):
                        ( isNaN(tmpCol.parseNumber()) ? (function(){calculable=false;return -1;})() : left - tmpCol.parseNumber() )
                ) : -1;
                if(tmpCol.width==='')
                    free.push(tmpCol);
            }
            if(free.length<this.colNum){
                if(calculable){
                    for(var i=0;i<free.length;i++){
                        var tmpCol = free[i];
                        tmpCol.width = left/free.length+'%';
                    }
                }
            }else{
                // TODO: 全都没有配置width，默认使用class col-*
                for(var i=0;i<this.cols.length;i++){
                    this.cols[i].width+= 100/this.cols.length+'%';
                }
            }
        }
    };
    Row.prototype.toJSON = function () {
        var cols = [];
        for(var i=0;i<this.cols.length;i++){
            cols.push(this.cols[i].toJSON());
        }
        return cols;
    };
    var Rows = function (rows) {
        this.rowNum = 0;
        this.rows = [];
        if(rows instanceof Array){
            this.rowNum = rows.length;
            for(var i=0;i<this.rowNum;i++){
                var tmpRow = new Row(rows[i]);
                this.rows.push(tmpRow);
            }
        }
    };
    Rows.prototype.toJson = function () {
        var rows = [];
        for(var i=0;i<this.rows.length;i++){
            rows.push(this.rows[i].toJSON());
        }
        return rows;
    };

    return {
        Rows: Rows,
        Row: Row,
        Col: Col
    };
});