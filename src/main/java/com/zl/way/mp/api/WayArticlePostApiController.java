package com.zl.way.mp.api;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.zl.way.mp.api.model.ApiResponseMessage;
import com.zl.way.mp.api.model.WayAuditArticlePostReq;
import com.zl.way.mp.api.model.WayQueryArticlePost;
import com.zl.way.mp.api.model.WayQueryArticlePostResp;
import com.zl.way.mp.exception.NotExistException;
import com.zl.way.mp.mapper.model.WayArticlePost;
import com.zl.way.mp.service.WayArticlePostService;
import com.zl.way.mp.service.model.WayAuditArticlePostParam;
import com.zl.way.mp.service.model.WayGetArticlePostBo;
import com.zl.way.mp.service.model.WayQueryArticlePostBo;
import com.zl.way.util.PageParam;
import com.zl.way.util.ResponseEntityUtil;

@RestController("mpWayArticlePostApi")
public class WayArticlePostApiController implements WayArticlePostApi {

    private static final Logger log = LoggerFactory.getLogger(WayArticlePostApiController.class);

    private final WayArticlePostService articlePostService;

    @Autowired
    public WayArticlePostApiController(WayArticlePostService articlePostService) {
        this.articlePostService = articlePostService;
    }

    @Override
    public ResponseEntity<ApiResponseMessage> auditArticle(@Valid @RequestBody WayAuditArticlePostReq body,
        @PathVariable("postId") Long postId,
        @RequestHeader(value = "X-userLoginId", required = true) Long xUserLoginId) {

        WayAuditArticlePostParam auditArticlePostParam =
            WayAuditArticlePostParam.WayAuditArticlePostParamBuilder.aWayAuditArticlePostParam().withPostId(postId)
                .withPass(body.getPass() == 1).withRejectContent(body.getRejectContent()).build();
        // 0:否 1：是
        try {
            articlePostService.auditArticlePost(auditArticlePostParam);
        } catch (NotExistException e) {
            log.warn("文章不存在", e);
            return ResponseEntityUtil.sendResponseEntity(new ApiResponseMessage(ApiResponseMessage.ERROR, "文章不存在"));
        }
        return ResponseEntityUtil.sendResponseEntity(new ApiResponseMessage(ApiResponseMessage.OK, "ok"));
    }

    @Override
    public ResponseEntity<ApiResponseMessage> deleteArticle(@PathVariable("postId") Long postId,
        @RequestHeader(value = "X-userLoginId", required = true) Long xUserLoginId) {
        try {
            articlePostService.deleteArticlePost(postId);
        } catch (NotExistException e) {
            return ResponseEntityUtil.sendResponseEntity(new ApiResponseMessage(ApiResponseMessage.ERROR, "文章不存在"));
        }
        return ResponseEntityUtil.sendResponseEntity(new ApiResponseMessage(ApiResponseMessage.OK, "ok"));
    }

    @Override
    public ResponseEntity<WayQueryArticlePost> getArticle(@PathVariable("postId") Long postId,
        @RequestHeader(value = "X-userLoginId", required = true) Long xUserLoginId) {
        WayGetArticlePostBo getArticlePostBo = articlePostService.getArticlePost(postId);
        if (null == getArticlePostBo.getArticlePost()) {
            return ResponseEntityUtil.sendResponseEntity(null, HttpStatus.NOT_FOUND);
        }
        WayQueryArticlePost queryArticlePost = new WayQueryArticlePost();
        queryArticlePost.setCommodityId(getArticlePostBo.getArticlePost().getCommodityId());
        queryArticlePost.setCommodityName(getArticlePostBo.getCommodityName());
        queryArticlePost.setPostContent(getArticlePostBo.getArticlePostContent().getContent());
        queryArticlePost.setPostId(postId);
        queryArticlePost.setSubject(getArticlePostBo.getArticlePost().getSubject());
        queryArticlePost.setPublishTime(getArticlePostBo.getArticlePost().getCreateTime());
        return ResponseEntityUtil.sendResponseEntity(queryArticlePost);
    }

    @Override
    public ResponseEntity<WayQueryArticlePostResp> queryArticles(
        @NotNull @Valid @RequestParam(value = "page", required = true, defaultValue = "1") Integer page,
        @NotNull @Valid @RequestParam(value = "pageSize", required = true, defaultValue = "10") Integer pageSize,
        @Valid @RequestParam(value = "keywords", required = false) String keywords,
        @Valid @RequestParam(value = "postId", required = false) Long postId) {

        WayQueryArticlePostBo queryArticlePostBo = articlePostService.queryArticlePost(keywords, postId,
            PageParam.PageParamBuilder.aPageParam().withPageNum(page).withPageSize(pageSize).build());
        List<WayQueryArticlePost> queryArticlePostList = queryArticlePostBo.getArticlePostList().stream()
            .map(item -> transformQueryPostArticle(item)).collect(Collectors.toList());

        WayQueryArticlePostResp queryArticlePostResp = new WayQueryArticlePostResp().items(queryArticlePostList)
            .total(queryArticlePostBo.getArticlePostListTotal());

        return ResponseEntityUtil.sendResponseEntity(queryArticlePostResp);
    }

    private WayQueryArticlePost transformQueryPostArticle(WayArticlePost articlePost) {
        WayQueryArticlePost queryArticlePost = new WayQueryArticlePost();
        queryArticlePost.setPublishTime(articlePost.getPublishedTime());
        queryArticlePost.setSubject(articlePost.getSubject());
        queryArticlePost.setPostId(articlePost.getId());
        queryArticlePost.setPostStatus(Integer.valueOf(articlePost.getIsDeleted()));

        return queryArticlePost;
    }

}
