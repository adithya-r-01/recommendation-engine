import org.apache.log4j.BasicConfigurator;
import org.apache.mahout.cf.taste.impl.model.file.FileDataModel;
import org.apache.mahout.cf.taste.impl.neighborhood.ThresholdUserNeighborhood;
import org.apache.mahout.cf.taste.impl.recommender.GenericUserBasedRecommender;
import org.apache.mahout.cf.taste.impl.similarity.CityBlockSimilarity;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.neighborhood.UserNeighborhood;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.apache.mahout.cf.taste.recommender.UserBasedRecommender;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

import org.apache.mahout.cf.taste.impl.model.file.FileDataModel;
import org.apache.mahout.cf.taste.impl.neighborhood.ThresholdUserNeighborhood;
import org.apache.mahout.cf.taste.impl.recommender.GenericUserBasedRecommender;
import org.apache.mahout.cf.taste.impl.similarity.CityBlockSimilarity;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.neighborhood.UserNeighborhood;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.apache.mahout.cf.taste.recommender.UserBasedRecommender;

public class Driver {

        public static void main(String[] args) {

            float threshold = (float) 0.0;
            int usrId = 0;
            int howMany = 0;
            boolean user = true;

            try {
                if (args.length == 3) {
                    user = true;
                    threshold = Float.parseFloat(args[0]);
                    usrId = Integer.parseInt(args[1]);
                    howMany = Integer.parseInt(args[2]);
                }
                if (args.length == 2) {
                    user = false;
                    threshold = Float.parseFloat(args[0]);
                    howMany = Integer.parseInt(args[1]);
                }

            } catch (Exception e) {
                System.out.println("Invalid command line argument!");
            }

            BasicConfigurator.configure();

            if (user) {
                try {
                    List<RecommendedItem> recommendations = userRecc(threshold, usrId, howMany);
                    for (RecommendedItem recommend: recommendations) {
                        System.out.println(recommend);

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            } else {
                try {
                    TreeMap<Long, Float> populars = popularity(threshold, howMany);
                    int count = 0;
                    //LinkedHashMap preserve the ordering of elements in which they are inserted
                    LinkedHashMap<Long, Float> reverseSortedMap = new LinkedHashMap<>();

                    populars.entrySet()
                            .stream()
                            .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                            .forEachOrdered(x -> reverseSortedMap.put(x.getKey(), x.getValue()));

                    System.out.println(reverseSortedMap);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        }

        private static List<RecommendedItem> userRecc (float threshold, int usrId, int howMany) throws Exception {
            List<RecommendedItem> recommendations;

            DataModel model = new FileDataModel(new File("dataset.csv"));
            CityBlockSimilarity similarity = new CityBlockSimilarity(model);
            UserNeighborhood neighborhood = new ThresholdUserNeighborhood(threshold,similarity, model);
            UserBasedRecommender recommender = new GenericUserBasedRecommender(model, neighborhood, similarity);

            // The First argument is the userID and the Second parameter is 'HOW MANY'
            recommendations = recommender.recommend(usrId, howMany);


            return recommendations;
        }

        private static TreeMap<Long, Float> popularity(float threshold, int howMany) throws Exception {
            TreeMap<Long, Float> tM = new TreeMap<>();
            Set<Integer> users = getUsers();

            for (Integer user: users) {
                List<RecommendedItem> temp = userRecc(threshold, user, howMany);
                for (RecommendedItem recommend: temp) {
                    if (tM.containsKey(recommend.getItemID())) {
                        tM.replace(recommend.getItemID(), tM.get(recommend.getItemID()) + recommend.getValue());
                    } else {
                        tM.put(recommend.getItemID(), recommend.getValue());
                    }
                }
            }

            return tM;
        }

        private static Set<Integer> getUsers() {

            Set<Integer> users = new HashSet<>();

            String fileName= "dataset.csv";
            File file= new File(fileName);

            List<List<String>> lines = new ArrayList<>();
            Scanner inputStream;

            try {
                inputStream = new Scanner(file);

                while(inputStream.hasNext()){
                    String line= inputStream.next();
                    String[] values = line.split(",");
                    lines.add(Arrays.asList(values));
                }

                inputStream.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

            for(List<String> line: lines) {
                for (String value: line) {
                    users.add(Integer.parseInt(value));
                    break;
                }
            }

            return users;
        }


}
