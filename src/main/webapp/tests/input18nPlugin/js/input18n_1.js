/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

(function($)
{

    var customKeyCode_BACKSPACE = "[0008.0008]";
    var customKeyCode_TAB = "[0000.0009]";
    var customKeyCode_ENTER = "[0013.0013]";
    var customKeyCode_DEL = "[0000.0046]";
    var customKeyCode_LEFT = "[0000.0037]";
    var customKeyCode_TOP = "[0000.0038]";
    var customKeyCode_RIGHT = "[0000.0039]";
    var customKeyCode_DOWN = "[0000.0040]";
    var customKeyCode_INSERT = "[0000.0045]";
    var customKeyCode_COLON = "[0044.0000]";
    var customKeyCode_DOT = "[0046.0000]";

    var nChars = 0;
    var currentValue = 0;
    var currentLocale = "en_US";

    var allowedEditKeys =
            customKeyCode_BACKSPACE
            + customKeyCode_TAB
            + customKeyCode_DEL
            + customKeyCode_DOT
            + customKeyCode_ENTER
            + customKeyCode_LEFT
            + customKeyCode_RIGHT;


    var currencyMaskString = '{\
        "en_US":{"currencyFormat": "#,###.##", "decimalSeparator": "."},\n\
        "pt_BR":{"currencyFormat": "#.###,##", "decimalSeparator": ","}\n\
        }';
    var currencyMaskJson = $.parseJSON(currencyMaskString);
    var obj = null;
    var stringValue = "";

    $.fn.maskCurrencyInput = function(myLocale)
    {
//        $(obj).mask(getMyCurrencyMask("en_US"));
        obj = this;
//        $(obj).val("0.00");
        $(obj).css("text-align", "right");
        currentValue = $(obj).val().length;
        currentLocale = myLocale;
        formatTest2();

//        this.keyup(function( event)
//        {
//            $("#msg0Div").text( $( obj ).val() );
//            if (getCustomKeyCode(event.which, event.keyCode) === customKeyCode_BACKSPACE)
//            {
//                alert(">" + $(obj).val());
//                // OK - $(obj).next().focus();
//            }
//
////            $(obj).val(round(currentValue, 2));
//        });
        this.keyup(function(event)
        {
//            alert(isValidDigit( event.which));
//            alert( String.fromCharCode(event.which) );
//            alert( zeroLeft( 1, 4 ) );
//            alert( getCustomKeyCode( event.which, event.keyCode) );

            //$("#msgDiv").text( getInputCaretPosition( this )  + ", " +$(obj).val().substr( getInputCaretPosition( this ) -1 , 1 ) );

            if (isDecimalDigit(event.which))
            {
//                nChars++;
                //formatTest();
//                stringValue += String.fromCharCode(event.which);
                stringValue = $(obj).val().replace(getDecimalSeparator( currentLocale ), "");
                formatTest2();
                $("#msg1Div").text(stringValue);
//                event.preventDefault();
                return;
            }
//
//            if (getCustomKeyCode(event.which, event.keyCode) === customKeyCode_ENTER)
//            {
//                alert($(obj).val());
//                // OK - $(obj).next().focus();
//            }
//            
//            
//            if (getCustomKeyCode(event.which, event.keyCode) === customKeyCode_DEL)
//            {
//                nChars--;
//                currentValue = $(obj).val();
//                formatLossDigit();
//                return;
//            }

            if (getCustomKeyCode(event.which, event.keyCode) === customKeyCode_BACKSPACE)
            {
                stringValue = $(obj).val().replace(getDecimalSeparator( currentLocale ), "");
                formatTest2();
                $("#msg1Div").text(stringValue);
//                event.preventDefault();
                return;
            }
//            
//            if (allowedEditKeys.indexOf(getCustomKeyCode(event.which, event.keyCode)) > -1)
//            {
//                return;
//            }
//            formatTest();
//            event.preventDefault();
        });
    };

    function formatTest2()
    {
        var showValue = stringValue;
        if (stringValue.length < 3)
        {
            showValue = zeroLeft(stringValue, 3);
        }

        if (hasZeroLeft(stringValue))
        {
            if (stringValue.length > 3)
            {
                stringValue = stringValue.substr(stringValue.length - 3);
                showValue = stringValue;
            }
        }
//        else
//        if (stringValue.substr(stringValue.length - 1) == "0")
//        {
//            showValue = stringValue.subst(0, stringValue.substr(stringValue.length - 2)) + ".00";
//        }
        $(obj).val(separateDecimals(showValue));
    }
    function formatTest()
    {
        var val = $(obj).val();
        if (val.indexOf(getDecimalSeparator( currentLocale )) === -1)
        {
            $(obj).val($(obj).val() / 100);
        }
        else
        {
            $(obj).val($(obj).val() * 10);
        }
    }

    function separateDecimals(value)
    {
        return value.substr(0, value.length - 2) + getDecimalSeparator( currentLocale ) + value.substr(value.length - 2);
    }

    function hasZeroLeft(value)
    {
        var r = false;
        var i = 0;
        var c = value.substr(0, 1);
        while (c === "0")
        {
            c = value.substr(i, 1);
            $("#msg0Div").text(c + ", " + i);
            i++;
        }
        if (i > 0)
        {
            return true;
        }
        return false;
    }

    function formatTest1()
    {
        var val = $(obj).val();
        if (val.indexOf(".") === -1)
        {
            $(obj).val($(obj).val() / 100);
        }
        else
        {
            $(obj).val($(obj).val() * 10);
        }
    }
    function format(newChar)
    {
        currentValue = currentValue * 10 + (newChar - 48) * Math.pow(10, -2);
        $(obj).val(round(currentValue, 2));
    }

    function formatGainDigit(newChar)
    {
        currentValue = currentValue * 10 + (newChar - 48) * Math.pow(10, -2);
//        $(obj).val(round(currentValue, 2));
    }

    function formatLossDigit()
    {
        currentValue /= 10;
        $(obj).val(round(currentValue, 2));
    }

    function round(number, decimals)
    {
        return Math.round(number * Math.pow(10, decimals)) / Math.pow(10, decimals);
    }

    function getMyCurrencyMask(locale)
    {
        return currencyMaskJson[ locale ].currencyFormat;
    }

    function getDecimalSeparator( locale )
    {
        return currencyMaskJson[ locale ].decimalSeparator;
    }

    function isDecimalDigit(digit)
    {
        return (digit >= 40 && digit <= 57)
    }

    function isDotDigit(digit)
    {
        return digit === 46;
    }

    function isValidDigit(digit)
    {
        return isDecimalDigit(digit) || isDotDigit(digit);
    }

    function zeroLeft(number, finalLength)
    {
        var bigString = "0".repeat(finalLength) + number.toString();
        return bigString.substring(bigString.length - finalLength);
    }

    String.prototype.repeat = function(num)
    {
        return new Array(num + 1).join(this);
    }

    function getCustomKeyCode(which, code)
    {
        return "[" + zeroLeft(which, 4) + "." + zeroLeft(code, 4) + "]";
    }

    function getRightCharacters(s, n)
    {
        return s.substr(s.length - n);
    }

}(jQuery));

