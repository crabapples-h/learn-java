<template>
  <a-tree-select
      v-model="value"
      style="width: 100%"
      :dropdown-style="{ maxHeight: '400px', overflow: 'auto' }"
      :tree-data="treeData"
      placeholder="Please select"
      tree-default-expand-all
  >
    <span v-if="key === '0-0-1'" slot="title" slot-scope="{ key, value }" style="color: #08c">
      Child Node1 {{ value }}
    </span>
  </a-tree-select>
</template>

<script>
const treeData = [
  {
    title: 'Node1',
    value: '0-0',
    key: '0-0',
    disabled: true,
    children: [
      {
        value: '0-0-1',
        key: '0-0-1',
        scopedSlots: {
          // custom title
          title: 'title',
        },
      },
      {
        title: 'Child Node2',
        value: '0-0-2',
        key: '0-0-2',
      },
    ],
  },
  {
    title: 'Node2',
    value: '0-1',
    key: '0-1',
    disabled: true,
  },
];
export default {
  data() {
    return {
      value: undefined,
      treeData,
    };
  },
  watch: {
    value(value) {
      console.log(value);
    },
  },
};
</script>
