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
        <c:out value='${section.section}'/>
    </h1>
</center>
<div align="center">
    <form action="update?name=<c:out value='${section.section}'/>" method="post">
        <textarea name="info" rows="50" cols="150">
            <c:out value='${section.info}'/>
        </textarea>
        <input type="submit" value="Save"/>
    </form>
</div>
</body>
</html>