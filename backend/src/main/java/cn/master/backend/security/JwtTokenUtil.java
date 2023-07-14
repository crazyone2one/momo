package cn.master.backend.security;

import cn.master.backend.entity.SystemUser;
import cn.master.backend.mapper.SystemUserMapper;
import cn.master.backend.utils.JsonUtils;
import com.fasterxml.jackson.core.type.TypeReference;
import io.jsonwebtoken.*;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.*;

/**
 * @author create by 11's papa on 2023/7/14-13:52
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class JwtTokenUtil {
    private final SystemUserMapper systemUserMapper;
    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private Long jwtExpiration;
    @Value("${jwt.prefix}")
    private String jwtPrefix;


    public String createAccessToken(SysUserDetails sysUserDetails) {
        return Jwts.builder().setId(sysUserDetails.getSystemUser().getId()).setSubject(sysUserDetails.getSystemUser().getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(Date.from(Instant.now().plus(jwtExpiration, ChronoUnit.MILLIS)))
                .setIssuer("MoMo")
                // 签名算法、密钥
                .signWith(SignatureAlgorithm.HS512, secret)
                .claim("authorities", JsonUtils.obj2String(sysUserDetails.getAuthorities()))
                .compact();
    }

    public SysUserDetails parseAccessToken(String token) {
        SysUserDetails sysUserDetails = null;
        if (Objects.nonNull(token)) {
            Claims claims = Jwts.parser().setSigningKey(secret).parseClaimsJwt(token).getBody();
            sysUserDetails = new SysUserDetails(systemUserMapper.selectById(claims.getId()));
            Set<GrantedAuthority> authorities = new HashSet<>();
            String authority = claims.get("authorities").toString();
            if (!StringUtils.isNoneBlank(authority)) {
                List<Map<String, String>> authorityList = JsonUtils.string2Obj(authority, new TypeReference<>() {
                });
                assert authorityList != null;
                for (Map<String, String> map : authorityList) {
                    if (!map.isEmpty()) {
                        authorities.add(new SimpleGrantedAuthority(map.get("authority")));
                    }
                }
            }
            sysUserDetails.setAuthorities(authorities);
        }
        return sysUserDetails;
    }

    public String getToken(HttpServletRequest httpServletRequest) {
        final String bearerToken = httpServletRequest.getHeader("Authorization");
        String tokenPrefix = jwtPrefix + " ";
        if (org.springframework.util.StringUtils.hasText(bearerToken) && bearerToken.startsWith(tokenPrefix)) {
            // The part after "Bearer "
            return bearerToken.substring(7);
        }
        return null;
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(secret).parseClaimsJws(token);
        } catch (SignatureException e) {
            log.info("Invalid JWT signature.");
            log.trace("Invalid JWT signature trace: {1}", e);
        } catch (MalformedJwtException e) {
            log.info("Invalid JWT token.");
            log.trace("Invalid JWT token trace: {1}", e);
        } catch (ExpiredJwtException e) {
            log.info("Expired JWT token.");
            log.trace("Expired JWT token trace: {1}", e);
        } catch (UnsupportedJwtException e) {
            log.info("Unsupported JWT token.");
            log.trace("Unsupported JWT token trace: {1}", e);
        } catch (IllegalArgumentException e) {
            log.info("JWT token compact of handler are invalid.");
            log.trace("JWT token compact of handler are invalid trace: {1}", e);
        }
        return false;
    }
}
