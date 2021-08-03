const express = require("express")
const app = express()
const port = 3300
const login = require('./data/Login.json')
const userInfo = require('./data/UserInfo.json')
const menus = require('./data/Menus.json')
app.post('/api/sys/login', (request, response) => {
    response.send(login)
})
app.get('/api/sys/userInfo', (request, response) => {
    response.send(userInfo)
})
app.get('/api/sys/menus/user', (request, response) => {
    response.send(menus)
})
app.get('/api/sys/permissions', (request, response) => {
    response.send(menus)
})

app.get('/', (request, response) => {
    console.log('request-->', request.baseUrl)
    response.send("hello world")
})

app.listen(port, () => {
    console.log('监听3300端口')
})
