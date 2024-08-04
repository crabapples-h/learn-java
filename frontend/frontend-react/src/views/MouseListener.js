import React from 'react'
import PropTypes from 'prop-types'

class MouseListener extends React.Component {
    constructor(props) {
        super(props);
    }

    state = {
        x: 0,
        y: 0,
    }

    setMouseLocation = (e) => {
        this.setState({
            x: e.clientX,
            y: e.clientY
        })
    }

    componentDidMount() {
        window.addEventListener('mousemove', this.setMouseLocation)
    }

    componentWillUnmount() {
        window.removeEventListener('mousemove', this.setMouseLocation)
    }

    render() {
        return this.props.children(this.state)
    }
}

MouseListener.propTypes = {
    children: PropTypes.func
}

MouseListener.defaultProps = {
    children: (e) => {
        console.log("未传入对应渲染函数，使用默认模式渲染")
        return <p>x:{e.x},y:{e.y}</p>
    }
}
export default MouseListener
