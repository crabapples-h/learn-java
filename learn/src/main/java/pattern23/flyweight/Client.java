package pattern23.flyweight;
/**
 * TODO 享元模式(多用于不可变对象的共享，防止获取到对象之后对对象进行修改操作)
 * 通过共享技术有效的支持大量细粒度的对象
 * 如果有大量类似的对象，可以节省大量的内存及CPU资源
 *
 * @author Mr.He
 * 2023/2/25 18:04
 * e-mail crabapples.cn@gmail.com
 * qq 294046317
 * pc-name mrhe
 */
public class Client {
    public static void main(String[] args) {
        // 第一次获取时执行创建
        TreeNode treeNode01 = new TreeNode(0, 1, TreeFactory.getTree("tree01", "这是tree01"));
        // 第二次获取时会直接将第一次创建的对象共享
        TreeNode treeNode02 = new TreeNode(0, 2, TreeFactory.getTree("tree01", "这是tree01"));
        System.out.println(treeNode01);
        System.out.println(treeNode02);
        System.out.println(treeNode01.getTree());
        System.out.println(treeNode02.getTree());
    }
}