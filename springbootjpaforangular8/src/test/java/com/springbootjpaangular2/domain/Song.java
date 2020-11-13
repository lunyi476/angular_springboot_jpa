package com.springbootjpaangular2.domain;

import java.util.*;

public class Song {
    private String name;
    private Song nextSong;

    public Song(String name) {
        this.name = name;
    }

    public void setNextSong(Song nextSong) {
        this.nextSong = nextSong;
    }

    public boolean isRepeatingPlaylist() { 
    	
    	Song i = this;
    	HashSet<Song> ls = new HashSet<Song>();
  
    	while (i != null) {
    			if (ls.contains(i))
        			return true;
    			ls.add(i);
    			i = i.nextSong;
    	}
    	
        return false;
    }

    public static void main(String[] args) {
        Song first = new Song("Hello");
        Song second = new Song("Eye of the tiger");

        first.setNextSong(second);
        second.setNextSong(first);

        System.out.println(first.isRepeatingPlaylist());
    }
}