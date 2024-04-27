package cse364.project;

import jakarta.persistence.Entity;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.apache.commons.lang3.tuple.Pair;

@Document(collection = "cosineSimilarity")
public class CosineSimilarity {

    private Pair<Integer, Integer> target;
    private Double similarity;
    CosineSimilarity() {};
    CosineSimilarity(Pair<Integer, Integer> target, Double similarity){
        this.target = target;
        this.similarity = similarity;
    }

    public Integer getLeftTarget() { return this.target.getLeft(); }
    public Integer getRightTarget() { return this.target.getRight(); }
    public Pair<Integer, Integer> getTarget() { return this.target; }
    public Double getSimilarity() { return this.similarity; }
    public void setTarget(Pair<Integer, Integer> newTarget) { this.target = newTarget; }
    public void setSimilarity(Double newSimilarity) { this.similarity = newSimilarity; }

}