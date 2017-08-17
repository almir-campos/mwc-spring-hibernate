<%@page import="java.util.Random"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <%@ include file = "../complements/includes/noscript.jspf" %>
        <!--CSS-->
        <link href="<c:url value='/resources/css/styles.css' />" type="text/css" rel="stylesheet" >
        <link rel="stylesheet" href="<c:url value='/resources/css/jquery-ui/ui-lightness/jquery-ui-1.10.3.core.css' />" />
        <link rel="stylesheet" href="<c:url value='/resources/css/jquery-ui/ui-lightness/jquery-ui-1.10.3.datepicker.css' />" />
        <link rel="stylesheet" type="text/css" href="<c:url value='/resources/js/jqplot-1.0.8/jquery.jqplot.min.css' />" />
        <!--[if lt IE 9]><script language="javascript" type="text/javascript" src="excanvas.js"></script><![endif]-->
        <!--SCRIPTS-->
        <script src="<c:url value='/resources/js/jquery-1.10.2.min.js' />" type="text/javascript" ></script> 
        <script src="<c:url value='/resources/js/jquery-ui-1.10.3/minified/jquery-ui.min.js' />" type="text/javascript" ></script> 
        <script src="<c:url value='/resources/js/mwc/jquery-complementary.js' />" type="text/javascript" ></script>
        <script language="javascript" type="text/javascript" src="<c:url value='/resources/js/jqplot-1.0.8/jquery.jqplot.min.js' />"></script>
        <script src="<c:url value='/resources/js/mwc/mwc.js' />" type="text/javascript" ></script>
        <!--PAGE MESSAGES-->
        <spring:message code="message_confirm_change_locale" var="messageConfirmChangeLocale" />
        <spring:message code="label_operations" var="labelOperations" />
        <spring:message code="label_current_series" var="labelCurrentSeries" />
        <spring:message code="label_series_details_list_title" var="labelSeriesDetailsTitle" />

        <spring:message code="label_series_details_add" var="labelSeriesDetailsAdd" />
        <!--SERIESDETAILS FIELDS-->
        <spring:message code="label.series.details.id" var="labelSeriesDetailsId" />
        <spring:message code="label.series.details.weight.date" var="labelSeriesDetailsWeightDate" />
        <spring:message code="label.series.details.weight" var="labelSeriesDetailsWeight" />
        <spring:message code="label.series.details.comment" var="labelSeriesDetailsComment" />
        <!--SERIES FIELDS-->
        <spring:message code="label_series_id" var="labelSeriesId" />
        <spring:message code="label_series_description" var="labelSeriesDescription" />
        <spring:message code="label_series_initial_weight" var="labelSeriesInitialWeight" />
        <spring:message code="label_series_goal_weight" var="labelSeriesGoalWeight" />
        <spring:message code="label_series_start_date" var="labelSeriesStartDate" />
        <spring:message code="label_series_estimated_end_date" var="labelSeriesEstimatedEndDate" />
        <spring:message code="label_series_real_end_date" var="labelSeriesRealEndDate" />

        <!--VARIABLES-->
        <c:set var="ctx" value="${pageContext.request.servletContext.contextPath}" />
        <c:set var="mwcUser" value="${series.mwcUser}" />
    </head>
    <body>
        <div class="contentDiv">
            <div id="rootMenuDiv">
                <jsp:include page='../complements/includes/genericmenu.jsp' />
                <!--<script>menuBuilder("seriesDetailsList", "", "${mwcUser.id}", "${series.id}");</script>-->
            </div>
            <div class='mainDiv'>
                <%@ include file = "../complements/includes/innerTopBar.jspf" %>
                <h1>${labelSeriesDetailsTitle}</h1>
                <script>var arrData = new Array();</script>
                <div class="seriesDetailsChartDiv">
                    <div>
                        <fieldset style="width: 30%; float: left;">
                            <!--<legend>${labelCurrentSeries}</label>cool :)-->
                            <legend>${labelCurrentSeries}</legend>
                            <table class="showTable">
                                <tr>
                                    <td>${labelSeriesId}</td>
                                    <td>${series.id}</td>
                                </tr>
                                <tr>
                                    <td>${labelSeriesDescription}</td>
                                    <td>${series.description}</td>
                                </tr>
                                <!--                                <tr>
                                                                    <td>${labelSeriesInitialWeight}</td>
                                                                    <td>${series.initialWeight}</td>
                                                                </tr>-->
                                <tr>
                                    <td>${labelSeriesGoalWeight}</td>
                                    <td>${series.goalWeight}</td>
                                </tr>
                                <tr>
                                    <td>${labelSeriesStartDate}</td>
                                    <td>${series.startDateString}</td>
                                </tr>
                                <tr>
                                    <td>${labelSeriesEstimatedEndDate}</td>
                                    <td>${series.estimatedEndDateString}</td>
                                </tr>
                                <!--                                <tr>
                                                                    <td>${labelSeriesRealEndDate}</td>
                                                                    <td>${series.realEndDateString}</td>
                                                                </tr>-->
                            </table>
                        </fieldset>
                    </div>
                    <div id='seriesDetailsChart1' style='width: 500px; height: 300px; float: right;'></div>
                    <div id="statisticsDiv" style="float: left; width: 30%; font-size: 12px;">
                        <fieldset>
                            <legend>Statistics</legend>
                            <div id="statisticsContentDiv"></div>
                        </fieldset>
                    </div>
                </div>

                <!--<h2>${labelSeriesDetailsTitle}</h2>-->
                <div style="margin-top: 20px;">
                    <table>
                        <thead>
                            <tr>
                                <th class='idColumn'>${labelSeriesDetailsId}</th>
                                <th class='weightDateColumn'>${labelSeriesDetailsWeightDate}</th>
                                <th class='weightColumn'>${labelSeriesDetailsWeight}</th>
                                <th class='commentColumn'>${labelSeriesDetailsComment}</th>
                                <th class='operationsColumn'>${labelOperations}</th>
                            </tr>
                        </thead>
                    </table>
                </div>
                <div>
                    <table class='addSeriesDetailTable'>
                        <tbody>
                            <tr>
                                <td class='idColumn'></td>
                                <td class='weightDateColumn'><input id='weightDate' type="text" name="weightDate" class="inputDate"/></td>
                                <td class='weightColumn'><input id='weight' type="text" name="weight" value=''/></td>
                                <td class='commentColumn'><input id='comment' type="text" name="comment" value=''/></td>
                                <td class='operationsColumn'><a id='addSeriesDetailBt' href='#'>${labelSeriesDetailsAdd}</a></td>
                            </tr>
                        </tbody>
                    </table>
                </div>
                <div id='detailsDiv'></div>
            </div>
        </div>
        <script>
            $("#detailsDiv").load("<c:url value='/series/details/table/${series.id}'/>", function()
            {
                if (arrData.length > 0)
                {
                    // var invertedArray = invertJqPlotArray(arrData);
                    drawChart();
                }
            });
            function deleteSerieDetail(obj)
            {
                var id = $(obj).attr("seriesDetailId");
                $("#seriesDetailsChart1").empty();
                $.post("<c:url value='/seriesDetail/delete/'/>" + id, function()
                {
                    $("#detailsDiv").load("<c:url value='/series/details/table/${series.id}'/>", function()
                    {
                        drawChart();
//                                                var chart = $.jqplot('seriesDetailsChart1', [invertJqPlotArray(arrData)]);
//                                                if (arrData.length > 0)
//                                                {
//                                                    chart.draw();
//                                                }
                    });
                });
            }


            function editSeriesDetail(obj)
            {
                var id = $(obj).attr("seriesDetailId");
                $(".showSd_" + id).hide();
                $(".inputSd_" + id).show();
            }

            function saveSeriesDetail(obj)
            {
                // alert( 'before id');
                var id = $(obj).attr("seriesDetailId");
                //alert( 'after id, before commentVal' );
                var commentVal = "" + $("#seriesDetail_comment_" + id).val();
                //alert( 'after commentVal, before ajax' );
                $.ajax(
                        {
                            url: "<c:url value='/seriesDetail/update' />",
                            dataType: "text",
                            type: "POST",
                            data: {
                                seriesDetailId: id,
                                weightDate: $("#seriesDetail_weightDate_" + id).val(),
                                weight: $("#seriesDetail_weight_" + id).val(),
                                comment: commentVal
                            },
                            success: function(result)
                            {
                                // alert(result);
                                $("#detailsDiv").load("<c:url value='/series/details/table/${series.id}'/>", function()
                                {
//                                    alert( 'after load' );
//                                    var chart = $.jqplot('seriesDetailsChart1', [invertJqPlotArray(arrData)]);
//                                    if (arrData.length > 0)
//                                    {
//                                        chart.draw();
//                                    }
                                    drawChart();
                                });
                            },
                            error: function()
                            {
                                alert('error');
                            }
                        });
            }

            function cancelSeriesDetail(obj)
            {
                var id = $(obj).attr("seriesDetailId");
                $(".showSd_" + id).show();
                $(".inputSd_" + id).hide();
            }

//                                    function rand()
//                                    {
//
//                                        var n = 185 + (10 * (0.5 - Math.random()));
//                                        var m = Math.pow(10, 1);
//                                        return Math.round(n * m) / m;
//                                    }

            $("#addSeriesDetailBt").click(function()
            {

                var d = new Date();
                $.ajax(
                        {
                            url: '${ctx}/seriesDetail/add/${series.id}',
                            type: "POST",
                            data:
                                    {
                                        seriesId: ${series.id},
                                        weightDate: $("#weightDate").val(),
                                        weight: $("#weight").val(),
                                        comment: $("#comment").val()
                                    },
                            success: function()
                            {
                                $("#seriesDetailsChart1").empty();
                                $("#detailsDiv").load("<c:url value='/series/details/table/${series.id}'/>", function()
                                {
                                      drawChart();
//                                    if (arrData.length > 0)
//                                    {
//                                        var chart = $.jqplot('seriesDetailsChart1', [invertJqPlotArray(arrData)]);
//                                        chart.draw();
//                                    }
                                });
                            },
                            error: function()
                            {
                                alert('error');
                            }
                        });
            });
//                                    function getArrParams(arr, name)
//                                    {
//                                        var ret = "";
//                                        for (var i = 0; i < arr.length; i++)
//                                        {
//                                            ret = ret + name + "=" + arr[ i ][ 1 ] + "&";
//                                        }
//                                        ret = ret.substr(0, ret.length - 1);
//                                        return ret;
//                                    }
//                                    function createJqPlotSeries1(arr)
//                                    {
//                                        var ret = new Array();
//                                        for (var i = 0; i < arr.length; i++)
//                                        {
//                                            ret.push(arr[ i ][ 1 ]);
//                                        }
//                                        return ret;
//                                    }
//                                    function createJqPlotSeries(arr)
//                                    {
//                                        var ret = new Array();
//                                        for (var i = 0; i < arr.length; i++)
//                                        {
//                                            ret.push(arr[ i ][ 1 ]);
//                                        }
//                                        return ret;
//                                    }
//                                    function createJqPlotMultipleSeries(arr0, arr1)
//                                    {
//                                        var ret = new Array();
//                                        ret.push(createJqPlotSeries(arr0));
//                                        ret.push(createJqPlotSeries(arr1));
//                                        return ret;
//                                    }
            function drawChart()
            {
                $.ajax({
                    url: "${ctx}/seriesDetail/simpleRegressionData/${seriesId}",
                                type: "post",
                                dataType: "text",
                                success: function(data)
                                {
                                    var realData = new Array();
                                    var trendLine = new Array();
                                    var index = 0;
                                    var dados = $.parseJSON(data);
                                    var medidas = dados.measurements;
                                    var slope = dados.slope;
                                    var intercept = dados.intercept;
                                    for (var index = 0; index < medidas.length; index++)
                                    {
                                        realData.push(new Array(medidas[ index ].dayNumber, medidas[ index ].weight));
                                        trendLine.push(new Array(medidas[ index ].dayNumber, medidas[ index ].weightTrendLine));
                                    }
                                    $("#statisticsContentDiv").empty()
                                            .append("Goal to be achieved in " + dados.currentEstimatedGoalDate)
                                            .append(" (" + dados.totalDaysToGoal + " days from the beginning")
                                            .append(" or " + dados.todayDaysToGoal + " days from today)");

                                    $.jqplot('seriesDetailsChart1', [realData, trendLine]);
                                },
                                error: function()
                                {
                                    alert('error');
                                }

                            });
                        }

        </script>
    </body>
</html>