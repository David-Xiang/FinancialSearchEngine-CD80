package cn.edu.pku.chengdu80.financialsearchengine.service;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

@Service
public class NameService {
    private Set<String> nameSet;

    public NameService() {
        Resource resource = new ClassPathResource("name.txt");
        File sourceFile = null;
        nameSet = new HashSet<>();
        try{
            sourceFile =  resource.getFile();
            BufferedReader in = new BufferedReader(new FileReader(sourceFile));
            String line = "";
            while ((line = in.readLine()) != null){
                if (line.length() < 2){
                    continue;
                }
                nameSet.add(line.toLowerCase());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean checkName(String queryString) {
        String word = queryString.split("[\\s,]")[0].toLowerCase();
        return nameSet.contains(word);
    }
}
