package cn.crabapples.spring.dao;

import cn.crabapples.spring.entity.OrderInfo;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * TODO
 *
 * @author Mr.He
 * 2020/3/22 1:57
 * e-mail crabapples.cn@gmail.com
 * qq 294046317
 * pc-name 29404
 */
public interface OrderInfoRepository extends JpaRepository<OrderInfo, String> {
}
