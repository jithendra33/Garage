//package com.example.garage.security;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.authentication.AuthenticationProvider;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.AuthenticationException;
//import org.springframework.security.core.userdetails.User;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.stereotype.Component;
//
//@Component
//public class OTPAuthenticationProvider implements AuthenticationProvider {
//
//    @Autowired
//    private OTPService otpService;
//
//    @Autowired
//    private UserDetailsService userDetailsService;
//
//    @Override
//    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
//        String email = authentication.getName();
//        String otp = authentication.getCredentials().toString();
//
//        if (otpService.verifyOtp(email, otp)) {
//            UserDetails userDetails = userDetailsService.loadUserByUsername(email);
//            return new UsernamePasswordAuthenticationToken(userDetails, otp, userDetails.getAuthorities());
//        } else {
//            throw new UsernameNotFoundException("Invalid OTP");
//        }
//    }
//
//    @Override
//    public boolean supports(Class<?> authentication) {
//        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
//    }
//}
