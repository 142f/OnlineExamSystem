package com.rabbiter.oes.entity;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;
import java.util.Objects;

public class Replay {
    private Integer messageId;

    private Integer replayId;

    private String replay;

    @JsonFormat(pattern = "yyyy-MM-dd", timezone="GMT+8")
    private Date replayTime;

    public Integer getMessageId() {
        return messageId;
    }

    public void setMessageId(Integer messageId) {
        this.messageId = messageId;
    }

    public Integer getReplayId() {
        return replayId;
    }

    public void setReplayId(Integer replayId) {
        this.replayId = replayId;
    }

    public String getReplay() {
        return replay;
    }

    public void setReplay(String replay) {
        this.replay = replay;
    }

    public Date getReplayTime() {
        return replayTime;
    }

    public void setReplayTime(Date replayTime) {
        this.replayTime = replayTime;
    }

    @Override
    public String toString() {
        return "Replay{" +
                "messageId=" + messageId +
                ", replayId=" + replayId +
                ", replay='" + replay + '\'' +
                ", replayTime=" + replayTime +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Replay replay1 = (Replay) o;
        return Objects.equals(messageId, replay1.messageId) && Objects.equals(replayId, replay1.replayId) && Objects.equals(replay, replay1.replay) && Objects.equals(replayTime, replay1.replayTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(messageId, replayId, replay, replayTime);
    }

    public Replay(Integer messageId, Integer replayId, String replay, Date replayTime) {
        this.messageId = messageId;
        this.replayId = replayId;
        this.replay = replay;
        this.replayTime = replayTime;
    }

    public Replay() {
    }
}