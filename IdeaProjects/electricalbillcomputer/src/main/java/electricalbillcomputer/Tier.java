package electricalbillcomputer;

public class Tier {
    private int level;
    private int lowerLimit;
    private int upperLimit;
    private float multiplier;

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getLowerLimit() {
        return lowerLimit;
    }

    public void setLowerLimit(int lowerLimit) {
        this.lowerLimit = lowerLimit;
    }

    public int getUpperLimit() {
        return upperLimit;
    }

    public void setUpperLimit(int upperLimit) {
        this.upperLimit = upperLimit;
    }

    public float getMultiplier() {
        return multiplier;
    }

    public void setMultiplier(float multiplier) {
        this.multiplier = multiplier;
    }
    
    public static Tier copyOf(Tier src) {
        Tier tier = new Tier();
        tier.setMultiplier(src.getMultiplier());
        tier.setUpperLimit(src.getUpperLimit());
        tier.setLowerLimit(src.getLowerLimit());
        tier.setLevel(src.getLevel());
        return tier;
    }

    public static Builder Builder() {
        return new Builder();
    }

    public static class Builder {
        private Tier tier = new Tier();
        
        public Builder level(int level) {
            tier.setLevel(level);
            return this;
        }

        public Builder lowerLimit(int lowerLimit) {
            tier.setLowerLimit(lowerLimit);
            return this;
        }
        
        public Builder upperLimit(int upperLimit) {
            tier.setUpperLimit(upperLimit);
            return this;
        }
        
        public Builder multiplier(float multiplier) {
            tier.setMultiplier(multiplier);
            return this;
        }
        
        public Tier build() {
            Tier ret = copyOf(tier);
            tier = new Tier();
            return ret;
        }
    }
}
