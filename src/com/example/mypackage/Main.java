package com.example.mypackage;

import com.example.mypackage.model.Artist;
import com.example.mypackage.model.Datasource;
import com.example.mypackage.model.SongArtist;

import java.util.List;

public class Main {

    public static void main(String[] args) {
        Datasource datasource = new Datasource();
        if(!datasource.open()){
            System.out.println("Cant' open datasource");
            return;
        }
        List<Artist> artists = datasource.queryArtists(Datasource.ORDER_BY_NONE);
        if(artists == null){
            System.out.println("no artists!");
            return;
        }
        for (Artist artist : artists){
            System.out.println("ID = " + artist.getId() + ", Name = " + artist.getName());
        }

        List<String> albumsForArtist = datasource.queryAlbumsForArtists("Carole King", Datasource.ORDER_BY_DESC);

        for(String album : albumsForArtist){
            System.out.println(album);
        }

        List<SongArtist> songArtists = datasource.queryArtistsForSong("Go Your Own Way",Datasource.ORDER_BY_ASC);
        if(songArtists == null){
            System.out.println("Couldn't find the artist for the song");
            return;
        }

        for(SongArtist artist : songArtists){
            System.out.println("Artist name = " + artist.getArtistName() + " Album name = "+
                    artist.getAlbumName() + " Track = " + artist.getTrack());
        }

        datasource.querySongMetaData();

        int count = datasource.getCount(Datasource.TABLE_SONGS);
        System.out.println("Number of songs is: " +count);

        datasource.createViewForSongArtists();

        songArtists = datasource.querySongInfoView("Heartless");
        if(songArtists.isEmpty()){
            System.out.println("Couldn't find the artist for the song");
            return;
        }

        for(SongArtist artist : songArtists){
            System.out.println("FROM VIEW - Artist name = " + artist.getArtistName()+ " Album name = " + artist.getAlbumName() +
                    " Track number = " + artist.getTrack());
        }
        datasource.close();

    }
}
