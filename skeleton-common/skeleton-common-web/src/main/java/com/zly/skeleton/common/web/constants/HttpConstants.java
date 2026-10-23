package com.zly.skeleton.common.web.constants;

/**
 * @Description: HTTP常量
 * @Author: ZLY
 * @Date: 2026/7/22
 */
public class HttpConstants
{

    private HttpConstants() {
        // 静态调用
    }

    // ==================== HTTP 方法 ====================

    public static final String GET = "GET";
    public static final String POST = "POST";
    public static final String PUT = "PUT";
    public static final String DELETE = "DELETE";
    public static final String PATCH = "PATCH";
    public static final String HEAD = "HEAD";
    public static final String OPTIONS = "OPTIONS";
    public static final String TRACE = "TRACE";

    // ==================== Content-Type ====================

    // 文本类型
    public static final String CONTENT_TYPE_TEXT_PLAIN = "text/plain";
    public static final String CONTENT_TYPE_TEXT_HTML = "text/html";
    public static final String CONTENT_TYPE_TEXT_XML = "text/xml";
    public static final String CONTENT_TYPE_TEXT_CSS = "text/css";
    public static final String CONTENT_TYPE_TEXT_JAVASCRIPT = "text/javascript";

    // JSON 类型
    public static final String CONTENT_TYPE_JSON = "application/json";
    public static final String CONTENT_TYPE_JSON_UTF8 = "application/json;charset=UTF-8";

    // XML 类型
    public static final String CONTENT_TYPE_XML = "application/xml";
    public static final String CONTENT_TYPE_XML_UTF8 = "application/xml;charset=UTF-8";

    // 表单类型
    public static final String CONTENT_TYPE_FORM_URLENCODED = "application/x-www-form-urlencoded";
    public static final String CONTENT_TYPE_FORM_URLENCODED_UTF8 = "application/x-www-form-urlencoded;charset=UTF-8";
    public static final String CONTENT_TYPE_MULTIPART_FORM_DATA = "multipart/form-data";

    // 图片类型
    public static final String CONTENT_TYPE_IMG_PNG = "image/png";
    public static final String CONTENT_TYPE_IMG_JPEG = "image/jpeg";
    public static final String CONTENT_TYPE_IMG_JPG = "image/jpg";
    public static final String CONTENT_TYPE_IMG_GIF = "image/gif";
    public static final String CONTENT_TYPE_IMG_WEBP = "image/webp";
    public static final String CONTENT_TYPE_IMG_SVG = "image/svg+xml";
    public static final String CONTENT_TYPE_IMG_BMP = "image/bmp";

    // 其他常见类型
    public static final String CONTENT_TYPE_OCTET_STREAM = "application/octet-stream";
    public static final String CONTENT_TYPE_PDF = "application/pdf";
    public static final String CONTENT_TYPE_ZIP = "application/zip";

    // ==================== HTTP 状态码 ====================

    // 1xx 信息响应
    public static final int STATUS_CODE_CONTINUE = 100;
    public static final int STATUS_CODE_SWITCHING_PROTOCOLS = 101;

    // 2xx 成功
    public static final int STATUS_CODE_OK = 200;
    public static final int STATUS_CODE_CREATED = 201;
    public static final int STATUS_CODE_ACCEPTED = 202;
    public static final int STATUS_CODE_NO_CONTENT = 204;

    // 3xx 重定向
    public static final int STATUS_CODE_MOVED_PERMANENTLY = 301;
    public static final int STATUS_CODE_FOUND = 302;
    public static final int STATUS_CODE_NOT_MODIFIED = 304;

    // 4xx 客户端错误
    public static final int STATUS_CODE_BAD_REQUEST = 400;
    public static final int STATUS_CODE_UNAUTHORIZED = 401;
    public static final int STATUS_CODE_FORBIDDEN = 403;
    public static final int STATUS_CODE_NOT_FOUND = 404;
    public static final int STATUS_CODE_METHOD_NOT_ALLOWED = 405;
    public static final int STATUS_CODE_CONFLICT = 409;
    public static final int STATUS_CODE_UNPROCESSABLE_ENTITY = 422;
    public static final int STATUS_CODE_TOO_MANY_REQUESTS = 429;

    // 5xx 服务器错误
    public static final int STATUS_CODE_INTERNAL_SERVER_ERROR = 500;
    public static final int STATUS_CODE_NOT_IMPLEMENTED = 501;
    public static final int STATUS_CODE_BAD_GATEWAY = 502;
    public static final int STATUS_CODE_SERVICE_UNAVAILABLE = 503;
    public static final int STATUS_CODE_GATEWAY_TIMEOUT = 504;

    // ==================== 请求头 ====================

    public static final String HEADER_ACCEPT = "Accept";
    public static final String HEADER_ACCEPT_CHARSET = "Accept-Charset";
    public static final String HEADER_ACCEPT_ENCODING = "Accept-Encoding";
    public static final String HEADER_ACCEPT_LANGUAGE = "Accept-Language";
    public static final String HEADER_AUTHORIZATION = "Authorization";
    public static final String HEADER_CACHE_CONTROL = "Cache-Control";
    public static final String HEADER_CONTENT_TYPE = "Content-Type";
    public static final String HEADER_CONTENT_LENGTH = "Content-Length";
    public static final String HEADER_CONTENT_ENCODING = "Content-Encoding";
    public static final String HEADER_COOKIE = "Cookie";
    public static final String HEADER_SET_COOKIE = "Set-Cookie";
    public static final String HEADER_HOST = "Host";
    public static final String HEADER_ORIGIN = "Origin";
    public static final String HEADER_REFERER = "Referer";
    public static final String HEADER_USER_AGENT = "User-Agent";
    public static final String HEADER_X_REQUESTED_WITH = "X-Requested-With";
    public static final String HEADER_CONNECTION = "Connection";
    public static final String HEADER_KEEP_ALIVE = "Keep-Alive";

    // CORS 相关
    public static final String HEADER_ACCESS_CONTROL_ALLOW_ORIGIN = "Access-Control-Allow-Origin";
    public static final String HEADER_ACCESS_CONTROL_ALLOW_METHODS = "Access-Control-Allow-Methods";
    public static final String HEADER_ACCESS_CONTROL_ALLOW_HEADERS = "Access-Control-Allow-Headers";
    public static final String HEADER_ACCESS_CONTROL_ALLOW_CREDENTIALS = "Access-Control-Allow-Credentials";
    public static final String HEADER_ACCESS_CONTROL_MAX_AGE = "Access-Control-Max-Age";

    // ==================== 字符编码 ====================

    public static final String CHARSET_UTF8 = "UTF-8";
    public static final String CHARSET_ISO8859_1 = "ISO-8859-1";
    public static final String CHARSET_GBK = "GBK";

    // ==================== 其他常用常量 ====================

    public static final String ENCODING_GZIP = "gzip";
    public static final String ENCODING_DEFLATE = "deflate";
    public static final String ENCODING_BR = "br";

    public static final String CONNECTION_KEEP_ALIVE = "keep-alive";
    public static final String CONNECTION_CLOSE = "close";

    public static final String AJAX_REQUEST_VALUE = "XMLHttpRequest";

    public static final String BEARER_PREFIX = "Bearer ";
    public static final String BASIC_PREFIX = "Basic ";


}
