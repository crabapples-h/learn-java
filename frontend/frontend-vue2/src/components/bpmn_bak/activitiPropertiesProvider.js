// activitiPropertiesProvider.js
export default function ActivitiPropertiesProvider(propertiesPanel, translate) {
    this.getGroups = function(element) {
        return function(groups) {
            if (element.type === 'bpmn:UserTask') {
                groups.push({
                    id: 'activiti',
                    label: 'Activiti',
                    entries: [
                        {
                            id: 'assignee',
                            label: 'Assignee',
                            modelProperty: 'activiti:assignee',
                            type: 'text'
                        },
                        {
                            id: 'candidateUsers',
                            label: 'Candidate Users',
                            modelProperty: 'activiti:candidateUsers',
                            type: 'text'
                        }
                    ]
                });
            }
            return groups;
        };
    };
}
