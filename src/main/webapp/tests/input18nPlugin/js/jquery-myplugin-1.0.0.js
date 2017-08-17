/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


(function($)
{
    $.fn.greenify = function()
    {
        var backColor = "green";
        var foreColor = "white";
        this.css({"background-color": backColor, "color": foreColor });
        return this;
    };
}(jQuery));
