package com.example.asciigenerator_bot.constants;

import com.example.asciigenerator_bot.entity.WordToSeconds;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class SongsConstants {

    public List<WordToSeconds> itsMyLifeSong(){
        List<WordToSeconds> sentBySecList  = new ArrayList<>();
        sentBySecList.add(new WordToSeconds(null,40L,
                """
                        This ain't a song for the broken-hearted (woah oh, woah oh, oh)
                        No silent prayer for the faith-departed
                        I ain't gonna be just a face in the crowd
                        You're gonna hear my voice when I shout it 
                        """));


        sentBySecList.add(new WordToSeconds(null,35L,
                """
                        It's my life, it's now or never
                        I ain't gonna live forever
                        I just want to live while I'm alive
                        (It's my life) My heart is like an open highway
                        Like Frankie said, "I did it my way"
                        I just wanna live while I'm alive
                        It's my life
                        """));

        sentBySecList.add(new WordToSeconds(null,32L,
                """
                        This is for the ones who stood their ground
                        For Tommy and Gina who never backed down
                        Tomorrow's getting harder make no mistake
                        Luck ain't even lucky, got to make your own breaks
                        """));

        sentBySecList.add(new WordToSeconds(null,64L,
                """
                        It's my life, it's now or never
                        I ain't gonna live forever
                        I just want to live while I'm alive
                        (It's my life) My heart is like an open highway
                        Like Frankie said, "I did it my way"
                        I just wanna live while I'm alive
                        Cause it's my life
                        Better stand tall when they're calling you out
                        Don't bend, don't break, baby, don't back down
                        """));

        sentBySecList.add(new WordToSeconds(null,1L,
                """
                        It's my life, it's now or never
                        I ain't gonna live forever
                        I just want to live while I'm alive
                        (It's my life) My heart is like an open highway
                        Like Frankie said, "I did it my way"
                        I just wanna live while I'm alive
                        It's my life
                        And it's now or never
                        I ain't gonna live forever
                        I just want to live while I'm alive
                        (It's my life) My heart is like an open highway
                        Like Frankie said, "I did it my way"
                        I just wanna live while I'm alive
                        It's my life
                        """));

        return sentBySecList;
    }
}
