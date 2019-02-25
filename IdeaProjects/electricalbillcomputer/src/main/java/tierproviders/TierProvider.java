package tierproviders;

import electricalbillcomputer.Tier;

public interface TierProvider {
    Iterable<Tier> tiers();
}
