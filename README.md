Это учебный проект по работе с SpringBoot. Он представляет из себя конвертер валют. По url мы получаем xml файл со списком курсов валют от ЦБРФ
Программа автоматически парсит его, составляет список валют с полной информацией(без курса к рублю) и добавляет их в БД
Так же программа добавляет в базу данных (связаную с первой по ID валюты) курс и дату, для которой этот курс актуален.
После чего пользователь может выбрать две валюты для конвертации и кол-во первой валюты. Сама конвертация будет записана в базу данных
с указанием даты перевода и автора. Автор конвертации может увидеть все свои конвертации.
Так-же реализованна регистрация и авторизация.