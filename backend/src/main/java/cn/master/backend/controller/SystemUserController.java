package cn.master.backend.controller;

import cn.master.backend.dao.request.SigninRequest;
import cn.master.backend.security.JwtTokenUtil;
import cn.master.backend.security.SysUserDetails;
import cn.master.backend.security.SysUserDetailsServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 用户信息表 前端控制器
 * </p>
 *
 * @author 11's papa
 * @since 2023-07-14
 */
@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class SystemUserController {
    private final SysUserDetailsServiceImpl sysUserDetailsService;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenUtil jwtTokenUtil;

    @PostMapping("/login")
    public ResponseEntity<String> signIn(@RequestBody SigninRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
        SysUserDetails sysUserDetails = sysUserDetailsService.loadUserByUsername(request.getUsername());
        String accessToken = jwtTokenUtil.createAccessToken(sysUserDetails);
        return ResponseEntity.ok(accessToken);
    }
}
