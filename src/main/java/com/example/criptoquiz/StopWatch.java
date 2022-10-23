package com.example.criptoquiz;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.control.Label;
import javafx.scene.paint.Stop;
import javafx.util.Duration;

import java.util.concurrent.TimeUnit;

public class StopWatch {

    private long beginTime = 0;
    public static StopWatch lastStopWatch;
    public Label label;
    private Timeline animation;


    public StopWatch(Label label)
    {
        this.label=label;
        StopWatch.lastStopWatch=this;
    }

    public void start() {
        this.beginTime=System.currentTimeMillis();
        this.setAnimation();
        this.animation.play();
    }

    private void setAnimation()
    {
        this.animation = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
            long elapsedTime = System.currentTimeMillis()-this.beginTime;

            this.label.setText(format(elapsedTime));
        }));

        this.animation.setCycleCount(Animation.INDEFINITE);
    }

    public String stopAnimation()
    {
        if(this.animation != null)
            this.animation.stop();

        long endTime = System.currentTimeMillis();
        long elapsedTime = endTime - this.beginTime;
        lastStopWatch=null;
        this.label.setText(format(elapsedTime));

        return format(elapsedTime);
    }

    private void setLabel(Label label)
    {
        this.label=label;
    }

    public static StopWatch getStopWatch(Label label)
    {
        if(StopWatch.lastStopWatch != null)
        {
            StopWatch.lastStopWatch.setLabel(label);
            return StopWatch.lastStopWatch;
        }
        StopWatch.lastStopWatch = new StopWatch(label);
        StopWatch.lastStopWatch.setAnimation();
        StopWatch.lastStopWatch.start();

        return  StopWatch.lastStopWatch;
    }

    public String format(long millis) {
        String hms = String.format("%02d:%02d:%02d", TimeUnit.MILLISECONDS.toHours(millis),
                TimeUnit.MILLISECONDS.toMinutes(millis) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millis)),
                TimeUnit.MILLISECONDS.toSeconds(millis) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis)));

        return  hms;
    }
}