/**
 * @project jchaos
 * @package
 * @file jsButter
 * Created by <a href="mailto:jincheng.jcs@gmail.com">瑾诚</a> on 2016/2/3.
 **/
'use strict';
if (!String.prototype.endWith){
    String.prototype.endWith = function (endStr) {
        return (this.length - endStr.length<0) ? false :
            this.slice(-endStr.length)===endStr;
    }
}

if (!String.prototype.parseNumber){
    String.prototype.parseNumber = function () {
        return this.length == 0 ? 0 :
        this.endWith('%') ? this.parseNumber()/100 :
            parseFloat(this.substring(0,this.length-1));
    }
}

if (!Array.prototype.indexOf){
    Array.prototype.indexOf = function(elt /*, from*/){
        var len = this.length >>> 0;

        var from = Number(arguments[1]) || 0;
        from = (from < 0)
            ? Math.ceil(from)
            : Math.floor(from);
        if (from < 0)
            from += len;

        for (; from < len; from++){
            if (from in this &&
                this[from] === elt)
                return from;
        }
        return -1;
    };
}