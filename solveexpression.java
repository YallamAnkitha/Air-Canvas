import java.util.*;

public class SolveExpression {
    static Map<String, String> symbolMap = new HashMap<>();
    static List<String> expressionSymbols = new ArrayList<>();

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String[] digits = new String[10];
        String[] ops = new String[5];
        String[] expr = new String[3];

        for (int i = 0; i < 3; i++) digits[i] = sc.nextLine();
        for (int i = 0; i < 3; i++) ops[i] = sc.nextLine();
        for (int i = 0; i < 3; i++) expr[i] = sc.nextLine();

        for (int i = 0; i < 10; i++) {
            String bin = extractBinary(digits, i);
            symbolMap.put(bin, String.valueOf(i));
        }

        String[] opNames = {"|", "&", "~", "(", ")"};
        for (int i = 0; i < 5; i++) {
            String bin = extractBinary(ops, i);
            symbolMap.put(bin, opNames[i]);
        }

        int total = expr[0].length() / 3;
        for (int i = 0; i < total; i++) {
            String bin = extractBinary(expr, i);
            expressionSymbols.add(symbolMap.get(bin));
        }

        String exprStr = String.join("", expressionSymbols);
        int result = evaluate(exprStr);
        System.out.println(result);
    }

    static String extractBinary(String[] lines, int index) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 3; i++) {
            for (int j = index * 3; j < index * 3 + 3; j++) {
                char c = lines[i].charAt(j);
                sb.append(c == ' ' ? '0' : '1');
            }
        }
        return sb.toString();
    }

    static int evaluate(String expr) {
        Stack<Integer> values = new Stack<>();
        Stack<Character> ops = new Stack<>();
        for (int i = 0; i < expr.length(); ) {
            char c = expr.charAt(i);
            if (Character.isDigit(c)) {
                int num = 0;
                while (i < expr.length() && Character.isDigit(expr.charAt(i))) {
                    num = num * 10 + (expr.charAt(i) - '0');
                    i++;
                }
                values.push(num);
            } else if (c == '(') {
                ops.push(c);
                i++;
            } else if (c == ')') {
                while (!ops.isEmpty() && ops.peek() != '(')
                    values.push(applyOp(ops.pop(), values.pop(), values.pop()));
                ops.pop();
                i++;
            } else {
                while (!ops.isEmpty() && precedence(ops.peek()) >= precedence(c))
                    values.push(applyOp(ops.pop(), values.pop(), values.pop()));
                ops.push(c);
                i++;
            }
        }
        while (!ops.isEmpty())
            values.push(applyOp(ops.pop(), values.pop(), values.pop()));
        return values.pop();
    }

    static int applyOp(char op, int b, int a) {
        if (op == '&') return a & b;
        if (op == '|') return a | b;
        return 0;
    }

    static int precedence(char op) {
        if (op == '~') return 3;
        if (op == '|') return 1;
        if (op == '&') return 2;
        return 0;
    }
}
