package com.min.app08;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.min.app08.entity.Menu;

@SpringBootTest
class C_EntityLifeCycleTests {

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
  void 비영속_test() {
    
    // 새로만든 엔티티는 영속성 컨텍스트에 저장되지 않습니다.
    // 즉, 비영속성 엔티티입니다.
    Menu newMenu = new Menu();
    newMenu.setMenuCode(1);
    newMenu.setMenuName("열무김치라떼");
    newMenu.setMenuPrice(4500);
    newMenu.setCategoryCode(8);
    newMenu.setOrderableStatus("Y");
    
    // find() 메소드를 이용해서 조회한 엔티티는 영속성 컨텍스트에 저장합니다.
    // 즉, 영속 엔티티입니다.
    int menuCode = 1;
    Menu foundMenu = entityManager.find(Menu.class, menuCode);
    
    // 비영속 엔티티와 영속 엔티티는 서로 다른 객체이기 때문에 둘은 같지 않습니다.
    Assertions.assertThat(newMenu == foundMenu).isTrue();
  }	 

  @Test
  void 영속_test() {
  
    // 조회할 식별자
    Integer menuCode = 1;
    
    // 엔티티 조회
    // 일단, 영속성 컨텍스트에 menuCode 가 1 인 엔티티가 존재하는지 조회합니다.
    // 없으면 데이터베이스에서 조회해 옵니다 (SELECT 문이 실행됩니다.)
    // 조회한 엔티티는 영속성 컨텍스트에 저장합니다.
    Menu foundMenu1 = entityManager.find(Menu.class, menuCode);
    
    // 엔티티 조회
    // 일단, 영속성 컨텍스트에 menuCode 가 1 인 엔티티가 존재하는지 조회합니다.
    // 있으므로 해당 엔티티를 사용합니다.
    Menu foundMenu2 = entityManager.find(Menu.class, menuCode);    
    
    // 두 엔티티 객체 비교
    Assertions.assertThat(foundMenu1 == foundMenu2).isTrue();
  }
  
  @Test
  void 영속_추가_test() {
    
    // 테스트를 위해서 AUTO_INCREMENT 동작을 잠시 중지하고 진행합니다. (Menu 엔티티에서 @GeneratedValue를 잠시 주석처리 합니다.)
    
    // 영속석 컨텍스트에 추가할(Not 관계형 데이터베이스) 엔티티 생성
    Menu newMenu = new Menu();
    newMenu.setMenuCode(999);
    newMenu.setMenuName("갈치파르페");
    newMenu.setMenuPrice(10000);
    newMenu.setCategoryCode(7);
    newMenu.setOrderableStatus("Y");
    
    // 영속성 컨텍스트에 엔티티 추가
    entityManager.persist(newMenu);

    // 엔티티 조회
    // 일단, 영속성 컨텍스트에 menuCode 가 999 인 엔티티가 존재하는지 조회합니다.  
    // 있으므로 해당 엔티티를 사용합니다. (*db에서 SELECT를 하지 않는다*)
    Integer menuCode = 999;
    Menu foundMenu = entityManager.find(Menu.class, menuCode);
    
    // 두 엔티티 비교
    Assertions.assertThat(newMenu == foundMenu).isTrue();
    
  }
  
  @Test
  void 영속_수정_test() {
    
    Menu newMenu = new Menu();
    newMenu.setMenuCode(999);
    newMenu.setMenuName("갈치파르페");
    newMenu.setMenuPrice(10000);
    newMenu.setCategoryCode(7);
    newMenu.setOrderableStatus("Y");
  
    //-= newMenu 가 비영속에서 영속으로 변경됨
    entityManager.persist(newMenu);
    //-= 영속인 newMenu 값을 변경함
    newMenu.setMenuName("우럭마카롱");
    
    //-= 영속성 컨텍스트에 newMenu 있으므로 이걸 사용
    Menu foundMenu = entityManager.find(Menu.class, 999);
    
    //=- 그러므로 같다 !!!!
    Assertions.assertThat(newMenu.getMenuName()).isEqualTo("우럭마카롱");
    Assertions.assertThat(foundMenu.getMenuName()).isEqualTo("우럭마카롱");
    
    //-= 영속, 비영속은 포인터라 생각하면됨
  }
  
  
  
}
