package cn.crabapples.common.base;

import cn.idev.excel.FastExcel;
import cn.idev.excel.support.ExcelTypeEnum;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.io.IOException;
import java.util.List;
import java.util.Set;


/**
 * TODO 基础Controller，其他controller请继承此类
 *
 * @author Mr.He
 * 2019/9/21 18:28
 * e-mail crabapples.cn@gmail.com
 * qq 294046317
 * pc-name 29404
 */
public abstract class BaseController {
    @Resource
    private Validator validator;

    @Resource
    private HttpServletResponse response;

    /**
     * 属性校验的方法
     *
     * @param object 需要验证的对象
     */
    protected final void validator(Object object, Class<?>... groups) {
        Set<ConstraintViolation<Object>> constraintViolations = validator.validate(object, groups);
        for (ConstraintViolation<Object> constraintViolation : constraintViolations) {
            throw new ApplicationException(constraintViolation.getMessageTemplate());
        }
    }

    protected <T> void exportExcel(String fileName, List<T> list, Class<T> clazz) throws IOException {
        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding("utf8");
        response.setHeader("Content-disposition", "attachment;filename=" + fileName);
        ServletOutputStream outputStream = response.getOutputStream();
        FastExcel.write(outputStream, clazz)
                .useDefaultStyle(false)
                .excelType(ExcelTypeEnum.XLSX)
                .sheet("sheet")
                .doWrite(list);
    }
}
