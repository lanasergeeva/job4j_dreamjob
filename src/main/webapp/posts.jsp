<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<!doctype html>
<html lang="en">
<head>

    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">


    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css"
          integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">

    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
    <title>Работа мечты</title>
</head>
<body>
<div class="container bg-light" style="background-color: #e3f2fd;">
    <nav class="navbar navbar-expand navbar-dark bg-primary text-white justify-content-center"
         aria-label="Twelfth navbar example">
        <ul class="navbar-nav">
            <li class="nav-item">
                <a class="nav-link" href="<%=request.getContextPath()%>/index.do">Сегодня</a>
            </li>
            <li class="nav-item">
                <a class="nav-link active" href="<%=request.getContextPath()%>/posts.do">Вакансии</a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="<%=request.getContextPath()%>/candidates.do">Кандидаты</a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="<%=request.getContextPath()%>/my">Мои публикации</a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="<%=request.getContextPath()%>/post/edit.jsp">Добавить
                    вакансию</a>
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

    <div class="card" style="background-color: #e3f2fd; width: 100%">
        <div class="card-body">
            <table class="table table-bordered">
                <tr>
                    <th scope="col" style="text-align: center; width: 120px">Дата</th>
                    <th scope="col" style="text-align: center;">Город</th>
                    <th scope="col" style="text-align: center;">Название</th>
                    <th scope="col" style="text-align: center;">Описание</th>
                    <th scope="col" style="text-align: center;">Автор</th>
                </tr>
                <tbody>
                <c:forEach items="${posts}" var="post">
                    <tr>
                        <td class="align-middle" style="text-align: center;">
                            <p><c:out value="${post.date}"/></p></td>
                        <td class="align-middle" style="text-align: center;"><c:out value="${post.city.city}"/></td>
                        <td class="align-middle" style="text-align: center;"><c:out value="${post.name}"/></td>
                        <td class="align-middle" style="text-align: center;"><c:out value="${post.text}"/></td>
                        <td class="align-middle" style="text-align: center;"><c:out value="${post.user.name}"/></td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
</div>
</body>
</html>