package cn.crabapples.system.form;

import cn.crabapples.common.base.BaseForm;
import com.alibaba.fastjson.JSONObject;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * TODO 用户角色Form
 *
 * @author Mr.He
 * 2021/4/13 4:05
 * e-mail crabapples.cn@gmail.com
 * qq 294046317
 * pc-name mrhe
 */
@Getter
@Setter
public class RolesForm extends BaseForm {
    private String name;

    private List<String> menusList;


    @Override
    public String toString() {
        return JSONObject.toJSONString(this);
    }
}
