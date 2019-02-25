package electricalbillcomputer;

import tierproviders.TierProvider;

import java.util.Iterator;

public class ElectricalBillComputer {
    private TierProvider tierProvider;

    public ElectricalBillComputer(TierProvider tierProvider) {
        this.tierProvider = tierProvider;
    }

    public float calculateBill(int consumption) {
        return consumption <= 0 ? 0 : calculateBill(consumption, tierProvider.tiers());
    }

    private float calculateBill(int consumption, Iterable<Tier> tiers) {
        float total = 0;
        int remainingConsumption = consumption;
        Iterator<Tier> tierIterator = tiers.iterator();
        while(remainingConsumption > 0) {
            Tier nextTier = tierIterator.next();
            total += calculateTierFees(remainingConsumption, nextTier);
            remainingConsumption -= getTierRange(nextTier);
        }
        return total / 1000.0f;
    }

    private float calculateTierFees(int remainingConsumption, Tier tier) {
        int tierRange = getTierRange(tier);
        return tier.getMultiplier() * Math.min(remainingConsumption, tierRange);
    }

    private int getTierRange(Tier nextTier) {
        return nextTier.getUpperLimit() - nextTier.getLowerLimit();
    }
}
