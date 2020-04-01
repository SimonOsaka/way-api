#!/bin/bash
# 生成sp openapi3实体类
java -jar swagger-codegen-cli.jar generate -i sp/way_article_post.yaml \
-l spring \
-o example/sp/ \
--api-package com.zl.way.sp.api \
--model-package com.zl.way.sp.api.model \
-t template \
-c config.json