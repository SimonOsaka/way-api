package com.zl.way.sp.api;

import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.github.benmanes.caffeine.cache.LoadingCache;
import com.zl.way.sp.api.model.*;
import com.zl.way.sp.constants.ApiConstants;
import com.zl.way.sp.exception.NotExistException;
import com.zl.way.sp.model.WayArticlePost;
import com.zl.way.sp.service.WayArticlePostService;
import com.zl.way.sp.service.model.*;
import com.zl.way.util.DateUtil;
import com.zl.way.util.PageParam;
import com.zl.way.util.TokenUtil;
import com.zl.way.validation.WayValidation;
import com.zl.way.validation.rule.WayValidationRuleNotNull;
import com.zl.way.validation.rule.WayValidationRuleStringNotBlank;

@RestController
public class WayArticlePostApiController implements WayArticlePostApi {

    private static final Logger log = LoggerFactory.getLogger(WayArticlePostApiController.class);

    @Qualifier("caffeine")
    private final LoadingCache<String, String> caffeienCache;
    private final WayArticlePostService articlePostService;
    private Lock createArticleLock = new ReentrantLock();

    @Autowired
    public WayArticlePostApiController(LoadingCache<String, String> caffeienCache,
        WayArticlePostService articlePostService) {
        this.caffeienCache = caffeienCache;
        this.articlePostService = articlePostService;
    }

    @Override
    public ResponseEntity<ApiResponseMessage> createArticle(@Valid @RequestBody WayCreateArticlePostReq body,
        @RequestHeader(value = "X-userLoginId", required = true) Long xUserLoginId) {
        // 校验开始
        WayValidation validation = new WayValidation().validRule(new WayValidationRuleStringNotBlank(body.getSubject()))
            .validRule(new WayValidationRuleStringNotBlank(body.getPostContent()))
            .validRule(new WayValidationRuleStringNotBlank(body.getPostToken()))
            .validRule(new WayValidationRuleNotNull(body.getCommodityId()));
        if (validation.hasErrors()) {
            return ResponseEntity.ok(new ApiResponseMessage(2, validation.getErrorString()));
        }
        // 校验结束

        String formPostToken = body.getPostToken();
        String cacheKey = ApiConstants.SUBMIT_TOKEN_SP_PREFIX + xUserLoginId;
        String cacheOkKey = cacheKey + "_ok";
        log.info("cacheKey: {}, cacheOkKey: {}", cacheKey, cacheOkKey);
        createArticleLock.lock();
        try {
            // 让key过期时间长一点
            String cachePostToken = caffeienCache.getIfPresent(cacheKey);
            if (!formPostToken.equalsIgnoreCase(cachePostToken)) {
                // 假设caffeine不删除key，持久化。数据库保存key更合理。
                String cacheOkPostToken = caffeienCache.getIfPresent(cacheOkKey);
                if (formPostToken.equalsIgnoreCase(cacheOkPostToken)) {
                    log.warn("不能重复提交 formPostToken: {}, cacheOkPostToken={}", formPostToken, cacheOkPostToken);
                    return ResponseEntity.ok(new ApiResponseMessage(ApiResponseMessage.ERROR, "已提交成功，不能重复提交"));
                }
                log.warn("提交失败 formPostToken: {}, cachePostToken: {}", formPostToken, cachePostToken);
                return ResponseEntity.ok(new ApiResponseMessage(ApiResponseMessage.ERROR, "提交失败"));
            }

            // 创建文章
            WayCreateArticlePostParam createArticlePostParam = WayCreateArticlePostParam.WayCreateArticlePostBoBuilder
                .aWayCreateArticlePostBo().withPostContent(body.getPostContent()).withCommodityId(body.getCommodityId())
                .withSubject(body.getSubject()).build();
            // 保存文章入库
            articlePostService.createArticlePostAndContent(createArticlePostParam);
            // 记录创建后的token，用来检查是否已经保存到数据库中。
            caffeienCache.put(cacheOkKey, cachePostToken);
            // 删除判断token
            caffeienCache.invalidate(cacheKey);
        } finally {
            createArticleLock.unlock();
        }
        return ResponseEntity.ok(new ApiResponseMessage(ApiResponseMessage.OK, "ok"));
    }

    @Override
    public ResponseEntity<ApiResponseMessage> deleteArticle(@PathVariable("postId") Long postId,
        @RequestHeader(value = "X-userLoginId", required = true) Long xUserLoginId) {
        WayDeleteArticlePostParam deleteArticlePostParam = WayDeleteArticlePostParam.WayDeleteArticlePostBoBuilder
            .aWayDeleteArticlePostBo().withPostId(postId).build();
        try {
            articlePostService.deleteArticlePost(deleteArticlePostParam);
        } catch (NotExistException e) {
            return ResponseEntity.ok(new ApiResponseMessage(ApiResponseMessage.ERROR, "文章不存在"));
        }
        return ResponseEntity.ok(new ApiResponseMessage(ApiResponseMessage.OK, "ok"));
    }

    @Override
    public ResponseEntity<WayQueryArticlePost> getArticle(@PathVariable("postId") Long postId,
        @RequestHeader(value = "X-userLoginId", required = true) Long xUserLoginId) {
        WayQueryArticlePost queryArticlePost = new WayQueryArticlePost();
        WayGetArticlePostBo getArticlePostBo = articlePostService.getArticlePost(postId);
        if (null == getArticlePostBo.getArticlePost()) {
            return ResponseEntity.status(404).build();
        }
        queryArticlePost.setCommodityId(getArticlePostBo.getArticlePost().getCommodityId());
        queryArticlePost.setCommodityName(getArticlePostBo.getCommodityName());
        queryArticlePost.setPostContent(getArticlePostBo.getArticlePostContent().getContent());
        queryArticlePost.setPostId(postId);
        queryArticlePost.setSubject(getArticlePostBo.getArticlePost().getSubject());
        queryArticlePost.setPublishTime(getArticlePostBo.getArticlePost().getCreateTime());
        return ResponseEntity.ok(queryArticlePost);
    }

    @Override
    public ResponseEntity<WayArticlePostToken>
        getToken(@RequestHeader(value = "X-userLoginId", required = true) Long xUserLoginId) {
        String postTokenStr = TokenUtil
            .getToken(ApiConstants.SUBMIT_TOKEN_SP_PREFIX + xUserLoginId + "_" + DateUtil.getDefaultDateTime());
        caffeienCache.put(ApiConstants.SUBMIT_TOKEN_SP_PREFIX + xUserLoginId, postTokenStr);
        WayArticlePostToken postToken = new WayArticlePostToken().token(postTokenStr);
        return ResponseEntity.ok(postToken);
    }

    @Override
    public ResponseEntity<List<WayQueryArticlePost>> queryArticles(
        @NotNull @Valid @RequestParam(value = "page", required = true, defaultValue = "1") Integer page,
        @NotNull @Valid @RequestParam(value = "pageSize", required = true, defaultValue = "10") Integer pageSize,
        @Valid @RequestParam(value = "keywords", required = false) String keywords) {
        WayQueryArticlePostParam queryArticlePostParam = new WayQueryArticlePostParam();
        queryArticlePostParam.setKeywords(keywords);

        WayQueryArticlePostBo queryArticlePostBo = articlePostService.queryArticlePost(queryArticlePostParam,
            PageParam.PageParamBuilder.aPageParam().withPageNum(page).withPageSize(pageSize).build());
        List<WayQueryArticlePost> queryArticlePostList = queryArticlePostBo.getArticlePostList().stream()
            .map(item -> transformQueryPostArticle(item)).collect(Collectors.toList());
        return ResponseEntity.ok(queryArticlePostList);
    }

    private WayQueryArticlePost transformQueryPostArticle(WayArticlePost articlePost) {
        WayQueryArticlePost queryArticlePost = new WayQueryArticlePost();
        queryArticlePost.setPublishTime(articlePost.getCreateTime());
        queryArticlePost.setSubject(articlePost.getSubject());
        queryArticlePost.setPostId(articlePost.getId());

        return queryArticlePost;
    }

    @Override
    public ResponseEntity<ApiResponseMessage> updateArticle(@Valid @RequestBody WayUpdateArticlePostReq body,
        @PathVariable("postId") Long postId,
        @RequestHeader(value = "X-userLoginId", required = true) Long xUserLoginId) {

        WayUpdateArticlePostParam updateArticlePostParam =
            WayUpdateArticlePostParam.WayUpdateArticlePostBoBuilder.aWayUpdateArticlePostBo()
                .withPostContent(body.getPostContent()).withPostId(postId).withSubject(body.getSubject()).build();
        try {
            // 是否提交
            switch (body.getEnableSubmit()) {
                case 0:
                    // 否
                    articlePostService.updateArticlePostAndContent(updateArticlePostParam);
                    break;
                case 1:
                    // 是
                    articlePostService.updateAndSubmitArticlePostAndContent(updateArticlePostParam);
                    break;
                default:
                    return ResponseEntity.ok(new ApiResponseMessage(1, "是否提交不正确"));
            }
        } catch (NotExistException e) {
            log.warn("更新的文章不存在 postId: {}", postId);
            return ResponseEntity.status(404).build();
        }
        return ResponseEntity.ok().build();
    }
}
