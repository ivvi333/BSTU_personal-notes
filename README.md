# iwr powershell:
## /api/auth:
Регистрация:
```powershell
iwr -Uri "http://localhost:8080/api/auth/register" -Method POST -ContentType "application/json" -Body '{"username":"{username}", "password":"{password}"}'
```
Вход:
```powershell
iwr -Uri "http://localhost:8080/api/auth/login" -Method POST -ContentType "application/json" -Body '{"username":"{username}", "password":"{password}"}'
```
## /api/notes
Создание личной заметки:
```powershell
iwr -Uri "http://localhost:8080/api/notes" -Method POST -ContentType "application/json" -Headers @{"Authorization"="Bearer {JWT}"} -Body '{"title":"{title}", "content":"{content}"}'
```
Получение всех личных заметок:
```powershell
iwr -Uri "http://localhost:8080/api/notes" -Method GET -Headers @{"Authorization"="Bearer {JWT}"}
```
Получение конкретной личной заметки:
```powershell
iwr -Uri "http://localhost:8080/api/notes/{id}" -Method GET -Headers @{"Authorization"="Bearer {JWT}"}
```
Удаление конкретной личной заметки:
```powershell
iwr -Uri "http://localhost:8080/api/notes/{id}" -Method DELETE -Headers @{"Authorization"="Bearer {JWT}"}
```
