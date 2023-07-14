package cn.master.backend.security;

import cn.master.backend.entity.SystemUser;
import cn.master.backend.service.SystemUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * @author create by 11's papa on 2023/7/14-14:07
 */
@Component
@RequiredArgsConstructor
public class SysUserDetailsServiceImpl implements UserDetailsService {
    private final SystemUserService systemUserService;

    @Override
    public SysUserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        SystemUser systemUser = systemUserService.findUserByUserName(username);
        if (Objects.nonNull(systemUser)) {
            SysUserDetails sysUserDetails = new SysUserDetails(systemUser);
            // 角色集合
            Set<GrantedAuthority> authorities = new HashSet<>();
            // TODO: 2023/7/14 设置权限
            sysUserDetails.setAuthorities(authorities);
            return sysUserDetails;
        }
        return null;
    }
}
