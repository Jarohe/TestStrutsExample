<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<html>
<head>
    <title>Success User Create</title>
</head>
<body>
<jsp:include page="general/header.jsp"/>
    <h1>User <bean:write name="userForm" property="login"/> success created</h1>
    <a href="/welcome.html">Welcom Page</a>
</body>
</html>
