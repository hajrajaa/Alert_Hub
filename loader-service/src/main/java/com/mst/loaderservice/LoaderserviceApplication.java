package com.mst.loaderservice;


import com.mst.loaderservice.enums.Label;
import com.mst.loaderservice.service.PlatformInformationService;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.scheduling.annotation.EnableScheduling;
import java.io.IOException;


@EnableScheduling
@SpringBootApplication
public class LoaderserviceApplication {

	public static void main(String[] args) throws GitAPIException, IOException {
        ConfigurableApplicationContext ctx = SpringApplication.run(LoaderserviceApplication.class, args);
        //PlatformInformationService ps = ctx.getBean(PlatformInformationService.class);
        //ps.cloneAndLoad();
        PlatformInformationService ps =ctx.getBean(PlatformInformationService.class);
        System.out.println(ps.getDeveloperWithMostLabelOccurrences(Label.BUG,5));
        System.out.println(ps.developerTaskAmount((long) 105,5));

    }

}
