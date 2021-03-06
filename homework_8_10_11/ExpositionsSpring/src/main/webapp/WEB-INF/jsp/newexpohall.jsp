<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ page isELIgnored="false" %>

<%@include file="../jspf/head.jspf" %>
<body>
<div class="container">
    <%@include file="../jspf/navbar.jspf" %>
    <h4>Create exposition:</h4>
    <h5>Step 2:</h5>

    <form:form class="input-group mb-3" method="post" action="/reservehall" modelAttribute="hallNameList">
        <form:checkboxes items="${hallList}" itemValue="name" path="hallNames" />
        <button type="submit" class="btn btn-primary mb-3">Submit</button>
    </form:form>

</div>
</body>
</html>

