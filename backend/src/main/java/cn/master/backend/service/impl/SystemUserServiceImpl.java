package cn.master.backend.service.impl;

import cn.master.backend.entity.SystemUser;
import cn.master.backend.mapper.SystemUserMapper;
import cn.master.backend.service.SystemUserService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.yulichang.base.MPJBaseServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户信息表 服务实现类
 * </p>
 *
 * @author 11's papa
 * @since 2023-07-14
 */
@Service
public class SystemUserServiceImpl extends MPJBaseServiceImpl<SystemUserMapper, SystemUser> implements SystemUserService {

    @Override
    public SystemUser findUserByUserName(String userName) {
        return baseMapper.selectOne(
                new QueryWrapper<SystemUser>().lambda().eq(SystemUser::getUsername, userName)
                        .eq(SystemUser::getStatus, true).eq(SystemUser::getDeleted, false)
        );
    }
}
