package ml.dreamingfire;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Method;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
/**
 * 服务类，主要用于注册服务并接收请求处理
 * */
public class Server {
    //线程池
    private static ExecutorService executor = Executors.newFixedThreadPool(10);
    //服务注册中心
    private static final HashMap<String, Class<?>> serviceRegistry = new HashMap<String, Class<?>>();
    /**
     * 注册服务实例
     * @Param serviceInterface 应用服务接口
     * @Param impl 服务接口实例
     * */
    public void register(Class<?> serviceInterface, Class<?> impl) {
        serviceRegistry.put(serviceInterface.getName(), impl);
    }
    /**
     * 启动服务
     * @param port 端口号
     * */
    public void start(int port) throws IOException{
        final ServerSocket server = new ServerSocket();
        server.bind(new InetSocketAddress(port));
        System.out.println("服务已启动");
        while(true) {
            executor.execute(new Runnable() {
                /*处理远程调用请求*/
                public void run() {
                    // TODO Auto-generated method stub
                    Socket socket = null;
                    ObjectInputStream input = null;
                    ObjectOutputStream output = null;
                    try {
                        socket = server.accept();
                        //接收到服务调用请求，将码流反序列化定位具体服务
                        input = new ObjectInputStream(socket.getInputStream());
                        String serviceName = input.readUTF();
                        String methodName = input.readUTF();
                        Class<?>[] parameterTypes = (Class<?>[]) input.readObject();
                        Object[] arguments = (Object[]) input.readObject();
                        Class<?> serviceClass = serviceRegistry.get(serviceName);
                        if(serviceClass == null) {
                            throw new ClassNotFoundException(serviceName + " 未找到");
                        }
                        Method method = serviceClass.getMethod(methodName, parameterTypes);
                        //调用获取结果
                        Object result = method.invoke(serviceClass.newInstance(), arguments);
                        output = new ObjectOutputStream(socket.getOutputStream());
                        output.writeObject(result);
                    } catch(Exception e) {
                        e.printStackTrace();
                    } finally {
                        //关闭资源
                        try {
                            if(socket != null) {socket.close();}
                            if(input != null)  {input.close();}
                            if(output != null) {output.close();}
                        } catch(IOException ex) {
                            ex.printStackTrace();
                        }
                    }
                }
            });
        }
    }
}
