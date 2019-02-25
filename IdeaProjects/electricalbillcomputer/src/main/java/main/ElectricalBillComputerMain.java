package main;

import electricalbillcomputer.ElectricalBillComputer;
import tierproviders.TierProvider;

import java.util.Iterator;
import java.util.Optional;
import java.util.Scanner;
import java.util.ServiceLoader;

public class ElectricalBillComputerMain {
    private static ElectricalBillComputer computer;

    public static void main(String[] args) {
        computer = new ElectricalBillComputer(loadTierProvider());
        if (args.length == 0)
            return;
        if ("-i".equals(args[0]) || "--interactive".equals(args[0]))
            interactiveMode();
        else
            parseAndCalculate(args);
    }

    private static void interactiveMode() {
        System.out.println("Interactive range mode: specify consumption " +
                "by providing previous and current counter readings\n" +
                "**note: enter q at any point to quit");
        try (Scanner scn = new Scanner(System.in)) {
            while (true) {
                int prev, current;

                Optional<Integer> input = cyclicPromptInt(scn, "Previous reading: ");
                if (!input.isPresent())
                    break;
                prev = input.get();

                input = cyclicPromptInt(scn, "Current reading: ");
                if (!input.isPresent())
                    break;
                current = input.get();

                calculateAndOutputFees(current - prev);
            }
        }
    }

    private static Optional<Integer> cyclicPromptInt(Scanner scn, String msg) {
        String line;
        while (true) {
            System.out.print(msg);
            line = scn.nextLine();
            if ("q".equals(line))
                return Optional.empty();
            if (line.matches("^[0-9]+$"))
                return Optional.of(Integer.parseInt(line));
            else
                System.out.println("Error: only positive integers are allowed");
        }
    }

    private static void parseAndCalculate(String[] args) {
        for (String arg : args) {
            try {
                calculateAndOutputFees(Integer.parseInt(arg));
            } catch (NumberFormatException ex) {
                System.out.println("Invalid consumption: " + arg);
            }
        }
    }

    private static void calculateAndOutputFees(int consumption) {
        float calculated = computer.calculateBill(consumption);
        System.out.format("Consumption: %d; fees: JOD%.2f\n", consumption, calculated);
    }

    private static TierProvider loadTierProvider() {
        ServiceLoader<TierProvider> loader = ServiceLoader.load(TierProvider.class);
        Iterator<TierProvider> tierProviders = loader.iterator();
        if (!tierProviders.hasNext())
            throw new IllegalStateException("No tier provider implementations found");
        return tierProviders.next();
    }
}
