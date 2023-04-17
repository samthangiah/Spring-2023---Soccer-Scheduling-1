package soccerManagment;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class LeagueValidationController {
    private List<String> errors;

    public LeagueValidationController() {
        errors = new ArrayList<>();
    }

    public List<String> validate(String leagueName, Date lowCutOff, Date highCutOff) {
        errors.clear();

        // Validate league name
        if (!leagueName.matches("^[a-zA-Z0-9]+$")) {
            errors.add("League name must contain only letters and numbers.");
        }

        // Validate low and high cut off dates
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            dateFormat.format(lowCutOff);
        } catch (Exception e) {
            errors.add("Low cut off date must be in yyyy-MM-dd format.");
        }

        try {
            dateFormat.format(highCutOff);
        } catch (Exception e) {
            errors.add("High cut off date must be in yyyy-MM-dd format.");
        }

        // Validate number of coaches
        

        return errors;
    }
}