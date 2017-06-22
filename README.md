# G.E.C.K.
Набор инструментов для сериализации, миграции, фильтрации и генерации **thrift**-овых объектов.  
![default](https://cloud.githubusercontent.com/assets/5084395/23034038/cf7e5eb0-f493-11e6-8698-66262306ca81.png)  

## В комплекте:  
- serializer (msgpack, jolt, thrift, xml, json)
- mock
- migrator
- filter

## Выпуск новой версии
Версии _geck-pom_ и всех его модулей должны совпадать, для этого перед началом работы над новой версией библиотеки нужно увеличить версию _geck-pom_ и в корневой директории проекта выполнить команду:  
`mvn versions:update-child-modules -DgenerateBackupPoms=false`  
Параметр `generateBackupPoms` можно опустить, если нужны резервные копии изменяемых файлов.

## HOW-TO
Собрать и инсталировать jar(s) в локальный мавен репозиторий(без локально установленного трифта):

```
make wc_java_install LOCAL_BUILD=true SETTINGS_XML=path_to_rbk_maven_settings
```
