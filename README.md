# RubleMood
Сервис обращается к сервису курсов валют, и отдает gif в ответ:
если курс по отношению к рублю за сегодня стал выше вчерашнего, то отдаем рандомную отсюда https://giphy.com/search/rich
если ниже - отсюда https://giphy.com/search/broke



## Сборка и запуск.
1. Скачайте файлы [Dockerfile](https://github.com/chernybro/RubleMood/blob/main/Dockerfile) и [RubleMood.jar](https://github.com/chernybro/RubleMood/blob/main/RubleMood.jar).
2. В директории со скачанными файлами введите "docker build -t spring-docker-simple:0.0.1 ."
3. Запустите контейнер docker run -d -p 8080:8080 -t ruble-mood 
4. В браузере перейдите на http://localhost:8080/currency-mood?code={код валюты} (например RUB).
