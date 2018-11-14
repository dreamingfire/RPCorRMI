package ml.dreamingfire;
/**
 * HelloService 服务实现类
 * */
public class HelloServiceImple implements HelloService {

    public String hello(String name) {
        // TODO Auto-generated method stub
        System.out.println("收到来自 " + name + " 的消息");
        return "你好！" + name;
    }

}
