package ohtu;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import java.io.IOException;
import java.util.ArrayList;
import org.apache.http.client.fluent.Request;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.util.Map;

public class Main {

    public static void main(String[] args) throws IOException {
        String url = "https://studies.cs.helsinki.fi/courses/courseinfo";
        String kurssitiedotText = Request.Get(url).execute().returnContent().asString();
        
        Gson mapper = new Gson();
        Kurssitiedot[] kurssitiedot = mapper.fromJson(kurssitiedotText, Kurssitiedot[].class);
        
        
        
        url = "https://studies.cs.helsinki.fi/courses/ohtu2018/stats";
        String statsitText = Request.Get(url).execute().returnContent().asString();
        
        String statsResponse = statsitText;

        JsonParser parser = new JsonParser();
        JsonObject parsittuData = parser.parse(statsResponse).getAsJsonObject();
        
        ArrayList<Statsit> ohtu2018Statsit = new ArrayList<>();
        
        for (Map.Entry<String, JsonElement> entry : parsittuData.entrySet()) {
            Statsit statsi = new Statsit();
            ohtu2018Statsit.add(statsi);
            statsi.setStudents(entry.getValue().getAsJsonObject().get("students").getAsInt());
            statsi.setExercise_total(entry.getValue().getAsJsonObject().get("exercise_total").getAsInt());
            statsi.setHour_total(entry.getValue().getAsJsonObject().get("hour_total").getAsInt());
        }
        
        url = "https://studies.cs.helsinki.fi/courses/rails2018/stats";
        statsitText = Request.Get(url).execute().returnContent().asString();
        
        statsResponse = statsitText;

        parsittuData = parser.parse(statsResponse).getAsJsonObject();
        
        ArrayList<Statsit> rails2018Statsit = new ArrayList<>();
        
        for (Map.Entry<String, JsonElement> entry : parsittuData.entrySet()) {
            Statsit statsi = new Statsit();
            rails2018Statsit.add(statsi);
            statsi.setStudents(entry.getValue().getAsJsonObject().get("students").getAsInt());
            statsi.setExercise_total(entry.getValue().getAsJsonObject().get("exercise_total").getAsInt());
            statsi.setHour_total(entry.getValue().getAsJsonObject().get("hour_total").getAsInt());
        }
        
        
        // ÄLÄ laita githubiin omaa opiskelijanumeroasi
        String studentNr = "012345678";
        if ( args.length>0) {
            studentNr = args[0];
        }

        url = "https://studies.cs.helsinki.fi/courses/students/"+studentNr+"/submissions";

        String bodyText = Request.Get(url).execute().returnContent().asString();

        /*System.out.println("json-muotoinen data:");
        System.out.println( bodyText );*/

        mapper = new Gson();
        Submission[] subs = mapper.fromJson(bodyText, Submission[].class);
        
        
        tulostaTulostusKursseille(subs, kurssitiedot, ohtu2018Statsit, rails2018Statsit);

        /*System.out.println("");
        System.out.println("yhteensä: " + tehtavat + " tehtävää " + tunnit + " tuntia");*/
    }
    
    public static void tulostaTulostusKursseille(Submission[] subs, Kurssitiedot[] kurssit, ArrayList<Statsit> ohtu2018Statsit, ArrayList<Statsit> rails2018Statsit) {
        ArrayList<String> kaydytKurssit = new ArrayList<>();
        
        
        for (Kurssitiedot kurssitiedot : kurssit) {
            int tehtavat = 0;
            int tunnit = 0;
            
            for (Submission submission : subs) {
                String kurssi = submission.getCourse();
                if (kurssitiedot.getName().equals(kurssi)) {
                    if (!kaydytKurssit.contains(kurssitiedot.getName())) {
                        kaydytKurssit.add(kurssitiedot.getName());
                        System.out.println(kurssitiedot.getFullName());
                        System.out.println("");
                    }
                    
                    System.out.println("viikko " + submission.getWeek() + ":");
                    System.out.println(submission);
                    tehtavat += submission.getExercisesCount();
                    tunnit += submission.getHours();
                }
            }
            
            if (kaydytKurssit.contains(kurssitiedot.getName())) {
                System.out.println("\nyhteensä: " + tehtavat + "/" + kurssitiedot.getNumberOfExercises() + " tehtävää " + tunnit + " tuntia");
                System.out.println("");
                
                
                if (kurssitiedot.getName().equals("ohtu2018")) {
                    int palautukset = 0;
                    int palautetutTehtavat = 0;
                    int aikaaKaytetty = 0;
                    
                    for (Statsit statsit : ohtu2018Statsit) {
                        palautukset += statsit.getStudents();
                        palautetutTehtavat += statsit.getExercise_total();
                        aikaaKaytetty += statsit.getHour_total();
                    }
                    
                    System.out.println("\nKurssilla yhteensä " + palautukset + " palautusta, palautettuja tehtäviä " + palautetutTehtavat + " kpl, aikaa käytetty yhteensä " + aikaaKaytetty + " tuntia\n");
                } else if (kurssitiedot.getName().equals("rails2018")) {
                    int palautukset = 0;
                    int palautetutTehtavat = 0;
                    int aikaaKaytetty = 0;
                    
                    for (Statsit statsit : rails2018Statsit) {
                        palautukset += statsit.getStudents();
                        palautetutTehtavat += statsit.getExercise_total();
                        aikaaKaytetty += statsit.getHour_total();
                    }
                    
                    System.out.println("\nKurssilla yhteensä " + palautukset + " palautusta, palautettuja tehtäviä " + palautetutTehtavat + " kpl, aikaa käytetty yhteensä " + aikaaKaytetty + " tuntia\n");
                }
                
            }
            
        }
        
        
    }
}