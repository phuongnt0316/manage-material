$(function(){
    $.fn.limit = function($n){
        this.each(function(){
            var allText   = $(this).html();
            var tLength   = $(this).html().length;
            var startText = allText.slice(0,$n);
            if(tLength >= $n){
                $(this).html(startText+'...');
            }else {
                $(this).html(startText);
            };
        });

        return this;
    }
    $('.product-card .card-detail p  span').limit(90);
    $('.product-card .card-detail .card-title a').limit(50);


});