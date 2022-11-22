### eql-apollo

[![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.github.charlemaznable/eql-apollo/badge.svg)](https://maven-badges.herokuapp.com/maven-central/com.github.charlemaznable/eql-apollo/)
[![MIT Licence](https://badges.frapsoft.com/os/mit/mit.svg?v=103)](https://opensource.org/licenses/mit-license.php)
![GitHub code size](https://img.shields.io/github/languages/code-size/CharLemAznable/eql-apollo)

Eql Apollo Extension, using Apollo config Eql.

##### Maven Dependency

```xml
<dependency>
  <groupId>com.github.charlemaznable</groupId>
  <artifactId>eql-apollo</artifactId>
  <version>2022.0.1</version>
</dependency>
```

##### Maven Dependency SNAPSHOT

```xml
<dependency>
  <groupId>com.github.charlemaznable</groupId>
  <artifactId>eql-apollo</artifactId>
  <version>2022.0.2-SNAPSHOT</version>
</dependency>
```

#### 使用Apollo配置[Eql](https://github.com/bingoohuang/eql)

使用Apollo统一管理Eql配置, 统一```namespace```: ```EqlConfig```.

```java
Eql eql = new Aql("connectionName");
```
即读取```namespace:EqlConfig property:{connectionName}```配置为Eql配置.
