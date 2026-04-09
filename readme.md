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
    ├── bean/                       # 빈 등록 관련
    │   └── before/                 # 내부 빈 등록기 로직 (사용자 정의 빈 등록하기 전)
    │   └── context/                # 빈 저장소 관련
    │   └── post/                   # 빈 후 처리기 로직
    ├── jdbc/                       # 커넥션 로직
    ├── proxy/                      # 프록시 로직
    ├── transacion/                 # 트랜잭션 로직
    └── event/                      # 이벤트 로직
```

---

## 개발 단계

- [x] 컴포넌트 스캔
- [x] 의존관계 주입 / 빈 생성
- [x] `@Transactional`
- [x] `@EventListener`
- [x] `@TransactionalEventListener`
- [ ] `@Async` - 진행 중
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