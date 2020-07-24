package pattern23.prototype;

import lombok.*;

import java.io.Serializable;

/**
 * TODO 原型模式-浅克隆
 *
 * @author Mr.He
 * 2020/7/14 15:55
 * mail crabapples.cn@gmail.com
 * qq 294046317
 * pc-name crabapples
 */
@ToString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Flower implements Cloneable, Serializable {
    private String name;
    private Color color;

    /**
     * 浅克隆
     */
    @Override
    protected Flower clone() throws CloneNotSupportedException {
        return (Flower) super.clone();
    }

    /**
     * 深克隆
     */
    protected Flower deepClone() throws CloneNotSupportedException {
        Flower flower = (Flower) super.clone();
        flower.color = this.color.clone();
        return flower;
    }
}
