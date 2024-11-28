package structure.flyweight;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * TODO 享元模式
 *
 * @author Mr.He
 * 2023/2/25 18:04
 * e-mail crabapples.cn@gmail.com
 * qq 294046317
 * pc-name mrhe
 */
@Getter
@Setter
@ToString
public class TreeFactory {
    // 使用ConcurrentHashMap可以保证在多线程的环境下数据的可靠性
    private static Map<String, Tree> treeMap = new ConcurrentHashMap<>();

    // 根据名称获取对象，如果存在就直接获取，如果不存在就新建
    public static Tree getTree(String name, String data) {
        if (treeMap.containsKey(name)) {
            return treeMap.get(name);
        }
        Tree tree = new Tree(name, data);
        treeMap.put(name, tree);
        return tree;
    }
}
