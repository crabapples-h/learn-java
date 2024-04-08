import java.util.*;

public class SimpleCollaborativeFiltering {
    // 用户-商品评分数据集
    private double[][] ratings;

    // 构造函数，初始化用户-商品评分数据集
    public SimpleCollaborativeFiltering(double[][] ratings) {
        this.ratings = ratings;
    }

    // 计算两个商品之间的皮尔逊相关系数
    private double pearsonCorrelation(double[] rating1, double[] rating2) {
        double sumXY = 0, sumX = 0, sumY = 0, sumXSquare = 0, sumYSquare = 0;
        int n = rating1.length;

        // 遍历每个商品，计算相关系数的分子和分母
        for (int i = 0; i < n; i++) {
            sumXY += rating1[i] * rating2[i];
            sumX += rating1[i];
            sumY += rating2[i];
            sumXSquare += Math.pow(rating1[i], 2);
            sumYSquare += Math.pow(rating2[i], 2);
        }

        // 计算皮尔逊相关系数
        double numerator = sumXY - (sumX * sumY) / n;
        double denominator = Math.sqrt((sumXSquare - Math.pow(sumX, 2) / n) * (sumYSquare - Math.pow(sumY, 2) / n));

        // 避免除零错误
        if (denominator == 0) return 0;

        return numerator / denominator;
    }

    // 获取与目标商品最相似的前 k 个商品
    public List<Integer> getTopSimilarItems(int targetItem, int k) {
        Map<Integer, Double> similarities = new HashMap<>();

        // 计算目标商品与其他商品的相似度
        for (int i = 0; i < ratings.length; i++) {
            if (i == targetItem) continue; // 跳过目标商品自身
            double similarity = pearsonCorrelation(ratings[targetItem], ratings[i]);
            similarities.put(i, similarity); // 存储相似度到Map中
        }

        // 对相似度进行排序
        List<Map.Entry<Integer, Double>> sortedSimilarities = new ArrayList<>(similarities.entrySet());
        sortedSimilarities.sort(Map.Entry.comparingByValue(Comparator.reverseOrder()));

        // 获取前 k 个最相似的商品
        List<Integer> topSimilarItems = new ArrayList<>();
        for (int i = 0; i < k && i < sortedSimilarities.size(); i++) {
            topSimilarItems.add(sortedSimilarities.get(i).getKey());
        }

        return topSimilarItems;
    }

    public static void main(String[] args) {
        // 示例评分数据集
        double[][] ratings = {
                {5, 3, 0, 1, 0},
                {4, 0, 0, 1, 4},
                {1, 1, 0, 5, 0},
                {0, 0, 5, 2, 2}
        };

        SimpleCollaborativeFiltering cf = new SimpleCollaborativeFiltering(ratings);
        int targetItem = 0; // 指定目标商品
        int k = 2; // 指定返回相似商品的数量
        List<Integer> topSimilarItems = cf.getTopSimilarItems(targetItem, k);

        // 输出结果
        System.out.println("与商品 " + targetItem + " 最相似的前 " + k + " 个商品是：");
        for (Integer item : topSimilarItems) {
            System.out.println(item);
        }
    }
}
