import React from "react";

class Form extends React.Component {
    state = {
        name: '',
        say: '',
        list: [],
    }
    add = () => {
        let {list, name, say} = this.state
        if (!(name.trim() && say.trim())) {
            console.log('用户名或密码不能为空')
            return
        }
        list.push({
            name: name,
            say: say,
        })
        this.setState({
            name: '',
            say: '',
            list: list
        })
    }
    change = (e) => {
        let {name, value} = e.target
        this.setState(
            {[name]: value}
        )
    }
    list = () => {
        if (this.state.list.length === 0) {
            return (
                <li><p>暂无数据</p></li>
            )
        }
        return this.state.list.map((e, index) => {
            return (
                <li key={index}>
                    <p>{e.name}-{e.say}</p>
                </li>
            )
        })
    }

    render() {
        return (
            <div>
                <p><input type="text" name="name" value={this.state.name} onChange={this.change}/></p>
                <p><input type="text" name="say" value={this.state.say} onChange={this.change}/></p>
                <p>
                    <button onClick={this.add}>添加</button>
                </p>
                <ul>{this.list()}</ul>
            </div>
        )
    }
}

export default Form
