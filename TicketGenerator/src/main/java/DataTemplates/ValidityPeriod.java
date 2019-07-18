


package DataTemplates;

import javax.persistence.*;
import java.time.LocalDate;

@Embeddable
public class ValidityPeriod {

    private LocalDate startDate;
    private LocalDate endDate;


    public ValidityPeriod(LocalDate startDate, LocalDate endDate) {
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public ValidityPeriod() {
    }


} // class




















