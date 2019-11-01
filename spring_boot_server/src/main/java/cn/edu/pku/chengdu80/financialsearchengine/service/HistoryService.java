package cn.edu.pku.chengdu80.financialsearchengine.service;

import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class HistoryService {
    private List<String> keyWords;
    private Map<String, String> abbrToFull;
    private List<Pair<String, Integer>> wordsFreq;

    public HistoryService() {
        keyWords = Arrays.asList("accounting" ,
            "business" ,
            "economy" ,
            "market" ,
            "management" ,
            "analysis" ,
            "fund" ,
            "asset" ,
            "international" ,
            "government" ,
            "commerce" ,
            "law" ,
            "policy" ,
            "finance" ,
            "risk" ,
            "bank" ,
            "quantitative" ,
            "contract");

        abbrToFull = new HashMap<>();
        abbrToFull.put("accounting", "acc");
        abbrToFull.put("business", "bus");
        abbrToFull.put("economy", "eco");
        abbrToFull.put("market", "market");
        abbrToFull.put("management", "manage");
        abbrToFull.put("analysis", "anal");
        abbrToFull.put("fund", "fund");
        abbrToFull.put("asset", "asset");
        abbrToFull.put("international", "internation");
        abbrToFull.put("government", "gov");
        abbrToFull.put("commerce", "com");
        abbrToFull.put("law", "law");
        abbrToFull.put("policy", "poli");
        abbrToFull.put("finance", "fin");
        abbrToFull.put("risk", "risk");
        abbrToFull.put("bank", "bank");
        abbrToFull.put("quantitative", "quant");
        abbrToFull.put("contract", "contract");

        wordsFreq = new ArrayList<>();
        for (String word: keyWords){
            wordsFreq.add(new Pair<>(word, 0));
        }
    }

    public void searchFor(String sentence) {
        List<String> words = Arrays.asList(sentence.split("\\s"));
        for (String word: words) {
            wordsFreq = wordsFreq.stream().map(stringIntegerPair -> {
                if (word.toLowerCase().contains(abbrToFull.get(stringIntegerPair.first))){
                    return new Pair<>(stringIntegerPair.first, stringIntegerPair.second+1);
                }
                return stringIntegerPair;
            }).collect(Collectors.toList());
        }
    }

    public List<String> getHitWords() {
        wordsFreq.sort((o1, o2) -> o2.second-o1.second);
        return wordsFreq.stream().map(stringIntegerPair -> stringIntegerPair.first.toUpperCase()).collect(Collectors.toList());
    }

    private class Pair<K, V> {
        K first;
        V second;
        Pair(K first, V second) {
            this.first = first;
            this.second = second;
        }
    }
}
