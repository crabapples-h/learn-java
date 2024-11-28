package create.prototype;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.*;

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
@AllArgsConstructor
@NoArgsConstructor
public class FlowerDeepClone implements Cloneable, Serializable {
    private String name;
    private Color color;

    /**
     * 浅克隆
     */
    @Override
    public FlowerDeepClone clone() throws CloneNotSupportedException {
        return (FlowerDeepClone) super.clone();
    }

    /**
     * 深克隆
     */
    public FlowerDeepClone deepClone() throws CloneNotSupportedException {
        FlowerDeepClone flower = (FlowerDeepClone) super.clone();
        flower.color = this.color.clone();
        return flower;
    }

    /**
     * 序列化方式实现深拷贝
     * CPU密集型操作，会对性能有较大影响，不建议使用
     */
    public FlowerDeepClone serializableClone() throws IOException, ClassNotFoundException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
        objectOutputStream.writeObject(this);
        byte[] byteArray = byteArrayOutputStream.toByteArray();
        ObjectInputStream objectInputStream = new ObjectInputStream(new ByteArrayInputStream(byteArray));
        FlowerDeepClone obj = (FlowerDeepClone) objectInputStream.readObject();
        objectOutputStream.close();
        objectInputStream.close();
        return obj;
    }

    public String toString() {
        return "Flower(name=" + this.getName() + ", color=" + this.getColor() + ")";
    }
}
