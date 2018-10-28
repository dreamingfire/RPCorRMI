package ml.dreamingfire;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.net.InetSocketAddress;
import java.net.Socket;

/**
 * 客户端类，用于发送请求并获得回复
 * */
public class Client<T> {
    /**
     * 获得接口的远程调用
     * @param serviceInterface 服务接口
     * @param addr 请求地址
     * @return 服务接口的远程调用实例
     * */
    @SuppressWarnings("unchecked")
    public static <T> T get(final Class<T> serviceInterface, final InetSocketAddress addr) {
        T instance = (T) Proxy.newProxyInstance(serviceInterface.getClassLoader(),
                new Class<?>[] {serviceInterface},
                new InvocationHandler() {

                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                        // TODO Auto-generated method stub
                        Socket socket = null;
                        ObjectOutputStream output = null;
                        ObjectInputStream input = null;
                        try {
                            //连接服务端
                            socket = new Socket();
                            socket.connect(addr);
                            //将调用的接口类、方法名、参数列表等序列化后发送给服务端
                            output = new ObjectOutputStream(socket.getOutputStream());
                            output.writeUTF(serviceInterface.getName());
                            output.writeUTF(method.getName());
                            output.writeObject(method.getParameterTypes());
                            output.writeObject(args);
                            //同步阻塞等待服务端返回应答，获取应答后返回
                            input = new ObjectInputStream(socket.getInputStream());
                            return input.readObject();
                        } catch(Exception e) {
                            return e;
                        } finally {
                            if(socket != null) {socket.close();}
                            if(output != null) {output.close();}
                            if(input != null)  {input.close();}
                        }
                    }
                });
        return instance;
    }
}