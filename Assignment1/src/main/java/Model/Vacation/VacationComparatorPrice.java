package Model.Vacation;

import java.util.Comparator;

public class VacationComparatorPrice implements Comparator<Vacation> {

    @Override
    public int compare(Vacation o1, Vacation o2) {
        return o1.getPrice().compareTo(o2.getPrice());
    }
}
