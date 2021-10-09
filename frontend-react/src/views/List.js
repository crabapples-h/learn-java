import React from 'react'
import {Button} from 'antd';
class List extends React.Component {
    render() {
        return (
            <ul>{this.props.list.map((e,index) =>
                <li key={index}>
                    <p>
                        <span>{e.age}:{e.say}</span>
                        <Button onClick={()=>{this.props.del(index)}}>删除</Button>
                    </p>
                </li>)}
            </ul>
        )
    }
}

export default List
