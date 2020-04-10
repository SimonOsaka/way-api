package com.zl.way.h5app.api;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.zl.way.h5app.api.model.WayQueryArticlePost;
import com.zl.way.h5app.api.model.WayQueryArticlePostResp;
import com.zl.way.h5app.mapper.model.WayArticlePost;
import com.zl.way.h5app.service.WayArticlePostService;
import com.zl.way.h5app.service.model.WayGetArticlePostBo;
import com.zl.way.h5app.service.model.WayQueryArticlePostBo;
import com.zl.way.util.PageParam;
import com.zl.way.util.ResponseEntityUtil;

@RestController
public class WayArticlePostApiController implements WayArticlePostApi {

    private static final Logger log = LoggerFactory.getLogger(WayArticlePostApiController.class);

    private final WayArticlePostService articlePostService;

    @Autowired
    public WayArticlePostApiController(WayArticlePostService articlePostService) {
        this.articlePostService = articlePostService;
    }

    @Override
    public ResponseEntity<WayQueryArticlePost> getArticle(@PathVariable("postId") Long postId,
        @RequestHeader(value = "token", required = true) String token,
        @RequestHeader(value = "userLoginId", required = false) Long userLoginId) {
        WayGetArticlePostBo getArticlePostBo = articlePostService.getArticlePost(postId);
        if (null == getArticlePostBo.getArticlePost()) {
            return ResponseEntityUtil.sendResponseEntity(null, HttpStatus.NOT_FOUND);
        }
        WayQueryArticlePost queryArticlePost = new WayQueryArticlePost();
        queryArticlePost.setCommodityId(getArticlePostBo.getArticlePost().getCommodityId());
        queryArticlePost.setCommodityName(getArticlePostBo.getCommodityName());
        queryArticlePost.setCommodityImage(getArticlePostBo.getCommodityImage());
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
        @Valid @RequestParam(value = "keywords", required = false) String keywords) {

        WayQueryArticlePostBo queryArticlePostBo = articlePostService.queryArticlePost(keywords,
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
