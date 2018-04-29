<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Alex
  Date: 25.04.2018
  Time: 18:44
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
</head>
<body>
<div align="center">Вы авторизовались как <c:out value='${userName}'/>
    <br>
    <a href="/all">Открыть справочник по Js</a><br>
</div>
</body>
</html>
