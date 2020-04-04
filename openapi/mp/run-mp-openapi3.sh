#!/bin/bash
# 生成mp openapi3实体类
java -jar ../swagger-codegen-cli.jar generate -i way_article_post.yaml \
-l spring \
-o ../example/mp/ \
--api-package com.zl.way.mp.api \
--model-package com.zl.way.mp.api.model \
-t ../template \
-c ../config.json