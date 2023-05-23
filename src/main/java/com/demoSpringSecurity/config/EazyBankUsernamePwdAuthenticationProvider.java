package com.demoSpringSecurity.config;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.demoSpringSecurity.entity.Authority;
import com.demoSpringSecurity.entity.Customer;
import com.demoSpringSecurity.repository.CustomerRepository;

@Component
public class EazyBankUsernamePwdAuthenticationProvider implements AuthenticationProvider{

	@Autowired
	private CustomerRepository customerrepository;
	
	@Autowired
	private PasswordEncoder passwordencoder;
	
	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		String username=authentication.getName();
		String pwd=authentication.getCredentials().toString();
		List<Customer> customer=customerrepository.findByEmail(username);
		if(customer.size()>0)
		{
			if(passwordencoder.matches(pwd, customer.get(0).getPwd()))
			{
//				List<GrantedAuthority> authorities=new ArrayList<>();
//				authorities.add(new SimpleGrantedAuthority(customer.get(0).getRole()));
				return new UsernamePasswordAuthenticationToken(username,pwd,getGrantedAuthorities(customer.get(0).getAuthorities()));
			}
			else
			{
				throw new BadCredentialsException("Invalid password");
			}
		}
		else
		{
			throw new BadCredentialsException("No user exists with the given details");
		}
		
	}
	
	
	private List<GrantedAuthority> getGrantedAuthorities(Set<Authority> authorities)
	{
		List<GrantedAuthority> grantedAuthorities=new ArrayList<>();
		for(Authority authority:authorities)
		{
			grantedAuthorities.add(new SimpleGrantedAuthority(authority.getName()));
		}
		return grantedAuthorities;
	}
	
	
	@Override
	public boolean supports(Class<?> authentication) {
		// TODO Auto-generated method stub
		return (UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication));
	}

}
