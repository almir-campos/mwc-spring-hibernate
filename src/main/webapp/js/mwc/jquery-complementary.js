// Forces descending order in the year dropdown selector in the datepicker.


$(document).ready(function()
{
    $.datepicker._generateMonthYearHeader_original = $.datepicker._generateMonthYearHeader;

    $.datepicker._generateMonthYearHeader = function(inst, dm, dy, mnd, mxd, s, mn, mns) {
        var header = $($.datepicker._generateMonthYearHeader_original(inst, dm, dy, mnd, mxd, s, mn, mns)),
                years = header.find('.ui-datepicker-year');

        // reverse the years
        years.html(Array.prototype.reverse.apply(years.children()));

        // return our new html
        return $('<div />').append(header).html();
    };

//    $(".confirmSubmit").click(function()
//    {
//        var formId = "#" + $(this).attr("formId");
//        $("#pleaseWaitDialogDiv").dialog(
//                {
//                    dialogClass: 'pleaseWaitDialog',
//                    resizable: false,
//                    draggable: false,
//                    modal: true,
//                    open: function()
//                    {
//                        $(formId).submit();
//                    }
//                });
//    });

    $(".inputDate")
            .attr("readOnly", "true")
            .datepicker(
                    {
                        dateFormat: "yy-mm-dd",
                        changeMonth: true,
                        stepMonths: 12,
                        monthNamesShort: getMonthNamesShort(),
                        monthNames: getMonthNames(),
                        firstDay: 1,
                        dayNamesMin: getDayNamesMin(),
                        changeYear: true,
                        yearRange: "-100:+0",
                        showAnim: "bounce",
                        showMonthAfterYear: true
                    });
                    
});