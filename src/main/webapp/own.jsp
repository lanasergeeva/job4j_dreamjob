<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">


    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css"
          integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
    <link rel="stylesheet"
          href="https://cdn.jsdelivr.net/npm/@fortawesome/fontawesome-free@5.15.4/css/fontawesome.min.css">
    <title>Мои публикации</title>
</head>
<body>
<div class="container bg-light" style="background-color: #e3f2fd;">
    <nav class="navbar navbar-expand-lg navbar-dark bg-primary text-white justify-content-center"
         aria-label="Twelfth navbar example">
        <ul class="navbar-nav">
            <li class="nav-item">
                <a class="nav-link" href="<%=request.getContextPath()%>/index.do">Сегодня</a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="<%=request.getContextPath()%>/posts.do">Вакансии</a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="<%=request.getContextPath()%>/candidates.do">Кандидаты</a>
            </li>
            <li class="nav-item">
                <a class="nav-link active" href="<%=request.getContextPath()%>/my">Мои публикации</a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="<%=request.getContextPath()%>/post/edit.jsp">Добавить вакансию</a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="<%=request.getContextPath()%>/candidate/edit.jsp">Добавить
                    кандидата</a>
            </li>
            <c:if test="${user != null}">
                <li class="nav-item">
                    <a class="nav-link" href="<%=request.getContextPath()%>/logout.do"> <c:out
                            value="${user.name}"/> | Выйти</a>
                </li>
            </c:if>
        </ul>
    </nav>

    <div class="card" style="background-color: #e3f2fd;">
        <div class="card-header d-flex justify-content-center">
            <h6>Мои вакансии.</h6>
        </div>
        <div class="card-body">
            <table class="table table-bordered">
                <thead>
                <tr>
                <tr>
                    <th scope="col" style="text-align: center; width: 120px">Дата</th>
                    <th scope="col" style="text-align: center;">Город</th>
                    <th scope="col" style="text-align: center;">Название</th>
                    <th scope="col" style="text-align: center;">Описание</th>
                    <th scope="col" style="text-align: center;">Редактировать</th>
                    <th scope="col" style="text-align: center;">Удалить</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${posts}" var="post">
                    <tr>
                        <td class="align-middle" style="text-align: center;">
                            <c:out value="${post.date}"/></td>
                        <td class="align-middle" style="text-align: center;"><c:out value="${post.city.city}"/></td>
                        <td class="align-middle" style="text-align: center;"><c:out value="${post.name}"/></td>
                        <td class="align-middle" style="text-align: center;"><c:out value="${post.text}"/></td>
                        <td class="align-middle"><p style="text-align: center;">
                            <a href='<c:url value="/post/edit.jsp?id=${post.id}"/>'>
                                <i class="fa fa-edit mr-3 fa-2x"></i>
                            </a></p>
                        </td>
                        <td class="align-middle"><p style="text-align: center;">
                            <a href='<c:url value='/deletePost.do?id=${post.id}'/>'>
                                <i class="fa fa-trash mr-3 fa-2x"></i>
                            </a></p>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </div>


    <div class="card" style="background-color: #e3f2fd; width: 100%">
        <div class="card-header d-flex justify-content-center">
            <h6>Мои резюме.</h6>
        </div>
        <c:forEach items="${candidates}" var="can">
            <table class="table table-bordered">
                <tr>
                    <td class="align-middle"
                        style="width: 10%; text-align: center;">
                        <h6 style="text-align: center;">Дата</h6>
                        <c:out value="${can.date}"/>
                    </td>
                    <td class="align-middle"
                        style="width: 10%; text-align: center;">
                        <h6 style="text-align: center;">Имя</h6>
                        <c:out value="${can.name}"/>
                    </td>
                    <td class="align-middle" rowspan="2" style="width: 40%; text-align: center;">
                        <h6 style="text-align: center;">Нывыки:</h6>
                        <c:out value="${can.skills}"/>
                    </td>
                    <td class="align-middle" colspan="2" style="width: 30%; text-align: center;">
                        <img src="<c:url value='/download.do?id=${can.id}'/>" width="160px"
                             height="150px"/>
                    </td>
                    <td class="align-middle" style="width: 10%; text-align: center;">
                        <h6 style="text-align: center;">Обновить</h6>
                        <p><a href='<c:url value="/candidate/edit.jsp?id=${can.id}"/>'>
                            <i class="fa fa-edit fa-2x"></i></a></p>
                    </td>
                </tr>
                <tr>
                    <td class="align-middle" style="text-align: center;">
                        <h6 style="text-align: center;">Должность</h6>
                        <c:out value="${can.position}"/>
                    </td>
                    <td class="align-middle"
                        style="text-align: center;">
                        <h6 style="text-align: center;">Город</h6>
                        <c:out value="${can.city.city}"/>
                    </td>
                    <td class="align-middle" style="text-align: center;">
                        <h6 style="text-align: center;">Обновить фото</h6>
                        <p><a href="<c:url value='/photoupload.jsp?id=${can.id}'/>">
                            <i class="fa fa fa-upload fa-2x"></i>
                        </a></p>
                    </td>
                    <td class="align-middle" style="text-align: center;">
                        <h6 style="text-align: center;">Удалить фото</h6>
                        <p>
                            <a href="<c:url value='/deletePhoto.do?id=${can.id}'/>">
                                <i class="fa fa-times fa-2x"></i>
                            </a></p>
                    </td>
                    <td class="align-middle" style="text-align: center;">
                        <h6 style="text-align: center;">Удалить</h6>
                        <p>
                            <a href="<c:url value='/deleteCan.do?id=${can.id}'/>">
                                <i class="fa fa-trash fa-2x"></i>
                            </a></p>
                    </td>
                </tr>
            </table>
        </c:forEach>

    </div>
</div>
>
</body>
</html>
