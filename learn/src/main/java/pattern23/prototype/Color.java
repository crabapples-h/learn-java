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
public class Color implements Cloneable, Serializable {
    private String color;

    @Override
    protected Color clone() throws CloneNotSupportedException {
        return (Color) super.clone();
    }
}
