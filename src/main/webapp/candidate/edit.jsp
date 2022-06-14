<%@ page import="dream.model.Candidate" %>
<%@ page import="dream.store.PsqlStore" %>
<%@ page import="dream.model.City" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<!doctype html>
<html lang="en">
<head>

    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css"
          integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh"
          crossorigin="anonymous">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
    <script src="https://code.jquery.com/jquery-3.4.1.min.js"></script>

    <%
        String id = request.getParameter("id");
        Candidate candidate = new Candidate(0, "", "", "", new City(0));
        if (id != null) {
            candidate = PsqlStore.instOf().findByIdCandidate(Integer.parseInt(id));
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
                    city.id === <%=candidate.getCity().getId()%>;
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
                <a class="nav-link" href="<%=request.getContextPath()%>/post/edit.jsp">Добавить вакансию</a>
            </li>
            <li class="nav-item">
                <a class="nav-link active" href="<%=request.getContextPath()%>/candidate/edit.jsp">Добавить
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
            <h6>Новый кандидат.</h6>
            <% } else { %>
            <h6>Редактирование кандидата. </h6>
            <% } %>
        </div>
        <div class="card-body d-flex justify-content-center">
            <form action="<%=request.getContextPath()%>/candidates.do?id=<%=candidate.getId()%>" method="post">
                <div class="form-group" style="text-align: center">
                    <label>Имя кандидата</label>
                    <div class="col-sm-30" style="width: 600px">
                        <input type="text" class="form-control"  autocomplete="off" required
                               name="name" value="<%=candidate.getName()%>" id="inputName"
                               oninvalid="this.setCustomValidity('Заполните имя')"
                               oninput="this.setCustomValidity('')"/>
                    </div>
                </div>
                <div class="form-group" style="text-align: center">
                    <label>Должность</label>
                    <div class="col-sm-30" style="width: 600px;">
                        <input type="text" class="form-control" title="Enter position." autocomplete="off" required
                               name="position" value="<%=candidate.getPosition()%>" id="inputPosition"
                               oninvalid="this.setCustomValidity('Заполните должность')"
                               oninput="this.setCustomValidity('')"/>
                    </div>
                </div>
                <div class="form-group" style="text-align: center">
                    <label>Навыки</label>
                    <div class="col-sm-30" style="width: 600px;">
                          <textarea class="form-control" name="skills" autocomplete="off"
                                    id="inputSkills" rows="4"><%=candidate.getSkills()%></textarea>
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
                        <button type="submit" class="btn btn-primary" onclick="return validate();">Сохранить
                        </button>
                    </div>
                </div>
            </form>
        </div>
    </div>
</div>
</body>
</html>


