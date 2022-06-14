<%@ page import="dream.model.Candidate" %>
<%@ page import="dream.store.PsqlStore" %>
<%@ page import="dream.model.City" %><%--
  Created by IntelliJ IDEA.
  User: Lana
  Date: 08.10.2021
  Time: 22:39
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Title</title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.0/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.0/js/bootstrap.min.js"></script>
</head>
<body>
<%
    String id = request.getParameter("id");
    Candidate candidate = new Candidate(0, "", "", "", new City(0));
    if (id != null) {
        candidate = PsqlStore.instOf().findByIdCandidate(Integer.parseInt(id));
    }
%>

<div class="container bg-light " style="background-color: #e3f2fd; width: 500px">
    <div class="card" style="background-color: #e3f2fd;">
        <div class="card-body" style="text-align: center">
            <br>
            <br>
            <br>
            <br>
            <h3>Загрузка фото</h3>
            <br>
            <form action="<%=request.getContextPath()%>/upload.do?id=<%=candidate.getId()%>" method="post"
                  enctype="multipart/form-data">
                <div class="form-group" style="display: flex; justify-content: center;">
                    <div class="checkbox">
                        <input type="file" name="file"/>
                    </div>
                    <div class="form-group">
                        <button type="submit" class="btn btn-primary">Сохранить</button>
                    </div>
                </div>
            </form>
        </div>
    </div>
</div>
</body>
</html>
