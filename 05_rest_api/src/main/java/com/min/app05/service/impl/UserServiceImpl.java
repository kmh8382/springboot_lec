package com.min.app05.service.impl;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.min.app05.mapper.IUserMapper;
import com.min.app05.model.dto.UserDto;
import com.min.app05.service.IUserService;
import com.min.app05.util.PageUtil;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements IUserService {

  private final IUserMapper userMapper;
  private final PageUtil pageUtil;
    
  @Override
  public UserDto registUser(UserDto userDto) throws Exception {  
    userDto.setCreateDt(new Timestamp(System.currentTimeMillis()));
    userMapper.insertUser(userDto);
    return userDto;
  }

  @Override
  public UserDto modifyUser(UserDto userDto) throws Exception {
    userMapper.updateUser(userDto);
    return userDto;
  }
  
  @Override
  public int removeUser(int userId) throws Exception {
    return userMapper.deleteUser(userId);
  }
  
  @Override
  public List<UserDto> getUsers(HttpServletRequest request) throws Exception {
    
    // 페이징 처리를 위한 파라미터 page, display
    Optional<String> optPage = Optional.ofNullable(request.getParameter("page"));
    int page = Integer.parseInt(optPage.orElse("1"));
    Optional<String> optDisplay = Optional.ofNullable(request.getParameter("display"));
    int display = Integer.parseInt(optDisplay.orElse("20"));
    
    // 페이징 처리를 위한 게시글 개수 count
    int count = userMapper.selectUserCount();
    
    // 페이징 처리에 필요한 모든 변수 처리하기
    pageUtil.setPaging(page, display, count);
    
    // offset
    int offset = pageUtil.getOffset();
    
    // 정렬을 위한 파라미터 sort
    Optional<String> optSort = Optional.ofNullable(request.getParameter("sort"));
    String sort = optSort.orElse("DESC");
    
    // 게시글 목록 가져오기 (전달 : offset, display, sort 를 저장한 Map)
    return userMapper.selectUserList(Map.of("offset", offset
                                          , "display", display
                                          , "sort", sort));
  }
  
  @Override
  public UserDto getUserById(int userId) throws Exception {
    return userMapper.selectUserById(userId);
  }
}
