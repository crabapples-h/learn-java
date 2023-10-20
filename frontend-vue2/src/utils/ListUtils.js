/**
 * TODO list工具
 *
 * @author Ms.He
 * @description
 * 2023/10/20 11:12
 * e-mail crabapples.cn@gmail.com
 * qq 294046317
 * pc-name mshe
 */
// list转树
export function buildTree(flatList, parentId = null, parentKey = 'pid') {
    const tree = [];
    flatList.forEach(item => {
        if (item[parentKey] === parentId) {
            const children = buildTree(flatList, item.id);
            if (children.length) {
                item.children = children;
            } else {
                item.children = null
            }
            tree.push(item);
        }
    });
    return tree;
}

// 树转list
export function tree2list(list, data) {
    list.forEach(r => {
        data.push({id: r.id, name: r.name, pid: r.pid, sort: r.sort})
        tree2list(r.children, data)
    })
}
