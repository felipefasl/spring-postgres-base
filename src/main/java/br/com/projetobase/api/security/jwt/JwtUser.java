package br.com.projetobase.api.security.jwt;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * @author Felipe
 */
public class JwtUser implements UserDetails {

	private static final long serialVersionUID = -268046329085485932L;

	private String username;
	private String password;
	private Collection<? extends GrantedAuthority> authorities;

	public JwtUser(String username, String password, Collection<? extends GrantedAuthority> authorities) {
		this.username = username;
		this.password = password;
		this.authorities = authorities;
	}

	@Override
	public String getUsername() {
		return username;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

}
