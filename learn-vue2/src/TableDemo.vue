<template>
    <a-table
            :columns="columns"
            :data-source="data"
            bordered
            size="middle"
            :scroll="{ x: 'calc(700px + 50%)', y: 240 }"
    />
</template>
<script>
const columns = [
    {
        title: '序号',
        dataIndex: 'name',
        key: 'name',
        width: 100,
        fixed: 'left',
        filters: [
            {
                text: 'Joe',
                value: 'Joe',
            },
            {
                text: 'John',
                value: 'John',
            },
        ],
        onFilter: (value, record) => record.name.indexOf(value) === 0,
    },
    {
        title: '省份',
        dataIndex: 'name',
        key: 'name',
        width: 100,
        fixed: 'left',
        filters: [
            {
                text: 'Joe',
                value: 'Joe',
            },
            {
                text: 'John',
                value: 'John',
            },
        ],
        onFilter: (value, record) => record.name.indexOf(value) === 0,
    },
    {
        title: '门店数',
        dataIndex: 'name',
        key: 'name',
        width: 100,
        fixed: 'left',
        filters: [
            {
                text: 'Joe',
                value: 'Joe',
            },
            {
                text: 'John',
                value: 'John',
            },
        ],
        onFilter: (value, record) => record.name.indexOf(value) === 0,
    },
    {
        title: '员工数',
        dataIndex: 'name',
        key: 'name',
        width: 100,
        fixed: 'left',
        filters: [
            {
                text: 'Joe',
                value: 'Joe',
            },
            {
                text: 'John',
                value: 'John',
            },
        ],
        onFilter: (value, record) => record.name.indexOf(value) === 0,
    },
    {
        title: 'AI预警',
        children: [
            {
                title: '总数',
                dataIndex: 'age',
                key: 'age',
                width: 200,
                sorter: (a, b) => a.age - b.age,
            },
            {
                title: '员工着装',
                dataIndex: 'building',
                key: 'buildin'
            },
            {
                title: '员工行为',
                dataIndex: 'street',
                key: 'street',
                width: 200,
            },
            {
                title: '员工脱岗',
                dataIndex: 'street',
                key: 'street',
                width: 200,
            },
            {
                title: '营业时段',
                dataIndex: 'street',
                key: 'street',
                width: 200,
            },
            {
                title: '产品陈列',
                dataIndex: 'street',
                key: 'street',
                width: 200,
            },
            {
                title: '风险单',
                dataIndex: 'street',
                key: 'street',
                width: 200,
            },
            {
                title: '店面卫生',
                dataIndex: 'street',
                key: 'street',
                width: 200,
            },
            {
                title: '用电量',
                dataIndex: 'street',
                key: 'street',
                width: 200,
            },
            {
                title: '温湿度',
                dataIndex: 'street',
                key: 'street',
                width: 200,
            },
            {
                title: '水浸',
                dataIndex: 'street',
                key: 'street',
                width: 200,
            },
        ],
    },
    {
        title: '申诉通过数',
        dataIndex: 'name',
        key: 'name',
        width: 100,
        fixed: 'left',
        filters: [
            {
                text: 'Joe',
                value: 'Joe',
            },
            {
                text: 'John',
                value: 'John',
            },
        ],
        onFilter: (value, record) => record.name.indexOf(value) === 0,
    },
    {
        title: '计入预警数',
        dataIndex: 'name',
        key: 'name',
        width: 100,
        fixed: 'left',
        filters: [
            {
                text: 'Joe',
                value: 'Joe',
            },
            {
                text: 'John',
                value: 'John',
            },
        ],
        onFilter: (value, record) => record.name.indexOf(value) === 0,
    },

];

const data = [];
for (let i = 0; i < 100; i++) {
    data.push({
        key: i,
        name: 'John Brown',
        age: i + 1,
        street: 'Lake Park',
        building: 'C',
        number: 2035,
        companyAddress: 'Lake Street 42',
        companyName: 'SoftLake Co',
        gender: 'M',
    });
}

export default {
    data() {
        return {
            data,
            columns,
        };
    },
};
</script>
