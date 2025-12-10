package com.mst.loaderservice.service;

import com.mst.loaderservice.enums.Environment;
import com.mst.loaderservice.enums.Label;
import com.mst.loaderservice.exception.CheckedExceptionWrapper;
import com.mst.loaderservice.model.PlatformInformation;
import com.mst.loaderservice.repository.PlatformInformationRepository;
import jakarta.transaction.Transactional;
import lombok.Getter;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.*;

@Getter
@Service
public class LoaderService {

    private static final long LOAD_RATE_IN_MS = 60 * 60 * 1000;

    @Autowired
    private PlatformInformationRepository repo;

    @Autowired
    private GitService gitService;

    @Value("${git.clone.localPath}")
    private String clonePath;

    @Value("${git.clone.url}")
    private String cloneUrl;


    @Scheduled(fixedRate = LOAD_RATE_IN_MS) //fixedRate gets number in milliseconds
    @Transactional
    public void cloneAndLoad(){
        try{

            if(clonePath == null || clonePath.isEmpty()){
                throw new IllegalArgumentException("Illegal PATH: " + clonePath);
            }

            if(cloneUrl == null || cloneUrl.isEmpty()){
                throw new IllegalArgumentException("Illegal Git Repo URL: " + cloneUrl);
            }

            String[] supportedPlatforms = {"clickUp", "gitHub", "jira"};
            gitService.cloneRepo(clonePath,cloneUrl);
            File gitRepo = new File(clonePath);
            File[] files = gitRepo.listFiles();
            if(files == null){
                return;
            }

            repo.deleteAll();  // TODO
            for(String pf : supportedPlatforms){
                File platFile = new File(clonePath + "/" + pf);
                File[] csvFiles = platFile.listFiles();
                if(csvFiles != null && csvFiles.length > 0){
                    loadFile(csvFiles[0].getPath(), pf); //TODO
                }
            }

        }catch(GitAPIException | IOException e){
            throw new CheckedExceptionWrapper(e.getMessage(),e);
        }

    }

    public void loadFile(String csvFilePath, String platformName) throws IOException {

        if(csvFilePath == null || csvFilePath.isEmpty()){
            throw new IllegalArgumentException("Illegal csv file path: " + csvFilePath);
        }

        if(platformName == null || platformName.isEmpty()){
            throw new IllegalArgumentException("Illegal Platform Name: " + platformName);
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(csvFilePath))) {
            String line;
            reader.readLine();
            while ((line = reader.readLine()) != null) {
                String[] fields = line.split(",");
                PlatformInformation pi = PlatformInformation.builder()
                        .ownerId(Long.parseLong(fields[1]))
                        .projectName(fields[2])
                        .tag(fields[3])
                        .label(Label.valueOf(fields[4].toUpperCase()))
                        .assigneeId(Long.parseLong(fields[5]))
                        .externalId(fields[6])
                        .environment(Environment.valueOf(fields[7].toUpperCase()))
                        .description(fields[8])
                        .taskPoint(Integer.parseInt(fields[9]))
                        .sprint(fields[10])
                        .platformName(platformName)
                        .build();
                repo.save(pi);
            }
        }
    }
}
