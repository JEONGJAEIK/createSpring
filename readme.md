# 미니 스프링
---

순수 자바로 스프링을 구현하는 프로젝트.
스프링의 클론코딩이 아닌 기반 지식을 활용해 스프링을 구현한다.

## 프로젝트 구조

```
src/main/java/com/createspring/
├── board/                          # 검증용 게시판 기능
└── spring/                         # 스프링 로직
    ├── annotation/                 # 어노테이션 정의
    ├── bean/                       # 빈 로직
    │   └── before/                 # 내부 빈 등록기 로직 (사용자 정의 빈 등록하기 전)
    │   └── post/                   # 빈 후 처리기 로직
    ├── proxy/                      # 프록시 로직
    ├── jdbc/                       # 트랜잭션/커넥션 로직
    └── event/                      # 이벤트 로직
```

---

## 개발 단계

- [x] 컴포넌트 스캔
- [x] 의존관계 주입 / 빈 생성
- [x] `@Transactional`
- [x] `@EventListener`
- [ ] `@TransactionalEventListener` - 진행 중
- [ ] `@Async`
- [ ] JDBC 템플릿
- [ ] 초기화/소멸 콜백
- [ ] 빈 스코프
- [ ] 트랜잭션 전파 속성
- [ ] 트랜잭션 격리 수준
- [ ] AOP
- [ ] MVC / HTTP 메시지 컨버터
- [ ] DataSourceDriverManager
- [ ] 스프링 예외 추상화

---

### TransactionalEventListener 구현 계획
별도의 프록시 로직은 없다.
이벤트 리스너와 동일한 publishEvent를 공유한다.
다만 이전 메서드 트랜잭션의 상태를 알아야한다.
인터셉터의 부가로직에서 이벤트 호출? - 안된다. 리스너와 이벤트의 매핑은 후처리기에서 이루어지고 인터셉터에 모든 경우를 나누는 것은 복잡하다.
publishEvent에서는 스레드로컬에 어댑터를 삽입하고 대기한다.
트랜잭션동기화매니저에서 커밋이 되는 경우 콜백으로 호출하는 방식.