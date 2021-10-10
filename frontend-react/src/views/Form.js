import React from "react";
import {Button} from 'antd';
import List from './List'
import MouseListener from './MouseListener'
import PropTypes from "prop-types"

import Context from "../utils/ContextUtils";

class Form extends React.Component {

    state = {
        age: '',
        say: '',
        list: [],
    }
    add = () => {
        let {list, age, say} = this.state
        if (!(age.trim() && say.trim())) {
            console.log('用户名或密码不能为空')
            return
        }
        list.push({
            age: age,
            say: say,
        })
        this.setState({
            age: '',
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
                    <p>{e.age}-{e.say}</p>
                </li>
            )
        })
    }
    del = (index) => {
        console.log('del-->', index)
        let list = this.state.list
        list.splice(index, 1)
        this.setState({list: list})
    }
    constructor(props) {
        super(props);
        console.log(this.props)
    }
    componentDidMount() {
        console.log('componentDidMount-->')
    }
    componentDidUpdate(prevProps, prevState, snapshot) {
        console.log('componentDidUpdate-->',prevProps,prevState,snapshot)
    }

    render() {
        return (
            <>
                <MouseListener/>
                <Context.Consumer>
                    {value => console.log(value)}
                </Context.Consumer>
                <p><input name="age" value={this.state.age} onChange={this.change}/></p>
                <p><input name="say" value={this.state.say} onChange={this.change}/></p>
                <p>
                    <Button type="primary" onClick={this.add}>添加</Button>
                </p>
                <List list={this.state.list} del={this.del}/>
            </>
        )
    }
}
Form.propTypes={
    demo:PropTypes.number.isRequired,
    demo1:PropTypes.string,
}
Form.defaultProps={
    demo1:'PropTypes.number.isRequired'
}

export default Form
