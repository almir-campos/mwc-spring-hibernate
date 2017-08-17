<%--
    Document   : details
    Created on : Aug 5, 2013, 3:30:39 PM
    Author     : almir
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <%@ include file = "../complements/includes/noscript.jspf" %>
        <%@ include file = "../complements/includes/jQueryWithDialog.jspf" %>
        
        <!--CSS-->
        <link href="<c:url value='/resources/css/styles.css' />" type="text/css" rel="stylesheet" >
        <link rel="stylesheet" href="<c:url value='/resources/css/jquery-ui/ui-lightness/jquery-ui-1.10.3.datepicker.css' />" />
        
        <!--SCRIPTS-->
        <script src="<c:url value='/resources/js/mwc/jquery-complementary.js' />" type="text/javascript" ></script>
        <script src="<c:url value='/resources/js/mwc/mwc.js' />" type="text/javascript" ></script>
        <script src="<c:url value='/resources/js/mwc/input18n-0.0.0.js' />" type="text/javascript" ></script>

        <!--PAGE MESSAGES-->
        <spring:message code="message_confirm_change_locale" var="messageConfirmChangeLocale" />

        <!--PAGE LABELS-->
        <spring:message code="label_series_edit_title" var="labelSeriesEditTitle" />
        <spring:message code="label_series_edit_data_legend" var="labelSeriesEditDataLegend" />
        <spring:message code="label_series_edit_data_update_button" var="labelSeriesEditDataUpdateButton" />
        <spring:message code="label_series_edit_initial_photo_legend" var="labelSeriesEditInitialPhotoLegend" />
        <spring:message code="label_series_edit_initial_photo_submit_button" var="labelSeriesEditInitialPhotoSubmitButton" />

        <!--VARIABLES-->
        <c:set var='viewName' value='seriesEdit' />
        <c:set var="ctx" value="${pageContext.request.servletContext.contextPath}" />
    </head>
    <body>
        <div class="contentDiv">
            <div id="rootMenuDiv">
                <jsp:include page='../complements/includes/genericmenu.jsp' />
                <!--<script>menuBuilder("seriesEdit", "${messageConfirmChangeLocale}", "${series.mwcUser.id}", "${series.id}");</script>-->
            </div>
            <div class='mainDiv'><%@ include file = "../complements/includes/innerTopBar.jspf" %>
                <h1>${labelSeriesEditTitle}</h1>
                <%@include file = '../complements/includes/showPersonBasicData.jspf' %>
                <form:form 
                    id="editSeriesForm" 
                    action="/series/edit"
                    commandName="series"
                    enctype="multipart/form-data"
                    method="post">
                    <%@include file = '../complements/includes/inputSeriesBasicData.jspf' %>
                </form:form>
                <button 
                    id="editSeriesBt"
                    class="confirmButton confirmSubmit"
                    formId="editSeriesForm">${labelConfirmButton}
                </button>
                <%@include file = '../complements/includes/showAvailableOptions.jspf' %>
                <!--                <fieldset>
                                    <legend>${labelSeriesEditDataLegend}</legend>
                                    <label for="id">${labelSeriesDescription}</label>
                                    <input type="text" id="id" value="${series.description}"/>
                                    <br />
                                    <label for="initialWeight">${labelSeriesInitialWeight}</label>
                                    <input type="text" id="initialWeight" value="${series.initialWeight}"/>
                                    <br />
                                    <label for="goalWeight">${labelSeriesGoalWeight}</label>
                                    <input id="goalWeight" value="${series.goalWeight}"/>
                                    <br />
                                    <label for="startDateStrin">${labelSeriesStartDate}</label>
                                    <input type="text" id="startDateString" value="${series.startDateString}"/>
                                    <br />
                                    <label for="estimatedEndDate">${labelSeriesEstimatedEndDate}:</label>
                                    <input type="text" id="estimatedEndDate" value="${series.estimatedEndDateString}"/>
                                    <br />
                                </fieldset>
                                    <button id="updateSeriesDataBt" class="confirmButton">${labelSeriesEditDataUpdateButton}</button>-->
                <!--                <form 
                                    id="updatePhotoForm" 
                                    action="<c:url value='/series/updatePhoto'/>"
                                    enctype="multipart/form-data"
                                    method="post">
                                    <fieldset>
                                        <legend>${labelSeriesEditInitialPhotoLegend}</legend>
                                        <div id="seriesPhotoDiv">
                                            <img class="picture" src="<c:url value='/series/seriesInitialPhoto/${series.id}' />" />
                                        </div>
                                        <input id="photoInput" type="file" name="file"/>
                                        <input type="hidden" name="id" value="${series.id}" />
                                        <input id="updatePhotoFormBt" type="submit" value="${labelSeriesEditInitialPhotoSubmitButton}">
                                    </fieldset>
                                </form>-->
            </div>
                <%@include file = '../complements/includes/ajaxWait.jspf' %>
        </div>
        <!--        <script>
                    $("#updateSeriesDataBt").click(function()
                    {
                        $.ajax(
                                {
                                    url: "<c:url value='/series/updateSeriesData' />",
                                    type: "POST",
                                    dataType: "text",
                                    data:
                                            {
                                                seriesId: ${series.id},
                                                description: $("#description").val(),
                                                initialWeight: $("#initialWeight").val(),
                                                goalWeight: $("#goalWeight").val(),
                                                startDateString: $("#startDateString").val(),
                                                estimatedEndDateString: $("#estimatedEndDate").val()
                                            },
                                    success: function(data)
                                    {
                                        alert(data);
                                    }
                                });
                    });
        
                    var jqfoptions = {
                        success: function()
                        {
                            var href = "<c:url value='/series/seriesphotoload' />" + "?seriesId=${series.id}";
                            $("#seriesPhotoDiv").load(href);
                        }
                    };
                    $("#updatePhotoForm").ajaxForm(jqfoptions);
                </script>-->
    </body>
</html>
