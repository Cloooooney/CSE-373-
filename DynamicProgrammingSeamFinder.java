package seamfinding;

import seamfinding.energy.EnergyFunction;

import java.util.*;

/**
 * Dynamic programming implementation of the {@link SeamFinder} interface.
 *
 * @see SeamFinder
 */
public class DynamicProgrammingSeamFinder implements SeamFinder {

    @Override
    public List<Integer> findHorizontal(Picture picture, EnergyFunction f) {
        int width = picture.width();
        int height = picture.height();

        double[][] dp = new double[width][height];
        int[][] edgeTo = new int[width][height];

        for (int y = 0; y < height; y++) {
            dp[0][y] = f.apply(picture, 0, y);
        }

        for (int x = 1; x < width; x++) {
            for (int y = 0; y < height; y++) {
                double minCost = dp[x - 1][y];
                int minIndex = y;

                if (y > 0 && dp[x - 1][y - 1] < minCost) {
                    minCost = dp[x - 1][y - 1];
                    minIndex = y - 1;
                }

                if (y < height - 1 && dp[x - 1][y + 1] < minCost) {
                    minCost = dp[x - 1][y + 1];
                    minIndex = y + 1;
                }

                dp[x][y] = minCost + f.apply(picture, x, y);
                edgeTo[x][y] = minIndex;
            }
        }

        int minSeamEnd = 0;
        double minSeamEnergy = dp[width - 1][0];
        for (int y = 1; y < height; y++) {
            if (dp[width - 1][y] < minSeamEnergy) {
                minSeamEnergy = dp[width - 1][y];
                minSeamEnd = y;

            }
        }

        List<Integer> seam = new ArrayList<>();
        int currentY = minSeamEnd;
        for (int x = width - 1; x >= 0; x--) {
            seam.add(currentY);
            currentY = edgeTo[x][currentY];
        }

        Collections.reverse(seam);
        return seam;
    }
}
