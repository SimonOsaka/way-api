package com.zl.way.user.api.model;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.zl.way.user.model.UserFeedbackBo;

public class UserFeedbackResponse implements Serializable {
    private UserFeedbackBo userFeedbackBo;
    private List<UserFeedbackBo> userFeedbackBoList;
    private Map<Byte, String> feedbackTypeMap;

    public UserFeedbackBo getUserFeedbackBo() {
        return userFeedbackBo;
    }

    public void setUserFeedbackBo(UserFeedbackBo userFeedbackBo) {
        this.userFeedbackBo = userFeedbackBo;
    }

    public List<UserFeedbackBo> getUserFeedbackBoList() {
        return userFeedbackBoList;
    }

    public void setUserFeedbackBoList(List<UserFeedbackBo> userFeedbackBoList) {
        this.userFeedbackBoList = userFeedbackBoList;
    }

    public Map<Byte, String> getFeedbackTypeMap() {
        return feedbackTypeMap;
    }

    public void setFeedbackTypeMap(Map<Byte, String> feedbackTypeMap) {
        this.feedbackTypeMap = feedbackTypeMap;
    }
}