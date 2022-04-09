package com.ivanxc.hse.dashboardrest.jwt;

import com.ivanxc.hse.dashboardrest.entity.Role;
import java.util.Collection;
import java.util.Set;
import lombok.Data;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

@Data
public class JwtAuthentication implements Authentication {

    private boolean authenticated;
    private long id;
    private String username;
    private Set<Role> roles;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() { return roles; }

    @Override
    public Object getCredentials() { return null; }

    @Override
    public Object getDetails() { return null; }

    @Override
    public Object getPrincipal() { return id; }

    @Override
    public boolean isAuthenticated() { return authenticated; }

    @Override
    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
        this.authenticated = isAuthenticated;
    }

    @Override
    public String getName() { return username; }

}
