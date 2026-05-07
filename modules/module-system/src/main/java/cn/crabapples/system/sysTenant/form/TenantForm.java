package cn.crabapples.system.sysTenant.form;

import cn.crabapples.common.base.BaseForm;
import cn.crabapples.system.sysTenant.entity.SysTenant;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

/**
 * TODO 系统租户Form
 *
 * @author Mr.He
 * 2025-01-09 23:20
 * e-mail crabapples.cn@gmail.com
 * qq 294046317
 * pc-name mshe
 */
@Getter
@Setter
public class TenantForm extends BaseForm<SysTenant> {
    private String id;
    private String name;

    @Override
    public SysTenant toEntity() {
        SysTenant entity = SysTenant.create();
        BeanUtils.copyProperties(this, entity);
        return entity;
    }
}
