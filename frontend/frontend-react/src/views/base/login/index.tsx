import {Input, Button} from "antd";
import './index.less'
import {useState} from "react";
import axios from "@/utils/request"
// @ts-ignore
import {SysApis} from "@/api/Apis"


export default function Index() {
  const [username, setAccount] = useState('');
  const [password, setPassword] = useState('');
  const login = (_: any) => {
    console.log(username, password)
    axios({
      url: SysApis.login,
      method: 'post',
      data: {
        username,
        password
      }
    }).then(r => {
      console.log(r)
    })
  }
  // axios.get('/api/user/login').then(res => {
  //   console.log(res)
  // })
  return (
    <>
      <div className="loginApi-bg">
        <div className="loginApi-div">
          <div className="title">用户登录</div>
          <Input autoComplete="off"
                 placeholder="用户名"
                 type="text"
                 value={username}
                 onChange={(e) => setAccount(e.target.value)}
                 className="input-text"/>
          <Input autoComplete="off"
                 placeholder="密码"
                 value={password}
                 onChange={(e) => setPassword(e.target.value)}
                 type="password"
                 className="input-text"/>
          <Button style={{width: "100%"}} type="primary" onClick={(e) => {
            login(e)
          }} className="loginApi-button">立即登录</Button>
        </div>
      </div>
    </>
  )
}

