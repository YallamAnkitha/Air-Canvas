import java.util.*;

public class BoxGame {
    static int N, K;
    static List<String> instructions = new ArrayList<>();
    static Map<String, char[][]> cube = new HashMap<>();
    static final String[] faces = {"base", "back", "top", "front", "left", "right"};

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        N = sc.nextInt();
        K = sc.nextInt();
        sc.nextLine();

        // Read cube faces
        for (String face : faces) {
            char[][] grid = new char[N][N];
            for (int i = 0; i < N; i++) {
                String[] row = sc.nextLine().split(" ");
                for (int j = 0; j < N; j++) {
                    grid[i][j] = row[j].charAt(0);
                }
            }
            cube.put(face, grid);
        }

        // Read instructions
        for (int i = 0; i < K; i++) {
            instructions.add(sc.nextLine());
        }

        // Try skipping each instruction
        for (int skip = 0; skip < K; skip++) {
            Map<String, char[][]> copy = deepCopyCube(cube);
            for (int i = 0; i < K; i++) {
                if (i == skip) continue;
                applyInstruction(copy, instructions.get(i));
            }
            if (hasUniformFace(copy)) {
                System.out.println(instructions.get(skip));
                return;
            }
        }

        // Try fixing one cubelet
        for (String face : faces) {
            for (int i = 0; i < N; i++) {
                for (int j = 0; j < N; j++) {
                    char original = cube.get(face)[i][j];
                    for (char c = 'A'; c <= 'Z'; c++) {
                        if (c == original) continue;
                        Map<String, char[][]> modified = deepCopyCube(cube);
                        modified.get(face)[i][j] = c;
                        for (int skip = 0; skip < K; skip++) {
                            Map<String, char[][]> test = deepCopyCube(modified);
                            for (int k = 0; k < K; k++) {
                                if (k == skip) continue;
                                applyInstruction(test, instructions.get(k));
                            }
                            if (hasUniformFace(test)) {
                                System.out.println("Faulty");
                                System.out.println(instructions.get(skip));
                                return;
                            }
                        }
                    }
                }
            }
        }

        System.out.println("Not Possible");
    }

    static Map<String, char[][]> deepCopyCube(Map<String, char[][]> original) {
        Map<String, char[][]> copy = new HashMap<>();
        for (String face : faces) {
            char[][] src = original.get(face);
            char[][] dst = new char[N][N];
            for (int i = 0; i < N; i++) dst[i] = Arrays.copyOf(src[i], N);
            copy.put(face, dst);
        }
        return copy;
    }

    static boolean hasUniformFace(Map<String, char[][]> cube) {
        for (String face : faces) {
            char[][] grid = cube.get(face);
            char c = grid[0][0];
            boolean same = true;
            for (int i = 0; i < N && same; i++) {
                for (int j = 0; j < N && same; j++) {
                    if (grid[i][j] != c) same = false;
                }
            }
            if (same) return true;
        }
        return false;
    }

    static void applyInstruction(Map<String, char[][]> cube, String instr) {
        // TODO: Implement all 7 instruction types as per problem rules
        // Parse and apply transformations to cube faces
    }
}
