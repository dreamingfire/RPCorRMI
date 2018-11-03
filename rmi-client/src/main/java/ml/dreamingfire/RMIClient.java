package ml.dreamingfire;

import java.net.InetAddress;
import java.rmi.Naming;
import java.net.UnknownHostException;

public class RMIClient {
    public RMIClient(){}
    public static void main(String args[]){
        String host = "";
        try {
            host = InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        System.out.println(host);
        try{
            //像在使用本地对象方法那样，调用远程方法(根据桩获得远程对象)
            HelloService stub = (HelloService) Naming.lookup("rmi://localhost:8888/Hello");
            String response=stub.sayHello("xxx");
            System.out.println("response:"+response);
        }
        catch(Exception e){
            System.out.println("Client exception :"+ e.toString());
            e.printStackTrace();
        }
    }
}
