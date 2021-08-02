package com.pf.springboot.vo;

import java.util.Date;
import java.util.List;

/**
 * @Author: PengFeng
 * @Description:
 * @Date: Created in 22:25 2021/7/28
 */
public class CommentVo {
    private Integer id;
    private Integer parentId;
    private String content;
    private Date createTime;
    private List<CommentVo> childrens;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public List<CommentVo> getChildrens() {
        return childrens;
    }

    public void setChildrens(List<CommentVo> childrens) {
        this.childrens = childrens;
    }
}
