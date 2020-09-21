2개의 AOP 어노테이션을 걸어두면 어드바이스는 어떻게 동작 할까?
===

@A와 @B 두개의 어노테이션을 만들고, MyAspect 라는 클래스 내에서 각각 a(), b() 로 어드바이스 하도록 할것이다.
이때, @A와 @B 둘다 걸려있는 메소드에 대해서 a()와 b() 동작 순서가 어떻게 될까?
어노테이션 순서를 바꿔서 @B, @A 로 등록하면 순서가 바뀔까?
아래 예시코드와 함께 알아보자.



## 예시 코드

#### 2개의 어노테이션 @A 와 @B 생성
```java
@Documented
@Target(ElementType.METHOD)
public @interface A {}

@Documented
@Target(ElementType.METHOD)
public @interface B {}
```

#### 하나의 메서드에 등록
```java
@Service
public class MyService {
  @A @B
  public void hello() {
    log.info("hello");
  }
}
```
#### Aspect 정의
```java
@Slf4j
@Aspect
@Component
public class MyAspect {
  @Around("@annotation(com.study.aop.demo.annotation.A)")
  public Object a(ProceedingJoinPoint pjp) throws Throwable {
    log.info("1");
    log.info("2");
    Object result = pjp.proceed();
    log.info("3");
    log.info("4");
    return result;
  }

  @Around("@annotation(com.study.aop.demo.annotation.B)")
  public Object b(ProceedingJoinPoint pjp) throws Throwable {
    log.info("5");
    log.info("6");
    Object result = pjp.proceed();
    log.info("7");
    log.info("8");
    return result;
  }
}
```

#### 결과
```
MyAspect              : 1
MyAspect              : 2
MyAspect              : 5
MyAspect              : 6
MyService             : hello
MyAspect              : 7
MyAspect              : 8
MyAspect              : 3
MyAspect              : 4
```

## 결론
마치 a 가 b 를 호출 한 듯한 동작을 보여주고 있다.
실제로 동작이 메소드 콜 스택에 2개의 어드바이스인 a() 와 b() 순서대로 들어 있는것을 볼 수 있다.
![](https://i.imgur.com/hHnRA5t.png)
> 밑에서부터 읽으면 된다.


#### 참고1 
메소드명을 각각 d()와 c() 로 바꾸면 콜스택에 들어가는 순서가 d,c 순으로 들어간다. 위에 언급한것처럼 메소드명 순 이기 때문.

#### 참고2
어노테이션의 순서는 뒤바껴도 아무런 상관없다. 
> 예를들어 @B, @A
