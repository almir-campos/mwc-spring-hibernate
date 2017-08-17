<%-- 
    Document   : seriedetailstableload
    Created on : Aug 13, 2013, 12:41:44 PM
    Author     : almir
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <%@ include file = "../../complements/includes/noscript.jspf" %>
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
        <spring:message code="label_operations" var="labelOperations" />
        <spring:message code="label_operation_delete" var="labelOperationDelete" />
        <spring:message code="label_operation_edit" var="labelOperationEdit" />
        <spring:message code="label_operation_confirm" var="labelOperationConfirm" />
        <spring:message code="label_operation_cancel" var="labelOperationCancel" />
    </head>
    <body>


        <div style="height: 270px; overflow: auto;">
            <table class='addSeriesDetailTable'>
                <tbody>
                <script>var arrData = new Array();</script>
                <c:forEach items="${seriesDetails}" var="seriesDetail" varStatus="index">
                    <script> arrData.push(new Array(${index.count}, ${seriesDetail.weight}));</script>
                    <c:if test="${index.count%2 eq 0}">
                        <tr class='oddLine'>
                        </c:if>
                        <c:if test="${index.count%2 ne 0}">
                        <tr class='evenLine'>
                        </c:if>
                        <td class='idColumn'>
                            ${index.count}(${seriesDetail.id})
                        </td>
                        <td class='weightDateColumn'>
                            <span class='showSd_${seriesDetail.id}'>
                                ${seriesDetail.weightDateString}
                            </span>
                            <span class='inputSd_${seriesDetail.id}' style='display: none;'>
                                <input
                                    type='text'
                                    id='seriesDetail_weightDate_${seriesDetail.id}'
                                    class="inputWeightDate"
                                    value='${seriesDetail.weightDateString}'/>
                            </span>
                        </td>
                        <td class='weightColumn'>
                            <span class='showSd_${seriesDetail.id}'>
                                ${seriesDetail.weight}
                            </span>
                            <span class='inputSd_${seriesDetail.id}' style='display: none;'>
                                <input
                                    type='text'
                                    id='seriesDetail_weight_${seriesDetail.id}'
                                    value='${seriesDetail.weight}'/>
                            </span>
                        </td>
                        <td class='commentColumn'>
                            <span class='showSd_${seriesDetail.id}'>
                                ${seriesDetail.comment}
                            </span>
                            <span class='inputSd_${seriesDetail.id}' style='display: none;'>
                                <input type='text' id='seriesDetail_comment_${seriesDetail.id}' value='${seriesDetail.comment}'/>
                            </span>
                        </td>
                        <td class='operationsColumn'>
                            <span class='showSd_${seriesDetail.id}'>
                                <a href='#' seriesDetailId='${seriesDetail.id}' onclick='deleteSerieDetail(this);'>${labelOperationDelete}</a>
                                |
                                <a href='#' id='editSeriesDetailId_${seriesDetail.id}' seriesDetailId='${seriesDetail.id}' onclick='editSeriesDetail(this);'>${labelOperationEdit}</a>
                            </span>
                            <span class='inputSd_${seriesDetail.id}' style='display: none;'>
                                <a href='#' id='saveSeriesDetailId_${seriesDetail.id}' seriesDetailId='${seriesDetail.id}' onclick='saveSeriesDetail(this);'>${labelOperationConfirm}</a>
                                |
                                <a href='#' id='cancelSeriesDetailId_${seriesDetail.id}' seriesDetailId='${seriesDetail.id}' onclick='cancelSeriesDetail(this);'>${labelOperationCancel}</a>
                            </span>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
        <script>
            function test(obj)
            {
                alert($(obj).val());
            }
            $(document).ready(function()
            {

//                $(".inputWeightDate")
//                        .attr("readOnly", "true")
//                        .datepicker(
//                                {
//                                    dateFormat: "yy-mm-dd",
//                                    changeMonth: true,
//                                    stepMonths: 12,
//                                    monthNamesShort: getMonthNamesShort(),
//                                    monthNames: getMonthNames(),
//                                    firstDay: 1,
//                                    dayNamesMin: getDayNamesMin(),
//                                    changeYear: true,
//                                    yearRange: "-100:+0",
//                                    showAnim: "bounce",
//                                    showMonthAfterYear: true
//                                });
            });
        </script>
    </body>
</html>
