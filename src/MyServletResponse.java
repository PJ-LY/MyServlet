import java.io.IOException;
import java.io.OutputStream;

/**
 * @author pengjian
 * @date 2020-08-02 18:58
 * 封装返回的响应，并交由MyServletImpl处理
 */
public class MyServletResponse {

    private OutputStream outputStream;

    public MyServletResponse(OutputStream outputStream) {
        this.outputStream =outputStream;
    }

    /**
     * 创建HTTP响应结果
     */
    public String assmbleResponseHeader(String contentType){
        //响应请求的第一行
        String responseFirstLine="HTTP/1.1 200 OK\r\n";
        //HTTP响应头
        String responseHeader="ContentType:"+contentType+"\r\n\r\n";

        return responseFirstLine+responseHeader;
    }

    /**
     * 创建HTTP响应的自定义页面
     */
    public String assmbleResponseBody(String param){

        String content="<body><h1>Hello:"+param+"</h1></body>";
        String title="<head><title>手捋Servlet，深刻了解Servlet机制</title></head>";
        String body ="<html>"+title+content+"</html>";
        return body;
    }

    public void write(String res) throws IOException {
        outputStream.write(res.getBytes());
    }
}
