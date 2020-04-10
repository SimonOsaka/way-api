#!/bin/bash
# 生成sp openapi3实体类
java -jar ../swagger-codegen-cli.jar generate -i way_article_post.yaml \
-l spring \
-o ../example/h5app/ \
--api-package com.zl.way.h5app.api \
--model-package com.zl.way.h5app.api.model \
-t ../template \
-c ../config.json