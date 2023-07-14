package cn.master.backend.service;

import cn.master.backend.entity.SystemUser;
import com.github.yulichang.base.MPJBaseService;

/**
 * <p>
 * 用户信息表 服务类
 * </p>
 *
 * @author 11's papa
 * @since 2023-07-14
 */
public interface SystemUserService extends MPJBaseService<SystemUser> {

    /**
     * desc: 根据用户名称查询用户信息
     *
     * @param userName 用户名称
     * @return cn.master.backend.entity.SystemUser
     */
    SystemUser findUserByUserName(String userName);
}
