package ml.dreamingfire;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface HelloService extends Remote{
    //声明方法 时，必需显式地抛出RemoteException异常
    public String sayHello(String name) throws RemoteException;
}
