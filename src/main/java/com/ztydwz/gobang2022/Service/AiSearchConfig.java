package main.java.com.ztydwz.gobang2022.Service;

public class AiSearchConfig {
    public enum Strength {
        LOW, MEDIUM, HIGH
    }

    public final Strength strength;
    public final int maxDepth;
    public final long timeLimitMillis;

    public AiSearchConfig(Strength strength) {
        this.strength = strength == null ? Strength.MEDIUM : strength;
        switch (this.strength) {
            case LOW:
                this.maxDepth = 2;
                this.timeLimitMillis = 3000;
                break;
            case MEDIUM:
                this.maxDepth = 4;
                this.timeLimitMillis = 15000;
                break;
            case HIGH:
            default:
                this.maxDepth = 6;
                this.timeLimitMillis = 120000;
                break;
        }
    }

    public int getEffectiveDepth(int stoneCount) {
        if (stoneCount < 4) {
            return Math.min(2, maxDepth);
        }
        if (stoneCount < 8) {
            return Math.min(3, maxDepth);
        }
        return maxDepth;
    }
}
