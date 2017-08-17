<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link href="<c:url value='/resources/css/styles.css' />" type="text/css" rel="stylesheet" ></script> 
        <script src="<c:url value='/resources/js/jquery-1.10.2.min.js' />" type="text/javascript" ></script> 
        <script src="<c:url value='/resources/js/jquery-ui-1.10.3/minified/jquery-ui.min.js' />" type="text/javascript" ></script> 
        <script src="<c:url value='/resources/js/jquery-ui-1.10.3/minified/jquery.ui.effect-fold.min.js' />" type="text/javascript" ></script>
        <script src="<c:url value='/resources/js/jquery-ui-1.10.3/minified/jquery.ui.effect-scale.min.js' />" type="text/javascript" ></script>
        <title>MWC</title><%@ include file = "../complements/includes/noscript.jspf" %>
    </head>
    <body>
        <h1>${dashboard}</h1>
        <div class="testBlock">
            <c:set var="ctx" value="${pageContext.request.servletContext.contextPath}" />
            <h2>Context: ${ctx}</h2>
            <h4>CRUD</h4>
            <a href="${ctx}/mwcUser/listusers">/mwcUser/listusers</a><br/>
            <a href="${ctx}/admin/crud/delUser/2">/admin/crud/delUser/2</a><br/>
            <h4>JSON</h4>
            <a href="${ctx}/admin/json/listusers">/admin/json/listusers</a><br/>
            <a href="${ctx}/admin/json/listuser?id=2">/admin/json/listuser?id=2</a><br/>
            <h4>JSP</h4>
            <a href="${ctx}/admin/jsp/listusers">/admin/jsp/listusers</a><br/>
            <a href="${ctx}/admin/jsp/showUser/2">/admin/jsp/showUser/2</a><br/>
            <h4>XML</h4>
            <a href="${ctx}/admin/xml/listusers">/admin/xml/listusers</a><br/>
            <a href="${ctx}/admin/xml/listuser?id=2">/admin/xml/listuser?id=2</a><br/>
        </div>
        <div class="testBlock">
            <h3>Add User</h3>
            <form id="addUserForm" action="<c:url value='/admin/crud/addUser'/>" enctype="multipart/form-data" method="post">
                <table>
                    <tr>
                        <td>First Name</td>
                        <td><input type="text" name="firstName" value="m123456"/></td>
                    </tr>
                    <tr>
                        <td>Last Name</td>
                        <td><input type="text" name="lastName" value="m123456"/></td>
                    </tr>
                    <tr>
                        <td>Display Name</td>
                        <td><input type="text" name="displayName" value="m123456"/></td>
                    </tr>
                    <tr>
                        <td>Birth Date</td>
                        <td><input type="text" name="birthDate" value="2013-08-01"/></td>
                    </tr>
                    <tr>
                        <td>Username</td>
                        <td><input type="text" name="username" value="m123456"/></td>
                    </tr>
                    <tr>
                        <td>Password</td>
                        <td><input type="text" name="password" value="m123456"/></td>
                    </tr>
                    <tr>
                        <td>Preferred Language</td>
                        <td><input type="text" name="preferredLanguage" value="en_US"/></td>
                    </tr>
                    <tr>
                        <td>User Photo</td>
                        <td><input type="file" name="file"/></td>
                    </tr>
                </table>

                <input type="submit" value="Add  mwcUser " />
            </form>
            <div id="addUserFormResponse"></div>
        </div>
        <div class="testBlock">
            <h3>Upload Photo</h3>
            <form id="uploadForm" action="<c:url value='/admin/upload' />" enctype="multipart/form-data" method="post">
                <input type="file" name="file"/>
            </form>
            <button id="uploadFormBt">Upload</button>
        </div>
        <div id="avatarImgLoadDiv" class="testBlock">
            <div id="avatarImgBtDiv">
                <button id="avatarImgBt">Load avatar</button>
                <button id="avatarImgClearBt">Clear avatar</button>
            </div>
            <div id="avatarImgDiv"></div>
        </div>
        <script>
            $('#uploadFormBt').click(function(e)
            {
                $("#uploadForm").submit();
                e.preventDefault();
            });
            $('#avatarImgBt').click(function()
            {
                $("#avatarImgDiv").hide().html("<img id='avatarImg' src='" + "<c:url value='/admin/crud/avatarImg/2' />" + "' onclick=zoomAvatarImg();" + "></img>");
                $("#avatarImg").attr("width", 100).attr("height", 100);
                $("#avatarImgDiv").show();
            });
            $('#avatarImgClearBt').click(function()
            {
                $("#avatarImgDiv").empty();
            });

            function zoomAvatarImg()
            {
                var img = document.getElementById('avatarImg');
                if (img.clientWidth == 100)
                {
                    $("#avatarImgDiv").hide();
                    $("#avatarImg").attr("width", img.naturalWidth).attr("height", img.naturalHeight);
                    $("#avatarImgDiv").show('scale', {direction: 'both', origin: ["top", "left"]}, 1000);
                }
                else
                {
                    $("#avatarImg").attr("width", 100).attr("height", 100);
                }
            }
        </script>
    </body>
</html>
