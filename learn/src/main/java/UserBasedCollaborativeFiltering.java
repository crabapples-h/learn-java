import java.util.*;

public class UserBasedCollaborativeFiltering {
    // 用户-商品评分数据集
    private double[][] ratings;

    // 构造函数，初始化用户-商品评分数据集
    public UserBasedCollaborativeFiltering(double[][] ratings) {
        this.ratings = ratings;
    }

    // 计算两个用户之间的皮尔逊相关系数
    private double pearsonCorrelation(double[] user1, double[] user2) {
        double sumXY = 0, sumX = 0, sumY = 0, sumXSquare = 0, sumYSquare = 0;
        int n = user1.length;

        // 计算相关系数的分子和分母
        for (int i = 0; i < n; i++) {
            sumXY += user1[i] * user2[i];
            sumX += user1[i];
            sumY += user2[i];
            sumXSquare += Math.pow(user1[i], 2);
            sumYSquare += Math.pow(user2[i], 2);
        }

        double numerator = sumXY - (sumX * sumY) / n;
        double denominator = Math.sqrt((sumXSquare - Math.pow(sumX, 2) / n) * (sumYSquare - Math.pow(sumY, 2) / n));

        // 避免除零错误
        if (denominator == 0) return 0;

        // 计算皮尔逊相关系数
        return numerator / denominator;
    }

    // 获取与目标用户最相似的前 k 个用户
    public List<Integer> getTopSimilarUsers(int targetUser, int k) {
        Map<Integer, Double> similarities = new HashMap<>(); // 保存用户相似度的Map

        // 计算目标用户与其他用户的相似度
        for (int i = 0; i < ratings.length; i++) {
            if (i == targetUser) continue; // 跳过目标用户自身
            double similarity = pearsonCorrelation(ratings[targetUser], ratings[i]); // 计算相似度
            similarities.put(i, similarity); // 存储相似度到Map中
        }

        // 对相似度进行排序
        List<Map.Entry<Integer, Double>> sortedSimilarities = new ArrayList<>(similarities.entrySet());
        System.err.println(sortedSimilarities);

        sortedSimilarities.sort(Map.Entry.comparingByValue(Comparator.reverseOrder())); // 根据相似度降序排序

        // 获取前 k 个最相似的用户
        List<Integer> topSimilarUsers = new ArrayList<>();
        for (int i = 0; i < k && i < sortedSimilarities.size(); i++) {
            topSimilarUsers.add(sortedSimilarities.get(i).getKey()); // 添加相似度最高的前 k 个用户到列表中
        }

        return topSimilarUsers;
    }

    // 根据相似用户的喜好向目标用户推荐商品
    public List<Integer> recommendItems(int targetUser, List<Integer> similarUsers) {
        Map<Integer, Double> recommendedItems = new HashMap<>(); // 保存推荐商品的Map

        // 遍历相似用户的评分情况，将相似用户喜欢的商品添加到推荐列表中
        for (int user : similarUsers) {
            for (int i = 0; i < ratings[user].length; i++) {
                if (ratings[user][i] > 0 && ratings[targetUser][i] == 0) { // 如果相似用户喜欢该商品且目标用户未评分
                    recommendedItems.put(i, recommendedItems.getOrDefault(i, 0.0) + ratings[user][i]); // 将该商品加入推荐列表
                }
            }
        }

        // 对推荐商品进行排序
        List<Map.Entry<Integer, Double>> sortedRecommendedItems = new ArrayList<>(recommendedItems.entrySet());
        sortedRecommendedItems.sort(Map.Entry.comparingByValue(Comparator.reverseOrder())); // 根据评分降序排序

        // 获取推荐的商品列表
        List<Integer> topRecommendedItems = new ArrayList<>();
        for (int i = 0; i < sortedRecommendedItems.size(); i++) {
            topRecommendedItems.add(sortedRecommendedItems.get(i).getKey()); // 添加推荐商品到列表中
        }

        return topRecommendedItems;
    }

    public static void main(String[] args) {
        // 示例评分数据集
        double[][] ratings = {
                {0, 0, 0, 1}, // 0
                {1, 0, 0, 0}, // 1
                {0, 0, 1, 0}, // 2
                {0, 1, 0, 0}, // 3
                {1, 0, 1, 0}, // 4
                {1, 1, 1, 1}, // 5
                {0, 1, 0, 0}, // 6
                {1, 0, 0, 0}, // 7
                {1, 0, 1, 0}, // 8
                {1, 1, 1, 0}, // 9
        };

        UserBasedCollaborativeFiltering cf = new UserBasedCollaborativeFiltering(ratings);
        int targetUser = 3; // 目标用户为第一个用户
        int k = 1; // 指定返回相似用户的数量
        List<Integer> similarUsers = cf.getTopSimilarUsers(targetUser, k); // 获取相似用户列表
        List<Integer> recommendedItems = cf.recommendItems(targetUser, similarUsers); // 获取推荐商品列表

        // 输出结果
        System.out.println("给用户推荐的商品是：");
        for (Integer item : recommendedItems) {
            System.out.println("商品" + (item + 1)); // 打印推荐的商品列表
        }
    }
}
