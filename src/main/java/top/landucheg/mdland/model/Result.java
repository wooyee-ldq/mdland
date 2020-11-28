package top.landucheg.mdland.model;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

/**
 * restful 返回数据
 *
 * @param <T>
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Result<T> {

    // 状态码
    public static final Integer SUCCESS_CODE = 600;
    public static final Integer ERROR_CODE = 400;

    // 消息内容
    public static final String SUCCESS_MSG = "success";
    public static final String ERROR_MSG = "error";

    private Integer code;  // 状态识别码
    private String msg;  // 消息内容，请求发生错误的时候的内容，正确的时候为"success"
    private T data;  // 具体数据
}
