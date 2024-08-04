package pattern23.flyweight;

/**
 * TODO 享元模式
 *
 * @author Mr.He
 * 2023/2/25 18:04
 * e-mail crabapples.cn@gmail.com
 * qq 294046317
 * pc-name mrhe
 */
public class TreeNode {
    private final Integer x;
    private final Integer y;
    private final Tree tree;

    public TreeNode(Integer x, Integer y, Tree tree) {
        this.x = x;
        this.y = y;
        this.tree = tree;
    }

    public Integer getX() {
        return x;
    }

    public Integer getY() {
        return y;
    }

    public Tree getTree() {
        return tree;
    }
}
