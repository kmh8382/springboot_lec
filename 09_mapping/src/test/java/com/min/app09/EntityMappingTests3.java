package com.min.app09;

import java.util.Date;
import java.util.List;

import javax.persistence.AccessType;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Persistence;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.min.app09.entity3.User;
import com.min.app09.entity3.UserRole;

@SpringBootTest
class EntityMappingTests3 {

  // 엔티티 매니저 팩토리
  private static EntityManagerFactory entityManagerFactory;
  
  // 엔티티 매니저
  private EntityManager entityManager;
  
  // 전체 테스트를 시작하기 전에 엔ㅌ니티 메니저 팩토리를 생성합니다. (테스트 클래스가 동작하기 이전)
  @BeforeAll
  static void setEntityManagerFactory() throws Exception {
    entityManagerFactory = Persistence.createEntityManagerFactory("jpa_test");
  }
  
  // 개별 테스트 시작하기 전에 엔티티 매니저를 생성합니다. (테스트 메소드가 동작하기 이전)
  @BeforeEach
  void setEntityManager() throws Exception {
    entityManager = entityManagerFactory.createEntityManager();
  }
  
  // 전체 테스트가 종료되면 엔티티 매니저 팩토리를 소멸합니다. (테스트 클래스가 동작한 이후)
  @AfterAll
  static void closeEntityManagerFactory() throws Exception {
    entityManagerFactory.close();
  }
  
  // 개별 테스트가 종료될때마다 엔티티 매니저를 소멸합니다. (테스트 메소드가 동작한 이후)
  @AfterEach
  void closeEntityManager() throws Exception {
    entityManager.close();
  }
  
  
	@Test
	void 테이블_생성_테스트() {
	  
	  User user = new User();
	  user.setUserId(1);
	  user.setUserEmail("admin@gmail.com");
	  user.setUserPassword("admin");
	  user.setUserPhone("010-0000-0000");
	  user.setNickname("관리자");
	  user.setAddress("서울지 강남구 강남대로");
	  user.setCreateDt(new Date());
	  user.setUserRole(UserRole.ADMIN);	 	 
	  
	  EntityTransaction entityTransaction = entityManager.getTransaction();
	  entityTransaction.begin();
	  
	  try {
      
	    entityManager.persist(user);
	    entityTransaction.commit();
	    
    } catch (Exception e) {
      
      e.printStackTrace();
      entityTransaction.rollback();
      
    }
	  
	  // @Enumerated(EnumType.ORDINAL) 인 경우 userRole 은 INTEGER 타입입니다.
	  // @Enumerated(EnumType.STRING) 인 경우 userRole 은 VARCHAR 타입입니다.
	  User FoundUser = entityManager.find(User.class, 1);
	  System.out.println(FoundUser);	  
	  Assertions.assertThat(FoundUser.getNickname()).isEqualTo("관리자님");
	  
	  // user3 엔티티에서 nickname 필드를 조회합니다.
	  //nickname 필드는 AccessType.PROPERTY 설정되어 있으므로 getNickname()을 이용해서 조회가 가능합니다
	  String jpql = "SELECT u.nickname FROM user3 u WHERE u.userId = 1";
	  String nickname = entityManager.createQuery(jpql, String.class).getSingleResult();
	  Assertions.assertThat(nickname).isEqualTo("관리자님");
	  
	}

	
	
	
	
	
}
