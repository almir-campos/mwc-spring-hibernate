<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Login Page</title>
        <%@ include file = "../complements/includes/noscript.jspf" %>
        <noscript><meta http-equiv="refresh" content="0; url=${pageContext.request.servletContext.contextPath}/public/nojavascript"></noscript>
        <link href="<c:url value='/resources/css/styles.css' />" type="text/css" rel="stylesheet" ></script> 
    <!--SCRIPTS-->
    <script src="<c:url value='/resources/js/jquery-1.10.2.min.js' />" type="text/javascript" ></script>
    <script src="<c:url value='/resources/js/jquery-cookie/jquery.cookie.js' />" type="text/javascript" ></script>
    <script src="<c:url value='/resources/js/mwc/mwc.js' />" type="text/javascript" ></script>
    <!--LABELS-->
    <spring:message code="page.login.title" var="pageLoginTitle" />
    <spring:message code="multiline.text.test" var="multilineTextTest" />
    <!--VARIABLES-->
    <c:set var="ctx" value="${pageContext.request.servletContext.contextPath}" />
</head>
<body>
    <div class="contentDiv">
        <div class='leftPanelDiv'>
            <img 
                src='${ctx}/resources/images/flags/br.gif'
                style='cursor: pointer; float: left; margin-right: 5px;'
                class="localeFlag"
                id="pt_BR" />
            <img 
                src='${ctx}/resources/images/flags/us.gif' 
                style='cursor: pointer; float: left; margin-left: 5px;' 
                class="localeFlag"
                id="en_US" />
            <script>
                var locale = $.cookie("MWC_locale");
                if (!locale)
                {
                    locale = "en_US";
                }
                $("#" + locale).addClass("flagShadow");

                $(".localeFlag").click(function()
                {
                    locale = $(this).attr("id");

                    $(".localeFlag").removeClass("flagShadow");
                    changeLocale(locale, "", "");
                });
            </script>
        </div>
        <div class='mainDiv'><%@ include file = "../complements/includes/outterTopBar.jspf" %>
            <h3>Login</h3>

            <c:if test="${not empty loginFailed}">
                <div class="errorblock">
                    *${loginFailed}*
                </div>
            </c:if>
            <div id='loginBlock'>
                <form id='loginForm' name='loginForm' action="<c:url value='/j_spring_security_check' />"
                      method='POST'>
                    <div class='block1'>
                        User
                        <br />
                        <input type='text' name='j_username' value='almir' class="normal"/>
                        <span id="signupSpan" class="note" onclick="signup();">Sign up</span>
                        <br />
                    </div>
                    <div class='block1'>
                        Password
                        <br >
                        <input type='password' name='j_password' value="" class="normal" />
                        <br />
                        <span id="resetPasswordSpan" class="note">Reset password</span>
                        <br />
                    </div>
                    <div class='block1' style="margin-top: 10px;">
                        <input class="submitButton" name="submit" type="submit" value="Login"style='float: left;'/>
                        <input class="submitButton" name="reset" type="reset" value="clear" style='float: right;'/>
                    </div>
                    <br />
                </form>
            </div>
        </div>
        <div>
            <!--${multilineTextTest}-->
        </div>
    </div>
    <script>
        function signup()
        {
            window.location.href = "<c:url value='/public/signup'/>";
        }
    </script>
</body>
</html>