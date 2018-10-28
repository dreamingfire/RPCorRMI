package ml.dreamingfire;

import java.io.IOException;

public class ServerApp {
    public static void main(String[] args) throws IOException{
        Server server = new Server();
        int port = 8020;
        //注册服务
        server.register(HelloService.class, HelloServiceImple.class);
        //启动服务并绑定端口
        server.start(port);
    }
}
