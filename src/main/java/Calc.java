/**
 * Created by nitzan
 * 22/07/2013 16:51
 */

import main.antlr.ExprLexer;
import main.antlr.ExprParser;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.Nullable;
import org.antlr.v4.runtime.misc.ParseCancellationException;
import org.antlr.v4.runtime.tree.ParseTree;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

public class Calc {
    public static void main(String[] args) {
        try {
            String inputFile = null;
            if ( args.length>0 ) inputFile = args[0];
            else {
                System.out.println("Type the calculator expressions and press enter after each expression, press ctrl+D when done.");
            }
            InputStream is = System.in;
            if ( inputFile!=null ) is = new FileInputStream(inputFile);

            Map<String, Integer> results = parseCalc(is);

            boolean first = true;
            StringBuilder sb = new StringBuilder();
            sb.append("(");
            for (Map.Entry<String, Integer> result : results.entrySet()){
                if (!first) sb.append(",");
                first = false;
                sb.append(result.getKey()).append("=").append(result.getValue());
            }
            sb.append(")");

            System.out.println(sb.toString());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }


    protected static Map<String, Integer> parseCalc(InputStream is) throws IOException {
        try {
            ANTLRInputStream input = new ANTLRInputStream(is);
            ExprLexer lexer = new ExprLexer(input);
            lexer.addErrorListener(new BaseErrorListener() {
                @Override
                public void syntaxError(Recognizer<?, ?> recognizer, @Nullable Object offendingSymbol, int line, int charPositionInLine, String msg, @Nullable RecognitionException e) {
                    throw new RuntimeException("The lexer encountered an error.");
                }
            });
            CommonTokenStream tokens = new CommonTokenStream(lexer);

            ExprParser parser = new ExprParser(tokens);
            parser.setErrorHandler(new BailErrorStrategy());

            ParseTree tree = parser.prog();

            CalcEvalVisitor cev = new CalcEvalVisitor();
            cev.visit(tree);

            return cev.getMemory();
        } catch (ParseCancellationException pce) {
            throw new RuntimeException("The parser encountered an error in the input.");
        }
    }


}
