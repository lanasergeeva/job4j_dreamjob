[![Build Status](https://app.travis-ci.com/lanasergeeva/job4j_dreamjob.svg?branch=master)](https://app.travis-ci.com/lanasergeeva/job4j_dreamjob)

[![codecov](https://codecov.io/gh/lanasergeeva/job4j_dreamjob/branch/master/graph/badge.svg?token=B7WQ471USN)](https://codecov.io/gh/lanasergeeva/job4j_dreamjob)

# Приложение "Биржа вакансий"

+ [Описание](#Описание-проекта)
+ [Технологии](#Используемые-технологии)
+ [Вид и описание](#Вид)

## Описание проекта

Web-приложение "Биржа вакансий". Предоставляет возможность создавать вакансии и кандидатов(резюме).
Доступ возможен только зарегистрированным пользователям.
У каждого пользователя уникальный email, по которому происходит вход на сайт.
Пользователь может удалить, отредактировать только свои записи.
Также предоставлена возможно добавить фото кандидата. Пост создается с дефолтным фото,
а в разделе "Мои публикации" фото можно обновить.

## Используемые технологии

+ **Maven**
+ **HTML**, **BOOTSTRAP**, **JS**, **AJAX**
+ **JSP**, **Servlet**, **Apache Tomcat**
+ **JDBC**, **PostgreSQL**
+ **Тестирование:** **Mockito**, **Liquibase**, **H2**, **Junit**
+ **Java 14**
+ **Checkstyle**

## Вид

<h5>Приложение доступно только для зарегистрированных пользователей. Поэтому сначала нужно пройти процесс
авторизации или зарегистрироваться. Стоит валидация на заполнение полей.
В случае ввода неверных данных поля обнулятся и будет выдана ошибка.</h5>

<p>Регистрация (валидация заполнения полей)</p>

![alt text](https://github.com/lanasergeeva/job4j_dreamjob/blob/master/src/main/java/dream/img/reg.png)

<p>Если пользователь ввел почту, которая уже есть в базе, получим предпруждение</p>

![alt text](https://github.com/lanasergeeva/job4j_dreamjob/blob/master/src/main/java/dream/img/regcheck.png)

<p>Окно авторизации</p>

![alt text](https://github.com/lanasergeeva/job4j_dreamjob/blob/master/src/main/java/dream/img/log.png)

<p>Валидация полей при авторизации.</p>

![alt text](https://github.com/lanasergeeva/job4j_dreamjob/blob/master/src/main/java/dream/img/logvalid.png)

<p>Если данные были введены неверно, получим предупрждение</p>

![alt text](https://github.com/lanasergeeva/job4j_dreamjob/blob/master/src/main/java/dream/img/logcheck.png)

<p>При успешной авторизации попадаем в раздел "Сегодня", где отображены все кандидаты и вакансии за текущий день</p>

![alt text](https://github.com/lanasergeeva/job4j_dreamjob/blob/master/src/main/java/dream/img/today.png)

<p>В разделе "Вакансии" можем посмотреть все вакансии</p>

![alt text](https://github.com/lanasergeeva/job4j_dreamjob/blob/master/src/main/java/dream/img/posts.png)

<p>В разделе "Кандидаты" отображены все кандидаты</p>

![alt text](https://github.com/lanasergeeva/job4j_dreamjob/blob/master/src/main/java/dream/img/candidates.png)

<p>В разделе "Мои публикации" отображены все вакансии пользователя. Здесь можно отредактировать, удалить публикации</p>

![alt text](https://github.com/lanasergeeva/job4j_dreamjob/blob/master/src/main/java/dream/img/mysec.png)

<p>В разделе "Добавить вакансии" присутствует валидация полей. Город выбираем из выгруженных из бд городов </p>

![alt text](https://github.com/lanasergeeva/job4j_dreamjob/blob/master/src/main/java/dream/img/postvalid.png)

<p>В разделе "Добавить кандидата" также присутствует валидация полей. </p>

![alt text](https://github.com/lanasergeeva/job4j_dreamjob/blob/master/src/main/java/dream/img/candidatesvalid.png)

<p> Чтобы выйти из приложения, нужно нажать в навигационно окне: </p>

![alt text](https://github.com/lanasergeeva/job4j_dreamjob/blob/master/src/main/java/dream/img/exit.png)