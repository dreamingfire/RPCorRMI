package ml.dreamingfire;

import java.io.IOException;
import java.net.InetSocketAddress;

public class ClientApp {
    public static void main(String[] args) throws IOException{
        String host = "localhost";
        int port = 8020;
        HelloService service = Client.get(HelloService.class, new InetSocketAddress(host, port));
        if(service instanceof Exception) {
            ((Exception) service).printStackTrace();
            return ;
        }
        String rs = service.hello("xxx");
        System.out.println(rs);
    }
}