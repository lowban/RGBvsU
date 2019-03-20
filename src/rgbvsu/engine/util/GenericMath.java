/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rgbvsu.engine.util;

/**
 *
 * @author Dauid
 */
public class GenericMath {
    public static float getPercentage(float part, float whole){ return part/whole; }
    public static float getPercentage(float part, int whole){ return part/whole; }
    public static float getPercentage(int part, float whole){ return part/whole; }
    public static float getPercentage(int part, int whole){ return (float)part/whole; }
    public static float roundToDecimal(float valueToRound, int numberOfDecimalPlaces)
    {
        float multipicationFactor = (float)Math.pow(10, numberOfDecimalPlaces);
        float interestedInZeroDPs = valueToRound * multipicationFactor;
        return (float)Math.round(interestedInZeroDPs) / multipicationFactor;
    }
}
