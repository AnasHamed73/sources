import electricalbillcomputer.ElectricalBillComputer;
import electricalbillcomputer.Tier;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import java.util.Arrays;

public class ElectricalBillComputerTest {
    public static final double MAX_FLOAT_ERROR = 0.3;
    private ElectricalBillComputer computer;
    private static final Tier ONE, TWO, THREE, FOUR, FIVE, SIX, SEVEN;

    static {
        Tier.Builder builder = new Tier.Builder();
        ONE = builder.level(1).lowerLimit(0).upperLimit(160).multiplier(33).build();
        TWO = builder.level(2).lowerLimit(161).upperLimit(300).multiplier(72).build();
        THREE = builder.level(3).lowerLimit(301).upperLimit(500).multiplier(86).build();
        FOUR = builder.level(4).lowerLimit(501).upperLimit(600).multiplier(114).build();
        FIVE = builder.level(5).lowerLimit(601).upperLimit(750).multiplier(158).build();
        SIX = builder.level(6).lowerLimit(751).upperLimit(1000).multiplier(188).build();
        SEVEN = builder.level(7).lowerLimit(1001).upperLimit(Integer.MAX_VALUE).multiplier(265).build();
    }

    @Before
    public void setup() {
        computer = new ElectricalBillComputer(this::defaultTiers);
    }

    @Test
    public void givenConsumptionSpanningOneTier_WhenCalculated_ShouldReturnOneTierConsumption() {
        int consumption = ONE.getUpperLimit();
        float calculated = computer.calculateBill(consumption);
        float expected = consumption * ONE.getMultiplier() / 1000.0f;
        Assert.assertEquals(expected, calculated, MAX_FLOAT_ERROR);
    }

    @Test
    public void givenConsumptionSpanningFiveTiers_WhenCalculted_ShouldReturnCorrectFee() {
        int consumption = 667;
        double expected = 54.546;
        Assert.assertEquals(expected, computer.calculateBill(consumption), MAX_FLOAT_ERROR);
    }

    private Iterable<Tier> defaultTiers() {
        return Arrays.asList(ONE, TWO, THREE, FOUR, FIVE, SIX, SEVEN);
    }
}
