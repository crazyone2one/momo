package cn.master.backend.security;

import cn.master.backend.entity.SystemUser;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

/**
 * @author create by 11's papa on 2023/7/14-13:47
 */
@Data
public class SysUserDetails implements UserDetails {
    private SystemUser systemUser;
    private Collection<GrantedAuthority> authorities;

    public SysUserDetails(SystemUser systemUser) {
        this.systemUser = systemUser;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return systemUser.getPassword();
    }

    @Override
    public String getUsername() {
        return systemUser.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return systemUser.getStatus();
    }

    @Override
    public boolean isAccountNonLocked() {
        return systemUser.getStatus();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return systemUser.getStatus();
    }

    @Override
    public boolean isEnabled() {
        return systemUser.getStatus();
    }
}
