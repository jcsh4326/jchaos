/**
 * @project jchaos
 * @package
 * @file chaos-animate
 * Created by <a href="mailto:jincheng.jcs@gmail.com">瑾诚</a> on 2016/1/21.
 **/
'use strict';

;(function( $ ){
    var defaultTurnOpts = {
        speed: 500,
        type: 'fade',
        replace: ''
    };
    var _turn = function(replace, opts){
        var _opts = $.extend({}, opts, defaultTurnOpts);
        var _repl = replace || _opts.replace, _repl = _repl === '' ? '<div></div>' : _repl, me = $(this);
        if(_opts.type === 'fade'){
            me.fadeOut(_opts.speed, function () {
                if(_opts.outCallback){
                    _opts.outCallback();
                }
                $(_repl).fadeIn(_opts.speed, _opts.callback);
            })
        }
    };
    /**
     * 换位，将背景fadeIn，前景fadeOut
     * @type {Function}
     */
    $.fn.turn = _turn;
})(jQuery);