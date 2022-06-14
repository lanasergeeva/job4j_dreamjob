<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<!doctype html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

    <link href="//maxcdn.bootstrapcdn.com/bootstrap/4.1.1/css/bootstrap.min.css" rel="stylesheet"
          id="bootstrap-css">
    <script src="//maxcdn.bootstrapcdn.com/bootstrap/4.1.1/js/bootstrap.min.js"></script>
    <script src="//cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>


    <title>Работа мечты</title>
</head>
<body>

<div id="login">
    <div class="container">
        <div style="height: 200px"></div>
        <div id="login-row" class="row justify-content-center align-items-center">
            <div id="login-column" class="col-md-6">
                <div id="login-box" class="col-md-12">
                    <form action="<%=request.getContextPath()%>/auth.do" method="post">
                        <h3 class="text-center text-primary">Пройдите процесс авторизации</h3>
                        <div class="form-group">
                            <label for="email" class="text-primary">Электронная почта:</label><br>
                            <input type="email" autocomplete="off"
                                   id="email" class="form-control"
                                   placeholder="Введите логин" name="email" required
                                   oninvalid="this.setCustomValidity('Нужно ввести email')"
                                   oninput="this.setCustomValidity('')"/>
                        </div>
                        <div class="form-group">
                            <label for="password" class="text-primary">Пароль:</label><br>
                            <input type="password" autocomplete="off"
                                   name='password' id="password" class="form-control"
                                   placeholder="Введите пароль"
                                   oninvalid="this.setCustomValidity('Нужно ввести пароль')"
                                   oninput="this.setCustomValidity('')" required/>
                        </div>
                        <div class="form-group row justify-content-center align-items-center">
                            <input type="submit" name="submit"
                                   class="btn btn-primary btn-md mr-5" value="Войти">
                            <button type="submit" class="btn btn-primary btn-md "
                                    formaction="<%=request.getContextPath()%>/reg.jsp" formnovalidate>Регистрация
                            </button>
                            <c:if test="${not empty error}">
                                <div style="color:red; font-weight: bold; margin: 30px 0;">
                                    <c:out value="${error}"/>
                                </div>
                            </c:if>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>