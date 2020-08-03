import interfaces.WebServlet;

import java.io.IOException;

/**
 * @author pengjian
 * @date 2020-08-02 19:02
 * MyServlet接口实现类业务逻辑
 */
@WebServlet("/pengjian")
public class MyServletImpl implements MyServlet{

    @Override
    public void init() {
        System.out.println("MyServlet 初始化");
    }

    @Override
    public void service(MyServletRequest request, MyServletResponse response) throws IOException {
        //组装HTTP响应头
        String header = response.assmbleResponseHeader(request.getContentType());
        //组装HTTP响应正文(参数是截取的)
        String body =response.assmbleResponseBody(request.getParam());
        //返回响应
        response.write(header+body);
    }

    @Override
    public void destroy() {

        System.out.println("MyServlet进程销毁");
    }
}
