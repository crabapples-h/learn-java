package pattern23.flyweight;

import lombok.AllArgsConstructor;
import lombok.Getter;

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
@AllArgsConstructor
public class TreeNode {
    private final Integer x;
    private final Integer y;
    private final Tree tree;
}
