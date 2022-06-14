<%@ page import="dream.model.Post" %>
<%@ page import="dream.store.PsqlStore" %>
<%@ page import="dream.model.City" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>

<!doctype html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

    <script src="https://code.jquery.com/jquery-3.4.1.slim.min.js"
            integrity="sha384-J6qa4849blE2+poT4WnyKhv5vZF5SrPo0iEjwBvKU7imGFAV0wwj1yYfoRSJoZ+n"
            crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js"
            integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo"
            crossorigin="anonymous"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js"
            integrity="sha384-wfSDF2E50Y2D1uUdj0O3uMBJnjuUD4Ih7YwaYd1iqfktj0Uod8GCExl3Og8ifwB6"
            crossorigin="anonymous"></script>

    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css"
          integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
    <script src="https://code.jquery.com/jquery-3.4.1.min.js"></script>

    <%
        String id = request.getParameter("id");
        Post post = new Post(0, "", "", new City(0));
        if (id != null) {
            post = PsqlStore.instOf().findByIdPost(Integer.parseInt(id));
        }
    %>

    <script>
        $(document).ready(function () {
            $.ajax({
                type: 'GET',
                url: 'http://localhost:8080/dreamjob/city',
                dataType: 'json'
            }).done(function (data) {
                for (var city of data) {
                    city.id === <%=post.getCity().getId()%>;
                    $('#city').append($('<option>', {
                        value: city.id,
                        text: city.city,
                    }));
                }
            }).fail(function (err) {
                console.log(err);
            });
        });
    </script>



    <title>Работа мечты</title>
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
                <a class="nav-link" href="<%=request.getContextPath()%>/my">Мои публикации</a>
            </li>
            <li class="nav-item">
                <a class="nav-link active" href="<%=request.getContextPath()%>/post/edit.jsp">Добавить вакансию</a>
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
        <div class="card-header d-flex justify-content-center">
            <% if (id == null) { %>
            <h6>Новая вакансия. </h6>
            <% } else { %>
            <h6>Редактирование вакансии. </h6>
            <% } %>
        </div>
        <div class="card-body d-flex justify-content-center">
            <form action="<%=request.getContextPath()%>/posts.do?id=<%=post.getId()%>" method="post">
                <div class="form-group" style="text-align: center">
                    <label>Название вакансии</label>
                    <div class="col-sm-30" style="width: 600px">
                        <input type="text" class="form-control" placeholder="Название" name="name" autocomplete="off"
                               title="Enter name of position." required
                               value="<%=post.getName()%>" id="inputName"
                               oninvalid="this.setCustomValidity('Заполните название вакансии')"
                               oninput="this.setCustomValidity('')"/>
                    </div>
                </div>
                <div class="form-group" style="text-align: center">
                    <label>Описание вакансии</label>
                    <div class="col-sm-30" style="width: 600px;">
                        <textarea class="form-control" name="text"
                                  id="inputDesc" placeholder="Описание"
                                  rows="4"><%=post.getText()%></textarea>
                    </div>
                </div>
                <div class="form-group" style="text-align: center">
                    <div class="col-sm-30" style="width: 600px;">
                        <label for="city" style="text-align: center">Выберите город</label>
                        <select class="form-control" style="text-align: center" id="city" name="city_id">
                        </select>
                    </div>
                </div>
                <div class="form-group justify-content-center" style="text-align: center">
                    <div class="col-sm-30" style="width: 600px;">
                        <button type="submit" class="btn btn-primary" onclick="return validate();">Сохранить</button>
                    </div>
                </div>
            </form>
        </div>
    </div>
</div>
</body>
</html>