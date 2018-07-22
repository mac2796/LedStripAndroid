package xyz.mateusztarnowski.homecontrol;

import android.util.Log;

import java.util.LinkedList;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by mac2796 on 02.01.18.
 */

public class LightSequence {
    private enum LightSequenceAction {
        WAIT, COLOR, BRIGHTNESS, OFF
    }

    private class LightSequenceStep {
        public LightSequenceAction action;
    }

    private class LightSequenceWaitStep extends LightSequenceStep {
        public int milis;

        public LightSequenceWaitStep(int milis) {
            this.action = LightSequenceAction.WAIT;
            this.milis = milis;
        }
    }

    private class LightSequenceColorStep extends LightSequenceStep {
        public int r, g, b;
        public int l = -1;

        public LightSequenceColorStep(int r, int g, int b) {
            this.action = LightSequenceAction.COLOR;
            this.r = r;
            this.g = g;
            this.b = b;
        }

        public LightSequenceColorStep(int l, int r, int g, int b) {
            this.action = LightSequenceAction.COLOR;
            this.l = l;
            this.r = r;
            this.g = g;
            this.b = b;
        }
    }

    private class LightSequenceBrightnessStep extends LightSequenceStep {
        public int brightness;

        public LightSequenceBrightnessStep(int brightness) {
            this.action = LightSequenceAction.BRIGHTNESS;
            this.brightness = brightness;
        }
    }

    private class LightSequenceOffStep extends LightSequenceStep {
        public LightSequenceOffStep() {
            this.action = LightSequenceAction.OFF;
        }
    }

    private LedStripController controller;
    private LinkedList<LightSequenceStep> steps;

    public LightSequence(LedStripController controller) {
        this.controller = controller;
        steps = new LinkedList<>();
    }

    public LightSequence wait(int milis) {
        steps.add(new LightSequenceWaitStep(milis));
        return this;
    }

    public LightSequence color(int r, int g, int b) {
        steps.add(new LightSequenceColorStep(r, g, b));
        return this;
    }

    public LightSequence color(int l, int r, int g, int b) {
        steps.add(new LightSequenceColorStep(l, r, g, b));
        return this;
    }

    public LightSequence brightness(int b) {
        steps.add(new LightSequenceBrightnessStep(b));
        return this;
    }

    public LightSequence off() {
        steps.add(new LightSequenceOffStep());
        return this;
    }

    public void start() {
        processNextStep();
    }

    private void processNextStep() {
        if (steps.size() <= 0) return;

        switch (steps.peek().action) {
            case WAIT:
                processWaitStep();
                break;
            case COLOR:
                processColorStep();
                processNextStep();
                break;
            case BRIGHTNESS:
                processBrightnessStep();
                processNextStep();
                break;
            case OFF:
                processOffStep();
                processNextStep();
                break;
        }
    }

    private void processWaitStep() {
        LightSequenceWaitStep waitStep = (LightSequenceWaitStep) steps.pop();

        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                processNextStep();
            }
        };
        Timer timer = new Timer();
        timer.schedule(task, waitStep.milis);
    }

    private void processColorStep() {
        LightSequenceColorStep colorStep = (LightSequenceColorStep) steps.pop();
        if (colorStep.l == -1) {
            controller.changeColor(colorStep.r, colorStep.g, colorStep.b);
        } else {
            controller.changeColor(colorStep.l, colorStep.r, colorStep.g, colorStep.b);
        }
    }

    private void processBrightnessStep() {
        LightSequenceBrightnessStep brightnessStep = (LightSequenceBrightnessStep) steps.pop();
        controller.changeBrightness(brightnessStep.brightness);
    }

    private void processOffStep() {
        LightSequenceOffStep offStep = (LightSequenceOffStep) steps.pop();
        controller.turnOff();
    }
}
