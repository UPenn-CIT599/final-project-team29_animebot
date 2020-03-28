import java.util.ArrayList;
import java.util.Map;

public class MyAnimeListDemo {

    public static void demoAnimeMethods(int top) {
        System.out.println("Top Anime:");
        Map<Integer, Anime> topAnime = MyAnimeList.getTopAnime("TV");
        for (int rank : topAnime.keySet()) {
            System.out.println(rank + ": " + topAnime.get(rank).getTitle());
        }
        
        Anime anime = topAnime.get(top);
        System.out.println("\r\nTop " + top + " Anime: " + anime.getTitle());
        
        System.out.println("Prequel: " + anime.getPrequel().get(1).getTitle());
        System.out.println("Sequel: " + anime.getSequel().get(0).getEnglishTitle());
        System.out.println("Score: " + anime.getScore());
        System.out.println("Synopsis: " + anime.getSynopsis());
        System.out.println("Background: " + anime.getBackground());
        
        System.out.println("\r\nEpisodes:");
        Map<Integer, AnimeEpisode> episodes = anime.getEpisodeList();
        for (int e : episodes.keySet()) {
            System.out.println(e + ": " + episodes.get(e).getTitle());
        } 
        
        System.out.println("\r\nNews:");
        ArrayList<News> articles = anime.getNews();
        for (News article : articles) {
            System.out.println("\r\n" + article.getUrl());
            System.out.println(article.getTitle());
            System.out.println(article.getIntro());
        }
        
        System.out.println("\r\nReviews:");
        ArrayList<Review> reviews = anime.getReviews();
        for (Review review : reviews) {
            System.out.println("\r\n" + review.getUrl());
            //System.out.println("Reviewer: " + review.getReviewer().getUsername());
            System.out.println(review.getContent());
        }
        
        System.out.println("\r\nRecommendations:");
        Map<Integer, ? extends MyAnimeListMedia> recommendations = anime.getRecommendations();
        for (int i : recommendations.keySet()) {
            System.out.println(Integer.toString(i) + ": " + recommendations.get(i).getTitle());
        }
    }
    
    public static void demoMangaMethods(int top)
    {
        Map<Integer, Manga> topManga = MyAnimeList.getTopManga(null);
        
        System.out.println("Top Manga:");
        for (int rank : topManga.keySet()) {
            System.out.println(rank + ": " + topManga.get(rank).getTitle());
        }
        
        Manga manga = topManga.get(top);
        System.out.println("\r\nTop " + top + " Manga: " + manga.getTitle());
        
        System.out.println("\r\nNews:");
        ArrayList<News> articles = manga.getNews();
        for (News article : articles) {
            System.out.println("\r\n" + article.getUrl());
            System.out.println(article.getTitle());
            System.out.println(article.getIntro());
        }
        
        System.out.println("\r\nPictures:");
        ArrayList<String> pictures = manga.getPictures();
        for (String picture : pictures) {
            System.out.println(picture);
        }
        
        
        System.out.println("\r\nReviews:");
        ArrayList<Review> reviews = manga.getReviews();
        for (Review review : reviews) {
            System.out.println("\r\n" + review.getUrl());
            System.out.println("Found Helpful: " + review.getHelpfulCount());
            System.out.println(review.getContent());
        }
        
        System.out.println("\r\nRecommendations:");
        Map<Integer, Manga> recommendations = manga.getRecommendations();
        for (int i : recommendations.keySet()) {
            System.out.println(Integer.toString(i) + ": " + recommendations.get(i).getTitle());
        }
    }
    
    public static void demoUserMethods(String username) {
        User user = MyAnimeList.getUser(username);
        System.out.println("Profile of " + user.getUsername());
        
        System.out.println("\r\nAnime List:");
        ArrayList<Anime> animeList = user.getAnimeList();
        for (Anime anime : animeList) {
            System.out.println(anime.getTitle());
        }
        
        System.out.println("\r\nManga List:");
        ArrayList<Manga> mangaList = user.getMangaList();
        for (Manga manga : mangaList) {
            System.out.println(manga.getTitle());
        }
    }
    
    public static void demoSearchForAnime(String title) {
        Map<Integer, Anime> searchResults = MyAnimeList.searchForAnimeByTitle(title, 5);
        System.out.println("Search results for " + title);
        for (int rank : searchResults.keySet()) {
            System.out.println(Integer.toString(rank) + ": " + searchResults.get(rank).getTitle());
        }
    }
    
    public static void demoSearchForManga(String title) {
        Map<Integer, Manga> searchResults = MyAnimeList.searchForMangaByTitle(title, 5);
        System.out.println("Search results for " + title);
        for (int rank : searchResults.keySet()) {
            System.out.println(Integer.toString(rank) + ": " + searchResults.get(rank).getTitle());
        }
    }
    
    public static void main(String[] args) {
        //demoAnimeMethods(2);
        //demoMangaMethods(5);
        //demoUserMethods("Maxine");
        demoSearchForAnime("Meitantei Conan");
        demoSearchForManga("Detective Conan");
    }

}
