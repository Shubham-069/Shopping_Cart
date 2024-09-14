package com.ecom.config;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Service;

import com.ecom.model.UserDtls;
import com.ecom.repository.UserRepository;
import com.ecom.service.UserService;
import com.ecom.util.AppConstant;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Service
public class AuthFailureHandlerImpl extends SimpleUrlAuthenticationFailureHandler  {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private UserService userService;
	
	
	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException exception) throws IOException, ServletException {
		
		String email=request.getParameter("username");
		UserDtls userDtls=userRepository.findByEmail(email);
		
		if(userDtls != null) {
			if(userDtls.getIsEnable()) {
				
				if(userDtls.getAccountNonLocked()) {
					if(userDtls.getFailedAttempt()<AppConstant.ATTEMPT_TIME) {
						userService.increaseFailedAttempt(userDtls);
					}else {
						userService.userAccountLock(userDtls);
						exception=new LockedException("Your account is locked || Failed Attempt 3");
					}
				}else {
					if(userService.unlockAccountTimeExpired(userDtls)) {
						exception=new LockedException("Your account is unlocked || Please try to login");
					}else {
						exception=new LockedException("Your account is locked || Please try after sometimes");
					}
				}
			}else {
				exception=new LockedException("Your account is inactive");
			}
		} else {
			exception = new LockedException("Email & password is invalid");
		}
		super.setDefaultFailureUrl("/signin?error");
		super.onAuthenticationFailure(request, response, exception);
	}

	@Override
	public void setDefaultFailureUrl(String defaultFailureUrl) {
		// TODO Auto-generated method stub
		super.setDefaultFailureUrl(defaultFailureUrl);
	}

	@Override
	protected boolean isUseForward() {
		// TODO Auto-generated method stub
		return super.isUseForward();
	}

	@Override
	public void setUseForward(boolean forwardToDestination) {
		// TODO Auto-generated method stub
		super.setUseForward(forwardToDestination);
	}

	@Override
	public void setRedirectStrategy(RedirectStrategy redirectStrategy) {
		// TODO Auto-generated method stub
		super.setRedirectStrategy(redirectStrategy);
	}

	@Override
	protected RedirectStrategy getRedirectStrategy() {
		// TODO Auto-generated method stub
		return super.getRedirectStrategy();
	}

	@Override
	protected boolean isAllowSessionCreation() {
		// TODO Auto-generated method stub
		return super.isAllowSessionCreation();
	}

	@Override
	public void setAllowSessionCreation(boolean allowSessionCreation) {
		// TODO Auto-generated method stub
		super.setAllowSessionCreation(allowSessionCreation);
	}
	

}
