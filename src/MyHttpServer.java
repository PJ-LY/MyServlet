import jdk.internal.org.objectweb.asm.tree.analysis.Value;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;

/**
 * @author pengjian
 * @date 2020-08-02 18:39
 * 服务器类，负责启动http服务
 */
public class MyHttpServer {
    //定义端口号
    private static final int port=8080;
    //初始化ServerSocket类对象为null
    private ServerSocket serverSocket =null;
    //
    public MyHttpServer() throws IOException {
        //创建ServerSocket实例
        serverSocket =new ServerSocket(port);
        System.out.println("HTTPServer startup OK!");
    }
    //
    public void work() throws Exception{
        while (true){
            try {
                Socket socket =serverSocket.accept();
                System.out.println(socket.getInputStream()+"请求流");
                MyServletRequest servletRequest =new MyServletRequest(socket.getInputStream());
                MyServletResponse servletResponse =new MyServletResponse(socket.getOutputStream());
                System.out.println("收到请求：\n"+"请求bir：\n"+servletRequest.getRequest()+"\n 请求end");

                String servletName = servletRequest.getServletName();
                HashMap<String, String> map =new HashMap<>();
                String value ="MyServletImpl";
                map.put(servletName,value);
                //通过反射获取servlet类对象的实例
                MyServlet myServlet =(MyServlet) Class.forName(map.get(servletName)).newInstance();
                //调用Myservlet接口方法
                myServlet.init();
                myServlet.service(servletRequest,servletResponse);
                myServlet.destroy();
                //关闭监听
                socket.close();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) throws Exception {
        MyHttpServer myHttpServer =new MyHttpServer();
        myHttpServer.work();
    }
}
