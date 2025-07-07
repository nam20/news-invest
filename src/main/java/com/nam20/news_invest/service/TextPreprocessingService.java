package com.nam20.news_invest.service;

import edu.stanford.nlp.pipeline.*;
import edu.stanford.nlp.ling.CoreLabel;
import java.util.*;

/**
 * 영어 뉴스 텍스트 전처리 서비스
 * - 정규화, 토큰화, 불용어 제거, 표제어 추출 기능 제공
 */
public class TextPreprocessingService {
    private final StanfordCoreNLP pipeline;
    private final Set<String> stopwords;

    // 생성자: 파이프라인 및 불용어 리스트 초기화
    public TextPreprocessingService() {
        Properties props = new Properties();
        props.setProperty("annotators", "tokenize,ssplit,pos,lemma");
        this.pipeline = new StanfordCoreNLP(props);
        this.stopwords = loadEnglishStopwords();
    }

    /**
     * 텍스트 전처리 메서드
     * @param text 원본 뉴스 텍스트
     * @return 전처리된 텍스트
     */
    public String preprocess(String text) {
        // 1. 정규화(특수문자 제거, 소문자 변환)
        String normalized = normalize(text);
        // 2. Stanford CoreNLP로 토큰화, 표제어 추출
        CoreDocument doc = new CoreDocument(normalized);
        pipeline.annotate(doc);
        List<String> tokens = new ArrayList<>();
        for (CoreLabel tok : doc.tokens()) {
            String word = tok.lemma().toLowerCase(); // 표제어 추출 및 소문자 변환
            if (!stopwords.contains(word) && word.length() > 1) { // 불용어 및 한 글자 제거
                tokens.add(word);
            }
        }
        // 3. 토큰을 공백으로 연결하여 반환
        return String.join(" ", tokens);
    }

    /**
     * 정규화: 특수문자, 숫자 제거 및 소문자 변환
     */
    private String normalize(String text) {
        if (text == null) return "";
        // 특수문자, 숫자 제거, 소문자 변환
        return text.replaceAll("[^a-zA-Z\s]", " ").toLowerCase();
    }

    /**
     * 영어 불용어 리스트 로드 (간단 샘플, 필요시 확장)
     */
    private Set<String> loadEnglishStopwords() {
        return new HashSet<>(Arrays.asList(
            "a", "an", "the", "and", "or", "but", "if", "while", "with", "of", "at", "by", "for", "to", "in", "on", "from", "up", "down", "out", "over", "under", "again", "further", "then", "once", "here", "there", "all", "any", "both", "each", "few", "more", "most", "other", "some", "such", "no", "nor", "not", "only", "own", "same", "so", "than", "too", "very", "can", "will", "just", "don", "should", "now", "is", "am", "are", "was", "were", "be", "been", "being", "have", "has", "had", "do", "does", "did"
        ));
    }
} 