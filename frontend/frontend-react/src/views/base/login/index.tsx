import {Input, Button} from "antd";
import './index.less'
export default function Index() {
  return (
    <>
      <div className="loginApi-bg">
        <div className="loginApi-div">
          <div className="title">用户登录</div>
          <Input autoComplete="off" placeholder="用户名" type="text" v-model="username" className="input-text"></Input>
          <Input autoComplete="off" placeholder="密码" type="password" v-model="password"
                 className="input-text"></Input>
          <Button style={{width: "100%"}} type="primary" onClick={(e) => {
            console.log(e)
          }} className="loginApi-button">立即登录</Button>
        </div>
      </div>
    </>
  )
}

