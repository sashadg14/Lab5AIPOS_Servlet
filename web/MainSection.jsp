<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<body>
<center>
    <h1>Js Reference</h1>
    <h3>
        <a href="/addMain">Add new section</a>
    </h3>
</center>
<div align="center">
    <table border="1" cellpadding="5">
        <caption><h2>List of sections</h2></caption>
        <tr>
            <th>Section</th>
        </tr>
        <jsp:useBean id="listMainSection" scope="request" type="java.util.List"/>
        <c:forEach var="section" items="${listMainSection}">
            <tr>
                <td>
                    <a href="/mainSection?name=<c:out value='${section.name}'/>"><c:out value="${section.name}" /></a>
                        <%--<a href="/delete?name=<c:out value='${section.section}'/>">Delete</a>--%>
                    <form action=/main_delete method="post">
                        <input type="hidden" name="name" value="${section.name}"/>
                        <input type="submit" value="Remove">
                    </form>
                </td>
            </tr>
        </c:forEach>
    </table>
</div>
</body>
</html>