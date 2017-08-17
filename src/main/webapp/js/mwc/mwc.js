/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

function warningMessage()
{
    var msg = "(i18n) WARNING!!! "
            + " This system was developed and tested using Firefox 20+ (recommended). "
            + " We do not guarantee that the system will work properly with your browser. "
            + " Please, see the section About/Disclaimer for more details.";
    alert(msg);
}

function getDayNames()
{
    var currentLocale = getCookieValue("MWC_locale");
    var dayNamesString = '{\n\
            "en_US":{"dayNames": [ "Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday" ]},\n\
            "pt_BR":{"dayNames": [ "Domingo", "Segunda-feira", "Terça-feira", "Quarta-feira", "Quinta-feira", "Sexta-feira", "Sábado" ]}}';
    if (!currentLocale)
    {
        currentLocale = "en_US";
    }
    var dayNamesJson = JSON.parse(dayNamesString);
    var dayNames = dayNamesJson[ currentLocale ].dayNames;
    return dayNames;
}

function getDayNamesMin()
{
    var currentLocale = getCookieValue("MWC_locale");
    var dayNamesString = '{\n\
            "en_US":{"dayNames": [ "Su", "Mo", "Tu", "We", "Th", "Fr", "Sa" ]},\n\
            "pt_BR":{"dayNames": [ "Do", "Se", "Te", "Qa", "Qi", "Se", "Sá" ]}}';
    if (!currentLocale)
    {
        currentLocale = "en_US";
    }
    var dayNamesJson = JSON.parse(dayNamesString);
    var dayNames = dayNamesJson[ currentLocale ].dayNames;
    return dayNames;
}

function getMonthNames()
{
    var currentLocale = getCookieValue("MWC_locale");
    var monthNamesString = '{\n\
            "en_US":{"monthNames": [ "Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec" ]},\n\
            "pt_BR":{"monthNames": [ "Jan", "Fev", "Mar", "Abr", "Mai", "Jun", "Jul", "Ago", "Set", "Out", "Nov", "Dez" ]}}';
    if (!currentLocale)
    {
        currentLocale = "en_US";
    }
    var monthNamesJson = JSON.parse(monthNamesString);
    var monthNames = monthNamesJson[ currentLocale ].monthNames;
    return monthNames;
}

function getMonthNamesShort()
{
    var currentLocale = getCookieValue("MWC_locale");
    var monthNamesString = '{\n\
            "en_US":{"monthNames": [ "Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec" ]},\n\
            "pt_BR":{"monthNames": [ "Jan", "Fev", "Mar", "Abr", "Mai", "Jun", "Jul", "Ago", "Set", "Out", "Nov", "Dez" ]}}';
    if (!currentLocale)
    {
        currentLocale = "en_US";
    }
    var monthNamesJson = JSON.parse(monthNamesString);
    var monthNames = monthNamesJson[ currentLocale ].monthNames;
    return monthNames;
}

function changeLocale(locale, message, currentId)
{
    var r = false;
    if (getCookieValue("MWC_locale") !== locale)
    {
        if (message)
        {
            var confirm = window.confirm(message);
        }

        var link = "";

        if (confirm || !message)
        {
            if (!currentId)
            {
                link =
                        location.protocol
                        + "//"
                        + location.host
                        + location.pathname
                        + "?lang="
                        + locale;
            }
            else
            {
                link =
                        location.protocol
                        + "//"
                        + location.host
                        + location.pathname
                        + "/"
                        + currentId
                        + "?lang="
                        + locale;
            }
            window.location.href = link;
            r = true;
        }
    }
    return r;
}

function getCookieValue(c_name)
{
    var c_value = document.cookie;
    var c_start = c_value.indexOf(" " + c_name + "=");
    if (c_start == -1)
    {
        c_start = c_value.indexOf(c_name + "=");
    }
    if (c_start == -1)
    {
        c_value = null;
    }
    else
    {
        c_start = c_value.indexOf("=", c_start) + 1;
        var c_end = c_value.indexOf(";", c_start);
        if (c_end == -1)
        {
            c_end = c_value.length;
        }
        c_value = unescape(c_value.substring(c_start, c_end));
    }
    return c_value;
}

function showHide(element, obj, msgShow, msgHide)
{
    var el = document.getElementById(element);
    var ob = document.getElementById(obj);

    if (el.style.display === "none")
    {
        el.style.display = 'block';
        if (msgHide !== null)
        {
            ob.innerHTML = msgHide;
        }
    }
    else
    {
        el.style.display = 'none';
        if (msgShow !== null)
        {
            ob.innerHTML = msgShow;
        }
    }
    return false;
}

function randomInteger(min, max)
{
    return Math.floor(Math.random() * (max - min + 1)) + min;
}

function invertJqPlotArray(arr)
{
    var invertedArray = new Array();
    for (var i = 0; i < arr.length; i++)
    {
        invertedArray.push(new Array(i, arr[ arr.length - 1 - i ][ 1 ]));
    }
    return invertedArray;

}