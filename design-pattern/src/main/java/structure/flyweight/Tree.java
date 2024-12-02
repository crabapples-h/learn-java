package structure.flyweight;

import lombok.Getter;
import lombok.ToString;

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
@ToString
public class Tree {
    private final String name;
    private final String Data;

    public Tree(String name, String Data) {
        this.name = name;
        this.Data = Data;
    }

}
