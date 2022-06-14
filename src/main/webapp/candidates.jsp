<%@ page language="java" contentType="text/html; charset=utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<!doctype html>
<html lang="en">
<head>

    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">


    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css"
          integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
    <link rel="stylesheet"
          href="https://cdn.jsdelivr.net/npm/@fortawesome/fontawesome-free@5.15.4/css/fontawesome.min.css">

    <title>Работа мечты</title>
</head>
<body>

<div class="container bg-light text-white" style="background-color: #e3f2fd;">
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
                <a class="nav-link active" href="<%=request.getContextPath()%>/candidates.do">Кандидаты</a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="<%=request.getContextPath()%>/my">Мои публикации</a>
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

    <div class="card" style="width: 100%; background-color: #e3f2fd;">
        <div class="card-body">
            <table class="table  table-bordered">
                <tr>
                    <th class="align-middle" style="text-align: center; width: 150px" scope="col">Дата</th>
                    <th class="align-middle" style="text-align: center;" scope="col">Город</th>
                    <th class="align-middle" style="text-align: center;" scope="col">Имя</th>
                    <th class="align-middle" style="text-align: center;" scope="col">Должность</th>
                    <th class="align-middle" style="text-align: center; width: 40%" scope="col">Навыки</th>
                    <th class="align-middle" style="text-align: center;" scope="col">Фото кандидата</th>
                </tr>
                <c:forEach items="${candidates}" var="can">
                <tr>
                    <td class="align-middle" style="text-align: center;  width: 150px"><c:out value="${can.date}"/></td>
                    <td class="align-middle" style="text-align: center;"><c:out value="${can.city.city}"/></td>
                    <td class="align-middle" style="text-align: center;"><c:out value="${can.name}"/></td>
                    <td class="align-middle" style="text-align: center;"><c:out value="${can.position}"/></td>
                    <td class="align-middle" style="text-align: center;"><c:out value="${can.skills}"/></td>
                    <td class="align-middle"><img src="<c:url value='/download.do?id=${can.id}'/>" width="160px"
                                                  height="150px"/>
                    </td>
                    </c:forEach>

            </table>
        </div>
    </div>
</div>
</body>
</html>