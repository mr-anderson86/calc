grammar Expr;

prog:   stat+ ;

stat:   ID '=' expr NEWLINE                         # assign
    |   ID op=('+='|'-='|'*='|'/=')  expr NEWLINE   # assignWithOp
    |   NEWLINE                                     # blank
    ;

expr:   INT                         # int
    |   ID                          # id
    |   '(' expr ')'                # parens
    |   op=('--'|'++') ID           # IncDec
    |   ID op=('--'|'++')           # IncDec
    |   expr op=('/'|'*') expr      # MulDiv
    |   expr op=('+'|'-') expr      # AddSub
    ;



MUL :   '*' ;
DIV :   '/' ;
ADD :   '+' ;
SUB :   '-' ;
INC :   '++' ;
DEC :   '--' ;
ASSMUL :   '*=' ;
ASSDIV :   '/=' ;
ASSADD :   '+=' ;
ASSSUB :   '-=' ;
ID  :   [a-zA-Z]+ ;      // match identifiers
INT :   [0-9]+ ;         // match integers
NEWLINE:'\r'? '\n' ;     // return newlines to parser (is end-statement signal)
WS  :   [ \t]+ -> skip ; // toss out whitespace

