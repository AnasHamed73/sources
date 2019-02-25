package tierproviders;

import electricalbillcomputer.Tier;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DefaultTierProvider implements TierProvider {

    private static final List<Tier> tiers;

    static {
        tiers = new ArrayList<>(7);
        Tier.Builder builder = Tier.Builder();
        tiers.add(builder.level(1).lowerLimit(0).upperLimit(160).multiplier(33).build());
        tiers.add(builder.level(2).lowerLimit(161).upperLimit(300).multiplier(72).build());
        tiers.add(builder.level(3).lowerLimit(301).upperLimit(500).multiplier(86).build());
        tiers.add(builder.level(4).lowerLimit(501).upperLimit(600).multiplier(114).build());
        tiers.add(builder.level(5).lowerLimit(601).upperLimit(750).multiplier(158).build());
        tiers.add(builder.level(6).lowerLimit(751).upperLimit(1000).multiplier(188).build());
        tiers.add(builder.level(7).lowerLimit(1001).upperLimit(Integer.MAX_VALUE).multiplier(265).build());
    }

    @Override
    public Iterable<Tier> tiers() {
        return Collections.unmodifiableList(tiers);
    }
}
