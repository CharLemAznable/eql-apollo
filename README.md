### eql-extension

[![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.github.charlemaznable/eql-extension/badge.svg)](https://maven-badges.herokuapp.com/maven-central/com.github.charlemaznable/eql-extension/)
[![MIT Licence](https://badges.frapsoft.com/os/mit/mit.svg?v=103)](https://opensource.org/licenses/mit-license.php)
![GitHub code size](https://img.shields.io/github/languages/code-size/CharLemAznable/eql-extension)

Eql Extension, using Apollo/Etcd config Eql.

##### Maven Dependency

```xml
<dependency>
  <groupId>com.github.charlemaznable</groupId>
  <artifactId>eql-extension</artifactId>
  <version>2023.2.0</version>
</dependency>
```

##### Maven Dependency SNAPSHOT

```xml
<dependency>
  <groupId>com.github.charlemaznable</groupId>
  <artifactId>eql-extension</artifactId>
  <version>2023.2.1-SNAPSHOT</version>
</dependency>
```

#### 使用Apollo配置[Eql](https://github.com/bingoohuang/eql)

使用Apollo统一管理Eql配置, 统一```namespace```: ```EqlConfig```.

```java
Eql eql = new Aql("connectionName");
```
即读取```namespace:EqlConfig property:{connectionName}```配置为Eql配置.

#### 使用Etcd配置[Eql](https://github.com/bingoohuang/eql)

使用Etcd统一管理Eql配置, 统一```namespace```: ```EqlConfig```.

```java
Eql eql = new Etql("connectionName");
```
即读取```namespace:EqlConfig key:{connectionName}```配置为Eql配置.
