/**
 * Servlet服务接口
 */
public interface MyServlet {
    //servlet初始化方法
    public void init();
    //请求处理，请求响应方法
    public void service(MyServletRequest request,MyServletResponse response) throws Exception;
    //清除
    public void destroy();

}
