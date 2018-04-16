<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<body>
<center>
    <h3>
        <a href="/">View all sections</a>
    </h3>
    <h1>
        <%--<jsp:useBean id="section" scope="request" type="Section"/>--%>
        <c:out value='${section.section}'/>
    </h1>
</center>
<div align="center">
    <form action="create" method="post">
        <textarea name="name"></textarea>
        <textarea name="info" rows="50" cols="150"></textarea>
        <input type="submit" value="Add"/>
    </form>
</div>
</body>
</html>