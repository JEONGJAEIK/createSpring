# 미니 스프링
---

순수 자바로 스프링을 구현하는 프로젝트.
스프링의 클론코딩이 아닌 기반 지식을 활용해 스프링을 구현한다.

## 프로젝트 구조

```
src/main/java/com/createspring/
├── board/                          # 검증용 게시판 로직
└── spring/                         # 스프링 로직
    ├── annotation/                 # 어노테이션 정의
    ├── bean/                       # 빈 로직
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
- [ ] `@EventListener` (진행 중)
- [ ] `@TransactionalEventListener`
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

### 이벤트리스너 구현 계획

1. AOP를 사용하지 않는다
2. 미리 구성된 팩토리(`SimpleEventListenerFactory`)가 리스너 목록을 관리한다
3. 이벤트 발생 시 리플렉션으로 리스너 메서드를 호출한다
