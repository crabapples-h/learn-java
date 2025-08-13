const translations = {
    // Labels
    'Id': 'ID',
    'Name': '名称',
    'General': '常规',
    'Details': '详情',
    'Message Name': '消息名称',
    'Message': '消息',
    'Initiator': '发起人',
    'Asynchronous Continuations': '异步执行',
    'Asynchronous Before': '异步前',
    'Asynchronous After': '异步后',
    'Job Configuration': '任务配置',
    'Exclusive': '排他',
    'Job Priority': '任务优先级',
    'Retry Time Cycle': '重试时间周期',
    'Documentation': '文档',
    'Element Documentation': '元素文档',
    'History Configuration': '历史配置',
    'History Time To Live': '历史存活时间',
    'Forms': '表单',
    'Form Key': '表单 Key',
    'Form Fields': '表单字段',
    'Business Key': '业务 Key',
    'Form Field': '表单字段',
    'ID': 'ID',
    'Type': '类型',
    'Label': '标签',
    'Default Value': '默认值',
    'Validation': '校验',
    'Add Constraint': '添加约束',
    'Config': '配置',
    'Properties': '属性',
    'Add Property': '添加属性',
    'Value': '值',
    'Listeners': '监听器',
    'Execution Listener': '执行监听器',
    'Event Type': '事件类型',
    'Listener Type': '监听器类型',
    'Java Class': 'Java 类',
    'Expression': '表达式',
    'Must provide a value': '必须提供一个值',
    'Delegate Expression': '代理表达式',
    'Script': '脚本',
    'Script Format': '脚本格式',
    'Script Type': '脚本类型',
    'Inline Script': '内联脚本',
    'External Script': '外部脚本',
    'Resource': '资源',
    'Field Injection': '字段注入',
    'Extensions': '扩展',
    'Input/Output': '输入/输出',
    'Input Parameters': '输入参数',
    'Output Parameters': '输出参数',
    'Parameters': '参数',
    'Output Parameter': '输出参数',
    'Timer Definition Type': '定时器定义类型',
    'Timer Definition': '定时器定义',
    'Date': '日期',
    'Duration': '持续时间',
    'Cycle': '循环',
    'Signal': '信号',
    'Signal Name': '信号名称',
    'Escalation': '升级',
    'Error': '错误',
    'Link Name': '链接名称',
    'Condition': '条件',
    'Variable Name': '变量名',
    'Variable Event': '变量事件',
    'Specify more than one variable change event as a comma separated list.': '多个变量事件以逗号分隔',
    'Wait for Completion': '等待完成',
    'Activity Ref': '活动引用',
    'Version Tag': '版本标签',
    'Executable': '可执行',
    'External Task Configuration': '外部任务配置',
    'Task Priority': '任务优先级',
    'External': '外部',
    'Connector': '连接器',
    'Must configure Connector': '必须配置连接器',
    'Connector Id': '连接器 ID',
    'Implementation': '实现',
    'Assignee': '负责人',
    'Candidate Users': '候选用户',
    'Candidate Groups': '候选组',
    'Due Date': '到期日',
    'Follow Up Date': '跟踪日',
    'Priority': '优先级',
    'The follow up date as an EL expression (e.g. ${someDate} or an ISO date (e.g. 2015-06-26T09:54:00).': '跟踪日期必须是 EL 表达式或 ISO 日期',
    'The due date as an EL expression (e.g. ${someDate} or an ISO date (e.g. 2015-06-26T09:54:00).': '到期日必须是 EL 表达式或 ISO 日期',
    'Variables': '变量',
    'Candidate Starter Configuration': '候选启动器配置',
    'Candidate Starter Groups': '候选启动组',
    'Candidate Starter Users': '候选启动用户',
    'Tasklist Configuration': '任务列表配置',
    'Startable': '可启动',

    // Palette
    'Create StartEvent': '创建开始事件',
    'Create EndEvent': '创建结束事件',
    'Create IntermediateThrowEvent/BoundaryEvent': '创建中间/边界事件',
    'Create Gateway': '创建网关',
    'Create Task': '创建任务',
    'Create DataObjectReference': '创建数据对象',
    'Create DataStoreReference': '创建数据存储',
    'Create Group': '创建分组',
    'Create Intermediate/Boundary Event': '创建中间/边界事件',
    'Create Pool/Participant': '创建池/参与者',
    'Create expanded SubProcess': '创建可展开的子流程',
    'Append EndEvent': '追加结束事件',
    'Append Gateway': '追加网关',
    'Append Task': '追加任务',
    'Append Intermediate/Boundary Event': '追加中间/边界事件',

    // Tooltips
    'Activate the hand tool': '激活抓手工具',
    'Activate the lasso tool': '激活套索工具',
    'Activate the create/remove space tool': '激活空间工具',
    'Activate the global connect tool': '激活全局连接工具',
    'Remove': '移除',
    'Change type': '改变类型',
    'Connect using Sequence/MessageFlow or Association': '使用顺序/消息流或关联连接',
    'Append': '追加',

    // Context Pad
    'Append UserTask': '追加用户任务',
    'Append ServiceTask': '追加服务任务',
    'Append SendTask': '追加发送任务',
    'Append ReceiveTask': '追加接收任务',
    'Append ManualTask': '追加手动任务',
    'Append BusinessRuleTask': '追加业务规则任务',
    'Append ScriptTask': '追加脚本任务',
    'Append CallActivity': '追加调用活动',

    // Element Types
    'Start Event': '开始事件',
    'End Event': '结束事件',
    'Intermediate Throw Event': '中间抛出事件',
    'Exclusive Gateway': '排他网关',
    'Parallel Gateway': '并行网关',
    'Inclusive Gateway': '包容网关',
    'Complex Gateway': '复杂网关',
    'Event-based Gateway': '事件网关',
    'Task': '任务',
    'User Task': '用户任务',
    'Service Task': '服务任务',
    'Send Task': '发送任务',
    'Receive Task': '接收任务',
    'Manual Task': '手动任务',
    'Business Rule Task': '业务规则任务',
    'Script Task': '脚本任务',
    'Call Activity': '调用活动',
    'Sub Process (expanded)': '子流程（展开）',
    'Data Object Reference': '数据对象引用',
    'Data Store Reference': '数据存储引用',
    'Participant': '参与者',
    'Group': '分组',
};

const customTranslate = function (template, replacements) {
    replacements = replacements || {};
    template = translations[template] || template;
    return template.replace(/{([^}]+)}/g, function (_, key) {
        return replacements[key] || '{' + key + '}';
    });
}

export const initialDiagramXML = '<?xml version="1.0" encoding="UTF-8"?>' +
    '<bpmn:definitions xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" ' +
    'xmlns:bpmn="http://www.omg.org/spec/BPMN/20100524/MODEL" ' +
    'xmlns:bpmndi="http://www.omg.org/spec/BPMN/20100524/DI" ' +
    'xmlns:dc="http://www.omg.org/spec/DD/20100524/DC" ' +
    'targetNamespace="http://bpmn.io/schema/bpmn" ' +
    'id="Definitions_1">' +
    '<bpmn:process id="Process_1" isExecutable="false">' +
    '<bpmn:startEvent id="StartEvent_1"/>' +
    '</bpmn:process>' +
    '<bpmndi:BPMNDiagram id="BPMNDiagram_1">' +
    '<bpmndi:BPMNPlane id="BPMNPlane_1" bpmnElement="Process_1">' +
    '<bpmndi:BPMNShape id="_BPMNShape_StartEvent_2" bpmnElement="StartEvent_1">' +
    '<dc:Bounds height="36.0" width="36.0" x="173.0" y="102.0"/>' +
    '</bpmndi:BPMNShape>' +
    '</bpmndi:BPMNPlane>' +
    '</bpmndi:BPMNDiagram>' +
    '</bpmn:definitions>'

// 将翻译函数包装成 bpmn-js 的模块
export default {
    translate: ['value', customTranslate]
};

