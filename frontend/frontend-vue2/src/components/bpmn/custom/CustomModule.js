// custom/CustomModule.js
import CustomRenderer from './CustomRenderer';
import CustomPalette from "@comp/bpmn/custom/CustomPalette";

export default {
    __init__: ['customRenderer', 'customPalette'],
    customRenderer: ['type', CustomRenderer],
    customPalette: ['type', CustomPalette]
};
