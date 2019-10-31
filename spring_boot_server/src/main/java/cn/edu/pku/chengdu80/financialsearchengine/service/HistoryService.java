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
        abbrToFull.put("acc", "accounting");
        abbrToFull.put("bus", "business");
        abbrToFull.put("eco", "economy");
        abbrToFull.put("market", "market");
        abbrToFull.put("manage", "management");
        abbrToFull.put("anal", "analysis");
        abbrToFull.put("fund", "fund");
        abbrToFull.put("asset", "asset");
        abbrToFull.put("internation", "international");
        abbrToFull.put("gov", "government");
        abbrToFull.put("com", "commerce");
        abbrToFull.put("law", "law");
        abbrToFull.put("poli", "policy");
        abbrToFull.put("fin", "finance");
        abbrToFull.put("risk", "risk");
        abbrToFull.put("bank", "bank");
        abbrToFull.put("quant", "quantitative");
        abbrToFull.put("contract", "contract");

        wordsFreq = new ArrayList<>();
        for (String word: keyWords){
            wordsFreq.add(new Pair<>(word, 0));
        }
    }

    public void searchFor(String sentence) {
        List<String> words = Arrays.asList(sentence.split(""));
        for (String word: keyWords) {
            wordsFreq = wordsFreq.stream().map(stringIntegerPair -> {
                if (stringIntegerPair.first.contains(word.toLowerCase())){
                    return new Pair<>(stringIntegerPair.first, stringIntegerPair.second+1);
                }
                return stringIntegerPair;
            }).collect(Collectors.toList());
        }
    }

    public List<String> getHitWords() {
        wordsFreq.sort(Comparator.comparingInt(o -> o.second));
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
