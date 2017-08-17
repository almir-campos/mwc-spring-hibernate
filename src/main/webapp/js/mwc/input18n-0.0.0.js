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
    var currentValue = 0;
    var currentLocale = "en_US";
    var allowedEditKeys =
            customKeyCode_BACKSPACE
            + customKeyCode_TAB
            + customKeyCode_DEL
            + customKeyCode_ENTER
            + customKeyCode_LEFT
            + customKeyCode_RIGHT;
    var numberMaskString = '{\
        "en_US":{"decimalSeparator": ".", "integerSeparator": ",", "integerBlockLength": "3"},\n\
        "pt_BR":{"decimalSeparator": ",", "integerSeparator": ".", "integerBlockLength": "3"}\n\
        }';
    var numberMaskJson = $.parseJSON(numberMaskString);
    // these variables shouldn't be global
    var obj = null;
    var stringValue = "";
    var decimals = 2;
    $.fn.maskNumberInput = function(myLocale, realNumber, numberDecimals )
    {
       decimals = numberDecimals;
       stringValue = ($( realNumber ).val() * Math.pow( 10,decimals )).toString() ;
       
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
                $( realNumber ).val( getRealNumber() );
                return;
            }

            if (getCustomKeyCode(event.which, event.keyCode) === customKeyCode_BACKSPACE)
            {
                stringValue = $(obj).val().replace(getDecimalSeparator(currentLocale), "", "g").replace(getIntegerSeparator(currentLocale), "", "g");
                formatTest2();
                $( realNumber ).val( getRealNumber() );
                return;
            }
        });
    };
    
    function getRealNumber()
    {
        return parseFloat( stringValue ) / Math.pow( 10,decimals );
    }
    
    function formatTest2()
    {
        var showValue = stringValue;
        if (stringValue.length < (decimals + 1) )
        {
            showValue = zeroLeft(stringValue, (decimals + 1));
        }

        if (hasZeroLeft(stringValue))
        {
            if (stringValue.length > (decimals + 1))
            {
                stringValue = stringValue.substr(stringValue.length - (decimals + 1));
                showValue = stringValue;
            }
        }
        if (parseFloat(showValue) / Math.pow( 10,decimals ) < Math.pow( 10, (decimals + 1) ))
        {
            $(obj).val(separateDecimals(showValue));
        }
        else
        {
            $(obj).val(formatInteger(showValue) + getDecimalSeparator(currentLocale) + showValue.substr(showValue.length - decimals));
        }
    }

    function formatInteger(value)
    {
        var integerPart = value.substr(0, value.length - decimals);
        var reminder = customReminder(integerPart.length, getIntegerBlockLength( currentLocale ) );
        var integerPartReminder = integerPart.substr(0, reminder);
        if (reminder > 0)
        {
            integerPartReminder += getIntegerSeparator(currentLocale);
        }
        var integerPartWithoutReminder = integerPart.substr(reminder);
        return integerPartReminder + splitBlock(integerPartWithoutReminder, getIntegerBlockLength( currentLocale ), getIntegerSeparator(currentLocale));
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
        var nbSeparators = Math.floor(value.length / number);
        for (var i = 0; i < nbSeparators; i++)
        {
            r += value.substr(i * number, number) + separator;
        }
        return r.substr(0, r.length - 1);
    }

    function separateDecimals(value)
    {
        return value.substr(0, value.length - decimals) + getDecimalSeparator(currentLocale) + value.substr(value.length - decimals);
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


    function round(number, decimals)
    {
        return Math.round(number * Math.pow(10, decimals)) / Math.pow(10, decimals);
    }

    function getIntegerBlockLength(locale)
    {
        return numberMaskJson[ locale ].integerBlockLength;
    }

    function getDecimalSeparator(locale)
    {
        return numberMaskJson[ locale ].decimalSeparator;
    }

    function getIntegerSeparator(locale)
    {
        return numberMaskJson[ locale ].integerSeparator;
    }

    function isDecimalDigit(digit)
    {
        return (digit >= 40 && digit <= 57);
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
    };

    function getCustomKeyCode(which, code)
    {
        return "[" + zeroLeft(which, 4) + "." + zeroLeft(code, 4) + "]";
    }

    function getRightCharacters(s, n)
    {
        return s.substr(s.length - n);
    }

}(jQuery));

