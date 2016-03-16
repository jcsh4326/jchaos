/**
 * @project jchaos
 * @package
 * @file main.debug.js
 * Created by <a href="mailto:jincheng.jcs@gmail.com">瑾诚</a> on 2016/1/21.
 **/
'use strict';
require.config({
    paths: {
        "text": "thirdpart/require/text",
        "jquery": "thirdpart/jquery/1.9.1/jquery",
        "jquery.turn": "js/utils/animate/chaos-animate",
        "jquery.validate": 'thirdpart/jquery.validation/1.14.0/jquery.validate',
        "vue": "thirdpart/vue/vue"
    },
    shim: {
        "jquery.turn": { deps:["jquery"]},
        "jquery.validate": { deps:["jquery"]}
    }
});