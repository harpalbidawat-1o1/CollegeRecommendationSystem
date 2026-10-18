
package com.college.service;

import com.college.model.Cutoff;
import java.util.ArrayList;
import java.util.List;

public class CutoffDataService {

    private static final List<Cutoff> cutoffs = new ArrayList<>();

    static {
        cutoffs.add(new Cutoff(
                1, 1, "OPEN", 95.0, "JEE Main"
        ));

        cutoffs.add(new Cutoff(
                2, 2, "OPEN", 80.0, "JEE Main"
        ));

        cutoffs.add(new Cutoff(
                3, 3, "OPEN", 90.0, "JEE Main"
        ));
    }

    public static List<Cutoff> getCutoffs() {
        return new ArrayList<>(cutoffs);
    }

    public static List<Cutoff> getCutoffsByCourse(int courseId) {
        List<Cutoff> result = new ArrayList<>();

        for (Cutoff cutoff : cutoffs) {
            if (cutoff.getCourseId() == courseId) {
                result.add(cutoff);
            }
        }

        return result;
    }

    public static void addCutoff(Cutoff cutoff) {
        cutoffs.add(cutoff);
    }

    public static void updateCutoff(Cutoff updatedCutoff) {
        for (int i = 0; i < cutoffs.size(); i++) {
            if (cutoffs.get(i).getCutoffId()
                    == updatedCutoff.getCutoffId()) {
                cutoffs.set(i, updatedCutoff);
                return;
            }
        }
    }

    public static void deleteCutoff(int cutoffId) {
        cutoffs.removeIf(
                cutoff -> cutoff.getCutoffId() == cutoffId
        );
    }

    public static int getNextId() {
        int maxId = 0;

        for (Cutoff cutoff : cutoffs) {
            if (cutoff.getCutoffId() > maxId) {
                maxId = cutoff.getCutoffId();
            }
        }

        return maxId + 1;
    }
}
