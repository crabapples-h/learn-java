// custom/CustomPalette.js
export default class CustomPalette {
    constructor(create, elementFactory, palette) {
        this._create = create;
        this._elementFactory = elementFactory;

        palette.registerProvider(this);
    }

    getPaletteEntries() {
        return {
            'create-triangle': {
                group: 'activity',
                className: 'bpmn-icon-triangle',
                title: 'Create Triangle',
                action: {
                    dragstart: this._createTriangle.bind(this),
                    click: this._createTriangle.bind(this)
                }
            }
        };
    }

    _createTriangle(event) {
        const shape = this._elementFactory.createShape({
            type: 'activiti:TriangleTask',  // 使用新类型
            width: 80,
            height: 80
        });
        this._create.start(event, shape);
    }
}

CustomPalette.$inject = ['create', 'elementFactory', 'palette'];
