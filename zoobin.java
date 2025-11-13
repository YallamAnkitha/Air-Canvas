import java.util.*;

public class Zoobin {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int E = Integer.parseInt(sc.nextLine());
        List<String> initial = new ArrayList<>();
        List<String> expected = new ArrayList<>();

        for (int i = 0; i < E; i++) {
            String[] parts = sc.nextLine().split(" ");
            int a = Integer.parseInt(parts[0]), b = Integer.parseInt(parts[1]);
            initial.add(Math.min(a, b) + "-" + Math.max(a, b));
        }

        for (int i = 0; i < E; i++) {
            String[] parts = sc.nextLine().split(" ");
            int a = Integer.parseInt(parts[0]), b = Integer.parseInt(parts[1]);
            expected.add(Math.min(a, b) + "-" + Math.max(a, b));
        }

        Map<String, Integer> indexMap = new HashMap<>();
        for (int i = 0; i < E; i++) indexMap.put(initial.get(i), i);

        int[] perm = new int[E];
        for (int i = 0; i < E; i++) perm[i] = indexMap.get(expected.get(i));

        boolean[] visited = new boolean[E];
        int cycles = 0;

        for (int i = 0; i < E; i++) {
            if (!visited[i]) {
                int j = i;
                while (!visited[j]) {
                    visited[j] = true;
                    j = perm[j];
                }
                cycles++;
            }
        }

        System.out.println(E - cycles);
    }
}
