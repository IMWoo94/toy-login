# toy-login

로그인 기능 만들어보기

- skils
    - Java17
    - SpringBoot 3.1.5
    - H2 DB
    - Spring Data JPA
    - thymeleaf
    - embedded tomcat

## 쿠키 사용

- branch(cookie) - LoginCookieController
- v1 : http request 에 담긴 Cookies 정보를 통해서 모든 쿠키 안에서 로그인 관련 쿠키 정보 확인 방식
- v2 : controller 에 파라미터 바인드 시 로그인 관련 쿠키 정보가 있다면 바로 바인드 되도록 @CookieValue 사용

## 세션 사용

- branch(session) - loginSessionController
- sessionID 를 Server 측에 저장하여 sessionId 를 통해서 사용자 식별 및 로그인 여부를 확인

## 토큰 사용

- branch(token) - loginController
