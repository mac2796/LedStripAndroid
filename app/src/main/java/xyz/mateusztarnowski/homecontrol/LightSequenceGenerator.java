package xyz.mateusztarnowski.homecontrol;

import android.graphics.Color;

/**
 * Created by mac2796 on 03.01.18.
 */

public class LightSequenceGenerator {
    private static final int min = 1000;
//    private static final int min = 60000;

    public static LightSequence generateSunriseLightSequence(LedStripController controller) {
        LightSequence lightSequence = new LightSequence(controller);
        lightSequence.brightness(5)
                .color(2, 2, 2)
                .wait(min * 2)
                .color(5, 5, 5)
                .wait(min * 2)
                .color(10, 10, 10)
                .wait(min * 2)
                .color(15, 10, 10)
                .wait(min * 2)
                .color(20, 15, 15)
                .wait(min * 2)
                .color(30, 25, 25)
                .wait(min * 2)
                .color(45, 35, 35)
                .wait(min * 2)
                .color(60, 50, 50)
                .wait(min * 2)
                .color(75, 60, 60)
                .wait(min * 2)
                .color(90, 75, 75)
                .wait(min * 2)
                .color(90, 90, 90);

        return lightSequence;
    }

    public static LightSequence generateLucidDreamSequence(LedStripController controller) {
        LightSequence sequence = new LightSequence(controller);
        sequence.brightness(5)
                .color(80, 80, 80)
                .wait(3000)
                .off();

        return sequence;
    }

    public static LightSequence generateFadeOutSequence(LedStripController ledStripController) {
        LightSequence lightSequence = new LightSequence(ledStripController);

        int currentColor = ledStripController.getColor();
        int cr = Color.red(currentColor);
        int cg = Color.green(currentColor);
        int cb = Color.blue(currentColor);

        while (cr > 0 || cg > 0 || cb > 0) {
            if (cr > 0) cr -= 1;
            if (cg > 0) cg -= 1;
            if (cb > 0) cb -= 1;
            lightSequence.color(cr, cg, cb).wait(1000);
        }

        return lightSequence;
    }

    public static LightSequence generateColorfulSequence(LedStripController ledStripController) {
        LightSequence lightSequence = new LightSequence(ledStripController);

        lightSequence.brightness(5)
                .color(0, 20, 0, 0)
                .color(1, 20, 10, 0)
                .color(2, 20, 20, 0)
                .color(3, 20, 20, 10)
                .color(4, 20, 20, 20)
                .color(5, 20, 0, 0)
                .color(6, 20, 10, 0)
                .color(7, 20, 20, 0)
                .color(8, 20, 20, 10)
                .color(9, 20, 20, 20)
                .color(10, 20, 0, 0)
                .color(11, 20, 10, 0)
                .color(12, 20, 20, 0)
                .color(13, 20, 20, 10)
                .color(14, 20, 20, 20)
                .color(15, 20, 0, 0)
                .color(16, 20, 10, 0)
                .color(17, 20, 20, 0)
                .color(18, 20, 20, 10)
                .color(19, 20, 20, 20)
                .color(20, 20, 0, 0)
                .color(21, 20, 10, 0)
                .color(22, 20, 20, 0)
                .color(23, 20, 20, 10)
                .color(24, 20, 20, 20)
                .color(25, 20, 0, 0)
                .color(26, 20, 10, 0)
                .color(27, 20, 20, 0)
                .color(28, 20, 20, 10)
                .color(29, 20, 20, 20);

        //TODO Implement
        return lightSequence;
    }
}
