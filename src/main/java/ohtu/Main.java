package ohtu;

import com.google.gson.Gson;
import java.io.IOException;
import java.util.ArrayList;
import org.apache.http.client.fluent.Request;

public class Main {
    
    public static String kurssitiedotText;

    public static void main(String[] args) throws IOException {
        String url = "https://studies.cs.helsinki.fi/courses/courseinfo";
        kurssitiedotText = Request.Get(url).execute().returnContent().asString();
        
        Gson mapper = new Gson();
        Kurssitiedot[] kurssitiedot = mapper.fromJson(kurssitiedotText, Kurssitiedot[].class);
        
        for (Kurssitiedot kurssitieto : kurssitiedot) {
            System.out.println(kurssitieto);
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
        
        
        tulostaTulostusKursseille(subs, kurssitiedot);

        /*System.out.println("");
        System.out.println("yhteensä: " + tehtavat + " tehtävää " + tunnit + " tuntia");*/
    }
    
    public static void tulostaTulostusKursseille(Submission[] subs, Kurssitiedot[] kurssit) {
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
            }
            
        }
        
        
    }
}