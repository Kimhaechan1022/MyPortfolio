package com.myportfolio.user.service.impl;

import com.myportfolio.components.MailComponents;
import com.myportfolio.user.exceptions.PasswordNotSameException;
import com.myportfolio.user.exceptions.UserEmailNotAuthenticationException;
import com.myportfolio.user.model.RegisterInput;
import com.myportfolio.user.model.ResetPasswordInput;
import com.myportfolio.user.model.UserInformation;
import com.myportfolio.user.repository.UserRepository;
import com.myportfolio.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final MailComponents mailComponents;

    @Override
    public boolean register(RegisterInput registerInput) {

        Optional<UserInformation> optionalUser = userRepository.findById(registerInput.getUserId());
        if(optionalUser.isPresent()){
            return false;
        }
        String encPassword = BCrypt.hashpw(registerInput.getPassword(), BCrypt.gensalt());
        String emailAuthenticationKey = UUID.randomUUID().toString();

        UserInformation newUser = UserInformation.builder()
                .userId(registerInput.getUserId())
                .password(encPassword)
                .userName(registerInput.getUserName())
                .birth(registerInput.getBirth())
                .phone(registerInput.getPhone())
                .email(registerInput.getEmail())
                .isEmailAuthentication(false)
                .emailAuthenticationKey(emailAuthenticationKey)
                .build();
        userRepository.save(newUser);

        String email = registerInput.getEmail();
        String subject = "MyPortfolio, ?????? ?????? ?????? ????????? ??????";
        String content = "?????? ????????? ???????????? ????????? ???????????????\n" +
                    "http://localhost:1234/user/email_auth?key=" + emailAuthenticationKey + "'";
        mailComponents.sendMail(email, subject, content);

        return true;
    }

    @Override
    public boolean emailAuthentication(String emailAuthenticationKey) {

        Optional<UserInformation> optionalUser = userRepository.findByEmailAuthenticationKey(emailAuthenticationKey);
        if(!optionalUser.isPresent()){
            return false;
        }
        UserInformation resultUser = optionalUser.get();
        resultUser.setEmailAuthentication(true);
        userRepository.save(resultUser);

        return true;
    }

    @Override
    public boolean sendResetPassword(ResetPasswordInput resetPasswordInput) {
        Optional<UserInformation> optionalUserInformation = userRepository.findByUserIdAndUserName(resetPasswordInput.getUserId(),resetPasswordInput.getUserName());
        if(!optionalUserInformation.isPresent()){
            throw new UsernameNotFoundException("?????? ????????? ???????????? ????????????.");
        }
        UserInformation userInformation = optionalUserInformation.get();

        String resetUuid = UUID.randomUUID().toString();

        userInformation.setResetPasswordKey(resetUuid);
        userRepository.save(userInformation);

        String email = optionalUserInformation.get().getEmail();
        String subject = "MyPortfolio, ???????????? ????????? ?????? ?????????.";
        String text = "????????? ????????? ???????????? ???????????? ???????????? ????????? ?????????\n"
                + "http://localhost:1234/user/resetPassword?resetPasswordKey=" +resetUuid;
        mailComponents.sendMail(email,subject,text);
        return true;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserInformation> optionalUser = userRepository.findById(username);
        if (!optionalUser.isPresent()){
            throw new UsernameNotFoundException("?????? ????????? ???????????? ????????????.");
        }
        UserInformation userInformation = optionalUser.get();

        if(!userInformation.isEmailAuthentication()){
            throw new UserEmailNotAuthenticationException("????????? ????????? ?????? ????????? ????????????");
        }

        List<GrantedAuthority> grantedAuthorityList = new ArrayList<>();
        grantedAuthorityList.add(new SimpleGrantedAuthority("ROLE_USER"));


        return new User(userInformation.getUserId(),userInformation.getPassword(),grantedAuthorityList);
    }

    @Override
    public boolean resetPassword(String resetPasswordKey, String password, String rePassword){
        if(!password.equals(rePassword)){
            throw new PasswordNotSameException("??????????????? ????????? ??????????????? ???????????? ????????????.");
        }
        Optional<UserInformation> optionalUserInformation = userRepository.findByResetPasswordKey(resetPasswordKey);
        if(!optionalUserInformation.isPresent()){
            throw new UsernameNotFoundException("?????? ????????? ???????????? ????????????.");
        }
        UserInformation passwordResetUser = optionalUserInformation.get();

        String encPassword = BCrypt.hashpw(password, BCrypt.gensalt());
        passwordResetUser.setPassword(encPassword);
        userRepository.save(passwordResetUser);

        return true;
    }
}
