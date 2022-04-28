package com.happy.todo.lib_common.event;

/**
 * @Describe：当页面没被回收时通过EventBus传值
 * @Date： 2018/12/04
 * @Author： dengkewu
 * @Contact：
 */
public class CommendEvent {
    private String name;
    private String id;
    private String type;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public CommendEvent(String name, String id, String type) {
        this.name = name;
        this.id = id;
        this.type = type;
    }
}
