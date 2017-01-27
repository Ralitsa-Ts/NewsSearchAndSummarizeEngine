# English
# News Search and Summarize

The purpose of this project is to retrieve news from the BBC site,
to find the one that best matches a search, to summarize it and to return it.

What will you need?

1. Java 1.8
2. Maven

Libraries used:

<groupId>org.apache.commons</groupId>
<artifactId>commons-lang3</artifactId>
<version>3.5</version>

------------------------------------------------
For parsing the fetched pages content
<groupId>org.jsoup</groupId>
<artifactId>jsoup</artifactId>
<version>1.10.2</version>

------------------------------------------------
For crawling
<groupId>edu.uci.ics</groupId>
<artifactId>crawler4j</artifactId>
<version>4.2</version>

------------------------------------------------
For indexing
<groupId>org.apache.lucene</groupId>
<artifactId>lucene-core</artifactId>
<version>6.4.0</version>

<groupId>org.apache.lucene</groupId>
<artifactId>lucene-analyzers-common</artifactId>
<version>6.4.0</version>

<groupId>org.apache.lucene</groupId>
<artifactId>lucene-queryparser</artifactId>
<version>6.4.0</version>

------------------------------------------------
For logging
<groupId>org.slf4j</groupId>
<artifactId>slf4j-api</artifactId>
<version>1.7.21</version>

------------------------------------------------
For creating a Spring Boot application
<groupId>org.springframework.boot</groupId>
<artifactId>spring-boot-starter-web</artifactId>
<version>1.3.5.RELEASE</version>

------------------------------------------------
For text summarization
<groupId>org.apache.opennlp</groupId>
<artifactId>opennlp-tools</artifactId>
<version>1.7.1</version>

------------------------------------------------
# Български
# Намиране и резюмиране на новини

Основната цел на проекта е да се създаде търсачка, която да обработва заявки от потребители, търсещи новини. 
Обработването на заявката включва следните основни функционалности:

1. Търсене на подходяща новина в сайта на BBC
2. Резюмиране на намерения текст
3. Връщане на резюмирания текст като отговор на заявката

Какво е необходимо за стартирането на приложението?

1. Java 1.8
2. Maven

Използвани библиотеки:

<groupId>org.apache.commons</groupId>
<artifactId>commons-lang3</artifactId>
<version>3.5</version>

------------------------------------------------
За парсване на извлечените уеб страници
<groupId>org.jsoup</groupId>
<artifactId>jsoup</artifactId>
<version>1.10.2</version>

------------------------------------------------
За обхождане на уеб страници
<groupId>edu.uci.ics</groupId>
<artifactId>crawler4j</artifactId>
<version>4.2</version>

------------------------------------------------
За индексиране и работа с индекс
<groupId>org.apache.lucene</groupId>
<artifactId>lucene-core</artifactId>
<version>6.4.0</version>

<groupId>org.apache.lucene</groupId>
<artifactId>lucene-analyzers-common</artifactId>
<version>6.4.0</version>

<groupId>org.apache.lucene</groupId>
<artifactId>lucene-queryparser</artifactId>
<version>6.4.0</version>

------------------------------------------------
За логове
<groupId>org.slf4j</groupId>
<artifactId>slf4j-api</artifactId>
<version>1.7.21</version>

------------------------------------------------
За създаване на Spring Boot приложение
<groupId>org.springframework.boot</groupId>
<artifactId>spring-boot-starter-web</artifactId>
<version>1.3.5.RELEASE</version>

------------------------------------------------
За резюмиране на текст
<groupId>org.apache.opennlp</groupId>
<artifactId>opennlp-tools</artifactId>
<version>1.7.1</version>
