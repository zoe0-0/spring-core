package hello.core.singleton;

import hello.core.AppConfig;
import hello.core.member.MemberServiceImpl;
import hello.core.member.MemoryMemberRepository;
import hello.core.order.OrderServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import static org.assertj.core.api.Assertions.assertThat;

public class ConfigurationSingletonTest {

    @Test
    void configurationTest(){
        ApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);

        MemberServiceImpl memberService = ac.getBean("memberService",MemberServiceImpl.class);
        OrderServiceImpl orderService = ac.getBean("orderService",OrderServiceImpl.class);
        MemoryMemberRepository memoryMemberRepository = ac.getBean("memberRepository",MemoryMemberRepository.class);

        //코드상으로는 여러번 new MemoryMemberRepository가 실행되는 것 같은데 정말 같은 객체일까?
        //테스트 해보면 모두 같은 인스턴스를 참조하고 있다
        System.out.println("memberService.getMemberRepository() = " + memberService.getMemberRepository());;
        System.out.println("orderService.getMemberRepository() = " + orderService.getMemberRepository());;
        System.out.println("memoryMemberRepository = " + memoryMemberRepository);

        assertThat(memberService.getMemberRepository()).isSameAs(memoryMemberRepository);
        assertThat(orderService.getMemberRepository()).isSameAs(memoryMemberRepository);

    }

    @Test
    void configurationDeep(){
        ApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);
        AppConfig bean = ac.getBean(AppConfig.class);

        System.out.println("bean = " + bean.getClass());
    }
}
