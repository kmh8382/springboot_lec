package com.min.app07.user.service;

import java.util.Map;

import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.min.app07.user.dto.SignupDto;
import com.min.app07.user.mapper.IUserMapper;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements IUserService {

  private final IUserMapper userMapper;
  private final PasswordEncoder passwordEncoder;
  
  @Override
  public Map<String, String> signup(SignupDto signupDto) {
    
    // 비밀번호 암호화
    signupDto.setUserPassword(passwordEncoder.encode(signupDto.getUserPassword()));
    
    // 반환할 데이터 (메시지, 가입 후 이동 경로)
    String message = null;
    String path = null;
    
    try {
      userMapper.insertUser(signupDto);
      message = "회원 가입 완료";
      path = "auth/login";
    } catch (DuplicateKeyException e) {
      e.printStackTrace();
      message = "중복된 아이디";
      path = "user/signup";
    } catch (Exception e) {
      e.printStackTrace();
      message = "서버 오류 발생";
      path = "user/signup";
    }
                  
    return Map.of("message", message, "path", path);
    
  }

}
