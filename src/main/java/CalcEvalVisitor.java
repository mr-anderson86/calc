import main.antlr.ExprBaseVisitor;
import main.antlr.ExprParser;
import org.antlr.v4.runtime.misc.NotNull;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by nitzan
 * 22/07/2013 17:31
 */
public class CalcEvalVisitor extends ExprBaseVisitor<Integer> {

    Map<String, Integer> memory = new HashMap<String, Integer>();

    public Map<String, Integer> getMemory() {
        return memory;
    }

    @Override
    public Integer visitId(@NotNull ExprParser.IdContext ctx) {
        String id = ctx.ID().getText();
        if ( memory.containsKey(id) ) return memory.get(id);
        else throw new RuntimeException("ID was used before assignment");
    }


    @Override
    public Integer visitAssign(@NotNull ExprParser.AssignContext ctx) {
        String id = ctx.ID().getText();
        int value = visit(ctx.expr());
        memory.put(id, value);
        return value;
    }

    @Override
    public Integer visitAssignWithOp(@NotNull ExprParser.AssignWithOpContext ctx) {
        String id = ctx.ID().getText();
        if (! memory.containsKey(id) ) throw new RuntimeException("ID was used before assignment");

        int result = memory.get(id);
        int value = visit(ctx.expr());
        if (ctx.op.getType() == ExprParser.ASSADD) {
            result = result + value;
        } else if (ctx.op.getType() == ExprParser.ASSSUB) {
            result = result - value;
        } else if (ctx.op.getType() == ExprParser.ASSMUL) {
            result = result * value;
        } else if (ctx.op.getType() == ExprParser.ASSDIV) {
            result = result / value;
        }

        memory.put(id, result);
        return result;
    }

    @Override
    public Integer visitIncDec(@NotNull ExprParser.IncDecContext ctx) {
        String id = ctx.ID().getText();
        if (! memory.containsKey(id) ) throw new RuntimeException("ID was used before assignment");
        boolean postfix =  ctx.start.getType() == ExprParser.ID;

        int before = memory.get(id);
        int after = 0;

        if ( ctx.op.getType() == ExprParser.INC ) {
            after = before + 1;
        } else {
            after = before - 1;
        }
        memory.put(id, after);

        //if this is a postfix operator return the original value before applying the operation
        //otherwise apply the operation first and return the result.
        if (postfix){
            return before;
        } else {
            return after;
        }
    }

    @Override
    public Integer visitInt(@NotNull ExprParser.IntContext ctx) {
        return Integer.valueOf(ctx.INT().getText());
    }

    @Override
    public Integer visitParens(@NotNull ExprParser.ParensContext ctx) {
        return visit(ctx.expr());
    }

    @Override
    public Integer visitAddSub(@NotNull ExprParser.AddSubContext ctx) {
        int left = visit(ctx.expr(0));
        int right = visit(ctx.expr(1));
        if ( ctx.op.getType() == ExprParser.SUB ) return left - right;
        return left + right;
    }

    @Override
    public Integer visitMulDiv(@NotNull ExprParser.MulDivContext ctx) {
        int left = visit(ctx.expr(0));
        int right = visit(ctx.expr(1));
        if ( ctx.op.getType() == ExprParser.DIV ) return left / right;
        return left * right;
    }

}
