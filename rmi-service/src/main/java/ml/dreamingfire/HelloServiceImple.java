package ml.dreamingfire;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class HelloServiceImple extends UnicastRemoteObject implements HelloService{
    private static final long serialVersionUID = 1L;
    //在实例化这个类时，就导出了远程对象，该构造方法必需
    public HelloServiceImple() throws RemoteException{
        super();
    }
    //实现接口中的方法，这个时间不需要显 式抛出RemoteException异常了
    public String sayHello(String name) throws RemoteException {
        System.out.println("收到来自 " + name + " 的消息");
        return "你好！" + name;
    }
}
