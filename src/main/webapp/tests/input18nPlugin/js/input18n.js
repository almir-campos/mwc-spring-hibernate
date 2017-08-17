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
            + customKeyCode_ENTER
            + customKeyCode_LEFT
            + customKeyCode_RIGHT;
    var currencyMaskString = '{\
        "en_US":{"currencyFormat": "#,###.##", "decimalSeparator": ".", "integerSeparator": ","},\n\
        "pt_BR":{"currencyFormat": "#.###,##", "decimalSeparator": ",", "integerSeparator": "."}\n\
        }';
    var currencyMaskJson = $.parseJSON(currencyMaskString);
    var obj = null;
    var stringValue = "";
    $.fn.maskNumberInput = function(myLocale, realNumber )
    {
        this.keypress(function(event)
        {
            if (event.which < 48 || event.which > 57)
            {
                if (allowedEditKeys.indexOf(getCustomKeyCode(event.which, event.keyCode)) === -1)
                {
                    event.preventDefault();
                    return;
                }
                if (getCustomKeyCode(event.which, event.keyCode) === customKeyCode_ENTER)
                {
                    $(obj).next().focus();
                    return;
                }
            }
        });


        obj = this;
        $(obj).css("text-align", "right");
        currentLocale = myLocale;
        formatTest2();
        this.keyup(function(event)
        {
            if (isDecimalDigit(event.which))
            {
                stringValue = $(obj).val().replace(getDecimalSeparator(currentLocale), "", "g").replace(getIntegerSeparator(currentLocale), "", "g");
                formatTest2();
                $("#msg1Div").text(stringValue);
                $( realNumber ).val( getRealNumber() );
                return;
            }

            if (getCustomKeyCode(event.which, event.keyCode) === customKeyCode_BACKSPACE)
            {
                stringValue = $(obj).val().replace(getDecimalSeparator(currentLocale), "", "g").replace(getIntegerSeparator(currentLocale), "", "g");
                formatTest2();
                $("#msg1Div").text(stringValue);
                $( realNumber ).val( getRealNumber() );
                return;
            }
        });
    };
    
//    $.fn.realNumber = function()
//    {
//        this.val( getRealNumber );
//    };
    
    function getRealNumber()
    {
        return parseFloat( stringValue ) / 100;
    }
    
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
        //formatInteger(showValue);
        if (parseFloat(showValue) / 100 < 1000)
        {
            $(obj).val(separateDecimals(showValue));
        }
        else
        {
            $(obj).val(formatInteger(showValue) + getDecimalSeparator(currentLocale) + showValue.substr(showValue.length - 2));
        }
    }

    function formatInteger(value)
    {
        var integerPart = value.substr(0, value.length - 2);
//        var nbSeparators = Math.floor((integerPart.length - 1) / 3);
        var reminder = customReminder(integerPart.length, 3);
//        var formattedIntegerPart = "";
        var integerPartReminder = integerPart.substr(0, reminder);
        if (reminder > 0)
        {
            integerPartReminder += getIntegerSeparator(currentLocale);
        }
        var integerPartWithoutReminder = integerPart.substr(reminder);
        {
//            formattedIntegerPart = integerPart.substr(0, reminder) + getIntegerSeparator(currentLocale);
//            formattedIntegerPart += integerPartWithoutReminder;
//            for (var i = 0; i < nbSeparators; i++)
//            {
//                formattedIntegerPart += integerPartWithoutReminder.substr(3 * i, 3) + ".";
//            }
            $("#msg0Div").text(value + " -> " + integerPartReminder + splitBlock(integerPartWithoutReminder, 3, getIntegerSeparator(currentLocale)));
        }
        return integerPartReminder + splitBlock(integerPartWithoutReminder, 3, getIntegerSeparator(currentLocale));
    }

    function customReminder(x, y)
    {
        if (x < y)
        {
            return 0;
        }
        return x % y;
    }

    function splitBlock(value, number, separator)
    {
        var r = "";
//        if (parseInt(value) < 1000)
//        {
//            return r;
//        }
        var nbSeparators = Math.floor(value.length / number);
        for (var i = 0; i < nbSeparators; i++)
        {
            r += value.substr(i * number, number) + separator;
        }
//        if (nbSeparators === 2)
//        {
////            alert( nbSeparators );
//            return r;
//        }
        return r.substr(0, r.length - 1);
    }

    function formatTest()
    {
        var val = $(obj).val();
        if (val.indexOf(getDecimalSeparator(currentLocale)) === -1)
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
        return value.substr(0, value.length - 2) + getDecimalSeparator(currentLocale) + value.substr(value.length - 2);
    }

    function hasZeroLeft(value)
    {
        var r = false;
        var i = 0;
        var c = value.substr(0, 1);
        while (c === "0")
        {
            c = value.substr(i, 1);
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

    function getDecimalSeparator(locale)
    {
        return currencyMaskJson[ locale ].decimalSeparator;
    }

    function getIntegerSeparator(locale)
    {
        return currencyMaskJson[ locale ].integerSeparator;
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

