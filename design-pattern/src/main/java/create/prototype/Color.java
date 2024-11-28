package create.prototype;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

/**
 * 原型模式-浅克隆
 *
 * @author Mr.He
 * 2020/7/14 15:55
 * mail crabapples.cn@gmail.com
 * qq 294046317
 * pc-name crabapples
 */
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Color implements Cloneable, Serializable {
    private String color;

    @Override
    protected Color clone() throws CloneNotSupportedException {
        return (Color) super.clone();
    }

    public String toString() {
        return "Color(color=" + this.getColor() + ")";
    }
}
