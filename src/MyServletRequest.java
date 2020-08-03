import java.io.IOException;
import java.io.InputStream;

/**
 * @author pengjian
 * @date 2020-08-02 18:58
 * 封装用户的请求，并交由MyServletImpl处理
 */
public class MyServletRequest {
    private String request;
    private InputStream inputStream;
    private String URI;
    private String contentType;
    private String param;

    public MyServletRequest(InputStream inputStream) throws IOException {
        this.inputStream=inputStream;
        this.request=_getRequest();
        this.URI=_getURI();
        this.contentType=_getContentType();
        this.param=_getParam();

    }

    public String getRequest() {
        return request;
    }

    public String getURI() {
        return URI;
    }

    public String getContentType() {
        return contentType;
    }

    public String getParam() {
        return param;
    }
    //读取请求流，返回字符串
    private String _getRequest() throws IOException {
        int size = inputStream.available();
        byte[] requestBuff = new byte[size];
        inputStream.read(requestBuff);
        return new String(requestBuff);
    }

    private String _getURI(){
        String firstLine = request.substring(0,request.indexOf("\r\n"));
        String [] parts = firstLine.split(" ");
        return parts[1];
    }

    //http响应正文类型
    private String _getContentType(){
        return "html";
    }

    //获取请求参数
    private String _getParam(){
        String paramString = URI.substring(URI.indexOf("?")+1);
        String[] paramPairs =paramString.split("=");
        return paramPairs[1];
    }

    //获取要调用的Servlet的类名
    public String getServletName(){
        return URI.substring(URI.indexOf("/"),URI.indexOf("?"));
    }



}
