package com.fire.scorecounter2.model;

/**
 * Created by brad on 2016/07/04.
 */
public class Challenge {
    private String email;
    private String challenge;
    private long time;

    /**Todo auto inserts press 'alt' + 'ins' on windows and 'alt' + 'shift' + '0' on mac*/
    public Challenge() {
    }

    public Challenge(String email, String challenge, long time) {
        this.email = email;
        this.challenge = challenge;
        this.time = time;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getChallenge() {
        return challenge;
    }

    public void setChallenge(String challenge) {
        this.challenge = challenge;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }
}
