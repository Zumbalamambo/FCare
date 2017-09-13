/*
 * @category Versa.
 * @copyright Copyright (C) 2017 Contus. All rights reserved.
 * @license http://www.apache.org/licenses/LICENSE-2.0
 */

package in.prasilabs.fcare.utils;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * Generate random colors.
 *
 * @author ContusTeam <developers@contus.in>
 * @version 1.0
 */
public class ColorGenerator {

    /**
     * Default color instance.
     */
    private static final ColorGenerator defaultColorGenerator;

    /**
     * Material color instance.
     */
    private static final ColorGenerator materialColorGenerator;

    static {
        defaultColorGenerator = create(Arrays.asList(
                0xfff16364,
                0xfff58559,
                0xfff9a43e,
                0xffe4c62e,
                0xff67bf74,
                0xff59a2be,
                0xff2093cd,
                0xffad62a7,
                0xff805781
        ));
        materialColorGenerator = create(Arrays.asList(
                0xffe57373,
                0xfff06292,
                0xffba68c8,
                0xff9575cd,
                0xff7986cb,
                0xff64b5f6,
                0xff4fc3f7,
                0xff4dd0e1,
                0xff4db6ac,
                0xff81c784,
                0xffaed581,
                0xffff8a65,
                0xffd4e157,
                0xffffd54f,
                0xffffb74d,
                0xffa1887f,
                0xff90a4ae
        ));
    }

    private final List<Integer> mColors;

    private final Random mRandom;

    private ColorGenerator(List<Integer> colorList) {
        mColors = colorList;
        mRandom = new Random(System.currentTimeMillis());
    }

    private static ColorGenerator create(List<Integer> colorList) {
        return new ColorGenerator(colorList);
    }

    /**
     * Get the {@link ColorGenerator} instance with default colors.
     *
     * @return Instance of {@link ColorGenerator}
     */
    public static ColorGenerator getDefaultColorGenerator() {
        return defaultColorGenerator;
    }

    /**
     * Get the {@link ColorGenerator} instance with material colors.
     *
     * @return Instance of {@link ColorGenerator}
     */
    public static ColorGenerator getMaterialColorGenerator() {
        return materialColorGenerator;
    }

    /**
     * Get the random color from the color list.
     *
     * @return Color code.
     */
    public int getRandomColor() {
        return mColors.get(mRandom.nextInt(mColors.size()));
    }

    /**
     * Get the random color for the key.
     * Based on the key it will give the color used for previous same key.
     *
     * @param key The key to identoify used colors.
     * @return Colour code.
     */
    public int getColor(Object key) {
        int hashCode = key.hashCode();
        return mColors.get(Math.abs(hashCode) % mColors.size());
    }
}