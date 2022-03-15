package Model.Vacation;

import java.util.Comparator;

public class VacationComparatorDate implements Comparator<Vacation> {

    @Override
    public int compare(Vacation o1, Vacation o2) {
        return o1.getStart().compareTo(o2.getStart());
    }
}
