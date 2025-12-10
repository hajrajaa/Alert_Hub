package com.mst.loaderservice.service;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;


@Service
public class GitService {
    public void cloneRepo(String localPath, String cloneUrl) throws GitAPIException {

        // if the Git Repo exists, delete it
        if(isGitRepoExist(localPath)){
            deleteLocalRepo(localPath);
        }

        // clone Git Repo
        try (Git main = Git.cloneRepository()
                .setURI(cloneUrl)
                .setBranch("main")
                .setDirectory(new File(localPath))
                .call()){
        }
    }

    public static boolean isGitRepoExist(String localPath) {
        File repoDir = new File(localPath);
        //File gitDir = new File(repoDir, ".git");
        return repoDir.exists() && repoDir.isDirectory(); //&& gitDir.exists() && gitDir.isDirectory();
    }

    public void deleteLocalRepo(String repoPath) {
        File repoDir = new File(repoPath);
        if (!repoDir.exists()) {
            System.out.println("Repository does not exist at path: " + repoPath);
            return;
        }
        deleteDirectoryRecursively(repoDir);
    }

    private void deleteDirectoryRecursively(File directory) {
        File[] allContents = directory.listFiles();
        if (allContents != null) {
            for (File file : allContents) {
                deleteDirectoryRecursively(file);
            }
        }
        boolean res = directory.delete();
        if(!res){
            throw new RuntimeException("Could Not delete the following file/directory: " + directory);
        }

    }
}
