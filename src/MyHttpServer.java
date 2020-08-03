import interfaces.HttpMethod;
import interfaces.WebServlet;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

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
    public void work(HttpMethod method ) throws Exception{
        while (true) {
//            ClassLoader classLoader = MyServletImpl.class.getClassLoader();
//            String path1 = classLoader.getResource("").getPath();
//            System.out.println(path1);

            //此处应获取接口实现类类路径，这里写死了
            String tempClassPath="MyServletImpl";
            test(HttpMethod.GET,tempClassPath);
        }
    }

    public  void test(HttpMethod method,String tempClassPath) throws IOException {
        Socket socket = null;
        try {
            socket = serverSocket.accept();
        } catch (IOException e) {
            e.printStackTrace();
        }
        MyServletRequest servletRequest =new MyServletRequest(socket.getInputStream());
        System.out.println("收到请求："+servletRequest.getRequest());
        MyServletResponse servletResponse =new MyServletResponse(socket.getOutputStream());
        String path = servletRequest.getServletName();
        System.out.println("用户访问的路径为："+path);
        //下面判断映射关系
        try {
            //加载Servlet接口实现类类路径
            Class<?> aClass = ClassLoader.getSystemClassLoader().loadClass(tempClassPath);
            System.out.println("aClass输出的东西为："+aClass);
            //获取注解路径
            WebServlet annotation = aClass.getAnnotation(WebServlet.class);
            System.out.println("用户通过注解定义的访问Servlet接口实现类路径为："+annotation.value());
            if (annotation.value().equals(path)){

                System.out.println("是/test的类");
                //初始化MyServlet接口为null
                MyServlet myServlet =null;
                //通过反射获取aClass对应类的实例
                Object o =aClass.newInstance();
                if(o instanceof MyServlet){
                    myServlet=(MyServlet) o;
                }
                if(myServlet==null){
                    return;
                }
                if(method==HttpMethod.GET){
                    myServlet.init();
                    myServlet.service(servletRequest,servletResponse);
                    myServlet.destroy();
                }else if (method==HttpMethod.POST){
                    System.out.println("目前还未编写处理POST请求的方法");
                }
            }else {
                System.out.println("不是/test的类");
            }
            //关闭监听
            socket.close();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws Exception {
        MyHttpServer myHttpServer =new MyHttpServer();
        myHttpServer.work(HttpMethod.GET);
    }
}
