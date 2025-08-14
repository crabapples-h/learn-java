export const ActivitiProperties = {
    // 任务类型扩展
    taskTypes: [
        'userTask',
        'serviceTask',
        'scriptTask',
        'mailTask',
        'manualTask',
        'businessRuleTask',
        'receiveTask',
        'sendTask',
        'callActivity'
    ],

    // Activiti 特有属性配置
    getActivitiExtensionProperties(element) {
        const commonProps = {
            // 通用属性
            'activiti:assignee': {
                label: 'Assignee',
                type: 'string',
                description: '直接指定任务负责人'
            },
            'activiti:candidateUsers': {
                label: 'Candidate Users',
                type: 'string',
                description: '候选用户（逗号分隔）'
            },
            'activiti:candidateGroups': {
                label: 'Candidate Groups',
                type: 'string',
                description: '候选组（逗号分隔）'
            },
            'activiti:dueDate': {
                label: 'Due Date',
                type: 'string',
                description: '任务到期时间（ISO格式）'
            },
            'activiti:priority': {
                label: 'Priority',
                type: 'number',
                description: '任务优先级'
            }
        };

        if (element.type === 'bpmn:UserTask') {
            return {
                ...commonProps,
                'activiti:formKey': {
                    label: 'Form Key',
                    type: 'string',
                    description: '关联的表单KEY'
                }
            };
        }

        if (element.type === 'bpmn:ServiceTask') {
            return {
                ...commonProps,
                'activiti:class': {
                    label: 'Java Class',
                    type: 'string',
                    description: '实现类的全限定名'
                },
                'activiti:expression': {
                    label: 'Expression',
                    type: 'string',
                    description: '执行表达式'
                },
                'activiti:delegateExpression': {
                    label: 'Delegate Expression',
                    type: 'string',
                    description: '委托表达式'
                }
            };
        }

        return commonProps;
    },

    // 监听器配置
    getListenerProperties() {
        return {
            'activiti:executionListener': {
                label: 'Execution Listeners',
                type: 'list',
                items: {
                    'activiti:event': {
                        label: 'Event',
                        type: 'string',
                        values: ['start', 'end', 'take']
                    },
                    'activiti:class': {
                        label: 'Class',
                        type: 'string'
                    },
                    'activiti:expression': {
                        label: 'Expression',
                        type: 'string'
                    },
                    'activiti:delegateExpression': {
                        label: 'Delegate Expression',
                        type: 'string'
                    }
                }
            },
            'activiti:taskListener': {
                label: 'Task Listeners',
                type: 'list',
                items: {
                    'activiti:event': {
                        label: 'Event',
                        type: 'string',
                        values: ['create', 'assignment', 'complete', 'delete']
                    },
                    'activiti:class': {
                        label: 'Class',
                        type: 'string'
                    },
                    'activiti:expression': {
                        label: 'Expression',
                        type: 'string'
                    },
                    'activiti:delegateExpression': {
                        label: 'Delegate Expression',
                        type: 'string'
                    }
                }
            }
        };
    }
};
