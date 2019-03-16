package com.zl.way.www;

import com.zl.way.www.service.MainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController @RequestMapping("/land") public class MainApi {

    private final MainService mainService;

    @Autowired public MainApi(MainService mainService) {
        this.mainService = mainService;
    }

    @GetMapping("/android/download") public void androidDownload(HttpServletResponse response) throws IOException {
        final String androidApkLink = mainService.getAndroidApkLink();
        response.sendRedirect(androidApkLink);
    }
}
