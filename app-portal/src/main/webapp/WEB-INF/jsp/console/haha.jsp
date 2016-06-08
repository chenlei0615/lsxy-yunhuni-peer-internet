<%--
  Created by IntelliJ IDEA.
  User: Tandy
  Date: 2016/6/7
  Time: 16:49
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<%@include file="/inc/import.jsp" %>
<html>
<head>
    <title>Title</title>
</head>
<body>

    <form action="${ctx}/logout" method="post" id="logoutForm">
        <input type="hidden" name="${_csrf.parameterName}"
               value="${_csrf.token}" />
    </form>
    <script>
        function formSubmit() {
            document.getElementById("logoutForm").submit();
        }
    </script>

用户控制台首页
    登陆成功

    <c:if test="${pageContext.request.userPrincipal.name != null}">
        <h2>
            Welcome : ${pageContext.request.userPrincipal.name}  ${currentUser.name}| <a
                href="javascript:formSubmit()"> Logout</a>
        </h2>
    </c:if>
</body>
</html>
