package cse364.project;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "cosineSimilarity")
public class CosineSimilarity {

    private @Id Integer target;
    private Double similarity;
    CosineSimilarity() {};
    CosineSimilarity(Integer target, Double similarity){
        this.target = target;
        this.similarity = similarity;
    }

    public Integer getTarget() { return this.target; }
    public Double getSimilarity() { return this.similarity; }
    public void setTarget(Integer newTarget) { this.target = newTarget; }
    public void setSimilarity(Double newSimilarity) { this.similarity = newSimilarity; }

}