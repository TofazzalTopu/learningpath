//package com.gr.learningpath.config.secuirty;
//
//import com.gr.learningpath.api.request.user.LearningPathUser;
//import com.gr.learningpath.domain.User;
//import com.gr.learningpath.service.UserService;
//import lombok.RequiredArgsConstructor;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.stereotype.Component;
//import org.springframework.transaction.annotation.Transactional;
//
//@Component("userDetailsService")
//@RequiredArgsConstructor
//public class UserDetailsServiceImpl implements UserDetailsService {
//
//    private final UserService userService;
//
//    @Override
//    @Transactional(readOnly = true)
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        try {
//            User user = userService.findUserByUsername(username);
//            return new LearningPathUser(user);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return null;
//    }
//}
