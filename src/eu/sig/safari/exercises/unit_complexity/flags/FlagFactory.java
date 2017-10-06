/** 
 * NOTICE: 
 * - This file has been modified by the Software Improvement Group (SIG) to adapt it for training purposes
 * - The original file can be found here: https://github.com/oreillymedia/building_maintainable_software
 * 
 */
package eu.sig.safari.exercises.unit_complexity.flags;

import java.awt.Color;
import java.util.Arrays;
import java.util.List;

import eu.sig.safari.exercises.stubs.flags.Nationality;

public class FlagFactory {

    public List<Color> getFlagColors(Nationality nationality) {
        List<Color> result;
        switch (nationality) {
        case DUTCH:
            result = Arrays.asList(Color.RED, Color.WHITE, Color.BLUE);
            break;
        case GERMAN:
            result = Arrays.asList(Color.BLACK, Color.RED, Color.YELLOW);
            break;
        case BELGIAN:
            result = Arrays.asList(Color.BLACK, Color.YELLOW, Color.RED);
            break;
        case FRENCH:
            result = Arrays.asList(Color.BLUE, Color.WHITE, Color.RED);
            break;
        case ITALIAN:
            result = Arrays.asList(Color.GREEN, Color.WHITE, Color.RED);
            break;
        case UNCLASSIFIED:
        default:
            result = Arrays.asList(Color.GRAY);
            break;
        }
        return result;
    }

}