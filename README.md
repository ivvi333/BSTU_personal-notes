# iwr powershell:
## /api/auth:
iwr -Uri "http://localhost:8080/api/auth/register" -Method POST -ContentType "application/json" -Body '{"username":"{username}", "password":"{password}"}'
iwr -Uri "http://localhost:8080/api/auth/login" -Method POST -ContentType "application/json" -Body '{"username":"{username}", "password":"{password}"}'
## /api/notes
iwr -Uri "http://localhost:8080/api/notes" -Method POST -ContentType "application/json" -Headers @{"Authorization"="Bearer {JWT}"} -Body '{"title":"{title}", "content":"{content}"}'
iwr -Uri "http://localhost:8080/api/notes" -Method GET -Headers @{"Authorization"="Bearer {JWT}"}
iwr -Uri "http://localhost:8080/api/notes/{id}" -Method GET -Headers @{"Authorization"="Bearer {JWT}"}
iwr -Uri "http://localhost:8080/api/notes/{id}" -Method DELETE -Headers @{"Authorization"="Bearer {JWT}"}
