// custom/CustomRenderer.js
export default class CustomRenderer {
    constructor(eventBus, graphicsFactory) {
        this._graphicsFactory = graphicsFactory;
        eventBus.on('render.shape', this.drawTriangle.bind(this));
    }

    // 绘制三角形
    drawTriangle(evt) {
        const { element, gfx } = evt;
        if (element.type === 'activiti:TriangleTask') {  // 匹配新类型
            const path = this._createTrianglePath(element);
            const attrs = { fill: '#FFD700', stroke: '#FFA500' };
            this._graphicsFactory.draw('path', attrs, gfx);
            evt.handled = true;
        }
    }

    _createTrianglePath(element) {
        const { width, height } = element;
        return `M ${width/2} 0 L ${width} ${height} L 0 ${height} Z`;
    }
}

CustomRenderer.$inject = ['eventBus', 'graphicsFactory'];
