package com.quiet.onterview.security;

import java.util.ArrayList;
import java.util.Collection;
import lombok.Getter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.CredentialsContainer;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.util.StringUtils;


/**
 * Authentication은 Security에서 중요한 역할을 한다.
 * Authentication 인터페이스를 보면 isAuthenticated()와 getAuthorities()을 통해 해당 사용자가 맞는 Role과 인증이 되었는지를 확인한다.
 * 그렇기 때문에 isAuthenticated()은 401과 관련이 있다. false를 return 하게 된다면 해당 사용자는 무조건 인증 되지 않는 사용자임을 나타낸다. 적절하게 바꿔주자.
 * getAuthorities()는 403에 관련 되어 있다. 해당 메소드에서 Null을 return 하게 된다면 무조건 forbidden인 상태가 되는 것이다.
 * 이러한 Authentication의 정보를 저장하고 이후에 지우는 것이 credentialsContainer 인터페이스이다.
 * 필수 구현체인 eraseCredentials()를 구현해주므로 요청 종료 시에 해당 정보를 모두 지워준다.
 *
 * 정리를 하자면 spring context holder에 있는 Authentication을 두 가지 필수 요소로 인증에 대한 검사가 이루어진다.
 * 1. isAuthenticated() 했을 때의 반환 값 - 401(인증)
 * 2. getAuthorities()를 했을 때 반환 값 - 403(인가)
 */
@Getter
public class SecurityMemberAuthentication implements Authentication, CredentialsContainer {

    private SecurityUser securityUser;
    private Collection<GrantedAuthority> authorities;
    private boolean authenticated = false;

    public SecurityMemberAuthentication(SecurityUser securityUser) {
        this.securityUser = securityUser;
        this.authorities = createAuthorities(securityUser.getRoles());
        setAuthenticated(true);
    }

    public SecurityMemberAuthentication() {
        setAuthenticated(false);
    }

    public void setSecurityUser(SecurityUser securityUser) {
        this.securityUser = securityUser;
        this.authorities = createAuthorities(securityUser.getRoles());
        setAuthenticated(true);
    }
    @Override
    public boolean isAuthenticated() {
        return this.authenticated;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getDetails() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return this.securityUser;
    }

    @Override
    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
        this.authenticated = isAuthenticated;
    }

    @Override
    public String getName() {
        return securityUser.getEmail();
    }

    @Override
    public void eraseCredentials() {
        eraseSecret(getCredentials());
        eraseSecret(getPrincipal());
        eraseSecret(this.securityUser);
    }

    private void eraseSecret(Object secret) {
        if (secret instanceof CredentialsContainer) {
            ((CredentialsContainer) secret).eraseCredentials();
        }
    }

    private Collection<GrantedAuthority> createAuthorities(String roles) {
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        for(String role : roles.split(",")) {
            if(!StringUtils.hasText(role)) {
                continue;
            }
            authorities.add(new SimpleGrantedAuthority(role));
        }
        return authorities;
    }
}
